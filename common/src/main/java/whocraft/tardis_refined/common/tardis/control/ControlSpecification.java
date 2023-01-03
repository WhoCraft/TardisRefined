package whocraft.tardis_refined.common.tardis.control;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class ControlSpecification {
    private ConsoleControl control;
    private Vec3 offsetPosition;
    private BlockPos scale;

    public ControlSpecification(ConsoleControl control, Vec3 offsetPosition, BlockPos scale) {
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

    public Vec3 offsetPosition() {
        return offsetPosition;
    }

    public ControlSpecification setOffsetPosition(Vec3 offsetPosition) {
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
