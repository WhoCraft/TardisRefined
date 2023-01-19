package whocraft.tardis_refined.common.block.device;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.block.*;
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
import whocraft.tardis_refined.client.model.blockentity.console.ConsolePatterns;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.block.properties.ConsoleProperty;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.blockentity.device.ConsoleConfigurationBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;
import whocraft.tardis_refined.registry.SoundRegistry;

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
    ).reduce((v1, v2) -> Shapes.join(v1, v2, OR)).get();

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
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(FACING, context.getHorizontalDirection()).setValue(CONSOLE, ConsoleTheme.FACTORY);
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (interactionHand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }


        var offset = blockState.getValue(FACING).getNormal();
        BlockState consoleBlock = level.getBlockState(blockPos.offset(offset));
        ConsoleTheme nextTheme = blockState.getValue(ConsoleConfigurationBlock.CONSOLE).next();

        if (player.getMainHandItem().getItem() == ItemRegistry.PATTERN_MANIPULATOR.get()) {

            if (level.getBlockEntity(blockPos.offset(offset)) instanceof GlobalConsoleBlockEntity globalConsoleBlock) {
                ConsoleTheme console = globalConsoleBlock.getBlockState().getValue(GlobalConsoleBlock.CONSOLE);

                if (ConsolePatterns.getPatternsForTheme(console).size() == 1) {
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BIT.value(), SoundSource.PLAYERS, 100, (float) (0.1 + (level.getRandom().nextFloat() * 0.5)));
                    return InteractionResult.SUCCESS;
                }

                globalConsoleBlock.setPattern(ConsolePatterns.next(console, globalConsoleBlock.pattern()));
                PlayerUtil.sendMessage(player, Component.translatable(ModMessages.pattern(globalConsoleBlock.pattern())), true);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegistry.PATTERN_MANIPULATOR.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                globalConsoleBlock.sendUpdates();
                player.getCooldowns().addCooldown(ItemRegistry.PATTERN_MANIPULATOR.get(), 20);
            }

            return InteractionResult.SUCCESS;
        }

        if (player.getMainHandItem().getItem() == Items.IRON_BLOCK) {
            if (!(consoleBlock.getBlock() instanceof GlobalConsoleBlock)) {
                placeGlobalConsoleBlock(blockPos.offset(offset), blockState, level);
                if (!player.isCreative()) {
                    player.getMainHandItem().shrink(1);
                }
                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.FAIL;
            }
        }

        if (player.isCrouching()) {
            removeGlobalConsoleBlock(blockPos.offset(offset), level);
        } else {
            changeConsoleTheme(blockPos, blockState, nextTheme, level);
        }

        return InteractionResult.PASS;
    }

    /**
     * Places a Global Console block at the specified position, with the same theme as the Console Configuration block.
     *
     * @param pos   The position to place the Global Console block at.
     * @param state The state of the Console Configuration block that was used to place the Global Console block.
     * @param level The level the Global Console block will be placed in.
     */
    private void placeGlobalConsoleBlock(BlockPos pos, BlockState state, Level level) {
        level.setBlockAndUpdate(pos, BlockRegistry.GLOBAL_CONSOLE_BLOCK.get().defaultBlockState().setValue(GlobalConsoleBlock.CONSOLE, state.getValue(ConsoleConfigurationBlock.CONSOLE)));
        if (Platform.isClient()) {
            level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 3, 0.45f);
            playParticles(pos, level);
        }
    }

    /**
     * Removes the Global Console block at the specified position and kills its controls.
     *
     * @param pos   The position of the Global Console block to be removed.
     * @param level The level the Global Console block is in.
     */
    private void removeGlobalConsoleBlock(BlockPos pos, Level level) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof GlobalConsoleBlockEntity) {
            ((GlobalConsoleBlockEntity) blockEntity).killControls();
        }
        level.destroyBlock(pos, true);
    }

    /**
     * Changes the theme of the Console Configuration block and its adjacent Global Console block.
     *
     * @param pos   The position of the Console Configuration block.
     * @param state The state of the Console Configuration block.
     * @param theme The new theme for the Console Configuration and Global Console blocks.
     * @param level The level the Console Configuration and Global Console blocks are in.
     */
    private void changeConsoleTheme(BlockPos pos, BlockState state, ConsoleTheme theme, Level level) {
        level.setBlockAndUpdate(pos, state.setValue(ConsoleConfigurationBlock.CONSOLE, theme));
        BlockState consoleBlock = level.getBlockState(pos.offset(state.getValue(FACING).getNormal()));
        if (consoleBlock.getBlock() instanceof GlobalConsoleBlock) {
            level.setBlockAndUpdate(pos.offset(state.getValue(FACING).getNormal()), BlockRegistry.GLOBAL_CONSOLE_BLOCK.get().defaultBlockState().setValue(GlobalConsoleBlock.CONSOLE, theme));
            if (Platform.isClient()) {
                level.playSound(null, pos.offset(state.getValue(FACING).getNormal()), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 3, 0.45f);
                playParticles(pos, level);
            }
        }
    }

    private void playParticles(BlockPos pos, Level level) {
        for (int i = 0; i < 3; i++) {
            double xCoord = pos.getX() + level.getRandom().nextDouble();
            double yCoord = pos.getY() + level.getRandom().nextDouble() * 0.5D + 0.5D;
            double zCoord = pos.getZ() + level.getRandom().nextDouble();
            level.addParticle(ParticleTypes.FLASH, xCoord, yCoord, zCoord, 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.CLOUD, xCoord, yCoord, zCoord, 0.0D, 0.0D, 0.0D);
        }
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
