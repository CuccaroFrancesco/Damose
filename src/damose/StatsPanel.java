package damose;

import org.onebusaway.gtfs.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StatsPanel extends JPanel {

    private Frame frame;
    private JButton btnClose, btnAgency;
    private JLabel codice, agenziaENome;

    // Costruttore generico
    public StatsPanel(Frame frame) {
        this.frame = frame;

        this.setBackground(new Color(130, 36, 51));
        this.setLayout(null);
        this.setVisible(false);


        // Pulsante per chiudere il lineaPanel
        btnClose = new JButton(" Chiudi pannello");

        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnClose.setFont(new Font("Arial Nova", Font.BOLD, 14));
        btnClose.setForeground(Color.WHITE);

        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setContentAreaFilled(false);

        btnClose.setBounds(-25, 5, 200, 30);

        ImageIcon iconClose = new ImageIcon("src/resources/close.png");
        Image scaledImageClose = iconClose.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon newIconClose = new ImageIcon(scaledImageClose);
        btnClose.setIcon(newIconClose);

        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StatsPanel.this.setVisible(false);
            }
        });

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
    public void creaPannelloStatistiche(Route linea) {
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


        // Visualizzazione dei nomi (long name e short name) assegnati alla linea e del nome dell'agenzia che la gestisce
        codice.setText(" " + shortName);
        codice.setFont(new Font("Arial Nova", Font.BOLD, 30));
        codice.setBounds(20, 120, 300, 20);

        if (longName == null || longName.isEmpty()) {
            agenziaENome.setText(agencyName);
        } else {
            agenziaENome.setText(agencyName + "  -  " + longName);
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
    }


    // Funzione in caso di statistiche per la fermata
    public void creaPannelloStatistiche(Stop fermata) {
        this.setVisible(true);

        frame.getRoutePanel().setVisible(false);
        frame.getStopPanel().setVisible(false);


        // Variabili che contengono informazioni sulla fermata (nome, ID)
        String name = fermata.getName();
        String id = fermata.getId().getId();


        // Visualizzazione del nome e dell'ID della fermata
        agenziaENome.setText("ID: " + id);
        codice.setText(name);
        codice.setFont(new Font("Arial Nova", Font.BOLD, 22));

        // Configurazione per l'icona della fermata
        btnAgency.setPreferredSize(new Dimension(40, 40));
        btnAgency.setBounds(20, 70, 40, 40);

        ImageIcon iconStop = new ImageIcon("src/resources/fermata-bianco.png");
        Image scaledImageStop = iconStop.getImage().getScaledInstance(32, 40, Image.SCALE_SMOOTH);
        ImageIcon newIconStop = new ImageIcon(scaledImageStop);
        btnAgency.setIcon(newIconStop);

        this.add(btnAgency);
    }
}
