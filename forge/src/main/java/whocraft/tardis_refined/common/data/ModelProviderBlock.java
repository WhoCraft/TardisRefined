package whocraft.tardis_refined.common.data;

import com.google.gson.JsonObject;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.device.ConsoleConfigurationBlock;
import whocraft.tardis_refined.common.block.device.TerraformerBlock;
import whocraft.tardis_refined.registry.BlockRegistry;

public class ModelProviderBlock extends BlockStateProvider {

    public ModelProviderBlock(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        ResourceLocation leavesTexture = new ResourceLocation("tardis_refined:block/ars_leaves");
        ResourceLocation growthStoneTexture = new ResourceLocation("tardis_refined:block/growth_stone");

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

        threeDeeRotating(BlockRegistry.LANDING_PAD.get(), new ResourceLocation(TardisRefined.MODID, "block/landing_pad"));

        terraformer(BlockRegistry.TERRAFORMER_BLOCK.get());

        /*Basic Blocks*/
        simpleBlock(BlockRegistry.ARS_LEAVES.get());
        simpleBlock(BlockRegistry.AIR_LOCK_GENERATION_BLOCK.get());
        simpleBlock(BlockRegistry.GROWTH_STONE.get());
        simpleBlock(BlockRegistry.HARDENED_GROWTH_STONE.get());

        /*Fences*/
        fenceBlock(BlockRegistry.ARS_LEAVES_FENCE.get(), leavesTexture);

        /*Slabs*/
        slabBlock(BlockRegistry.ARS_LEAVES_SLAB.get(), leavesTexture, leavesTexture);


        // To be readded
//        buttonBlock(BlockRegistry.GROWTH_STONE_BUTTON.get(), growthStoneTexture);
//        pressurePlateBlock(BlockRegistry.GROWTH_STONE_PRESSURE_PLATE.get(), growthStoneTexture);
//        wallBlock(BlockRegistry.GROWTH_STONE_WALL.get(), growthStoneTexture);
//        stairsBlock(BlockRegistry.GROWTH_STONE_STAIRS.get(), growthStoneTexture);
//        slabBlock(BlockRegistry.GROWTH_STONE_SLAB.get(), growthStoneTexture, growthStoneTexture);

        threeDeeRotating(BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get(), new ResourceLocation(TardisRefined.MODID, "block/console_configuration"));
    }



    public JsonObject emptyBlockState(Block block) {
        return getVariantBuilder(block).partialState().modelForState().modelFile(models().getExistingFile(new ResourceLocation("minecraft:block/barrier"))).addModel().toJson();
    }

    public JsonObject customLocation(Block block, ResourceLocation resourceLocation) {
        return getVariantBuilder(block).partialState().modelForState().modelFile(models().getExistingFile(resourceLocation)).addModel().toJson();
    }

    public JsonObject terraformer(Block block) {
        return getVariantBuilder(block).forAllStates(
                state -> state.getValue(TerraformerBlock.ACTIVE) ?
                        ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(TardisRefined.MODID, "block/terraformer_on"))).build() :
                        ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(TardisRefined.MODID, "block/terraformer"))).build()).toJson();

    }

    // Paul McGann is...
    public JsonObject threeDeeRotating(Block block, ResourceLocation location) {
        return getVariantBuilder(block).forAllStates(
                state ->  ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(location)).rotationY((int) state.getValue(HorizontalDirectionalBlock.FACING).toYRot()).build()).toJson();

    }
}