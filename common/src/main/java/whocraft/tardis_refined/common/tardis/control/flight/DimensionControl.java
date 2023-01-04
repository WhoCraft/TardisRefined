package whocraft.tardis_refined.common.tardis.control.flight;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.IControl;
import whocraft.tardis_refined.common.tardis.manager.TardisControlManager;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.DimensionTypes;

import java.util.ArrayList;
import java.util.Iterator;

public class DimensionControl implements IControl {
    private static ArrayList<ResourceKey<Level>> dimensions = new ArrayList<>();

    private void setDimensions(MinecraftServer server) {
        if(dimensions.isEmpty()) {
            dimensions.addAll(server.getWorldData().worldGenSettings().levels());
            ResourceKey<Level>[] array = new ResourceKey[dimensions.size()];
            dimensions.toArray(array);
            for (ResourceKey<Level> level : array) {
                if(server.getLevel(level).dimensionTypeId().equals(DimensionTypes.TARDIS)) {
                    dimensions.remove(level);
                }
            }
        }
    }

    @Override
    public void onRightClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {
        setDimensions(controlEntity.getServer());
        TardisControlManager manager = operator.getControlManager();
        int i = dimensions.indexOf(manager.getTargetLocation().level.dimension()) + 1;
        if(i > dimensions.size() - 1) {
            i = 0;
        }
        manager.getTargetLocation().level = controlEntity.getServer().getLevel(dimensions.get(i));

        PlayerUtil.sendMessage(player,
                Component.translatable(ModMessages.MSG_DIMENSION_SELECT).append(manager.getTargetLocation().level.dimension().location().getPath().toUpperCase()), true);
    }

    @Override
    public void onLeftClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player) {
        setDimensions(controlEntity.getServer());
        TardisControlManager manager = operator.getControlManager();
        int i = dimensions.indexOf(manager.getTargetLocation().level.dimension()) - 1;
        if(i < 0) {
            i = dimensions.size() - 1;
        }

        manager.getTargetLocation().level = controlEntity.getServer().getLevel(dimensions.get(i));
        PlayerUtil.sendMessage(player,
                Component.translatable(ModMessages.MSG_DIMENSION_SELECT).append(manager.getTargetLocation().level.dimension().location().getPath().toUpperCase()), true);
    }
}
