package whocraft.tardis_refined.compat.create;

import com.simibubi.create.api.behaviour.display.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import whocraft.tardis_refined.common.blockentity.device.FlightDetectorBlockEntity;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.SublevelAccessor;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.ArrayList;
import java.util.List;

public class TardisDisplaySource extends DisplaySource {


    @Override
    public List<MutableComponent> provideText(DisplayLinkContext context, DisplayTargetStats stats) {

        BlockEntity blockEntity = context.getSourceBlockEntity();

        ArrayList<MutableComponent> list = new ArrayList<>();

        if (blockEntity instanceof FlightDetectorBlockEntity flightDetectorBlockEntity) {
            Level level = flightDetectorBlockEntity.getLevel();

            if (level instanceof ServerLevel serverLevel) {
                boolean isPresent = TardisLevelOperator.get(serverLevel).isPresent();
                if (isPresent) {
                    TardisLevelOperator levelOperator = TardisLevelOperator.get(serverLevel).get();
                    TardisNavLocation currentLoc = levelOperator.getPilotingManager().getCurrentLocation();

                    list.add(Component.translatable(ModMessages.IN_FLIGHT, levelOperator.getPilotingManager().isInFlight()));
                    list.add(Component.translatable(ModMessages.POSITION, currentLoc.getRealPosition().toShortString()));
                    list.add(Component.translatable(ModMessages.DIMENSION, MiscHelper.getCleanDimensionName(currentLoc.getDimensionKey())));
                    list.add(Component.translatable(ModMessages.FUEL, String.valueOf(Math.round(levelOperator.getPilotingManager().getFuelPercentage() * 100))).append("%"));
                    list.add(Component.translatable(ModMessages.SHELL, levelOperator.getAestheticHandler().getShellTheme().getPath()));
                    list.add(Component.translatable(ModMessages.JOURNEY_PROGRESS, levelOperator.getPilotingManager().getFlightPercentageCovered() * 100 + "%"));

                }
                return list;
            }
        }

        return List.of();
    }

    @Override
    public int getPassiveRefreshTicks() {
        return 10;
    }


}