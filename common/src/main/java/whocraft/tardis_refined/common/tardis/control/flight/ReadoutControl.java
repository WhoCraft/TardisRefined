package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;

public class ReadoutControl extends whocraft.tardis_refined.common.tardis.control.Control {
    public ReadoutControl(ResourceLocation id) {
        super(id);
    }

    public ReadoutControl(ResourceLocation id, String langId) {
        super(id, langId);
    }

    private static Component getMessage(String key, TardisNavLocation location) {
        var message = Component.translatable(key).append(" - X: " + location.getRealPosition().getX() + " Y: " + location.getRealPosition().getY() + " Z: " + location.getRealPosition().getZ() + " F: " + location.getDirection().getName() + " D: " + location.getDimensionKey().location().getPath());
        location.getSublevelDescription().ifPresent(meta -> message.append(", ").append(meta));
        return message;
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        TardisNavLocation currentPosition = operator.getPilotingManager().getCurrentLocation();
        PlayerUtil.sendMessage(player, getMessage(ModMessages.CURRENT, currentPosition), true);


        return true;
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        TardisNavLocation targetLocation = operator.getPilotingManager().getTargetLocation();
        PlayerUtil.sendMessage(player, getMessage(ModMessages.DESTINATION, targetLocation), true);

        return true;
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }
}
