package damose;

import java.net.URL;
import java.io.File;
import java.util.*;
import java.awt.Shape;

import com.google.transit.realtime.GtfsRealtime.*;

import org.onebusaway.gtfs.serialization.*;
import org.onebusaway.gtfs.impl.*;
import org.onebusaway.gtfs.model.*;




public class GTFSReader {
	
	// Metodo che permette di caricare dei dati GTFS statici da file di testo contenuti in una cartella
	public GtfsRelationalDaoImpl caricaDatiStaticiGTFS(String path, boolean caricaStopTimes) throws Exception {
		
		GtfsReader reader = new GtfsReader();
		GtfsRelationalDaoImpl dati = new GtfsRelationalDaoImpl();
		
		reader.setInputLocation(new File(path));
		if (!caricaStopTimes) {
			reader.setEntityClasses(List.of(Agency.class, Calendar.class, Route.class, Shape.class, Stop.class, Trip.class));
		}
		
		reader.setEntityStore(dati);
		reader.run();
		
		return dati;
	}
	
	
	// Metodo che permette di caricare dei dati GTFS real-time da file .pb contenuti in un determinato URL
	public FeedMessage caricaDatiRealTimeGTFS(String urlGTFS) throws Exception {
		
		URL url = new URL(urlGTFS);
		
		FeedMessage feed = FeedMessage.parseFrom(url.openStream());
		return feed;
	}
}