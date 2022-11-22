package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.core.Vec3i;
import net.minecraft.util.StringRepresentable;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;


public enum ConsoleTheme implements StringRepresentable {

    FACTORY("factory", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.THROTTLE, new Vec3i(0, 1,0), new Vec3i(0.1f, 0.1f, 0.1f))
    }),

    TEMP("removethiswhentwoconsolesexitpleaseandthankyouxx", new ControlSpecification[] {
    });

    private final String id;
    private final ControlSpecification[] controlSpecificationList;

    ConsoleTheme(String id, ControlSpecification[] controlSpecificationList) {
        this.id = id;
        this.controlSpecificationList = controlSpecificationList;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }

    public ControlSpecification[] getControlSpecificationList() {
        return controlSpecificationList;
    }
}
