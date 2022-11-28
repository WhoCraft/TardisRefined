package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.world.entity.player.Player;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;

public interface IControl {

    void onRightClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player);
    void onLeftClick(TardisLevelOperator operator, ControlEntity controlEntity, Player player);

}
