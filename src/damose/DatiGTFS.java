package damose;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.google.protobuf.Descriptors;
import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;
import org.onebusaway.gtfs.model.*;
import org.onebusaway.gtfs.serialization.GtfsReader;

import com.google.transit.realtime.GtfsRealtime.*;
import com.google.transit.realtime.GtfsRealtime.TripUpdate.*;



public class DatiGTFS {
	
	private GtfsRelationalDaoImpl datiStatici;
	private FeedMessage tripUpdates, vehiclePositions, alert;
	private JButton logoDamose;
	private JProgressBar progressBar;
	private JPanel schermataCaricamento;
	private JLabel logs;
	
	
	// Metodo che crea e gestisce la schermata di caricamento iniziale dell'applicazione
	public void creaCaricamento() throws Exception {
		
		// JPanel della schermata di caricamento, dove verranno aggiunti gli altri componenti
		schermataCaricamento = new JPanel();
		
		schermataCaricamento.setLayout(null);
		schermataCaricamento.setBackground(new Color(130, 36, 51));
		schermataCaricamento.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		schermataCaricamento.setMinimumSize(new Dimension(1678, 715));
		
		
		// Barra di caricamento per i dati GTFS
		progressBar = new JProgressBar(0, 22);
		
		progressBar.setStringPainted(true);
		progressBar.setBounds(200, 600, 1278, 20);
		
		
		// Messaggi che informano l'utente su quali dati stanno venendo caricati
		logs = new JLabel();
		
		logs.setForeground(Color.LIGHT_GRAY);
		logs.setFont(new Font("Arial Nova", Font.ITALIC, 16));
		logs.setBounds(200, 615, 1278, 40);
		
		
		// Logo dell'applicazione
		logoDamose = new JButton();
		
		logoDamose.setPreferredSize(new Dimension(700, 700));
		logoDamose.setFocusable(false);
		logoDamose.setContentAreaFilled(false);
		logoDamose.setBorderPainted(false);
        logoDamose.setFocusPainted(false);
		
		ImageIcon iconDamose = new ImageIcon("src/resources/damose-logo.png");
		Image scaledImageDamose = iconDamose.getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH);
		ImageIcon newIconDamose = new ImageIcon(scaledImageDamose);
		logoDamose.setIcon(newIconDamose);
		
		logoDamose.setBounds(489, -50, 700, 700);
		
		
		// Aggiunta dei vari componenti alla schermata di caricamento
		schermataCaricamento.add(progressBar);
		schermataCaricamento.add(logs);
		schermataCaricamento.add(logoDamose);
	}


// ---------------------------------------------------------------------------------------------
	
	
	// Metodo get per la schermata di caricamento
	public JPanel getSchermataCaricamento() {
		return this.schermataCaricamento;
	}
	
	
	// Metodo get per i messaggi riguardanti lo stato del caricamento
	public JLabel getLogs() {
		return this.logs;
	}
	
	
	// Metodo get per la barra di caricamento
	public JProgressBar getProgressBar() {
		return this.progressBar;
	}
	
	
	// Metodo set per il valore da mostrare sulla barra di caricamento
	public void setProgress(int i, String nome) {
		this.progressBar.setValue(i);
		logs.setText("Loading " + nome + "...  (" + i +"/22)");
	}

	
// ---------------------------------------------------------------------------------------------


	// Metodo che permette di caricare dei dati GTFS statici da file di testo contenuti in una cartella
	public void caricaDatiStaticiGTFS(String path) throws Exception {

		// Istanziamento del lettore di dati GTFS e configurazione della variabile di destinazione degli stessi
		GtfsReader reader = new GtfsReader();
		GtfsRelationalDaoImpl dati = new GtfsRelationalDaoImpl();
			
		reader.setInputLocation(new File(path));
		reader.setEntityStore(dati);


		// Lista dei tipi di dati GTFS da caricare
		List<Class<?>> listaClassi = List.of(
			Agency.class,
			Calendar.class,
			Route.class,
			Shape.class,
			Stop.class,
			Trip.class,
			Block.class,
			ShapePoint.class,
			Area.class,
			RouteStop.class,
			RouteShape.class,
			Location.class,
			StopTime.class,
			Frequency.class,
			Pathway.class,
			Transfer.class,
		    FeedInfo.class,
		    Vehicle.class,
		    RouteNameException.class,
		    DirectionNameException.class,
		    DirectionEntry.class,
		    AlternateStopNameException.class
		);


		// Caricamento di ciascuna delle classi indicate in listaClassi
		for (int i = 0; i < listaClassi.size(); i++) {
			
			Class<?> classe = listaClassi.get(i);
			reader.readEntities(classe);
			
			if (listaClassi.getLast().equals(classe)) this.setProgress(i + 1, classe.getName());
			else this.setProgress(i + 1, listaClassi.get(i + 1).getName());
		}


		// Assegnamento dei dati GTFS caricati all'attributo datiStatici
		this.datiStatici = dati;
	}
	
	
	// Metodo che permette di caricare dei dati GTFS real-time "TripUpdates" da file .pb
	public void caricaTripUpdatesGTFS() throws Exception {
			
		URL url = new URL("https://romamobilita.it/sites/default/files/rome_rtgtfs_trip_updates_feed.pb");
			
		FeedMessage feed = FeedMessage.parseFrom(url.openStream());
		this.tripUpdates = feed;

		System.out.println("TripUpdates caricati.");
	}


	// Metodo che permette di caricare dei dati GTFS real-time "VehiclePositions" da file .pb
	public void caricaVehiclePositionsGTFS() throws Exception {
				
		URL url = new URL("https://romamobilita.it/sites/default/files/rome_rtgtfs_vehicle_positions_feed.pb");
				
		FeedMessage feed = FeedMessage.parseFrom(url.openStream());
		this.vehiclePositions = feed;

		System.out.println("VehiclePositions caricati.");
	}


	// Metodo che permette di caricare dei dati GTFS real-time "Alert" da file .pb
	public void caricaAlertGTFS() throws Exception {
				
		URL url = new URL("https://romamobilita.it/sites/default/files/rome_rtgtfs_service_alerts_feed.pb");
				
		FeedMessage feed = FeedMessage.parseFrom(url.openStream());
		this.alert = feed;

		System.out.println("Alert caricati.");
	}


// ---------------------------------------------------------------------------------------------


	// Metodo get per i dati statici GTFS dell'istanza
	public GtfsRelationalDaoImpl getDatiStatici() {
		return this.datiStatici;
	}
	
	
	// Metodo get per i dati statici GTFS dell'istanza relativi alle fermate
	public Collection<Stop> getFermate() {
		return this.datiStatici.getAllStops();
	}
	
	
	// Metodo get per i dati statici GTFS dell'istanza relativi alle linee
	public Collection<Route> getLinee() {
		return this.datiStatici.getAllRoutes();
	}
	
	
	// Metodo get per i dati statici GTFS dell'istanza relativi ai viaggi
	public Collection<Trip> getViaggi() {
		return this.datiStatici.getAllTrips();
	}
	
	
	// Metodo get per i dati statici GTFS dell'istanza relativi alle shapes
	public Collection<AgencyAndId> getShapes() {
		return this.datiStatici.getAllShapeIds();
	}
	
	
	// Metodo che cerca e restituisce delle linee in base a una stringa di input
	public List<Route> cercaLinee(String input) {
		
	    List<Route> linee = new ArrayList<>();
	    String normalizedInput = input.toLowerCase();

	    for (Route route : this.getLinee()) {
	        String routeId = route.getId().getId().toLowerCase();
	        String longName = route.getLongName() != null ? route.getLongName().toLowerCase() : "";

	        if (routeId.contains(normalizedInput) || longName.contains(normalizedInput)) {
	            linee.add(route);
	        }
	    }
	    
	    linee.sort((l1, l2) -> l1.getShortName().compareTo(l2.getShortName()));
	    linee.sort((l1, l2) -> Integer.compare(l1.getType(), l2.getType()));

	    return linee;
	}

	
	// Metodo che cerca e restituisce delle fermate in base a una stringa di input
	public List<Stop> cercaFermate(String input) {
		
	    List<Stop> fermate = new ArrayList<>();
	    String normalizedInput = input.toLowerCase();

	    for (Stop stop : this.getFermate()) {
	        String stopId = stop.getId().getId().toLowerCase();
	        String stopName = stop.getName() != null ? stop.getName().toLowerCase() : "";

	        if (stopId.contains(normalizedInput) || stopName.contains(normalizedInput)) {
	            fermate.add(stop);
	        }
	    }
	    
	    fermate.sort((f1, f2) -> f1.getId().getId().compareTo(f2.getId().getId()));

	    return fermate;
	}

	
	// Metodo che cerca e restituisce una linea in base al suo ID
	public Route cercaLineaByID(String lineaID) {

		for (Route linea : this.getLinee()) {
            if (linea.getId().getId().equals(lineaID)) {
                return linea;
            }
        }

		return null;
	}


	// Metodo che cerca e restituisce una fermata in base al suo ID
	public Stop cercaFermataByID(String stopId) {

		for (Stop stop : this.getFermate()) {
            if (stop.getId().getId().equals(stopId)) {
                return stop;
            }
        }

		return null;
	}


	// Metodo che restituisce tutte le fermate appartenenti a una determinata linea
	public List<Stop> getFermatePerViaggio(Trip viaggio) {
		
		List<StopTime> stopTimes = this.datiStatici.getStopTimesForTrip(viaggio);
		List<Stop> listaFermate = new ArrayList<>();
		
		for (StopTime stopTime: stopTimes) {
			listaFermate.add((Stop) stopTime.getStop());
		}

		if (viaggio.getDirectionId().equals("1")) listaFermate = listaFermate.reversed();
		
		return listaFermate;
	}
	
	
	// Metodo che restituisce tutte le linee passanti per una determinata fermata
	public List<Route> getLineePassantiPerFermata(Stop fermata) {
		
		List<Trip> viaggi = new ArrayList<>();
		List<Route> lineePassanti = new ArrayList<>();
		
		for (StopTime stopTime : this.datiStatici.getStopTimesForStop(fermata)) {
			if (!viaggi.contains(stopTime.getTrip())) {
				viaggi.add(stopTime.getTrip());
			}
		}
		
		for (Trip viaggio : viaggi) {
			if (!lineePassanti.contains(viaggio.getRoute())) {
				lineePassanti.add(viaggio.getRoute());
			}
		}
		
		return lineePassanti;
	}


	// Metodo che restituisce tutti i viaggi relativi alla linea attuale
	public List<Trip> getViaggiDaVisualizzare(Route linea) {

		List<Trip> listaViaggi = this.getDatiStatici().getTripsForRoute(linea);
		List<Trip> listaViaggiCopy = new ArrayList<>(listaViaggi);
		listaViaggiCopy.sort((t1, t2) -> Integer.compare(this.getDatiStatici().getStopTimesForTrip(t1).getFirst().getDepartureTime(),
							  								       this.getDatiStatici().getStopTimesForTrip(t2).getFirst().getDepartureTime()));

		return listaViaggiCopy;
	}


	// Metodo che restituisce un booleano in base alla circolarità del viaggio
	public boolean isCircolare(Trip viaggio) {

		List<Stop> fermate = this.getFermatePerViaggio(viaggio);

		if (fermate.getFirst().equals(fermate.getLast())) return true;
		return false;
	}


// ---------------------------------------------------------------------------------------------


	// Metodo get per i tripUpdates dell'istanza
	public FeedMessage getTripUpdates() {
		return this.tripUpdates;
	}
	
	
	// Metodo get per i vehiclePositions dell'istanza
	public FeedMessage getVehiclePositions() {
		return this.vehiclePositions;
	}
	
	
	// Metodo get per gli alert dell'istanza
	public FeedMessage getAlert() {
		return this.alert;
	}


	// Metodo che restituisce tutti i mezzi che stanno percorrendo attualmente una linea
	public List<VehiclePosition> getVeicoliPerLinea(Route linea) {

		FeedMessage veicoli;

		if (this.vehiclePositions != null) veicoli = getVehiclePositions();
		else return null;

		List<VehiclePosition> veicoliDellaLinea = new ArrayList<>();

		for (FeedEntity vp : veicoli.getEntityList()) {

			if (vp.hasVehicle()) {
				VehiclePosition veicolo = vp.getVehicle();
				if (veicolo.getTrip().getRouteId().equals(linea.getId().getId())) veicoliDellaLinea.add(veicolo);
			}
		}

		return veicoliDellaLinea;
	}


// ---------------------------------------------------------------------------------------------


	// Metodo che genera gli storici per le fermate e per le linee
	public void creaStorico() throws IOException {

		// Creazione delle directory "linee" e "fermate", che ospiteranno gli storici delle rispettive entità
		new File("files/linee").mkdirs();
		new File("files/fermate").mkdirs();


		// Ottenimento dell'orario e della data attuali
		LocalDateTime now = LocalDateTime.now();


		// Mappa dei TripUpdate per accesso rapido
		Map<String, TripUpdate> tripUpdatesMap = new HashMap<>();

		for (FeedEntity entity : tripUpdates.getEntityList()) {
			if (entity.hasTripUpdate()) tripUpdatesMap.put(entity.getTripUpdate().getTrip().getTripId(), entity.getTripUpdate());
		}


		// Iterazione su tutte le linee in modo da creare/aggiornare lo storico relativo a ciascuna di esse
		for (Route linea : this.getLinee()) {

			// Ottenimento dei viaggi relativi alla linea
			List<Trip> viaggiProgrammati = this.getViaggiDaVisualizzare(linea);


			for (Trip viaggio : viaggiProgrammati) {

				int ritardo = 0;
				String stato = "NO_DATA";

				// Ottenimento dell'ID del viaggio e degli stopTimes associati ad esso
				String tripId = viaggio.getId().getId();
				List<StopTime> stopTimes = datiStatici.getStopTimesForTrip(viaggio);

				if (stopTimes.isEmpty()) continue;


				// Ottenimento del primo e dell'ultimo stopTime, e conversione in LocalDateTime
				StopTime primaFermata = stopTimes.getFirst();
				StopTime ultimaFermata = stopTimes.getLast();

				LocalDateTime orarioPartenza = LocalDate.now().atStartOfDay().plusSeconds(primaFermata.getDepartureTime());
				LocalDateTime orarioArrivo = LocalDate.now().atStartOfDay().plusSeconds(ultimaFermata.getArrivalTime());


				// Gestione dello stato e del ritardo del viaggio, sia in base alla sua presenza o meno nei tripUpdates, sia in base all'informazione eventualmente contenuta nel tripUpdate associato
				if (!now.isBefore(orarioPartenza) && !now.isAfter(orarioArrivo) && !tripUpdatesMap.containsKey(tripId)) stato = "PUNTUALE";

				else if (tripUpdatesMap.containsKey(tripId)) {

					TripUpdate tripUpdate = tripUpdatesMap.get(tripId);
					TripDescriptor tripDescriptor = tripUpdate.getTrip();

					ritardo = tripUpdate.getDelay();

					if (ritardo >= 120) stato = "RITARDATO";
					if (ritardo <= -120) stato = "ANTICIPO";
					if (ritardo < 120 && ritardo > -120) stato = "PUNTUALE";

					if (tripDescriptor.hasScheduleRelationship()) {
						if (tripDescriptor.getScheduleRelationship() == TripDescriptor.ScheduleRelationship.CANCELED) stato = "CANCELLATO";
						if (tripDescriptor.getScheduleRelationship() == TripDescriptor.ScheduleRelationship.ADDED) stato = "EXTRA";
					}

				} else continue;


				// Se non esiste già, creazione del file dello storico relativo alla linea
				File fileLinea = new File("files/linee/storico_" + linea.getId().getId() + ".txt");
				if (!fileLinea.exists()) fileLinea.createNewFile();


				// Controllo per evitare duplicati
				boolean esiste = false;

				try (BufferedReader reader = new BufferedReader(new FileReader(fileLinea))) {
					String riga;
					while ((riga = reader.readLine()) != null) {
						if (riga.startsWith(tripId + "," + now.toLocalDate() + "," + orarioPartenza)) {
							esiste = true;
							break;
						}
					}
				}


				// Riscrittura dell'intero file di testo dello storico
				if (!esiste) {
					try (FileWriter fw = new FileWriter(fileLinea, true)) {
						fw.write(tripId + "," + now.toLocalDate() + "," + orarioPartenza.toLocalTime() + "," + ritardo + "," + stato + "\n");
					}
				}


				// Mappa degli stopTimeUpdate per accesso rapido
				Map<String, StopTimeUpdate> stopTimeUpdateMap = new HashMap<>();

				if (tripUpdatesMap.containsKey(tripId)) {
					for (StopTimeUpdate stopTimeUpdate : tripUpdatesMap.get(tripId).getStopTimeUpdateList()) { stopTimeUpdateMap.put(stopTimeUpdate.getStopId(), stopTimeUpdate); }
				}


				// Iterazione sulla lista degli stopTimes del viaggio considerato
				for (StopTime stopTime : stopTimes) {

					int ritardoFermata = 0;
					String statoFermata = "PUNTUALE";


					// Ottenimento dell'ID della fermata e dell'orario di arrivo ad essa
					String stopId = stopTime.getStop().getId().getId();
					LocalDateTime orarioFermata = LocalDate.now().atStartOfDay().plusSeconds(stopTime.getArrivalTime());


					// Gestione dello stato e del ritardo dell'arrivo alla fermata, in base all'informazione contenuta nello stopTimeUpdate associato
					if (stopTimeUpdateMap.containsKey(stopId) && tripUpdatesMap.containsKey(tripId)) {

						StopTimeUpdate stopTimeUpdate = stopTimeUpdateMap.get(stopId);
						ritardoFermata = stopTimeUpdate.getArrival().getDelay();

						if (ritardoFermata >= 120) statoFermata = "RITARDATO";
						if (ritardoFermata <= -120) statoFermata = "ANTICIPO";
						if (ritardoFermata > -120 && ritardoFermata < 120) statoFermata = "PUNTUALE";

						if (stopTimeUpdate.hasScheduleRelationship()) {
							if (stopTimeUpdate.getScheduleRelationship() == StopTimeUpdate.ScheduleRelationship.SKIPPED) statoFermata = "SALTATA";
							if (stopTimeUpdate.getScheduleRelationship() == StopTimeUpdate.ScheduleRelationship.NO_DATA) statoFermata = "NO_DATA";
						}

					} else if (tripUpdatesMap.containsKey(tripId))     // Se c'è solamente un tripUpdate, ma non uno stopTimeUpdate
						statoFermata = "NO_DATA";


					// Se non esiste già, creazione del file dello storico relativo alla fermata
					File fileFermata = new File("files/fermate/storico_" + stopId + ".txt");
					if (!fileFermata.exists()) fileFermata.createNewFile();


					// Controllo per evitare duplicati
					boolean esisteStopTime = false;

					try (BufferedReader reader = new BufferedReader(new FileReader(fileFermata))) {
						String riga;
						while ((riga = reader.readLine()) != null) {
							if (riga.startsWith(tripId + "," + now.toLocalDate() + "," + orarioFermata)) {
								esisteStopTime = true;
								break;
							}
						}
					}


					// Riscrittura dell'intero file di testo dello storico
					if (!esisteStopTime) {
						try (FileWriter fw = new FileWriter(fileFermata, true)) {
							fw.write(tripId + "," + now.toLocalDate() + "," + orarioFermata.toLocalTime() + "," + ritardoFermata + "," + statoFermata + "\n");
						}
					}
				}
			}
		}

		System.out.println("Storici creati per le fermate");
		System.out.println("Storici creati per le linee");
	}
}