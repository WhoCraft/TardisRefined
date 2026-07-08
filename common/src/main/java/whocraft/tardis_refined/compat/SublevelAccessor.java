package whocraft.tardis_refined.compat;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Math;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.compat.sable.SableSublevelAccessor;

import java.util.List;
import java.util.stream.Stream;

public interface SublevelAccessor {

    SublevelAccessor DUMMY = new SublevelAccessor() {
        @Override
        public boolean isChunkInSublevelSpace(Level level, ChunkPos pos) {
            return false;
        }

        @Override
        public boolean isBlockInSublevelSpace(Level level, BlockPos pos) {
            return false;
        }

        @Override
        public Iterable<Sublevel> getSublevelsIntersecting(Level level, AABB aabb) {
            return List.of();
        }

        @Override
        public boolean collidesWithSublevel(Level level, AABB boundingBox) {
            return false;
        }

        @Override
        public boolean isBlockInSublevel(Level level, BlockPos pos) {
            return false;
        }

        @Override
        public AABB toMainLevelAABB(Level level, BlockPos pos, AABB aabb) {
            return aabb;
        }

        @Override
        public Quaternionf toMainLevelRotation(Level level, BlockPos position, Quaternionf rotation) {
            return rotation;
        }

        @Override
        public Vector3d toMainLevelRotation(Level level, BlockPos position, Direction direction) {
            return directionToVector(direction);
        }

        @Override
        public Vec3 toMainLevelRotation(Level level, BlockPos position, Vec3 direction) {
            return direction;
        }

        @Override
        public BlockPos toMainLevelPosition(Level level, BlockPos blockPos) {
            return blockPos;
        }

        @Override
        public Vec3 toMainLevelPosition(Level level, BlockPos blockPos, Vec3 vector) {
            return vector;
        }

        @Override
        public TardisNavLocation toMainLevelLocation(TardisNavLocation input) {
            return input;
        }

        @Override
        public Vec2 toMainLevelRotation(Level level, BlockPos blockPos, Vec2 rotation) {
            return rotation;
        }
    };

    interface Sublevel {

        AABB toSublevelAABB(AABB aabb);

        Direction toSublevelDirection(Direction direction);

        default Stream<BlockPos> toSublevelPositions(AABB worldAABB) {
            var shypAABB = toSublevelAABB(worldAABB);
            return BlockPos.betweenClosedStream(shypAABB);
        }

        boolean isHorizontalEnough();

        static boolean isHorizontalEnough(Vector3d angles) {
            return Math.abs(angles.x) < Math.PI / 2 || Math.abs(angles.z) < Math.PI / 2;
        }

    }

    boolean isChunkInSublevelSpace(Level level, ChunkPos pos);

    boolean isBlockInSublevelSpace(Level level, BlockPos pos);

    Iterable<Sublevel> getSublevelsIntersecting(Level level, AABB aabb);

    default boolean collidesWithSublevel(Level level, BlockPos pos) {
        return collidesWithSublevel(level, AABB.of(new BoundingBox(pos)));
    }

    boolean collidesWithSublevel(Level level, AABB boundingBox);

    boolean isBlockInSublevel(Level level, BlockPos pos);

    AABB toMainLevelAABB(Level level, BlockPos pos, AABB aabb);

    Quaternionf toMainLevelRotation(Level level, BlockPos position, Quaternionf rotation);

    Vector3d toMainLevelRotation(Level level, BlockPos position, Direction direction);

    Vec3 toMainLevelRotation(Level level, BlockPos position, Vec3 direction);

    BlockPos toMainLevelPosition(Level level, BlockPos blockPos);

    Vec3 toMainLevelPosition(Level level, BlockPos blockPos, Vec3 vector);

    TardisNavLocation toMainLevelLocation(TardisNavLocation input);

    Vec2 toMainLevelRotation(Level level, BlockPos blockPos, Vec2 rotation);

    static Vector3d blockPosToVector(BlockPos blockPos) {
        return new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    static Vector3d vectorToVector(Vec3 vector) {
        return new Vector3d(vector.x, vector.y, vector.z);
    }

    static Vec3 vectorToVector(Vector3d vector) {
        return new Vec3(vector.x, vector.y, vector.z);
    }

    static BlockPos vectorToBlockPos(Vector3d vector) {
        return new BlockPos((int) org.joml.Math.round(vector.x), (int) org.joml.Math.round(vector.y), (int) Math.round(vector.z));
    }

    static Vector3d directionToVector(Direction direction) {
        return new Vector3d(direction.getStepX(), direction.getStepY(), direction.getStepZ());
    }

    static Direction vectorToDirection(Vector3d vector) {
        return Direction.getNearest(vector.x, vector.y, vector.z);
    }

    static Vector3d rotationToVector(Vec2 rotation) {
        var rot = Vec3.directionFromRotation(rotation);
        return new Vector3d(rot.x, rot.y, rot.z);
    }

    static Vec2 vectorToRotation(Vector3d vector) {
        // Copied from Entity::lookAt
        double x = vector.x;
        double y = vector.y;
        double z = vector.z;
        double hor = java.lang.Math.sqrt(x * x + z * z);
        float pitch = Mth.wrapDegrees((float)(-(Mth.atan2(y, hor) * Mth.RAD_TO_DEG)));
        float yaw = Mth.wrapDegrees((float)(Mth.atan2(z, x) * Mth.RAD_TO_DEG) - 90.0f);
        return new Vec2(pitch, yaw);
    }

    static SublevelAccessor get() {
        if (ModCompatChecker.sable()) {
            return SableSublevelAccessor.INSTANCE;
        }
        return DUMMY;
    }
    
}
