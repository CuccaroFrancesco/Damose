package damose;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.*;
import org.jxmapviewer.viewer.GeoPosition;



public class VeicoliPainter implements Painter<JXMapViewer> {

    private List<VeicoliWaypoint> veicoliDaDisegnare;


    // Costruttore del veicoliPainter
    public VeicoliPainter(List<VeicoliWaypoint> veicoli) {
        this.veicoliDaDisegnare = veicoli;
    }


// ---------------------------------------------------------------------------------------------


    // Metodo che gestisce il disegno delle varie fermate sulla mappa
    @Override
    public void paint(Graphics2D g, JXMapViewer mappa, int w, int h) {

        for (VeicoliWaypoint veicolo : veicoliDaDisegnare) {

            Graphics2D g2 = (Graphics2D) g.create();

            GeoPosition posizioneVeicolo = veicolo.getPosition();
            Point2D point = mappa.getTileFactory().geoToPixel(posizioneVeicolo, mappa.getZoom());
            Point viewportPosition = mappa.getViewportBounds().getLocation();

            int x = (int) (point.getX() - viewportPosition.getX());
            int y = (int) (point.getY() - viewportPosition.getY());

            Image icona = veicolo.getWaypointIcon();
            double angolo = veicolo.getAngolo();

            g2.translate(x, y);
            g2.rotate(Math.toRadians(angolo));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.drawImage(icona, -icona.getWidth(null) / 2, -icona.getHeight(null) / 2, null);

            g2.dispose();
        }
    }


// ---------------------------------------------------------------------------------------------


    // Metodo set per impostare i veicoli da disegnare
    public void setVeicoliDaDisegnare(List<VeicoliWaypoint> veicoli) {
        this.veicoliDaDisegnare = veicoli;
    }
}
