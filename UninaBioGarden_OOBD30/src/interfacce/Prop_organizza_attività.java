
package interfacce;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import controller.Controller;
import interfacce.Exceptions.Global_exceptions;
import interfacce.Exceptions.Specific_exceptions.Proprietario_addons_selection_exceptions;
import interfacce.Exceptions.Specific_exceptions.Proprietario_addons_selection_exceptions.Tipo;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Prop_organizza_attività extends JFrame {

    private static final long serialVersionUID = 1L;
    private Controller TheController;
    private String username;
    private Image PropImage;
    private Map<String, Integer> colturaMap = new HashMap<>();
    private String currentSelectedLotto = null;  // per tracciare lotto selezionato e evitare reload inutili
    private ArrayList<String> coltivatoreCFList = new ArrayList<>();
    private String selectedColtivatoreCF = null;
    // campo 1 - Lotto
    private JTextField Lotto;
    private JScrollPane popupScrollLotto;
    private JList<String> Lotto_list;

    // campo 2 - Attività (statica)
    private JTextField Attività;
    private JScrollPane popupScrollAttività;
    private JList<String> Lista_attività;

    // campo 3 - Coltura (con campo nascosto accanto)
    private JTextField colturaNome;
    private JTextField colturaCodice;  // campo nascosto
    private JScrollPane popupScrollColtura;
    private JList<String> Coltura_list;  // useremo formato "nome - codice" per mostrare nel popup

    // campo 4 - Coltivatore (unificato Nome + Cognome)
    private JTextField coltivatore;
    private JScrollPane popupScrollColtivatore;
    private JList<String> coltivatore_list;  // useremo formato "Nome Cognome"

    // Date fields
    private JTextField data_inizio;
    private JTextField data_fine;

    private String[] dati_selected = new String[7]; // 0=Lotto,1=Nome Coltivatore,2= (n.d.),3=Attività,4=Coltura nome,5=data_inizio,6=data_fine
    // Nota: abbiamo un solo campo per nome e cognome coltivatore, quindi dati_selected[2] sarà lasciato vuoto o nullo

    public Prop_organizza_attività(String username, Controller TheController) {
        setResizable(false);
        this.TheController = TheController;
        this.username = username;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = Prop_organizza_attività.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            PropImage = new ImageIcon(imageUrl).getImage();
        }

        JPanel pageProp = new JPanel() {
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
        pageProp.setLayout(null);
        setContentPane(pageProp);

        JLabel lblTitle = new JLabel("Organizza una attività");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        lblTitle.setBounds(409, 10, 704, 92);
        pageProp.add(lblTitle);

        // === CAMPO 1 === Lotto
        Lotto = new JTextField();
        Lotto.setEditable(false);
        Lotto.setFocusable(false);
        Lotto.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Lotto.setBounds(31, 231, 300, 40);
        pageProp.add(Lotto);

        Lotto_list = new JList<>();
        Lotto_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Lotto_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String sel = Lotto_list.getSelectedValue();
                if (sel != null) {
                    if (!sel.equals(currentSelectedLotto)) {
                        currentSelectedLotto = sel;
                        // Carico le liste dipendenti dal lotto selezionato
                        caricaColturePerLotto(sel);
                        caricaColtivatoriPerLotto(sel);
                        // Attività è statica, non cambia al cambio lotto
                        // Pulisco campi coltura e coltivatore per nuova selezione
                        colturaNome.setText("");
                        colturaCodice.setText("");
                        coltivatore.setText("");
                    }
                    Lotto.setText(sel);
                    popupScrollLotto.setVisible(false);
                }
            }
        });
        popupScrollLotto = new JScrollPane(Lotto_list);
        popupScrollLotto.setBounds(Lotto.getX(), Lotto.getY() + Lotto.getHeight(), 300, 100);
        popupScrollLotto.setVisible(false);
        pageProp.add(popupScrollLotto);

        Lotto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Carico lotto sempre (potresti voler aggiungere caching)
                Lotto_list.setListData(TheController.getLottiPerProprietario(username));
                popupScrollLotto.setVisible(true);
            }
        });

        // === CAMPO 2 === Attività (statica, 4 righe)
        Attività = new JTextField();
        Attività.setEditable(false);
        Attività.setFocusable(false);
        Attività.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Attività.setBounds(794, 348, 300, 40);
        pageProp.add(Attività);

        Lista_attività = new JList<>();
        Lista_attività.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Lista_attività.setVisibleRowCount(4); // massimo 4 righe visibili
        Lista_attività.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String sel = Lista_attività.getSelectedValue();
                if (sel != null) {
                    Attività.setText(sel);
                    popupScrollAttività.setVisible(false);
                }
            }
        });
        popupScrollAttività = new JScrollPane(Lista_attività);
        popupScrollAttività.setBounds(Attività.getX(), Attività.getY() + Attività.getHeight(), 300, 100);
        popupScrollAttività.setVisible(false);
        pageProp.add(popupScrollAttività);

        Attività.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Lista_attività.getModel().getSize() == 0) {
                    // Popolo attività statica 1 sola volta
                	String[] attivitàStatiche = {
                		    "Applica Pesticida",
                		    "Semina",
                		    "Irrigazione",
                		    "Raccolta"
                		};
                		Lista_attività.setListData(attivitàStatiche);
                }
                popupScrollAttività.setVisible(true);
            }
        });

     // === CAMPO 3 === Coltura + campo nascosto codice
        colturaNome = new JTextField();
        colturaNome.setEditable(false);
        colturaNome.setFocusable(false);
        colturaNome.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        colturaNome.setBounds(440, 231, 300, 40);
        pageProp.add(colturaNome);

        colturaCodice = new JTextField();
        colturaCodice.setEditable(false);
        colturaCodice.setFocusable(false);
        colturaCodice.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        colturaCodice.setBounds(670, 231, 70, 40);
        colturaCodice.setVisible(false); // nascosto ma disponibile
        pageProp.add(colturaCodice);

        Coltura_list = new JList<>();
        Coltura_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Coltura_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String sel = Coltura_list.getSelectedValue();
                if (sel != null) {
                    // supponendo che sel sia solo il nome, se contiene "nome - codice" devi estrarre il nome
                    String nomeColtura = sel.contains(" - ") ? sel.split(" - ")[0] : sel;
                    colturaNome.setText(nomeColtura);
                    Integer codice = colturaMap.get(nomeColtura);
                    colturaCodice.setText(codice != null ? codice.toString() : "");
                    popupScrollColtura.setVisible(false);
                }
            }
        });
        popupScrollColtura = new JScrollPane(Coltura_list);
        popupScrollColtura.setBounds(colturaNome.getX(), colturaNome.getY() + colturaNome.getHeight(), 300, 100);
        popupScrollColtura.setVisible(false);
        pageProp.add(popupScrollColtura);

        colturaNome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (currentSelectedLotto != null) {
                        popupScrollColtura.setVisible(true);
                    } else {
                        throw new Proprietario_addons_selection_exceptions(Tipo.Select_lotto_first);
                    }
                } catch (Proprietario_addons_selection_exceptions ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // === CAMPO 4 === Coltivatore (Nome + Cognome unificati)
        coltivatore = new JTextField();
        coltivatore.setEditable(false);
        coltivatore.setFocusable(false);
        coltivatore.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        coltivatore.setBounds(234, 430, 300, 40);
        pageProp.add(coltivatore);

        coltivatore_list = new JList<>();
        coltivatore_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coltivatore_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = coltivatore_list.getSelectedIndex();
                if (index >= 0 && index < coltivatoreCFList.size()) {
                    String selNome = coltivatore_list.getSelectedValue();
                    String selCF = coltivatoreCFList.get(index);
                    coltivatore.setText(selNome);
                    selectedColtivatoreCF = selCF;
                    popupScrollColtivatore.setVisible(false);
                }
            }
        });
        popupScrollColtivatore = new JScrollPane(coltivatore_list);
        popupScrollColtivatore.setBounds(coltivatore.getX(), coltivatore.getY() + coltivatore.getHeight(), 300, 100);
        popupScrollColtivatore.setVisible(false);
        pageProp.add(popupScrollColtivatore);

        coltivatore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (currentSelectedLotto != null) {
                        popupScrollColtivatore.setVisible(true);
                    } else {
                        throw new Proprietario_addons_selection_exceptions(Tipo.Select_lotto_first);
                    }
                } catch (Proprietario_addons_selection_exceptions ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Date fields
        data_inizio = new JTextField("DD/MM/YYYY");
        data_inizio.setForeground(new Color(192, 192, 192));
        data_inizio.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        data_inizio.setBounds(1150, 305, 300, 40);
        pageProp.add(data_inizio);

        data_inizio.addMouseListener(new MouseAdapter() {
            private boolean firstClick = true;
            @Override
            public void mouseClicked(MouseEvent e) {
                if (firstClick) {
                    data_inizio.setText("");
                    data_inizio.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    data_inizio.setForeground(Color.BLACK);
                    firstClick = false;
                }
            }
        });

        data_fine = new JTextField("DD/MM/YYYY");
        data_fine.setForeground(new Color(192, 192, 192));
        data_fine.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        data_fine.setBounds(1150, 402, 300, 40);
        pageProp.add(data_fine);

        data_fine.addMouseListener(new MouseAdapter() {
            private boolean firstClick = true;
            @Override
            public void mouseClicked(MouseEvent e) {
                if (firstClick) {
                    data_fine.setText("");
                    data_fine.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    data_fine.setForeground(Color.BLACK);
                    firstClick = false;
                }
            }
        });

        // Pulsanti Torna Indietro, Cancella tutto e Organizza Attività
        JButton Torna_indietro = new JButton("Torna indietro");
        Torna_indietro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!Lotto.getText().isEmpty() ||
                    !coltivatore.getText().isEmpty() ||
                    !Attività.getText().isEmpty() ||
                    !colturaNome.getText().isEmpty() ||
                    !data_inizio.getText().equals("DD/MM/YYYY") ||
                    !data_fine.getText().equals("DD/MM/YYYY")) {

                    Object[] options = {"Conferma", "Annulla"};
                    int risposta = JOptionPane.showOptionDialog(
                        null,
                        "L'azione cancellerà tutti i campi selezionati, procedere?",
                        "Errore",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]  // default su "Annulla"
                    );

                    if (risposta == JOptionPane.YES_OPTION) {
                        pulisciCampi();
                        TheController.OpenProprietarioLoggedIn_closeCaller(username, Prop_organizza_attività.this);
                    } else {
                        return; // Annulla l'azione
                    }
                } else {
                    TheController.OpenProprietarioLoggedIn_closeCaller(username, Prop_organizza_attività.this);
                }
            }
        });
        Torna_indietro.setPreferredSize(new Dimension(200, 70));
        Torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Torna_indietro.setBounds(49, 733, 385, 70);
        pageProp.add(Torna_indietro);

        JButton Cancella_tutto = new JButton("Cancella tutto");
        Cancella_tutto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!Lotto.getText().isEmpty() ||
                    !coltivatore.getText().isEmpty() ||
                    !Attività.getText().isEmpty() ||
                    !colturaNome.getText().isEmpty() ||
                    !data_inizio.getText().equals("DD/MM/YYYY") ||
                    !data_fine.getText().equals("DD/MM/YYYY")) {

                    pulisciCampi();

                } else {
                    JOptionPane.showMessageDialog(null, "Nessun campo da cancellare", "Informazione", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        Cancella_tutto.setPreferredSize(new Dimension(200, 70));
        Cancella_tutto.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Cancella_tutto.setBounds(572, 733, 385, 70);
        pageProp.add(Cancella_tutto);

        JButton Aggiungi_attività = new JButton("Organizza Attività");
        Aggiungi_attività.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // reset dati_selected
                    for (int i = 0; i < dati_selected.length; i++) {
                        dati_selected[i] = null;
                    }
                    int raccolto_forse=0;
                    dati_selected[0] = Lotto.getText();
                    dati_selected[1] = selectedColtivatoreCF;
                    dati_selected[2] = Attività.getText();
                    dati_selected[3] = colturaNome.getText();
                    dati_selected[4] = colturaCodice.getText(); // codice nascosto
                    if (dati_selected [2].equals("Raccolta")) {
                    	String quantità= JOptionPane.showInputDialog(null, "Inserisci il numero da raccogliere", "Raccolta", JOptionPane.QUESTION_MESSAGE);
                    	if (quantità == null || quantità.isEmpty()) {
							throw new Global_exceptions("Quantità", Global_exceptions.Tipo.empty_field);
                    	}else if (!quantità.matches("^[0-9]+$")) {
                    		throw new Global_exceptions("Quantità", Global_exceptions.Tipo.format_mismatch);
                    	}
                    	int quantitaInt= Integer.parseInt(quantità);
                    	if (quantitaInt < 0) {
							throw new Global_exceptions("Quantità", Global_exceptions.Tipo.Type_mismatch);
						}else if (quantitaInt==0) {
							JOptionPane.showMessageDialog(null, "Non puoi raccogliere 0 unità", "Attenzione", JOptionPane.WARNING_MESSAGE);
						}
                    	raccolto_forse=quantitaInt;
                    }
                    
                    if (dati_selected[0].isEmpty()) {
                        Lotto.setBackground(Color.RED);
                        throw new Global_exceptions("Lotto", Global_exceptions.Tipo.empty_field);
                    }
                    if (dati_selected[1].isEmpty()) {
                        coltivatore.setBackground(Color.RED);
                        throw new Global_exceptions("Coltivatore", Global_exceptions.Tipo.empty_field);
                    }
                    if (dati_selected[3].isEmpty()) {
                        Attività.setBackground(Color.RED);
                        throw new Global_exceptions("Attività", Global_exceptions.Tipo.empty_field);
                    }
                    if (dati_selected[4].isEmpty()) {
                        colturaNome.setBackground(Color.RED);
                        throw new Global_exceptions("Coltura", Global_exceptions.Tipo.empty_field);
                    }
                    if (data_inizio.getText().equals("DD/MM/YYYY") || data_inizio.getText().isEmpty()) {
                        data_inizio.setBackground(Color.RED);
                        throw new Global_exceptions("Data di inizio", Global_exceptions.Tipo.empty_field);
                    }
                    if (data_fine.getText().equals("DD/MM/YYYY") || data_fine.getText().isEmpty()) {
                        data_fine.setBackground(Color.RED);
                        throw new Global_exceptions("Data di fine", Global_exceptions.Tipo.empty_field);
                    }

                    // Controllo formato date
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate inizio = LocalDate.parse(data_inizio.getText(), formatter);
                    LocalDate fine = LocalDate.parse(data_fine.getText(), formatter);

                    if (fine.isBefore(inizio)) {
                        throw new Proprietario_addons_selection_exceptions(Tipo.Date_of_completion_precede_starting_date);
                    }

                    dati_selected[5] = data_inizio.getText();
                    dati_selected[6] = data_fine.getText();
                    
                    int validat=TheController.Organizza_Attività(username, dati_selected, raccolto_forse);
                    if (validat==-99) {
                    	throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
                    }
                  
						
					
                } catch (DateTimeParseException dtpe) {
                    JOptionPane.showMessageDialog(null, "Formato data non valido! Usa dd/MM/yyyy", "Errore data", JOptionPane.ERROR_MESSAGE);
                } catch (Global_exceptions | Proprietario_addons_selection_exceptions ge) {
                    JOptionPane.showMessageDialog(null, ge.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        Aggiungi_attività.setPreferredSize(new Dimension(200, 70));
        Aggiungi_attività.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Aggiungi_attività.setBounds(1095, 733, 385, 70);
        pageProp.add(Aggiungi_attività);

        }

        private void caricaColturePerLotto(String lotto) {
            String[][] colture = TheController.getColture(lotto, username);
            String[] listaPerJList = new String[colture.length];
            colturaMap.clear();
            for (int i = 0; i < colture.length; i++) {
                listaPerJList[i] = colture[i][0];  // solo nome
                try {
                    colturaMap.put(colture[i][0], Integer.parseInt(colture[i][1])); // nome -> codice
                } catch (NumberFormatException e) {
                    colturaMap.put(colture[i][0], null);
                }
            }
            Coltura_list.setListData(listaPerJList);
        }

        private void caricaColtivatoriPerLotto(String lotto) {
            String[][] coltivatoriData = TheController.getColtivatoriConCF(lotto, username);
            if (coltivatoriData == null) {
                coltivatore_list.setListData(new String[0]);
                coltivatoreCFList.clear();
                return;
            }
            String[] nomi = new String[coltivatoriData.length];
            coltivatoreCFList.clear();
            for (int i = 0; i < coltivatoriData.length; i++) {
                coltivatoreCFList.add(coltivatoriData[i][0]);
                nomi[i] = coltivatoriData[i][1];
            }
            coltivatore_list.setListData(nomi);
            selectedColtivatoreCF = null;
        }

        private void pulisciCampi() {
            Lotto.setText("");
            coltivatore.setText("");
            Attività.setText("");
            colturaNome.setText("");
            colturaCodice.setText("");
            data_inizio.setText("DD/MM/YYYY");
            data_inizio.setForeground(new Color(192,192,192));
            data_inizio.setFont(new Font("Times New Roman", Font.ITALIC, 20));
            data_fine.setText("DD/MM/YYYY");
            data_fine.setForeground(new Color(192,192,192));
            data_fine.setFont(new Font("Times New Roman", Font.ITALIC, 20));
            currentSelectedLotto = null;
            Coltura_list.setListData(new String[0]);
            coltivatore_list.setListData(new String[0]);
            popupScrollLotto.setVisible(false);
            popupScrollColtura.setVisible(false);
            popupScrollColtivatore.setVisible(false);
            popupScrollAttività.setVisible(false);
        }
}
