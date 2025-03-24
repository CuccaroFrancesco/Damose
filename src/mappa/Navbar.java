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
        setBackground(new Color(255, 255, 255, 0)); // Semi-trasparente
        setBounds(0, 0, 1059, 33);
        setLayout(null);

        
        // ComboBox per selezionare il tipo di mappa
        JComboBox<String> scelta = new JComboBox<>();
        
        scelta.setOpaque(false);
        scelta.setFont(new Font("Arial Nova", Font.BOLD, 12));
        scelta.setModel(new DefaultComboBoxModel<>(new String[]{"Normale", "Satellitare", "Mista"}));
        scelta.setBounds(29, 6, 169, 30);
        add(scelta);
        scelta.setBackground(new Color(255, 255, 255, 180));

        
        // Listener per cambiare tipo di mappa
        scelta.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
            	
                int selectedIndex = scelta.getSelectedIndex();
                TilesetManager.updateMap(mapPanel, selectedIndex);
            }
        });

        
        // Campo di ricerca
        searchBar = new JTextField("  Cerca linea o fermata...");
        
        searchBar.setFont(new Font("Arial Nova", Font.BOLD, 12));
        searchBar.setBounds(442, 6, 300, 30);
        add(searchBar);
        searchBar.setColumns(10);
        searchBar.setBackground(new Color(255, 255, 255, 180));

        
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
