package whocraft.tardis_refined.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        for (RegistrySupplier<Block> entry : BlockRegistry.BLOCKS.getEntries()) {
            blockItem(entry.getId());
        }
    }

    public ItemModelBuilder blockItem(ResourceLocation item) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile(new ResourceLocation(item.getNamespace(), "block/" + item.getPath())));
    }
}
