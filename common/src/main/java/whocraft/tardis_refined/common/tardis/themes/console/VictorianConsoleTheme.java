package whocraft.tardis_refined.common.tardis.themes.console;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityDimensions;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class VictorianConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0.9f, 0.50000304f, 0.15f), EntityDimensions.scalable(0.125f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.59999996f, 0.55000305f, 0.65f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.54999995f, 0.75000304f, 0.15f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.49999997f, 0.75000304f, 0.3000002f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.39999998f, 0.75000304f, 0.4000002f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.59999996f, 0.50000304f, -0.7f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.24999997f, 0.6500031f, -0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.25000003f, 0.6500031f, -0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.59999996f, 0.6500031f, -0.35f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(0.8f, 0.50000155f, -0.85f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.54999995f, 0.5f, 0.7000001f), EntityDimensions.scalable(0.1f, 0.1f))
        };
    }
}
