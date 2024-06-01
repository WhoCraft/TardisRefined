package whocraft.tardis_refined.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;

public class TRPointOfInterestTypes {

    public static void init(){};

    public static final ResourceKey<PoiType> PILOT = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, new ResourceLocation(TardisRefined.MODID, "pilot"));

    public static final PoiType PILOT_POI = registerPointOfInterest( PILOT, 5, 25, TRBlockRegistry.GLOBAL_CONSOLE_BLOCK.get());


    private static PoiType registerPointOfInterest(ResourceKey<PoiType> key, int maxTickets, int validRange, Block... blocks) {

        final ImmutableSet.Builder<BlockState> builder = ImmutableSet.builder();

        for (Block block : blocks) {
            builder.addAll(block.getStateDefinition().getPossibleStates());
        }

        return PoiTypes.register(BuiltInRegistries.POINT_OF_INTEREST_TYPE, key, builder.build(), maxTickets, validRange);
    }

}
