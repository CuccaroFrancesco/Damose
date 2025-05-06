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
	
	private JLabel titolo, codiceFermata, lblArrivi;
	private JButton btnWheelChair, btnClose, btnPreferiti;
	private Utente utente;
	private DatiGTFS dati;
	
	
	// Costruttore del pannello stopPanel
	public StopPanel(Utente utente, DatiGTFS dati) {
		
		this.utente = utente;
		this.dati = dati;
		
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		
		
		// JLabel che contiene il nome della fermata
        titolo = new JLabel("Nome fermata");
		
		titolo.setForeground(new Color(255, 255, 255));
		titolo.setFont(new Font("Arial Nova", Font.BOLD, 24));
		titolo.setFocusable(false);
								
		titolo.setBounds(20, 107, 340, 50);
								
		this.add(titolo);
		
		
		// JLabel per il codice della fermata
		codiceFermata = new JLabel("Codice fermata");
		
		codiceFermata.setForeground(Color.WHITE);
		codiceFermata.setFont(new Font("Arial Nova", Font.PLAIN, 14));
		codiceFermata.setFocusable(false);
		
		codiceFermata.setBounds(20, 135, 145, 50);
        
        this.add(codiceFermata);
        
        
        // JLabel che contiene la scritta "Prossimi arrivi"
		lblArrivi = new JLabel("Prossimi arrivi");
		
		lblArrivi.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblArrivi.setForeground(Color.WHITE);
		lblArrivi.setFont(new Font("Arial Nova", Font.BOLD, 24));
		lblArrivi.setFocusable(false);
		
		lblArrivi.setBounds(0, 250, 400, 50);
		
		this.add(lblArrivi);
		
        
		// Pulsante per rimuovere o aggiungere la fermata ai preferiti dell'utente
		btnPreferiti = new JButton();
		
        btnPreferiti.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
        btnPreferiti.setContentAreaFilled(false);
        btnPreferiti.setFocusPainted(false);
        btnPreferiti.setBorderPainted(false);
        btnPreferiti.setBackground(new Color(130, 36, 51));
		
        btnPreferiti.setPreferredSize(new Dimension(50, 50));
        btnPreferiti.setBounds(350, 107, 50, 50);
		
        this.add(btnPreferiti);
        
        
        // Pulsante per accessibilità con sedia a rotelle (non interattivo, serve solo per implementare comodamente il simbolo)
		btnWheelChair = new JButton();
		
		btnWheelChair.setForeground(new Color(255, 255, 255));
		btnWheelChair.setFont(new Font("Arial Nova", Font.BOLD, 15));
		btnWheelChair.setHorizontalAlignment(SwingConstants.LEADING);
		btnWheelChair.setText("Disponibilità");
		btnWheelChair.setFocusable(false);
		
		btnWheelChair.setContentAreaFilled(false);
		btnWheelChair.setFocusPainted(false);
		btnWheelChair.setBorderPainted(false);
		btnWheelChair.setBackground(new Color(130, 36, 51));
		
		btnWheelChair.setPreferredSize(new Dimension(50, 50));
		btnWheelChair.setBounds(10, 200, 205, 50);
		
		ImageIcon iconWheel = new ImageIcon("src/resources/sedia.png");
        Image scaledImageWheel = iconWheel.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon newIconWheel = new ImageIcon(scaledImageWheel);
        btnWheelChair.setIcon(newIconWheel);
        
        this.add(btnWheelChair);
        
        
        // Pulsante per chiuderere lo stopPanel
     	btnClose = new JButton("Chiudi finestra");
     	
     	btnClose.addActionListener(new ActionListener() {
     		public void actionPerformed(ActionEvent e) {
     			StopPanel.this.setVisible(false);
     		}
     	});
     			
     	btnClose.setBounds(-10, 20, 185, 30);
     	btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
     			
     	btnClose.setFont(new Font("Arial Nova", Font.BOLD, 14));
     	btnClose.setForeground(new Color(255, 255, 255));
     			
     	btnClose.setBorderPainted(false);
     	btnClose.setFocusPainted(false);
     	btnClose.setContentAreaFilled(false);
     		
     	ImageIcon iconClose = new ImageIcon("src/resources/close.png");
     	Image scaledImageClose = iconClose.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon newIconClose = new ImageIcon(scaledImageClose);
        btnClose.setIcon(newIconClose);
             
        this.setVisible(false);
        
    	this.add(btnClose); 
	}
	
	
	// Metodo che gestisce la creazione dello stopPanel
	public void creaPannelloFermata(Stop fermata) {
		
		this.setVisible(true);
		
		titolo.setText(fermata.getName());
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
		
		for (ActionListener a : btnPreferiti.getActionListeners()) {
		    btnPreferiti.removeActionListener(a);
		}
		
		// Funzionalità per il pulsante btnPreferiti
		btnPreferiti.addActionListener(new ActionListener() {
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
		        btnPreferiti.setIcon(new ImageIcon(scaledIconPreferiti));
		    }
		});
	}	
}