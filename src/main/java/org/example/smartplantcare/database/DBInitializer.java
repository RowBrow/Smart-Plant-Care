package org.example.smartplantcare.database;

import java.sql.Connection;
import java.sql.Statement;

public class DBInitializer {
    public static void createUserTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                dob TEXT,
                gender TEXT,
                reservation INTEGER,
                technologies TEXT,
                education TEXT,
                location TEXT,
                username TEXT UNIQUE,
                password TEXT
            );
        """;

        try {
            Connection conn = DBConnectionUser.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
