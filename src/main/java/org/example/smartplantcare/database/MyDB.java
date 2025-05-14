package org.example.smartplantcare.database;

import org.example.smartplantcare.model.Measurement;

import java.sql.*;

public class MyDB {
    Connection connection = null;

    public MyDB() {
        open();
    }
    public void open() {
        try {
            String url = "jdbc:sqlite:identifier.sqlite";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("cannot open");
            if (connection != null) {
                close();
            }
        };
    }
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("cannot close");
        }
        connection = null;
    }

    public void cmd(String command) {
        if(connection == null) {
            open();
        }

        if(connection == null) {
            System.out.println("No connection");
            return;
        }

        Statement stmt = null;

        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(command);
        } catch (SQLException e) {
            System.out.println("Error in statement "+command);
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in statement "+command);
        }
    }

    public Measurement queryOneMeasurement(String query) {
        if (connection == null) {
            open();
        }

        if (connection == null) {
            System.out.println("No connection");
            return null;
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Get the
            String deviceId = resultSet.getString("device_id");
            String timestamp = resultSet.getString("datetime");
            int light = resultSet.getInt("light");
            float temp = resultSet.getFloat("temp");
            int water = resultSet.getInt("water");
            float humidity = resultSet.getFloat("humidity");

            return new Measurement(deviceId, timestamp, light, temp, water, humidity);
        } catch (SQLException e) {
            System.out.println("Error in statement: " + query);
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing statement: " + query );
        }
        return null;
    }

    public void insertMeasurement(String query,  Measurement measurement) throws SQLException {
        if (connection == null) {
            open();
        }

        if (connection == null) {
            throw new SQLException("Connection to the database failed");
        }
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, measurement.deviceId());
            pstmt.setString(2, measurement.timestamp());
            pstmt.setInt(3, measurement.light()); //INT
            pstmt.setFloat(4, measurement.temp());
            pstmt.setInt(5, measurement.water()); //INT
            pstmt.setFloat(6, measurement.humidity());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in statement: " + query);
        }
    }
}