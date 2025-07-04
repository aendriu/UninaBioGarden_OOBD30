package dao;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import controller.Controller;
import entità.Attivita;
import entità.Coltivatore;
import entità.Lotto;
import entità.Progetto;
import entità.ProprietarioDiLotto;

public class ProgettoDAO extends DAO {

	// Constructor
	
	public ProgettoDAO(String filePath, Controller c) throws IOException, SQLException {
        super(filePath, c);
    }
	
	// RETRIEVAL FUNCTIONS
	
	public Progetto FindSpecificProgetto(int idProgetto) throws SQLException {
		String sql = "SELECT * FROM progetto WHERE idProgetto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idProgetto);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Progetto(
						rs.getInt("idprogetto"),
						rs.getInt("annoprogetto"),
						c.propDAO.FindSpecificProprietario(rs.getString("cf_proprietario")),
						c.lottoDAO.FindSpecificLotto(rs.getInt("idlotto")),
						GetColtivatoriProgetto(idProgetto),
						GetAttivitaProgetto(idProgetto)
					);
				}
			}
		}
		return null;
	}

	/* ************************* */

	public ArrayList<Attivita> GetAttivitaProgetto(int idProgetto) {
		String sql = "SELECT * FROM progetto_coltivatore WHERE idprogetto = ?";
		ArrayList<Attivita> attivitaList = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idProgetto);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Attivita attivita = new Attivita(
							rs.getInt("idAttività"),
			                rs.getString("nomeAttività"),
			                rs.getDate("inizio"),
			                rs.getDate("fine"),
			                rs.getString("CF_Coltivatore"),
			                rs.getTime("TempoLavorato"),
			                rs.getString("stato")
					);
					attivitaList.add(attivita);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	/* ************************* */

	public ArrayList<Coltivatore> GetColtivatoriProgetto(int idProgetto) throws SQLException {
		String sql = "SELECT CF_coltivatore FROM progetto_coltivatore WHERE idprogetto = ?";
		ArrayList<Coltivatore> coltivatoriList = new ArrayList<>();
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idProgetto);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String CF = rs.getString("CF_coltivatore");
					Coltivatore coltivatore = c.coltivatoreDAO.FindSpecificColtivatore(CF);
					if (coltivatore != null) {
						coltivatoriList.add(coltivatore);
					}
				}
			}
		}
		return new ArrayList<>(coltivatoriList);
	}

	public boolean InsertProgetto(Progetto p2) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* ************************* */

	
}






















