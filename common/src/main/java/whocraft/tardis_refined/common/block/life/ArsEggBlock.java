package whocraft.tardis_refined.common.block.life;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.life.ArsEggBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.network.messages.upgrades.S2CDisplayUpgradeScreen;

public class ArsEggBlock extends BaseEntityBlock {
    public ArsEggBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ArsEggBlockEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if(player instanceof ServerPlayer serverPlayer){
            TardisLevelOperator.get(serverPlayer.serverLevel()).ifPresent(tardisLevelOperator -> {
                CompoundTag upgradeNbt = tardisLevelOperator.getUpgradeHandler().saveData(new CompoundTag());
                new S2CDisplayUpgradeScreen(upgradeNbt).send(serverPlayer);
            });
        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}
