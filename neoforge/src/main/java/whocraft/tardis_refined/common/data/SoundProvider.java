package whocraft.tardis_refined.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.TRSoundRegistry;

public class SoundProvider extends SoundDefinitionsProvider {

    public SoundProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator.getPackOutput(), TardisRefined.MODID, helper);
    }

    public static String createSubtitle(String langKey) {
        return "sound." + langKey + ".subtitle";
    }

    @Override
    public void registerSounds() {
        add(TRSoundRegistry.TARDIS_LAND.get(), basicSound("tardis_land", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/tardis_land")));
        add(TRSoundRegistry.TARDIS_SINGLE_FLY.get(), basicSound("tardis_single_fly", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/tardis_single_fly")));
        add(TRSoundRegistry.TARDIS_TAKEOFF.get(), basicSound("tardis_takeoff", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/tardis_takeoff")));
        add(TRSoundRegistry.PATTERN_MANIPULATOR.get(), basicSound("pattern_manipulator", ResourceLocation.tryBuild(TardisRefined.MODID, "gadgets/pattern_manipulator")));
        add(TRSoundRegistry.TIME_BLAST.get(), basicSound("time_blast", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/time_blast")));
        add(TRSoundRegistry.TARDIS_CRASH_LAND.get(), basicSound("tardis_crash_land", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/tardis_crash_land")));
        add(TRSoundRegistry.TARDIS_MISC_SPARKLE.get(), basicSound("tardis_misc_sparkle", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/tardis_misc_sparkle")));
        add(TRSoundRegistry.STATIC.get(), basicSound("static", ResourceLocation.tryBuild(TardisRefined.MODID, "ui/static")));
        add(TRSoundRegistry.DESTINATION_DING.get(), basicSound("destination_ding", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/destination_ding")));
        add(TRSoundRegistry.ARS_HUM.get(), basicSound("ars_hum", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/ars/ars_hum")));
        add(TRSoundRegistry.BULKHEAD_LOCKED.get(), basicSound("bulkhead_locked", ResourceLocation.tryBuild(TardisRefined.MODID, "blocks/bulkhead_locked")));
        add(TRSoundRegistry.HUM_CORAL.get(), basicSound("hum_coral", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/interior_hums/hum_coral")));
        add(TRSoundRegistry.HUM_CAVE.get(), basicSound("hum_cave", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/interior_hums/hum_cave")));
        add(TRSoundRegistry.HUM_VICTORIAN.get(), basicSound("hum_victorian", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/interior_hums/hum_victorian")));
        add(TRSoundRegistry.HUM_CLASSIC.get(), basicSound("hum_classic", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/interior_hums/hum_classic")));
        add(TRSoundRegistry.HUM_TOYOTA.get(), basicSound("hum_toyota", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/interior_hums/hum_toyota")));
        add(TRSoundRegistry.HUM_AVIATRAX.get(), basicSound("hum_aviatrax", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/interior_hums/hum_aviatrax")));
        add(TRSoundRegistry.HUM_COPPER.get(), basicSound("hum_copper", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/interior_hums/hum_copper")));
        add(TRSoundRegistry.INTERIOR_CREAKS.get(), basicSound("interior_creaks", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/ambience/interior_creaks_1"), ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/ambience/interior_creaks_2"), ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/ambience/interior_creaks_3")));
        add(TRSoundRegistry.FLIGHT_FAIL_START.get(), basicSound("flight_fail_start", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/flight_fail_start")));
        add(TRSoundRegistry.CONSOLE_POWER_ON.get(), basicSound("console_power_on", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/console_power_on")));
        add(TRSoundRegistry.ALARM.get(), basicSound("alarm", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/alarm")));
        add(TRSoundRegistry.SCREWDRIVER_SHORT.get(), basicSound("screwdriver_short", ResourceLocation.tryBuild(TardisRefined.MODID, "tools/screwdriver/screwdriver_short")));
        add(TRSoundRegistry.SCREWDRIVER_CONNECT.get(), basicSound("screwdriver_connect", ResourceLocation.tryBuild(TardisRefined.MODID, "tools/screwdriver/screwdriver_connect")));
        add(TRSoundRegistry.SCREWDRIVER_DISCARD.get(), basicSound("screwdriver_discard", ResourceLocation.tryBuild(TardisRefined.MODID, "tools/screwdriver/screwdriver_discard")));
        add(TRSoundRegistry.GRAVITY_TUNNEL.get(), basicSound("gravity_tunnel", ResourceLocation.tryBuild(TardisRefined.MODID, "gravity_tunnel")));
        add(TRSoundRegistry.INTERIOR_VOICE.get(), basicSound("interior_voice", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/ambience/w1"), ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/ambience/w2"), ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/ambience/w3"), ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/ambience/w4")));
        add(TRSoundRegistry.LOW_FUEL.get(), basicSound("low_fuel", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/low_fuel")));
        add(TRSoundRegistry.ARTRON_PILLAR_ACTIVE.get(), basicSound("artron_pillar", ResourceLocation.tryBuild(TardisRefined.MODID, "blocks/artron_pillar_active")));
        add(TRSoundRegistry.CORRIDOR_TELEPORTER.get(), basicSound("corridor_teleporter", ResourceLocation.tryBuild(TardisRefined.MODID, "blocks/corridor_teleporter")));
        add(TRSoundRegistry.CORRIDOR_TELEPORTER_SUCCESS.get(), basicSound("corridor_teleporter_success", ResourceLocation.tryBuild(TardisRefined.MODID, "blocks/corridor_teleporter_success")));
        add(TRSoundRegistry.VORTEX.get(), basicSound("vortex", ResourceLocation.tryBuild(TardisRefined.MODID, "tardis/vortex")));
        add(TRSoundRegistry.MALLET.get(), basicSound("mallet", ResourceLocation.tryBuild(TardisRefined.MODID, "tools/mallet")));


        add(TRSoundRegistry.CLOISTER_BELL.get(),
                basicSound("cloister_bell", SoundDefinition.Sound.sound(ResourceLocation.tryBuild("block/bell/resonate"), SoundDefinition.SoundType.SOUND).pitch(0.85),
                        SoundDefinition.Sound.sound(ResourceLocation.tryBuild("block/bell/resonate"), SoundDefinition.SoundType.SOUND).pitch(0.9)
                ));
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

    public SoundDefinition basicSound(String langKey, final SoundDefinition.Sound... resourceLocation) {
        SoundDefinition soundDefinition = SoundDefinition.definition();
        for (SoundDefinition.Sound sound : resourceLocation) {
            soundDefinition.with(sound);
        }

        return soundDefinition.subtitle(createSubtitle(langKey));
    }

}
