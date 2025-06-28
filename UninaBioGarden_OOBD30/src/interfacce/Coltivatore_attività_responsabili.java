package interfacce;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.net.URL;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import interfacce.Exceptions.Specific_exceptions.Coltivatore_attività_table_exceptions;
import controller.Controller;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import javax.swing.JTextField;

public class Coltivatore_attività_responsabili extends JFrame {

    private static final long serialVersionUID = 1L;
    private Image coltivatoreImage;
    private String username_colt;
    private Controller TheController;
    private JTable table;
    private Page_Coltivatore pageColtivatore;
    // Costruttore che prende solo la stringa username
    public Coltivatore_attività_responsabili(String username_colt, Controller TheController) {
    	setResizable(false);
        this.username_colt = username_colt;
        this.TheController = TheController;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dimensione schermo e fullscreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = Login.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            coltivatoreImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }

        // Pannello personalizzato con sfondo
        JPanel Attività_panel = new JPanel() {
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

        Attività_panel.setLayout(null);

        
       
        
        int spazioBottoni=100;
        setContentPane(Attività_panel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, screenSize.width, screenSize.height - spazioBottoni);
        Attività_panel.add(scrollPane);

        // CREO LA TABELLA PRIMA DI USARLA
        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		int selectedRow = table.getSelectedRow();
                     // converto dalla view alla model
                     int modelRow = table.convertRowIndexToModel(selectedRow);
                    
                     // PRENDI I VALORI DELLE COLONNE che vuoi (esempio col 0 e col 2)
                     String Lotto_selected = (String) table.getModel().getValueAt(modelRow, 0);
                     String Attività_selected = (String) table.getModel().getValueAt(modelRow, 1);
                     String Stato_selected = (String) table.getModel().getValueAt(modelRow, 3);
                     try {
                     if (Stato_selected.equals("completata")) {
                    	    throw new Coltivatore_attività_table_exceptions(
                    	        Coltivatore_attività_table_exceptions.Tipo.activity_arleady_completed
                    	        );
                     }
                     }catch (Coltivatore_attività_table_exceptions e1) {
						 JOptionPane.showMessageDialog(null, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
						 return;
					 }
                     String InputTXT=JOptionPane.showInputDialog("Quante ore è stato dedicato all attività"+ Attività_selected + " del lotto " + Lotto_selected + "? oggi? (se non si inserirà nulla verrà contata come nulla e quindi non vi sarà alcun aggiornamento)");
        		int time_spent = 0;
        		time_spent=Integer.parseInt(InputTXT);
        		try {
        		if(time_spent<0) {
        			throw new Coltivatore_attività_table_exceptions(
							Coltivatore_attività_table_exceptions.Tipo.illegal_number_input
						);
        		}
        		} catch (Coltivatore_attività_table_exceptions e1) {
        			JOptionPane.showMessageDialog(null, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        		}
        	}
        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setToolTipText("clicca una riga per aggiornare la percentuale completamento");

        // prendo i dati
        List<Object[]> righe = TheController.Riempi_tab_attività_responsabili(username_colt);

        // ordina per Nome Lotto
        righe.sort((a, b) -> ((String) a[0]).compareToIgnoreCase((String) b[0]));

        // intestazioni
        String[] colonne = {
            "Nome Lotto",
            "Nome Attività",
            "Coltura",
            "Stato",
            "Percentuale Completamento"
        };

        // conversione con formattazione
        Object[][] dati = new Object[righe.size()][colonne.length];
        for (int i = 0; i < righe.size(); i++) {
            Object[] row = righe.get(i);
            Object[] formattedRow = new Object[colonne.length];
            for (int j = 0; j < colonne.length; j++) {
                if (j == 4) {
                    formattedRow[j] = row[j] + "%";
                } else {
                    formattedRow[j] = row[j];
                }
            }
            dati[i] = formattedRow;
        }

        // modello con dati e intestazioni
        DefaultTableModel model = new DefaultTableModel(
            dati,
            colonne
        ) {
            Class[] columnTypes = new Class[]{
                String.class, String.class, String.class, String.class, String.class
            };
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // **Importante: setto il modello con i dati e non un modello vuoto!**
        table.setModel(model);

        // setto font e altezza righe
        table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table.setRowHeight(30);

        // aggiungo la tabella allo JScrollPane
        scrollPane.setViewportView(table);


        JButton Torna_indietro = new JButton("Torna Indietro");
        Torna_indietro.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
       Torna_indietro.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				pageColtivatore = new Page_Coltivatore(username_colt, TheController);
				pageColtivatore.setVisible(true);
				dispose(); // Chiude la finestra corrente
			}
		});
        
        Torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Torna_indietro.setBounds(645, screenSize.height - 80, 210, 43);
        Attività_panel.add(Torna_indietro);
}
}