package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.BlockUtil;
import net.minecraft.nbt.CompoundTag;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

import java.util.ArrayList;
import java.util.List;

public class FlightDanceManager extends BaseHandler {

    private GlobalConsoleBlockEntity currentConsole;
    private TardisLevelOperator operator;
    private TardisPilotingManager pilotingManager;
    private List<ControlEntity> controlEntityList = new ArrayList<>();

    private boolean weAreDancing = false;

    public FlightDanceManager(TardisLevelOperator operator) {
        this.operator = operator;
        this.pilotingManager = this.operator.getPilotingManager();
    }


    public void startFlightDance(GlobalConsoleBlockEntity controllerConsole) {
        this.currentConsole = controllerConsole;
        this.controlEntityList = controllerConsole.getControlEnttityList();

        System.out.println("Prepping for the dance");

        TardisNavLocation locationAtStart = this.operator.getExteriorManager().getLastKnownLocation();
        TardisNavLocation targetLocation = this.operator.getPilotingManager().getTargetLocation();

        if (locationAtStart.getPosition() != null && targetLocation.getPosition() != null) {

            double distance = locationAtStart.getPosition().distSqr(targetLocation.getPosition());
            System.out.println("Distance between two points is (not including dimensions) " + distance + " blocks.");
        }



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

    // A dance tick that runs every 20 ticks.
    private void onDanceTick() {
        // At a random time, run an event.

        if (this.operator.getLevel().random.nextInt(5) == 0) {
            System.out.println("New event!!");
            this.triggerNextEvent();
        }

        // Have that event target controls, either randomly or from a specific list.

        // Tell that control to start ticking down.
    }

    private void triggerNextEvent() {
        ControlEntity randomControl = controlEntityList.get(this.operator.getLevel().random.nextInt(controlEntityList.size() - 1));
        randomControl.setTickingDown();

        System.out.println("Determined the next random control is: " + randomControl.getName() + ". Type of: " + randomControl.controlSpecification().control().name());
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
