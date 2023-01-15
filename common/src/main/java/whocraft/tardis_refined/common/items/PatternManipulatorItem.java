package whocraft.tardis_refined.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.client.model.blockentity.console.ConsolePatterns;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.SoundRegistry;

import static whocraft.tardis_refined.common.block.device.ConsoleConfigurationBlock.FACING;

public class PatternManipulatorItem extends Item {

    public PatternManipulatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {

        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = level.getBlockState(blockPos).getBlock();
        Player player = useOnContext.getPlayer();

        if (block == BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get()) {
            var offset = blockState.getValue(FACING).getNormal();
            if (level.getBlockEntity(blockPos.offset(offset)) instanceof GlobalConsoleBlockEntity globalConsoleBlock) {
                ConsoleTheme console = globalConsoleBlock.getBlockState().getValue(GlobalConsoleBlock.CONSOLE);
                globalConsoleBlock.setPattern(ConsolePatterns.next(console, globalConsoleBlock.pattern()));
                PlayerUtil.sendMessage(player, Component.translatable(ModMessages.pattern(globalConsoleBlock.pattern())), true);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegistry.PATTERN_MANIPULATOR.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                globalConsoleBlock.sendUpdates();
                player.getCooldowns().addCooldown(this, 20);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(useOnContext);
    }
}
