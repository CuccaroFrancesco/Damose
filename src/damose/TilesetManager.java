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
            	newInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
                break;

            case 1:
                newInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
                break;

            case 2:
                newInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
                break;

            default:
            	newInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
                break;
        }

        DefaultTileFactory newTileFactory = new DefaultTileFactory(newInfo);
        
        newTileFactory.setLocalCache(mapPanel.getLocalCache());
        newTileFactory.setThreadPoolSize(8);

        mapPanel.updateMap(newTileFactory);
    }
}

