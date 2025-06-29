package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import controller.Controller;

public abstract class DAO {
    protected String user;
    protected String password;
    protected String url;
    protected Connection connection;
    protected Controller c;

    
    /* COSTRUTTORE */
    public DAO(String filePath, Controller c) throws IOException, SQLException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
        }
        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
        url = props.getProperty("db.url");
        connect();
        
        this.c = c;
    }

    // ***** 
    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PostgreSQL server successfully!");
        }
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Disconnected from PostgreSQL server.");
        }
    }
    
    // BOOLEAN FUNCTIONS 
    
    public boolean ColHasValue(String tabName, String attName, String toCheck) throws SQLException {
        if (!tabName.matches("[a-zA-Z0-9_]+") || !attName.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Invalid table or column name");
        }

        String sql = "SELECT 1 FROM " + tabName + " WHERE " + attName + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, toCheck);
            ResultSet rs = ps.executeQuery();
            return rs.next(); 
        }
     
        
    }
    

    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}


