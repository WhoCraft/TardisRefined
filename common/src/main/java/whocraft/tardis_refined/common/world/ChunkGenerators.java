package whocraft.tardis_refined.common.world;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.world.chunk.TardisChunkGenerator;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ChunkGenerators {
    public static final DeferredRegistry<MapCodec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegistry.create(TardisRefined.MODID, Registries.CHUNK_GENERATOR);
    public static final ResourceKey<Biome> TARDIS_BIOME = ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(TardisRefined.MODID, "tardis"));
    public static final RegistrySupplier<MapCodec<? extends ChunkGenerator>> TARDIS_CHUNK_GENERATOR = CHUNK_GENERATORS.register("tardis", () -> TardisChunkGenerator.CODEC);
}
