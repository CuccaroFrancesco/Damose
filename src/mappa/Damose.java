package mappa;

import javax.swing.*;
import java.awt.*;

public class Damose extends JFrame {

    public Damose() {
    	
    	// Gestione della finestra (JFrame)
        setTitle("Damose App Trasporti");
        setMinimumSize(new Dimension(1080, 720));
        setSize(new Dimension(1080, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        // Creazione di un JLayeredPane per gestire i livelli
        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        
        // Aggiunta della mappa 
        Mappa mapPanel = new Mappa();
        
        mapPanel.setBounds(0, 0, 1080, 720);  // Posizione e dimensione della mappa
        layeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);  

        
        // Aggiunta della navbar (a cui viene passata la mappa)
        Navbar navbar = new Navbar(mapPanel);
        
        navbar.setBounds(0, 0, 1080, 35);  // Posizione e dimensione della navbar
        layeredPane.add(navbar, JLayeredPane.PALETTE_LAYER);  
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
