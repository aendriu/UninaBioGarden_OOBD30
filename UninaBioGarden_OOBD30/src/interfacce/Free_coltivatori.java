package interfacce;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import interfacce.Exceptions.Specific_exceptions.Proprietario_addons_selection_exceptions;
import interfacce.Exceptions.Specific_exceptions.Proprietario_addons_selection_exceptions.Tipo;
import interfacce.Exceptions.Global_exceptions;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Free_coltivatori extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel Free_coltivatori;
    private JTable tableColtivatori;
    private Controller TheController;
    private String username;
    private String Lottoname;

    public Free_coltivatori(String username, Controller TheController, String Lottoname) {
        this.TheController = TheController;
        this.username = username;
        this.Lottoname = Lottoname;

        setResizable(false);
        setTitle("Lista Coltivatori Liberi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocation(0, 0);

        Free_coltivatori = new JPanel();
        Free_coltivatori.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(Free_coltivatori);
        Free_coltivatori.setLayout(null);

        List<Object[]> nomiColtivatori = TheController.Riempi_tab_Proprietario_nome_coltivatore_free(username, Lottoname);
        try {
            if (nomiColtivatori == null) {
                throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
            } else if (nomiColtivatori.isEmpty()) {
                JOptionPane.showMessageDialog(Free_coltivatori, "Non ci sono coltivatori liberi al momento", "Informazione", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Global_exceptions e) {
            JOptionPane.showMessageDialog(Free_coltivatori, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] colonne = {"Nome", "Cognome", "SecretCF"};
        Object[][] dati = new Object[nomiColtivatori.size()][3];

        for (int i = 0; i < nomiColtivatori.size(); i++) {
            Object[] riga = nomiColtivatori.get(i);
            dati[i][0] = riga[0];
            dati[i][1] = riga[1];
            dati[i][2] = riga[2];
        }

        DefaultTableModel tableModel = new DefaultTableModel(dati, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableColtivatori = new JTable(tableModel);
        tableColtivatori.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        tableColtivatori.setRowHeight(30);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tableColtivatori.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableColtivatori.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Nascondi la colonna CF (SecretCF)
        tableColtivatori.removeColumn(tableColtivatori.getColumnModel().getColumn(2));

        JScrollPane scrollPane = new JScrollPane(tableColtivatori);
        scrollPane.setBounds(10, 10, 1502, 756);
        Free_coltivatori.add(scrollPane);

        JPanel panelButtons = new JPanel();
        panelButtons.setBounds(10, 760, 1502, 60);
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Free_coltivatori.add(panelButtons);

        JButton torna_indietro = new JButton("Torna indietro");
        torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        torna_indietro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TheController.OpenIstanceOfLottoSelected_closeCaller(username, Free_coltivatori.this, Lottoname);
            }
        });

        JButton add_coltivatore = new JButton("Aggiungi coltivatore");
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
                    // Per il CF si prende direttamente dal modello
                    int modelRow = tableColtivatori.convertRowIndexToModel(selectedRow);
                    String selectedCF = (String) tableModel.getValueAt(modelRow, 2);
                    boolean validat= TheController.Aggiungi_coltivatore(username, Lottoname, selectedCF);
                    try {
						if (!validat) {
							throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
						}
					} catch (Global_exceptions e1) {
						JOptionPane.showMessageDialog(Free_coltivatori, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
					}

					if (validat) {
						JOptionPane.showMessageDialog(Free_coltivatori, "Coltivatore " + selectedNome + " " + selectedCognome + " aggiunto con successo al lotto " + Lottoname, "Successo", JOptionPane.INFORMATION_MESSAGE);
						TheController.OpenIstanceOfLottoSelected_closeCaller(username, Free_coltivatori.this, Lottoname);
					}
                } catch (Proprietario_addons_selection_exceptions ex) {
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
