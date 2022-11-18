package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.tardis.interior.TardisArchitecture;
import whocraft.tardis_refined.common.tardis.interior.arctypes.DesktopTheme;

public class RootedShellBlockEntity extends ShellBaseBlockEntity{
    public RootedShellBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public DesktopTheme getAssociatedTheme() {
        return TardisArchitecture.DEFAULT_OVERGROWN_THEME;
    }
}
