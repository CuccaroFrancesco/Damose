/**********************************************************************************

Classe "TilesetManager" che contiene il metodo statico che gestisce il cambiamento
di "tipo" della mappa visualizzata.

METODI:
- updateMap(), gestisce il cambiamento di "tipo" della mappa visualizzata, offrendo
  tre opzioni (normale, satellitare, mista).

**********************************************************************************/

package damose;

import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.*;



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

