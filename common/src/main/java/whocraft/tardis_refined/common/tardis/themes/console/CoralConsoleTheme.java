package whocraft.tardis_refined.common.tardis.themes.console;


import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class CoralConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
// Paste this into your designed ConsoleTheme
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.60f, 0.67f, -1.10f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.26f, 0.48f, 1.10f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.01f, 0.48f, 1.03f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(0.24f, 0.48f, 1.09f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.09f, 0.63f, 0.77f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.47f, 0.63f, 0.91f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.37f, 0.55f, -1.28f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0.25f, 0.56f, -1.04f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.72f, 0.94f, -0.61f), EntityDimensions.scalable(0.37f, 0.38f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(1.19f, 0.59f, -0.33f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-1.14f, 0.44f, 0.44f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.04f, 0.49f, 0.70f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.95f, 0.49f, 0.80f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.88f, 0.49f, 0.88f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.81f, 0.49f, 0.96f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.38f, 0.36f, 1.26f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.06f, 0.36f, 0.70f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.69f, 0.49f, 1.20f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.62f, 0.61f, 0.95f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.87f, 0.49f, 0.70f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.04f, 0.49f, 0.42f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.96f, 0.49f, 0.55f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.68f, 0.66f, 0.61f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.57f, 0.29f, -0.01f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.86f, 0.41f, -1.00f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.12f, 0.58f, -1.00f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.02f, 0.58f, -1.00f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.07f, 0.58f, -1.00f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.03f, 0.44f, -1.32f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.12f, 0.44f, -1.29f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.22f, 0.44f, -1.27f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.31f, 0.44f, -1.25f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.39f, 0.44f, -1.25f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ConsoleControl.HANDBRAKE, new Vector3f(-1.03f, 0.68f, -0.03f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ConsoleControl.READOUT, new Vector3f(0.39f, 0.78f, 0.62f), EntityDimensions.scalable(0.13f, 0.13f))


        };
    }
}
