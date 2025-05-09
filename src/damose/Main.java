package damose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jxmapviewer.painter.*;
import org.jxmapviewer.*;



public class Main extends JFrame {
	
	public Main() throws Exception {
    	
    	// Instanza dell'utente, dei dati GTFS statici e del CompoundPainter
    	Utente utente = new Utente();
    	DatiGTFS dati = new DatiGTFS();
    	CompoundPainter<JXMapViewer> painterGroup = new CompoundPainter<JXMapViewer>();
    	
    	dati.caricaDati();

    	
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
        
        mapPanel.setBounds(0, 70, screenSize.width, screenSize.height - 70);   // Dimensioni pari alle dimensioni dello schermo - altezza navbar
        layeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);

        
        // Aggiunta del pannello delle linee 
        LineaPanel lineaPanel = new LineaPanel(utente, dati);
        
        lineaPanel.setBounds(0, 70, 350, screenSize.height - 70);
        layeredPane.add(lineaPanel, Integer.valueOf(101));
        
        // Aggiunta del pannello delle fermate 
        StopPanel stopPanel = new StopPanel(utente, dati);
        
        stopPanel.setBounds(0, 70, 350, screenSize.height - 70);
        layeredPane.add(stopPanel, Integer.valueOf(101));
        
        // Aggiunta del pannello di ricerca
        Ricerca ricerca = new Ricerca(dati, stopPanel, lineaPanel, mapPanel);
        layeredPane.add(ricerca, Integer.valueOf(103));
        
        
        // Aggiunta della navbar alla finestra principale
        Navbar navbar = new Navbar(mapPanel, dati, stopPanel, lineaPanel, ricerca);
        
        navbar.setBounds(0, 0, screenSize.width, 70);
        layeredPane.add(navbar, Integer.valueOf(102));
        
        
        // Aggiunta del pannello utente (inizialmente invisibile)
        UserPanel userPanel = new UserPanel(utente, dati, navbar, mapPanel, stopPanel, lineaPanel);
        
        userPanel.setBounds(screenSize.width - 350, 70, 350, screenSize.height - 70);
        userPanel.setVisible(false);
        layeredPane.add(userPanel, Integer.valueOf(101));
        
        // Chiusura automatica della ricerca se si perde il focus
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent && ((MouseEvent) event).getID() == MouseEvent.MOUSE_PRESSED) {
                    MouseEvent me = (MouseEvent) event;
                    Component clickedComponent = SwingUtilities.getDeepestComponentAt(Main.this, me.getXOnScreen(), me.getYOnScreen());

                    // Controlla se il click è dentro Ricerca o SearchBar
                    boolean inRicerca = SwingUtilities.isDescendingFrom(clickedComponent, ricerca);
                    boolean inSearchBar = SwingUtilities.isDescendingFrom(clickedComponent, navbar.getSearchBar());

                    if (!inRicerca && !inSearchBar) {
                        SwingUtilities.invokeLater(() -> ricerca.setVisible(false));
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);



        
        
        // Adattamento dinamico delle dimensioni della navbar e delle sue componenti
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            // Esegui la funzione calibra nel background
            calibra(navbar, userPanel, mapPanel, ricerca);
        }, 0, 300, TimeUnit.MILLISECONDS);
        

        
        // Gestione del click sul pulsante di login per mostrare/nascondere il pannello utente
        navbar.getBtnLogin().addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		
        		if (userPanel.isVisible()) {
        			
        			// Pannello invisibile e mappa scoperta
        			userPanel.setVisible(false);
                    mapPanel.setBounds(0, 70, screenSize.width, screenSize.height - 70);
                    calibra(navbar, userPanel, mapPanel, ricerca);
                    
        		} else {
        			
        			// Pannello visibile e mappa coperta
        			userPanel.setVisible(true);
        			mapPanel.setBounds(0, 70, screenSize.width - 350, screenSize.height - 70);
        			calibra(navbar, userPanel, mapPanel, ricerca);
        		}
        	}
        });
	}
	
	
    // Metodo che gestisce l'adattamento dinamico delle dimensioni della navbar e delle sue componenti
    public void calibra(Navbar navbar, UserPanel userPanel, Mappa mapPanel, Ricerca ricerca) {
    	
    	int newWidth = getWidth();              // Nuova larghezza della finestra
    	int newHeight = getHeight();            // Nuova altezza della finestra
    	
    	userPanel.setBounds(newWidth - 350, 70, 350, newHeight - 70);
    	
    	if (userPanel.isVisible()) {
    		mapPanel.setBounds(0, 70, newWidth - 350, newHeight - 70);
    	} else {
    		mapPanel.setBounds(0, 70, newWidth, newHeight - 70);
    	}
    	
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
		
		navbar.getBtnRicerca().setBounds(460, 8, 30, 25);
		if(ricerca.getLineeScrollPane() == null) 
			ricerca.setBounds(navbar.getSearchBar().getX(), 55, navbar.getSearchBar().getWidth(), 60);
		else
			ricerca.setBounds(navbar.getSearchBar().getX(), 55, navbar.getSearchBar().getWidth(), ricerca.getLineeScrollPane().getHeight());
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