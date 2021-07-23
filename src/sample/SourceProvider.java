package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SourceProvider {
    private static final String URL = "jdbc:sqlserver://localhost;database=coursework";
    private static final String USER = "hosp";
    private static final String PASSWORD = "newpass";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
