package damose;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
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


public class StatsPanel extends JPanel {

    private Frame frame;
    private JButton btnBack, btnAgency;
    private JLabel codice, agenziaENome, lblDettagli, lblExtra;
    private ChartPanel chartPanel;
    private Trip viaggioDaVisualizzare;
    private Stop fermataDaVisualizzare;


    // Costruttore del pannello statsPanel
    public StatsPanel(Frame frame) {

        this.frame = frame;

        this.setBackground(new Color(130, 36, 51));
        this.setLayout(null);
        this.setVisible(false);


        // Pulsante per chiudere lo statsPanel
        btnBack = new JButton(" Torna indietro");

        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnBack.setFont(new Font("Arial Nova", Font.BOLD, 14));
        btnBack.setForeground(Color.WHITE);

        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setContentAreaFilled(false);

        btnBack.setBounds(-25, 5, 200, 30);

        ImageIcon iconBack = new ImageIcon("src/resources/assets/indietro.png");
        Image scaledImageBack = iconBack.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon newIconBack = new ImageIcon(scaledImageBack);
        btnBack.setIcon(newIconBack);

        this.add(btnBack);


        // JLabel che ospiterà il codice della linea o il nome della fermata relativi allo statsPanel
        codice = new JLabel("Codice linea / fermata");

        codice.setForeground(Color.WHITE);
        codice.setFocusable(false);

        codice.setBounds(80, 70, 180, 50);

        this.add(codice);


        // JLabel che ospiterà il nome dell'agenzia ed eventualmente il long name (linea), oppure l'ID (fermata)
        agenziaENome = new JLabel("Agenzia  -  Nome linea");

        agenziaENome.setForeground(new Color(210, 210, 210));
        agenziaENome.setFont(new Font("Arial Nova", Font.ITALIC, 12));
        agenziaENome.setFocusable(false);

        agenziaENome.setBounds(20, 125, 300, 20);

        this.add(agenziaENome);


        // Pulsante per il logo dell'agenzia (linea), oppure per l'icona del puntatore (fermata)
        btnAgency = new JButton();

        btnAgency.setContentAreaFilled(false);
        btnAgency.setFocusPainted(false);
        btnAgency.setBorderPainted(false);
        btnAgency.setBackground(new Color(255, 255, 255));

        btnAgency.setFocusable(false);

        this.add(btnAgency);


        // Titolo per statistiche extra
        lblDettagli = new JLabel("Dettagli:");

        lblDettagli.setForeground(Color.WHITE);
        lblDettagli.setFont(new Font("Arial Nova", Font.BOLD, 22));
        lblDettagli.setFocusable(false);

        lblDettagli.setBounds(10, 520, 300, 30);

        this.add(lblDettagli);

        // Testo per i dettagli extra
        lblExtra= new JLabel();

        lblExtra.setForeground(Color.WHITE);
        lblExtra.setFont(new Font("Arial Nova", Font.PLAIN, 15));
        lblExtra.setFocusable(false);

        lblExtra.setBounds(10, 560, 400, 300);

        this.add(lblExtra);
    }


// ---------------------------------------------------------------------------------------------


    // Metodo get per il viaggioDaVisualizzare associato allo statsPanel
    public Trip getViaggioDaVisualizzare() {
        return this.viaggioDaVisualizzare;
    }


    // Metodo get per la fermataDaVisualizzare associata allo statsPanel
    public Stop getFermataDaVisualizzare() {
        return this.fermataDaVisualizzare;
    }


// ---------------------------------------------------------------------------------------------


    // Metodo che "costruisce" concretamente lo statsPanel in base alla linea del routePanel associato ad esso
    public void creaPannelloStatistiche(Route linea) throws IOException {

        // Ottenimento del viaggio da visualizzare dal routePanel associato
        this.viaggioDaVisualizzare = frame.getRoutePanel().getViaggiDaVisualizzare().get(frame.getRoutePanel().getIndiceViaggioVisualizzato());
        this.fermataDaVisualizzare = null;


        // Visualizzazione dello statsPanel e disattivazione di eventuali stopPanel e routePanel precedentemente visibili
        this.setVisible(true);

        frame.getRoutePanel().setVisible(false);
        frame.getStopPanel().setVisible(false);


        // Rimozione di eventuali chartPanel precedenti (necessario per evitare overlap)
        if (this.chartPanel != null) {
            this.remove(this.chartPanel);
            this.chartPanel = null;
        }


        // Rimozione di eventuali ActionListener precedenti dal btnBack (necessario per evitare overlap)
        for (ActionListener a : btnBack.getActionListeners()) { btnBack.removeActionListener(a); }


        // Funzionalità per il pulsante btnBack, che re-istanzia il routePanel associato
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                StatsPanel.this.setVisible(false);
                frame.getRoutePanel().creaPannelloLinea(linea);
            }
        });


        // Configurazione per l'icona dell'agenzia della linea
        btnAgency.setPreferredSize(new Dimension(50, 50));
        btnAgency.setBounds(20, 70, 50, 50);


        // Variabili che contengono informazioni sulla linea (agenzia, long name e short name)
        String agencyName = linea.getAgency().getName();
        String longName = linea.getLongName();
        String shortName = linea.getShortName();


        // Visualizzazione dell'eventuale logo dell'agenzia che gestisce la linea in base a agencyName
        switch (agencyName) {
            case "Atac":
                btnAgency.setVisible(true);

                ImageIcon iconAtac = new ImageIcon("src/resources/assets/atac-logo.png");
                Image scaledImageAtac = iconAtac.getImage().getScaledInstance(65, 45, Image.SCALE_SMOOTH);
                ImageIcon newIconAtac = new ImageIcon(scaledImageAtac);
                btnAgency.setIcon(newIconAtac);

                codice.setBounds(80, 70, 180, 50);

                break;

            case "Autoservizi Troiani":
                btnAgency.setVisible(true);

                ImageIcon iconTroiani = new ImageIcon("src/resources/assets/autoservizi-troiani-logo.png");
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


        // Visualizzazione dei nomi (long name e short name) assegnati alla linea e del nome dell'agenzia che la gestisce
        codice.setText(" " + shortName);
        codice.setFont(new Font("Arial Nova", Font.BOLD, 30));
        codice.setBounds(80, 70, 180, 50);

        agenziaENome.setBounds(20, 125, 300, 20);

        if (longName == null || longName.isEmpty()) agenziaENome.setText(agencyName);
        else agenziaENome.setText(agencyName + "  -  " + longName);


        // Assegnamento speciale di icona al btnAgency e di testo a codice in caso la linea sia una metropolitana
        switch (shortName) {
            case "MEA":
                ImageIcon iconMetroA = new ImageIcon("src/resources/assets/metro-a-logo-withborder.png");
                Image scaledImageMetroA = iconMetroA.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon newIconMetroA = new ImageIcon(scaledImageMetroA);
                btnAgency.setIcon(newIconMetroA);

                codice.setText(" Metro A");

                break;

            case "MEB":
                ImageIcon iconMetroB = new ImageIcon("src/resources/assets/metro-b-logo-withborder.png");
                Image scaledImageMetroB = iconMetroB.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon newIconMetroB = new ImageIcon(scaledImageMetroB);
                btnAgency.setIcon(newIconMetroB);

                codice.setText(" Metro B");

                break;

            case "MEB1":
                ImageIcon iconMetroB1 = new ImageIcon("src/resources/assets/metro-b-logo-withborder.png");
                Image scaledImageMetroB1 = iconMetroB1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon newIconMetroB1 = new ImageIcon(scaledImageMetroB1);
                btnAgency.setIcon(newIconMetroB1);

                codice.setText(" Metro B1");

                break;

            case "MEC":
                ImageIcon iconMetroC = new ImageIcon("src/resources/assets/metro-c-logo-withborder.png");
                Image scaledImageMetroC = iconMetroC.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon newIconMetroC = new ImageIcon(scaledImageMetroC);
                btnAgency.setIcon(newIconMetroC);

                codice.setText(" Metro C");

                break;
        }


        // Ottenimento delle statistiche da visualizzare relativamente alla linea considerata
        ArrayList<String> statistiche = this.ottieniStatistiche(linea);

        int numero = Integer.valueOf(statistiche.get(0));
        int mediaRitardi = Integer.valueOf(statistiche.get(1));
        int mediaAnticipi = Integer.valueOf(statistiche.get(2));
        int puntuali = Integer.valueOf(statistiche.get(3));
        int ritardati = Integer.valueOf(statistiche.get(4));
        int cancellati = Integer.valueOf(statistiche.get(5));
        int duplicati = Integer.valueOf(statistiche.get(6));
        int anticipati = Integer.valueOf(statistiche.get(7));
        int minimoRitardo = Integer.valueOf(statistiche.get(8));
        int minimoAnticipo = Integer.valueOf(statistiche.get(9));
        int massimoRitardo = Integer.valueOf(statistiche.get(10));
        int massimoAnticipo = Integer.valueOf(statistiche.get(11));


        // Configurazione del DefaultPieDataset utilizzando i valori appena ottenuti
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("PUNTUALI", puntuali);
        dataset.setValue("RITARDATI", ritardati);
        dataset.setValue("CANCELLATI", cancellati);
        dataset.setValue("DUPLICATI", duplicati);
        dataset.setValue("ANTICIPATI", anticipati);


        // Istanziamento del grafico a torta che ospiterà i dati ottenuti
        JFreeChart chart = ChartFactory.createPieChart(
                null,
                dataset,
                true,
                false,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();

        plot.setSectionPaint("PUNTUALI", new Color(57, 194, 57));
        plot.setSectionPaint("CANCELLATI", new Color(220, 87, 166));
        plot.setSectionPaint("RITARDATI", new Color(241, 124, 29));
        plot.setSectionPaint("DUPLICATI", new Color(211, 211, 211));
        plot.setSectionPaint("ANTICIPATI", new Color(92, 168, 215));

        plot.setShadowPaint(null);
        plot.setSectionOutlinesVisible(false);

        plot.setLabelFont(new Font("Arial Nova", Font.BOLD, 10));
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})"));

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

        String ritardoMinString = formattaTempo(minimoRitardo);
        String ritardoMedioString = formattaTempo(mediaRitardi);
        String ritardoMaxString = formattaTempo(massimoRitardo);

        String anticipoMinString = formattaTempo(minimoAnticipo);
        String anticipoMedioString = formattaTempo(mediaAnticipi);
        String anticipoMaxString = formattaTempo(massimoAnticipo);

        this.lblExtra.setText("<html>" +
                "<div style='font-size:10px; font-style:italic; margin-bottom:20px;'>Statistiche ottenute su <b>" + numero + "</b> viaggi totali.</div>" +
                "<div><b>Ritardo minore:</b> " + ritardoMinString + "</div>" +
                "<div><b>Ritardo maggiore:</b> " + ritardoMaxString + "</div>" +
                "<div style='padding-bottom:15px;'><b>Ritardo medio:</b> " + ritardoMedioString + "</div>" +
                "<div><b>Anticipo minore:</b> - " + anticipoMaxString + "</div>" +
                "<div><b>Anticipo maggiore:</b> - " + anticipoMinString + "</div>" +
                "<div style='padding-bottom:15px;'><b>Anticipo medio:</b> - " + anticipoMedioString + "</div>" +
                "<div><b>Numero viaggi puntuali:</b> " + puntuali + "</div>" +
                "<div><b>Numero viaggi in ritardo:</b> " + ritardati + "</div>" +
                "<div><b>Numero viaggi in anticipo:</b> " + anticipati + "</div>" +
                "<div><b>Numero viaggi cancellati:</b> " + cancellati + "</div>" +
                "<div><b>Numero viaggi duplicati:</b> " + duplicati + "</div>" +
                "</html>");

        this.lblExtra.repaint();
        this.lblExtra.revalidate();
        this.chartPanel.repaint();
        this.chartPanel.revalidate();

        this.repaint();
    }


    // Metodo che "costruisce" concretamente lo statsPanel in base alla fermata dello stopPanel associato ad esso
    public void creaPannelloStatistiche(Stop fermata) {

        // Ottenimento della fermata da visualizzare dallo stopPanel associato
        this.viaggioDaVisualizzare = null;
        this.fermataDaVisualizzare = frame.getDati().cercaFermataByID(frame.getStopPanel().getCodiceFermata().substring(4));


        // Visualizzazione dello statsPanel e disattivazione di eventuali stopPanel e routePanel precedentemente visibili
        this.setVisible(true);

        frame.getRoutePanel().setVisible(false);
        frame.getStopPanel().setVisible(false);


        // Rimozione di eventuali chartPanel precedenti (necessario per evitare overlap)
        if (this.chartPanel != null) {
            this.remove(this.chartPanel);
            this.chartPanel = null;
        }


        // Rimozione di eventuali ActionListener precedenti dal btnBack (necessario per evitare overlap)
        for (ActionListener a : btnBack.getActionListeners()) { btnBack.removeActionListener(a); }


        // Funzionalità per il pulsante btnBack, che re-istanzia lo stopPanel associato
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                StatsPanel.this.setVisible(false);
                frame.getStopPanel().creaPannelloFermata(fermata);
            }
        });


        // Configurazione per l'icona della fermata
        btnAgency.setPreferredSize(new Dimension(40, 40));
        btnAgency.setBounds(20, 70, 40, 40);

        ImageIcon iconStop = new ImageIcon("src/resources/assets/fermata-bianco.png");
        Image scaledImageStop = iconStop.getImage().getScaledInstance(32, 40, Image.SCALE_SMOOTH);
        ImageIcon newIconStop = new ImageIcon(scaledImageStop);
        btnAgency.setIcon(newIconStop);


        // Variabili che contengono informazioni sulla fermata (nome, ID)
        String name = fermata.getName();
        String id = fermata.getId().getId();


        // Visualizzazione del nome e dell'ID della fermata
        agenziaENome.setText("ID: " + id);
        agenziaENome.setBounds(20, 120, 300, 20);

        codice.setText(name);
        codice.setFont(new Font("Arial Nova", Font.BOLD, 22));
        codice.setBounds(70, 65, 200, 50);


        // Ottenimento delle statistiche da visualizzare relativamente alla fermata considerata
        ArrayList<String> statistiche = this.ottieniStatistiche(fermata);

        int puntuali = Integer.valueOf(statistiche.get(2));
        int ritardati = Integer.valueOf(statistiche.get(3));
        int saltati = Integer.valueOf(statistiche.get(4));
        int anticipati = Integer.valueOf(statistiche.get(6));
        int noData = Integer.valueOf(statistiche.get(5));


        // Configurazione del DefaultPieDataset utilizzando i valori appena ottenuti
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("PUNTUALI", puntuali);
        dataset.setValue("RITARDATI", ritardati);
        dataset.setValue("SALTATI", saltati);
        dataset.setValue("ANTICIPATI", anticipati);
        dataset.setValue("NO DATA", noData);


        // Istanziamento del grafico a torta che ospiterà i dati ottenuti
        JFreeChart chart = ChartFactory.createPieChart(
                null,
                dataset,
                true,
                false,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();

        plot.setSectionPaint("PUNTUALI", new Color(57, 194, 57));
        plot.setSectionPaint("SALTATI", new Color(220, 87, 166));
        plot.setSectionPaint("RITARDATI", new Color(241, 124, 29));
        plot.setSectionPaint("NO DATA", new Color(211, 211, 211));
        plot.setSectionPaint("ANTICIPATI", new Color(92, 168, 215));

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


// ---------------------------------------------------------------------------------------------


    // Metodo per formattare velocemente da secondi a minuti avendo la stringa stritta per bene
    private String formattaTempo(int secondiTotali) {
        int secondiAssoluti = Math.abs(secondiTotali);     // rimuove segno negativo se presente

        int minuti = secondiAssoluti / 60;
        int secondi = secondiAssoluti % 60;

        StringBuilder sb = new StringBuilder();

        if (minuti > 0) {
            sb.append(minuti).append(" ").append(minuti == 1 ? "minuto" : "minuti");
        }

        if (secondi > 0) {
            if (minuti > 0) sb.append(" e ");
            sb.append(secondi).append(" ").append(secondi == 1 ? "secondo" : "secondi");
        }

        if (minuti == 0 && secondi == 0) {
            sb.append("0 secondi");
        }

        return sb.toString();
    }


    // Metodo che restituisce le statistiche relative a una determinata linea
    public ArrayList<String> ottieniStatistiche(Route linea) {

        // Ottenimento del file relativo alla linea, e istanziamento di un ArrayList che ospiterà le statistiche della stessa
        File fileLinea = new File("src/resources/files/linee/storico_" + linea.getId().getId() + ".txt");
        ArrayList<String> statistiche = new ArrayList<>();


        // Lettura del file relativo alla linea
        try (BufferedReader reader = new BufferedReader(new FileReader(fileLinea))) {

            // Istanziamento di varie variabili che verranno utilizzate per il conteggio delle statistiche
            int count = 0;
            int sommaRitardi = 0;
            int sommaAnticipi = 0;

            int massimoRitardo = Integer.MIN_VALUE;
            int minimoRitardo = Integer.MAX_VALUE;

            int massimoAnticipo = Integer.MIN_VALUE;
            int minimoAnticipo = Integer.MAX_VALUE;


            int puntuali = 0;
            int ritardati = 0;
            int anticipati = 0;
            int cancellati = 0;
            int duplicati = 0;


            // Lettura delle varie righe del file, e parsing dei dati trovati su di esse
            String riga;

            while ((riga = reader.readLine()) != null) {

                String[] parti = riga.split(",");
                if (parti.length >= 5) {

                    String tripId = parti[0];
                    String data = parti[1];
                    String orario = parti[2];
                    int ritardo = Integer.parseInt(parti[3]);
                    String stato = parti[4];

                    count += 1;



                    switch (stato) {
                        case "RITARDATO":
                            ritardati += 1;
                            sommaRitardi += ritardo;

                            if (ritardo > 0) {
                                if (ritardo > massimoRitardo) massimoRitardo = ritardo;
                                if (ritardo < minimoRitardo) minimoRitardo = ritardo;
                            }

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
                            sommaAnticipi += ritardo;

                            if (ritardo < 0) {
                                if (ritardo > massimoAnticipo) massimoAnticipo = ritardo; // Es: -10 > -30
                                if (ritardo < minimoAnticipo) minimoAnticipo = ritardo;    // Es: -30 < -10
                            }

                            break;
                    }

                } else System.err.println("Riga non valida: "+ riga);
            }


            // Ottenimento del ritardo medio effettuato dai viaggi relativi alla linea considerata
            int mediaRitardi = sommaRitardi / ritardati;
            int mediaAnticipi = sommaAnticipi / anticipati;


            // Aggiunta delle informazioni ottenute all'ArrayList statistiche, e restituzione di quest'ultimo
            statistiche.add(String.valueOf(count));
            statistiche.add(String.valueOf(mediaRitardi));
            statistiche.add(String.valueOf(mediaAnticipi));
            statistiche.add(String.valueOf(puntuali));
            statistiche.add(String.valueOf(ritardati));
            statistiche.add(String.valueOf(cancellati));
            statistiche.add(String.valueOf(duplicati));
            statistiche.add(String.valueOf(anticipati));
            statistiche.add(String.valueOf(minimoRitardo));
            statistiche.add(String.valueOf(minimoAnticipo));
            statistiche.add(String.valueOf(massimoRitardo));
            statistiche.add(String.valueOf(massimoAnticipo));

            return statistiche;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Metodo che restituisce le statistiche relative a una determinata fermata
    public ArrayList<String> ottieniStatistiche(Stop fermata) {

        // Ottenimento del file relativo alla fermata, e istanziamento di un ArrayList che ospiterà le statistiche della stessa
        File fileFermata = new File("src/resources/files/fermate/storico_" + fermata.getId().getId() + ".txt");
        ArrayList<String> statistiche = new ArrayList<>();


        // Lettura del file relativo alla fermata
        try (BufferedReader reader = new BufferedReader(new FileReader(fileFermata))) {

            // Istanziamento di varie variabili che verranno utilizzate per il conteggio delle statistiche
            int count = 0;
            int sommaRitardi = 0;

            int puntuali = 0;
            int ritardati = 0;
            int anticipati = 0;
            int saltate = 0;
            int noData = 0;


            // Lettura delle varie righe del file, e parsing dei dati trovati su di esse
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

                } else System.err.println("Riga non valida: "+ riga);
            }


            // Ottenimento del ritardo medio effettuato dai viaggi relativi alla linea considerata
            int mediaRitardi = sommaRitardi / count;


            // Aggiunta delle informazioni ottenute all'ArrayList statistiche, e restituzione di quest'ultimo
            statistiche.add(String.valueOf(count));
            statistiche.add(String.valueOf(mediaRitardi));
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