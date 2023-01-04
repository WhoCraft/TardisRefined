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

    public static final Event<TardisEntry> TARDIS_ENTRY_EVENT = new Event<>(TardisEntry.class, listeners -> (tardisLevelOperator, shell, player, externalPos, level, direction) -> {
        for (TardisEntry listener : listeners) {
            listener.onEnterTardis(tardisLevelOperator, shell, player, externalPos, level, direction);
        }
    });

    /**
     * An event that is triggered when a TARDIS takes off.
     */
    @FunctionalInterface
    public interface TakeOff {
        /**
         * Called when a TARDIS takes off.
         *
         * @param tardisLevelOperator The operator of the TARDIS level.
         * @param level The level where the TARDIS is taking off from.
         * @param pos The position of the TARDIS.
         * @return The result of the event.
         */
        EventResult onTakeOff(TardisLevelOperator tardisLevelOperator, LevelAccessor level, BlockPos pos);
    }

    /**
     * An event that is triggered when a TARDIS lands.
     */
    @FunctionalInterface
    public interface Land {
        /**
         * Called when a TARDIS lands.
         *
         * @param tardisLevelOperator The operator of the TARDIS level.
         * @param level The level where the TARDIS is landing.
         * @param pos The position of the TARDIS.
         */
        void onLand(TardisLevelOperator tardisLevelOperator, LevelAccessor level, BlockPos pos);
    }

    /**
     * An event that is triggered when a player enters a TARDIS.
     */
    @FunctionalInterface
    public interface TardisEntry {
        /**
         * Called when a player enters a TARDIS.
         *
         * @param shell The exterior shell of the TARDIS.
         * @param player The player who is entering the TARDIS.
         * @param externalPos The position of the exterior of the TARDIS.
         * @param level The level where the TARDIS is located.
         * @param direction The direction the player is facing when entering the TARDIS.
         */
        void onEnterTardis(TardisLevelOperator tardisLevelOperator, IExteriorShell shell, Player player, BlockPos externalPos, Level level, Direction direction);
    }
}
