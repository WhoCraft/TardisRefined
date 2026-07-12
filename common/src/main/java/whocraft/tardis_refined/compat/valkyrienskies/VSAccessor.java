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
            /*Vector3d pos = blockPosToVector(blockPos);
            Vector3d worldPos = VSGameUtilsKt.getWorldCoordinates(level, blockPos, pos);
            return vectorToBlockPos(worldPos);*/
            return pos;
        }

        @Override
        public Vec3 toMainLevelPos(Vec3 pos) {
            /*Vector3d pos = VectorConversionsMCKt.toJOML(vector);
            Vector3d worldPos = VSGameUtilsKt.getWorldCoordinates(level, blockPos, pos);
            return VectorConversionsMCKt.toMinecraft(worldPos);*/
            return pos;
        }

        @Override
        public Vector3d toMainLevelAccurateDirection(Direction direction) {
            //ship.getShipToWorld().transformDirection(directionToVector(direction))
            return SublevelAccessor.directionToVector(direction);
        }

        @Override
        public Stream<BlockPos> toSublevelPositions(AABB worldAABB) {
            var shypAABB = toSublevelAABB(worldAABB);
            return BlockPos.betweenClosedStream(shypAABB);
        }

        @Override
        public AABB toMainLevelAABB(AABB aabb) {
            /*Vector3d min = new Vector3d();
            Vector3d max = new Vector3d();
            ship.getShipToWorld().transformAab(
                    aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ,
                    min, max
            );
            return new AABB(min.x, min.y, min.z, max.x, max.y, max.z);*/
            return aabb;
        }

        @Override
        public Quaternionf toMainLevelRotation(Quaternionf rotation) {
            //new Quaternionf().setFromNormalized(ship.getShipToWorld()).mul(rotation);
            return rotation;
        }

        @Override
        public Vec3 toMainLevelRotation(Vec3 direction) {
            //vectorToVector(ship.getShipToWorld().transformDirection(vectorToVector(direction)))
            return direction;
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
    public Optional<Sublevel> getContainingSublevelIfLoaded(Level level, Position position) {
        return Optional.empty();
    }
}
