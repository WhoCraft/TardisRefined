package whocraft.tardis_refined.common.hum;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.CodecJsonReloadListener;
import whocraft.tardis_refined.registry.SoundRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TardisHums {

    private static final CodecJsonReloadListener<HumEntry> RELOAD_LISTENER = createReloadListener();

    private static final Map<ResourceLocation, HumEntry> DEFAULT_HUMS = new HashMap<>();

    public static final HumEntry CAVE = new HumEntry(new ResourceLocation(TardisRefined.MODID, "cave"), SoundRegistry.HUM_CAVE.getId(), new ArrayList<>());
    public static final HumEntry TOYOTA = new HumEntry(new ResourceLocation(TardisRefined.MODID, "toyota"), SoundRegistry.HUM_TOYOTA.getId(), new ArrayList<>());
    public static final HumEntry CLASSIC = new HumEntry(new ResourceLocation(TardisRefined.MODID, "classic"), SoundRegistry.HUM_CLASSIC.getId(), new ArrayList<>());
    public static final HumEntry HUM_1996 = new HumEntry(new ResourceLocation(TardisRefined.MODID, "hum_1996"), SoundRegistry.HUM_1996.getId(), new ArrayList<>());

    /**
     * A factory method to create the instance of our reload listener.
     *
     * @return
     */
    private static CodecJsonReloadListener<HumEntry> createReloadListener() {
        return CodecJsonReloadListener.create(TardisRefined.MODID + "/hums", HumEntry.codec());
    }

    public static CodecJsonReloadListener<HumEntry> getReloadListener() {
        return RELOAD_LISTENER;
    }

    public static Map<ResourceLocation, HumEntry> getRegistry() {
        return RELOAD_LISTENER.getData();
    }

    public static HumEntry getHumById(ResourceLocation location) {
        return RELOAD_LISTENER.getData().getOrDefault(location, getDefaultHum());
    }

    /**
     * Creates and adds the Tardis Refined default list of HumEntries to a standalone map.
     * Can be used for datagenerators or as a fallback registry
     *
     * @return
     */
    public static Map<ResourceLocation, HumEntry> registerDefaultHums() {
        DEFAULT_HUMS.clear();
        addDefaultHum(getDefaultHum());
        addDefaultHum(CAVE);
        addDefaultHum(TOYOTA);
        addDefaultHum(HUM_1996);
        addDefaultHum(CLASSIC);
        return DEFAULT_HUMS;
    }

    private static HumEntry addDefaultHum(HumEntry hum) {
        DEFAULT_HUMS.put(hum.getIdentifier(), hum);
        return hum;
    }


    /**
     * Gets a default HumEntry added by Tardis Refined. Useful as a fallback entry.
     */
    public static HumEntry getDefaultHum() {
        ArrayList<ResourceLocation> arrayList = new ArrayList();
        arrayList.add(new ResourceLocation(TardisRefined.MODID, "interior_creaks"));
        return new HumEntry(new ResourceLocation(TardisRefined.MODID, "coral_hum"), SoundRegistry.HUM_CORAL.getId(), arrayList);
    }

    public static Map<ResourceLocation, HumEntry> getDefaultHums() {
        return DEFAULT_HUMS;
    }
}
