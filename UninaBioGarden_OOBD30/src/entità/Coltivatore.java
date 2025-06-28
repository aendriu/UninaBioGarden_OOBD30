package entità;
import java.util.ArrayList;


public class Coltivatore extends Utente {
	
	private ArrayList<Lotto> lottiColtivatore;
	private ArrayList<Attivita> attivitaColtivatore;
	
	
	/* CONSTRUCTORS */
	public Coltivatore(String username, String nome, String cognome, String password, String CF) {
		super(username, nome, cognome, password, CF);
	}
	
	public Coltivatore(String username, String nome, String cognome, String password, String CF, ArrayList<Lotto> lottiColtivatore) {
		super(username, nome, cognome, password, CF);
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
