package entità;
import java.util.ArrayList;


public class Coltivatore extends Utente{
	
	private ArrayList<Lotto> lottiColtivatore;
	private ArrayList<Attivita> attivitaColtivatore;
	
	
	/* CONSTRUCTORS */
	public Coltivatore(String nome, String cognome, String CF, String username, String password) {
		super(nome, cognome, CF, username, password);
		tableName = "coltivatore";
	}
	
	public Coltivatore(String nome, String cognome, String CF, String username, String password, ArrayList<Lotto> lottiColtivatore
			,ArrayList<Attivita> attivitaColtivatore) {
		super(nome, cognome, CF, username, password);
		this.lottiColtivatore = lottiColtivatore;
		this.attivitaColtivatore = attivitaColtivatore;
		tableName = "coltivatore";
	}
	
	
	
	
	/* FUNCTIONS */
	
	public void aggiungiLotto(Lotto lotto) {
        lottiColtivatore.add(lotto);
    }
	
	public void rimuoviLotto(Lotto lotto) {
		lottiColtivatore.remove(lotto);
	}
	
	/* ***** */
	
	public void aggiungiAttivita(Attivita att) {
		attivitaColtivatore.add(att);
	}
	
	public void rimuoviAttivita(Attivita att) {
		attivitaColtivatore.remove(att);
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
