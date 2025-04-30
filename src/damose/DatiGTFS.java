/**********************************************************************************

Classe "DatiGTFS" per oggetti destinati a conservare dati GTFS (statici e real-time)
come attributi. 

METODI:
- caricaDatiStaticiGTFS(), carica i dati statici e li assegna come attributo all'istanza;
- caricaDatiRealTimeGTFS(), carica i dati real-time e li assegna come attributo all'istanza;
 
- getDatiStatici(), restituisce i dati statici attribuiti all'istanza;
- getLinee(), restituisce esclusivamente i dati statici relativi alle linee;
- getFermate(), restituisce esclusivamente i dati statici relativi alle fermate;

- getDatiRealTime(), restituisce i dati real-time attribuiti all'istanza.

**********************************************************************************/

package damose;

import java.awt.Shape;
import java.io.File;
import java.net.URL;
import java.util.*;

import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;
import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.Trip;
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
	public void caricaDatiRealTimeGTFS(String urlGTFS) throws Exception {
			
		URL url = new URL(urlGTFS);
			
		FeedMessage feed = FeedMessage.parseFrom(url.openStream());
		this.datiRealTime = feed;
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
	
// ---------------------------------------------------------------------------------------------

	// Metodo get per i dati real-time GTFS dell'istanza
	public FeedMessage getDatiRealTime() {
		return this.datiRealTime;
	}
}
