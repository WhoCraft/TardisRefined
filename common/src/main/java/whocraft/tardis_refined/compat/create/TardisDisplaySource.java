package whocraft.tardis_refined.compat.create;

import com.simibubi.create.content.logistics.block.display.DisplayLinkContext;
import com.simibubi.create.content.logistics.block.display.source.SingleLineDisplaySource;
import com.simibubi.create.content.logistics.block.display.target.DisplayTargetStats;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import whocraft.tardis_refined.common.block.device.FlightDetectorBlock;
import whocraft.tardis_refined.common.blockentity.device.FlightDetectorBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;

public class TardisDisplaySource extends SingleLineDisplaySource {

    @Override
    protected boolean allowsLabeling(DisplayLinkContext context) {
        return false;
    }

    @Override
    public int getPassiveRefreshTicks() {
        return 40;
    }

    @Override
    protected MutableComponent provideLine(DisplayLinkContext context, DisplayTargetStats stats) {

        BlockEntity blockEntity = context.getSourceTE();

        if(blockEntity instanceof FlightDetectorBlockEntity flightDetectorBlockEntity){
            Level level = flightDetectorBlockEntity.getLevel();
            if(level instanceof ServerLevel serverLevel){
                boolean isPresent = TardisLevelOperator.get(serverLevel).isPresent();
                if(isPresent){
                    TardisLevelOperator levelOperator = TardisLevelOperator.get(serverLevel).get();
                    return levelOperator.getPilotingManager().isInFlight() ? Component.literal("In Flight True") : Component.literal("In Flight: False");
                }
            }
        }

        return Component.literal("");
    }
}
