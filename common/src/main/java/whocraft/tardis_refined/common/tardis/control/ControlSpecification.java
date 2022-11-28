package whocraft.tardis_refined.common.tardis.control;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;

public class ControlSpecification {
    public ConsoleControl control;
    public Vector3f offsetPosition;
    public BlockPos scale;

    public ControlSpecification(ConsoleControl control, Vector3f offsetPosition, BlockPos scale) {
        this.control = control;
        this.offsetPosition = offsetPosition;
        this.scale = scale;
    }

}
