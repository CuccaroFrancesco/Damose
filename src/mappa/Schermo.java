package mappa;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

public class Schermo {

    // Variabili per la classe necessarie per accedere sempre
    private static DefaultTileFactory tileFactory;
    private static TileFactoryInfo info;

    // Creo la schermata di base dell'applicazione
    public static JFrame Schermata() 
    {
        final JFrame frame = new JFrame("Damose App Trasporti");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(1080, 720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;  
    }
    
    public static void main(String[] args) {
    	// Serve a poter eseguire il codice dopo altre funzionalità in modo da evitare rallentamenti
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	//Creo il frame tramite la funzione dichiarata sopra
                    JFrame frame = Schermata();  

                    // Inizializzazione della mappa con quella di default 
                    info = new OSMTileFactoryInfo("Mappa", "https://a.tile.openstreetmap.fr/hot/");
                    tileFactory = new DefaultTileFactory(info);
                    
                    // Implementazione di una cache per la mappa 
                    File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
                    tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));
                    
                    // Istanziamento della mappa, indicazione per il numero di thread utilizzati per renderizzare
                    final JXMapViewer mapViewer = new JXMapViewer();
                    mapViewer.setTileFactory(tileFactory);
                    tileFactory.setThreadPoolSize(8);
                    
                    // Specifica della posizione e dello zoom da visualizzare all'avvio
                    GeoPosition Roma = new GeoPosition(41.90, 12.49);
                    mapViewer.setAddressLocation(Roma);
                    mapViewer.setZoom(7);

                    // Creo l'elemento di scelta per selezionare il tipo di mappa
                    JComboBox<String> scelta = new JComboBox<>();
                    scelta.setModel(new DefaultComboBoxModel<>(new String[] { "Normale", "Satellite", "Mista" }));

                    // Aggiungo un evento che al cambio di selezione modifica il tipo di mappa
                    scelta.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            int selectedIndex = scelta.getSelectedIndex();
                            switch (selectedIndex) {
                                case 0:  // Normale
                                    info = new OSMTileFactoryInfo("Mappa", "https://a.tile.openstreetmap.fr/hot/");
                                    break;
                                case 1:  // Satellite
                                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
                                    break;
                                case 2:  // Mista
                                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
                                    break;
                            }
                            
                            // Aggiorno la mappa e reimposto lo zoom (purtroppo dava problemi lo zoom, ma la posizione rimane)
                            tileFactory = new DefaultTileFactory(info);
                            mapViewer.setTileFactory(tileFactory);
                            mapViewer.setZoom(7);
                            mapViewer.repaint();  
                        }
                    });
                    
                    // Dichiarazione e inizializzazione del sistema di gestione input per il mouse
                    MouseInputListener Mouse = new PanMouseInputListener(mapViewer);
                    ZoomMouseWheelListenerCursor Wheel = new ZoomMouseWheelListenerCursor(mapViewer);
                    mapViewer.addMouseListener(Mouse);
                    mapViewer.addMouseMotionListener(Mouse);
                    mapViewer.addMouseWheelListener(Wheel);
                    
                    // Aggiungo il "selezionatore" del tipo di mappa e la mappa alla schermata
                    frame.getContentPane().add(scelta, BorderLayout.NORTH);
                    frame.getContentPane().add(mapViewer, BorderLayout.CENTER);
                    frame.setVisible(true); 
                } catch (Exception e) {
                    e.printStackTrace(); // Stampo un eventuale errore a schermo (non dovrebbe accadere, però u never know)
                }
            }
        });
    }

}
