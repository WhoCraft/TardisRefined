package whocraft.tardis_refined.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.SoundRegistry;

public class SoundProvider extends SoundDefinitionsProvider {

    public SoundProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator.getPackOutput(), TardisRefined.MODID, helper);
    }

    public static String createSubtitle(String langKey) {
        return "sound." + langKey + ".subtitle";
    }

    @Override
    public void registerSounds() {
        add(SoundRegistry.TARDIS_LAND.get(), basicSound("tardis_land", new ResourceLocation(TardisRefined.MODID, "tardis/tardis_land")));
        add(SoundRegistry.TARDIS_SINGLE_FLY.get(), basicSound("tardis_single_fly", new ResourceLocation(TardisRefined.MODID, "tardis/tardis_single_fly")));
        add(SoundRegistry.TARDIS_TAKEOFF.get(), basicSound("tardis_takeoff", new ResourceLocation(TardisRefined.MODID, "tardis/tardis_takeoff")));
        add(SoundRegistry.PATTERN_MANIPULATOR.get(), basicSound("pattern_manipulator", new ResourceLocation(TardisRefined.MODID, "gadgets/pattern_manipulator")));
        add(SoundRegistry.TIME_BLAST.get(), basicSound("time_blast", new ResourceLocation(TardisRefined.MODID, "tardis/time_blast")));
        add(SoundRegistry.TARDIS_CRASH_LAND.get(), basicSound("tardis_crash_land", new ResourceLocation(TardisRefined.MODID, "tardis/tardis_crash_land")));
        add(SoundRegistry.TARDIS_MISC_SPARKLE.get(), basicSound("tardis_misc_sparkle", new ResourceLocation(TardisRefined.MODID, "tardis/tardis_misc_sparkle")));
        add(SoundRegistry.STATIC.get(), basicSound("static", new ResourceLocation(TardisRefined.MODID, "ui/static")));
        add(SoundRegistry.DESTINATION_DING.get(), basicSound("destination_ding", new ResourceLocation(TardisRefined.MODID, "tardis/destination_ding")));
        add(SoundRegistry.ARS_HUM.get(), basicSound("ars_hum", new ResourceLocation(TardisRefined.MODID, "tardis/ars/ars_hum")));
        add(SoundRegistry.BULKHEAD_LOCKED.get(), basicSound("bulkhead_locked", new ResourceLocation(TardisRefined.MODID, "blocks/bulkhead_locked")));
        add(SoundRegistry.HUM_CORAL.get(), basicSound("hum_coral", new ResourceLocation(TardisRefined.MODID, "tardis/interior_hums/humcoral")));
        add(SoundRegistry.HUM_CAVE.get(), basicSound("hum_cave", new ResourceLocation(TardisRefined.MODID, "tardis/interior_hums/cave")));
        add(SoundRegistry.HUM_1996.get(), basicSound("hum_1996", new ResourceLocation(TardisRefined.MODID, "tardis/interior_hums/hum1996")));
        add(SoundRegistry.HUM_CLASSIC.get(), basicSound("hum_classic", new ResourceLocation(TardisRefined.MODID, "tardis/interior_hums/humclassic")));
        add(SoundRegistry.HUM_TOYOTA.get(), basicSound("hum_toyota", new ResourceLocation(TardisRefined.MODID, "tardis/interior_hums/humtoyota")));
        add(SoundRegistry.INTERIOR_CREAKS.get(), basicSound("interior_creaks", new ResourceLocation(TardisRefined.MODID, "tardis/ambience/interior_creaks_1"), new ResourceLocation(TardisRefined.MODID, "tardis/ambience/interior_creaks_2"), new ResourceLocation(TardisRefined.MODID, "tardis/ambience/interior_creaks_3")));
        add(SoundRegistry.FLIGHT_FAIL_START.get(), basicSound("flight_fail_start", new ResourceLocation(TardisRefined.MODID, "tardis/flight_fail_start")));
        add(SoundRegistry.CONSOLE_POWER_ON.get(), basicSound("console_power_on", new ResourceLocation(TardisRefined.MODID, "tardis/console_power_on")));
        add(SoundRegistry.SCREWDRIVER_SHORT.get(), basicSound("screwdriver_short", new ResourceLocation(TardisRefined.MODID, "tools/screwdriver/screwdriver_short")));
        add(SoundRegistry.SCREWDRIVER_CONNECT.get(), basicSound("screwdriver_connect", new ResourceLocation(TardisRefined.MODID, "tools/screwdriver/screwdriver_connect")));
        add(SoundRegistry.SCREWDRIVER_DISCARD.get(), basicSound("screwdriver_discard", new ResourceLocation(TardisRefined.MODID, "tools/screwdriver/screwdriver_discard")));
        add(SoundRegistry.GRAVITY_TUNNEL.get(), basicSound("gravity_tunnel", new ResourceLocation(TardisRefined.MODID, "gravity_tunnel")));
        add(SoundRegistry.INTERIOR_VOICE.get(), basicSound("interior_voice", new ResourceLocation(TardisRefined.MODID, "tardis/ambience/w1"), new ResourceLocation(TardisRefined.MODID, "tardis/ambience/w2"), new ResourceLocation(TardisRefined.MODID, "tardis/ambience/w3"), new ResourceLocation(TardisRefined.MODID, "tardis/ambience/w4")));
    }


    public SoundDefinition basicSound(String langKey, ResourceLocation resourceLocation) {
        return SoundDefinition.definition().with(SoundDefinition.Sound.sound(resourceLocation, SoundDefinition.SoundType.SOUND)).subtitle(createSubtitle(langKey));
    }

    public SoundDefinition basicSound(String langKey, ResourceLocation... resourceLocation) {
        SoundDefinition soundDefinition = SoundDefinition.definition();
        for (ResourceLocation location : resourceLocation) {
            soundDefinition.with(SoundDefinition.Sound.sound(location, SoundDefinition.SoundType.SOUND));
        }

        return soundDefinition.subtitle(createSubtitle(langKey));
    }

}
