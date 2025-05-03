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

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.onebusaway.gtfs.model.Route;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class lineaPanel extends JPanel {
	
	private JLabel titolo, scrittaAgenzia;
	private JButton btnWheelChair, btnClose, btnLink;
	private Utente utente;
	private DatiGTFS dati;
	private Navbar navbar;
	
	public lineaPanel(Utente utente, DatiGTFS dati, Navbar navbar) {
		this.utente = utente;
		this.dati = dati;
		this.navbar = navbar;
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		
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
								
		titolo.setBounds(20, 107, 380, 50);
								
		this.add(titolo);
		
		JLabel lblAccessibilit = new JLabel("Accessibilità");
		lblAccessibilit.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccessibilit.setForeground(Color.WHITE);
		lblAccessibilit.setFont(new Font("Arial Nova", Font.BOLD, 24));
		lblAccessibilit.setFocusable(false);
		lblAccessibilit.setBounds(0, 344, 400, 50);
		add(lblAccessibilit);
		
		JButton btnWheelChair = new JButton();
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
		btnWheelChair.setBounds(10, 400, 205, 50);
		
		ImageIcon iconW = new ImageIcon("src/resources/sedia.png");
        Image scaledImageW = iconW.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon newIconW = new ImageIcon(scaledImageW);
        btnWheelChair.setIcon(newIconW);
        
        this.add(btnWheelChair);
        
        scrittaAgenzia = new JLabel("Nome agenzia");
        scrittaAgenzia.setForeground(Color.WHITE);
        scrittaAgenzia.setFont(new Font("Arial Nova", Font.PLAIN, 14));
        scrittaAgenzia.setFocusable(false);
        scrittaAgenzia.setBounds(20, 135, 145, 50);
        this.add(scrittaAgenzia);
        
        btnClose = new JButton("Chiudi pannello");
     	btnClose.addActionListener(new ActionListener() {
     		public void actionPerformed(ActionEvent e) {
     			lineaPanel.this.setVisible(false);
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
		
		String agenzia = linea.getAgency().getName();
		titolo.setText(linea.getShortName());
		scrittaAgenzia.setText(agenzia);
		
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
