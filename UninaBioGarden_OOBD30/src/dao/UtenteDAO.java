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
	
	public int Where_is_that_username_into (String username) throws SQLException {
	    String sql = "SELECT 'coltivatore' AS table_name FROM coltivatore WHERE username = ? " +
	                 "UNION ALL " +
	                 "SELECT 'proprietariodilotto' AS table_name FROM proprietariodilotto WHERE username = ?";
	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, username);
	        ps.setString(2, username);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getString("table_name").equals("coltivatore") ? 0 : 1;
	        } else {
	            return -1; // Username not found
	        }
	    }
	}
	
	
	public String Convert_UsernameToCF(String username) throws SQLException {
	    String sql = "SELECT cf_coltivatore AS cf FROM coltivatore WHERE username = ? " +
	                 "UNION " +
	                 "SELECT cf_proprietario AS cf FROM proprietariodilotto WHERE username = ?";
	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, username);
	        ps.setString(2, username);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getString("cf");
	        } else {
	            return null;
	        }
	    }
	}
	
	public String Get_Password_of_that_username(String username) throws SQLException {
	    String sql = "SELECT password FROM coltivatore WHERE username = ? " +
	                 "UNION " +
	                 "SELECT password FROM proprietariodilotto WHERE username = ?";
	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, username);
	        ps.setString(2, username);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getString("password");
	        } else {
	            return null;
	        }
	    }
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
	
	public boolean DoesPasswordExist(String password) throws SQLException {
	    String sql = "SELECT password FROM coltivatore " +
	                 "UNION " +
	                 "SELECT password FROM proprietariodilotto";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            String currUser = rs.getString("password");
	            if (currUser.equals(password)) {
	                return true;
	            }
	        }
	    }

	    return false;
	}
	
	public boolean DoesCFExist(String CF) throws SQLException {
	    String sql = "SELECT CF_coltivatore AS cf FROM coltivatore WHERE CF_coltivatore = ? " +
	                 "UNION " +
	                 "SELECT CF_proprietario AS cf FROM proprietariodilotto WHERE CF_proprietario = ?";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, CF);
	        ps.setString(2, CF);
	        ResultSet rs = ps.executeQuery();
	        return rs.next(); // true se il CF esiste, false altrimenti
	    }
	}

	
	
	 //INSERT FUNCIONS
	public boolean InsertUser(Utente u) {
	    String tabName = u.GetTableName();
	    String sql = "INSERT INTO " + tabName + " VALUES (?, ?, ?, ?, ?)";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, u.getNome());
	        ps.setString(2, u.getCognome());
	        ps.setString(3, u.getCF());
	        ps.setString(4, u.getUsername());
	        ps.setString(5, u.getPassword());

	        int rows = ps.executeUpdate();
	        return rows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public boolean InsertUser(Utente u, int tipoUtente) {
	    String tabName;

	    if (tipoUtente == 0) {
	        tabName = "coltivatore";
	    } else {
	        tabName = "proprietariodilotto";
	    }

	    String sql = "INSERT INTO " + tabName + " VALUES (?, ?, ?, ?, ?)";

	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, u.getNome());
	        ps.setString(2, u.getCognome());
	        ps.setString(3, u.getCF());
	        ps.setString(4, u.getUsername());
	        ps.setString(5, u.getPassword());

	        int rows = ps.executeUpdate();
	        return rows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	
	// REMOVE FUNCIONS
	public boolean RemoveUser(Utente u) {
	    String tabName = u.GetTableName();
	    String cfColumn = tabName.equals("coltivatore") ? "cf_coltivatore" : "cf_proprietario";
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




}
;
