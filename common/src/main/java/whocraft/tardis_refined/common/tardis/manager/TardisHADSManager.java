package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.capability.upgrades.Upgrades;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

public class TardisHADSManager {

    private static final int MAX_DISTANCE = 1000;
    private static final int RETURN_DELAY_MIN = 12000;
    private static final int RETURN_DELAY_MAX = 24000;

    private int timeTillReturn = 0;

    private TardisLevelOperator tardisLevelOperator;

    public TardisHADSManager(TardisLevelOperator tardisLevelOperator) {
        this.tardisLevelOperator = tardisLevelOperator;
    }

    public void tick() {
        tardisLevelOperator.getUpgradeHandler().setUpgradePoints(100);

        if (timeTillReturn > 0) {
            timeTillReturn = timeTillReturn - 1;
            // Check if it's time to return
            if (timeTillReturn == 0 && tardisLevelOperator.getLevel() instanceof ServerLevel serverLevel) {
                returnToPreviousPosition(serverLevel);
            }
        }
    }

    public void loadData(CompoundTag tag) {
        this.timeTillReturn = tag.getInt("timeTillReturn");
    }

    public CompoundTag saveData(CompoundTag tag) {
        tag.putInt("timeTillReturn", this.timeTillReturn);
        return tag;
    }

    public int getTimeTillReturn() {
        return timeTillReturn;
    }

    public void activateHads(Level level, BlockPos blockPos) {
        if (level instanceof ServerLevel serverLevel && timeTillReturn == 0) {
            // Set the time until return
            timeTillReturn = serverLevel.random.nextInt(RETURN_DELAY_MIN, RETURN_DELAY_MAX);

            BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
            if (blockEntity instanceof GlobalShellBlockEntity globalShellBlockEntity && globalShellBlockEntity.TARDIS_ID != null) {
                RandomSource random = serverLevel.random;
                UpgradeHandler upgradeHandler = tardisLevelOperator.getUpgradeHandler();

                if (!Upgrades.HOSTILE_DISPLACEMENT.get().isUnlocked(upgradeHandler)) {
                    return;
                }

                TardisNavLocation lastKnown = tardisLevelOperator.getExteriorManager().getLastKnownLocation();
                int maxX = (int) lastKnown.getLevel().getWorldBorder().getMaxX();
                int maxZ = (int) lastKnown.getLevel().getWorldBorder().getMaxZ();
                BlockPos newLocation = new BlockPos(random.nextInt(maxX - MAX_DISTANCE), lastKnown.getPosition().getY(), random.nextInt(maxZ - MAX_DISTANCE));

                tardisLevelOperator.getPilotingManager().setTargetPosition(newLocation);
                tardisLevelOperator.getPilotingManager().beginFlight(true);
                System.out.println("New destination set. Returning in " + (timeTillReturn / 20) + " seconds. " + newLocation.toShortString());
            }
        }
    }

    public void returnToPreviousPosition(ServerLevel serverLevel) {
        TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
            TardisPilotingManager pilotingManager = tardisLevelOperator.getPilotingManager();
            pilotingManager.preloadFastReturn();
            pilotingManager.beginFlight(true);
            timeTillReturn = 0;
        });
    }
}
