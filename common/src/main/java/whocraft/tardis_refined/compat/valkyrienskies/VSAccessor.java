package whocraft.tardis_refined.compat.valkyrienskies;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.compat.SublevelAccessor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

//Dummy code which might become useful again in the future.
public class VSAccessor implements SublevelAccessor {
    public static class VSShip implements Sublevel {

        /*private final Ship ship;

        public VSShip(Ship ship) {
            this.ship = ship;
        }*/

        @Override
        public AABB toSublevelAABB(AABB aabb) {
            /*AABBd worldAABB = VectorConversionsMCKt.toJOML(aabb);
            return VectorConversionsMCKt.toMinecraft(worldAABB.transform(ship.getWorldToShip()));*/
            return aabb;
        }

        @Override
        public Direction toSublevelDirection(Direction direction) {
            //return vectorToDirection(ship.getWorldToShip().transformDirection(directionToVector(direction)));
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
            return SublevelAccessor.directionToVector(direction);
        }

        @Override
        public Stream<BlockPos> toSublevelPositions(AABB worldAABB) {
            var shypAABB = toSublevelAABB(worldAABB);
            return BlockPos.betweenClosedStream(shypAABB);
        }

        @Override
        public boolean isHorizontalEnough() {
            /*var angles = ship.getTransform().getShipToWorldRotation().getEulerAnglesZYX(new Vector3d());
            return Math.abs(angles.x) < Math.PI / 2 || Math.abs(angles.z) < Math.PI / 2;*/
            return false;
        }

        @Override
        public Optional<Component> description() {
            return Optional.empty();
        }

    }

    @Override
    public boolean isChunkInSublevelSpace(Level level, ChunkPos pos) {
        //return VS2ChunkAllocator.INSTANCE.isChunkInShipyardCompanion(pos.x, pos.z);
        return false;
    }

    @Override
    public boolean isBlockInSublevelSpace(Level level, BlockPos pos) {
        //return VSGameUtilsKt.isBlockInShipyard(level, pos);
        return false;
    }

    @Override
    public boolean isBlockInSublevelSpace(Level level, Position pos) {
        return false;
    }

    @Override
    public Iterable<Sublevel> getSublevelsIntersecting(Level level, AABB aabb) {
        //return Iterables.transform(VSGameUtilsKt.getShipsIntersecting(level, aabb), VSShip::new);
        return List.of();
    }

    @Override
    public boolean collidesWithSublevel(Level level, BlockPos pos) {
        return collidesWithSublevel(level, AABB.of(new BoundingBox(pos)));
    }

    @Override
    public boolean collidesWithSublevel(Level level, AABB boundingBox) {
        // not an exact collision, because we use an axis-aligned bounding box, but good enough
        /*AABBd bb = VectorConversionsMCKt.toJOML(boundingBox);
        Ship currentShip = VSGameUtilsKt.getShipManagingPos(level, boundingBox.getCenter());
        if (currentShip != null) {
            bb.transform(currentShip.getShipToWorld());
        }

        for(Ship ship : VSGameUtilsKt.getShipsIntersecting(level, bb)) {
            if (ship == currentShip)
                continue;

            AABBd transformedBB = bb.transform(ship.getWorldToShip(), new AABBd());
            Stream<BlockPos> blocks = BlockPos.betweenClosedStream(VectorConversionsMCKt.toMinecraft(transformedBB));
            boolean bbEmpty = blocks.allMatch(blockPos -> {
                BlockState state = level.getBlockState(blockPos);
                return state.getCollisionShape(level, blockPos).isEmpty() && state.getFluidState().isEmpty();
            });
            if (!bbEmpty)
                return true;
        }*/
        return false;
    }

    @Override
    public AABB toMainLevelAABB(Level level, BlockPos pos, AABB aabb) {
        /*Ship ship = VSGameUtilsKt.getShipManagingPos(level, pos);
        if (ship != null) {
            Vector3d min = new Vector3d();
            Vector3d max = new Vector3d();
            ship.getShipToWorld().transformAab(
                    aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ,
                    min, max
            );
            return new AABB(min.x, min.y, min.z, max.x, max.y, max.z);
        } else {
            return aabb;
        }*/
        return aabb;
    }

    @Override
    public Quaternionf toMainLevelRotation(Level level, BlockPos position, Quaternionf rotation) {
        /*Ship ship = VSGameUtilsKt.getShipManagingPos(level, position);
        if (ship != null) {
            return new Quaternionf().setFromNormalized(ship.getShipToWorld()).mul(rotation);
        } else {
            return rotation;
        }*/
        return rotation;
    }

    @Override
    public Vector3d toMainLevelRotation(Level level, BlockPos position, Direction direction) {
        /*Ship ship = VSGameUtilsKt.getShipManagingPos(level, position);
        if (ship != null) {
            return ship.getShipToWorld().transformDirection(directionToVector(direction));
        } else {
            return directionToVector(direction);
        }*/
        return SublevelAccessor.directionToVector(direction);
    }

    @Override
    public Vec3 toMainLevelRotation(Level level, BlockPos position, Vec3 direction) {
        /*Ship ship = VSGameUtilsKt.getShipManagingPos(level, position);
        if (ship != null) {
            return vectorToVector(ship.getShipToWorld().transformDirection(vectorToVector(direction)));
        } else {
            return direction;
        }*/
        return direction;
    }

    @Override
    public BlockPos toMainLevelPosition(Level level, BlockPos blockPos) {
        /*Vector3d pos = blockPosToVector(blockPos);
        Vector3d worldPos = VSGameUtilsKt.getWorldCoordinates(level, blockPos, pos);
        return vectorToBlockPos(worldPos);*/
        return blockPos;
    }

    @Override
    public Vec3 toMainLevelPosition(Level level, BlockPos blockPos, Vec3 vector) {
        /*Vector3d pos = VectorConversionsMCKt.toJOML(vector);
        Vector3d worldPos = VSGameUtilsKt.getWorldCoordinates(level, blockPos, pos);
        return VectorConversionsMCKt.toMinecraft(worldPos);*/
        return vector;
    }

    @Override
    public Vec2 toMainLevelRotation(Level level, BlockPos blockPos, Vec2 rotation) {
        /*var ship = VSGameUtilsKt.getShipManagingPos(level, blockPos);
        if (ship != null) {
            return vectorToRotation(ship.getShipToWorld().transformDirection(rotationToVector(rotation)));
        }*/
        return rotation;
    }
}
