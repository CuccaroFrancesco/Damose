package mappa;

import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;

public class TilesetManager {

    // Metodo per aggiornare la mappa in base alla selezione
    public static void updateMap(Mappa mapPanel, int selectedIndex) {
        TileFactoryInfo newInfo;
        switch (selectedIndex) {
            case 0:  // Normale
                newInfo = new OSMTileFactoryInfo("Mappa", "https://a.tile.openstreetmap.fr/hot/");
                break;
            case 1:  // Satellite
                newInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
                break;
            case 2:  // Mista
                newInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
                break;
            default: // Normale
                newInfo = new OSMTileFactoryInfo("Mappa", "https://a.tile.openstreetmap.fr/hot/");
                break;
        }

        DefaultTileFactory newTileFactory = new DefaultTileFactory(newInfo);
        mapPanel.updateMap(newTileFactory);
        
    }
}
