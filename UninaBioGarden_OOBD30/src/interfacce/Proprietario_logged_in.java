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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Proprietario_logged_in extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel Prop_content_Pane;
    private Controller TheController;
    private String username;
    private Image PropImage;
    private Login loginFrame;
    private Prop_lotti_visual_scheme Lotti_vs;
    private Free_lotti lotti_liberi;
    private Proprietario_activities_visual Attività_vs;
    private Prop_organizza_attività orgAttività;
    private Progetti_visual_scheme Progetti_vs;
    private Progetti_creation_scheme Progetti_creation;
    int selector_coming_from;
    /**
     * Create the frame.
     */
    public Proprietario_logged_in(String username, Controller TheController) {
        setResizable(false);
        this.TheController = TheController;
        this.username = username;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = Login.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            PropImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }

        // Pannello personalizzato con sfondo
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

        JLabel User_name_printer = new JLabel("Bentornato");
        User_name_printer.setHorizontalAlignment(SwingConstants.CENTER);
        User_name_printer.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        User_name_printer.setBounds(51, 10, 1461, 56);
        pageProp.add(User_name_printer, TheController);

        JLabel User_name_printer_actual = new JLabel(username);
        User_name_printer_actual.setHorizontalAlignment(SwingConstants.CENTER);
        User_name_printer_actual.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        User_name_printer_actual.setBounds(51, 59, 1461, 56);
        pageProp.add(User_name_printer_actual);

        JButton Lotti_prop_button = new JButton("Visualizza lotti");
        Lotti_prop_button.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Lotti_prop_button.setBounds(685, 138, 191, 70);
        pageProp.add(Lotti_prop_button);

        // creo il popup menu per il pulsante
        JPopupMenu popupMenu_4_lotti = new JPopupMenu();

        JMenuItem voce1 = new JMenuItem("I miei lotti");
        voce1.addActionListener(e -> {
        	selector_coming_from = 0; // 0 per i lotti
        	Lotti_vs = new Prop_lotti_visual_scheme(username, TheController, selector_coming_from);
        Lotti_vs.setVisible(true);
        dispose();
        });

        JMenuItem voce2 = new JMenuItem("ottieni nuovo lotto");
        voce2.addActionListener(e -> {
           lotti_liberi = new Free_lotti(username, TheController);
           lotti_liberi.setVisible(true);
           dispose();
        	// qui puoi richiamare il controller
        });

        popupMenu_4_lotti.add(voce1);
        popupMenu_4_lotti.add(voce2);

        // mostra menu a destra sotto con click sinistro
        Lotti_prop_button.addActionListener(e -> {
            int x = Lotti_prop_button.getWidth(); // a destra
            int y = Lotti_prop_button.getHeight(); // sotto
            popupMenu_4_lotti.show(Lotti_prop_button, x, y);
        });

        JButton Attività_prop_button = new JButton("Visualizza attività");
        Attività_prop_button.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Attività_prop_button.setBounds(685, 289, 191, 70);
        pageProp.add(Attività_prop_button);
        JPopupMenu popupMenu_4_attività = new JPopupMenu();
        JMenuItem voce1_attività = new JMenuItem("Le mie attività");
        voce1_attività.addActionListener(e -> {
			Attività_vs = new Proprietario_activities_visual(username, TheController);
			Attività_vs.setVisible(true);
			dispose();
			// qui puoi richiamare il controller
		});
        JMenuItem voce2_attività = new JMenuItem("organizza attività");
        voce2_attività.addActionListener(e -> {
        	orgAttività = new Prop_organizza_attività(username, TheController);
        	orgAttività.setVisible(true);
        	dispose();
        });
        
        popupMenu_4_attività.add(voce1_attività);
        popupMenu_4_attività.add(voce2_attività);
        // mostra menu a destra sotto con click sinistro
        Attività_prop_button.addActionListener(e -> {
			int x = Attività_prop_button.getWidth(); // a destra
			int y = Attività_prop_button.getHeight(); // sotto
			popupMenu_4_attività.show(Attività_prop_button, x, y);
		});
        
        JButton Progetti_prop_button = new JButton("Visualizza progetti");
        Progetti_prop_button.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Progetti_prop_button.setBounds(685, 437, 191, 70);
        pageProp.add(Progetti_prop_button);
        JPopupMenu popupMenu_4_progetti = new JPopupMenu();
        JMenuItem voce1_progetti = new JMenuItem("I miei progetti");
        voce1_progetti.addActionListener(e -> {
        	Progetti_vs = new Progetti_visual_scheme(username, TheController);
        	Progetti_vs.setVisible(true);
        	dispose();
        });
        JMenuItem voce2_progetti = new JMenuItem("crea nuovo progetto");
        voce2_progetti.addActionListener(e -> {
		 selector_coming_from = 1; // 1 per i progetti
        Lotti_vs = new Prop_lotti_visual_scheme(username, TheController, selector_coming_from);
		 Lotti_vs.setVisible(true);
		 dispose();
		});
        
        popupMenu_4_progetti.add(voce1_progetti);
        popupMenu_4_progetti.add(voce2_progetti);
        // mostra menu a destra sotto con click sinistro
        Progetti_prop_button.addActionListener(e -> {
        	int x = Progetti_prop_button.getWidth(); // a destra
			int y = Progetti_prop_button.getHeight(); // sotto
			popupMenu_4_progetti.show(Progetti_prop_button, x, y);
        });
        JButton Report_prop_button = new JButton("Visualizza report");
        Report_prop_button.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Report_prop_button.setBounds(685, 589, 191, 70);
        pageProp.add(Report_prop_button);
        //TODO
        
        JButton Torna_indietro = new JButton("Logout");
        Torna_indietro.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Arrivederci " + username + "!");
            loginFrame = new Login(TheController);
            loginFrame.setVisible(true);
            dispose();
        });
        Torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Torna_indietro.setBounds(540, 727, 483, 70);
        pageProp.add(Torna_indietro);
    }
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}