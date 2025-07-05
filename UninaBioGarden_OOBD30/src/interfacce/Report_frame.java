package interfacce;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import controller.Controller;

public class Report_frame extends JFrame {
    private Controller TheController;
    private JLabel title_reports;
    private JButton torna_indietro;
    private JList<String> lottoList;
    private DefaultListModel<String> listModel;
    private JPanel chartContainer;
    private JScrollPane chartScrollPane;
    private ChartPanel chartPanel;
    private Image PropImage;
    private String username;

    public Report_frame(String username, Controller controller) {
    	setResizable(false);
        this.TheController = controller;
        this.username = username;
        initUI();
        loadLotti();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // Pannello con sfondo
        JPanel pageProp = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (PropImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.drawImage(PropImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        pageProp.setLayout(new BorderLayout());
        setContentPane(pageProp);

        // Carica icona e immagine sfondo
        URL imageUrl = Login.class.getResource("Images/PLACEHOLDER_LOGO.jpg");
        if (imageUrl != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
            PropImage = new ImageIcon(imageUrl).getImage();
        } else {
            System.out.println("Immagine non trovata!");
        }

        // Label titolo
        title_reports = new JLabel("Report per i lotti di " + username, SwingConstants.CENTER);
        title_reports.setPreferredSize(new Dimension(getWidth(), 40));
        title_reports.setFont(new Font("Times New Roman", Font.BOLD, 20));
        pageProp.add(title_reports, BorderLayout.NORTH);

        // Bottone torna indietro
        torna_indietro = new JButton("Torna indietro");
        torna_indietro.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        torna_indietro.setAlignmentX(Component.CENTER_ALIGNMENT);
        torna_indietro.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        torna_indietro.setVisible(true);
        torna_indietro.addActionListener(e -> {
            Proprietario_logged_in frame = new Proprietario_logged_in(username, TheController);
            frame.setVisible(true);
            dispose();
        });

        // Lista sinistra
        listModel = new DefaultListModel<>();
        lottoList = new JList<>(listModel);
        lottoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lottoList.setFixedCellWidth(300);
        lottoList.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        JScrollPane listScrollPane = new JScrollPane(lottoList);
        listScrollPane.setPreferredSize(new Dimension(300, 0)); // larghezza fissa

        // Pannello sinistro con BoxLayout verticale: lista + spazio + bottone
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(listScrollPane);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // spazio
        leftPanel.add(torna_indietro);

        pageProp.add(leftPanel, BorderLayout.WEST);

        // Contenitore grafico a destra
        chartContainer = new JPanel(new BorderLayout());
        chartScrollPane = new JScrollPane(chartContainer);
        pageProp.add(chartScrollPane, BorderLayout.CENTER);

        // Listener lista selezione
        lottoList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int idx = lottoList.getSelectedIndex();
                if (idx >= 0) {
                    String nomeLotto = listModel.get(idx);
                    List<Map<String, Object>> raccolte = TheController.getDatiRaccoltaPerLotto(username, nomeLotto);
                    mostraGrafico(nomeLotto, raccolte);
                }
            }
        });
    }

    private void loadLotti() {
        listModel.clear();
        List<String> nomiLotti = TheController.getNomiLotti(username);
        for (String nome : nomiLotti) {
            listModel.addElement(nome);
        }
        if (!nomiLotti.isEmpty()) {
            lottoList.setSelectedIndex(0);
        }
    }

    private void mostraGrafico(String nomeLotto, List<Map<String, Object>> raccolte) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        double globalMin = Double.MAX_VALUE;
        double globalMax = -Double.MAX_VALUE;

        for (Map<String, Object> info : raccolte) {
            String coltura = (String) info.get("coltura");
            Double media = (Double) info.get("media");
            Double min = (Double) info.get("min");
            Double max = (Double) info.get("max");

            dataset.addValue(media, "Media", coltura);
            dataset.addValue(min, "Min", coltura);
            dataset.addValue(max, "Max", coltura);

            if (min < globalMin) globalMin = min;
            if (media < globalMin) globalMin = media;
            if (max < globalMin) globalMin = max;

            if (min > globalMax) globalMax = min;
            if (media > globalMax) globalMax = media;
            if (max > globalMax) globalMax = max;
        }

        JFreeChart chart = ChartFactory.createBarChart(
                nomeLotto,
                "Coltura",
                "quantità raccolti",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        CategoryPlot plot = chart.getCategoryPlot();

        // Font assi e label
        plot.getDomainAxis().setTickLabelFont(new Font("Times New Roman", Font.PLAIN, 20));
        plot.getRangeAxis().setTickLabelFont(new Font("Times New Roman", Font.PLAIN, 20));
        plot.getDomainAxis().setLabelFont(new Font("Times New Roman", Font.PLAIN, 20));
        plot.getRangeAxis().setLabelFont(new Font("Times New Roman", Font.PLAIN, 20));

        // Range dinamico + margine
        double lowerBound = globalMin;
        double upperBound = globalMax + 20;

        if (lowerBound > 0) lowerBound = 0;

        plot.getRangeAxis().setRange(lowerBound, upperBound);

        plot.getDomainAxis().setCategoryMargin(0.4);
        plot.getDomainAxis().setLowerMargin(0.05);
        plot.getDomainAxis().setUpperMargin(0.05);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setItemMargin(0.1);

        if (chartPanel != null) {
            chartContainer.remove(chartPanel);
        }

        chartPanel = new ChartPanel(chart);
        // NON impostare dimensioni fisse, lascia gestire al layout e allo scrollpane
        chartContainer.add(chartPanel, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }
}
