package interfacce.Exceptions.Specific_exceptions;


public class Registration_exceptions extends Exception {
	private static final long serialVersionUID = 1L;
	public enum Tipo{
		No_checkbox_selected,
		Double_checkbox_selected,
		username_already_exists,
		password_already_exists,
	}
	private Tipo tipo;
	public Registration_exceptions(String message, Tipo tipo) {
		super(message);
		this.tipo = tipo;
	}

	public String getMessage() {
		switch (tipo) {
			case No_checkbox_selected:
				return "Nessuna opzione è stata selezionata";
			case Double_checkbox_selected:
				return "Sono state selezionate più di un'opzione, solo una di esse è ammessa";
			case username_already_exists:
				return "Il nome utente " + super.getMessage() + " è già in uso, scegline un altro";
			case password_already_exists:
				return "La password " + super.getMessage() + " è già in uso, scegline un'altra";
			default:
				return super.getMessage();
		}
	}
}
