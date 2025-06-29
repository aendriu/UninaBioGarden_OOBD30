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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.Controller;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class instance_of_lotto_selected extends JFrame {

    private static final long serialVersionUID = 1L;
    private String lottoName;
    private Controller TheController;
    private String username_proprietario;
    private Image PropImage;
    private JTable table_colture;
    private JTable table_coltivatori;
    private JTable table;
    private Free_coltivatori freeColtivatori;
    public instance_of_lotto_selected(String lottoName, Controller TheController, String username_proprietario) {
        this.lottoName = lottoName;
        this.TheController = TheController;
        this.username_proprietario = username_proprietario;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = Login.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            PropImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }

        // Pannello personalizzato con sfondo
        JPanel pageProprietario = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (PropImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.drawImage(PropImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        pageProprietario.setLayout(null); // per posizionare liberamente gli elementi
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false); // trasparente
        titlePanel.setBounds(267, 10, 938, 81);
        titlePanel.setLayout(new BorderLayout());

        // aggiungo il pannello al pageProprietario
        pageProprietario.add(titlePanel);

        // creo il JLabel che mostra il testo centrato
        JLabel titleLabel = new JLabel(lottoName, JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);

        // aggiungo il label al pannello del titolo (che ha BorderLayout)
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        // Imposto come pannello principale
        setContentPane(pageProprietario);

        // Aggiungo la JTextArea
        table_colture = new JTable();
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] { "Nome" }
        ) {
            Class[] columnTypes = new Class[] { String.class };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        };
        table_colture.setModel(model);
        table_colture.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table_colture.setRowHeight(30);

        // Ora popola la tabella con la lista nomi
        List<Object[]> nomi =  TheController.Riempi_tab_Proprietario_nome_coltura(username_proprietario,lottoName); //METTERE PURE LOTTO NAME QUANDO HO LE DAO
        for (Object[] nome : nomi) {
            model.addRow(new Object[] { nome[0] });
        }
                // Creo lo scroll pane che contiene la tabella
                JScrollPane scrollPaneColture = new JScrollPane(table_colture);
                scrollPaneColture.setBounds(103, 210, 462, 226);  // imposta posizione e dimensione dello scrollpane

                // Aggiungo lo scroll pane al pannello
                pageProprietario.add(scrollPaneColture);
                
             // Crea la JTable senza dati inizialmente (va bene, la riempi dopo)
                table_coltivatori = new JTable();

                // Ottieni la lista di coltivatori, ogni Object[] contiene {nome, cognome}
                List<Object[]> daticoltivatori = TheController.Riempi_tab_Proprietario_nome_coltivatore(username_proprietario,lottoName);

                // Costruisci la matrice dati con n righe e 2 colonne
                int n = daticoltivatori.size();
                Object[][] dati = new Object[n][2];

                for (int i = 0; i < n; i++) {
                    dati[i][0] = daticoltivatori.get(i)[0]; // nome
                    dati[i][1] = daticoltivatori.get(i)[1]; // cognome
                }

                // Definisci le intestazioni di colonna
                String[] colonne = { "Nome", "Cognome" };

                // Crea il modello della tabella con i dati e le intestazioni
                DefaultTableModel modelColtivatori = new DefaultTableModel(dati, colonne) {
                    @Override
                    public Class<?> getColumnClass(int columnIndex) {
                        return String.class;
                    }
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                // Imposta il modello nella JTable
             // --- Tabella coltivatori ---

             // Imposta modello per coltivatori (modelColtivatori già definito prima)
             table_coltivatori.setModel(modelColtivatori);
             table_coltivatori.setRowHeight(30);
             table_coltivatori.setFont(new Font("Times New Roman", Font.PLAIN, 30));

             // Inserisci table_coltivatori dentro JScrollPane
             JScrollPane scrollPaneColtivatori = new JScrollPane(table_coltivatori);
             scrollPaneColtivatori.setBounds(946, 210, 462, 226);
             pageProprietario.add(scrollPaneColtivatori);

             // --- Tabella attività ---

             // Definisci modello per attività
             DefaultTableModel model2 = new DefaultTableModel(
                 new Object[][] {
                     {"Raccolta"},
                     {"Semina"},
                     {"Irrigazione"},
                     {"Concimazione"},
                     {"Controllo parassiti"},
                     {"Potatura"},
                     {"Vendita"},
                     {"Monitoraggio crescita"}
                 },
                 new String[] {
                     "Nome attività"
                 }
             ) {
                 Class<?>[] columnTypes = new Class[] { String.class };
                 @Override
                 public Class<?> getColumnClass(int columnIndex) {
                     return columnTypes[columnIndex];
                 }
                 @Override
                 public boolean isCellEditable(int row, int column) {
                     return false; // tabella non modificabile
                 }
             };

             // Crea la JTable attività con il modello corretto (model2)
             JTable table_activities = new JTable(model2);
             table_activities.setFont(new Font("Times New Roman", Font.PLAIN, 20));
             table_activities.setRowHeight(30);

             // Inserisci table_activities dentro JScrollPane
             JScrollPane scrollPane_activities = new JScrollPane(table_activities);
             scrollPane_activities.setBounds(632, 216, 249, 220);
             pageProprietario.add(scrollPane_activities);


                	// Aggiungi lo JScrollPane al panel
                	pageProprietario.add(scrollPane_activities);
                	
                	JButton aggiungi_coltura = new JButton("Aggiungi coltura");
                	aggiungi_coltura.addMouseListener(new MouseAdapter() {
                		@Override
                		public void mouseClicked(MouseEvent e) {
                		
                			// Apre la finestra per aggiungere una coltura
							Free_colture freeColture = new Free_colture(username_proprietario, TheController, lottoName);
							freeColture.setVisible(true);
							dispose();
						}
                	});
                	aggiungi_coltura.setToolTipText("aggiungi al tuo lotto una nuova coltura");
                	aggiungi_coltura.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                	aggiungi_coltura.setBounds(499, 475, 483, 70);
                	pageProprietario.add(aggiungi_coltura);
                	
                	
                	JButton aggiungi_coltivatore = new JButton("Aggiungi coltivatore");
                	aggiungi_coltivatore.addMouseListener(new MouseAdapter() {
                		@Override
                		public void mouseClicked(MouseEvent e) {
                		 freeColtivatori = new Free_coltivatori(username_proprietario, TheController, lottoName);
                		 freeColtivatori.setVisible(true);
                		 dispose();
                		}
                	});
                	aggiungi_coltivatore.setToolTipText("visualizza i coltivatori liberi al momento e aggiungine uno");
                	aggiungi_coltivatore.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                	aggiungi_coltivatore.setBounds(499, 580, 483, 70);
                	pageProprietario.add(aggiungi_coltivatore);
                	
                	JButton Torna_indietro_2 = new JButton("Torna indietro");
                	Torna_indietro_2.addActionListener(new ActionListener() {
                		public void actionPerformed(ActionEvent e) {
                		Prop_lotti_visual_scheme plvs = new Prop_lotti_visual_scheme(username_proprietario, TheController);
                		plvs.setVisible(true);
                		dispose();
                		}
                	});
                	Torna_indietro_2.addMouseListener(new MouseAdapter() {
                		@Override
                		public void mouseClicked(MouseEvent e) {
                		Prop_lotti_visual_scheme plvs = new Prop_lotti_visual_scheme(username_proprietario, TheController);
                		plvs.setVisible(true);
                		dispose();
                		}
                	});
                	Torna_indietro_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                	Torna_indietro_2.setBounds(499, 698, 483, 70);
                	pageProprietario.add(Torna_indietro_2);
}
}