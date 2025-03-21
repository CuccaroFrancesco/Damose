package mappa;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.SwingUtilities;
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
        TileFactoryInfo info = new OSMTileFactoryInfo("Cazzo","https://a.tile.openstreetmap.fr/hot/");
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

        final JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);
        tileFactory.setThreadPoolSize(8);
        GeoPosition Roma = new GeoPosition(41.90, 12.49);

        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(Roma);

        MouseInputListener Mouse = new PanMouseInputListener(mapViewer);
        ZoomMouseWheelListenerCursor Wheel= new ZoomMouseWheelListenerCursor(mapViewer);
        mapViewer.addMouseListener(Mouse);
        mapViewer.addMouseMotionListener(Mouse);
        mapViewer.addMouseWheelListener(Wheel);

        
        final JFrame frame = new JFrame("Damose App Trasporti Roma");
        final JLabel label = new JLabel("Fratello cosa");
        frame.setLayout(new BorderLayout());
        frame.add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
