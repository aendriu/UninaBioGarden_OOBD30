package dao;

import java.sql.Statement;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import entità.Attivita;
import entità.Coltivatore;

public class AttivitaDAO extends DAO {
    
    public AttivitaDAO(String filePath, Controller c) throws IOException, SQLException {
        super(filePath, c);
    }
    
    /* ***** */
    
    /* RETRIEVAL FUNCTIONS */
    public Attivita FindSpecificAttivita(int idAttivita) throws SQLException {
        String sql = "SELECT * FROM attività WHERE idAttività = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAttivita);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Attivita(
                    	rs.getInt("idAttività"),
    					rs.getString("nomeAttività"),
    					rs.getDate("inizio"),
    					rs.getDate("fine"),
    					rs.getString("CF_Coltivatore"),
    					rs.getInt("idColtura"),
   						rs.getTime("TempoLavorato"),
   						rs.getString("stato")
                    );
                }
            }
        }
        return null;
    }

    
    /* ******************************* */
    
    public Attivita FindSpecificAttivita(Attivita a) throws SQLException {
		if (a == null || a.getIdAttivita() <= 0) {
			throw new IllegalArgumentException("Attività non valida: " + a);
		}
		return FindSpecificAttivita(a.getIdAttivita());
	}
    
    /* ******************************* */

    
    public ArrayList<Attivita> GetAttivitaColtivatore(String cf_coltivatore) throws SQLException {
        String sql = "SELECT * FROM attività WHERE cf_Coltivatore = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cf_coltivatore);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Attivita> attivitaList = new ArrayList<>();
                while (rs.next()) {
                    attivitaList.add(new Attivita(
                    		rs.getInt("idAttività"),
    						rs.getString("nomeAttività"),
    						rs.getDate("inizio"),
    						rs.getDate("fine"),
    						rs.getString("CF_Coltivatore"),
    						rs.getInt("idColtura"),
    						rs.getTime("TempoLavorato"),
    						rs.getString("stato")
                        ));
                }
                return new ArrayList<>(attivitaList);
            }
        }
    }
    
    /* ******************************* */

    public ArrayList<Attivita> GetAttivitaOfColtivatoreOnColtura(String CF, int idC) throws SQLException {
    	String sql = "SELECT * FROM attività NATURAL JOIN lavora_in NATURAL JOIN coltura WHERE CF_Coltivatore = ? AND idColtura = ?";
		ArrayList<Attivita> lista = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, CF);
			stmt.setInt(2, idC);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					
					Attivita a = new Attivita(
						rs.getInt("idAttività"),
						rs.getString("nomeAttività"),
						rs.getDate("inizio"),
						rs.getDate("fine"),
						rs.getString("CF_Coltivatore"),
						rs.getInt("idColtura"),
						rs.getTime("TempoLavorato"),							
						rs.getString("stato")
					);
					lista.add(a);
				}
			}
		}
		return new ArrayList<>(lista);
    }
    
    /* ******************************* */

    public ArrayList<Attivita> GetAttivitaColtivatoreOnLotto(String CF, int idL) throws SQLException {
		String sql = "SELECT * FROM attività NATURAL JOIN lavora_in WHERE CF_Coltivatore = ? AND idLotto = ?";
		ArrayList<Attivita> lista = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, CF);
			stmt.setInt(2, idL);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Attivita a = new Attivita(
						rs.getInt("idAttività"),
						rs.getString("nomeAttività"),
						rs.getDate("inizio"),
						rs.getDate("fine"),
						rs.getString("CF_Coltivatore"),
						rs.getInt("idColtura"),
						rs.getTime("TempoLavorato"),							
						rs.getString("stato")
					);
					lista.add(a);
				}
			}
		}
		return new ArrayList<>(lista);
	}
    
    /* ******************************* */
    
    public ArrayList<Attivita> GetAttivitaColtivatore(Coltivatore coltivatore) throws SQLException {
		if (coltivatore == null || coltivatore.getCF() == null || coltivatore.getCF().isEmpty()) {
			throw new IllegalArgumentException("Coltivatore non valido: " + coltivatore);
		}
		return GetAttivitaColtivatore(coltivatore.getCF());
	}
    
    /* ******************************* */
    
    public ArrayList<Attivita> GetAttivitaCompletate() throws SQLException {
        String sql = "SELECT * "
                   + "FROM attività WHERE stato = 'Completata'";
        ArrayList<Attivita> attivitaList = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs   = stmt.executeQuery()) {
            while (rs.next()) {
            	Attivita a = new Attivita(
                	rs.getInt("idAttività"),
        			rs.getString("nomeAttività"),
        			rs.getDate("inizio"),
        			rs.getDate("fine"),
       				rs.getString("CF_Coltivatore"),
       				rs.getInt("idColtura"),
       				rs.getTime("TempoLavorato"),
       				rs.getString("stato")
                );

                attivitaList.add(a);
            }
        }
        return attivitaList;
    }
    
    /* ******************************* */

    public ArrayList<Attivita> GetAttivitaOfLotto(int idLotto, String CF) throws SQLException {
        String sql = """
            SELECT a.*
            FROM attività a
            WHERE a.cf_coltivatore = ?
              AND EXISTS (
                  SELECT 1
                  FROM lavora_in li
                  WHERE li.cf_coltivatore = a.cf_coltivatore
                    AND li.idlotto = ?
              )
            """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, CF);
            stmt.setInt(2, idLotto);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Attivita> attivitaList = new ArrayList<>();
                while (rs.next()) {
                    attivitaList.add(new Attivita(
                    		rs.getInt("idAttività"),
            				rs.getString("nomeAttività"),
            				rs.getDate("inizio"),
            				rs.getDate("fine"),
            				rs.getString("CF_Coltivatore"),
            				rs.getInt("idColtura"),
            				rs.getTime("TempoLavorato"),
            				rs.getString("stato")
                    
                    		));
                }
                return new ArrayList<>(attivitaList);
            }
        }
    }

    
    /* INSERT ATTIVITA */
    
    public int InsertAttivita(Attivita a) throws SQLException {
    	return InsertAttivita(a.getNomeAttivita(), a.getInizio(), a.getFine(), a.getCfColtivatore(), a.getIdColtura(), a.getTempoLavorato(), a.getStato());
    }

    /* ******************************* */
    
    public int InsertAttivita(String nomeAtt, Date inizio, Date fine, String cf, int idColt) throws SQLException {
        if(!isInsertionOnAttivitaValid(nomeAtt, inizio, fine, cf, idColt)) {
        	throw new IllegalStateException("Il metodo InsertAttivita non è stato implementato correttamente.");
        }
    	

        String sql = "INSERT INTO attività (nomeAttività, inizio, fine, cf_Coltivatore) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nomeAtt);
            stmt.setDate(2, inizio);
            stmt.setDate(3, fine);
            stmt.setString(4, cf);

            int affected = stmt.executeUpdate();             
            if (affected == 0) {
                throw new SQLException("Nessuna riga inserita.");
            }

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    throw new SQLException("Nessun ID generato.");
                }
            }
        }
    }

    
    

	/* ******************************* */
    
    public int InsertAttivita(String nomeAtt, Date inizio, Date fine, String cf, int idColt,Time tempoLavorato, String stato) 
            throws SQLException {
    	if(!isInsertionOnAttivitaValid(nomeAtt, inizio, fine, cf, idColt)) {
        	throw new IllegalStateException("Il metodo InsertAttivita non è stato implementato correttamente.");
        }

        String sql = """
            INSERT INTO attività
              (nomeAttività, inizio, fine, tempoLavorato, CF_Coltivatore, stato)
            VALUES (?, ?, ?, ?::interval, ?, ?)
            """;
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nomeAtt);
            stmt.setDate(2, inizio);
            stmt.setDate(3, fine);
           
            stmt.setString(4, tempoLavorato.toLocalTime().toString());
            stmt.setString(5, cf);
            stmt.setString(6, stato);

            int affected = stmt.executeUpdate();             
            if (affected == 0) {
                throw new SQLException("Nessuna riga inserita.");
            }

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    throw new SQLException("Nessun ID generato.");
                }
            }
        }
    }


    /* ******************************* */

    
    /* REMOVE ATTIVITA */
    public boolean RemoveAttivita(int idAttivita) throws SQLException {
        String sql = "DELETE FROM attività WHERE idAttività = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAttivita);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /* ******************************* */
    
    public boolean RemoveAttivita(Attivita a) throws SQLException {
        if (a == null || a.getIdAttivita() <= 0) {
			throw new IllegalArgumentException("Attività non valida: " + a);
		}
		return RemoveAttivita(a.getIdAttivita());
    }
    
    /* ******************************* */
    
    public boolean RemoveAllAttivitaOfColtivatore(String cfColtivatore) throws SQLException {
		String sql = "DELETE FROM attività WHERE cf_Coltivatore = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, cfColtivatore);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}
    
    /* ******************************* */
    
    public boolean RemoveAllAttivitaOfColtivatoreInLotto(String cfColtivatore, int idL) throws SQLException {
    	String sql = "DELETE FROM attività WHERE cf_Coltivatore = ? "
    			+ "AND idLotto IN (SELECT idLotto FROM lavora_in WHERE CF_Coltivatore = ? AND idLotto = ?)";
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, cfColtivatore);
			stmt.setString(2, cfColtivatore);
			stmt.setInt(3, idL);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}

    
    /* UPDATE ATTIVITA */
    
    public boolean UpdateStatoAttivita(int idAttivita, String nuovoStato) throws SQLException {
        if(!isNewStateValid(nuovoStato)) {
            throw new IllegalArgumentException("Stato non valido: " + nuovoStato);
        }
        String sql = "UPDATE attività SET stato = ? WHERE idAttività = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nuovoStato);
            stmt.setInt(2, idAttivita);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /* ******************************* */

    private boolean isNewStateValid(String nuovoStato) {
        String ns = nuovoStato.toLowerCase();
    	if(nuovoStato == null || nuovoStato.isEmpty()) {
            return false;
        }
        if(ns.equals("completata") || ns.equals("in corso") || ns.equals("pianificata")) {
            return true;
        } else {
            return false;
        }
    }
        
    /* ******************************* */

    public boolean UpdateTempoLavoratoAttivita(int idAttivita, Time nuovoTempo) throws SQLException {
    	Attivita a = FindSpecificAttivita(idAttivita);
    	if(WouldExceedMaxTime(a, nuovoTempo)) {
    		UpdateStatoAttivita(idAttivita, "Completata");
    		return true;
    	}
        String sql = "UPDATE attività "
                   + "SET tempoLavorato = (tempoLavorato + ?::interval) "
        		
                   + "WHERE idAttività = ?";
                   
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nuovoTempo.toLocalTime().toString());
            stmt.setInt(2, idAttivita);
            return stmt.executeUpdate() > 0;		
        }
    }
    
    /* ******************************* */

    public boolean WouldExceedMaxTime(Attivita a, Time nuovoTempo) {
        long max = a.getFine().getTime() - a.getInizio().getTime();

        long lavorato = a.getTempoLavorato().getTime();
        long nuovo    = nuovoTempo.getTime();

        return (lavorato + nuovo) >= max;
    }

    /* ******************************* */
    
    private boolean isInsertionOnAttivitaValid(String nomeAtt, Date inizio, Date fine, String cf, int idColt) {
		if (cf == null || cf.isEmpty()) {
			return false;
		}
    	if (nomeAtt == null || inizio == null || fine == null) {
            return false;
        }
        if (inizio.after(fine)) {
			return false;
		}
        List<String> attivitaValide = List.of("Raccolto", "Irrigazione", "Semina", "Applica Pesticida");
        if (!attivitaValide.contains(nomeAtt)) {
            return false;
        }
        if(!isIdColtValid(cf, idColt)) {
        	System.out.println("Il Coltivatore non lavora nel lotto cui appartiene la coltura indicata, ID Coltura non valido: " + idColt);
        	return false;
        }
		return true;
	}
    
    /* ******************************* */

    private boolean isIdColtValid(String cfColtivatore, int idColt) {
        String sql = """
            SELECT COUNT(*) 
            FROM coltura c
            JOIN lavora_in li
              ON c.idlotto = li.idlotto
            WHERE c.idcoltura = ?
              AND li.cf_coltivatore = ?
            """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idColt);
            stmt.setString(2, cfColtivatore);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}