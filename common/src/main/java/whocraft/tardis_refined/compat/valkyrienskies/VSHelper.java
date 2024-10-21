package whocraft.tardis_refined.compat.valkyrienskies;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.primitives.AABBd;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VS2ChunkAllocator;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.mod.common.util.VectorConversionsMCKt;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class VSHelper {
    public static boolean isChunkInShipyard(ChunkPos pos) {
        return VS2ChunkAllocator.INSTANCE.isChunkInShipyardCompanion(pos.x, pos.z);
    }

    public static boolean isBlockInShipyard(Level level, BlockPos pos) {
        return VSGameUtilsKt.isBlockInShipyard(level, pos);
    }

    public static void forEachShipInAABB(Level level, AABB aabb, Consumer<AABB> consumer) {
        AABBd worldAABB = VectorConversionsMCKt.toJOML(aabb);
        for (Ship ship : VSGameUtilsKt.getShipsIntersecting(level, aabb)) {
            consumer.accept(VectorConversionsMCKt.toMinecraft(worldAABB.transform(ship.getWorldToShip())));
        }
    }

    public static boolean collidesWithShip(Level level, AABB boundingBox) {
        // not an exact collision, because we use an axis-aligned bounding box, but good enough
        AABBd bb = VectorConversionsMCKt.toJOML(boundingBox);
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
        }
        return false;
    }

    public static boolean isBlockOnShip(Level level, BlockPos pos) {
        Ship ship = VSGameUtilsKt.getShipManagingPos(level, pos);
        var ships = VSGameUtilsKt.getShipsIntersecting(level, new AABB(0, 0, 0, 0, 0, 0));
        if (ship == null) {
            return false;
        } else {
            return true;
        }
    }

    public static Vector3d toWorldRotation(Level level, BlockPos position, Direction direction) {
        Ship ship = VSGameUtilsKt.getShipManagingPos(level, position);
        if (ship != null) {
            return ship.getShipToWorld().transformDirection(directionToVector(direction));
        } else {
            return directionToVector(direction);
        }
    }

    public static BlockPos toWorldPosition(Level level, BlockPos blockPos) {
        Vector3d pos = blockPosToVector(blockPos);
        Vector3d worldPos = VSGameUtilsKt.getWorldCoordinates(level, blockPos, pos);
        return vectorToBlockPos(worldPos);
    }

    public static Vec3 toWorldPosition(Level level, BlockPos blockPos, Vec3 vector) {
        Vector3d pos = VectorConversionsMCKt.toJOML(vector);
        Vector3d worldPos = VSGameUtilsKt.getWorldCoordinates(level, blockPos, pos);
        return VectorConversionsMCKt.toMinecraft(worldPos);
    }

    public static TardisNavLocation toWorldLocation(TardisNavLocation input) {
        Ship ship = VSGameUtilsKt.getShipManagingPos(input.getLevel(), input.getPosition());
        if (ship != null) {
            BlockPos pos = vectorToBlockPos(ship.getShipToWorld().transformPosition(blockPosToVector(input.getPosition())));
            Direction dir = vectorToDirection(ship.getShipToWorld().transformDirection(directionToVector(input.getDirection())));
            TardisNavLocation output = new TardisNavLocation(pos, dir, input.getLevel());
            output.setName(input.getName());
            return output;
        } else {
            return input;
        }
    }

    public static Vector3d blockPosToVector(BlockPos blockPos) {
        return new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static BlockPos vectorToBlockPos(Vector3d vector) {
        return new BlockPos((int) Math.round(vector.x), (int) Math.round(vector.y), (int) Math.round(vector.z));
    }

    public static Vector3d directionToVector(Direction direction) {
        return new Vector3d(direction.getStepX(), direction.getStepY(), direction.getStepZ());
    }

    public static Direction vectorToDirection(Vector3d vector) {
        return Direction.getNearest(vector.x, vector.y, vector.z);
    }
}
