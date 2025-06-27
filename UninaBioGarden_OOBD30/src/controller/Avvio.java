package controller;

import java.awt.EventQueue;

import entità.*;
import interfacce.Home;

public class Avvio {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				// Starting with HOME
				Home frame = new Home();
				frame.setVisible(true);
				
				// Testing ColtivatoreDAO
				Controller c = new Controller();
				Coltivatore colt1 = c.getColtivatore("DLSNMN04E14F839Q");
				System.out.println(colt1);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        });

	}
	
	/* *************** */
}

