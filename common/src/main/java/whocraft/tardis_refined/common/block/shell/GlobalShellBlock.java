package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.properties.ShellProperty;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;

public class GlobalShellBlock extends ShellBaseBlock{

    public static final ShellProperty SHELL = ShellProperty.create("shell");

    public GlobalShellBlock(Properties properties) {

        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(SHELL, ShellTheme.FACTORY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SHELL);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        return super.getStateForPlacement(blockPlaceContext).setValue(SHELL, ShellTheme.FACTORY);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GlobalShellBlockEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (level instanceof ServerLevel serverLevel) {
            if (blockHitResult.getDirection().getOpposite() == blockState.getValue(FACING)) {
                if (serverLevel.getBlockEntity(blockPos) instanceof GlobalShellBlockEntity entity) {



                    /*Locking Logic, would be inside onRightClick but not enough access*/
                    ItemStack itemStack = player.getItemInHand(interactionHand);
                    if (itemStack.getItem() instanceof KeyItem) {

                        ResourceKey<Level> dimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(TardisRefined.MODID, entity.id.toString()));

                        boolean validKey = KeyItem.keychainContains(itemStack, dimension);
                        if (validKey) {
                            BlockState state = blockState.cycle(LOCKED);
                            boolean locked = state.getValue(LOCKED);
                            level.setBlock(blockPos, state, 77);
                            TardisLevelOperator.get(Platform.getServer().getLevel(dimension)).ifPresent(tardisLevelOperator -> tardisLevelOperator.getExteriorManager().setLocked(locked));
                        }
                    }
                    entity.onRightClick(blockState);
                    return InteractionResult.SUCCESS;
                }

            }
        }

        return InteractionResult.SUCCESS;
    }
}
