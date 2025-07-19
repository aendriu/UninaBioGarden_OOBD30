package interfacce;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.net.URL;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import interfacce.Exceptions.Global_exceptions;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

public class Proprietario_activities_visual extends JFrame {

    private static final long serialVersionUID = 1L;
    private Image proprietarioImage;             
    private String username_prop;                 
    private Controller TheController;     
   

    private JTable activitiesTable;  // aggiunto dichiarazione mancata
    private Proprietario_logged_in pageProprietario;  
    
    public Proprietario_activities_visual(String username_prop, Controller TheController) {
        setResizable(false);
        this.username_prop = username_prop;
        this.TheController = TheController;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dimensione schermo e fullscreen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Carico immagine
        URL imageUrl = Proprietario_activities_visual.class.getResource("Images/image_progetto_logo.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            proprietarioImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }

        // Pannello personalizzato con sfondo
        JPanel attività_panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (proprietarioImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.drawImage(proprietarioImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        attività_panel.setLayout(null);
        setContentPane(attività_panel);

        int spazioBottoni = 100;

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, screenSize.width, screenSize.height - spazioBottoni);
        attività_panel.add(scrollPane);

        // crea tabella
     // crea tabella
        activitiesTable = new JTable();

     // prendo i dati
     List<Object[]> righe = TheController.Riempi_tab_attività_vista_proprietario(username_prop);
     try {
		if (righe.isEmpty()) {
			 throw new Global_exceptions("Non ci sono attività da visualizzare al momento.");
		 }
	} catch (Global_exceptions e) {
		JOptionPane.showMessageDialog(
				this, 
				e.getMessage(), 
				"Errore", 
				JOptionPane.ERROR_MESSAGE
		);
		TheController.OpenProprietarioLoggedIn_closeCaller(username_prop, Proprietario_activities_visual.this);
	}
     righe.sort((a, b) -> ((String) a[0]).compareToIgnoreCase((String) b[0]));

     String[] colonne = {
         "Nome Lotto", "Nome Coltivatore", "Cognome Coltivatore",
         "Nome Attività", "Coltura", "Stato", "Percentuale Completamento"
     };

     Object[][] dati = new Object[righe.size()][colonne.length];
     for (int i = 0; i < righe.size(); i++) {
         Object[] row = righe.get(i);
         Object[] formattedRow = new Object[colonne.length];

         formattedRow[0] = row[0];  
         formattedRow[1] = row[1];  
         formattedRow[2] = row[2];  
         formattedRow[3] = row[3];  
         formattedRow[4] = row[4]; 
         formattedRow[5] = row[5]; 
         formattedRow[6] = row[6]; 

         dati[i] = formattedRow;
     }

     DefaultTableModel model = new DefaultTableModel(
         dati, colonne
     ) {
         Class[] columnTypes = new Class[]{
             String.class, String.class, String.class,
             String.class, String.class, String.class, Integer.class
         };

         @Override
         public Class<?> getColumnClass(int columnIndex) {
             return columnTypes[columnIndex];
         }

         @Override
         public boolean isCellEditable(int row, int column) {
             return false;
         }
     };

     activitiesTable.setModel(model);

     // renderer progress bar sulla colonna percentuale (indice 6)
     activitiesTable.getColumnModel().getColumn(6).setCellRenderer(new javax.swing.table.TableCellRenderer() {
         private final javax.swing.JProgressBar bar = new javax.swing.JProgressBar(0, 100);
         {
             bar.setStringPainted(true);
             bar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
         }
         @Override
         public java.awt.Component getTableCellRendererComponent(
             JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
         ) {
             int progress = 0;
             if (value instanceof Integer) {
                 progress = (Integer) value;
             }
             bar.setValue(progress);
             bar.setString(progress + "%");
             return bar;
         }
     });

     activitiesTable.setFont(new Font("Times New Roman", Font.PLAIN, 20));
     activitiesTable.setRowHeight(30);


        scrollPane.setViewportView(activitiesTable);

        JButton tornaIndietro = new JButton("Torna Indietro");
        tornaIndietro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               TheController.OpenProprietarioLoggedIn_closeCaller(username_prop, Proprietario_activities_visual.this);
            }
        });
        tornaIndietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        tornaIndietro.setBounds(645, screenSize.height - 80, 210, 43);
        attività_panel.add(tornaIndietro);
    }

}
