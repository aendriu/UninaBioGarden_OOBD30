package interfacce;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
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
    private String Proprietario_username;
    private String Progetto_selezionato;
    private String[] nomiProgetti;
    private String[] nomiLotti;

    public Progetti_visual_scheme(String username, Controller TheController) {
        this.username = username;
        this.TheController = TheController;
        this.Proprietario_username = username;
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        getContentPane().setLayout(null);

        String[] colonne = {"Nome Progetto", "Nome Lotto"};
        tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Project_table = new JTable(tableModel);
        Project_table.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        Project_table.setRowHeight(50);
        Project_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Project_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = Project_table.getSelectedRow();
               String nomeProgetto = (String) tableModel.getValueAt(selectedRow, 0);
                String Lotto_selezionato = (String) Project_table.getValueAt(selectedRow, 1);
                TheController.OpenInstanceOfProgettoSelected_closeCaller(Proprietario_username, Progetti_visual_scheme.this, Lotto_selezionato, nomeProgetto);
            }
        });

        JScrollPane scrollPane = new JScrollPane(Project_table);
        scrollPane.setBounds(10, 60, 1500, 700);
        getContentPane().add(scrollPane);

        Object[] dati = TheController.getNomiProgettiELotti(username);
        if (dati.length == 0) {
            JOptionPane.showMessageDialog(null, "Nessun progetto presente al momento");
            TheController.OpenProprietarioLoggedIn_closeCaller(username, Progetti_visual_scheme.this);
        }
        nomiProgetti = (String[]) dati[0];
        nomiLotti = (String[]) dati[1];

        caricaTuttiDati();

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

        JButton Filtra = new JButton("Aggiungi filtro per lotto");
        Filtra.setPreferredSize(new Dimension(200, 70));
        Filtra.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Filtra.setBounds(10, 10, 1522, 50);
        getContentPane().add(Filtra);

        // Popolamento diretto del filtro con lotti
        List<String> listaPopup = new ArrayList<>();
        listaPopup.add("Rimuovi filtro");
        for (String lotto : nomiLotti) {
            listaPopup.add(lotto);
        }
        String[] lottiLista = listaPopup.toArray(new String[0]);

        JList<String> listPopup = new JList<>(lottiLista);
        listPopup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPopup = new JScrollPane(listPopup);
        scrollPopup.setPreferredSize(new Dimension(300, 150));

        JPopupMenu popup = new JPopupMenu();
        popup.setLayout(new BoxLayout(popup, BoxLayout.Y_AXIS));
        popup.add(scrollPopup);

        boolean[] isPopupVisible = {false};

        Filtra.addActionListener(e -> {
            if (!isPopupVisible[0]) {
                popup.show(Filtra, Filtra.getWidth() - scrollPopup.getPreferredSize().width, Filtra.getHeight());
                isPopupVisible[0] = true;
            } else {
                popup.setVisible(false);
                isPopupVisible[0] = false;
            }
        });

        listPopup.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedLotto = listPopup.getSelectedValue();
                if (selectedLotto != null) {
                    popup.setVisible(false);
                    isPopupVisible[0] = false;

                    if (selectedLotto.equals("Rimuovi filtro")) {
                        if (tableModel.getRowCount() == nomiProgetti.length) {
                            JOptionPane.showMessageDialog(null, "Impossibile rimuovere il filtro: visualizzazione completa già attiva");
                        } else {
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
    }
     

    private void caricaTuttiDati() {
        tableModel.setRowCount(0);
        for (int i = 0; i < nomiProgetti.length; i++) {
            tableModel.addRow(new Object[]{nomiProgetti[i], nomiLotti[i]});
        }
    }

    private void filtraTabellaPerLotto(String lottoSelezionato) {
        tableModel.setRowCount(0);
        for (int i = 0; i < nomiProgetti.length; i++) {
            if (nomiLotti[i].equals(lottoSelezionato)) {
                tableModel.addRow(new Object[]{nomiProgetti[i], nomiLotti[i]});
            }
        }
    }
}
