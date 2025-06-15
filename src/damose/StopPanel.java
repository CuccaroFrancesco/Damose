package damose;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;

import org.onebusaway.gtfs.model.*;

import java.awt.event.ActionListener;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import java.util.Map;

import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.TripUpdate;
import com.google.transit.realtime.GtfsRealtime.TripUpdate.StopTimeUpdate;



public class StopPanel extends JPanel {
	
	private Frame frame;
	
	private JLabel nomeFermata, codiceFermata, lblArrivi, lblLineePassanti;
	private JButton btnClose, btnRefresh, btnStopIcon, btnFavorite, btnCoordinates, btnStats;
	private JPanel prossimiArriviPanel, lineePassantiPanel;
	private JScrollPane prossimiArriviScrollPane, lineePassantiScrollPane;
	private List<StopTime> arriviDaVisualizzare;


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
		
		
		// JLabel che contiene la scritta "Linee passanti:"
		lblLineePassanti = new JLabel("Linee passanti:");
		
		lblLineePassanti.setHorizontalAlignment(SwingConstants.LEADING);
		
		lblLineePassanti.setForeground(Color.WHITE);
		lblLineePassanti.setFont(new Font("Arial Nova", Font.BOLD, 24));
		lblLineePassanti.setFocusable(false);
		
		lblLineePassanti.setBounds(20, 430, 200, 50);
		
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

				StopPanel.this.frame.getMappa().aggiornaFermateVisibili();
				StopPanel.this.frame.getMappa().getMapViewer().repaint();
        	}
        });
        
        this.add(btnClose);


		// Pulsante per aggiornare i dati visualizzati in base all'orario di visualizzazione
		btnRefresh = new JButton();

		btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnRefresh.setBorderPainted(false);
		btnRefresh.setFocusPainted(false);
		btnRefresh.setContentAreaFilled(false);

		btnRefresh.setBounds(313, 4, 30, 30);

		ImageIcon iconRefresh = new ImageIcon("src/resources/refresh.png");
		Image scaledImageRefresh = iconRefresh.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon newIconRefresh = new ImageIcon(scaledImageRefresh);
		btnRefresh.setIcon(newIconRefresh);

		this.add(btnRefresh);

		// Pulsante per vedere il pannello delle statistiche della fermata
		btnStats = new JButton();

		btnStats.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnStats.setBorderPainted(false);
		btnStats.setFocusPainted(false);
		btnStats.setContentAreaFilled(false);

		btnStats.setBounds(280, 4, 30, 30);

		ImageIcon iconStats = new ImageIcon("src/resources/stats.png");
		Image scaledImageStats = iconStats.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon newIconStats = new ImageIcon(scaledImageStats);
		btnStats.setIcon(newIconStats);

		this.add(btnStats);
        
        
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

		// Visualizzazione dello stopPanel e disattivazione di eventuali routePanel e statsPanel precedentemente visibili
		this.setVisible(true);

		frame.getMappa().getLineaPainter().setLineaDaDisegnare(new ArrayList<>(), null);
		frame.getRoutePanel().setVisible(false);
		frame.getStatsPanel().setVisible(false);


		// Rimozione di eventuali lineePassantiScrollPane precedenti (necessario per evitare overlap)
		if (prossimiArriviScrollPane != null) this.remove(prossimiArriviScrollPane);
		if (lineePassantiScrollPane != null) this.remove(lineePassantiScrollPane);


		// Rimozione di eventuali ActionListener precedenti da vari pulsanti (necessario per evitare overlap)
		for (ActionListener a : btnRefresh.getActionListeners()) { btnRefresh.removeActionListener(a); }
		for (ActionListener a : btnFavorite.getActionListeners()) { btnFavorite.removeActionListener(a); }
		for (ActionListener a : btnStats.getActionListeners()) { btnStats.removeActionListener(a); }

		// Funzionalità per il pulsante btnStats, che permette di accedere al pannello con le statistiche relative alla linea
		btnStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getStatsPanel().creaPannelloStatistiche(fermata);
			}
		});

		// Ottenimento dei prossimi 10 arrivi alla fermata
		aggiornaArriviDaVisualizzare(fermata);


		// Funzionalità per il pulsante btnRefresh
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					aggiornaArriviDaVisualizzare(fermata);

					ImageIcon iconCheck = new ImageIcon("src/resources/check-notification.png");
					Image scaledImageCheck = iconCheck.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
					ImageIcon newIconCheck = new ImageIcon(scaledImageCheck);
					frame.getNotificationPanel().getBtnMessage().setIcon(newIconCheck);
					frame.getNotificationPanel().getBtnMessage().setText("  Dati aggiornati con successo!");

					frame.getNotificationPanel().attivaNotifica();

				} catch (Exception ex) {

					ImageIcon iconError = new ImageIcon("src/resources/error-notification.png");
					Image scaledImageError = iconError.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
					ImageIcon newIconError = new ImageIcon(scaledImageError);
					frame.getNotificationPanel().getBtnMessage().setIcon(newIconError);
					frame.getNotificationPanel().getBtnMessage().setText("  Errore nell'aggiornamento dei dati. Riprova più tardi.");

					frame.getNotificationPanel().attivaNotifica();

					ex.printStackTrace();
				}
			}
		});


		// Assegnamento dell'icona di default per il pulsante btnFavorite
		String iconCuorePath = "src/resources/cuore-vuoto.png";
		ImageIcon iconCuore = new ImageIcon(iconCuorePath);
		Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		btnFavorite.setIcon(new ImageIcon(scaledImageCuore));


		// Chiamata al metodo controllaUtente() per verificare se visualizzare o meno il pulsante btnFavorite
		this.controllaUtente(this.frame.getUtente().getIsLogged());


		// Funzionalità per il pulsante btnFavorite
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


		// Variabili che contengono informazioni sulla fermata (nome, ID e coordinate)
		String name = fermata.getName();
		String id = fermata.getId().getId();
		double lat = fermata.getLat();
		double lon = fermata.getLon();


		// Visualizzazione del nome e dell'ID della fermata
		nomeFermata.setText(name);
		codiceFermata.setText("ID: " + id);


		// Visualizzazione delle coordinate (latitudine e longitudine) della fermata
		btnCoordinates.setText(" " + Math.floor(lat * 100000) / 100000 + ", " + Math.floor(lon * 100000) / 100000);


		// Visualizzazione delle linee passanti per la fermata, con pulsanti interattivi per le varie linee
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


		// Inserimento delle linee passanti in un JScrollPane
		lineePassantiScrollPane = new JScrollPane(lineePassantiPanel);
        
		lineePassantiScrollPane.setBorder(null);
		lineePassantiScrollPane.setBounds(10, 480, 350, 200);
        
		lineePassantiScrollPane.getVerticalScrollBar().setUnitIncrement(12);
		lineePassantiScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		lineePassantiScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        this.add(lineePassantiScrollPane);
        lineePassantiPanel.repaint();


		// Aggiornamento del rendering dello stopPanel
		this.revalidate();
		this.repaint();
	}


// ---------------------------------------------------------------------------------------------


	// Metodo get per l'ID della fermata
	public String getCodiceFermata() {
		return this.codiceFermata.getText();
	}


// ---------------------------------------------------------------------------------------------


	// Metodo che restituisce i 10 prossimi arrivi alla fermata in base all'orario attuale
	private void aggiornaArriviDaVisualizzare(Stop fermata) {

		// Rimozione di eventuali prossimiArriviScrollPane precedenti (necessario per evitare overlap)
		if (prossimiArriviScrollPane != null) this.remove(prossimiArriviScrollPane);


		// Ottenimento dei 10 arrivi da visualizzare in base all'orario attuale
		LocalDateTime timeNow = LocalDateTime.now();
		List<StopTime> arriviDaVisualizzare = new ArrayList<>();

		List<StopTime> arrivi = frame.getDati().getDatiStatici().getStopTimesForStop(fermata);
		List<StopTime> arriviCopy = new ArrayList<>(arrivi);
		arriviCopy.sort((s1, s2) -> LocalDate.now().atStartOfDay().plusSeconds(s1.getArrivalTime()).compareTo(LocalDate.now().atStartOfDay().plusSeconds(s2.getArrivalTime())));

		for (int i = 0; i < arriviCopy.size(); i++) {

			StopTime stopTime = arriviCopy.get(i);

			int orarioArrivoInSecondi = stopTime.getArrivalTime();
			LocalDateTime orarioArrivo = LocalDate.now().atStartOfDay().plusSeconds(orarioArrivoInSecondi);

			if (orarioArrivo.isAfter(timeNow)) {
				arriviDaVisualizzare.add(stopTime);

				if (i + 1 < arriviCopy.size()) arriviDaVisualizzare.add(arriviCopy.get(i + 1));
				if (i + 2 < arriviCopy.size()) arriviDaVisualizzare.add(arriviCopy.get(i + 2));
				if (i + 3 < arriviCopy.size()) arriviDaVisualizzare.add(arriviCopy.get(i + 3));
				if (i + 4 < arriviCopy.size()) arriviDaVisualizzare.add(arriviCopy.get(i + 4));
				if (i + 5 < arriviCopy.size()) arriviDaVisualizzare.add(arriviCopy.get(i + 5));
				if (i + 6 < arriviCopy.size()) arriviDaVisualizzare.add(arriviCopy.get(i + 6));
				if (i + 7 < arriviCopy.size()) arriviDaVisualizzare.add(arriviCopy.get(i + 7));
				if (i + 8 < arriviCopy.size()) arriviDaVisualizzare.add(arriviCopy.get(i + 8));
				if (i + 9 < arriviCopy.size()) arriviDaVisualizzare.add(arriviCopy.get(i + 9));

				break;
			}
		}


		// Aggiornamento degli arrivi in base ai dati real-time, se presenti
		if (frame.getTripUpdatesStatus() != 0) {
			Map<StopTime, Integer> ritardi = new HashMap<>();

			for (StopTime arrivo : arriviDaVisualizzare) {

				int ritardo = 0;

				for (FeedEntity entity : frame.getDati().getTripUpdates().getEntityList()) {
					if (entity.hasTripUpdate() && entity.getTripUpdate().getTrip().getTripId().equals(arrivo.getTrip().getId().getId())) {

						TripUpdate tripUpdate = entity.getTripUpdate();

						for (StopTimeUpdate stopTimeUpdate : tripUpdate.getStopTimeUpdateList()) {
							if (stopTimeUpdate.getStopId().equals(arrivo.getStop().getId().getId()) && stopTimeUpdate.hasArrival() && stopTimeUpdate.getArrival().hasDelay()) {
								ritardo = stopTimeUpdate.getArrival().getDelay();
								break;
							}
						}

						break;
					}
				}

				ritardi.put(arrivo, ritardo);
			}

			arriviDaVisualizzare.sort((s1, s2) -> LocalDate.now().atStartOfDay().plusSeconds(s1.getArrivalTime() + ritardi.getOrDefault(s1, 0)).compareTo(LocalDate.now().atStartOfDay().plusSeconds(s2.getArrivalTime() + ritardi.getOrDefault(s2, 0))));
		}


		// Assegnamento degli arriviDaVisualizzare al corrispondente attributo dell'istanza di StopPanel
		this.arriviDaVisualizzare = arriviDaVisualizzare;


		// Istanziamento di un JPanel che ospiterà i visualizzatori degli arrivi da visualizzare
		prossimiArriviPanel = new JPanel();
		prossimiArriviPanel.setLayout(null);
		prossimiArriviPanel.setBackground(new Color(130, 36, 51));
		prossimiArriviPanel.setPreferredSize(new Dimension(350, Math.max(100, this.arriviDaVisualizzare.size() * 50)));


		// Creazione di vari JLabel e JButton per visualizzare i dati relativi ai prossimi arrivi
		for (int i = 0; i < this.arriviDaVisualizzare.size(); i++) {

			StopTime arrivo = this.arriviDaVisualizzare.get(i);
			int y = i * 70;

			Route lineaDiAppartenenza = arrivo.getTrip().getRoute();
			LocalDateTime orarioArrivo = LocalDate.now().atStartOfDay().plusSeconds(arrivo.getArrivalTime());
			Duration tempoMancante = Duration.between(LocalDateTime.now(), orarioArrivo);
			Stop fermataDiArrivo = frame.getDati().getFermatePerViaggio(arrivo.getTrip()).getLast();

			JLabel lblTempoMancante = new JLabel();
			lblTempoMancante.setForeground(Color.WHITE);
			lblTempoMancante.setFont(new Font("Arial Nova", Font.BOLD, 12));
			lblTempoMancante.setHorizontalAlignment(SwingConstants.CENTER);
			lblTempoMancante.setBounds(10, y + 10, 50, 50);

			if (tempoMancante.compareTo(Duration.ofMinutes(1)) < 0) lblTempoMancante.setText("<html>" +
																								"<div style='text-align:center;'>" +
																									"<span style='font-size:8px;'>IN</span><br>" +
																									"<span style='font-size:10px;'>ARRIVO</span>" +
																								"</div>" +
																							 "</html>");
			else if (tempoMancante.compareTo(Duration.ofHours(1)) < 0) {
				if (tempoMancante.toMinutes() < 2) lblTempoMancante.setText("<html>" +
																				"<div style='text-align:center;'>" +
																					"<span style='font-size:8px;'>TRA</span><br>" +
																					"<span style='font-size:14px;'>1</span><br>" +
																					"<span style='font-size:8px;'>MINUTO</span>" +
																				"</div>" +
																			"</html>");
				else lblTempoMancante.setText("<html>" +
												"<div style='text-align:center;'>" +
													"<span style='font-size:8px;'>TRA</span><br>" +
													"<span style='font-size:14px;'>" + tempoMancante.toMinutes() + "</span><br>" +
													"<span style='font-size:8px;'>MINUTI</span>" +
												"</div>" +
											  "</html>");
			} else {
				if (tempoMancante.toHours() < 2) lblTempoMancante.setText("<html>" +
																			"<div style='text-align:center;'>" +
																				"<span style='font-size:8px;'>TRA</span><br>" +
																				"<span style='font-size:14px;'>1</span><br>" +
																				"<span style='font-size:8px;'>ORA</span>" +
																			"</div>" +
						                                                  "</html>");
				else lblTempoMancante.setText("<html>" +
												"<div style='text-align:center;'>" +
													"<span style='font-size:8px;'>TRA</span><br>" +
													"<span style='font-size:14px;'>" + tempoMancante.toMinutes() + "</span><br>" +
													"<span style='font-size:8px;'>ORE</span>" +
												"</div>" +
											  "</html>");
			}

			JLabel lblCodiceLinea = new JLabel();
			lblCodiceLinea.setForeground(Color.WHITE);
			lblCodiceLinea.setFont(new Font("Arial Nova", Font.BOLD, 18));
			lblCodiceLinea.setHorizontalAlignment(SwingConstants.LEADING);
			lblCodiceLinea.setBounds(105, y + 13, 100, 15);

			lblCodiceLinea.setText(lineaDiAppartenenza.getShortName());

			JButton btnIconaLinea = new JButton();
			btnIconaLinea.setContentAreaFilled(false);
			btnIconaLinea.setFocusPainted(false);
			btnIconaLinea.setBorderPainted(false);
			btnIconaLinea.setFocusable(false);
			btnIconaLinea.setBackground(new Color(130, 36, 51));

			btnIconaLinea.setBounds(80, y + 13, 16, 16);
			btnIconaLinea.setPreferredSize(new Dimension(16, 16));

			switch (lineaDiAppartenenza.getType()) {
				case 0:
					ImageIcon iconTram = new ImageIcon("src/resources/tram.png");
					Image scaledImageTram = iconTram.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
					ImageIcon newIconTram = new ImageIcon(scaledImageTram);
					btnIconaLinea.setIcon(newIconTram);

					break;

				case 1:
					switch (lineaDiAppartenenza.getShortName()) {
						case "MEA":
							ImageIcon iconMetroA = new ImageIcon("src/resources/metro-a-logo-withborder.png");
							Image scaledImageMetroA = iconMetroA.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
							ImageIcon newIconMetroA = new ImageIcon(scaledImageMetroA);
							btnIconaLinea.setIcon(newIconMetroA);

							lblCodiceLinea.setText("Metro A");

							break;

						case "MEB":
							ImageIcon iconMetroB = new ImageIcon("src/resources/metro-b-logo-withborder.png");
							Image scaledImageMetroB = iconMetroB.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
							ImageIcon newIconMetroB = new ImageIcon(scaledImageMetroB);
							btnIconaLinea.setIcon(newIconMetroB);

							lblCodiceLinea.setText("Metro B");

							break;

						case "MEB1":
							ImageIcon iconMetroB1 = new ImageIcon("src/resources/metro-b-logo-withborder.png");
							Image scaledImageMetroB1 = iconMetroB1.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
							ImageIcon newIconMetroB1 = new ImageIcon(scaledImageMetroB1);
							btnIconaLinea.setIcon(newIconMetroB1);

							lblCodiceLinea.setText("Metro B1");

							break;

						case "MEC":
							ImageIcon iconMetroC = new ImageIcon("src/resources/metro-c-logo-withborder.png");
							Image scaledImageMetroC = iconMetroC.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
							ImageIcon newIconMetroC = new ImageIcon(scaledImageMetroC);
							btnIconaLinea.setIcon(newIconMetroC);

							lblCodiceLinea.setText("Metro C");

							break;
					}

					break;

				case 2:
					ImageIcon iconTreno = new ImageIcon("src/resources/train.png");
					Image scaledImageTreno = iconTreno.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
					ImageIcon newIconTreno = new ImageIcon(scaledImageTreno);
					btnIconaLinea.setIcon(newIconTreno);

					break;

				case 3:
					ImageIcon iconBus = new ImageIcon("src/resources/bus.png");
					Image scaledImageBus = iconBus.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
					ImageIcon newIconBus = new ImageIcon(scaledImageBus);
					btnIconaLinea.setIcon(newIconBus);

					break;
			}

			JLabel lblOrarioArrivo = new JLabel();
			lblOrarioArrivo.setForeground(new Color(210, 210, 210));
			lblOrarioArrivo.setFont(new Font("Arial Nova", Font.PLAIN, 12));
			lblOrarioArrivo.setHorizontalAlignment(SwingConstants.LEADING);
			lblOrarioArrivo.setBounds(80, y + 33, 100, 10);

			lblOrarioArrivo.setText("Arriva alle " + orarioArrivo.format(DateTimeFormatter.ofPattern("HH:mm")));

			JLabel lblDirezione = new JLabel();
			lblDirezione.setForeground(new Color(210, 210, 210));
			lblDirezione.setFont(new Font("Arial Nova", Font.PLAIN, 12));
			lblDirezione.setHorizontalAlignment(SwingConstants.LEADING);
			lblDirezione.setBounds(80, y + 48, 235, 10);

			lblDirezione.setText("Direzione: " + fermataDiArrivo.getName());

			prossimiArriviPanel.add(lblTempoMancante);
			prossimiArriviPanel.add(lblCodiceLinea);
			prossimiArriviPanel.add(lblOrarioArrivo);
			prossimiArriviPanel.add(lblDirezione);
			prossimiArriviPanel.add(btnIconaLinea);
		}


		// Creazione del pannello prossimiArriviScrollPane, necessario per ospitare prossimiArriviPanel e rendere quest'ultimo "scrollabile"
		prossimiArriviScrollPane = new JScrollPane(prossimiArriviPanel);

		prossimiArriviScrollPane.setBorder(null);
		prossimiArriviScrollPane.setBounds(10, 210, 350, 200);

		prossimiArriviScrollPane.getVerticalScrollBar().setUnitIncrement(12);
		prossimiArriviScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		prossimiArriviScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(prossimiArriviScrollPane);


		// Aggiornamento del rendering dello stopPanel
		this.revalidate();
		this.repaint();
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

			btnFavorite.revalidate();
		    btnFavorite.repaint();

		} else {

			btnFavorite.setEnabled(false);
			btnFavorite.setVisible(false);
			StopPanel.this.repaint();
		}
	}
}