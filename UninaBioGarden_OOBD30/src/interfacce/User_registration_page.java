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
public class User_registration_page extends JFrame {

	private static final long serialVersionUID = 1L;
	private Image Registration_image;
	private JTextField username_txt;
	private JPasswordField password_login;
	private Login Login;
	private JTextField Nome_txt;
	private JTextField Cognome_txt;
	private JTextField CF_txt;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User_registration_page frame = new User_registration_page();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public User_registration_page() {
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
	    username_txt.setToolTipText("si ricorda che l'email deve seguire il seguente formato, <nome>.<cognome>@[gmail o libero].[it o com]");
	    username_txt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	    username_txt.setBounds(564, 530, 471, 48);
	    User_registration_interface.add(username_txt);
	    username_txt.setColumns(10);
	    
	    
	    password_login = new JPasswordField();
	    password_login.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	    password_login.setBounds(564, 643, 471, 48);
	    User_registration_interface.add(password_login);
	    
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
	            Login= new Login();
	            Login.setVisible(true);
	            User_registration_interface.setVisible(false);
	            dispose(); // Chiude la finestra corrente
	        }
	    });
	    Indietro.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	    Indietro.setBounds(564, 728, 140, 45);
	    User_registration_interface.add(Indietro);
	    
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
	}
}
