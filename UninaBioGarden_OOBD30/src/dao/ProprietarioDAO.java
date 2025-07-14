package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Controller;
import entità.Coltivatore;
import entità.Coltura;
import entità.Lotto;
import entità.Progetto;
import entità.ProprietarioDiLotto;
import entità.Raccolto;

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
	
	
	/* ****************************** */

	public ProprietarioDiLotto FindSpecificProprietario(ProprietarioDiLotto p) throws SQLException {
	    return FindSpecificProprietario(p.getCF());
	}
	
	/* ****************************** */
	
	public ArrayList<Lotto> GetLottiProprietario(String CF) throws SQLException {
        String sql =
            "SELECT l.idLotto, l.NumColture, l.NomeLotto, l.CF_Proprietario, l.idProgetto "
            + "FROM Lotto l "
            + "WHERE l.CF_Proprietario = ? ";
        
        ArrayList<Lotto> foundLotti = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CF);                      
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idL = rs.getInt("idLotto");
                    int numColt = rs.getInt("NumColture");
                    String nomeLotto = rs.getString("NomeLotto");
                    ProprietarioDiLotto prop = new ProprietarioDiLotto("", "", "", "", rs.getString("CF_Proprietario"));
                    Progetto Proj = c.progettoDAO.FindSpecificProgetto(rs.getInt("idProgetto"));
                    ArrayList<Coltura> colture = c.coltureDAO.GetColtureOfLotto(idL);
                    ArrayList<Coltivatore> coltivatori = c.coltivatoreDAO.GetColtivatoriLotto(idL);
                    ArrayList<Raccolto> raccolti = c.raccoltoDAO.GetRaccoltiLotto(idL);
                    
                    Lotto lotto = new Lotto(idL, numColt, nomeLotto, prop, Proj, colture, coltivatori, raccolti);
                    
                    foundLotti.add(lotto);
                }
            }
        }
        return foundLotti;
    }
	
	/* ****************************** */

	public ArrayList<Lotto> GetLottiProprietario(ProprietarioDiLotto p) throws SQLException {
	    return GetLottiProprietario(p.getCF());
	}
	
	/* ****************************** */

	
	/* INSERT FUNCTIONS */
	
	public boolean AddLottoToProprietario(ProprietarioDiLotto p, Lotto l) throws SQLException {
	    return AddLottoToProprietario(p.getCF(), l.getIdLotto());
	}
	
	/* ****************************** */

	public boolean AddLottoToProprietario(String CF, int idL) throws SQLException {
	    String sql = "SELECT * FROM Lotto WHERE idLotto = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1, idL);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next() && rs.getString("CF_Proprietario") == null) {
	            	String updateSql = "UPDATE Lotto SET CF_Proprietario = ? WHERE idLotto = ?";
	                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
	                    updateStmt.setString(1, CF);
	                    updateStmt.setInt(2, idL);
	                    int rowsUpdated = updateStmt.executeUpdate();	                    
	                    return rowsUpdated > 0; 
	                }
	                
	            } else {
	                System.out.println("Lotto already has a Proprietario or does not exist.");
	                return false; 
	            }
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/* ****************************** */

	public boolean AddLottoToProprietario(ProprietarioDiLotto p, int idL) throws SQLException {
	    return AddLottoToProprietario(p.getCF(), idL);
	}
	
	/* ****************************** */

	
	
	/* REMOVAL FUNCTIONS*/
	
	public boolean RemoveLottoFromProprietario(ProprietarioDiLotto p, Lotto l) throws SQLException {
	    return RemoveLottoFromProprietario(p.getCF(), l.getIdLotto());
	}
	
	/* ****************************** */

	public boolean RemoveLottoFromProprietario(String CF, int idL) throws SQLException {
	    String sql = "SELECT * FROM Lotto WHERE idLotto = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1, idL);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next() && rs.getString("CF_Proprietario").equals(CF)) {
	                String updateSql = "UPDATE Lotto SET CF_Proprietario = NULL WHERE idLotto = ?";
	                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
	                    updateStmt.setInt(1, idL);
	                    int rowsUpdated = updateStmt.executeUpdate();

	                    
	                    return rowsUpdated > 0; 
	                }
	            } else {
	                System.out.println("Lotto does not belong to the specified Proprietario or does not exist.");
	                return false; 
	            }
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/* ****************************** */

	public boolean RemoveLottoFromProprietario(ProprietarioDiLotto p, int idL) throws SQLException {
	    return RemoveLottoFromProprietario(p.getCF(), idL);
	}
	
	/* ****************************** */

	
	
	


}
