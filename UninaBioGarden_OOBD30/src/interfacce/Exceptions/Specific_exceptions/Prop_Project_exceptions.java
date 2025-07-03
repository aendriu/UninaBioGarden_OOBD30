package interfacce.Exceptions.Specific_exceptions;

public class Prop_Project_exceptions extends Exception {
    public enum Tipo {
        invalid_activity_state,
        repeated_activity_state,
        removing_non_present_activity,
        adding_same_activity_twice,
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
			case removing_non_present_activity:
				return "non è possibile rimuovere lattività selezionata dato che non è presente nel progetto.";
			case adding_same_activity_twice:
				return "la stessa attività con gli stessi setting è stata aggiunta una seconda volta, pertanto tale inseriento verrà ingnorato.";
			default:
				return "Errore non specificato.";
		}
	}
    public Tipo getTipo() {
		return tipo;
	}

}


