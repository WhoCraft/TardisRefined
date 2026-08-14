package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.api.event.TardisCommonEvents;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.ShellBaseBlockEntity;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.tardis.upgrades.IncrementUpgrade;
import whocraft.tardis_refined.common.capability.tardis.upgrades.SpeedUpgrade;
import whocraft.tardis_refined.common.capability.tardis.upgrades.Upgrade;
import whocraft.tardis_refined.common.capability.tardis.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.control.flight.DimensionalControl;
import whocraft.tardis_refined.common.util.LevelHelper;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.registry.TRBlockRegistry;
import whocraft.tardis_refined.registry.TRSoundRegistry;
import whocraft.tardis_refined.registry.TRUpgrades;

import javax.annotation.Nullable;
import java.util.*;

import static whocraft.tardis_refined.constants.NbtConstants.CAN_USE_CONTROLS;
import static whocraft.tardis_refined.constants.NbtConstants.CURRENT_CONSOLE_POS;

public class TardisPilotingManager extends TickableHandler {

    public static final int MAX_THROTTLE_STAGE = 5;
    // CONSTANTS
    private static final int TICKS_LANDING_MAX = 9 * 20;
    public static final int TICKS_COOLDOWN_MAX = (10 * 60) * 20;
    private static final double DEFAULT_MAXIMUM_FUEL = 1000;
    private static final double FLIGHT_COST = 0.5f;
    private final TardisLevelOperator operator;

    // Location based.
    private TardisNavLocation targetLocation = TardisNavLocation.ORIGIN;
    private TardisNavLocation currentLocation = TardisNavLocation.ORIGIN;
    private TardisNavLocation fastReturnLocation = TardisNavLocation.ORIGIN;

    // Inflight timers (ticks)
    private boolean isInFlight = false;
    private int ticksInFlight = 0;
    private int flightDistance = 100;
    private int distanceCovered = 0;
    private int ticksLanding = 0;
    private int ticksTakingOff = 0;
    private boolean sentLandingError = false;

    // Crash Fields
    private int ticksCrashing = 0;
    private int ticksinCrashRecovery = 0;
    private boolean isInCrashRecovery = false;

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

    private Optional<BlockPos> currentConsoleBlockPos = Optional.empty();

    private boolean isPassivelyRefuelling = false;
    private ShellBaseBlockEntity newCurrentBlockEntity;

    public TardisPilotingManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public void endRecovery() {
        this.isInCrashRecovery = false;
        this.canUseControls = true;
        ticksinCrashRecovery = 0;
        this.operator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, TRSoundRegistry.TARDIS_SINGLE_FLY.get(), SoundSource.AMBIENT, 100f, 0.25f);
    }

    @Override
    public void loadData(CompoundTag tag) {
        this.autoLand = tag.getBoolean(NbtConstants.CONTROL_AUTOLAND);
        this.isInFlight = tag.getBoolean(NbtConstants.CONTROL_IS_IN_FLIGHT);

        this.isHandbrakeOn = tag.getBoolean(NbtConstants.IS_HANDBRAKE_ON);
        this.throttleStage = tag.getInt(NbtConstants.THROTTLE_STAGE);

        this.isPassivelyRefuelling = tag.getBoolean(NbtConstants.IS_PASSIVELY_REFUELING);

        this.currentLocation = NbtConstants.getTardisNavLocation(tag, NbtConstants.CURRENT_LOCATION);
        this.targetLocation = NbtConstants.getTardisNavLocation(tag, NbtConstants.TARGET_LOCATION);
        this.fastReturnLocation = NbtConstants.getTardisNavLocation(tag, NbtConstants.RETURN_LOCATION);

        if (tag.contains(CURRENT_CONSOLE_POS)) {
            this.currentConsoleBlockPos = Optional.of(NbtUtils.readBlockPos(tag.getCompound(CURRENT_CONSOLE_POS)));
        } else {
            this.currentConsoleBlockPos = Optional.empty();
        }


        this.ticksCrashing = tag.getInt(NbtConstants.TICKS_CRASHING);
        this.ticksinCrashRecovery = tag.getInt(NbtConstants.RECOVERY_TICKS);
        this.isInCrashRecovery = tag.getBoolean(NbtConstants.IS_IN_RECOVERY);
        this.flightDistance = tag.getInt(NbtConstants.FLIGHT_DISTANCE);
        this.distanceCovered = tag.getInt(NbtConstants.DISTANCE_COVERED);
        this.canUseControls = tag.getBoolean(CAN_USE_CONTROLS);

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

        tag.putInt(NbtConstants.TICKS_CRASHING, this.ticksCrashing);
        tag.putInt(NbtConstants.RECOVERY_TICKS, this.ticksinCrashRecovery);
        tag.putBoolean(NbtConstants.IS_IN_RECOVERY, this.isInCrashRecovery);
        tag.putInt(NbtConstants.FLIGHT_DISTANCE, this.flightDistance);
        tag.putInt(NbtConstants.DISTANCE_COVERED, this.distanceCovered);
        tag.putBoolean(CAN_USE_CONTROLS, this.canUseControls);
        tag.putBoolean(NbtConstants.IS_PASSIVELY_REFUELING, this.isPassivelyRefuelling);

        if (currentConsoleBlockPos.isPresent()) {
            tag.put(CURRENT_CONSOLE_POS, NbtUtils.writeBlockPos(this.currentConsoleBlockPos.get()));
        }


        NbtConstants.writeTardisNavLocation(tag, NbtConstants.TARGET_LOCATION, this.getTargetLocation());
        NbtConstants.writeTardisNavLocation(tag, NbtConstants.CURRENT_LOCATION, this.getCurrentLocation());
        NbtConstants.writeTardisNavLocation(tag, NbtConstants.RETURN_LOCATION, this.getFastReturnLocation());

        tag.putInt(NbtConstants.CONTROL_INCREMENT_INDEX, this.cordIncrementIndex);

        tag.putDouble(NbtConstants.FUEL, this.fuel);
        tag.putDouble(NbtConstants.MAXIMUM_FUEL, this.maximumFuel);

        return tag;
    }

    public void setCurrentLocationOnNextTick(ShellBaseBlockEntity blockEntity) {
        this.newCurrentBlockEntity = blockEntity;
    }

    @Override
    public void tick(ServerLevel level) {

        if (getTargetLocation() == null) {
            setTargetLocation(getCurrentLocation() != null ? getCurrentLocation().copy() : TardisNavLocation.ORIGIN);
        }

        if (isInFlight) {
            loadConsolePosition(level);
            onFlightTick(level);
        } else {
            unloadConsolePosition(level);
        }

        checkThrottleStatesForFlight();

        // If the flight has been completed, then make sure that we're not still dancing.
        if (getFlightPercentageCovered() == 1) {
            if (this.operator.getFlightDanceManager().isDancing()) {
                this.operator.getFlightDanceManager().stopDancing();
            }
        }

        if (ticksinCrashRecovery > 0) {
            tickCrashRecovery();
        }

        if (isPassivelyRefuelling && level.getGameTime() % 60 == 0) {
            this.addFuel(10);

            if (this.getFuel() >= this.getMaximumFuel()) {
                this.setFuel(this.getMaximumFuel());
                this.isPassivelyRefuelling = false;
            }
        } else if (!operator.getInteriorManager().isCave() && level.getGameTime() % 20 == 0 && !isPassivelyRefuelling && this.getFuel() < (this.getMaximumFuel() * 0.05) && this.operator.getTardisState() == TardisLevelOperator.STATE_EYE_OF_HARMONY) {
            level.playSound(null, getSoundCenterPos(), TRSoundRegistry.ALARM.get(), SoundSource.AMBIENT, 10f, 1f);
        }


    }

    private void onFlightTick(ServerLevel level) {
        if (!this.autoLand) {
            var flightDance = operator.getFlightDanceManager();
            if (this.throttleStage == 0) {
                if (flightDance.isDancing()) {
                    flightDance.stopDancing();
                }
            } else {
                if (!flightDance.isDancing() && isInFlight() && !isLanding() && distanceCovered < flightDistance) {
                    flightDance.startFlightDance(getCurrentConsole());
                }
            }
        }

        if (this.throttleStage != 0 || this.autoLand) {
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
                        level.playSound(null, getSoundCenterPos(), TRSoundRegistry.DESTINATION_DING.get(), SoundSource.AMBIENT, 10f, 1f);
                        this.operator.getFlightDanceManager().stopDancing();
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
                this.endFlight(false, false);
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

    private void loadConsolePosition(ServerLevel level) {
        if (getCurrentConsole() != null) {
            BlockPos consolePos = getCurrentConsole().getBlockPos();
            ChunkPos chunkPos = new ChunkPos(consolePos);

            level.getChunkSource().addRegionTicket(
                    TicketType.PORTAL,
                    chunkPos,
                    1,
                    consolePos
            );
        }
    }

    private void unloadConsolePosition(ServerLevel level) {
        if (getCurrentConsole() != null) {
            BlockPos consolePos = getCurrentConsole().getBlockPos();
            ChunkPos chunkPos = new ChunkPos(consolePos);

            level.getChunkSource().removeRegionTicket(
                    TicketType.PORTAL,
                    chunkPos,
                    1,
                    consolePos
            );
        }
    }


    private void checkThrottleStatesForFlight() {
        if (!isInFlight && !this.isHandbrakeOn && this.throttleStage != 0 && this.canBeginFlight()) {
            this.beginFlight(false);
        }

        // End the flight if the TARDIS is peacefully gliding.
        if (isInFlight && !this.canEndFlight() && this.isHandbrakeOn && this.throttleStage == 0 && !this.isLanding() && !this.isTakingOff()) {
            this.endFlightEarly(false);
        }

        boolean justChanged = false;
        if (isInFlight && this.canEndFlight() && !this.isLanding() && !this.isTakingOff() && (this.isHandbrakeOn || this.throttleStage == 0)) {
            if (!this.endFlight(false, false)) {
                if (!sentLandingError) {
                    operator.getLevel().players().forEach(player -> {
                        PlayerUtil.sendMessage(
                                player,
                                Component.translatable(ModMessages.CANNOT_LAND).withStyle(
                                        s -> s.withColor(ChatFormatting.DARK_RED)
                                ),
                                true
                        );
                    });
                }
                sentLandingError = true;
                justChanged = true;
            }
        }
        if (sentLandingError && !justChanged) {
            sentLandingError = false;
        }
    }

    private void tickCrashRecovery() {
        ticksinCrashRecovery++;

        if (ticksinCrashRecovery % 120 == 0) {
            TardisHelper.playCloisterBell(operator);
        }

        // After 10 minutes
        if (ticksinCrashRecovery >= TICKS_COOLDOWN_MAX) {
            endRecovery();
        } else {
            isInCrashRecovery = true;
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
        }
        setTargetLocation(this.fastReturnLocation.copy());
        return true;

    }


    /**
     * @deprecated Please use {@link TardisPilotingManager#findClosestValidPosition(TardisNavLocation, int) instead}
     */
    @Deprecated
    public TardisNavLocation findClosestValidPosition(TardisNavLocation location) {
        return findClosestValidPosition(location, 1).orElse(TardisNavLocation.ORIGIN);
    }

    public Optional<TardisNavLocation> findClosestValidPosition(TardisNavLocation location, int radius) {
        ServerLevel level = location.getLevel();
        BlockPos position = location.getPosition();
        Direction direction = location.getDirection();

        var maxBuildHeight = level.getMaxBuildHeight();
        var minHeight = level.getMinBuildHeight();

        List<TardisNavLocation> solutionsInRow = new ArrayList<>();

        Optional<TardisNavLocation> closest = Optional.empty();

        boolean shouldLandOnShip = false;

        // Make sure TARDIS lands as a part of the ship and not on top of it.
        if (ModCompatChecker.valkyrienSkies()) {
            if (!VSHelper.isBlockInShipyard(level, position) && VSHelper.collidesWithShip(level, position.below())) {
                shouldLandOnShip = true;
            }
        }

        //First manually check if the exact target position can allow us to place the Tardis
        if (this.canPlaceTardis(location) && this.isExitPositionSafeOrLandingPad(location) && !shouldLandOnShip) {
            solutionsInRow.add(location);
        }

        // If the exact target location was a valid area, let's set it as the final position to use for landing, no extra searching needed.
        if (!solutionsInRow.isEmpty()) {
            closest = Optional.of(location);
        } else {

            //If the exact target location isn't valid, check blocks in the vertical column
            List<TardisNavLocation> nextValidLocations = this.findValidLocationInColumn(level, position, direction, minHeight, maxBuildHeight);
            if (!nextValidLocations.isEmpty()) {
                solutionsInRow.addAll(nextValidLocations);
            } else {
                //If the vertical column is not valid, let's check the surrounding area at the same y level.
                List<BlockPos> surroundingPositionsSameYLevel = LevelHelper.getBlockPosInRadius(position, radius, true, false);
                for (BlockPos directionOffset : surroundingPositionsSameYLevel) {
                    TardisNavLocation nextLocation = new TardisNavLocation(directionOffset, location.getDirection(), location.getLevel());
                    if (this.canPlaceTardis(nextLocation) && this.isExitPositionSafeOrLandingPad(nextLocation)) {
                        solutionsInRow.add(nextLocation);
                    }
                }

                //If the surrounding areas also aren't suitable, search vertically in the original location as well as surrounding areas
                //This is a much more expensive search so ideally we don't want to do this.
                if (solutionsInRow.isEmpty()) {

                    List<BlockPos> surroundingPositionsForColumn = LevelHelper.getBlockPosInRadius(position, radius, true, true);

                    for (BlockPos pos : surroundingPositionsForColumn) {
                        List<TardisNavLocation> surroundingColumn = this.findValidLocationInColumn(level, pos, direction, minHeight, maxBuildHeight);
                        if (!surroundingColumn.isEmpty()) {
                            solutionsInRow.addAll(surroundingColumn);
                        }
                    }
                }
            }

            //Now after we have searched all possible solutions, find the closest solution.
            closest = this.findClosestValidPositionFromTarget(solutionsInRow, location);

        }

        return closest;
    }

    private List<TardisNavLocation> findValidLocationInColumn(TardisNavLocation location, int minHeight, int maxBuildHeight) {
        return this.findValidLocationInColumn(location.getLevel(), location.getPosition(), location.getDirection(), minHeight, maxBuildHeight);
    }

    /**
     * Within all Y level positions for a given position, search for valid landing positions
     *
     * @param level          - target Level we are trying to land in
     * @param position       - the original position we are searching vertically in
     * @param direction      - the direction we are landing at
     * @param minHeight      - minimum height to search upwards from
     * @param maxBuildHeight - the maximum height to search under
     * @return
     */
    private List<TardisNavLocation> findValidLocationInColumn(ServerLevel level, BlockPos position, Direction direction, int minHeight, int maxBuildHeight) {

        List<TardisNavLocation> solutionsInRow = new ArrayList<>();

        // Fetch the row of blocks and filter them all out to air.
        List<BlockPos> blockColumn = this.getBlockPosColumn(position, minHeight, maxBuildHeight);
        Map<BlockPos, Direction> directionOverrides = new HashMap<>();
        if (ModCompatChecker.valkyrienSkies()) {
            var box = AABB.of(BoundingBox.fromCorners(position.atY(minHeight), position.atY(maxBuildHeight)));
            for (var ship : VSHelper.getShipsIntersecting(level, box)) {
                if (!ship.isHorizontalEnough()) continue;
                ship.toShipPositions(box).forEach(pos -> {
                    var p = pos.immutable();
                    blockColumn.add(p);
                    directionOverrides.put(p, ship.toShipDirection(direction));
                });
            }
        }

        List<BlockPos> filteredForAir = blockColumn.stream().filter(x -> isLegalLandingBlock(level, x, LandingBlockType.AIR)).toList();
        List<BlockPos> filteredForNonAir = blockColumn.stream().filter(x -> isLegalLandingBlock(level, x, LandingBlockType.GROUND)).toList();

        //Out of all the positions which are considered empty spaces (air), check if each position allows for the Tardis exterior to be placed and the area outside the door is safe
        for (BlockPos airPos : filteredForAir) {

            // Ignore any higher scans above the logical height (e.g. nether roof).
            if (!isWithinLogicalHeight(level, airPos)) {
                continue;
            }

            BlockPos below = airPos.below();
            BlockPos above = airPos.above();

            // Ignore positions on top of ships not in the shipyard. We want the TARDIS to land in the ship world.
            if (ModCompatChecker.valkyrienSkies() && !VSHelper.isBlockInShipyard(level, below)) {
                if (VSHelper.collidesWithShip(level, below)) continue;
            }

            // Check if this position have the space for a TARDIS.
            if (filteredForNonAir.contains(below) && filteredForAir.contains(above)) {
                // Check if the exit position allows an entity to be teleported without suffocating or falling.
                if (this.canPlaceTardis(level, airPos) && this.isExitPositionSafeOrLandingPad(level, airPos, direction)) {
                    var targetDirection = directionOverrides.getOrDefault(airPos, direction);
                    solutionsInRow.add(new TardisNavLocation(airPos, targetDirection, level));
                }
            }
        }
        return solutionsInRow;
    }

    private static int distManhattan(TardisNavLocation lhs, TardisNavLocation rhs) {
        var lhsPos = lhs.getPosition();
        var rhsPos = rhs.getPosition();
        if (ModCompatChecker.valkyrienSkies()) {
            lhsPos = VSHelper.toWorldPosition(lhs.getLevel(), lhsPos);
            rhsPos = VSHelper.toWorldPosition(rhs.getLevel(), rhsPos);
        }
        return lhsPos.distManhattan(rhsPos);
    }

    /**
     * Finds the closest valid position out of a list of possible solutions, from the original intended landing location
     */
    private Optional<TardisNavLocation> findClosestValidPositionFromTarget(List<TardisNavLocation> validPositions, TardisNavLocation targetLocation) {
        int distance = Integer.MAX_VALUE;
        TardisNavLocation intendedLocation = targetLocation;
        TardisNavLocation closestSolution = null;
        for (TardisNavLocation potentialLocation : validPositions) {
            int distanceBetween = Math.abs(distManhattan(potentialLocation, intendedLocation));
            if (distanceBetween < distance) {
                distance = distanceBetween;
                closestSolution = potentialLocation;
            }
        }
        return Optional.ofNullable(closestSolution);
    }

    /**
     * Gets a list of block positions in the same y level as the reference point
     *
     * @param referencePoint - the position to search vertically in
     * @param min            - The minimum Y value to search upwards from
     * @param max            - The maximum Y value to search downwards from (this max value is included in the search)
     * @return
     */
    private List<BlockPos> getBlockPosColumn(BlockPos referencePoint, int min, int max) {

        List<BlockPos> positions = new ArrayList<>();

        for (int i = min; i <= max; i++) {
            positions.add(new BlockPos(referencePoint.getX(), i, referencePoint.getZ()));
        }

        return positions;
    }

    private enum LandingBlockType {
        AIR(false, true),
        GROUND(true, false);

        private final boolean ground;
        private final boolean checkWorldFromShipyard;

        LandingBlockType(boolean ground, boolean checkWorldFromShipyard) {
            this.ground = ground;
            this.checkWorldFromShipyard = checkWorldFromShipyard;
        }
    }

    private boolean isLegalLandingBlock(ServerLevel level, BlockPos pos, LandingBlockType type) {
        if (ModCompatChecker.valkyrienSkies()) {
            if (VSHelper.collidesWithShip(level, pos)) {
                return type.ground;
            }
            if (VSHelper.isBlockInShipyard(level, pos)) {
                if (type.checkWorldFromShipyard && !isLegalLandingBlockExcludeShips(level, VSHelper.toWorldPosition(level, pos), type)) {
                    return false;
                }
            }
        }


        return isLegalLandingBlockExcludeShips(level, pos, type);
    }


    /**
     * Check if the block at the target position is a valid block to land inside.
     **/
    private boolean isLegalLandingBlockExcludeShips(ServerLevel level, BlockPos pos, LandingBlockType type) {
        BlockState state = level.getBlockState(pos);
        // Can land in air or override any block that can be marked as "replaceable" such as snow, tall grass etc.
        boolean isSpaceEmpty = state.isAir() || (state.canBeReplaced() && state.getFluidState().isEmpty() && !state.isCollisionShapeFullBlock(level, pos));
        if (type.ground) {
            return !isSpaceEmpty;
        } else {
            return isSpaceEmpty;
        }
    }

    private boolean isExitPositionSafe(TardisNavLocation location) {
        return this.isExitPositionSafe(location.getLevel(), location.getPosition(), location.getDirection());
    }

    private boolean isExitPositionSafe(ServerLevel level, BlockPos pos, Direction offsetDirection) {
        BlockPos exitPosition = pos.offset(offsetDirection.getNormal()); //Check the block that is facing away from the doors.
        if (this.isLegalLandingBlock(level, exitPosition.above(), LandingBlockType.AIR)
                && this.isLegalLandingBlock(level, exitPosition, LandingBlockType.AIR) //If there is a 2 block space for the entity to be placed at
                && this.isLegalLandingBlock(level, exitPosition.below(), LandingBlockType.GROUND) //If there is a solid block beneath the exit position for the entity to stand on
        ) {
            return true;
        }
        return false;
    }

    private boolean onLandingPad(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(TRBlockRegistry.LANDING_PAD.get());
    }

    private boolean isExitPositionSafeOrLandingPad(ServerLevel level, BlockPos pos, Direction offsetDirection) {
        return isExitPositionSafe(level, pos, offsetDirection) || onLandingPad(level, pos);
    }

    private boolean isExitPositionSafeOrLandingPad(TardisNavLocation location) {
        return this.isExitPositionSafeOrLandingPad(location.getLevel(), location.getPosition(), location.getDirection());
    }

    private boolean isWithinLogicalHeight(ServerLevel level, BlockPos pos) {
        if (ModCompatChecker.valkyrienSkies() && VSHelper.isBlockInShipyard(level, pos)) {
            return true;
        }
        return pos.getY() < (level.getMinBuildHeight() + level.getLogicalHeight());
    }

    /**
     * If there is a 2 block vertical space for the exterior to be placed at, and the block below the exterior is solid
     */
    private boolean canPlaceTardis(TardisNavLocation location) {
        ServerLevel targetLevel = location.getLevel();
        BlockPos pos = location.getPosition();
        boolean isBelowLogicalHeight = isWithinLogicalHeight(location.getLevel(), location.getPosition()) || onLandingPad(location.getLevel(), location.getPosition());
        return isBelowLogicalHeight && this.isLegalLandingBlock(targetLevel, pos, LandingBlockType.AIR) && isLegalLandingBlock(targetLevel, pos.above(), LandingBlockType.AIR) && isLegalLandingBlock(targetLevel, pos.below(), LandingBlockType.GROUND);
    }

    /**
     * If there is a 2 block vertical space for the exterior to be placed at, and the block below the exterior is solid
     */
    private boolean canPlaceTardis(ServerLevel level, BlockPos pos) {
        return this.isLegalLandingBlock(level, pos, LandingBlockType.AIR) && isLegalLandingBlock(level, pos.above(), LandingBlockType.AIR) && isLegalLandingBlock(level, pos.below(), LandingBlockType.GROUND);
    }

    /**
     * If the Tardis can start flight at the time of this method call
     *
     * @return true if able to, false if not
     */
    public boolean canBeginFlight() {
        return !operator.getInteriorManager().isGeneratingDesktop() && !operator.getInteriorManager().isWaitingToGenerate() && !isInFlight && ticksTakingOff <= 0 && !this.isHandbrakeOn && !this.isCrashing && (!this.isOutOfFuel() || this.fuel > 5);
    }

    public BlockPos getSoundCenterPos() {
        return currentConsoleBlockPos.orElse(TardisArchitectureHandler.DESKTOP_CENTER_POS);
    }

    /**
     * Logic to handle starting flight. Must be synced to client
     *
     * @return false if didn't start flight, true if flight was started
     */
    public boolean beginFlight(boolean autoLand) {

        if (this.getFuel() < 50) {

            operator.getLevel().players().forEach(x -> {
                PlayerUtil.sendMessage(x, Component.translatable(ModMessages.CANNOT_START_NO_FUEL), true);
            });

            this.failTakeoff();
            return false;
        }

        if (this.getTargetLocation().getLevel().dimension() == Level.END) {

            if (!TardisHelper.hasTheEndBeenCompleted(this.getTargetLocation().getLevel())) {

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

            TardisNavLocation currentLocationPreTakeoff = getCurrentLocation();

            this.fastReturnLocation = currentLocationPreTakeoff.copy();

            TardisNavLocation targetPosition = this.getTargetLocation();

            this.flightDistance = calculateFlightDistance(currentLocationPreTakeoff, targetPosition);

            if (!autoLand) {
                this.operator.getFlightDanceManager().startFlightDance(getCurrentConsole());
            }


            operator.setDoorClosed(true);
            operator.getLevel().playSound(null, getSoundCenterPos(), TRSoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.AMBIENT, 10f, 1f);
            operator.getExteriorManager().playSoundAtShell(TRSoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.BLOCKS, 1, 1);


            this.isInFlight = true;
            this.ticksInFlight = 0;
            this.ticksTakingOff = 1;
            this.operator.getExteriorManager().setIsTakingOff(isTakingOff());

            this.operator.tardisClientData().sync();//Sync to client
            //Debug if the blockstate at the current position during takeoff is air. If not air, it means we have forgotten to actually remove the exterior block which could be the cause of the duplication issue
//            System.out.println(this.operator.getLevel().getBlockState(this.operator.getExteriorManager().getLastKnownLocation().getPosition()).getBlock().toString());

            return true;
        }
        return false;
    }

    public void failTakeoff() {
        this.operator.getLevel().playSound(null, getSoundCenterPos(), TRSoundRegistry.FLIGHT_FAIL_START.get(), SoundSource.BLOCKS, 1, 1);


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
        TardisNavLocation targetPosition = this.operator.getPilotingManager().getTargetLocation();
        TardisNavLocation lastKnownLocation = getCurrentLocation().copy();

        this.flightDistance = calculateFlightDistance(lastKnownLocation, targetPosition);
        this.operator.getFlightDanceManager().startFlightDance(getCurrentConsole());

    }

    public int calculateFlightDistance(TardisNavLocation startingPoint, TardisNavLocation endingPoint) {
        BlockPos startingPointPos = startingPoint.getPosition();
        BlockPos endingPointPos = endingPoint.getPosition();

        if (ModCompatChecker.valkyrienSkies()) {
            startingPointPos = VSHelper.toWorldPosition(startingPoint.getLevel(), startingPointPos);
            endingPointPos = VSHelper.toWorldPosition(endingPoint.getLevel(), endingPointPos);
        }

        int distance = 1000;

        if (startingPointPos != null && endingPointPos != null && startingPointPos != BlockPos.ZERO && endingPointPos != BlockPos.ZERO) {
            distance = startingPointPos.distManhattan(endingPointPos);
        }

        if (startingPoint.getLevel() != endingPoint.getLevel()) {
            distance += 500 + this.operator.getLevel().random.nextInt(250);
        }

        return distance;
    }

    private TardisNavLocation searchHarderForAValidLandingSpot(
            TardisNavLocation target, boolean canSwitchDimensions
    ) {
        final int tryCount = 10;
        for (int i = 0; i <= tryCount; i++) {
            var optLoc = findClosestValidPosition(target, 5);
            if (optLoc.isPresent()) {
                return optLoc.get();
            } else {
                var random = operator.getLevel().random;
                ServerLevel level;
                if (TRUpgrades.DIMENSION_TRAVEL.get().isUnlocked(operator.getUpgradeHandler()) && canSwitchDimensions) {
                    var levels = DimensionalControl.getAllowedDimensions(operator);
                    if (!levels.isEmpty()) {
                        level = levels.get(random.nextInt(levels.size()));
                    } else {
                        level = target.getLevel();
                    }
                } else {
                    level = target.getLevel();
                }
                int minX = Mth.ceil(level.getWorldBorder().getMinX());
                int minZ = Mth.ceil(level.getWorldBorder().getMinZ());

                int maxX = Mth.floor(level.getWorldBorder().getMaxX());
                int maxZ = Mth.floor(level.getWorldBorder().getMaxZ());

                int range = Mth.floor(Math.pow(2, i + 4));

                minX = Math.max(target.getPosition().getX() - range, minX);
                minZ = Math.max(target.getPosition().getZ() - range, minZ);
                maxX = Math.min(target.getPosition().getX() + range, maxX);
                maxZ = Math.min(target.getPosition().getZ() + range, maxZ);

                if (maxX - minX < 1 || maxZ - minZ < 1) break; // In this invalid state we're never going to find a valid location anyway...

                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
                pos.set(random.nextInt(maxX - minX) + minX, 0, random.nextInt(maxZ - minZ) + minZ);

                if (ModCompatChecker.valkyrienSkies()) {
                    int count = 0;
                    // Wouldn't it be nice with a function to get the area not in the shipyard?
                    while (VSHelper.isBlockInShipyard(level, pos) && count < 100) {
                        pos.set(random.nextInt(maxX - minX) + minX, 0, random.nextInt(maxZ - minZ) + minZ);
                        count++;
                    }
                    if (VSHelper.isBlockInShipyard(level, pos)) {
                        continue;
                    }
                }

                pos.setY(level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ()));

                Direction finalDirection = null;

                for (var direction : Direction.allShuffled(random)) {
                    if (direction.getAxis().isVertical()) continue;
                    if (isExitPositionSafe(level, pos, direction)) {
                        finalDirection = direction;
                        break;
                    }
                }

                if (finalDirection == null) {
                    if (i < tryCount) {
                        continue;
                    }
                    finalDirection = Direction.get(
                            random.nextBoolean() ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE,
                            random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z
                    );
                }

                return new TardisNavLocation(
                        pos.immutable(), finalDirection, level
                );
            }
        }
        return TardisNavLocation.ORIGIN; // Worst case scenario, should not happen very often.
    }

    /**
     * Logic to handle the start of ending the flight. Must be synced to client.
     *
     * @param forceFlightEnd Ignores the required flight time conditions for the TARDIS to land and lands.
     * @return false if didn't end flight, true if flight was ended
     */
    public boolean endFlight(boolean forceFlightEnd, boolean isCrashing) {
        if (forceFlightEnd || this.canEndFlight()) {
            TardisExteriorManager exteriorManager = operator.getExteriorManager();

            Level level = operator.getLevel();

            TardisNavLocation landingLocation = this.getTargetLocation();
            var optLoc = findClosestValidPosition(landingLocation, 1);
            if (optLoc.isEmpty()) {
                if (!forceFlightEnd) {
                    return false;
                }
                optLoc = Optional.of(searchHarderForAValidLandingSpot(landingLocation, isCrashing));
            }
            TardisNavLocation location = optLoc.get();

            this.ticksInFlight = 0;

            this.ticksLanding = TICKS_LANDING_MAX;

            // Added so it updates for everything else
            // TODO: Does this cause https://github.com/WhoCraft/TardisRefined/issues/427 ?
            setTargetLocation(location);
            setCurrentLocation(location);

            exteriorManager.startLanding(operator, location);


            exteriorManager.playSoundAtShell(isCrashing ? TRSoundRegistry.TARDIS_CRASH_LAND.get() : TRSoundRegistry.TARDIS_LAND.get(), SoundSource.BLOCKS, 1, 1);

            level.playSound(null, getSoundCenterPos(), isCrashing ? TRSoundRegistry.TARDIS_CRASH_LAND.get() : TRSoundRegistry.TARDIS_LAND.get(), SoundSource.AMBIENT, 10f, 1f);

            int totalPoints = (int) (distanceCovered * 0.05f);
            this.operator.getUpgradeHandler().addUpgradeXP(totalPoints);

            var players = level.players();
            for (var player : players) {
                PlayerUtil.sendMessage(player, Component.translatable("+" + totalPoints + " XP"), true);
            }
            distanceCovered = 0;
            this.operator.tardisClientData().sync();

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

        BlockPos targetPosition = this.getTargetLocation().getPosition();
        BlockPos startingPosition = this.getCurrentLocation().getPosition();

        if (ModCompatChecker.valkyrienSkies()) {
            targetPosition = VSHelper.toWorldPosition(getTargetLocation().getLevel(), targetPosition);
            startingPosition = VSHelper.toWorldPosition(getCurrentLocation().getLevel(), startingPosition);
        }

        float percentage = this.getFlightPercentageCovered();
        float percentageX = startingPosition.getX() + (targetPosition.getX() - startingPosition.getX()) * percentage;
        float percentageY = startingPosition.getY() + (targetPosition.getY() - startingPosition.getY()) * percentage;
        float percentageZ = startingPosition.getZ() + (targetPosition.getZ() - startingPosition.getZ()) * percentage;

        TardisNavLocation newLocation = new TardisNavLocation(new BlockPos((int) percentageX, (int) percentageY, (int) percentageZ), this.getTargetLocation().getDirection(), percentage > 0.49f ? this.getTargetLocation().getLevel() : this.getCurrentLocation().getLevel());
        setTargetLocation(newLocation);

        if (dramatic) {
            for (Player player : this.operator.getLevel().players()) {
                MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.DARKNESS, 180, 180, false, false);
                player.addEffect(mobEffectInstance);
            }
            this.operator.getLevel().explode(null, getSoundCenterPos().getX(), getSoundCenterPos().getY(), getSoundCenterPos().getZ(), 2f, Level.ExplosionInteraction.NONE);
        }

        this.endFlight(true, false);
    }

    /**
     * Start to remove the Tardis Shell block and set up fast return location data. This means we are no longer taking off.
     */
    public void enterTimeVortex() {
        TardisNavLocation lastKnown = this.getCurrentLocation();
        operator.getExteriorManager().removeExteriorBlock();
        this.ticksTakingOff = 0;
        this.operator.getExteriorManager().setIsTakingOff(false);
        TardisCommonEvents.TAKE_OFF.invoker().onTakeOff(operator, lastKnown.getLevel(), lastKnown.getPosition());

        var console = getCurrentConsole();
        if (console != null && distanceCovered < flightDistance) {
            operator.getFlightDanceManager().startFlightDance(console);
        }


        this.operator.tardisClientData().sync();
    }

    /**
     * Update data to indicate we have completed the landing process.
     */
    public void onFlightEnd() {
        this.operator.getFlightDanceManager().stopDancing();

        this.isInFlight = false;
        this.ticksTakingOff = 0;
        this.autoLand = false;

        if (this.getFuel() < getMaximumFuel() * 0.1) {
            this.operator.getLevel().playSound(null, getSoundCenterPos(), TRSoundRegistry.LOW_FUEL.get(), SoundSource.AMBIENT, 1000, 1);
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

        if (this.getTargetLocation().getLevel().dimension() == Level.END) {
            this.getTargetLocation().setLevel(this.operator.getLevel().getServer().overworld());
        }

        float progress = getFlightPercentageCovered();

        Vec3 targetPos = this.getTargetLocation().getPosition().getCenter();
        BlockPos currentLoc = this.getCurrentLocation().getPosition();
        Vec3 currentPos = new Vec3(currentLoc.getX(), currentLoc.getY(), currentLoc.getZ());

        int x = (int) (currentPos.x + ((targetPos.x - currentPos.x) * progress));
        int y = (int) (currentPos.y + ((targetPos.y - currentPos.y) * progress));
        int z = (int) (currentPos.z + ((targetPos.z - currentPos.z) * progress));

        BlockPos landingLocation = new BlockPos(x, y, z);
        this.setTargetPosition(landingLocation);
        TardisNavLocation weWantToGoHere = this.getTargetLocation().copy();
        setTargetLocation(weWantToGoHere);
        setCurrentLocation(weWantToGoHere);

        endFlight(true, true);

        tardisExteriorManager.playSoundAtShell(TRSoundRegistry.TARDIS_CRASH_LAND.get(), SoundSource.BLOCKS, 1, 1);
        tarisLevel.playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, TRSoundRegistry.TARDIS_CRASH_LAND.get(), SoundSource.BLOCKS, 10f, 1f);
        this.operator.tardisClientData().sync();
    }

    public void onCrashEnd() {
        this.isCrashing = false;
        this.ticksCrashing = 0;
        this.ticksinCrashRecovery = 1;

        onFlightEnd();
        TardisCommonEvents.TARDIS_CRASH_EVENT.invoker().onTardisCrash(this.operator, this.getTargetLocation());
    }

    public float getFlightPercentageCovered() {
        if (this.flightDistance == 0) {
            return 0;
        }
        return Mth.clamp((float) this.distanceCovered / this.flightDistance, 0, 1);
    }

    public void offsetTargetPositionX(int x) {
        this.getTargetLocation().setPosition(this.getTargetLocation().getPosition().offset(x, 0, 0));
    }

    public void offsetTargetPositionY(int y) {
        this.getTargetLocation().setPosition(this.getTargetLocation().getPosition().offset(0, y, 0));
    }

    public void offsetTargetPositionZ(int z) {
        this.getTargetLocation().setPosition(this.getTargetLocation().getPosition().offset(0, 0, z));
    }

    public TardisNavLocation getTargetLocation() {
        return this.targetLocation;
    }


    public void setTargetLocation(TardisNavLocation targetLocation) {
        this.targetLocation = targetLocation.copy();
    }

    /**
     * @return the current fast return location
     */
    public TardisNavLocation getFastReturnLocation() {
        return this.fastReturnLocation;
    }

    public TardisNavLocation getCurrentLocation() {
        return Objects.requireNonNullElse(this.currentLocation, TardisNavLocation.ORIGIN);
    }

    public void setCurrentLocation(TardisNavLocation currentLocation) {
        this.currentLocation = currentLocation.copy();
    }

    public void setTargetPosition(BlockPos pos) {
        this.targetLocation.setPosition(pos);
    }

    public void setTargetDimension(ServerLevel serverLevel) {
        this.targetLocation.setLevel(serverLevel);
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

    public boolean isInRecovery() {
        return (ticksinCrashRecovery > 0);
    }

    public Optional<BlockPos> getCurrentConsoleBlockPos() {
        return currentConsoleBlockPos;
    }

    public GlobalConsoleBlockEntity getCurrentConsole() {

        if (this.currentConsoleBlockPos.isPresent()) {
            if (operator.getLevel().getBlockEntity(this.currentConsoleBlockPos.get()) instanceof GlobalConsoleBlockEntity globalConsoleBlockEntity) {
                return globalConsoleBlockEntity;
            }
        }

        return null;
    }


    public void setCurrentConsole(@Nullable GlobalConsoleBlockEntity newConsole) {

        if (currentConsoleBlockPos.isPresent()) {

            Level level = operator.getLevel();

            if (level != null && level.getBlockState(currentConsoleBlockPos.get()).getBlock() instanceof GlobalConsoleBlock && level.getBlockEntity(currentConsoleBlockPos.get()) instanceof GlobalConsoleBlockEntity consoleBlockEntity) {

                ResourceLocation oldTheme = consoleBlockEntity.theme();
                ConsolePattern oldPattern = consoleBlockEntity.pattern();

                level.setBlock(currentConsoleBlockPos.get(), level.getBlockState(currentConsoleBlockPos.get()).setValue(GlobalConsoleBlock.POWERED, false), Block.UPDATE_ALL);
                GlobalConsoleBlockEntity updated = (GlobalConsoleBlockEntity) level.getBlockEntity(currentConsoleBlockPos.get());
                updated.setConsoleTheme(oldTheme);
                updated.setPattern(oldPattern);
                updated.sendUpdates();

            }
        }


        this.currentConsoleBlockPos = Optional.ofNullable(newConsole != null ? newConsole.getBlockPos() : null);

        if (newConsole != null && newConsole.hasLevel()) {
            Level level = newConsole.getLevel();

            if (level.getBlockState(newConsole.getBlockPos()).getBlock() instanceof GlobalConsoleBlock && level.getBlockEntity(newConsole.getBlockPos()) instanceof GlobalConsoleBlockEntity consoleBlockEntity) {

                ResourceLocation oldTheme = consoleBlockEntity.theme();
                ConsolePattern oldPattern = consoleBlockEntity.pattern();

                level.setBlock(this.currentConsoleBlockPos.get(), newConsole.getBlockState().setValue(GlobalConsoleBlock.POWERED, true), Block.UPDATE_ALL);
                GlobalConsoleBlockEntity updated = (GlobalConsoleBlockEntity) level.getBlockEntity(this.currentConsoleBlockPos.get());
                updated.setConsoleTheme(oldTheme);
                updated.setPattern(oldPattern);
                updated.sendUpdates();

                level.playSound(null, this.currentConsoleBlockPos.get(), TRSoundRegistry.CONSOLE_POWER_ON.get(), SoundSource.BLOCKS, 2f, 1f);

            }
        }
    }

    /**
     * Accessor for the number of ticks since the Tardis crashed.
     *
     * @return private field ticksinCrashRecovery
     */
    public int getCrashRecoveryTicks() {
        return ticksinCrashRecovery;
    }


    /**
     * A progress value after crashing that determines how long until cooldown has finished.
     * Zero means it has only started, 1 means that cooldown has finished.
     *
     * @return a percentage value between 0 - 1.
     */
    public float getCooldownDuration() {
        return (float) ticksinCrashRecovery / (float) TICKS_COOLDOWN_MAX;
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
     **/
    public boolean isPassivelyRefuelling() {
        return this.isPassivelyRefuelling;
    }

    /**
     * Sets the TARDIS to passively fuel
     *
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
