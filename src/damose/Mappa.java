/**********************************************************************************

Classe "Mappa" per l'oggetto destinato a contenere la mappa visualizzata all'interno
dell'applicazione. 

METODI:
- getLocalCache(), restituisce la cache locale utilizzata dalla mappa;
- getPainterGroup(), restituisce il CompoundPainter associato alla mappa;

- updateMap(), gestisce il cambiamento di "tipo" della mappa visualizzata;
- aggiornaFermateVisibili(), viene invocato dinamicamente ogni volta che l'utente 
  si muove sulla mappa o modifica lo zoom, disegna sulla mappa le fermate visibili
  e scarta quelle non visibili.

**********************************************************************************/

package damose;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.awt.Point;
import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.stream.*;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.*;
import org.jxmapviewer.painter.*;
import org.jxmapviewer.painter.Painter;

import org.onebusaway.gtfs.model.Stop;



public class Mappa extends JComponent {

    private JXMapViewer mapViewer;
    private FileBasedLocalCache localCache;
    private DatiGTFS dati;
    private WaypointPainter<Waypoint> painterFermate;
    private LineaPainter painterLinea;
    private CompoundPainter<JXMapViewer> painterGroup;

    
    // Costruttore dell'oggetto Mappa
    public Mappa(Frame frame) throws Exception {
    	
    	// Assegnamento dei dati GTFS e del CompoundPainter all'istanza
    	this.dati = frame.getDati();
    	this.painterGroup = frame.getPainterGroup();
    	
    	
    	// Impostazione iniziale della mappa
        TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        
        // Creazione della mappa                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          1                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
        Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        
        mapViewer = new JXMapViewer();
        mapViewer.setBounds(0, 0, screenSize.width, screenSize.height);  // Posizione e dimensione
        mapViewer.setTileFactory(tileFactory);
        
        mapViewer.setOverlayPainter(painterGroup);
        
        
        // Selezione del numero di thread per il rendering, destinazione di una cache
        tileFactory.setThreadPoolSize(8);
        
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        localCache = new FileBasedLocalCache(cacheDir, true);
        tileFactory.setLocalCache(localCache);
        
        
        // Creazione dei vari painter e aggiunta al painterGroup della mappa
        painterFermate = new WaypointPainter<Waypoint>();
        this.painterFermate = painterFermate;
        
        painterLinea = new LineaPainter(new ArrayList<>());
        this.painterLinea = painterLinea;
        
        this.painterGroup.addPainter(painterLinea);
        this.painterGroup.addPainter(painterFermate);

        
        // Impostazione della posizione e dello zoom iniziale
        GeoPosition Roma = new GeoPosition(41.90, 12.49);
        
        mapViewer.setAddressLocation(Roma);
        mapViewer.setZoom(4);

        
        // Listener per le azioni eseguibili dal mouse sulla mappa
        PanMouseInputListener panListener = new PanMouseInputListener(mapViewer);
        ZoomMouseWheelListenerCursor zoomListener = new ZoomMouseWheelListenerCursor(mapViewer);

        mapViewer.addMouseListener(panListener);
        mapViewer.addMouseMotionListener(panListener);
        mapViewer.addMouseWheelListener(zoomListener);
        
        mapViewer.addPropertyChangeListener("zoom", e -> aggiornaFermateVisibili());
        mapViewer.addPropertyChangeListener("centerPosition", e -> aggiornaFermateVisibili());
        
        
        // Configurazione del layout della mappa e aggiunta al componente
        this.setLayout(null); 
        this.add(mapViewer);
    }

    
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
    	return this.painterGroup;
    }
    
    
    // Metodo get per il painterGroup, ossia il gruppo ordinato di painter che disegna sulla mappa
    public WaypointPainter<Waypoint> getPainterFermate() {
    	return this.painterFermate;
    }
    
    
    // Metodo get per il painterGroup, ossia il gruppo ordinato di painter che disegna sulla mappa
    public LineaPainter getPainterLinea() {
    	return this.painterLinea;
    }
    
    
    // Metodo che permette di aggiornare il tipo di mappa (normale, satellitare, mista)
    public void updateMap(DefaultTileFactory tileFactory) {
        mapViewer.setTileFactory(tileFactory);
        mapViewer.setZoom(mapViewer.getZoom());
        mapViewer.repaint();
    }
    
    
    // Metodo che centra 
    public void centraMappa(double lon, double lat, int zoom) {
    	GeoPosition target = new GeoPosition(lat, lon);
        mapViewer.setAddressLocation(target); 
        mapViewer.setZoom(zoom); 
        mapViewer.repaint();
    }
    
    
    // Metodo che controlla quali sono le fermate visibili sulla mappa e le disegna, "eliminando" invece quelle non visibili
    public void aggiornaFermateVisibili() {
    	
    	int zoomAttuale = mapViewer.getZoom();
    	
    	if (zoomAttuale > 3) {
    		painterFermate.setWaypoints(Collections.emptySet());
    		mapViewer.repaint();
    		return;
    	}
    	
    	Rectangle mappaVisibile = mapViewer.getViewportBounds();
    	TileFactory tileFactory = mapViewer.getTileFactory();
    	
    	GeoPosition topLeft = tileFactory.pixelToGeo(new Point(mappaVisibile.x, mappaVisibile.y), zoomAttuale);
    	GeoPosition bottomRight = tileFactory.pixelToGeo(new Point(mappaVisibile.x + mappaVisibile.width, mappaVisibile.y + mappaVisibile.height), zoomAttuale);
    	double nord = topLeft.getLatitude();
    	double ovest = topLeft.getLongitude();
    	double sud = bottomRight.getLatitude();
    	double est = bottomRight.getLongitude();
    	
    	List<Stop> fermateVisibili = dati.getFermate().stream()
    			.filter(stop -> stop.getLat() >= Math.min(nord, sud) && stop.getLat() <= Math.max(nord, sud))
    	        .filter(stop -> stop.getLon() >= Math.min(ovest, est) && stop.getLon() <= Math.max(ovest, est))
    	        .collect(Collectors.toList());
    	
    	Set<Waypoint> puntatoriFermate = new HashSet<>();
    	for (Stop fermata : fermateVisibili) {
    		puntatoriFermate.add(new DefaultWaypoint(fermata.getLat(), fermata.getLon()));
    	}
    	
    	painterFermate.setWaypoints(puntatoriFermate);
        mapViewer.repaint();
    }
}