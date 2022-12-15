package whocraft.tardis_refined.common.data;

import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.device.TerraformerBlock;
import whocraft.tardis_refined.registry.BlockRegistry;

public class ModelProviderBlock extends BlockStateProvider {

    public ModelProviderBlock(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        ResourceLocation leavesTexture = new ResourceLocation("tardis_refined:block/ars_leaves");

        /*Blocks that are rendered in code*/
        emptyBlockState(BlockRegistry.GLOBAL_CONSOLE_BLOCK.get());
        emptyBlockState(BlockRegistry.BULK_HEAD_DOOR.get());
        emptyBlockState(BlockRegistry.GLOBAL_DOOR_BLOCK.get());
        emptyBlockState(BlockRegistry.GLOBAL_SHELL_BLOCK.get());
        emptyBlockState(BlockRegistry.ROOT_SHELL_DOOR.get());
        emptyBlockState(BlockRegistry.ROOT_PLANT_BLOCK.get());
        emptyBlockState(BlockRegistry.ROOT_SHELL_BLOCK.get());
        emptyBlockState(BlockRegistry.INTERNAL_DOOR_BLOCK.get());
        emptyBlockState(BlockRegistry.ARS_EGG.get());

        terraformer(BlockRegistry.TERRAFORMER_BLOCK.get());

        /*Basic Blocks*/
        simpleBlock(BlockRegistry.ARS_LEAVES.get());
        simpleBlock(BlockRegistry.AIR_LOCK_GENERATION_BLOCK.get());

        /*Fences*/
        fenceBlock(BlockRegistry.ARS_LEAVES_FENCE.get(), leavesTexture);

        /*Slabs*/
        slabBlock(BlockRegistry.ARS_LEAVES_SLAB.get(), leavesTexture, leavesTexture);
    }


    public JsonObject emptyBlockState(Block block) {
        return getVariantBuilder(block).partialState().modelForState().modelFile(models().getExistingFile(new ResourceLocation("minecraft:block/barrier"))).addModel().toJson();
    }

    public JsonObject terraformer(Block block) {
        return getVariantBuilder(block).forAllStates(
                state -> state.getValue(TerraformerBlock.ACTIVE) ?
                        ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(TardisRefined.MODID, "block/terraformer_on"))).build() :
                        ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(TardisRefined.MODID, "block/terraformer"))).build()).toJson();

    }
}