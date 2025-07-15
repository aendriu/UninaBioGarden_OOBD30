package entità;

public class Raccolto extends Entita{
	private int idRaccolto;
	private String nomeRaccolto;
	private int quantitaRaccolta;
	private int idLotto;

		
	/* CONSTRUCTORS */
	
	public Raccolto(String nomeRaccolto, int quantitaRaccolta, int idLotto) {
		super();
		this.nomeRaccolto = nomeRaccolto;
		this.quantitaRaccolta = quantitaRaccolta;
		this.idLotto = idLotto;
	}
	
	// *****
	
	public Raccolto(int idRaccolto, String nomeRaccolto, int quantitaRaccolta, int idLotto) {
		super();
		this.idRaccolto = idRaccolto;
		this.nomeRaccolto = nomeRaccolto;
		this.quantitaRaccolta = quantitaRaccolta;
		this.idLotto = idLotto;
	}
	
	/* GETTERS and SETTERS */

	public int getIdRaccolto() {
		return idRaccolto;
	}
	
	/* ***** */

	public void setIdRaccolto(int idRaccolto) {
		this.idRaccolto = idRaccolto;
	}
	
	/* ***** */

	public String getNomeRaccolto() {
		return nomeRaccolto;
	}
	
	/* ***** */

	public void setNomeRaccolto(String nomeRaccolto) {
		this.nomeRaccolto = nomeRaccolto;
	}
	
	/* ***** */

	public int getQuantitaRaccolta() {
		return quantitaRaccolta;
	}
	
	/* ***** */

	public void setQuantitaRaccolta(int quantitaRaccolta) {
		this.quantitaRaccolta = quantitaRaccolta;
	}
	
	/* ***** */

	public int getIdLotto() {
		return idLotto;
	}
	
	/* ***** */

	public void setIdLotto(int idLotto) {
		this.idLotto = idLotto;
	}
	
	/* ***** */

	@Override
	public String toString() {
		return "Raccolto [idRaccolto=" + idRaccolto + ", nomeRaccolto=" + nomeRaccolto + ", quantitaRaccolta="
				+ quantitaRaccolta + ", idLotto=" + idLotto + "]";
	}
	
	
	
}
