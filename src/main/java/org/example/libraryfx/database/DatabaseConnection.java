package org.example.libraryfx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/library1";
    public static final String USER = "postgres";
    public static final String PASSWORD = "7632";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}

