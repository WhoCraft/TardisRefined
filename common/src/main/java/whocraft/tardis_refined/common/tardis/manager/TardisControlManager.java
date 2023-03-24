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


    public TardisNavLocation findClosestValidPosition(TardisNavLocation location) {
        ServerLevel level = location.getLevel();

        var height = level.getMaxBuildHeight();
        var minHeight = level.getMinBuildHeight();

        var failOffset = 1;
        var attempts = 20;

        var originalY = location.position.getY();

        // Do any specific dimension checks
        if (level.dimension() == Level.NETHER) {
            if (location.position.getY() > 127) {
                height = 125;
                failOffset = 10;
                location.position = new BlockPos(location.position.getX(), 80, location.position.getZ());
            }
        }

        boolean isTargetFine = !isSolidBlock(level, location.position) && !isSolidBlock(level, location.position.above()) && isSolidBlock(level, location.position.below());
        if (isTargetFine) {
            var safeDir = findSafeDirection(location);
            if (safeDir != null) {return safeDir;}
        }

        var attemptNumber = 0;
        while (attemptNumber > -1) {
            location.position = getLegalPosition(location.getLevel(), location.position, originalY);
            var result = scanUpwardsFromCord(location, height);
            if (result != null && location.position.getY() < height && location.position.getY() > minHeight ) {
                return result;
            }

            location.position = getLegalPosition(location.getLevel(), location.position, originalY);
            result = scanDownwardsFromCord(location, minHeight);
            if (result != null && location.position.getY() < height && location.position.getY() > minHeight ) {
                return result;
            }

            // Try the next interval in the rotation.
            location.position = getLegalPosition(location.getLevel(), location.position, originalY);
            location.position = location.position.offset(location.rotation.getNormal().multiply((int) (failOffset * (1 + ((float) attemptNumber * 0.1f)))));
            attemptNumber++;
        }

        return location;
    }

    private BlockPos getLegalPosition(Level level, BlockPos pos, int originalY) {
        if (level.dimension() == Level.NETHER) {

            if (pos.getY() > 125 || originalY > 125) {
                return new BlockPos(pos.getX(), 60, pos.getZ());
            }
        }

        return new BlockPos(pos.getX(), originalY, pos.getZ());
    }

    private boolean isSafeToLand(TardisNavLocation location)
    {
        if (!isSolidBlock(location.getLevel(), location.position) && isSolidBlock(location.getLevel(), location.position.below()) && !isSolidBlock(location.getLevel(), location.position.above())) {
            return location.getLevel().getBlockState(location.position.below()).getBlock() != Blocks.LAVA && location.getLevel().getBlockState(location.position.below()).getBlock() != Blocks.WATER;
        }
        return false;
    }

    private TardisNavLocation scanUpwardsFromCord(TardisNavLocation location, int maxHeight) {
        while (location.position.getY() <= maxHeight) {
            if (isSafeToLand(location)) {
                return findSafeDirection(location);
            }

            location.position = location.position.above(1);
        }
        return null;
    }


    private TardisNavLocation scanDownwardsFromCord(TardisNavLocation location, int minHeight) {
        while (location.position.getY() >= minHeight) {

            if (isSafeToLand(location)) {

                return findSafeDirection(location);
            }

            location.position = location.position.below(1);
        }

        return null;
    }

    private TardisNavLocation findSafeDirection(TardisNavLocation location) {

        Direction[] directions = new Direction[]{location.rotation, location.rotation.getOpposite(), Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        for (Direction dir : directions) {
            BlockPos basePos = BlockPos.of(BlockPos.offset(location.position.asLong(), dir));
            if (!isSolidBlock(location.getLevel(), basePos) && !isSolidBlock(location.getLevel(), basePos.above())) {
                return new TardisNavLocation(location.position, dir, location.getLevel());
            }
        }

        return null;
    }

    private boolean isSolidBlock(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos).getMaterial().isSolidBlocking() || level.getBlockState(pos).getBlock() == Blocks.WATER || level.getBlockState(pos).getBlock() == Blocks.LAVA;
    }

    public boolean beginFlight(boolean autoLand) {
        if (isInFlight || ticksTakingOff > 0) {
            return false;
        }

        this.autoLand = autoLand;
        this.fastReturnLocation = this.operator.getExteriorManager().getLastKnownLocation();

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

        Platform.getServer().executor.execute(() -> {
            this.ticksInFlight = 0;
            this.ticksLanding = TICKS_LANDING_MAX;

            TardisNavLocation landingLocation = this.targetLocation;
            var location = findClosestValidPosition(landingLocation);

            operator.getExteriorManager().placeExteriorBlock(operator, location);
            if (currentExteriorTheme != null) {
                operator.getInteriorManager().setShellTheme(currentExteriorTheme);
            }

            operator.getExteriorManager().playSoundAtShell(SoundRegistry.TARDIS_LAND.get(), SoundSource.BLOCKS, 1, 1);
            operator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundRegistry.TARDIS_LAND.get(), SoundSource.AMBIENT, 1000f, 1f);
        });
        return true;
    }

    public void enterTimeVortex() {
        operator.getExteriorManager().removeExteriorBlock();
        this.ticksTakingOff = 0;
        this.operator.getExteriorManager().setIsTakingOff(false);
        TardisNavLocation lastKnown = operator.getExteriorManager().getLastKnownLocation();
        TardisEvents.TAKE_OFF.invoker().onTakeOff(operator, lastKnown.getLevel(), lastKnown.position);
    }

    public void onFlightEnd() {
        this.isInFlight = false;
        this.ticksTakingOff = 0;
        this.autoLand = false;
        TardisEvents.LAND.invoker().onLand(operator, getTargetLocation().getLevel(), getTargetLocation().position);
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
        TardisNavLocation landing = this.targetLocation;
        var location =  findClosestValidPosition(landing);

        operator.getExteriorManager().placeExteriorBlock(operator, location);
        if (currentExteriorTheme != null) {
            operator.getInteriorManager().setShellTheme(currentExteriorTheme);
        }

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
