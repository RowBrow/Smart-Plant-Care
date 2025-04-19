package org.example.smartplantcare.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MyDB {
    Connection conn = null;

    public MyDB(){
        if(conn==null)open();
    }
    public void open(){
        try {
            String url = "jdbc:sqlite:identifier.sqlite";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("cannot open");
            if (conn != null) close();
        };
    }
    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("cannot close");
        }
        conn = null;
    }
    public void cmd(String sql){
        if(conn==null)open();
        if(conn==null){System.out.println("No connection");return;}
        Statement stmt=null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e ) {
            System.out.println("Error in statement "+sql);
        }
        try {
            if (stmt != null) { stmt.close(); }
        } catch (SQLException e ) {
            System.out.println("Error in statement "+sql);
        }
    }

    public Measurement queryOneMeasurement(String query) {
        Measurement res = new Measurement();
        if (conn == null) open();
        if (conn == null) {
            System.out.println("No connection");
            return res;
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
                //앞에서 빈 measurement 가져오고 여기서 값 입력
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


}