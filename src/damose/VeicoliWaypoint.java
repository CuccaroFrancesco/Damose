/**********************************************************************************

 Ispirato dal file MyWaypoint.java fornito come esempio a questo link:
 https://github.com/msteiger/jxmapviewer2/blob/master/examples/src/sample4_fancy/MyWaypoint.java

 **********************************************************************************/

package damose;

import java.awt.*;

import org.jxmapviewer.viewer.*;

import com.google.transit.realtime.GtfsRealtime.*;

import javax.swing.*;


public class VeicoliWaypoint extends DefaultWaypoint {

    private VehiclePosition vehiclePosition;
    private double angolo;
    private Image waypointIcon;
    private DatiGTFS dati;


    // Costruttore di un veicoliWaypoint
    public VeicoliWaypoint(VehiclePosition vehiclePosition, GeoPosition coordinate, DatiGTFS dati) {

        super(coordinate);
        this.vehiclePosition = vehiclePosition;
        this.dati = dati;

        this.angolo = vehiclePosition.getPosition().getBearing();

        switch (dati.cercaLineaByID(vehiclePosition.getTrip().getRouteId()).getType()) {
            case 0:
                ImageIcon iconTram = new ImageIcon("src/resources/assets/tram_waypoint.png");
                this.waypointIcon = iconTram.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
                break;

            case 3:
                ImageIcon iconBus = new ImageIcon("src/resources/assets/bus_waypoint.png");
                this.waypointIcon = iconBus.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
                break;

            default:
                break;
        }
    }


// ---------------------------------------------------------------------------------------------


    // Metodo get per il VehiclePosition associato al veicoliWaypoint
    public VehiclePosition getVehiclePosition() {
        return this.vehiclePosition;
    }


    // Metodo get per l'angolazione del veicoliWaypoint
    public double getAngolo() {
        return this.angolo;
    }


    // Metodo get per l'icona del veicoliWaypoint
    public Image getWaypointIcon() {
        return this.waypointIcon;
    }
}
