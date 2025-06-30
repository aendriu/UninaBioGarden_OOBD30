package entità;

import java.time.LocalDate;
import java.time.Duration;

public class Attivita {
    private int idAttivita;
    private String nomeAttivita;
    private LocalDate inizio;
    private LocalDate fine;
    private String tempoLavoratoString;  
    private Duration tempoLavorato;     
    private String cfColtivatore;
    private String stato;


    /* CONSTRUCTORS */
    public Attivita(int idAttivita, String nomeAttivita, LocalDate inizio, LocalDate fine,
                    String tempoLavoratoString, String cfColtivatore, String stato) {
        this.idAttivita = idAttivita;
        this.nomeAttivita = nomeAttivita;
        this.inizio = inizio;
        this.fine = fine;
        this.tempoLavoratoString = tempoLavoratoString;
        this.tempoLavorato = parseDurationFromHHMMSS(tempoLavoratoString);
        this.cfColtivatore = cfColtivatore;
        this.stato = stato;
    }
    
    /* INTERVAL and DATE managers */
    private Duration parseDurationFromHHMMSS(String intervalStr) {
        if (intervalStr != null && intervalStr.contains(":")) {
            String[] hms = intervalStr.split(":");
            if (hms.length == 3) {
                try {
                    int ore = Integer.parseInt(hms[0].trim());
                    int min = Integer.parseInt(hms[1].trim());
                    int sec = Integer.parseInt(hms[2].trim());
                    return Duration.ofHours(ore)
                                   .plusMinutes(min)
                                   .plusSeconds(sec);
                } catch (NumberFormatException e) {
                    
                }
            }
        }
        return Duration.ZERO;
    }
    
    /* ************************************************** */
    public void updateTempoLavorato(Duration tl) {
        if (tl != null) {
            this.tempoLavorato = this.tempoLavorato.plus(tl);
            this.tempoLavoratoString = formatDurationToHHMMSS(this.tempoLavorato);
        }
    }
    
    /* ************************************************** */
    
    private String formatDurationToHHMMSS(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String formatted = String.format(
            "%02d:%02d:%02d",
            absSeconds / 3600,
            (absSeconds % 3600) / 60,
            absSeconds % 60);
        return formatted;
    }
    
    /* GETTER E SETTER */

    public int getIdAttivita() { return idAttivita; }
    public void setIdAttivita(int idAttivita) { this.idAttivita = idAttivita; }

    public String getNomeAttivita() { return nomeAttivita; }
    public void setNomeAttivita(String nomeAttivita) { this.nomeAttivita = nomeAttivita; }

    public LocalDate getInizio() { return inizio; }
    public void setInizio(LocalDate inizio) { this.inizio = inizio; }

    public LocalDate getFine() { return fine; }
    public void setFine(LocalDate fine) { this.fine = fine; }

    public Duration getTempoLavorato() { return tempoLavorato; }
    public void setTempoLavorato(Duration tempoLavorato) {
        this.tempoLavorato = tempoLavorato;
        this.tempoLavoratoString = formatDurationToHHMMSS(tempoLavorato);
    }

    public String getTempoLavoratoString() { return tempoLavoratoString; }
    public void setTempoLavoratoString(String tempoLavoratoString) {
        this.tempoLavoratoString = tempoLavoratoString;
        this.tempoLavorato = parseDurationFromHHMMSS(tempoLavoratoString);
    }

    public String getCfColtivatore() { return cfColtivatore; }
    public void setCfColtivatore(String cfColtivatore) { this.cfColtivatore = cfColtivatore; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }



    @Override
    public String toString() {
        return String.format(
            "Attivita{id=%d, nome='%s', inizio=%s, fine=%s, tempoLavorato=%s, cf='%s', stato='%s'}",
            idAttivita,
            nomeAttivita,
            inizio,
            fine,
            tempoLavoratoString,      
            cfColtivatore,
            stato
        );
    }
}
