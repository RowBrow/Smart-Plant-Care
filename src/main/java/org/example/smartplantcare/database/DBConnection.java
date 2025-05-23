package org.example.smartplantcare.database;

import org.example.smartplantcare.model.Measurement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/// A singleton class responsible
/// for managing connections to the
/// database.
public class DBConnection {
    /// Holds the single copy of the
    /// database class
    static DBConnection instance;

    /// Returns the unique instance
    /// of DBConnection
    public static DBConnection getInstance() {
        // If there was no instance made already
        if (instance == null) {
            // Make a new instance
            instance = new DBConnection();
        }
        // Return existing copy of DBConnection
        return instance;
    }

    Connection connection = null;

    /// Constructs the single
    /// instance of DBConnection
    ///
    /// It is private so that
    /// other classes can not
    /// call the constructor.
    private DBConnection() {
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
                String timestamp = resultSet.getString("timestamp");
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

    public Measurement getOneMeasurement(String query) {
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
            String timestamp = resultSet.getString("timestamp");
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
        INSERT INTO measurement\s
        (device_id, timestamp, light, temp, water, humidity)\s
        VALUES\s
        (?, ?, ?, ?, ?, ?)
       \s""";

        try {
            PreparedStatement stmt = connection.prepareStatement(insertionStatement);
            stmt.setString(1, measurement.deviceId());
            stmt.setString(2, measurement.timestamp());
            stmt.setInt(3, measurement.light()); //INT
            stmt.setFloat(4, measurement.temp());
            stmt.setInt(5, measurement.water()); //INT
            stmt.setFloat(6, measurement.humidity());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in statement: " + insertionStatement);
        }
    }
}