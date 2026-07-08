package whocraft.tardis_refined.compat.sable;

import com.google.common.collect.Iterables;
import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.companion.SubLevelAccess;
import dev.ryanhcode.sable.companion.math.BoundingBox3d;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4d;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.compat.SublevelAccessor;

import java.util.stream.Stream;

public class SableSublevelAccessor implements SublevelAccessor {

    public static final SableSublevelAccessor INSTANCE = new SableSublevelAccessor();
    
    private SableSublevelAccessor() {}
    
    public static class SableSublevel implements Sublevel {

        private final SubLevelAccess access;

        public SableSublevel(SubLevelAccess access) {
            this.access = access;
        }

        @Override
        public AABB toSublevelAABB(AABB aabb) {
            var mat = access.logicalPose().bakeIntoMatrix(new Matrix4d());
            Vector3d min = new Vector3d();
            Vector3d max = new Vector3d();
            mat.transformAab(
                    aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ,
                    min, max
            );
            return new AABB(min.x, min.y, min.z, max.x, max.y, max.z);
        }

        @Override
        public Direction toSublevelDirection(Direction direction) {
            return SublevelAccessor.vectorToDirection(
                    access.logicalPose().transformNormalInverse(SublevelAccessor.directionToVector(direction))
            );
        }

        @Override
        public boolean isHorizontalEnough() {
            return Sublevel.isHorizontalEnough(access.logicalPose().orientation().getEulerAnglesXYZ(new Vector3d()));
        }
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
    public Iterable<Sublevel> getSublevelsIntersecting(Level level, AABB aabb) {
        return Iterables.transform(SableCompanion.INSTANCE.getAllIntersecting(level, new BoundingBox3d(aabb)), SableSublevel::new);
    }

    @Override
    public boolean collidesWithSublevel(Level level, AABB boundingBox) {
        var box = new BoundingBox3d(boundingBox);
        var currentSub = SableCompanion.INSTANCE.getContaining(level, boundingBox.getCenter());
        if (currentSub != null) {
            box.transform(currentSub.logicalPose());
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
    public boolean isBlockInSublevel(Level level, BlockPos pos) {
        return SableCompanion.INSTANCE.getContaining(level, pos) != null;
    }

    @Override
    public AABB toMainLevelAABB(Level level, BlockPos pos, AABB aabb) {
        var sub = SableCompanion.INSTANCE.getContaining(level, pos);
        if (sub != null) {
            var mat = sub.logicalPose().bakeIntoMatrix(new Matrix4d()).invert();
            Vector3d min = new Vector3d();
            Vector3d max = new Vector3d();
            mat.transformAab(
                    aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ,
                    min, max
            );
            return new AABB(min.x, min.y, min.z, max.x, max.y, max.z);
        } else {
            return aabb;
        }
    }

    @Override
    public Quaternionf toMainLevelRotation(Level level, BlockPos position, Quaternionf rotation) {
        var sub = SableCompanion.INSTANCE.getContaining(level, position);
        if (sub != null) {
            return new Quaternionf(sub.logicalPose().orientation().invert(new Quaterniond()).mul(new Quaterniond(rotation)));
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
            return SublevelAccessor.vectorToBlockPos(sub.logicalPose().transformPosition(SublevelAccessor.blockPosToVector(blockPos)));
        } else {
            return blockPos;
        }
    }

    @Override
    public Vec3 toMainLevelPosition(Level level, BlockPos blockPos, Vec3 vector) {
        var sub = SableCompanion.INSTANCE.getContaining(level, blockPos);
        if (sub != null) {
            return SublevelAccessor.vectorToVector(sub.logicalPose().transformPosition(SublevelAccessor.vectorToVector(vector)));
        } else {
            return vector;
        }
    }

    @Override
    public TardisNavLocation toMainLevelLocation(TardisNavLocation input) {
        var sub = SableCompanion.INSTANCE.getContaining(input.getLevel(), input.getPosition());
        if (sub != null) {
            BlockPos pos = SublevelAccessor.vectorToBlockPos(sub.logicalPose().transformPosition(
                    SublevelAccessor.blockPosToVector(input.getPosition()))
            );
            Direction dir = SublevelAccessor.vectorToDirection(
                    sub.logicalPose().transformNormal(SublevelAccessor.directionToVector(input.getDirection()))
            );
            TardisNavLocation output = new TardisNavLocation(pos, dir, input.getLevel());
            output.setName(input.getName());
            return output;
        } else {
            return input;
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
