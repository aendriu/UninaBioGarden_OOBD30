package interfacce.Exceptions.Specific_exceptions;

public class Prop_Project_exceptions extends Exception {
    public enum Tipo {
        invalid_activity_state,
        repeated_activity_state,
        project_arleady_exixts_4_that_lotto,
        adding_same_activity_twice,
        no_new_arguments_of_project_added,
        project_was_wiped_out_by_user,
        no_row_selected_4_deletion,
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
			case adding_same_activity_twice:
				return "la stessa attività con gli stessi setting è stata aggiunta una seconda volta, pertanto tale inseriento verrà ingnorato.";
			case project_arleady_exixts_4_that_lotto:
				return "il lotto selezionato ha già un progetto non completato associato, pertanto non è possibile crearne uno nuovo.";
			case no_new_arguments_of_project_added:
				return "il progetto è privo di attività pertanto è impossibile finalizzare, aggiungi almeno una attività per continuare.";
			case project_was_wiped_out_by_user:
				return "il progetto è stato svuotato dall utente, pertanto non è possibile finalizzare, si verrà reinderizzati alla pagina precedente per crearne uno nuovo.";
			case no_row_selected_4_deletion:
				return "non è stata selezionata nessuna riga per la cancellazione, si prega di selezionarne una se si vuole usare tale funzionalità.";
			default:
				return "Errore non specificato.";
		}
	}
    public Tipo getTipo() {
		return tipo;
	}

}


