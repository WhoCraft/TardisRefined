package whocraft.tardis_refined.common.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.world.chunk.TardisChunkGenerator;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ChunkGenerators {
    public static final DeferredRegistry<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegistry.create(TardisRefined.MODID, Registry.CHUNK_GENERATOR_REGISTRY);
    public static final RegistrySupplier<Codec<? extends ChunkGenerator>> TARDIS_CHUNK_GENERATOR = CHUNK_GENERATORS.register("tardis", () -> TardisChunkGenerator.CODEC);
    public static final ResourceKey<Biome> TARDIS_BIOME = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(TardisRefined.MODID, "tardis"));
}
