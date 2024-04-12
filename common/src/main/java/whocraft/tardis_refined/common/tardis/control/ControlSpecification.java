package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.registry.RegistrySupplier;


public class ControlSpecification {
    private Control control;
    private Vector3f offsetPosition;
    private EntityDimensions scale;

    public ControlSpecification(Control control, Vector3f offsetPosition, EntityDimensions scale) {
        this.control = control;
        this.offsetPosition = offsetPosition;
        this.scale = scale;
    }

    public ControlSpecification(RegistrySupplier<Control> controlRegistrySupplier, Vector3f offsetPosition, EntityDimensions scalable) {
        this(controlRegistrySupplier.get(), offsetPosition, scalable);
    }

    public Control control() {
        return control;
    }

    public ControlSpecification setControl(Control control) {
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