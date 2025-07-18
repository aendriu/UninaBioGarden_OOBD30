package entità;

import java.util.ArrayList;

public class Progetto extends Entita{
	private int idProgetto;
	private int annoProgetto;
	private String nomeProgetto;
	private ProprietarioDiLotto proprietario;
	private Lotto lotto;
	private ArrayList<Coltivatore> coltProgetto;
	private ArrayList<Attivita> attivitaProgetto;
	
	/* CONSTRUCTORS */
	
	public Progetto(int idProgetto) {
	    this.idProgetto = idProgetto;
	}
	
	/* ***** */
	
	public Progetto( String nomeProgetto, int annoProgetto, ProprietarioDiLotto proprietario, Lotto lotto) {
		this.annoProgetto = annoProgetto;
		this.proprietario = proprietario;
		this.nomeProgetto= nomeProgetto;
		this.lotto = lotto;
		this.coltProgetto = new ArrayList<>();
		this.attivitaProgetto = new ArrayList<>();
	}
	
	/* ***** */
	
	public Progetto( String nomeProgetto, int annoProgetto, ProprietarioDiLotto proprietario, Lotto lotto, 
			ArrayList<Coltivatore> coltProgetto, ArrayList<Attivita> attivitaProgetto) {
		this.annoProgetto = annoProgetto;
		this.proprietario = proprietario;
		this.lotto = lotto;
		this.nomeProgetto = nomeProgetto;
		this.coltProgetto = coltProgetto;
		this.attivitaProgetto = attivitaProgetto;

	}
	
	/* ***** */
	
	public Progetto( String nomeProgetto, int idProgetto, int annoProgetto, ProprietarioDiLotto proprietario, Lotto lotto, 
			ArrayList<Coltivatore> coltProgetto, ArrayList<Attivita> attivitaProgetto) {
		this.idProgetto = idProgetto;
		this.annoProgetto = annoProgetto;
		this.proprietario = proprietario;
		this.lotto = lotto;
		this.nomeProgetto = nomeProgetto;
		this.coltProgetto = coltProgetto;
		this.attivitaProgetto = attivitaProgetto;
	}
	
	/* GETTERS and SETTERS */



	public int getIdProgetto() {
		return idProgetto;
	}
	
	/* ***** */

	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}
	
	/* ***** */

	public int getAnnoProgetto() {
		return annoProgetto;
	}
	
	/* ***** */

	public void setAnnoProgetto(int annoProgetto) {
		this.annoProgetto = annoProgetto;
	}
	
	/* ***** */

	public ProprietarioDiLotto getProprietario() {
		return proprietario;
	}
	
	/* ***** */

	public void setProprietario(ProprietarioDiLotto proprietario) {
		this.proprietario = proprietario;
	}
	
	/* ***** */

	public Lotto getLotto() {
		return lotto;
	}
	
	/* ***** */

	public void setLotto(Lotto lotto) {
		this.lotto = lotto;
	}
	
	/* ***** */

	public ArrayList<Coltivatore> getColtProgetto() {
		return coltProgetto;
	}
	
	/* ***** */

	public void setColtProgetto(ArrayList<Coltivatore> coltProgetto) {
		this.coltProgetto = coltProgetto;
	}
	
	/* ***** */

	public ArrayList<Attivita> getAttivitaProgetto() {
		return attivitaProgetto;
	}
	
	/* ***** */

	public void setAttivitaProgetto(ArrayList<Attivita> attivitaProgetto) {
		this.attivitaProgetto = attivitaProgetto;
	}
	
	/* ***** */

	public String getNomeProgetto() {
		return nomeProgetto;
	}
	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}
	@Override
	public String toString() {
		return "Progetto [idProgetto=" + idProgetto + " nome progetto: "+ nomeProgetto +", annoProgetto=" + annoProgetto + ", proprietario="
				+ proprietario + ", lotto=" + lotto + ", coltProgetto=" + coltProgetto + ", attivitaProgetto="
				+ attivitaProgetto + "]";
	}
	
	/* */
	
	
	
	
	
	
	
	
}
