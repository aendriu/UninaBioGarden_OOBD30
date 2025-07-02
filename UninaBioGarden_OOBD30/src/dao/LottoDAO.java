package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Controller;
import entità.Lotto;

public class LottoDAO extends DAO {

	public LottoDAO(String filePath, Controller c) throws IOException, SQLException {
		super(filePath, c);
	}

	// RETRIEVAL FUNCTIONS
	
	/*
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
						rs.getString("CF_Proprietario"),
						rs.getInt("idProgetto")
					);
				}
			}
		}
		return null;
	}
	*/

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
