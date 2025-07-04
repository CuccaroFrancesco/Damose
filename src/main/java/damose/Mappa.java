package damose;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.List;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.*;
import org.jxmapviewer.painter.*;

import org.onebusaway.gtfs.model.*;



public class Mappa extends JComponent {

	private final Frame frame;
	
    private final JXMapViewer mapViewer;
    private final FileBasedLocalCache localCache;
    private final WaypointPainter<Waypoint> fermateVisibiliPainter;
    private final LineaPainter lineaPainter;
    private final VeicoliPainter veicoliPainter;
    private boolean waitingForClick;

    
    // Costruttore dell'oggetto Mappa
    public Mappa(Frame frame) throws Exception {
    	
    	this.frame = frame;
        this.waitingForClick = false;
    	
    	
    	// Impostazioni iniziale della mappa
        TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        
        // Creazione della mappa                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          1                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
        Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        
        mapViewer = new JXMapViewer();
        mapViewer.setBounds(0, 0, screenSize.width, screenSize.height);  // Posizione e dimensione
        mapViewer.setTileFactory(tileFactory);
        
        mapViewer.setOverlayPainter(frame.getPainterGroup());
        
        
        // Selezione del numero di thread per il rendering, destinazione di una cache
        tileFactory.setThreadPoolSize(8);
        
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        localCache = new FileBasedLocalCache(cacheDir, true);
        tileFactory.setLocalCache(localCache);
        
        
        // Creazione dei vari painter e aggiunta al painterGroup della mappa
        this.fermateVisibiliPainter = new WaypointPainter<Waypoint>();
        this.lineaPainter = new LineaPainter(new ArrayList<>());
        this.veicoliPainter = new VeicoliPainter(new ArrayList<>());
        
        this.frame.getPainterGroup().addPainter(lineaPainter);
        this.frame.getPainterGroup().addPainter(fermateVisibiliPainter);
        this.frame.getPainterGroup().addPainter(veicoliPainter);

        
        // Impostazione della posizione e dello zoom iniziale
        GeoPosition roma = new GeoPosition(41.90, 12.49);
        
        mapViewer.setAddressLocation(roma);
        mapViewer.setZoom(3);

        aggiornaFermateVisibili();

        
        // Listener per le azioni eseguibili dal mouse sulla mappa
        PanMouseInputListener panListener = new PanMouseInputListener(mapViewer);
        ZoomMouseWheelListenerCursor zoomListener = new ZoomMouseWheelListenerCursor(mapViewer);

        mapViewer.addMouseListener(panListener);
        mapViewer.addMouseMotionListener(panListener);
        mapViewer.addMouseWheelListener(zoomListener);
        
        mapViewer.addPropertyChangeListener("zoom", e -> {

                if (frame.getRoutePanel().isVisible()) {
                    aggiornaFermateVisibili(frame.getRoutePanel().getViaggiDaVisualizzare().get(frame.getRoutePanel().getIndiceViaggioVisualizzato()));

                } else if (frame.getStopPanel().isVisible()) {
                    aggiornaFermateVisibili(frame.getDati().cercaFermataByID(frame.getStopPanel().getCodiceFermata().substring(4)));

                } else if (frame.getStatsPanel().isVisible()) {
                    if (frame.getStatsPanel().getViaggioDaVisualizzare() != null) aggiornaFermateVisibili(frame.getStatsPanel().getViaggioDaVisualizzare());
                    else if (frame.getStatsPanel().getFermataDaVisualizzare() != null) aggiornaFermateVisibili(frame.getStatsPanel().getFermataDaVisualizzare());

                } else {
                    if (frame.getUtente().getIsLogged() && frame.getUtente().getFermatePreferiteToggleStatus()) aggiornaFermateVisibili(frame.getUtente().getFermatePreferite());
                    else aggiornaFermateVisibili();
                }
            });

        mapViewer.addPropertyChangeListener("centerPosition", e -> {

                if (frame.getRoutePanel().isVisible()) {
                    aggiornaFermateVisibili(frame.getRoutePanel().getViaggiDaVisualizzare().get(frame.getRoutePanel().getIndiceViaggioVisualizzato()));

                } else if (frame.getStopPanel().isVisible()) {
                    aggiornaFermateVisibili(frame.getDati().cercaFermataByID(frame.getStopPanel().getCodiceFermata().substring(4)));

                } else if (frame.getStatsPanel().isVisible()) {
                    if (frame.getStatsPanel().getViaggioDaVisualizzare() != null) aggiornaFermateVisibili(frame.getStatsPanel().getViaggioDaVisualizzare());
                    else if (frame.getStatsPanel().getFermataDaVisualizzare() != null) aggiornaFermateVisibili(frame.getStatsPanel().getFermataDaVisualizzare());

                } else {
                    if (frame.getUtente().getIsLogged() && frame.getUtente().getFermatePreferiteToggleStatus()) aggiornaFermateVisibili(frame.getUtente().getFermatePreferite());
                    else aggiornaFermateVisibili();
                }
            });

        mapViewer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (waitingForClick) {

                    Point puntoCliccato = e.getPoint();
                    GeoPosition pos = mapViewer.convertPointToGeoPosition(puntoCliccato);

                    frame.getUtente().aggiornaUtente(frame.getUtente().getLineePreferite(),
                            frame.getUtente().getFermatePreferite(),
                            frame.getUtente().getFermatePreferiteToggleStatus(),
                            pos.getLatitude(),
                            pos.getLongitude(),
                            frame.getUtente().getCentroAutoSpawnPointToggleStatus());

                    frame.getSettingsPanel().getLblSpawnPointAttualeInfo().setText("<html>Lat: <b>" + frame.getUtente().getSpawnPointLat() + "</b>, Lon: <b>" + frame.getUtente().getSpawnPointLon() + "</b></html>");

                    waitingForClick = false;

                    ImageIcon iconCheck = new ImageIcon(getClass().getResource("/assets/check-notification.png"));
                    Image scaledImageCheck = iconCheck.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                    ImageIcon newIconCheck = new ImageIcon(scaledImageCheck);
                    frame.getNotificationPanel().getBtnMessage().setIcon(newIconCheck);
                    frame.getNotificationPanel().getBtnMessage().setText("  Nuovo SpawnPoint impostato!");

                    frame.getNotificationPanel().attivaNotifica();
                }
            }
        });
        
        
        // Configurazione del layout e aggiunta del mapViewer alla mappa
        this.setLayout(null); 
        this.add(mapViewer);
    }


// ---------------------------------------------------------------------------------------------

    
    // Metodo get per la cache locale, dove vengono conservati i tile della mappa in modalità offline
    public FileBasedLocalCache getLocalCache() {
        return this.localCache;
    }
    
    
    // Metodo get per l'oggetto JXMapViewer associato alla mappa
    public JXMapViewer getMapViewer() {
    	return this.mapViewer;
    }
    
    
    // Metodo get per il painterGroup, ossia il gruppo ordinato di painter che disegna sulla mappa
    public CompoundPainter<JXMapViewer> getPainterGroup() {
    	return this.frame.getPainterGroup();
    }
    
    
    // Metodo get per il fermateVisibiliPainter
    public WaypointPainter<Waypoint> getFermateVisibiliPainter() {
    	return this.fermateVisibiliPainter;
    }
    
    
    // Metodo get per il lineaPainter
    public LineaPainter getLineaPainter() {
    	return this.lineaPainter;
    }


    // Metodo get per il veicoliPainter
    public VeicoliPainter getVeicoliPainter() {
        return this.veicoliPainter;
    }


    // Metodi get e set per la variabile waitingForClick
    public boolean getWaitingForClick() {
        return this.waitingForClick;
    }

    public void setWaitingForClick(boolean waitingForClick) {
        this.waitingForClick = waitingForClick;
    }


// ---------------------------------------------------------------------------------------------
    
    
    // Metodo che permette di aggiornare il tipo di mappa (normale, satellitare, mista)
    public void updateMap(DefaultTileFactory tileFactory) {
        mapViewer.setTileFactory(tileFactory);
        mapViewer.setZoom(mapViewer.getZoom());
        mapViewer.repaint();
    }
    
    
    // Metodo che centra la mappa su un determinato punto e con un determinato zoom
    public void centraMappa(double lon, double lat, int zoom) {
    	GeoPosition target = new GeoPosition(lat, lon);
        mapViewer.setAddressLocation(target); 
        mapViewer.setZoom(zoom); 
        mapViewer.repaint();
    }
    
    
    // Metodo che controlla quali sono le fermate visibili sulla mappa e le disegna, "eliminando" invece quelle non visibili
    public void aggiornaFermateVisibili(Trip viaggio) {

        int zoomAttuale = mapViewer.getZoom();

        Set<Waypoint> puntatoriFermate = new HashSet<>();

        Rectangle mappaVisibile = mapViewer.getViewportBounds();
        TileFactory tileFactory = mapViewer.getTileFactory();

        GeoPosition topLeft = tileFactory.pixelToGeo(new Point(mappaVisibile.x, mappaVisibile.y), zoomAttuale);
        GeoPosition bottomRight = tileFactory.pixelToGeo(new Point(mappaVisibile.x + mappaVisibile.width, mappaVisibile.y + mappaVisibile.height), zoomAttuale);
        double nord = topLeft.getLatitude();
        double ovest = topLeft.getLongitude();
        double sud = bottomRight.getLatitude();
        double est = bottomRight.getLongitude();

        List<Stop> fermateVisibili = frame.getDati().getFermate().stream()
                .filter(stop -> stop.getLat() >= Math.min(nord, sud) && stop.getLat() <= Math.max(nord, sud))
                .filter(stop -> stop.getLon() >= Math.min(ovest, est) && stop.getLon() <= Math.max(ovest, est))
                .toList();

        for (Stop fermata : fermateVisibili) {
            if (this.frame.getDati().getFermatePerViaggio(viaggio).contains(fermata)) puntatoriFermate.add(new DefaultWaypoint(fermata.getLat(), fermata.getLon()));
        }

        fermateVisibiliPainter.setWaypoints(puntatoriFermate);
        mapViewer.repaint();
    }

    public void aggiornaFermateVisibili(Stop fermata) {

        Set<Waypoint> puntatoreFermata = new HashSet<>();
        puntatoreFermata.add(new DefaultWaypoint(fermata.getLat(), fermata.getLon()));

        fermateVisibiliPainter.setWaypoints(puntatoreFermata);
        mapViewer.repaint();
    }

    public void aggiornaFermateVisibili(List<String> fermatePreferite) {

        List<Stop> fermate = new ArrayList<>();
        Set<Waypoint> puntatoriFermate = new HashSet<>();

        for (String fermataPreferita : fermatePreferite) {
            Stop fermata = frame.getDati().cercaFermataByID(fermataPreferita);
            fermate.add(fermata);
        }

        for (Stop fermataDaDisegnare : fermate) {
            puntatoriFermate.add(new DefaultWaypoint(fermataDaDisegnare.getLat(), fermataDaDisegnare.getLon()));
        }

        fermateVisibiliPainter.setWaypoints(puntatoriFermate);
        mapViewer.repaint();
    }

    public void aggiornaFermateVisibili() {

        int zoomAttuale = mapViewer.getZoom();

        if (zoomAttuale > 3) {
            fermateVisibiliPainter.setWaypoints(Collections.emptySet());
            mapViewer.repaint();
            return;
        }

        Set<Waypoint> puntatoriFermate = new HashSet<>();

        Rectangle mappaVisibile = mapViewer.getViewportBounds();
        TileFactory tileFactory = mapViewer.getTileFactory();

        GeoPosition topLeft = tileFactory.pixelToGeo(new Point(mappaVisibile.x, mappaVisibile.y), zoomAttuale);
        GeoPosition bottomRight = tileFactory.pixelToGeo(new Point(mappaVisibile.x + mappaVisibile.width, mappaVisibile.y + mappaVisibile.height), zoomAttuale);
        double nord = topLeft.getLatitude();
        double ovest = topLeft.getLongitude();
        double sud = bottomRight.getLatitude();
        double est = bottomRight.getLongitude();

        List<Stop> fermateVisibili = frame.getDati().getFermate().stream()
                .filter(stop -> stop.getLat() >= Math.min(nord, sud) && stop.getLat() <= Math.max(nord, sud))
                .filter(stop -> stop.getLon() >= Math.min(ovest, est) && stop.getLon() <= Math.max(ovest, est))
                .toList();

        for (Stop fermata : fermateVisibili) {
            puntatoriFermate.add(new DefaultWaypoint(fermata.getLat(), fermata.getLon()));
        }

        fermateVisibiliPainter.setWaypoints(puntatoriFermate);
        mapViewer.repaint();
    }
}