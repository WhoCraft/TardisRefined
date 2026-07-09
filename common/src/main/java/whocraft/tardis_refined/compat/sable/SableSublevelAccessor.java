package whocraft.tardis_refined.compat.sable;

import com.google.common.collect.Iterables;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.ryanhcode.sable.api.sublevel.ServerSubLevelContainer;
import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.SubLevelAccess;
import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import dev.ryanhcode.sable.companion.math.Pose3dc;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.storage.HoldingSubLevel;
import dev.ryanhcode.sable.sublevel.storage.holding.GlobalSavedSubLevelPointer;
import dev.ryanhcode.sable.sublevel.tracking_points.SubLevelTrackingPointSavedData;
import dev.ryanhcode.sable.sublevel.tracking_points.TrackingPoint;
import net.minecraft.core.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.compat.SublevelAccessor;
import whocraft.tardis_refined.mixin.SubLevelHoldingChunkMapAccessor;

import java.util.*;
import java.util.stream.Stream;

public class SableSublevelAccessor implements SublevelAccessor {

    public static final SableSublevelAccessor INSTANCE = new SableSublevelAccessor();
    
    private SableSublevelAccessor() {}

    private static Stream<Vec3> getCorners(AABB aabb) {
        return Stream.of(
                new Vec3(aabb.minX, aabb.minY, aabb.minZ),
                new Vec3(aabb.maxX, aabb.minY, aabb.minZ),
                new Vec3(aabb.minX, aabb.maxY, aabb.minZ),
                new Vec3(aabb.minX, aabb.minY, aabb.maxZ),
                new Vec3(aabb.maxX, aabb.maxY, aabb.minZ),
                new Vec3(aabb.minX, aabb.maxY, aabb.maxZ),
                new Vec3(aabb.maxX, aabb.minY, aabb.maxZ),
                new Vec3(aabb.maxX, aabb.maxY, aabb.maxZ)
        );
    }

    private static AABB encapsulating(Stream<Vec3> corners) {
        return corners.map(pos -> new AABB(pos, pos)).reduce(AABB::minmax).orElseThrow();
    }

    // Using matrix produces inaccurate transformation.
    private static AABB transformAABB(Pose3dc pose, AABB aabb) {
        return encapsulating(getCorners(aabb).map(pose::transformPosition));
    }

    private static AABB transformAABBInverse(Pose3dc pose, AABB aabb) {
        return encapsulating(getCorners(aabb).map(pose::transformPositionInverse));
    }

    private static BlockPos toMainLevelPosition(SubLevelAccess sub, BlockPos pos) {
        return SublevelAccessor.vectorToBlockPos(sub.logicalPose().transformPosition(SublevelAccessor.blockPosToVector(pos)));
    }

    private static Vec3 toMainLevelPosition(SubLevelAccess sub, Vec3 pos) {
        return SublevelAccessor.vectorToVector(sub.logicalPose().transformPosition(SublevelAccessor.vectorToVector(pos)));
    }

    public static class SableSublevel implements Sublevel {

        private final SubLevelAccess access;

        public SableSublevel(SubLevelAccess access) {
            this.access = access;
        }

        @Override
        public AABB toSublevelAABB(AABB aabb) {
            return transformAABBInverse(access.logicalPose(), aabb);
        }

        @Override
        public Direction toSublevelDirection(Direction direction) {
            return SublevelAccessor.vectorToDirection(
                    access.logicalPose().transformNormalInverse(SublevelAccessor.directionToVector(direction))
            );
        }

        @Override
        public BlockPos toMainLevelPos(BlockPos pos) {
            return toMainLevelPosition(access, pos);
        }

        @Override
        public Vec3 toMainLevelPos(Vec3 pos) {
            return toMainLevelPosition(access, pos);
        }

        @Override
        public Vector3d toMainLevelAccurateDirection(Direction direction) {
            return access.logicalPose().transformNormal(SublevelAccessor.directionToVector(direction));
        }

        @Override
        public boolean isHorizontalEnough() {
            return Sublevel.isHorizontalEnough(access.logicalPose().orientation().getEulerAnglesXYZ(new Vector3d()));
        }
    }

    public record SablePosition(Vec3 pos, Sublevel sublevel) implements PositionReference {

    }

    public record LoadableSablePosition(UUID uuid, Component meta, ResourceKey<Level> dimension) implements LoadablePositionReference {

        public static final MapCodec<LoadableSablePosition> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        UUIDUtil.CODEC.fieldOf("uuid").forGetter(LoadableSablePosition::uuid),
                        ComponentSerialization.CODEC.fieldOf("meta").forGetter(LoadableSablePosition::meta),
                        Level.RESOURCE_KEY_CODEC.fieldOf("dimension").forGetter(LoadableSablePosition::dimension)
                ).apply(instance, LoadableSablePosition::new)
        );

        @Override
        public void destroy(MinecraftServer server) {
            var level = server.getLevel(dimension);
            if (level == null) return;
            var trackedStorage = SubLevelTrackingPointSavedData.getOrLoad(level);
            if (trackedStorage != null) {
                trackedStorage.removeTrackingPoint(uuid);
            }
        }

        private Optional<TrackingPoint> loadPoint(MinecraftServer server) {
            var level = server.getLevel(dimension);
            if (level == null) return Optional.empty();
            var subLevels = ServerSubLevelContainer.getContainer(level);
            var trackedStorage = SubLevelTrackingPointSavedData.getOrLoad(level);
            if (subLevels != null && trackedStorage != null) {
                var point = trackedStorage.getTrackingPoint(uuid);
                if (point == null) return Optional.empty();
                if (subLevels.getSubLevel(point.subLevelID()) != null) return Optional.of(point);
                var chunkMap = subLevels.getHoldingChunkMap();
                if (point.subLevelID() != null && point.lastSavedSubLevelPointer() != null) {
                    chunkMap.snatchAndLoad(point.lastSavedSubLevelPointer(), point.subLevelID());
                }
                return Optional.of(point);
            }
            return Optional.empty();
        }

        @Override
        public Optional<PositionReference> tryLoad(MinecraftServer server) {
            return loadPoint(server).map(point -> {
                var p = point.point();
                return new SablePosition(
                        new Vec3(p.x, p.y, p.z), new SableSublevel(SableCompanion.INSTANCE.getContaining(server.getLevel(dimension), p))
                );
            });
        }

        @Override
        public Optional<Component> metadata() {
            return Optional.of(meta);
        }

        @Override
        public MapCodec<? extends LoadablePositionReference> codec() {
            return CODEC;
        }
    }

    private static Optional<LoadablePositionReference> getPositionReference(ServerLevel level, Vec3 pos) {
        if (SableCompanion.INSTANCE.getContaining(level, pos) instanceof ServerSubLevel serverSub) {
            var trackedStorage = SubLevelTrackingPointSavedData.getOrLoad(level);
            return Optional.of(new LoadableSablePosition(
                    trackedStorage.generateTrackingPoint(pos, serverSub),
                    Component.literal(
                            "[Sable] " + Optional.ofNullable(serverSub.getName()).orElse(
                                    serverSub.getUniqueId().toString().substring(0, 5)
                            )
                    ),
                    level.dimension()
            ));
        }
        return Optional.empty();
    }

    @Override
    public Optional<LoadablePositionReference> getPositionReference(Level level, BlockPos pos) {
        if (level instanceof ServerLevel sl) {
            return getPositionReference(sl, pos.getCenter());
        }
        return SublevelAccessor.super.getPositionReference(level, pos);
    }

    @Override
    public boolean isChunkInSublevelSpace(Level level, ChunkPos pos) {
        return SableCompanion.INSTANCE.isInPlotGrid(level, pos);
    }

    @Override
    public boolean isBlockInSublevelSpace(Level level, BlockPos pos) {
        return SableCompanion.INSTANCE.isInPlotGrid(level, pos);
    }

    @Override
    public boolean isBlockInSublevelSpace(Level level, Position pos) {
        return SableCompanion.INSTANCE.isInPlotGrid(level, pos);
    }

    private static void loadAllSubLevelsIn(ServerLevel level, AABB aabb) {
        if (SableCompanion.INSTANCE.isInPlotGrid(level, aabb.getMinPosition()) || SableCompanion.INSTANCE.isInPlotGrid(level, aabb.getMaxPosition())) {
            return;
        }
        double maxSize = Math.max(aabb.getXsize(), aabb.getZsize());
        if (maxSize > 128) return;


        var subLevels = ServerSubLevelContainer.getContainer(level);
        if (subLevels == null) return;

        var chunkMap = subLevels.getHoldingChunkMap();

        Map<UUID, GlobalSavedSubLevelPointer> levelsToLoad = new HashMap<>();

        ChunkPos.rangeClosed(
                new ChunkPos(SectionPos.blockToSectionCoord(aabb.minX), SectionPos.blockToSectionCoord(aabb.minZ)),
                new ChunkPos(SectionPos.blockToSectionCoord(aabb.maxX), SectionPos.blockToSectionCoord(aabb.maxZ))
        ).forEach(cPos -> {
            var chunk = ((SubLevelHoldingChunkMapAccessor) chunkMap).callGetOrLoadHoldingChunk(cPos, false);
            if (chunk == null) return;
            for (var sublevel : chunk.getLoadedHoldingSubLevels()) {
                levelsToLoad.put(sublevel.data().uuid(), sublevel.pointer());
            }
        });

        levelsToLoad.forEach((id, pointer) -> {
            chunkMap.snatchAndLoad(pointer, id);
        });
    }

    @Override
    public Iterable<Sublevel> getSublevelsIntersecting(Level level, AABB aabb) {
        if (level instanceof ServerLevel sl) {
            loadAllSubLevelsIn(sl, aabb);
        }
        return Iterables.transform(SableCompanion.INSTANCE.getAllIntersecting(level, new BoundingBox3d(aabb)), SableSublevel::new);
    }

    @Override
    public boolean collidesWithSublevel(Level level, AABB boundingBox) {
        var box = new BoundingBox3d(boundingBox);
        var currentSub = SableCompanion.INSTANCE.getContaining(level, boundingBox.getCenter());
        if (currentSub != null) {
            box.transform(currentSub.logicalPose());
        }

        if (level instanceof ServerLevel sl) {
            loadAllSubLevelsIn(sl, boundingBox);
        }

        for (var sub : SableCompanion.INSTANCE.getAllIntersecting(level, box)) {
            if (sub == currentSub) continue;
            var transformedBox = box.transform(sub.logicalPose(), new BoundingBox3d());
            Stream<BlockPos> blocks = BlockPos.betweenClosedStream(transformedBox.toMojang());
            boolean boxEmpty = blocks.allMatch(blockPos -> {
                BlockState state = level.getBlockState(blockPos);
                return state.getCollisionShape(level, blockPos).isEmpty() && state.getFluidState().isEmpty();
            });
            if (!boxEmpty) {
                return true;
            }
        }

        return false;
    }

    @Override
    public AABB toMainLevelAABB(Level level, BlockPos pos, AABB aabb) {
        var sub = SableCompanion.INSTANCE.getContaining(level, pos);
        if (sub != null) {
            return transformAABB(sub.logicalPose(), aabb);
        } else {
            return aabb;
        }
    }

    @Override
    public Quaternionf toMainLevelRotation(Level level, BlockPos position, Quaternionf rotation) {
        var sub = SableCompanion.INSTANCE.getContaining(level, position);
        if (sub != null) {
            return new Quaternionf(sub.logicalPose().orientation().mul(new Quaterniond(rotation), new Quaterniond()));
        } else {
            return rotation;
        }
    }

    @Override
    public Vector3d toMainLevelRotation(Level level, BlockPos position, Direction direction) {
        var sub = SableCompanion.INSTANCE.getContaining(level, position);
        if (sub != null) {
            return sub.logicalPose().transformNormal(SublevelAccessor.directionToVector(direction));
        } else {
            return SublevelAccessor.directionToVector(direction);
        }
    }

    @Override
    public Vec3 toMainLevelRotation(Level level, BlockPos position, Vec3 direction) {
        var sub = SableCompanion.INSTANCE.getContaining(level, position);
        if (sub != null) {
            return sub.logicalPose().transformNormal(direction);
        } else {
            return direction;
        }
    }

    @Override
    public BlockPos toMainLevelPosition(Level level, BlockPos blockPos) {
        var sub = SableCompanion.INSTANCE.getContaining(level, blockPos);
        if (sub != null) {
            return toMainLevelPosition(sub, blockPos);
        } else {
            return blockPos;
        }
    }

    @Override
    public Vec3 toMainLevelPosition(Level level, BlockPos blockPos, Vec3 vector) {
        var sub = SableCompanion.INSTANCE.getContaining(level, blockPos);
        if (sub != null) {
            return toMainLevelPosition(sub, vector);
        } else {
            return vector;
        }
    }

    @Override
    public Vec2 toMainLevelRotation(Level level, BlockPos blockPos, Vec2 rotation) {
        var sub = SableCompanion.INSTANCE.getContaining(level, blockPos);
        if (sub != null) {
            return SublevelAccessor.vectorToRotation(sub.logicalPose().transformNormal(SublevelAccessor.rotationToVector(rotation)));
        } else {
            return rotation;
        }
    }
}
