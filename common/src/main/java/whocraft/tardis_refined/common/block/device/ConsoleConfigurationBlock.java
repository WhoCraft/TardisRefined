package whocraft.tardis_refined.common.block.device;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DebugStickItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.block.properties.ConsoleProperty;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.blockentity.device.ConsoleConfigurationBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.registry.BlockRegistry;

import java.util.stream.Stream;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;


public class ConsoleConfigurationBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final ConsoleProperty CONSOLE = ConsoleProperty.create("console");

    public static VoxelShape SHAPE = Stream.of(
            Shapes.join(Block.box(2, 6, 8, 14, 11, 8), Block.box(2, 6, 8, 14, 11, 8), OR),
            Block.box(5, 9, 5, 11, 9, 11),
            Block.box(4, 4, 4, 12, 6, 12),
            Block.box(2, 0, 2, 14, 4, 14)
    ).reduce( (v1, v2) -> Shapes.join(v1, v2, OR)).get();

    public ConsoleConfigurationBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CONSOLE, ConsoleTheme.FACTORY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, CONSOLE);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(CONSOLE, ConsoleTheme.FACTORY);
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (interactionHand == InteractionHand.MAIN_HAND) {
            var offset = blockState.getValue(FACING).getOpposite().getNormal();
            BlockState consoleBlock = level.getBlockState(blockPos.offset(offset));
            ConsoleTheme nextTheme = blockState.getValue(ConsoleConfigurationBlock.CONSOLE).next();


            if (player.getMainHandItem().getItem() == Items.IRON_BLOCK) {
                if (!(consoleBlock.getBlock() instanceof GlobalConsoleBlock)) {
                    level.setBlockAndUpdate(blockPos.offset(offset), BlockRegistry.GLOBAL_CONSOLE_BLOCK.get().defaultBlockState().setValue(GlobalConsoleBlock.CONSOLE, blockState.getValue(ConsoleConfigurationBlock.CONSOLE)));
                    player.getMainHandItem().setCount(player.getMainHandItem().getCount() - 1);
                    if (Platform.isClient()) {
                        level.playSound(null, blockPos.offset(offset), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 3, 0.45f);
                        int i;
                        double d;
                        double e;
                        double f;
                        for (i = 0; i < 3; ++i) {
                            d = (double) blockPos.offset(offset).getX() + level.getRandom().nextDouble();
                            e = (double) blockPos.offset(offset).getY() + level.getRandom().nextDouble() * 0.5D + 0.5D;
                            f = (double) blockPos.offset(offset).getZ() + level.getRandom().nextDouble();
                            level.addParticle(ParticleTypes.FLASH, d, e, f, 0.0D, 0.0D, 0.0D);
                            level.addParticle(ParticleTypes.CLOUD, d, e, f, 0.0D, 0.0D, 0.0D);
                        }
                    }
                    return InteractionResult.CONSUME;
                } else {
                    return InteractionResult.FAIL;
                }
            } else {

                if (player.isCrouching()) {
                    if (level.getBlockEntity(blockPos.offset(offset)) instanceof GlobalConsoleBlockEntity globalConsoleBlock) {
                        globalConsoleBlock.killControls();
                    }
                    level.destroyBlock(blockPos.offset(offset), true);
                } else {
                    level.setBlockAndUpdate(blockPos, blockState.setValue(ConsoleConfigurationBlock.CONSOLE, nextTheme));
                    if ((consoleBlock.getBlock() instanceof GlobalConsoleBlock)) {
                        level.setBlockAndUpdate(blockPos.offset(offset), BlockRegistry.GLOBAL_CONSOLE_BLOCK.get().defaultBlockState().setValue(GlobalConsoleBlock.CONSOLE, nextTheme));
                        if (Platform.isClient()) {
                            level.playSound(null, blockPos.offset(offset), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 3, 0.45f);
                            int i;
                            double d;
                            double e;
                            double f;
                            for (i = 0; i < 3; ++i) {
                                d = (double) blockPos.offset(offset).getX() + level.getRandom().nextDouble();
                                e = (double) blockPos.offset(offset).getY() + level.getRandom().nextDouble() * 0.5D + 0.5D;
                                f = (double) blockPos.offset(offset).getZ() + level.getRandom().nextDouble();
                                level.addParticle(ParticleTypes.FLASH, d, e, f, 0.0D, 0.0D, 0.0D);
                                level.addParticle(ParticleTypes.CLOUD, d, e, f, 0.0D, 0.0D, 0.0D);
                            }
                        }
                    }
                }
            }
        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ConsoleConfigurationBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }
}
