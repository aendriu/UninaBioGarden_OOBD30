package interfacce;

import java.awt.Component;
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
import javax.swing.JProgressBar;

import controller.Controller;
import interfacce.Exceptions.Global_exceptions;
import interfacce.Exceptions.Specific_exceptions.Prop_Project_exceptions;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

public class Instance_of_progetto_selected extends JFrame {

    private static final long serialVersionUID = 1L;
    private Image proprietarioImage;             
    private String username_prop;                 
    private Controller TheController;              
    private String nome_progetto;                  
    private String nome_lotto;
    private JTable Project_overall;  

    public Instance_of_progetto_selected(String username_prop, Controller TheController, String nome_progetto, String nome_lotto) {
        setResizable(false);
        this.username_prop = username_prop;
        this.TheController = TheController;
        this.nome_progetto = nome_progetto;
        this.nome_lotto = nome_lotto;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dimensione schermo e fullscreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl =  Instance_of_progetto_selected.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            proprietarioImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }

        // Pannello personalizzato con sfondo
        JPanel attività_panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (proprietarioImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.drawImage(proprietarioImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        attività_panel.setLayout(null);
        setContentPane(attività_panel);

        // LABEL NOME PROGETTO SOPRA TABELLA
        JLabel progettoLabel = new JLabel(nome_progetto, JLabel.CENTER);
        progettoLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
        progettoLabel.setBounds(0, 10, screenSize.width, 50);
        attività_panel.add(progettoLabel);

        int spazioBottoni = 100;
        int spazioLabel = 70; // spazio occupato dalla label + margine

        JScrollPane scrollPane = new JScrollPane();
        // Posiziona la tabella sotto la label e sopra il bottone torna indietro
        scrollPane.setBounds(0, spazioLabel, screenSize.width, screenSize.height - spazioBottoni - spazioLabel);
        attività_panel.add(scrollPane);

        // Creo la tabella
        Project_overall = new JTable();

        // Prendo i dati
        List<Object[]> righe = TheController.Riempi_tab_progetti_vista_proprietario(username_prop, nome_lotto, 1);
        try {
			if (righe == null || righe.isEmpty()) {
				throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
			}
		} catch (Global_exceptions e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			TheController.OpenPropProgettiVisualScheme_closeCaller(username_prop, Instance_of_progetto_selected.this);
			return;
		}

		// Ordino le righe per nome coltivatore
		if (righe.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nessuna attività trovata per il progetto selezionato.", "Informazione", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
        righe.sort((a, b) -> ((String) a[0]).compareToIgnoreCase((String) b[0]));

        // Colonne da visualizzare
        String[] colonne = {
        	    "Nome Coltivatore", "Cognome Coltivatore",
        	    "Nome Attività", "Coltura", "Stato",
        	    "Percentuale Completamento", "Hidden Data"
        	};

        	// Popolo la matrice dati con 7 colonne
        	Object[][] dati = new Object[righe.size()][colonne.length];
        	for (int i = 0; i < righe.size(); i++) {
        	    Object[] row = righe.get(i);
        	    Object[] formattedRow = new Object[colonne.length];

        	    formattedRow[0] = row[0]; // Nome Coltivatore
        	    formattedRow[1] = row[1]; // Cognome Coltivatore
        	    formattedRow[2] = row[2]; // Nome Attività
        	    formattedRow[3] = row[3]; // Coltura
        	    formattedRow[4] = row[4]; // Stato
        	    formattedRow[5] = Integer.parseInt(row[5].toString()); // Percentuale
        	    formattedRow[6] = (row.length > 6) ? row[6] : ""; // Hidden field (opzionale)

        	    dati[i] = formattedRow;
        	}

        	DefaultTableModel model = new DefaultTableModel(dati, colonne) {
        	    Class[] columnTypes = new Class[] {
        	        String.class, String.class, String.class,
        	        String.class, String.class, Integer.class, Object.class
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

        	Project_overall.setModel(model);

        	// Renderer per la progress bar nella colonna 5
        	TableCellRenderer progressRenderer = new TableCellRenderer() {
        	    private final JProgressBar progressBar = new JProgressBar(0, 100);

        	    @Override
        	    public Component getTableCellRendererComponent(JTable table, Object value,
        	            boolean isSelected, boolean hasFocus, int row, int column) {
        	        int percent = 0;
        	        if (value instanceof Integer) {
        	            percent = (Integer) value;
        	        }
        	        progressBar.setValue(percent);
        	        progressBar.setStringPainted(true);
        	        if (isSelected) {
        	            progressBar.setBackground(table.getSelectionBackground());
        	        } else {
        	            progressBar.setBackground(UIManager.getColor("ProgressBar.background"));
        	        }
        	        return progressBar;
        	    }
        	};
        	Project_overall.getColumnModel().getColumn(5).setCellRenderer(progressRenderer);

        	// Nascondo la colonna 6 ("Hidden Data") visivamente ma NON la rimuovo dal modello
        	Project_overall.getColumnModel().getColumn(6).setMinWidth(0);
        	Project_overall.getColumnModel().getColumn(6).setMaxWidth(0);
        	Project_overall.getColumnModel().getColumn(6).setWidth(0);
        	Project_overall.getColumnModel().getColumn(6).setResizable(false);
        Project_overall.getColumnModel().getColumn(5).setCellRenderer(progressRenderer);

        // Configurazione tabella
        Project_overall.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Project_overall.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Project_overall.setRowHeight(30);

        // Gestione click riga per modificare lo stato
        Project_overall.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = Project_overall.getSelectedRow();
                String stato_attuale = (String) Project_overall.getValueAt(selectedRow, 4);
                String nome_attivita = (String) Project_overall.getValueAt(selectedRow, 2);
                int id_attività = (int) Project_overall.getValueAt(selectedRow, 6); // Assuming this is the hidden ID
                String input = JOptionPane.showInputDialog(null,
                        "Riportare il nuovo stato dell'attività: " + nome_attivita + ".");

                try {
                    if(input==null) {
                    	return; // Se l'utente chiude il dialogo, esce senza fare nulla
                    }
                	if (input.trim().isEmpty()) {
                        throw new Global_exceptions("di input", Global_exceptions.Tipo.empty_field);
                    } else if (!input.matches("^[a-zA-Z ]+$")) {
                        throw new Global_exceptions("di input", Global_exceptions.Tipo.format_mismatch);
                    } else if (input.equalsIgnoreCase(stato_attuale != null ? stato_attuale.toString() : "")) {
                        throw new Prop_Project_exceptions(Prop_Project_exceptions.Tipo.repeated_activity_state);
                    } else if (!input.equalsIgnoreCase("In Corso") &&
                               !input.equalsIgnoreCase("Completata") &&
                               !input.equalsIgnoreCase("Non Completata")) {
                        throw new Prop_Project_exceptions(Prop_Project_exceptions.Tipo.invalid_activity_state);
                    }

                    input = capitalizeFirstLetter(input);
                    boolean validat= TheController.AggiornaAttività(id_attività, input);
                    if (!validat) {
						throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
					}
                    Project_overall.getModel().setValueAt(input, selectedRow, 4);
                    JOptionPane.showMessageDialog(null,
                            "Stato dell'attività aggiornato con successo.",
                            "Successo",
                            JOptionPane.INFORMATION_MESSAGE);
                    if (input.equalsIgnoreCase("completata")) {
                    	Project_overall.getModel().setValueAt(100, selectedRow, 5);
                    }else if (input.equalsIgnoreCase("in corso")) {
						Project_overall.getModel().setValueAt(50, selectedRow, 5);
					} else if (input.equalsIgnoreCase("pianificata")) {
						Project_overall.getModel().setValueAt(0, selectedRow, 5);
					}
                    
                } catch (Global_exceptions | Prop_Project_exceptions e) {
                    if (e instanceof Prop_Project_exceptions) {
                        Prop_Project_exceptions ppe = (Prop_Project_exceptions) e;
                        if (ppe.getTipo().equals(Prop_Project_exceptions.Tipo.repeated_activity_state)) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Attenzione", JOptionPane.WARNING_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        scrollPane.setViewportView(Project_overall);

        JButton tornaIndietro = new JButton("Torna Indietro");
        tornaIndietro.addActionListener(e -> {
            TheController.OpenPropProgettiVisualScheme_closeCaller(username_prop, Instance_of_progetto_selected.this);
        });
        tornaIndietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        tornaIndietro.setBounds(645, screenSize.height - 80, 210, 43);
        attività_panel.add(tornaIndietro);
    }


    private String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
