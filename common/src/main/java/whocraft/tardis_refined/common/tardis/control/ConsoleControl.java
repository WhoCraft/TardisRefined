package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.util.StringRepresentable;
import whocraft.tardis_refined.common.tardis.control.flight.*;
import whocraft.tardis_refined.common.tardis.control.ship.ToggleDoorControl;

public enum ConsoleControl implements StringRepresentable {

    DOOR_TOGGLE("door_toggle", new ToggleDoorControl()),
    X("x_cord", new CoordinateControl(CoordinateButton.X)),
    Y("y_cord", new CoordinateControl(CoordinateButton.Y)),
    Z("z_cord", new CoordinateControl(CoordinateButton.Z)),
    INCREMENT("increment", new IncrementControl()),
    ROTATE("rotate", new RotationControl()),
    RANDOM("random", new RandomControl()),
    THROTTLE("throttle", new ThrottleControl());

    private String id;
    private IControl control;

    ConsoleControl(String id, IControl control) {
        this.id = id;
        this.control = control;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }

    public IControl getControl() {
        return control;
    }
}
