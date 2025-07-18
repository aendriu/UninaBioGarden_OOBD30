package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import entità.Raccolto;

public class RaccoltoDAO extends DAO {
	public RaccoltoDAO(String filePath, Controller c) throws IOException, SQLException {
		super(filePath, c);
	}
	
	// RETRIEVAL FUNCTIONS
	
	public Raccolto FindSpecificRaccolto(int idRaccolto) throws SQLException {
		String sql = "SELECT * FROM raccolto WHERE idRaccolto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idRaccolto);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					    Raccolto raccolto = new Raccolto(
						rs.getInt("idRaccolto"),
						rs.getString("nomecolturaraccolta"),
						rs.getInt("QuantitàRaccolta"),
						rs.getInt("idLotto")
					);
					return raccolto;
				}
			}
		}
		return null; 
	}
	
	/* *************** */
	
	public Raccolto FindSpecificRaccolto(Raccolto r) throws SQLException {
		if (r == null || r.getIdRaccolto() <= 0) {
			throw new IllegalArgumentException("Raccolto non valido: " + r);
		}
		return FindSpecificRaccolto(r.getIdRaccolto());
	}
	
	/* *************** */

	
	public ArrayList<Raccolto> GetRaccoltiOfLottoByNomeColtura(String nomeColtura, int idLotto) throws SQLException {
		if(!isValidNomeRaccolto(nomeColtura)) {
			throw new IllegalArgumentException("Coltura non valida: " + nomeColtura);
		}
		String sql = "SELECT * FROM raccolto WHERE nomecolturaraccolta = ? AND idLotto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, nomeColtura);
			stmt.setInt(2, idLotto);
			try (ResultSet rs = stmt.executeQuery()) {
				ArrayList<Raccolto> raccoltiList = new ArrayList<>();
				while (rs.next()) {
					raccoltiList.add(new Raccolto(
						rs.getInt("idRaccolto"),
						rs.getString("nomecolturaraccolta"),
						rs.getInt("QuantitàRaccolta"),
						rs.getInt("idLotto"))
					);
				}
				return raccoltiList;
			}
		}
		
	}
	
	/* *************** */

	
	public ArrayList<Raccolto> GetRaccoltiLotto(int idLotto) throws SQLException {
		String sql = "SELECT * FROM raccolto WHERE idLotto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idLotto);
			try (ResultSet rs = stmt.executeQuery()) {
				ArrayList<Raccolto> raccoltiList = new ArrayList<>();
				while (rs.next()) {
					raccoltiList.add(new Raccolto(
						rs.getInt("idRaccolto"),
						rs.getString("nomecolturaraccolta"),
						rs.getInt("QuantitàRaccolta"),
						rs.getInt("idLotto"))
					);
				}
				return raccoltiList;
			}
		}
		
	}
	
	/* *************** */

	public ArrayList<Raccolto> GetRaccoltiLotto(Raccolto r) throws SQLException {
		if (r == null || r.getIdLotto() < 0) {
			throw new IllegalArgumentException("Raccolto non valido: " + r);
		}
		return GetRaccoltiLotto(r.getIdLotto());
	}
	
	/* *************** */
	//TODO nuova query Potente per report
	public List<Map<String, Object>> getStatisticheRaccoltaPerLotto(int idLotto) throws SQLException {
	    String sql = "SELECT nomecolturaraccolta AS coltura, " +
	                 "AVG(QuantitàRaccolta) AS media, " +
	                 "MIN(QuantitàRaccolta) AS min, " +
	                 "MAX(QuantitàRaccolta) AS max, " +
	                 "COUNT(*) AS numeroRaccolte " +
	                 "FROM raccolto " +
	                 "WHERE idLotto = ? " +
	                 "GROUP BY nomecolturaraccolta";
	                 
	    List<Map<String, Object>> result = new ArrayList<>();
	    
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1, idLotto);
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Map<String, Object> stats = new HashMap<>();
	                stats.put("coltura", rs.getString("coltura"));
	                stats.put("media", rs.getDouble("media"));
	                stats.put("min", rs.getDouble("min"));
	                stats.put("max", rs.getDouble("max"));
	                stats.put("numeroRaccolte", rs.getInt("numeroRaccolte"));
	                result.add(stats);
	            }
	        }
	    }
	    return result;
	}

	
	/* INSERT FUNCTIONS */
	public int InsertRaccolto(Raccolto r) throws SQLException {
	    return InsertRaccolto(r.getNomeRaccolto(), r.getQuantitaRaccolta(), r.getIdLotto());
	}

	/* *************** */

	public int InsertRaccolto(String nomeColt, int amount, int idLotto) throws SQLException {
	    if(!isValidNomeRaccolto(nomeColt) || !isValidAmountRaccolto(amount)) {
	        throw new IllegalArgumentException("Coltura non valida: " + nomeColt);
	    }
	    String sql = "INSERT INTO raccolto (nomecolturaraccolta, QuantitàRaccolta, idLotto) VALUES (?, ?, ?)";
	    try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
	        stmt.setString(1, nomeColt);
	        stmt.setInt(2, amount);
	        stmt.setInt(3, idLotto);
	        
	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected == 0) throw new SQLException("Inserimento fallito");
	        
	        try (ResultSet keys = stmt.getGeneratedKeys()) {
	            if (keys.next()) {
	                int id = keys.getInt(1);
	                System.out.println("idRaccolto generated by DB is: " + id);
	                return id; 
	            } else {
	                throw new SQLException("Inserimento riuscito ma nessun ID ottenuto");
	            }
	        }
	    }
	}
	
	/* REMOVAL FUNCTIONS */
	public boolean RemoveRaccolto(Raccolto r) throws SQLException {
		if (r == null || r.getIdRaccolto() <= 0) {
			throw new IllegalArgumentException("Raccolto non valido: " + r);
		}
		String sql = "DELETE FROM raccolto WHERE idRaccolto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, r.getIdRaccolto());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}
	
	/* *************** */
	
	public boolean RemoveRaccolto(int idRaccolto) throws SQLException {
		if (idRaccolto <= 0) {
			throw new IllegalArgumentException("ID Raccolto non valido: " + idRaccolto);
		}
		String sql = "DELETE FROM raccolto WHERE idRaccolto = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idRaccolto);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}


	
	/* BOOLEAN FUNCTIONS */
	public boolean isValidNomeRaccolto(String nomeColtura) {
		if(nomeColtura != null && !nomeColtura.trim().isEmpty()) {
			if(nomeColtura.toLowerCase().equals("pomodoro") || 
			   nomeColtura.toLowerCase().equals("mais") || 
			   nomeColtura.toLowerCase().equals("zucchine") || 
			   nomeColtura.toLowerCase().equals("basilico") || 
			   nomeColtura.toLowerCase().equals("fragole")) {
				return true;
			}
		}
		return false;
	}
	
	/* *************** */

	public boolean isValidAmountRaccolto(int quantitaRaccolta) {
		if(quantitaRaccolta >= 0) {
			return true;
		}
		return false;
	}
	
	/* *************** */
	
	public boolean isValidRaccolto(Raccolto r) throws SQLException {
		if(!isValidNomeRaccolto(r.getNomeRaccolto())) {
			System.out.println("Coltura non valida: " + r.getNomeRaccolto());
			return false;
		} else if (!isValidAmountRaccolto(r.getQuantitaRaccolta())) {
			System.out.println("Quantità raccolta non valida: " + r.getQuantitaRaccolta());
			return false;
		} else {
			return true;
		}
	}

}
