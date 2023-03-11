package whocraft.tardis_refined.common.tardis.themes.console;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityDimensions;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class NukaConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.225f,0.6f,1), EntityDimensions.scalable(0.125f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.225f,0.6f,-1), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-.975f, .55f, -0.45f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-1, .55f, -0.35f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-1.05f, .55f, -0.25f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.815f, 0.7f, -0.315f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.9f, .55f, 0.55f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(0.22500001f, 0.5999985f, 1.0f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.65f, 0.6499985f, 0.6f),  EntityDimensions.scalable(0.1f, 0.1f))
        };
    }
}
