package entità;

public class Lotto {
	private int idLotto;
	private int numColture;
	private String nomeLotto;
	private ProprietarioDiLotto proprietario;
	// private int idProgetto;
	
	public Lotto(int idLotto, int numColture, String nomeLotto) {
		this.idLotto = idLotto;
		this.numColture = numColture;
		this.nomeLotto = nomeLotto;	
	}

	/* GETTERS AND SETTERS */
	
	public int getIdLotto() {
		return idLotto;
	}

	public void setIdLotto(int idLotto) {
		this.idLotto = idLotto;
	}

	public int getNumColture() {
		return numColture;
	}

	public void setNumColture(int numColture) {
		this.numColture = numColture;
	}

	public String getNomeLotto() {
		return nomeLotto;
	}

	public void setNomeLotto(String nomeLotto) {
		this.nomeLotto = nomeLotto;
	}

	public ProprietarioDiLotto getProprietario() {
		return proprietario;
	}

	public void setProprietario(ProprietarioDiLotto proprietario) {
		this.proprietario = proprietario;
	}
	
	/* FUNCTIONS */

	
	
}
