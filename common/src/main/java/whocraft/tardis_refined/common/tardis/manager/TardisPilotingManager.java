package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.api.event.TardisCommonEvents;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.ShellBaseBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.IncrementUpgrade;
import whocraft.tardis_refined.common.capability.upgrades.SpeedUpgrade;
import whocraft.tardis_refined.common.capability.upgrades.Upgrade;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.LevelHelper;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.registry.TRSoundRegistry;
import whocraft.tardis_refined.registry.TRUpgrades;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TardisPilotingManager extends TickableHandler {

    // CONSTANTS
    private static final int TICKS_LANDING_MAX = 9 * 20;
    private static final int TICKS_COOLDOWN_MAX = (10 * 60) * 20;
    private static final double DEFAULT_MAXIMUM_FUEL = 1000;
    private static final double FLIGHT_COST = 0.5f;

    public static final int MAX_THROTTLE_STAGE = 5;

    private final TardisLevelOperator operator;

    // Location based.
    private TardisNavLocation targetLocation = TardisNavLocation.ORIGIN;
    private TardisNavLocation currentLocation = TardisNavLocation.ORIGIN;
    private TardisNavLocation fastReturnLocation = TardisNavLocation.ORIGIN;
    private TardisNavLocation takeOffLocation = TardisNavLocation.ORIGIN;
    private ShellBaseBlockEntity newCurrentBlockEntity = null;

    // Inflight timers (ticks)
    private boolean isInFlight = false;
    private int ticksInFlight = 0;
    private int flightDistance = 100;
    private int distanceCovered = 0;
    private int ticksLanding = 0;
    private int ticksTakingOff = 0;
    private int ticksCrashing = 0;
    private int ticksSinceCrash = 0;

    private boolean isCrashing = false;

    private int speedModifier = 1;

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

    private boolean isPassivelyRefuelling = false;

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

        this.isPassivelyRefuelling = tag.getBoolean(NbtConstants.IS_PASSIVELY_REFUELING);

        this.currentLocation = NbtConstants.getTardisNavLocation(tag, "current_location", operator);
        this.targetLocation = NbtConstants.getTardisNavLocation(tag, "ctrl_target", operator);
        this.fastReturnLocation = NbtConstants.getTardisNavLocation(tag, "ctrl_fr_loc", operator);
        this.takeOffLocation = NbtConstants.getTardisNavLocation(tag, "take_off_loc", operator);

        this.currentConsoleBlockPos = NbtUtils.readBlockPos(tag.getCompound("currentConsoleBlockPos"));


        this.ticksCrashing = tag.getInt("ticksCrashing");
        this.ticksSinceCrash = tag.getInt("ticksSinceCrash");
        this.flightDistance = tag.getInt(NbtConstants.FLIGHT_DISTANCE);
        this.distanceCovered = tag.getInt(NbtConstants.DISTANCE_COVERED);
        this.canUseControls = tag.getBoolean("canUseControls");

        if (this.targetLocation == null) {
            this.targetLocation = TardisNavLocation.ORIGIN;
        }

        this.cordIncrementIndex = tag.getInt(NbtConstants.CONTROL_INCREMENT_INDEX);
        this.speedModifier = tag.getInt(NbtConstants.SPEED_MODIFIER);

        this.fuel = tag.getDouble(NbtConstants.FUEL);
        this.maximumFuel = tag.getDouble(NbtConstants.MAXIMUM_FUEL);

        if (!tag.contains(NbtConstants.MAXIMUM_FUEL)) {
            this.maximumFuel = DEFAULT_MAXIMUM_FUEL;
        }
    }

    @Override
    public CompoundTag saveData(CompoundTag tag) {
        tag.putBoolean(NbtConstants.CONTROL_IS_IN_FLIGHT, this.isInFlight);
        tag.putBoolean(NbtConstants.CONTROL_AUTOLAND, this.autoLand);
        tag.putBoolean(NbtConstants.IS_HANDBRAKE_ON, this.isHandbrakeOn);
        tag.putInt(NbtConstants.THROTTLE_STAGE, this.throttleStage);
        tag.putInt(NbtConstants.SPEED_MODIFIER, this.speedModifier);

        tag.putInt("ticksCrashing", this.ticksCrashing);
        tag.putInt("ticksSinceCrash", this.ticksSinceCrash);
        tag.putInt(NbtConstants.FLIGHT_DISTANCE, this.flightDistance);
        tag.putInt(NbtConstants.DISTANCE_COVERED, this.distanceCovered);
        tag.putBoolean("canUseControls", this.canUseControls);
        tag.putBoolean(NbtConstants.IS_PASSIVELY_REFUELING, this.isPassivelyRefuelling);

        if (targetLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "ctrl_target", this.targetLocation);
        }

        if (currentConsoleBlockPos != null) {
            tag.put("currentConsolePos", NbtUtils.writeBlockPos(this.currentConsoleBlockPos));
        }

        if (fastReturnLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "ctrl_fr_loc", this.fastReturnLocation);
        }

        if (takeOffLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "take_off_loc", this.takeOffLocation);
        }

        NbtConstants.putTardisNavLocation(tag, "current_location", this.getCurrentLocation());


        tag.putInt(NbtConstants.CONTROL_INCREMENT_INDEX, this.cordIncrementIndex);

        tag.putDouble(NbtConstants.FUEL, this.fuel);
        tag.putDouble(NbtConstants.MAXIMUM_FUEL, this.maximumFuel);

        return tag;
    }
    @Override
    public void tick(ServerLevel level) {

        if (newCurrentBlockEntity != null) {
            if (newCurrentBlockEntity.getLevel() instanceof ServerLevel serverLevel) {
                setCurrentLocation(new TardisNavLocation(
                    newCurrentBlockEntity.getBlockPos(),
                    newCurrentBlockEntity.getBlockState().getValue(ShellBaseBlock.FACING),
                    serverLevel
                ));
            }
            newCurrentBlockEntity = null;
        }

        if (targetLocation == null) {
            if (this.currentLocation != null) { //If the target location is somehow null and the current location isn't null, set target location to the current location
                this.targetLocation = currentLocation;
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

        if (isPassivelyRefuelling && level.getGameTime() % 60 == 0) {
            this.addFuel(10);

            if (this.getFuel() >= this.getMaximumFuel()) {
                this.setFuel(this.getMaximumFuel());
                this.isPassivelyRefuelling = false;
            }
        }

    }

    private void onFlightTick(ServerLevel level) {
        // Don't continue the flight if the throttle isn't active!!!

        if (this.ticksTakingOff == 0 && (this.throttleStage != 0 || this.autoLand)) {
            ticksInFlight++;

            // Removing fuel once every 2.5 seconds
            if (ticksInFlight % (45) == 0) {
                this.removeFuel(this.getFlightFuelCost() * throttleStage);
            }

            if (this.operator.getLevel().getGameTime() % (20) == 0) {
                if (distanceCovered <= flightDistance) {
                    distanceCovered += (int) (throttleStage + (0.5 * throttleStage * speedModifier));

                    // If this tick was enough to push us over.
                    if (distanceCovered >= flightDistance) {
                        if (this.currentConsole != null) {
                            level.playSound(null, currentConsole.getBlockPos(), TRSoundRegistry.DESTINATION_DING.get(), SoundSource.AMBIENT, 10f, 1f);
                            this.operator.getFlightDanceManager().stopDancing();
                        }
                    }
                }
            }

            if (this.isOutOfFuel() && !this.isLanding() && this.ticksCrashing == 0) {
                this.endFlightEarly(true);
            }

            if (this.isHandbrakeOn && !this.isLanding() && !this.isTakingOff() && this.ticksCrashing == 0) {
                this.endFlightEarly(true);
            }

            // Automatically trigger the ship to land for things such as landing pads.
            if (distanceCovered >= flightDistance && autoLand && !this.isLanding()) {
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
            this.operator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, TRSoundRegistry.TARDIS_SINGLE_FLY.get(), SoundSource.AMBIENT, 100f, 0.25f);
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
        BlockPos position = location.getPosition();
        Direction direction = location.getDirection();

        ChunkPos chunkPos = level.getChunk(position).getPos();

        var maxBuildHeight = level.getMaxBuildHeight();
        var minHeight = level.getMinBuildHeight();

        List<TardisNavLocation> solutionsInRow = new ArrayList<>();

        //Force load chunk to search positions
        level.setChunkForced(chunkPos.x, chunkPos.z, true);

        //Set the default location to 0,0,0 at the target level. DO NOT set this to null else we cause a game crash
        TardisNavLocation closest = new TardisNavLocation(BlockPos.ZERO, Direction.NORTH, level);

        //First manually check if the exact target position can allow us to place the Tardis
        if (this.canPlaceTardis(location) && this.isExitPositionSafe(location)) {
            solutionsInRow.add(location);
        }

        // If the exact target location was a valid area, let's set it as the final position to use for landing, no extra searching needed.
        if (!solutionsInRow.isEmpty()) {
            closest = location;
        }
        else{

            //If the exact target location isn't valid, check blocks in the vertical column
            List<TardisNavLocation> nextValidLocations = this.findValidLocationInColumn(level, position, direction, minHeight, maxBuildHeight);
            if (!nextValidLocations.isEmpty()){
                solutionsInRow.addAll(nextValidLocations);
            }
            else {
                //If the vertical column is not valid, let's check the surrounding area at the same y level.
                List<BlockPos> surroundingPositionsSameYLevel = LevelHelper.getBlockPosInRadius(position, 1, true, false);
                for (BlockPos directionOffset : surroundingPositionsSameYLevel) {
                    TardisNavLocation nextLocation = new TardisNavLocation(directionOffset, location.getDirection(), location.getLevel());
                    if (this.canPlaceTardis(nextLocation) && this.isExitPositionSafe(nextLocation)) {
                        solutionsInRow.add(nextLocation);
                    }
                }

                //If the surrounding areas also aren't suitable, search vertically in the original location as well as surrounding areas
                //This is a much more expensive search so ideally we don't want to do this.
                if (solutionsInRow.isEmpty()) {

                    List<BlockPos> surroundingPositionsForColumn = LevelHelper.getBlockPosInRadius(position, 1, true, false);

                    for(BlockPos pos : surroundingPositionsForColumn){
                        List<TardisNavLocation> surroundingColumn = this.findValidLocationInColumn(level, pos, direction, minHeight, maxBuildHeight);
                        if (!surroundingColumn.isEmpty()){
                            solutionsInRow.addAll(surroundingColumn);
                        }
                    }
                }
            }

            //Now after we have searched all possible solutions, find the closest solution.
            closest = this.findClosestValidPositionFromTarget(solutionsInRow, location);

        }

        //Unforce chunk after we are done searching
        level.setChunkForced(chunkPos.x, chunkPos.z, false);

        return closest;
    }

    private List<TardisNavLocation> findValidLocationInColumn(TardisNavLocation location, int minHeight, int maxBuildHeight) {
        return this.findValidLocationInColumn(location.getLevel(), location.getPosition(), location.getDirection(), minHeight, maxBuildHeight);
    }

    /** Within all Y level positions for a given position, search for valid landing positions
     * @param level - target Level we are trying to land in
     * @param position - the original position we are searching vertically in
     * @param direction - the direction we are landing at
     * @param minHeight - minimum height to search upwards from
     * @param maxBuildHeight - the maximum height to search under
     * @return
     */
    private List<TardisNavLocation> findValidLocationInColumn(ServerLevel level, BlockPos position, Direction direction, int minHeight, int maxBuildHeight) {

        List<TardisNavLocation> solutionsInRow = new ArrayList<>();

        // Ignore any higher scans above the nether roof.
        if (level.dimension() == Level.NETHER) {
            maxBuildHeight = 125;
        }

        // Fetch the row of blocks and filter them all out to air.
        Stream<BlockPos> blockColumn = BlockPos.betweenClosedStream(position.atY(minHeight), position.atY(maxBuildHeight));
        Set<BlockPos> filteredForAir = blockColumn.filter(x -> isLegalLandingBlock(level, x))
            .map(x -> x.immutable()) // mojang moment
            .collect(Collectors.toSet());

        if (ModCompatChecker.valkyrienSkies()) {
            Vec3 min = Vec3.atLowerCornerOf(position.atY(minHeight));
            Vec3 max = Vec3.atLowerCornerWithOffset(position.atY(maxBuildHeight), 0.99, 0.99, 0.99);
            // slightly less than a full block wide column
            AABB column = new AABB(min, max);
            // add blocks on ships in the column as possible landing locations
            VSHelper.forEachShipInAABB(level, column, (aabb -> {
                Set<BlockPos> airOnShip = BlockPos.betweenClosedStream(aabb).map(x -> x.immutable())
                    .filter(x -> isLegalLandingBlock(level, x)).collect(Collectors.toSet());
                filteredForAir.addAll(airOnShip);
            }));
        }

        //Out of all the positions which are considered empty spaces (air), check if each position allows for the Tardis exterior to be placed and the area outside the door is safe
        for (BlockPos airPos : filteredForAir) {
            BlockPos below = airPos.below();
            BlockPos above = airPos.above();

            // Check if this position have the space for a TARDIS.
            if (!filteredForAir.contains(below) && filteredForAir.contains(above)) {
                // Check if the exit position allows an entity to be teleported without suffocating or falling.
                if (this.canPlaceTardis(level, airPos) && this.isExitPositionSafe(level, airPos, direction)) {
                    solutionsInRow.add(new TardisNavLocation(airPos, direction, level));
                }
            }
        }

        return solutionsInRow;
    }

    /** Finds the closest valid position out of a list of possible solutions, from the original intended landing location*/
    private TardisNavLocation findClosestValidPositionFromTarget(List<TardisNavLocation> validPositions, TardisNavLocation targetLocation){
        double distance = Double.MAX_VALUE;
        TardisNavLocation intendedLocation = targetLocation;
        Vec3 intendedPosition = targetLocation.getPosition().getCenter();
        TardisNavLocation closestSolution = new TardisNavLocation(BlockPos.ZERO, Direction.NORTH, intendedLocation.getLevel());
        for (TardisNavLocation potentialLocation : validPositions) {
            Vec3 potentialPosition = potentialLocation.getPosition().getCenter();
            if (ModCompatChecker.valkyrienSkies()) {
                potentialPosition = VSHelper.toWorldPosition(potentialLocation.getLevel(), potentialLocation.getPosition(), potentialPosition);
            }
            double distanceBetween = Math.abs(potentialPosition.distanceTo(intendedPosition));
            if (distanceBetween < distance) {
                distance = distanceBetween;
                closestSolution = potentialLocation;
            }
        }
        return closestSolution;
    }


    /**
     * Check if the block at the target position is a valid block to land inside.
     * **/
    private boolean isLegalLandingBlock(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        // Can land in air or override any block that can be marked as "replaceable" such as snow, tall grass etc.
        return state.isAir() || (state.canBeReplaced() && state.getFluidState().isEmpty() && !state.isCollisionShapeFullBlock(level, pos));
    }

    private boolean isExitPositionSafe(TardisNavLocation location){
        return this.isExitPositionSafe(location.getLevel(), location.getPosition(), location.getDirection());
    }

    private boolean isExitPositionSafe(ServerLevel level, BlockPos pos, Direction offsetDirection){
        BlockPos exitPosition = pos.offset(offsetDirection.getNormal()); //Check the block that is facing away from the doors.
        if (this.isLegalLandingBlock(level, exitPosition.above())
                && this.isLegalLandingBlock(level, exitPosition) //If there is a 2 block space for the entity to be placed at
                && !this.isLegalLandingBlock(level, exitPosition.below()) //If there is a solid block beneath the exit position for the entity to stand on
        ) {
            if (ModCompatChecker.valkyrienSkies()) {
                Vec3 vecPos = Vec3.atLowerCornerOf(pos);
                // slightly less than two full blocks
                if (VSHelper.collidesWithShip(level, new AABB(vecPos, vecPos.add(0.99, 1.99 ,0.99)))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /** If there is a 2 block vertical space for the exterior to be placed at, and the block below the exterior is solid*/
    private boolean canPlaceTardis(TardisNavLocation location){
        ServerLevel targetLevel = location.getLevel();
        BlockPos pos = location.getPosition();
        boolean isBelowNetherRoof = (targetLevel.dimension() == Level.NETHER && pos.getY() <= 125);
        return isBelowNetherRoof && canPlaceTardis(targetLevel, pos);
    }

    /** If there is a 2 block vertical space for the exterior to be placed at, and the block below the exterior is solid*/
    private boolean canPlaceTardis(ServerLevel level, BlockPos pos){
        boolean canPlaceTardis = this.isLegalLandingBlock(level, pos) && isLegalLandingBlock(level, pos.above()) && !isLegalLandingBlock(level, pos.below());
        if (!canPlaceTardis)
            return false;

        if (ModCompatChecker.valkyrienSkies()) {
            Vec3 vecPos = Vec3.atLowerCornerOf(pos);
            // slightly less than two full blocks
            if (VSHelper.collidesWithShip(level, new AABB(vecPos, vecPos.add(0.99, 1.99 ,0.99)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * If the Tardis can start flight at the time of this method call
     *
     * @return true if able to, false if not
     */
    public boolean canBeginFlight() {
        return !operator.getInteriorManager().isGeneratingDesktop() && !operator.getInteriorManager().isWaitingToGenerate() && !isInFlight && ticksTakingOff <= 0 && !this.isHandbrakeOn && !this.isCrashing && (!this.isOutOfFuel() || this.fuel > 5);
    }

    /**
     * Logic to handle starting flight. Must be synced to client
     *
     * @return false if didn't start flight, true if flight was started
     */
    public boolean beginFlight(boolean autoLand, Optional<GlobalConsoleBlockEntity> consoleBlockEntity) {

        if (this.getFuel() < 50) {

            operator.getLevel().players().forEach(x -> {
                PlayerUtil.sendMessage(x, Component.translatable(ModMessages.CANNOT_START_NO_FUEL), true);
            });

            this.failTakeoff();
            return false;
        }

        if (this.targetLocation.getLevel().dimension() == Level.END) {

            if (!TardisHelper.hasTheEndBeenCompleted(this.targetLocation.getLevel())) {

                failTakeoff();

                for (Player player : this.operator.getLevel().players()) {
                    PlayerUtil.sendMessage(player, Component.translatable(ModMessages.NO_END_DRAGON_PREVENTS), true);
                }

                return false;
            }
        }



        if (this.canBeginFlight()) {
            this.autoLand = autoLand;
            this.isPassivelyRefuelling = false;
            this.flightDistance = 0;
            this.distanceCovered = 0;
            this.speedModifier = this.getLatestSpeedModifier();

            this.takeOffLocation = getCurrentLocation().copy();
            this.fastReturnLocation = getCurrentLocation().copy();
            TardisNavLocation targetWorldLocation = this.getTargetLocation();

            if (ModCompatChecker.valkyrienSkies()) {
                // Calculate the distance based on world coordinates
                this.takeOffLocation = VSHelper.toWorldLocation(this.takeOffLocation);
                targetWorldLocation = VSHelper.toWorldLocation(targetWorldLocation);
            }


            this.flightDistance = calculateFlightDistance(takeOffLocation, targetWorldLocation);

            if (!autoLand) {
                this.operator.getFlightDanceManager().startFlightDance(this.currentConsole);
            }



            operator.setDoorClosed(true);
            operator.getLevel().playSound(null, operator.getInternalDoor().getDoorPosition(), TRSoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.AMBIENT, 10f, 1f);
            operator.getExteriorManager().playSoundAtShell(TRSoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.BLOCKS, 1, 1);
            this.isInFlight = true;
            this.ticksInFlight = 0;
            this.ticksTakingOff = 1;
            this.operator.getExteriorManager().setIsTakingOff(true);

            this.operator.tardisClientData().sync();//Sync to client
            //Debug if the blockstate at the current position during takeoff is air. If not air, it means we have forgotten to actually remove the exterior block which could be the cause of the duplication issue
//            System.out.println(this.operator.getLevel().getBlockState(this.operator.getExteriorManager().getLastKnownLocation().getPosition()).getBlock().toString());

            return true;
        }
        return false;
    }

    public void failTakeoff() {
        if (this.currentConsole != null) {
            this.operator.getLevel().playSound(null, this.currentConsole.getBlockPos(), TRSoundRegistry.FLIGHT_FAIL_START.get(), SoundSource.BLOCKS, 1, 1);

        }

        this.throttleStage = 0;

    }

    /**
     * If the Tardis can end flight at the time of this method call
     *
     * @return true if able to, false if not
     */
    public boolean canEndFlight() {
        return isInFlight && ticksTakingOff <= 0 && (distanceCovered >= flightDistance || this.autoLand) && !this.isCrashing;
    }

    public void recalculateFlightDistance() {
        TardisNavLocation targetWorldLocation = this.targetLocation;
        if (ModCompatChecker.valkyrienSkies()) {
            // Calculate the distance based on world coordinates
            targetWorldLocation = VSHelper.toWorldLocation(targetWorldLocation);
        }

        this.flightDistance = calculateFlightDistance(takeOffLocation, targetWorldLocation);
        this.operator.getFlightDanceManager().startFlightDance(this.currentConsole);

    }

    public int calculateFlightDistance(TardisNavLocation startingPoint, TardisNavLocation endingPoint) {
        BlockPos startingPointPos = startingPoint.getPosition();
        BlockPos endingPointPos = endingPoint.getPosition();

        int distance = 1000;

        if (startingPointPos != null && endingPointPos != null && startingPointPos != BlockPos.ZERO && endingPointPos != BlockPos.ZERO) {
            distance = startingPointPos.distManhattan(endingPointPos);
        }

        if (startingPoint.getLevel() != endingPoint.getLevel()) {
            distance += 500 + this.operator.getLevel().random.nextInt(250);
        }

        return distance;
    }

    /**
     * Logic to handle the start of ending the flight. Must be synced to client.
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

            currentLocation = location;

            exteriorManager.startLanding(operator, location);

            exteriorManager.playSoundAtShell(TRSoundRegistry.TARDIS_LAND.get(), SoundSource.BLOCKS, 1, 1);

            if (currentConsole != null) {
                level.playSound(null, currentConsole.getBlockPos(), TRSoundRegistry.TARDIS_LAND.get(), SoundSource.AMBIENT, 10f, 1f);
            } else {
                level.playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, TRSoundRegistry.TARDIS_LAND.get(), SoundSource.AMBIENT, 10f, 1f);
            }

            int totalPoints = (int) (distanceCovered * 0.05f);
            this.operator.getUpgradeHandler().addUpgradeXP(totalPoints);

            var players = level.players();
            for (var player : players) {
                PlayerUtil.sendMessage(player, Component.translatable("+" + totalPoints + " XP"), true);
            }

            this.operator.tardisClientData().sync();

            if (this.autoLand) {
                if (ModCompatChecker.valkyrienSkies()) {
                    // if we landed on a landing pad on a ship, set world coordinates as destination after landing.
                    this.targetLocation = VSHelper.toWorldLocation(this.targetLocation);
                }
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
        BlockPos startingPosition = this.takeOffLocation.getPosition();
        float percentage = this.getFlightPercentageCovered();
        float percentageX = startingPosition.getX() + (targetPosition.getX() - startingPosition.getX()) * percentage;
        float percentageY = startingPosition.getY() + (targetPosition.getY() - startingPosition.getY()) * percentage;
        float percentageZ = startingPosition.getZ() + (targetPosition.getZ() - startingPosition.getZ()) * percentage;

        TardisNavLocation newLocation = new TardisNavLocation(new BlockPos((int) percentageX, (int) percentageY, (int) percentageZ), this.targetLocation.getDirection(), percentage > 0.49f ? this.targetLocation.getLevel() : takeOffLocation.getLevel());
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
     * Start to remove the Tardis Shell block and set up fast return location data. This means we are no longer taking off.
     */
    public void enterTimeVortex() {
        operator.getExteriorManager().removeExteriorBlock();
        this.ticksTakingOff = 0;
        this.operator.getExteriorManager().setIsTakingOff(false);
        TardisCommonEvents.TAKE_OFF.invoker().onTakeOff(operator, takeOffLocation.getLevel(), takeOffLocation.getPosition());

        if (this.currentConsole != null) {
            operator.getFlightDanceManager().startFlightDance(this.currentConsole);
        }
        this.operator.tardisClientData().sync();
    }
    /** Update data to indicate we have completed the landing process.*/
    public void onFlightEnd() {
        this.operator.getFlightDanceManager().stopDancing();

        this.isInFlight = false;
        this.ticksTakingOff = 0;
        this.autoLand = false;

        if (this.getFuel() < getMaximumFuel() * 0.1) {
            this.operator.getLevel().playSound(null, this.currentConsoleBlockPos, TRSoundRegistry.LOW_FUEL.get(), SoundSource.AMBIENT, 1000, 1 );
        }

        TardisCommonEvents.LAND.invoker().onLand(operator, getTargetLocation().getLevel(), getTargetLocation().getPosition());
        this.operator.tardisClientData().sync();
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

        BlockPos targetLoc = this.targetLocation.getPosition();
        Vec3 targetPos = new Vec3(targetLoc.getX(), targetLoc.getY(), targetLoc.getZ());
        BlockPos takeOffLoc = takeOffLocation.getPosition();
        Vec3 takeOffPos = new Vec3(takeOffLoc.getX(), takeOffLoc.getY(), takeOffLoc.getZ());

        int x = (int) (takeOffPos.x + ((targetPos.x - takeOffPos.x) * progress));
        int y = (int) (takeOffPos.y + ((targetPos.y - takeOffPos.y) * progress));
        int z = (int) (takeOffPos.z + ((targetPos.z - takeOffPos.z) * progress));

        BlockPos landingLocation = new BlockPos(x, y, z);

        this.setTargetPosition(landingLocation);
        TardisNavLocation landing = this.targetLocation;
        TardisNavLocation location = findClosestValidPosition(landing);

        tardisExteriorManager.startLanding(operator, location);

        tardisExteriorManager.playSoundAtShell(TRSoundRegistry.TARDIS_CRASH_LAND.get(), SoundSource.BLOCKS, 1, 1);
        tarisLevel.playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, TRSoundRegistry.TARDIS_CRASH_LAND.get(), SoundSource.BLOCKS, 10f, 1f);
        this.operator.tardisClientData().sync();
    }

    public void onCrashEnd() {
        this.isCrashing = false;
        this.ticksCrashing = 0;
        this.ticksSinceCrash = 1;

        onFlightEnd();
        TardisCommonEvents.TARDIS_CRASH_EVENT.invoker().onTardisCrash(this.operator, this.targetLocation);
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

    public void setCurrentLocationOnNextTick(ShellBaseBlockEntity blockEntity) {
        this.newCurrentBlockEntity = blockEntity;
    }
    public void setCurrentLocation(TardisNavLocation newLocation) {
        if (newLocation == null)
            return;
        if (this.isInFlight && !operator.getExteriorManager().isLanding()) {
            // in this situation, there should not be a shell, so remove it.
            newLocation.getLevel().removeBlock(newLocation.getPosition(), false);
            return;
        }

        ServerLevel currentLevel = this.currentLocation.getLevel();

        boolean shellExistsSomewhereElse = false;
        // if the new position is different
        if (!currentLevel.equals(newLocation.getLevel())
            || !this.currentLocation.getPosition().equals(newLocation.getPosition())
        ) {
            // if there is a tardis at the old current location
            if (currentLevel.getBlockState(this.currentLocation.getPosition()).getBlock() instanceof ShellBaseBlock
                && currentLevel.getBlockEntity(this.currentLocation.getPosition()) instanceof ShellBaseBlockEntity shellBlockEntity
            ) {
                // and it is the same tardis id
                if (shellBlockEntity.getTardisId().equals(this.operator.getLevel().dimension())) {
                    if (ModCompatChecker.valkyrienSkies()) {
                        boolean inshipyard = VSHelper.isBlockInShipyard(currentLevel, this.currentLocation.getPosition());
                        boolean onShip = VSHelper.isBlockOnShip(currentLevel, this.currentLocation.getPosition());
                        shellExistsSomewhereElse = onShip || !inshipyard;
                    } else {
                        shellExistsSomewhereElse = true;
                    }
                }
            }
        }
        if (shellExistsSomewhereElse) {
            // this tardis already has a shell, so remove this doubly placed one.
            newLocation.getLevel().removeBlock(newLocation.getPosition(), false);
            return;
        }
        this.currentLocation = newLocation;
    }
    public TardisNavLocation getCurrentLocation() {
        return Objects.requireNonNullElse(this.currentLocation, TardisNavLocation.ORIGIN);
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

        for (Map.Entry<ResourceKey<Upgrade>, Upgrade> entry : TRUpgrades.UPGRADE_DEFERRED_REGISTRY.entrySet()) {
            Upgrade upgrade = entry.getValue();
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

            if (level.getBlockState(this.currentConsole.getBlockPos()).getBlock() instanceof GlobalConsoleBlock && level.getBlockEntity(this.currentConsole.getBlockPos()) instanceof GlobalConsoleBlockEntity consoleBlockEntity ) {

                ResourceLocation oldTheme = consoleBlockEntity.theme();
                ConsolePattern oldPattern = consoleBlockEntity.pattern();

                level.setBlock(this.currentConsole.getBlockPos(), this.currentConsole.getBlockState().setValue(GlobalConsoleBlock.POWERED, false), Block.UPDATE_ALL);
                GlobalConsoleBlockEntity updated = (GlobalConsoleBlockEntity) level.getBlockEntity(this.currentConsole.getBlockPos());
                updated.setConsoleTheme(oldTheme);
                updated.setPattern(oldPattern);
                updated.sendUpdates();

            }
        }


        this.currentConsole = newConsole;
        this.currentConsoleBlockPos = newConsole.getBlockPos();

        Level level = this.currentConsole.getLevel();

        if (level.getBlockState(this.currentConsole.getBlockPos()).getBlock() instanceof GlobalConsoleBlock && level.getBlockEntity(this.currentConsole.getBlockPos()) instanceof GlobalConsoleBlockEntity consoleBlockEntity ) {

            ResourceLocation oldTheme = consoleBlockEntity.theme();
            ConsolePattern oldPattern = consoleBlockEntity.pattern();

            level.setBlock(this.currentConsoleBlockPos, this.currentConsole.getBlockState().setValue(GlobalConsoleBlock.POWERED, true), Block.UPDATE_ALL);
            GlobalConsoleBlockEntity updated = (GlobalConsoleBlockEntity) level.getBlockEntity(this.currentConsoleBlockPos);
            updated.setConsoleTheme(oldTheme);
            updated.setPattern(oldPattern);
            updated.sendUpdates();

            level.playSound(null, this.currentConsoleBlockPos, TRSoundRegistry.CONSOLE_POWER_ON.get(), SoundSource.BLOCKS, 2f, 1f);

        }
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
     * Is the TARDIS set to refuel passively?
     * **/
    public boolean isPassivelyRefuelling() {return this.isPassivelyRefuelling;}

    /**
     * Sets the TARDIS to passively fuel
     * @return Returns if it was successful in updating the state. Will fail if the TARDIS is in flight or has crashed.
     */
    public boolean setPassivelyRefuelling(boolean refuel) {
        if (this.isInFlight() || !this.canUseControls()) {
            return false;
        }

        this.isPassivelyRefuelling = refuel;

        return true;
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

    /**
     * Returns the speed modifier as determined by which speed upgrades
     * are unlocked.
     */
    private int getLatestSpeedModifier() {
        UpgradeHandler upgradeHandler = this.operator.getUpgradeHandler();

        this.speedModifier = TRUpgrades.UPGRADE_DEFERRED_REGISTRY.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(upgrade -> upgrade instanceof SpeedUpgrade)
                .map(upgrade -> (SpeedUpgrade) upgrade)
                .filter(upgradeHandler::isUpgradeUnlocked)
                .mapToInt(SpeedUpgrade::getSpeedModifier)
                .max()
                .orElse(1);
        return this.speedModifier;
    }

}
