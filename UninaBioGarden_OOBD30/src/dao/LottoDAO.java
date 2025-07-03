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
import entità.ProprietarioDiLotto;
import entità.Raccolto;

public class LottoDAO extends DAO {

	public LottoDAO(String filePath, Controller c) throws IOException, SQLException {
		super(filePath, c);
	}

	// RETRIEVAL FUNCTIONS
	
	public Lotto FindSpecificLotto(int idLotto) throws SQLException {
		String sql = "SELECT * FROM lotto WHERE idLotto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idLotto);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Lotto(
						rs.getInt("idLotto"),
						rs.getInt("NumColture"),
						rs.getString("NomeLotto"),
						c.propDAO.FindSpecificProprietario(rs.getString("CF_Proprietario")),
						rs.getInt("idProgetto"),
						c.coltureDAO.GetColtureOfLotto(rs.getInt("idLotto")),
						c.coltivatoreDAO.GetColtivatoriLotto(rs.getInt("idLotto")),
						c.raccoltoDAO.GetRaccoltiLotto(rs.getInt("idLotto"))
					);
				}
			}
		}
		return null;
	}
	
	/* *************** */
	
	public Lotto FindSpecificLotto(Lotto l) throws SQLException {
		if (l == null || l.getIdLotto() <= 0) {
			throw new IllegalArgumentException("Lotto non valido: " + l);
		}
		return FindSpecificLotto(l.getIdLotto());
	}

	

	/* BOOLEAN FUNCIONS */
	
	public boolean doesLottoExist(int idLotto) throws SQLException {
		String sql = "SELECT 1 FROM lotto WHERE idLotto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idLotto);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}
	
}
