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

import controller.Controller;
import interfacce.Exceptions.Global_exceptions;
import interfacce.Exceptions.Specific_exceptions.Prop_Project_exceptions;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class Istance_of_progetto_selected extends JFrame {

    private static final long serialVersionUID = 1L;
    private Image proprietarioImage;             
    private String username_prop;                 
    private Controller TheController;              
    private String nome_progetto;                  
    private JTable Project_overall;  
    private Progetti_visual_scheme pageProgettiVisualScheme;

    public Istance_of_progetto_selected(String username_prop, Controller TheController, String nome_progetto) {
        setResizable(false);
        this.username_prop = username_prop;
        this.TheController = TheController;
        this.nome_progetto = nome_progetto;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dimensione schermo e fullscreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = Login.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
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
        List<Object[]> righe = TheController.Riempi_tab_progetti_vista_proprietario(username_prop);
        righe.sort((a, b) -> ((String) a[0]).compareToIgnoreCase((String) b[0]));

        // Nuove colonne, senza "Nome Lotto" (indice 0) e "Percentuale Completamento" (indice 6)
        String[] colonne = {
            "Nome Coltivatore", "Cognome Coltivatore",
            "Nome Attività", "Coltura", "Stato"
        };

        Object[][] dati = new Object[righe.size()][colonne.length];
        for (int i = 0; i < righe.size(); i++) {
            Object[] row = righe.get(i);
            Object[] formattedRow = new Object[colonne.length];

            formattedRow[0] = row[0]; // Nome Coltivatore
            formattedRow[1] = row[1]; // Cognome Coltivatore
            formattedRow[2] = row[2]; // Nome Attività
            formattedRow[3] = row[3]; // Coltura
            formattedRow[4] = row[4]; // Stato

            dati[i] = formattedRow;
        }

        DefaultTableModel model = new DefaultTableModel(
            dati, colonne
        ) {
            Class[] columnTypes = new Class[]{
                String.class, String.class, String.class,
                String.class, String.class
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

        // Imposto selezione singola riga
        Project_overall.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Project_overall.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Project_overall.setRowHeight(30);

        Project_overall.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = Project_overall.getSelectedRow();
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(null, "Seleziona una riga prima di cliccare.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                Object valore_do_not_show = Project_overall.getValueAt(selectedRow, 4);
                Object valore_to_show = Project_overall.getValueAt(selectedRow, 2);

                String input = JOptionPane.showInputDialog(null, "Riportare il nuovo stato dell'attivita: " + valore_to_show + ".");

                try {
                    if (input == null || input.trim().isEmpty()) {
                        throw new Global_exceptions("di input", Global_exceptions.Tipo.empty_field);
                    } else if (!input.matches("^[a-zA-Z ]+$")) {
                        throw new Global_exceptions("di input", Global_exceptions.Tipo.format_mismatch);
                    } else if (input.equalsIgnoreCase(valore_do_not_show != null ? valore_do_not_show.toString() : "")) {
                        throw new Prop_Project_exceptions(Prop_Project_exceptions.Tipo.repeated_activity_state);
                    } else if (!input.equalsIgnoreCase("In Corso") && !input.equalsIgnoreCase("Completata") && !input.equalsIgnoreCase("Non Completata")) {
                        throw new Prop_Project_exceptions(Prop_Project_exceptions.Tipo.invalid_activity_state);
                    }

                    valore_do_not_show = input;
                    JOptionPane.showMessageDialog(null, "Stato dell'attività aggiornato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    input = capitalizeFirstLetter(input);
                    Project_overall.getModel().setValueAt(input, selectedRow, 4);
                    Project_overall.getModel().setValueAt(input, selectedRow, 4);
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
            pageProgettiVisualScheme = new Progetti_visual_scheme(username_prop, TheController);
            pageProgettiVisualScheme.setVisible(true);
            dispose(); // Chiude la finestra corrente
        });
        tornaIndietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        tornaIndietro.setBounds(645, screenSize.height - 80, 210, 43);
        attività_panel.add(tornaIndietro);
    }


    private String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
