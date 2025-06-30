package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		String sql = "SELECT * FROM attivita WHERE idAttivita = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idAttivita);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Attivita(
						rs.getInt("idAttivita"),
						rs.getString("nomeAttivita"),
						rs.getDate("inizio").toLocalDate(),
						rs.getDate("fine").toLocalDate(),
						rs.getString("tempoLavorato"),
						rs.getString("cfColtivatore"),
						rs.getString("stato")
					);
				}
			}
		}
		return null;
	}
	
	/* ******************************* */
	
	public Attivita[] GetAttivitaColtivatore(String cfColtivatore) throws SQLException {
		String sql = "SELECT * FROM attivita WHERE cfColtivatore = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, cfColtivatore);
			try (ResultSet rs = stmt.executeQuery()) {
				List<Attivita> attivitaList = new ArrayList<>();
				while (rs.next()) {
					attivitaList.add(new Attivita(
						rs.getInt("idAttivita"),
						rs.getString("nomeAttivita"),
						rs.getDate("inizio").toLocalDate(),
						rs.getDate("fine").toLocalDate(),
						rs.getString("tempoLavorato"),
						rs.getString("cfColtivatore"),
						rs.getString("stato")
					));
				}
				return attivitaList.toArray(new Attivita[0]);
			}
		}
	}
	
	public Attivita[] GetAttivitaColtivatore(Coltivatore coltivatore) throws SQLException {
		return GetAttivitaColtivatore(coltivatore.getCF());
	}
	
	/* ******************************* */
	
	public Attivita[] GetAttivitaCompletate() throws SQLException {
		String sql = "SELECT * FROM attivita WHERE stato = 'completata'";
		try (PreparedStatement stmt = connection.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			List<Attivita> attivitaList = new ArrayList<>();
			while (rs.next()) {
				attivitaList.add(new Attivita(
					rs.getInt("idAttivita"),
					rs.getString("nomeAttivita"),
					rs.getDate("inizio").toLocalDate(),
					rs.getDate("fine").toLocalDate(),
					rs.getString("tempoLavorato"),
					rs.getString("cfColtivatore"),
					rs.getString("stato")
				));
			}
			return attivitaList.toArray(new Attivita[0]);
		}
		
	}
	
	/* ******************************* */
	
	/* REMOVE ATTIVITA */
	
	public boolean RemoveAttivita(int idAttivita) throws SQLException {
		String sql = "DELETE FROM attivita WHERE idAttivita = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idAttivita);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}
		
	/* ******************************* */

	public boolean RemoveAttivita(Attivita a) throws SQLException {
		return RemoveAttivita(a.getIdAttivita());	
	}
	
	/* ******************************* */
	
	/* UPDATE ATTIVITA */
	
	public boolean UpdateStatoAttivita(int idAttivita, String nuovoStato) throws SQLException {
		if(!isNewStateValid(nuovoStato)) {
			throw new IllegalArgumentException("Stato non valido: " + nuovoStato);
		}
		String sql = "UPDATE attivita SET stato = ? WHERE idAttivita = ?";
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

	public boolean UpdateStatoAttivita(Attivita a, String nuovoStato) throws SQLException {
		return UpdateStatoAttivita(a.getIdAttivita(), nuovoStato);
	}
	
	/* ******************************* */

	public boolean UpdateTempoLavoratoAttivita(int idAttivita, String nuovoTempoLavorato) throws SQLException {
		String sql = "UPDATE attivita SET tempoLavorato = ? WHERE idAttivita = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, nuovoTempoLavorato);
			stmt.setInt(2, idAttivita);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}
			
	

		
	
	
	

}
