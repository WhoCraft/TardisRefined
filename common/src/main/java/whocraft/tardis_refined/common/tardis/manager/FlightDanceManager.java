package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.BlockUtil;
import net.minecraft.nbt.CompoundTag;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;

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
        return controls.stream().filter(x -> x.controlSpecification().control() != ConsoleControl.THROTTLE || x.controlSpecification().control() != ConsoleControl.HANDBRAKE || x.controlSpecification().control() != ConsoleControl.MONITOR ).toList();
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

    public void stopDancing() {
        this.updateControlsAfterDance();
        this.controlEntityList = new ArrayList<>();
        this.damagedControlCount = 0;
        this.weAreDancing = false;
    }

    // A dance tick that runs every 20 ticks.
    private void onDanceTick() {
        // At a random time, run an event.

        if (damagedControlCount >= 5) {
            System.out.println("TARDIS HAS CRASHED!!!!!!");
            this.stopDancing();
            this.operator.getPilotingManager().crash();
            return;
        }

        int chance = 20 - this.operator.getPilotingManager().getThrottleStage() * 2;
        if (this.operator.getLevel().random.nextInt(chance) == 0) {
            System.out.println("New event!!");
            this.triggerNextEvent();
        }

        // Have that event target controls, either randomly or from a specific list.

        // Tell that control to start ticking down.
    }

    private void triggerNextEvent() {
        ControlEntity randomControl = controlEntityList.get(this.operator.getLevel().random.nextInt(controlEntityList.size() - 1));
        randomControl.setTickingDown(this);

        System.out.println("Determined the next random control is: " + randomControl.getName() + ". Type of: " + randomControl.controlSpecification().control().name());
    }

    public void updateDamageList(ControlEntity entity) {
        this.damagedControlCount += 1;
        System.out.println("Damaged count is now " + this.damagedControlCount);
    }

    private void updateControlsAfterDance() {
        if (this.pilotingManager.getCurrentConsole() != null) {
            this.pilotingManager.getCurrentConsole().killControls();
            this.pilotingManager.getCurrentConsole().spawnControlEntities();
        }

    }

    @Override
    CompoundTag saveData(CompoundTag tag) {

        // Save the position of the current console unit.

        return null;
    }

    @Override
    void loadData(CompoundTag tag) {

    }
}
