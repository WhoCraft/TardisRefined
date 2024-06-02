package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.registry.RegistrySupplier;


public class ControlSpecification {
    private Control control;
    private Vector3f offsetPosition;
    private EntityDimensions size;

    public ControlSpecification(Control control, Vector3f offsetPosition, EntityDimensions size) {
        this.control = control;
        this.offsetPosition = offsetPosition;
        this.size = size;
    }

    public ControlSpecification(RegistrySupplier<Control> controlRegistrySupplier, Vector3f offsetPosition, EntityDimensions size) {
        this(controlRegistrySupplier.get(), offsetPosition, size);
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

    public EntityDimensions size() {
        return size;
    }

    public ControlSpecification setSize(EntityDimensions size) {
        this.size = size;
        return this;
    }
}