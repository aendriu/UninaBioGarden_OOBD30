package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Controller;
import entità.Utente;

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
	
	// INSERT FUNCIONS
	public boolean InsertUserInto(String tabName, Utente u) {
	    if(tabName.equalsIgnoreCase("proprietariodilotto") || tabName.equalsIgnoreCase("coltivatore")) {
	        String sql = "INSERT INTO " + tabName + " VALUES (?, ?, ?, ?, ?)";
	        try (PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setString(1, u.getNome());
	            ps.setString(2, u.getCognome());
	            ps.setString(3, u.getCF());
	            ps.setString(4, u.getUsername());
	            ps.setString(5, u.getPassword());
	            
	            int rows = ps.executeUpdate();
	            return rows > 0;
	        } catch(SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    return false;
	}
	
	// INSERT FUNCIONS
	public boolean RemoveUserFrom(String tabName, Utente u) {
	    if (tabName.equalsIgnoreCase("proprietariodilotto") || tabName.equalsIgnoreCase("coltivatore")) {
	        String cfColumn = tabName.equalsIgnoreCase("coltivatore") ? "cf_coltivatore" : "cf_proprietario";
	        String sql = "DELETE FROM " + tabName + " WHERE " + cfColumn + " = ?";

	        try (PreparedStatement ps = connection.prepareStatement(sql)) {
	            ps.setString(1, u.getCF());

	            int rows = ps.executeUpdate();
	            return rows > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	    return false;
	}




}
;
