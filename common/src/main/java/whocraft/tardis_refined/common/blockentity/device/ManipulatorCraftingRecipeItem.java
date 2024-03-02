package whocraft.tardis_refined.common.blockentity.device;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public class ManipulatorCraftingRecipeItem {

    public BlockPos relativeBlockPos;
    public Block block;

    public ManipulatorCraftingRecipeItem(BlockPos pos, Block block) {
        this.relativeBlockPos = pos;
        this.block = block;
    }

    public boolean IsSameAs(ManipulatorCraftingRecipeItem compared) {
        if (compared.block != block) {
            return false;
        }
        return this.relativeBlockPos.getX() == compared.relativeBlockPos.getX() && this.relativeBlockPos.getY() == compared.relativeBlockPos.getY() && this.relativeBlockPos.getZ() == compared.relativeBlockPos.getZ();
    }
}

