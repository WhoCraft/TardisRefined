package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRControlRegistry;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class ToyotaConsoleTheme extends ConsoleTheme {

    public ToyotaConsoleTheme(ResourceLocation translationKey) {
        super(translationKey);
    }

    @Override
    public void addControlSpecifications() {
        addControl(TRControlRegistry.THROTTLE, 0.27f, 0.50f, 0.94f, 0.19f, 0.19f);
        addControl(TRControlRegistry.X, -0.07f, 0.63f, 0.80f, 0.06f, 0.06f);
        addControl(TRControlRegistry.Y, 0.02f, 0.63f, 0.80f, 0.06f, 0.06f);
        addControl(TRControlRegistry.Z, 0.11f, 0.63f, 0.80f, 0.06f, 0.06f);
        addControl(TRControlRegistry.INCREMENT, -0.72f, 0.71f, 0.14f, 0.13f, 0.12f);
        addControl(TRControlRegistry.ROTATE, -1.06f, 0.53f, -0.19f);
        addControl(TRControlRegistry.RANDOM, 0.58f, 0.57f, -0.74f);
        addControl(TRControlRegistry.DOOR_TOGGLE, -0.01f, 0.69f, -0.69f, 0.12f, 0.13f);
        addControl(TRControlRegistry.MONITOR, 0.34f, 1.04f, 0.53f, 0.39f, 0.24f);
        addControl(TRControlRegistry.MONITOR, -0.32f, 1.04f, -0.56f, 0.39f, 0.24f);
        addControl(TRControlRegistry.DIMENSION, 0.655f, 0.5f, -0.4f, 0.34f, 0.31f);
        addControl(TRControlRegistry.FAST_RETURN, -0.65f, 0.53f, -0.86f);
        addEmptyControl(-0.12f, 0.75f, 0.69f, 0.06f, 0.06f);
        addEmptyControl(0.01f, 0.75f, 0.69f, 0.06f, 0.06f);
        addEmptyControl(0.13f, 0.75f, 0.69f, 0.06f, 0.06f);
        addEmptyControl(-0.03f, 0.51f, 0.98f, 0.13f, 0.12f);
        addEmptyControl(-0.94f, 0.61f, 0.19f, 0.06f, 0.06f);
        addEmptyControl(-1.02f, 0.54f, 0.24f, 0.06f, 0.06f);
        addControl(TRControlRegistry.FUEL, 1.04f, 0.49f, -0.19f, 0.12f, 0.12f);
        addEmptyControl(0.73f, 0.49f, -0.82f, 0.12f, 0.12f);
        addEmptyControl(0.18f, 0.54f, -0.98f, 0.06f, 0.06f);
        addEmptyControl(0.08f, 0.54f, -0.98f, 0.06f, 0.06f);
        addEmptyControl(-0.05f, 0.54f, -0.98f, 0.06f, 0.06f);
        addEmptyControl(-0.14f, 0.54f, -0.98f, 0.06f, 0.06f);
        addEmptyControl(0.02f, 0.64f, -0.79f, 0.06f, 0.06f);
        addEmptyControl(0.13f, 0.64f, -0.79f, 0.06f, 0.06f);
        addEmptyControl(-0.08f, 0.64f, -0.79f, 0.06f, 0.06f);
        addEmptyControl(-0.28f, 0.49f, -1.00f, 0.12f, 0.12f);
        addEmptyControl(0.27f, 0.49f, -1.00f, 0.12f, 0.12f);
        addEmptyControl(-0.88f, 0.54f, 0.37f, 0.13f, 0.12f);
        addEmptyControl(-0.61f, 0.69f, 0.31f, 0.13f, 0.12f);
        addEmptyControl(-0.61f, 0.69f, 0.57f, 0.13f, 0.12f);
        addEmptyControl(-0.77f, 0.53f, 0.60f, 0.13f, 0.12f);
        addEmptyControl(-0.71f, 0.53f, 0.78f, 0.13f, 0.12f);
        addControl(TRControlRegistry.READOUT, 0.88f, 0.56f, -0.17f);
        addControl(TRControlRegistry.HANDBRAKE, -0.34f, 0.44f, 0.935f, 0.25f, 0.25f);

    }

}
