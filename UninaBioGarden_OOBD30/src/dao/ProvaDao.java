package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.sql.*;



public class ProvaDao {
	private String user;
    private String password;
    private String url;
    
    public ProvaDao(String filePath) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(filePath);
        props.load(fis);
        fis.close();

        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
        url = props.getProperty("db.url");
    }
    
    public void connect() {
    	try(Connection connection = DriverManager.getConnection(url, user, password);) {
    		if(connection != null) {
    			System.out.println("Connected to PostgresSQL server successfully!");
    		} else {
    			System.out.println("Failed to connect to PostgreSQL server!");
    		}
    		
    		Statement statement = connection.createStatement();
    		ResultSet resSet = statement.executeQuery("SELECT * FROM Coltivatore");
    		
    		while(resSet.next()) {
    		    System.out.println(resSet.getString(1));
    		}

    		
    		
    	} catch (SQLException e){
    		e.printStackTrace();
    	}
    }
    
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}
