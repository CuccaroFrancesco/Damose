package damose;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;

import java.awt.event.ActionListener;



public class Ricerca extends JPanel{
	
	private final Frame frame;
	
	private JPanel panelRisultati;
    private JScrollPane risultatiScrollPane;
	
    
    // Costruttore dell'oggetto Ricerca
	public Ricerca(Frame frame) {
		
		this.frame = frame;
		
		this.setLayout(null);
		this.setVisible(false);
	}


// ---------------------------------------------------------------------------------------------


	// Metodo get per il risultatiScrollPane
	public JScrollPane getRisultatiScrollPane() {
		return this.risultatiScrollPane;
	}


// ---------------------------------------------------------------------------------------------
	
	
	// Metodo che costruisce il pannello risultatiScrollPane, dove vengono visualizzati i risultati di una ricerca
	public void mostraRisultatiRicerca(String input, Navbar navbar) {
		
		this.revalidate();
		this.repaint();
		
		if (panelRisultati != null) panelRisultati.removeAll();
		if (risultatiScrollPane != null) this.remove(risultatiScrollPane); 
		
		this.setVisible(true);
		
		List<Route> lineeTrovate = frame.getDati().cercaLinee(input);
		List<Stop> fermateTrovate = frame.getDati().cercaFermate(input);
		
		panelRisultati = new JPanel();
        
		panelRisultati.setLayout(null);
		panelRisultati.setBackground(new Color(212, 212, 212));
		panelRisultati.setPreferredSize(new Dimension(navbar.getSearchBar().getWidth(), Math.max(60, (lineeTrovate.size() + fermateTrovate.size()) * 60)));
		panelRisultati.setBounds(0, 0, navbar.getSearchBar().getWidth(), Math.max(100, lineeTrovate.size() * 60));
        
        for (int i = 0; i < lineeTrovate.size(); i++) {
        	
        	Route linea = lineeTrovate.get(i);
        	int y = i * 60;
        	
        	JButton btnLinea = new JButton();
        	
        	btnLinea.setHorizontalAlignment(SwingConstants.LEADING);
        	btnLinea.setIconTextGap(15);
        	btnLinea.setBounds(0, y, navbar.getSearchBar().getWidth() + 400, 60);
        	
        	btnLinea.setBorder(BorderFactory.createCompoundBorder(
        		    new MatteBorder(0, 0, 1, 0, new Color(46, 46, 46)),
        		    new EmptyBorder(5, 15, 5, 0)
        		));
        	
        	if (linea.getLongName() == null || linea.getLongName().isBlank()) {
        		btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b></font><br><font size='3'><b>" + linea.getAgency().getName().toUpperCase() +  "</b></font></html>");
        	} else {
        		btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b></font><br><font size='3'><b>" + linea.getLongName() +  "&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</b>" + linea.getAgency().getName().toUpperCase() + "</font></html>");
        	}
        	
        	btnLinea.setBackground(new Color(212, 212, 212));
        	btnLinea.setFont(new Font("Arial Nova", Font.PLAIN, 15));
        	btnLinea.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    		
        	btnLinea.setFocusable(false);
        	
        	for (ActionListener a : btnLinea.getActionListeners()) { btnLinea.removeActionListener(a); }
	        
	        btnLinea.addActionListener(e -> {

	        	frame.getRoutePanel().creaPannelloLinea(linea);
				LineaPainter.costruisciLineaDaDisegnare(frame.getDati().getViaggiDaVisualizzare(linea).getFirst(), frame.getMappa(), frame.getDati());
        		
	        	Ricerca.this.setVisible(false);
	        });
        	
	        panelRisultati.add(btnLinea);
        	
        	switch (linea.getType()) {
				case 0:
					ImageIcon iconTram = new ImageIcon(getClass().getResource("/assets/tram.png"));
			        Image scaledImageTram = iconTram.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
			        ImageIcon newIconTram = new ImageIcon(scaledImageTram);
			        btnLinea.setIcon(newIconTram);
			        break;
			
				case 1:
					switch (linea.getShortName()) {
			        	case "MEA":
			        		ImageIcon iconMetroA = new ImageIcon(getClass().getResource("/assets/metro-a-logo-withborder.png"));
					        Image scaledImageMetroA = iconMetroA.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
					        ImageIcon newIconMetroA = new ImageIcon(scaledImageMetroA);
					        
					        btnLinea.setIcon(newIconMetroA);
					        btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b></font><br><font size='3'><b>METRO A&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</b>" + linea.getAgency().getName().toUpperCase() + "</font></html>");
					        
					        break;
					    
			        	case "MEB":
			        		ImageIcon iconMetroB = new ImageIcon(getClass().getResource("/assets/metro-b-logo-withborder.png"));
					        Image scaledImageMetroB = iconMetroB.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
					        ImageIcon newIconMetroB = new ImageIcon(scaledImageMetroB);
					        
					        btnLinea.setIcon(newIconMetroB);
					        btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b></font><br><font size='3'><b>METRO B&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</b>" + linea.getAgency().getName().toUpperCase() + "</font></html>");
					        
					        break;
					        
			        	case "MEB1":
			        		ImageIcon iconMetroB1 = new ImageIcon(getClass().getResource("/assets/metro-b-logo-withborder.png"));
					        Image scaledImageMetroB1 = iconMetroB1.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
					        ImageIcon newIconMetroB1 = new ImageIcon(scaledImageMetroB1);
					        
					        btnLinea.setIcon(newIconMetroB1);
					        btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b></font><br><font size='3'><b>METRO B1&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</b>" + linea.getAgency().getName().toUpperCase() + "</font></html>");
					        break;
					        
			        	case "MEC":
			        		ImageIcon iconMetroC = new ImageIcon(getClass().getResource("/assets/metro-c-logo-withborder.png"));
					        Image scaledImageMetroC = iconMetroC.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
					        ImageIcon newIconMetroC = new ImageIcon(scaledImageMetroC);
					        
					        btnLinea.setIcon(newIconMetroC);
					        btnLinea.setText("<html><font size='5'><b>" + linea.getId().getId() + "</b></font><br><font size='3'><b>METRO C&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;</b>" + linea.getAgency().getName().toUpperCase() + "</font></html>");
					        
					        break;
			        	}
			        
			        break;
		        
				case 2:
					ImageIcon iconTreno = new ImageIcon(getClass().getResource("/assets/train.png"));
			        Image scaledImageTreno = iconTreno.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
			        ImageIcon newIconTreno = new ImageIcon(scaledImageTreno);
			        btnLinea.setIcon(newIconTreno);
			        break;
		        
				case 3:
					ImageIcon iconBus = new ImageIcon(getClass().getResource("/assets/bus.png"));
			        Image scaledImageBus = iconBus.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
			        ImageIcon newIconBus = new ImageIcon(scaledImageBus);
			        btnLinea.setIcon(newIconBus);
			        break;
        	}	
        }
        
        for (int i = 0; i < fermateTrovate.size(); i++) {
        	
        	int y = (lineeTrovate.size() + i) * 60;
        	Stop fermata = fermateTrovate.get(i);
        	
        	JButton btnFermata = new JButton();
        	
        	btnFermata.setHorizontalAlignment(SwingConstants.LEADING);
        	btnFermata.setIconTextGap(15);
        	btnFermata.setBounds(0, y, navbar.getSearchBar().getWidth()  + 400, 60);
        	
        	btnFermata.setBorder(BorderFactory.createCompoundBorder(
        		    new MatteBorder(0, 0, 1, 0, new Color(46, 46, 46)),
        		    new EmptyBorder(5, 15, 5, 0)
        		));     		
        	
        	btnFermata.setBackground(new Color(212, 212, 212));
        	btnFermata.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        	
        	btnFermata.setFont(new Font("Arial Nova", Font.PLAIN, 15));
        	btnFermata.setText("<html><font size='5'><b>" + fermata.getId().getId() + "</b></font><br><font size='3'><b>" + fermata.getName() + "</b></font></html>");
    		
        	btnFermata.setFocusable(false);
        	
        	ImageIcon iconFermata = new ImageIcon(getClass().getResource("/assets/fermata.png"));
	        Image scaledImageFermata = iconFermata.getImage().getScaledInstance(24, 32, Image.SCALE_SMOOTH);
	        ImageIcon newIconFermata = new ImageIcon(scaledImageFermata);
	        btnFermata.setIcon(newIconFermata);
	        
	        for (ActionListener a : btnFermata.getActionListeners()) { btnFermata.removeActionListener(a); }
	        
	        btnFermata.addActionListener(e -> {

	        	frame.getStopPanel().creaPannelloFermata(fermata);
	        	frame.getMappa().centraMappa(fermata.getLon(), fermata.getLat(), 2);
	        	
	        	Ricerca.this.setVisible(false);
	        });
	        
	        panelRisultati.add(btnFermata);
        }
        
        panelRisultati.revalidate();
        panelRisultati.repaint();
        
        risultatiScrollPane = new JScrollPane(panelRisultati);
        
        risultatiScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        risultatiScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        risultatiScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        risultatiScrollPane.setBounds(0, 0, navbar.getSearchBar().getWidth(), Math.min(300, (lineeTrovate.size() + fermateTrovate.size()) * 60));
        
        this.add(risultatiScrollPane);
        
        this.revalidate();
        this.repaint();
	}
}