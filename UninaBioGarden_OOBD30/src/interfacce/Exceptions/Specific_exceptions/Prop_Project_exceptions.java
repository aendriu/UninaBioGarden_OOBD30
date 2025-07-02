package interfacce.Exceptions.Specific_exceptions;

public class Prop_Project_exceptions extends Exception {
    public enum Tipo {
        invalid_activity_state,
        repeated_activity_state,
    }

    private Tipo tipo; // specifica il tipo dell'attributo

    public Prop_Project_exceptions(Tipo tipo) {
        super("Errore: " + tipo.toString());
        this.tipo = tipo;
    }
    public String getMessage() {
		switch (tipo) {
			case invalid_activity_state:
				return "è stato inserito uno stato non valido per l'attività.";
			case repeated_activity_state:
				return "è stato inserito uno stato uguale al predecedente per l'attività selezionata, pertanto l'aggiornamento verrà ignorato";
			default:
				return "Errore non specificato.";
		}
	}
    public Tipo getTipo() {
		return tipo;
	}

}


