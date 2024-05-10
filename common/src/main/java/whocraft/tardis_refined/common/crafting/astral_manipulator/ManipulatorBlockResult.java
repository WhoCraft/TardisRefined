package whocraft.tardis_refined.common.crafting.astral_manipulator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.registry.TRManipulatorRecipeResultTypes;

public class ManipulatorBlockResult extends ManipulatorCraftingResult{

    //Creates a MapCodec. We must use MapCodecs because the "type" field in our json will need to use MapCodecs
    //to determine what fields to deserialise. In 1.20.5+ MapCodecs will also become mandatory so it's better to future-proof for it now
    public static final MapCodec<ManipulatorBlockResult> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BlockState.CODEC.fieldOf("blockstate").forGetter(ManipulatorBlockResult::recipeOutput)
            ).apply(instance, ManipulatorBlockResult::new)
    );

    // Output to be summoned when the recipe is confirmed.
    public BlockState recipeOutputBlock;

    public ManipulatorBlockResult(BlockState recipeOutputBlock) {
        this.recipeOutputBlock = recipeOutputBlock;
    }


    public BlockState recipeOutput() {
        return this.recipeOutputBlock;
    }

    @Override
    public Codec<? extends ManipulatorCraftingResult> type() {
        return TRManipulatorRecipeResultTypes.BLOCK_RESULT.get();
    }

}
