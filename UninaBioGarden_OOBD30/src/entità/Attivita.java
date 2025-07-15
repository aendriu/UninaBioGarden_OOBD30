package entità;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

public class Attivita extends Entita {
    private int idAttivita;
	private String nomeAttivita;
    private java.sql.Date inizio;
    private java.sql.Date fine;  
    private java.sql.Time tempoLavorato;     
    private String cfColtivatore;
    private String stato;


    /* CONSTRUCTORS */
   
    
    public Attivita(String nomeAttivita, Date inizio, Date fine, String cfColtivatore) {
    	this.nomeAttivita = nomeAttivita;
    	this.inizio = inizio;
    	this.fine = fine;
        this.tempoLavorato = Time.valueOf("00:00:00"); 
    	this.cfColtivatore = cfColtivatore;
    	this.stato = "Pianificata";
    	this.tableName = "Attività";
    }
    
    /* *************** */
    
    public Attivita(String nomeAttivita, Date inizio, Date fine, String cfColtivatore, Time tempoLavorato, String stato) {
        this.nomeAttivita = nomeAttivita;
        this.inizio = inizio;
        this.fine = fine;
        this.tempoLavorato = tempoLavorato;
        this.cfColtivatore = cfColtivatore;
        this.stato = stato;
        tableName = "Attività";
    }
    
    /* *************** */
    
  
    public Attivita(int idAttivita, String nomeAttivita, Date inizio, Date fine, String cfColtivatore, Time tempoLavorato, String stato) {
    	this.idAttivita = idAttivita;
        this.nomeAttivita = nomeAttivita;
        this.inizio = inizio;
        this.fine = fine;
        this.tempoLavorato = tempoLavorato;
        this.cfColtivatore = cfColtivatore;
        this.stato = stato;
        tableName = "Attività";
    }

    /* ************************************************** */
    
    public void AddTempoLavorato(Time tl) {
        if (tl != null) {
         
            LocalTime current = this.tempoLavorato.toLocalTime();
            LocalTime added   = tl.toLocalTime();

            LocalTime sum = current
                .plusHours(added.getHour())
                .plusMinutes(added.getMinute())
                .plusSeconds(added.getSecond());

     
            this.tempoLavorato = Time.valueOf(sum);
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

	public Time getTempoLavorato() { return tempoLavorato; }
	public void setTempoLavorato(Time tempoLavorato) { this.tempoLavorato = tempoLavorato; }
	
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
}

