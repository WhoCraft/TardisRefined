package whocraft.tardis_refined.common.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.EntityRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ProviderLootTable extends LootTableProvider {

    public ProviderLootTable(PackOutput arg) {
        super(arg, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(ModEntityLoot::new, LootContextParamSets.ENTITY)));
    }

    public static class ModBlockLoot extends BlockLootSubProvider {
        protected ModBlockLoot() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            for (Block block : getKnownBlocks()) {
                dropSelf(block);
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            ArrayList<@NotNull Block> blocks = new ArrayList<>();
            for (RegistrySupplier<Block> entry : BlockRegistry.BLOCKS.getEntries()) {
                blocks.add(entry.get());
            }
            return blocks;
        }
    }

    public static class ModEntityLoot extends EntityLootSubProvider {

        protected ModEntityLoot() {
            super(FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected Stream<EntityType<?>> getKnownEntityTypes() {
            ArrayList<@NotNull EntityType<?>> entities = new ArrayList<>();
            for (RegistrySupplier<EntityType<?>> entry : EntityRegistry.ENTITY_TYPES.getEntries()) {
                if (entry.get() != EntityRegistry.CONTROL_ENTITY.get())
                    break;
                entities.add(entry.get());
            }
            return entities.stream();
        }

        @Override
        public void generate() {

        }
    }
}