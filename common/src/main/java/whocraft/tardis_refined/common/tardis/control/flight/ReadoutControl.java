package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.Locale;

public class ReadoutControl extends whocraft.tardis_refined.common.tardis.control.Control {
    public ReadoutControl(ResourceLocation id) {
        super(id);
    }

    public ReadoutControl(ResourceLocation id, String langId) {
        super(id, langId);
    }

    private static String directionName(Direction direction) {
        return direction.getName().substring(0, 1).toUpperCase(Locale.ROOT) + direction.getName().substring(1);
    }

    @Override
    public boolean onLeftClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        TardisNavLocation currentPosition = operator.getPilotingManager().getCurrentLocation();
        if (ModCompatChecker.valkyrienSkies()) {
            currentPosition = VSHelper.toWorldLocation(currentPosition);
        }
        PlayerUtil.sendMessage(player, Component.translatable(ModMessages.CURRENT).append(" - X: " + currentPosition.getPosition().getX() + " Y: " + currentPosition.getPosition().getY() + " Z: " + currentPosition.getPosition().getZ() + " F: " + directionName(currentPosition.getDirection()) + " D: ").append(MiscHelper.getTranslatableDimensionName(currentPosition.getDimensionKey())), true);


        return true;
    }

    @Override
    public boolean onRightClick(TardisLevelOperator operator, ConsoleTheme theme, ControlEntity controlEntity, Player player) {

        TardisNavLocation targetLocation = operator.getPilotingManager().getTargetLocation();
        if (ModCompatChecker.valkyrienSkies()) {
            targetLocation = VSHelper.toWorldLocation(targetLocation);
        }
        PlayerUtil.sendMessage(player, Component.translatable(ModMessages.DESTINATION).append(" - X: " + targetLocation.getPosition().getX() + " Y: " + targetLocation.getPosition().getY() + " Z: " + targetLocation.getPosition().getZ() + " F: " + directionName(targetLocation.getDirection()) + " D: ").append(MiscHelper.getTranslatableDimensionName(targetLocation.getDimensionKey())), true);

        return true;
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }
}
