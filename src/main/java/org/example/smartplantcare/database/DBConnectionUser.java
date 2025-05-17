package org.example.smartplantcare.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Connecting SQLite, creating TABLE, and saving/finding userInfo from DB
public class DBConnectionUser {
    public static Connection getConnection() {
        try {
            String url = "jdbc:sqlite:users.db";
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}