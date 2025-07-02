package entità;

import java.time.LocalDate;
import java.time.Duration;

public class Attivita extends Entita{
    private String nomeAttivita;
    private LocalDate inizio;
    private LocalDate fine;  
    private Duration tempoLavorato;     
    private String cfColtivatore;
    private String stato;


    /* CONSTRUCTORS */
    
    public Attivita(String nomeAttivita, LocalDate inizio, LocalDate fine, String cfColtivatore) {
    	this.nomeAttivita = nomeAttivita;
    	this.inizio = inizio;
    	this.fine = fine;
    	this.tempoLavorato = Duration.ZERO; 
    	this.cfColtivatore = cfColtivatore;
    	this.stato = "Pianificata";
    	this.tableName = "Attività";
    }
    
    /* *************** */
    
    public Attivita(String nomeAttivita, LocalDate inizio, LocalDate fine, String cfColtivatore, Duration tempoLavorato, String stato) {
        this.nomeAttivita = nomeAttivita;
        this.inizio = inizio;
        this.fine = fine;
        this.tempoLavorato = tempoLavorato;
        this.cfColtivatore = cfColtivatore;
        this.stato = stato;
        tableName = "Attività";
    }
   
    
    /* ************************************************** */
    public void updateTempoLavorato(Duration tl) {
        if (tl != null) {
            this.tempoLavorato = this.tempoLavorato.plus(tl);
        }
    }
    
    /* ************************************************** */
    
    /* GETTER E SETTER */


    public String getNomeAttivita() { return nomeAttivita; }
    public void setNomeAttivita(String nomeAttivita) { this.nomeAttivita = nomeAttivita; }

    public LocalDate getInizio() { return inizio; }
    public void setInizio(LocalDate inizio) { this.inizio = inizio; }

    public LocalDate getFine() { return fine; }
    public void setFine(LocalDate fine) { this.fine = fine; }

	public Duration getTempoLavorato() { return tempoLavorato; }
    public String getCfColtivatore() { return cfColtivatore; }
    public void setCfColtivatore(String cfColtivatore) { this.cfColtivatore = cfColtivatore; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }



    @Override
    public String toString() {
        return String.format(
            "Attivita{id=%d, nome='%s', inizio=%s, fine=%s, tempoLavorato=%s, cf='%s', stato='%s'}",
            nomeAttivita,
            inizio,
            fine,
            Duration.ofDays(tempoLavorato.toHours()),  
            cfColtivatore,
            stato
        );
    }
}
