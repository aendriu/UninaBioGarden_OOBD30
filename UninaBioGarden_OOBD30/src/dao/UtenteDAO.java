package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Controller;

public class UtenteDAO extends DAO {

	public UtenteDAO(String filePath, Controller c) throws IOException, SQLException {
		super(filePath, c);
	}
	
	// BOOLEAN FUNCTIONS
	public boolean DoesUsernameExist(String username) throws SQLException {
	    String sql = "SELECT username FROM coltivatore " +
	                 "UNION " +
	                 "SELECT username FROM proprietariodilotto";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            String currUser = rs.getString("username");
	            if (currUser.equals(username)) {
	                return true;
	            }
	        }
	    }

	    return false;
	}


}
