package interfacce;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;
import interfacce.Exceptions.Global_exceptions;
import interfacce.Exceptions.Specific_exceptions.Coltivatore_attività_table_exceptions;
import controller.Controller;

public class Coltivatore_attività_responsabili extends JFrame {

    private static final long serialVersionUID = 1L;
    private Image coltivatoreImage;
    private String username_colt;
    private Controller TheController;
    private JTable activities_table;
    private TableColumnModel columnModelBackup;

    public Coltivatore_attività_responsabili(String username_colt, Controller TheController) {
        setResizable(false);
        this.username_colt = username_colt;
        this.TheController = TheController;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dimensione schermo
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Immagine
        URL imageUrl = Coltivatore_attività_responsabili.class.getResource("Images/image_progetto_logo.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            coltivatoreImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }

        // Sfondo
        JPanel Attività_panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (coltivatoreImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.drawImage(coltivatoreImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        Attività_panel.setLayout(null);
        setContentPane(Attività_panel);

        int spazioBottoni = 100;

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, screenSize.width, screenSize.height - spazioBottoni);
        Attività_panel.add(scrollPane);

        activities_table = new JTable();
        activities_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        activities_table.setToolTipText("Clicca una riga per aggiornare la percentuale completamento");

        activities_table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = activities_table.getSelectedRow();
                int modelRow = activities_table.convertRowIndexToModel(selectedRow);

                String lottoName = (String) activities_table.getModel().getValueAt(modelRow, 0);
                String attivitaName = (String) activities_table.getModel().getValueAt(modelRow, 1);
                String stato = (String) activities_table.getModel().getValueAt(modelRow, 3);

                try {
                    if (stato.equalsIgnoreCase("completata")) {
                        throw new Coltivatore_attività_table_exceptions(
                            Coltivatore_attività_table_exceptions.Tipo.activity_arleady_completed
                        );
                    }
                } catch (Coltivatore_attività_table_exceptions ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String inputTXT = JOptionPane.showInputDialog(
                    "Quante ore sono state dedicate all'attività " + attivitaName + " del lotto " + lottoName + "? (oggi)"
                );

                try {
                    if (inputTXT != null && !inputTXT.trim().isEmpty()) {
                        int time_spent = Integer.parseInt(inputTXT);
                        if (time_spent < 0 || time_spent > 23) {
                            throw new Coltivatore_attività_table_exceptions(
                                Coltivatore_attività_table_exceptions.Tipo.illegal_number_input
                            );
                        }
                        LocalTime localTime = LocalTime.of(time_spent, 0, 0);
                        Time time = Time.valueOf(localTime);
                        boolean validat = TheController.aggiorna_tempo_lavorato(username_colt, lottoName, attivitaName, time);
                        if (validat) {
                            JOptionPane.showMessageDialog(
                                null, "Tempo aggiornato con successo per l'attività " + attivitaName + " del lotto " + lottoName,
                                "Aggiornamento Completato", JOptionPane.INFORMATION_MESSAGE
                            );
                            reloadTableData();
                        } else {
                            throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);    
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Inserisci un numero valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                } catch (Coltivatore_attività_table_exceptions | Global_exceptions ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        scrollPane.setViewportView(activities_table);
        loadTableData();

        activities_table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        activities_table.setRowHeight(30);

        JButton Torna_indietro = new JButton("Torna Indietro");
        Torna_indietro.addActionListener(e -> {
            TheController.OpenPageColtivatore_closeCaller(username_colt, Coltivatore_attività_responsabili.this);
        });
        Torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Torna_indietro.setBounds(0, 774, 1522, 53);
        Attività_panel.add(Torna_indietro);
    }

    private void loadTableData() {
        List<Object[]> righe = TheController.Riempi_tab_attività_responsabili(username_colt);
        if (righe == null || righe.isEmpty()) {
            JOptionPane.showMessageDialog(
                null, "Non ci sono attività responsabili per il coltivatore " + username_colt,
                "Attività Responsabili", JOptionPane.INFORMATION_MESSAGE
            );
            TheController.OpenPageColtivatore_closeCaller(username_colt, Coltivatore_attività_responsabili.this);
            return;
        }

        righe.sort(Comparator.comparing(a -> ((String) a[0]))); // Ordina per nome lotto

        String[] colonne = {
            "Nome Lotto", "Nome Attività", "Coltura", "Stato", "Percentuale Completamento"
        };

        Object[][] dati = new Object[righe.size()][colonne.length];
        for (int i = 0; i < righe.size(); i++) {
            dati[i] = righe.get(i);
        }

        DefaultTableModel model = new DefaultTableModel(dati, colonne) {
            Class<?>[] columnTypes = new Class[]{
                String.class, String.class, String.class, String.class, Integer.class, Integer.class, Integer.class
            };
            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        activities_table.setModel(model);

        if (columnModelBackup != null) {
            activities_table.setColumnModel(columnModelBackup);
        } else {
            columnModelBackup = activities_table.getColumnModel();
        }

        // Progress bar sulla colonna percentuale
        activities_table.getColumnModel().getColumn(4).setCellRenderer(new TableCellRenderer() {
            private final JProgressBar bar = new JProgressBar(0, 100);
            {
                bar.setStringPainted(true);
                bar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            }

            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
            ) {
                int progress = (value instanceof Integer) ? (Integer) value : 0;
                bar.setValue(progress);
                bar.setString(progress + "%");
                return bar;
            }
        });

    
    }

    private void reloadTableData() {
        loadTableData();
    }

    
}
