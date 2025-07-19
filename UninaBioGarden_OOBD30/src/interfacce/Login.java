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
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import controller.Controller;


import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import interfacce.Exceptions.Global_exceptions;
import javax.swing.JCheckBox;
public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Image LoginImage;
	private JTextField username_txt;
	private JPasswordField password_login;
	private Login LoginFrame;
	private Controller TheController;
	
	public Login(Controller TheController) {
		this.TheController = TheController;
		setResizable(false);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    // Ottengo dimensione schermo
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	    // Setto i bounds a tutta la dimensione dello schermo
	    setBounds(0, 0, screenSize.width , screenSize.height);

	    // Carico immagine di sfondo
	    URL imageUrl = Login.class.getResource("Images/Login_image.jpg");

	    if (imageUrl != null) {
	        setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
	        LoginImage = new ImageIcon(imageUrl).getImage();
	    } else {
	        System.out.println("Immagine non trovata!");
	    }

	    // JPanel con sfondo
	    JPanel Login = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            if (LoginImage != null) {
	                Graphics2D g2d = (Graphics2D) g;
	                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	                g2d.drawImage(LoginImage, 0, 0, getWidth(), getHeight(), this);
	            }
	        }
	    };
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(Login);
		Login.setLayout(null);
		
		JTextPane login_welcome_text = new JTextPane();
		login_welcome_text.setForeground(new Color(0, 0, 0));
		login_welcome_text.setBackground(new Color(255, 255, 255, 0));
		login_welcome_text.setOpaque(false);
		login_welcome_text.setFont(new Font("Times New Roman", Font.PLAIN, 50));
		login_welcome_text.setText("Bentornato/a, è un piacere rivederti!, ti auguriamo un'altra piacevole esperienza con noi, se è la tua prima volta che accedi, allora clicca il tasto \"Registrati\"");
		login_welcome_text.setEditable(false);
		login_welcome_text.setFocusable(false);
		login_welcome_text.setBounds(0, 10, 1512, 261);

		// Imposta il testo centrato
		StyledDocument doc = login_welcome_text.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		Login.add(login_welcome_text);
		
		username_txt = new JTextField();
		username_txt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		username_txt.setBounds(564, 530, 471, 48);
		Login.add(username_txt);
		username_txt.setColumns(10);
		
		
		password_login = new JPasswordField();
		password_login.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		password_login.setBounds(564, 643, 471, 48);
		Login.add(password_login);
		
		JLabel USERNAME = new JLabel("USERNAME");
		USERNAME.setHorizontalAlignment(SwingConstants.CENTER);
		USERNAME.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		USERNAME.setBounds(564, 464, 471, 56);
		Login.add(USERNAME);
		
		JLabel PASSWORD = new JLabel("PASSWORD");
		PASSWORD.setHorizontalAlignment(SwingConstants.CENTER);
		PASSWORD.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		PASSWORD.setBounds(564, 588, 471, 56);
		Login.add(PASSWORD);
		
		JButton Indietro = new JButton("Indietro");
		Indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	    Indietro.addMouseListener(new MouseAdapter() {@Override
	    	public void mouseClicked(MouseEvent e) {
	    		TheController.OpenHome_closeCaller(Login.this);
	    }
	    });
		Indietro.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Indietro.setBounds(564, 728, 140, 45);
		Login.add(Indietro);
		
		JButton Registrati_button = new JButton("REGISTRATI");
		Registrati_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				  String username = username_txt.getText();
				try {
					if (!username.isEmpty()) {
						throw new Global_exceptions("il campo username deve essere vuoto");
					}
					String password = new String(password_login.getPassword());
					if (!password.isEmpty()) {
						throw new Global_exceptions("il campo password deve essere vuoto");
					}
					TheController.OpenUserRegistration_closeCaller(Login.this);
				} catch (Global_exceptions e1) {
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
		Registrati_button.setBounds(895, 728, 140, 45);
		Login.add(Registrati_button);
		
		JButton Accedi_button = new JButton("ACCEDI");
		Accedi_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Accedi_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    String username = username_txt.getText().trim();
			    String password = new String(password_login.getPassword()).trim();
			    username_txt.setBackground(Color.WHITE);
			    password_login.setBackground(Color.WHITE);
			    try {
			        if (username.isEmpty()) {
			            username_txt.setBackground(Color.RED);
			        	throw new Global_exceptions("username", Global_exceptions.Tipo.empty_field);
			        }
			        if (!username.matches("^(?=.*[A-Za-z])[A-Za-z0-9]{2,}$")) {
			            username_txt.setBackground(Color.RED);
			            throw new Global_exceptions("Username", Global_exceptions.Tipo.format_mismatch);
			        }
			        if (password.isEmpty()) {
			            password_login.setBackground(Color.RED);
			        	throw new Global_exceptions("password", Global_exceptions.Tipo.empty_field);
			        }
			        if (password.length() < 4) {
			            password_login.setBackground(Color.RED);
			        	throw new Global_exceptions("password", Global_exceptions.Tipo.format_mismatch);
			        }
			        username_txt.setBackground(Color.WHITE);
			        password_login.setBackground(Color.WHITE);
			        String Username_to_convert = username_txt.getText();
			        String CF= TheController.Convert_UsernameToCF(Username_to_convert);
			        if (CF == null) {
			            username_txt.setBackground(Color.RED);
			        	throw new Global_exceptions("Username", Global_exceptions.Tipo.not_found_in_DB);
			        }
			        String validat=TheController.Get_password(Username_to_convert);
			        if (validat == null) {
			        	throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
			        }
			        if (!password.trim().equals(validat.trim())) {
			        	username_txt.setBackground(Color.RED);
			        	throw new Global_exceptions(Username_to_convert, Global_exceptions.Tipo.correct_username_but_wrong_password);
			        }
			        int userType = TheController.Find_where_to_acces(Username_to_convert);
			        if (userType==0){
			        	String username_4_colt = Username_to_convert;
			        	TheController.OpenPageColtivatore_closeCaller(username_4_colt, Login.this);
			        	
			        }else { 
			        String username_4_prop = Username_to_convert;
			        TheController.OpenProprietarioLoggedIn_closeCaller(username_4_prop, Login.this);
			        	
			        }if (userType==-99 || password.equals(null)) {
			        	throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
			        }
			        } catch (Global_exceptions e1) {
			        JOptionPane.showMessageDialog(
			            null,
			            e1.getMessage(),
			            "Errore",
			            JOptionPane.ERROR_MESSAGE
			        );
			    }
			}
		});
		
		Accedi_button.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		Accedi_button.setBounds(734, 728, 140, 45);
		Login.add(Accedi_button);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 80));
		lblNewLabel.setBounds(564, 257, 471, 94);
		Login.add(lblNewLabel);
	}
	public String getUsername_txt() {
		return username_txt.getText();
	}
}


