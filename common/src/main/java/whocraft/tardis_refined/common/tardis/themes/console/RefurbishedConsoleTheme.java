package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRControlRegistry;

public class RefurbishedConsoleTheme extends ConsoleTheme {

    public RefurbishedConsoleTheme(ResourceLocation translationKey) {
        super(translationKey);
    }

    @Override
    public void addControlSpecifications() {
        addControl(TRControlRegistry.THROTTLE, -1.22f, 0.63f, 0.71f, 0.25f, 0.25f);
        addControl(TRControlRegistry.X, 1.43f, 0.66f, -0.29f);
        addControl(TRControlRegistry.Y, 1.18f, 0.69f, -0.51f);
        addControl(TRControlRegistry.Z, 0.96f, 0.75f, -0.75f, 0.12f, 0.13f);
        addControl(TRControlRegistry.INCREMENT, 0.96f, 0.64f, -1.05f, 0.12f, 0.12f);
        addControl(TRControlRegistry.ROTATE, 1.68f, 0.50f, 0.11f);
        addControl(TRControlRegistry.RANDOM, -1.07f, 0.56f, -0.65f, 0.25f, 0.25f);
        addControl(TRControlRegistry.DOOR_TOGGLE, -0.33f, 0.50f, -1.51f);
        addControl(TRControlRegistry.MONITOR, -0.815f, 0.915f, -0.005f, 0.35f, 0.25f);
        addControl(TRControlRegistry.DIMENSION, 1.30f, 0.50f, -0.78f);
        addControl(TRControlRegistry.FAST_RETURN, -0.48f, 0.50f, -1.51f);
        addControl(TRControlRegistry.READOUT, 0.80f, 0.71f, 0.93f, 0.13f, 0.12f);
        addControl(TRControlRegistry.HANDBRAKE, 0.24f, 0.69f, -1.20f, 0.25f, 0.25f);
        addEmptyControl(-0.01f, 0.66f, -1.34f, 0.12f, 0.13f);
        addEmptyControl(-0.47f, 0.56f, -1.29f, 0.12f, 0.13f);
        addEmptyControl(0.47f, 0.50f, -1.46f, 0.12f, 0.13f);
        addEmptyControl(0.36f, 0.50f, -1.46f);
        addEmptyControl(0.26f, 0.50f, -1.46f);
        addEmptyControl(1.11f, 0.51f, -1.05f, 0.06f, 0.06f);
        addEmptyControl(1.16f, 0.51f, -0.95f, 0.06f, 0.06f);
        addEmptyControl(1.22f, 0.51f, -0.86f, 0.06f, 0.06f);
        addEmptyControl(1.43f, 0.51f, -0.49f, 0.06f, 0.06f);
        addEmptyControl(1.38f, 0.51f, -0.59f, 0.06f, 0.06f);
        addEmptyControl(1.49f, 0.51f, -0.40f, 0.06f, 0.06f);
        addEmptyControl(1.24f, 0.59f, 0.46f, 0.13f, 0.12f);
        addEmptyControl(1.32f, 0.59f, 0.32f);
        addEmptyControl(1.21f, 0.70f, 0.21f);
        addEmptyControl(0.96f, 0.70f, 0.66f, 0.12f, 0.13f);
        addEmptyControl(1.05f, 0.51f, 1.21f, 0.12f, 0.13f);
        addEmptyControl(0.94f, 0.51f, 1.37f, 0.12f, 0.13f);
        addEmptyControl(0.85f, 0.55f, 1.24f, 0.12f, 0.13f);
        addEmptyControl(1.49f, 0.51f, 0.33f, 0.06f, 0.06f);
        addEmptyControl(1.61f, 0.49f, 0.33f, 0.06f, 0.06f);
        addEmptyControl(1.57f, 0.49f, 0.40f, 0.06f, 0.06f);
        addEmptyControl(1.56f, 0.53f, 0.26f, 0.06f, 0.06f);
        addEmptyControl(0.35f, 0.51f, 1.41f, 0.12f, 0.13f);
        addEmptyControl(-0.49f, 0.51f, 1.41f, 0.12f, 0.13f);
        addEmptyControl(0.21f, 0.49f, 1.50f, 0.06f, 0.06f);
        addEmptyControl(-0.29f, 0.49f, 1.50f, 0.06f, 0.06f);
        addEmptyControl(-1.35f, 0.49f, 0.66f, 0.06f, 0.06f);
        addEmptyControl(-1.27f, 0.49f, 0.78f, 0.06f, 0.06f);
        addEmptyControl(-1.22f, 0.49f, 0.88f, 0.06f, 0.06f);
        addEmptyControl(-1.20f, 0.62f, -0.35f, 0.13f, 0.12f);
        addEmptyControl(-0.90f, 0.62f, -0.89f, 0.12f, 0.12f);
        addEmptyControl(-1.64f, 0.46f, -0.27f, 0.12f, 0.12f);
        addEmptyControl(-1.02f, 0.46f, -1.29f, 0.12f, 0.12f);
        addControl(TRControlRegistry.FUEL, -0.04f, 0.50f, -1.48f);
    }


}
