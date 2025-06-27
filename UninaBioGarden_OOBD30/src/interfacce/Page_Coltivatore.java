package interfacce;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Page_Coltivatore extends JFrame {

    private static final long serialVersionUID = 1L;
    private Image coltivatoreImage;
    private String username_colt;
    private Home home;
    
    
    
    // Costruttore che prende solo la stringa username
    public Page_Coltivatore(String username_colt) {
        this.username_colt = username_colt;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dimensione schermo e fullscreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = Login.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            coltivatoreImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }

        // Pannello personalizzato con sfondo
        JPanel pageColtivatorePanel = new JPanel() {
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

        pageColtivatorePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        pageColtivatorePanel.setLayout(null);

        // Imposto il pannello come content pane della finestra
        setContentPane(pageColtivatorePanel);
        
        JLabel User_name_printer = new JLabel("Bentornato");
        User_name_printer.setHorizontalAlignment(SwingConstants.CENTER);
        User_name_printer.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        User_name_printer.setBounds(51, 10, 1461, 56);
        pageColtivatorePanel.add(User_name_printer);
        
        // Qui mostri la stringa username passata
        JLabel User_name_printer_actual = new JLabel(username_colt);
        User_name_printer_actual.setHorizontalAlignment(SwingConstants.CENTER);
        User_name_printer_actual.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        User_name_printer_actual.setBounds(51, 59, 1461, 56);
        pageColtivatorePanel.add(User_name_printer_actual);
        
        JButton Vedi_attività = new JButton("Le mie Attività");
        Vedi_attività.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Vedi_attività.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        Vedi_attività.setBounds(694, 371, 199, 82);
        pageColtivatorePanel.add(Vedi_attività);
        
        JButton Vedi_attività_1 = new JButton("Lotti in cui lavoro");
        Vedi_attività_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        Vedi_attività_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Vedi_attività_1.setBounds(694, 522, 199, 82);
        pageColtivatorePanel.add(Vedi_attività_1);
        
        JButton Vedi_attività_2 = new JButton("Torna alla home");
        Vedi_attività_2.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		home = new Home();
				home.setVisible(true);
				dispose(); // Chiude la finestra corrente
			}
        });
        Vedi_attività_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        	
        });
        Vedi_attività_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Vedi_attività_2.setBounds(694, 673, 199, 82);
        pageColtivatorePanel.add(Vedi_attività_2);
    }
}
