package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRControlRegistry;

public class FactoryConsoleTheme extends ConsoleTheme {

    public FactoryConsoleTheme(ResourceLocation translationKey) {
        super(translationKey);
    }

    @Override
    public void addControlSpecifications() {
        addControl(TRControlRegistry.THROTTLE, 0.87f, 0.58f, -0.45f, 0.13f, 0.12f);
        addControl(TRControlRegistry.X, -0.29f, 0.65f, 0.73f);
        addControl(TRControlRegistry.Y, -0.29f, 0.59f, 0.86f, 0.13f, 0.12f);
        addControl(TRControlRegistry.Z, -0.29f, 0.53f, 0.99f);
        addControl(TRControlRegistry.INCREMENT, -0.07f, 0.54f, 1.00f);
        addControl(TRControlRegistry.ROTATE, 0.13f, 0.56f, 0.91f, 0.13f, 0.12f);
        addControl(TRControlRegistry.RANDOM, 0.86f, 0.56f, 0.42f);
        addControl(TRControlRegistry.DOOR_TOGGLE, -0.01f, 0.63f, -1.00f);
        addControl(TRControlRegistry.MONITOR, -0.6f, 0.53f, -0.575f, 0.25f, 0.25f);
        addControl(TRControlRegistry.DIMENSION, -0.65f, 0.53f, -0.87f);
        addControl(TRControlRegistry.FAST_RETURN, -1.04f, 0.55f, -0.16f);
        addControl(TRControlRegistry.READOUT, -0.90f, 0.50f, 0.44f);
        addControl(TRControlRegistry.HANDBRAKE, 1.02f, 0.58f, -0.17f);
        addEmptyControl(0.78f, 0.67f, 0.16f);
        addEmptyControl(0.54f, 0.67f, 0.58f);
        addEmptyControl(0.69f, 0.61f, 0.68f);
        addEmptyControl(0.69f, 0.57f, 0.85f);
        addEmptyControl(0.58f, 0.67f, 0.33f);
        addEmptyControl(-0.04f, 0.70f, 0.71f);
        addEmptyControl(0.12f, 0.70f, 0.71f);
        addEmptyControl(0.27f, 0.65f, 0.72f);
        addEmptyControl(-0.69f, 0.63f, 0.47f);
        addEmptyControl(-0.75f, 0.63f, 0.33f);
        addEmptyControl(-0.10f, 0.69f, -0.80f);
        addEmptyControl(0.05f, 0.69f, -0.80f);
        addEmptyControl(0.62f, 0.58f, -0.78f);
        addEmptyControl(0.70f, 0.58f, -0.63f);
        addEmptyControl(1.00f, 0.55f, 0.20f);
        addEmptyControl(0.92f, 0.56f, -0.30f);
        addControl(TRControlRegistry.FUEL, -0.84f, 0.69f, -0.18f);
    }

}
