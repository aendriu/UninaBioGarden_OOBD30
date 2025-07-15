package entità;
import java.util.ArrayList;

public class ProprietarioDiLotto extends Utente {
	
	private ArrayList<Lotto> lottiProprietario;
	
	
	/* CONSTRUCTORS */
	
	public ProprietarioDiLotto(String CF) {
	    super(CF);
	}
	
	public ProprietarioDiLotto(String nome, String cognome, String CF, String username, String password) {
		super(nome, cognome, CF, username, password);
		tableName = "proprietariodilotto";
	}
	
	public ProprietarioDiLotto(String nome, String cognome, String CF, String username, String password, ArrayList<Lotto> lottiProprietario) {
		super(nome, cognome, CF, username, password);
		this.lottiProprietario = lottiProprietario;
		tableName = "proprietariodilotto";
	}
	
	/* ***** */
	
	public void aggiungiLotto(Lotto lotto) {
        lottiProprietario.add(lotto);
    }
	
	/* ***** */
	
	public void rimuoviLotto(Lotto lotto) {
		lottiProprietario.remove(lotto);
	}
	
	/* ***** */
	
	public ArrayList<Lotto> getLotti() {
        return lottiProprietario;
    }
	
	/* ***** */
	
	@Override
	public String toString() {
		return "ProprietarioDiLotto [lottiProprietario=" + lottiProprietario + ", username=" + username + ", nome="
				+ nome + ", cognome=" + cognome + ", password=" + password + ", CF=" + CF + "]";
	}
	
	
}
	

