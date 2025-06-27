package controller;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.nio.file.Path;
import java.awt.EventQueue;

import dao.*;
import entità.*;
import interfacce.Home;

public class Controller {
	String userDir = System.getProperty("user.dir");
	Path dbprop;
	ColtivatoreDAO coltDAO;
	
	public Controller() {
		try {
			dbprop = Paths.get(userDir, "libs", "dbprop.txt");
			coltDAO = new ColtivatoreDAO(dbprop.toString());
			coltDAO.connect();
		
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/* *************** ColtivatoreDAO test *************** */

	public Coltivatore getColtivatore(String CF) throws SQLException {
		Coltivatore colt = coltDAO.FindSpecificColtivatore(CF);
		return colt;
	}
	
}
		
	

