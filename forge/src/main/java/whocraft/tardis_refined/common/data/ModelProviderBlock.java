package whocraft.tardis_refined.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.BlockModelGenerators;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import whocraft.tardis_refined.TardisRefined;

public class ModelProviderBlock extends BlockModelGenerators {

    public ModelProviderBlock(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

/*        ResourceLocation leavesTexture = new ResourceLocation("tardis_refined:block/ars_leaves");
        ResourceLocation growthStoneTexture = new ResourceLocation("tardis_refined:block/growth_stone");

        *//*Blocks that are rendered in code*//*
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
        threeDeeRotating(BlockRegistry.FLIGHT_DETECTOR.get(), new ResourceLocation(TardisRefined.MODID, "block/flight_detector"));

        terraformer(BlockRegistry.TERRAFORMER_BLOCK.get());

        *//*Basic Blocks*//*
        singleTexture(BlockRegistry.ARS_LEAVES.get());
        singleTexture(BlockRegistry.AIR_LOCK_GENERATION_BLOCK.get());
        singleTexture(BlockRegistry.GROWTH_STONE.get());
        singleTexture(BlockRegistry.HARDENED_GROWTH_STONE.get());

        *//*Fences*//*
        fencePost(BlockRegistry.ARS_LEAVES_FENCE.get(), leavesTexture);

        *//*Slabs*//*
        slab(BlockRegistry.ARS_LEAVES_SLAB.get(), leavesTexture, leavesTexture);


        threeDeeRotating(BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get(), new ResourceLocation(TardisRefined.MODID, "block/console_configuration"));
   */ }


   /* public JsonObject emptyBlockState(Block block) {
        return getBuilder(block).partialState().modelForState().modelFile(models().getExistingFile(new ResourceLocation("minecraft:block/barrier"))).addModel().toJson();
    }

    public JsonObject customLocation(Block block, ResourceLocation resourceLocation) {
        return getBuilder(block).partialState().modelForState().modelFile(models().getExistingFile(resourceLocation)).addModel().toJson();
    }

    public JsonObject terraformer(Block block) {
        return getBuilder(block).forAllStates(
                state -> state.getValue(TerraformerBlock.ACTIVE) ?
                        ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(TardisRefined.MODID, "block/terraformer_on"))).build() :
                        ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(TardisRefined.MODID, "block/terraformer"))).build()).toJson();

    }

    // Paul McGann is...
    public JsonObject threeDeeRotating(Block block, ResourceLocation location) {
        return getBuilder(block.).forAllStates(
                state -> ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(location)).rotationY((int) state.getValue(HorizontalDirectionalBlock.FACING).toYRot()).build()).toJson();

    }*/

}