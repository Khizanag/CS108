package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector implements DatabaseConfig {

    private static Connection instance;

    public static Connection getInstance() {
        if(instance == null){
            synchronized (DatabaseConnector.class){
                if(instance == null){
                    try {
                        instance = DriverManager.getConnection (
                                SERVER_URL,  USERNAME, PASSWORD);
                    } catch (SQLException ex) { }
                }
            }
        }
        return instance;
    }

    public static void closeConnection(){
        if(instance == null)
            return;
        try {
            instance.close();
        } catch (SQLException e) { }
    }
}
