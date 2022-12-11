package whocraft.tardis_refined.common.block.door;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.desktop.door.RootShellDoorBlockEntity;

public class RootShellDoorBlock extends InternalDoorBlock {

    protected static final VoxelShape SOUTH_AABB, NORTH_AABB, WEST_AABB, EAST_AABB;


    public RootShellDoorBlock(Properties properties) {

        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, true));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RootShellDoorBlockEntity(blockPos, blockState);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        switch (blockState.getValue(FACING)) {
            case SOUTH -> {
                return SOUTH_AABB;
            }
            case NORTH -> {return NORTH_AABB;}
            case WEST -> {return WEST_AABB;}
            case EAST -> {return EAST_AABB;}
        }

        return SOUTH_AABB;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        switch(blockState.getValue(FACING)) {
            case EAST:
            default:
                return  EAST_AABB;
            case SOUTH:
                return SOUTH_AABB ;
            case WEST:
                return WEST_AABB;
            case NORTH:
                return NORTH_AABB;
        }
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(blockPos) instanceof RootShellDoorBlockEntity door) {
                if (entity instanceof Player player) {
                    door.onAttemptEnter(level,player);
                }
            }
        }
    }

    static {
        NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
        SOUTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
        WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    }
}
