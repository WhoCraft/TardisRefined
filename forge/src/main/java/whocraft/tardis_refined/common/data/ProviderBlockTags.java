package whocraft.tardis_refined.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRBlockRegistry;

import java.util.concurrent.CompletableFuture;

public class ProviderBlockTags extends BlockTagsProvider {

    public ProviderBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        for (RegistrySupplier<Block> blocksEntry : TRBlockRegistry.BLOCKS.getEntries().stream().toList()) {
            Block block = blocksEntry.get();

            /*Fences*/
            if (block instanceof FenceBlock fenceBlock) {
                tag(BlockTags.FENCES).add(fenceBlock);
            }

            /*Leaves*/
            if (block instanceof LeavesBlock leavesBlock) {
                tag(BlockTags.LEAVES).add(leavesBlock);
            }

            /*Slabs*/
            if (block instanceof SlabBlock slabBlock) {
                tag(BlockTags.SLABS).add(slabBlock);
            }
        }

        tag(BlockTags.DRAGON_IMMUNE).add(TRBlockRegistry.ROOT_SHELL_BLOCK.get()).add(TRBlockRegistry.GLOBAL_SHELL_BLOCK.get()).add(TRBlockRegistry.GLOBAL_CONSOLE_BLOCK.get());
        tag(BlockTags.WITHER_IMMUNE).add(TRBlockRegistry.ROOT_SHELL_BLOCK.get()).add(TRBlockRegistry.GLOBAL_SHELL_BLOCK.get()).add(TRBlockRegistry.GLOBAL_CONSOLE_BLOCK.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TRBlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get())
                .add(TRBlockRegistry.LANDING_PAD.get())
                .add(TRBlockRegistry.FLIGHT_DETECTOR.get())
                .add(TRBlockRegistry.TERRAFORMER_BLOCK.get())
                .add(TRBlockRegistry.ZEITON_FUSED_IRON_BLOCK.get())
                .add(TRBlockRegistry.ZEITON_FUSED_COPPER_BLOCK.get())
                .add(TRBlockRegistry.ZEITON_ORE_DEEPSLATE.get())
                .add(TRBlockRegistry.ZEITON_ORE.get())
                .add(TRBlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get())
                .add(TRBlockRegistry.ZEITON_BLOCK.get())
                .add(TRBlockRegistry.GRAVITY_WELL.get())
                .add(TRBlockRegistry.ROOT_PLANT_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(TRBlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get())
                .add(TRBlockRegistry.LANDING_PAD.get())
                .add(TRBlockRegistry.GRAVITY_WELL.get())
                .add(TRBlockRegistry.FLIGHT_DETECTOR.get())
                .add(TRBlockRegistry.TERRAFORMER_BLOCK.get())
                .add(TRBlockRegistry.ZEITON_FUSED_IRON_BLOCK.get())
                .add(TRBlockRegistry.ZEITON_FUSED_COPPER_BLOCK.get())
                .add(TRBlockRegistry.ZEITON_ORE_DEEPSLATE.get())
                .add(TRBlockRegistry.ZEITON_ORE.get())
                .add(TRBlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get())
                .add(TRBlockRegistry.ZEITON_BLOCK.get())
                .add(TRBlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get());

    }
}
