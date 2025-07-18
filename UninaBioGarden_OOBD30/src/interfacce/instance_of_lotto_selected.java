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
import javax.swing.JOptionPane;
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
import interfacce.Exceptions.Global_exceptions;
import interfacce.Exceptions.Specific_exceptions.Proprietario_addons_selection_exceptions;

public class instance_of_lotto_selected extends JFrame {

    private static final long serialVersionUID = 1L;
    private String lottoName;
    private Controller TheController;
    private String username_proprietario;
    private Image PropImage;
    private JTable table_colture;
    private JTable table_coltivatori;
    private JTable table;
    int decisor_defaulted = 0;

    public instance_of_lotto_selected(String username_proprietario, Controller TheController, String lottoname) {
        this.lottoName = lottoname;
        this.TheController = TheController;
        this.username_proprietario = username_proprietario;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = instance_of_lotto_selected.class.getResource("Images/image_progetto_logo.jpg");
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
        pageProprietario.setLayout(null); // posizionamento libero

        // Pannello titolo
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false); // trasparente
        titlePanel.setBounds(267, 10, 938, 81);
        titlePanel.setLayout(new BorderLayout());
        pageProprietario.add(titlePanel);

        // Label titolo
        JLabel titleLabel = new JLabel(lottoName, JLabel.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        setContentPane(pageProprietario);

        // === Tabella colture ===
        // Modello tabella con due colonne: "Nome coltura" e "Categoria"
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] { "Nome coltura", "IdColtura" }
        ) {
            Class[] columnTypes = new Class[] { String.class, String.class };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Popolo modello con i dati ricevuti dal controller
        List<Object[]> nomi = TheController.Riempi_tab_Proprietario_nome_coltura(username_proprietario, lottoName);
        try {
            if (nomi == null) {
                throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
            } else if (nomi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Non ci sono colture associate a questo lotto.", "Informazione", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        } catch (Global_exceptions e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Object[] nome : nomi) {
            // nome[0] = nome coltura, nome[1] = categoria (seconda colonna)
            model.addRow(new Object[] { nome[0], nome[1] });
        }

        // Creo la JTable usando il modello popolato
        table_colture = new JTable(model);
        table_colture.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table_colture.setRowHeight(30);

        // Nascondo la seconda colonna ("Categoria")
        table_colture.getColumnModel().getColumn(1).setMinWidth(0);
        table_colture.getColumnModel().getColumn(1).setMaxWidth(0);
        table_colture.getColumnModel().getColumn(1).setWidth(0);
        table_colture.getColumnModel().getColumn(1).setPreferredWidth(0);

        // Scroll pane per la tabella colture
        JScrollPane scrollPaneColture = new JScrollPane(table_colture);
        scrollPaneColture.setBounds(103, 210, 462, 226);
        pageProprietario.add(scrollPaneColture);

        // === Tabella coltivatori ===
        table_coltivatori = new JTable();

        List<Object[]> daticoltivatori = TheController.Riempi_tab_Proprietario_nome_coltivatore(username_proprietario, lottoName);
        try {
            if (daticoltivatori.isEmpty()) {
                throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);

            }
        } catch (Global_exceptions e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (daticoltivatori.size() == 0) {
            JOptionPane.showMessageDialog(null, "Non ci sono coltivatori associati a questo lotto.", "Informazione", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int n = daticoltivatori.size();
        Object[][] dati = new Object[n][2];
        for (int i = 0; i < n; i++) {
            dati[i][0] = daticoltivatori.get(i)[0]; // nome
            dati[i][1] = daticoltivatori.get(i)[1]; // cognome
        }
        String[] colonne = { "Nome", "Cognome" };
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

        table_coltivatori.setModel(modelColtivatori);
        table_coltivatori.setRowHeight(30);
        table_coltivatori.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        JScrollPane scrollPaneColtivatori = new JScrollPane(table_coltivatori);
        scrollPaneColtivatori.setBounds(946, 210, 462, 226);
        pageProprietario.add(scrollPaneColtivatori);

        // === Tabella attività ===
        DefaultTableModel model2 = new DefaultTableModel(
            new Object[][] {
                { "Raccolta" },
                { "Semina" },
                { "Irrigazione" },
                { "Applica Pesticida" },
            },
            new String[] { "Nome attività" }
        ) {
            Class<?>[] columnTypes = new Class[] { String.class };
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table_activities = new JTable(model2);
        table_activities.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table_activities.setRowHeight(30);
        JScrollPane scrollPane_activities = new JScrollPane(table_activities);
        scrollPane_activities.setBounds(632, 216, 249, 220);
        pageProprietario.add(scrollPane_activities);

        // === Pulsanti ===

        JButton aggiungi_coltura = new JButton("Aggiungi coltura");
        aggiungi_coltura.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
                int num=TheController.GetNumCultureOfLotto(username_proprietario, lottoname);
                try {
                if (num >= 10) {  // >= per maggiore sicurezza
                    throw new Proprietario_addons_selection_exceptions(
                        Proprietario_addons_selection_exceptions.Tipo.Culture_maximum_number_reached, 
                        lottoname
                    );
                }
        		TheController.OpenFreeColture_closeCaller(username_proprietario, lottoName, instance_of_lotto_selected.this);
            
                } catch (Proprietario_addons_selection_exceptions e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				} 
				
			}
        });
        aggiungi_coltura.setToolTipText("aggiungi al tuo lotto una nuova coltura");
        aggiungi_coltura.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        aggiungi_coltura.setBounds(519, 484, 483, 70);
        pageProprietario.add(aggiungi_coltura);

        JButton aggiungi_coltivatore = new JButton("Aggiungi coltivatore");
        aggiungi_coltivatore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TheController.OpenFreeColtivatori_closeCaller(username_proprietario, lottoName, instance_of_lotto_selected.this);
            }
        });
        aggiungi_coltivatore.setToolTipText("visualizza i coltivatori liberi al momento e aggiungine uno");
        aggiungi_coltivatore.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        aggiungi_coltivatore.setBounds(519, 644, 483, 70);
        pageProprietario.add(aggiungi_coltivatore);

        JButton Torna_indietro = new JButton("Torna indietro");
        Torna_indietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TheController.OpenPropLottiVisualScheme_closeCaller(username_proprietario, decisor_defaulted, instance_of_lotto_selected.this);
            }
        });
        Torna_indietro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TheController.OpenPropLottiVisualScheme_closeCaller(username_proprietario, decisor_defaulted, instance_of_lotto_selected.this);
            }
        });
        Torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Torna_indietro.setBounds(519, 724, 483, 70);
        pageProprietario.add(Torna_indietro);

        JButton tolgi_coltura = new JButton("Rimuovi coltura");
        tolgi_coltura.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table_colture.getSelectedRow();
                try {
                    if (selectedRow == -1) {
                        throw new Global_exceptions("coltura da rimuovere", Global_exceptions.Tipo.empty_field);
                    } else {
                        DefaultTableModel model = (DefaultTableModel) table_colture.getModel();
                        // Prendo il nome dalla colonna 0
                        String nomeColtura = (String) model.getValueAt(selectedRow, 0);
                        int idColtura = Integer.parseInt(model.getValueAt(selectedRow, 1).toString());
                        int validat = TheController.RemoveColturaFromLotto(idColtura);
                        if (validat ==-99) {
                        	throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
                        }
                        JOptionPane.showMessageDialog(null, "la coltura '" + nomeColtura + "' è stata rimossa con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                        model.removeRow(selectedRow);
                    }
                } catch (Global_exceptions e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        tolgi_coltura.setToolTipText("rimuovi una coltura dal tuo lotto");
        tolgi_coltura.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        tolgi_coltura.setBounds(519, 564, 483, 70);
        pageProprietario.add(tolgi_coltura);
    }
}
