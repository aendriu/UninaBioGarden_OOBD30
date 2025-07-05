package interfacce;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import controller.Controller;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import interfacce.Exceptions.Global_exceptions;
import javax.swing.JCheckBox;
import interfacce.Exceptions.Specific_exceptions.Registration_exceptions;
import javax.swing.JOptionPane;
public class User_registration_page extends JFrame {

	private static final long serialVersionUID = 1L;
	private Image Registration_image;
	private JTextField username_txt;
	private JPasswordField password_Registrazione;
	private Login Login;
	private JTextField Nome_txt;
	private JTextField Cognome_txt;
	private JTextField CF_txt;
	private JCheckBox Colt_optz;
	private JCheckBox Prop_optz;
	private Controller TheController;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public User_registration_page(Controller TheController) {
	    this.TheController = TheController;
		setResizable(false);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle("Registration Page");

	    // Ottengo dimensione schermo
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	    // Setto i bounds a tutta la dimensione dello schermo
	    setBounds(0, 0, screenSize.width , screenSize.height);

	    // Carico immagine di sfondo
	    URL imageUrl = User_registration_page.class.getResource("Images/Login_image.jpg");

	    if (imageUrl != null) {
	        setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
	        Registration_image = new ImageIcon(imageUrl).getImage();
	    } else {
	        System.out.println("Immagine non trovata!");
	    }

	    // JPanel con sfondo
	    JPanel User_registration_interface = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            if (Registration_image != null) {
	                Graphics2D g2d = (Graphics2D) g;
	                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	                g2d.drawImage(Registration_image, 0, 0, getWidth(), getHeight(), this);
	            }
	        }
	    };
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setContentPane(User_registration_interface);
	    User_registration_interface.setLayout(null);
	    SimpleAttributeSet center = new SimpleAttributeSet();
	    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
	
	    
	    username_txt = new JTextField();
	    username_txt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	    username_txt.setBounds(564, 530, 471, 48);
	    User_registration_interface.add(username_txt);
	    username_txt.setColumns(10);
	    
	    
	    password_Registrazione = new JPasswordField();
	    password_Registrazione.setToolTipText("minimo 9 charatteri e almeno una maiuscola");
	    password_Registrazione.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	    password_Registrazione.setBounds(564, 643, 471, 48);
	    User_registration_interface.add(password_Registrazione);
	    
	    JLabel USERNAME = new JLabel("USERNAME");
	    USERNAME.setHorizontalAlignment(SwingConstants.CENTER);
	    USERNAME.setFont(new Font("Times New Roman", Font.PLAIN, 30));
	    USERNAME.setBounds(564, 464, 471, 56);
	    User_registration_interface.add(USERNAME);
	    
	    JLabel PASSWORD = new JLabel("PASSWORD");
	    PASSWORD.setHorizontalAlignment(SwingConstants.CENTER);
	    PASSWORD.setFont(new Font("Times New Roman", Font.PLAIN, 30));
	    PASSWORD.setBounds(564, 588, 471, 56);
	    User_registration_interface.add(PASSWORD);
	    
	    JButton Indietro = new JButton("Indietro");
	    Indietro.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	}
	    });
	    Indietro.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            Login= new Login(TheController);
	            Login.setVisible(true);
	            User_registration_interface.setVisible(false);
	            dispose(); // Chiude la finestra corrente
	        }
	    });
	    Indietro.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	    Indietro.setBounds(564, 755, 140, 45);
	    User_registration_interface.add(Indietro);
	    
	    JButton Registrati_button = new JButton("REGISTRATI");
	    Registrati_button.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            String username = username_txt.getText();
	            String nome = Nome_txt.getText();
	            String cognome = Cognome_txt.getText();
	            String cf = CF_txt.getText();
	            String password = new String(password_Registrazione.getPassword());
	            try {
					if (nome.isEmpty()){
						Nome_txt.setBackground(Color.RED);
						throw new Global_exceptions("nome",Global_exceptions.Tipo.empty_field);
					}
					if (cognome.isEmpty()){
						Cognome_txt.setBackground(Color.RED);
						throw new Global_exceptions("cognome",Global_exceptions.Tipo.empty_field);
					}
					if (cf.isEmpty()){
						CF_txt.setBackground(Color.RED);
						throw new Global_exceptions("codice fiscale",Global_exceptions.Tipo.empty_field);
					}
					if (username.isEmpty()){
						username_txt.setBackground(Color.RED);
						throw new Global_exceptions("username",Global_exceptions.Tipo.empty_field);
					}
					if (password.isEmpty()){
						password_Registrazione.setBackground(Color.RED);
						throw new Global_exceptions("password",Global_exceptions.Tipo.empty_field);
					}
					if (!nome.matches("[A-Za-z]")) {
						Nome_txt.setBackground(Color.RED);
						throw new Global_exceptions("nome", Global_exceptions.Tipo.Type_mismatch);
					}
					if (!cognome.matches("[A-Za-z]")) {
						Cognome_txt.setBackground(Color.RED);
						throw new Global_exceptions("cognome", Global_exceptions.Tipo.Type_mismatch);
					}
					if (!cf.matches("[A-Z0-9]")) {
						CF_txt.setBackground(Color.RED);
						throw new Global_exceptions("codice fiscale", Global_exceptions.Tipo.Type_mismatch);
					}
					if (!username.matches("^[A-Za-z0-9]*$")) {
						username_txt.setBackground(Color.RED);
						throw new Global_exceptions("username", Global_exceptions.Tipo.Type_mismatch);
					}
					
					if (!nome.matches("^[a-zA-Z]+$")) {
						Nome_txt.setBackground(Color.RED);
						throw new Global_exceptions("nome", Global_exceptions.Tipo.format_mismatch);
					}
					if (!cognome.matches("^[a-zA-Z]+$")) {
						Cognome_txt.setBackground(Color.RED);
						throw new Global_exceptions("cognome", Global_exceptions.Tipo.format_mismatch);
					}
					if (!cf.matches("^[A-Z0-9]{16}$")) {
						CF_txt.setBackground(Color.RED);
						throw new Global_exceptions("codice fiscale", Global_exceptions.Tipo.format_mismatch);
					}
					
					if (!username.matches("^[A-Za-z]+[0-9]*$")) {
					    username_txt.setBackground(Color.RED);
						throw new Global_exceptions("username", Global_exceptions.Tipo.format_mismatch);
					}
					if (password.length() < 8||!password.matches(".*[A-Z].*")) {
						password_Registrazione.setBackground(Color.RED);
						throw new Global_exceptions("password", Global_exceptions.Tipo.format_mismatch);
					}
					if(cf.matches("Abcdefghijklmnopqrstu")) {
						CF_txt.setBackground(Color.RED);
						throw new Global_exceptions("codice fiscale", Global_exceptions.Tipo.already_exists_in_DB);
					}
					 if (username.equals("annabartolini")){
				            username_txt.setBackground(Color.RED);
				        	throw new Registration_exceptions("username", Registration_exceptions.Tipo.username_already_exists);
				        }
					 if (password.equals("12345678")) {
				            password_Registrazione.setBackground(Color.RED);
				        	throw new Registration_exceptions("password", Registration_exceptions.Tipo.password_already_exists);
				        }
					if (Colt_optz.isSelected() && Prop_optz.isSelected()) {
						Colt_optz.setBackground(Color.RED);
						Prop_optz.setBackground(Color.RED);
						throw new Registration_exceptions("Errore", Registration_exceptions.Tipo.Double_checkbox_selected);
					}
					if (!Colt_optz.isSelected() && !Prop_optz.isSelected()) {
						Colt_optz.setBackground(Color.RED);
						Prop_optz.setBackground(Color.RED);
						throw new Registration_exceptions("Errore", Registration_exceptions.Tipo.No_checkbox_selected);
					}
					username_txt.setBackground(Color.WHITE);
					Nome_txt.setBackground(Color.WHITE);
					Cognome_txt.setBackground(Color.WHITE);
					CF_txt.setBackground(Color.WHITE);
					password_Registrazione.setBackground(Color.WHITE);
					//fare registrazione coltivatore e proprietario
				} catch (Global_exceptions | Registration_exceptions e1) {
				JOptionPane.showMessageDialog(
			            null,
			            e1.getMessage(),
			            "Errore",
			            JOptionPane.ERROR_MESSAGE
			        );
				}
	        }
	    });
	    Registrati_button.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	    Registrati_button.setBounds(895, 755, 140, 45);
	    User_registration_interface.add(Registrati_button);
	    
	    JLabel lblNewLabel = new JLabel("REGISTRATI");
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 90));
	    lblNewLabel.setBounds(515, 0, 566, 94);
	    User_registration_interface.add(lblNewLabel);
	    
	    Nome_txt = new JTextField();
	    Nome_txt.setToolTipText("si ricorda che l'email deve seguire il seguente formato, <nome>.<cognome>@[gmail o libero].[it o com]");
	    Nome_txt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	    Nome_txt.setColumns(10);
	    Nome_txt.setBounds(564, 183, 471, 48);
	    User_registration_interface.add(Nome_txt);
	    
	    Cognome_txt = new JTextField();
	    Cognome_txt.setToolTipText("si ricorda che l'email deve seguire il seguente formato, <nome>.<cognome>@[gmail o libero].[it o com]");
	    Cognome_txt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	    Cognome_txt.setColumns(10);
	    Cognome_txt.setBounds(564, 297, 471, 48);
	    User_registration_interface.add(Cognome_txt);
	    
	    JLabel NOME = new JLabel("NOME");
	    NOME.setHorizontalAlignment(SwingConstants.CENTER);
	    NOME.setFont(new Font("Times New Roman", Font.PLAIN, 30));
	    NOME.setBounds(564, 131, 471, 56);
	    User_registration_interface.add(NOME);
	    
	    JLabel COGNOME = new JLabel("COGNOME");
	    COGNOME.setHorizontalAlignment(SwingConstants.CENTER);
	    COGNOME.setFont(new Font("Times New Roman", Font.PLAIN, 30));
	    COGNOME.setBounds(564, 252, 471, 56);
	    User_registration_interface.add(COGNOME);
	    
	    CF_txt = new JTextField();
	    CF_txt.setToolTipText("si ricorda che l'email deve seguire il seguente formato, <nome>.<cognome>@[gmail o libero].[it o com]");
	    CF_txt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	    CF_txt.setColumns(10);
	    CF_txt.setBounds(564, 406, 471, 48);
	    User_registration_interface.add(CF_txt);
	    
	    JLabel CODICE_FISCALE = new JLabel("CODICE FISCALE");
	    CODICE_FISCALE.setHorizontalAlignment(SwingConstants.CENTER);
	    CODICE_FISCALE.setFont(new Font("Times New Roman", Font.PLAIN, 30));
	    CODICE_FISCALE.setBounds(564, 355, 471, 56);
	    User_registration_interface.add(CODICE_FISCALE);
	    
	    JCheckBox Colt_optz = new JCheckBox("COLTIVATORE");
	    Colt_optz.setForeground(new Color(0, 0, 0));
	    Colt_optz.setToolTipText("Registrati come coltivatore");
	    Colt_optz.setOpaque(false); // Imposta la checkbox come trasparente
	    Colt_optz.setHorizontalAlignment(SwingConstants.CENTER);
	    Colt_optz.setFont(new Font("Times New Roman", Font.BOLD, 15));
	    Colt_optz.setHorizontalTextPosition(SwingConstants.CENTER); // testo centrato orizzontalmente rispetto alla checkbox
	    Colt_optz.setVerticalTextPosition(SwingConstants.TOP);      // testo sopra la checkbox
	    Colt_optz.setBackground(new Color(255, 255, 255, 0)); // sfondo trasparente
	    Colt_optz.setBounds(647, 697, 150, 50);
	    User_registration_interface.add(Colt_optz);
	    
	    JCheckBox Prop_optz = new JCheckBox("PROPRIETARIO");
	    Prop_optz.setVerticalTextPosition(SwingConstants.TOP);
	    Prop_optz.setToolTipText("Registrati come propetario");
	    Prop_optz.setOpaque(false);
	    Prop_optz.setHorizontalTextPosition(SwingConstants.CENTER);
	    Prop_optz.setHorizontalAlignment(SwingConstants.CENTER);
	    Prop_optz.setForeground(Color.BLACK);
	    Prop_optz.setFont(new Font("Times New Roman", Font.BOLD, 15));
	    Prop_optz.setBackground(new Color(255, 255, 255, 0));
	    Prop_optz.setBounds(785, 699, 150, 50);
	    User_registration_interface.add(Prop_optz);
	
	    
	}
}
