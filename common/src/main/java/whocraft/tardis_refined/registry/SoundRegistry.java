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
    public static final RegistrySupplier<SoundEvent> PATTERN_MANIPULATOR = setUpSound("pattern_manipulator");

    private static RegistrySupplier<SoundEvent> setUpSound(String soundName) {
        SoundEvent sound = SoundEvent.createVariableRangeEvent(new ResourceLocation(TardisRefined.MODID, soundName));
        return SOUNDS.register(soundName, () -> sound);
    }
}
