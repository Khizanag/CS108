package metropolises;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Metropolises implements MetropolisesConstants {

    /* private instance variables */
    private Connection connection;
    private Statement statement;

    private MetropolisesTable tm;

    public Metropolises(String username, String password) {
        connectToDataBase(username, password);
        tm = new MetropolisesTable();
        JFrame frame = new MetropolisesFrame(this, tm);
        search("", "",  "-1", POPULATION_GRATER_THAN_TEXT, SEARCH_PARTIAL_LABEL);
    }

    public Metropolises(){
        this(USERNAME, PASSWORD);
    }

    private void connectToDataBase(String username, String password){
        try{
            System.setProperty("jdbc.drivers", "sun.jdbc.odbc.JdbcodbcDriver");
            connection = DriverManager.getConnection(URL, username, password);
            statement = connection.createStatement();
            statement.executeQuery("USE "+ DATABASE_NAME + ";\n");
        } catch(Exception unused){
            System.out.println("Error during connecting to the server");
        };
    }

    /* Searches appropriate Metropolises and updates table */
    public void search(String metropolis, String continent, String populationLabel,
                        String populationMode, String searchMode){
        tm.clearTable();
        String query = getSearchQuery(metropolis, continent, populationLabel, populationMode, searchMode);
        trySearch(query);
        tm.fireTableDataChanged();
    }

    /**
     * Tries to connect MySQL database and get appropriate queries
     * @param query MySQL command as Java String
     */
    private void trySearch(String query){
        try{
            if (connection.isClosed()){
                throw new Exception();
            }
            Statement stmt = connection.createStatement();
            ResultSet set = stmt.executeQuery(query);
            while(set.next()){
                String currMetropolis = set.getString("metropolis");
                String currContinent = set.getString("continent");
                long currPopulation = set.getLong("population");
                tm.addRow(currMetropolis, currContinent, currPopulation);
            }
        } catch(Exception unused){
            System.out.println("'Select' in search couldnt be done");
        };
    }

    public void closeConnection() throws SQLException{
            connection.close();
    }

    /* Retruns query for MySQL language, to select some queries */
    private String getSearchQuery(String metropolis, String continent, String populationLabel,
                                  String populationMode, String searchMode){
        String query = "SELECT * FROM metropolises ";
        query += "WHERE ";

        if(searchMode == SEARCH_EXACT_LABEL) {
            query += "metropolis = '" + metropolis + "'";
            query += " AND continent = '" + continent + "'";
        }else {
            query += "metropolis LIKE '%" + metropolis + "%'";
            query += " AND continent LIKE '%" + continent + "%'";
        }

        if(populationLabel.isEmpty())
            populationLabel = "0";

        if(populationMode == POPULATION_GRATER_THAN_TEXT)
            query += " AND population > " + populationLabel;
        else
            query += " AND population <= " + populationLabel;

        query += ";\n";
        return query;
    }

    public void add(String metropolis, String continent, String populationLabel){
        String query = "INSERT INTO metropolises(metropolis, continent, population)\n";
        query += "VALUES (\"" + metropolis + "\", \"" + continent + "\", " + populationLabel + ");\n";

        tm.clearTable();

        try {
            statement.execute(query);
            tm.addRow(metropolis, continent, Long.parseLong(populationLabel));
        } catch(Exception unused){}

        tm.fireTableDataChanged();
    }

    public static void main(String[] args) {
        // GUI Look And Feel
        // Do this incantation at the start of main() to tell Swing
        // to use the GUI LookAndFeel of the native platform. It's ok
        // to ignore the exception.
        new Metropolises();
    }

    public void dropTable(){
        String query = "DROP TABLE IF EXISTS metropolises;";
    }

    public TableModel getTableModel(){
        return tm;
    }
}
