package interfacce.Exceptions.Specific_exceptions;

public class Proprietario_addons_selection_exceptions extends Exception {

    public enum Tipo {
       Culture_maximum_number_reached,
        No_one_selected,
        Date_of_completion_precede_starting_date,
    }

    private Tipo tipo;
    private String nomeLotto;

    // Costruttore riceve nome coltura e tipo
    public Proprietario_addons_selection_exceptions( Tipo tipo) {
        this.tipo = tipo;
    }
    
    public Proprietario_addons_selection_exceptions(Tipo tipo, String nomeLotto) {
		this.tipo = tipo;
		this.nomeLotto = nomeLotto;
	}
    @Override
    public String getMessage() {
        switch (tipo) {
            case Culture_maximum_number_reached:
                return "impossiblie procedere con l'inserimento, è stato raggiunto il numero massimo di colture per il lotto '" + nomeLotto + "'.";
            case No_one_selected:
				return "Nessun coltivatore è stato selezionato, impossibile procedere con l'inserimento.";
            case Date_of_completion_precede_starting_date:
				return "La data di completamento non può precedere la data di inizio per l'attività legata alla coltura selezionata.";
            default:
                return "Errore generico con la coltura '" + nomeLotto + "'.";
        }
    }
}

	 


