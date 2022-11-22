package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.core.Vec3i;

public class ControlSpecification {
    public ConsoleControl control;
    public Vec3i offsetPosition;
    public Vec3i scale;

    public ControlSpecification(ConsoleControl control, Vec3i offsetPosition, Vec3i scale) {
        this.control = control;
        this.offsetPosition = offsetPosition;
        this.scale = scale;
    }

}
