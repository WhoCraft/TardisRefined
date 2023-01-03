package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class ControlSpecification {
    private ConsoleControl control;
    private Vector3f offsetPosition;
    private BlockPos scale;

    public ControlSpecification(ConsoleControl control, Vector3f offsetPosition, BlockPos scale) {
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

    public BlockPos scale() {
        return scale;
    }

    public ControlSpecification setScale(BlockPos scale) {
        this.scale = scale;
        return this;
    }
}
