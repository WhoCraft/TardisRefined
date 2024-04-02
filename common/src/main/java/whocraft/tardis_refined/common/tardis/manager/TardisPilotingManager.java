package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.api.event.TardisEvents;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.IncrementUpgrade;
import whocraft.tardis_refined.common.capability.upgrades.Upgrade;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.capability.upgrades.Upgrades;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.SoundRegistry;

import java.util.*;

public class TardisPilotingManager extends BaseHandler {

    // CONSTANTS
    private static final int TICKS_LANDING_MAX = 9 * 20;
    private static final int TICKS_COOLDOWN_MAX = (10 * 60) * 20;
    private static final double DEFAULT_MAXIMUM_FUEL = 1000;
    private static final double FLIGHT_COST = 3;

    public static final int MAX_THROTTLE_STAGE = 5;

    private final TardisLevelOperator operator;

    // Location based.
    private TardisNavLocation targetLocation;
    private TardisNavLocation fastReturnLocation;

    // Inflight timers (ticks)
    private boolean isInFlight = false;
    private int ticksInFlight = 0;
    private int flightDistance = 0;
    private int distanceCovered = 0;
    private int ticksLanding = 0;
    private int ticksTakingOff = 0;
    private int ticksCrashing = 0;
    private int ticksSinceCrash = 0;

    private boolean isCrashing = false;


    private boolean canUseControls = true;

    private int cordIncrementIndex = 0;

    private boolean autoLand = false;

    // Fuel
    private double fuel = 0;
    private double maximumFuel = DEFAULT_MAXIMUM_FUEL;

    private boolean isHandbrakeOn = false;
    private int throttleStage = 0;

    private GlobalConsoleBlockEntity currentConsole;
    private BlockPos currentConsoleBlockPos = BlockPos.ZERO;

    public TardisPilotingManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public void endCoolDown() {
        this.ticksSinceCrash = TICKS_COOLDOWN_MAX;
    }

    @Override
    public void loadData(CompoundTag tag) {
        this.autoLand = tag.getBoolean(NbtConstants.CONTROL_AUTOLAND);
        this.isInFlight = tag.getBoolean(NbtConstants.CONTROL_IS_IN_FLIGHT);

        this.isHandbrakeOn = tag.getBoolean(NbtConstants.IS_HANDBRAKE_ON);
        this.throttleStage = tag.getInt(NbtConstants.THROTTLE_STAGE);


        this.targetLocation = NbtConstants.getTardisNavLocation(tag, "ctrl_target", operator);
        this.fastReturnLocation = NbtConstants.getTardisNavLocation(tag, "ctrl_fr_loc", operator);

        this.currentConsoleBlockPos = NbtUtils.readBlockPos(tag.getCompound("currentConsoleBlockPos"));


        this.ticksCrashing = tag.getInt("ticksCrashing");
        this.ticksSinceCrash = tag.getInt("ticksSinceCrash");
        this.flightDistance = tag.getInt(NbtConstants.FLIGHT_DISTANCE);
        this.distanceCovered = tag.getInt(NbtConstants.FLIGHT_DISTANCE);
        this.canUseControls = tag.getBoolean("canUseControls");

        if (this.targetLocation == null) {
            this.targetLocation = TardisNavLocation.ORIGIN;
        }

        this.cordIncrementIndex = tag.getInt(NbtConstants.CONTROL_INCREMENT_INDEX);

        this.fuel = tag.getDouble(NbtConstants.FUEL);
        this.maximumFuel = tag.getDouble(NbtConstants.MAXIMUM_FUEL);

        if (!tag.contains(NbtConstants.MAXIMUM_FUEL)) {
            this.maximumFuel = DEFAULT_MAXIMUM_FUEL;
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public CompoundTag saveData(CompoundTag tag) {
        tag.putBoolean(NbtConstants.CONTROL_IS_IN_FLIGHT, this.isInFlight);
        tag.putBoolean(NbtConstants.CONTROL_AUTOLAND, this.autoLand);
        tag.putBoolean(NbtConstants.IS_HANDBRAKE_ON, this.isHandbrakeOn);
        tag.putInt(NbtConstants.THROTTLE_STAGE, this.throttleStage);

        tag.putInt("ticksCrashing", this.ticksCrashing);
        tag.putInt("ticksSinceCrash", this.ticksSinceCrash);
        tag.putInt(NbtConstants.FLIGHT_DISTANCE, this.flightDistance);
        tag.putInt(NbtConstants.DISTANCE_COVERED, this.distanceCovered);
        tag.putBoolean("canUseControls", this.canUseControls);

        if (targetLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "ctrl_target", this.targetLocation);
        }

        if (currentConsoleBlockPos != null) {
            tag.put("currentConsolePos", NbtUtils.writeBlockPos(this.currentConsoleBlockPos));
        }

        if (fastReturnLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "ctrl_fr_loc", this.fastReturnLocation);
        }

        tag.putInt(NbtConstants.CONTROL_INCREMENT_INDEX, this.cordIncrementIndex);

        tag.putDouble(NbtConstants.FUEL, this.fuel);
        tag.putDouble(NbtConstants.MAXIMUM_FUEL, this.maximumFuel);

        return tag;
    }

    public void tick(Level level) {
        if (targetLocation == null) {
            var location = this.operator.getExteriorManager().getLastKnownLocation();
            if (targetLocation != null) {
                this.targetLocation = location;
            } else {
                this.targetLocation = TardisNavLocation.ORIGIN;
            }
        }


        if (isInFlight) {
            onFlightTick(level);
        }

        checkThrottleStatesForFlight();

        // If the flight has been completed, then make sure that we're not still dancing.
        if (getFlightPercentageCovered() == 1) {
            if (this.operator.getFlightDanceManager().isDancing()) {
                this.operator.getFlightDanceManager().stopDancing();
            }
        }


        if (ticksSinceCrash > 0) {
            onCrashCooldownTick();
        }

    }

    private void onFlightTick(Level level) {
        // Don't continue the flight if the throttle isn't active!!!
        if (this.throttleStage != 0) {
            ticksInFlight++;

            // Removing fuel once every 2.5 seconds
            if (ticksInFlight % (2.5 * 20) == 0) {
                this.removeFuel(this.getFlightFuelCost());
            }

            if (this.operator.getLevel().getGameTime() % (20) == 0) {
                if (distanceCovered <= flightDistance) {
                    distanceCovered += throttleStage + (0.5 * throttleStage);

                    // If this tick was enough to push us over.
                    if (distanceCovered >= flightDistance) {
                        if (distanceCovered >= flightDistance && this.currentConsole != null) {
                            level.playSound(null, currentConsole.getBlockPos(), SoundRegistry.DESTINATION_DING.get(), SoundSource.AMBIENT, 10f, 1f);
                            this.operator.getFlightDanceManager().stopDancing();
                        }
                    }
                }
            }

            if (this.isHandbrakeOn && !this.isLanding() && !this.isTakingOff() && this.ticksCrashing == 0) {
                this.endFlightEarly(true);
            }

            // Automatically trigger the ship to land for things such as landing pads.
            if (ticksInFlight > (20 * 10) && autoLand) {
                this.endFlight(false);
            }
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
    }


    private void checkThrottleStatesForFlight() {
        if (!isInFlight && !this.isHandbrakeOn && this.throttleStage != 0 && this.canBeginFlight()) {
            this.beginFlight(false, null);
        }

        // End the flight if the TARDIS is peacefully gliding.
        if (isInFlight && !this.canEndFlight() && this.isHandbrakeOn && this.throttleStage == 0 && !this.isLanding() && !this.isTakingOff()) {
            this.endFlightEarly(false);
        }

        if (isInFlight && this.canEndFlight() && !this.isLanding() && !this.isTakingOff() && (this.isHandbrakeOn || this.throttleStage == 0)) {
            this.endFlight(false);
        }
    }

    private void onCrashCooldownTick() {
        ticksSinceCrash++;
        // After 10 minutes
        if (ticksSinceCrash >= TICKS_COOLDOWN_MAX) {
            this.canUseControls = true;
            ticksSinceCrash = 0;
            this.operator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundRegistry.TARDIS_SINGLE_FLY.get(), SoundSource.AMBIENT, 100f, 0.25f);
        }
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

        var maxBuildHeight = level.getMaxBuildHeight();
        var minHeight = level.getMinBuildHeight();

        var failOffset = 1;
        var attempts = 20;

        var originalY = location.getPosition().getY();

        // Do any specific dimension checks
        //TODO: Handle dimension checks in a dedicated function, we already have duplicated codfein #getLegalPosition
        if (level.dimension() == Level.NETHER) {
            if (location.getPosition().getY() > 127) {
                maxBuildHeight = 125;
                failOffset = 10;
                location.setPosition(new BlockPos(location.getPosition().getX(), 80, location.getPosition().getZ())); //TODO: Remove this hardcoding to continue searching for a spot
            }
        }

        boolean isTargetFine = !isSolidBlock(level, location.getPosition()) && !isSolidBlock(level, location.getPosition().above()) && isSolidBlock(level, location.getPosition().below());
        if (isTargetFine) {
            var safeDir = findSafeDirection(location);
            if (safeDir != null) {
                return safeDir;
            }
        }

        for (int i = 0; i < attempts; i++) {

            location.setPosition(getLegalPosition(location.getLevel(), location.getPosition(), originalY));
            var result = scanUpwardsFromCord(location, maxBuildHeight);
            if (result != null && location.getPosition().getY() < maxBuildHeight && location.getPosition().getY() > minHeight) {
                return result;
            }

            location.setPosition(getLegalPosition(location.getLevel(), location.getPosition(), originalY));
            result = scanDownwardsFromCord(location, minHeight);
            if (result != null && location.getPosition().getY() < maxBuildHeight && location.getPosition().getY() > minHeight) {
                return result;
            }

            // Try the next interval in the rotation.
            location.setPosition(getLegalPosition(location.getLevel(), location.getPosition(), originalY));
            location.setPosition(location.getPosition().offset(location.getDirection().getNormal().multiply((int) (failOffset * (1 + ((float) i * 0.1f))))));
        }


        return location;
    }

    public BlockPos getLegalPosition(Level level, BlockPos pos, int originalY) {
        if (level.dimension() == Level.NETHER) {

            if (pos.getY() > level.getMaxBuildHeight() || originalY > level.getMaxBuildHeight()) {
                return new BlockPos(pos.getX(), 60, pos.getZ()); //TODO: Remove this hardcoding and run a search below max height
            }
        }

        return new BlockPos(pos.getX(), originalY, pos.getZ());
    }

    /**
     * Checks the tardis nav location for a variety of reasons that a given position would be unsafe to land at.
     *
     * @param location the coordinates to check against
     * @return true if safe to land, otherwise false
     */
    public boolean isSafeToLand(TardisNavLocation location) {
        if (!isSolidBlock(location.getLevel(), location.getPosition()) && isSolidBlock(location.getLevel(), location.getPosition().below()) && !isSolidBlock(location.getLevel(), location.getPosition().above())) {
            return !location.getLevel().getBlockState(location.getPosition().below()).getFluidState().is(FluidTags.LAVA) && !location.getLevel().getBlockState(location.getPosition().below()).getFluidState().is(FluidTags.WATER);
        }
        return false;
    }

    public TardisNavLocation scanUpwardsFromCord(TardisNavLocation location, int maxHeight) {

        int startingHeight = location.getPosition().getY();
        for (int i = startingHeight; i < maxHeight; i++) {
            if (isSafeToLand(location)) {
                return findSafeDirection(location);
            }

            location.setPosition(location.getPosition().above(1));
        }

        return null;
    }

    public TardisNavLocation scanDownwardsFromCord(TardisNavLocation location, int minHeight) {

        int startingHeight = location.getPosition().getY();
        for (int i = startingHeight; i >= minHeight; i--) {
            if (isSafeToLand(location)) {
                return findSafeDirection(location);
            }

            location.setPosition(location.getPosition().below(1));
        }

        return null;
    }

    public TardisNavLocation findSafeDirection(TardisNavLocation location) {

        Direction[] directions = new Direction[]{location.getDirection(), location.getDirection().getOpposite(), Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        for (Direction dir : directions) {
            BlockPos basePos = BlockPos.of(BlockPos.offset(location.getPosition().asLong(), dir));
            if (!isSolidBlock(location.getLevel(), basePos) && !isSolidBlock(location.getLevel(), basePos.above())) {
                return new TardisNavLocation(location.getPosition(), dir, location.getLevel());
            }
        }

        return null;
    }

    public boolean isSolidBlock(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos).isSolid() || level.getBlockState(pos).liquid();
    }

    /**
     * If the Tardis can start flight at the time of this method call
     *
     * @return true if able to, false if not
     */
    public boolean canBeginFlight() {
        return !operator.getInteriorManager().isGeneratingDesktop() && !operator.getInteriorManager().isWaitingToGenerate() && !isInFlight && ticksTakingOff <= 0 && !this.isHandbrakeOn && !this.isCrashing && !this.isOutOfFuel();
    }


    /**
     * Logic to handle starting flight
     *
     * @return false if didn't start flight, true if flight was started
     */
    public boolean beginFlight(boolean autoLand, Optional<GlobalConsoleBlockEntity> consoleBlockEntity) {

        if (this.targetLocation.getLevel().dimension() == Level.END) {

            if (!TardisHelper.hasTheEndBeenCompleted(this.targetLocation.getLevel())) {
                if (this.currentConsole != null) {
                    this.operator.getLevel().playSound(null, this.currentConsole.getBlockPos(), SoundRegistry.FLIGHT_FAIL_START.get(), SoundSource.BLOCKS, 1, 1);
                }


                for (Player player : this.operator.getLevel().players()) {
                    PlayerUtil.sendMessage(player, Component.translatable(ModMessages.NO_END_DRAGON_PREVENTS), true);
                }

                this.setThrottleStage(0);
                return false;
            }
        }

        if (this.canBeginFlight()) {
            this.autoLand = autoLand;
            this.flightDistance = 0;
            this.distanceCovered = 0;
            this.fastReturnLocation = this.operator.getExteriorManager().getLastKnownLocation();

            TardisNavLocation targetPosition = this.operator.getPilotingManager().getTargetLocation();
            TardisNavLocation lastKnownLocation = this.operator.getExteriorManager().getLastKnownLocation();

            this.flightDistance = calculateFlightDistance(lastKnownLocation, targetPosition);
            this.operator.getFlightDanceManager().startFlightDance(this.currentConsole);

            operator.setDoorClosed(true);
            operator.getLevel().playSound(null, operator.getInternalDoor().getDoorPosition(), SoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.AMBIENT, 10f, 1f);
            operator.getExteriorManager().playSoundAtShell(SoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.BLOCKS, 1, 1);
            this.isInFlight = true;
            this.ticksInFlight = 0;
            this.ticksTakingOff = 1;
            this.operator.getExteriorManager().setIsTakingOff(true);
            //Debug if the blockstate at the current position during takeoff is air. If not air, it means we have forgotten to actually remove the exterior block which could be the cause of the duplication issue
//            System.out.println(this.operator.getLevel().getBlockState(this.operator.getExteriorManager().getLastKnownLocation().getPosition()).getBlock().toString());

            return true;
        }
        return false;
    }

    /**
     * If the Tardis can end flight at the time of this method call
     *
     * @return true if able to, false if not
     */
    public boolean canEndFlight() {
        return isInFlight && ticksInFlight >= (20 * 5) && ticksTakingOff <= 0 && (distanceCovered >= flightDistance || this.autoLand) && !this.isCrashing;
    }

    public void recalculateFlightDistance() {
        TardisNavLocation targetPosition = this.operator.getPilotingManager().getTargetLocation();
        TardisNavLocation lastKnownLocation = this.operator.getExteriorManager().getLastKnownLocation();

        this.flightDistance = calculateFlightDistance(lastKnownLocation, targetPosition);
        this.operator.getFlightDanceManager().startFlightDance(this.currentConsole);

    }

    public int calculateFlightDistance(TardisNavLocation startingPoint, TardisNavLocation endingPoint) {
        BlockPos startingPointPos = startingPoint.getPosition();
        BlockPos endingPointPos = endingPoint.getPosition();

        int distance = startingPointPos.distManhattan(endingPointPos);

        if (startingPoint.getLevel() != endingPoint.getLevel()) {
            distance += 500 + this.operator.getLevel().random.nextInt(250);
        }

        return distance;
    }

    /**
     * Logic to handle ending flight
     *
     * @param forceFlightEnd Ignores the required flight time conditions for the TARDIS to land and lands.
     * @return false if didn't end flight, true if flight was ended
     */
    public boolean endFlight(boolean forceFlightEnd) {
        if (forceFlightEnd || this.canEndFlight()) {
            this.ticksInFlight = 0;

            this.ticksLanding = TICKS_LANDING_MAX;

            TardisExteriorManager exteriorManager = operator.getExteriorManager();

            Level level = operator.getLevel();

            TardisNavLocation landingLocation = this.targetLocation;
            TardisNavLocation location = findClosestValidPosition(landingLocation);

            exteriorManager.placeExteriorBlock(operator, location);

            exteriorManager.playSoundAtShell(SoundRegistry.TARDIS_LAND.get(), SoundSource.BLOCKS, 1, 1);

            if (currentConsole != null) {
                level.playSound(null, currentConsole.getBlockPos(), SoundRegistry.TARDIS_LAND.get(), SoundSource.AMBIENT, 10f, 1f);
            } else {
                level.playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundRegistry.TARDIS_LAND.get(), SoundSource.AMBIENT, 10f, 1f);
            }


            return true;
        }
        return false;

    }

    /**
     * Ends a flight earlier than intended, setting the target position at the percent completed of flight.
     *
     * @param dramatic Play sounds to show the TARDIS doesn't like it.
     */
    private void endFlightEarly(boolean dramatic) {
        BlockPos targetPosition = this.targetLocation.getPosition();
        BlockPos startingPosition = this.operator.getExteriorManager().getLastKnownLocation().getPosition();
        float percentage = this.getFlightPercentageCovered();
        float percentageX = (targetPosition.getX() - startingPosition.getX()) * percentage;
        float percentageY = (targetPosition.getY() - startingPosition.getY()) * percentage;
        float percentageZ = (targetPosition.getZ() - startingPosition.getZ()) * percentage;

        TardisNavLocation newLocation = new TardisNavLocation(new BlockPos((int) percentageX, (int) percentageY, (int) percentageZ), this.targetLocation.getDirection(), percentage > 0.49f ? this.targetLocation.getLevel() : this.operator.getExteriorManager().getLastKnownLocation().getLevel());
        this.targetLocation = newLocation;

        if (dramatic) {
            for (Player player : this.operator.getLevel().players()) {
                MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.DARKNESS, 180, 180, false, false);
                player.addEffect(mobEffectInstance);
            }
            if (this.currentConsole != null) {
                this.operator.getLevel().explode(null, this.currentConsole.getBlockPos().getX(), this.currentConsole.getBlockPos().getY(), this.currentConsole.getBlockPos().getZ(), 2f, Level.ExplosionInteraction.NONE);
            }
        }

        this.endFlight(true);
    }

    /**
     * Start to remove the Tardis Shell block and set up fast return location data
     */
    public void enterTimeVortex() {
        operator.getExteriorManager().removeExteriorBlock();
        this.ticksTakingOff = 0;
        this.operator.getExteriorManager().setIsTakingOff(false);
        TardisNavLocation lastKnown = operator.getExteriorManager().getLastKnownLocation();
        TardisEvents.TAKE_OFF.invoker().onTakeOff(operator, lastKnown.getLevel(), lastKnown.getPosition());

        if (this.currentConsole != null) {
            operator.getFlightDanceManager().startFlightDance(this.currentConsole);
        }

    }

    public void onFlightEnd() {
        this.operator.getFlightDanceManager().stopDancing();
        this.isInFlight = false;
        this.ticksTakingOff = 0;
        this.autoLand = false;
        TardisEvents.LAND.invoker().onLand(operator, getTargetLocation().getLevel(), getTargetLocation().getPosition());
    }

    // Triggers the crash event.
    public void crash() {
        this.canUseControls = false;
        this.isCrashing = true;
        this.ticksCrashing = 8 * 20;
        this.throttleStage = 0;
        this.setHandbrakeOn(true);

        TardisExteriorManager tardisExteriorManager = operator.getExteriorManager();
        Level tarisLevel = operator.getLevel();

        for (Player player : this.operator.getLevel().players()) {
            MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.DARKNESS, 60, 60, false, false);
            player.addEffect(mobEffectInstance);
        }

        // Calculate the random position from what we've gotten.

        if (this.targetLocation.getLevel().dimension() == Level.END) {
            this.targetLocation.setLevel(this.operator.getLevel().getServer().overworld());
        }

        float progress = getFlightPercentageCovered();

        Vec3 targetPos = new Vec3(this.targetLocation.getPosition().getX(), this.targetLocation.getPosition().getY(), this.targetLocation.getPosition().getZ());
        BlockPos currentLoc = tardisExteriorManager.getLastKnownLocation().getPosition();
        Vec3 currentPos = new Vec3(currentLoc.getX(), currentLoc.getY(), currentLoc.getZ());

        int x = (int) (currentPos.x + ((targetPos.x - currentPos.x) * progress));
        int y = (int) (currentPos.y + ((targetPos.y - currentPos.y) * progress));
        int z = (int) (currentPos.z + ((targetPos.z - currentPos.z) * progress));

        BlockPos landingLocation = new BlockPos(x, y, z);

        this.setTargetPosition(landingLocation);
        TardisNavLocation landing = this.targetLocation;
        TardisNavLocation location = findClosestValidPosition(landing);

        tardisExteriorManager.placeExteriorBlock(operator, location);

        tardisExteriorManager.playSoundAtShell(SoundRegistry.TARDIS_CRASH_LAND.get(), SoundSource.BLOCKS, 1, 1);
        tarisLevel.playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundRegistry.TARDIS_CRASH_LAND.get(), SoundSource.BLOCKS, 10f, 1f);
    }

    public void onCrashEnd() {
        this.isCrashing = false;
        this.ticksCrashing = 0;
        this.ticksSinceCrash = 1;

        onFlightEnd();
        TardisEvents.TARDIS_CRASH_EVENT.invoker().onTardisCrash(this.operator, this.targetLocation);
    }

    public float getFlightPercentageCovered() {

        if (this.flightDistance == 0) {
            return 0;
        }

        return (float) this.distanceCovered / this.flightDistance;
    }

    public void offsetTargetPositionX(int x) {
        this.targetLocation.setPosition(this.targetLocation.getPosition().offset(x, 0, 0));
    }

    public void offsetTargetPositionY(int y) {
        this.targetLocation.setPosition(this.targetLocation.getPosition().offset(0, y, 0));
    }

    public void offsetTargetPositionZ(int z) {
        this.targetLocation.setPosition(this.targetLocation.getPosition().offset(0, 0, z));
    }

    public TardisNavLocation getTargetLocation() {
        return this.targetLocation;
    }

    /**
     * @return the current fast return location
     */
    public TardisNavLocation getFastReturnLocation() {
        return this.fastReturnLocation;
    }

    public void setTargetLocation(TardisNavLocation targetLocation) {
        this.targetLocation = targetLocation;
    }

    public void setTargetPosition(BlockPos pos) {
        this.targetLocation.setPosition(pos);
    }

    public int getCordIncrement() {
        return getCoordinateIncrements(operator.getUpgradeHandler())[this.cordIncrementIndex];
    }

    public void cycleCordIncrement(int direction) {
        int nextIndex = this.cordIncrementIndex + direction;

        int[] coordinateIncrements = getCoordinateIncrements(operator.getUpgradeHandler());
        if (nextIndex < 0) nextIndex = coordinateIncrements.length - 1;
        if (nextIndex >= coordinateIncrements.length) nextIndex = 0;

        this.cordIncrementIndex = nextIndex;
    }

    public int[] getCoordinateIncrements(UpgradeHandler upgradeHandler) {
        List<Integer> increments = new ArrayList<>(List.of(1, 10, 100));

        for (Upgrade upgrade : Upgrades.UPGRADE_DEFERRED_REGISTRY.getRegistry()) {
            if (upgrade instanceof IncrementUpgrade incrementUpgrade) {
                if (upgrade.isUnlocked(upgradeHandler)) {
                    increments.add(incrementUpgrade.getIncrementAmount());
                }
            }
        }
        Collections.sort(increments);
        return increments.stream().mapToInt(Integer::intValue).toArray();
    }

    public int getThrottleStage() {
        return this.throttleStage;
    }

    public void setThrottleStage(int stage) {
        this.throttleStage = stage;
    }

    public boolean isInFlight() {
        return this.isInFlight;
    }

    public boolean isLanding() {
        return (ticksLanding > 0);
    }

    public boolean isTakingOff() {
        return (ticksTakingOff > 0);
    }

    public boolean canUseControls() {
        return canUseControls;
    }

    public boolean isHandbrakeOn() {
        return this.isHandbrakeOn;
    }

    public void setHandbrakeOn(boolean handbrakeOn) {
        this.isHandbrakeOn = handbrakeOn;
    }

    public void setAutoLand(boolean autoLand) {
        this.autoLand = autoLand;
    }

    public boolean isAutoLandSet() {
        return this.autoLand;
    }

    public boolean isOnCooldown() {
        return (ticksSinceCrash > 0);
    }

    public GlobalConsoleBlockEntity getCurrentConsole() {

        if (this.currentConsole == null && this.currentConsoleBlockPos != null) {
            if (operator.getLevel().getBlockEntity(this.currentConsoleBlockPos) instanceof GlobalConsoleBlockEntity globalConsoleBlockEntity) {
                this.currentConsole = globalConsoleBlockEntity;
            }
        }

        return this.currentConsole;
    }


    public void setCurrentConsole(GlobalConsoleBlockEntity newConsole) {

        if (this.currentConsole != null) {

            Level level = this.currentConsole.getLevel();

            if (level.getBlockState(this.currentConsole.getBlockPos()).getBlock() instanceof GlobalConsoleBlock) {
                level.setBlockAndUpdate(this.currentConsole.getBlockPos(), this.currentConsole.getBlockState().setValue(GlobalConsoleBlock.POWERED, false));
            }
        }


        this.currentConsole = newConsole;
        this.currentConsoleBlockPos = newConsole.getBlockPos();

        Level level = this.currentConsole.getLevel();

        level.setBlockAndUpdate(this.currentConsole.getBlockPos(), this.currentConsole.getBlockState().setValue(GlobalConsoleBlock.POWERED, true));
        level.playSound(null, this.currentConsole.getBlockPos(), SoundRegistry.CONSOLE_POWER_ON.get(), SoundSource.BLOCKS, 2f, 1f);
    }

    /**
     * Accessor for the number of ticks since the Tardis crashed.
     *
     * @return private field ticksSinceCrash
     */
    public int getCooldownTicks() {
        return ticksSinceCrash;
    }


    /**
     * A progress value after crashing that determines how long until cooldown has finished.
     * Zero means it has only started, 1 means that cooldown has finished.
     *
     * @return a percentage value between 0 - 1.
     */
    public float getCooldownDuration() {
        return (float) ticksSinceCrash / (float) TICKS_COOLDOWN_MAX;
    }

    public boolean isCrashing() {
        return isCrashing;
    }

    /**
     * Accessor for the amount of fuel remaining in the Tardis.
     *
     * @return private field fuel
     */
    public double getFuel() {
        return this.fuel;
    }

    /**
     * Accessor for the maximum amount of fuel a Tardis can hold
     * Will be adjustable in future to allow for upgrades etc.
     *
     * @return private field maximumFuel
     */
    public double getMaximumFuel() {
        return this.maximumFuel;
    }

    /**
     * Accessor for the cost of being in flight
     * Will be adjustable in future to allow for upgrades etc.
     *
     * @return private static field FLIGHT_COST
     */
    private double getFlightFuelCost() {
        return FLIGHT_COST;
    }

    /**
     * The percentage of fuel this Tardis has, from 0 -> 1
     * Preferably should be rounded to the nearest whole number
     *
     * @return the percentage of fuel
     */
    public float getFuelPercentage() {
        return (float) this.fuel / (float) this.getMaximumFuel();
    }

    public boolean isOutOfFuel() {
        return this.fuel == 0;
    }

    public void setFuel(double fuel) {
        double previous = this.fuel;

        this.fuel = Mth.clamp(fuel, 0, this.getMaximumFuel());

        if (this.isOutOfFuel() && previous > 0) {
            this.onRunOutOfFuel();
            return;
        }
        if (!this.isOutOfFuel() && previous == 0) {
            this.onRestoreFuel();
            return;
        }
    }

    /**
     * Removes fuel from the Tardis.
     * Clamps fuel to 0 if it goes below 0
     *
     * @param amount the amount to remove
     */
    public void removeFuel(double amount) {
        this.setFuel(Math.max(0, this.fuel - amount));
    }

    /**
     * Adds fuel to the Tardis
     * Clamps fuel to the maximum if it goes above the maximum
     *
     * @param amount the amount to add
     * @return the amount of fuel left over if it reached maximum
     */
    public double addFuel(double amount) {
        this.setFuel(Math.min(this.getMaximumFuel(), this.fuel + amount));

        double remainder = this.fuel - this.getMaximumFuel();

        return Math.max(0, remainder);
    }

    /**
     * Called when the Tardis runs out of fuel
     */
    private void onRunOutOfFuel() {
        this.operator.tardisClientData().sync();

        // Temporary sfx
        this.operator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 1000f, 0.6f);
    }

    /**
     * Called when the Tardis regains fuel after previously being out of fuel
     */
    private void onRestoreFuel() {
        this.operator.tardisClientData().sync();

        // Temporary sfx
        this.operator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1000f, 0.6f);
    }
}
