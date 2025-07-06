package interfacce;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.Controller;

import java.awt.*;
import java.util.List;

import interfacce.Exceptions.Specific_exceptions.Proprietario_addons_selection_exceptions;
import interfacce.Exceptions.Specific_exceptions.Proprietario_addons_selection_exceptions.Tipo;
import interfacce.Exceptions.Global_exceptions;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        // Prendi la dimensione dello schermo esatta
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Imposta la dimensione della finestra esattamente come lo schermo
        setSize(screenSize.width, screenSize.height);
        setLocation(0, 0); // in alto a sinistra

        Free_colture = new JPanel();
        Free_colture.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(Free_colture);

        Free_colture.setLayout(null);

        // usa il listModel d'istanza
        listModel = new DefaultListModel<>();

        // ottieni dati dal controller (corretto il punto e virgola)
        List<Object[]> nomiColture = TheController.Riempi_tab_Proprietario_free_colture(username, Lottoname);

        // riempi il modello
        for (Object[] nome : nomiColture) {
            String nomeColtura = (String) nome[0];
            listModel.addElement(nomeColtura);
        }

        // crea la lista
        listColture = new JList<>(listModel);
        listColture.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        listColture.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // CENTRA TESTO DELLA LISTA
        listColture.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        // dentro scrollpane
        JScrollPane scrollPane = new JScrollPane(listColture);
        scrollPane.setBounds(10, 10, 1502, 756);
        Free_colture.add(scrollPane);

        // Pannello bottoni in basso
        JPanel panelButtons = new JPanel();
        panelButtons.setBounds(10, 760, 1502, 60); // altezza pannello
        // CENTRA ORIZZONTALMENTE I BOTTONI NEL PANEL
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
        JButton add_coltura = new JButton("aggiungi coltura ");
        add_coltura.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String selectedColtura=listColture.getSelectedValue();
        		try {
					if (selectedColtura== null) {
						throw new Global_exceptions("coltura", Global_exceptions.Tipo.empty_field);
					}else if(selectedColtura.equals("Zucchina")) {
						throw new Proprietario_addons_selection_exceptions(Tipo.Culture_arleady_in_the_lotto);
					}
				} catch (Global_exceptions | Proprietario_addons_selection_exceptions e1) {
					
					JOptionPane.showMessageDialog(Free_colture, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				
        	
        	}	
        	
        });
        add_coltura.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        // Imposta dimensioni personalizzate ai bottoni
        Dimension dimensionBottoni = new Dimension(375, 50); // dimensioni bottoni
        torna_indietro.setPreferredSize(dimensionBottoni);
        add_coltura.setPreferredSize(dimensionBottoni);

        panelButtons.add(torna_indietro);
        panelButtons.add(add_coltura);

        // Aggiungi azione per inserire un elemento
        

        // Rimuovi elemento selezionato
       
    }

    
    
}
