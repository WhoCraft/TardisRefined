package whocraft.tardis_refined.common.tardis.themes.console;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class NukaConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.225f,0.6f,1), new BlockPos(0.125f, 0.1875f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.225f,0.6f,-1), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-.975f, .55f, -0.45f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-1, .55f, -0.35f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-1.05f, .55f, -0.25f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.815f, 0.7f, -0.315f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.9f, .55f, 0.55f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.15f, 0.85f, 0.75f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0,1.15f,-0.6f), new BlockPos(0.1f, 0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.65f, 0.6499985f, 0.6f), new BlockPos(0.1f, 0.1f, 0.1f))
        };
    }
}
