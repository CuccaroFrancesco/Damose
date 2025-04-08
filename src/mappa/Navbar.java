package mappa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Navbar extends JPanel {

	private Mappa mapPanel;
    private JTextField searchBar;
    private JPanel mapButtonGroup;
    private JButton mappaNormale;
    private JButton mappaSatellitare;
    private JButton mappaMista;
    private JButton btnLogin;
   

    public Navbar(Mappa mapPanel) {
    	
        this.mapPanel = mapPanel;

        
        // Gestione delle caratteristiche della navbar
        this.setOpaque(false);
        this.setBackground(new Color(255, 255, 255, 0));
        this.setLayout(null);

        
        // Pulsanti per scelta del tipo di mappa (normale, satellitare, mista)
        mappaNormale = new JButton();
        mappaSatellitare = new JButton();
        mappaMista = new JButton();
        
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
        
        mapButtonGroup.setOpaque(false);
        mapButtonGroup.setBounds(30, 20, 180, 50);
        mapButtonGroup.setBackground(new Color(255, 255, 255, 0));

        mapButtonGroup.add(mappaNormale);
        mapButtonGroup.add(mappaSatellitare);
        mapButtonGroup.add(mappaMista);
        
        this.add(mapButtonGroup);
        
        
        // Barra di ricerca, con annessa gestione del testo placeholder
        searchBar = new JTextField("  Cerca linea o fermata...");
        
        searchBar.setBounds(440, 5, 300, 35);
        searchBar.setBackground(new Color(255, 255, 255, 160));
        searchBar.setFont(new Font("Arial Nova", Font.BOLD, 12));
        
        this.add(searchBar);

        searchBar.addFocusListener(new FocusListener() {
        	
            @Override
            public void focusGained(FocusEvent e) {
                if (searchBar.getText().equals("  Cerca linea o fermata...")) {
                    searchBar.setText("");   // Cancella il testo attuale
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBar.getText().isEmpty()) {
                    searchBar.setText("  Cerca linea o fermata...");   // Reinserisce il testo predefinito
                }
            }
        });
        
        
        // Pulsante per la sezione utente
        btnLogin = new JButton();
        
        btnLogin.setPreferredSize(new Dimension(50, 50));
        btnLogin.setBounds(getWidth() - btnLogin.getWidth() - 40, 10, 50, 50);
        
        ImageIcon icon = new ImageIcon("src/resources/user_placeholder.png");
        Image scaledImage = icon.getImage().getScaledInstance(44, 44, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);
        btnLogin.setIcon(newIcon);
        
        this.add(btnLogin);
        
        btnLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// TODO: azione da fare quando viene premuto il pulsante
        	}
        });
    }
    
    
    public JTextField getSearchBar() {
    	return this.searchBar;
    }
    
    public JPanel getMapButtonGroup() {
    	return this.mapButtonGroup;
    }
    
    public JButton getBtnLogin() {
    	return this.btnLogin;
    }
}