package damose;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.onebusaway.gtfs.model.Route;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class LineaPanel extends JPanel {
	
	private JLabel codiceLinea, agenziaENomeLinea;
	private JButton btnWheelChair, btnClose, btnLink, btnPreferiti;
	private Utente utente;
	private DatiGTFS dati;
	
	// Costruttore del lineaPanel
	public LineaPanel(Utente utente, DatiGTFS dati) {
		
		this.utente = utente;
		this.dati = dati;
		
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		
		
		// JLabel che contiene il nome (long name) della linea in questione
		codiceLinea = new JLabel("Codice linea");
		
		codiceLinea.setForeground(Color.WHITE);
		codiceLinea.setFont(new Font("Arial Nova", Font.BOLD, 26));
		codiceLinea.setFocusable(false);
								
		codiceLinea.setBounds(80, 70, 200, 50);
								
		this.add(codiceLinea);
        
		
		// JLabel che contiene il nome dell'agenzia che gestisce la linea in questione
        agenziaENomeLinea = new JLabel("Agenzia  -  Nome linea");
        
        agenziaENomeLinea.setForeground(Color.WHITE);
        agenziaENomeLinea.setFont(new Font("Arial Nova", Font.PLAIN, 12));
        agenziaENomeLinea.setFocusable(false);
        
        agenziaENomeLinea.setBounds(20, 120, 300, 20);
        
        this.add(agenziaENomeLinea);
        
        
        // Pulsante per l'aggiunta o la rimozione della linea ai preferiti dell'utente
        btnPreferiti = new JButton();
        
        btnPreferiti.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnPreferiti.setContentAreaFilled(false);
        btnPreferiti.setFocusPainted(false);
        btnPreferiti.setBorderPainted(false);
        btnPreferiti.setBackground(new Color(130, 36, 51));
        
        btnPreferiti.setEnabled(false);
        btnPreferiti.setVisible(false);
        
        btnPreferiti.setPreferredSize(new Dimension(50, 50));
        btnPreferiti.setBounds(290, 70, 50, 50);
        
        this.add(btnPreferiti);
        
        
        // Pulsante per chiudere il lineaPanel
        btnClose = new JButton("Chiudi pannello");
     			
     	btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
     			
     	btnClose.setFont(new Font("Arial Nova", Font.BOLD, 14));
     	btnClose.setForeground(new Color(255, 255, 255));
     			
     	btnClose.setBorderPainted(false);
     	btnClose.setFocusPainted(false);
     	btnClose.setContentAreaFilled(false);
     	
     	btnClose.setBounds(-25, 5, 200, 30);
     		
     	ImageIcon iconClose = new ImageIcon("src/resources/close.png");
     	Image scaledImageClose = iconClose.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon newIconClose = new ImageIcon(scaledImageClose);
        btnClose.setIcon(newIconClose);
        
        btnClose.addActionListener(new ActionListener() {
     		public void actionPerformed(ActionEvent e) {
     			LineaPanel.this.setVisible(false);
     		}
     	});
             
    	this.add(btnClose);   

        
        // Pulsante per aprire l'URL relativo alla linea in questione
		btnLink = new JButton();
		
		btnLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnLink.setForeground(new Color(255, 255, 255));
		btnLink.setFont(new Font("Arial Nova", Font.BOLD, 12));
		btnLink.setHorizontalAlignment(SwingConstants.LEADING);
		btnLink.setText("Sito Web");
		
		btnLink.setContentAreaFilled(false);
		btnLink.setFocusPainted(false);
		btnLink.setBorderPainted(false);
		btnLink.setBackground(new Color(130, 36, 51));
		
		btnLink.setPreferredSize(new Dimension(20, 20));
		btnLink.setBounds(0, 150, 200, 20);
		
		ImageIcon iconLink = new ImageIcon("src/resources/mondo.png");
        Image scaledImageLink = iconLink.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon newIconLink = new ImageIcon(scaledImageLink);
        btnLink.setIcon(newIconLink);
        
		this.add(btnLink);
		
		
		this.setVisible(false);
	}
	
	
	// Metodo che "costruisce" concretamente il lineaPanel in base alla linea in questione
	public void creaPannelloLinea(Route linea, DatiGTFS dati) {
		
		this.setVisible(true);
		
		if (utente.getLineePreferite() != null) {
			btnPreferiti.setEnabled(true);
			btnPreferiti.setVisible(true);
			
		    boolean isPreferita = false;
		    
		    for (String lineaPreferita : utente.getLineePreferite()) {
		        if (lineaPreferita.equals(linea.getId().getId())) {
		            isPreferita = true;
		            break;
		        }
		    }

		    String iconCuorePath = isPreferita ? "src/resources/cuore.png" : "src/resources/cuore-vuoto.png";
		    ImageIcon iconCuore = new ImageIcon(iconCuorePath);
		    Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		    btnPreferiti.setIcon(new ImageIcon(scaledImageCuore));
		}
		
		for (ActionListener a : btnPreferiti.getActionListeners()) {
		    btnPreferiti.removeActionListener(a);
		}
		
		for (ActionListener a : btnLink.getActionListeners()) {
		    btnLink.removeActionListener(a);
		}
		
		btnPreferiti.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        List<String> lineePreferite = utente.getLineePreferite();
		        String idLinea = linea.getId().getId();
		        
		        boolean isOraPreferita;
		        
		        if (lineePreferite.contains(idLinea)) {
		            lineePreferite.remove(idLinea);
		            isOraPreferita = false;
		        } else {
		            lineePreferite.add(idLinea);
		            isOraPreferita = true;
		        }
		        
		        try {
					utente.cambiaLineePreferite(lineePreferite);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        
		        String iconCuorePath = isOraPreferita ? "src/resources/cuore.png" : "src/resources/cuore-vuoto.png";
			    ImageIcon iconCuore = new ImageIcon(iconCuorePath);
			    Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			    btnPreferiti.setIcon(new ImageIcon(scaledImageCuore));
		    }
		});
		
		String agenzia = linea.getAgency().getName();
		String longName = linea.getLongName();
		String shortName = linea.getShortName();
		String url = linea.getUrl();
		int tipoLinea = linea.getType();
		
		codiceLinea.setText(shortName);
		
		if(longName == null || longName.isEmpty()) {
			agenziaENomeLinea.setText(agenzia);
		} else {
			agenziaENomeLinea.setText(agenzia + " - " + longName);
		}
		
		btnLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
						desktop.browse(new URI(url));
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}  
                }
			}
		});
	}
}