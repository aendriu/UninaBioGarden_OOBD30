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

import controller.Controller;
import interfacce.Exceptions.Global_exceptions;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
    private Controller TheController;
    
    
    // Costruttore che prende solo la stringa username
    public Page_Coltivatore(String username_colt, Controller TheController) {
    	setResizable(false);
        this.TheController = TheController;
    	this.username_colt = username_colt;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dimensione schermo e fullscreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = Page_Coltivatore.class.getResource("Images/image_progetto_logo.jpg");
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

        
        pageColtivatorePanel.setLayout(null);

        // Imposto il pannello come content pane della finestra
        setContentPane(pageColtivatorePanel);
        
        JLabel User_name_printer = new JLabel("Bentornato");
        User_name_printer.setHorizontalAlignment(SwingConstants.CENTER);
        User_name_printer.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        User_name_printer.setBounds(30, 10, 1461, 56);
        pageColtivatorePanel.add(User_name_printer,TheController);
        
        // Qui mostri la stringa username passata
        String Print_details= TheController.get_Info_From_Username(username_colt, 0);
        
        try {
			if (Print_details.isEmpty()){
				throw new Global_exceptions("", Global_exceptions.Tipo.DB_fault);
			}
		} catch (Global_exceptions e) {
			 JOptionPane.showMessageDialog(
					 null,
			          e.getMessage(),
			            "Errore",
			            JOptionPane.ERROR_MESSAGE
			        );
		}
        JLabel User_name_printer_actual = new JLabel(Print_details);
        User_name_printer_actual.setHorizontalAlignment(SwingConstants.CENTER);
        User_name_printer_actual.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        User_name_printer_actual.setBounds(30, 59, 1461, 56);
        pageColtivatorePanel.add(User_name_printer_actual);
        
        JButton Vedi_attività = new JButton("Le mie Attività");
        Vedi_attività.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		TheController.OpenColtivatoreAttivitaResponsabili_closeCaller(username_colt, Page_Coltivatore.this);
        	}
        });
        Vedi_attività.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Vedi_attività.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        Vedi_attività.setBounds(661, 371, 199, 82);
        pageColtivatorePanel.add(Vedi_attività);
        
        JButton Lotti_lavora = new JButton("Lotti in cui lavoro");
        Lotti_lavora.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		TheController.OpenColtivatoreLottiInCuiLavora_closeCaller(username_colt, Page_Coltivatore.this);
			}
        });
        Lotti_lavora.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        Lotti_lavora.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Lotti_lavora.setBounds(661, 522, 199, 82);
        pageColtivatorePanel.add(Lotti_lavora);
        
        JButton Torna_alla_home = new JButton("Torna alla home");
        Torna_alla_home.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		TheController.OpenHome_closeCaller(Page_Coltivatore.this);
			}
        });
        Torna_alla_home.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        	
        });
        Torna_alla_home.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Torna_alla_home.setBounds(661, 673, 199, 82);
        pageColtivatorePanel.add(Torna_alla_home);
    }
}
