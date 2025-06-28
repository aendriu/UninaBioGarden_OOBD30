package controller;

import java.io.IOException;
import java.sql.SQLException;

import dao.ColtivatoreDAO;
import entità.Coltivatore;
import entità.*;

public class TESTING {
	ColtivatoreDAO coltDAO;
	
	public TESTING(String propertiesFilePath, Controller c) throws SQLException, IOException {
        coltDAO = new ColtivatoreDAO(propertiesFilePath, c);
        coltDAO.connect();
    }
	
	
	// TESTING
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


		
		
	}
}


















