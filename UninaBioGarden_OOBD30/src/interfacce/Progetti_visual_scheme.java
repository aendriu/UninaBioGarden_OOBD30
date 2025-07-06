package interfacce;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Progetti_visual_scheme extends JFrame {

    private static final long serialVersionUID = 1L;
    private String username;
    private Controller TheController;
    private Image Prop_lotti_visual_scheme_image;
    private JTable Project_table;
    private DefaultTableModel tableModel;
    private Instance_of_progetto_selected iops;
    private String Proprietario_username;
    private String Progetto_selezionato;
    // Variabili per mantenere dati originali
    private String[] nomiProgetti;
    private String[] nomiLotti;

    public Progetti_visual_scheme(String username, Controller TheController) {
        this.username = username;
        this.TheController = TheController;
        Proprietario_username = username;
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dimensione schermo
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        getContentPane().setLayout(null);

        // Colonne per la tabella
        String[] colonne = {"Nome Progetto", "Nome Lotto"};

        // Modello tabella senza righe iniziali
        tableModel = new DefaultTableModel(colonne, 0) {
            // Rendi non modificabili le celle
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Tabella
        Project_table = new JTable(tableModel);
        Project_table.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        Project_table.setRowHeight(50);
        Project_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Project_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Gestione del click sulla tabella
				int selectedRow = Project_table.getSelectedRow();
					Progetto_selezionato = (String) tableModel.getValueAt(selectedRow, 0);
					TheController.OpenInstanceOfProgettoSelected_closeCaller(Proprietario_username, Progetti_visual_scheme.this, Progetto_selezionato);
				
			}
		});
        // JScrollPane che contiene la tabella
        JScrollPane scrollPane = new JScrollPane(Project_table);
        scrollPane.setBounds(10, 60, 1500, 700);
        getContentPane().add(scrollPane);

        // Carico dati dal controller PRIMA di tutto
        Object[] dati = TheController.getNomiProgettiELotti(username);
        nomiProgetti = (String[]) dati[0];
        nomiLotti = (String[]) dati[1];

        // Aggiungo righe al modello tabella
        caricaTuttiDati();

        // Bottone torna indietro sotto
        JButton Torna_indietro = new JButton("Torna indietro");
        Torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Torna_indietro.setBounds(0, 770, 1522, 60);
        Torna_indietro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               TheController.OpenProprietarioLoggedIn_closeCaller(username, Progetti_visual_scheme.this);
            }
        });
        Torna_indietro.setPreferredSize(new Dimension(200, 70));
        getContentPane().add(Torna_indietro);

        // Bottone filtro sopra
        JButton Filtra = new JButton("Aggiungi filtro per lotto");
        Filtra.setPreferredSize(new Dimension(200, 70));
        Filtra.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Filtra.setBounds(10, 10, 1522, 50);
        getContentPane().add(Filtra);

        // Set con nomi lotti unici
        Set<String> setLottiUnici = new LinkedHashSet<>();
        for (String lotto : nomiLotti) {
            setLottiUnici.add(lotto);
        }

        // Creo lista con opzione speciale "Rimuovi filtro" in cima
        List<String> listaPopup = new ArrayList<>();
        listaPopup.add("Rimuovi filtro");
        listaPopup.addAll(setLottiUnici);
        String[] lottiUnici = listaPopup.toArray(new String[0]);

        // Lista dentro JScrollPane per il popup
        JList<String> listPopup = new JList<>(lottiUnici);
        listPopup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPopup = new JScrollPane(listPopup);
        scrollPopup.setPreferredSize(new Dimension(300, 150));

        // Popup menu
        JPopupMenu popup = new JPopupMenu();
        popup.setLayout(new BoxLayout(popup, BoxLayout.Y_AXIS));
        popup.add(scrollPopup);

        // Variabile di controllo per mostrare/nascondere popup una volta
        boolean[] isPopupVisible = {false};

        Filtra.addActionListener(e -> {
            if (!isPopupVisible[0]) {
                // mostra popup sotto il bottone, in alto a destra
                popup.show(Filtra, Filtra.getWidth() - scrollPopup.getPreferredSize().width, Filtra.getHeight());
                isPopupVisible[0] = true;
            } else {
                popup.setVisible(false);
                isPopupVisible[0] = false;
            }
        });

        // Listener per selezione nel popup
        listPopup.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedLotto = listPopup.getSelectedValue();
                if (selectedLotto != null) {
                    popup.setVisible(false);
                    isPopupVisible[0] = false;

                    if (selectedLotto.equals("Rimuovi filtro")) {
                        if (tableModel.getRowCount() == nomiProgetti.length) {
							JOptionPane.showMessageDialog(null, "Impossibile rimuovere il filtro: visualizzazione completa già attiva");
						}else {
                    	caricaTuttiDati();
                       JOptionPane.showMessageDialog(null, "Filtro rimosso, passaggio alla visualizzazione completa");
            				}
                    } else {
                        filtraTabellaPerLotto(selectedLotto);
                        JOptionPane.showMessageDialog(null, "Filtro applicato per lotto: " + selectedLotto);
                    }
                }
            }
        });

        // Listener click sulla riga della tabella
       

        // icona
        URL imageUrl = Progetti_visual_scheme .class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            Prop_lotti_visual_scheme_image = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }
    }

    private void caricaTuttiDati() {
        tableModel.setRowCount(0);
        for (int i = 0; i < nomiProgetti.length; i++) {
            tableModel.addRow(new Object[]{nomiProgetti[i], nomiLotti[i]});
        }
    }

    // Metodo per filtrare la tabella in base al nome lotto
    private void filtraTabellaPerLotto(String lottoSelezionato) {
        tableModel.setRowCount(0);
        for (int i = 0; i < nomiProgetti.length; i++) {
            if (nomiLotti[i].equals(lottoSelezionato)) {
                tableModel.addRow(new Object[]{nomiProgetti[i], nomiLotti[i]});
            }
        }
    }

    
}
