package dao;

import java.beans.Statement;
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
                        rs.getString("cf_Coltivatore"),
                        rs.getTime("tempoLavorato"),
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
                            rs.getString("cf_Coltivatore"),
                            rs.getTime("tempoLavorato"),
                            rs.getString("stato")
                        ));
                }
                return new ArrayList<>(attivitaList);
            }
        }
    }
    
    /* ******************************* */
    
    public ArrayList<Attivita> GetAttivitaColtivatore(Coltivatore coltivatore) throws SQLException {
		if (coltivatore == null || coltivatore.getCF() == null || coltivatore.getCF().isEmpty()) {
			throw new IllegalArgumentException("Coltivatore non valido: " + coltivatore);
		}
		return GetAttivitaColtivatore(coltivatore.getCF());
	}
    
    /* ******************************* */
    
    public Attivita[] GetAttivitaCompletate() throws SQLException {
        String sql = "SELECT * "
                   + "FROM attività WHERE stato = 'Completata'";
        List<Attivita> attivitaList = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs   = stmt.executeQuery()) {
            while (rs.next()) {
                int id       = rs.getInt("idAttività");
                String nome  = rs.getString("nomeAttività");
                Date inizio  = rs.getDate("inizio");
                Date fine    = rs.getDate("fine");
                Time t       = rs.getTime("TempoLavorato");
                String cf    = rs.getString("CF_Coltivatore");
                String stato = rs.getString("stato");

                Attivita a = new Attivita(
                    id,
                    nome,
                    inizio,
                    fine,
                    cf,
                    t,
                    stato
                );

                attivitaList.add(a);
            }
        }
        return attivitaList.toArray(new Attivita[0]);
    }
//TODO NUOVA QUERY
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
                            rs.getInt("idattività"),
                            rs.getString("nomeattività"),
                            rs.getDate("inizio"),
                            rs.getDate("fine"),
                            rs.getString("cf_coltivatore"),
                            rs.getTime("tempolavorato"),
                            rs.getString("stato")
                    
                    		));
                }
                return new ArrayList<>(attivitaList);
            }
        }
    }

    /* ******************************* */
    
    /* INSERT ATTIVITA */
    
    public boolean InsertAttivita(Attivita a) throws SQLException {
    
        String sql = "INSERT INTO attività (nomeAttività, inizio, fine, tempoLavorato, CF_Coltivatore, stato) VALUES (?, ?, ?, ?::interval, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, a.getNomeAttivita());
            stmt.setDate(2, a.getInizio());
            stmt.setDate(3, a.getFine());
            
            String tempoStr = (a.getTempoLavorato() != null) 
                ? a.getTempoLavorato().toLocalTime().toString() 
                : "00:00:00";
            stmt.setString(4, tempoStr); 
            
            stmt.setString(5, a.getCfColtivatore());
            stmt.setString(6, a.getStato());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) return false;

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    a.setIdAttivita(keys.getInt(1));
                    System.out.println("idAttivta generated by DB is : " + a.getIdAttivita());
                }
            }

            return true;
        }
    }

    
    /* ******************************* */
    
    public int InsertAttivita(String nomeAtt, Date inizio, Date fine, String cf) throws SQLException {
        if (nomeAtt == null || inizio == null || fine == null || cf == null) {
            throw new IllegalArgumentException("Attività non valida");
        }

        List<String> attivitaValide = List.of("Raccolto", "Irrigazione", "Semina", "Applica Pesticida");
        if (!attivitaValide.contains(nomeAtt)) {
            throw new IllegalArgumentException("Tipo attività non valido: " + nomeAtt);
        }

        String sql = "INSERT INTO attività (nomeAttività, inizio, fine, cf_Coltivatore) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nomeAtt);
            stmt.setDate(2, inizio);
            stmt.setDate(3, fine);
            stmt.setString(4, cf);
            
            int id;
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    id = keys.getInt(1);
                    System.out.println("idAttivta generated by DB is : " + id);
                } else {
                    throw new SQLException("Inserimento attività fallito, nessun ID generato.");
                }
            }

            return id;
        }
    }
    
    /* ******************************* */
    
    public int InsertAttivita(String nome, Date inizio, Date fine, String cfColtivatore, Time tempoLavorato, String stato) throws SQLException {
        if (nome == null || inizio == null || fine == null || cfColtivatore == null || tempoLavorato == null || stato == null) {
            throw new IllegalArgumentException("Attività non valida");
        }

        String sql = "INSERT INTO attività (nomeAttività, inizio, fine, tempoLavorato, CF_Coltivatore, stato) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nome);
            stmt.setDate(2, inizio);
            stmt.setDate(3, fine);
            String tempoStr = tempoLavorato.toLocalTime().toString();
            stmt.setString(4, tempoStr); 
            stmt.setString(5, cfColtivatore);
            stmt.setString(6, stato);
            
            int id;
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    id = keys.getInt(1);
                    System.out.println("idAttivta generated by DB is : " + id);
                } else {
                    throw new SQLException("Inserimento attività fallito, nessun ID generato.");
                }
            }

            return id;
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
        String sql = "DELETE FROM attività WHERE idAttività = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, a.getIdAttivita());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
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
    
    public boolean WouldExceedMaxTime(Attivita a, Time nuovoTempo) {
        long max = a.getFine().getTime() - a.getInizio().getTime();

        long lavorato = a.getTempoLavorato().getTime();
        long nuovo    = nuovoTempo.getTime();

        return (lavorato + nuovo) >= max;
    }



    
    /* ******************************* */
}