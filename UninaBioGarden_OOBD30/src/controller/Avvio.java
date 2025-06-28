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
				//
				Lotto[] colt1_lotti = c.getLottiOfColtivatore(colt1.getCF());
				for(Lotto l : colt1_lotti) {
					System.out.println(l);
				}
				//
				Attivita[] att_colt1 = c.getAttivitaColtivatore(colt1.getCF());				
				for(Attivita a : att_colt1) {
					System.out.println(a);
				}
				//
				Coltivatore colt2 = new Coltivatore("Barbagianni", "Nic", "Ola", "asdagaoiujdaoihjw", "GJJSNSOFWAHDFOVQ");
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        });

	}
	
	/* *************** */
}

