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

import java.awt.Shape;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;
import org.onebusaway.gtfs.model.*;
import org.onebusaway.gtfs.serialization.GtfsReader;

import com.google.transit.realtime.GtfsRealtime.FeedMessage;



public class DatiGTFS {
	
	private GtfsRelationalDaoImpl datiStatici;
	private FeedMessage datiRealTime;
	
	
	// Metodo che permette di caricare dei dati GTFS statici da file di testo contenuti in una cartella
	public void caricaDatiStaticiGTFS(String path, boolean caricaStopTimes) throws Exception {
			
		GtfsReader reader = new GtfsReader();
		GtfsRelationalDaoImpl dati = new GtfsRelationalDaoImpl();
			
		reader.setInputLocation(new File(path));
		if (!caricaStopTimes) {
			reader.setEntityClasses(List.of(Agency.class, Calendar.class, Route.class, Shape.class, Stop.class, Trip.class));
		}
			
		reader.setEntityStore(dati);
		reader.run();
			
		this.datiStatici = dati;
	}
	
	
	// Metodo che permette di caricare dei dati GTFS real-time da file .pb contenuti in un determinato URL
	public void caricaDatiRealTimeGTFS() throws Exception {
			
		URL url = new URL("https://romamobilita.it/sites/default/files/rome_rtgtfs_trip_updates_feed.pb");
			
		FeedMessage feed = FeedMessage.parseFrom(url.openStream());
		this.datiRealTime = feed;
	}
	
	
	// Metodo generico che permette di caricare tutti i dati GTFS disponibili
	public void caricaDati() throws Exception {
		
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        try {
        	
        	this.caricaDatiStaticiGTFS("staticGTFS", false);
        	
        	Future<?> caricamentoInBackground = executor.submit(() -> {
        		
        		try {
        			this.caricaDatiStaticiGTFS("staticGTFS", true);
        			this.caricaDatiRealTimeGTFS();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	});
        	
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	executor.shutdown();
        }  
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
		
		String idLinea = linea.getId().getId();
		Trip viaggio = this.datiStatici.getTripsForRoute(linea).getFirst();
		
		List<StopTime> stopTimes = this.datiStatici.getStopTimesForTrip(viaggio);
		List<Stop> listaFermate = new ArrayList<>();
		
		for (StopTime stopTime: stopTimes) {
			listaFermate.add((Stop) stopTime.getStop());
		}
		
		return listaFermate;
	}
	
// ---------------------------------------------------------------------------------------------

	// Metodo get per i dati real-time GTFS dell'istanza
	public FeedMessage getDatiRealTime() {
		return this.datiRealTime;
	}
}
