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
    private JButton mappaNormale, mappaSatellitare, mappaMista, btnLogin;
    private DatiGTFS dati;

    
    public Navbar(Mappa mapPanel, DatiGTFS dati) {
    	
    	// Assegnamento della mappa all'istanza
        this.mapPanel = mapPanel;
        this.dati = dati;

        
        // Gestione delle caratteristiche della navbar
        this.setOpaque(true);
        this.setBackground(new Color(130, 36, 51));
        this.setLayout(null);

        
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
        	}
        });
        
        mappaSatellitare.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TilesetManager.updateMap(mapPanel, 1);
        		mappaNormale.setEnabled(true);
        		mappaSatellitare.setEnabled(false);
        		mappaMista.setEnabled(true);
        	}
        });
        
        mappaMista.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TilesetManager.updateMap(mapPanel, 2);
        		mappaNormale.setEnabled(true);
        		mappaSatellitare.setEnabled(true);
        		mappaMista.setEnabled(false);
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
        
        
        // Barra di ricerca, con annessa gestione del testo placeholder
        searchBar = new JTextField("  Cerca linea o fermata...");
        
        searchBar.setBounds(290, 15, 500, 40);
        searchBar.setMinimumSize(new Dimension(370, 40));
        searchBar.setMaximumSize(new Dimension(500, 40));
        
        searchBar.setBackground(new Color(202, 202, 202));
        searchBar.setFont(new Font("Arial Nova", Font.BOLD, 12));
        searchBar.setForeground(new Color(0, 0, 0));
        
        this.add(searchBar);

        searchBar.addFocusListener(new FocusListener() {
        	
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("  Cerca linea o fermata...")) {
                    searchBar.setText("");     // Cancella il testo attuale
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setText("  Cerca linea o fermata...");     // Reinserisce il testo predefinito
                }
            }
        });
        
        
        // Funzionalità di ricerca di una linea per la searchBar 
        searchBar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		String lineaDaCercare = searchBar.getText();
        		LineaPainter.costruisciLineaDaDisegnare(lineaDaCercare, mapPanel, dati);
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
    public JTextField getSearchBar() {
    	return this.searchBar;
    }
    
    
    // Metodo get per l'insieme dei pulsanti per il cambio di mappa
    public JPanel getMapButtonGroup() {
    	return this.mapButtonGroup;
    }
    
    
    // Metodo get per il pulsante della sezione utente
    public JButton getBtnLogin() {
    	return this.btnLogin;
    }
}