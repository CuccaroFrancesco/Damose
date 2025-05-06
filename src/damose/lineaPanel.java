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
	
	private JLabel titolo, scrittaAgenzia;
	private JButton btnWheelChair, btnClose, btnLink, btnPreferiti;
	private Utente utente;
	private DatiGTFS dati;
	
	public LineaPanel(Utente utente, DatiGTFS dati) {
		this.utente = utente;
		this.dati = dati;
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		
		btnPreferiti = new JButton();
        btnPreferiti.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
        btnPreferiti.setContentAreaFilled(false);
        btnPreferiti.setFocusPainted(false);
        btnPreferiti.setBorderPainted(false);
        btnPreferiti.setBackground(new Color(130, 36, 51));
		
        btnPreferiti.setPreferredSize(new Dimension(50, 50));
        btnPreferiti.setBounds(350, 107, 50, 50);
		
        this.add(btnPreferiti);
		
		btnLink = new JButton();
		btnLink.setForeground(new Color(255, 255, 255));
		btnLink.setFont(new Font("Arial Nova", Font.BOLD, 15));
		btnLink.setHorizontalAlignment(SwingConstants.LEADING);
		btnLink.setText("Sito web");
		
		btnLink.setContentAreaFilled(false);
		btnLink.setFocusPainted(false);
		btnLink.setBorderPainted(false);
		btnLink.setBackground(new Color(130, 36, 51));
		
		btnLink.setPreferredSize(new Dimension(50, 50));
		btnLink.setBounds(10, 215, 145, 50);
		btnLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		ImageIcon icon = new ImageIcon("src/resources/mondo.png");
        Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(scaledImage);
        btnLink.setIcon(newIcon);
        
		this.add(btnLink);
		
		titolo = new JLabel("Nome linea");
		
		titolo.setForeground(new Color(255, 255, 255));
		titolo.setFont(new Font("Arial Nova", Font.BOLD, 24));
		titolo.setFocusable(false);
								
		titolo.setBounds(20, 107, 340, 50);
								
		this.add(titolo);
        
        scrittaAgenzia = new JLabel("Nome agenzia");
        scrittaAgenzia.setForeground(Color.WHITE);
        scrittaAgenzia.setFont(new Font("Arial Nova", Font.PLAIN, 14));
        scrittaAgenzia.setFocusable(false);
        scrittaAgenzia.setBounds(20, 135, 145, 50);
        this.add(scrittaAgenzia);
        
        btnClose = new JButton("Chiudi pannello");
     	btnClose.addActionListener(new ActionListener() {
     		public void actionPerformed(ActionEvent e) {
     			LineaPanel.this.setVisible(false);
     		}
     	});
     			
     	btnClose.setBounds(-10, 20, 185, 25);
     	btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
     			
     	btnClose.setFont(new Font("Arial Nova", Font.BOLD, 14));
     	btnClose.setForeground(new Color(255, 255, 255));
     			
     	btnClose.setBorderPainted(false);
     	btnClose.setFocusPainted(false);
     	btnClose.setContentAreaFilled(false);
     		
     	ImageIcon iconC = new ImageIcon("src/resources/close.png");
     	Image scaledImageC = iconC.getImage().getScaledInstance(23, 30, Image.SCALE_SMOOTH);
        ImageIcon newIconC = new ImageIcon(scaledImageC);
        btnClose.setIcon(newIconC);
             
    	this.add(btnClose);
        
        this.setVisible(false);
        
	}
	
	public void creaPannelloLinea(Route linea) {
		this.setVisible(true);
		System.out.println("Prova");
		
		if (utente.getLineePreferite() != null) {
		    boolean isPreferita = false;
		    for (String preferito : utente.getLineePreferite()) {
		        if (preferito.equals(linea.getId().getId())) {
		            isPreferita = true;
		            break;
		        }
		    }

		    String iconPath = isPreferita ? "src/resources/cuore.png" : "src/resources/cuore-vuoto.png";
		    ImageIcon icon = new ImageIcon(iconPath);
		    Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		    btnPreferiti.setIcon(new ImageIcon(scaledImage));

		}
		
		for (ActionListener al : btnPreferiti.getActionListeners()) {
		    btnPreferiti.removeActionListener(al);
		}
		
		for (ActionListener al : btnLink.getActionListeners()) {
		    btnLink.removeActionListener(al);
		}
		
		btnPreferiti.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        List<String> lista = utente.getLineePreferite();
		        String idLinea = linea.getId().getId();
		        
		        boolean isOraPreferita;
		        
		        if (lista.contains(idLinea)) {
		            lista.remove(idLinea);
		            isOraPreferita = false;
		        } else {
		            lista.add(idLinea);
		            isOraPreferita = true;
		        }
		        
		        try {
					utente.cambiaLineePreferite(lista);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        
		        String iconPath = isOraPreferita ? "src/resources/cuore.png" : "src/resources/cuore-vuoto.png";
		        ImageIcon icon = new ImageIcon(iconPath);
		        Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		        btnPreferiti.setIcon(new ImageIcon(scaledImage));
		    }
		});

		
		
		String agenzia = linea.getAgency().getName();
		String longName = linea.getLongName();
		String shortName = linea.getShortName();
		scrittaAgenzia.setText(agenzia);
		System.out.println(longName);
		if(longName == null || longName.isEmpty())
		{
			titolo.setText(shortName);
		}
		else
		{
			titolo.setText(longName);
			scrittaAgenzia.setText(shortName + "  -  " + agenzia);
		}
		
		btnLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
						desktop.browse(new URI(linea.getUrl()));
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
