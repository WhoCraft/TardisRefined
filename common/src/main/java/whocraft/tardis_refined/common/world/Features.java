package whocraft.tardis_refined.common.world;

import net.minecraft.core.registries.Registries;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.levelgen.feature.Feature;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.world.feature.NbtTemplateFeature;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class Features {

    // Minecraft 26.3 registers feature codecs (types), rather than Feature instances.
    public static final DeferredRegistry<MapCodec<? extends Feature>> FEATURES = DeferredRegistry.create(TardisRefined.MODID, Registries.FEATURE_TYPE);

    public static final RegistrySupplier<MapCodec<? extends Feature>> NBT_FEATURE = FEATURES.register("nbt_feature", () -> NbtTemplateFeature.CODEC);
}
