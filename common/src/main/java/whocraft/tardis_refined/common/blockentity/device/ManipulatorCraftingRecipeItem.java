package whocraft.tardis_refined.common.blockentity.device;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

/**
 * Ingredient block for a ManipulatorCraftingRecipe.
 * **/
public class ManipulatorCraftingRecipeItem {

    // Block position relative to the closest corner to 0,0,0 in world space.
    public BlockPos relativeBlockPos;

    // The block that must exist at that position.
    public Block block;

    public ManipulatorCraftingRecipeItem(BlockPos pos, Block block) {
        this.relativeBlockPos = pos;
        this.block = block;
    }

    /**
     * Compares a ManipulatorCraftingRecipeItem to another.
     * @param compared The recipe item to compare to.
     * @return If the items are equivalent.
     * **/
    public boolean IsSameAs(ManipulatorCraftingRecipeItem compared) {
        if (compared.block != block) {
            return false;
        }
        return this.relativeBlockPos.getX() == compared.relativeBlockPos.getX() && this.relativeBlockPos.getY() == compared.relativeBlockPos.getY() && this.relativeBlockPos.getZ() == compared.relativeBlockPos.getZ();
    }
}

