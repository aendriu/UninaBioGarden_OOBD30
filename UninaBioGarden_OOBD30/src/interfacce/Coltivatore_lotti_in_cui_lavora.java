package interfacce;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.Controller;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Coltivatore_lotti_in_cui_lavora extends JFrame {

    private static final long serialVersionUID = 1L;
    private Image coltivatoreImage;
    private String username_colt;
    private JTable lotti_table;
    private Controller TheController;

    public Coltivatore_lotti_in_cui_lavora(String username_colt, Controller TheController) {
        this.username_colt = username_colt;
        this.TheController = TheController;

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dimensione schermo e fullscreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = Coltivatore_lotti_in_cui_lavora.class.getResource("Images/image_progetto_logo.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            coltivatoreImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }

        // Pannello personalizzato con sfondo
        JPanel Tabella_lotti = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (coltivatoreImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.drawImage(coltivatoreImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        Tabella_lotti.setLayout(null);
        setContentPane(Tabella_lotti);

        int spazioBottoni = 100;

     // JScrollPane
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, screenSize.width, screenSize.height - spazioBottoni);
        Tabella_lotti.add(scrollPane);

        // Prendo i dati dal controller
        List<Object[]> righe = TheController.Riempi_tab_lotti_in_cui_lavora(username_colt);
        try {
        	if (righe.isEmpty()) {
        	    throw new IllegalArgumentException("Nessun lotto trovato per il coltivatore: " + username_colt+ "si verrà reindirizzati alla pagina principale");
        	}
        	} catch (Exception e) {
        	    JOptionPane.showMessageDialog(
        	        null,
        	        e.getMessage(),
        	        "Errore",
        	        JOptionPane.ERROR_MESSAGE
        	    		);
        	    TheController.OpenPageColtivatore_closeCaller(username_colt, Coltivatore_lotti_in_cui_lavora.this);	
        	}


        // Intestazioni colonne
        String[] colonne = { "Nome Lotto", "Numero colture" };

        // Conversione lista a matrice
        Object[][] dati = new Object[righe.size()][colonne.length];
        for (int i = 0; i < righe.size(); i++) {
            dati[i] = righe.get(i);
        }

        // Creo la tabella
        lotti_table = new JTable(dati, colonne);

        // AGGIUNGO ordinamento crescente sulla colonna 0
        TableRowSorter sorter = new TableRowSorter(lotti_table.getModel());
        lotti_table.setRowSorter(sorter);
        sorter.toggleSortOrder(0);

        // Configurazioni grafiche
        Font tableFont = new Font("Times New Roman", Font.PLAIN, 22);
        lotti_table.setFont(tableFont);
        lotti_table.setRowHeight(30);
        lotti_table.setFillsViewportHeight(true);

        // Disabilito selezioni
        lotti_table.setRowSelectionAllowed(false);
        lotti_table.setCellSelectionEnabled(false);
        lotti_table.setColumnSelectionAllowed(false);

        // Aggiungo la tabella allo JScrollPane
        scrollPane.setViewportView(lotti_table);

        // Bottone torna indietro
        JButton Torna_Indietro = new JButton("Torna indietro");
        Torna_Indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Torna_Indietro.setBounds(656, 784, 210, 43);
        Torna_Indietro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              TheController.OpenPageColtivatore_closeCaller(username_colt, Coltivatore_lotti_in_cui_lavora.this);
            	// Chiude la finestra corrente
            }
        });
        Tabella_lotti.add(Torna_Indietro);
    }
}
