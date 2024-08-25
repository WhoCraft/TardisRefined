package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRControlRegistry;

public class VictorianConsoleTheme extends ConsoleTheme {

    public VictorianConsoleTheme(ResourceLocation translationKey) {
        super(translationKey);
    }

    @Override
    public void addControlSpecifications() {
        addControl(TRControlRegistry.THROTTLE, -0.55f, 0.50f, 0.61f, 0.25f, 0.25f);
        addControl(TRControlRegistry.X, -0.58f, 0.78f, 0.11f);
        addControl(TRControlRegistry.Y, -0.48f, 0.78f, 0.25f);
        addControl(TRControlRegistry.Z, -0.40f, 0.77f, 0.39f);
        addControl(TRControlRegistry.INCREMENT, -0.72f, 0.66f, 0.16f);
        addControl(TRControlRegistry.ROTATE, -0.02f, 0.75f, -0.68f);
        addControl(TRControlRegistry.RANDOM, -0.26f, 0.66f, -0.74f);
        addControl(TRControlRegistry.DOOR_TOGGLE, 0.83f, 0.56f, -0.16f);
        addControl(TRControlRegistry.MONITOR, -0.6375f, 0.5625f, -0.3875f, 0.25f, 0.25f);
        addControl(TRControlRegistry.DIMENSION, 0.80f, 0.50f, -0.83f, 0.23f, 0.13f);
        addControl(TRControlRegistry.FAST_RETURN, 0.72f, 0.75f, -0.09f, 0.06f, 0.06f);
        addControl(TRControlRegistry.HANDBRAKE, 0.32f, 0.50f, 0.85f);
        addControl(TRControlRegistry.FUEL, -0.34f, 0.50f, 0.85f);
        addControl(TRControlRegistry.READOUT, -0.01f, 0.75f, 0.65f);
        addEmptyControl(-0.12f, 0.54f, 0.86f);
        addEmptyControl(-0.82f, 0.54f, 0.45f);
        addEmptyControl(-0.90f, 0.51f, 0.24f);
        addEmptyControl(-0.64f, 0.51f, -0.73f);
        addEmptyControl(-0.44f, 0.81f, -0.45f);
        addEmptyControl(-0.59f, 0.81f, -0.17f);
        addEmptyControl(-0.49f, 0.81f, -0.29f);
        addEmptyControl(0.24f, 0.69f, -0.78f);
        addEmptyControl(0.08f, 0.59f, -0.94f);
        addEmptyControl(-0.07f, 0.59f, -0.94f);
        addEmptyControl(0.50f, 0.59f, -0.62f);
        addEmptyControl(0.88f, 0.52f, 0.13f);
        addEmptyControl(0.58f, 0.52f, 0.70f);
        addEmptyControl(0.70f, 0.52f, 0.53f);
        addEmptyControl(0.55f, 0.75f, 0.30f);
        addEmptyControl(0.82f, 0.54f, 0.35f);
        addEmptyControl(0.65f, 0.62f, 0.36f);
    }
}
