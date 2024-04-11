package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.registry.ControlRegistry;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class CrystalConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                // Paste this into your designed ConsoleTheme
                new ControlSpecification(ControlRegistry.THROTTLE, new Vector3f(-0.76f, 1.13f, -0.52f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ControlRegistry.X, new Vector3f(-0.70f, 0.50f, 0.93f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.Y, new Vector3f(-0.52f, 0.50f, 0.89f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ControlRegistry.Z, new Vector3f(-0.45f, 0.50f, 1.05f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.INCREMENT, new Vector3f(-0.92f, 0.46f, 0.87f), EntityDimensions.scalable(0.12f, 0.13f)),
                new ControlSpecification(ControlRegistry.ROTATE, new Vector3f(-0.62f, 0.57f, -1.07f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.RANDOM, new Vector3f(-0.62f, 0.55f, 1.04f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.DOOR_TOGGLE, new Vector3f(1.18f, 0.52f, -0.35f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.MONITOR, new Vector3f(-0.14f, 0.94f, 1.29f), EntityDimensions.scalable(0.38f, 0.38f)),
                new ControlSpecification(ControlRegistry.DIMENSION, new Vector3f(1.30f, 0.33f, -0.07f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ControlRegistry.FAST_RETURN, new Vector3f(-0.39f, 0.39f, -1.26f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(1.11f, 0.56f, 0.11f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(1.11f, 0.56f, -0.16f), EntityDimensions.scalable(0.13f, 0.12f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(1.06f, 0.44f, -0.90f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.61f, 0.56f, -0.96f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.68f, 0.56f, -1.08f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.29f, 0.56f, -1.28f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.52f, 0.61f, -0.74f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-1.03f, 0.64f, -0.52f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.81f, 0.64f, -0.01f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-1.14f, 0.51f, -0.38f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.82f, 1.07f, 0.16f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.72f, 0.52f, 0.93f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.49f, 0.52f, 1.07f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.61f, 0.52f, 1.01f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.29f, 0.52f, 1.23f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.58f, 0.52f, 1.29f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.69f, 0.52f, 1.22f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.83f, 0.52f, 1.14f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(1.12f, 0.40f, -0.99f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.GENERIC_NO_SHOW, new Vector3f(1.12f, 0.40f, -0.99f), EntityDimensions.scalable(0.13f, 0.13f)),
                new ControlSpecification(ControlRegistry.HANDBRAKE, new Vector3f(0.61f, 1.13f, 0.30f), EntityDimensions.scalable(0.25f, 0.25f)),
                new ControlSpecification(ControlRegistry.READOUT, new Vector3f(0.83f, 1.06f, -0.56f), EntityDimensions.scalable(0.13f, 0.13f))

        };
    }
}
