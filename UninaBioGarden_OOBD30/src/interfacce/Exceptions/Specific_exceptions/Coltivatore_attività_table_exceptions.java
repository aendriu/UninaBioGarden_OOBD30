package interfacce.Exceptions.Specific_exceptions;

import interfacce.Exceptions.Specific_exceptions.Registration_exceptions.Tipo;

public class Coltivatore_attività_table_exceptions extends Exception {
	 public enum Tipo {
	        activity_arleady_completed,
	        illegal_number_input,
	    }

	    private Tipo tipo;

	    public Coltivatore_attività_table_exceptions(Tipo tipo) {
	        this.tipo = tipo;
	    }

	    @Override
	    public String getMessage() {
	        switch (tipo) {
	            case activity_arleady_completed:
	                return "L'attività è già stata completata";
	            case illegal_number_input:
	                return "Il numero inserito non è valido";
	            default:
	                return "Errore non specificato";
	        }
	    }
	}
