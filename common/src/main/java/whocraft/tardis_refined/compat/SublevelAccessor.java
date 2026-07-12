package whocraft.tardis_refined.compat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
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
import whocraft.tardis_refined.compat.sable.SableSublevelAccessor;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
        public boolean isBlockInSublevelSpace(Level level, Position pos) {
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
        public Optional<Sublevel> getContainingSublevelIfLoaded(Level level, Position position) {
            return Optional.empty();
        }
    };

    interface Sublevel {

        AABB toSublevelAABB(AABB aabb);

        Direction toSublevelDirection(Direction direction);

        BlockPos toMainLevelPos(BlockPos pos);

        Vec3 toMainLevelPos(Vec3 pos);

        default Direction toMainLevelDirection(Direction direction) {
            return vectorToDirection(toMainLevelAccurateDirection(direction));
        }

        Vector3d toMainLevelAccurateDirection(Direction direction);

        default Stream<BlockPos> toSublevelPositions(AABB worldAABB) {
            var shypAABB = toSublevelAABB(worldAABB);
            return BlockPos.betweenClosedStream(shypAABB);
        }

        AABB toMainLevelAABB(AABB aabb);

        Quaternionf toMainLevelRotation(Quaternionf rotation);

        Vec3 toMainLevelRotation(Vec3 direction);

        boolean isHorizontalEnough();

        static boolean isHorizontalEnough(Vector3d angles) {
            return Math.abs(angles.x) < Math.PI / 2 || Math.abs(angles.z) < Math.PI / 2;
        }

        Optional<Component> description();


        record Dummy(Optional<Component> description) implements Sublevel {

            @Override
            public AABB toSublevelAABB(AABB aabb) {
                return aabb;
            }

            @Override
            public Direction toSublevelDirection(Direction direction) {
                return direction;
            }

            @Override
            public BlockPos toMainLevelPos(BlockPos pos) {
                return pos;
            }

            @Override
            public Vec3 toMainLevelPos(Vec3 pos) {
                return pos;
            }

            @Override
            public Vector3d toMainLevelAccurateDirection(Direction direction) {
                return directionToVector(direction);
            }

            @Override
            public AABB toMainLevelAABB(AABB aabb) {
                return aabb;
            }

            @Override
            public Quaternionf toMainLevelRotation(Quaternionf rotation) {
                return rotation;
            }

            @Override
            public Vec3 toMainLevelRotation(Vec3 direction) {
                return direction;
            }

            @Override
            public boolean isHorizontalEnough() {
                return true;
            }

            @Override
            public Optional<Component> description() {
                return description;
            }
        }
    }

    interface PositionReference {
        Sublevel sublevel();
        Vec3 pos();
    }

    interface LoadablePositionReference {

        Codec<LoadablePositionReference> CODEC = SublevelPositionRegistry.REGISTRY.byNameCodec().dispatch(
                LoadablePositionReference::codec, Function.identity()
        );

        default void destroy(MinecraftServer server) {}

        Optional<PositionReference> tryLoad(MinecraftServer server);

        default Optional<Tag> toNbt() {
            return SublevelAccessor.LoadablePositionReference.CODEC.encodeStart(NbtOps.INSTANCE, this).resultOrPartial();
        }

        static Optional<LoadablePositionReference> fromNbt(Tag tag) {
            if (tag == null) return Optional.empty();
            return SublevelAccessor.LoadablePositionReference.CODEC.parse(NbtOps.INSTANCE, tag).resultOrPartial();
        }

        MapCodec<? extends LoadablePositionReference> codec();

    }

    default Optional<LoadablePositionReference> getPositionReference(Level level, BlockPos pos) {
        return Optional.empty();
    }

    boolean isChunkInSublevelSpace(Level level, ChunkPos pos);

    boolean isBlockInSublevelSpace(Level level, BlockPos pos);

    boolean isBlockInSublevelSpace(Level level, Position pos);

    Iterable<Sublevel> getSublevelsIntersecting(Level level, AABB aabb);

    default boolean collidesWithSublevel(Level level, BlockPos pos) {
        return collidesWithSublevel(level, AABB.of(new BoundingBox(pos)));
    }

    boolean collidesWithSublevel(Level level, AABB boundingBox);

    Optional<Sublevel> getContainingSublevelIfLoaded(Level level, Position position);

    default Optional<Sublevel> getContainingSublevelIfLoaded(Level level, BlockPos position) {
        return getContainingSublevelIfLoaded(level, position.getCenter());
    }

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
