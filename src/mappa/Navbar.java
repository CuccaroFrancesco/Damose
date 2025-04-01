package mappa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Navbar extends JPanel {

	private Mappa mapPanel;
	private JComboBox<String> scelta;
    private JTextField searchBar;
    private JButton mappaNormale;
    private JButton mappaSatellitare;
    private JButton mappaMista;

    public Navbar(Mappa mapPanel) {
    	
        this.mapPanel = mapPanel;

        setOpaque(false);
        setBackground(new Color(255, 255, 255, 0)); // Trasparente
        setLayout(null);

        
        // Scelta del tipo di mappa (normale, satellitare, mista)
        mappaNormale = new JButton();
        mappaSatellitare = new JButton();
        mappaMista = new JButton();
        
        mappaNormale.setBounds(30, 5, 30, 30);
        mappaSatellitare.setBounds(62, 5, 30, 30);
        mappaMista.setBounds(94, 5, 30, 30);
        
        this.add(mappaNormale);
        this.add(mappaSatellitare);
        this.add(mappaMista);
        
        mappaNormale.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TilesetManager.updateMap(mapPanel, 0);
        	}
        });
        
        mappaSatellitare.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TilesetManager.updateMap(mapPanel, 1);
        	}
        });
        
        mappaMista.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		TilesetManager.updateMap(mapPanel, 2);
        	}
        });
        
        
        // Barra di ricerca
        searchBar = new JTextField("  Cerca linea o fermata...");
        
        searchBar.setBounds(440, 5, 300, 30);
        searchBar.setBackground(new Color(255, 255, 255, 160));
        searchBar.setFont(new Font("Arial Nova", Font.BOLD, 12));
        
        this.add(searchBar);

        
        // Gestione placeholder
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
        JButton btnLogin = new JButton();
        
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