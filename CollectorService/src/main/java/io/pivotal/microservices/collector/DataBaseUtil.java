package io.pivotal.microservices.collector;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DataBaseUtil{
    
    public Connection getConnection() throws SQLException {
        String url = null;
        String db = null;
        String username = null;
        String password = null;
        try {
                System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
	            url = "jdbc:mysql://127.0.0.1:3306";
	            db = "zipkin";
	            username = "feixu";
	            password = "1234";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url+"/"+db+"?useUnicode=true&characterEncoding=utf-8&useSSL=false", username, password);
    }
    
    
}
