package org.example.smartplantcare.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Connecting SQLite, creating TABLE, and saving/finding userInfo from DB

public class DBConnection {
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
/*
public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "your_username";
        String password = "your_password";
        return DriverManager.getConnection(url, user, password);
    }
}


public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}

*/


