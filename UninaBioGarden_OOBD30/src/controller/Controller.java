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
	ColtivatoreDAO coltDAO;
	TESTING tests;
	
	/* ***** CONSTRUCTOR ***** */
	public Controller() {
		try {
			dbprop = Paths.get(userDir, "libs", "dbprop.txt");
			coltDAO = new ColtivatoreDAO(dbprop.toString(), this);
			coltDAO.connect();
			
			// TO COMMENT
			tests = new TESTING(dbprop.toString(), this);
			tests.InitTestColtDAO();
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
}
		
	

