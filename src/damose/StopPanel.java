package damose;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.onebusaway.gtfs.model.Stop;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;



public class StopPanel extends JPanel {
	
	private JLabel nomeFermata, codiceFermata, lblArrivi;
	private JButton btnClose, btnFavorite, btnWebsite, btnWheelChair, btnStopType;
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
								
		nomeFermata.setBounds(80, 70, 180, 50);
								
		this.add(nomeFermata);
		
		
		// JLabel per il codice della fermata
		codiceFermata = new JLabel("Codice fermata");
		
		codiceFermata.setForeground(new Color(210, 210, 210));
		codiceFermata.setFont(new Font("Arial Nova", Font.ITALIC, 12));
		codiceFermata.setFocusable(false);
		
		codiceFermata.setBounds(20, 125, 300, 20);
        
        this.add(codiceFermata);
        
        
        // JLabel che contiene la scritta "Prossimi arrivi:"
		lblArrivi = new JLabel("Prossimi arrivi:");
		
		lblArrivi.setHorizontalAlignment(SwingConstants.LEADING);
		
		lblArrivi.setForeground(Color.WHITE);
		lblArrivi.setFont(new Font("Arial Nova", Font.BOLD, 24));
		lblArrivi.setFocusable(false);
		
		lblArrivi.setBounds(20, 200, 200, 50);
		
		this.add(lblArrivi);
		

        // Pulsante per chiuderere lo stopPanel
        btnClose = new JButton("Chiudi pannello");
        
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnClose.setFont(new Font("Arial Nova", Font.BOLD, 14));
        btnClose.setForeground(new Color(255, 255, 255));
        
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setContentAreaFilled(false);
        
        btnClose.setBounds(-25, 5, 200, 30);
        
        ImageIcon iconClose = new ImageIcon("src/resources/close.png");
        Image scaledImageClose = iconClose.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon newIconClose = new ImageIcon(scaledImageClose);
        btnClose.setIcon(newIconClose);
        
        btnClose.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		StopPanel.this.setVisible(false);
        	}
        });
        
        this.add(btnClose);
        
        
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
        btnFavorite.setBounds(290, 70, 50, 50);
        
        this.add(btnFavorite);
        
        
        // Pulsante per aprire l'URL relativo alla linea in questione
        btnWebsite = new JButton();
        
        btnWebsite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnWebsite.setForeground(new Color(255, 255, 255));
        btnWebsite.setFont(new Font("Arial Nova", Font.BOLD, 11));
        btnWebsite.setText(" Sito Web");
        btnWebsite.setHorizontalAlignment(SwingConstants.LEADING);
        
        btnWebsite.setContentAreaFilled(false);
        btnWebsite.setFocusPainted(false);
        btnWebsite.setBorderPainted(false);
        btnWebsite.setBackground(new Color(130, 36, 51));
        
        btnWebsite.setPreferredSize(new Dimension(20, 20));
        btnWebsite.setBounds(3, 160, 120, 20);
        
        ImageIcon iconWebsite = new ImageIcon("src/resources/mondo.png");
        Image scaledImageWebsite = iconWebsite.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon newIconWebsite = new ImageIcon(scaledImageWebsite);
        btnWebsite.setIcon(newIconWebsite);
        
        this.add(btnWebsite);
        
        
        // Pulsante per accessibilità con sedia a rotelle (non interattivo, serve solo per implementare comodamente il simbolo)
        btnWheelChair = new JButton();
     		
     	btnWheelChair.setForeground(new Color(255, 255, 255));
     	btnWheelChair.setFont(new Font("Arial Nova", Font.BOLD, 11));
     	btnWheelChair.setHorizontalAlignment(SwingConstants.LEADING);
     	btnWheelChair.setText("Disponibilità");
     	btnWheelChair.setFocusable(false);
     		
     	btnWheelChair.setContentAreaFilled(false);
     	btnWheelChair.setFocusPainted(false);
     	btnWheelChair.setBorderPainted(false);
     	btnWheelChair.setBackground(new Color(130, 36, 51));
     		
     	btnWheelChair.setPreferredSize(new Dimension(20, 20));
     	btnWheelChair.setBounds(140, 160, 120, 20);
     		
     	ImageIcon iconWheelChair = new ImageIcon("src/resources/sedia.png");
        Image scaledImageWheelChair = iconWheelChair.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon newIconWheelChair = new ImageIcon(scaledImageWheelChair);
        btnWheelChair.setIcon(newIconWheelChair);
             
        this.add(btnWheelChair);
        
        
        // Pulsante per il tipo della fermata (fermata, stazione, ingresso di stazione, nodo, boarding area) (non interattivo, serve solo a visualizzare comodamente icona e informazione)
        btnStopType = new JButton();
        
        btnStopType.setForeground(Color.WHITE);
        btnStopType.setFont(new Font("Arial Nova", Font.BOLD, 11));
        btnStopType.setHorizontalAlignment(SwingConstants.LEADING);
        btnStopType.setText("Tipo di fermata");
        
        btnStopType.setContentAreaFilled(false);
        btnStopType.setFocusPainted(false);
        btnStopType.setBorderPainted(false);
        btnStopType.setBackground(new Color(130, 36, 51));
        
        btnStopType.setFocusable(false);
        
        btnStopType.setPreferredSize(new Dimension(20, 20));
        btnStopType.setBounds(280, 160, 120, 20);
        
        this.add(btnStopType);
	}
	
	
	// Metodo che gestisce la creazione dello stopPanel
	public void creaPannelloFermata(Stop fermata) {
		
		this.setVisible(true);
		
		nomeFermata.setText(fermata.getName());
		codiceFermata.setText("ID: " + fermata.getCode());
		int accessibile = fermata.getWheelchairBoarding(); 
		
		switch (accessibile) {
			case 1:
				btnWheelChair.setText("Accessibile");
				break;
				
			case 0:
				btnWheelChair.setText("Non accessibile");
				break;
				
			default:
				btnWheelChair.setText("Nessuna informazione");
				break;
		}
		
		for (ActionListener a : btnFavorite.getActionListeners()) {
		    btnFavorite.removeActionListener(a);
		}
		
		// Funzionalità per il pulsante btnPreferiti
		btnFavorite.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        List<String> listaFermatePreferite = utente.getFermatePreferite();
		        String idFermata = fermata.getId().getId();
		        boolean isPreferita;
		        
		        if (listaFermatePreferite.contains(idFermata)) {
		        	
		            listaFermatePreferite.remove(idFermata);
		            isPreferita = false;
		        
		        } else {
		        	
		            listaFermatePreferite.add(idFermata);
		            isPreferita = true;
		        }
		        
		        try {
					utente.cambiaFermatePreferite(listaFermatePreferite);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        
		        String iconPath = isPreferita ? "src/resources/cuore.png" : "src/resources/cuore-vuoto.png";
		        ImageIcon iconPreferiti = new ImageIcon(iconPath);
		        Image scaledIconPreferiti = iconPreferiti.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		        btnFavorite.setIcon(new ImageIcon(scaledIconPreferiti));
		    }
		});
	}	
}