package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import whocraft.tardis_refined.NbtConstants;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.registry.SoundRegistry;

public class TardisControlManager {

    private TardisLevelOperator operator;

    // Location based.
    private TardisNavLocation believedLocation;
    private TardisNavLocation targetLocation;
    private boolean isInFlight = false;
    private int ticksInFlight = 0;

    private int[] coordinateIncrements = new int[] {1,10,100,1000};
    private int cordIncrementIndex = 0;

    private ShellTheme currentExteriorTheme;
    public ShellTheme getCurrentExteriorTheme() {return this.currentExteriorTheme;}
    public void setCurrentExteriorTheme(ShellTheme theme) {this.currentExteriorTheme = theme;}

    public TardisControlManager(TardisLevelOperator operator) {
        this.operator = operator;
        this.targetLocation = new TardisNavLocation(new BlockPos(0, 0, 0), Direction.NORTH, Platform.getServer().getLevel(Level.OVERWORLD));
    }

    public void loadData(CompoundTag tag) {
        this.isInFlight = tag.getBoolean(NbtConstants.CONTROL_IS_IN_FLIGHT);
        this.targetLocation = NbtConstants.getTardisNavLocation(tag, "ctrl_target", operator);
        if (this.targetLocation == null) {
            this.targetLocation = new TardisNavLocation(new BlockPos(0, 0, 0), Direction.NORTH, Platform.getServer().getLevel(Level.OVERWORLD));
        }

        this.cordIncrementIndex = tag.getInt(NbtConstants.CONTROL_INCREMENT_INDEX);

    }

    public CompoundTag saveData(CompoundTag tag) {
        tag.putBoolean(NbtConstants.CONTROL_IS_IN_FLIGHT, this.isInFlight);
        if (targetLocation != null) {
            NbtConstants.putTardisNavLocation(tag, "ctrl_target", this.targetLocation);
        }

        tag.putInt(NbtConstants.CONTROL_INCREMENT_INDEX, this.cordIncrementIndex);

        return tag;
    }


    public void tick(Level level) {
        if (isInFlight) {
            ticksInFlight++;

            if (level.getGameTime() % (20 * 1.75) == 0) {
                operator.getLevel().playSound(null, operator.getInternalDoor().getDoorPosition(), SoundRegistry.TARDIS_SINGLE_FLY.get(), SoundSource.AMBIENT, 1000f, 1f);
            }
        }
    }

    public boolean isInFlight() {
        return this.isInFlight;
    }

    public TardisNavLocation getClosestValidPosition(TardisNavLocation location) {
        ServerLevel level = location.level;
        BlockPos currentScanPosition = location.position;

        System.out.println("Starting by checking for flight at: " + location.position.toShortString());

        // We need to be able to determine whether the position we're aiming for is a valid location.

        boolean isTargetAir= level.getBlockState(currentScanPosition).is(Blocks.AIR);

        if (isTargetAir) {
            currentScanPosition = currentScanPosition.below();
            if (level.getBlockState(currentScanPosition).is(Blocks.AIR)) {
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
            if (level.getBlockState(currentPos).is(Blocks.AIR)) {
                // Check if the Shell can be physically in the location.
                if (!level.getBlockState(currentPos.below()).is(Blocks.AIR) && !level.getBlockState(currentPos.below()).is(Blocks.WATER)  && level.getBlockState(currentPos.above()).is(Blocks.AIR)) {

                    // Check that the facing location !!!!!
                    Direction[] directions = new Direction[]{startingLocation.rotation,startingLocation.rotation.getOpposite(), Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                    for (Direction dir:
                         directions) {
                        BlockPos basePos = BlockPos.of(BlockPos.offset(currentPos.asLong(), dir));
                        if (level.getBlockState(basePos).is(Blocks.AIR) && level.getBlockState(basePos.above()).is(Blocks.AIR)) {
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
            if (level.getBlockState(currentPos).is(Blocks.AIR)) {
                // Check if the Shell can be physically in the location.
                if (!level.getBlockState(currentPos.below()).is(Blocks.AIR) && !level.getBlockState(currentPos.below()).is(Blocks.WATER) && level.getBlockState(currentPos.above()).is(Blocks.AIR)) {

//

                    // Check that the facing location !!!!!
                    Direction[] directions = new Direction[]{startingLocation.rotation,startingLocation.rotation.getOpposite(), Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                    for (Direction dir:
                            directions) {
                        BlockPos basePos = BlockPos.of(BlockPos.offset(currentPos.asLong(), dir));
                        if (level.getBlockState(basePos).is(Blocks.AIR) && level.getBlockState(basePos.above()).is(Blocks.AIR)) {
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
        if (isInFlight) {return;}
        System.out.println("TARDIS is taking off");
        operator.setDoorClosed(true);
        operator.getLevel().playSound(null, operator.getInternalDoor().getDoorPosition(), SoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.AMBIENT, 1000f, 1f);
        operator.getExteriorManager().playSoundAtShell(SoundRegistry.TARDIS_TAKEOFF.get(), SoundSource.BLOCKS, 5, 1);
        operator.getExteriorManager().removeExteriorBlock();
        this.isInFlight = true;
        this.ticksInFlight = 0;
    }
    public void endFlight() {
        if (!isInFlight || ticksInFlight < (20 * 5)) {return;}
        this.isInFlight = false;
        this.ticksInFlight = 0;

        BlockPos landingLocation = this.targetLocation.position;

        TardisNavLocation location = null;
        while (location == null) {
            location = getClosestValidPosition(new TardisNavLocation(landingLocation, targetLocation.rotation, operator.getExteriorManager().getLevel()));
            if (location != null) {
                break;
            } else {
                landingLocation = landingLocation.north(1); // Make a more sophisticated version of this!
            }
        }

        operator.getExteriorManager().placeExteriorBlock(operator, location);
        operator.getInteriorManager().setShellTheme(currentExteriorTheme);
        operator.getExteriorManager().playSoundAtShell(SoundRegistry.TARDIS_LAND.get(), SoundSource.BLOCKS, 5, 1);
        operator.getLevel().playSound(null, operator.getInternalDoor().getDoorPosition(), SoundRegistry.TARDIS_LAND.get(), SoundSource.AMBIENT, 1000f, 1f);
    }

    public void offsetTargetPositionX(float x) {
        this.targetLocation.position = this.targetLocation.position.offset(x, 0,0);
    }

    public void offsetTargetPositionY(float y) {
        this.targetLocation.position = this.targetLocation.position.offset(0, y,0);
    }

    public void offsetTargetPositionZ(float z) {
        this.targetLocation.position = this.targetLocation.position.offset(0, 0,z);
    }

    public TardisNavLocation getTargetLocation(){
        return this.targetLocation;
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



}
