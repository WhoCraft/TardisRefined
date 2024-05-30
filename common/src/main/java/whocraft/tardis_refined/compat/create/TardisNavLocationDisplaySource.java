package whocraft.tardis_refined.compat.create;

import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.ArrayList;
import java.util.List;

public class TardisNavLocationDisplaySource extends DisplaySource {

    private final TardisNavInfo tardisNavInfo;

    public TardisNavLocationDisplaySource(TardisNavInfo tardisNavInfo) {
        this.tardisNavInfo = tardisNavInfo;
    }

    @Override
    public List<MutableComponent> provideText(DisplayLinkContext context, DisplayTargetStats stats) {
        if (context.level() instanceof ServerLevel serverLevel) {
            boolean isPresent = TardisLevelOperator.get(serverLevel).isPresent();
            if (isPresent) {
                TardisNavLocation tardisNavLocation = tardisNavInfo.provideInfo(TardisLevelOperator.get(serverLevel).get());
                List<MutableComponent> components = new ArrayList<>();

                components.add(Component.literal("Position: " + tardisNavLocation.getPosition().toShortString()));
                components.add(Component.literal("Direction: " + tardisNavLocation.getDirection().getName()));
                components.add(Component.literal("Dimension: " + MiscHelper.getCleanDimensionName(tardisNavLocation.getDimensionKey())));

                return components;
            }
        }
        return List.of(Component.translatable(ModMessages.HARDWARE_OFFLINE));
    }

    @Override
    public int getPassiveRefreshTicks() {
        return 10;
    }

    public interface TardisNavInfo {
        TardisNavLocation provideInfo(TardisLevelOperator tardisLevelOperator);
        ResourceLocation getId();
    }
}
