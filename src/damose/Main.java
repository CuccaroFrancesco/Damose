package damose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jxmapviewer.painter.*;
import org.jxmapviewer.*;



public class Main extends JFrame {
	
	private Utente utente;
	private DatiGTFS dati;
	private JLayeredPane layeredPane;
	private Mappa mapPanel;
	private RoutePanel routePanel;
	private StopPanel stopPanel;
	private UserPanel userPanel;
	private Navbar navbar;
	private Ricerca ricerca;
	
	public Main() throws Exception {
    	
    	// Instanza dell'utente, dei dati GTFS statici e del CompoundPainter
    	utente = new Utente();
    	
    	dati = new DatiGTFS();
    	dati.creaCaricamento();
    	
    	CompoundPainter<JXMapViewer> painterGroup = new CompoundPainter<JXMapViewer>();
    	
    	
    	// Costruzione e gestione della finestra principale
    	Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    	
        this.setTitle("Damose");
        this.setSize(new Dimension(1678, 715));
        this.setMinimumSize(new Dimension(1400, 720));
        this.setMaximumSize(screenSize);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon iconaDamose = new ImageIcon("src/resources/damose-icon.png");
        Image newIconaDamose16 = iconaDamose.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        Image newIconaDamose32 = iconaDamose.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        Image newIconaDamose64 = iconaDamose.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        
        List<Image> listIconeDamose = new ArrayList<>();
        listIconeDamose.add(newIconaDamose16);
        listIconeDamose.add(newIconaDamose32);
        listIconeDamose.add(newIconaDamose64);
        
        this.setIconImages(listIconeDamose);

        
        // Creazione di un JLayeredPane per gestire i livelli
        layeredPane = new JLayeredPane();
        
        layeredPane.setLayout(null);
        this.setContentPane(layeredPane);
        
        layeredPane.add(dati.getSchermataCaricamento(), Integer.valueOf(104));

        
        // 
        SwingWorker<Void, Void> loader = new SwingWorker<>() {
            
        	@Override
            protected Void doInBackground() throws Exception {
                
        		dati.caricaDatiStaticiGTFS("staticGTFS");
                return null;
                
            }

            @Override
            protected void done() {
            	
            	layeredPane.remove(dati.getSchermataCaricamento());
                layeredPane.repaint();
            	
				try {
					
					// Aggiunta della mappa alla finestra principale
					mapPanel = new Mappa(dati, painterGroup);
					
					mapPanel.setBounds(0, 70, screenSize.width, screenSize.height - 70);
	                layeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);

	                
	                // Aggiunta del pannello delle linee 
	                routePanel = new RoutePanel(utente, dati);
	                
	                routePanel.setBounds(0, 70, 350, screenSize.height - 70);
	                layeredPane.add(routePanel, Integer.valueOf(101));
	                
	                
	                // Aggiunta del pannello delle fermate 
	                stopPanel = new StopPanel(utente, dati);
	                
	                stopPanel.setBounds(0, 70, 350, screenSize.height - 70);
	                layeredPane.add(stopPanel, Integer.valueOf(101));
	                
	                
	                // Aggiunta del pannello di ricerca
	                ricerca = new Ricerca(dati, stopPanel, routePanel, mapPanel);
	                layeredPane.add(ricerca, Integer.valueOf(103));
	                
	                
	                // Aggiunta della navbar alla finestra principale
	                navbar = new Navbar(mapPanel, dati, stopPanel, routePanel, ricerca);
	                
	                navbar.setBounds(0, 0, screenSize.width, 70);
	                layeredPane.add(navbar, Integer.valueOf(102));
	                
	                
	                // Aggiunta del pannello utente (inizialmente invisibile) alla finestra principale
	                userPanel = new UserPanel(utente, dati, navbar, mapPanel, stopPanel, routePanel);
	                
	                userPanel.setBounds(screenSize.width - 350, 70, 350, screenSize.height - 70);
	                userPanel.setVisible(false);
	                layeredPane.add(userPanel, Integer.valueOf(101));
	                
	                
	                // Adattamento dinamico delle dimensioni della navbar e delle sue componenti
	                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	                
	                scheduler.scheduleAtFixedRate(() -> {
	                    calibra();  
	                }, 0, 150, TimeUnit.MILLISECONDS);
	                

	                // Gestione del click sul pulsante di login per mostrare/nascondere il pannello utente
	                navbar.getBtnLogin().addActionListener(new ActionListener() {
	                	
	                	public void actionPerformed(ActionEvent e) {
	                		
	                		if (userPanel.isVisible()) {
	                			
	                			// Pannello invisibile e mappa scoperta
	                			userPanel.setVisible(false);
	                            mapPanel.setBounds(0, 70, screenSize.width, screenSize.height - 70);
	                            calibra();
	                            
	                		} else {
	                			
	                			// Pannello visibile e mappa coperta
	                			userPanel.setVisible(true);
	                			mapPanel.setBounds(0, 70, screenSize.width - 350, screenSize.height - 70);
	                			calibra();
	                		}
	                	}
	                });
	                
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        };
        
        loader.execute();
	}
	
	
    // Metodo che gestisce l'adattamento dinamico delle dimensioni della navbar e delle sue componenti
    private void calibra() {
    	
    	int newWidth = getWidth();              // Nuova larghezza della finestra
    	int newHeight = getHeight();            // Nuova altezza della finestra
    	
    	navbar.setBounds(0, 0, newWidth, 70);
    	navbar.getBtnLogin().setBounds(newWidth - navbar.getBtnLogin().getWidth() - 30, 10, 50, 50);
    	
    	userPanel.setBounds(newWidth - 350, 70, 350, newHeight - 70);
    	
    	if ((newWidth / 2) - 250 <= 230) {                                               
    		
    		if (newWidth - 340 <= 500) navbar.getSearchBar().setBounds(230, 15, newWidth - 340, 40);
    		else navbar.getSearchBar().setBounds(230, 15, 500, 40);
    		
    	} else {
    		
    		navbar.getSearchBar().setBounds((navbar.getWidth() / 2) - 250, 15, 500, 40);
    	}
    	
    	navbar.getBtnRicerca().setBounds(460, 8, 30, 25);
    	
    	if (ricerca.getRisultatiScrollPane() == null) ricerca.setBounds(navbar.getSearchBar().getX(), 55, navbar.getSearchBar().getWidth(), 60);
    	else ricerca.setBounds(navbar.getSearchBar().getX(), 55, navbar.getSearchBar().getWidth(), ricerca.getRisultatiScrollPane().getHeight());
    	
    	if (userPanel.isVisible()) {
    		
    		if (stopPanel.isVisible() || routePanel.isVisible()) mapPanel.setBounds(350, 70, newWidth - 350, newHeight - 70);
    		else mapPanel.setBounds(0, 70, newWidth - 350, newHeight - 70);
    		
    	} else {
    		
    		if (stopPanel.isVisible() || routePanel.isVisible()) mapPanel.setBounds(350, 70, newWidth, newHeight - 70);
    		else mapPanel.setBounds(0, 70, newWidth, newHeight - 70);
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