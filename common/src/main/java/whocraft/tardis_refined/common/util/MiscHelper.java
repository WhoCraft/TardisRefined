package whocraft.tardis_refined.common.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.registry.DimensionTypes;

public class MiscHelper {

    @ExpectPlatform
    public static Packet<?> spawnPacket(Entity entity) {
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }

    public static boolean shouldCancelBreaking(Level world, Player player, BlockPos pos, BlockState state) {

        if(world.dimensionTypeId() == DimensionTypes.TARDIS && world instanceof ServerLevel serverLevel){
            TardisLevelOperator data = TardisLevelOperator.get(serverLevel).get();
            if(data.getInteriorManager().isInAirlock(player)){
                return true;
            }
        }

        return state.getBlock() instanceof GlobalConsoleBlock || state.getBlock() instanceof ShellBaseBlock;
    }

}
