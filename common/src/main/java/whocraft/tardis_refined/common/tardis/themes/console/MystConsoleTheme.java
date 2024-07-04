package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRControlRegistry;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class MystConsoleTheme extends ConsoleTheme {

    public MystConsoleTheme(ResourceLocation translationKey) {
        super(translationKey);
    }

    @Override
    public void addControlSpecifications() {
        addControl(TRControlRegistry.THROTTLE, -0.39f, 0.59f, 1.04f, 0.25f, 0.25f);
        addControl(TRControlRegistry.X, -0.86f, 0.63f, -0.615f, 0.06f, 0.06f);
        addControl(TRControlRegistry.Y, -0.97f, 0.63f, -0.56f, 0.06f, 0.06f);
        addControl(TRControlRegistry.Z, -0.96f, 0.63f, -0.44f, 0.06f, 0.06f);
        addControl(TRControlRegistry.INCREMENT, -0.39f, 0.50f, -1.07f, 0.25f, 0.25f);
        addControl(TRControlRegistry.ROTATE, -0.02f, 0.69f, -1.10f, 0.12f, 0.13f);
        addControl(TRControlRegistry.RANDOM, 1.14f, 0.56f, 0.17f);
        addControl(TRControlRegistry.DOOR_TOGGLE, 1.09f, 0.50f, 0.67f, 0.06f, 0.06f);
        addControl(TRControlRegistry.MONITOR, 0.735f, 0.56f, 0.405f, 0.35f, 0.25f);
        addControl(TRControlRegistry.DIMENSION, 0.47f, 0.58f, 1.08f, 0.06f, 0.06f);
        addControl(TRControlRegistry.FAST_RETURN, 0.33f, 0.63f, 0.95f, 0.06f, 0.06f);
        addEmptyControl(-0.63f, 0.79f, 0.35f);
        addEmptyControl(-0.88f, 0.69f, 0.81f);
        addEmptyControl(-0.69f, 0.88f, -0.44f, 0.25f, 0.25f);
        addEmptyControl(-0.94f, 0.69f, -0.29f, 0.13f, 0.12f);
        addEmptyControl(-1.14f, 0.59f, -0.37f, 0.13f, 0.12f);
        addEmptyControl(-0.71f, 0.66f, -0.81f, 0.13f, 0.12f);
        addEmptyControl(0.93f, 0.60f, -0.56f, 0.13f, 0.12f);
        addControl(TRControlRegistry.FUEL, 0.76f, 0.51f, 0.90f, 0.13f, 0.12f);
        addEmptyControl(-0.01f, 0.58f, 1.06f);
        addEmptyControl(-0.94f, 0.74f, 0.53f);
        addEmptyControl(-1.01f, 0.53f, 0.57f);
        addControl(TRControlRegistry.MONITOR, -0.005f, 0.85f, -0.72f, 0.4f, 0.25f);
        addControl(TRControlRegistry.ROTATE, 0.78f, 0.63f, -0.87f, 0.12f, 0.12f);
        addControl(TRControlRegistry.ROTATE, 0.59f, 0.80f, -0.38f, 0.12f, 0.12f);
        addControl(TRControlRegistry.HANDBRAKE, -1.01f, 0.56f, 0.09f, 0.25f, 0.25f);
        addControl(TRControlRegistry.READOUT, -0.02f, 0.69f, -1.09f, 0.12f, 0.13f);
    }

}
