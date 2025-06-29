package entità;
import java.util.ArrayList;


public class Coltivatore extends Utente {
	
	private ArrayList<Lotto> lottiColtivatore;
	private ArrayList<Attivita> attivitaColtivatore;
	
	
	/* CONSTRUCTORS */
	public Coltivatore(String nome, String cognome, String CF, String username, String password) {
		super(nome, cognome, CF, username, password);
	}
	
	public Coltivatore(String nome, String cognome, String CF, String username, String password, ArrayList<Lotto> lottiColtivatore) {
		super(nome, cognome, CF, username, password);
		this.lottiColtivatore = lottiColtivatore;
	}
	
	/* FUNCTIONS */
	
	public void aggiungiLotto(Lotto lotto) {
        lottiColtivatore.add(lotto);
    }
	
	public void aggiungiAttivita(Attivita att) {
		attivitaColtivatore.add(att);
	}
	
	public ArrayList<Lotto> getLotti() {
        return lottiColtivatore;
    }

	
	// MISC FUNCTIONS
	
	@Override
	public String toString() {
		return "Coltivatore [username=" + username + ", nome=" + nome + ", cognome=" + cognome + ", password="
				+ password + ", CF=" + CF + "]";
	}
	
	
}
