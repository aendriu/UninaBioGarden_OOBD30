package dao;

import java.io.IOException;
	
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.Controller;
import entità.Coltura;
import entità.Lotto;
import java.sql.Statement;


public class ColturaDAO extends DAO {

	public ColturaDAO(String filePath, Controller c) throws IOException, SQLException {
		super(filePath, c);
	}
	
	// RETRIEVAL FUNCTIONS
	
	public Coltura FindSpecificColtura(int idColtura) throws SQLException {
		String sql = "SELECT * FROM coltura WHERE idcoltura = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idColtura);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Coltura(
						    rs.getInt("idColtura"),
						    rs.getString("nomeColtura"),
						    rs.getString("tempomaturazione"),
						    rs.getString("giornoSemina"),
						    rs.getInt("idLotto")
						);
					}
			}
		}
		return null;
	}
	
	/* ************************* */
	
	public Coltura FindSpecificColtura(Coltura c) throws SQLException {
		if (c == null || c.getIdColtura() <= 0) {
			throw new IllegalArgumentException("Coltura non valida: " + c);
		}
		return FindSpecificColtura(c.getIdColtura());
	}
	
	/* ************************* */
	
	public ArrayList<Coltura> GetColtureOfLotto(int idLotto) throws SQLException {
		String sql = "SELECT * FROM coltura WHERE idLotto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idLotto);
			try (ResultSet rs = stmt.executeQuery()) {
				ArrayList<Coltura> coltureList = new ArrayList<>();
				while (rs.next()) {
					 // Leggi come stringa
	                String tempoMaturazioneStr = rs.getString("tempomaturazione");

	                // Estrai i giorni (es. "40 days")
	                long giorni = Long.parseLong(tempoMaturazioneStr.split(" ")[0]);
	                Duration durata = Duration.ofDays(giorni);
					String giornoSeminaStr = rs.getString("giornoSemina");
					LocalDate giornoSemina = LocalDate.parse(giornoSeminaStr); 

					coltureList.add(new Coltura(
						rs.getInt("idColtura"),
						rs.getString("nomeColtura"),
						rs.getInt("idLotto")
					));
				}
				return new ArrayList<>(coltureList);
			}
		}
	}
	
	/* ************************* */

	public ArrayList<Coltura> GetColtureOfLotto(Lotto l) throws SQLException {
		if (l == null || l.getIdLotto() <= 0) {
			throw new IllegalArgumentException("Lotto non valido: " + l);
		}
		return GetColtureOfLotto(l.getIdLotto());
	}
	
	
	/* ************************* */
	
	public ArrayList<Coltura> GetColturaOfLottoByNomeColtura(String nomeColt, int idLotto) throws SQLException {
		String sql = "SELECT * FROM coltura WHERE nomeColtura = ? AND idLotto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, nomeColt);
			stmt.setInt(2, idLotto);
			try (ResultSet rs = stmt.executeQuery()) {
				java.util.List<Coltura> coltureList = new java.util.ArrayList<>();
				while (rs.next()) {
					java.sql.Time time = rs.getTime("tempomaturazione");
					Duration durata = Duration.ofSeconds(time.toLocalTime().toSecondOfDay());

					String giornoSeminaStr = rs.getString("giornoSemina");
					LocalDate giornoSemina = LocalDate.parse(giornoSeminaStr); 

					coltureList.add(new Coltura(
						rs.getInt("idColtura"),
						rs.getString("nomeColtura"),
						rs.getInt("idLotto")
					));
				}
				return new ArrayList<>(coltureList);
			}
		}
		
		
	}
	
	/* ************************* */

	// Restituisce una lista di colture di un lotto specifico filtrate per nome della coltura	
	public ArrayList<Coltura> GetColturaOfLottoByNomeColtura(String nomeColt, Lotto l) throws SQLException {
		if (l == null || l.getIdLotto() <= 0) {
			throw new IllegalArgumentException("Lotto non valido: " + l);
		}
		return GetColturaOfLottoByNomeColtura(nomeColt, l.getIdLotto());
	}
	
	
	/* INSERT FUNCTIONS */
	
	public int InsertColtura(Coltura c) throws SQLException {
		return InsertColtura(c.getNomeColtura(), c.getIdLotto());
	}
	
	/* ************************* */

	public int InsertColtura(String nomeColt, int idLotto) {
	    Coltura c = new Coltura(nomeColt, idLotto); // se il formato non è corretto, il costruttore lancia un errore
	    String sql = "INSERT INTO coltura (nomeColtura, tempomaturazione, giornosemina, idLotto) VALUES (?, ?::interval, ?, ?)";

	    try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        stmt.setString(1, nomeColt);
	        stmt.setString(2, c.getTempoMaturazione());
	        stmt.setNull(3, java.sql.Types.DATE);
	        stmt.setInt(4, idLotto);

	        stmt.executeUpdate();

	        try (ResultSet rs = stmt.getGeneratedKeys()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            } else {
	                throw new SQLException("InsertColtura fallita: nessun id restituito.");
	            }
	        }

	    } catch (org.postgresql.util.PSQLException e) {
	        System.out.println("L'inserimento porterebbe il lotto ad avere troppe colture!");
	        return -1;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1;
	    }
	}

	
	/* REMOVAL FUNCTIONS */
	
	public boolean RemoveColtura(Coltura c) throws SQLException {
		return RemoveColtura(c.getIdColtura());
	}
	
	/* ************************* */
	
	public boolean RemoveColtura(int idColtura) throws SQLException {
		if (idColtura <= 0) {
			throw new IllegalArgumentException("IdColtura non valido: " + idColtura);
		}
		String sql = "DELETE FROM coltura WHERE idColtura = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idColtura);
			return stmt.executeUpdate() > 0; 
		}
	}
	

	

	
	
	
	
	


}
