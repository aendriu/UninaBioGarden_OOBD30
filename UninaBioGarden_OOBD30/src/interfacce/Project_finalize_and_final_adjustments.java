package interfacce;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import interfacce.Exceptions.Specific_exceptions.Prop_Project_exceptions;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Project_finalize_and_final_adjustments extends JFrame {
    private static final long serialVersionUID = 1L;
    private Image PropImage;
    private Controller TheController;
    private String username;
    private String projectName;
    private String lottoname;
    private Set<List<String>> Project_layout; // Lista di righe, ogni riga può avere 4 o 5 elementi

    public JTable tableProjectLayout;
    public DefaultTableModel modelTabella;
    public JScrollPane scrollPaneTabella;

    public Project_finalize_and_final_adjustments(String username, Controller TheController, String lottoname, String projectName, Set<List<String>> Project_layout) {
        this.TheController = TheController;
        this.username = username;
        this.projectName = projectName;
        this.Project_layout = Project_layout;
        this.lottoname = lottoname;

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Caricamento immagine
        URL imageUrl =  Project_finalize_and_final_adjustments.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            PropImage = new ImageIcon(imageUrl).getImage();
        }

        JPanel page = new JPanel() {
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
        page.setLayout(null);

        // Label titolo progetto
        JLabel projectNameLabel = new JLabel(projectName, SwingConstants.CENTER);
        projectNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 36));
        projectNameLabel.setForeground(Color.BLACK);
        projectNameLabel.setBounds(200, 20, screenSize.width - 400, 60);
        page.add(projectNameLabel);

        JLabel lblCreatoPerIl = new JLabel("creato per il lotto: " + lottoname + ".", SwingConstants.CENTER);
        lblCreatoPerIl.setForeground(Color.BLACK);
        lblCreatoPerIl.setFont(new Font("Times New Roman", Font.BOLD, 36));
        lblCreatoPerIl.setBounds(200, 87, screenSize.width - 400, 60);
        page.add(lblCreatoPerIl);

        // Intestazioni 5 colonne, ultima "Quantità" vuota se non raccolta
        String[] colonne = { "Coltivatore", "Coltura", "Attività", "Durata (giorni)", "Quantità" };

        // Prepara dati per la tabella
        Object[][] datiTabella = new Object[Project_layout.size()][5];
        int rigaIndex = 0;
        for (List<String> riga : Project_layout) {
            // riga normalmente ha 4 elementi: nome completo, coltura, attività, durata
            // se attività == "Raccolta" c'è un 5° elemento: quantità
            datiTabella[rigaIndex][0] = riga.get(0); // Coltivatore (nome e cognome)
            datiTabella[rigaIndex][1] = riga.get(1); // Coltura
            datiTabella[rigaIndex][2] = riga.get(2); // Attività
            datiTabella[rigaIndex][3] = riga.get(3); // Durata

            if (riga.size() == 5 && riga.get(2).equalsIgnoreCase("Raccolta")) {
                datiTabella[rigaIndex][4] = riga.get(4); // Quantità
            } else {
                datiTabella[rigaIndex][4] = "non possibile"; // Vuoto se non raccolta
            }
            rigaIndex++;
        }

        modelTabella = new DefaultTableModel(datiTabella, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabella non editabile
            }
        };

        tableProjectLayout = new JTable(modelTabella);
        tableProjectLayout.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        tableProjectLayout.setRowHeight(25);
        tableProjectLayout.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
        tableProjectLayout.setFillsViewportHeight(true);

        scrollPaneTabella = new JScrollPane(tableProjectLayout);
        int widthTable = screenSize.width - 400;
        int heightTable = (int)(screenSize.height * 0.5);
        int xTable = 200;
        int yTable = (screenSize.height - heightTable) / 2;

        scrollPaneTabella.setBounds(xTable, yTable, widthTable, heightTable);
        scrollPaneTabella.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneTabella.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        page.add(scrollPaneTabella);

        setContentPane(page);
        
        JButton indietro = new JButton("Torna indietro");
        indietro.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        indietro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int scelta = JOptionPane.showConfirmDialog(
                    null,
                    "Se torni indietro perderai tutte le modifiche fatte.\nSei sicuro di voler continuare?",
                    "Attenzione",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                if (scelta == JOptionPane.YES_OPTION) {
                    TheController.OpenProprietarioLoggedIn_closeCaller(username, Project_finalize_and_final_adjustments.this);
                }
               return;
            }
        });
        indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        indietro.setBounds(10, 747, 483, 60);
        page.add(indietro);
        
        JButton cancella_attività = new JButton("cancella attività");
        cancella_attività.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        cancella_attività.setBounds(1029, 747, 483, 60);
        page.add(cancella_attività);cancella_attività.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int selectedRow = tableProjectLayout.getSelectedRow();
                    if (selectedRow == -1) {
                        throw new Prop_Project_exceptions(Prop_Project_exceptions.Tipo.no_row_selected_4_deletion);
                    }
                    // conferma
                    int conferma = JOptionPane.showConfirmDialog(
                        null,
                        "Sei sicuro di voler cancellare l'attività selezionata da progetto?",
                        "Conferma cancellazione",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );
                    if (conferma == JOptionPane.YES_OPTION) {
                        modelTabella.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(
                            null,
                            "Attività rimossa con successo.",
                            "Informazione",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                } catch (Prop_Project_exceptions ex) {
                    if (ex.getTipo() == Prop_Project_exceptions.Tipo.no_row_selected_4_deletion) {
                        JOptionPane.showMessageDialog(
                            null,
                            ex.getMessage(),
                            "Errore",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

        
        JButton concludi = new JButton("Concludi");
        concludi.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        	try {
        		if (modelTabella.getRowCount() == 0) {
					throw new Prop_Project_exceptions(Prop_Project_exceptions.Tipo.project_was_wiped_out_by_user);
				}
        		
        		JOptionPane.showMessageDialog(
						null,
						"Il progetto è stato finalizzato con successo.",
						"Informazione",
						JOptionPane.INFORMATION_MESSAGE
					);
        		TheController.OpenProprietarioLoggedIn_closeCaller(username, Project_finalize_and_final_adjustments.this); 
        	}catch(Prop_Project_exceptions ex) {
					JOptionPane.showMessageDialog(
							null,
							ex.getMessage(),
							"Errore",
							JOptionPane.ERROR_MESSAGE
						);
					 TheController.OpenPropProgettiVisualScheme_closeCaller(username, Project_finalize_and_final_adjustments.this);
				}
        	}
        });
        concludi.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        concludi.setBounds(521, 747, 483, 60);
        page.add(concludi);

    }
   
}
