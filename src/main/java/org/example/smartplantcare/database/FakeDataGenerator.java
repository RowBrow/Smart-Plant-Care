package org.example.smartplantcare.database;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class FakeDataGenerator {
    private static final String DB_URL = "jdbc:sqlite:identifier.sqlite";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                insertFakeData(conn);
                System.out.println("Data inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertFakeData(Connection conn) throws SQLException {

        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM plantData");

        String insertSQL = "INSERT OR IGNORE INTO plantData (datetime, light, temp, water, humidity) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        Random rand = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd hh:mm:ss a");



        // 1. 2025-04-19 17:00:00 ~ 17:59:59 → 10개
        LocalDateTime baseTime1 = LocalDateTime.of(2025, 4, 19, 17, 0);
        for (int i = 0; i < 10; i++) {
            LocalDateTime timestamp = baseTime1.plusMinutes(i * 6); // every 6 minutes
            insertRow(pstmt, timestamp, rand, formatter);
        }

        // 2. 2025-04-18 17:00:00 ~ 2025-04-19 17:00:00 → 24개
        LocalDateTime baseTime2 = LocalDateTime.of(2025, 4, 18, 17, 0);
        for (int i = 0; i < 24; i++) {
            LocalDateTime timestamp = baseTime2.plusHours(i); // every 1 hours
            insertRow(pstmt, timestamp, rand, formatter);
        }

        // 3. 2025-04-12 17:00:00 ~ 2025-04-19 17:00:00 → 24 for each date
        for (int d = 12; d <= 18; d++) {
            LocalDateTime base = LocalDateTime.of(2025, 4, d, 0, 0);
            for (int h = 0; h < 24; h++) {
                LocalDateTime timestamp = base.withHour(h);
                insertRow(pstmt, timestamp, rand, formatter);
            }
        }

        pstmt.close();
    }

    public static void insertRow(PreparedStatement pstmt, LocalDateTime timestamp, Random rand, DateTimeFormatter formatter) throws SQLException {
        pstmt.setString(1, timestamp.format(formatter));
        pstmt.setInt(2, rand.nextInt(11)); // light: 0~10
        pstmt.setInt(3, rand.nextInt(16) + 15); // temperature: 15~30
        pstmt.setInt(4, rand.nextInt(11)); // water: 0~10
        pstmt.setInt(5, rand.nextInt(11)); // nutrient: 0~10

        pstmt.executeUpdate();
        System.out.println("Inserted: " + timestamp.format(formatter));

    }
}

