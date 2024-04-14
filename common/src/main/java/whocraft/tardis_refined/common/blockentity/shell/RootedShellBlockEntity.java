package whocraft.tardis_refined.common.blockentity.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;

public class RootedShellBlockEntity extends ShellBaseBlockEntity{
    public RootedShellBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.ROOT_SHELL.get(), blockPos, blockState);
    }

    @Override
    public DesktopTheme getAssociatedTheme() {
        return TardisDesktops.DEFAULT_OVERGROWN_THEME;
    }
}
