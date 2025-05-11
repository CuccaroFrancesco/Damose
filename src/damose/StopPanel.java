package damose;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

import org.onebusaway.gtfs.model.*;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;



public class StopPanel extends JPanel {
	
	private JLabel nomeFermata, codiceFermata, lblArrivi, lblLineePassanti;
	private JButton btnClose, btnStopIcon, btnFavorite, btnCoordinates;
	private JPanel lineePassantiPanel;
	private JScrollPane lineePassantiScrollPane;
	private Utente utente;
	private DatiGTFS dati;
	
	
	// Costruttore del pannello stopPanel
	public StopPanel(Utente utente, DatiGTFS dati) {
		
		this.utente = utente;
		this.dati = dati;
		
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		this.setVisible(false);
		
		
		// JLabel che contiene il nome della fermata
        nomeFermata = new JLabel("Nome fermata");
		
		nomeFermata.setForeground(new Color(255, 255, 255));
		nomeFermata.setFont(new Font("Arial Nova", Font.BOLD, 24));
		nomeFermata.setFocusable(false);
								
		nomeFermata.setBounds(70, 65, 200, 50);
								
		this.add(nomeFermata);
		
		
		// JLabel per il codice della fermata
		codiceFermata = new JLabel("Codice fermata");
		
		codiceFermata.setForeground(new Color(210, 210, 210));
		codiceFermata.setFont(new Font("Arial Nova", Font.ITALIC, 12));
		codiceFermata.setFocusable(false);
		
		codiceFermata.setBounds(20, 120, 300, 20);
        
        this.add(codiceFermata);
        
        
        // JLabel che contiene la scritta "Prossimi arrivi:"
		lblArrivi = new JLabel("Prossimi arrivi:");
		
		lblArrivi.setHorizontalAlignment(SwingConstants.LEADING);
		
		lblArrivi.setForeground(Color.WHITE);
		lblArrivi.setFont(new Font("Arial Nova", Font.BOLD, 24));
		lblArrivi.setFocusable(false);
		
		lblArrivi.setBounds(20, 160, 200, 50);
		
		this.add(lblArrivi);
		
		
		// JLabel che contiene la scritta "Appartiene a:"
		lblLineePassanti = new JLabel("Appartiene a:");
		
		lblLineePassanti.setHorizontalAlignment(SwingConstants.LEADING);
		
		lblLineePassanti.setForeground(Color.WHITE);
		lblLineePassanti.setFont(new Font("Arial Nova", Font.BOLD, 24));
		lblLineePassanti.setFocusable(false);
		
		lblLineePassanti.setBounds(20, 380, 200, 50);
		
		this.add(lblLineePassanti);
		

        // Pulsante per chiuderere lo stopPanel
        btnClose = new JButton(" Chiudi pannello");
        
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnClose.setFont(new Font("Arial Nova", Font.BOLD, 14));
        btnClose.setForeground(new Color(255, 255, 255));
        
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setContentAreaFilled(false);
        
        btnClose.setBounds(-25, 5, 200, 30);
        
        ImageIcon iconClose = new ImageIcon("src/resources/close.png");
        Image scaledImageClose = iconClose.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon newIconClose = new ImageIcon(scaledImageClose);
        btnClose.setIcon(newIconClose);
        
        btnClose.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		StopPanel.this.setVisible(false);
        	}
        });
        
        this.add(btnClose);
        
        
        // Pulsante per l'icona di una fermata (non interattivo, serve solo a visualizzare comodamente l'icona)
        btnStopIcon = new JButton();
        
        btnStopIcon.setContentAreaFilled(false);
        btnStopIcon.setFocusPainted(false);
        btnStopIcon.setBorderPainted(false);
        btnStopIcon.setBackground(new Color(255, 255, 255));
		
        btnStopIcon.setFocusable(false);
		
        btnStopIcon.setPreferredSize(new Dimension(40, 40));
        btnStopIcon.setBounds(20, 70, 40, 40);
        
        ImageIcon iconStop = new ImageIcon("src/resources/fermata-bianco.png");
        Image scaledImageStop = iconStop.getImage().getScaledInstance(32, 40, Image.SCALE_SMOOTH);
        ImageIcon newIconStop = new ImageIcon(scaledImageStop);
        btnStopIcon.setIcon(newIconStop);
		
		this.add(btnStopIcon);
        
        
        // Pulsante per l'aggiunta o la rimozione della fermata dai preferiti dell'utente
        btnFavorite = new JButton();
        
        btnFavorite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnFavorite.setContentAreaFilled(false);
        btnFavorite.setFocusPainted(false);
        btnFavorite.setBorderPainted(false);
        btnFavorite.setBackground(new Color(130, 36, 51));
        
        btnFavorite.setEnabled(false);
        btnFavorite.setVisible(false);
        
        btnFavorite.setPreferredSize(new Dimension(50, 50));
        btnFavorite.setBounds(290, 68, 50, 50);
        
        this.add(btnFavorite);
        
        
        // Pulsante per le coordinate della fermata (non interattivo, serve solo a visualizzare comodamente icona e informazione)
        btnCoordinates = new JButton();
        
        btnCoordinates.setForeground(Color.WHITE);
        btnCoordinates.setFont(new Font("Arial Nova", Font.BOLD, 12));
        btnCoordinates.setHorizontalAlignment(SwingConstants.LEADING);
        btnCoordinates.setText(" Coordinate della fermata");
        
        btnCoordinates.setContentAreaFilled(false);
        btnCoordinates.setFocusPainted(false);
        btnCoordinates.setBorderPainted(false);
        btnCoordinates.setBackground(new Color(130, 36, 51));
        
        btnCoordinates.setFocusable(false);
        
        btnCoordinates.setPreferredSize(new Dimension(20, 20));
        btnCoordinates.setBounds(145, 119, 180, 20);
        
        ImageIcon iconCoordinates = new ImageIcon("src/resources/coordinates.png");
        Image scaledImageCoordinates = iconCoordinates.getImage().getScaledInstance(20, 18, Image.SCALE_SMOOTH);
        ImageIcon newIconCoordinates = new ImageIcon(scaledImageCoordinates);
        btnCoordinates.setIcon(newIconCoordinates);
        
        this.add(btnCoordinates);
	}
	
	
	// Metodo che gestisce la creazione dello stopPanel
	public void creaPannelloFermata(Stop fermata) {
		
		this.setVisible(true);
		
		if (lineePassantiScrollPane != null) {
		    this.remove(lineePassantiScrollPane);
		}
		
		if (utente.getFermatePreferite() != null) {
			btnFavorite.setEnabled(true);
			btnFavorite.setVisible(true);
			
		    boolean isPreferita = false;
		    
		    for (String fermataPreferita : utente.getFermatePreferite()) {
		        if (fermataPreferita.equals(fermata.getId().getId())) {
		            isPreferita = true;
		            break;
		        }
		    }

		    String iconCuorePath = isPreferita ? "src/resources/cuore.png" : "src/resources/cuore-vuoto.png";
		    ImageIcon iconCuore = new ImageIcon(iconCuorePath);
		    Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		    btnFavorite.setIcon(new ImageIcon(scaledImageCuore));
		}
		
		for (ActionListener a : btnFavorite.getActionListeners()) {
		    btnFavorite.removeActionListener(a);
		}
		
		btnFavorite.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        List<String> fermatePreferite = utente.getFermatePreferite();
		        String idFermata = fermata.getId().getId();
		        
		        boolean isOraPreferita;
		        
		        if (fermatePreferite.contains(idFermata)) {
		        	fermatePreferite.remove(idFermata);
		        	isOraPreferita = false;
		        } else {
		        	fermatePreferite.add(idFermata);
		            isOraPreferita = true;
		        }
		        
		        try {
					utente.cambiaFermatePreferite(fermatePreferite);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        
		        String iconCuorePath = isOraPreferita ? "src/resources/cuore.png" : "src/resources/cuore-vuoto.png";
		        ImageIcon iconCuore = new ImageIcon(iconCuorePath);
		        Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		        btnFavorite.setIcon(new ImageIcon(scaledImageCuore));
		    }
		});
		
		nomeFermata.setText(fermata.getName());
		codiceFermata.setText("ID: " + fermata.getCode());
		btnCoordinates.setText(" " + Math.floor(fermata.getLat() * 100000) / 100000 + ", " + Math.floor(fermata.getLon() * 100000) / 100000);
		
		List<Route> lineePassanti = dati.getLineePassantiPerFermata(fermata);
		
		lineePassantiPanel = new JPanel();
		lineePassantiPanel.setLayout(null);
		lineePassantiPanel.setBackground(new Color(130, 36, 51));
		lineePassantiPanel.setPreferredSize(new Dimension(350, Math.max(100, lineePassanti.size() * 40)));
		
		for (int i = 0; i < lineePassanti.size(); i++) {
			
			Route linea = lineePassanti.get(i);
			int y = i * 40;
			
			JButton lineaBtn = new JButton();
			
			lineaBtn.setBounds(10, y, 290, 60);
			
			lineaBtn.setFocusable(false);
            lineaBtn.setContentAreaFilled(false);
            lineaBtn.setFocusPainted(false);
            lineaBtn.setBorderPainted(false);
            
            lineaBtn.setFont(new Font("Arial Nova", Font.BOLD, 12));
            lineaBtn.setText(linea.getShortName());
            lineaBtn.setHorizontalAlignment(SwingConstants.LEADING);
            
            lineaBtn.setForeground(new Color(255, 255, 255));
            lineaBtn.setBackground(new Color(130, 36, 51));
            lineaBtn.setBorder(BorderFactory.createEmptyBorder());
            
            lineePassantiPanel.add(lineaBtn);
		}
		
		lineePassantiScrollPane = new JScrollPane(lineePassantiPanel);
        
		lineePassantiScrollPane.setBorder(null);
		lineePassantiScrollPane.setBounds(10, 430, 350, 200);
        
		lineePassantiScrollPane.getVerticalScrollBar().setUnitIncrement(12);
		lineePassantiScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		lineePassantiScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        this.add(lineePassantiScrollPane);
        lineePassantiPanel.repaint();
	}	
}