package entità;

public class Utente {
	protected String username;
	protected String nome;
	protected String cognome;
	protected String password;
	protected String CF;
	
	
	public Utente(String username, String nome, String cognome, String password, String CF) {
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.password = password;
		this.CF = CF;
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
}
 