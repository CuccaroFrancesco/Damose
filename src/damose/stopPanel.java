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
import java.awt.event.ActionEvent;

public class stopPanel extends JPanel {
	
	private JLabel titolo, codiceFermata, lblArrivi;
	private JButton btnWheelChair, btnClose;
	private Utente utente;
	private DatiGTFS dati;
	private Navbar navbar;
	
	public stopPanel(Utente utente, DatiGTFS dati, Navbar navbar) {
		this.utente = utente;
		this.dati = dati;
		this.navbar = navbar;
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		
		titolo = new JLabel("Nome fermata");
		
		titolo.setForeground(new Color(255, 255, 255));
		titolo.setFont(new Font("Arial Nova", Font.BOLD, 24));
		titolo.setFocusable(false);
								
		titolo.setBounds(20, 107, 380, 50);
								
		this.add(titolo);
		
		codiceFermata = new JLabel("Codice fermata");
		
		codiceFermata.setForeground(Color.WHITE);
		codiceFermata.setFont(new Font("Arial Nova", Font.PLAIN, 14));
		codiceFermata.setFocusable(false);
		codiceFermata.setBounds(20, 135, 145, 50);
        
        this.add(codiceFermata);
		
		lblArrivi = new JLabel("Prossimi arrivi");
		lblArrivi.setHorizontalAlignment(SwingConstants.CENTER);
		lblArrivi.setForeground(Color.WHITE);
		lblArrivi.setFont(new Font("Arial Nova", Font.BOLD, 24));
		lblArrivi.setFocusable(false);
		lblArrivi.setBounds(0, 250, 400, 50);
		add(lblArrivi);
		
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
		
		ImageIcon iconW = new ImageIcon("src/resources/sedia.png");
        Image scaledImageW = iconW.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon newIconW = new ImageIcon(scaledImageW);
        btnWheelChair.setIcon(newIconW);
        
        this.add(btnWheelChair);
        
     	btnClose = new JButton("Chiudi pannello");
     	btnClose.addActionListener(new ActionListener() {
     		public void actionPerformed(ActionEvent e) {
     			stopPanel.this.setVisible(false);
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
	
	public void creaPannelloFermata(Stop fermata) {
		this.setVisible(true);
		
		titolo.setText(fermata.getName());
		codiceFermata.setText(fermata.getCode());
		int accessibile = fermata.getWheelchairBoarding(); 
		switch(accessibile)
		{
			case 1:
				btnWheelChair.setText("Accessibile");
				break;
			case 0:
				btnWheelChair.setText("Non accessibile");
				break;
			default:
				btnWheelChair.setText("Non accessibile");
				break;
		}
	}
	
	
}
