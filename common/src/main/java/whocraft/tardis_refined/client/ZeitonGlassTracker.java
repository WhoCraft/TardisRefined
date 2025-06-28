package whocraft.tardis_refined.client;

import whocraft.tardis_refined.common.blockentity.life.ZeitonGlassBlockEntity;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ZeitonGlassTracker {
    public static final Set<ZeitonGlassBlockEntity> loadedGlass = ConcurrentHashMap.newKeySet();

    public static void onLoad(ZeitonGlassBlockEntity entity) {
        loadedGlass.add(entity);
    }

    public static void onUnload(ZeitonGlassBlockEntity entity) {
        loadedGlass.remove(entity);
    }

    public static void clear() {
        loadedGlass.clear();
    }
}
