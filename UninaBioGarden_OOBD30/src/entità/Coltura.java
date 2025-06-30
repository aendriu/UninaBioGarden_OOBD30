package entità;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class Coltura {
	private int idColtura;
	private String nomeColtura;
	private Duration tempoMaturazione;
	private LocalDate giornoSemina;
	
	/* CONSTRUCTORS */
	public Coltura(int idColtura, String nomeColtura, Duration tempoMaturazione, LocalDate giornoSemina) {
		super();
		this.idColtura = idColtura;
		this.nomeColtura = nomeColtura;
	}
	
	
	/* GETTER and SETTERS */
	public int getIdColtura() {
		return idColtura;
	}
	
	public void setIdColtura(int idColtura) {
		this.idColtura = idColtura;
	}
	public String getNomeColtura() {
		return nomeColtura;
	}
	public void setNomeColtura(String nomeColtura) {
		this.nomeColtura = nomeColtura;
	}
	public Duration getTempoMaturazione() {
		return tempoMaturazione;
	}
	public void setTempoMaturazione(Duration tempoMaturazione) {
		this.tempoMaturazione = tempoMaturazione;
	}
	public LocalDate getGiornoSemina() {
		return giornoSemina;
	}
	public void setGiornoSemina(LocalDate giornoSemina) {
		this.giornoSemina = giornoSemina;
	}
	
	
}
