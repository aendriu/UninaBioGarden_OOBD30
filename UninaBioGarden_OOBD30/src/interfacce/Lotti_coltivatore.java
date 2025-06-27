package interfacce;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class Lotti_coltivatore extends JFrame {

    private static final long serialVersionUID = 1L;
    private Image coltivatoreImage;
    private String username_colt;

    // Costruttore che prende solo la stringa username
    public Lotti_coltivatore (String username_colt) {
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

        // Qui aggiungo la JLabel per mostrare la username, centrata e con font grande
        if (username_colt != null && !username_colt.isEmpty()) {
            JLabel userLabel = new JLabel(username_colt);
            userLabel.setHorizontalAlignment(SwingConstants.CENTER);
            userLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
            userLabel.setBounds(50, 10, screenSize.width - 100, 60);
            pageColtivatorePanel.add(userLabel);
        }

        // imposto correttamente il contentPane
        setContentPane(pageColtivatorePanel);
    }

}