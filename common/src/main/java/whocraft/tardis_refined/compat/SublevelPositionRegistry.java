package whocraft.tardis_refined.compat;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.compat.sable.SableSublevelAccessor;
import whocraft.tardis_refined.registry.DeferredRegister;
import whocraft.tardis_refined.registry.RegistryBuilder;

public class SublevelPositionRegistry {
    
    public static final ResourceKey<Registry<MapCodec<? extends SublevelAccessor.LoadablePositionReference>>> KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "sublevel_position_reference"));
    
    public static final Registry<MapCodec<? extends SublevelAccessor.LoadablePositionReference>> REGISTRY = RegistryBuilder.create(KEY).defaultKey(
            ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "dummy")
    ).build();

    public static final DeferredRegister<MapCodec<? extends SublevelAccessor.LoadablePositionReference>> DEFERRED = DeferredRegister.create(TardisRefined.MODID, KEY);
    
    static {
        if (ModCompatChecker.sable()) {
            DEFERRED.register("sable", () -> SableSublevelAccessor.LoadableSablePosition.CODEC);
        }
    }
}
