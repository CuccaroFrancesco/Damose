package damose;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

import org.onebusaway.gtfs.model.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;



public class StopPanel extends JPanel {
	
	private Frame frame;
	
	private JLabel nomeFermata, codiceFermata, lblArrivi, lblLineePassanti;
	private JButton btnClose, btnStopIcon, btnFavorite, btnCoordinates;
	private JPanel lineePassantiPanel;
	private JScrollPane lineePassantiScrollPane;
	
	
	// Costruttore del pannello stopPanel
	public StopPanel(Frame frame) {
		
		this.frame = frame;
		
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		this.setVisible(false);
		
		
		// JLabel che contiene il nome della fermata
        nomeFermata = new JLabel("Nome fermata");
		
		nomeFermata.setForeground(new Color(255, 255, 255));
		nomeFermata.setFont(new Font("Arial Nova", Font.BOLD, 22));
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


// ---------------------------------------------------------------------------------------------


	// Metodo che gestisce la creazione dello stopPanel
	public void creaPannelloFermata(Stop fermata) {
		
		this.setVisible(true);
		frame.getMappa().getLineaPainter().setLineaDaDisegnare(new ArrayList<>(), null);
		frame.getRoutePanel().setVisible(false);
		
		if (lineePassantiScrollPane != null) {
		    this.remove(lineePassantiScrollPane);
		}
		
		String iconCuorePath = "src/resources/cuore-vuoto.png";
		ImageIcon iconCuore = new ImageIcon(iconCuorePath);
		Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		btnFavorite.setIcon(new ImageIcon(scaledImageCuore));
		
		this.controllaUtente(this.frame.getUtente().getIsLogged());
		
		for (ActionListener a : btnFavorite.getActionListeners()) {
		    btnFavorite.removeActionListener(a);
		}
		
		btnFavorite.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        List<String> fermatePreferite = frame.getUtente().getFermatePreferite();
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
		        	frame.getUtente().cambiaPreferiti(frame.getUtente().getLineePreferite(), fermatePreferite);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		        
		        String iconCuorePath = isOraPreferita ? "src/resources/cuore.png" : "src/resources/cuore-vuoto.png";
		        ImageIcon iconCuore = new ImageIcon(iconCuorePath);
		        Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		        btnFavorite.setIcon(new ImageIcon(scaledImageCuore));
		    }
		});
		
		nomeFermata.setText(fermata.getName());
		codiceFermata.setText("ID: " + fermata.getId().getId());
		btnCoordinates.setText(" " + Math.floor(fermata.getLat() * 100000) / 100000 + ", " + Math.floor(fermata.getLon() * 100000) / 100000);
		
		List<Route> lineePassanti = frame.getDati().getLineePassantiPerFermata(fermata);
		
		lineePassantiPanel = new JPanel();
		lineePassantiPanel.setLayout(null);
		lineePassantiPanel.setBackground(new Color(130, 36, 51));
		lineePassantiPanel.setPreferredSize(new Dimension(350, Math.max(100, lineePassanti.size() * 50)));
		
		for (int i = 0; i < lineePassanti.size(); i++) {
			
			Route linea = lineePassanti.get(i);
			int y = i * 50;
			
			JButton btnInfoLinea = new JButton();
			
			btnInfoLinea.setBounds(10, y, 200, 50);
			
			btnInfoLinea.setFocusable(false);
            btnInfoLinea.setContentAreaFilled(false);
            btnInfoLinea.setFocusPainted(false);
            btnInfoLinea.setBorderPainted(false);
            
            btnInfoLinea.setFont(new Font("Arial Nova", Font.BOLD, 16));
            btnInfoLinea.setHorizontalAlignment(SwingConstants.LEADING);
			btnInfoLinea.setText("<html><font size='5'>&nbsp;&nbsp;&nbsp;<b>" + linea.getId().getId() + "</b></font><br><font size='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>" + linea.getAgency().getName().toUpperCase() +  "</b></font></html>");

			btnInfoLinea.setForeground(new Color(255, 255, 255));
            btnInfoLinea.setBackground(new Color(130, 36, 51));
            btnInfoLinea.setBorder(BorderFactory.createEmptyBorder());
            
            switch (linea.getType()) {
				case 0:
					ImageIcon iconTram = new ImageIcon("src/resources/tram.png");
			        Image scaledImageTram = iconTram.getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH);
			        ImageIcon newIconTram = new ImageIcon(scaledImageTram);
			        btnInfoLinea.setIcon(newIconTram);
			        
			        break;
				
				case 1:
					switch (linea.getShortName()) {
			        	case "MEA":
			        		ImageIcon iconMetroA = new ImageIcon("src/resources/metro-a-logo-withborder.png");
					        Image scaledImageMetroA = iconMetroA.getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH);
					        ImageIcon newIconMetroA = new ImageIcon(scaledImageMetroA);
					        btnInfoLinea.setIcon(newIconMetroA);

							btnInfoLinea.setText("<html><font size='5'>&nbsp;&nbsp;&nbsp;<b>" + "Metro A" + "</b></font><br><font size='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>" + linea.getAgency().getName().toUpperCase() +  "</b></font></html>");
					        
					        break;
					    
			        	case "MEB":
			        		ImageIcon iconMetroB = new ImageIcon("src/resources/metro-b-logo-withborder.png");
					        Image scaledImageMetroB = iconMetroB.getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH);
					        ImageIcon newIconMetroB = new ImageIcon(scaledImageMetroB);
					        btnInfoLinea.setIcon(newIconMetroB);

							btnInfoLinea.setText("<html><font size='5'>&nbsp;&nbsp;&nbsp;<b>" + "Metro B" + "</b></font><br><font size='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>" + linea.getAgency().getName().toUpperCase() +  "</b></font></html>");
					        
					        break;
					        
			        	case "MEB1":
			        		ImageIcon iconMetroB1 = new ImageIcon("src/resources/metro-b-logo-withborder.png");
					        Image scaledImageMetroB1 = iconMetroB1.getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH);
					        ImageIcon newIconMetroB1 = new ImageIcon(scaledImageMetroB1);
					        btnInfoLinea.setIcon(newIconMetroB1);

							btnInfoLinea.setText("<html><font size='5'>&nbsp;&nbsp;&nbsp;<b>" + "Metro B1" + "</b></font><br><font size='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>" + linea.getAgency().getName().toUpperCase() +  "</b></font></html>");
					        break;
					        
			        	case "MEC":
			        		ImageIcon iconMetroC = new ImageIcon("src/resources/metro-c-logo-withborder.png");
					        Image scaledImageMetroC = iconMetroC.getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH);
					        ImageIcon newIconMetroC = new ImageIcon(scaledImageMetroC);
					        btnInfoLinea.setIcon(newIconMetroC);

							btnInfoLinea.setText("<html><font size='5'>&nbsp;&nbsp;&nbsp;<b>" + "Metro C" + "</b></font><br><font size='3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>" + linea.getAgency().getName().toUpperCase() +  "</b></font></html>");
					        
					        break;
			        }
			        
			        break;
			        
				case 2:
					ImageIcon iconTreno = new ImageIcon("src/resources/train.png");
			        Image scaledImageTreno = iconTreno.getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH);
			        ImageIcon newIconTreno = new ImageIcon(scaledImageTreno);
			        btnInfoLinea.setIcon(newIconTreno);
			        
			        break;
			        
				case 3:
					ImageIcon iconBus = new ImageIcon("src/resources/bus.png");
			        Image scaledImageBus = iconBus.getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH);
			        ImageIcon newIconBus = new ImageIcon(scaledImageBus);
			        btnInfoLinea.setIcon(newIconBus);
			        
			        break;
            }
            
			JButton btnLinea = new JButton();

			btnLinea.setBorderPainted(false);
			btnLinea.setFocusPainted(false);
			btnLinea.setContentAreaFilled(false);
			btnLinea.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			btnLinea.setBounds(280, y + 10, 30, 30);

			ImageIcon iconBtnLinea = new ImageIcon("src/resources/lineabtn-icon.png");
			Image scaledImageBtnLinea = iconBtnLinea.getImage().getScaledInstance(16, 20, Image.SCALE_SMOOTH);
			ImageIcon newIconBtnLinea = new ImageIcon(scaledImageBtnLinea);
			btnLinea.setIcon(newIconBtnLinea);

			btnLinea.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.getRoutePanel().creaPannelloLinea(linea);
				}
			});

            lineePassantiPanel.add(btnInfoLinea);
			lineePassantiPanel.add(btnLinea);
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


// ---------------------------------------------------------------------------------------------


	// Metodo che gestisce il comportamento del pulsante dei preferiti in base allo stato (logged o non logged) dell'utente
	public void controllaUtente(boolean isLogged) {
		
		if (isLogged) {
			btnFavorite.setEnabled(true);
			btnFavorite.setVisible(true);
			
			boolean isPreferita = false;
		    
		    for (String fermataPreferita : frame.getUtente().getFermatePreferite()) {
		        if (fermataPreferita.equals(codiceFermata.getText().substring(4).trim())) {
		            isPreferita = true;
		            break;
		        }
		    }

		    String iconCuorePath = isPreferita ? "src/resources/cuore.png" : "src/resources/cuore-vuoto.png";
		    ImageIcon iconCuore = new ImageIcon(iconCuorePath);
		    Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		    btnFavorite.setIcon(new ImageIcon(scaledImageCuore));
		    
		    btnFavorite.repaint();
		}
	}
}