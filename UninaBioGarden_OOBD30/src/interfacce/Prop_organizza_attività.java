
package interfacce;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import controller.Controller;
import interfacce.Exceptions.Global_exceptions;
import interfacce.Exceptions.Specific_exceptions.Proprietario_addons_selection_exceptions;
import interfacce.Exceptions.Specific_exceptions.Proprietario_addons_selection_exceptions.Tipo;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Prop_organizza_attività extends JFrame {

    private static final long serialVersionUID = 1L;
    private Controller TheController;
    private String username;
    private Image PropImage;
    private boolean First_time_getting_data_lotto = true;
    private boolean First_time_getting_data_attività = true;
    private boolean First_time_getting_data_coltura = true;
    private boolean First_time_getting_data_nome_colt = true;
    private boolean First_time_getting_data_cognome = true;
    String[] dati_selected = new String[7];
    //private String lotto
    // campo 1
    private JTextField Lotto;
    private JScrollPane popupScroll1;
    private JList<String> Lotto_list;

    // campo 2
    private JTextField Attività;
    private JScrollPane popupScroll2;
    private JList<String> Lista_attività;

    // campo 3
    private JTextField coltura;
    private JScrollPane popupScroll3;
    private JList<String> Coltura_list;

    // campo 4
    private JTextField Nome_colt;
    private JScrollPane popupScroll4;
    private JList<String> Nome_colt_list;
    // campo 5
    private JTextField cognome;
    private JScrollPane popupScroll5;
    private JList<String> cognome_list;
    private JTextField data_inizio;
    private JTextField data_fine;
    

    public Prop_organizza_attività(String username, Controller TheController) {
        setResizable(false);
        this.TheController = TheController;
        this.username = username;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = Prop_organizza_attività.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            PropImage = new ImageIcon(imageUrl).getImage();
        }

        JPanel pageProp = new JPanel() {
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
        pageProp.setLayout(null);
        setContentPane(pageProp);

        JLabel lblTitle = new JLabel("Organizza una attività");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        lblTitle.setBounds(409, 10, 704, 92);
        pageProp.add(lblTitle);

        // === CAMPO 1 ===
     // === CAMPO 1 ===
        Lotto = new JTextField();
        Lotto.setEditable(false);
        Lotto.setFocusable(false);
        Lotto.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Lotto.setBounds(31, 231, 300, 40);
        pageProp.add(Lotto);

        Lotto_list = new JList<>();
        Lotto_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Lotto_list.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
                String sel = Lotto_list.getSelectedValue();
                if (sel != null) {
                    Lotto.setText(sel);
                    popupScroll1.setVisible(false);
                }
            }
        });
        popupScroll1 = new JScrollPane(Lotto_list);
        popupScroll1.setBounds(Lotto.getX(), Lotto.getY() + Lotto.getHeight(), 300, 100);
        popupScroll1.setVisible(false);
        pageProp.add(popupScroll1);
       
        Lotto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(First_time_getting_data_lotto==true) {
            	Lotto_list.setListData(TheController.getData1(username));
            	First_time_getting_data_lotto = false;
                }
            	popupScroll1.setVisible(true);
            }
        });
       

        // === CAMPO 2 ===
        Attività = new JTextField();
        Attività.setEditable(false);
        Attività.setFocusable(false);
        Attività.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Attività.setBounds(794, 348, 300, 40);
        pageProp.add(Attività);

        Lista_attività = new JList<>();
        Lista_attività.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Lista_attività.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String sel = Lista_attività.getSelectedValue();
                if (sel != null) {
                    Attività.setText(sel);
                    popupScroll2.setVisible(false);
                }
            }
        });
        popupScroll2 = new JScrollPane(Lista_attività);
        popupScroll2.setBounds(Attività.getX(), Attività.getY() + Attività.getHeight(), 300, 100);
        popupScroll2.setVisible(false);
        pageProp.add(popupScroll2);

        Attività.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(First_time_getting_data_attività==true) {
            	Lista_attività.setListData(TheController.getData2(username));
                First_time_getting_data_attività = false;
				}
            	popupScroll2.setVisible(true);
            }
        });
       

        // === CAMPO 3 ===
        coltura = new JTextField();
        coltura.setEditable(false);
        coltura.setFocusable(false);
        coltura.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        coltura.setBounds(440, 231, 300, 40);
        pageProp.add(coltura);

        Coltura_list = new JList<>();
        Coltura_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Coltura_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String sel = Coltura_list.getSelectedValue();
                if (sel != null) {
                    coltura.setText(sel);
                    popupScroll3.setVisible(false);
                }
            }
        });
        popupScroll3 = new JScrollPane(Coltura_list);
        popupScroll3.setBounds(coltura.getX(), coltura.getY() + coltura.getHeight(), 300, 100);
        popupScroll3.setVisible(false);
        pageProp.add(popupScroll3);

        coltura.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(First_time_getting_data_coltura==true) {
            	Coltura_list.setListData(TheController.getData3(username));
                First_time_getting_data_coltura = false;
                				}
            	popupScroll3.setVisible(true);
            }
        });
        

        // === CAMPO 4 ===
        Nome_colt = new JTextField();
        Nome_colt.setEditable(false);
        Nome_colt.setFocusable(false);
        Nome_colt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Nome_colt.setBounds(440, 456, 300, 40);
        pageProp.add(Nome_colt);

        Nome_colt_list = new JList<>();
        Nome_colt_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Nome_colt_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String sel = Nome_colt_list.getSelectedValue();
                if (sel != null) {
                    Nome_colt.setText(sel);
                    popupScroll4.setVisible(false);
                }
            }
        });
        popupScroll4 = new JScrollPane(Nome_colt_list);
        popupScroll4.setBounds(Nome_colt.getX(), Nome_colt.getY() + Nome_colt.getHeight(), 300, 100);
        popupScroll4.setVisible(false);
        pageProp.add(popupScroll4);

        Nome_colt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(First_time_getting_data_nome_colt==true) {
            	Nome_colt_list.setListData(TheController.getData4(username));
                First_time_getting_data_nome_colt = false;
				}
            	popupScroll4.setVisible(true);
            }
        });
        

     // === CAMPO 5 ===
        cognome = new JTextField();
        cognome.setEditable(false);
        cognome.setFocusable(false);
        cognome.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        cognome.setBounds(31, 456, 300, 40);
        pageProp.add(cognome);

        cognome_list = new JList<>();
        cognome_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cognome_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String sel = cognome_list.getSelectedValue();
                if (sel != null) {
                    cognome.setText(sel);
                    popupScroll5.setVisible(false);
                }
            }
        });
        popupScroll5 = new JScrollPane(cognome_list);
        popupScroll5.setBounds(cognome.getX(), cognome.getY() + cognome.getHeight(), 300, 100);
        popupScroll5.setVisible(false);
        pageProp.add(popupScroll5);

        cognome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(First_time_getting_data_cognome==true) {
            	cognome_list.setListData(TheController.getData5(username));
                First_time_getting_data_cognome = false;
                }
            	popupScroll5.setVisible(true);
            }
        });
       
    data_inizio = new JTextField("DD/MM/YYYY");
    data_inizio.setForeground(new Color(192, 192, 192));
    data_inizio.setFont(new Font("Times New Roman", Font.ITALIC, 20));
    data_inizio.setBounds(1150, 305, 300, 40);
    pageProp.add(data_inizio);

    data_inizio.addMouseListener(new MouseAdapter() {
        private boolean firstClick = true;
        @Override
        public void mouseClicked(MouseEvent e) {
            if (firstClick) {
                data_inizio.setText("");
                data_inizio.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                data_inizio.setForeground(Color.BLACK);
                firstClick = false;
            }
        }
    });
    
    data_fine = new JTextField("DD/MM/YYYY");
    data_fine.setForeground(new Color(192, 192, 192));
    data_fine.setFont(new Font("Times New Roman", Font.ITALIC, 20));
    data_fine.setBounds(1150, 402, 300, 40);
    pageProp.add(data_fine);

    data_fine.addMouseListener(new MouseAdapter() {
        private boolean firstClick = true;
        @Override
        public void mouseClicked(MouseEvent e) {
            if (firstClick) {
                data_fine.setText("");
                data_fine.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                data_fine.setForeground(Color.BLACK);
                firstClick = false;
            }
        }
    });
   
    JButton Torna_indietro = new JButton("Torna indietro");
    Torna_indietro.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    	}
    });
    Torna_indietro.addMouseListener(new MouseAdapter() {
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		if (!Lotto.getText().isEmpty() ||
    			!Nome_colt.getText().isEmpty() ||
    			!cognome.getText().isEmpty() ||
    			!Attività.getText().isEmpty() ||
    			!coltura.getText().isEmpty() ||
    			!data_inizio.getText().equals("DD/MM/YYYY") ||
    			 !data_fine.getText().equals("DD/MM/YYYY")) {
    			Object[] options = {"Conferma", "Annulla"};
    			int risposta = JOptionPane.showOptionDialog(
    			    null,
    			    "L'azione cancellerà tutti i campi selezionati, procedere?",
    			    "Errore",
    			    JOptionPane.YES_NO_OPTION,
    			    JOptionPane.WARNING_MESSAGE,
    			    null,
    			    options,
    			    options[1]  // default su "Annulla"
    			);

    			if (risposta == JOptionPane.YES_OPTION) {
    				Lotto.setBackground(Color.WHITE);
    	            Nome_colt.setBackground(Color.WHITE);
    	            cognome.setBackground(Color.WHITE);
    	            Attività.setBackground(Color.WHITE);
    	            coltura.setBackground(Color.WHITE);
    	            data_inizio.setBackground(Color.WHITE);
    	            data_fine.setBackground(Color.WHITE);
    	            Lotto.setText("");
    				Attività.setText("");
    				coltura.setText("");
    				Nome_colt.setText("");
    				cognome.setText("");
    				data_inizio.setText("DD/MM/YYYY");
    				data_fine.setText("DD/MM/YYYY");
    				Lotto_list.clearSelection();
    				Lista_attività.clearSelection();
    				Coltura_list.clearSelection();
    				Nome_colt_list.clearSelection();
    				cognome_list.clearSelection();
    				TheController.OpenProprietarioLoggedIn_closeCaller(username, Prop_organizza_attività.this);
    			} else if  (risposta == JOptionPane.NO_OPTION) {
    				return; // Annulla l'azione
    			}
    		}else {
    			TheController.OpenProprietarioLoggedIn_closeCaller(username, Prop_organizza_attività.this);
			}
    	
    	}
    });
    Torna_indietro.setPreferredSize(new Dimension(200, 70));
    Torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    Torna_indietro.setBounds(49, 733, 385, 70);
    pageProp.add(Torna_indietro);
    
    JButton Torna_indietro_1 = new JButton("Cancella tutto");
    Torna_indietro_1.addMouseListener(new MouseAdapter() {
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		if (!Lotto.getText().isEmpty() ||
    			    !Nome_colt.getText().isEmpty() ||
    			    !cognome.getText().isEmpty() ||
    			    !Attività.getText().isEmpty() ||
    			    !coltura.getText().isEmpty() ||
    			    !data_inizio.getText().equals("DD/MM/YYYY") ||
    			    !data_fine.getText().equals("DD/MM/YYYY")) {
    			    
    			Lotto.setBackground(Color.WHITE);
	            Nome_colt.setBackground(Color.WHITE);
	            cognome.setBackground(Color.WHITE);
	            Attività.setBackground(Color.WHITE);
	            coltura.setBackground(Color.WHITE);
	            data_inizio.setBackground(Color.WHITE);
	            data_fine.setBackground(Color.WHITE);
            Lotto.setText("");
			Attività.setText("");
			coltura.setText("");
			Nome_colt.setText("");
			cognome.setText("");
			data_inizio.setText("DD/MM/YYYY");
			data_fine.setText("DD/MM/YYYY");
			Lotto_list.clearSelection();
			Lista_attività.clearSelection();
			Coltura_list.clearSelection();
			Nome_colt_list.clearSelection();
			cognome_list.clearSelection();
		}
		else {
			JOptionPane.showMessageDialog(null, "Nessun campo da cancellare", "Informazione", JOptionPane.INFORMATION_MESSAGE);
    	}
    	}
    });
    
    Torna_indietro_1.setPreferredSize(new Dimension(200, 70));
    Torna_indietro_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    Torna_indietro_1.setBounds(572, 733, 385, 70);
    pageProp.add(Torna_indietro_1);
    
    JButton Aggiungi_attività = new JButton("Organizza Attività");
    Aggiungi_attività.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
            	for (int i = 0; i < dati_selected.length; i++) {
					dati_selected[i] = null;
				}
				dati_selected[0] = Lotto.getText();
				dati_selected[1] = Nome_colt.getText();
				dati_selected[2] = cognome.getText();
				dati_selected[3] = Attività.getText();
				dati_selected[4] = coltura.getText();
				
            	if (dati_selected[0].isEmpty()) {	
                    Lotto.setBackground(Color.RED);
                    throw new Global_exceptions("Lotto", Global_exceptions.Tipo.empty_field);
                }
                if (dati_selected[1].isEmpty()) {
                    Nome_colt.setBackground(Color.RED);
                    throw new Global_exceptions("Nome coltivatore", Global_exceptions.Tipo.empty_field);
                }
                if (dati_selected[2].isEmpty()) {
                    cognome.setBackground(Color.RED);
                    throw new Global_exceptions("Cognome coltivatore", Global_exceptions.Tipo.empty_field);
                }
                if (dati_selected[3].isEmpty()) {
                    Attività.setBackground(Color.RED);
                    throw new Global_exceptions("Attività", Global_exceptions.Tipo.empty_field);
                }
                if (dati_selected[4].isEmpty()) {
                    coltura.setBackground(Color.RED);
                    throw new Global_exceptions("Coltura", Global_exceptions.Tipo.empty_field);
                }
                if (data_inizio.getText().equals("DD/MM/YYYY")|| data_inizio.getText().isEmpty()) {
                    data_inizio.setBackground(Color.RED);
                    throw new Global_exceptions("Data di inizio", Global_exceptions.Tipo.empty_field);
                }
                if (data_fine.getText().equals("DD/MM/YYYY")|| data_fine.getText().isEmpty()) {
                    data_fine.setBackground(Color.RED);
                    throw new Global_exceptions("Data di fine", Global_exceptions.Tipo.empty_field);
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            	LocalDate dataInizio = LocalDate.parse(data_inizio.getText(), formatter);
                LocalDate dataFine = LocalDate.parse(data_fine.getText(), formatter);
                if (dataFine.isBefore(dataInizio)) {
                    throw new Proprietario_addons_selection_exceptions(Tipo.Date_of_completion_precede_starting_date);
                }
               JOptionPane.showMessageDialog(null, "Attività organizzata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
				dati_selected[5] = data_inizio.toString();
				dati_selected[6] = dataFine.toString();
				Lotto.setBackground(Color.WHITE);
	            Nome_colt.setBackground(Color.WHITE);
	            cognome.setBackground(Color.WHITE);
	            Attività.setBackground(Color.WHITE);
	            coltura.setBackground(Color.WHITE);
	            data_inizio.setBackground(Color.WHITE);
	            data_fine.setBackground(Color.WHITE);
               Lotto.setText("");
   			Attività.setText("");
   			coltura.setText("");
   			Nome_colt.setText("");
   			cognome.setText("");
   			data_inizio.setText("DD/MM/YYYY");
   			data_fine.setText("DD/MM/YYYY");
   			Lotto_list.clearSelection();
   			Lista_attività.clearSelection();
   			Coltura_list.clearSelection();
   			Nome_colt_list.clearSelection();
   			cognome_list.clearSelection();
   			
            } catch (Proprietario_addons_selection_exceptions | Global_exceptions ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }catch (DateTimeParseException ex) {
				JOptionPane.showMessageDialog(null, "Formato data non valido. Usa il formato DD/MM/YYYY, oppure inserisci dati validi", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    });  


    Aggiungi_attività.setPreferredSize(new Dimension(200, 70));
    Aggiungi_attività.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    Aggiungi_attività.setBounds(1085, 733, 385, 70);
    pageProp.add(Aggiungi_attività);
    
    JLabel lblScegliLotto = new JLabel("Scegli il lotto");
    lblScegliLotto.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    lblScegliLotto.setHorizontalAlignment(SwingConstants.CENTER);
    lblScegliLotto.setBounds(31, 192, 300, 40);
    pageProp.add(lblScegliLotto);
    
    JLabel lblScegliIlNome = new JLabel("Scegli il nome del coltivatore");
    lblScegliIlNome.setHorizontalAlignment(SwingConstants.CENTER);
    lblScegliIlNome.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    lblScegliIlNome.setBounds(31, 418, 300, 40);
    pageProp.add(lblScegliIlNome);
    
    JLabel lblScegliLaColtura = new JLabel("Scegli la coltura");
    lblScegliLaColtura.setHorizontalAlignment(SwingConstants.CENTER);
    lblScegliLaColtura.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    lblScegliLaColtura.setBounds(440, 192, 300, 40);
    pageProp.add(lblScegliLaColtura);
    
    JLabel lblScegliLattivit = new JLabel("Scegli l'attività");
    lblScegliLattivit.setHorizontalAlignment(SwingConstants.CENTER);
    lblScegliLattivit.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    lblScegliLattivit.setBounds(794, 305, 300, 40);
    pageProp.add(lblScegliLattivit);
    
    JLabel lblScegliIlCognome = new JLabel("Scegli il cognome del coltivatore");
    lblScegliIlCognome.setHorizontalAlignment(SwingConstants.CENTER);
    lblScegliIlCognome.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    lblScegliIlCognome.setBounds(440, 418, 300, 40);
    pageProp.add(lblScegliIlCognome);
    
    JLabel lblInserisciLaData = new JLabel("Inserisci la data di inizio");
    lblInserisciLaData.setHorizontalAlignment(SwingConstants.CENTER);
    lblInserisciLaData.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    lblInserisciLaData.setBounds(1150, 273, 300, 40);
    pageProp.add(lblInserisciLaData);
    
    JLabel lblInserisciLaData_2 = new JLabel("Inserisci la data di fine");
    lblInserisciLaData_2.setHorizontalAlignment(SwingConstants.CENTER);
    lblInserisciLaData_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
    lblInserisciLaData_2.setBounds(1150, 364, 300, 40);
    pageProp.add(lblInserisciLaData_2);

    

    }
    // Metodo che ritorna i dati per la lista
   
}
