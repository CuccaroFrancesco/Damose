package damose;

import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import com.google.transit.realtime.GtfsRealtime.*;
import org.onebusaway.gtfs.serialization.*;
import org.onebusaway.gtfs.impl.*;

public class GTFSReader {
	
	// Metodo che permette di caricare dei dati GTFS statici da file di testo contenuti in una cartella
	public static GtfsRelationalDaoImpl caricaDatiStaticiGTFS(String path) throws Exception {
		
		GtfsReader reader = new GtfsReader();
		GtfsRelationalDaoImpl dati = new GtfsRelationalDaoImpl();
		
		reader.setInputLocation(new File("src/damose/staticGTFS"));
		reader.setEntityStore(dati);
		reader.run();
		
		return dati;
	}
	
	
	// Metodo che permette di caricare dei dati GTFS real-time da file .pb contenuti in un determinato URL
	public static FeedMessage caricaDatiRealTimeGTFS(String urlGTFS) throws Exception {
		
		URL url = new URL(urlGTFS);
		
		FeedMessage feed = FeedMessage.parseFrom(url.openStream());
		return feed;
	}	
}