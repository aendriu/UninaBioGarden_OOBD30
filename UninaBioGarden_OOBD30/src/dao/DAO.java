package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import controller.Controller;

public abstract class DAO {
    protected String user;
    protected String password;
    protected String url;
    protected Connection connection;
    protected Controller c;

    // Costruttore
    public DAO(String filePath, Controller c) throws IOException, SQLException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
        }
        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
        url = props.getProperty("db.url");
        connect();

        this.c = c;
    }

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PostgreSQL server successfully!");
        }
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Disconnected from PostgreSQL server.");
        }
    }
    
    protected double GetTempoLavoratoInOre(int idAttivita) throws SQLException {
		String sql = "SELECT EXTRACT(EPOCH FROM tempoLavorato) / 3600 AS ore FROM attività WHERE idAttività = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idAttivita);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getDouble("ore");
				}
			}
		}
		return 0.0;
	}

    
    /* ******************************* */
	
	protected Duration TempoLavoratoDoubleToDuration(double tempoLavorato) {
		if (tempoLavorato < 0) {
			throw new IllegalArgumentException("Il tempo lavorato non può essere negativo: " + tempoLavorato);
		}
		return Duration.ofHours((long) tempoLavorato);
	}

	/* ******************************* */
	
	// In AttivitaDAO
	protected String DurationToStringInterval(Duration duration) {
	    if (duration == null) return "0 seconds";

	    long seconds = duration.getSeconds();
	    long hours = seconds / 3600;
	    long minutes = (seconds % 3600) / 60;
	    long secs = seconds % 60;

	    return String.format("%d hours %d minutes %d seconds", hours, minutes, secs);
	}



    
}
