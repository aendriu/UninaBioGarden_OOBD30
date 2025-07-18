package dao;

import java.beans.Statement;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
						rs.getString("nomeProgetto"),
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

	public Progetto FindSpecificProgetto(Progetto p) throws SQLException {
		if (p == null || p.getIdProgetto() <= 0) {
			throw new IllegalArgumentException("Progetto non valido: " + p);
		}
		return FindSpecificProgetto(p.getIdProgetto());
	}
	
	/* ************************* */

	public ArrayList<Attivita> GetAttivitaProgetto(int idProgetto) {
		String sql = "SELECT * FROM attività NATURAL JOIN progetto_coltivatori NATURAL JOIN progetto WHERE idprogetto = ?";
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
			                rs.getInt("idColtura"),
			                rs.getTime("TempoLavorato"),
			                rs.getString("stato")
					);
					attivitaList.add(attivita);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>(attivitaList);
	}


	/* ************************* */

	public ArrayList<Coltivatore> GetColtivatoriProgetto(int idProgetto) throws SQLException {
		String sql = "SELECT CF_coltivatore FROM progetto_coltivatori WHERE idprogetto = ?";
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
	
	/* ************************* */
	//TODO NUOVA QUERY
	public ArrayList<Lotto> GetLottiWithProgetto() throws SQLException {
		String sql = "SELECT * FROM lotto WHERE idprogetto IS NOT NULL";
		ArrayList<Lotto> lottiList = new ArrayList<>();
		
		try (PreparedStatement stmt = connection.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Lotto lotto = c.lottoDAO.FindSpecificLotto(rs.getInt("idLotto"));
				if (lotto != null) {
					lottiList.add(lotto);
				}
			}
		}
		return new ArrayList<>(lottiList);
	}
	
	public Lotto GetLottoProgetto(int idProgetto) throws SQLException {
		String sql = "SELECT idLotto FROM progetto WHERE idProgetto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idProgetto);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int idLotto = rs.getInt("idLotto");
					return c.lottoDAO.FindSpecificLotto(idLotto);
				}
			}
		}
		return null;
	}
		
	/* ************************* */

	public Lotto GetLottoProgetto(Progetto p) throws SQLException {
		return GetLottoProgetto(p.getIdProgetto());
	}
	
	/* ************************* */
	//TODO modifcata
	public ArrayList<Attivita> GetAllAttivitaProgetto(int idP) throws SQLException {
		String sql = "SELECT * FROM progetto_attività WHERE idProgetto = ?";
		ArrayList<Attivita> attivitaList = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idP);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Attivita attivita = new Attivita(
						rs.getInt("idAttività"),
						rs.getString("nomeAttività"),
						rs.getDate("inizio"),
						rs.getDate("fine"),
						rs.getString("CF_Coltivatore"),
						rs.getInt("idColtura"),
						rs.getTime("TempoLavorato"),
						rs.getString("stato")
					);
					attivitaList.add(attivita);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attivitaList;
	}

	
	/* ************************* */
//TODO metti nome progetto come parametro
	public int InsertProgetto(int annoProg, String CF_prop, int idLotto, String nomeprogetto) throws SQLException {
	    if (annoProg <= 0 || CF_prop == null || CF_prop.isEmpty() || idLotto <= 0) {
	        throw new IllegalArgumentException(
	            "Dati progetto non validi: annoProg=" + annoProg +
	            ", CF_prop=" + CF_prop +
	            ", idLotto=" + idLotto +
	            ", nomeprogetto=" + nomeprogetto
	        );
	    }

	    String sql = "INSERT INTO progetto (annoprogetto, cf_proprietario, idlotto, nomeprogetto) VALUES (?, ?, ?, ?)";

	    try (PreparedStatement stmt = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
	    	stmt.setInt(1, annoProg);
	        stmt.setString(2, CF_prop);
	        stmt.setInt(3, idLotto);
	        stmt.setString(4, nomeprogetto);

	        int affectedRows = stmt.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("InsertProgetto fallita: nessuna riga inserita.");
	        }

	        try (ResultSet rs = stmt.getGeneratedKeys()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            } else {
	                throw new SQLException("InsertProgetto fallita: nessun id restituito.");
	            }
	        }

	    }
	}

	
	/* ************************* */

	public int InsertProgetto(Progetto p) throws SQLException {
		if (p == null || p.getAnnoProgetto() <= 0 || p.getProprietario().getCF() == null || p.getLotto().getIdLotto() <= 0) {
			throw new IllegalArgumentException("Progetto non valido: " + p);
		}
		return InsertProgetto(p.getAnnoProgetto(), p.getProprietario().getCF(), p.getLotto().getIdLotto(), p.getNomeProgetto());
	}
	
	/* ************************* */
	//TODO nuova query
	public boolean InsertColtivatoriInProgetto(int idProgetto, List<String> coltivatoriCF) {
		String sql = "INSERT INTO progetto_coltivatori (idprogetto, CF_coltivatore) VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			for (String cf : coltivatoriCF) {
				stmt.setInt(1, idProgetto);
				stmt.setString(2, cf);
				stmt.addBatch();
			}
			int[] affectedRows = stmt.executeBatch();
			for (int rows : affectedRows) {
				if (rows == 0) {
					return false; // Se almeno una riga non è stata inserita, ritorna false
				}
			}
			return true; // Tutte le righe sono state inserite con successo
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // In caso di errore, ritorna false
		}
	}
	
	//TODO nuova query
	public boolean InsertAttivitàInProgetto(int idprogetto, List<Integer> idattività) {
		String sql = "INSERT INTO progetto_attività (idProgetto, idAttività) VALUES (?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			for (int idAttività : idattività) {
				stmt.setInt(1, idprogetto);
				stmt.setInt(2, idAttività);
				stmt.addBatch();
			}
			int[] affectedRows = stmt.executeBatch();
			for (int rows : affectedRows) {
				if (rows == 0) {
					return false; // Se almeno una riga non è stata inserita, ritorna false
				}
			}
			return true; // Tutte le righe sono state inserite con successo
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // In caso di errore, ritorna false
		}
	}
	
	public boolean RemoveProgettoUsingIdLotto(int idLotto) throws SQLException {
	    if (idLotto <= 0) {
	        throw new IllegalArgumentException("IdL non valido: " + idLotto);
	    }
	    String getProj = "SELECT idprogetto FROM lotto WHERE idlotto = ?";
	    int idProgetto;
	    try (PreparedStatement ps = connection.prepareStatement(getProj)) {
	        ps.setInt(1, idLotto);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (!rs.next()) {
	                return false;  
	            }
	            idProgetto = rs.getInt("idprogetto");
	        }
	    }
	    
	    connection.setAutoCommit(false);
	    try {
	        String sql1 = "UPDATE lotto SET idprogetto = NULL WHERE idlotto = ?";
	        try (PreparedStatement ps1 = connection.prepareStatement(sql1)) {
	            ps1.setInt(1, idLotto);
	            ps1.executeUpdate();
	        }

	        String sql2 = "DELETE FROM progetto WHERE idprogetto = ?";
	        try (PreparedStatement ps2 = connection.prepareStatement(sql2)) {
	            ps2.setInt(1, idProgetto);
	            int deleted = ps2.executeUpdate();
	            connection.commit();
	            return deleted > 0;
	        }

	    } catch (SQLException ex) {
	        connection.rollback();
	        throw ex;
	    } finally {
	        connection.setAutoCommit(true);
	    }
	}

	
	/* ************************* */

	
}






















