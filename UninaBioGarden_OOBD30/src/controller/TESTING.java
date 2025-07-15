package controller;

import java.sql.Time;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import dao.AttivitaDAO;
import dao.ColtivatoreDAO;
import dao.ColturaDAO;
import dao.LottoDAO;
import dao.ProgettoDAO;
import dao.ProprietarioDAO;
import dao.RaccoltoDAO;
import entità.*;

public class TESTING {
	ColtivatoreDAO coltDAO;
	ProprietarioDAO propDAO;
	LottoDAO lottoDAO;
	AttivitaDAO attDAO;
	RaccoltoDAO racDAO;
	ColturaDAO coltuDAO;
	ProgettoDAO progettoDAO;
	

	
	
	public TESTING(String propertiesFilePath, Controller c) throws SQLException, IOException {
        coltDAO = new ColtivatoreDAO(propertiesFilePath, c);
        propDAO = new ProprietarioDAO(propertiesFilePath, c);
        lottoDAO = new LottoDAO(propertiesFilePath, c);
        attDAO = new AttivitaDAO(propertiesFilePath, c);
        racDAO = new RaccoltoDAO(propertiesFilePath, c);
        coltuDAO = new ColturaDAO(propertiesFilePath, c);
        progettoDAO = new ProgettoDAO(propertiesFilePath, c);
        
        coltDAO.connect();
        propDAO.connect();
        lottoDAO.connect();
        attDAO.connect();
        racDAO.connect();
        coltuDAO.connect();
        progettoDAO.connect();
    }
	
	
	// TESTING
	
	public void InitTests() throws SQLException {
		//InitTestPropDAO();
		//InitTestColtDAO();
		//InitTestLottoDAO();
		InitTestAttDAO();
		//InitTestRacDAO();
		//InitTestColturaDAO();
		//InitTestProgettoDAO();
		return;
	}
	
	public void InitTestColturaDAO() throws SQLException {
		System.out.println("TESTING FindSpecificColtura");
		Coltura c1 = coltuDAO.FindSpecificColtura(1);
		System.out.println(c1);
		
		// *****
		
	
		
		// *****
		
		System.out.println("TESTING InsertColtura...");
		Coltura c2 = new Coltura("Pomodoro", 11);
		int idColtura = coltuDAO.InsertColtura(c2);
		System.out.println(
				idColtura >= 0
				? "Coltura inserita con successo: " + c2.getNomeColtura()
				: "Errore nell'inserimento della coltura " + c2.getNomeColtura()
			);
		
		System.out.println(
				coltuDAO.RemoveColtura(idColtura)
				? c2.getNomeColtura() + " Has been removed"
				: c2.getNomeColtura() + " Has NOT been removed" 
			);
		
	}
	
	public void InitTestProgettoDAO() throws SQLException {
		
		// *****

		System.out.println("TESTING FindSpecificProgetto");
		Progetto p1 = progettoDAO.FindSpecificProgetto(4);
		System.out.println(p1);
		
		// *****
		
		System.out.println("TESTING GetColtivatoriProgetto");
		ArrayList<Coltivatore> coltivatori = progettoDAO.GetColtivatoriProgetto(4);
		for(Coltivatore c : coltivatori) {
			System.out.println(c);
		}
		
		// *****
		
		System.out.println("TESTING GetAttivitaProgetto");
		ArrayList<Attivita> attivita = progettoDAO.GetAttivitaProgetto(4);
		for(Attivita a : attivita) {
			System.out.println(a);
		}
		// *****
		
		System.out.println("TESTING InsertProgetto...");
		int idProgetto = progettoDAO.InsertProgetto(2025, "LMBMTT85D20F205F", 21);
		System.out.println(
				idProgetto >= 0
				? "Progetto inserito con successo "
				: "Errore nell'inserimento del progetto " 
			);
		
		// *****
		
		System.out.println("TESTING RemoveProgetto...");
		System.out.println(
				progettoDAO.RemoveProgettoUsingIdLotto(21)
				? "Progetto Has been removed"
				: "Progetto Has NOT been removed" 
			);
		
	}
	
	public void InitTestLottoDAO() throws SQLException {
		System.out.println("TESTING FindSpecificLotto");
		Lotto l1 = lottoDAO.FindSpecificLotto(32);
		System.out.println(l1);
		// *****
		System.out.println("TESTING GetProprietarioOfLotto");
	}

	
	public void InitTestAttDAO() throws SQLException {
		
		System.out.println("TESTING FindSpecificAttività");
		Attivita att1 = attDAO.FindSpecificAttivita(187);
		System.out.println(att1);
		
		// ***** 
		
		System.out.println("TESTING GetAttivitaColtivatore");
		ArrayList<Attivita> att1_colt = attDAO.GetAttivitaColtivatore("DLSNMN04E14F839Q");
		for(Attivita a : att1_colt) {
			System.out.println(a);
		}
		
		// ***** 

		System.out.println("TESTING GetAttivita completate");
		ArrayList<Attivita> att1_colt_completate = attDAO.GetAttivitaCompletate();
		for(Attivita a : att1_colt_completate) {
			System.out.println(a);
		}
		
		// *****
		
		System.out.println("TESTING InsertAttività...");
		Attivita att2 = new Attivita("Applica Pesticida", Date.valueOf(LocalDate.of(2025, 10, 1)), Date.valueOf(LocalDate.of(2025, 10, 15)), "DLSNMN04E14F839Q");
		System.out.println(att2.getTempoLavorato());
		att2.setIdAttivita(attDAO.InsertAttivita(att2)); 
		System.out.println( att2.getIdAttivita() >= 0
	            ? "Attività inserita con successo: " + att2.getNomeAttivita() + " con ID " + att2.getIdAttivita()
	            : "Errore nell'inserimento dell'attività " + att2.getNomeAttivita()
	        );
	    
		System.out.println(
			attDAO.RemoveAttivita(att2)
			? att2.getNomeAttivita() + " Has been removed"
			: att2.getNomeAttivita() + " Has NOT been removed" 
		);
		
		
		/*
		System.out.println(
				attDAO.RemoveAttivita(416)
				? "Attività with id Has been removed"
				: " Has NOT been removed" 
			);
		*/
	    // *****

	    System.out.println("TESTING UpdateTempoTrascorsoAttività...");
	    System.out.println("BEFORE");
	    System.out.println(att1);
	    att1.AddTempoLavorato(java.sql.Time.valueOf("05:00:00"));
	    attDAO.UpdateTempoLavoratoAttivita(att1.getIdAttivita(), Time.valueOf("05:00:00"));
	    System.out.println("AFTER");
	    System.out.println(att1);
	    
	    	
	}
	
	/* ******************** */
	
	public void InitTestRacDAO() throws SQLException {
		System.out.println("TESTING FindSpecificRaccolto");
		Raccolto rac1 = racDAO.FindSpecificRaccolto(1);
		System.out.println(rac1);
		
		// *****
		
		System.out.println("TESTING GetRaccoltiOfLotto");
		ArrayList<Raccolto> rac1_lotto = racDAO.GetRaccoltiLotto(rac1);
		for(Raccolto r : rac1_lotto) {
			System.out.println(r);
		}
		
		// *****
		
		System.out.println("TESTING GetRaccoltiOfLottoByNomeColtura");	
		ArrayList<Raccolto> rac1_lotto_coltura = racDAO.GetRaccoltiOfLottoByNomeColtura("Pomodoro", 1);
		for(Raccolto r : rac1_lotto_coltura) {
			System.out.println(r);
		}
		ArrayList<Raccolto> rac2_lotto_coltura = racDAO.GetRaccoltiOfLottoByNomeColtura("Mais", 1);
		for(Raccolto r : rac2_lotto_coltura) {
			System.out.println(r);
		}
		
		// *****
		
		System.out.println("TESTING InsertRaccolto...");
		Raccolto rac2 = new Raccolto("Pomodoro", 100, 11);
		
		// *****
		int idRaccolto = racDAO.InsertRaccolto(rac2);
		System.out.println(
				idRaccolto >= 0
				? "Raccolto inserito con successo: " + rac2.getNomeRaccolto()
				: "Errore nell'inserimento del raccolto " + rac2.getNomeRaccolto()
			);
		
		// *****
		
		System.out.println("Testing RemoveRaccolto...");
		System.out.println(
				racDAO.RemoveRaccolto(rac2)
				? rac2.getNomeRaccolto() + " Has been removed"
				: rac2.getNomeRaccolto() + " Has NOT been removed" 
			);
		
		// *****
		
	}
	
	/* ******************** */
	
	public void InitTestPropDAO() throws SQLException {
		System.out.println("TESTING getPropFromCF : ");
		ProprietarioDiLotto prop1 = propDAO.FindSpecificProprietario("DLSNMN04E14F839Q");
		System.out.println(prop1);
		prop1 = propDAO.FindSpecificProprietario("BRNLSS75C11F839Q");
		System.out.println(prop1);
		
		// ***** 
		
		System.out.println("TESTING getLottiProp");
		ArrayList<Lotto> prop1_lotti = propDAO.GetLottiProprietario(prop1.getCF());
		for(Lotto l : prop1_lotti) {
			System.out.println(l);
		}
		prop1_lotti = propDAO.GetLottiProprietario("VLNCHR93C10F205U");
		
		// ***** 
		
		System.out.println("TESTING AddLottoToProp && RemoveLottoFromProp...");
		System.out.println("\nBEFORE");
		for(Lotto l : prop1_lotti) {
			System.out.println(l);
		}
		System.out.println("\nAFTER");
		propDAO.AddLottoToProprietario("VLNCHR93C10F205U", 73);
		for(Lotto l : prop1_lotti) {
			System.out.println(l);
		}
		System.out.println(
				propDAO.RemoveLottoFromProprietario("VLNCHR93C10F205U", 73)
				? "Lotto 73 has been removed from VLNCHR93C10F205U"
				: "Lotto 73 has NOT been removed from VLNCHR93C10F205U"
			);
		
	    // ***** 
		
	}
	
	/* ******************** */
	
	public void InitTestColtDAO() throws SQLException {
		System.out.println("TESTING getColtFromCF : ");
		Coltivatore colt1 = coltDAO.FindSpecificColtivatore("DLSNMN04E14F839Q");
		System.out.println(colt1);
		
		// ***** 
		
		System.out.println("TESTING getLottiColt");
		ArrayList<Lotto> colt1_lotti = coltDAO.GetLottiColtivatore(colt1.getCF());
		for(Lotto l : colt1_lotti) {
			System.out.println(l);
		}
		
		// ***** 
		
		System.out.println("TESTING getAttivitaColt");
		ArrayList<Attivita> colt1_att = coltDAO.GetAttivitaColtivatore(colt1.getCF());
		for(Attivita a : colt1_att) {
			System.out.println(a);
		}
		
		// ***** 
		
		System.out.println("TESTING DoesUsernameExist");
		System.out.println(
			    coltDAO.DoesUsernameExist("usernameDaTestare") 
			    ? "Username esiste nel DB" 
			    : "Username NON esiste nel DB"
			);
		
		// *****
		
		System.out.println("TESTING InsertColtInLotto && RemoveColt...");
		System.out.println("\nBEFORE");
		System.out.println(
				coltDAO.RemoveColtivatoreFromLotto(colt1.getCF(), 32)
				? colt1.getUsername() + " Has been removed from lotto 32"
				: colt1.getUsername() + " Has NOT been removed from lotto 32"
			);
		colt1_lotti = coltDAO.GetLottiColtivatore(colt1.getCF());
		for(Lotto l : colt1_lotti) {
			System.out.println(l);
		}
		coltDAO.InsertColtivatoreInLotto("DLSNMN04E14F839Q", 32);
		System.out.println("\nAFTER");
		colt1_lotti = coltDAO.GetLottiColtivatore(colt1.getCF());
		for(Lotto l : colt1_lotti) {
			System.out.println(l);
		}
		
		// *****
		
	    System.out.println("TESTING InsertUserInto per coltivatore...");
		Coltivatore colt2 = new Coltivatore(
		        "Mario", "Rossastro", "RSSMRA80A01H501Z", "mrossi", "passworda123"
		    );
		System.out.println(
				coltDAO.RemoveUser(colt2)
				? colt2.getUsername() + " Has been removed"
				: colt2.getUsername() + " Has NOT been removed" 
		);
		
//	    System.out.println(coltDAO.InsertUser(colt2)
//	            ? "Coltivatore inserito con successo: " + colt2.getUsername()
//	            : "Errore nell'inserimento del coltivatore " + colt2.getUsername()
//	        );
		
		
		System.out.println("Testing GetFreeColtivatori...");
		ArrayList<Coltivatore> freeColtivatori = coltDAO.GetFreeColtivatori();
		for(Coltivatore c : freeColtivatori) {
			System.out.println(c);
		}
	}
	
		



		
		
}



















