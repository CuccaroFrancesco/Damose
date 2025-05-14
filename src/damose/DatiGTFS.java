/**********************************************************************************

Classe "DatiGTFS" per oggetti destinati a conservare dati GTFS (statici e real-time)
come attributi. 

METODI:
- caricaDatiStaticiGTFS(), carica i dati statici e li assegna come attributo all'istanza;
- caricaDatiRealTimeGTFS(), carica i dati real-time e li assegna come attributo all'istanza;
 
- getDatiStatici(), restituisce i dati statici attribuiti all'istanza;
- getLinee(), restituisce esclusivamente i dati statici relativi alle linee;
- getFermate(), restituisce esclusivamente i dati statici relativi alle fermate;
- getShapes(), restituisce esclusivamente i dati statici relativi alle shapes;
- getViaggi(), restituisce esclusivamente i dati statici relativi ai viaggi;

- getDatiRealTime(), restituisce i dati real-time attribuiti all'istanza.

**********************************************************************************/

package damose;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;
import org.onebusaway.gtfs.model.*;
import org.onebusaway.gtfs.serialization.GtfsReader;

import com.google.transit.realtime.GtfsRealtime.FeedMessage;



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
		schermataCaricamento.setMinimumSize(new Dimension(1400, 720));
		
		
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
		
		logoDamose.setPreferredSize(new Dimension(600, 600));
		logoDamose.setFocusable(false);
		logoDamose.setContentAreaFilled(false);
		logoDamose.setBorderPainted(false);
        logoDamose.setFocusPainted(false);
		
		ImageIcon iconDamose = new ImageIcon("src/resources/damose-logo.png");
		Image scaledImageDamose = iconDamose.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
		ImageIcon newIconDamose = new ImageIcon(scaledImageDamose);
		logoDamose.setIcon(newIconDamose);
		
		logoDamose.setBounds(539, 0, 600, 600);
		
		
		// Aggiunta dei vari componenti alla schermata di caricamento
		schermataCaricamento.add(progressBar);
		schermataCaricamento.add(logs);
		schermataCaricamento.add(logoDamose);
	}
	
	
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
	
	
	// Metodo che gestisce l'adattamento dinamico della schermata di caricamento
	private void calibraCaricamento() {
		
	}
	
// ---------------------------------------------------------------------------------------------
	
	// Metodo che permette di caricare dei dati GTFS statici da file di testo contenuti in una cartella
	public void caricaDatiStaticiGTFS(String path) throws Exception {
			
		GtfsReader reader = new GtfsReader();
		GtfsRelationalDaoImpl dati = new GtfsRelationalDaoImpl();
			
		reader.setInputLocation(new File(path));
		reader.setEntityStore(dati);
		
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

		
		for (int i = 0; i < listaClassi.size(); i++) {
			
			Class<?> classe = listaClassi.get(i);
			reader.readEntities(classe);
			
			if (listaClassi.getLast().equals(classe)) this.setProgress(i + 1, classe.getName());
			else this.setProgress(i + 1, listaClassi.get(i + 1).getName());
		}
			
		this.datiStatici = dati;
	}
	
	
	// Metodo che permette di caricare dei dati GTFS real-time "TripUpdates" da file .pb
	public void caricaTripUpdatesGTFS() throws Exception {
			
		URL url = new URL("https://romamobilita.it/sites/default/files/rome_rtgtfs_trip_updates_feed.pb");
			
		FeedMessage feed = FeedMessage.parseFrom(url.openStream());
		this.tripUpdates = feed;
	}
	
	// Metodo che permette di caricare dei dati GTFS real-time "VehiclePositions" da file .pb
	public void caricaVehiclePositionsGTFS() throws Exception {
				
		URL url = new URL("https://romamobilita.it/sites/default/files/rome_rtgtfs_vehicle_positions_feed.pb");
				
		FeedMessage feed = FeedMessage.parseFrom(url.openStream());
		this.vehiclePositions = feed;
	}
	
	// Metodo che permette di caricare dei dati GTFS real-time "Alert" da file .pb
	public void caricaAlertGTFS() throws Exception {
				
		URL url = new URL("https://romamobilita.it/sites/default/files/rome_rtgtfs_service_alerts_feed.pb");
				
		FeedMessage feed = FeedMessage.parseFrom(url.openStream());
		this.alert = feed;
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

	    return fermate;
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
	
	
	// Metodo che cerca e restituisce una linea in base al suo ID
	public Route cercaLineaByID(String lineaID) {
		
		for (Route linea : this.getLinee()) {
            if (linea.getId().getId().equals(lineaID)) {
                return linea;
            }
        }
		
		return null;
	}
	
	
	// Metodo che restituisce tutte le fermate appartenenti a una determinata linea
	public List<Stop> getFermatePerLinea(Route linea) {
		
		Trip viaggio = this.datiStatici.getTripsForRoute(linea).getFirst();
		
		List<StopTime> stopTimes = this.datiStatici.getStopTimesForTrip(viaggio);
		List<Stop> listaFermate = new ArrayList<>();
		
		for (StopTime stopTime: stopTimes) {
			listaFermate.add((Stop) stopTime.getStop());
		}
		
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
}
