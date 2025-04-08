package mappa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Damose extends JFrame {

    public Damose() {
    	
    	// Costruzione e gestione della finestra principale
    	Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    	
        this.setTitle("Damose App Trasporti");
        this.setSize(new Dimension(1080, 720));
        this.setMinimumSize(new Dimension(720, 480));
        this.setMaximumSize(screenSize);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        // Creazione di un JLayeredPane per gestire i livelli
        JLayeredPane layeredPane = new JLayeredPane();
        this.setContentPane(layeredPane);

        
        // Aggiunta della mappa alla finestra principale
        Mappa mapPanel = new Mappa();
        
        mapPanel.setBounds(0, 0, screenSize.width, screenSize.height);   // Dimensioni pari alle dimensioni dello schermo
        layeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);

        
        // Aggiunta della navbar alla finestra principale
        Navbar navbar = new Navbar(mapPanel);
        
        navbar.setBounds(0, 0, screenSize.width, 60);   // Posizione e dimensione della navbar
        layeredPane.add(navbar, JLayeredPane.PALETTE_LAYER);
        
        
        // Adattamento dinamico delle dimensioni della navbar
        this.addComponentListener(new ComponentAdapter() {
        	
            @Override
            public void componentResized(ComponentEvent e) {
            	
            	int newWidth = getWidth();
            	int newHeight = getHeight();
            	
            	navbar.setBounds(0, 0, newWidth, 70);
            	
            	navbar.getSearchBar().setBounds((newWidth - navbar.getSearchBar().getWidth()) / 2, 20, navbar.getSearchBar().getWidth(), 35);
            	navbar.getBtnLogin().setBounds(newWidth - navbar.getBtnLogin().getWidth() - 40, 10, 50, 50);
            	
            }
        });
    }

    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
        	
            public void run() {
            	
                try {
                    Damose frame = new Damose();
                    frame.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}