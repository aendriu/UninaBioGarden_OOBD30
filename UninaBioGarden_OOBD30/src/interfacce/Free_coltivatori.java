package interfacce;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

import java.awt.*;
import java.util.List;

import interfacce.Exceptions.Specific_exceptions.Proprietario_addons_selection_exceptions;
import interfacce.Exceptions.Specific_exceptions.Proprietario_addons_selection_exceptions.Tipo;
import interfacce.Exceptions.Global_exceptions;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Free_coltivatori extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel Free_coltivatori;
    private JTable tableColtivatori;
    private Controller TheController;
    private String username;
    private String Lottoname;
    private instance_of_lotto_selected iols;
    
    public Free_coltivatori(String username, Controller TheController, String Lottoname) {
        this.TheController = TheController;
        this.username = username;
        this.Lottoname = Lottoname;
        // Imposta le proprietà della finestra
        setResizable(false);
        setTitle("Lista Coltivatori Liberi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prendi la dimensione dello schermo esatta
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Imposta la dimensione della finestra esattamente come lo schermo
        setSize(screenSize.width, screenSize.height);
        setLocation(0, 0); // in alto a sinistra

        Free_coltivatori = new JPanel();
        Free_coltivatori.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(Free_coltivatori);

        Free_coltivatori.setLayout(null);

        // ottieni dati dal controller
        List<Object[]> nomiColture = TheController.Riempi_tab_Proprietario_nome_coltivatore_free(username, Lottoname);

        // Prepara dati per la tabella
        String[] colonne = { "Nome", "Cognome" };
        Object[][] dati = new Object[nomiColture.size()][2];

        // Riempi la matrice dati
        for (int i = 0; i < nomiColture.size(); i++) {
            Object[] riga = nomiColture.get(i);
            dati[i][0] = riga[0];
            dati[i][1] = riga[1];
        }

        // Crea modello tabella non editabile
        DefaultTableModel tableModel = new DefaultTableModel(dati, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Crea la JTable con il modello
        tableColtivatori = new JTable(tableModel);
        tableColtivatori.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        tableColtivatori.setRowHeight(30);

        // Centra il testo nelle colonne
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tableColtivatori.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableColtivatori.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Metti la tabella dentro uno JScrollPane
        JScrollPane scrollPane = new JScrollPane(tableColtivatori);
        scrollPane.setBounds(10, 10, 1502, 756);
        Free_coltivatori.add(scrollPane);

        // Pannello bottoni in basso
        JPanel panelButtons = new JPanel();
        panelButtons.setBounds(10, 760, 1502, 60);
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Free_coltivatori.add(panelButtons);

        JButton torna_indietro = new JButton("Torna indietro");
        torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        torna_indietro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                iols = new instance_of_lotto_selected(username, TheController, Lottoname);
                iols.setVisible(true);
                dispose();
            }
        });
        JButton add_coltivatore = new JButton("aggiungi coltivatore");
        add_coltivatore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableColtivatori.getSelectedRow();
                try {
                    if (selectedRow == -1) {
						throw new Proprietario_addons_selection_exceptions(Tipo.No_one_selected);
                    }
                    String selectedNome = (String) tableColtivatori.getValueAt(selectedRow, 0);
                    String selectedCognome = (String) tableColtivatori.getValueAt(selectedRow, 1);
                } catch ( Proprietario_addons_selection_exceptions ex) {
                    JOptionPane.showMessageDialog(Free_coltivatori, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }    
        });
        add_coltivatore.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        Dimension dimensionBottoni = new Dimension(375, 50);
        torna_indietro.setPreferredSize(dimensionBottoni);
        add_coltivatore.setPreferredSize(dimensionBottoni);

        panelButtons.add(torna_indietro);
        panelButtons.add(add_coltivatore);

    }

    
}
