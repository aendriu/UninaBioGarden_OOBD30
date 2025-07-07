package entità;
import java.util.Random;

public class Utente extends Entita{
	protected String username;
	protected String nome;
	protected String cognome;
	protected String password;
	protected String CF;
	
	
	public Utente(String nome, String cognome, String CF, String username, String password) {
		this.nome = nome;
		this.cognome = cognome;
		this.CF = CF;
		this.username = username;
		this.password = password;
	}

	
	
	/* GETTERS AND SETTERS */
		
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getCF() {
		return CF;
	}


	public void setCF(String cF) {
		CF = cF;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
}
 