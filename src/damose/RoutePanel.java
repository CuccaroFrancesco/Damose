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

import org.onebusaway.gtfs.model.*;

import java.util.List;

import java.time.*;
import java.time.format.*;



public class RoutePanel extends JPanel {

	private Frame frame;

	private JLabel codiceLinea, agenziaENomeLinea, lblViaggi, lblViaggioVisualizzato, lblViaggioVisualizzatoInfo, lblMezzi;
	private JButton btnClose, btnRefresh, btnAgency, btnFavorite, btnWebsite, btnRouteType, btnTripLeft, btnTripRight;
	private JPanel fermatePanel;
	private JScrollPane fermateScrollPane;
	private ImageIcon iconIntermezzo, newIconIntermezzo, iconInizio, newIconInizio, iconFine, newIconFine,
			iconIntermezzoMetroA, newIconIntermezzoMetroA, iconInizioMetroA, newIconInizioMetroA, iconFineMetroA, newIconFineMetroA,
			iconIntermezzoMetroB, newIconIntermezzoMetroB, iconInizioMetroB, newIconInizioMetroB, iconFineMetroB, newIconFineMetroB,
			iconIntermezzoMetroC, newIconIntermezzoMetroC, iconInizioMetroC, newIconInizioMetroC, iconFineMetroC, newIconFineMetroC;
	private Image scaledImageIntermezzo, scaledImageFine, scaledImageInizio,
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


		// JLabel per il testo "Prossimi viaggi:"
		lblViaggi = new JLabel("Prossimi viaggi:");

		lblViaggi.setForeground(Color.WHITE);
		lblViaggi.setFont(new Font("Arial Nova", Font.BOLD, 24));
		lblViaggi.setFocusable(false);

		lblViaggi.setBounds(20, 205, 200, 50);

		this.add(lblViaggi);


		// JLabel per l'indice del viaggio visualizzato
		lblViaggioVisualizzato = new JLabel();

		lblViaggioVisualizzato.setForeground(new Color(210, 210, 210));
		lblViaggioVisualizzato.setFont(new Font("Arial Nova", Font.ITALIC, 14));
		lblViaggioVisualizzato.setHorizontalAlignment(SwingConstants.CENTER);
		lblViaggioVisualizzato.setFocusable(false);

		lblViaggioVisualizzato.setBounds(275, 220, 36, 24);

		this.add(lblViaggioVisualizzato);


		// JLabel per le informazioni relative al viaggio visualizzato
		lblViaggioVisualizzatoInfo = new JLabel("<html><div style='width: 280px;'>Capolinea:<br>Fascia oraria:</div></html");

		lblViaggioVisualizzatoInfo.setForeground(new Color(210, 210, 210));
		lblViaggioVisualizzatoInfo.setFont(new Font("Arial Nova", Font.ITALIC, 12));
		lblViaggioVisualizzatoInfo.setFocusable(false);

		lblViaggioVisualizzatoInfo.setBounds(20, 258, 280, 50);

		this.add(lblViaggioVisualizzatoInfo);


		// JLabel per il testo "Mezzi:"
		lblMezzi = new JLabel("Mezzi:");

		lblMezzi.setForeground(Color.WHITE);
		lblMezzi.setFont(new Font("Arial Nova", Font.BOLD, 24));
		lblMezzi.setFocusable(false);

		lblMezzi.setBounds(20, 550, 150, 50);

		this.add(lblMezzi);


		// Pulsante per chiudere il lineaPanel
		btnClose = new JButton(" Chiudi pannello");

		btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		btnClose.setFont(new Font("Arial Nova", Font.BOLD, 14));
		btnClose.setForeground(Color.WHITE);

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
				RoutePanel.this.setVisible(false);
				RoutePanel.this.frame.getMappa().getLineaPainter().setLineaDaDisegnare(new ArrayList<>(), null);
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

		btnWebsite.setForeground(new Color(255, 255, 255));
		btnWebsite.setFont(new Font("Arial Nova", Font.BOLD, 12));
		btnWebsite.setText(" Sito Web");
		btnWebsite.setHorizontalAlignment(SwingConstants.LEADING);

		btnWebsite.setContentAreaFilled(false);
		btnWebsite.setFocusPainted(false);
		btnWebsite.setBorderPainted(false);
		btnWebsite.setBackground(new Color(130, 36, 51));

		btnWebsite.setPreferredSize(new Dimension(20, 20));
		btnWebsite.setBounds(3, 160, 120, 20);

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
		btnTripLeft.setBounds(250, 220, 24, 24);

		ImageIcon iconLeft = new ImageIcon("src/resources/left.png");
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

		ImageIcon iconRight = new ImageIcon("src/resources/right.png");
		Image scaledImageRight = iconRight.getImage().getScaledInstance(12, 20, Image.SCALE_SMOOTH);
		ImageIcon newIconRight = new ImageIcon(scaledImageRight);
		btnTripRight.setIcon(newIconRight);

		this.add(btnTripRight);


		// Icona per l'intermezzo di una linea generica
		iconIntermezzo = new ImageIcon("src/resources/linea-default.png");
		scaledImageIntermezzo = iconIntermezzo.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconIntermezzo = new ImageIcon(scaledImageIntermezzo);

		// Icona per l'inizio di una linea generica
		iconInizio = new ImageIcon("src/resources/linea-default-inizio.png");
		scaledImageInizio = iconInizio.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconInizio = new ImageIcon(scaledImageInizio);

		// Icona per la fine di una linea generica
		iconFine = new ImageIcon("src/resources/linea-default-fine.png");
		scaledImageFine = iconFine.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconFine = new ImageIcon(scaledImageFine);

		// Icona per l'intermezzo della Metro A
		iconIntermezzoMetroA = new ImageIcon("src/resources/linea-metroA.png");
		scaledImageIntermezzoMetroA = iconIntermezzoMetroA.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconIntermezzoMetroA = new ImageIcon(scaledImageIntermezzoMetroA);

		// Icona per l'inizio della Metro A
		iconInizioMetroA = new ImageIcon("src/resources/linea-metroA-inizio.png");
		scaledImageInizioMetroA = iconInizioMetroA.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconInizioMetroA = new ImageIcon(scaledImageInizioMetroA);

		// Icona per la fine della Metro A
		iconFineMetroA = new ImageIcon("src/resources/linea-metroA-fine.png");
		scaledImageFineMetroA = iconFineMetroA.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconFineMetroA = new ImageIcon(scaledImageFineMetroA);

		// Icona per l'intermezzo della Metro B
		iconIntermezzoMetroB = new ImageIcon("src/resources/linea-metroB.png");
		scaledImageIntermezzoMetroB = iconIntermezzoMetroB.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconIntermezzoMetroB = new ImageIcon(scaledImageIntermezzoMetroB);

		// Icona per l'inizio della Metro B
		iconInizioMetroB = new ImageIcon("src/resources/linea-metroB-inizio.png");
		scaledImageInizioMetroB = iconInizioMetroB.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconInizioMetroB = new ImageIcon(scaledImageInizioMetroB);

		// Icona per la fine della Metro B
		iconFineMetroB = new ImageIcon("src/resources/linea-metroB-fine.png");
		scaledImageFineMetroB = iconFineMetroB.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconFineMetroB = new ImageIcon(scaledImageFineMetroB);

		// Icona per l'intermezzo della Metro C
		iconIntermezzoMetroC = new ImageIcon("src/resources/linea-metroC.png");
		scaledImageIntermezzoMetroC = iconIntermezzoMetroC.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconIntermezzoMetroC = new ImageIcon(scaledImageIntermezzoMetroC);

		// Icona per l'inizio della Metro C
		iconInizioMetroC = new ImageIcon("src/resources/linea-metroC-inizio.png");
		scaledImageInizioMetroC = iconInizioMetroC.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconInizioMetroC = new ImageIcon(scaledImageInizioMetroC);

		// Icona per la fine della Metro C
		iconFineMetroC = new ImageIcon("src/resources/linea-metroC-fine.png");
		scaledImageFineMetroC = iconFineMetroC.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		newIconFineMetroC = new ImageIcon(scaledImageFineMetroC);
	}


// ---------------------------------------------------------------------------------------------


	// Metodo che "costruisce" concretamente il routePanel in base alla linea in questione
	public void creaPannelloLinea(Route linea) {

		// Visualizzazione del routePanel e disattivazione di un eventuale stopPanel precedentemente visibile
		this.setVisible(true);
		frame.getStopPanel().setVisible(false);


		// All'inizializzazione del pannello, viene visualizzato di default il primo dei viaggi da visualizzare
		this.viaggiDaVisualizzare = updateViaggiDaVisualizzare(linea);
		this.indiceViaggioVisualizzato = 0;


		// Rimozione di eventuali fermateScrollPane precedenti (necessario per evitare overlap)
		if (fermateScrollPane != null) this.remove(fermateScrollPane);


		// Rimozione di eventuali ActionListener precedenti da vari pulsanti (necessario per evitare overlap)
		for (ActionListener a : btnRefresh.getActionListeners()) { btnRefresh.removeActionListener(a); }
		for (ActionListener a : btnFavorite.getActionListeners()) { btnFavorite.removeActionListener(a); }
		for (ActionListener a : btnWebsite.getActionListeners()) { btnWebsite.removeActionListener(a); }
		for (ActionListener a : btnTripRight.getActionListeners()) { btnTripRight.removeActionListener(a); }
		for (ActionListener a : btnTripLeft.getActionListeners()) { btnTripLeft.removeActionListener(a); }


		// Chiamata al metodo controllaUtente() per verificare se visualizzare o meno il pulsante btnFavorite
		this.controllaUtente(linea);


		// Funzionalità per il pulsante btnRefresh, che permette di aggiornare i viaggi da visualizzare in base all'orario
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RoutePanel.this.viaggiDaVisualizzare = updateViaggiDaVisualizzare(linea);
				aggiornaViaggio(linea, indiceViaggioVisualizzato);
			}
		});


		// Assegnamento dell'icona di default per il pulsante btnFavorite
		String iconCuorePath = "src/resources/cuore-vuoto.png";
		ImageIcon iconCuore = new ImageIcon(iconCuorePath);
		Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		btnFavorite.setIcon(new ImageIcon(scaledImageCuore));


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
					frame.getUtente().cambiaLineePreferite(lineePreferite);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				String iconCuorePath = isOraPreferita ? "src/resources/cuore.png" : "src/resources/cuore-vuoto.png";
				ImageIcon iconCuore = new ImageIcon(iconCuorePath);
				Image scaledImageCuore = iconCuore.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				btnFavorite.setIcon(new ImageIcon(scaledImageCuore));
			}
		});


		// Variabili che contengono informazioni sulla linea (agenzia, long name e short name)
		String agencyName = linea.getAgency().getName();
		String longName = linea.getLongName();
		String shortName = linea.getShortName();


		// Visualizzazione dell'eventuale logo dell'agenzia che gestisce la linea in base a agencyName
		switch (agencyName) {
			case "Atac":
				ImageIcon iconAtac = new ImageIcon("src/resources/atac-logo.png");
				Image scaledImageAtac = iconAtac.getImage().getScaledInstance(65, 45, Image.SCALE_SMOOTH);
				ImageIcon newIconAtac = new ImageIcon(scaledImageAtac);
				btnAgency.setIcon(newIconAtac);
				codiceLinea.setBounds(80, 70, 180, 50);

				break;

			case "Autoservizi Troiani":
				ImageIcon iconTroiani = new ImageIcon("src/resources/autoservizi-troiani-logo.png");
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

		btnAgency.setVisible(true);


		// Visualizzazione dei nomi (long name e short name) assegnati alla linea e del nome dell'agenzia che la gestisce
		codiceLinea.setText(" " + shortName);

		if (longName == null || longName.isEmpty()) {
			agenziaENomeLinea.setText(agencyName);
		} else {
			agenziaENomeLinea.setText(agencyName + "  -  " + longName);
		}


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
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}
		});


		// Visualizzazione del tipo di linea (tram, metropolitana, treno, autobus) in base alla variabile routeType
		int routeType = linea.getType();

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

					case "MEB":
						ImageIcon iconMetroB = new ImageIcon("src/resources/metro-b-logo-withborder.png");
						Image scaledImageMetroB = iconMetroB.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
						ImageIcon newIconMetroB = new ImageIcon(scaledImageMetroB);
						btnAgency.setIcon(newIconMetroB);

						codiceLinea.setText(" Metro B");

						break;

					case "MEB1":
						ImageIcon iconMetroB1 = new ImageIcon("src/resources/metro-b-logo-withborder.png");
						Image scaledImageMetroB1 = iconMetroB1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
						ImageIcon newIconMetroB1 = new ImageIcon(scaledImageMetroB1);
						btnAgency.setIcon(newIconMetroB1);

						codiceLinea.setText(" Metro B1");
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


		// Gestione del viaggio visualizzato nel routePanel e funzionalità per i pulsanti btnTripLeft e btnTripRight
		lblViaggioVisualizzato.setText(indiceViaggioVisualizzato + 1 + "/" + getViaggiDaVisualizzare().size());
		btnTripLeft.setEnabled(false);
		btnTripRight.setEnabled(true);

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
				if (indiceViaggioVisualizzato == getViaggiDaVisualizzare().size() - 1) btnTripRight.setEnabled(false);

				aggiornaViaggio(linea, indiceViaggioVisualizzato);

				frame.getMappa().aggiornaFermateVisibili(viaggiDaVisualizzare.get(indiceViaggioVisualizzato));
				LineaPainter.costruisciLineaDaDisegnare(viaggiDaVisualizzare.get(indiceViaggioVisualizzato), frame.getMappa(), frame.getDati());
			}
		});

		// Chiamata al metodo aggiornaViaggio con l'indice 0, per visualizzare il viaggio di default
		aggiornaViaggio(linea, indiceViaggioVisualizzato);


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


	// Metodo che restituisce i viaggi più rilevanti (quello appena cominciato e i 4 successivi) rispetto all'orario attuale
	public List<Trip> updateViaggiDaVisualizzare(Route linea) {

		LocalTime timeNow = LocalTime.now();

		List<Trip> listaViaggi = this.frame.getDati().getDatiStatici().getTripsForRoute(linea);
		List<Trip> listaViaggiCopy = new ArrayList<>(listaViaggi);
		listaViaggiCopy.sort((t1, t2) -> Integer.compare(RoutePanel.this.frame.getDati().getDatiStatici().getStopTimesForTrip(t1).getFirst().getDepartureTime(),
				                                                   RoutePanel.this.frame.getDati().getDatiStatici().getStopTimesForTrip(t2).getFirst().getDepartureTime()));

		List<Trip> listaViaggiDaVisualizzare = new ArrayList<>();

		for (int i = 0; i < listaViaggiCopy.size(); i++) {

			Trip viaggio = listaViaggiCopy.get(i);

			StopTime primaFermata = this.frame.getDati().getDatiStatici().getStopTimesForTrip(viaggio).getFirst();
			LocalTime orarioPrimaFermata = LocalTime.ofSecondOfDay(primaFermata.getDepartureTime());

			if (orarioPrimaFermata.isAfter(timeNow)) {

				if (i > 0) {

					listaViaggiDaVisualizzare.add(listaViaggiCopy.get(i - 1));
					listaViaggiDaVisualizzare.add(listaViaggiCopy.get(i));

					if (i + 1 < listaViaggiCopy.size()) listaViaggiDaVisualizzare.add(listaViaggiCopy.get(i + 1));
					if (i + 2 < listaViaggiCopy.size()) listaViaggiDaVisualizzare.add(listaViaggiCopy.get(i + 2));
					if (i + 3 < listaViaggiCopy.size()) listaViaggiDaVisualizzare.add(listaViaggiCopy.get(i + 3));

					break;

				} else {

					listaViaggiDaVisualizzare.add(listaViaggiCopy.get(i));

					if (i + 1 < listaViaggiCopy.size()) listaViaggiDaVisualizzare.add(listaViaggiCopy.get(i + 1));
					if (i + 2 < listaViaggiCopy.size()) listaViaggiDaVisualizzare.add(listaViaggiCopy.get(i + 2));
					if (i + 3 < listaViaggiCopy.size()) listaViaggiDaVisualizzare.add(listaViaggiCopy.get(i + 3));
					if (i + 4 < listaViaggiCopy.size()) listaViaggiDaVisualizzare.add(listaViaggiCopy.get(i + 4));

					break;
				}
			}
		}

		return listaViaggiDaVisualizzare;
	}


	// Metodo che gestisce la visualizzazione delle fermate e dei rispettivi orari in base al viaggio scelto
	public void aggiornaViaggio(Route linea, int indice) {

		// Rimozione di eventuali fermateScrollPane precedenti (necessario per evitare overlap)
		if (fermateScrollPane != null) this.remove(fermateScrollPane);


		// Scelta del viaggio da visualizzare in base alla variabile indice
		List<Trip> viaggi = getViaggiDaVisualizzare();
		Trip viaggioDaVisualizzare = viaggi.get(indice);


		// Gestione della lista delle fermate associate al viaggioDaVisualizzare
		List<Stop> fermate = frame.getDati().getFermatePerViaggio(viaggioDaVisualizzare);
		if (viaggioDaVisualizzare.getDirectionId().equals("1")) fermate = fermate.reversed();


		// Ottenimento degli orari associati a ciascuna fermata
		List<StopTime> listaStopTimes = frame.getDati().getDatiStatici().getStopTimesForTrip(viaggioDaVisualizzare);


		// Ottenimento delle informazioni principali relative al viaggio visualizzato (capolinea e fascia oraria) e visualizzazione di tali informazioni
		String partenza = fermate.getFirst().getName();
		String arrivo = fermate.getLast().getName();

		String fasciaOraria = LocalTime.ofSecondOfDay(listaStopTimes.getFirst().getArrivalTime())
				.format(DateTimeFormatter.ofPattern("HH:mm"))
				+ " - "
				+ LocalTime.ofSecondOfDay(listaStopTimes.getLast().getArrivalTime())
				.format(DateTimeFormatter.ofPattern("HH:mm"));

		lblViaggioVisualizzatoInfo.setText(String.format(
				"<html><div style='width: 280px;'>"
						+ "<b>Partenza</b>: %s<br>"
						+ "<b>Arrivo</b>: %s<br>"
						+ "<b>Fascia oraria</b>: %s"
						+ "</div></html>",
				partenza, arrivo, fasciaOraria
		));


		// Creazione e gestione del pannello fermatePanel, che ospiterà la lista delle fermate con i relativi orari
		fermatePanel = new JPanel();
		fermatePanel.setLayout(null);
		fermatePanel.setBackground(new Color(130, 36, 51));
		fermatePanel.setPreferredSize(new Dimension(350, Math.max(100, fermate.size() * 40)));

		List<String> controllati = new ArrayList<>();

		for (int i = 0; i < fermate.size(); i++) {

			int y = i * 40;

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

				default:
					if (i == 0) stopBtn.setIcon(newIconInizio);
					else if (i == fermate.size() - 1) stopBtn.setIcon(newIconFine);
					else stopBtn.setIcon(newIconIntermezzo);

					break;
			}

			fermatePanel.add(stopBtn);

			JLabel orario = new JLabel("--:--");

			orario.setBounds(290, y, 60, 60);
			orario.setFont(new Font("Arial Nova", Font.BOLD, 14));
			orario.setForeground(Color.WHITE);

			if (linea.getType() == 1) {
				if (!controllati.contains(fermata.getName())) {

					for (StopTime stopTime : listaStopTimes) {
						if (stopTime.getStop().getName().equals(fermata.getName())) {

							controllati.add(fermata.getName());

							int orarioArrivoSecondi = stopTime.getArrivalTime();
							LocalTime orarioArrivo = LocalTime.ofSecondOfDay(orarioArrivoSecondi);
							String formattedOrarioArrivo = orarioArrivo.format(DateTimeFormatter.ofPattern("HH:mm"));
							orario.setText(formattedOrarioArrivo);

							break;
						}
					}

				} else {

					for (StopTime stopTime : listaStopTimes) {
						if (stopTime.getStop().getName().equals(fermata.getName())) {

							int orarioArrivoSecondi = stopTime.getArrivalTime();
							LocalTime orarioArrivo = LocalTime.ofSecondOfDay(orarioArrivoSecondi);
							String formattedOrarioArrivo = orarioArrivo.format(DateTimeFormatter.ofPattern("HH:mm"));
							orario.setText(formattedOrarioArrivo);
						}
					}
				}

			} else {
				if (!controllati.contains(fermataID)) {

					for (StopTime stopTime : listaStopTimes) {
						if (stopTime.getStop().getId().getId().equals(fermataID)) {

							controllati.add(fermataID);

							int orarioArrivoSecondi = stopTime.getArrivalTime();
							LocalTime orarioArrivo = LocalTime.ofSecondOfDay(orarioArrivoSecondi);
							String formattedOrarioArrivo = orarioArrivo.format(DateTimeFormatter.ofPattern("HH:mm"));
							orario.setText(formattedOrarioArrivo);

							break;
						}
					}

				} else {

					for (StopTime stopTime : listaStopTimes) {
						if (stopTime.getStop().getId().getId().equals(fermataID)) {

							int orarioArrivoSecondi = stopTime.getArrivalTime();
							LocalTime orarioArrivo = LocalTime.ofSecondOfDay(orarioArrivoSecondi);
							String formattedOrarioArrivo = orarioArrivo.format(DateTimeFormatter.ofPattern("HH:mm"));
							orario.setText(formattedOrarioArrivo);
						}
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


		// Aggiornamento del rendering di fermatePanel e del routePanel nel complesso
		fermatePanel.repaint();
		this.repaint();
	}


// ---------------------------------------------------------------------------------------------


	// Metodo che gestisce il comportamento del pulsante dei preferiti in base allo stato (logged o non logged) dell'utente
	public void controllaUtente(Route linea) {

		if (linea == null) {
			btnFavorite.setEnabled(false);
			btnFavorite.setVisible(false);
			return;
		}

		if (frame.getUtente().getIsLogged()) {
			btnFavorite.setEnabled(true);
			btnFavorite.setVisible(true);

			boolean isPreferita = false;

			for (String lineaPreferita : frame.getUtente().getLineePreferite()) {
				if (lineaPreferita.equals(linea.getId().getId())) {
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

	public void controllaUtente(boolean isLogged) {

		if (isLogged) {
			btnFavorite.setEnabled(true);
			btnFavorite.setVisible(true);

			boolean isPreferita = false;

			for (String lineaPreferita : frame.getUtente().getLineePreferite()) {
				if (lineaPreferita.equals(codiceLinea.getText().trim())) {
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