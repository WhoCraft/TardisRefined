package whocraft.tardis_refined.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

import java.util.concurrent.CompletableFuture;

public class ProviderBlockTags extends BlockTagsProvider {

    public ProviderBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        for (Block blocksEntry : BlockRegistry.BLOCKS.getRegistry().stream().toList()) {
            Block block = blocksEntry;

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

        tag(BlockTags.DRAGON_IMMUNE).add(BlockRegistry.ROOT_SHELL_BLOCK.get()).add(BlockRegistry.GLOBAL_SHELL_BLOCK.get()).add(BlockRegistry.GLOBAL_CONSOLE_BLOCK.get());
        tag(BlockTags.WITHER_IMMUNE).add(BlockRegistry.ROOT_SHELL_BLOCK.get()).add(BlockRegistry.GLOBAL_SHELL_BLOCK.get()).add(BlockRegistry.GLOBAL_CONSOLE_BLOCK.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get())
                .add(BlockRegistry.LANDING_PAD.get())
                .add(BlockRegistry.FLIGHT_DETECTOR.get())
                .add(BlockRegistry.TERRAFORMER_BLOCK.get())
                .add(BlockRegistry.ZEITON_FUSED_IRON_BLOCK.get())
                .add(BlockRegistry.ZEITON_FUSED_COPPER_BLOCK.get())
                .add(BlockRegistry.ZEITON_ORE_DEEPSLATE.get())
                .add(BlockRegistry.ZEITON_ORE.get())
                .add(BlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get())
                .add(BlockRegistry.ZEITON_BLOCK.get())
                .add(BlockRegistry.ROOT_PLANT_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get())
                .add(BlockRegistry.LANDING_PAD.get())
                .add(BlockRegistry.FLIGHT_DETECTOR.get())
                .add(BlockRegistry.TERRAFORMER_BLOCK.get())
                .add(BlockRegistry.ZEITON_FUSED_IRON_BLOCK.get())
                .add(BlockRegistry.ZEITON_FUSED_COPPER_BLOCK.get())
                .add(BlockRegistry.ZEITON_ORE_DEEPSLATE.get())
                .add(BlockRegistry.ZEITON_ORE.get())
                .add(BlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get())
                .add(BlockRegistry.ZEITON_BLOCK.get())
                .add(BlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get());

    }
}
