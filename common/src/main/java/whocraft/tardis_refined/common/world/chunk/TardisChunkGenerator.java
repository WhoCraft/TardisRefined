package whocraft.tardis_refined.common.world.chunk;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.compat.SublevelAccessor;
import whocraft.tardis_refined.constants.TardisDimensionConstants;
import whocraft.tardis_refined.mixin.StructureManagerAccessor;
import whocraft.tardis_refined.registry.TRARSStructurePieceRegistry;
import whocraft.tardis_refined.registry.TRBlockRegistry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static whocraft.tardis_refined.common.tardis.TardisArchitectureHandler.EYE_OF_HARMONY_PLACEMENT;

public class TardisChunkGenerator extends ChunkGenerator {

    public static final MapCodec<TardisChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(tardisChunkGenerator -> tardisChunkGenerator.biomeSource)
            ).apply(instance, instance.stable(TardisChunkGenerator::new))
    );

    // Some parameter values.
    public final int distanceBetweenGravityWell = 18;
    public final int arsChunkSize = ARSStructurePiece.LOCKED_PIECE_CHUNK_SIZE;

    public record GenerationPlacement(
            ChunkPos min, ChunkPos max, BlockPos placePos, StructureTemplate structure, int flags,
            boolean isFirstFloorRoom
    ) {

        public boolean isCorrectChunk(ChunkPos pos) {
            return pos.x >= min.x && pos.z >= min.z && pos.x <= max.x && pos.z <= max.z;
        }

    }
    private final Map<ResourceLocation, GenerationPlacement> structureBounds = new ConcurrentHashMap<>();

	public TardisChunkGenerator(BiomeSource biomeSource) {
		super(biomeSource);
	}

    private static final ResourceLocation EOH_PART_A = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "rooms/eye_of_harmony_part_a");
    private static final ResourceLocation EOH_PART_B = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "rooms/eye_of_harmony_part_b");
    private static final ResourceLocation HUB_CENTER = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "corridors/corridor_hub/corridor_hub_center");
    private static final ResourceLocation HUB_SOUTH = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "corridors/corridor_hub/corridor_hub_south");
    private static final ResourceLocation HUB_SOUTH_EAST = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "corridors/corridor_hub/corridor_hub_south_east");
    private static final ResourceLocation HUB_EAST = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "corridors/corridor_hub/corridor_hub_east");
    private static final ResourceLocation HUB_NORTH_EAST = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "corridors/corridor_hub/corridor_hub_north_east");
    private static final ResourceLocation HUB_NORTH = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "corridors/corridor_hub/corridor_hub_north");
    private static final ResourceLocation HUB_WEST = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "corridors/corridor_hub/corridor_hub_west");

    private static final List<ResourceLocation> STATIC_ROOM_ORDER = List.of(
            EOH_PART_A, EOH_PART_B, HUB_CENTER, HUB_SOUTH, HUB_SOUTH_EAST,
            HUB_EAST, HUB_NORTH_EAST, HUB_NORTH, HUB_WEST
    );

    private static Optional<GenerationPlacement> getPlacement(
            StructureTemplateManager manager, StructurePlaceSettings settings, ResourceLocation id, BlockPos pos, boolean hasSecondFloorRoom, int flags
    ) {
        return manager.get(id).map(structure -> {
            var box = structure.getBoundingBox(settings, pos);
            return new GenerationPlacement(
                    new ChunkPos(SectionPos.blockToSectionCoord(box.minX()), SectionPos.blockToSectionCoord(box.minZ())),
                    new ChunkPos(SectionPos.blockToSectionCoord(box.maxX()), SectionPos.blockToSectionCoord(box.maxZ())),
                    pos, structure, flags, hasSecondFloorRoom
            );
        });
    }

    private void addPlacement(StructureTemplateManager manager, StructurePlaceSettings settings, ResourceLocation id, BlockPos pos, boolean hasSecondFloorRoom) {
        addPlacement(manager, settings, id, pos, hasSecondFloorRoom, 0);
    }

    private void addPlacement(StructureTemplateManager manager, StructurePlaceSettings settings, ResourceLocation id, BlockPos pos, boolean hasSecondFloorRoom, int flags) {
        getPlacement(manager, settings, id, pos, hasSecondFloorRoom, flags).ifPresent(
                placement -> structureBounds.put(id, placement)
        );
    }

    private static BlockPos get3x3StartPos(int chunkX, int chunkZ, int height) {
        return new BlockPos(SectionPos.sectionToBlockCoord(chunkX, 0), height, SectionPos.sectionToBlockCoord(chunkZ, 0)).north(16).west(16);
    }

    private static BlockPos get3x3StartPos(int chunkX, int chunkZ, boolean isSecondFloor) {
        return get3x3StartPos(chunkX, chunkZ, getHeight(isSecondFloor));
    }

    private static int getHeight(boolean isSecondFloor) {
        return isSecondFloor ? 125 : 97;
    }

    private void populateStructureMap(StructureTemplateManager manager, StructurePlaceSettings settings) {
        if (structureBounds.isEmpty()) {
            addPlacement(manager, settings, EOH_PART_A, EYE_OF_HARMONY_PLACEMENT, false, Block.UPDATE_NONE);
            addPlacement(manager, settings, EOH_PART_B, EYE_OF_HARMONY_PLACEMENT.offset(0, 0, 24), false, Block.UPDATE_NONE);
            addPlacement(manager, settings, HUB_CENTER, get3x3StartPos(63, 0, 76), true, Block.UPDATE_NEIGHBORS);
            addPlacement(manager, settings, HUB_SOUTH, get3x3StartPos(63, 3, false), true);
            addPlacement(manager, settings, HUB_SOUTH_EAST, get3x3StartPos(66, 3, false), true);
            addPlacement(manager, settings, HUB_EAST, get3x3StartPos(66, 0, false), true);
            addPlacement(manager, settings, HUB_NORTH_EAST, get3x3StartPos(66, -3, false), true);
            addPlacement(manager, settings, HUB_NORTH, get3x3StartPos(63, -3, false), true);
            addPlacement(manager, settings, HUB_WEST, get3x3StartPos(60, 0, false), true);
        }
    }

    private Optional<GenerationPlacement> getIfCorrectChunk(ResourceLocation id, ChunkPos pos) {
        var settings = structureBounds.get(id);
        if (settings != null && settings.isCorrectChunk(pos)) {
            return Optional.of(settings);
        } else {
            return Optional.empty();
        }
    }

    private boolean placeIfCorrectChunk(PositionalRandomFactory random, WorldGenLevel level, StructurePlaceSettings settings, ChunkPos pos, ResourceLocation id) {
        var placement = getIfCorrectChunk(id, pos);
        if (placement.isPresent()) {
            placement.get().structure.placeInWorld(
                    level, placement.get().placePos, placement.get().placePos, settings, level.getRandom(), placement.get().flags
            );
            if (placement.get().isFirstFloorRoom) {
                ResourceLocation pieceToPlace = getRandomCorridorPiece(random.at(placement.get().placePos)).getResourceLocation();
                placePieceInWorld(level, pieceToPlace, placement.get().placePos.atY(getHeight(true)), settings);
                return true;
            }
        }
        return false;
    }

    private ChunkPos get3x3ARSReferencePos(ChunkPos pos) {
        int xOffset = pos.x % arsChunkSize;
        int zOffset = pos.z % arsChunkSize;
        int x = pos.x - xOffset;
        int z = pos.z - zOffset;

        if (xOffset > arsChunkSize / 2) {
            x += arsChunkSize;
        }
        if (zOffset > arsChunkSize / 2) {
            z += arsChunkSize;
        }

        if (xOffset < -arsChunkSize / 2) {
            x -= arsChunkSize;
        }
        if (zOffset < -arsChunkSize / 2) {
            z -= arsChunkSize;
        }

        return new ChunkPos(x, z);
    }

    public static Optional<UUID> getUUIDForTARDIS(ResourceKey<Level> tardisID) {
        try {
            return Optional.of(UUID.fromString(tardisID.location().getPath()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public void applyBiomeDecoration(WorldGenLevel pLevel, ChunkAccess pChunk, StructureManager pStructureManager) {
        var uuid = getUUIDForTARDIS(pLevel.getLevel().dimension()).orElseGet(UUID::randomUUID);
        var random = new XoroshiroRandomSource.XoroshiroPositionalRandomFactory(uuid.getLeastSignificantBits(), uuid.getMostSignificantBits());
        StructurePlaceSettings settings = new StructurePlaceSettings();
        populateStructureMap(pLevel.getLevel().getStructureManager(), settings);
        settings.setBoundingBox(getWritableArea(pChunk));

        boolean placedMainFloors = false;

        for (var room : STATIC_ROOM_ORDER) {
            placedMainFloors |= placeIfCorrectChunk(random, pLevel, settings, pChunk.getPos(), room);
        }

        if (SublevelAccessor.get().isChunkInSublevelSpace(pLevel.getLevel(), pChunk.getPos())) {
            return;
        }

        var arsReferencePos = get3x3ARSReferencePos(pChunk.getPos());

        if (arsReferencePos.x > -25 && arsReferencePos.x < 25 && arsReferencePos.z > -25 && arsReferencePos.z < 25) {
            return;
        }


        if (!placedMainFloors) {
            BlockPos arsPosBottom = get3x3StartPos(arsReferencePos.x, arsReferencePos.z, false);
            BlockPos arsPosTop = get3x3StartPos(arsReferencePos.x, arsReferencePos.z, true);

            if (isChunkAtGravityInterval(arsReferencePos)) {
                ResourceLocation pieceToPlace = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "corridors/corridor_connection_bottom");
                placePieceInWorld(pLevel, pieceToPlace, arsPosBottom, settings);

                pieceToPlace = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "corridors/corridor_connection_top");
                placePieceInWorld(pLevel, pieceToPlace, arsPosTop, settings);

            } else {
                ResourceLocation bottomFloorPiece = getRandomRoomPiece(random.at(arsPosBottom)).getResourceLocation();
                placePieceInWorld(pLevel, bottomFloorPiece, arsPosBottom, settings);

                ResourceLocation topFloorPiece = getRandomRoomPiece(random.at(arsPosTop)).getResourceLocation();
                placePieceInWorld(pLevel, topFloorPiece, arsPosTop, settings);
            }
        }
    }

    @Override
    public void createStructures(RegistryAccess registryAccess, ChunkGeneratorStructureState chunkGeneratorStructureState, StructureManager structureManager, ChunkAccess chunkAccess, StructureTemplateManager structureTemplateManager) {
        // super.createStructures(registryAccess, chunkGeneratorStructureState, structureManager, chunkAccess, structureTemplateManager);
    }

    @Override
    public void createReferences(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkAccess pChunk) {
        //super.createReferences(pLevel, pStructureManager, pChunk);
    }


    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion p_223043_, long p_223044_, RandomState p_223045_, BiomeManager p_223046_, StructureManager p_223047_, ChunkAccess p_223048_, GenerationStep.Carving p_223049_) {
    }


    @Override
    public void buildSurface(WorldGenRegion level, StructureManager structureManager, RandomState random, ChunkAccess chunk) {

    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion p_62167_) {
    }

    @Override
    public int getGenDepth() {
        return 384;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess access) {
        if (((StructureManagerAccessor) structureManager).getLevel() instanceof Level l && SublevelAccessor.get().isChunkInSublevelSpace(l, access.getPos())) {
            return CompletableFuture.completedFuture(access);
        }
        // Flatworlds appear to use this function instead of the surface.
        BlockPos cornerPos = new BlockPos(access.getPos().getMinBlockX(), TardisDimensionConstants.TARDIS_ROOT_GENERATION_MIN_HEIGHT - 5, access.getPos().getMinBlockZ());
        BlockPos lastCornerPos = new BlockPos(access.getPos().getMaxBlockX(), TardisDimensionConstants.TARDIS_ROOT_GENERATION_MAX_HEIGHT + 5, access.getPos().getMaxBlockZ());
        for (BlockPos pos : BlockPos.betweenClosed(cornerPos, lastCornerPos)) {

            if (pos.getY() <= TardisDimensionConstants.TARDIS_ROOT_GENERATION_MIN_HEIGHT || pos.getY() > TardisDimensionConstants.TARDIS_ROOT_GENERATION_MAX_HEIGHT) {
                access.setBlockState(pos, Blocks.BEDROCK.defaultBlockState(), false);
            } else {

                access.setBlockState(pos, TRBlockRegistry.FOOLS_STONE.get().defaultBlockState(), false);
            }
        }

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
        for (int i = 0; i < states.length; ++i) {
            states[i] = Blocks.STONE.defaultBlockState();
        }

        return new NoiseColumn(0, states);
    }

    /**
     * Determines if the chunk is a Gravity hallways chunk
     *
     * @param pos the position of the chunk
     * @return is the chunk a Gravity chunk
     */

    private boolean isChunkAtGravityInterval(ChunkPos pos) {

        return (pos.x % distanceBetweenGravityWell == 0) && (pos.z % distanceBetweenGravityWell == 0);
    }

    /**
     * Fetch a random corridor piece to populate a chunk
     *
     * @return random corridor ARS piece from the registry.
     */
    private ARSStructurePiece getRandomCorridorPiece(RandomSource random) {
        return TRARSStructurePieceRegistry.CORRIDORS.get(random.nextInt(TRARSStructurePieceRegistry.CORRIDORS.size()));
    }


    /**
     * Fetch a random room piece to populate a chunk
     *
     * @return random room ARS piece from the registry.
     */
    private ARSStructurePiece getRandomRoomPiece(RandomSource random) {
        return TRARSStructurePieceRegistry.ROOMS.get(random.nextInt(TRARSStructurePieceRegistry.ROOMS.size()));
    }


    private static void placePieceInWorld(WorldGenLevel level, ResourceLocation pieceToPlace, BlockPos pos, StructurePlaceSettings settings) {
        // Place the desired piece.

        level.getLevel().getStructureManager().get(pieceToPlace).ifPresent(structure -> {
            structure.placeInWorld(level, pos, pos, settings, level.getRandom(), 0);
        });

    }


    @Override
    public void addDebugScreenInfo(List<String> p_223175_, RandomState p_223176_, BlockPos p_223177_) {
    }
}
