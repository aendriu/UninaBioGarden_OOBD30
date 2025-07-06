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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import interfacce.Exceptions.Specific_exceptions.Coltivatore_attività_table_exceptions;
import controller.Controller;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

public class Coltivatore_attività_responsabili extends JFrame {

    private static final long serialVersionUID = 1L;
    private Image coltivatoreImage;
    private String username_colt;
    private Controller TheController;
    private JTable table;

    public Coltivatore_attività_responsabili(String username_colt, Controller TheController) {
        setResizable(false);
        this.username_colt = username_colt;
        this.TheController = TheController;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dimensione schermo e fullscreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = Coltivatore_attività_responsabili.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            coltivatoreImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }

        // Pannello personalizzato con sfondo
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

        // crea tabella
        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                int modelRow = table.convertRowIndexToModel(selectedRow);
                String Lotto_selected = (String) table.getModel().getValueAt(modelRow, 0);
                String Attività_selected = (String) table.getModel().getValueAt(modelRow, 1);
                String Stato_selected = (String) table.getModel().getValueAt(modelRow, 3);

                try {
                    if (Stato_selected.equals("completata")) {
                        throw new Coltivatore_attività_table_exceptions(
                                Coltivatore_attività_table_exceptions.Tipo.activity_arleady_completed
                        );
                    }
                } catch (Coltivatore_attività_table_exceptions e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String inputTXT = JOptionPane.showInputDialog(
                    "Quante ore sono state dedicate all'attività " + Attività_selected + " del lotto " + Lotto_selected + "? (oggi)"
                );
                int time_spent = 0;
                try {
                    if (inputTXT != null && !inputTXT.trim().isEmpty()) {
                        time_spent = Integer.parseInt(inputTXT);
                        if (time_spent < 0) {
                            throw new Coltivatore_attività_table_exceptions(
                                    Coltivatore_attività_table_exceptions.Tipo.illegal_number_input
                            );
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Inserisci un numero valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                } catch (Coltivatore_attività_table_exceptions e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
                //occhio a null per input txt
                // TODO: passare il valore time_spent al controller per aggiornare la percentuale
            }
        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setToolTipText("clicca una riga per aggiornare la percentuale completamento");

        // prendo i dati
        List<Object[]> righe = TheController.Riempi_tab_attività_responsabili(username_colt);
        righe.sort((a, b) -> ((String) a[0]).compareToIgnoreCase((String) b[0]));

        String[] colonne = {
            "Nome Lotto", "Nome Attività", "Coltura", "Stato", "Percentuale Completamento"
        };

        Object[][] dati = new Object[righe.size()][colonne.length];
        for (int i = 0; i < righe.size(); i++) {
            Object[] row = righe.get(i);
            Object[] formattedRow = new Object[colonne.length];
            for (int j = 0; j < colonne.length; j++) {
                if (j == 4) {
                    formattedRow[j] = row[j];  // percentuale int
                } else {
                    formattedRow[j] = row[j];
                }
            }
            dati[i] = formattedRow;
        }

        DefaultTableModel model = new DefaultTableModel(
            dati, colonne
        ) {
            Class[] columnTypes = new Class[]{
                String.class, String.class, String.class, String.class, Integer.class
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
        table.setModel(model);

        // renderer progress bar
        table.getColumnModel().getColumn(4).setCellRenderer(new javax.swing.table.TableCellRenderer() {
            private final javax.swing.JProgressBar bar = new javax.swing.JProgressBar(0, 100);
            {
                bar.setStringPainted(true);
                bar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            }
            @Override
            public java.awt.Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
            ) {
                int progress = 0;
                if (value instanceof Integer) {
                    progress = (Integer) value;
                }
                bar.setValue(progress);
                bar.setString(progress + "%");
                return bar;
            }
        });

        table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        table.setRowHeight(30);

        scrollPane.setViewportView(table);

        JButton Torna_indietro = new JButton("Torna Indietro");
        Torna_indietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               TheController.OpenPageColtivatore_closeCaller(username_colt, Coltivatore_attività_responsabili.this);
            }
        });
        Torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Torna_indietro.setBounds(645, screenSize.height - 80, 210, 43);
        Attività_panel.add(Torna_indietro);
    }
}
