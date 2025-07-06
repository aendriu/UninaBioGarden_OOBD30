package interfacce;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controller.Controller;
import java.util.List;
import interfacce.Exceptions.Specific_exceptions.Prop_Project_exceptions;
import interfacce.Exceptions.Global_exceptions;

public class Progetti_creation_scheme extends JFrame {

    private static final long serialVersionUID = 1L;
    private String lottoName;
    private Controller TheController;
    private String username_proprietario;
    private Image PropImage;
    private JTable table_colture;
    private JTable table_coltivatori;
    private JTable table_activities;
    private JTextField textFieldDurata;
    private JTextField textFieldQuantita;
    private JLabel lblQuantita;
    private String lottoname;
    private Set<List<String>> attivitàSelezionate = new HashSet<>();

    public Progetti_creation_scheme(String username_proprietario, Controller TheController, String Lotto_selected) {
        this.TheController = TheController;
        this.username_proprietario = username_proprietario;
        this.lottoName = Lotto_selected;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        URL imageUrl = Progetti_creation_scheme.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            PropImage = new ImageIcon(imageUrl).getImage();
        }

        JPanel page = new JPanel() {
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
        page.setLayout(null);

        JLabel lblTitolo = new JLabel("Crea un nuovo progetto", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Times New Roman", Font.BOLD, 40));
        lblTitolo.setBounds(267, 10, 938, 81);
        page.add(lblTitolo);

        // tabella colture
        table_colture = new JTable();
        DefaultTableModel modelColture = new DefaultTableModel(new Object[][]{}, new String[]{"Nome"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table_colture.setModel(modelColture);
        table_colture.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_colture.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table_colture.setRowHeight(30);
        List<Object[]> nomi = TheController.Riempi_tab_Proprietario_nome_coltura(username_proprietario, lottoName);
        for (Object[] nome : nomi) modelColture.addRow(new Object[]{nome[0]});
        JScrollPane scrollColture = new JScrollPane(table_colture);
        scrollColture.setBounds(631, 175, 462, 226);
        page.add(scrollColture);

        // tabella coltivatori
        table_coltivatori = new JTable();
        DefaultTableModel modelColtivatori = new DefaultTableModel(new Object[][]{}, new String[]{"Nome", "Cognome"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table_coltivatori.setModel(modelColtivatori);
        table_coltivatori.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_coltivatori.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table_coltivatori.setRowHeight(30);
        List<Object[]> daticoltivatori = TheController.Riempi_tab_Proprietario_nome_coltivatore(username_proprietario, lottoName);
        for (Object[] c : daticoltivatori) modelColtivatori.addRow(new Object[]{c[0], c[1]});
        JScrollPane scrollColtivatori = new JScrollPane(table_coltivatori);
        scrollColtivatori.setBounds(103, 175, 462, 226);
        page.add(scrollColtivatori);

        // tabella attività
        DefaultTableModel modelActivities = new DefaultTableModel(
                new Object[][]{
                    {"Raccolta"}, {"Semina"}, {"Irrigazione"}, {"Concimazione"},
                    {"Controllo parassiti"}, {"Potatura"}, {"Vendita"}, {"Monitoraggio crescita"}
                },
                new String[]{"Nome attività"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table_activities = new JTable(modelActivities);
        table_activities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table_activities.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table_activities.setRowHeight(30);
        JScrollPane scrollActivities = new JScrollPane(table_activities);
        scrollActivities.setBounds(1169, 175, 249, 220);
        page.add(scrollActivities);

        textFieldDurata = new JTextField();
        textFieldDurata.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldDurata.setBounds(499, 467, 483, 37);
        page.add(textFieldDurata);

        JLabel lblDurata = new JLabel("Inserisci durata attività (in giorni e solo numeri)", SwingConstants.CENTER);
        lblDurata.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblDurata.setBounds(499, 435, 466, 37);
        page.add(lblDurata);

        textFieldQuantita = new JTextField();
        textFieldQuantita.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFieldQuantita.setBounds(499, 539, 483, 37);
        textFieldQuantita.setVisible(false);
        page.add(textFieldQuantita);

        lblQuantita = new JLabel("Inserisci quantità da raccogliere", SwingConstants.CENTER);
        lblQuantita.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblQuantita.setBounds(499, 514, 466, 30);
        lblQuantita.setVisible(false);
        page.add(lblQuantita);

        table_activities.getSelectionModel().addListSelectionListener(e -> {
            int row = table_activities.getSelectedRow();
            if (row != -1) {
                String attività = table_activities.getValueAt(row, 0).toString();
                if (attività.equalsIgnoreCase("Raccolta")) {
                    textFieldQuantita.setVisible(true);
                    lblQuantita.setVisible(true);
                } else {
                    textFieldQuantita.setVisible(false);
                    lblQuantita.setVisible(false);
                }
            }
        });

        JButton aggiungi = new JButton("aggiungi attività al progetto");
        aggiungi.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        aggiungi.setBounds(499, 581, 483, 60);
        aggiungi.addActionListener(e -> {
            List<String> entry = new ArrayList<>();
            try {
                int rColtivatore = table_coltivatori.getSelectedRow();
                int rColtura = table_colture.getSelectedRow();
                int rAttività = table_activities.getSelectedRow();
                String durata = textFieldDurata.getText();
                textFieldDurata.setBackground(Color.WHITE);
                textFieldQuantita.setBackground(Color.WHITE);
                if (rColtivatore == -1) {
                    throw new Global_exceptions("Coltivatore", Global_exceptions.Tipo.empty_field);
                }
                if (rColtura == -1) {
                    throw new Global_exceptions("Coltura", Global_exceptions.Tipo.empty_field);
                }
                if (rAttività == -1) {
                    throw new Global_exceptions("Attività", Global_exceptions.Tipo.empty_field);
                }
                if (durata == null || durata.isEmpty()) {
                    textFieldDurata.setBackground(Color.RED);
                    throw new Global_exceptions("Durata", Global_exceptions.Tipo.empty_field);
                }
                int valoreDurata;
                try {
                    valoreDurata = Integer.parseInt(durata);
                } catch (NumberFormatException nfe) {
                    textFieldDurata.setBackground(Color.RED);
                    throw new Global_exceptions("Durata", Global_exceptions.Tipo.format_mismatch);
                }
                if (valoreDurata <= 0) {
                    textFieldDurata.setBackground(Color.RED);
                    throw new Global_exceptions("Durata", Global_exceptions.Tipo.Type_mismatch);
                }

                String nome = table_coltivatori.getValueAt(rColtivatore, 0).toString();
                String cognome = table_coltivatori.getValueAt(rColtivatore, 1).toString();
                String coltura = table_colture.getValueAt(rColtura, 0).toString();
                String attività = table_activities.getValueAt(rAttività, 0).toString();

                entry.add(nome + " " + cognome);
                entry.add(coltura);
                entry.add(attività);
                entry.add(durata);

                if (attività.equalsIgnoreCase("Raccolta")) {
                    String quantita = textFieldQuantita.getText();
                    if (quantita == null || quantita.trim().isEmpty()) {
                        textFieldQuantita.setBackground(Color.RED);
                        throw new Global_exceptions("Quantità", Global_exceptions.Tipo.empty_field);
                    }
                    int valoreQuantita;
                    try {
                        valoreQuantita = Integer.parseInt(quantita.trim());
                    } catch (NumberFormatException nfe) {
                        textFieldQuantita.setBackground(Color.RED);
                        throw new Global_exceptions("Quantità", Global_exceptions.Tipo.format_mismatch);
                    }
                    if (valoreQuantita <= 0) {
                        textFieldQuantita.setBackground(Color.RED);
                        throw new Global_exceptions("Quantità", Global_exceptions.Tipo.Type_mismatch);
                    }
                    entry.add(quantita);
                }

                if (attivitàSelezionate.contains(entry)) {
                    throw new Prop_Project_exceptions(Prop_Project_exceptions.Tipo.adding_same_activity_twice);
                }

                attivitàSelezionate.add(entry);
                System.out.println("Aggiunta: " + entry);
                textFieldDurata.setBackground(Color.WHITE);
                textFieldQuantita.setBackground(Color.WHITE);
                JOptionPane.showMessageDialog(null, "Attività aggiunta con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);

                table_coltivatori.clearSelection();
                table_colture.clearSelection();
                table_activities.clearSelection();
                textFieldDurata.setText("");
                textFieldQuantita.setText("");

            } catch (Prop_Project_exceptions ex) {
                if (ex.getTipo() == Prop_Project_exceptions.Tipo.adding_same_activity_twice) {
                    JOptionPane.showMessageDialog(null, "Attività già presente nel progetto.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Errore di progetto: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    attivitàSelezionate.remove(entry);
                }
            } catch (Global_exceptions ex) {
                JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                attivitàSelezionate.remove(entry);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Errore imprevisto: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                attivitàSelezionate.remove(entry);
            }
        });
        page.add(aggiungi);

        JButton indietro = new JButton("Torna indietro");
        indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        indietro.setBounds(499, 721, 483, 60);
        indietro.addActionListener(e -> {
            if (!attivitàSelezionate.isEmpty()) {
                int scelta = JOptionPane.showConfirmDialog(
                        null,
                        "Ci sono attività già aggiunte al progetto.\nSe torni indietro il progetto verrà cancellato.\nVuoi continuare?",
                        "Attenzione",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (scelta != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            TheController.OpenProprietarioLoggedIn_closeCaller(username_proprietario, Progetti_creation_scheme.this);
        });
        page.add(indietro);

        JButton Finalize = new JButton("Concludi e finalizza");
        Finalize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (attivitàSelezionate.isEmpty()) {
                    try {
                        throw new Prop_Project_exceptions(Prop_Project_exceptions.Tipo.no_new_arguments_of_project_added);
                    } catch (Prop_Project_exceptions ex) {
                        JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                String input = null;
                while (true) {
                    try {
                        input = JOptionPane.showInputDialog(null, "Inserisci il nome del progetto:", "Nome Progetto", JOptionPane.INFORMATION_MESSAGE);
                        if (input == null) {
                            JOptionPane.showMessageDialog(null, "Devi inserire un nome per procedere.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                            continue;
                        }
                        input = input.trim();
                        if (input.isEmpty()) {
                            throw new Global_exceptions("Nome progetto", Global_exceptions.Tipo.empty_field);
                        }
                        break;
                    } catch (Global_exceptions ex) {
                        JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
                TheController.OpenProjectFinalizeAndFinalAdjustments_closeCaller(username_proprietario, lottoName, input, attivitàSelezionate, Progetti_creation_scheme.this);
            }
        });
        Finalize.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Finalize.setBounds(499, 651, 483, 60);
        page.add(Finalize);

        setContentPane(page);
    }
}
