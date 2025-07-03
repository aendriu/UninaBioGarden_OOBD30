package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import controller.Controller;
import entità.Attivita;
import entità.Coltivatore;
import entità.Coltura;
import entità.Lotto;
import entità.ProprietarioDiLotto;

import java.sql.*;
import java.time.LocalDate;
import java.time.Duration;




public class ColtivatoreDAO extends UtenteDAO{
 
    
	public ColtivatoreDAO(String filePath, Controller c) throws IOException, SQLException {
        super(filePath, c);
    }
   
    
    /* RETRIEVAL FUNCTIONS */
	
    public Coltivatore FindSpecificColtivatore(String CF) throws SQLException {
    	String CF_Select = "SELECT * FROM coltivatore WHERE CF_coltivatore = ?";
    	PreparedStatement stmt = connection.prepareStatement(CF_Select);
		stmt.setString(1, CF);
    	
		try(ResultSet rs = stmt.executeQuery()) {
			if(rs.next()) {
				return new Coltivatore(
						rs.getString("nome"),
						rs.getString("cognome"),
						rs.getString("CF_coltivatore"),
						rs.getString("username"),
						rs.getString("password")
						//c.attivitaDAO.GetAttivitaColtivatore(CF)
						//c.lottoDAO.GetLottiColtivatore(CF)
				);
			}
		}
		

    	return null;
    }

    
    /* ****************************** */
    
    public Lotto[] GetLottiColtivatore(String CF) throws SQLException {
        String sql =
            "SELECT l.idLotto, l.NumColture, l.NomeLotto, l.CF_Proprietario, l.idProgetto " +
            "FROM Lavora_in li " +
            "  JOIN Lotto l ON li.idLotto = l.idLotto " +
            "WHERE li.CF_Coltivatore = ?";
        
        ArrayList<Lotto> foundLotti = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CF);                      
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idL = rs.getInt("idLotto");
                    int numColt = rs.getInt("NumColture");
                    String nomeLotto = rs.getString("NomeLotto");
                    int idProj = rs.getInt("idProgetto");
                    String CFprop = rs.getString("CF_Proprietario");
                    ProprietarioDiLotto prop = new ProprietarioDiLotto("", "", "", "", CFprop);
                    
                    Lotto lotto = new Lotto(idL, numColt, nomeLotto, prop, idProj);
                    
                    foundLotti.add(lotto);
                }
            }
        }
        return foundLotti.toArray(new Lotto[0]);
    }
    
    /* ****************************** */
    
    public Attivita[] GetAttivitaColtivatore(String CF) throws SQLException {
        String sql =
            "SELECT * " +
            "FROM attività " +
            "WHERE CF_coltivatore = ?";
        
        List<Attivita> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CF);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Date inizio = rs.getDate("Inizio");
                    Date fine   = rs.getDate("Fine");
                    Time tempoLavorato = rs.getTime("TempoLavorato");
                    String nome  = rs.getString("NomeAttività");
                    String cf    = rs.getString("CF_coltivatore");
                    String stato = rs.getString("Stato");

                    Attivita a = new Attivita(
                        nome,
                        inizio,
                        fine,
                        cf,
                        tempoLavorato,
                        stato
                    );
                    lista.add(a);
                }
            }
        }
        return lista.toArray(new Attivita[0]);
    }

    
    /* ****************************** */

    /* INSERT FUNCTIONS */
        
    public boolean InsertColtivatoreInLotto(Coltivatore colt, Lotto l) throws SQLException {
        return InsertColtivatoreInLotto(colt.getCF(), l.getIdLotto());
    }

    /* ************* */

    public boolean InsertColtivatoreInLotto(String CF, int idL) throws SQLException {
        String sql = "INSERT INTO lavora_in (idLotto, CF_coltivatore) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idL);       
            ps.setString(2, CF);
            ps.executeUpdate();
            return true; 
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // violation of unique PK
                return false;
            }
            throw e;
        }
    }
    
    /* ************* */
    



    /* REMOVE FUNCTION */
    
    public boolean RemoveColtivatoreFromLotto(Coltivatore colt, Lotto l) throws SQLException {
        return RemoveColtivatoreFromLotto(colt.getCF(), l.getIdLotto());
    }
    
    /* ************* */

    public boolean RemoveColtivatoreFromLotto(String CF, int idL) throws SQLException {
        String sql = "DELETE FROM lavora_in WHERE idLotto = ? AND CF_coltivatore = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idL);
            ps.setString(2, CF);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }



    
    
    /* ****************************** */
    
    

    
    
    /* OVERRIDES */

	@Override
	public String toString() {
		return "ColtivatoreDAO [user=" + user + ", password=" + password + ", url=" + url + ", connection=" + connection
				+ "]";
	}
    
    
    
    
}

