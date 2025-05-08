package damose;

import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;

public class Ricerca {
	
	private DatiGTFS dati;
	private StopPanel stopPanel;
    private LineaPanel lineaPanel;
    private Mappa mappa;
	
	public void effettuaRicerca(String input, DatiGTFS dati, StopPanel stopPanel, LineaPanel lineaPanel, Mappa mappa) {
		this.dati = dati;
		this.stopPanel = stopPanel;
		this.lineaPanel = lineaPanel;
		this.mappa = mappa;
		checkFermata(input);
		checkLinea(input);
		
		
	}
	
	// Funzione per trovare una fermata
	public void checkFermata(String input) {
		
		Stop fermata = dati.cercaFermata(input.toUpperCase());
		if (fermata != null) {
			stopPanel.creaPannelloFermata(fermata);
			mappa.centraMappa(fermata.getLon(), fermata.getLat(), 2);
			lineaPanel.setVisible(false);
		}
	}
	
	// Funzione per trovare una linea
	public void checkLinea(String input) {
		
		Route linea = dati.cercaRoute(input.toUpperCase());
		if (linea != null) {
			lineaPanel.creaPannelloLinea(linea, dati);
			stopPanel.setVisible(false);
			LineaPainter.costruisciLineaDaDisegnare(linea, mappa, dati);
		}
	}
}
