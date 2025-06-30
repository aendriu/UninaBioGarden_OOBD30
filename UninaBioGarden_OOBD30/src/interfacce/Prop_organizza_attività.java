
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

import controller.Controller;

public class Prop_organizza_attività extends JFrame {

    private static final long serialVersionUID = 1L;
    private Controller TheController;
    private String username;
    private Image PropImage;

    // campo 1
    private JTextField field1;
    private JScrollPane popupScroll1;
    private JList<String> popupList1;

    // campo 2
    private JTextField field2;
    private JScrollPane popupScroll2;
    private JList<String> popupList2;

    // campo 3
    private JTextField field3;
    private JScrollPane popupScroll3;
    private JList<String> popupList3;

    // campo 4
    private JTextField field4;
    private JScrollPane popupScroll4;
    private JList<String> popupList4;

    public Prop_organizza_attività(String username, Controller TheController) {
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
        field1 = new JTextField();
        field1.setEditable(false);
        field1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        field1.setBounds(45, 200, 300, 40);
        pageProp.add(field1);

        popupList1 = new JList<>();
        popupList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        popupList1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String sel = popupList1.getSelectedValue();
                if (sel != null) {
                    field1.setText(sel);
                    popupScroll1.setVisible(false);
                }
            }
        });
        popupScroll1 = new JScrollPane(popupList1);
        popupScroll1.setBounds(field1.getX(), field1.getY() + field1.getHeight(), 300, 100);
        popupScroll1.setVisible(false);
        pageProp.add(popupScroll1);

        field1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupList1.setListData(getData1());
                popupScroll1.setVisible(true);
            }
        });
        field1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                popupScroll1.setVisible(false);
            }
        });

        // === CAMPO 2 ===
        field2 = new JTextField();
        field2.setEditable(false);
        field2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        field2.setBounds(440, 200, 300, 40);
        pageProp.add(field2);

        popupList2 = new JList<>();
        popupList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        popupList2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String sel = popupList2.getSelectedValue();
                if (sel != null) {
                    field2.setText(sel);
                    popupScroll2.setVisible(false);
                }
            }
        });
        popupScroll2 = new JScrollPane(popupList2);
        popupScroll2.setBounds(field2.getX(), field2.getY() + field2.getHeight(), 300, 100);
        popupScroll2.setVisible(false);
        pageProp.add(popupScroll2);

        field2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupList2.setListData(getData2());
                popupScroll2.setVisible(true);
            }
        });
        field2.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                popupScroll2.setVisible(false);
            }
        });

        // === CAMPO 3 ===
        field3 = new JTextField();
        field3.setEditable(false);
        field3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        field3.setBounds(833, 200, 300, 40);
        pageProp.add(field3);

        popupList3 = new JList<>();
        popupList3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        popupList3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String sel = popupList3.getSelectedValue();
                if (sel != null) {
                    field3.setText(sel);
                    popupScroll3.setVisible(false);
                }
            }
        });
        popupScroll3 = new JScrollPane(popupList3);
        popupScroll3.setBounds(field3.getX(), field3.getY() + field3.getHeight(), 300, 100);
        popupScroll3.setVisible(false);
        pageProp.add(popupScroll3);

        field3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupList3.setListData(getData3());
                popupScroll3.setVisible(true);
            }
        });
        field3.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                popupScroll3.setVisible(false);
            }
        });

        // === CAMPO 4 ===
        field4 = new JTextField();
        field4.setEditable(false);
        field4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        field4.setBounds(1191, 200, 300, 40);
        pageProp.add(field4);

        popupList4 = new JList<>();
        popupList4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        popupList4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String sel = popupList4.getSelectedValue();
                if (sel != null) {
                    field4.setText(sel);
                    popupScroll4.setVisible(false);
                }
            }
        });
        popupScroll4 = new JScrollPane(popupList4);
        popupScroll4.setBounds(field4.getX(), field4.getY() + field4.getHeight(), 300, 100);
        popupScroll4.setVisible(false);
        pageProp.add(popupScroll4);

        field4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupList4.setListData(getData4());
                popupScroll4.setVisible(true);
            }
        });
        field4.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                popupScroll4.setVisible(false);
            }
        });
    }


    // metodi di esempio per popolare i campi
    private String[] getData1() {
        return new String[]{"Lotto 1", "Lotto 2", "Lotto 3", "Lotto 4"};
    }

    private String[] getData2() {
        return new String[]{"Attività A", "Attività B", "Attività C"};
    }

    private String[] getData3() {
        return new String[]{"Mais", "Frumento", "Orzo"};
    }

    private String[] getData4() {
        return new String[]{"Mario", "Anna", "Luca", "Giorgia"};
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Controller Thecontroller = new Controller();
                Prop_organizza_attività frame = new Prop_organizza_attività("username", Thecontroller);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
