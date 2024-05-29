package whocraft.tardis_refined.common.block.shell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.blockentity.shell.RootedShellBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.ShellBaseBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisExteriorManager;
import whocraft.tardis_refined.common.tardis.manager.TardisInteriorManager;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RootedShellBlock extends ShellBaseBlock {

    public RootedShellBlock(BlockBehaviour.Properties properties) {

        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(REGEN, false).setValue(WATERLOGGED, false).setValue(LOCKED, false));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (!player.getMainHandItem().is(Items.SHEARS)) {

            if (!blockState.getValue(OPEN)) {
                PlayerUtil.sendMessage(player, Component.translatable(ModMessages.ROOT_PLANT_CUT_OPEN), true);
                level.playSound(player, blockPos, SoundEvents.AZALEA_LEAVES_HIT, SoundSource.BLOCKS, 1, 0.75f + level.getRandom().nextFloat());
                return InteractionResult.SUCCESS;
            }


            return InteractionResult.FAIL;
        }


        this.setUpTardis(blockState, level, blockPos);

        if (player != null) {

            player.getMainHandItem().hurtAndBreak(1, player, arg2 -> arg2.broadcastBreakEvent(interactionHand));
            level.playSound(player, player.blockPosition(), SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.playSound(player, player.blockPosition(), SoundEvents.SLIME_JUMP, SoundSource.BLOCKS, 1.0f, 1.0f);
        }


        return InteractionResult.SUCCESS; //Prevents processing the ShellBaseBlockEntity generating another UUID and causing a second dimension to be created
    }


    private boolean setUpTardis(BlockState blockState, Level level, BlockPos blockPos) {
        if (level instanceof ServerLevel serverLevel) {
            if (level.getBlockEntity(blockPos) instanceof ShellBaseBlockEntity shellBaseBlockEntity) {
                if (shellBaseBlockEntity.shouldSetup()) {


                    //Create a Level Key with a randomised UUID
                    ResourceKey<Level> generatedLevelKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(TardisRefined.MODID, UUID.randomUUID().toString()));

                    //Create the Level on demand which will create our capability
                    ServerLevel interior = DimensionHandler.getOrCreateInterior(serverLevel, generatedLevelKey.location());

                    // Set the UUID on the block entity.
                    shellBaseBlockEntity.setTardisId(generatedLevelKey);

                    TardisLevelOperator.get(interior).ifPresent(tardisLevelOperator -> {
                        if (!tardisLevelOperator.hasInitiallyGenerated()) {
                            tardisLevelOperator.setupInitialCave(serverLevel, blockState, blockPos);
                        }
                    });

                    return true;
                }
            }
        }
        return false;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RootedShellBlockEntity(blockPos, blockState);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        BlockState state = super.getStateForPlacement(blockPlaceContext);
        return state.setValue(FACING, blockPlaceContext.getHorizontalDirection()).setValue(OPEN, false).setValue(REGEN, false);
    }
}
