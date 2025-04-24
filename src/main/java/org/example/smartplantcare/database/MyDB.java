package org.example.smartplantcare.database;

import java.sql.*;

public class MyDB {
    Connection conn = null;

    public MyDB() {
        if(conn == null) {
            open();
        }
    }
    public void open(){
        try {
            String url = "jdbc:sqlite:identifier.sqlite";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("cannot open");
            if (conn != null) {
                close();
            }
        };
    }
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("cannot close");
        }
        conn = null;
    }
    public void cmd(String sql){
        if(conn == null) {
            open();
        }
        if(conn == null) {
            System.out.println("No connection");return;
        }
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error in statement "+sql);
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in statement "+sql);
        }
    }

    public Measurement queryOneMeasurement(String query) {
        Measurement res = null;
        if (conn == null) {
            open();
        }

        if (conn == null) {
            System.out.println("No connection");
            return null;
        }
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                String datetime = rs.getString("datetime");
                float light = rs.getFloat("light");
                float temp = rs.getFloat("temp");
                float water = rs.getFloat("water");
                float humidity = rs.getFloat("humidity");
                //Get the empty measurement from the front and enter the value here
                res = new Measurement(datetime, light, temp, water, humidity);
            }
        } catch (SQLException e) {
            System.out.println("Error in statement: " + query);
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing statement: " + query );
        }
        return res;
    }

    public void insertMeasurement(String query, String datetime, float light, float temp, float water, float humidity) throws SQLException {
        if (conn == null) open();
        if (conn == null) {
            System.out.println("No connection");
        }
        Statement stmt = null;
        try {

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, datetime);
            pstmt.setFloat(2, light); //INT
            pstmt.setFloat(3, temp);
            pstmt.setFloat(4, water); //INT
            pstmt.setFloat(5, humidity);

            pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Error in statement: " + query);
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing statement: " + query );
        }
    }
}