package damose;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.onebusaway.gtfs.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class StatsPanel extends JPanel {

    private Frame frame;
    private JButton btnClose, btnAgency;
    private JLabel codice, agenziaENome;
    private ChartPanel chartPanel;

    // Costruttore generico
    public StatsPanel(Frame frame) {
        this.frame = frame;

        this.setBackground(new Color(130, 36, 51));
        this.setLayout(null);
        this.setVisible(false);


        // Pulsante per chiudere il lineaPanel
        btnClose = new JButton(" Torna indietro");

        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnClose.setFont(new Font("Arial Nova", Font.BOLD, 14));
        btnClose.setForeground(Color.WHITE);

        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setContentAreaFilled(false);

        btnClose.setBounds(-25, 5, 200, 30);

        ImageIcon iconClose = new ImageIcon("src/resources/indietro.png");
        Image scaledImageClose = iconClose.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon newIconClose = new ImageIcon(scaledImageClose);
        btnClose.setIcon(newIconClose);

        this.add(btnClose);


        // JLabel che contiene il nome (long name) della linea in questione
        codice = new JLabel("Codice linea / fermata");

        codice.setForeground(Color.WHITE);
        codice.setFocusable(false);

        codice.setBounds(80, 70, 180, 50);

        this.add(codice);

        // JLabel che contiene il nome dell'agenzia che gestisce la linea in questione
        agenziaENome = new JLabel("Agenzia  -  Nome linea");

        agenziaENome.setForeground(new Color(210, 210, 210));
        agenziaENome.setFont(new Font("Arial Nova", Font.ITALIC, 12));
        agenziaENome.setFocusable(false);

        agenziaENome.setBounds(20, 125, 300, 20);

        this.add(agenziaENome);

        // Pulsante per il logo dell'agenzia o il puntatore della fermata
        btnAgency = new JButton();

        btnAgency.setContentAreaFilled(false);
        btnAgency.setFocusPainted(false);
        btnAgency.setBorderPainted(false);
        btnAgency.setBackground(new Color(255, 255, 255));

        btnAgency.setFocusable(false);

        this.add(btnAgency);
    }

    // Funzione in caso di statistiche per la linea
    public void creaPannelloStatistiche(Route linea) throws IOException {
        this.setVisible(true);

        frame.getRoutePanel().setVisible(false);
        frame.getStopPanel().setVisible(false);

        // Configurazione per l'icona dell'agenzia della linea
        btnAgency.setPreferredSize(new Dimension(50, 50));
        btnAgency.setBounds(20, 70, 50, 50);

        // Variabili che contengono informazioni sulla linea (agenzia, long name e short name)
        String agencyName = linea.getAgency().getName();
        String longName = linea.getLongName();
        String shortName = linea.getShortName();

        // Visualizzazione dei nomi (long name e short name) assegnati alla linea e del nome dell'agenzia che la gestisce
        codice.setText(" " + shortName);
        codice.setFont(new Font("Arial Nova", Font.BOLD, 30));
        codice.setBounds(80, 70, 180, 50);

        agenziaENome.setBounds(20, 125, 300, 20);

        if (longName == null || longName.isEmpty()) {
            agenziaENome.setText(agencyName);
        } else {
            agenziaENome.setText(agencyName + "  -  " + longName);
        }

        for (ActionListener a : btnClose.getActionListeners()) {
            btnClose.removeActionListener(a);
        }
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StatsPanel.this.setVisible(false);
                frame.getRoutePanel().creaPannelloLinea(linea);
            }
        });


        // Visualizzazione dell'eventuale logo dell'agenzia che gestisce la linea in base a agencyName
        switch (agencyName) {
            case "Atac":
                btnAgency.setVisible(true);

                ImageIcon iconAtac = new ImageIcon("src/resources/atac-logo.png");
                Image scaledImageAtac = iconAtac.getImage().getScaledInstance(65, 45, Image.SCALE_SMOOTH);
                ImageIcon newIconAtac = new ImageIcon(scaledImageAtac);
                btnAgency.setIcon(newIconAtac);

                codice.setBounds(80, 70, 180, 50);

                break;

            case "Autoservizi Troiani":
                btnAgency.setVisible(true);

                ImageIcon iconTroiani = new ImageIcon("src/resources/autoservizi-troiani-logo.png");
                Image scaledImageTroiani = iconTroiani.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon newIconTroiani = new ImageIcon(scaledImageTroiani);
                btnAgency.setIcon(newIconTroiani);

                codice.setBounds(80, 70, 180, 50);

                break;

            default:
                btnAgency.setVisible(false);
                codice.setBounds(12, 70, 180, 50);

                break;
        }

        // Visualizzazione del tipo di linea (tram, metropolitana, treno, autobus) in base alla variabile routeType
        int routeType = linea.getType();

        switch (shortName) {
            case "MEA":
                ImageIcon iconMetroA = new ImageIcon("src/resources/metro-a-logo-withborder.png");
                Image scaledImageMetroA = iconMetroA.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon newIconMetroA = new ImageIcon(scaledImageMetroA);
                btnAgency.setIcon(newIconMetroA);

                codice.setText(" Metro A");

                break;

            case "MEB":
                ImageIcon iconMetroB = new ImageIcon("src/resources/metro-b-logo-withborder.png");
                Image scaledImageMetroB = iconMetroB.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon newIconMetroB = new ImageIcon(scaledImageMetroB);
                btnAgency.setIcon(newIconMetroB);

                codice.setText(" Metro B");

                break;

            case "MEB1":
                ImageIcon iconMetroB1 = new ImageIcon("src/resources/metro-b-logo-withborder.png");
                Image scaledImageMetroB1 = iconMetroB1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon newIconMetroB1 = new ImageIcon(scaledImageMetroB1);
                btnAgency.setIcon(newIconMetroB1);

                codice.setText(" Metro B1");

                break;

            case "MEC":
                ImageIcon iconMetroC = new ImageIcon("src/resources/metro-c-logo-withborder.png");
                Image scaledImageMetroC = iconMetroC.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon newIconMetroC = new ImageIcon(scaledImageMetroC);
                btnAgency.setIcon(newIconMetroC);

                codice.setText(" Metro C");

                break;
        }

        if (this.chartPanel != null) {
            this.remove(this.chartPanel);
            this.chartPanel = null;
        }


        ArrayList<String> statistiche = this.ottieniStatistiche(linea);

        int puntuali = Integer.valueOf(statistiche.get(2));
        int ritardati = Integer.valueOf(statistiche.get(3));
        int cancellati = Integer.valueOf(statistiche.get(4));
        int duplicati = Integer.valueOf(statistiche.get(5));
        int anticipati = Integer.valueOf(statistiche.get(6));

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("PUNTUALI", puntuali);
        dataset.setValue("RITARDATI", ritardati);
        dataset.setValue("CANCELLATI", cancellati);
        dataset.setValue("DUPLICATI", duplicati);
        dataset.setValue("ANTICIPATI", anticipati);

        JFreeChart chart = ChartFactory.createPieChart(
                null,
                dataset,
                true,
                false,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();

        plot.setSectionPaint("PUNTUALI", new Color(144, 238, 144));
        plot.setSectionPaint("CANCELLATI", new Color(255, 102, 194));
        plot.setSectionPaint("RITARDATI", new Color(255, 179, 71));
        plot.setSectionPaint("DUPLICATI", new Color(180, 180, 180));
        plot.setSectionPaint("ANTICIPATI", new Color(135, 206, 250));

        plot.setShadowPaint(null);
        plot.setSectionOutlinesVisible(false);

        plot.setLabelFont(new Font("Arial Nova", Font.BOLD, 10));
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({1})"));

        chart.setBackgroundPaint(new Color(130, 36, 51));
        plot.setBackgroundPaint(new Color(130, 36, 51));
        plot.setOutlineVisible(false);

        LegendTitle legend = chart.getLegend();
        legend.setItemPaint(Color.WHITE);
        legend.setBackgroundPaint(new Color(0, 0, 0, 0));
        legend.setFrame(BlockBorder.NONE);

        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder());
        chartPanel.setBackground(new Color(130, 36, 51));
        chartPanel.setBounds(0, 150, 350, 350);

        this.add(chartPanel);
    }


    // Funzione in caso di statistiche per la fermata
    public void creaPannelloStatistiche(Stop fermata) {
        this.setVisible(true);

        frame.getRoutePanel().setVisible(false);
        frame.getStopPanel().setVisible(false);

        for (ActionListener a : btnClose.getActionListeners()) { btnClose.removeActionListener(a); }
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StatsPanel.this.setVisible(false);
                frame.getStopPanel().creaPannelloFermata(fermata);
            }
        });

        if (this.chartPanel != null) {
            this.remove(this.chartPanel);
            this.chartPanel = null;
        }


        // Variabili che contengono informazioni sulla fermata (nome, ID)
        String name = fermata.getName();
        String id = fermata.getId().getId();


        // Visualizzazione del nome e dell'ID della fermata
        agenziaENome.setText("ID: " + id);
        agenziaENome.setBounds(20, 120, 300, 20);

        codice.setText(name);
        codice.setFont(new Font("Arial Nova", Font.BOLD, 22));
        codice.setBounds(70, 65, 200, 50);

        // Configurazione per l'icona della fermata
        btnAgency.setPreferredSize(new Dimension(40, 40));
        btnAgency.setBounds(20, 70, 40, 40);

        ImageIcon iconStop = new ImageIcon("src/resources/fermata-bianco.png");
        Image scaledImageStop = iconStop.getImage().getScaledInstance(32, 40, Image.SCALE_SMOOTH);
        ImageIcon newIconStop = new ImageIcon(scaledImageStop);
        btnAgency.setIcon(newIconStop);

        this.add(btnAgency);

        ArrayList<String> statistiche = this.ottieniStatistiche(fermata);

        int puntuali = Integer.valueOf(statistiche.get(2));
        int ritardati = Integer.valueOf(statistiche.get(3));
        int saltati = Integer.valueOf(statistiche.get(4));
        int anticipati = Integer.valueOf(statistiche.get(6));
        int noData = Integer.valueOf(statistiche.get(5));

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("PUNTUALI", puntuali);
        dataset.setValue("RITARDATI", ritardati);
        dataset.setValue("SALTATI", saltati);
        dataset.setValue("ANTICIPATI", anticipati);
        dataset.setValue("NO DATA", noData);

        JFreeChart chart = ChartFactory.createPieChart(
                null,
                dataset,
                true,
                false,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();

        plot.setSectionPaint("PUNTUALI", new Color(144, 238, 144));
        plot.setSectionPaint("SALTATI", new Color(255, 102, 194));
        plot.setSectionPaint("RITARDATI", new Color(255, 179, 71));
        plot.setSectionPaint("NO DATA", new Color(180, 180, 180));
        plot.setSectionPaint("ANTICIPATI", new Color(135, 206, 250));

        plot.setShadowPaint(null);
        plot.setSectionOutlinesVisible(false);

        plot.setLabelFont(new Font("Arial Nova", Font.BOLD, 10));
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({1})"));

        chart.setBackgroundPaint(new Color(130, 36, 51));
        plot.setBackgroundPaint(new Color(130, 36, 51));
        plot.setOutlineVisible(false);

        LegendTitle legend = chart.getLegend();
        legend.setItemPaint(Color.WHITE);
        legend.setBackgroundPaint(new Color(0, 0, 0, 0));
        legend.setFrame(BlockBorder.NONE);

        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder());
        chartPanel.setBackground(new Color(130, 36, 51));
        chartPanel.setBounds(0, 150, 350, 350);

        this.add(chartPanel);



    }

    public ArrayList<String> ottieniStatistiche(Route linea) {
        File fileLinea = new File("files/linee/storico_" + linea.getId().getId() + ".txt");
        ArrayList<String> statistiche = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileLinea))) {
            int count = 0;
            int sommaRitardi = 0;

            int puntuali = 0;
            int ritardati = 0;
            int anticipati = 0;
            int cancellati = 0;
            int duplicati = 0;

            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] parti = riga.split(",");
                if (parti.length >= 5) {
                    String tripId = parti[0];
                    String data = parti[1];
                    String orario = parti[2];
                    String ritardo = parti[3];
                    String stato = parti[4];

                    sommaRitardi += Integer.parseInt(ritardo);
                    count += 1;

                    switch (stato) {
                        case "RITARDATO":
                            ritardati += 1;
                            break;
                        case "PUNTUALE":
                            puntuali += 1;
                            break;
                        case "CANCELLATO":
                            cancellati += 1;
                            break;
                        case "EXTRA":
                            duplicati += 1;
                            break;
                        case "ANTICIPO":
                            anticipati += 1;
                            break;
                    }

                }
                else System.err.println("Riga non valida: "+ riga);
            }

            int media = sommaRitardi/count;
            statistiche.add(String.valueOf(count));
            statistiche.add(String.valueOf(media));
            statistiche.add(String.valueOf(puntuali));
            statistiche.add(String.valueOf(ritardati));
            statistiche.add(String.valueOf(cancellati));
            statistiche.add(String.valueOf(duplicati));
            statistiche.add(String.valueOf(anticipati));

            return statistiche;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> ottieniStatistiche(Stop fermata) {
        File fileFermata = new File("files/fermate/storico_" + fermata.getId().getId() + ".txt");
        ArrayList<String> statistiche = new ArrayList<>();

        int count = 0;
        int sommaRitardi = 0;

        int puntuali = 0;
        int ritardati = 0;
        int anticipati = 0;
        int saltate = 0;
        int noData = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileFermata))) {
            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] parti = riga.split(",");
                if (parti.length >= 5) {
                    String tripId = parti[0];
                    String data = parti[1];
                    String orario = parti[2];
                    String ritardo = parti[3];
                    String stato = parti[4];

                    sommaRitardi += Integer.parseInt(ritardo);
                    count += 1;

                    switch (stato) {
                        case "RITARDATO":
                            ritardati += 1;
                            break;
                        case "PUNTUALE":
                            puntuali += 1;
                            break;
                        case "SALTATA":
                            saltate += 1;
                            break;
                        case "NO_DATA":
                            noData += 1;
                            break;
                        case "ANTICIPO":
                            anticipati += 1;
                            break;
                    }

                }
                else System.err.println("Riga non valida: "+ riga);
            }

            int media = sommaRitardi/count;
            statistiche.add(String.valueOf(count));
            statistiche.add(String.valueOf(media));
            statistiche.add(String.valueOf(puntuali));
            statistiche.add(String.valueOf(ritardati));
            statistiche.add(String.valueOf(saltate));
            statistiche.add(String.valueOf(anticipati));
            statistiche.add(String.valueOf(noData));

            return statistiche;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
