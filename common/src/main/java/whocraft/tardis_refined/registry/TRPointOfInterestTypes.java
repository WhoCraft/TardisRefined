package whocraft.tardis_refined.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;

public class TRPointOfInterestTypes {

    public static void init(){};

    public static final ResourceKey<PoiType> PILOT = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, new ResourceLocation(TardisRefined.MODID, "console_unit"));


    public static final DeferredRegistry<PoiType> POIS = DeferredRegistry.create(TardisRefined.MODID, Registries.POINT_OF_INTEREST_TYPE);


    public static final RegistrySupplier<PoiType> PILOT_POI = POIS.register("console_unit", () -> registerPointOfInterest(1, 1, TRBlockRegistry.GLOBAL_CONSOLE_BLOCK.get()));



    private static PoiType registerPointOfInterest(int maxTickets, int validRange, Block... blocks) {

        final ImmutableSet.Builder<BlockState> builder = ImmutableSet.builder();

        for (Block block : blocks) {
            builder.addAll(block.getStateDefinition().getPossibleStates());
        }

        return new PoiType(builder.build(), maxTickets, validRange);
    }

}
