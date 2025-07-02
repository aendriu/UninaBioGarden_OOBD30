package entità;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class Coltura extends Entita {
	private int idColtura;
	private String nomeColtura;
	private Duration tempoMaturazione;
	private LocalDate giornoSemina;
	private int idLotto;
	
	/* CONSTRUCTORS */
	
	public Coltura(int idColtura, String nomeColtura, int idLotto) {
		super();
		this.idColtura = idColtura;
		this.nomeColtura = nomeColtura;
		this.idLotto = idLotto;
		this.tempoMaturazione = Duration.ZERO;
		this.giornoSemina = LocalDate.now(); 
		tableName = "coltura";
	}
	
	/* ************************* */
	
	public Coltura(int idColtura, String nomeColtura, Duration tempoMaturazione, LocalDate giornoSemina, int idLotto) {
		super();
		this.idColtura = idColtura;
		this.nomeColtura = nomeColtura;
		this.tempoMaturazione = tempoMaturazione;
		this.giornoSemina = giornoSemina;
		this.idLotto = idLotto;
		tableName = "coltura";
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
	public int getIdLotto() {
		return idLotto;
	}
	public void setIdLotto(int idLotto) {
		this.idLotto = idLotto;
	}
	
	
}
