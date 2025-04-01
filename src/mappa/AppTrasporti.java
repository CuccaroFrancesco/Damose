package mappa;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.PanMouseInputListener;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import java.io.File;

public class AppTrasporti extends JFrame {

    private static DefaultTileFactory tileFactory;
    private static TileFactoryInfo info;
    private JTextField searchBar;
    private JXMapViewer mapViewer;

    public AppTrasporti() {
    	
        setTitle("Damose App Trasporti");
        setMinimumSize(new Dimension(1080, 720));
        setSize(new Dimension(1080, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        // Creazione di un JLayeredPane per gestire i livelli
        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);
        
        
        // Impostazione della mappa iniziale (Normale)
        info = new OSMTileFactoryInfo("Mappa", "https://a.tile.openstreetmap.fr/hot/");
        tileFactory = new DefaultTileFactory(info);
        
        
        // Inizializzazione della mappa con una cache
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

        
        // Creazione del visualizzatore della mappa
        mapViewer = new JXMapViewer();
        mapViewer.setBounds(0, 0, 1059, 681);
        mapViewer.setTileFactory(tileFactory);
        tileFactory.setThreadPoolSize(8);

        
        // Impostazione della posizione e dello zoom iniziale
        GeoPosition Roma = new GeoPosition(41.90, 12.49);
        
        mapViewer.setAddressLocation(Roma);
        mapViewer.setZoom(7);

        
        // Sistema di gestione input per il mouse
        MouseInputListener Mouse = new PanMouseInputListener(mapViewer);
        ZoomMouseWheelListenerCursor Wheel = new ZoomMouseWheelListenerCursor(mapViewer);

        mapViewer.addMouseListener(Mouse);
        mapViewer.addMouseMotionListener(Mouse);
        mapViewer.addMouseWheelListener(Wheel);
        
        
        // Aggiungi la mappa come background al layeredPane
        layeredPane.add(mapViewer, JLayeredPane.DEFAULT_LAYER);

        
        // Barra superiore con campo di ricerca e selezione mappa
        JPanel navbar = new JPanel();
        
        navbar.setOpaque(false);  // Mantieni la navbar opaca
        navbar.setBackground(new Color(255, 255, 255, 128));  // Semi-trasparente
        navbar.setBounds(0, 0, 1059, 33);
        layeredPane.add(navbar, JLayeredPane.PALETTE_LAYER);  // Posiziona la navbar sopra la mappa
        navbar.setLayout(null);

        
        // ComboBox per selezionare il tipo di mappa
        JComboBox<String> scelta = new JComboBox<>();
        scelta.setOpaque(false);
        scelta.setFont(new Font("Arial Nova", Font.BOLD, 12));
        scelta.setModel(new DefaultComboBoxModel<>(new String[]{"Normale", "Satellitare", "Mista"}));
        scelta.setBounds(29, 6, 169, 22);
        navbar.add(scelta);
        scelta.setBackground(new Color(255, 255, 255, 180)); 

        
        // Aggiungi listener per cambiare tipo di mappa
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

                
                // Aggiorna la mappa con la selezione
                tileFactory = new DefaultTileFactory(info);
                mapViewer.setTileFactory(tileFactory);
                mapViewer.setZoom(7);
                mapViewer.repaint();
            }
        });

        
        // Campo di ricerca
        searchBar = new JTextField();
        
        searchBar.setFont(new Font("Arial Nova", Font.BOLD, 12));
        searchBar.setBounds(442, 6, 302, 22);
        navbar.add(searchBar);
        searchBar.setColumns(10);
        searchBar.setBackground(new Color(255, 255, 255, 180));  // Barra di ricerca semi-trasparente
        
        String placeholder = "  Cerca linea o fermata...";
        searchBar.setText(placeholder);
        
        JButton btnLogin = new JButton();
        btnLogin.setBounds(950, 1, 32, 30);
        navbar.add(btnLogin);
        ImageIcon icona = new ImageIcon("C:\\\\Users\\\\franc\\\\Downloads\\\\circle-user-solid.png");
        Image scaledImage = icona.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);
        btnLogin.setIcon(newIcon);
        btnLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// dopo il click
        	}
        });

        
        // Listener per gestire il placeholder
        searchBar.addFocusListener(new FocusListener() {
        	
            @Override
            public void focusGained(FocusEvent e) {
            	
                if (searchBar.getText().equals(placeholder)) {
                    searchBar.setText(""); // Cancella il testo attuale se si vuole scrivere
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            	
                if (searchBar.getText().isEmpty()) {
                    searchBar.setText(placeholder); // Reinserisce il testo predefinito se non è stato scritto nulla
                }
            }
        });
    }

    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
        	
            public void run() {
            	
                try {
                    AppTrasporti frame = new AppTrasporti();
                    frame.setVisible(true);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
