package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import whocraft.tardis_refined.api.event.EventResult;
import whocraft.tardis_refined.api.event.TardisEvents;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.SoundRegistry;

public class TardisControlManager {

    private TardisLevelOperator operator;

    // Location based.
    private TardisNavLocation believedLocation;
    private TardisNavLocation targetLocation;

    // Inflight coundowns
    private boolean isInFlight = false;
    private int ticksInFlight = 0;
    private int ticksLanding = 0;
    private int TICKS_LANDING_MAX = 9 * 20;
    private int ticksTakingOff = 0;

    private int[] coordinateIncrements = new int[]{1, 10, 100, 1000};
    private int cordIncrementIndex = 0;

    private ShellTheme currentExteriorTheme;

    public ShellTheme getCurrentExteriorTheme() {
        return this.currentExteriorTheme;
    }

    public void setCurrentExteriorTheme(ShellTheme theme) {
        this.currentExteriorTheme = theme;
    }

    public TardisControlManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public void loadData(CompoundTag tag) {
        this.isInFlight = tag.getBoolean(NbtConstants.CONTROL_IS_IN_FLIGHT);
        this.targetLocation = NbtConstants.getTardisNavLocation(tag, "ctrl_target", operator);

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
        if (this.currentExteriorTheme != null) {
            tag.putString(NbtConstants.CONTROL_CURRENT_EXT, this.currentExteriorTheme.getSerializedName());
        }
        if (targetLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "ctrl_target", this.targetLocation);
        }

        tag.putInt(NbtConstants.CONTROL_INCREMENT_INDEX, this.cordIncrementIndex);

        return tag;
    }

    public void tick(Level level) {

        if(targetLocation == null){
            var location = this.operator.getExteriorManager().getLastKnownLocation();
            if (targetLocation != null) {
                this.targetLocation = location;
            } else {
                this.targetLocation = new TardisNavLocation(new BlockPos(0, 100, 0), Direction.NORTH, Platform.getServer().overworld());
            }

        }

        if (isInFlight) {
            ticksInFlight++;

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

            if (level.getGameTime() % (20 * 1.75) == 0 && (ticksLanding >= 6 * 20 || ticksLanding == 0)) {
                operator.getLevel().playSound(null, operator.getInternalDoor().getDoorPosition(), SoundRegistry.TARDIS_SINGLE_FLY.get(), SoundSource.AMBIENT, 1000f, 1f);
            }
        }

    }

    public boolean isInFlight() {
        return this.isInFlight;
    }

    public boolean isLanding() {
        return (ticksLanding > 0);
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
                if (level.getBlockState(currentPos.below()).getMaterial().isSolidBlocking()
                        && !level.getBlockState(currentPos.above()).getMaterial().isSolidBlocking()
                        && !level.getBlockState(currentPos.below()).is(Blocks.WATER)) {

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
                if (level.getBlockState(currentPos.below()).getMaterial().isSolidBlocking()
                        && !level.getBlockState(currentPos.above()).getMaterial().isSolidBlocking()
                        && !level.getBlockState(currentPos.below()).is(Blocks.WATER)) {

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

    public void beginFlight() {
        if (isInFlight || ticksTakingOff > 0) {return;}

        // TEMP: Force the targetLocation's level to be the overworld.
        this.targetLocation.level = Platform.getServer().overworld();

        operator.setDoorClosed(true);
        operator.getLevel().playSound(null, operator.getInternalDoor().getDoorPosition(), SoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.AMBIENT, 1000f, 1f);
        operator.getExteriorManager().playSoundAtShell(SoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.BLOCKS, 1, 1);
        this.isInFlight = true;
        this.ticksInFlight = 0;
        this.ticksTakingOff = 1;
        this.operator.getExteriorManager().setIsTakingOff(true);

    }

    public void endFlight() {
        if (!isInFlight || ticksInFlight < (20 * 5) || ticksTakingOff > 0) {
            return;
        }
        this.ticksInFlight = 0;
        this.ticksLanding = TICKS_LANDING_MAX;

        BlockPos landingLocation = this.targetLocation.position;

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

        operator.getExteriorManager().playSoundAtShell(SoundRegistry.TARDIS_LAND.get(), SoundSource.BLOCKS, 1, 1);
        operator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundRegistry.TARDIS_LAND.get(), SoundSource.AMBIENT, 1000f, 1f);
    }

    public void enterTimeVortex() {
        operator.getExteriorManager().removeExteriorBlock();
        this.ticksTakingOff = 0;
        this.operator.getExteriorManager().setIsTakingOff(false);
        TardisNavLocation lastKnown = operator.getControlManager().getTargetLocation();
        TardisEvents.TAKE_OFF.invoker().onTakeOff(operator, lastKnown.level, lastKnown.position);
    }

    public void onFlightEnd() {
        this.isInFlight = false;
        this.ticksTakingOff = 0;
        TardisNavLocation lastKnown = operator.getControlManager().getTargetLocation();
        TardisEvents.LAND.invoker().onLand(operator, lastKnown.level, lastKnown.position);
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
