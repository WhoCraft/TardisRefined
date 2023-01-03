package whocraft.tardis_refined.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.BlockRegistry;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        /*Simple Block Items*/
        blockItem(BlockRegistry.ARS_EGG.getId());
        blockItem(BlockRegistry.ARS_LEAVES.getId());
        blockItem(BlockRegistry.ARS_LEAVES_SLAB.getId());


        ResourceLocation leavesTexture = new ResourceLocation("tardis_refined:block/ars_leaves");


        fenceInventory("ars_leaves_fence_inventory", leavesTexture);

        blockItem(BlockRegistry.ARS_LEAVES_FENCE.getId(), new ResourceLocation(TardisRefined.MODID, "item/ars_leaves_fence_inventory"));
        blockItem(BlockRegistry.TERRAFORMER_BLOCK.getId());
        blockItem(BlockRegistry.AIR_LOCK_GENERATION_BLOCK.getId());

        basicItem(BlockRegistry.BULK_HEAD_DOOR.getId());
        basicItem(BlockRegistry.GLOBAL_CONSOLE_BLOCK.getId());
        basicItem(BlockRegistry.ARS_EGG.getId());
        basicItem(BlockRegistry.ROOT_SHELL_BLOCK.getId());
        basicItem(BlockRegistry.ROOT_SHELL_DOOR.getId());
        basicItem(BlockRegistry.GLOBAL_DOOR_BLOCK.getId());
        basicItem(BlockRegistry.GLOBAL_SHELL_BLOCK.getId());


    }

    public ItemModelBuilder basicItem(ResourceLocation item, ResourceLocation texture) {
        return this.getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", texture);
    }

    public ItemModelBuilder blockItem(ResourceLocation item) {
        return getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile(new ResourceLocation(item.getNamespace(), "block/" + item.getPath())));
    }

    public ItemModelBuilder blockItem(ResourceLocation item, ResourceLocation modelLocation) {
        return getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile(modelLocation));
    }
}
