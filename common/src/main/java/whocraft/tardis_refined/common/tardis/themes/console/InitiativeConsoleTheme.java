package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.registry.ControlRegistry;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class InitiativeConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ControlRegistry.THROTTLE, new Vector3f(-0.01f, 0.75f, 1.05f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.X, new Vector3f(-0.67f, 0.72f, -0.62f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.Y, new Vector3f(-0.75f, 0.72f, -0.45f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ControlRegistry.Z, new Vector3f(-0.84f, 0.72f, -0.29f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ControlRegistry.INCREMENT, new Vector3f(-0.32f, 0.56f, -1.04f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ControlRegistry.ROTATE, new Vector3f(-1.00f, 0.63f, 0.49f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ControlRegistry.RANDOM, new Vector3f(0.30f, 0.56f, -1.06f), EntityDimensions.scalable(0.12f, 0.12f)),
                new ControlSpecification(ControlRegistry.DOOR_TOGGLE, new Vector3f(0.04f, 0.88f, -0.64f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ControlRegistry.MONITOR, new Vector3f(-0.64f, 1.38f, 0.24f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ControlRegistry.DIMENSION, new Vector3f(-0.54f, 0.81f, -0.37f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ControlRegistry.FAST_RETURN, new Vector3f(-0.87f, 0.75f, 0.44f), EntityDimensions.scalable(0.13f, 0.13f)),

                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.04f, 0.92f, -0.67f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.01f, 0.62f, -0.99f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.99f, 0.62f, 0.34f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.82f, 0.62f, 0.68f), EntityDimensions.scalable(0.12f, 0.12f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.79f, 0.75f, 0.44f), EntityDimensions.scalable(0.12f, 0.12f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.24f, 0.62f, 1.06f), EntityDimensions.scalable(0.12f, 0.12f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.26f, 0.62f, 1.06f), EntityDimensions.scalable(0.12f, 0.12f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.85f, 0.58f, 0.69f), EntityDimensions.scalable(0.12f, 0.12f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.76f, 0.74f, 0.61f), EntityDimensions.scalable(0.12f, 0.12f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(1.05f, 0.61f, -0.27f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(1.01f, 0.61f, -0.35f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.96f, 0.61f, -0.43f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.91f, 0.61f, -0.51f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.87f, 0.61f, -0.58f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.82f, 0.61f, -0.68f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.76f, 0.61f, -0.76f), EntityDimensions.scalable(0.06f, 0.06f)),
                new ControlSpecification(ControlRegistry.HANDBRAKE, new Vector3f(-1.06f, 0.72f, 0.33f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.READOUT, new Vector3f(-0.92f, 0.50f, -0.54f), EntityDimensions.scalable(0.13f, 0.13f))

        };
    }
}
