package interfacce;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import interfacce.Exceptions.Global_exceptions;
import controller.Controller;

public class Free_colture extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel Free_colture;
    private DefaultListModel<String> listModel;
    private JList<String> listColture;
    private Controller TheController;
    private String username;
    private String Lottoname;

    public Free_colture(String username, Controller TheController, String Lottoname) {
        this.TheController = TheController;
        this.username = username;
        this.Lottoname = Lottoname;

        // Imposta le proprietà della finestra
        setResizable(false);
        setTitle("Lista Colture Libere");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocation(0, 0);

        Free_colture = new JPanel();
        setContentPane(Free_colture);
        Free_colture.setLayout(null);

        listModel = new DefaultListModel<>();

        // Lista statica con 5 righe e 2 colonne
        List<Object[]> nomiColture = Arrays.asList(
            new Object[]{"Pomodoro", "60 giorni"},
            new Object[]{"Mais", "30 giorni"},
            new Object[]{"Basilico", "50 giorni"},
            new Object[]{"Fragole", "70 giorni"},
            new Object[]{"Zucchina", "90 giorni"}
        );

        // Riempimento listModel con entrambe le colonne
        for (Object[] nome : nomiColture) {
            String nomeColtura = nome[0] + " - " + nome[1];
            listModel.addElement(nomeColtura);
        }

        listColture = new JList<>(listModel);
        listColture.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        listColture.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listColture.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(listColture);
        scrollPane.setBounds(10, 10, 1502, 756);
        Free_colture.add(scrollPane);

        JPanel panelButtons = new JPanel();
        panelButtons.setBounds(10, 760, 1502, 60);
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Free_colture.add(panelButtons);

        JButton torna_indietro = new JButton("Torna indietro");
        torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        torna_indietro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TheController.OpenIstanceOfLottoSelected_closeCaller(username, Free_colture.this, Lottoname);
            }
        });

        JButton add_coltura = new JButton("Aggiungi coltura");
        add_coltura.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String selectedColtura = listColture.getSelectedValue();
                try {
                    if (selectedColtura == null) {
                        throw new Global_exceptions("coltura", Global_exceptions.Tipo.empty_field);
                    }
                    String nomeColtura = selectedColtura.split(" - ")[0].trim();
   				 int validat=TheController.AddColturaToLotto(username, Lottoname, nomeColtura);
   				 if (validat==-99) {
   					 throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
   				 }
   				 JOptionPane.showMessageDialog(Free_colture, "Coltura aggiunta con successo al lotto '" + Lottoname + "'.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Coltura selezionata: " + selectedColtura);
                } catch (Global_exceptions e1) {
                    JOptionPane.showMessageDialog(Free_colture, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add_coltura.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        Dimension dimensionBottoni = new Dimension(375, 50);
        torna_indietro.setPreferredSize(dimensionBottoni);
        add_coltura.setPreferredSize(dimensionBottoni);

        panelButtons.add(torna_indietro);
        panelButtons.add(add_coltura);
    }
}

