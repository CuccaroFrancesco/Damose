package damose;

import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;

public class TilesetManager {

    public static void updateMap(Mappa mapPanel, int selectedIndex) {

        TileFactoryInfo newInfo;

        switch (selectedIndex) {
            case 0:
                newInfo = new OSMTileFactoryInfo("Mappa", "https://a.tile.openstreetmap.fr/hot/");
                break;

            case 1:
                newInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
                break;

            case 2:
                newInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
                break;

            default:
                newInfo = new OSMTileFactoryInfo("Mappa", "https://a.tile.openstreetmap.fr/hot/");
                break;
        }

        DefaultTileFactory newTileFactory = new DefaultTileFactory(newInfo);
        
        // Mantengo la stessa cache utilizzata già attualmente
        newTileFactory.setLocalCache(mapPanel.getLocalCache());
        newTileFactory.setThreadPoolSize(8);

        mapPanel.updateMap(newTileFactory);
    }
}

