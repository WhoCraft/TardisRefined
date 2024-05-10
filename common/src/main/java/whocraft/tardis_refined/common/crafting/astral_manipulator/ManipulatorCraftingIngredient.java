package whocraft.tardis_refined.common.crafting.astral_manipulator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Main ingredient object for a ManipulatorCraftingRecipe.
 * **/
public class ManipulatorCraftingIngredient {

    // Block position relative to the closest corner to 0,0,0 in world space.
    private BlockPos relativeBlockPos;

    // The block state that must exist at that position.
    private BlockState blockState;

    public static final Codec<ManipulatorCraftingIngredient> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                    BlockPos.CODEC.fieldOf("relative_pos").forGetter(recipe -> recipe.relativeBlockPos),
                    BlockState.CODEC.fieldOf("block_state").forGetter(recipe -> recipe.blockState)
            )
            .apply(builder, ManipulatorCraftingIngredient::new)
    );

    public ManipulatorCraftingIngredient(BlockPos pos, BlockState blockState) {
        this.relativeBlockPos = pos;
        this.blockState = blockState;
    }

    /**
     * Compares a ManipulatorCraftingRecipeItem to another.
     * @param compared The recipe item to compare to.
     * @return If the items are equivalent.
     * **/
    public boolean IsSameAs(ManipulatorCraftingIngredient compared) {
        if (!compared.blockState.is(this.blockState.getBlock())) {
            return false;
        }
        return this.relativeBlockPos.getX() == compared.relativeBlockPos.getX() && this.relativeBlockPos.getY() == compared.relativeBlockPos.getY() && this.relativeBlockPos.getZ() == compared.relativeBlockPos.getZ();
    }

    public BlockPos relativeBlockPos(){
        return this.relativeBlockPos;
    }

    public BlockState input(){
        return this.blockState;
    }
}

