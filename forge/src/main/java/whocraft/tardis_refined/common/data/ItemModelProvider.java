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
        super(generator, TardisRefined.MODID, existingFileHelper);
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



        ResourceLocation temp = new ResourceLocation(TardisRefined.MODID, "item/walter_white");
        basicItem(BlockRegistry.BULK_HEAD_DOOR.getId(), temp);
        basicItem(BlockRegistry.GLOBAL_CONSOLE_BLOCK.getId(), temp);
        basicItem(BlockRegistry.ARS_EGG.getId(), temp);
        basicItem(BlockRegistry.ROOT_SHELL_BLOCK.getId(), temp);
        basicItem(BlockRegistry.ROOT_SHELL_DOOR.getId(), temp);
        basicItem(BlockRegistry.INTERNAL_DOOR_BLOCK.getId(), temp);
        basicItem(BlockRegistry.GLOBAL_DOOR_BLOCK.getId(), temp);
        basicItem(BlockRegistry.GLOBAL_SHELL_BLOCK.getId(), temp);
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
