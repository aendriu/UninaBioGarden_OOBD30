package entità;
import java.util.ArrayList;

public class ProprietarioDiLotto extends Utente {
	
	private ArrayList<Lotto> lottiProprietario;
	
	
	/* CONSTRUCTORS */
	public ProprietarioDiLotto(String username, String nome, String cognome, String password, String CF) {
		super(username, nome, cognome, password, CF);
	}
	
	public void aggiungiLotto(Lotto lotto) {
        lottiProprietario.add(lotto);
    }
	
	public ArrayList<Lotto> getLotti() {
        return lottiProprietario;
    }
}
