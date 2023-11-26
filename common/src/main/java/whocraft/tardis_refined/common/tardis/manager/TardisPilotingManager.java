package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.api.event.TardisEvents;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.RegistryHelper;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.SoundRegistry;

public class TardisPilotingManager {

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
    private ResourceLocation currentExteriorTheme;

    public TardisPilotingManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public void setAutoLand(boolean autoLand) {
        this.autoLand = autoLand;
    }

    public boolean isAutoLandSet() {
        return this.autoLand;
    }

    public ResourceLocation getCurrentExteriorTheme() {
        return this.currentExteriorTheme;
    }

    public void setCurrentExteriorTheme(ResourceLocation theme) {
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
            this.currentExteriorTheme = new ResourceLocation(tag.getString(NbtConstants.CONTROL_CURRENT_EXT));
        }

        if (this.targetLocation == null) {
            this.targetLocation = TardisNavLocation.ORIGIN;
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
            tag.putString(NbtConstants.CONTROL_CURRENT_EXT, this.currentExteriorTheme.toString());
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
                this.targetLocation = TardisNavLocation.ORIGIN;
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

        var originalY = location.getPosition().getY();

        // Do any specific dimension checks
        if (level.dimension() == Level.NETHER) {
            if (location.getPosition().getY() > 127) {
                height = 125;
                failOffset = 10;
                location.setPosition(new BlockPos(location.getPosition().getX(), 80, location.getPosition().getZ()));
            }
        }

        boolean isTargetFine = !isSolidBlock(level, location.getPosition()) && !isSolidBlock(level, location.getPosition().above()) && isSolidBlock(level, location.getPosition().below());
        if (isTargetFine) {
            var safeDir = findSafeDirection(location);
            if (safeDir != null) {return safeDir;}
        }

        for (int i = 0; i < attempts; i++) {
            location.setPosition(getLegalPosition(location.getLevel(), location.getPosition(), originalY));
            var result = scanUpwardsFromCord(location, height);
            if (result != null && location.getPosition().getY() < height && location.getPosition().getY() > minHeight) {
                return result;
            }

            location.setPosition(getLegalPosition(location.getLevel(), location.getPosition(), originalY));
            result = scanDownwardsFromCord(location, minHeight);
            if (result != null && location.getPosition().getY() < height && location.getPosition().getY() > minHeight) {
                return result;
            }

            // Try the next interval in the rotation.
            location.setPosition(getLegalPosition(location.getLevel(), location.getPosition(), originalY));
            location.setPosition(location.getPosition().offset(location.getDirection().getNormal().multiply((int) (failOffset * (1 + ((float) i * 0.1f))))));
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
        if (!isSolidBlock(location.getLevel(), location.getPosition()) && isSolidBlock(location.getLevel(), location.getPosition().below()) && !isSolidBlock(location.getLevel(), location.getPosition().above())) {
            return !location.getLevel().getBlockState(location.getPosition().below()).getFluidState().is(FluidTags.LAVA) && !location.getLevel().getBlockState(location.getPosition().below()).getFluidState().is(FluidTags.WATER);
        }
        return false;
    }

    private TardisNavLocation scanUpwardsFromCord(TardisNavLocation location, int maxHeight) {
        while (location.getPosition().getY() <= maxHeight) {
            if (isSafeToLand(location)) {
                return findSafeDirection(location);
            }

            location.setPosition(location.getPosition().above(1));
        }
        return null;
    }


    private TardisNavLocation scanDownwardsFromCord(TardisNavLocation location, int minHeight) {
        while (location.getPosition().getY() >= minHeight) {

            if (isSafeToLand(location)) {

                return findSafeDirection(location);
            }

            location.setPosition(location.getPosition().below(1));
        }

        return null;
    }

    private TardisNavLocation findSafeDirection(TardisNavLocation location) {

        Direction[] directions = new Direction[]{location.getDirection(), location.getDirection().getOpposite(), Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        for (Direction dir : directions) {
            BlockPos basePos = BlockPos.of(BlockPos.offset(location.getPosition().asLong(), dir));
            if (!isSolidBlock(location.getLevel(), basePos) && !isSolidBlock(location.getLevel(), basePos.above())) {
                return new TardisNavLocation(location.getPosition(), dir, location.getLevel());
            }
        }

        return null;
    }

    private boolean isSolidBlock(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos).isSolid() || level.getBlockState(pos).liquid();
    }

    /**
     * If the Tardis can start flight at the time of this method call
     * @return true if able to, false if not
     */
    public boolean canBeginFlight() {
        return !operator.getInteriorManager().isGeneratingDesktop() && !operator.getInteriorManager().isWaitingToGenerate()
                && !isInFlight && ticksTakingOff <= 0;
    }


    /**
     * Logic to handle starting flight
     * @return false if didn't start flight, true if flight was started
     */
    public boolean beginFlight(boolean autoLand) {

        if (this.canBeginFlight()){
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
        return false;
    }

    /**
     * If the Tardis can end flight at the time of this method call
     * @return true if able to, false if not
     */
    public boolean canEndFlight(){
        return isInFlight && ticksInFlight >= (20 * 5) && ticksTakingOff <= 0 && (this.operator.getTardisFlightEventManager().areControlEventsComplete() || this.autoLand);
    }

    /**
     * Logic to handle ending flight
     * @return false if didn't end flight, true if flight was ended
     */
    public boolean endFlight() {
        if (this.canEndFlight()){
            this.ticksInFlight = 0;
            this.ticksLanding = TICKS_LANDING_MAX;

            TardisExteriorManager exteriorManager = operator.getExteriorManager();
            TardisInteriorManager interiorManager = operator.getInteriorManager();
            Level level = operator.getLevel();

            TardisNavLocation landingLocation = this.targetLocation;
            TardisNavLocation location = findClosestValidPosition(landingLocation);


            exteriorManager.placeExteriorBlock(operator, location);
            if (this.currentExteriorTheme != null) {
                interiorManager.setShellTheme(this.currentExteriorTheme);
            }

            exteriorManager.playSoundAtShell(SoundRegistry.TARDIS_LAND.get(), SoundSource.BLOCKS, 1, 1);
            level.playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundRegistry.TARDIS_LAND.get(), SoundSource.AMBIENT, 1000f, 1f);

            return true;
        }
        return false;

    }

    public void enterTimeVortex() {
        operator.getExteriorManager().removeExteriorBlock();
        this.ticksTakingOff = 0;
        this.operator.getExteriorManager().setIsTakingOff(false);
        TardisNavLocation lastKnown = operator.getExteriorManager().getLastKnownLocation();
        TardisEvents.TAKE_OFF.invoker().onTakeOff(operator, lastKnown.getLevel(), lastKnown.getPosition());
    }

    public void onFlightEnd() {
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

        var progress = this.operator.getTardisFlightEventManager().getPercentComplete();
        Vec3 targetPos = new Vec3(this.targetLocation.getPosition().getX(), this.targetLocation.getPosition().getY(), this.targetLocation.getPosition().getZ());
        BlockPos currentLoc = tardisExteriorManager.getLastKnownLocation().getPosition();
        Vec3 currentPos = new Vec3(currentLoc.getX(), currentLoc.getY(), currentLoc.getZ());

        int x = (int) (currentPos.x + ((targetPos.x - currentPos.x) * progress));
        int y = (int) (currentPos.y + ((targetPos.y - currentPos.y) * progress));
        int z = (int) (currentPos.z + ((targetPos.z - currentPos.z) * progress));

        BlockPos landingLocation = new BlockPos(x, y, z);

        this.setTargetPosition(landingLocation);
        TardisNavLocation landing = this.targetLocation;
        var location =  findClosestValidPosition(landing);

        tardisExteriorManager.placeExteriorBlock(operator, location);
        if (currentExteriorTheme != null) {
            tardisExteriorManager.setShellTheme(currentExteriorTheme);
        }

        tardisExteriorManager.playSoundAtShell(SoundRegistry.TARDIS_CRASH_LAND.get(), SoundSource.BLOCKS, 1, 1);
        tarisLevel.playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundRegistry.TARDIS_CRASH_LAND.get(), SoundSource.BLOCKS, 1000f, 1f);
    }

    public void onCrashEnd() {
        this.isCrashing = false;
        this.ticksCrashing = 0;
        this.ticksSinceCrash = 1;

        onFlightEnd();
        TardisEvents.TARDIS_CRASH_EVENT.invoker().onTardisCrash(this.operator, this.targetLocation);
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

    public void setTargetLocation(TardisNavLocation targetLocation) {
        this.targetLocation = targetLocation;
    }

    public void setTargetPosition(BlockPos pos) {
        this.targetLocation.setPosition(pos);
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
