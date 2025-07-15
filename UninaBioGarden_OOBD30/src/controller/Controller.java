package controller;
import java.nio.file.Paths;
import interfacce.*;
import java.util.*;
import javax.swing.*;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.nio.file.Path;
import java.awt.EventQueue;

import dao.*;
import entità.*;

public class Controller {
	String userDir = System.getProperty("user.dir");
	Path dbprop;
	
	// DAOs
	public ColtivatoreDAO coltivatoreDAO;
	public ProprietarioDAO propDAO;
	public AttivitaDAO attivitaDAO;
	public LottoDAO lottoDAO;
	public RaccoltoDAO raccoltoDAO;
	public ColturaDAO coltureDAO;
	public ProgettoDAO progettoDAO;
	public UtenteDAO utenteDAO;
	private Coltivatore_attività_responsabili Colt_attività_frame;
	private Coltivatore_lotti_in_cui_lavora Colt_lotti_frame;
	private Free_coltivatori Colt_free_frame;
	private Free_colture Colture_free_frame;
	private Free_lotti Free_lotti_frame;
	private Home Home_frame;
	private instance_of_lotto_selected Lotto_select_frame;
	private Instance_of_progetto_selected Progetto_select_frame;
	private Login Login_frame;
	private Page_Coltivatore Page_coltivatore_frame;
	private Progetti_creation_scheme Progetti_creation_frame;
	private Progetti_visual_scheme Progetti_visual_frame;
	private Project_finalize_and_final_adjustments Project_finalize_frame;
	private Prop_lotti_visual_scheme Prop_lotti_frame;
	private Prop_organizza_attività Prop_organizza_attività_frame;
	private Proprietario_activities_visual Proprietario_activities_frame;
	private Proprietario_logged_in Proprietario_logged_in_frame;
	private Report_frame Report_frame;
	private User_registration_page User_registration_frame;
	private String CF;
	private String password;
	//TESTING tests;
	
	/* ***** CONSTRUCTOR ***** */
	public Controller() {
		try {
			dbprop = Paths.get(userDir, "libs", "dbprop.txt");
			
			coltivatoreDAO = new ColtivatoreDAO(dbprop.toString(), this);
			propDAO = new ProprietarioDAO(dbprop.toString(), this);
			attivitaDAO = new AttivitaDAO(dbprop.toString(), this);
			lottoDAO = new LottoDAO(dbprop.toString(), this);
			raccoltoDAO = new RaccoltoDAO(dbprop.toString(), this);
			coltureDAO = new ColturaDAO(dbprop.toString(), this);
			progettoDAO = new ProgettoDAO(dbprop.toString(), this);
			utenteDAO = new UtenteDAO(dbprop.toString(), this);
			
			coltivatoreDAO.connect();
            propDAO.connect();
            attivitaDAO.connect();
            lottoDAO.connect();
            utenteDAO.connect(); 
			
			
			// TO COMMENT
            //utenteDAO.ChangePassword("andluise04", "gggg");
			//tests = new TESTING(dbprop.toString(), this);
			//tests.InitTests();
			//


			
			
		} catch (Exception e){
			e.printStackTrace();
		}
		 Home_frame = new Home(this);
        Home_frame.setVisible(true);
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
		Coltivatore colt = coltivatoreDAO.FindSpecificColtivatore(CF);
		return colt;
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
    	 

    	 public String[] getData1(String username_Prop) {
    		    String[] possibiliNomi = {
    		        "Campo San Marco", "Vigna Vecchia", "Orto di Giulia", "Terreno Verde",
    		        "Campo Grande", "Podere Aurora", "Prato Fiorito", "Orto del Sole",
    		        "Valle Serena", "Podere delle Rose", "Orto Primavera", "Campo Alto",
    		        "Prato dei Fiori", "Giardino Segreto", "Campo dei Miracoli",
    		        "Vigna del Borgo", "Campo Serena", "Poderetto", "Orto Felice", "Vigneto Nuovo"
    		    };
    		    Random rand = new Random();
    		    int n = rand.nextInt(20) + 1; // da 1 a 20 nomi
    		    String[] dati = new String[n];
    		    for (int i = 0; i < n; i++) {
    		        dati[i] = possibiliNomi[rand.nextInt(possibiliNomi.length)];
    		    }
    		    return dati;
    		}


    	 public String[] getData2(String username_Prop) {
    	     // Attività: attività agricole random (esempi)
    	     String[] activities = {
    	         "Irrigazione", "Semina", "Concimazione", "Raccolta", "Potatura",
    	         "Diserbo", "Trapianto", "Sarchiatura", "Controllo parassiti",
    	         "Aratura", "Fertilizzazione", "Monitoraggio", "Impianto", "Pacciamatura",
    	         "Rincalzatura", "Sfoltimento", "Pulizia campo", "Concimazione fogliare",
    	         "Irrigazione localizzata", "Scerbatura"
    	     };
    	     Random rand = new Random();
    	     int n = rand.nextInt(20) + 1;
    	     String[] dati = new String[n];
    	     for (int i = 0; i < n; i++) {
    	         dati[i] = activities[i];
    	     }
    	     return dati;
    	 }

    	 public String[] getData3(String username_Prop) {
    	     // Coltura: nomi di colture tipiche
    	     String[] cultures = {
    	         "Pomodoro", "Mais", "Grano", "Orzo", "Frumento",
    	         "Patata", "Lattuga", "Zucchina", "Carota", "Peperone",
    	         "Melanzana", "Fagiolo", "Pisello", "Cipolla", "Cavolo",
    	         "Fragola", "Aglio", "Cetriolo", "Spinacio", "Ravanello"
    	     };
    	     Random rand = new Random();
    	     int n = rand.nextInt(20) + 1;
    	     String[] dati = new String[n];
    	     for (int i = 0; i < n; i++) {
    	         dati[i] = cultures[i];
    	     }
    	     return dati;
    	 }

    	 public String[] getData4(String username_Prop) {
    	     // Nome_colt: nomi di persona comuni
    	     String[] names = {
    	         "Marco", "Luca", "Giulia", "Francesca", "Matteo",
    	         "Sara", "Andrea", "Alessandro", "Chiara", "Davide",
    	         "Federica", "Stefano", "Elena", "Simone", "Valentina",
    	         "Riccardo", "Laura", "Paolo", "Marta", "Giorgio"
    	     };
    	     Random rand = new Random();
    	     int n = rand.nextInt(20) + 1;
    	     String[] dati = new String[n];
    	     for (int i = 0; i < n; i++) {
    	         dati[i] = names[i];
    	     }
    	     return dati;
    	 }

    	 public String[] getData5(String username_Prop) {
    	     // Cognome: cognomi italiani comuni
    	     String[] surnames = {
    	         "Rossi", "Russo", "Ferrari", "Esposito", "Bianchi",
    	         "Romano", "Colombo", "Ricci", "Marino", "Greco",
    	         "Bruno", "Gallo", "Conti", "De Luca", "Mancini",
    	         "Costa", "Giordano", "Rizzo", "Lombardi", "Moretti"
    	     };
    	     Random rand = new Random();
    	     int n = rand.nextInt(20) + 1;
    	     String[] dati = new String[n];
    	     for (int i = 0; i < n; i++) {
    	         dati[i] = surnames[i];
    	     }
    	     return dati;
    	 }
    	 
    	 public Object[] getNomiProgettiELotti(String username) {
    		    String[] nomiProgettiPossibili = {
    		        "Progetto Orto Urbano", "Vigna del Sole", "Frutteto Sostenibile",
    		        "Serra Innovativa", "Campo di Grano Antico", "Orto delle Erbe Aromatiche",
    		        "Giardino dei Fiori Rari", "Progetto Biodiversità", "Orto Verticale",
    		        "Frutteto Familiare"
    		    };

    		    String[] nomiLottiPossibili = {
    		        "Orto Centrale", "Campo Est", "Serra Moderna", "Giardino Nord",
    		        "Vigneto Sud", "Frutteto Ovest", "Pista Agraria", "Bosco Urbano",
    		        "Prato Fiorito", "Collina Blu"
    		    };

    		    Random rnd = new Random();
    		    int n = rnd.nextInt(20) + 1; // da 1 a 20 elementi

    		    String[] nomiProgetti = new String[n];
    		    String[] nomiLotti = new String[n];

    		    for (int i = 0; i < n; i++) {
    		        nomiProgetti[i] = nomiProgettiPossibili[rnd.nextInt(nomiProgettiPossibili.length)];
    		        nomiLotti[i] = nomiLottiPossibili[rnd.nextInt(nomiLottiPossibili.length)];
    		    }

    		    return new Object[] { nomiProgetti, nomiLotti };
    		}

    	 public List<Object[]> Riempi_tab_progetti_vista_proprietario(String username_colt) {
 		    List<Object[]> lista = new ArrayList<>();
 		    Random rnd = new Random();

 		    String[] nomiAttività = {
 		        "Irrigazione", "Potatura", "Raccolta", "Semina",
 		        "Concimazione", "Controllo Parassiti", "Monitoraggio Clima"
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
 		            nomeColtivatore,     
 		            cognomeColtivatore,  
 		            nomeAttivita,        
 		            coltura,             
 		            stato,
 		            percentuale,
 		        };

 		        lista.add(riga);
 		    }

 		    return lista;
 		}
    	 
    	  public List<Map<String, Object>> getDatiRaccoltaPerLotto(String username, String nomeLotto) {
    		    List<Map<String, Object>> raccolte = new ArrayList<>();
    		    Random rnd = new Random();

    		    String[] colturePossibili = {
    		        "Pomodoro", "Lattuga", "Carota", "Zucchina",
    		        "Peperone", "Melanzana", "Cetriolo", "Spinaci"
    		        , "Riso", "Mais", "Grano", "Orzo", "Fagiolo", "Pisello", "Soia", "Avena"
    		        , "Cipolla", "Aglio", "Porro", "Barbabietola", "Ravanello", "Sedano", "Cavolo", "Broccoli"
    		    };

    		    for (String coltura : colturePossibili) {
    		        Map<String, Object> info = new HashMap<>();
    		        info.put("coltura", coltura);
    		        info.put("media",  10 + rnd.nextDouble() * 80);
    		        info.put("min",    5  + rnd.nextDouble() * 40);
    		        info.put("max",    30 + rnd.nextDouble() * 90);
    		        info.put("numeroRaccolte", 1 + rnd.nextInt(15));  // da 1 a 15 raccolte
    		        raccolte.add(info);
    		    

    		    }

    		    return raccolte;
    		}
    	   
    	  //DAO INTERACTIONS
    	  public String Convert_UsernameToCF(String username) {
    		  try {
				CF=utenteDAO.ConvertUsernameToCF(username);
				return CF;
    		  } catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
    		  
    		   
    	   }
    	   
    	  public String Get_password(String username) {
			 		try {
			 			password = utenteDAO.GetPasswordOfThatUsername(username);
			 		
			 			return password;
			 		} catch (SQLException e) {
			 			e.printStackTrace();
			 			return null; // Valore di errore
			 		}
		  }
    	  
    	  public int Find_where_to_acces (String username) {
			  try {
				  int decisor = utenteDAO.WhereIsThatUsernameInto(username);
				  return decisor;
			  } catch (SQLException e) {
				  e.printStackTrace();
				  return -99; // Valore di errore
			  }
		  }
    	  
    	  public int Check_if_username_exists(String username) {
    		  try {
    		  if (utenteDAO.DoesUsernameExist(username)) {
				  return 1;
			  } else {
				  return 0;
			  }
    	  }catch (SQLException e) {
			  e.printStackTrace();
			  return -99; // Valore di errore
			  		  }
		  }
    	  
    	  public int Check_if_password_exists(String password) {
    		  try {
    		  if (utenteDAO.DoesPasswordExist(password)) {
				  return 1;
			  } else {
				  return 0;
			  }
    	  }catch (SQLException e) {
			  e.printStackTrace();
			  return -99; // Valore di errore
			  		  }
		  }
    	  public int Check_if_CF_exists(String CF) {
    		  try {
    		  if (utenteDAO.DoesCFExist(CF)) {
				  return 1;
			  } else {
				  return 0;
			  }
    	  }catch (SQLException e) {
			  e.printStackTrace();
			  return -99; // Valore di errore
			  		  }
		  }
    	   
    	  public void inserisci_utente(Utente utente) {
				   utenteDAO.InsertUser(utente);
		  }
    	  public String get_Info_From_Username(String username, int decisor) {
    		    try {
    		        String CF = utenteDAO.ConvertUsernameToCF(username);

    		        if (decisor == 0) {
    		            Coltivatore c = coltivatoreDAO.FindSpecificColtivatore(CF);
    		            return c.getNome() + " " + c.getCognome();
    		        } else if (decisor == 1) {
    		            ProprietarioDiLotto p = propDAO.FindSpecificProprietario(CF);
    		            return p.getNome() + " " + p.getCognome();
    		        } else {
    		            return null; 
    		        }

    		    } catch (SQLException e) {
    		        e.printStackTrace(); // utile per debug
    		        return null;
    		    }
    		}
    	  
    	  public List<Object[]> Riempi_tab_lotti_in_cui_lavora(String username){
    		  
    		  List<Object[]> Righe = new ArrayList<>();
    		  try {
    			  String CF = utenteDAO.ConvertUsernameToCF(username);
    			  ArrayList<Lotto> lotti = coltivatoreDAO.GetLottiColtivatore(CF);
    			  if (lotti.isEmpty()) {
    				  return Righe; 
    			  }
    			  for(Lotto lotto_to_get : lotti) {
    				  String nome = lotto_to_get.getNomeLotto();
    		            int numColture = lotto_to_get.getNumColture();
    		            Righe.add(new Object[]{nome, numColture});
    		            
    			  }
    		  }
    		  catch (SQLException e) {
				  e.printStackTrace();
				  return Righe;
			  }
			  return Righe;
    	  }

    	  //Metodi per la gestione delle finestre aprtura e chiusura
    	// Metodo generico che apre un frame e chiude il caller
    	  public void openFrameAndCloseCaller(JFrame newFrame, JFrame caller) {
    	      newFrame.setVisible(true);
    	          caller.dispose();
    	      
    	  }
    	  
    	  
    	  public List<Object[]> Riempi_tab_attività_responsabili(String username) {
    		    List<Object[]> righe = new ArrayList<>();
    		    try {
    		        String CF = utenteDAO.ConvertUsernameToCF(username);
    		        ArrayList<Lotto> lotti = coltivatoreDAO.GetLottiColtivatore(CF);
    		        if (lotti.isEmpty()) return righe;

    		        Set<Integer> attivitaViste = new HashSet<>(); // Moved fuori dal ciclo lotti per evitare duplicati

    		        for (Lotto lotto : lotti) {
    		            String nomeLotto = lotto.getNomeLotto();
    		            int lottoID = lotto.getIdLotto();

    		            List<Coltura> colturePerLotto = coltureDAO.GetColtureOfLotto(lottoID);

    		            for (Coltura colturaObj : colturePerLotto) {
    		                String nomeColtura = colturaObj.getNomeColtura();

    		                // Ottieni attività filtrate per lotto, coltivatore e coltura
    		                List<Attivita> attivitaFiltrate = coltivatoreDAO.getAttivitaPerColtivatoreELotto(lottoID, CF, nomeColtura);

    		                for (Attivita att : attivitaFiltrate) {
    		                    if (!attivitaViste.add(att.getIdAttivita())) {
    		                        // Salta attività già viste per evitare duplicati
    		                        continue;
    		                    }

    		                    int idAttività= att.getIdAttivita();
    		                    String nomeAttività = att.getNomeAttivita();
    		                    String stato = att.getStato();
    		                    Date dataInizio = att.getInizio();
    		                    Date dataFine = att.getFine();
    		                    Time tempo = att.getTempoLavorato();

    		                    long durataTotaleMillis = dataFine.getTime() - dataInizio.getTime();

    		                    // Calcolo millisecondi tempo lavorato
    		                    LocalTime localTime = tempo.toLocalTime();
    		                    int ore = localTime.getHour();
    		                    int minuti = localTime.getMinute();
    		                    int secondi = localTime.getSecond();
    		                    long tempoLavoratoEffettivoMillis = (ore * 3600 + minuti * 60 + secondi) * 1000;

    		                    int percentuale = (durataTotaleMillis <= 0) ? 0 :
    		                        (int) Math.min(100, (tempoLavoratoEffettivoMillis * 100.0) / durataTotaleMillis);

    		                    righe.add(new Object[] {nomeLotto, nomeAttività, nomeColtura, stato, percentuale});
    		                }
    		            }
    		        }
    		    } catch (SQLException e) {
    		        e.printStackTrace();
    		    }
    		    return righe;
    		}


    	  public boolean aggiorna_tempo_lavorato(String username, String lottoname, String nomeattività, Time tempoLavorato) {
			 try {
				 int lotto_id=0;
				 String CF = utenteDAO.ConvertUsernameToCF(username);
				 List<Lotto> lotti = coltivatoreDAO.GetLottiColtivatore(CF);
				 for (Lotto l : lotti) {
					 if (l.getNomeLotto().equals(lottoname)) {
						 lotto_id = l.getIdLotto();
						 break; // Esci dal ciclo una volta trovato il lotto
						 }
					 List<Attivita> attività = attivitaDAO.GetAttivitaOfLotto(lotto_id, CF);
					 for (Attivita a : attività) {
						 if (a.getNomeAttivita().equals(nomeattività)) {
							attivitaDAO.UpdateTempoLavoratoAttivita(a.getIdAttivita(), tempoLavorato);
							return true; // Aggiornamento riuscito
						 }
				 }
				 }
				
				 
			 }
			 catch (SQLException e) {
				 return false; // Aggiornamento fallito
			 }
			return false; // Aggiornamento fallito 
    	  }
    	  
    	  public String[] getNomiLotti(String username) {
    		  List<Lotto> Lotti = new ArrayList<>();
    		  List<String> nomiLotti = new ArrayList<>();
    		  String CF = Convert_UsernameToCF(username);
    		  try {
    		  Lotti=propDAO.GetLottiProprietario(CF);
    		  for (Lotto lotto: Lotti) {
    			  nomiLotti.add(lotto.getNomeLotto());
			  }
			  return nomiLotti.toArray(new String[0]); // Converte la lista in un array di stringhe
    		  } catch (SQLException e) {
				 return new String[0]; // Ritorna un array vuoto in caso di errore
    		  }
    	  }
    	  
    	  public List<Object[]> Riempi_tab_Proprietario_nome_coltivatore(String username_proprietario, String lottoName) {
    		    List<Object[]> righe = new ArrayList<>();
    		    try {
    		        // Ottieni tutti i lotti del proprietario
    		        List<Lotto> lotti = propDAO.GetLottiProprietario(Convert_UsernameToCF(username_proprietario));
    		        if (lotti.isEmpty()) return righe; // Se non ci sono lotti, ritorna lista vuota
    		        
    		        for (Lotto lotto : lotti) {
    		            if (lotto.getNomeLotto().equals(lottoName)) {
    		                // Ottieni coltivatori del lotto corrispondente
    		                List<Coltivatore> coltivatori = lottoDAO.GetColtivatoriOfLotto(lotto.getIdLotto());
    		                for (Coltivatore coltivatore : coltivatori) {
    		                    String nomeColtivatore = coltivatore.getNome();
    		                    String cognomeColtivatore = coltivatore.getCognome();
    		                    righe.add(new Object[]{nomeColtivatore, cognomeColtivatore});
    		                }
    		                break; // Trovato il lotto, esci dal ciclo perché nome lotto è unico
    		            }
    		        }
    		    } catch (SQLException e) {
    		       return new ArrayList<>(); // In caso di errore, ritorna lista vuota
    		    }
    		    return righe;
    		}

    	  
    	  public List<Object[]> Riempi_tab_Proprietario_nome_coltura(String username, String lottoName) {
    		    Set<Object[]> righeSet = new LinkedHashSet<>(); // Set per evitare duplicati mantenendo ordine
    		    try {
    		        List<Lotto> lotti = propDAO.GetLottiProprietario(Convert_UsernameToCF(username));
    		        if (lotti.isEmpty()) return new ArrayList<>(); // Lista vuota se niente lotti
    		        
    		        for (Lotto lotto : lotti) {
    		            if (lotto.getNomeLotto().equals(lottoName)) {
    		                List<Coltura> colture = coltureDAO.GetColtureOfLotto(lotto.getIdLotto());
    		                for (Coltura coltura : colture) {
    		                    String nomeColtura = coltura.getNomeColtura();
    		                    righeSet.add(new Object[]{nomeColtura});
    		                }
    		                break; // trovato lotto, esco dal ciclo
    		            }
    		        }
    		    } catch (SQLException e) {
    		        righeSet.clear(); // In caso di errore, pulisco il set
    		        
    		    }
    		    // Converto Set in List e la ritorno
    		    return new ArrayList<>(righeSet);
    		}

    	  public List<Object[]> Riempi_tab_Proprietario_nome_coltivatore_free(String username, String lottoName) {
    		    List<Object[]> righe = new ArrayList<>();
    		    try {
    		        List<Coltivatore> coltivatoriLiberi = coltivatoreDAO.GetFreeColtivatori();

    		        for (Coltivatore coltivatore : coltivatoriLiberi) {
    		            String nome = coltivatore.getNome();
    		            String cognome = coltivatore.getCognome();
    		            String CF = coltivatore.getCF();
    		            righe.add(new Object[]{nome, cognome, CF});
    		        }

    		    } catch (SQLException e) {
    		        return new ArrayList<>(); // In caso di errore, ritorna lista vuota
    		    }
    		    return righe;
    		}

    	  public boolean Aggiungi_coltivatore(String username, String Lottoname, String CF) {
    		    try {
    		        Coltivatore colt_to_add = coltivatoreDAO.FindSpecificColtivatore(CF);
    		        int id_lotto_to_insert = 0;
    		        List<Lotto> lotti = propDAO.GetLottiProprietario(Convert_UsernameToCF(username));
    		        if (lotti.isEmpty()) return false; // Lista vuota se niente lotti
    		        for (Lotto lotto : lotti) {
    		            if (lotto.getNomeLotto().equals(Lottoname)) {
    		                id_lotto_to_insert = lotto.getIdLotto();
    		                break; // Trovato il lotto, esci dal ciclo perché nome lotto è unico
    		            }
    		        }
    		        if (id_lotto_to_insert == 0) return false; // Nessun lotto trovato con quel nome
    		        lottoDAO.AddColtivatoreToLotto(colt_to_add, id_lotto_to_insert);
    		    } catch (SQLException e) {
    		        return false; // In caso di errore, ritorna false
    		    }
    		    return true;
    		}

    		  
    		  
    		  
    		  
    	  // Metodi specifici che creano il frame desiderato e usano il metodo generico

    	// LOGIN
    	  public void OpenLogin_closeCaller(JFrame caller) {
    	      Login loginFrame = new Login(this);
    	      openFrameAndCloseCaller(loginFrame, caller);
    	  }

    	  // HOME
    	  public void OpenHome_closeCaller(JFrame caller) {
    	      Home homeFrame = new Home(this);
    	      openFrameAndCloseCaller(homeFrame, caller);
    	  }

    	  // REGISTRAZIONE UTENTE
    	  public void OpenUserRegistration_closeCaller(JFrame caller) {
    	      User_registration_page regFrame = new User_registration_page(this);
    	      openFrameAndCloseCaller(regFrame, caller);
    	  }

    	  // PAGINA COLTIVATORE
    	  public void OpenPageColtivatore_closeCaller(String credenziali, JFrame caller) {
    	      Page_Coltivatore coltFrame = new Page_Coltivatore(credenziali, this);
    	      openFrameAndCloseCaller(coltFrame, caller);
    	  }

    	  // PAGINA PROPRIETARIO
    	  public void OpenProprietarioLoggedIn_closeCaller(String credenziali, JFrame caller) {
    	      Proprietario_logged_in propFrame = new Proprietario_logged_in(credenziali, this);
    	      openFrameAndCloseCaller(propFrame, caller);
    	  }

    	  // COLTIVATORE ATTIVITÀ RESPONSABILI
    	  public void OpenColtivatoreAttivitaResponsabili_closeCaller(String cf, JFrame caller) {
    	      Coltivatore_attività_responsabili attivitaFrame = new Coltivatore_attività_responsabili(cf, this);
    	      openFrameAndCloseCaller(attivitaFrame, caller);
    	  }

    	  // COLTIVATORE LOTTI IN CUI LAVORA
    	  public void OpenColtivatoreLottiInCuiLavora_closeCaller(String cf, JFrame caller) {
    	      Coltivatore_lotti_in_cui_lavora lottiFrame = new Coltivatore_lotti_in_cui_lavora(cf, this);
    	      openFrameAndCloseCaller(lottiFrame, caller);
    	  }

    	  // PROPRIETARIO LOTTI VISUAL SCHEME
    	  public void OpenPropLottiVisualScheme_closeCaller(String cf, int decisor, JFrame caller) {
    	      Prop_lotti_frame = new Prop_lotti_visual_scheme(cf, this, decisor);
    	      openFrameAndCloseCaller(Prop_lotti_frame, caller);
    	  }

    	  // PROPRIETARIO ATTIVITÀ VISUAL
    	  public void OpenPropAttivitàVisualScheme_closeCaller(String cf, JFrame caller) {
    	      Proprietario_activities_frame = new Proprietario_activities_visual(cf, this);
    	      openFrameAndCloseCaller(Proprietario_activities_frame, caller);
    	  }

    	  // PROPRIETARIO ORGANIZZA ATTIVITÀ
    	  public void OpenPropAttivitàOrganizza_closeCaller(String cf, JFrame caller) {
    	      Prop_organizza_attività_frame = new Prop_organizza_attività(cf, this);
    	      openFrameAndCloseCaller(Prop_organizza_attività_frame, caller);
    	  }

    	  // PROPRIETARIO PROGETTI VISUAL SCHEME
    	  public void OpenPropProgettiVisualScheme_closeCaller(String cf, JFrame caller) {
    	      Progetti_visual_frame = new Progetti_visual_scheme(cf, this);
    	      openFrameAndCloseCaller(Progetti_visual_frame, caller);
    	  }

    	  // FREE LOTTI (PROPRIETARIO)
    	  public void OpenFreeLotti_closeCaller(String CF, JFrame caller) {
    	      Free_lotti_frame = new Free_lotti(CF, this);
    	      openFrameAndCloseCaller(Free_lotti_frame, caller);
    	  }

    	  // ISTANZA DI LOTTO SELEZIONATO
    	  public void OpenIstanceOfLottoSelected_closeCaller(String CF, JFrame caller, String nomeLotto) {
    	      Lotto_select_frame = new instance_of_lotto_selected(CF, this, nomeLotto);
    	      openFrameAndCloseCaller(Lotto_select_frame, caller);
    	  }

    	  // FREE COLTIVATORI
    	  public void OpenFreeColtivatori_closeCaller(String CF, String lottoname, JFrame caller) {
    	      Colt_free_frame = new Free_coltivatori(CF, this, lottoname);
    	      openFrameAndCloseCaller(Colt_free_frame, caller);
    	  }

    	  // FREE COLTURE
    	  public void OpenFreeColture_closeCaller(String CF, String lottoname, JFrame caller) {
    	      Colture_free_frame = new Free_colture(CF, this, lottoname);
    	      openFrameAndCloseCaller(Colture_free_frame, caller);
    	  }

    	  // PROGETTI CREATION SCHEME
    	  public void OpenProgettoCreationScheme_closeCaller(String CF, JFrame caller, String lottoname) {
    	      Progetti_creation_frame = new Progetti_creation_scheme(CF, this, lottoname);
    	      openFrameAndCloseCaller(Progetti_creation_frame, caller);
    	  }

    	  // ISTANZA DI PROGETTO SELEZIONATO
    	  public void OpenInstanceOfProgettoSelected_closeCaller(String CF, JFrame caller, String nomeProgetto) {
    	      Progetto_select_frame = new Instance_of_progetto_selected(CF, this, nomeProgetto);
    	      openFrameAndCloseCaller(Progetto_select_frame, caller);
    	  }

    	  // PROGETTO FINALIZE AND FINAL ADJUSTMENTS
    	  public void OpenProjectFinalizeAndFinalAdjustments_closeCaller(String CF, String lottoname, String nomeProgetto, Set<List<String>> Project_layout, JFrame caller) {
    	      Project_finalize_frame = new Project_finalize_and_final_adjustments(CF, this, lottoname, nomeProgetto, Project_layout);
    	      openFrameAndCloseCaller(Project_finalize_frame, caller);
    	  }

    	  // REPORT FRAME
    	  public void OpenReportFrame_closeCaller(String CF, JFrame caller) {
    	      Report_frame = new Report_frame(CF, this);
    	      openFrameAndCloseCaller(Report_frame, caller);
    	  }
}