package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.util.StringRepresentable;
import whocraft.tardis_refined.common.tardis.control.flight.ThrottleControl;

public enum ConsoleControl implements StringRepresentable {
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
