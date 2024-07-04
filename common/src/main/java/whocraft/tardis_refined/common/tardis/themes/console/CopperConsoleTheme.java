package whocraft.tardis_refined.common.tardis.themes.console;


import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRControlRegistry;

public class CopperConsoleTheme extends ConsoleTheme {

    public CopperConsoleTheme(ResourceLocation translationKey) {
        super(translationKey);
    }

    @Override
    public void addControlSpecifications() {
        addControl(TRControlRegistry.THROTTLE, 0.11f, 0.69f, -1.26f, 0.19f, 0.19f);
        addControl(TRControlRegistry.X, 0.68f, 0.97f, 0.14f);
        addControl(TRControlRegistry.Y, 0.68f, 0.97f, -0.01f, 0.13f, 0.12f);
        addControl(TRControlRegistry.Z, 0.68f, 0.97f, -0.17f);
        addControl(TRControlRegistry.INCREMENT, 1.19f, 0.51f, -0.26f, 0.13f, 0.12f);
        addControl(TRControlRegistry.ROTATE, 0.74f, 0.50f, 0.93f, 0.19f, 0.19f);
        addControl(TRControlRegistry.RANDOM, 0.68f, 0.25f, -1.26f, 0.19f, 0.19f);
        addControl(TRControlRegistry.DOOR_TOGGLE, -0.20f, 0.63f, -1.26f, 0.19f, 0.19f);
        addControl(TRControlRegistry.MONITOR, 0.89f, 1.39f, -0.515f, 0.45f, 0.4f);
        addControl(TRControlRegistry.DIMENSION, 0.35f, 0.63f, 1.11f);
        addControl(TRControlRegistry.FAST_RETURN, -0.79f, 0.53f, -0.95f);
        addEmptyControl(0.71f, 0.47f, -0.91f);
        addEmptyControl(1.02f, 0.63f, -0.25f);
        addEmptyControl(1.23f, 0.57f, -0.00f);
        addEmptyControl(1.23f, 0.57f, 0.27f, 0.13f, 0.12f);
        addEmptyControl(1.23f, 0.57f, 0.54f, 0.13f, 0.12f);
        addEmptyControl(1.10f, 0.69f, 0.47f, 0.13f, 0.12f);
        addEmptyControl(1.04f, 0.69f, 0.79f, 0.13f, 0.12f);
        addEmptyControl(0.13f, 0.82f, 0.97f, 0.13f, 0.12f);
        addEmptyControl(0.09f, 0.60f, 1.19f, 0.19f, 0.19f);
        addEmptyControl(-0.19f, 0.76f, 1.03f);
        addEmptyControl(-1.06f, 0.57f, 0.75f);
        addEmptyControl(-0.96f, 0.69f, 0.69f);
        addEmptyControl(-0.74f, 0.76f, 0.56f);
        addEmptyControl(-0.36f, 1.06f, 0.65f);
        addEmptyControl(-1.25f, 0.57f, 0.51f);
        addEmptyControl(-1.00f, 0.76f, 0.39f);
        addEmptyControl(-1.21f, 0.57f, 0.05f);
        addEmptyControl(-1.21f, 0.64f, -0.28f);
        addEmptyControl(-0.49f, 0.54f, -1.09f, 0.12f, 0.13f);
        addControl(TRControlRegistry.HANDBRAKE, -1.20f, 0.63f, -0.57f, 0.25f, 0.25f);
        addControl(TRControlRegistry.READOUT, 0.24f, 0.97f, -0.70f);
        addControl(TRControlRegistry.FUEL, 0.55f, 1.00f, -0.51f);

    }
}
