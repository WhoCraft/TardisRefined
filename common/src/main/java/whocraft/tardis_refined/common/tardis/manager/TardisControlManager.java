package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.api.event.TardisEvents;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.SoundRegistry;

public class TardisControlManager {

    // CONSTANTS
    private static final int TICKS_LANDING_MAX = 9 * 20;
    private static final int TICKS_COOLDOWN_MAX = (10 * 60) * 20;

    private final TardisLevelOperator operator;

    // Location based.
    private TardisNavLocation targetLocation;
    private TardisNavLocation fastReturnLocation;

    // Inflight timers (ticks)
    private boolean isInFlight = false;
    private int ticksInFlight = 0;
    private int ticksLanding = 0;
    private int ticksTakingOff = 0;
    private int ticksCrashing = 0;
    private int ticksSinceCrash = 0;

    private boolean isCrashing = false;

    public boolean isCrashing() {
        return isCrashing;
    }

    private boolean canUseControls = true;

    private final int[] coordinateIncrements = new int[]{1, 10, 100, 1000};
    private int cordIncrementIndex = 0;

    private boolean autoLand = false;
    private ShellTheme currentExteriorTheme;

    public TardisControlManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public void setAutoLand(boolean autoLand) {
        this.autoLand = autoLand;
    }

    public boolean isAutoLandSet() {
        return this.autoLand;
    }

    public ShellTheme getCurrentExteriorTheme() {
        return this.currentExteriorTheme;
    }

    public void setCurrentExteriorTheme(ShellTheme theme) {
        this.currentExteriorTheme = theme;
    }

    public boolean isOnCooldown() {
        return (ticksSinceCrash > 0);
    }

    public void endCoolDown() {
        this.ticksSinceCrash = TICKS_COOLDOWN_MAX;
    }

    public void loadData(CompoundTag tag) {
        this.autoLand = tag.getBoolean(NbtConstants.CONTROL_AUTOLAND);
        this.isInFlight = tag.getBoolean(NbtConstants.CONTROL_IS_IN_FLIGHT);
        this.targetLocation = NbtConstants.getTardisNavLocation(tag, "ctrl_target", operator);
        this.fastReturnLocation = NbtConstants.getTardisNavLocation(tag, "ctrl_fr_loc", operator);

        this.ticksCrashing = tag.getInt("ticksCrashing");
        this.ticksSinceCrash = tag.getInt("ticksSinceCrash");
        this.canUseControls = tag.getBoolean("canUseControls");

        if (tag.getString(NbtConstants.CONTROL_CURRENT_EXT) != null && !tag.getString(NbtConstants.CONTROL_CURRENT_EXT).isEmpty()) {
            this.currentExteriorTheme = ShellTheme.findOr(tag.getString(NbtConstants.CONTROL_CURRENT_EXT), ShellTheme.FACTORY);
        }

        if (this.targetLocation == null) {
            this.targetLocation = new TardisNavLocation(new BlockPos(0, 0, 0), Direction.NORTH, operator.getLevel().getServer().getLevel(Level.OVERWORLD));
        }

        this.cordIncrementIndex = tag.getInt(NbtConstants.CONTROL_INCREMENT_INDEX);
    }

    public CompoundTag saveData(CompoundTag tag) {
        tag.putBoolean(NbtConstants.CONTROL_IS_IN_FLIGHT, this.isInFlight);
        tag.putBoolean(NbtConstants.CONTROL_AUTOLAND, this.autoLand);

        tag.putInt("ticksCrashing", this.ticksCrashing);
        tag.putInt("ticksSinceCrash", this.ticksSinceCrash);
        tag.putBoolean("canUseControls", this.canUseControls);

        if (this.currentExteriorTheme != null) {
            tag.putString(NbtConstants.CONTROL_CURRENT_EXT, this.currentExteriorTheme.getSerializedName());
        }
        if (targetLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "ctrl_target", this.targetLocation);
        }

        if (fastReturnLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "ctrl_fr_loc", this.fastReturnLocation);
        }

        tag.putInt(NbtConstants.CONTROL_INCREMENT_INDEX, this.cordIncrementIndex);

        return tag;
    }

    public void tick(Level level) {
        if (targetLocation == null) {
            var location = this.operator.getExteriorManager().getLastKnownLocation();
            if (targetLocation != null) {
                this.targetLocation = location;
            } else {
                this.targetLocation = new TardisNavLocation(new BlockPos(0, 100, 0), Direction.NORTH, Platform.getServer().overworld());
            }
        }

        if (isInFlight) {
            ticksInFlight++;

            // Automatically trigger the ship to land for things such as landing pads.
            if (ticksInFlight > (20 * 10) && autoLand) {
                this.endFlight();
            }

            if (ticksTakingOff > 0) {
                ticksTakingOff++;
            }

            if (ticksTakingOff == (11 * 20)) {
                this.enterTimeVortex();
            }

            if (ticksLanding > 0) {
                ticksLanding--;
            }

            if (ticksLanding == 1) {
                this.onFlightEnd();
            }

            if (ticksCrashing > 1) {
                ticksCrashing--;
            }

            if (ticksCrashing == 1) {
                onCrashEnd();
            }


            if ((ticksLanding >= 6 * 20 || ticksLanding == 0) && !this.isCrashing()) {
                var distanceBetweenWroop = (this.operator.getTardisFlightEventManager().getControlResponses() < this.operator.getTardisFlightEventManager().getRequiredControlRequests()) ? 20 * 1.6 : 20 * 1.75;
                if (level.getGameTime() % (distanceBetweenWroop) == 0)
                    operator.getLevel().playSound(null, operator.getInternalDoor().getDoorPosition(), SoundRegistry.TARDIS_SINGLE_FLY.get(), SoundSource.AMBIENT, 10f, (this.operator.getTardisFlightEventManager().getControlResponses() < this.operator.getTardisFlightEventManager().getRequiredControlRequests()) ? 1.02f : 1f);
            }
        }

        if (ticksSinceCrash > 0) {
            ticksSinceCrash++;
            // After 10 minutes
            if (ticksSinceCrash >= TICKS_COOLDOWN_MAX) {
                this.canUseControls = true;
                ticksSinceCrash = 0;
                this.operator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundRegistry.TARDIS_SINGLE_FLY.get(), SoundSource.AMBIENT, 100f, 0.25f);
            }
        }

    }

    public boolean isInFlight() {
        return this.isInFlight;
    }

    public boolean isLanding() {
        return (ticksLanding > 0);
    }

    public boolean canUseControls() {
        return canUseControls;
    }

    /**
     * Load the fast return into the target location.
     *
     * @return if the load was successful
     **/
    public boolean preloadFastReturn() {
        if (this.fastReturnLocation == null) {
            return false;
        } else {
            this.targetLocation = this.fastReturnLocation;
            return true;
        }
    }


    public TardisNavLocation getClosestValidPosition(TardisNavLocation location) {
        ServerLevel level = location.level;
        BlockPos currentScanPosition = location.position;

        // We need to be able to determine whether the position we're aiming for is a valid location.
        boolean isTargetLandableBlock = !level.getBlockState(currentScanPosition).getMaterial().isSolidBlocking();

        if (isTargetLandableBlock) {
            currentScanPosition = currentScanPosition.below();
            if (!level.getBlockState(currentScanPosition).getMaterial().isSolidBlocking()) {
                return startScanDownwards(location);
            }
        }

        return startScanUpwards(location);
    }

    private TardisNavLocation startScanDownwards(TardisNavLocation startingLocation) {
        TardisNavLocation solvedLocation = scanDownForPosition(startingLocation);
        if (solvedLocation != null) {
            return solvedLocation;
        } else {
            // Below wasn't an option. Scan up for a location.
            return scanUpForPosition(startingLocation);
        }

    }

    private TardisNavLocation startScanUpwards(TardisNavLocation startingLocation) {
        TardisNavLocation solvedLocation = scanUpForPosition(startingLocation);
        if (solvedLocation != null) {
            return solvedLocation;
        } else {
            // Below wasn't an option. Scan up for a location.
            return scanDownForPosition(startingLocation);
        }

    }

    private TardisNavLocation scanDownForPosition(TardisNavLocation startingLocation) {
        ServerLevel level = startingLocation.level;
        BlockPos currentPos = startingLocation.position;

        while (currentPos.getY() > level.getMinBuildHeight()) {
            if (!level.getBlockState(currentPos).getMaterial().isSolidBlocking()) {
                // Check if the Shell can be physically in the location.
                if (level.getBlockState(currentPos.below()).getMaterial().isSolidBlocking() && !level.getBlockState(currentPos.above()).getMaterial().isSolidBlocking() && !level.getBlockState(currentPos.below()).is(Blocks.WATER)) {

                    // Check that the facing location !!!!!
                    Direction[] directions = new Direction[]{startingLocation.rotation, startingLocation.rotation.getOpposite(), Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                    for (Direction dir : directions) {
                        BlockPos basePos = BlockPos.of(BlockPos.offset(currentPos.asLong(), dir));
                        if (!level.getBlockState(basePos).getMaterial().isSolidBlocking() && !level.getBlockState(basePos.above()).getMaterial().isSolidBlocking()) {
                            return new TardisNavLocation(currentPos, dir, level);
                        }
                    }
                }
            }

            currentPos = currentPos.below();
        }

        return null;
    }

    private TardisNavLocation scanUpForPosition(TardisNavLocation startingLocation) {
        ServerLevel level = startingLocation.level;
        BlockPos currentPos = startingLocation.position;

        while (currentPos.getY() < level.getMaxBuildHeight()) {
            if (!level.getBlockState(currentPos).getMaterial().isSolidBlocking()) {
                // Check if the Shell can be physically in the location.
                if (level.getBlockState(currentPos.below()).getMaterial().isSolidBlocking() && !level.getBlockState(currentPos.above()).getMaterial().isSolidBlocking() && !level.getBlockState(currentPos.below()).is(Blocks.WATER)) {

                    // Check that the facing location !!!!!
                    Direction[] directions = new Direction[]{startingLocation.rotation, startingLocation.rotation.getOpposite(), Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                    for (Direction dir : directions) {
                        BlockPos basePos = BlockPos.of(BlockPos.offset(currentPos.asLong(), dir));
                        if (!level.getBlockState(basePos).getMaterial().isSolidBlocking() && !level.getBlockState(basePos.above()).getMaterial().isSolidBlocking()) {
                            return new TardisNavLocation(currentPos, dir, level);
                        }
                    }
                }
            }

            currentPos = currentPos.above();
        }

        return null;
    }

    public boolean beginFlight(boolean autoLand) {
        if (isInFlight || ticksTakingOff > 0) {
            return false;
        }

        this.autoLand = autoLand;
        this.fastReturnLocation = this.operator.getExteriorManager().getLastKnownLocation();

        // TEMP: Force the targetLocation's level to be the overworld.
        this.targetLocation.level = Platform.getServer().overworld();

        operator.setDoorClosed(true);
        operator.getLevel().playSound(null, operator.getInternalDoor().getDoorPosition(), SoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.AMBIENT, 1000f, 1f);
        operator.getExteriorManager().playSoundAtShell(SoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.BLOCKS, 1, 1);
        this.isInFlight = true;
        this.ticksInFlight = 0;
        this.ticksTakingOff = 1;
        this.operator.getExteriorManager().setIsTakingOff(true);

        this.operator.getTardisFlightEventManager().calculateTravelLogic();

        return true;
    }

    public boolean endFlight() {
        if (!isInFlight || ticksInFlight < (20 * 5) || ticksTakingOff > 0 || (!this.operator.getTardisFlightEventManager().areControlEventsComplete() && !this.autoLand)) {
            return false;
        }
        this.ticksInFlight = 0;
        this.ticksLanding = TICKS_LANDING_MAX;

        BlockPos landingLocation = this.targetLocation.position;
        calculatePositionToLand(landingLocation);

        operator.getExteriorManager().playSoundAtShell(SoundRegistry.TARDIS_LAND.get(), SoundSource.BLOCKS, 1, 1);
        operator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundRegistry.TARDIS_LAND.get(), SoundSource.AMBIENT, 1000f, 1f);
        return true;
    }

    public void calculatePositionToLand(BlockPos landingLocation) {

        TardisNavLocation location = null;
        while (location == null) {
            location = getClosestValidPosition(new TardisNavLocation(landingLocation, targetLocation.rotation, targetLocation.level));
            if (location != null) {
                break;
            } else {
                landingLocation = landingLocation.north(1); // Make a more sophisticated version of this!
            }
        }

        operator.getExteriorManager().placeExteriorBlock(operator, location);
        if (currentExteriorTheme != null) {
            operator.getInteriorManager().setShellTheme(currentExteriorTheme);
        }
    }

    public void enterTimeVortex() {
        operator.getExteriorManager().removeExteriorBlock();
        this.ticksTakingOff = 0;
        this.operator.getExteriorManager().setIsTakingOff(false);
        TardisNavLocation lastKnown = operator.getExteriorManager().getLastKnownLocation();
        TardisEvents.TAKE_OFF.invoker().onTakeOff(operator, lastKnown.level, lastKnown.position);
    }

    public void onFlightEnd() {
        this.isInFlight = false;
        this.ticksTakingOff = 0;
        this.autoLand = false;
        TardisNavLocation lastKnown = operator.getControlManager().getTargetLocation();
        TardisEvents.LAND.invoker().onLand(operator, lastKnown.level, lastKnown.position);
    }

    // Triggers the crash event.
    public void crash() {
        this.canUseControls = false;
        this.isCrashing = true;
        this.ticksCrashing = 8 * 20;

        for (Player player : this.operator.getLevel().players()) {
            MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.DARKNESS, 60, 60, false, false);
            player.addEffect(mobEffectInstance);
        }

        // Calculate the random position from what we've gotten.
        var progress = this.operator.getTardisFlightEventManager().getPercentComplete();
        var targetPos = new Vec3(this.targetLocation.position.getX(), this.targetLocation.position.getY(), this.targetLocation.position.getZ());
        var currentLoc = this.operator.getExteriorManager().getLastKnownLocation().position;
        var currentPos = new Vec3(currentLoc.getX(), currentLoc.getY(), currentLoc.getZ());

        var x = currentPos.x + ((targetPos.x - currentPos.x) * progress);
        var y = currentPos.y + ((targetPos.y - currentPos.y) * progress);
        var z = currentPos.z + ((targetPos.z - currentPos.z) * progress);

        BlockPos landingLocation = new BlockPos(x, y, z);

        this.setTargetPosition(landingLocation);
        calculatePositionToLand(landingLocation);

        operator.getExteriorManager().playSoundAtShell(SoundRegistry.TARDIS_CRASH_LAND.get(), SoundSource.BLOCKS, 1, 1);
        this.operator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundRegistry.TARDIS_CRASH_LAND.get(), SoundSource.BLOCKS, 1000f, 1f);
    }

    public void onCrashEnd() {
        this.isCrashing = false;
        this.ticksCrashing = 0;
        this.ticksSinceCrash = 1;

        onFlightEnd();
        TardisEvents.TARDIS_CRASH_EVENT.invoker().onTardisCrash(this.operator, this.targetLocation);
    }

    public void offsetTargetPositionX(float x) {
        this.targetLocation.position = this.targetLocation.position.offset(x, 0, 0);
    }

    public void offsetTargetPositionY(float y) {
        this.targetLocation.position = this.targetLocation.position.offset(0, y, 0);
    }

    public void offsetTargetPositionZ(float z) {
        this.targetLocation.position = this.targetLocation.position.offset(0, 0, z);
    }

    public TardisNavLocation getTargetLocation() {
        return this.targetLocation;
    }

    public void setTargetLocation(TardisNavLocation targetLocation) {
        this.targetLocation = targetLocation;
    }

    public void setTargetPosition(BlockPos pos) {
        this.targetLocation.position = pos;
    }

    public int getCordIncrement() {
        return this.coordinateIncrements[this.cordIncrementIndex];
    }

    public void cycleCordIncrement(int direction) {
        int nextIndex = this.cordIncrementIndex + direction;
        if (nextIndex < 0) nextIndex = this.coordinateIncrements.length - 1;
        if (nextIndex >= this.coordinateIncrements.length) nextIndex = 0;

        this.cordIncrementIndex = nextIndex;
    }

    public boolean shouldThrottleBeDown() {
        return isInFlight && ticksLanding == 0;
    }


}
