package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Controller;
import entità.ProprietarioDiLotto;

public class ProprietarioDAO extends UtenteDAO{
	
	public ProprietarioDAO(String filePath, Controller c) throws IOException, SQLException {
        super(filePath, c);
    }
	
	
	
	/* RETRIEVAL FUNCTIONS */
    
	public ProprietarioDiLotto FindSpecificProprietario(String CF) throws SQLException {
	    String sql = "SELECT * FROM proprietariodilotto WHERE CF_proprietario = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, CF);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return new ProprietarioDiLotto(
	                    rs.getString("nome"),
	                    rs.getString("cognome"),
	                    rs.getString("CF_proprietario"),
	                    rs.getString("username"),
	                    rs.getString("password")
	                );
	            }
	        }
	    }
	    return null;
	}

}
