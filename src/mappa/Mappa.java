package mappa;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.io.File;

public class Mappa extends JComponent {

    private JXMapViewer mapViewer;

    public Mappa() {
        // Impostazione iniziale della mappa
        TileFactoryInfo info = new OSMTileFactoryInfo("Mappa", "https://a.tile.openstreetmap.fr/hot/");
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        
        // Creazione della mappa
        mapViewer = new JXMapViewer();
        mapViewer.setBounds(0, 0, 1080, 720);  // Posizione e dimensione
        mapViewer.setTileFactory(tileFactory);
        
        
        // Selezione del numero di thread per il rendering, destinazione di una cache
        tileFactory.setThreadPoolSize(8);
        
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

        
        // Impostazione della posizione e dello zoom iniziale
        GeoPosition Roma = new GeoPosition(41.90, 12.49);
        
        mapViewer.setAddressLocation(Roma);
        mapViewer.setZoom(7);

        
        // Listener per la gestione del mouse sulla mappa
        PanMouseInputListener panListener = new PanMouseInputListener(mapViewer);
        ZoomMouseWheelListenerCursor zoomListener = new ZoomMouseWheelListenerCursor(mapViewer);

        mapViewer.addMouseListener(panListener);
        mapViewer.addMouseMotionListener(panListener);
        mapViewer.addMouseWheelListener(zoomListener);
        
        
        // Configurazione del layout della mappa e aggiunta al componente
        this.setLayout(null); 
        this.add(mapViewer);
    }

    
    public void updateMap(DefaultTileFactory tileFactory) {
    	
        mapViewer.setTileFactory(tileFactory);
        mapViewer.setZoom(7);
        mapViewer.repaint();
    }
}
