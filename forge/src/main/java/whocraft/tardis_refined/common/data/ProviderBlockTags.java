package whocraft.tardis_refined.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ProviderBlockTags extends BlockTagsProvider {

    public ProviderBlockTags(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {

        for (RegistrySupplier<Block> blocksEntry : BlockRegistry.BLOCKS.getEntries()) {
            Block block = blocksEntry.get();

            /*Fences*/
            if(block instanceof FenceBlock fenceBlock){
                tag(BlockTags.FENCES).add(fenceBlock);
            }

            /*Leaves*/
            if(block instanceof LeavesBlock leavesBlock){
                tag(BlockTags.LEAVES).add(leavesBlock);
            }

            /*Slabs*/
            if(block instanceof SlabBlock slabBlock){
                tag(BlockTags.SLABS).add(slabBlock);
            }
        }

    }
}
