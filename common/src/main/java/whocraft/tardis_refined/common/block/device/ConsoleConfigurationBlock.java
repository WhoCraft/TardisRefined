package whocraft.tardis_refined.common.block.device;

import net.minecraft.world.level.block.state.properties.EnumProperty;

import com.mojang.brigadier.StringReader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.blockentity.device.ConsoleConfigurationBlockEntity;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.constants.ResourceConstants;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.registry.TRBlockRegistry;
import whocraft.tardis_refined.registry.TRDimensionTypes;
import whocraft.tardis_refined.registry.TRItemRegistry;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import java.util.List;
import java.util.stream.Stream;

import static net.minecraft.world.phys.shapes.BooleanOp.OR;

public class ConsoleConfigurationBlock extends BaseEntityBlock {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public static VoxelShape SHAPE = Stream.of(
            Shapes.join(Block.box(2, 6, 8, 14, 11, 8), Block.box(2, 6, 8, 14, 11, 8), OR),
            Block.box(5, 9, 5, 11, 9, 11),
            Block.box(4, 4, 4, 12, 6, 12),
            Block.box(2, 0, 2, 14, 4, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, OR)).get();

    public ConsoleConfigurationBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state.setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    protected InteractionResult useItemOn(net.minecraft.world.item.ItemStack stack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (interactionHand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (!player.level().isClientSide()) {

            BlockPos consolePos = findPairedConsolePos(level, blockPos, blockState.getValue(FACING));
            whocraft.tardis_refined.TardisRefined.LOGGER.info("Console configurator block interaction: player={}, item={}, position={}, facing={}, target={}, targetBlockEntity={}",
                    player.getGameProfile().name(), stack.getItem(), blockPos, blockState.getValue(FACING), consolePos,
                    level.getBlockEntity(consolePos) == null ? "none" : level.getBlockEntity(consolePos).getType());

            if (player.getMainHandItem().getItem() == TRItemRegistry.PATTERN_MANIPULATOR.get()) {
                this.changePattern(level, blockPos, consolePos, player);
                return InteractionResult.SUCCESS; //Use InteractionResult.SUCCESS for non-client side. Stops hand swinging twice. We don't want to use InteractionResult.SUCCESS because the client calls SUCCESS, so the server side calling it too sends the hand swinging packet twice.
            }


            if (level instanceof ServerLevel serverLevel) {
                if (serverLevel.dimensionTypeRegistration().is(TRDimensionTypes.TARDIS)) {
                    // A missing operator must not swallow the interaction. This can happen
                    // briefly while the interior capability is being initialized.
                    boolean inFlight = TardisLevelOperator.get(serverLevel)
                            .map(operator -> operator.getPilotingManager().isInFlight())
                            .orElse(false);
                    if (inFlight) {
                        PlayerUtil.sendMessage(player, Component.translatable(ModMessages.CONSOLE_CONFIGURATION_NOT_IN_FLIGHT), true);
                    } else if (player.isShiftKeyDown()) {
                        this.removeGlobalConsoleBlock(consolePos, level);
                    } else {
                        this.changeConsoleTheme(level, blockPos, consolePos);
                    }
                } else {
                    if (player.isShiftKeyDown()) { //If we are destroying the console block
                        this.removeGlobalConsoleBlock(consolePos, level);
                    } else {
                        this.changeConsoleTheme(level, blockPos, consolePos);
                    }
                }
            }


            return InteractionResult.SUCCESS; //Use InteractionResult.SUCCESS for non-client side. Stops hand swinging twice. We don't want to use InteractionResult.SUCCESS because the client calls SUCCESS, so the server side calling it too sends the hand swinging packet twice.
        }

        return InteractionResult.SUCCESS; //Use InteractionResult.SUCCESS for client side. Stops hand swinging twice. We don't want to use InteractionResult.SUCCESS because the client calls SUCCESS, so the server side calling it too sends the hand swinging packet twice.
    }

    /**
     * Places a Global Console block at the specified position, with the same theme as the Console Configuration block.
     *
     * @param level           The level the Global Console block will be placed in.
     * @param configuratorPos - The position where this current configurator block is at
     * @param consolePos      The position to place the Global Console block at.
     */
    private boolean placeNewGlobalConsoleBlock(Level level, BlockPos configuratorPos, BlockPos consolePos) {
        BlockEntity expectedConfiguratorBlockEntity = level.getBlockEntity(configuratorPos);

        if (expectedConfiguratorBlockEntity instanceof ConsoleConfigurationBlockEntity consoleConfigurationBlockEntity) {

            Identifier consoleThemeId = consoleConfigurationBlockEntity.theme();

            level.setBlockAndUpdate(consolePos, TRBlockRegistry.GLOBAL_CONSOLE_BLOCK.get().defaultBlockState());

            BlockEntity expectedConsoleBlockEntity = level.getBlockEntity(consolePos);

            if (expectedConsoleBlockEntity instanceof GlobalConsoleBlockEntity globalConsoleBlockEntity) { //Set console theme
                globalConsoleBlockEntity.setConsoleTheme(consoleThemeId);
                globalConsoleBlockEntity.setPattern(consoleConfigurationBlockEntity.pattern());

                level.playSound(null, consolePos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 3, 0.45f);

                if (level.isClientSide()) {
                    this.playParticles(configuratorPos, level);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the Global Console block at the specified position and kills its controls.
     *
     * @param consolePos The position of the Global Console block to be removed.
     * @param level      The level the Global Console block is in.
     */
    private boolean removeGlobalConsoleBlock(BlockPos consolePos, Level level) {
        BlockEntity blockEntity = level.getBlockEntity(consolePos);
        if (blockEntity instanceof GlobalConsoleBlockEntity consoleBlockEntity) {
            consoleBlockEntity.killControls();
            level.destroyBlock(consolePos, true);
            return true;
        }
        return false;
    }

    /**
     * Change the pattern of the console blocks' current theme
     *
     * @param level
     * @param configuratorPos
     * @param consolePos
     * @param player
     * @return
     */
    private boolean changePattern(Level level, BlockPos configuratorPos, BlockPos consolePos, Player player) {

        BlockEntity expectedConfiguratorBlockEntity = level.getBlockEntity(configuratorPos);

        BlockEntity expectedConsoleBlockEntity = level.getBlockEntity(consolePos);

        if (expectedConfiguratorBlockEntity instanceof ConsoleConfigurationBlockEntity consoleConfigurationBlockEntity) {
            if (expectedConsoleBlockEntity instanceof GlobalConsoleBlockEntity globalConsoleBlockEntity) { //Change console theme
                Identifier currentConsoleThemeId = globalConsoleBlockEntity.theme();

                if (ConsolePatterns.getPatternsForTheme(currentConsoleThemeId).size() == 1) { //If there is only one pattern for this theme, don't iterate and warn the player
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.NOTE_BLOCK_BIT.value(), SoundSource.PLAYERS, 100, (float) (0.1 + (level.getRandom().nextFloat() * 0.5)));
                    return false;
                }

                globalConsoleBlockEntity.setPattern(ConsolePatterns.next(currentConsoleThemeId, globalConsoleBlockEntity.pattern()));
                consoleConfigurationBlockEntity.setConsolePattern(globalConsoleBlockEntity.pattern());
                PlayerUtil.sendMessage(player, whocraft.tardis_refined.common.util.ComponentJson.parse(globalConsoleBlockEntity.pattern().name()), true);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), TRSoundRegistry.PATTERN_MANIPULATOR.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

                player.getCooldowns().addCooldown(player.getMainHandItem(), 20);
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the theme of the Console Configuration block and its adjacent Global Console block using the next theme from the configurator's current theme.
     *
     * @param level           The level the Console Configuration and Global Console blocks are in.
     * @param configuratorPos The position of the Console Configuration block.
     * @param consolePos      - The configurator block entity which we expect to not be null
     */
    private boolean changeConsoleTheme(Level level, BlockPos configuratorPos, BlockPos consolePos) {

        BlockEntity expectedConfiguratorBlockEntity = level.getBlockEntity(configuratorPos);

        BlockEntity expectedConsoleBlockEntity = level.getBlockEntity(consolePos);

        if (expectedConfiguratorBlockEntity instanceof ConsoleConfigurationBlockEntity configurationBlockEntity) { //Change console theme
            // When a configurator is paired after a console already exists, its saved
            // selection can be stale. Use the visible console as the starting point.
            if (expectedConsoleBlockEntity instanceof GlobalConsoleBlockEntity pairedConsole) {
                configurationBlockEntity.setConsoleTheme(pairedConsole.theme());
                configurationBlockEntity.setConsolePattern(pairedConsole.pattern());
            }
            Identifier nextTheme = this.nextTheme(configurationBlockEntity);
            if (nextTheme == null) return false;

            configurationBlockEntity.setConsoleTheme(nextTheme);

            if (expectedConsoleBlockEntity instanceof GlobalConsoleBlockEntity consoleBlockEntity) {

                consoleBlockEntity.setConsoleTheme(nextTheme);

                // Change theme and set the current pattern to the default
                ConsolePattern defaultOrEquivalentPattern = ConsolePatterns.getPatternOrDefault(nextTheme, ResourceConstants.DEFAULT_PATTERN_ID);

                if (defaultOrEquivalentPattern != null) {
                    consoleBlockEntity.setPattern(defaultOrEquivalentPattern);
                    configurationBlockEntity.setConsolePattern(defaultOrEquivalentPattern);
                    // Rebuild control entities only after the new theme is installed.
                    consoleBlockEntity.spawnControlEntities();

                    level.playSound(null, consolePos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 3, 0.45f);

                    if (level.isClientSide()) {
                        this.playParticles(configuratorPos, level);
                    }
                }
            }
            whocraft.tardis_refined.TardisRefined.LOGGER.info(
                    "Console configurator applied selection: position={}, pairedPosition={}, paired={}, theme={}",
                    configuratorPos, consolePos, expectedConsoleBlockEntity instanceof GlobalConsoleBlockEntity, nextTheme);
            return true;
        }
        whocraft.tardis_refined.TardisRefined.LOGGER.warn(
                "Console configurator could not apply selection: position={}, configuratorBlockEntity={}, pairedPosition={}, pairedBlockEntity={}",
                configuratorPos, expectedConfiguratorBlockEntity == null ? "none" : expectedConfiguratorBlockEntity.getType(),
                consolePos, expectedConsoleBlockEntity == null ? "none" : expectedConsoleBlockEntity.getType());
        return false;
    }

    /** Handles the amethyst screwdriver directly so item use cannot swallow the
     * configurator's normal block interaction. */
    public InteractionResult useScrewdriver(Level level, BlockPos configuratorPos, Player player) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        BlockState state = level.getBlockState(configuratorPos);
        if (!state.is(this)) return InteractionResult.PASS;
        BlockPos consolePos = findPairedConsolePos(level, configuratorPos, state.getValue(FACING));
        whocraft.tardis_refined.TardisRefined.LOGGER.info("Console configurator screwdriver dispatch: player={}, position={}, facing={}, target={}, targetBlockEntity={}",
                player.getGameProfile().name(), configuratorPos, state.getValue(FACING), consolePos,
                level.getBlockEntity(consolePos) == null ? "none" : level.getBlockEntity(consolePos).getType());
        if (level instanceof ServerLevel serverLevel
                && serverLevel.dimensionTypeRegistration().is(TRDimensionTypes.TARDIS)) {
            boolean inFlight = TardisLevelOperator.get(serverLevel)
                    .map(operator -> operator.getPilotingManager().isInFlight())
                    .orElse(false);
            if (inFlight) {
                PlayerUtil.sendMessage(player, Component.translatable(ModMessages.CONSOLE_CONFIGURATION_NOT_IN_FLIGHT), true);
            } else {
                this.changeConsoleTheme(level, configuratorPos, consolePos);
            }
        } else {
            this.changeConsoleTheme(level, configuratorPos, consolePos);
        }
        return InteractionResult.SUCCESS;
    }

    /** Finds a paired console, preferring a directly adjacent console. Some
     * desktop layouts place the configurator beside the console controls,
     * rather than directly beside the console block, so also search locally. */
    public static BlockPos findPairedConsolePos(Level level, BlockPos configuratorPos, Direction facing) {
        BlockPos adjacent = findAdjacentConsolePos(level, configuratorPos, facing);
        if (adjacent != null) return adjacent;

        BlockPos nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        int searchRadius = 8;
        for (BlockPos candidate : BlockPos.betweenClosed(
                configuratorPos.offset(-searchRadius, -searchRadius, -searchRadius),
                configuratorPos.offset(searchRadius, searchRadius, searchRadius))) {
            if (!(level.getBlockEntity(candidate) instanceof GlobalConsoleBlockEntity)) continue;
            double distance = candidate.distToCenterSqr(configuratorPos.getX() + 0.5,
                    configuratorPos.getY() + 0.5, configuratorPos.getZ() + 0.5);
            if (distance < nearestDistance) {
                nearest = candidate.immutable();
                nearestDistance = distance;
            }
        }
        return nearest != null ? nearest : configuratorPos.relative(facing);
    }

    /** Returns only a directly adjacent console, used to avoid drawing the
     * hologram on top of a console occupying the neighboring block. */
    public static @Nullable BlockPos findAdjacentConsolePos(Level level, BlockPos configuratorPos, Direction facing) {
        BlockPos facingPos = configuratorPos.relative(facing);
        if (level.getBlockEntity(facingPos) instanceof GlobalConsoleBlockEntity) return facingPos;
        BlockPos oppositePos = configuratorPos.relative(facing.getOpposite());
        if (level.getBlockEntity(oppositePos) instanceof GlobalConsoleBlockEntity) return oppositePos;
        return null;
    }

    private Identifier nextTheme(ConsoleConfigurationBlockEntity blockEntity) {
        Identifier consoleThemeId = blockEntity.theme();
        //Get next console theme
        List<Identifier> themesList = ConsoleTheme.CONSOLE_THEME_DEFERRED_REGISTRY.keySet().stream().toList();
        if (themesList.isEmpty()) return null;
        int index = themesList.indexOf(consoleThemeId);
        int nextIndex = index + 1;
        if (nextIndex >= themesList.size()) {
            nextIndex = 0;
        }
        return themesList.get(nextIndex);

    }

    private void playParticles(BlockPos pos, Level level) {
        for (int i = 0; i < 3; i++) {
            double xCoord = pos.getX() + level.getRandom().nextDouble();
            double yCoord = pos.getY() + level.getRandom().nextDouble() * 0.5D + 0.5D;
            double zCoord = pos.getZ() + level.getRandom().nextDouble();
            level.addParticle(net.minecraft.core.particles.ColorParticleOption.create(ParticleTypes.FLASH, 0xFFFFFFFF), xCoord, yCoord, zCoord, 0.0D, 0.0D, 0.0D);
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
