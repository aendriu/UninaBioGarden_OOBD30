package entità;

public abstract class Entita {
	protected String tableName;
	
	
	public Entita () {};
	
	/* ********** */
	
	public String GetTableName() {
		return tableName;
	}
	
	public String WhichTempoMaturazione(String nomeColtura) {
        String nomecolt = nomeColtura.toLowerCase().trim();
        switch (nomecolt) {
            case "pomodoro":
                return "60";
            case "mais":
                return "30";
            case "zucchine":
                return "90";
            case "basilico":
                return "50";
            case "fragole":
                return "70";
            default:
                throw new IllegalArgumentException("Coltura non riconosciuta: " + nomeColtura);
        }
    }
}
