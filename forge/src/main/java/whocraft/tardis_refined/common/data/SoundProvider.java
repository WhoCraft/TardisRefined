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
        add(TRSoundRegistry.TARDIS_LAND.get(), basicSound("tardis_land", TardisRefined.modLocation( "tardis/tardis_land")));
        add(TRSoundRegistry.TARDIS_SINGLE_FLY.get(), basicSound("tardis_single_fly", TardisRefined.modLocation( "tardis/tardis_single_fly")));
        add(TRSoundRegistry.TARDIS_TAKEOFF.get(), basicSound("tardis_takeoff", TardisRefined.modLocation( "tardis/tardis_takeoff")));
        add(TRSoundRegistry.PATTERN_MANIPULATOR.get(), basicSound("pattern_manipulator", TardisRefined.modLocation( "gadgets/pattern_manipulator")));
        add(TRSoundRegistry.TIME_BLAST.get(), basicSound("time_blast", TardisRefined.modLocation( "tardis/time_blast")));
        add(TRSoundRegistry.TARDIS_CRASH_LAND.get(), basicSound("tardis_crash_land", TardisRefined.modLocation( "tardis/tardis_crash_land")));
        add(TRSoundRegistry.TARDIS_MISC_SPARKLE.get(), basicSound("tardis_misc_sparkle", TardisRefined.modLocation( "tardis/tardis_misc_sparkle")));
        add(TRSoundRegistry.STATIC.get(), basicSound("static", TardisRefined.modLocation( "ui/static")));
        add(TRSoundRegistry.DESTINATION_DING.get(), basicSound("destination_ding", TardisRefined.modLocation( "tardis/destination_ding")));
        add(TRSoundRegistry.ARS_HUM.get(), basicSound("ars_hum", TardisRefined.modLocation( "tardis/ars/ars_hum")));
        add(TRSoundRegistry.BULKHEAD_LOCKED.get(), basicSound("bulkhead_locked", TardisRefined.modLocation( "blocks/bulkhead_locked")));
        add(TRSoundRegistry.HUM_CORAL.get(), basicSound("hum_coral", TardisRefined.modLocation( "tardis/interior_hums/humcoral")));
        add(TRSoundRegistry.HUM_CAVE.get(), basicSound("hum_cave", TardisRefined.modLocation( "tardis/interior_hums/cave")));
        add(TRSoundRegistry.HUM_VICTORIAN.get(), basicSound("hum_victorian", TardisRefined.modLocation( "tardis/interior_hums/humvictorian")));
        add(TRSoundRegistry.HUM_CLASSIC.get(), basicSound("hum_classic", TardisRefined.modLocation( "tardis/interior_hums/humclassic")));
        add(TRSoundRegistry.HUM_TOYOTA.get(), basicSound("hum_toyota", TardisRefined.modLocation( "tardis/interior_hums/humtoyota")));
        add(TRSoundRegistry.HUM_AVIATRAX.get(), basicSound("hum_aviatrax", TardisRefined.modLocation( "tardis/interior_hums/aviatrax")));
        add(TRSoundRegistry.INTERIOR_CREAKS.get(), basicSound("interior_creaks", TardisRefined.modLocation( "tardis/ambience/interior_creaks_1"), TardisRefined.modLocation( "tardis/ambience/interior_creaks_2"), TardisRefined.modLocation( "tardis/ambience/interior_creaks_3")));
        add(TRSoundRegistry.FLIGHT_FAIL_START.get(), basicSound("flight_fail_start", TardisRefined.modLocation( "tardis/flight_fail_start")));
        add(TRSoundRegistry.CONSOLE_POWER_ON.get(), basicSound("console_power_on", TardisRefined.modLocation( "tardis/console_power_on")));
        add(TRSoundRegistry.SCREWDRIVER_SHORT.get(), basicSound("screwdriver_short", TardisRefined.modLocation( "tools/screwdriver/screwdriver_short")));
        add(TRSoundRegistry.SCREWDRIVER_CONNECT.get(), basicSound("screwdriver_connect", TardisRefined.modLocation( "tools/screwdriver/screwdriver_connect")));
        add(TRSoundRegistry.SCREWDRIVER_DISCARD.get(), basicSound("screwdriver_discard", TardisRefined.modLocation( "tools/screwdriver/screwdriver_discard")));
        add(TRSoundRegistry.GRAVITY_TUNNEL.get(), basicSound("gravity_tunnel", TardisRefined.modLocation( "gravity_tunnel")));
        add(TRSoundRegistry.INTERIOR_VOICE.get(), basicSound("interior_voice", TardisRefined.modLocation( "tardis/ambience/w1"), TardisRefined.modLocation( "tardis/ambience/w2"), TardisRefined.modLocation( "tardis/ambience/w3"), TardisRefined.modLocation( "tardis/ambience/w4")));
        add(TRSoundRegistry.LOW_FUEL.get(), basicSound("low_fuel", TardisRefined.modLocation( "tardis/low_fuel")));
        add(TRSoundRegistry.ARTRON_PILLAR_ACTIVE.get(), basicSound("artron_pillar", TardisRefined.modLocation( "blocks/artron_pillar_active")));
        add(TRSoundRegistry.CORRIDOR_TELEPORTER.get(), basicSound("corridor_teleporter", TardisRefined.modLocation( "blocks/corridor_teleporter")));
        add(TRSoundRegistry.CORRIDOR_TELEPORTER_SUCCESS.get(), basicSound("corridor_teleporter_success", TardisRefined.modLocation( "blocks/corridor_teleporter_success")));
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
