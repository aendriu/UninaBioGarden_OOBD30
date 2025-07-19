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
public class Free_lotti extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel Free_lotti;
    private DefaultListModel<String> listModel;
    private JList<String> listLotti;
    private Controller TheController;
    private String username;
    private String Lottoname;
    public Free_lotti(String username, Controller TheController) {
        this.TheController = TheController;
        this.username = username;
        
        // Imposta le proprietà della finestra
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prendi la dimensione dello schermo esatta
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Imposta la dimensione della finestra esattamente come lo schermo
        setSize(screenSize.width, screenSize.height);
        setLocation(0, 0); // in alto a sinistra

        Free_lotti = new JPanel();
       
        setContentPane(Free_lotti);

        Free_lotti.setLayout(null);

        // usa il listModel d'istanza
        listModel = new DefaultListModel<>();

        // ottieni dati dal controller (corretto il punto e virgola)
        List<Object[]> nomiColture = TheController.Riempi_tab_lotti_free();

        // riempi il modello
        for (Object[] nome : nomiColture) {
            String nomeColtura = (String) nome[0];
            listModel.addElement(nomeColtura);
        }

        // crea la lista
        listLotti = new JList<>(listModel);
        listLotti.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        listLotti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // CENTRA TESTO DELLA LISTA
        listLotti.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        // dentro scrollpane
        JScrollPane scrollPane = new JScrollPane(listLotti);
        scrollPane.setBounds(10, 10, 1502, 756);
        Free_lotti.add(scrollPane);

        // Pannello bottoni in basso
        JPanel panelButtons = new JPanel();
        panelButtons.setBounds(10, 760, 1502, 60); // altezza pannello
        // CENTRA ORIZZONTALMENTE I BOTTONI NEL PANEL
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        Free_lotti.add(panelButtons);

        JButton torna_indietro = new JButton("Torna indietro");
        torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        torna_indietro.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        	TheController.OpenProprietarioLoggedIn_closeCaller(username, Free_lotti.this);
        	}
        });
        JButton add_lotto = new JButton("aggiungi lotto");
        add_lotto.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String selectedLotto=listLotti.getSelectedValue();
        		try {
					if (selectedLotto== null) {
						throw new Global_exceptions("Lotto", Global_exceptions.Tipo.empty_field);
					}
					int validat = TheController.AggiungiProprietarioALotto(selectedLotto, username);
					if (validat==-99) {
						throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
					}
					JOptionPane.showMessageDialog(Free_lotti, selectedLotto+ " aggiunto con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
				} catch (Global_exceptions  e1) {
					
					JOptionPane.showMessageDialog(Free_lotti, e1.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
				}
				
        	
        	}	
        	
        });
        add_lotto.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        // Imposta dimensioni personalizzate ai bottoni
        Dimension dimensionBottoni = new Dimension(375, 50); // dimensioni bottoni
        torna_indietro.setPreferredSize(dimensionBottoni);
        add_lotto.setPreferredSize(dimensionBottoni);

        panelButtons.add(torna_indietro);
        panelButtons.add(add_lotto);

        // Aggiungi azione per inserire un elemento
        

        // Rimuovi elemento selezionato
       
    }
    


	

    
    
}
