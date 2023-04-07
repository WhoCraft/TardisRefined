package whocraft.tardis_refined.common.tardis.control;

import com.mojang.math.Vector3f;
import net.minecraft.world.entity.EntityDimensions;

public class ControlSpecification {
    private ConsoleControl control;
    private Vector3f offsetPosition;
    private EntityDimensions scale;

    public ControlSpecification(ConsoleControl control, Vector3f offsetPosition, EntityDimensions scale) {
        this.control = control;
        this.offsetPosition = offsetPosition;
        this.scale = scale;
    }

    public ConsoleControl control() {
        return control;
    }

    public ControlSpecification setControl(ConsoleControl control) {
        this.control = control;
        return this;
    }

    public Vector3f offsetPosition() {
        return offsetPosition;
    }

    public ControlSpecification setOffsetPosition(Vector3f offsetPosition) {
        this.offsetPosition = offsetPosition;
        return this;
    }

    public EntityDimensions scale() {
        return scale;
    }

    public ControlSpecification setScale(EntityDimensions scale) {
        this.scale = scale;
        return this;
    }
}