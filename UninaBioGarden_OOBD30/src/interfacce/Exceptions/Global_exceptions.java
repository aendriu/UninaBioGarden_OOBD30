package interfacce.Exceptions;

public class Global_exceptions extends Exception {

    private static final long serialVersionUID = 1L;

    public enum Tipo{
        format_mismatch,
        Type_mismatch,
        not_found_in_DB,
        already_exists_in_DB,
        empty_field,
        correct_username_but_wrong_password,
        DB_fault,
    }
    private Tipo tipo;

    public Global_exceptions(String message, Tipo tipo) {
        super(message);
        this.tipo = tipo;
    }

    public Global_exceptions(String message) {
        super(message);
        this.tipo =null;
    }
    
    
    @Override
    public String getMessage() {
        if (tipo == null) {
			return super.getMessage();
		}
    	switch (tipo) {
            case format_mismatch:
                return "L'input nella casella " + super.getMessage() + " non segue il formato richiesto";
            case Type_mismatch:
                return "Nella casella " + super.getMessage() + " è stato inserito un tipo di dato non valido";
            case not_found_in_DB:
                return super.getMessage() + " non risulta registrato nella piattaforma";
            case already_exists_in_DB:
                return super.getMessage() + " è già registrato nella piattaforma, impossibile procedere";
            case empty_field:
                return "Il campo " + super.getMessage() + " è vuoto";
            case correct_username_but_wrong_password:
				return "La password inserita per l'username " + super.getMessage() + " è errata";
            case DB_fault:
				return "Si è verificato un errore durante l'accesso al database: " + super.getMessage();
            default:
                return super.getMessage();
        }
    }

	public Tipo getTipo() {
		return tipo;
		}
}