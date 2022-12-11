package whocraft.tardis_refined.client.screen;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BlankContainer extends AbstractContainerMenu {

    public BlankContainer() {
        super(null, 999);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return false;
    }
}

