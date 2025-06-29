package controller;

import java.io.IOException;
import java.sql.SQLException;

import dao.ColtivatoreDAO;
import dao.ProprietarioDAO;
import entità.Coltivatore;
import entità.*;

public class TESTING {
	ColtivatoreDAO coltDAO;
	ProprietarioDAO propDAO;
	
	public TESTING(String propertiesFilePath, Controller c) throws SQLException, IOException {
        coltDAO = new ColtivatoreDAO(propertiesFilePath, c);
        propDAO = new ProprietarioDAO(propertiesFilePath, c);
        coltDAO.connect();
        propDAO.connect();
    }
	
	
	// TESTING
	
	public void InitTests() throws SQLException {
		InitTestPropDAO();
		InitTestColtDAO();
		return;
	}
	
	public void InitTestPropDAO() throws SQLException {
		System.out.println("TESTING getPropFromCF : ");
		ProprietarioDiLotto prop1 = propDAO.FindSpecificProprietario("DLSNMN04E14F839Q");
		System.out.println(prop1);
		prop1 = propDAO.FindSpecificProprietario("BRNLSS75C11F839Q");
		System.out.println(prop1);
		
		// ***** 
		
		
		
		
	}
	
	public void InitTestColtDAO() throws SQLException {
		System.out.println("TESTING getColtFromCF : ");
		Coltivatore colt1 = coltDAO.FindSpecificColtivatore("DLSNMN04E14F839Q");
		System.out.println(colt1);
		
		// ***** 
		
		System.out.println("TESTING getLottiColt");
		Lotto[] colt1_lotti = coltDAO.GetLottiColtivatore(colt1.getCF());
		for(Lotto l : colt1_lotti) {
			System.out.println(l);
		}
		
		// ***** 
		
		System.out.println("TESTING getAttivitaColt");
		Attivita[] colt1_att = coltDAO.GetAttivitaColtivatore(colt1.getCF());
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
		
	    System.out.println(coltDAO.InsertUser(colt2)
	            ? "Coltivatore inserito con successo: " + colt2.getUsername()
	            : "Errore nell'inserimento del coltivatore " + colt2.getUsername()
	        );
	    }



		
		
}



















