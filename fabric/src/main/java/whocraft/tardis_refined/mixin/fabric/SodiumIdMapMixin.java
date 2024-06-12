package whocraft.tardis_refined.mixin.fabric;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.irisshaders.iris.shaderpack.IdMap;
import net.irisshaders.iris.shaderpack.materialmap.BlockEntry;
import net.irisshaders.iris.shaderpack.materialmap.NamespacedId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.TardisRefined;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(IdMap.class)
public class SodiumIdMapMixin {

    @Shadow
    private Int2ObjectMap<List<BlockEntry>> blockPropertiesMap;

    @Inject(method = "getBlockProperties()Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;", at = @At("HEAD"), cancellable = true, remap = false)
    private void restrict(CallbackInfoReturnable<Int2ObjectMap<List<BlockEntry>>> cir) {
        BlockEntry blockEntry = new BlockEntry(new NamespacedId(TardisRefined.MODID, "tardis_console"), Collections.emptyMap());

        Int2ObjectMap<List<BlockEntry>> modifiableMap = new Int2ObjectOpenHashMap<>(blockPropertiesMap);

        List<BlockEntry> entries = modifiableMap.get(60008);

        if (entries != null) {
            List<BlockEntry> modifiableEntries = new ArrayList<>(entries);
            modifiableEntries.add(blockEntry);
            modifiableMap.put(60008, Collections.unmodifiableList(modifiableEntries));
        } else {
            modifiableMap.put(60008, Collections.singletonList(blockEntry));
        }

        blockPropertiesMap = Int2ObjectMaps.unmodifiable(modifiableMap);

        cir.setReturnValue(blockPropertiesMap);
    }
}


