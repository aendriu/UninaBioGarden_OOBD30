package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;

import controller.Controller;
import entità.Coltura;
import entità.Lotto;

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
					java.sql.Time time = rs.getTime("tempomaturazione");
					Duration durata = Duration.ofSeconds(time.toLocalTime().toSecondOfDay());

					String giornoSeminaStr = rs.getString("giornoSemina");
					LocalDate giornoSemina = LocalDate.parse(giornoSeminaStr); 

					return new Coltura(
						rs.getInt("idColtura"),
						rs.getString("nomeColtura"),
						durata,
						giornoSemina,
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
	
	public Coltura[] GetColtureOfLotto(int idLotto) throws SQLException {
		String sql = "SELECT * FROM coltura WHERE idLotto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idLotto);
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
						durata,
						giornoSemina,
						rs.getInt("idLotto")
					));
				}
				return coltureList.toArray(new Coltura[0]);
			}
		}
	}
	
	/* ************************* */

	public Coltura[] GetColtureOfLotto(Lotto l) throws SQLException {
		if (l == null || l.getIdLotto() <= 0) {
			throw new IllegalArgumentException("Lotto non valido: " + l);
		}
		return GetColtureOfLotto(l.getIdLotto());
	}
	
	
	/* ************************* */
	
	public Coltura[] GetColturaOfLottoByNomeColtura(String nomeColt, int idLotto) throws SQLException {
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
						durata,
						giornoSemina,
						rs.getInt("idLotto")
					));
				}
				return coltureList.toArray(new Coltura[0]);
			}
		}
		
		
	}
	
	/* ************************* */

	public Coltura[] GetColturaOfLottoByNomeColtura(String nomeColt, Lotto l) throws SQLException {
		if (l == null || l.getIdLotto() <= 0) {
			throw new IllegalArgumentException("Lotto non valido: " + l);
		}
		return GetColturaOfLottoByNomeColtura(nomeColt, l.getIdLotto());
	}
	
	/* ************************* */


	


}
