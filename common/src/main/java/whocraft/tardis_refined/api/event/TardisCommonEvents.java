package whocraft.tardis_refined.api.event;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.Upgrade;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class TardisCommonEvents {

    public static final Event<TakeOff> TAKE_OFF = new Event<>(TakeOff.class, listeners -> (tardisLevelOperator, level, pos) -> Event.result(listeners, takeOff -> takeOff.onTakeOff(tardisLevelOperator, level, pos)));

    public static final Event<Land> LAND = new Event<>(Land.class, listeners -> ((tardisLevelOperator, level, pos) -> {
        for (Land listener : listeners) {
            listener.onLand(tardisLevelOperator, level, pos);
        }
    }));

    public static final Event<TardisEntry> TARDIS_ENTRY_EVENT = new Event<>(TardisEntry.class, listeners -> (tardisLevelOperator, livingEntity, source, destination) -> {
        for (TardisEntry listener : listeners) {
            listener.onEnterTardis(tardisLevelOperator, livingEntity, source, destination);
        }
    });

    public static final Event<TardisExit> TARDIS_EXIT_EVENT = new Event<>(TardisExit.class, listeners -> (tardisLevelOperator, livingEntity, source, destination) -> {
        for (TardisExit listener : listeners) {
            listener.onExitTardis(tardisLevelOperator, livingEntity, source, destination);
        }
    });

    public static final Event<CloseDoor> DOOR_CLOSED_EVENT = new Event<>(CloseDoor.class, listeners -> (tardisLevelOperator) -> {
        for(CloseDoor listener : listeners) {
            listener.onDoorClosed(tardisLevelOperator);
        }
    });

    public static final Event<OpenDoor> DOOR_OPENED_EVENT = new Event<>(OpenDoor.class, listeners -> (tardisLevelOperator) -> {
        for(OpenDoor listener : listeners) {
            listener.onDoorOpen(tardisLevelOperator);
        }
    });

    public static final Event<ShellChange> SHELL_CHANGE_EVENT = new Event<>(ShellChange.class, listeners -> (tardisLevelOperator, theme, isSetupTardis) -> {
        for (ShellChange listener : listeners) {
            listener.onShellChange(tardisLevelOperator, theme, isSetupTardis);
        }
    });

    public static final Event<DesktopChangeEvent> DESKTOP_CHANGE_EVENT = new Event<>(DesktopChangeEvent.class, listeners -> (tardisLevelOperator) -> {
        for (DesktopChangeEvent listener : listeners) {
            listener.onDesktopChange(tardisLevelOperator);
        }
    });

    public static final Event<TardisCrash> TARDIS_CRASH_EVENT = new Event<>(TardisCrash.class, listeners -> ((tardisLevelOperator, crashLocation) -> {
        for (TardisCrash listener : listeners) {
            listener.onTardisCrash(tardisLevelOperator, crashLocation);
        }
    }));

    public static final Event<UpgradeUnlocked> UPGRADE_UNLOCKED = new Event<>(UpgradeUnlocked.class, listeners -> ((tardisLevelOperator, upgrade) -> {
        for (UpgradeUnlocked listener : listeners) {
            listener.onUpgradeUnlock(tardisLevelOperator, upgrade);
        }
    }));


    /**
     * Represents an event that allows checking whether player control can be used.
     */
    public static final Event<CanControlBeUsed> PLAYER_CONTROL_INTERACT = new Event<>(CanControlBeUsed.class, listeners -> (tardisLevelOperator, control, controlEntity) -> Event.result(listeners, takeOff -> takeOff.canControlBeUsed(tardisLevelOperator, control, controlEntity)));

    public static final Event<ConsoleThemeRegistered> CONSOLE_THEME_REGISTERED = new Event<>(ConsoleThemeRegistered.class, listeners -> theme -> {
        for (ConsoleThemeRegistered listener : listeners) {
            listener.onThemeRegistered(theme);
        }
    });

    /**
     * Functional interface to define the conditions for using player control.
     */
    @FunctionalInterface
    public interface CanControlBeUsed {

        /**
         * Checks whether player control can be used based on specified parameters.
         *
         * @param tardisLevelOperator The Tardis level operator.
         * @param control The control to be used.
         * @param controlEntity The entity associated with the control.
         * @return True if control can be used, false otherwise.
         */
        EventResult canControlBeUsed(TardisLevelOperator tardisLevelOperator, Control control, ControlEntity controlEntity);
    }


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
     * An event that is triggered when the TARDIS Door is closed.
     */
    @FunctionalInterface
    public interface CloseDoor {
        /**
         * Called when the TARDIS door is closed.
         *
         * @param tardisLevelOperator The operator of the TARDIS level.
         */
        void onDoorClosed(TardisLevelOperator tardisLevelOperator);
    }

    /**
     * An event that is triggered when the TARDIS Door is opened.
     */
    @FunctionalInterface
    public interface OpenDoor {
        /**
         * Called when the TARDIS door is opened.
         *
         * @param tardisLevelOperator The operator of the TARDIS level.
         */
        void onDoorOpen(TardisLevelOperator tardisLevelOperator);
    }


    /**
     * An event that is triggered when the TARDIS desktp is changed.
     * Note: Only fired once all players have left the dimension
     */
    @FunctionalInterface
    public interface DesktopChangeEvent {
        /**
         * Called when the TARDIS desktp is changed.
         *
         * @param tardisLevelOperator The operator of the TARDIS level.
         */
        void onDesktopChange(TardisLevelOperator tardisLevelOperator);
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
     * An event that is triggered when a TARDIS changes its Shell.
     */
    @FunctionalInterface
    public interface ShellChange {
        /**
         * Called when a TARDIS lands.
         *
         * @param tardisLevelOperator The operator of the TARDIS level.
         * @param theme The theme the TARDIS changed to.
         * @param isSetupTardis if the Shell Change event was caused by a Tardis being setup from a Root Shell to a fully functioning version
         */
        void onShellChange(TardisLevelOperator tardisLevelOperator, ResourceLocation theme, boolean isSetupTardis);
    }

    /**
     * An event that is triggered when a player enters a TARDIS.
     */
    @FunctionalInterface
    public interface TardisEntry {
        /**
         * Called when a living entity enters a TARDIS.
         *
         * @param tardisLevelOperator The Tardis capability
         * @param livingEntity The living entity who is entering the TARDIS.
         * @param sourceLocation The position, level and direction of the exterior of the TARDIS.
         * @param destinationLocation The position, level and direction of the internal door of the TARDIS.
         */
        void onEnterTardis(TardisLevelOperator tardisLevelOperator, LivingEntity livingEntity, TardisNavLocation sourceLocation, TardisNavLocation destinationLocation);

    }

    @FunctionalInterface
    public interface TardisExit {
        /**
         * Called when a living entity exits a TARDIS.
         *
         * @param tardisLevelOperator The Tardis capability
         * @param livingEntity The living entity who is exiting the TARDIS.
         * @param sourceLocation The position, level and direction of the internal door of the TARDIS.
         * @param destinationLocation The position, level and direction of the exterior of the TARDIS.
         */
        void onExitTardis(TardisLevelOperator tardisLevelOperator, LivingEntity livingEntity, TardisNavLocation sourceLocation, TardisNavLocation destinationLocation);
    }

    /**
     * An event that is triggered when a player crashes a TARDIS.
     */
    @FunctionalInterface
    public interface TardisCrash {
        /**
         * Called when a player crashes a TARDIS.
         *
         * @param tardisLevelOperator The TARDIS Level Operator.
         * @param crashLocation       The Location of the crash..
         */
        void onTardisCrash(TardisLevelOperator tardisLevelOperator, TardisNavLocation crashLocation);
    }

    /**
     * An event that is triggered when a TARDIS unlocks a new Upgrade.
     */
    @FunctionalInterface
    public interface UpgradeUnlocked {
        /**
         * Called when a TARDIS unlocks a new Upgrade.
         *
         * @param tardisLevelOperator The TARDIS Level Operator.
         * @param upgrade       The Upgrade
         */
        void onUpgradeUnlock(TardisLevelOperator tardisLevelOperator, Upgrade upgrade);
    }

    /**
     * An event that is triggered when a {@link ConsoleTheme} is registered
     */
    @FunctionalInterface
    public interface ConsoleThemeRegistered {
        /**
         * Called when a {@link ConsoleTheme} is registered, to allow addon mods to replace empty control switches
         * Use {@link ConsoleTheme#replaceControl(Control, int, ConsoleTheme)}
         *
         * @param theme The {@link ConsoleTheme} being registered
         */
        void onThemeRegistered(ConsoleTheme theme);
    }
}
