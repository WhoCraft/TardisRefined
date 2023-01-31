package whocraft.tardis_refined.common.world.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import whocraft.tardis_refined.common.world.ChunkGenerators;
import whocraft.tardis_refined.registry.BlockRegistry;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TardisChunkGenerator extends ChunkGenerator {
    public static final Codec<TardisChunkGenerator> CODEC = RecordCodecBuilder
            .create(instance -> commonCodec(instance).and(RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter((thing) -> thing.BIOME_REG))
                    .apply(instance, instance.stable(TardisChunkGenerator::new)));

    public final Registry<Biome> BIOME_REG;
    public final Registry<StructureSet> SET_REG;

    public TardisChunkGenerator(Registry<StructureSet> setReg, Registry<Biome> biomeReg) {
        super(setReg, Optional.empty(), new FixedBiomeSource(biomeReg.getHolderOrThrow(ChunkGenerators.TARDIS_BIOME)));
        this.BIOME_REG = biomeReg;
        this.SET_REG = setReg;
    }

    @Override
    public void applyBiomeDecoration(WorldGenLevel pLevel, ChunkAccess pChunk, StructureManager pStructureManager) {
    }

    @Override
    public void createStructures(RegistryAccess pRegistryAccess, RandomState pRandom, StructureManager pStructureManager, ChunkAccess pChunk, StructureTemplateManager pStructureTemplateManager, long pSeed) {
        //super.createStructures(pRegistryAccess, pRandom, pStructureManager, pChunk, pStructureTemplateManager, pSeed);
    }

    @Override
    public void createReferences(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkAccess pChunk) {
        //super.createReferences(pLevel, pStructureManager, pChunk);
    }

    @Override
    public Codec<TardisChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion p_223043_, long p_223044_, RandomState p_223045_, BiomeManager p_223046_, StructureManager p_223047_, ChunkAccess p_223048_, GenerationStep.Carving p_223049_) {
    }


    @Override
    public void buildSurface(WorldGenRegion level, StructureManager structureManager, RandomState random, ChunkAccess chunk) {
        var bottom = chunk.getMinBuildHeight() + 70;
        BlockPos cornerPos = new BlockPos(chunk.getPos().getMinBlockX(), bottom, chunk.getPos().getMinBlockZ());
        BlockPos lastCornerPos = new BlockPos(chunk.getPos().getMaxBlockX(), chunk.getMaxBuildHeight() - 75, chunk.getPos().getMaxBlockZ());
        for (BlockPos pos : BlockPos.betweenClosed(cornerPos, lastCornerPos)) {
            if (pos.getY() <= bottom + 5) {
                chunk.setBlockState(pos, BlockRegistry.HARDENED_GROWTH_STONE.get().defaultBlockState(), false);
            } else {
                chunk.setBlockState(pos, BlockRegistry.GROWTH_STONE.get().defaultBlockState(), false);
            }

        }
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion p_62167_) {}

    @Override
    public int getGenDepth() {
        return 384;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender p_223210_, RandomState p_223211_, StructureManager p_223212_, ChunkAccess access) {
        return CompletableFuture.completedFuture(access);
    }

    @Override
    public int getSeaLevel() {
        return -63;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int p_223032_, int p_223033_, Heightmap.Types p_223034_, LevelHeightAccessor p_223035_, RandomState p_223036_) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int p_223028_, int p_223029_, LevelHeightAccessor level, RandomState p_223031_) {

        BlockState[] states = new BlockState[level.getHeight()];
        for(int i = 0; i < states.length; ++i){
            states[i] = Blocks.STONE.defaultBlockState();
        }

        return new NoiseColumn(0, states);
    }

    @Override
    public void addDebugScreenInfo(List<String> p_223175_, RandomState p_223176_, BlockPos p_223177_) {}
}
