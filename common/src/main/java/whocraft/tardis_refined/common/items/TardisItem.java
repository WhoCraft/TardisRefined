package whocraft.tardis_refined.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.UUID;

public class TardisItem extends Item {
    public TardisItem(Properties properties) {
        super(properties);
    }

    private static final int COOLDOWN_TICKS = 20 * 30; // 30 seconds

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (level instanceof ServerLevel serverLevel && player != null) {

            // Cooldown
            if (player.getCooldowns().isOnCooldown(this)) {
                return InteractionResult.FAIL;
            }
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

            if (!DimensionUtil.isAllowedDimension(level.dimension())) {
                PlayerUtil.sendMessage(player, ModMessages.SPAWN_TARDIS_DIMENSION_FAIL, true);
                return InteractionResult.FAIL;
            }

            Direction blockFace = context.getClickedFace();
            Direction playerFacing = player.getDirection();

            if (blockFace != Direction.UP) {
                return InteractionResult.FAIL;
            }

            ResourceLocation shellTheme = ShellTheme.HALF_BAKED.getId();
            DesktopTheme desktopTheme = TardisDesktops.TERRAFORMED;

            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);
            if (state.isSolid()) {
                pos = pos.above();
            }

            MinecraftServer server = serverLevel.getServer();

            ResourceKey<Level> generatedLevelKey = ResourceKey.create(
                    Registries.DIMENSION,
                    new ResourceLocation(TardisRefined.MODID, UUID.randomUUID().toString())
            );

            MutableComponent tardisId = TardisHelper.createTardisIdComponent(generatedLevelKey.location());
            server.sendSystemMessage(Component.translatable(ModMessages.CMD_CREATE_TARDIS_IN_PROGRESS, tardisId));

            TardisHelper.createTardis(
                    pos, serverLevel, generatedLevelKey, shellTheme, desktopTheme, playerFacing, false,
                    () -> server.sendSystemMessage(Component.translatable(ModMessages.CMD_CREATE_TARDIS_SUCCESS, tardisId)), () -> {}
            );

            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

}
