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
                    ProprietarioDiLotto prop = new ProprietarioDiLotto("", "", "", "", CF);
                    
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
                    
                    LocalDate inizio = rs.getObject("Inizio", LocalDate.class);
                    LocalDate fine   = rs.getObject("Fine",   LocalDate.class);
                    String intervalStr = rs.getString("TempoLavorato");
                    String tempoLavorato = rs.getString("TempoLavorato");
                    String nome       = rs.getString("NomeAttività");
                    String cf         = rs.getString("CF_coltivatore");
                    String stato      = rs.getString("Stato");
                    int    idAtt      = rs.getInt("idAttività");
                    
                    Attivita a = new Attivita(
                        idAtt,
                        nome,
                        inizio,
                        fine,
                        tempoLavorato,
                        cf,
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
    public void InsertColtivatoreInLotto(Coltivatore colt, Lotto l) throws SQLException {
    	String sql = "INSERT INTO lavora_in (idLotto, CF_coltivatore) VALUES (?,?)";
    	// IF NOT CheckDuplicates() THEN
    	try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, colt.getCF());
            ps.setLong(2, l.getIdLotto());
            ps.executeUpdate();
            
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

