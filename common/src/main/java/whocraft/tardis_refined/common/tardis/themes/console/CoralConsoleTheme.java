package whocraft.tardis_refined.common.tardis.themes.console;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRControlRegistry;

public class CoralConsoleTheme extends ConsoleTheme {

    public CoralConsoleTheme(ResourceLocation translationKey) {
        super(translationKey);
    }

    @Override
    public void addControlSpecifications() {
        addControl(TRControlRegistry.THROTTLE, 0.60f, 0.67f, -1.10f);
        addControl(TRControlRegistry.X, -0.26f, 0.48f, 1.10f);
        addControl(TRControlRegistry.Y, -0.01f, 0.48f, 1.03f);
        addControl(TRControlRegistry.Z, 0.24f, 0.48f, 1.09f);
        addControl(TRControlRegistry.INCREMENT, -0.09f, 0.63f, 0.77f);
        addControl(TRControlRegistry.ROTATE, -0.47f, 0.63f, 0.91f);
        addControl(TRControlRegistry.RANDOM, -0.37f, 0.55f, -1.28f);
        addControl(TRControlRegistry.DOOR_TOGGLE, 0.25f, 0.56f, -1.04f);
        addControl(TRControlRegistry.MONITOR, 0.745f, 0.89f, -0.46f, 0.37f, 0.38f);
        addControl(TRControlRegistry.DIMENSION, 1.19f, 0.59f, -0.33f);
        addControl(TRControlRegistry.FAST_RETURN, -1.14f, 0.44f, 0.44f);
        addEmptyControl(-1.04f, 0.49f, 0.70f, 0.06f, 0.06f);
        addEmptyControl(-0.95f, 0.49f, 0.80f, 0.06f, 0.06f);
        addEmptyControl(-0.88f, 0.49f, 0.88f, 0.06f, 0.06f);
        addEmptyControl(-0.81f, 0.49f, 0.96f, 0.06f, 0.06f);
        addEmptyControl(-0.38f, 0.36f, 1.26f);
        addEmptyControl(1.06f, 0.36f, 0.70f);
        addEmptyControl(0.69f, 0.49f, 1.20f);
        addEmptyControl(0.62f, 0.61f, 0.95f);
        addEmptyControl(0.87f, 0.49f, 0.70f);
        addEmptyControl(1.04f, 0.49f, 0.42f);
        addEmptyControl(0.96f, 0.49f, 0.55f, 0.13f, 0.12f);
        addEmptyControl(0.68f, 0.66f, 0.61f, 0.13f, 0.12f);
        addEmptyControl(0.86f, 0.41f, -1.00f, 0.12f, 0.13f);
        addEmptyControl(0.12f, 0.58f, -1.00f, 0.06f, 0.06f);
        addEmptyControl(0.02f, 0.58f, -1.00f, 0.06f, 0.06f);
        addEmptyControl(-0.07f, 0.58f, -1.00f, 0.06f, 0.06f);
        addEmptyControl(0.03f, 0.44f, -1.32f, 0.06f, 0.06f);
        addEmptyControl(0.12f, 0.44f, -1.29f, 0.06f, 0.06f);
        addEmptyControl(0.22f, 0.44f, -1.27f, 0.06f, 0.06f);
        addEmptyControl(0.31f, 0.44f, -1.25f, 0.06f, 0.06f);
        addEmptyControl(0.39f, 0.44f, -1.25f, 0.06f, 0.06f);
        addControl(TRControlRegistry.HANDBRAKE, -1.03f, 0.68f, -0.03f, 0.25f, 0.25f);
        addControl(TRControlRegistry.READOUT, 0.39f, 0.78f, 0.62f);
        addControl(TRControlRegistry.FUEL, 1.61f, 0.27f, -0.01f);

    }
}
