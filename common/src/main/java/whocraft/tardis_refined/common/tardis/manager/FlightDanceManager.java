package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.nbt.CompoundTag;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.registry.TRControlRegistry;

import java.util.ArrayList;
import java.util.List;

public class FlightDanceManager extends BaseHandler {


    private TardisLevelOperator operator;
    private TardisPilotingManager pilotingManager;
    private List<ControlEntity> controlEntityList = new ArrayList<>();
    private int damagedControlCount = 0;

    private boolean weAreDancing = false;

    public FlightDanceManager(TardisLevelOperator operator) {
        this.operator = operator;
        this.pilotingManager = this.operator.getPilotingManager();
    }

    public TardisLevelOperator getOperator() {
        return this.operator;
    }

    public boolean isDancing() {
        return this.weAreDancing;
    }

    private List<ControlEntity> getNonCriticalControls(GlobalConsoleBlockEntity controllerConsole) {
        var controls = controllerConsole.getControlEnttityList();
        return controls.stream().filter(x -> !(x.controlSpecification().control().equals(TRControlRegistry.THROTTLE.get())) || !(x.controlSpecification().control().equals(TRControlRegistry.HANDBRAKE.get())) || !(x.controlSpecification().control().equals(TRControlRegistry.MONITOR.get()))).toList();
    }

    public void startFlightDance(GlobalConsoleBlockEntity controllerConsole) {
        this.controlEntityList = getNonCriticalControls(controllerConsole);
        this.weAreDancing = true;
    }

    @Override
    public void tick() {
        if (this.weAreDancing) {
            if (this.operator.getLevel().getGameTime() % (1 * 20) == 0) {
                this.onDanceTick();
            }
        }
    }

    @Override
    CompoundTag saveData(CompoundTag tag) {
        return tag;
    }

    @Override
    void loadData(CompoundTag tag) {

    }

    public void stopDancing() {
        this.updateControlsAfterDance();
        this.controlEntityList = new ArrayList<>();
        this.damagedControlCount = 0;
        this.weAreDancing = false;
    }

    // A dance tick that runs every 20 ticks.
    private void onDanceTick() {

        if (damagedControlCount >= 5) {
            this.stopDancing();
            this.operator.getPilotingManager().crash();
            return;
        }

        int chance = 20 - this.operator.getPilotingManager().getThrottleStage() * 2;
        if (this.operator.getLevel().random.nextInt(chance) == 0) {
            this.triggerNextEvent();
        }

    }

    private void triggerNextEvent() {
        ControlEntity randomControl = controlEntityList.get(this.operator.getLevel().random.nextInt(controlEntityList.size() - 1));
        randomControl.setTickingDown(this);
    }

    public void updateDamageList() {
        this.damagedControlCount += 1;
    }

    private void updateControlsAfterDance() {
        if (this.pilotingManager.getCurrentConsole() != null) {
            this.pilotingManager.getCurrentConsole().killControls();
            this.pilotingManager.getCurrentConsole().spawnControlEntities();
        }

    }
}
