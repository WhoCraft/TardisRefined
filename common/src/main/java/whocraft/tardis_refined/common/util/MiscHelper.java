package whocraft.tardis_refined.common.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;

public class MiscHelper {

    @ExpectPlatform
    public static Packet<?> spawnPacket(Entity entity) {
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }

    public static boolean shouldCancelBreaking(BlockState blockState) {
        return blockState.getBlock() instanceof GlobalConsoleBlock || blockState.getBlock() instanceof GlobalShellBlock;
    }

}
