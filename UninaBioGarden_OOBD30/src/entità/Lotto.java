package entità;

import java.util.ArrayList;

public class Lotto extends Entita {
	private int idLotto;
	private int numColture;
	private String nomeLotto;
	//FK
	private ProprietarioDiLotto proprietario;
	private int myProgetto;
	private ArrayList<Coltura> myColture;
	private ArrayList<Coltivatore> myColtivatori;
	private ArrayList<Raccolto> myRaccolti;
	
	/* CONSTRUCTORS */
	
	public Lotto(int idLotto, int numColture, String nomeLotto) {
		this.idLotto = idLotto;
		this.numColture = numColture;
		this.nomeLotto = nomeLotto;	
		tableName = "lotto";
	}
	
	public Lotto(int idLotto, int numColture, String nomeLotto, ProprietarioDiLotto proprietario) {
		super();
		this.idLotto = idLotto;
		this.numColture = numColture;
		this.nomeLotto = nomeLotto;
		this.proprietario = proprietario;
		tableName = "lotto";
	}
	
	public Lotto(int idLotto, int numColture, String nomeLotto, ProprietarioDiLotto proprietario, int myProgetto) {
		super();
		this.idLotto = idLotto;
		this.numColture = numColture;
		this.nomeLotto = nomeLotto;
		this.proprietario = proprietario;
		this.myProgetto = myProgetto;
		tableName = "lotto";
	}
	
	public Lotto(int idLotto, int numColture, String nomeLotto, ProprietarioDiLotto proprietario, int myProgetto,
			ArrayList<Coltura> myColture) {
		super();
		this.idLotto = idLotto;
		this.numColture = numColture;
		this.nomeLotto = nomeLotto;
		this.proprietario = proprietario;
		this.myProgetto = myProgetto;
		this.myColture = myColture;
		tableName = "lotto";
	}
	
	public Lotto(int idLotto, int numColture, String nomeLotto, ProprietarioDiLotto proprietario, int myProgetto,
			ArrayList<Coltura> myColture, ArrayList<Coltivatore> myColtivatori, ArrayList<Raccolto> myRaccolti) {
		super();
		this.idLotto = idLotto;
		this.numColture = numColture;
		this.nomeLotto = nomeLotto;
		this.proprietario = proprietario;
		this.myProgetto = myProgetto;
		this.myColture = myColture;
		this.myColtivatori = myColtivatori;
		this.myRaccolti = myRaccolti;
		tableName = "lotto";
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

	public int getMyProgetto() {
		return myProgetto;
	}

	public void setMyProgetto(int myProgetto) {
		this.myProgetto = myProgetto;
	}

	public ArrayList<Coltura> getMyColture() {
		return myColture;
	}

	public void setMyColture(ArrayList<Coltura> myColture) {
		this.myColture = myColture;
	}

	public ArrayList<Coltivatore> getMyColtivatori() {
		return myColtivatori;
	}

	public void setMyColtivatori(ArrayList<Coltivatore> myColtivatori) {
		this.myColtivatori = myColtivatori;
	}
	
	public ArrayList<Raccolto> getMyRaccolti() {
		return myRaccolti;
	}

	public void setMyRaccolti(ArrayList<Raccolto> myRaccolti) {
		this.myRaccolti = myRaccolti;
	}

	
	/* FUNCTIONS */
	
	@Override
	public String toString() {
		return "Lotto [idLotto=" + idLotto + ", numColture=" + numColture + ", nomeLotto=" + nomeLotto
				+ ", proprietario=" + proprietario.getUsername() + ", myProgetto=" + myProgetto + ", myColture=" + myColture
				+ ", myColtivatori=" + myColtivatori + "]";
	}
	
}
