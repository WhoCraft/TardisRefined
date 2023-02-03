package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.core.BlockPos;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class CopperConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.16f, 0.65f, -1.2f), new BlockPos(0.125f, 0.1875f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.175f, 0.65f, -1.2f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(0.725f, 0.95f, 0.15f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(.725f, 0.95f, 0), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(0.725f, 0.95f, -0.15f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(1.22f, 0.45f, -0.25f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(0.775f, 0.55f, 0.95f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.7f, 0.25f, -1.2f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(1.05f,1.4f,-0.5f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-0.81f, 0.5500015f, -0.9499998f), new BlockPos(0.1f, 0.1f, 0.1f))
        };
    }
}
