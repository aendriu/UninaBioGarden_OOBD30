package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
						rs.getString("nomeAttività"),
						rs.getDate("inizio").toLocalDate(),
						rs.getDate("fine").toLocalDate(),
						rs.getString("cf_Coltivatore"),
						Duration.parse(rs.getString("tempoLavorato")),
						rs.getString("stato")
					);
				}
			}
		}
		return null;
	}

	
	/* ******************************* */
	
	public Attivita[] GetAttivitaColtivatore(String cf_coltivatore) throws SQLException {
		String sql = "SELECT * FROM attività WHERE cf_Coltivatore = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, cf_coltivatore);
			try (ResultSet rs = stmt.executeQuery()) {
				List<Attivita> attivitaList = new ArrayList<>();
				while (rs.next()) {
					attivitaList.add(new Attivita(
							rs.getString("nomeAttività"),
							rs.getDate("inizio").toLocalDate(),
							rs.getDate("fine").toLocalDate(),
							rs.getString("cf_Coltivatore"),
							Duration.parse(rs.getString("tempoLavorato")),
							rs.getString("stato")
						));
				}
				return attivitaList.toArray(new Attivita[0]);
			}
		}
	}
	
	/* ******************************* */

	
	public Attivita[] GetAttivitaColtivatore(Coltivatore coltivatore) throws SQLException {
		return GetAttivitaColtivatore(coltivatore.getCF());
	}
	
	/* ******************************* */
	
	public Attivita[] GetAttivitaCompletate() throws SQLException {
		String sql = "SELECT * FROM attività WHERE stato = 'Completata'";
		try (PreparedStatement stmt = connection.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			List<Attivita> attivitaList = new ArrayList<>();
			while (rs.next()) {
				attivitaList.add(new Attivita(
					rs.getString("nomeAttività"),
					rs.getDate("inizio").toLocalDate(),
					rs.getDate("fine").toLocalDate(),
					rs.getString("cf_Coltivatore"),
					Duration.parse(rs.getString("tempoLavorato")),
					rs.getString("stato")
				));
			}
			return attivitaList.toArray(new Attivita[0]);
		}
		
	}
	
	/* ******************************* */
	
	/* INSERT ATTIVITA */
	public boolean InsertAttivita(Attivita a) throws SQLException {
		if (a == null || a.getNomeAttivita() == null || a.getInizio() == null || a.getFine() == null || a.getCfColtivatore() == null) {
			throw new IllegalArgumentException("Attività non valida: " + a);
		}
		
		String sql = "INSERT INTO attività (nomeAttività, inizio, fine, tempoLavorato, cf_Coltivatore, stato) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, a.getNomeAttivita());
			stmt.setDate(2, java.sql.Date.valueOf(a.getInizio()));
			stmt.setDate(3, java.sql.Date.valueOf(a.getFine()));
			stmt.setString(5, a.getCfColtivatore());
			stmt.setString(6, a.getStato());
			
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}
	
	/* ******************************* */
	
	public boolean InsertAttivita(String nomeAtt, LocalDateTime inizio, LocalDateTime fine, String cf) throws SQLException {
		if (nomeAtt == null || inizio == null || fine == null || cf == null) {
			throw new IllegalArgumentException("Attività non valida");
		}

		List<String> attivitaValide = List.of("Raccolto", "Irrigazione", "Semina", "Applica Pesticida");
		if (!attivitaValide.contains(nomeAtt)) {
			throw new IllegalArgumentException("Tipo attività non valido: " + nomeAtt);
		}

		String sql = "INSERT INTO attività (nomeAttività, inizio, fine, cf_Coltivatore) VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, nomeAtt);
			stmt.setTimestamp(2, java.sql.Timestamp.valueOf(inizio));
			stmt.setTimestamp(3, java.sql.Timestamp.valueOf(fine));
			stmt.setString(4, cf);

			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
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
		if(nuovoStato == null || nuovoStato.isEmpty()) {
			return false;
		}
		if(nuovoStato.equals("completata") || nuovoStato.equals("in corso") || nuovoStato.equals("annullata")) {
			return true;
		} else {
			return false;
		}
	}
		
	/* ******************************* */

	public boolean UpdateTempoLavoratoAttivita(int idAttivita, String nuovoTempoLavorato) throws SQLException {
		String sql = "UPDATE attività SET tempoLavorato = ? WHERE idAttività = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, nuovoTempoLavorato);
			stmt.setInt(2, idAttivita);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}
			
	

		
	
	
	

}
