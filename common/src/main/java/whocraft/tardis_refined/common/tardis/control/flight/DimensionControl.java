package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.IControl;
import whocraft.tardis_refined.common.tardis.manager.TardisControlManager;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.DimensionTypes;

import java.util.ArrayList;

public class DimensionControl implements IControl {
    private static ArrayList<ServerLevel> dimensions = new ArrayList<>();

    private void setDimensions(MinecraftServer server) {
        if(dimensions.isEmpty()) {
            for(ServerLevel level : server.getAllLevels()) {
                if(!level.dimensionTypeId().equals(DimensionTypes.TARDIS)) {
                    dimensions.add(level);
                }
            }
        }
    }

    @Override
    public void onRightClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {
        setDimensions(controlEntity.getServer());
        TardisControlManager manager = operator.getControlManager();
        int i = dimensions.indexOf(manager.getTargetLocation().level) + 1;
        if(i > dimensions.size() - 1) {
            i = 0;
        }

        manager.getTargetLocation().level = dimensions.get(i);
        PlayerUtil.sendMessage(player,
                Component.translatable(ModMessages.MSG_DIMENSION_SELECT).append(manager.getTargetLocation().level.dimension().location().getPath().toUpperCase()), true);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {
        setDimensions(controlEntity.getServer());
        TardisControlManager manager = operator.getControlManager();
        int i = dimensions.indexOf(manager.getTargetLocation().level) - 1;
        if(i < 0) {
            i = dimensions.size() - 1;
        }

        manager.getTargetLocation().level = dimensions.get(i);
        PlayerUtil.sendMessage(player,
                Component.translatable(ModMessages.MSG_DIMENSION_SELECT).append(manager.getTargetLocation().level.dimension().location().getPath().toUpperCase()), true);
    }
}
