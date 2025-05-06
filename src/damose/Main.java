package damose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jxmapviewer.painter.*;
import org.jxmapviewer.*;



public class Main extends JFrame {
	
	public Main() throws Exception {
    	
    	// Instanza dell'utente, dei dati GTFS statici e del CompoundPainter
    	Utente utente = new Utente();
    	DatiGTFS dati = new DatiGTFS();
    	CompoundPainter<JXMapViewer> painterGroup = new CompoundPainter<JXMapViewer>();
    	
    	
    	// Caricamento dei dati GTFS statici
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        try {
        	
        	dati.caricaDatiStaticiGTFS("staticGTFS", false);
        	
        	Future<?> caricamentoInBackground = executor.submit(() -> {
        		
        		try {
        			dati.caricaDatiStaticiGTFS("staticGTFS", true);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	});
        	
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	executor.shutdown();
        }
    	
        
    	// Costruzione e gestione della finestra principale
    	Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    	
        this.setTitle("Damose App Trasporti");
        this.setSize(new Dimension(1678, 715));
        this.setMinimumSize(new Dimension(1400, 720));
        this.setMaximumSize(screenSize);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        // Creazione di un JLayeredPane per gestire i livelli
        JLayeredPane layeredPane = new JLayeredPane();
        
        this.setContentPane(layeredPane);

        
        // Aggiunta della mappa alla finestra principale
        Mappa mapPanel = new Mappa(dati, painterGroup);
        
        mapPanel.setBounds(0, 60, screenSize.width, screenSize.height - 70);   // Dimensioni pari alle dimensioni dello schermo - altezza navbar
        layeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);

        
        // Aggiunta del pannello delle linee 
        LineaPanel lineaPanel = new LineaPanel(utente, dati);
        
        lineaPanel.setBounds(0, 60, 350, screenSize.height);
        layeredPane.add(lineaPanel, Integer.valueOf(101));
        
        // Aggiunta del pannello delle fermate 
        StopPanel stopPanel = new StopPanel(utente, dati);
        
        stopPanel.setBounds(0, 60, 350, screenSize.height);
        layeredPane.add(stopPanel, Integer.valueOf(101));
        
        // Aggiunta della navbar alla finestra principale
        Navbar navbar = new Navbar(mapPanel, dati, stopPanel, lineaPanel);
        
        navbar.setBounds(0, 0, screenSize.width, 60);
        layeredPane.add(navbar, JLayeredPane.PALETTE_LAYER);
        
        // Aggiunta del pannello utente (inizialmente invisibile)
        UserPanel userPanel = new UserPanel(utente, dati, navbar, mapPanel, stopPanel, lineaPanel);
        
        userPanel.setBounds(screenSize.width - 400, 70, 400, screenSize.height - 60);
        userPanel.setVisible(false);
        layeredPane.add(userPanel, Integer.valueOf(101));
        
        
        // Adattamento dinamico delle dimensioni della navbar e delle sue componenti
        this.addComponentListener(new ComponentAdapter() {
        	
        	@Override
            public void componentResized(ComponentEvent e) {
        		
        		calibra(navbar, userPanel, mapPanel, stopPanel, lineaPanel);
            }
        });

        
        // Gestione del click sul pulsante di login per mostrare/nascondere il pannello utente
        navbar.getBtnLogin().addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		
        		if (userPanel.isVisible()) {
        			
        			// Pannello invisibile e mappa scoperta
        			userPanel.setVisible(false);
                    mapPanel.setBounds(0, 60, screenSize.width, screenSize.height - 60);
                    calibra(navbar, userPanel, mapPanel, stopPanel, lineaPanel);
                    
        		} else {
        			
        			// Pannello visibile e mappa coperta
        			userPanel.setVisible(true);
        			mapPanel.setBounds(0, 60, screenSize.width - 400, screenSize.height);
        			calibra(navbar, userPanel, mapPanel, stopPanel, lineaPanel);
        		}
        	}
        });
	}
	
	
    // Metodo che gestisce l'adattamento dinamico delle dimensioni della navbar e delle sue componenti
    public void calibra(Navbar navbar, UserPanel userPanel, Mappa mapPanel, StopPanel stopPanel, LineaPanel lineaPanel) {
    	
    	int newWidth = getWidth();              // Nuova larghezza della finestra
    	int newHeight = getHeight();            // Nuova altezza della finestra
    	
    	userPanel.setBounds(newWidth - 400, 70, 400, newHeight);
    	
    	navbar.setBounds(0, 0, newWidth, 70);
		navbar.getBtnLogin().setBounds(newWidth - navbar.getBtnLogin().getWidth() - 30, 10, 50, 50);
		
		if ((newWidth / 2) - 250 <= 230) {                                               
    		
    		if (newWidth - 340 <= 500) {                                                 
    			navbar.getSearchBar().setBounds(230, 15, newWidth - 340, 40);
    		} else {                                                                     
    			navbar.getSearchBar().setBounds(230, 15, 500, 40);
    		}
    	
    	} else {
    		navbar.getSearchBar().setBounds((navbar.getWidth() / 2) - 250, 15, 500, 40);
    	}
    }
    
    
    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
        	public void run() {
        		
                try {
                	
                	Main frame = new Main();
                    frame.setVisible(true);
                    
                } catch (Exception e) {
                	
                    e.printStackTrace();
                }
            }
        });
    }
}