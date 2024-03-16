package whocraft.tardis_refined.common.tardis;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.BlockRegistry;

public class CorridorGenerator {

    private static int TEMPLATE_SIZE = 8;
    private static int PLACEMENT_OFFSET = 6;

    // Generate from a position in world space and create the structure to be saved.
    public static void generateFromPosition(Level level, BlockPos corner, BlockPos resultLocation) {

        // Create a 2D array.
        BlockState[][] hasBlockVector = new BlockState[8][8];

        for (int x = 0; x < TEMPLATE_SIZE; x++) {
            for (int z = 0; z < TEMPLATE_SIZE; z++) {
                hasBlockVector[x][z] = level.getBlockState(new BlockPos(corner.getX() + 1 + x, corner.getY(), corner.getZ() + 1 + z));
            }
        }

        for (var blockPos:
             BlockPos.betweenClosed(corner.above(9),  new BlockPos( corner.above(9).getX() + 48, corner.above(9).getY() +28, corner.above(9).getZ() + 48))) {

            level.setBlock(blockPos, Blocks.STRUCTURE_VOID.defaultBlockState(), Block.UPDATE_ALL);

        }


        // Place structures if the position is correct.
        for (int x = 0; x < TEMPLATE_SIZE; x++) {
            for (int z = 0; z < TEMPLATE_SIZE; z++) {

                BlockState currentBlockState = hasBlockVector[x][z];
                if (currentBlockState.isAir()) {
                    continue;
                } else {

                    // Get direction of the placement to place, ya dig?
                    // NORTH, SOUTH, EAST, WEST
                    BlockState[] directionBlockStates = new BlockState[4];

                    directionBlockStates[0] = getBlockStateAtGridPos(hasBlockVector, x, z-1);
                    directionBlockStates[1] = getBlockStateAtGridPos(hasBlockVector, x, z+1);
                    directionBlockStates[2] = getBlockStateAtGridPos(hasBlockVector, x+1, z);
                    directionBlockStates[3] = getBlockStateAtGridPos(hasBlockVector, x-1, z);


                    // Place the final piece.
                    int finalX = x;
                    int finalZ = z;
                    level.getServer().getStructureManager().get(getStructureResourceLocationByType(currentBlockState, directionBlockStates)).ifPresent(structure -> {

                        BlockPos position = new BlockPos(corner.getX() + (finalX * PLACEMENT_OFFSET), corner.getY() + 10, corner.getZ() + (finalZ * PLACEMENT_OFFSET));
                        StructurePlaceSettings settings = new StructurePlaceSettings();
                        ServerLevel slevel = (ServerLevel) level;
                        structure.placeInWorld(slevel, position, position, settings, level.getRandom(), 3);
                    });

                }

            }
        }

        level.setBlockAndUpdate(corner.above(9), BlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get().defaultBlockState());
    }

    private static ResourceLocation getStructureResourceLocationByType(BlockState blockState, BlockState[] results) {

        if (blockState == Blocks.GLOWSTONE.defaultBlockState()) {
            return getRoomPieceResourceLocation(results);
        }

        if (blockState == Blocks.REDSTONE_BLOCK.defaultBlockState()) {
            return getRoomPieceConnectionResourceLocation(results);
        }


        return getCorridorPieceResourceLocation(results);

    }

    private static BlockState getBlockStateAtGridPos(BlockState[][] states, int x, int z) {

        if (x < 0 || x >= TEMPLATE_SIZE || z < 0 || z >= TEMPLATE_SIZE) return Blocks.STONE.defaultBlockState();

        BlockState state = states[x][z];

        return state;
    }

    private static ResourceLocation getRoomPieceConnectionResourceLocation(BlockState[] results) {
        // CHECK IF NORTH/SOUTH/EAST/WEST
        if (doesBlockExist(results[0]) && doesBlockExist(results[1]) && !doesBlockExist(results[2]) && !doesBlockExist(results[3])) {
            return createResourceLocation( "room_entry_ns");
        }

        if (!doesBlockExist(results[0]) && !doesBlockExist(results[1]) && doesBlockExist(results[2]) && doesBlockExist(results[3])) {
            return createResourceLocation( "room_entry_ew");
        }

        return new ResourceLocation("");
    }

    private static ResourceLocation getRoomPieceResourceLocation(BlockState[] results) {


        if (doesBlockExist(results[0]) && doesBlockExist(results[1]) && doesBlockExist(results[2]) && doesBlockExist(results[3])) {

            if (isBlockRoomConnection(results[0])) {
                return createResourceLocation( "room_door_n");
            }
            if (isBlockRoomConnection(results[1])) {
                return createResourceLocation( "room_door_s");
            }
            if (isBlockRoomConnection(results[2])) {
                return createResourceLocation( "room_door_e");
            }
            if (isBlockRoomConnection(results[3])) {
                return createResourceLocation( "room_door_w");
            }


            return createResourceLocation( "room_center");
        }

        // CHECK IF NORTH/SOUTH/EAST/WEST
        if (doesBlockExist(results[0]) && !doesBlockExist(results[1]) && doesBlockExist(results[2]) && !doesBlockExist(results[3])) {
            return createResourceLocation( "room_wall_ne");
        }

        if (doesBlockExist(results[0]) && !doesBlockExist(results[1]) && !doesBlockExist(results[2]) && doesBlockExist(results[3])) {
            return createResourceLocation( "room_wall_nw");
        }

        if (!doesBlockExist(results[0]) && doesBlockExist(results[1]) && doesBlockExist(results[2]) && !doesBlockExist(results[3])) {
            return createResourceLocation( "room_wall_se");
        }

        if (!doesBlockExist(results[0]) && doesBlockExist(results[1]) && !doesBlockExist(results[2]) && doesBlockExist(results[3])) {
            return createResourceLocation( "room_wall_sw");
        }

        if (doesBlockExist(results[0]) && !doesBlockExist(results[1]) && doesBlockExist(results[2]) && doesBlockExist(results[3])) {

            if (isBlockRoomConnection(results[1])) {
                return createResourceLocation( "room_door_s");
            }

            return createResourceLocation( "room_wall_nwe");
        }

        if (doesBlockExist(results[0]) && doesBlockExist(results[1]) && doesBlockExist(results[2]) && !doesBlockExist(results[3])) {

            if (isBlockRoomConnection(results[3])) {
                return createResourceLocation( "room_door_w");
            }

            return createResourceLocation( "room_wall_nse");

        }

        if (!doesBlockExist(results[0]) && doesBlockExist(results[1]) && doesBlockExist(results[2]) && doesBlockExist(results[3])) {

            if (isBlockRoomConnection(results[0])) {
                return createResourceLocation( "room_door_n");
            }

            return createResourceLocation( "room_wall_sew");
        }

        if (doesBlockExist(results[0]) && doesBlockExist(results[1]) && !doesBlockExist(results[2]) && doesBlockExist(results[3])) {

            if (isBlockRoomConnection(results[2])) {
                return createResourceLocation( "room_door_e");
            }

            return createResourceLocation( "room_wall_nsw");
        }




        return new ResourceLocation("");
    }

    private static boolean doesBlockExist(BlockState blockState) {
        return !blockState.isAir();
    }

    private static boolean isBlockRoomConnection(BlockState blockState) {
        return blockState == Blocks.REDSTONE_BLOCK.defaultBlockState();
    }


    private static ResourceLocation getCorridorPieceResourceLocation(BlockState[] results) {

        // CHECK IF NORTH/SOUTH/EAST/WEST
        if (doesBlockExist(results[0]) && doesBlockExist(results[1]) && !doesBlockExist(results[2]) && !doesBlockExist(results[3])) {
            return createResourceLocation( "corridor_piece_ns");
        }

        if (!doesBlockExist(results[0]) && !doesBlockExist(results[1]) && doesBlockExist(results[2]) && doesBlockExist(results[3])) {
            return createResourceLocation( "corridor_piece_ew");
        }

        if (doesBlockExist(results[0]) && doesBlockExist(results[1]) && doesBlockExist(results[2]) && doesBlockExist(results[3])) {
            return createResourceLocation( "corridor_piece_cross");
        }

        // CHECK IF NORTH/SOUTH/EAST/WEST
        if (doesBlockExist(results[0]) && !doesBlockExist(results[1]) && doesBlockExist(results[2]) && !doesBlockExist(results[3])) {
            return createResourceLocation( "corridor_piece_ne");
        }

        if (doesBlockExist(results[0]) && !doesBlockExist(results[1]) && !doesBlockExist(results[2]) && doesBlockExist(results[3])) {
            return createResourceLocation( "corridor_piece_nw");
        }

        if (!doesBlockExist(results[0]) && doesBlockExist(results[1]) && doesBlockExist(results[2]) && !doesBlockExist(results[3])) {
            return createResourceLocation( "corridor_piece_se");
        }

        if (!doesBlockExist(results[0]) && doesBlockExist(results[1]) && !doesBlockExist(results[2]) && doesBlockExist(results[3])) {
            return createResourceLocation( "corridor_piece_sw");
        }

        if (doesBlockExist(results[0]) && !doesBlockExist(results[1]) && doesBlockExist(results[2]) && doesBlockExist(results[3])) {
            return createResourceLocation( "corridor_piece_new");
        }

        if (doesBlockExist(results[0]) && doesBlockExist(results[1]) && doesBlockExist(results[2]) && !doesBlockExist(results[3])) {
            return createResourceLocation( "corridor_piece_nse");
        }

        if (!doesBlockExist(results[0]) && doesBlockExist(results[1]) && doesBlockExist(results[2]) && doesBlockExist(results[3])) {
            return createResourceLocation( "corridor_piece_sew");
        }

        if (doesBlockExist(results[0]) && doesBlockExist(results[1]) && !doesBlockExist(results[2]) && doesBlockExist(results[3])) {
            return createResourceLocation( "corridor_piece_nsw");
        }

        return new ResourceLocation("");
    }

    private static ResourceLocation createResourceLocation(String id) {
        return new ResourceLocation(TardisRefined.MODID, "corridor_template/" + id);
    }

}

