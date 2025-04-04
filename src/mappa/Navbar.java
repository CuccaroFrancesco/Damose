package mappa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Navbar extends JPanel {

	private Mappa mapPanel;
	private JComboBox<String> scelta;
    public JTextField searchBar;
    public JPanel mapButtonGroup;
    private JButton mappaNormale;
    private JButton mappaSatellitare;
    private JButton mappaMista;
    public JButton btnLogin;

    public Navbar(Mappa mapPanel) {
    	
        this.mapPanel = mapPanel;

        setOpaque(false);
        setBackground(new Color(255, 255, 255, 0));
        setLayout(null);

        
        // Gruppo di pulsanti per scelta del tipo di mappa (normale, satellitare, mista)
        mapButtonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        mapButtonGroup.setOpaque(false);
        mapButtonGroup.setBackground(new Color(255, 255, 255, 0));
        
        mappaNormale = new JButton();
        mappaSatellitare = new JButton();
        mappaMista = new JButton();
        
        Dimension buttonDimension = new Dimension(30, 30);
        mappaNormale.setPreferredSize(buttonDimension);
        mappaSatellitare.setPreferredSize(buttonDimension);
        mappaMista.setPreferredSize(buttonDimension);
        
        mapButtonGroup.add(mappaNormale);
        mapButtonGroup.add(mappaSatellitare);
        mapButtonGroup.add(mappaMista);
        
        this.add(mapButtonGroup);
        
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
        
        
        // Barra di ricerca
        searchBar = new JTextField("  Cerca linea o fermata...");
        
        searchBar.setBounds(440, 5, 300, 30);
        searchBar.setBackground(new Color(255, 255, 255, 160));
        searchBar.setFont(new Font("Arial Nova", Font.BOLD, 12));
        
        this.add(searchBar);

        
        // Gestione placeholder della barra di ricerca
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
        
        
        // Sezione utente
        btnLogin = new JButton();
        
        btnLogin.setBounds(950, 1, 32, 30);
        
        ImageIcon icon = new ImageIcon("src/resources/circle-user-solid.png");
        Image scaledImage = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);
        btnLogin.setIcon(newIcon);
        
        this.add(btnLogin);
        
        btnLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// dopo il click
        	}
        });
    }
}