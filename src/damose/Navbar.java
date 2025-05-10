/**********************************************************************************

Classe "Navbar" per l'oggetto destinato a contenere la navbar visualizzata nella
parte superiore dell'applicazione, all'interno della quale si troveranno i pulsanti
per il cambio del "tipo" della mappa visualizzata (mapButtonGroup), la barra di
ricerca (searchBar) e il pulsante per la sezione utente (btnLogin).

METODI:
- getSearchBar(), restituisce la barra di ricerca contenuta nella navbar;
- getMapButtonGroup(), restituisce il gruppo di pulsanti per il cambio della mappa
  contenuto nella navbar;
- getBtnLogin(), restituisce il pulsante per la sezione utente contenuto nella
  navbar.

**********************************************************************************/

package damose;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;
import java.util.ArrayList;

import org.onebusaway.gtfs.model.*;

import org.jxmapviewer.viewer.*;



public class Navbar extends JPanel {

	private Mappa mapPanel;
    private JTextField searchBar;
    private JPanel mapButtonGroup;
    private JLayeredPane searchLayeredPane;
    private JButton mappaNormale, mappaSatellitare, mappaMista, btnLogin, btnRicerca;
    private DatiGTFS dati;
    private StopPanel stopPanel;
    private RoutePanel lineaPanel;
    private Ricerca ricerca;

    
    public Navbar(Mappa mapPanel, DatiGTFS dati, StopPanel stopPanel, RoutePanel lineaPanel, Ricerca ricerca) {
    	
    	// Assegnamento della mappa all'istanza
        this.mapPanel = mapPanel;
        this.dati = dati;
        this.stopPanel = stopPanel;
        this.lineaPanel = lineaPanel;
        this.ricerca = ricerca;

        
        // Gestione delle caratteristiche della navbar
        this.setOpaque(true);
        this.setBackground(new Color(130, 36, 51));
        this.setLayout(null);
        this.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));;

        
        // Pulsanti per scelta del tipo di mappa (normale, satellitare, mista)
        mappaNormale = new JButton();
        mappaSatellitare = new JButton();
        mappaMista = new JButton();
        
        mappaNormale.setFocusPainted(false);
        mappaSatellitare.setFocusPainted(false);
        mappaMista.setFocusPainted(false);
        
        mappaNormale.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mappaSatellitare.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mappaMista.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        mappaNormale.setBackground(new Color(130, 36, 51));
        mappaSatellitare.setBackground(new Color(130, 36, 51));
        mappaMista.setBackground(new Color(130, 36, 51));
        
        Dimension buttonDimension = new Dimension(50, 50);
        mappaNormale.setPreferredSize(buttonDimension);
        mappaSatellitare.setPreferredSize(buttonDimension);
        mappaMista.setPreferredSize(buttonDimension);
        
        ImageIcon mappaNormaleIcon = new ImageIcon("src/resources/mappaNormale.png");
        Image mappaNormaleScaled = mappaNormaleIcon.getImage().getScaledInstance(44, 44, Image.SCALE_SMOOTH);
        ImageIcon mappaNormaleNewIcon = new ImageIcon(mappaNormaleScaled);
        mappaNormale.setIcon(mappaNormaleNewIcon);
        
        ImageIcon mappaSatellitareIcon = new ImageIcon("src/resources/mappaSatellitare.png");
        Image mappaSatellitareScaled = mappaSatellitareIcon.getImage().getScaledInstance(44, 44, Image.SCALE_SMOOTH);
        ImageIcon mappaSatellitareNewIcon = new ImageIcon(mappaSatellitareScaled);
        mappaSatellitare.setIcon(mappaSatellitareNewIcon);
        
        ImageIcon mappaMistaIcon = new ImageIcon("src/resources/mappaMista.png");
        Image mappaMistaScaled = mappaMistaIcon.getImage().getScaledInstance(44, 44, Image.SCALE_SMOOTH);
        ImageIcon mappaMistaNewIcon = new ImageIcon(mappaMistaScaled);
        mappaMista.setIcon(mappaMistaNewIcon);
        
        mappaNormale.setEnabled(false);
        
        
        // Funzionalità per i pulsanti mappaNormale, mappaSatellitare e mappaMista
        mappaNormale.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TilesetManager.updateMap(mapPanel, 0);
        		mappaNormale.setEnabled(false);
        		mappaSatellitare.setEnabled(true);
        		mappaMista.setEnabled(true);
        		Navbar.this.requestFocusInWindow();
        	}
        });
        
        mappaSatellitare.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TilesetManager.updateMap(mapPanel, 1);
        		mappaNormale.setEnabled(true);
        		mappaSatellitare.setEnabled(false);
        		mappaMista.setEnabled(true);
        		Navbar.this.requestFocusInWindow();
        	}
        });
        
        mappaMista.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TilesetManager.updateMap(mapPanel, 2);
        		mappaNormale.setEnabled(true);
        		mappaSatellitare.setEnabled(true);
        		mappaMista.setEnabled(false);
        		Navbar.this.requestFocusInWindow();
        	}
        });
        
        
        // Raggruppamento dei pulsanti in un unico pannello
        mapButtonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        mapButtonGroup.setBounds(30, 10, 170, 50);
        mapButtonGroup.setBackground(new Color(130, 36, 51));

        mapButtonGroup.add(mappaNormale);
        mapButtonGroup.add(mappaSatellitare);
        mapButtonGroup.add(mappaMista);
        
        this.add(mapButtonGroup);
        
        
        ImageIcon iconRicerca = new ImageIcon("src/resources/ricerca.png");
        Image scaledImageRicerca = iconRicerca.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon newIconRicerca = new ImageIcon(scaledImageRicerca);
        
        
        // Barra di ricerca, con annessa gestione del testo placeholder
        searchBar = new JTextField("  Cerca linea o fermata...");
        
        searchBar.setBounds(400, 15, 500, 40);
        searchBar.setMinimumSize(new Dimension(370, 40));
        searchBar.setMaximumSize(new Dimension(500, 40));
        
        searchBar.setBackground(new Color(202, 202, 202));
        searchBar.setFont(new Font("Arial Nova", Font.BOLD, 12));
        searchBar.setForeground(new Color(0, 0, 0));
        
        searchLayeredPane = new JLayeredPane();
        searchLayeredPane.setBounds(400, 15, 500, 40);  // stesse dimensioni della searchBar
        searchLayeredPane.setLayout(null);
        searchLayeredPane.setOpaque(false);
        
        searchBar.setBounds(0, 0, 500, 40); // relativo al layered pane
        
        // Pulsante per la barra di ricerca
        btnRicerca = new JButton();
        searchLayeredPane.setLayer(btnRicerca, 100);
        
        btnRicerca.setContentAreaFilled(false);
        btnRicerca.setFocusPainted(false);
        btnRicerca.setBorderPainted(false);
        btnRicerca.setBackground(new Color(130, 36, 51));
        btnRicerca.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnRicerca.setPreferredSize(new Dimension(30, 25));
        btnRicerca.setIcon(newIconRicerca);
        
        searchLayeredPane.add(btnRicerca, 100);  
        searchLayeredPane.add(searchBar, Integer.valueOf(1));      
        
        this.add(searchLayeredPane);

        
        // Gestione del testo placeholder della searchBar
        searchBar.addFocusListener(new FocusListener() {
        	
        	@Override
        	public void focusGained(FocusEvent e) {
        		if (searchBar.getText().equals("  Cerca linea o fermata...")) {
        			searchBar.setText("");     // Cancella il testo attuale
        			Navbar.this.getBtnRicerca().repaint();
        		}
        	}
        	
        	@Override
        	public void focusLost(FocusEvent e) {
        		if (searchBar.getText().isEmpty()) {
        			searchBar.setText("  Cerca linea o fermata...");     // Reinserisce il testo predefinito
        			Navbar.this.getBtnRicerca().repaint();
        		}
        	}
        });
        
        
        // Funzionalità di ricerca di una linea per la lente di ricerca
        btnRicerca.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ricerca();
        	}
        });
        
        // Funzionalità di ricerca di una linea per la searchBar 
        searchBar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ricerca();
        	}
        });
        
        // Aggiungi un listener per monitorare l'input nella searchBar
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (searchBar.getText().length() > 2) {
                    ricerca();  // Chiamata alla funzione di ricerca
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (searchBar.getText().length() > 2) {
                    ricerca();  // Chiamata alla funzione di ricerca
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Niente
            }
        });
        
        
        // Pulsante per la sezione utente e il login
        btnLogin = new JButton();
        
        btnLogin.setContentAreaFilled(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setBackground(new Color(130, 36, 51));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnLogin.setPreferredSize(new Dimension(50, 50));
        btnLogin.setBounds(getWidth() - btnLogin.getWidth() - 40, 10, 50, 50);
        
        ImageIcon icon = new ImageIcon("src/resources/user_placeholder.png");
        Image scaledImage = icon.getImage().getScaledInstance(44, 44, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);
        btnLogin.setIcon(newIcon);
        
        this.add(btnLogin);
    }
    
    
    // Metodo get per la barra di ricerca
    public JLayeredPane getSearchBar() {
    	return this.searchLayeredPane;
    }
    
    public JButton getBtnRicerca() {
    	return this.btnRicerca;
    }
    
    
    // Metodo get per l'insieme dei pulsanti per il cambio di mappa
    public JPanel getMapButtonGroup() {
    	return this.mapButtonGroup;
    }
    
    
    // Metodo get per il pulsante della sezione utente
    public JButton getBtnLogin() {
    	return this.btnLogin;
    }
    
    // Metodo per la ricerca
    public void ricerca()
    {
    	String lineaDaCercare = searchBar.getText();
    	ricerca.mostraRisultatiRicerca(lineaDaCercare, this);
    	searchBar.requestFocusInWindow();
    }
}