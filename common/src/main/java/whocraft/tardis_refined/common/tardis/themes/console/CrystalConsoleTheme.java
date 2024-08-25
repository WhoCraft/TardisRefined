package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRControlRegistry;

public class CrystalConsoleTheme extends ConsoleTheme {

    public CrystalConsoleTheme(ResourceLocation translationKey) {
        super(translationKey);
    }

    @Override
    public void addControlSpecifications() {
        addControl(TRControlRegistry.THROTTLE, -0.735f, 1.13f, -0.45f, 0.25f, 0.25f);
        addControl(TRControlRegistry.X, -0.70f, 0.50f, 0.93f);
        addControl(TRControlRegistry.Y, -0.52f, 0.50f, 0.89f, 0.13f, 0.12f);
        addControl(TRControlRegistry.Z, -0.45f, 0.50f, 1.05f);
        addControl(TRControlRegistry.INCREMENT, -0.92f, 0.46f, 0.87f, 0.12f, 0.13f);
        addControl(TRControlRegistry.ROTATE, -0.62f, 0.57f, -1.07f);
        addControl(TRControlRegistry.RANDOM, -0.595f, 0.55f, 1.04f, 0.08f, 0.08f);
        addControl(TRControlRegistry.DOOR_TOGGLE, 1.18f, 0.52f, -0.35f);
        addControl(TRControlRegistry.MONITOR, -0.0149f, 0.94f, 1.38f, 0.43f, 0.43f);
        addControl(TRControlRegistry.DIMENSION, 0.65f, 0.5f, -0.42f, 0.33f, 0.3f);
        addControl(TRControlRegistry.FAST_RETURN, -0.29f, 0.39f, -1.21f, 0.25f, 0.25f);
        addEmptyControl(1.11f, 0.56f, 0.11f);
        addEmptyControl(1.11f, 0.56f, -0.16f, 0.13f, 0.12f);
        addEmptyControl(0.61f, 0.56f, -0.96f);
        addEmptyControl(0.68f, 0.56f, -1.08f);
        addEmptyControl(0.29f, 0.56f, -1.28f);
        addEmptyControl(-0.52f, 0.61f, -0.74f);
        addEmptyControl(-1.03f, 0.64f, -0.52f);
        addEmptyControl(-0.81f, 0.64f, -0.01f);
        addEmptyControl(-1.14f, 0.51f, -0.38f);
        addEmptyControl(-0.82f, 1.07f, 0.16f);
        addControl(TRControlRegistry.FUEL, 0.29f, 0.52f, 1.23f);
        addEmptyControl(0.58f, 0.52f, 1.29f);
        addEmptyControl(0.69f, 0.52f, 1.22f);
        addEmptyControl(0.83f, 0.52f, 1.14f);
        addEmptyControl(1.12f, 0.40f, -0.99f);
        addEmptyControl(1.12f, 0.40f, -0.99f);
        addControl(TRControlRegistry.HANDBRAKE, 0.685f, 1.13f, 0.37f, 0.25f, 0.25f);
        addControl(TRControlRegistry.READOUT, 0.83f, 1.06f, -0.56f);

    }
}
