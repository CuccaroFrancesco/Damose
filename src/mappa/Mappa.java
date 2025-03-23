package mappa;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;

import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

public class Mappa
{
    public static void main(String[] args)
    {
    	// Dichiarazione del tileset da cui caricare la mappa
        TileFactoryInfo info = new OSMTileFactoryInfo("Mappa","https://a.tile.openstreetmap.fr/hot/");
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        
        // Istanziamento della mappa, indicazione per il numero di thread utilizzati per renderizzare
        final JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);
        
        tileFactory.setThreadPoolSize(8);
        
        
        // Implementazione di una cache per la mappa
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));
        
        
        // Specifica della posizione e dello zoom da visualizzare all'avvio
        GeoPosition Roma = new GeoPosition(41.90, 12.49);
        mapViewer.setAddressLocation(Roma);
        mapViewer.setZoom(7);

        
        // Dichiarazione e inizializzazione del sistema di gestione input per il mouse
        MouseInputListener Mouse = new PanMouseInputListener(mapViewer);
        ZoomMouseWheelListenerCursor Wheel = new ZoomMouseWheelListenerCursor(mapViewer);
        
        mapViewer.addMouseListener(Mouse);
        mapViewer.addMouseMotionListener(Mouse);
        mapViewer.addMouseWheelListener(Wheel);

        
        // Costruzione della finestra che contiene la mappa
        final JFrame frame = new JFrame("Damose");
        
        frame.setLayout(new BorderLayout());
        frame.add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
