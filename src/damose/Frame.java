package damose;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingWorker;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.onebusaway.gtfs.model.Route;


public class Frame extends JFrame {
	
	private Utente utente;
	private DatiGTFS dati;
	private JLayeredPane layeredPane;
	private Mappa mapPanel;
	private RoutePanel routePanel;
	private StopPanel stopPanel;
	private UserPanel userPanel;
	private StatsPanel statsPanel;
	private NotificationPanel notificationPanel;
	private Navbar navbar;
	private Ricerca ricerca;
	private CompoundPainter<JXMapViewer> painterGroup;

	private int tripUpdatesStatus = 0;
	private int vehiclePositionsStatus = 0;
	private int alertStatus = 0;
	
	
	// Costruttore dell'oggetto Frame
	public Frame() throws Exception {
    	
    	// Instanza dell'utente, dei dati GTFS statici e del CompoundPainter
    	utente = new Utente();
    	
    	dati = new DatiGTFS();
    	dati.creaCaricamento();
    	
    	painterGroup = new CompoundPainter<JXMapViewer>();
    	
    	
    	// Costruzione e gestione della finestra principale
    	Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    	
        this.setTitle("Damose");
        this.setSize(new Dimension(1678, 715));
        this.setMinimumSize(new Dimension(1678, 715));
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


		// Istanziamento di uno ScheduledExecutor per ricalibrare il layout della schermata di caricamento ogni 150ms
        ScheduledExecutorService schedulerCalibraCaricamento = Executors.newScheduledThreadPool(1);
        schedulerCalibraCaricamento.scheduleAtFixedRate(() -> calibraCaricamento(), 0, 150, TimeUnit.MILLISECONDS);

        
        // Istanziamento di uno SwingWorker che permette di gestire il caricamento dei dati GTFS e la seguente apertura dell'applicazione effettiva
        SwingWorker<Void, Void> staticDataLoader = new SwingWorker<>() {
            
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
					mapPanel = new Mappa(Frame.this);
					
					mapPanel.setBounds(0, 70, screenSize.width, screenSize.height - 70);
	                layeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);

	                
	                // Aggiunta del routePanel alla finestra principale
	                routePanel = new RoutePanel(Frame.this);
	                
	                routePanel.setBounds(0, 70, 350, screenSize.height - 70);
	                layeredPane.add(routePanel, Integer.valueOf(101));
	                
	                
	                // Aggiunta dello stopPanel alla finestra principale
	                stopPanel = new StopPanel(Frame.this);
	                
	                stopPanel.setBounds(0, 70, 350, screenSize.height - 70);
	                layeredPane.add(stopPanel, Integer.valueOf(101));


					// Aggiunta dello statsPanel alla finestra principale
					statsPanel = new StatsPanel(Frame.this);

					statsPanel.setBounds(0, 70, 350, screenSize.height - 70);
					layeredPane.add(statsPanel, Integer.valueOf(101));


					// Aggiunta del notificationPanel alla finestra principale
					notificationPanel = new NotificationPanel(Frame.this);

					notificationPanel.setBounds(479, 600, 720, 64);
					layeredPane.add(notificationPanel, Integer.valueOf(104));
	                
	                
	                // Aggiunta del pannello dei risultati di ricerca alla finestra principale
	                ricerca = new Ricerca(Frame.this);
	                layeredPane.add(ricerca, Integer.valueOf(103));
	                
	                
	                // Aggiunta della navbar alla finestra principale
	                navbar = new Navbar(Frame.this);
	                
	                navbar.setBounds(0, 0, screenSize.width, 70);
	                layeredPane.add(navbar, Integer.valueOf(102));
	                
	                
	                // Aggiunta dello userPanel alla finestra principale
	                userPanel = new UserPanel(Frame.this);
	                
	                userPanel.setBounds(screenSize.width - 350, 70, 350, screenSize.height - 70);
	                userPanel.setVisible(false);
	                layeredPane.add(userPanel, Integer.valueOf(101));
	                
	                
	                // Istanziamento di uno ScheduledExecutor per ricalibrare il layout della finestra ogni 150ms
	                ScheduledExecutorService schedulerCalibra = Executors.newScheduledThreadPool(1);
	                
	                schedulerCalibra.scheduleAtFixedRate(() -> {
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

					SwingWorker<Void, Void> realTimeDataLoader = new SwingWorker<>() {

						@Override
						protected Void doInBackground() throws Exception {

							ScheduledExecutorService schedulerRealTimeData = Executors.newScheduledThreadPool(1);
							schedulerRealTimeData.scheduleAtFixedRate(() -> {

								try {

									dati.caricaTripUpdatesGTFS();
									tripUpdatesStatus = 2;

									dati.caricaVehiclePositionsGTFS();
									vehiclePositionsStatus = 2;

									dati.caricaAlertGTFS();
									alertStatus = 2;

                                    dati.creaStorico();

								} catch (Exception e) {

									if (Frame.this.dati.getTripUpdates() != null) tripUpdatesStatus = 1;
									if (Frame.this.dati.getVehiclePositions() != null) vehiclePositionsStatus = 1;
									if (Frame.this.dati.getAlert() != null) alertStatus = 1;

									ImageIcon iconError = new ImageIcon("src/resources/error-notification.png");
									Image scaledImageError = iconError.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
									ImageIcon newIconError = new ImageIcon(scaledImageError);
									notificationPanel.getBtnMessage().setIcon(newIconError);
									notificationPanel.getBtnMessage().setText("<html>&nbsp;&nbsp; Errore nel caricamento dei dati real-time. Controllare la connessione o<br>&nbsp;&nbsp; riprovare più tardi. Passaggio alla modalità offline...</html>");

									notificationPanel.attivaNotifica();

									System.err.println("Errore nel caricamento dei dati real-time. Controllare la connessione o riprovare più tardi.");
								}
							}, 0, 45, TimeUnit.SECONDS);

							return null;
						}
					};

					realTimeDataLoader.execute();
	                
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        };
        
        staticDataLoader.execute();
	}


// ---------------------------------------------------------------------------------------------


	// Metodo get per i dati GTFS assegnati all'istanza
	public DatiGTFS getDati() {
		return this.dati;
	}
	
	
	// Metodo get per l'utente assegnato all'istanza
	public Utente getUtente() {
		return this.utente;
	}
	
	
	// Metodo get per la mappa assegnata all'istanza
	public Mappa getMappa() {
		return this.mapPanel;
	}
	
	
	// Metodo get per il routePanel assegnato all'istanza
	public RoutePanel getRoutePanel() {
		return this.routePanel;
	}
	
	
	// Metodo get per lo stopPanel assegnato all'istanza
	public StopPanel getStopPanel() {
		return this.stopPanel;
	}


	// Metodo get per lo statsPanel assegnato all'istanza
	public StatsPanel getStatsPanel() { return this.statsPanel; }


	// Metodo get per il notificationPanel assegnato all'istanza
	public NotificationPanel getNotificationPanel() {
		return this.notificationPanel;
	}
	
	
	// Metodo get per lo userPanel assegnato all'istanza
	public UserPanel getUserPanel() {
		return this.userPanel;
	}
	
	
	// Metodo get per la navbar assegnata all'istanza
	public Navbar getNavbar() {
		return this.navbar;
	}
	
	
	// Metodo get per l'oggetto Ricerca assegnato all'istanza
	public Ricerca getRicerca() {
		return this.ricerca;
	}
	
	
	// Metodo get per il CompoundPainter assegnato all'istanza
	public CompoundPainter<JXMapViewer> getPainterGroup() {
		return this.painterGroup;
	}


	// Metodo get per la variabile tripUpdatesStatus
	public int getTripUpdatesStatus() {
		return this.tripUpdatesStatus;
	}


	// Metodo get per la variabile vehiclePositionsStatus
	public int getVehiclePositionsStatus() {
		return this.vehiclePositionsStatus;
	}


	// Metodo get per la variabile alertStatus
	public int getAlertStatus() {
		return this.alertStatus;
	}


// ---------------------------------------------------------------------------------------------


	// Metodo che gestisce l'adattamento dinamico delle componenti della finestra principale
	private void calibra() {
		
		int newWidth = getWidth();              // Nuova larghezza della finestra
		int newHeight = getHeight();            // Nuova altezza della finestra
		
		navbar.setBounds(0, 0, newWidth, 70);
		navbar.getBtnLogin().setBounds(newWidth - navbar.getBtnLogin().getWidth() - 30, 10, 50, 50);
		
		userPanel.setBounds(newWidth - 350, 70, 350, newHeight - 70);
		
		if ((newWidth / 2) - 250 <= 230) {                                               
			
			if (newWidth - 340 <= 500) navbar.getSearchBar().setBounds(230, 15, newWidth - 340, 40);
			else navbar.getSearchBar().setBounds(230, 15, 500, 40);
			
		} else navbar.getSearchBar().setBounds((navbar.getWidth() / 2) - 250, 15, 500, 40);
		
		navbar.getBtnRicerca().setBounds(460, 8, 30, 25);
		
		if (ricerca.getRisultatiScrollPane() == null) ricerca.setBounds(navbar.getSearchBar().getX(), 55, navbar.getSearchBar().getWidth(), 0);
		else ricerca.setBounds(navbar.getSearchBar().getX(), 55, navbar.getSearchBar().getWidth(), ricerca.getRisultatiScrollPane().getHeight());
		
		if (userPanel.isVisible()) {
			
			if (stopPanel.isVisible() || routePanel.isVisible()) mapPanel.setBounds(350, 70, newWidth - 350, newHeight - 70);
			else mapPanel.setBounds(0, 70, newWidth - 350, newHeight - 70);
			
		} else {
			
			if (stopPanel.isVisible() || routePanel.isVisible()) mapPanel.setBounds(350, 70, newWidth, newHeight - 70);
			else mapPanel.setBounds(0, 70, newWidth, newHeight - 70);
		}

		mapPanel.getMapViewer().setBounds(0, 0, mapPanel.getWidth(), mapPanel.getHeight());

		if (notificationPanel.isVisible()) notificationPanel.setBounds(newWidth / 2 - 360, newHeight - 115, notificationPanel.getWidth(), notificationPanel.getHeight());
	}
	
	
	// Metodo che gestisce l'adattamento dinamico della schermata di caricamento
	private void calibraCaricamento() {
			
		int newWidth = getWidth();
		int newHeight = getHeight();
		
		this.dati.getSchermataCaricamento().getComponent(0).setBounds(200, 600 + (newHeight - 715) / 2, newWidth - 400, 20);            // progressBar
		this.dati.getSchermataCaricamento().getComponent(1).setBounds(200, 615 + (newHeight - 715) / 2, newWidth - 400, 40);            // logs
		this.dati.getSchermataCaricamento().getComponent(2).setBounds((newWidth / 2) - 350, -50 + (newHeight - 715) / 3, 700, 700);     // logoDamose
	}
}
