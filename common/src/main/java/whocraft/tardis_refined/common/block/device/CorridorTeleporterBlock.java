package whocraft.tardis_refined.common.block.device;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.device.CorridorTeleporterBlockEntity;
import whocraft.tardis_refined.common.util.ClientHelper;

public class CorridorTeleporterBlock extends Block implements EntityBlock {

    public CorridorTeleporterBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CorridorTeleporterBlockEntity(blockPos, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.join(Block.box(2, 0, 2, 14, 2, 14), Block.box(4, 1, 4, 12, 3, 12), BooleanOp.OR);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return (level1, blockPos, blockState, t) -> {
            if (t instanceof CorridorTeleporterBlockEntity corridorTeleporterBlockEntity) {
                corridorTeleporterBlockEntity.tick(level, blockPos, blockState, corridorTeleporterBlockEntity);
            }
        };
    }

    
    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {

        if (!level.isClientSide()){
            ServerLevel serverLevel = (ServerLevel)level;
            if (serverLevel.getBlockEntity(blockPos) instanceof CorridorTeleporterBlockEntity corridorTeleporterBlockEntity) {
                corridorTeleporterBlockEntity.startTeleporterTimer();
            }
        } else {

            for (int i = 0; i < 5; i++) {
                ClientHelper.playParticle((ClientLevel) level, ParticleTypes.PORTAL, new Vec3(blockPos.getX() + 0.5f, blockPos.getY(), blockPos.getZ() + 0.5f), -0.5 + level.random.nextFloat(), 0.5f, -0.5 + level.random.nextFloat());
            }
        }
    }
}
