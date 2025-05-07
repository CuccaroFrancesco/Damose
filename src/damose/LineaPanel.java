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
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class LineaPanel extends JPanel {
	
	private JLabel codiceLinea, agenziaENomeLinea, lblFermate, lblMezzi;
	private JButton btnClose, btnAgency, btnFavorite, btnWebsite, btnRouteType;
	private JPanel fermatePanel;
	private JScrollPane fermateScrollPane;
	private ImageIcon iconFermata, newIconFermata;
	private Image scaledImageFermata;
	private Utente utente;
	private DatiGTFS dati;
	
	// Costruttore del lineaPanel
	public LineaPanel(Utente utente, DatiGTFS dati) {
		
		this.utente = utente;
		this.dati = dati;
		
		this.setBackground(new Color(130, 36, 51));
		this.setLayout(null);
		this.setVisible(false);
		
		
		// JLabel che contiene il nome (long name) della linea in questione
		codiceLinea = new JLabel("Codice linea");
		
		codiceLinea.setForeground(Color.WHITE);
		codiceLinea.setFont(new Font("Arial Nova", Font.BOLD, 30));
		codiceLinea.setFocusable(false);
								
		codiceLinea.setBounds(80, 70, 180, 50);
								
		this.add(codiceLinea);
        
		
		// JLabel che contiene il nome dell'agenzia che gestisce la linea in questione
        agenziaENomeLinea = new JLabel("Agenzia  -  Nome linea");
        
        agenziaENomeLinea.setForeground(new Color(210, 210, 210));
        agenziaENomeLinea.setFont(new Font("Arial Nova", Font.ITALIC, 12));
        agenziaENomeLinea.setFocusable(false);
        
        agenziaENomeLinea.setBounds(20, 125, 300, 20);
        
        this.add(agenziaENomeLinea);
        
        
        // JLabel per il testo "Fermate:"
        lblFermate = new JLabel("Fermate:");
        
        lblFermate.setForeground(Color.WHITE);
        lblFermate.setFont(new Font("Arial Nova", Font.BOLD, 24));
        lblFermate.setFocusable(false);
        
        lblFermate.setBounds(20, 200, 150, 50);
        
        this.add(lblFermate);
        
        
        // JLabel per il testo "Mezzi:"
        lblMezzi = new JLabel("Mezzi:");
        
        lblMezzi.setForeground(Color.WHITE);
        lblMezzi.setFont(new Font("Arial Nova", Font.BOLD, 24));
        lblMezzi.setFocusable(false);
        
        lblMezzi.setBounds(20, 460, 150, 50);
        
        this.add(lblMezzi);
        
        
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
        
        
        // Pulsante per il logo dell'agenzia di trasporti che gestisce la linea (non interattivo, serve solo a visualizzare comodamente il logo)
        btnAgency = new JButton();
		
        btnAgency.setContentAreaFilled(false);
		btnAgency.setFocusPainted(false);
		btnAgency.setBorderPainted(false);
		btnAgency.setBackground(new Color(255, 255, 255));
		
		btnAgency.setFocusable(false);
		
		btnAgency.setPreferredSize(new Dimension(50, 50));
		btnAgency.setBounds(20, 70, 50, 50);
		
		this.add(btnAgency);
        
        
        // Pulsante per l'aggiunta o la rimozione della linea ai preferiti dell'utente
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
		btnWebsite.setFont(new Font("Arial Nova", Font.BOLD, 12));
		btnWebsite.setText(" Sito Web");
		btnWebsite.setHorizontalAlignment(SwingConstants.LEADING);
		
		btnWebsite.setContentAreaFilled(false);
		btnWebsite.setFocusPainted(false);
		btnWebsite.setBorderPainted(false);
		btnWebsite.setBackground(new Color(130, 36, 51));
		
		btnWebsite.setPreferredSize(new Dimension(20, 20));
		btnWebsite.setBounds(0, 155, 120, 20);
		
		ImageIcon iconWebsite = new ImageIcon("src/resources/mondo.png");
        Image scaledImageWebsite = iconWebsite.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon newIconWebsite = new ImageIcon(scaledImageWebsite);
        btnWebsite.setIcon(newIconWebsite);
        
		this.add(btnWebsite);
		
		
		// Pulsante per il tipo di linea (tram, metro, treni, bus) (non interattivo, serve solo a visualizzare comodamente icona e informazione)
		btnRouteType = new JButton();
		
		btnRouteType.setForeground(Color.WHITE);
		btnRouteType.setFont(new Font("Arial Nova", Font.BOLD, 12));
		btnRouteType.setHorizontalAlignment(SwingConstants.LEADING);
		btnRouteType.setText("Tipo di trasporto");
		
		btnRouteType.setContentAreaFilled(false);
		btnRouteType.setFocusPainted(false);
		btnRouteType.setBorderPainted(false);
		btnRouteType.setBackground(new Color(130, 36, 51));
		
		btnRouteType.setFocusable(false);
		
		btnRouteType.setPreferredSize(new Dimension(20, 20));
		btnRouteType.setBounds(175, 155, 150, 20);
		
		this.add(btnRouteType);
		
		iconFermata = new ImageIcon("src/resources/linea.png");
        scaledImageFermata = iconFermata.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        newIconFermata = new ImageIcon(scaledImageFermata);
	}
	
	
	// Metodo che "costruisce" concretamente il lineaPanel in base alla linea in questione
	public void creaPannelloLinea(Route linea, DatiGTFS dati) {
		this.setVisible(true);
		
		if (fermateScrollPane != null) {
		    this.remove(fermateScrollPane);
		}
		
		if (utente.getLineePreferite() != null) {
			btnFavorite.setEnabled(true);
			btnFavorite.setVisible(true);
			
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
		    btnFavorite.setIcon(new ImageIcon(scaledImageCuore));
		}
		
		for (ActionListener a : btnFavorite.getActionListeners()) {
			btnFavorite.removeActionListener(a);
		}
		
		for (ActionListener a : btnWebsite.getActionListeners()) {
		    btnWebsite.removeActionListener(a);
		}
		
		btnFavorite.addActionListener(new ActionListener() {
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
			    btnFavorite.setIcon(new ImageIcon(scaledImageCuore));
		    }
		});
		
		String agencyName = linea.getAgency().getName();
		String longName = linea.getLongName();
		String shortName = linea.getShortName();
		String url = linea.getUrl();
		int routeType = linea.getType();
		
		switch (agencyName) {
			case "Atac":
				ImageIcon iconAtac = new ImageIcon("src/resources/atac-logo.png");
		        Image scaledImageAtac = iconAtac.getImage().getScaledInstance(50, 41, Image.SCALE_SMOOTH);
		        ImageIcon newIconAtac = new ImageIcon(scaledImageAtac);
		        btnAgency.setIcon(newIconAtac);
		        
		        break;
		    
			case "Autoservizi Troiani":
				ImageIcon iconTroiani = new ImageIcon("src/resources/autoservizi-troiani-logo.png");
		        Image scaledImageTroiani = iconTroiani.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		        ImageIcon newIconTroiani = new ImageIcon(scaledImageTroiani);
		        btnAgency.setIcon(newIconTroiani);
		        
		        break;
		    
			default:
				break;
		}
		
		codiceLinea.setText(" " + shortName);
		
		if (longName == null || longName.isEmpty()) {
			agenziaENomeLinea.setText(agencyName);
		} else {
			agenziaENomeLinea.setText(agencyName + "  -  " + longName);
		}
		
		List<Stop> fermate = dati.getFermatePerLinea(linea);
		
		fermatePanel = new JPanel();
		fermatePanel.setLayout(null);
		fermatePanel.setPreferredSize(new Dimension(350, Math.max(100, fermate.size() * 40)));
		fermatePanel.setBackground(new Color(130, 36, 51));
		
		
		for(int i = 0; i < fermate.size(); i++) {
			Stop fermata = fermate.get(i);
			int y = i * 40;
			
			JButton stopBtn = new JButton();
            
            stopBtn.setBounds(10, y, 290, 40);
            stopBtn.setFocusable(false);
            stopBtn.setFocusPainted(false);
            stopBtn.setFont(new Font("Arial Nova", Font.BOLD, 12));
            stopBtn.setText("   " + fermata.getName());
            stopBtn.setForeground(new Color(255, 255, 255));
            stopBtn.setBackground(new Color(130, 36, 51));
            stopBtn.setBorder(BorderFactory.createEmptyBorder());
            stopBtn.setHorizontalAlignment(SwingConstants.LEADING);
	        stopBtn.setIcon(newIconFermata);
            
	        fermatePanel.add(stopBtn);
            
		}
		
		fermateScrollPane = new JScrollPane(fermatePanel);
        
        fermateScrollPane.setBorder(null);
        fermateScrollPane.setBounds(0, 250, 350, 200);
        fermateScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        fermateScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        fermateScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        this.add(fermateScrollPane);
        fermatePanel.repaint();
		
		btnWebsite.addActionListener(new ActionListener() {
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
		
		switch (routeType) {
			case 0:
				ImageIcon iconTram = new ImageIcon("src/resources/tram.png");
		        Image scaledImageTram = iconTram.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		        ImageIcon newIconTram = new ImageIcon(scaledImageTram);
		        btnRouteType.setIcon(newIconTram);
		        
		        btnRouteType.setText(" Tram");
		        break;
			
			case 1:
				ImageIcon iconMetro = new ImageIcon("src/resources/metro.png");
		        Image scaledImageMetro = iconMetro.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		        ImageIcon newIconMetro = new ImageIcon(scaledImageMetro);
		        btnRouteType.setIcon(newIconMetro);
		        
		        btnRouteType.setText(" Metropolitana");
		        
		        switch (shortName) {
		        	case "MEA":
		        		ImageIcon iconMetroA = new ImageIcon("src/resources/metro-a-logo-withborder.png");
				        Image scaledImageMetroA = iconMetroA.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				        ImageIcon newIconMetroA = new ImageIcon(scaledImageMetroA);
				        btnAgency.setIcon(newIconMetroA);
				        
				        codiceLinea.setText(" Metro A");
				        
				        break;
				    
		        	case "MEB", "MEB1":
		        		ImageIcon iconMetroB = new ImageIcon("src/resources/metro-b-logo-withborder.png");
				        Image scaledImageMetroB = iconMetroB.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				        ImageIcon newIconMetroB = new ImageIcon(scaledImageMetroB);
				        btnAgency.setIcon(newIconMetroB);
				        
				        codiceLinea.setText(" Metro B");
				        
				        break;
				    
		        	case "MEC":
		        		ImageIcon iconMetroC = new ImageIcon("src/resources/metro-c-logo-withborder.png");
				        Image scaledImageMetroC = iconMetroC.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				        ImageIcon newIconMetroC = new ImageIcon(scaledImageMetroC);
				        btnAgency.setIcon(newIconMetroC);
				        
				        codiceLinea.setText(" Metro C");
				        
				        break;
		        }
		        
		        break;
		        
			case 2:
				ImageIcon iconTreno = new ImageIcon("src/resources/train.png");
		        Image scaledImageTreno = iconTreno.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		        ImageIcon newIconTreno = new ImageIcon(scaledImageTreno);
		        btnRouteType.setIcon(newIconTreno);
		        
		        btnRouteType.setText(" Treno");
		        break;
		        
			case 3:
				ImageIcon iconBus = new ImageIcon("src/resources/bus.png");
		        Image scaledImageBus = iconBus.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		        ImageIcon newIconBus = new ImageIcon(scaledImageBus);
		        btnRouteType.setIcon(newIconBus);
		        
		        btnRouteType.setText(" Autobus");
		        break;
		}
	}
}