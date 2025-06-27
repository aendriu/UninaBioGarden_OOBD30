package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import entità.Coltivatore;

import java.sql.*;



public class ColtivatoreDAO {
	private String user;
    private String password;
    private String url;
    private Connection connection;  
        
    public ColtivatoreDAO(String filePath) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(filePath);
        props.load(fis);
        fis.close();

        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
        url = props.getProperty("db.url");
    }
    
    
    /* CONNECT TO DB */
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
    
    /* RETRIEVAL FUNCTIONS */
    public Coltivatore FindSpecificColtivatore(String CF) throws SQLException {
    	String CF_Select = "SELECT * FROM coltivatore WHERE CF_coltivatore = ?";
    	PreparedStatement stmt = connection.prepareStatement(CF_Select);
		stmt.setString(1, CF);
    	
		try(ResultSet rs = stmt.executeQuery()) {
			if(rs.next()) {
				return new Coltivatore(
						rs.getString("username"),
						rs.getString("nome"),
						rs.getString("cognome"),
						rs.getString("password"),
						rs.getString("CF_coltivatore")
				);
			}
		}
		

    	return null;
    }

    /* MISC FUNCTIONS */

	@Override
	public String toString() {
		return "ColtivatoreDAO [user=" + user + ", password=" + password + ", url=" + url + ", connection=" + connection
				+ "]";
	}
    
    
    
    
}

