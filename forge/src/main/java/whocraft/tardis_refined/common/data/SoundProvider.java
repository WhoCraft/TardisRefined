package whocraft.tardis_refined.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.SoundRegistry;

public class SoundProvider extends SoundDefinitionsProvider {

    public SoundProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, TardisRefined.MODID, helper);
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
    }

    public SoundDefinition basicSound(SoundEvent soundEvent) {
        @Nullable ResourceLocation soundKey = ForgeRegistries.SOUND_EVENTS.getKey(soundEvent);
        return SoundDefinition.definition().with(SoundDefinition.Sound.sound(soundKey, SoundDefinition.SoundType.SOUND)).subtitle(createSubtitle(soundKey.getPath()));
    }

    public SoundDefinition basicSound(String langKey, ResourceLocation resourceLocation) {
        return SoundDefinition.definition().with(SoundDefinition.Sound.sound(resourceLocation, SoundDefinition.SoundType.SOUND)).subtitle(createSubtitle(langKey));
    }

}
