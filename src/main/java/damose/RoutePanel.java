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

import javax.swing.*;

import java.util.Iterator;
import java.util.List;

import java.time.*;
import java.time.format.*;

import org.jxmapviewer.viewer.GeoPosition;
import org.onebusaway.gtfs.model.*;

import com.google.transit.realtime.GtfsRealtime.*;
import com.google.transit.realtime.GtfsRealtime.TripUpdate.*;



public class RoutePanel extends JPanel {

	private final Frame frame;

	private final JLabel codiceLinea, agenziaENomeLinea, lblPartenze, lblViaggioVisualizzato, lblViaggioVisualizzatoInfo, lblVeicoli, lblNoInfoVeicoli;
	private final JButton btnClose, btnRefresh, btnStats, btnAgency, btnFavorite, btnWebsite, btnRouteType, btnTripLeft, btnTripRight;
	private JPanel fermatePanel, veicoliPanel;
	private JScrollPane fermateScrollPane, veicoliScrollPane;
	private final ImageIcon iconIntermezzoBus, newIconIntermezzoBus, iconInizioBus, newIconInizioBus, iconFineBus, newIconFineBus,
			iconIntermezzoTram, newIconIntermezzoTram, iconInizioTram, newIconInizioTram, iconFineTram, newIconFineTram,
			iconIntermezzoTreno, newIconIntermezzoTreno, iconInizioTreno, newIconInizioTreno, iconFineTreno, newIconFineTreno,
			iconIntermezzoMetroA, newIconIntermezzoMetroA, iconInizioMetroA, newIconInizioMetroA, iconFineMetroA, newIconFineMetroA,
			iconIntermezzoMetroB, newIconIntermezzoMetroB, iconInizioMetroB, newIconInizioMetroB, iconFineMetroB, newIconFineMetroB,
			iconIntermezzoMetroC, newIconIntermezzoMetroC, iconInizioMetroC, newIconInizioMetroC, iconFineMetroC, newIconFineMetroC;
	private final Image scaledImageIntermezzoBus, scaledImageInizioBus, scaledImageFineBus,
			scaledImageIntermezzoTram, scaledImageInizioTram, scaledImageFineTram,
			scaledImageIntermezzoTreno, scaledImageInizioTreno, scaledImageFineTreno,
			scaledImageIntermezzoMetroA, scaledImageInizioMetroA, scaledImageFineMetroA,
			scaledImageIntermezzoMetroB, scaledImageInizioMetroB, scaledImageFineMetroB,
			scaledImageIntermezzoMetroC, scaledImageInizioMetroC, scaledImageFineMetroC;
	private int indiceViaggioVisualizzato;
	private List<Trip> viaggiDaVisualizzare;



	// Costruttore del pannello routePanel
	public RoutePanel(Frame frame) {

		this.frame = frame;

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


		// JLabel per il testo "Partenze:"
		lblPartenze = new JLabel("Partenze:");

		lblPartenze.setForeground(Color.WHITE);
		lblPartenze.setFont(new Font("Arial Nova", Font.BOLD, 24));
		lblPartenze.setFocusable(false);

		lblPartenze.setBounds(20, 205, 120, 50);

		this.add(lblPartenze);


		// JLabel per l'indice del viaggio visualizzato
		lblViaggioVisualizzato = new JLabel();

		lblViaggioVisualizzato.setForeground(new Color(210, 210, 210));
		lblViaggioVisualizzato.setFont(new Font("Arial Nova", Font.ITALIC, 12));
		lblViaggioVisualizzato.setHorizontalAlignment(SwingConstants.CENTER);
		lblViaggioVisualizzato.setFocusable(false);

		lblViaggioVisualizzato.setBounds(245, 220, 66, 24);

		this.add(lblViaggioVisualizzato);


		// JLabel per le informazioni relative al viaggio visualizzato
		lblViaggioVisualizzatoInfo = new JLabel();

		lblViaggioVisualizzatoInfo.setForeground(new Color(210, 210, 210));
		lblViaggioVisualizzatoInfo.setFont(new Font("Arial Nova", Font.ITALIC, 12));
		lblViaggioVisualizzatoInfo.setFocusable(false);

		lblViaggioVisualizzatoInfo.setBounds(20, 258, 280, 50);

		this.add(lblViaggioVisualizzatoInfo);


		// JLabel per il testo "Veicoli:"
		lblVeicoli = new JLabel("Veicoli:");

		lblVeicoli.setForeground(Color.WHITE);
		lblVeicoli.setFont(new Font("Arial Nova", Font.BOLD, 24));
		lblVeicoli.setFocusable(false);

		lblVeicoli.setBounds(20, 550, 150, 50);

		this.add(lblVeicoli);


		// JLabel per il testo "Nessuna informazione sui veicoli della linea."
		lblNoInfoVeicoli = new JLabel("<html>Nessuna informazione sui veicoli<br> della linea.</html>");

		lblNoInfoVeicoli.setForeground(Color.WHITE);
		lblNoInfoVeicoli.setFont(new Font("Arial Nova", Font.BOLD, 14));
		lblNoInfoVeicoli.setFocusable(false);
		lblNoInfoVeicoli.setHorizontalAlignment(SwingConstants.LEADING);

		lblNoInfoVeicoli.setBounds(30, 605, 280, 30);

		this.add(lblNoInfoVeicoli);


		// Pulsante per chiudere il lineaPanel
		btnClose = new JButton(" Chiudi pannello");

		btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnClose.setFont(new Font("Arial Nova", Font.BOLD, 14));
		btnClose.setForeground(Color.WHITE);

		btnClose.setBorderPainted(false);
		btnClose.setFocusPainted(false);
		btnClose.setContentAreaFilled(false);

		btnClose.setBounds(-25, 5, 200, 30);

		ImageIcon iconClose = new ImageIcon(getClass().getResource("/assets/close.png"));
		Image scaledImageClose = iconClose.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon newIconClose = new ImageIcon(scaledImageClose);
		btnClose.setIcon(newIconClose);

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				RoutePanel.this.setVisible(false);

				frame.getMappa().getLineaPainter().setLineaDaDisegnare(new ArrayList<>(), null);
				frame.getMappa().getVeicoliPainter().setVeicoliDaDisegnare(new ArrayList<>());

                if (frame.getUtente().getIsLogged() && frame.getUtente().getFermatePreferiteToggleStatus()) frame.getMappa().aggiornaFermateVisibili(frame.getUtente().getFermatePreferite());
                else frame.getMappa().aggiornaFermateVisibili();
				frame.getMappa().getMapViewer().repaint();
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

		ImageIcon iconRefresh = new ImageIcon(getClass().getResource("/assets/refresh.png"));
		Image scaledImageRefresh = iconRefresh.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon newIconRefresh = new ImageIcon(scaledImageRefresh);
		btnRefresh.setIcon(newIconRefresh);

		this.add(btnRefresh);


		// Pulsante per vedere il pannello delle statistiche della linea
		btnStats = new JButton();

		btnStats.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnStats.setBorderPainted(false);
		btnStats.setFocusPainted(false);
		btnStats.setContentAreaFilled(false);

		btnStats.setBounds(280, 4, 30, 30);

		ImageIcon iconStats = new ImageIcon(getClass().getResource("/assets/stats.png"));
		Image scaledImageStats = iconStats.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon newIconStats = new ImageIcon(scaledImageStats);
		btnStats.setIcon(newIconStats);

		this.add(btnStats);


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


		// Pulsante per l'aggiunta o la rimozione della linea dai preferiti dell'utente
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

		btnWebsite.setForeground(Color.WHITE);
		btnWebsite.setFont(new Font("Arial Nova", Font.BOLD, 12));
		btnWebsite.setText(" Sito Web");
		btnWebsite.setHorizontalAlignment(SwingConstants.LEADING);

		btnWebsite.setContentAreaFilled(false);
		btnWebsite.setFocusPainted(false);
		btnWebsite.setBorderPainted(false);
		btnWebsite.setBackground(new Color(130, 36, 51));

		btnWebsite.setPreferredSize(new Dimension(20, 20));
		btnWebsite.setBounds(3, 160, 120, 20);

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
		btnRouteType.setBounds(175, 160, 150, 20);

		this.add(btnRouteType);


		// Pulsante per scorrere il viaggio da visualizzare verso sinistra
		btnTripLeft = new JButton();

		btnTripLeft.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnTripLeft.setContentAreaFilled(false);
		btnTripLeft.setFocusPainted(false);
		btnTripLeft.setBorderPainted(false);

		btnTripLeft.setBackground(new Color(130, 36, 51));

		btnTripLeft.setPreferredSize(new Dimension(24, 24));
		btnTripLeft.setBounds(220, 220, 24, 24);

		ImageIcon iconLeft = new ImageIcon(getClass().getResource("/assets/left.png"));
		Image scaledImageLeft = iconLeft.getImage().getScaledInstance(12, 20, Image.SCALE_SMOOTH);
		ImageIcon newIconLeft = new ImageIcon(scaledImageLeft);
		btnTripLeft.setIcon(newIconLeft);

		this.add(btnTripLeft);


		// Pulsante per scorrere il viaggio da visualizzare verso destra
		btnTripRight = new JButton();

		btnTripRight.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnTripRight.setContentAreaFilled(false);
		btnTripRight.setFocusPainted(false);
		btnTripRight.setBorderPainted(false);

		btnTripRight.setBackground(new Color(130, 36, 51));

		btnTripRight.setPreferredSize(new Dimension(24, 24));
		btnTripRight.setBounds(310, 220, 24, 24);

		ImageIcon iconRight = new ImageIcon(getClass().getResource("/assets/right.png"));
		Image scaledImageRight = iconRight.getImage().getScaledInstance(12, 20, Image.SCALE_SMOOTH);
		ImageIcon newIconRight = new ImageIcon(scaledImageRight);
		btnTripRight.setIcon(newIconRight);

		this.add(btnTripRight);


		// Icona per l'intermezzo di una linea di bus
		iconIntermezzoBus = new ImageIcon(getClass().getResource("/assets/linea-bus.png"));
		scaledImageIntermezzoBus = iconIntermezzoBus.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconIntermezzoBus = new ImageIcon(scaledImageIntermezzoBus);

		// Icona per l'inizio di una linea di bus
		iconInizioBus = new ImageIcon(getClass().getResource("/assets/linea-bus-inizio.png"));
		scaledImageInizioBus = iconInizioBus.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconInizioBus = new ImageIcon(scaledImageInizioBus);

		// Icona per la fine di una linea di bus
		iconFineBus = new ImageIcon(getClass().getResource("/assets/linea-bus-fine.png"));
		scaledImageFineBus = iconFineBus.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconFineBus = new ImageIcon(scaledImageFineBus);


		// Icona per l'intermezzo di una linea di tram
		iconIntermezzoTram = new ImageIcon(getClass().getResource("/assets/linea-tram.png"));
		scaledImageIntermezzoTram = iconIntermezzoTram.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconIntermezzoTram = new ImageIcon(scaledImageIntermezzoTram);

		// Icona per l'inizio di una linea di tram
		iconInizioTram = new ImageIcon(getClass().getResource("/assets/linea-tram-inizio.png"));
		scaledImageInizioTram = iconInizioTram.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconInizioTram = new ImageIcon(scaledImageInizioTram);

		// Icona per la fine di una linea di tram
		iconFineTram = new ImageIcon(getClass().getResource("/assets/linea-tram-fine.png"));
		scaledImageFineTram = iconFineTram.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconFineTram = new ImageIcon(scaledImageFineTram);


		// Icona per l'intermezzo di una linea di treno
		iconIntermezzoTreno = new ImageIcon(getClass().getResource("/assets/linea-treno.png"));
		scaledImageIntermezzoTreno = iconIntermezzoTreno.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconIntermezzoTreno = new ImageIcon(scaledImageIntermezzoTreno);

		// Icona per l'inizio di una linea di treno
		iconInizioTreno = new ImageIcon(getClass().getResource("/assets/linea-treno-inizio.png"));
		scaledImageInizioTreno = iconInizioTreno.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconInizioTreno = new ImageIcon(scaledImageInizioTreno);

		// Icona per la fine di una linea di treno
		iconFineTreno = new ImageIcon(getClass().getResource("/assets/linea-treno-fine.png"));
		scaledImageFineTreno = iconFineTreno.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconFineTreno = new ImageIcon(scaledImageFineTreno);


		// Icona per l'intermezzo della Metro A
		iconIntermezzoMetroA = new ImageIcon(getClass().getResource("/assets/linea-metroA.png"));
		scaledImageIntermezzoMetroA = iconIntermezzoMetroA.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconIntermezzoMetroA = new ImageIcon(scaledImageIntermezzoMetroA);

		// Icona per l'inizio della Metro A
		iconInizioMetroA = new ImageIcon(getClass().getResource("/assets/linea-metroA-inizio.png"));
		scaledImageInizioMetroA = iconInizioMetroA.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconInizioMetroA = new ImageIcon(scaledImageInizioMetroA);

		// Icona per la fine della Metro A
		iconFineMetroA = new ImageIcon(getClass().getResource("/assets/linea-metroA-fine.png"));
		scaledImageFineMetroA = iconFineMetroA.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconFineMetroA = new ImageIcon(scaledImageFineMetroA);


		// Icona per l'intermezzo della Metro B
		iconIntermezzoMetroB = new ImageIcon(getClass().getResource("/assets/linea-metroB.png"));
		scaledImageIntermezzoMetroB = iconIntermezzoMetroB.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconIntermezzoMetroB = new ImageIcon(scaledImageIntermezzoMetroB);

		// Icona per l'inizio della Metro B
		iconInizioMetroB = new ImageIcon(getClass().getResource("/assets/linea-metroB-inizio.png"));
		scaledImageInizioMetroB = iconInizioMetroB.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconInizioMetroB = new ImageIcon(scaledImageInizioMetroB);

		// Icona per la fine della Metro B
		iconFineMetroB = new ImageIcon(getClass().getResource("/assets/linea-metroB-fine.png"));
		scaledImageFineMetroB = iconFineMetroB.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconFineMetroB = new ImageIcon(scaledImageFineMetroB);


		// Icona per l'intermezzo della Metro C
		iconIntermezzoMetroC = new ImageIcon(getClass().getResource("/assets/linea-metroC.png"));
		scaledImageIntermezzoMetroC = iconIntermezzoMetroC.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconIntermezzoMetroC = new ImageIcon(scaledImageIntermezzoMetroC);

		// Icona per l'inizio della Metro C
		iconInizioMetroC = new ImageIcon(getClass().getResource("/assets/linea-metroC-inizio.png"));
		scaledImageInizioMetroC = iconInizioMetroC.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconInizioMetroC = new ImageIcon(scaledImageInizioMetroC);

		// Icona per la fine della Metro C
		iconFineMetroC = new ImageIcon(getClass().getResource("/assets/linea-metroC-fine.png"));
		scaledImageFineMetroC = iconFineMetroC.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconFineMetroC = new ImageIcon(scaledImageFineMetroC);
	}


// ---------------------------------------------------------------------------------------------


	// Metodo che "costruisce" concretamente il routePanel in base alla linea in questione
	public void creaPannelloLinea(Route linea) {

		// Visualizzazione del routePanel e disattivazione di eventuali stopPanel e statsPanel precedentemente visibili
		this.setVisible(true);

		frame.getStopPanel().setVisible(false);
		frame.getStatsPanel().setVisible(false);


		// All'inizializzazione del pannello, viene visualizzato di default il viaggio con la partenza più vicina all'orario attuale
		this.viaggiDaVisualizzare = this.frame.getDati().getViaggiDaVisualizzare(linea);

		LocalTime timeNow = LocalTime.now();

		for (int i = 0; i < viaggiDaVisualizzare.size(); i++) {
			Trip viaggio = viaggiDaVisualizzare.get(i);

			StopTime primaFermata = this.frame.getDati().getDatiStatici().getStopTimesForTrip(viaggio).getFirst();
			LocalTime orarioPrimaFermata;

			if (primaFermata.getArrivalTime() >= 86400) orarioPrimaFermata = LocalTime.ofSecondOfDay(primaFermata.getArrivalTime() - 86400);
			else orarioPrimaFermata = LocalTime.ofSecondOfDay(primaFermata.getArrivalTime());

			if (orarioPrimaFermata.isAfter(timeNow)) {
				if (i > 0) indiceViaggioVisualizzato = i - 1;
				else indiceViaggioVisualizzato = i;

				break;
			}
		}


		// Rimozione di eventuali JScrollPane precedenti (necessario per evitare overlap)
		if (fermateScrollPane != null) this.remove(fermateScrollPane);
		if (veicoliScrollPane != null) this.remove(veicoliScrollPane);


		// Rimozione di eventuali ActionListener precedenti da vari pulsanti (necessario per evitare overlap)
		for (ActionListener a : btnRefresh.getActionListeners()) { btnRefresh.removeActionListener(a); }
		for (ActionListener a : btnFavorite.getActionListeners()) { btnFavorite.removeActionListener(a); }
		for (ActionListener a : btnWebsite.getActionListeners()) { btnWebsite.removeActionListener(a); }
		for (ActionListener a : btnTripRight.getActionListeners()) { btnTripRight.removeActionListener(a); }
		for (ActionListener a : btnTripLeft.getActionListeners()) { btnTripLeft.removeActionListener(a); }
		for (ActionListener a : btnStats.getActionListeners()) { btnStats.removeActionListener(a); }


		// Funzionalità per il pulsante btnStats, che permette di accedere al pannello con le statistiche relative alla linea
		btnStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                    frame.getStatsPanel().creaPannelloStatistiche(linea);
                } catch (URISyntaxException e1) {
					throw new RuntimeException(e1);
				}
            }
		});


		// Funzionalità per il pulsante btnRefresh, che permette di aggiornare sia i viaggi da visualizzare in base all'orario sia i veicoli percorrenti la linea
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LocalTime timeNow = LocalTime.now();

				for (int i = 0; i < viaggiDaVisualizzare.size(); i++) {
					Trip viaggio = viaggiDaVisualizzare.get(i);

					StopTime primaFermata = RoutePanel.this.frame.getDati().getDatiStatici().getStopTimesForTrip(viaggio).getFirst();
					LocalTime orarioPrimaFermata;

					if (primaFermata.getArrivalTime() >= 86400) orarioPrimaFermata = LocalTime.ofSecondOfDay(primaFermata.getArrivalTime() - 86400);
					else orarioPrimaFermata = LocalTime.ofSecondOfDay(primaFermata.getArrivalTime());

					if (orarioPrimaFermata.isAfter(timeNow)) {
						if (i > 0) indiceViaggioVisualizzato = i - 1;
						else indiceViaggioVisualizzato = i;

						break;
					}
				}

				try {

					aggiornaViaggio(linea, indiceViaggioVisualizzato);
					lblViaggioVisualizzato.setText(indiceViaggioVisualizzato + 1 + "/" + getViaggiDaVisualizzare().size());

					aggiornaVeicoli(linea);

				} catch (Exception ex) {

					ImageIcon iconError = new ImageIcon(getClass().getResource("/assets/error-notification.png"));
					Image scaledImageError = iconError.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
					ImageIcon newIconError = new ImageIcon(scaledImageError);
					frame.getNotificationPanel().getBtnMessage().setIcon(newIconError);
					frame.getNotificationPanel().getBtnMessage().setText("  Errore nell'aggiornamento dei dati. Riprova più tardi.");

					frame.getNotificationPanel().attivaNotifica();

					ex.printStackTrace();
				}
			}
		});


		// Variabili che contengono informazioni sulla linea (agenzia, long name e short name)
		String agencyName = linea.getAgency().getName();
		String longName = linea.getLongName();
		String shortName = linea.getShortName();


		// Visualizzazione dell'eventuale logo dell'agenzia che gestisce la linea in base a agencyName
		switch (agencyName) {
			case "Atac":
				btnAgency.setVisible(true);

				ImageIcon iconAtac = new ImageIcon(getClass().getResource("/assets/atac-logo.png"));
				Image scaledImageAtac = iconAtac.getImage().getScaledInstance(65, 45, Image.SCALE_SMOOTH);
				ImageIcon newIconAtac = new ImageIcon(scaledImageAtac);
				btnAgency.setIcon(newIconAtac);

				codiceLinea.setBounds(80, 70, 180, 50);

				break;

			case "Autoservizi Troiani":
				btnAgency.setVisible(true);

				ImageIcon iconTroiani = new ImageIcon(getClass().getResource("/assets/autoservizi-troiani-logo.png"));
				Image scaledImageTroiani = iconTroiani.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				ImageIcon newIconTroiani = new ImageIcon(scaledImageTroiani);
				btnAgency.setIcon(newIconTroiani);

				codiceLinea.setBounds(80, 70, 180, 50);

				break;

			default:
				btnAgency.setVisible(false);
				codiceLinea.setBounds(12, 70, 180, 50);

				break;
		}


		// Visualizzazione dei nomi (long name e short name) assegnati alla linea e del nome dell'agenzia che la gestisce
		codiceLinea.setText(" " + shortName);

		if (longName == null || longName.isEmpty()) agenziaENomeLinea.setText(agencyName);
		else agenziaENomeLinea.setText(agencyName + "  -  " + longName);


		// Chiamata al metodo controllaUtente() per verificare se visualizzare o meno il pulsante btnFavorite
		this.controllaUtente(frame.getUtente().getIsLogged());


		// Funzionalità per il pulsante btnFavorite
		btnFavorite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				List<String> lineePreferite = frame.getUtente().getLineePreferite();
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
					frame.getUtente().aggiornaUtente(lineePreferite,
							frame.getUtente().getFermatePreferite(),
							frame.getUtente().getFermatePreferiteToggleStatus(),
							frame.getUtente().getSpawnPointLat(),
							frame.getUtente().getSpawnPointLon(),
							frame.getUtente().getCentroAutoSpawnPointToggleStatus());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				String iconCuorePath = isOraPreferita ? "/assets/cuore.png" : "/assets/cuore-vuoto.png";
				ImageIcon iconCuore = new ImageIcon(getClass().getResource(iconCuorePath));
				Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				btnFavorite.setIcon(new ImageIcon(scaledImageCuore));
			}
		});


		// Funzionalità per il pulsante btnWebsite, che permette di accedere a un sito web con informazioni relative alla linea
		String url = linea.getUrl();

		btnWebsite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();

					try {
						desktop.browse(new URI(url));
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (URISyntaxException e2) {
						e2.printStackTrace();
					}
				}
			}
		});


		// Visualizzazione del tipo di linea (tram, metropolitana, treno, autobus) in base alla variabile routeType
		int routeType = linea.getType();

		switch (routeType) {
			case 0:
				ImageIcon iconTram = new ImageIcon(getClass().getResource("/assets/tram.png"));
				Image scaledImageTram = iconTram.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon newIconTram = new ImageIcon(scaledImageTram);
				btnRouteType.setIcon(newIconTram);

				btnRouteType.setText(" Tram");

				ImageIcon iconWebsiteTram = new ImageIcon(getClass().getResource("/assets/mondo-tram.png"));
				Image scaledImageWebsiteTram = iconWebsiteTram.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon newIconWebsiteTram = new ImageIcon(scaledImageWebsiteTram);
				btnWebsite.setIcon(newIconWebsiteTram);
				
				break;

			case 1:
				btnRouteType.setText(" Metropolitana");

				switch (shortName) {
					case "MEA":
						ImageIcon iconMetroA = new ImageIcon(getClass().getResource("/assets/metro-a-logo-withborder.png"));
						Image scaledImageMetroA = iconMetroA.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
						ImageIcon newIconMetroA = new ImageIcon(scaledImageMetroA);
						btnAgency.setIcon(newIconMetroA);

						ImageIcon iconRouteTypeMetroA = new ImageIcon(getClass().getResource("/assets/metro-a.png"));
						Image scaledImageRouteTypeMetroA = iconRouteTypeMetroA.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
						ImageIcon newIconRouteTypeMetroA = new ImageIcon(scaledImageRouteTypeMetroA);
						btnRouteType.setIcon(newIconRouteTypeMetroA);

						ImageIcon iconWebsiteMetroA = new ImageIcon(getClass().getResource("/assets/mondo-metro-a.png"));
						Image scaledImageWebsiteMetroA = iconWebsiteMetroA.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
						ImageIcon newIconWebsiteMetroA = new ImageIcon(scaledImageWebsiteMetroA);
						btnWebsite.setIcon(newIconWebsiteMetroA);

						codiceLinea.setText(" Metro A");

						break;

					case "MEB":
						ImageIcon iconMetroB = new ImageIcon(getClass().getResource("/assets/metro-b-logo-withborder.png"));
						Image scaledImageMetroB = iconMetroB.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
						ImageIcon newIconMetroB = new ImageIcon(scaledImageMetroB);
						btnAgency.setIcon(newIconMetroB);

						ImageIcon iconRouteTypeMetroB = new ImageIcon(getClass().getResource("/assets/metro-b.png"));
						Image scaledImageRouteTypeMetroB = iconRouteTypeMetroB.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
						ImageIcon newIconRouteTypeMetroB = new ImageIcon(scaledImageRouteTypeMetroB);
						btnRouteType.setIcon(newIconRouteTypeMetroB);

						ImageIcon iconWebsiteMetroB = new ImageIcon(getClass().getResource("/assets/mondo-metro-b.png"));
						Image scaledImageWebsiteMetroB = iconWebsiteMetroB.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
						ImageIcon newIconWebsiteMetroB = new ImageIcon(scaledImageWebsiteMetroB);
						btnWebsite.setIcon(newIconWebsiteMetroB);

						codiceLinea.setText(" Metro B");

						break;

					case "MEB1":
						ImageIcon iconMetroB1 = new ImageIcon(getClass().getResource("/assets/metro-b-logo-withborder.png"));
						Image scaledImageMetroB1 = iconMetroB1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
						ImageIcon newIconMetroB1 = new ImageIcon(scaledImageMetroB1);
						btnAgency.setIcon(newIconMetroB1);

						ImageIcon iconRouteTypeMetroB1 = new ImageIcon(getClass().getResource("/assets/metro-b.png"));
						Image scaledImageRouteTypeMetroB1 = iconRouteTypeMetroB1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
						ImageIcon newIconRouteTypeMetroB1 = new ImageIcon(scaledImageRouteTypeMetroB1);
						btnRouteType.setIcon(newIconRouteTypeMetroB1);

						ImageIcon iconWebsiteMetroB1 = new ImageIcon(getClass().getResource("/assets/mondo-metro-b.png"));
						Image scaledImageWebsiteMetroB1 = iconWebsiteMetroB1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
						ImageIcon newIconWebsiteMetroB1 = new ImageIcon(scaledImageWebsiteMetroB1);
						btnWebsite.setIcon(newIconWebsiteMetroB1);

						codiceLinea.setText(" Metro B1");

						break;

					case "MEC":
						ImageIcon iconMetroC = new ImageIcon(getClass().getResource("/assets/metro-c-logo-withborder.png"));
						Image scaledImageMetroC = iconMetroC.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
						ImageIcon newIconMetroC = new ImageIcon(scaledImageMetroC);
						btnAgency.setIcon(newIconMetroC);

						ImageIcon iconRouteTypeMetroC = new ImageIcon(getClass().getResource("/assets/metro-c.png"));
						Image scaledImageRouteTypeMetroC = iconRouteTypeMetroC.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
						ImageIcon newIconRouteTypeMetroC = new ImageIcon(scaledImageRouteTypeMetroC);
						btnRouteType.setIcon(newIconRouteTypeMetroC);

						ImageIcon iconWebsiteMetroC = new ImageIcon(getClass().getResource("/assets/mondo-metro-c.png"));
						Image scaledImageWebsiteMetroC = iconWebsiteMetroC.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
						ImageIcon newIconWebsiteMetroC = new ImageIcon(scaledImageWebsiteMetroC);
						btnWebsite.setIcon(newIconWebsiteMetroC);

						codiceLinea.setText(" Metro C");

						break;
				}

				break;

			case 2:
				ImageIcon iconTreno = new ImageIcon(getClass().getResource("/assets/train.png"));
				Image scaledImageTreno = iconTreno.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon newIconTreno = new ImageIcon(scaledImageTreno);
				btnRouteType.setIcon(newIconTreno);

				btnRouteType.setText(" Treno");

				ImageIcon iconWebsiteTreno = new ImageIcon(getClass().getResource("/assets/mondo-train.png"));
				Image scaledImageWebsiteTreno = iconWebsiteTreno.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon newIconWebsiteTreno = new ImageIcon(scaledImageWebsiteTreno);
				btnWebsite.setIcon(newIconWebsiteTreno);

				break;

			case 3:
				ImageIcon iconBus = new ImageIcon(getClass().getResource("/assets/bus.png"));
				Image scaledImageBus = iconBus.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon newIconBus = new ImageIcon(scaledImageBus);
				btnRouteType.setIcon(newIconBus);

				btnRouteType.setText(" Autobus");

				ImageIcon iconWebsiteBus = new ImageIcon(getClass().getResource("/assets/mondo-bus.png"));
				Image scaledImageWebsiteBus = iconWebsiteBus.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				ImageIcon newIconWebsiteBus = new ImageIcon(scaledImageWebsiteBus);
				btnWebsite.setIcon(newIconWebsiteBus);

				break;
		}


		// Gestione del viaggio visualizzato nel routePanel e funzionalità per i pulsanti btnTripLeft e btnTripRight
		lblViaggioVisualizzato.setText(indiceViaggioVisualizzato + 1 + "/" + getViaggiDaVisualizzare().size());
		btnTripLeft.setEnabled(indiceViaggioVisualizzato != 0);
		btnTripRight.setEnabled(indiceViaggioVisualizzato != getViaggiDaVisualizzare().size() - 1);

		btnTripLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				indiceViaggioVisualizzato--;

				int totaleViaggi = getViaggiDaVisualizzare().size();
				lblViaggioVisualizzato.setText((indiceViaggioVisualizzato + 1) + "/" + totaleViaggi);

				btnTripRight.setEnabled(true);
				btnTripLeft.setEnabled(indiceViaggioVisualizzato != 0);

				aggiornaViaggio(linea, indiceViaggioVisualizzato);

				frame.getMappa().aggiornaFermateVisibili(viaggiDaVisualizzare.get(indiceViaggioVisualizzato));
				LineaPainter.costruisciLineaDaDisegnare(viaggiDaVisualizzare.get(indiceViaggioVisualizzato), frame.getMappa(), frame.getDati());
			}
		});

		btnTripRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				indiceViaggioVisualizzato++;

				lblViaggioVisualizzato.setText(indiceViaggioVisualizzato + 1 + "/" + getViaggiDaVisualizzare().size());

				btnTripLeft.setEnabled(true);
				btnTripRight.setEnabled(indiceViaggioVisualizzato != getViaggiDaVisualizzare().size() - 1);

				aggiornaViaggio(linea, indiceViaggioVisualizzato);

				frame.getMappa().aggiornaFermateVisibili(viaggiDaVisualizzare.get(indiceViaggioVisualizzato));
				LineaPainter.costruisciLineaDaDisegnare(viaggiDaVisualizzare.get(indiceViaggioVisualizzato), frame.getMappa(), frame.getDati());
			}
		});


		// Chiamata al metodo aggiornaViaggio con l'indice 0, per visualizzare il viaggio di default
		aggiornaViaggio(linea, indiceViaggioVisualizzato);


		// Chiamata al metodo aggiornaVeicoli per visualizzare i veicoli percorrenti la linea
		aggiornaVeicoli(linea);


		// Aggiornamento del rendering del routePanel
		this.revalidate();
		this.repaint();
	}


// ---------------------------------------------------------------------------------------------


	// Metodo get per il codice (short name) della linea
	public String getCodiceLinea() {
		return this.codiceLinea.getText();
	}


	// Metodo get per i viaggi da visualizzare nel routePanel
	public List<Trip> getViaggiDaVisualizzare() {
		return this.viaggiDaVisualizzare;
	}


	// Metodo get per l'indice del viaggio visualizzato
	public int getIndiceViaggioVisualizzato() {
		return this.indiceViaggioVisualizzato;
	}


// ---------------------------------------------------------------------------------------------


	// Metodo che gestisce la visualizzazione delle fermate e dei rispettivi orari in base al viaggio scelto
	private void aggiornaViaggio(Route linea, int indice) {

		// Rimozione di eventuali fermateScrollPane precedenti (necessario per evitare overlap)
		if (fermateScrollPane != null) this.remove(fermateScrollPane);


		// Scelta del viaggio da visualizzare in base alla variabile indice
		List<Trip> viaggi = getViaggiDaVisualizzare();
		Trip viaggioDaVisualizzare = viaggi.get(indice);


		// Ottenimento della lista delle fermate associate al viaggioDaVisualizzare
		List<Stop> fermate = frame.getDati().getFermatePerViaggio(viaggioDaVisualizzare);
		if (viaggioDaVisualizzare.getDirectionId().equals("1")) fermate = fermate.reversed();


		// Ottenimento degli orari associati a ciascuna fermata
		List<StopTime> listaStopTimes = frame.getDati().getDatiStatici().getStopTimesForTrip(viaggioDaVisualizzare);


		// Gestione delle fermate e degli orari del viaggioDaVisualizzare in base ai dati real-time
		int ritardo = 0;

		if (frame.getTripUpdatesStatus() != 0) {

			for (FeedEntity entity : frame.getDati().getTripUpdates().getEntityList()) {
				if (entity.hasTripUpdate() && entity.getTripUpdate().getTrip().getTripId().equals(viaggioDaVisualizzare.getId().getId())) {

					TripUpdate tripUpdate = entity.getTripUpdate();
					List<StopTimeUpdate> stopTimeUpdates = tripUpdate.getStopTimeUpdateList();

					ritardo = tripUpdate.getDelay();

					for (StopTimeUpdate stopTimeUpdate : stopTimeUpdates) {

						if (stopTimeUpdate.hasScheduleRelationship() && stopTimeUpdate.getScheduleRelationship() == StopTimeUpdate.ScheduleRelationship.SKIPPED) {
							String skippedStopId = stopTimeUpdate.getStopId();

							Iterator<Stop> iterator = fermate.iterator();
							while (iterator.hasNext()) {

								Stop fermata = iterator.next();
								if (fermata.getId().getId().equals(skippedStopId)) {
									iterator.remove();
									break;
								}
							}
						}
					}

					break;
				}
			}
		}


		// Ottenimento delle informazioni principali relative al viaggio visualizzato (capolinea e fascia oraria) e visualizzazione di tali informazioni
		String partenza = fermate.getFirst().getName();
		String arrivo = fermate.getLast().getName();
		LocalTime orarioPartenza;
		LocalTime orarioArrivo;

		if (listaStopTimes.getFirst().getArrivalTime() + ritardo >= 86400) orarioPartenza = LocalTime.ofSecondOfDay(listaStopTimes.getFirst().getArrivalTime() + ritardo - 86400);
		else orarioPartenza = LocalTime.ofSecondOfDay(listaStopTimes.getFirst().getArrivalTime() + ritardo);

		if (listaStopTimes.getLast().getArrivalTime() + ritardo >= 86400) orarioArrivo = LocalTime.ofSecondOfDay(listaStopTimes.getLast().getArrivalTime() + ritardo - 86400);
		else orarioArrivo = LocalTime.ofSecondOfDay(listaStopTimes.getLast().getArrivalTime() + ritardo);

		String fasciaOraria = orarioPartenza
				.format(DateTimeFormatter.ofPattern("HH:mm"))
				+ " - "
				+ orarioArrivo
				.format(DateTimeFormatter.ofPattern("HH:mm"));

		if (ritardo >= 120) {
			lblViaggioVisualizzatoInfo.setText(String.format(
					"<html><div style='width: 280px;'>"
							+ "<b>Partenza</b>: %s<br>"
							+ "<b>Arrivo</b>: %s<br>"
							+ "<b>Fascia oraria</b>: %s"
							+ "     (ritardo di %s minuti)"
							+ "</div></html>",
					partenza, arrivo, fasciaOraria, ritardo / 60
			));
		} else if (ritardo <= -120) {
			lblViaggioVisualizzatoInfo.setText(String.format(
					"<html><div style='width: 280px;'>"
							+ "<b>Partenza</b>: %s<br>"
							+ "<b>Arrivo</b>: %s<br>"
							+ "<b>Fascia oraria</b>: %s"
							+ "     (anticipo di %s minuti)"
							+ "</div></html>",
					partenza, arrivo, fasciaOraria, - ritardo / 60
			));
		} else {
			lblViaggioVisualizzatoInfo.setText(String.format(
					"<html><div style='width: 280px;'>"
							+ "<b>Partenza</b>: %s<br>"
							+ "<b>Arrivo</b>: %s<br>"
							+ "<b>Fascia oraria</b>: %s"
							+ "</div></html>",
					partenza, arrivo, fasciaOraria
			));
		}


		// Creazione e gestione del pannello fermatePanel, che ospiterà la lista delle fermate con i relativi orari
		fermatePanel = new JPanel();
		fermatePanel.setLayout(null);
		fermatePanel.setBackground(new Color(130, 36, 51));
		fermatePanel.setPreferredSize(new Dimension(350, Math.max(100, fermate.size() * 40 - 20)));

		List<String> controllati = new ArrayList<>();

		for (int i = 0; i < fermate.size(); i++) {

			int y = i * 40 - 20;

			Stop fermata = fermate.get(i);
			String fermataID = fermata.getId().getId();

			JButton stopBtn = new JButton();

			stopBtn.setBounds(10, y, 250, 60);

			stopBtn.setFocusable(false);
			stopBtn.setContentAreaFilled(false);
			stopBtn.setFocusPainted(false);
			stopBtn.setBorderPainted(false);

			stopBtn.setFont(new Font("Arial Nova", Font.BOLD, 12));
			stopBtn.setText("<html><div style='width: 205px;'>  " + fermata.getName() + "</div></html");
			stopBtn.setHorizontalAlignment(SwingConstants.LEADING);

			stopBtn.setForeground(new Color(255, 255, 255));
			stopBtn.setBackground(new Color(130, 36, 51));
			stopBtn.setBorder(BorderFactory.createEmptyBorder());

			switch (linea.getType()) {
				case 0:
					if (i == 0) stopBtn.setIcon(newIconInizioTram);
					else if (i == fermate.size() - 1) stopBtn.setIcon(newIconFineTram);
					else stopBtn.setIcon(newIconIntermezzoTram);

					break;

				case 1:
					switch (linea.getShortName()) {
						case "MEA":
							if (i == 0) stopBtn.setIcon(newIconInizioMetroA);
							else if (i == fermate.size() - 1) stopBtn.setIcon(newIconFineMetroA);
							else stopBtn.setIcon(newIconIntermezzoMetroA);

							break;

						case "MEB", "MEB1":
							if (i == 0) stopBtn.setIcon(newIconInizioMetroB);
							else if (i == fermate.size() - 1) stopBtn.setIcon(newIconFineMetroB);
							else stopBtn.setIcon(newIconIntermezzoMetroB);

							break;

						case "MEC":
							if (i == 0) stopBtn.setIcon(newIconInizioMetroC);
							else if (i == fermate.size() - 1) stopBtn.setIcon(newIconFineMetroC);
							else stopBtn.setIcon(newIconIntermezzoMetroC);

							break;
					}

					break;

				case 2:
					if (i == 0) stopBtn.setIcon(newIconInizioTreno);
					else if (i == fermate.size() - 1) stopBtn.setIcon(newIconFineTreno);
					else stopBtn.setIcon(newIconIntermezzoTreno);

					break;

				case 3:
					if (i == 0) stopBtn.setIcon(newIconInizioBus);
					else if (i == fermate.size() - 1) stopBtn.setIcon(newIconFineBus);
					else stopBtn.setIcon(newIconIntermezzoBus);

					break;
			}

			fermatePanel.add(stopBtn);

			JLabel orario = new JLabel("--:--");

			orario.setBounds(290, y, 60, 60);
			orario.setFont(new Font("Arial Nova", Font.BOLD, 14));
			orario.setForeground(Color.WHITE);

			LocalDateTime timeNow = LocalDateTime.now();

				String verifica = linea.getType() == 1 ? fermata.getName() : fermataID;
				if (!controllati.contains(verifica)) {

					for (StopTime stopTime : listaStopTimes) {
						String IdStopName = linea.getType() == 1 ? stopTime.getStop().getName() : stopTime.getStop().getId().getId();

						if (IdStopName.equals(verifica)) {

							controllati.add(verifica);

							int orarioArrivoFermataInSecondi = stopTime.getArrivalTime() + ritardo;
							LocalDateTime orarioArrivoFermata = LocalDate.now().atStartOfDay().plusSeconds(orarioArrivoFermataInSecondi);

							if (!orarioArrivoFermata.isAfter(timeNow)) {
								orario.setForeground(new Color(170, 170, 170));
								stopBtn.setForeground(new Color(170, 170, 170));
								stopBtn.setFont(new Font("Arial Nova", Font.BOLD | Font.ITALIC, 12));
							}

							String formattedOrarioArrivoFermata = orarioArrivoFermata.format(DateTimeFormatter.ofPattern("HH:mm"));
							orario.setText(formattedOrarioArrivoFermata);

							break;
						}
					}

				} else {

					for (StopTime stopTime : listaStopTimes) {
						String IdStopName = linea.getType() == 1 ? stopTime.getStop().getName() : stopTime.getStop().getId().getId();

						if (IdStopName.equals(verifica)) {

							int orarioArrivoFermataInSecondi = stopTime.getArrivalTime() + ritardo;
							LocalDateTime orarioArrivoFermata = LocalDate.now().atStartOfDay().plusSeconds(orarioArrivoFermataInSecondi);

							if (!orarioArrivoFermata.isAfter(timeNow)) {
								orario.setForeground(new Color(170, 170, 170));
								stopBtn.setForeground(new Color(170, 170, 170));
								stopBtn.setFont(new Font("Arial Nova", Font.BOLD | Font.ITALIC, 12));
							}

							String formattedOrarioArrivoFermata = orarioArrivoFermata.format(DateTimeFormatter.ofPattern("HH:mm"));
							orario.setText(formattedOrarioArrivoFermata);
						}
					}
				}

			fermatePanel.add(orario);
		}


		// Creazione del pannello fermateScrollPane, necessario per ospitare fermatePanel e rendere quest'ultimo "scrollabile"
		fermateScrollPane = new JScrollPane(fermatePanel);

		fermateScrollPane.setBorder(null);
		fermateScrollPane.setBounds(0, 323, 350, 200);

		fermateScrollPane.getVerticalScrollBar().setUnitIncrement(12);
		fermateScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		fermateScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(fermateScrollPane);


		// Aggiornamento del rendering del routePanel
		this.revalidate();
		this.repaint();
	}


	// Metodo che gestisce la visualizzazione dei veicoli che percorrono la linea e delle relative informazioni
	private void aggiornaVeicoli(Route linea) {

		// Rimozione di eventuali veicoliScrollPane precedenti (necessario per evitare overlap)
		if (veicoliScrollPane != null) this.remove(veicoliScrollPane);


		// Rimozione di eventuali veicoli da disegnare precedenti dal veicoliPainter
		frame.getMappa().getVeicoliPainter().setVeicoliDaDisegnare(new ArrayList<>());


		// Gestione della visualizzazione della sezione "Veicoli" in base a vehiclePositionsStatus
		if (frame.getVehiclePositionsStatus() == 0) {

			lblVeicoli.setVisible(false);
			lblNoInfoVeicoli.setVisible(false);

			ImageIcon iconError = new ImageIcon(getClass().getResource("/assets/error-notification.png"));
			Image scaledImageError = iconError.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
			ImageIcon newIconError = new ImageIcon(scaledImageError);
			frame.getNotificationPanel().getBtnMessage().setIcon(newIconError);
			frame.getNotificationPanel().getBtnMessage().setText("<html>&nbsp;&nbsp; Errore nel caricamento dei dati relativi ai veicoli. Controllare la connessione<br>&nbsp;&nbsp; e aggiornare il pannello più tardi.</html>");

			frame.getNotificationPanel().attivaNotifica();

		} else {

			lblVeicoli.setVisible(true);

			// Ottenimento dei veicoli che stanno percorrendo la linea
			List<VehiclePosition> veicoliDellaLinea = frame.getDati().getVeicoliPerLinea(linea);


			// Creazione del pannello veicoliPanel, che ospiterà la lista dei veicoli percorrenti la linea e le relative informazioni
			if (!veicoliDellaLinea.isEmpty()) {

				lblNoInfoVeicoli.setVisible(false);

				veicoliPanel = new JPanel();
				veicoliPanel.setLayout(null);
				veicoliPanel.setBackground(new Color(130, 36, 51));
				veicoliPanel.setPreferredSize(new Dimension(350, Math.max(150, veicoliDellaLinea.size() * 60 - 10)));

				for (int i = 0; i < veicoliDellaLinea.size(); i++) {

					int y = i * 60;

					// Ottenimento del veicolo all'indice i
					VehiclePosition veicolo = veicoliDellaLinea.get(i);


					// Ottenimento di ID e targa (se disponibile) del veicolo
					String idVeicolo = veicolo.getVehicle().getId();
					String targaVeicolo = veicolo.getVehicle().getLicensePlate();


					// Ottenimento dello status (fermo, in arrivo, ecc. ecc.) del veicolo e della fermata verso cui sta andando o nella quale sta sostando
					int statusVeicolo = veicolo.getCurrentStatus().getNumber();
					String fermataVeicolo = frame.getDati().cercaFermataByID(veicolo.getStopId()).getName();


					// Ottenimento della quantità di posti disponibili a bordo del veicolo (se disponibile)
					int postiDisponibiliVeicolo = veicolo.getOccupancyStatus().getNumber();


					// JLabel che visualizza l'ID e la targa (se disponibile) del veicolo
					JLabel lblIdTargaVeicolo = new JLabel();
					lblIdTargaVeicolo.setForeground(Color.WHITE);
					lblIdTargaVeicolo.setFont(new Font("Arial Nova", Font.PLAIN, 14));
					lblIdTargaVeicolo.setHorizontalAlignment(SwingConstants.LEADING);
					lblIdTargaVeicolo.setBounds(20, y, 200, 15);

					if (veicolo.getVehicle().hasLicensePlate() && !veicolo.getVehicle().getLicensePlate().equals("EMPTY")) lblIdTargaVeicolo.setText("<html>" +
							"<div style='width: 150px;'>ID: <b>" + idVeicolo + "</b>   -   " +
							"Targa: <b>" + targaVeicolo + "</b></div>" +
							"</html>");
					else lblIdTargaVeicolo.setText("<html><div style='width: 150px;'>ID: <b>" + idVeicolo + "</b></div></html>");


					// JLabel che visualizza lo status e la fermata del veicolo
					JLabel lblStatusFermataVeicolo = new JLabel();
					lblStatusFermataVeicolo.setForeground(new Color(210, 210, 210));
					lblStatusFermataVeicolo.setFont(new Font("Arial Nova", Font.PLAIN, 12));
					lblStatusFermataVeicolo.setHorizontalAlignment(SwingConstants.LEADING);
					lblStatusFermataVeicolo.setBounds(20, y + 17, 250, 14);

					switch (statusVeicolo) {
						case 1:
							lblStatusFermataVeicolo.setText("Sta arrivando a " + fermataVeicolo);
							break;

						case 2:
							lblStatusFermataVeicolo.setText("E' fermo a " + fermataVeicolo);
							break;

						case 3:
							lblStatusFermataVeicolo.setText("Si dirige verso " + fermataVeicolo);
							break;

						default:
							lblStatusFermataVeicolo.setText("Status e fermata sconosciuti.");
							lblStatusFermataVeicolo.setFont(new Font("Arial Nova", Font.ITALIC, 12));
							lblStatusFermataVeicolo.setForeground(new Color(202, 203, 202));
							break;
					}


					// JLabel che visualizza i posti disponibili a bordo del veicolo
					JLabel lblPostiDisponibiliVeicolo = new JLabel();
					lblPostiDisponibiliVeicolo.setForeground(new Color(210, 210, 210));
					lblPostiDisponibiliVeicolo.setFont(new Font("Arial Nova", Font.PLAIN, 12));
					lblPostiDisponibiliVeicolo.setHorizontalAlignment(SwingConstants.LEADING);
					lblPostiDisponibiliVeicolo.setBounds(20, y + 32, 250, 14);

					switch (postiDisponibiliVeicolo) {
						case 1:
							lblPostiDisponibiliVeicolo.setText("Posti disponibili: TUTTI");
							break;

						case 2:
							lblPostiDisponibiliVeicolo.setText("Posti disponibili: MOLTI");
							break;

						case 3:
							lblPostiDisponibiliVeicolo.setText("Posti disponibili: POCHI");
							break;

						case 4:
							lblPostiDisponibiliVeicolo.setText("Posti disponibili: SOLO IN PIEDI");
							break;

						case 5:
							lblPostiDisponibiliVeicolo.setText("Posti disponibili: POCHI IN PIEDI");
							break;

						case 6:
							lblPostiDisponibiliVeicolo.setText("Posti disponibili: NESSUNO");
							break;

						case 7:
							lblPostiDisponibiliVeicolo.setText("Non accetta passeggeri");
							break;

						default:
							lblPostiDisponibiliVeicolo.setText("Nessuna informazione sui posti disponibili.");
							lblPostiDisponibiliVeicolo.setFont(new Font("Arial Nova", Font.ITALIC, 12));
							lblPostiDisponibiliVeicolo.setForeground(new Color(202, 203, 202));
							break;
					}


					// Aggiunta delle varie componenti a veicoliPanel
					veicoliPanel.add(lblIdTargaVeicolo);
					veicoliPanel.add(lblStatusFermataVeicolo);
					veicoliPanel.add(lblPostiDisponibiliVeicolo);
				}


				// Creazione del pannello veicoliScrollPane, necessario per ospitare veicoliPanel e rendere quest'ultimo "scrollabile"
				veicoliScrollPane = new JScrollPane(veicoliPanel);

				veicoliScrollPane.setBorder(null);
				veicoliScrollPane.setBounds(0, 605, 350, 200);

				veicoliScrollPane.getVerticalScrollBar().setUnitIncrement(12);
				veicoliScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
				veicoliScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

				this.add(veicoliScrollPane);


				// Disegno dei veicoli sulla mappa
				List<VeicoliWaypoint> veicoliDaDisegnare = new ArrayList<>();

				for (VehiclePosition veicolo : veicoliDellaLinea) {

					double latitudine = veicolo.getPosition().getLatitude();
					double longitudine = veicolo.getPosition().getLongitude();
					GeoPosition posizione = new GeoPosition(latitudine, longitudine);

					VeicoliWaypoint veicoloDaDisegnare = new VeicoliWaypoint(veicolo, posizione, frame.getDati());
					veicoliDaDisegnare.add(veicoloDaDisegnare);
				}

				frame.getMappa().getVeicoliPainter().setVeicoliDaDisegnare(veicoliDaDisegnare);

			} else {

				lblNoInfoVeicoli.setVisible(true);
			}


			// Se i vehiclePositions non sono aggiornati, visualizzazione di una notifica
			if (frame.getVehiclePositionsStatus() == 1) {

				ImageIcon iconError = new ImageIcon(getClass().getResource("/assets/error-notification.png"));
				Image scaledImageError = iconError.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
				ImageIcon newIconError = new ImageIcon(scaledImageError);
				frame.getNotificationPanel().getBtnMessage().setIcon(newIconError);
				frame.getNotificationPanel().getBtnMessage().setText("<html>&nbsp;&nbsp; Dati relativi ai veicoli non aggiornati. Controllare la connessione e aggiornare<br>&nbsp;&nbsp; il pannello più tardi.</html>");

				frame.getNotificationPanel().attivaNotifica();

			} else if (frame.getVehiclePositionsStatus() == 2) {

				ImageIcon iconCheck = new ImageIcon(getClass().getResource("/assets/check-notification.png"));
				Image scaledImageCheck = iconCheck.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
				ImageIcon newIconCheck = new ImageIcon(scaledImageCheck);
				frame.getNotificationPanel().getBtnMessage().setIcon(newIconCheck);
				frame.getNotificationPanel().getBtnMessage().setText("  Dati aggiornati con successo!");

				frame.getNotificationPanel().attivaNotifica();
			}
		}


		// Aggiornamento del rendering del routePanel
		this.revalidate();
		this.repaint();
	}

	
// ---------------------------------------------------------------------------------------------


	// Metodo che gestisce il comportamento del pulsante dei preferiti in base allo stato (logged o non logged) dell'utente
	public void controllaUtente(boolean isLogged) {

		if (isLogged) {

			String linea = codiceLinea.getText().trim();

			switch (linea) {
				case "Metro A":
					linea = "MEA";
					break;
				case "Metro B":
					linea = "MEB";
					break;
				case "Metro B1":
					linea = "MEB1";
					break;
				case "Metro C":
					linea = "MEC";
					break;
				default:
					break;
			}

			btnFavorite.setEnabled(true);
			btnFavorite.setVisible(true);

			boolean isPreferita = false;

			for (String lineaPreferita : frame.getUtente().getLineePreferite()) {
				if (lineaPreferita.equals(linea)) {
					isPreferita = true;
					break;
				}
			}

			String iconCuorePath = isPreferita ? "/assets/cuore.png" : "/assets/cuore-vuoto.png";
			ImageIcon iconCuore = new ImageIcon(getClass().getResource(iconCuorePath));
			Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			btnFavorite.setIcon(new ImageIcon(scaledImageCuore));

			btnFavorite.revalidate();
			btnFavorite.repaint();
            RoutePanel.this.repaint();

		} else {

			btnFavorite.setEnabled(false);
			btnFavorite.setVisible(false);
			RoutePanel.this.repaint();
		}
	}
}