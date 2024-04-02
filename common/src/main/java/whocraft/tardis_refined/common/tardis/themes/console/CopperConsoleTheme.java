package whocraft.tardis_refined.common.tardis.themes.console;


import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class CopperConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.11f, 0.69f, -1.26f), EntityDimensions.scalable(0.19f, 0.19f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(0.68f, 0.97f, 0.14f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(0.68f, 0.97f, -0.01f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(0.68f, 0.97f, -0.17f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(1.19f, 0.51f, -0.26f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(0.74f, 0.50f, 0.93f), EntityDimensions.scalable(0.19f, 0.19f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.68f, 0.25f, -1.26f), EntityDimensions.scalable(0.19f, 0.19f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.20f, 0.63f, -1.26f), EntityDimensions.scalable(0.19f, 0.19f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.99f, 1.44f, -0.64f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(0.35f, 0.63f, 1.11f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-0.79f, 0.53f, -0.95f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.71f, 0.47f, -0.91f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.02f, 0.63f, -0.25f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.23f, 0.57f, -0.00f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.23f, 0.57f, 0.27f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.23f, 0.57f, 0.54f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.10f, 0.69f, 0.47f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.04f, 0.69f, 0.79f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.13f, 0.82f, 0.97f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.09f, 0.60f, 1.19f), EntityDimensions.scalable(0.19f, 0.19f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.19f, 0.76f, 1.03f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.06f, 0.57f, 0.75f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.96f, 0.69f, 0.69f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.74f, 0.76f, 0.56f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.36f, 1.06f, 0.65f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.25f, 0.57f, 0.51f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.00f, 0.76f, 0.39f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.21f, 0.57f, 0.05f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.21f, 0.64f, -0.28f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.49f, 0.54f, -1.09f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.HANDBRAKE, new Vector3f(-1.20f, 0.63f, -0.57f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ConsoleControl.READOUT, new Vector3f(0.24f, 0.97f, -0.70f), EntityDimensions.scalable(0.13f, 0.13f))


        };
    }
}
