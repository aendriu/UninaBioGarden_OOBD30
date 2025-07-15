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
import entità.Progetto;
import entità.ProprietarioDiLotto;
import entità.Raccolto;

public class LottoDAO extends DAO {

	public LottoDAO(String filePath, Controller c) throws IOException, SQLException {
		super(filePath, c);
	}
	
	/* RETRIEVAL FUNCTIONS */

	
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
	                    new ProprietarioDiLotto(rs.getString("CF_Proprietario")), 
	                    new Progetto(rs.getInt("idProgetto")), 
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
	
	/* *************** */

	public ProprietarioDiLotto GetProprietarioOfLotto(Lotto l) throws SQLException {
		if (l == null || l.getIdLotto() <= 0) {
			throw new IllegalArgumentException("Lotto non valido: " + l);
		}
		return c.propDAO.FindSpecificProprietario(l.getProprietario().getCF());
	}
	
	
	/* *************** */	

	public ProprietarioDiLotto GetProprietarioOfLotto(int idL) throws SQLException {
		return GetProprietarioOfLotto(FindSpecificLotto(idL));
	}
	
	/* *************** */	
	
	public ArrayList<Coltivatore> GetColtivatoriOfLotto(int idL) throws SQLException {
		if (idL <= 0) {
			throw new IllegalArgumentException("ID Lotto non valido: " + idL);
		}
		return c.coltivatoreDAO.GetColtivatoriLotto(idL);
	}
	
	/* *************** */	

	public Progetto GetProgettoOfLotto(int idL) throws SQLException {
		return c.progettoDAO.FindSpecificProgetto(FindSpecificLotto(idL).getMyProgetto());
	}
	
	/* *************** */	
	
	public ArrayList<Coltura> GetColtureOfLotto(int idL) throws SQLException {
		if (idL <= 0) {
			throw new IllegalArgumentException("ID Lotto non valido: " + idL);
		}
		return c.coltureDAO.GetColtureOfLotto(idL);
	}
	
	/* *************** */
	
	public ArrayList<Raccolto> GetRaccoltiOfLotto(int idL) throws SQLException {
		if (idL <= 0) {
			throw new IllegalArgumentException("ID Lotto non valido: " + idL);
		}
		return c.raccoltoDAO.GetRaccoltiLotto(idL);
	}
	
	/* INSERT FUNCTIONS */
	
	public boolean AddRaccoltoToLotto(Raccolto r) throws SQLException {
		if (r == null || r.getIdLotto() <= 0) {
			throw new IllegalArgumentException("Raccolto non valido: " + r);
		}
		String sql = "INSERT INTO raccolto (nomecolturaraccolta, QuantitàRaccolta, idLotto) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, r.getNomeRaccolto());
			stmt.setInt(2, r.getQuantitaRaccolta());
			stmt.setInt(3, r.getIdLotto());
			return stmt.executeUpdate() > 0;
		}
	} 
	
	/* *************** */
	
	public boolean AddColturaToLotto(Coltura c, int idLotto) throws SQLException {
		if (c == null || idLotto <= 0) {
			throw new IllegalArgumentException("Coltura o ID Lotto non valido: " + c + ", " + idLotto);
		}
		String sql = "INSERT INTO coltura (nomeColtura, idLotto) VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, c.getNomeColtura());
			stmt.setInt(2, idLotto);
			return stmt.executeUpdate() > 0;
		}
	}
	
	/* *************** */
	
	public boolean AddColtivatoreToLotto(Coltivatore coltivatore, int idLotto) throws SQLException {
		if (coltivatore == null || idLotto <= 0) {
			throw new IllegalArgumentException("Coltivatore o ID Lotto non valido: " + coltivatore + ", " + idLotto);
		}
		String sql = "INSERT INTO lavora_in (CF_Coltivatore, idLotto) VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, coltivatore.getCF());
			stmt.setInt(2, idLotto);
			return stmt.executeUpdate() > 0;
		}
	}
	
	/* *************** */
	
	public ArrayList<Lotto> GetLottiSenzaProprietario() throws SQLException {
		String sql = "SELECT * FROM lotto WHERE CF_Proprietario IS NULL";
		ArrayList<Lotto> lottiSenzaProprietario = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Lotto lotto = new Lotto(
					rs.getInt("idLotto"),
					rs.getInt("NumColture"),
					rs.getString("NomeLotto"),
					new ProprietarioDiLotto(rs.getString("CF_Proprietario")), 
					new Progetto(rs.getInt("idProgetto")), 
					c.coltureDAO.GetColtureOfLotto(rs.getInt("idLotto")),
					c.coltivatoreDAO.GetColtivatoriLotto(rs.getInt("idLotto")), 
					c.raccoltoDAO.GetRaccoltiLotto(rs.getInt("idLotto"))
				);
				lottiSenzaProprietario.add(lotto);
			}
		}
		return lottiSenzaProprietario;
	}
	
	/* REMOVE FUNCTIONS */
	
	public boolean RemoveColtivatoreFromLotto(Coltivatore coltivatore, int idLotto) throws SQLException {
		return RemoveColtivatoreFromLotto(coltivatore.getCF(), idLotto);
	}
	
	public boolean RemoveColtivatoreFromLotto(String CF, int idLotto) throws SQLException {
		c.attivitaDAO.RemoveAllAttivitaOfColtivatoreInLotto(CF, idLotto);
		if (CF == null || CF.isEmpty() || idLotto <= 0) {
			throw new IllegalArgumentException("CF o ID Lotto non valido: " + CF + ", " + idLotto);
		}
		String sql = "DELETE FROM lavora_in WHERE CF_Coltivatore = ? AND idLotto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, CF);
			stmt.setInt(2, idLotto);
			return stmt.executeUpdate() > 0;
		}
	}

	
	/* BOOLEAN FUNCIONS */
	
	public boolean DoesLottoExist(int idLotto) throws SQLException {
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

	public ArrayList<Lotto> GetLottiColtivatore(String string) {
		if (string == null || string.isEmpty()) {
			throw new IllegalArgumentException("CF Coltivatore non valido: " + string);
		}
		String sql = "SELECT idLotto FROM lavora_in WHERE CF_Coltivatore = ?";
		ArrayList<Lotto> foundLotti = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, string);                      
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Lotto lotto = FindSpecificLotto(rs.getInt("idLotto"));
					if (lotto != null) {
						foundLotti.add(lotto);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foundLotti;
	}
	
}
