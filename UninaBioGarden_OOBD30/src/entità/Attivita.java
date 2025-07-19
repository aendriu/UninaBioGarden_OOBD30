package entità;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Attivita extends Entita {
	
	private List<String> validNomeAttivita = new ArrayList<>(Arrays.asList("raccolto", "irrigazione", "semina", "applicapesticida"));
    private int idAttivita;
	private String nomeAttivita;
    private java.sql.Date inizio;
    private java.sql.Date fine;  
    private Duration tempoLavorato;
    private String cfColtivatore;
    private int idColtura;
    private String stato;
    
    /* CONSTRUCTORS */
   
    public Attivita(String nomeAttivita, String cfColtivatore, int idColtura) {
    	if (!isValidNomeAttivita(nomeAttivita)) {
			throw new IllegalArgumentException("Nome attività non può essere nullo o vuoto.");
		}
    	nomeAttivita = FormatNomeAttivita(nomeAttivita);
    	this.nomeAttivita = nomeAttivita;
    	this.cfColtivatore = cfColtivatore;
		this.inizio = new Date(System.currentTimeMillis());
		this.fine = new Date(System.currentTimeMillis() + HowMuchTime(nomeAttivita.trim().toLowerCase()));
		this.tempoLavorato = Duration.ZERO;
		this.stato = "Pianificata";
		this.idColtura = idColtura;
		this.tableName = "Attività";
    }

	/* *************** */

    public Attivita(String nomeAttivita, Date inizio, Date fine, String cfColtivatore, int idColtura) {
    	this.nomeAttivita = nomeAttivita;
    	this.inizio = inizio;
    	this.fine = fine;
        this.tempoLavorato = Duration.ZERO; 
    	this.cfColtivatore = cfColtivatore;
    	this.stato = "Pianificata";
    	this.idColtura = idColtura;
    	this.tableName = "Attività";
    }
    
    /* *************** */
    
    public Attivita(String nomeAttivita, Date inizio, Date fine, String cfColtivatore, int idColtura, Duration tempoLavorato, String stato) {
        this.nomeAttivita = nomeAttivita;
        this.inizio = inizio;
        this.fine = fine;
        this.tempoLavorato = tempoLavorato;
        this.cfColtivatore = cfColtivatore;
        this.stato = stato;
        this.idColtura = idColtura;
        tableName = "Attività";
    }
    
    /* *************** */
    
  
    public Attivita(int idAttivita, String nomeAttivita, Date inizio, Date fine, String cfColtivatore, int idColtura, Duration tempoLavorato, String stato) {
    	this.idAttivita = idAttivita;
        this.nomeAttivita = nomeAttivita;
        this.inizio = inizio;
        this.fine = fine;
        this.tempoLavorato = tempoLavorato;
        this.cfColtivatore = cfColtivatore;
        this.stato = stato;
        this.idColtura = idColtura;
        tableName = "Attività";
    }

    /* ************************************************** */
    
    public void AddTempoLavorato(Duration d) {
    	if (d != null) {
    		this.tempoLavorato = this.tempoLavorato.plus(d);
    	}
    }

    
    /* ************************************************** */
    
    /* GETTER E SETTER */


    public String getNomeAttivita() { return nomeAttivita; }
    public void setNomeAttivita(String nomeAttivita) { this.nomeAttivita = nomeAttivita; }
    
    /* ***** */

    public Date getInizio() { return inizio; }
    public void setInizio(Date inizio) { this.inizio = inizio; }
    
    /* ***** */

    public Date getFine() { return fine; }
    public void setFine(Date fine) { this.fine = fine; }
    
    /* ***** */

	public Duration getTempoLavorato() { return tempoLavorato; }
	public void setTempoLavorato(Duration tempoLavorato) { this.tempoLavorato = tempoLavorato; }
	
	/* ***** */
	
    public String getCfColtivatore() { return cfColtivatore; }
    public void setCfColtivatore(String cfColtivatore) { this.cfColtivatore = cfColtivatore; }
    
    /* ***** */

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
    
    /* ***** */
    
    public int getIdAttivita() { return idAttivita; }
    public void setIdAttivita(int idAttivita) { this.idAttivita = idAttivita; }

    /* ***** */
    
    public int getIdColtura() { return idColtura; }
    public void setIdColtura(int idColtura) { this.idColtura = idColtura; }
    
    /* ***** */

    @Override
    public String toString() {
        return String.format(
            "Attivita[id='%s', nome='%s', inizio=%s, fine=%s, tempoLavorato=%s, cf='%s', stato='%s']",
            idAttivita,
            nomeAttivita,
            inizio,
            fine,
            tempoLavorato,
            cfColtivatore,
            stato
        );
    }
    
    /* ***** */

    private long HowMuchTime(String nomeAtt) {
    		switch (nomeAtt) {
    			case "raccolto":
    				return HowMuchTimeRaccolto(); 
    			case "irrigazione":
    				return 1000 * 60 * 30; // 30 minuti
    			case "semina":
    				return 1000 * 60 * 60; // 1 ora
    			case "applica pesticida":
    				return 1000 * 60 * 45; // 45 minuti
    			default:
    				throw new IllegalArgumentException("Nome attività non valido: " + nomeAtt);
														
    		}
	}
    
    /* ***** */
    
    private long HowMuchTimeRaccolto() {
        long oneDayMs = 24L * 60 * 60 * 1000;
        long min = 2 * oneDayMs; 
        long max = 4 * oneDayMs; 
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

	/* ***** */
    
    private String FormatNomeAttivita(String nomeAttivita) {
		if (nomeAttivita == null || nomeAttivita.isEmpty()) {
			throw new IllegalArgumentException("Nome attività non può essere nullo o vuoto.");
		}
		String newname = nomeAttivita.toLowerCase().trim();
		switch (newname) {
			case "raccolto":
				return "Raccolto";
			case "irrigazione":
				return "Irrigazione";
			case "semina":
				return "Semina";
			case "applicapesticida":
				return "Applica Pesticida";
			default:
				throw new IllegalArgumentException("Nome attività non valido: " + nomeAttivita);
		}

	}
    
    /* ***** */

	private boolean isValidNomeAttivita(String nomeAttivita) {
		String nomeAtt = nomeAttivita.toLowerCase().trim();
		return validNomeAttivita.contains(nomeAtt);
	}
	
    /* ***** */

}

