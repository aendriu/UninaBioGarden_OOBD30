package entità;

import java.time.LocalDate;

public class Coltura extends Entita {
    private int idColtura;
    private String nomeColtura;
    private String tempoMaturazione; 
    private String giornoSemina;
    private int idLotto;

    /* CONSTRUCTORS */

    public Coltura(String nomeColtura, int idLotto) {
        if(!isValidNomeColtura(nomeColtura)) {
			throw new IllegalArgumentException("Nome coltura non valido: " + nomeColtura);
		}
        this.nomeColtura = capitalizeFirstLetter(nomeColtura.toLowerCase().trim());
        this.tempoMaturazione = WhichTempoMaturazione(nomeColtura);
        this.giornoSemina = LocalDate.now().toString();
        this.idLotto = idLotto;
        tableName = "coltura";
    }
    
    /* ***** */

    public Coltura(int idColtura, String nomeColtura, int idLotto) {
    	if(!isValidNomeColtura(nomeColtura)) {
    		throw new IllegalArgumentException("Nome coltura non valido: " + nomeColtura);
    	}
    	this.idColtura = idColtura;
    	this.nomeColtura = capitalizeFirstLetter(nomeColtura.toLowerCase().trim());
    	this.tempoMaturazione = WhichTempoMaturazione(nomeColtura);
    	this.giornoSemina = LocalDate.now().toString();
    	this.idLotto = idLotto;
        tableName = "coltura";
    }
    
    /* ***** */
    
    public Coltura(int idColtura, String nomeColtura, String tempoMaturazione, String giornoSemina, int idLotto) {
        if(!isValidNomeColtura(nomeColtura)) {
            throw new IllegalArgumentException("Nome coltura non valido: " + nomeColtura);
        }
        this.idColtura = idColtura;
        this.nomeColtura = capitalizeFirstLetter(nomeColtura.toLowerCase().trim());
        this.tempoMaturazione = tempoMaturazione;
        this.giornoSemina = giornoSemina;
        this.idLotto = idLotto;
        tableName = "coltura";
    }


    /* GETTER and SETTERS */

    public int getIdColtura() {
        return idColtura;
    }
    
    /* ***** */

    public void setIdColtura(int idColtura) {
        this.idColtura = idColtura;
    }
    
    /* ***** */

    public String getNomeColtura() {
        return nomeColtura;
    }
    
    /* ***** */

    public void setNomeColtura(String nomeColtura) {
        this.nomeColtura = nomeColtura;
    }
    
    /* ***** */

    public String getTempoMaturazione() {
        return tempoMaturazione;
    }
    
    /* ***** */

    public void setTempoMaturazione(String tempoMaturazione) {
        this.tempoMaturazione = tempoMaturazione;
    }
    
    /* ***** */

    public String getGiornoSemina() {
        return giornoSemina;
    }
    
    /* ***** */

    public void setGiornoSemina(String giornoSemina) {
        this.giornoSemina = giornoSemina;
    }
    
    /* ***** */

    public int getIdLotto() {
        return idLotto;
    }
    
    /* ***** */

    public void setIdLotto(int idLotto) {
        this.idLotto = idLotto;
    }

    /* AUXILIARY FUNCTIONS */


    
    /* ***** */
    
	public boolean isValidNomeColtura(String nomeColtura) {
		if (nomeColtura == null || nomeColtura.isEmpty()) {
			return false;
		}
		String nomeColt = nomeColtura.toLowerCase().trim();
		return nomeColt.equals("pomodoro") || nomeColt.equals("mais") || nomeColt.equals("zucchine")
				|| nomeColt.equals("basilico") || nomeColt.equals("fragole");
		}
	
	public String capitalizeFirstLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
