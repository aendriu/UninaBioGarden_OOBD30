package controller;
import java.nio.file.Paths;
import interfacce.*;
import java.util.*;
import javax.swing.*;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Path;
import java.awt.EventQueue;
import java.sql.Date;
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
			TESTING tests = new TESTING(dbprop.toString(), this);
			tests.InitTests();
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
    		                List<Attivita> attivitaFiltrate = coltivatoreDAO.GetAttivitaOfColtivatoreOnColtura(CF, colturaObj.getIdColtura());

    		                for (Attivita att : attivitaFiltrate) {
    		                    if (!attivitaViste.add(att.getIdAttivita())) {
    		                        // Salta attività già viste per evitare duplicati
    		                        continue;
    		                    }

    		                    
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
    		        // Puoi loggare e/o gestire l'eccezione qui se vuoi
    		        // Ritorna comunque la lista vuota in caso di errore
    		    }
    		    return righe;
    		}

    	  
    	  public List<Object[]> Riempi_tab_Proprietario_nome_coltura(String username, String lottoName) {
    		    Set<Integer> idColtureSet = new HashSet<>();
    		    List<Object[]> righe = new ArrayList<>();

    		    try {
    		        List<Lotto> lotti = propDAO.GetLottiProprietario(Convert_UsernameToCF(username));
    		        if (lotti.isEmpty()) return righe;

    		        for (Lotto lotto : lotti) {
    		            if (lotto.getNomeLotto().equals(lottoName)) {
    		                List<Coltura> colture = coltureDAO.GetColtureOfLotto(lotto.getIdLotto());
    		                for (Coltura coltura : colture) {
    		                    int idcoltura = coltura.getIdColtura();
    		                    if (!idColtureSet.contains(idcoltura)) {
    		                        idColtureSet.add(idcoltura);
    		                        righe.add(new Object[]{coltura.getNomeColtura(), idcoltura});
    		                    }
    		                }
    		                break;
    		            }
    		        }
    		    } catch (SQLException e) {
    		        righe.clear();
    		    }

    		    return righe;
    		}

    	  public List<Object[]> Riempi_tab_Proprietario_nome_coltivatore_free(String username, String lottoName) {
    		    List<Object[]> righe = new ArrayList<>();
    		    try {
    		        List<Coltivatore> coltivatoriLiberi = coltivatoreDAO.GetFreeColtivatori();

    		        for (Coltivatore coltivatore : coltivatoriLiberi) {
    		            String nome = coltivatore.getNome();
    		            String cognome = coltivatore.getCognome();
    		            String cf = coltivatore.getCF();
    		            righe.add(new Object[]{nome, cognome, CF});
    		        }

    		    } catch (SQLException e) {
    		        return new ArrayList<>(); // In caso di errore, ritorna lista vuota
    		    }
    		    return righe;
    		}

    	  public List<Object[]> Riempi_tab_attività_vista_proprietario(String username) {
    		    List<Object[]> righe = new ArrayList<>();
    		    try {
    		        String CFproprietario = Convert_UsernameToCF(username);
    		        List<Lotto> lotti_proprietario = propDAO.GetLottiProprietario(CFproprietario);
    		        if (lotti_proprietario.isEmpty()) return righe;

    		        Set<Integer> attivitaViste = new HashSet<>();

    		        for (Lotto lotto : lotti_proprietario) {
    		            int idlotto = lotto.getIdLotto();
    		            String nomelotto = lotto.getNomeLotto();

    		            List<Coltivatore> coltivatori_in_lotto = lottoDAO.GetColtivatoriOfLotto(idlotto);
    		            for (Coltivatore coltivatore : coltivatori_in_lotto) {
    		                String nomecoltivatore = coltivatore.getNome();
    		                String cognomecoltivatore = coltivatore.getCognome();
    		                String CFcoltivatore = coltivatore.getCF();

    		                List<Coltura> colture = coltureDAO.GetColtureOfLotto(idlotto);

    		                for (Coltura coltura : colture) {
    		                    String nomecoltura = coltura.getNomeColtura();
    		                    int idcoltura = coltura.getIdColtura();

    		                    // FIX chiamata corretta con cf_coltivatore e id_coltura
    		                    List<Attivita> attività = coltivatoreDAO.GetAttivitaOfColtivatoreOnColtura(CFcoltivatore, idcoltura);

    		                    for (Attivita att : attività) {
    		                        if (!attivitaViste.add(att.getIdAttivita())) continue; // evita duplicati

    		                        String nomeattività = att.getNomeAttivita();
    		                        
    		                        String stato = att.getStato();
    		                        Date dataInizio = att.getInizio();
    		                        Date dataFine = att.getFine();
    		                        Time tempo = att.getTempoLavorato();

    		                        long durataTotaleMillis = dataFine.getTime() - dataInizio.getTime();
    		                        LocalTime localTime = tempo.toLocalTime();
    		                        long tempoLavoratoMillis = (localTime.getHour() * 3600 + localTime.getMinute() * 60 + localTime.getSecond()) * 1000;

    		                        int percentuale = (durataTotaleMillis <= 0) ? 0 :
    		                                (int) Math.min(100, (tempoLavoratoMillis * 100.0) / durataTotaleMillis);

    		                        righe.add(new Object[] {
    		                            nomelotto,
    		                            nomecoltivatore,
    		                            cognomecoltivatore,
    		                            nomeattività,
    		                            nomecoltura,
    		                            stato,
    		                            percentuale
    		                        });
    		                    }
    		                }
    		            }
    		        }
    		    } catch (SQLException e) {
    		        e.printStackTrace();
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

    	  public List<Object[]> Riempi_tab_lotti_free() {
    		    List<Object[]> righe = new ArrayList<>();
    		    List<Lotto> lotti_free = new ArrayList<>();
    		    try {
    		        lotti_free = lottoDAO.GetLottiSenzaProprietario();
    		        for (Lotto lotto : lotti_free) {
    		            String nome = lotto.getNomeLotto();
    		            righe.add(new Object[]{nome});
    		        }
    		    } catch (SQLException e) {
    		        e.printStackTrace();
    		        righe.clear(); // In caso di errore, ritorna lista vuota
    		    }
    		    return righe;
    		}

    	  public int AddColturaToLotto(String username, String lottoname, String coltname) {
    		    List<Lotto> lotti = new ArrayList<>();
    		    try {
    		        lotti = propDAO.GetLottiProprietario(Convert_UsernameToCF(username));
    		        for (Lotto lotto : lotti) {
    		            if (lotto.getNomeLotto().equals(lottoname)) {
    		                int id_lotto = lotto.getIdLotto();
    		                coltureDAO.InsertColtura(coltname, id_lotto);
    		                return 1; // Successo
    		            }
    		        }
    		    } catch (SQLException e) {
    		        return -99; // Errore durante l'inserimento
    		    }
    		    return -99; // Nessun lotto corrispondente trovato
    		}

    	  public int GetNumCultureOfLotto(String username, String lottoname) {
			    try {
			        String CF = Convert_UsernameToCF(username);
			        List<Lotto> lotti = propDAO.GetLottiProprietario(CF);
			        for (Lotto lotto : lotti) {
			            if (lotto.getNomeLotto().equals(lottoname)) {
			                return lotto.getNumColture();
			            }
			        }
			    } catch (SQLException e) {
			        return -99; // Log dell'errore
			    }
			    return 0; // Se non trovato o errore, ritorna 0
    		  }
    	  
    	  public int AggiungiProprietarioALotto(String username, String lottoname) {
    		  try {
    		  int validat =lottoDAO.AggiungiProprietarioALotto(lottoname, Convert_UsernameToCF(username));
    	  
    		  if (validat == 1) {
				  return 1; // Successo
			  } else {
				  return -99; // Errore durante l'inserimento
			  }
			  } catch (SQLException e) {
				  return -99; // Errore durante l'inserimento
			  }
    	  }
    	  
    	  public int RemoveColturaFromLotto(int id) {
    		  try {
    		  coltureDAO.RemoveColtura(id);
    		  return 1; // Successo
			  } catch (SQLException e) {
				  e.printStackTrace();
				  return -99; // Errore durante l'inserimento
			  }
    	  }
    	  public String[] getLottiPerProprietario(String username) {
    		    List<String> righe = new ArrayList<>();
    		    try {
    		        String CF = Convert_UsernameToCF(username);
    		        List<Lotto> lotti = propDAO.GetLottiProprietario(CF);
    		        if (lotti.isEmpty()) return new String[0]; // Ritorna array vuoto se non ci sono lotti
    		        for (Lotto lotto : lotti) {
    		            String nome = lotto.getNomeLotto();
    		            righe.add(nome);
    		        }
    		    } catch (SQLException e) {
    		        righe.clear(); // In caso di errore, ritorna lista vuota
    		    }
    		    return righe.toArray(new String[0]);  // converte la lista in array di stringhe
    		}

    	  public String[][] getColtivatoriConCF(String lottoname, String username) {
    		    List<String[]> righe = new ArrayList<>();
    		    try {
    		        List<Lotto> lotti = propDAO.GetLottiProprietario(Convert_UsernameToCF(username));
    		        if (lotti.isEmpty()) return new String[0][0]; // array vuoto 2D
    		        for (Lotto lotto : lotti) {
    		            if (lotto.getNomeLotto().equals(lottoname)) {
    		                List<Coltivatore> coltivatori = lottoDAO.GetColtivatoriOfLotto(lotto.getIdLotto());
    		                for (Coltivatore coltivatore : coltivatori) {
    		                    String cf = coltivatore.getCF();
    		                    String nomeCognome = coltivatore.getNome() + " " + coltivatore.getCognome();
    		                    righe.add(new String[] { cf, nomeCognome });
    		                }
    		                break; // trovato lotto, esco
    		            }
    		        }
    		    } catch (SQLException e) {
    		        righe.clear();
    		    }
    		    return righe.toArray(new String[0][0]);
    		}

    	  
    		public String[][] getColture(String lottoname, String username) {
    		    List<String[]> righe = new ArrayList<>();
    		    try {
    		        String CF = Convert_UsernameToCF(username);
    		        List<Lotto> lotti = propDAO.GetLottiProprietario(CF);
    		        if (lotti.isEmpty()) return new String[0][0]; 

    		        for (Lotto lotto : lotti) {
    		            if (lotto.getNomeLotto().equals(lottoname)) {
    		                List<Coltura> colture = coltureDAO.GetColtureOfLotto(lotto.getIdLotto());
    		                for (Coltura coltura : colture) {
    		                    String nomeColtura = coltura.getNomeColtura();
    		                    int idColtura = coltura.getIdColtura();
    		                    righe.add(new String[]{nomeColtura, String.valueOf(idColtura)});
    		                }
    		                break;
    		            }
    		        }
    		    } catch (Exception e) {
    		        e.printStackTrace(); // Per debug
    		        righe.clear(); 
    		    }
    		    return righe.toArray(new String[0][0]); 
    		}
    		
    		public int Organizza_Attività(String username, String[] dati_selected, int raccolto_forse) {
    			int validat = 0;
    			try {
    				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    				String coltivatoreCF = dati_selected[1];
    				String nomeAttività = dati_selected[2];
    				int coltura = Integer.parseInt(dati_selected[4]);
    				LocalDate Inizio = LocalDate.parse(dati_selected[5], formatter);
    				LocalDate Fine = LocalDate.parse(dati_selected[6], formatter);
    				Date dataInizio = Date.valueOf(Inizio);
    				Date dataFine = Date.valueOf(Fine);
    				Time tempoLavorato = Time.valueOf("00:00:00");
    				String stato = "Pianificata";
    				if (raccolto_forse != 0) {
						Coltura colturaObj = coltureDAO.FindSpecificColtura(coltura);
						String nomeColtura = colturaObj.getNomeColtura();
    					 validat=InserisciInRaccolto(nomeColtura, raccolto_forse, Integer.parseInt(dati_selected[0]));
					}
    				if (validat == -99) {
						return -99; // Errore durante l'inserimento del raccolto
					}
    				attivitaDAO.InsertAttivita(nomeAttività, dataInizio, dataFine, coltivatoreCF, coltura, tempoLavorato, stato);
    				return 1; // Successo}
    			}
    			catch (SQLException e) {
					e.printStackTrace();
					return -99; 
				}
    		}
    		
    		public Object[] getNomiProgettiELotti(String username) {
    		    List<String> nomiProgetti = new ArrayList<>();
    		    List<String> nomiLotti = new ArrayList<>();
    		    String CF = Convert_UsernameToCF(username);
    		    
    		    try {
    		        List<Lotto> lotti_candidati = progettoDAO.GetLottiWithProgetto();
    		        for (Lotto l : lotti_candidati) {
    		            if (l.getProprietario().getCF().equals(CF)) {  // usa .equals per confrontare stringhe!
    		                nomiLotti.add(l.getNomeLotto());
    		                nomiProgetti.add(l.getMyProgetto().getNomeProgetto());
    		            }
    		        }
    		    } catch (SQLException e) {
    		        e.printStackTrace();
    		        return new Object[]{new ArrayList<>(), new ArrayList<>()};  // liste vuote in caso di errore
    		    }

    		    return new Object[]{nomiProgetti, nomiLotti};
    		}

    		public List<Object[]> Riempi_tab_progetti_vista_proprietario(String username_prop,String nome_lotto, int decisor){
    			List<Object[]> righe = new ArrayList<>();
			    try {
			        String CF = Convert_UsernameToCF(username_prop);
			        List<Lotto> lotti = propDAO.GetLottiProprietario(CF);
			        if (lotti.isEmpty()) return righe; // Se non ci sono lotti, ritorna lista vuota
			        for (Lotto lotto : lotti) {
			            if (lotto.getNomeLotto().equals(nome_lotto)) {
			                Progetto progetto = lotto.getMyProgetto(); 
			                int idprogetto= progetto.getIdProgetto();
			                    List<Attivita> attivita = progettoDAO.GetAttivitaProgetto(idprogetto);
			                    for (Attivita att : attivita) {
			                           String CF_coltivatore_responsabile = att.getCfColtivatore();
			                           Coltivatore coltivatore = coltivatoreDAO.FindSpecificColtivatore(CF_coltivatore_responsabile);
			                           String nomeColtivatore = coltivatore.getNome();
			                           String cognomeColtivatore = coltivatore.getCognome();
			                           int idColtura = att.getIdColtura();
			                           Coltura coltura = coltureDAO.FindSpecificColtura(idColtura);
			                           String nomeColtura = coltura.getNomeColtura();
			                    		String nomeAttività = att.getNomeAttivita();
			                           String stato = att.getStato();
			                           Date dataInizio = att.getInizio();
			                           Date dataFine = att.getFine();
			                           Time tempo = att.getTempoLavorato();
			                           
			                           long durataTotaleMillis = dataFine.getTime() - dataInizio.getTime();

			                           LocalTime localTime = tempo.toLocalTime();
			                           int ore = localTime.getHour();
			                           int minuti = localTime.getMinute();
			                           int secondi = localTime.getSecond();
			                           long tempoLavoratoEffettivoMillis = (ore * 3600 + minuti * 60 + secondi) * 1000;

			                           int percentuale = (durataTotaleMillis <= 0) ? 0 :
			                               (int) Math.min(100, (tempoLavoratoEffettivoMillis * 100.0) / durataTotaleMillis);
			                           if (decisor ==1) {
			                        	  int idattività= att.getIdAttivita();
			                        	  righe.add(new Object[] { nomeColtivatore, cognomeColtivatore, nomeAttività, nomeColtura , stato, percentuale, idattività,}); 
			                           }
			                          righe.add(new Object[] {nomeColtivatore, cognomeColtivatore, nomeAttività, nomeColtura , stato, percentuale}); 
			                    }
			                    }
			            break;            
			        }
			                        // Trovato il lotto, esci dal ciclo perché nome lotto è unico
			            
			        
			    } catch (SQLException e) {
			        return new ArrayList<>(); // In caso di errore, ritorna lista vuota
			    }
			    return righe;
    		}
    	  
    		public int InserisciInRaccolto (String nomeColt, int amount, int idLotto) {
			    try {
			        raccoltoDAO.InsertRaccolto(nomeColt, amount, idLotto);
			        return 1; // Successo
			    } catch (SQLException e) {
			        e.printStackTrace();
			        return -99; // Errore durante l'inserimento
			    }
			}
    		
    		public boolean AggiornaAttività (int idattività, String new_state) {
    			try {
					attivitaDAO.UpdateStatoAttivita(idattività, new_state);
					return true; // Aggiornamento riuscito
				} catch (SQLException e) {
					return false; // Aggiornamento fallito
    		}
    		}
    		
    		public int FinalizeAndInsertProgetto (String username, String lottoname, String nomeprogetto, Set<List<String>> projectLayout) {
    			String CF_prop = Convert_UsernameToCF(username);
    			int anno = LocalDate.now().getYear();
    			int idProgetto = 0;
    			List<Integer> AttivitàIds = new ArrayList<>();
    			List<String> CF_coltivatori = new ArrayList<>();
    			try {
					int id_lotto = 0;
					List<Lotto> lotti = propDAO.GetLottiProprietario(CF);
					for (Lotto lotto : lotti) {
						if (lotto.getNomeLotto().equals(lottoname)) {
							id_lotto = lotto.getIdLotto();
							break; // Trovato il lotto, esci dal ciclo perché nome lotto è unico
						}
					}
					if (id_lotto == 0)
						return -99; // Nessun lotto trovato con quel nome
					idProgetto=progettoDAO.InsertProgetto(anno, CF_prop, id_lotto, nomeprogetto);
					for (List<String> attività : projectLayout) {
					    String nomeColtivatore = attività.get(0);
					    String cfColtivatore = attività.get(1);
					    String coltura = attività.get(2);
					    int idColtura = Integer.parseInt(attività.get(3));
					    String nomeAttività = attività.get(4);
					    int durata = Integer.parseInt(attività.get(5));
					    Time tempoLavorato = Time.valueOf("00:00:00"); // Tempo inizialmente impostato a 0
					    // Quantità corretta
					    int quantita;
					    if (nomeAttività.equalsIgnoreCase("Raccolta")) {
					        quantita = Integer.parseInt(attività.get(6));
					    } else {
					        quantita = 0;
					    }
					    String dataInizioStr = attività.get(7);
					    LocalDate dataInizio = LocalDate.parse(dataInizioStr);
					    LocalDate dataFine = dataInizio.plusDays(durata);
					    Date sqlDataInizio = Date.valueOf(dataInizio);
					    Date sqlDataFine = Date.valueOf(dataFine);
					    attivitaDAO.InsertAttivita(nomeAttività, sqlDataInizio, sqlDataFine, cfColtivatore, idColtura, tempoLavorato, "Pianificata");
					    if (quantita > 0) {
					        raccoltoDAO.InsertRaccolto(coltura, quantita, id_lotto);
					    }
					CF_coltivatori.add(cfColtivatore);
					AttivitàIds.add(idColtura);
					}
					progettoDAO.InsertColtivatoriInProgetto(idProgetto, CF_coltivatori);
					progettoDAO.InsertAttivitàInProgetto(idProgetto, AttivitàIds);
					return 1; // Successo
				} catch (SQLException e) {
					return -99; // Errore durante l'inserimento
    			
    		}
    		}
    		
    		public List<Map<String, Object>> getDatiRaccoltaPerLotto(String nomelotto, String username) {
    		    int idLotto = 0;
    			try {
    		        List<Lotto> lotti = propDAO.GetLottiProprietario(Convert_UsernameToCF(username));
    		        for (Lotto lotto : lotti) {
			            if (lotto.getNomeLotto().equals(nomelotto)) {
			                idLotto = lotto.getIdLotto();
			                break; // Trovato il lotto, esci dal ciclo perché nome lotto è unico
			            }
			        }
    		    	return raccoltoDAO.getStatisticheRaccoltaPerLotto(idLotto);
    		    } catch (SQLException e) {
    		        e.printStackTrace();
    		        return new ArrayList<>();
    		    }
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
    	  public void OpenInstanceOfProgettoSelected_closeCaller(String CF, JFrame caller, String nomeProgetto, String lottoname) {
    	      Progetto_select_frame = new Instance_of_progetto_selected(CF, this, nomeProgetto, lottoname);
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