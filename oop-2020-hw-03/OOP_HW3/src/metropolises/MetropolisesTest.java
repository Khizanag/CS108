package metropolises;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.swing.table.TableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MetropolisesTest implements MetropolisesConstants{

    private Connection connection;
    private Metropolises metropolises;

    @BeforeAll
    void setUp()throws ClassNotFoundException, SQLException {
        metropolises = new Metropolises();
    }

    @AfterAll
    void tearDown()throws SQLException {
        metropolises.dropTable();
    }

    @Test
    public void testAdd(){
        metropolises.search("", "", "-1",
                POPULATION_GRATER_THAN_TEXT, SEARCH_PARTIAL_LABEL);
        metropolises.add("Sakartvelo", "Europe", "4000001");
    }

    @Test
    public void testMain(){
        metropolises.main(null);
    }

    @Test
    public void testEmptyPopulation(){
        metropolises.search("", "", "",
                POPULATION_GRATER_THAN_TEXT, SEARCH_PARTIAL_LABEL);
    }

    @Test
    public void testLessThan(){
        metropolises.search("", "", "-1",
                POPULATION_LESS_THAT_TEXT, SEARCH_PARTIAL_LABEL);
    }

    @Test
    public void testExactMatch(){
        metropolises.search("", "", "-1",
                POPULATION_LESS_THAT_TEXT, SEARCH_EXACT_LABEL);
    }


    @Test
    public void testExceptionOnCloseConnection() throws SQLException{
        metropolises.closeConnection();
        metropolises.search("", "", "", "", "");
    }


    @Test
    public void testWrongUsername() throws SQLException{
        Metropolises newUseredMetro = new Metropolises("wrongUserName", "WrongPassword");
    }

    @Test
    public  void testCloseConnectionTwoTimes() throws SQLException{
        metropolises.closeConnection();
        metropolises.closeConnection();
    }

    @Test
    public void testAddRowResult(){
        metropolises = new Metropolises();
        metropolises.add("Sakartvelo", "Europe", "4000001");
        TableModel tm = metropolises.getTableModel();
        assertEquals(3,tm.getColumnCount());
        assertEquals(1, tm.getRowCount());
        assertEquals("Sakartvelo", tm.getValueAt(0,0));
        assertEquals("Europe", tm.getValueAt(0,1));
//        assertEquals("Sakartveli", tm.getValueAt(0,0));
    }

    @Test
    public void testAddingMultipleRows(){
        metropolises = new Metropolises();
        TableModel tm = metropolises.getTableModel();
        int prevNum = tm.getRowCount();
        assertEquals(3,tm.getColumnCount());
//        assertEquals(1, tm.getRowCount());
        for(int i = 0; i < 50; i++){
            metropolises.add("Sakartvelo", "Europe", "4000001");
            assertEquals(1, tm.getRowCount());
        }

        metropolises.search("", "", "",
                POPULATION_GRATER_THAN_TEXT, SEARCH_PARTIAL_LABEL);

        assertEquals(prevNum+50, tm.getRowCount());

    }

    @Test
    public void testColumnNames(){
        metropolises = new Metropolises();
        TableModel tm = metropolises.getTableModel();
        assertEquals("Metropolis",tm.getColumnName(0));
        assertEquals("Continent",tm.getColumnName(1));
        assertEquals("Population",tm.getColumnName(2));
    }
}