package mappa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Navbar extends JPanel {

    private JTextField searchBar;
    private Mappa mapPanel;

    public Navbar(Mappa mapPanel) {
    	
        this.mapPanel = mapPanel;

        setOpaque(false);
        setBackground(new Color(255, 255, 255, 0)); // Trasparente
        setLayout(null);

        
        // ComboBox per selezionare il tipo di mappa
        JComboBox<String> scelta = new JComboBox<>();
        
        scelta.setOpaque(false);
        scelta.setFont(new Font("Arial Nova", Font.BOLD, 12));
        scelta.setModel(new DefaultComboBoxModel<>(new String[]{"Normale", "Satellitare", "Mista"}));
        scelta.setBounds(30, 5, 170, 30);
        add(scelta);
        scelta.setBackground(new Color(255, 255, 255, 160));

        
        // Listener per cambiare tipo di mappa
        scelta.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	
                int selectedIndex = scelta.getSelectedIndex();
                TilesetManager.updateMap(mapPanel, selectedIndex);
            }
        });
        
        JButton btnLogin = new JButton();
        btnLogin.setBounds(950, 1, 32, 30);
        add(btnLogin);
        ImageIcon icona = new ImageIcon("src/resources/circle-user-solid.png");
        Image scaledImage = icona.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);
        btnLogin.setIcon(newIcon);
        btnLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// dopo il click
        	}
        });

        
        // Campo di ricerca
        searchBar = new JTextField("  Cerca linea o fermata...");
        
        searchBar.setFont(new Font("Arial Nova", Font.BOLD, 12));
        searchBar.setBounds(440, 5, 300, 30);
        add(searchBar);
        searchBar.setBackground(new Color(255, 255, 255, 160));

        
        // Gestione placeholder
        searchBar.addFocusListener(new FocusListener() {
        	
            @Override
            public void focusGained(FocusEvent e) {
            	
                if (searchBar.getText().equals("  Cerca linea o fermata...")) {
                	
                    searchBar.setText(""); // Cancella il testo attuale
                }
            }

            
            @Override
            public void focusLost(FocusEvent e) {
            	
                if (searchBar.getText().isEmpty()) {
                	
                    searchBar.setText("  Cerca linea o fermata..."); // Reinserisce il testo predefinito
                }
            }
        });
    }
}
