package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class FactoryConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.87f, 0.58f, -0.45f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.29f, 0.65f, 0.73f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.29f, 0.59f, 0.86f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.29f, 0.53f, 0.99f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.07f, 0.54f, 1.00f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(0.13f, 0.56f, 0.91f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.86f, 0.56f, 0.42f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.01f, 0.63f, -1.00f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.65f, 0.53f, -0.65f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(-0.65f, 0.53f, -0.87f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-1.04f, 0.55f, -0.16f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.READOUT, new Vector3f(-0.90f, 0.50f, 0.44f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.HANDBRAKE, new Vector3f(1.02f, 0.58f, -0.17f), EntityDimensions.scalable(0.13f, 0.13f)),

                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.78f, 0.67f, 0.16f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.54f, 0.67f, 0.58f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.69f, 0.61f, 0.68f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.69f, 0.57f, 0.85f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.58f, 0.67f, 0.33f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.04f, 0.70f, 0.71f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.12f, 0.70f, 0.71f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.27f, 0.65f, 0.72f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.69f, 0.63f, 0.47f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.75f, 0.63f, 0.33f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.10f, 0.69f, -0.80f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.05f, 0.69f, -0.80f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.62f, 0.58f, -0.78f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.70f, 0.58f, -0.63f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.00f, 0.55f, 0.20f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.92f, 0.56f, -0.30f), EntityDimensions.scalable(0.13f, 0.13f))
        };
    }
}
