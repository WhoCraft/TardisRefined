package whocraft.tardis_refined.mixin.forge;

import net.minecraftforge.data.loading.DatagenModLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;

// Necessary workaround for Forge datagen to shut down when it's done.
@Mixin(value = DatagenModLoader.class, priority = 1100, remap = false)
public class DatagenModLoaderMixin {

    @Inject(method = "begin", at = @At("TAIL"))
    private static void forceKillDatagenWhenComplete(
            Set<String> mods, Path path, Collection<Path> inputs,
            Collection<Path> existingPacks, Set<String> existingMods,
            boolean serverGenerators, boolean clientGenerators, boolean devToolGenerators,
            boolean reportsGenerator, boolean structureValidator, boolean flat,
            String assetIndex, File assetsDir, CallbackInfo ci
    ) {
        var prop = System.getProperty("tardis_refined.force_stop_datagen");
        if (prop != null && prop.toLowerCase(Locale.ROOT).equals("true")) {
            System.exit(0);
        }
    }

}
