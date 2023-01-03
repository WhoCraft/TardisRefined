package whocraft.tardis_refined.api.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.IExteriorShell;

public class TardisEvents {

    public static final Event<TakeOff> TAKE_OFF = new Event<>(TakeOff.class, listeners -> (tardisLevelOperator, level, pos) -> Event.result(listeners, takeOff -> takeOff.onTakeOff(tardisLevelOperator, level, pos)));

    public static final Event<Land> LAND = new Event<>(Land.class, listeners -> ((tardisLevelOperator, level, pos) -> {
        for (Land listener : listeners) {
            listener.onLand(tardisLevelOperator, level, pos);
        }
    }));

    public static final Event<TardisEntry> TARDIS_ENTRY_EVENT = new Event<>(TardisEntry.class, listeners -> (shell, player, externalPos, level, direction) -> {
        for (TardisEntry listener : listeners) {
            listener.onEnterTardis(shell, player, externalPos, level, direction);
        }
    });

    @FunctionalInterface
    public interface TardisEntry {
        void onEnterTardis(IExteriorShell shell, Player player, BlockPos externalPos, Level level, Direction direction);
    }

    @FunctionalInterface
    public interface TakeOff {
        EventResult onTakeOff(TardisLevelOperator tardisLevelOperator, LevelAccessor level, BlockPos pos);

    }

    @FunctionalInterface
    public interface Land {
        void onLand(TardisLevelOperator tardisLevelOperator, LevelAccessor level, BlockPos pos);
    }

}
