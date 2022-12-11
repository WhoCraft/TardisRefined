package whocraft.tardis_refined.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import whocraft.tardis_refined.TardisRefined;

public class ModelProviderBlock extends BlockStateProvider {

    public ModelProviderBlock(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}