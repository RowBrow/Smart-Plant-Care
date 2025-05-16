package org.example.smartplantcare.database;

import org.example.smartplantcare.model.Measurement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void command(String command) {
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

    public List<Measurement> getMeasurements(String query) {
        List<Measurement> measurements = new ArrayList<>();
        if (connection == null) {
            open();
        }

        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String deviceId = resultSet.getString("device_id");
                String timestamp = resultSet.getString("datetime");
                int light = resultSet.getInt("light");
                float temp = resultSet.getFloat("temp");
                int water = resultSet.getInt("water");
                float humidity = resultSet.getFloat("humidity");

                measurements.add(new Measurement(deviceId, timestamp, light, temp, water, humidity));
            }
        }
        catch (SQLException e) {
            System.out.println("Error in statement "+query);
        }

        return measurements;
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

    public void insertMeasurement(Measurement measurement) throws SQLException {
        if (connection == null) {
            open();
        }

        if (connection == null) {
            throw new SQLException("Connection to the database failed");
        }

        String insertionStatement = """
        INSERT OR IGNORE INTO measurement 
        (id, device_id, timestamp, light, temp, water, humidity) 
        VALUES 
        (?, ?, ?, ?, ?, ?, ?)
        """;

        try {
            PreparedStatement stmt = connection.prepareStatement(insertionStatement);
            stmt.setInt(1, 0); // The database should decide the ID of the measurement
            stmt.setString(2, measurement.deviceId());
            stmt.setString(3, measurement.timestamp());
            stmt.setInt(4, measurement.light()); //INT
            stmt.setFloat(5, measurement.temp());
            stmt.setInt(6, measurement.water()); //INT
            stmt.setFloat(7, measurement.humidity());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in statement: " + insertionStatement);
        }
    }
}