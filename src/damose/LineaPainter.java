/**********************************************************************************

Ispirato dal file RoutePainter.java fornito come esempio a questo link:
https://github.com/msteiger/jxmapviewer2/blob/master/examples/src/sample2_waypoints/RoutePainter.java

**********************************************************************************/

package damose;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.Trip;
import org.jxmapviewer.painter.*;



public class LineaPainter implements Painter<JXMapViewer> {
	
	private Color colore;
	private boolean antiAlias = true;
	private List<GeoPosition> linea;
	
	
	// Costruttore dell'istanza della classe LineaPainter
	public LineaPainter(List<GeoPosition> linea) {
		
		this.linea = new ArrayList<GeoPosition>(linea);
	}
	
	
	// Metodo utilizzato per disegnare una singola linea a partire dalla lista di punti fornita all'istanza
	private void disegnaLinea(Graphics2D g, JXMapViewer mappa) {
        
		int precedenteX = 0;
        int precedenteY = 0;
        boolean isPrimo = true;

        for (GeoPosition gp : linea) {
        	
            Point2D punto = mappa.getTileFactory().geoToPixel(gp, mappa.getZoom());

            if (isPrimo) {
                isPrimo = false;
            } else {
                g.drawLine(precedenteX, precedenteY, (int) punto.getX(), (int) punto.getY());
            }

            precedenteX = (int) punto.getX();
            precedenteY = (int) punto.getY();
        }
    }
	
	
	// Metodo che gestisce i vari passaggi del disegno di una linea sulla mappa
	@Override
	public void paint(Graphics2D g, JXMapViewer mappa, int w, int h) {
		
        g = (Graphics2D) g.create();

        Rectangle rect = mappa.getViewportBounds();
        g.translate(-rect.x, -rect.y);

        if (antiAlias) {
        	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(5));

        this.disegnaLinea(g, mappa);

        g.setColor(colore);
        g.setStroke(new BasicStroke(4));

        this.disegnaLinea(g, mappa);

        g.dispose();
    }
	
	
	// Metodo che restituisce la lista di GeoPosition corrispondenti alla shape di una determinata linea
	public static void costruisciLineaDaDisegnare(Route lineaTrovata, Mappa mapPanel, DatiGTFS dati) {
	 		
		List<GeoPosition> puntiDaDisegnare = new ArrayList<>();
	 		
	 	Trip viaggio = dati.getDatiStatici().getTripsForRoute(lineaTrovata).getFirst();
	 			
	 	List<ShapePoint> shapePoints = new ArrayList<>(dati.getDatiStatici().getShapePointsForShapeId(viaggio.getShapeId()));
	 	for (ShapePoint sp : shapePoints) {
	 		puntiDaDisegnare.add(new GeoPosition(sp.getLat(), sp.getLon()));
	 	}

	 		
	 	if (puntiDaDisegnare.isEmpty()) {
	 	    System.out.println("Dati non ancora disponibili.");
	 	    return;
	 	}

	 	Color colore = null;

	 	switch (lineaTrovata.getType()) {
	 	    case 0:
	 	        colore = new Color(1, 140, 146);
	 	        break;

	 	    case 1:
	 	        Map<String, Color> coloriLinea = Map.of(
	 	            "MEA", new Color(242, 109, 27),
	 	            "MEB", new Color(1, 112, 187),
	 	            "MEB1", new Color(1, 112, 187),
	 	            "MEC", new Color(1, 135, 81)
	 	        );
	 	        colore = coloriLinea.get(lineaTrovata.getShortName());
	 	        break;

	 	    case 2:
	 	        colore = new Color(30, 27, 9);
	 	        break;

	 	    case 3:
	 	        colore = new Color(181, 1, 1);
	 	        break;
	 	}

	 	if (colore != null) {
	 	    mapPanel.getPainterLinea().setLineaDaDisegnare(puntiDaDisegnare, colore);
	 	} else {
	 	    System.out.println("Colore non definito per la linea: " + lineaTrovata.getShortName());
	 	}

    		
    		int centro = puntiDaDisegnare.size() / 2;
    		GeoPosition puntoCentrale = puntiDaDisegnare.get(centro);
    		mapPanel.centraMappa(puntoCentrale.getLongitude(), puntoCentrale.getLatitude(), 4);
    			
        	mapPanel.repaint();
    	}
	
	
	// Metodo set per impostare la linea da disegnare
	public void setLineaDaDisegnare(List<GeoPosition> linea, Color colore) {
		this.linea = linea;
		this.colore = colore;
	}
}