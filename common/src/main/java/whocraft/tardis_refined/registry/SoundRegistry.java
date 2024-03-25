package whocraft.tardis_refined.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import whocraft.tardis_refined.TardisRefined;

public class SoundRegistry {
    public static final DeferredRegistry<SoundEvent> SOUNDS = DeferredRegistry.create(TardisRefined.MODID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> TARDIS_TAKEOFF = setUpSound("tardis_takeoff");
    public static final RegistrySupplier<SoundEvent> TARDIS_LAND = setUpSound("tardis_land");
    public static final RegistrySupplier<SoundEvent> TARDIS_SINGLE_FLY = setUpSound("tardis_single_fly");
    public static final RegistrySupplier<SoundEvent> TIME_BLAST = setUpSound("time_blast");
    public static final RegistrySupplier<SoundEvent> TARDIS_CRASH_LAND = setUpSound("tardis_crash_land");
    public static final RegistrySupplier<SoundEvent> PATTERN_MANIPULATOR = setUpSound("pattern_manipulator");
    public static final RegistrySupplier<SoundEvent> TARDIS_MISC_SPARKLE = setUpSound("tardis_misc_sparkle");
    public static final RegistrySupplier<SoundEvent> STATIC = setUpSound("static");
    public static final RegistrySupplier<SoundEvent> DESTINATION_DING = setUpSound("destination_ding");
    public static final RegistrySupplier<SoundEvent> ARS_HUM = setUpSound("ars_hum");
    public static final RegistrySupplier<SoundEvent> BULKHEAD_LOCKED = setUpSound("bulkhead_locked");
    public static final RegistrySupplier<SoundEvent> HUM_CORAL = setUpSound("hum_coral");
    public static final RegistrySupplier<SoundEvent> INTERIOR_CREAKS = setUpSound("interior_creaks");
    public static final RegistrySupplier<SoundEvent> FLIGHT_FAIL_START = setUpSound("flight_fail_start");
    public static final RegistrySupplier<SoundEvent> CONSOLE_POWER_ON = setUpSound("console_power_on");


    // Screwdriver
    public static final RegistrySupplier<SoundEvent> SCREWDRIVER_SHORT = setUpSound("screwdriver_short");
    public static final RegistrySupplier<SoundEvent> SCREWDRIVER_CONNECT = setUpSound("screwdriver_connect");
    public static final RegistrySupplier<SoundEvent> SCREWDRIVER_DISCARD = setUpSound("screwdriver_discard");
    public static final RegistrySupplier<SoundEvent> GRAVITY_TUNNEL = setUpSound("gravity_tunnel");

    private static RegistrySupplier<SoundEvent> setUpSound(String soundName) {
        SoundEvent sound = SoundEvent.createVariableRangeEvent(new ResourceLocation(TardisRefined.MODID, soundName));
        return SOUNDS.register(soundName, () -> sound);
    }
}
