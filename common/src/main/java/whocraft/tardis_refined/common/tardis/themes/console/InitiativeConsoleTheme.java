package whocraft.tardis_refined.common.tardis.themes.console;

import org.joml.Vector3f;
import net.minecraft.core.BlockPos;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class InitiativeConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(1.1920929E-8f, 0.6500015f, -0.9999997f), new BlockPos(0.125f, 0.1875f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(3.576279E-8f, 0.7000061f, 1.0999999f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.59999996f, 0.700003f, -0.5999999f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.75f, 0.7f, -0.44999972f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.84999996f, 0.700003f, -0.2999998f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.9499999f, 0.6f, 0.5000001f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.29999998f, 0.6000015f, -1.0499997f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.8499999f, 0.75f, 0.4500001f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.55f, 1.4500015f, 0.2999998f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.05f, 0.8500015f, -0.59999955f), new BlockPos(0.1f, 0.1f, 0.1f))
        };
    }
}
