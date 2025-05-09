package damose;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ricerca extends JPanel{
	
	private DatiGTFS dati;
	private StopPanel stopPanel;
    private LineaPanel lineaPanel;
    private Mappa mappa;
    private JPanel panelLinee;
    private JScrollPane lineeScrollPane;
	
	public Ricerca(DatiGTFS dati, StopPanel stopPanel, LineaPanel lineaPanel, Mappa mappa) {
		this.dati = dati;
		this.stopPanel = stopPanel;
		this.lineaPanel = lineaPanel;
		this.mappa = mappa;
		
		this.setLayout(null);
		this.setVisible(false);
		
	}
	
	
	public void mostraRisultatiRicerca(String input, Navbar navbar) {
		this.revalidate(); // <- forza il layout manager ad aggiornarsi
		this.repaint();    // <- forza il ridisegno
		if(panelLinee != null) panelLinee.removeAll();
		if (lineeScrollPane != null) this.remove(lineeScrollPane); 
		
		this.setVisible(true);
		List<Route> linee = dati.cercaLinee(input);
		List<Stop> fermate = dati.cercaFermate(input);
		
		// Creazioe del pannello per le linee
        panelLinee = new JPanel();
        panelLinee.setLayout(null);
        panelLinee.setPreferredSize(new Dimension(navbar.getSearchBar().getWidth(), Math.max(60, (linee.size() + fermate.size()) * 60)));
        panelLinee.setBounds(0, 0, navbar.getSearchBar().getWidth(), Math.max(100, linee.size() * 60));
        panelLinee.setBackground(new Color(200, 200, 200));
        
        
        
        for(int i=0; i < linee.size(); i++) {
        	Route linea = linee.get(i);
        	int y = i * 60;
        	
        	JButton btnLinea = new JButton();
        	btnLinea.setBounds(0, y, navbar.getSearchBar().getWidth() + 400, 60);
        	btnLinea.setHorizontalAlignment(SwingConstants.LEADING);
        	btnLinea.setIconTextGap(15);
        	btnLinea.setBorder(BorderFactory.createCompoundBorder(
        		    new MatteBorder(0, 0, 1, 0, Color.BLACK),
        		    new EmptyBorder(5, 20, 5, 0) // margine interno
        		));
        	if(linea.getLongName() == null || linea.getLongName().isBlank()) {        		
        		btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b>"+generaSpaziHTML(linea.getId().getId().length())+"</font><font size='3'><b>" + linea.getAgency().getName().toUpperCase() +  "</b></font></html>");
        	} else {
        		btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b>"+generaSpaziHTML(linea.getId().getId().length())+"</font><font size='3'><b>" + linea.getLongName() +  "&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</b>" + linea.getAgency().getName().toUpperCase() + "</font></html>");
        	}
        	btnLinea.setFont(new Font("Arial Nova", Font.PLAIN, 15));
        	btnLinea.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	btnLinea.setBackground(new Color(200, 200, 200));
    		
        	btnLinea.setFocusable(false);
        	
        	for (ActionListener al : btnLinea.getActionListeners()) {
        		btnLinea.removeActionListener(al);
	        }
	        
	        // Listener
        	btnLinea.addActionListener(e -> {
        		lineaPanel.creaPannelloLinea(linea, dati);
        		stopPanel.setVisible(false);
        		LineaPainter.costruisciLineaDaDisegnare(linea, mappa, dati);
	        	Ricerca.this.setVisible(false);
	        });
        	
        	panelLinee.add(btnLinea);
        	
        	switch (linea.getType()) {
			case 0:
				ImageIcon iconTram = new ImageIcon("src/resources/tramR.png");
		        Image scaledImageTram = iconTram.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		        ImageIcon newIconTram = new ImageIcon(scaledImageTram);
		        btnLinea.setIcon(newIconTram);
		        break;
			
			case 1:
				ImageIcon iconMetro = new ImageIcon("src/resources/metroR.png");
		        Image scaledImageMetro = iconMetro.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		        ImageIcon newIconMetro = new ImageIcon(scaledImageMetro);
		        btnLinea.setIcon(newIconMetro);
		        
		        switch (linea.getShortName()) {
		        	case "MEA":
		        		ImageIcon iconMetroA = new ImageIcon("src/resources/metro-a-logo-withborder.png");
				        Image scaledImageMetroA = iconMetroA.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				        ImageIcon newIconMetroA = new ImageIcon(scaledImageMetroA);
				        
				        btnLinea.setIcon(newIconMetroA);
				        btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b>"+generaSpaziHTML(4)+"</font><font size='3'><b>METRO A&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</b>" + linea.getAgency().getName().toUpperCase() + "</font></html>");
				        
				        break;
				    
		        	case "MEB":
		        		ImageIcon iconMetroB = new ImageIcon("src/resources/metro-b-logo-withborder.png");
				        Image scaledImageMetroB = iconMetroB.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				        ImageIcon newIconMetroB = new ImageIcon(scaledImageMetroB);
				        
				        btnLinea.setIcon(newIconMetroB);
				        btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b>"+generaSpaziHTML(4)+"</font><font size='3'><b>METRO B&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</b>" + linea.getAgency().getName().toUpperCase() + "</font></html>");
				        
				        break;
				        
		        	case "MEB1":
		        		ImageIcon iconMetroB1 = new ImageIcon("src/resources/metro-b-logo-withborder.png");
				        Image scaledImageMetroB1 = iconMetroB1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				        ImageIcon newIconMetroB1 = new ImageIcon(scaledImageMetroB1);
				        
				        btnLinea.setIcon(newIconMetroB1);
				        btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b>"+generaSpaziHTML(4)+"</font><font size='3'><b>METRO B1&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</b>" + linea.getAgency().getName().toUpperCase() + "</font></html>");
				        break;
				        
		        	case "MEC":
		        		ImageIcon iconMetroC = new ImageIcon("src/resources/metro-c-logo-withborder.png");
				        Image scaledImageMetroC = iconMetroC.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				        ImageIcon newIconMetroC = new ImageIcon(scaledImageMetroC);
				        
				        btnLinea.setIcon(newIconMetroC);
				        btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b>"+generaSpaziHTML(4)+"</font><font size='3'><b>METRO C&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</b>" + linea.getAgency().getName().toUpperCase() + "</font></html>");
				        
				        break;
		        }
		        
		        break;
		        
			case 2:
				ImageIcon iconTreno = new ImageIcon("src/resources/trainR.png");
		        Image scaledImageTreno = iconTreno.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		        ImageIcon newIconTreno = new ImageIcon(scaledImageTreno);
		        btnLinea.setIcon(newIconTreno);
		        break;
		        
			case 3:
				ImageIcon iconBus = new ImageIcon("src/resources/busR.png");
		        Image scaledImageBus = iconBus.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		        ImageIcon newIconBus = new ImageIcon(scaledImageBus);
		        btnLinea.setIcon(newIconBus);
		        break;
		}
        	
        }
        
        
        
        for(int i=0; i< fermate.size(); i++) {
        	int y = (linee.size() + i) * 60;
        	Stop fermata = fermate.get(i);
        	JButton btnFermata = new JButton();
        	btnFermata.setBounds(0, y, navbar.getSearchBar().getWidth()  + 400, 60);
        	btnFermata.setHorizontalAlignment(SwingConstants.LEADING);
        	btnFermata.setIconTextGap(15);
        	btnFermata.setBorder(BorderFactory.createCompoundBorder(
        		    new MatteBorder(0, 0, 1, 0, Color.BLACK),
        		    new EmptyBorder(5, 20, 5, 0) // margine interno
        		));     		
        	btnFermata.setText("<html><font size='5'><b>" + fermata.getId().getId() + "</b></font><font size='5'>"+generaSpaziHTML(fermata.getId().getId().length())+"</font><font size='3'><b>" + fermata.getName() +  "</b></font></html>");
        	btnFermata.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	btnFermata.setBackground(new Color(200, 200, 200));
    		
        	btnFermata.setFocusable(false);
        	
        	ImageIcon iconFermata = new ImageIcon("src/resources/fermata.png");
	        Image scaledImageFermata = iconFermata.getImage().getScaledInstance(20, 25, Image.SCALE_SMOOTH);
	        ImageIcon newIconFermata = new ImageIcon(scaledImageFermata);
	        btnFermata.setIcon(newIconFermata);
	        
	        for (ActionListener al : btnFermata.getActionListeners()) {
	            btnFermata.removeActionListener(al);
	        }
	        
	        // Listener
	        btnFermata.addActionListener(e -> {
	            stopPanel.creaPannelloFermata(fermata);
	        	mappa.centraMappa(fermata.getLon(), fermata.getLat(), 2);
	        	lineaPanel.setVisible(false);
	        	Ricerca.this.setVisible(false);
	        });
	        
        	panelLinee.add(btnFermata);
        }
        
        panelLinee.revalidate();
        panelLinee.repaint();
        
        lineeScrollPane = new JScrollPane(panelLinee);
        lineeScrollPane.setBounds(0, 0, navbar.getSearchBar().getWidth(), Math.min(300, (linee.size() + fermate.size()) * 60)) ;
        lineeScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        lineeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        lineeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        this.add(lineeScrollPane);
        
        this.revalidate();
        this.repaint();
	
	
	}
	
	public JScrollPane getLineeScrollPane() {
		return this.lineeScrollPane;
	}
	
	private String generaSpaziHTML(int lunghezzaId) {
	    int spaziTotali = 8;
	    int spaziDaAggiungere = Math.max(0, spaziTotali - lunghezzaId);
	    return "&nbsp;".repeat(spaziDaAggiungere * 2); // *2 per visibilità
	}
}
