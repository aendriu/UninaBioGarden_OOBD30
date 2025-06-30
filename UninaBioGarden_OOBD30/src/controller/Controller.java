package controller;
import java.nio.file.Paths;
import interfacce.*;
import java.util.*;
import javax.swing.*;
import java.sql.SQLException;
import java.nio.file.Path;
import java.awt.EventQueue;

import dao.*;
import entità.*;
import interfacce.Home;

public class Controller {
	String userDir = System.getProperty("user.dir");
	Path dbprop;
	
	// DAOs
	ColtivatoreDAO coltDAO;
	ProprietarioDAO propDAO;
	LottoDAO lottoDAO;
	TESTING tests;
	
	/* ***** CONSTRUCTOR ***** */
	public Controller() {
		try {
			dbprop = Paths.get(userDir, "libs", "dbprop.txt");
			coltDAO = new ColtivatoreDAO(dbprop.toString(), this);
			propDAO = new ProprietarioDAO(dbprop.toString(), this);
            coltDAO.connect();
            propDAO.connect();
			
			
			// TO COMMENT
			tests = new TESTING(dbprop.toString(), this);
			tests.InitTests();
			//


			
			
		} catch (Exception e){
			e.printStackTrace();
		}
		Home home = new Home(this);
        home.setVisible(true);
	}
	
	/* ***** MAIN ***** */
	public static void main(String[] args) throws SQLException {
        // Creo l’istanza del controller che avvia tutto nel thread Swing
        SwingUtilities.invokeLater(() -> {
            new Controller();
        });
       
    }
	/* *************** ColtivatoreDAO test *************** */

	public Coltivatore getColtivatoreByCF(String CF) throws SQLException {
		Coltivatore colt = coltDAO.FindSpecificColtivatore(CF);
		return colt;
	}
	
	
	public List<Object[]> Riempi_tab_lotti_in_cui_lavora(String username_colt) {
        List<Object[]> lista = new ArrayList<>();
        Random rnd = new Random();

        String[] nomiLotti = {
            "Orto Centrale", "Campo Est", "Serra Moderna", "Giardino Nord",
            "Vigneto Sud", "Frutteto Ovest", "Pista Agraria", "Bosco Urbano",
            "Prato Fiorito", "Collina Blu"
        };

        for (String nome : nomiLotti) {
            int numeroColture = 1 + rnd.nextInt(10); // 1..10
            int attivitaInCorso = rnd.nextInt(4);    // 0..3
            Object[] riga = { nome, numeroColture, attivitaInCorso };
            lista.add(riga);
        }

        return lista;
    }
    public List<Object[]> Riempi_tab_attività_responsabili(String username_colt) {
        List<Object[]> lista = new ArrayList<>();
        Random rnd = new Random();

        String[] nomiAttività = {
            "Irrigazione", "Potatura", "Raccolta", "Semina",
            "Concimazione", "Controllo Parassiti", "Monitoraggio Clima", "Applica Pesticida"
        };

        String[] nomiLotti = {
            "Orto Centrale", "Campo Est", "Serra Moderna", "Giardino Nord",
            "Vigneto Sud", "Frutteto Ovest", "Prato Fiorito", "Collina Blu"
        };
        String[] colture = {
			"Pomodoro", "Lattuga", "Carota", "Zucchina",
			"Peperone", "Melanzana", "Cetriolo", "Spinaci"
		};
        for (int i = 0; i < 20; i++) {  // genero 20 righe di esempio
            String nomeAttivita = nomiAttività[rnd.nextInt(nomiAttività.length)];
            String nomeLotto = nomiLotti[rnd.nextInt(nomiLotti.length)];
            String coltura = colture[rnd.nextInt(colture.length)];
            String stato = "In corso";
            int percentuale = rnd.nextInt(101); // 0..100
         

            Object[] riga = { 
                nomeLotto, 
                nomeAttivita, 
                coltura, 
                stato, 
                percentuale, 
                
            };

            lista.add(riga);
        }

        return lista;
    }
    public String[] getNomiLottiPlaceholder(String username_colt) {
        List<Object[]> lista = Riempi_tab_lotti_in_cui_lavora(username_colt);
        int size = Math.min(lista.size(), 10);
        String[] nomi = new String[size];
        for (int i = 0; i < size; i++) {
            nomi[i] = (String) lista.get(i)[0]; // prendo solo il nome del lotto
        }
        return nomi;
    }
    public List<Object[]> Riempi_tab_Proprietario_nome_coltura(String username_colt, String Lottoname) { //METTERE PURE LOTTO NAME QUANDO HO LE DAO
        List<Object[]> lista = new ArrayList<>();
        Random rnd = new Random();
        String[] colture = {
			"Pomodoro", "Lattuga", "Carota", "Zucchina",
			"Peperone", "Melanzana", "Cetriolo", "Spinaci"
		};
        for (int i = 0; i < 20; i++) {  // genero 20 righe di esempio
            String coltura = colture[rnd.nextInt(colture.length)];
            Object[] riga = { 
                coltura, 
            };
            lista.add(riga);
        }
        return lista;
    }
    	public List<Object[]> Riempi_tab_Proprietario_nome_coltivatore(String username_colt,String Lottoname ) { //METTERE PURE LOTTO NAME QUANDO HO LE DAO
		List<Object[]> lista = new ArrayList<>();
		Random rnd = new Random();
		String[] nomiColtivatori = {
			"Mario ", "Luca ", "Giulia ", "Anna ",
			"Marco ", "Sara ", "Francesco ", "Elena "
		};
		String[] cognomiColtivatori = {
			"Rossi", "Bianchi", "Verdi", "Neri",
			"Gialli", "Blu", "Arancio", "Viola"
		};
		for (int i = 0; i < 20; i++) {  // genero 20 righe di esempio
			String nome = nomiColtivatori[rnd.nextInt(nomiColtivatori.length)];
			String cognome = cognomiColtivatori[rnd.nextInt(cognomiColtivatori.length)];
			Object[] riga = { 
				nome, 
				cognome
			};
			lista.add(riga);
		}
		return lista;
}
    	 public List<Object[]> Riempi_tab_Proprietario_free_colture(String username_colt, String Lottoname) { //METTERE PURE LOTTO NAME QUANDO HO LE DAO
    	        List<Object[]> lista = new ArrayList<>();
    	        Random rnd = new Random();
    	        String[] colture = {
    				"Pomodoro", "Lattuga", "Carota", "Zucchina",
    				"Peperone", "Melanzana", "Cetriolo", "Spinaci",
    				 "Riso", "Mais", "Grano", "Orzo", " Fagiolo", "Pisello", "Soia", "Avena"
    				 , "Cipolla", "Aglio", "Porro", "Barbabietola", "Ravanello", "Sedano", "Cavolo", "Broccoli"
    			};
    	        for (int i = 0; i < 50; i++) {  // genero 50 righe di esempio
    	            String coltura = colture[rnd.nextInt(colture.length)];
    	            Object[] riga = { 
    	                coltura, 
    	            };
    	            lista.add(riga);
    	        }
    	        return lista;
    	    }
    	 
    	 public List<Object[]> Riempi_tab_Proprietario_nome_coltivatore_free(String username_colt,String Lottoname ) { //METTERE PURE LOTTO NAME QUANDO HO LE DAO
    			List<Object[]> lista = new ArrayList<>();
    			Random rnd = new Random();
    			String[] nomiColtivatori = {
    				"Mario ", "Luca ", "Giulia ", "Anna ",
    				"Marco ", "Sara ", "Francesco ", "Elena "
    				, "Alessandro", "Chiara", "Giovanni", "Martina", "Stefano", "Laura"
    				, "Davide", "Federica", "Simone", "Valentina", "Giorgio", "Claudia"
    				, "Roberto", "Silvia", "Antonio", "Francesca"
    			};
    			String[] cognomiColtivatori = {
    				"Rossi", "Bianchi", "Verdi", "Neri",
    				"Gialli", "Blu", "Arancio", "Viola", "Grigi", "Marroni", "Rossi", "Neri"
    				, "Bianchi", "Verdi", "Gialli", "Blu", "Arancio", "Viola"
    			};
    			for (int i = 0; i < 40; i++) {  // genero 20 righe di esempio
    				String nome = nomiColtivatori[rnd.nextInt(nomiColtivatori.length)];
    				String cognome = cognomiColtivatori[rnd.nextInt(cognomiColtivatori.length)];
    				Object[] riga = { 
    					nome, 
    					cognome
    				};
    				lista.add(riga);
    			}
    			return lista;
    	}
    	 public List<Object[]> Riempi_tab_lotti_free(String username_colt) {
    	        List<Object[]> lista = new ArrayList<>();
    	        Random rnd = new Random();

    	        String[] nomiLotti = {
    	            "Orto Centrale", "Campo Est", "Serra Moderna", "Giardino Nord",
    	            "Vigneto Sud", "Frutteto Ovest", "Pista Agraria", "Bosco Urbano",
    	            "Prato Fiorito", "Collina Blu", "Campo di Grano", "Orto delle Erbe", "Serra Tropicale"
    	            , "Giardino delle Rose", "Frutteto dei Sogni", "Campo di Mais", "Orto delle Radici"
    	            , "Serra delle Spezie", "Giardino dei Fiori", "Frutteto delle Meraviglie"
    	            , "Campo di Ortaggi", "Orto delle Bontà", "Serra dei Sogni", "Giardino delle Delizie"
    	            , "Frutteto delle Stelle", "Campo di Legumi", "Orto delle Aromatiche"
    	        };

    	        for (int i = 0; i < 50; i++) {
    	            // Prendi un nome casuale dalla lista esistente (con ripetizioni)
    	            String nome = nomiLotti[rnd.nextInt(nomiLotti.length)];
    	            int numeroColture = 1 + rnd.nextInt(10); // da 1 a 10
    	            int attivitaInCorso = rnd.nextInt(4);    // da 0 a 3
    	            Object[] riga = { nome, numeroColture, attivitaInCorso };
    	            lista.add(riga);
    	        }

    	        return lista;
    	    }
    	 public List<Object[]> Riempi_tab_attività_vista_proprietario(String username_colt) {
    		    List<Object[]> lista = new ArrayList<>();
    		    Random rnd = new Random();

    		    String[] nomiAttività = {
    		        "Irrigazione", "Potatura", "Raccolta", "Semina",
    		        "Concimazione", "Controllo Parassiti", "Monitoraggio Clima"
    		    };

    		    String[] nomiLotti = {
    		        "Orto Centrale", "Campo Est", "Serra Moderna", "Giardino Nord",
    		        "Vigneto Sud", "Frutteto Ovest", "Prato Fiorito", "Collina Blu"
    		    };

    		    String[] colture = {
    		        "Pomodoro", "Lattuga", "Carota", "Zucchina",
    		        "Peperone", "Melanzana", "Cetriolo", "Spinaci"
    		    };

    		    String[] statiPossibili = {  // non usato più, ma puoi tenere
    		        "In corso", "Completata", "Pianificata"
    		    };

    		    String[] nomiColtivatori = {
    		        "Mario", "Luca", "Giulia", "Anna",
    		        "Marco", "Sara", "Francesco", "Elena",
    		        "Alessandro", "Chiara", "Giovanni", "Martina",
    		        "Stefano", "Laura", "Davide", "Federica",
    		        "Simone", "Valentina", "Giorgio", "Claudia",
    		        "Roberto", "Silvia", "Antonio", "Francesca"
    		    };

    		    String[] cognomiColtivatori = {
    		        "Rossi", "Bianchi", "Verdi", "Neri",
    		        "Gialli", "Blu", "Arancio", "Viola",
    		        "Grigi", "Marroni", "Rossi", "Neri",
    		        "Bianchi", "Verdi", "Gialli", "Blu",
    		        "Arancio", "Viola"
    		    };

    		    for (int i = 0; i < 30; i++) {
    		        String nomeAttivita = nomiAttività[rnd.nextInt(nomiAttività.length)];
    		        String nomeLotto = nomiLotti[rnd.nextInt(nomiLotti.length)];
    		        String coltura = colture[rnd.nextInt(colture.length)];
    		        String nomeColtivatore = nomiColtivatori[rnd.nextInt(nomiColtivatori.length)];
    		        String cognomeColtivatore = cognomiColtivatori[rnd.nextInt(cognomiColtivatori.length)];

    		        String stato = "";
    		        int percentuale = rnd.nextInt(101); // 0..100
    		        if (percentuale == 0) {
    		            stato = "Pianificata";
    		        } else if (percentuale < 100) {
    		            stato = "In corso";
    		        } else {
    		            stato = "Completata";
    		        }

    		        // Modifico qui l’ordine
    		        Object[] riga = {
    		            nomeLotto,           
    		            nomeColtivatore,     
    		            cognomeColtivatore,  
    		            nomeAttivita,        
    		            coltura,             
    		            stato,               
    		            percentuale          
    		        };

    		        lista.add(riga);
    		    }

    		    return lista;
    		}


}


		
	

