package interfacce;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import javax.swing.AbstractListModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Prop_lotti_visual_scheme extends JFrame {

    private static final long serialVersionUID = 1L;
    private String username;
    private Controller TheController;
    private Image Prop_lotti_visual_scheme_image;
    private JList<String> list;
    private MyListModel model;
    private instance_of_lotto_selected iols;
    private int selectedIndex;
    private Progetti_creation_scheme Progetti_creation_page;
    private class MyListModel extends AbstractListModel<String> {
    private String[] values = new String[0];
        @Override
        public int getSize() {
            return values.length;
        }

        @Override
        public String getElementAt(int index) {
            return values[index];
        }

        public void setValues(String[] newValues) {
            values = newValues;
            fireContentsChanged(this, 0, values.length - 1);
        }
    }

    public Prop_lotti_visual_scheme(String username, Controller TheController, int SelectedIndex) {
        this.username = username;
        this.TheController = TheController;
        this.selectedIndex = SelectedIndex;

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dimensione schermo e fullscreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Crea modello e lista
        model = new MyListModel();
        list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        list.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        list.setFixedCellHeight(100);

        // Qui centro il testo degli elementi nella lista
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });
        list.addListSelectionListener(e -> {
			// Azione da eseguire quando si seleziona un lotto
			String selectedLotto = list.getSelectedValue();
			if (selectedIndex==0) {
			iols = new instance_of_lotto_selected(selectedLotto, TheController, username);
			iols.setVisible(true);
			dispose();
			}else if (SelectedIndex==1) {
				Progetti_creation_page = new Progetti_creation_scheme(selectedLotto, TheController, username);
				Progetti_creation_page.setVisible(true);
				dispose();
			}
		});
        // scroll pane per renderla scrollabile
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0, 0, 1522, 775);

        // bottone sotto
        JButton Torna_indietro = new JButton("Torna indietro");
        Torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        Torna_indietro.setBounds(0, 775, 1522, 70);
        Torna_indietro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	Proprietario_logged_in proprietario_logged_in = new Proprietario_logged_in(username, TheController);
                proprietario_logged_in.setVisible(true);
                dispose(); // chiude la finestra corrente
            }
        });
        Torna_indietro.setPreferredSize(new Dimension(200, 70));
        getContentPane().setLayout(null);
        getContentPane().add(scrollPane);
        getContentPane().add(Torna_indietro);

        // Carico immagine icona
        URL imageUrl = Login.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            Prop_lotti_visual_scheme_image = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }

        // Carico dati dal controller e aggiorno la lista
        String[] nomiLotti = TheController.getNomiLottiPlaceholder(username);
        model.setValues(nomiLotti);
    }

}