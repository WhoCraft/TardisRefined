package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRControlRegistry;

public class NukaConsoleTheme extends ConsoleTheme {

    public NukaConsoleTheme(ResourceLocation translationKey) {
        super(translationKey);
    }

    @Override
    public void addControlSpecifications() {
        addControl(TRControlRegistry.THROTTLE, 0.22f, 0.56f, -1.01f, 0.13f, 0.13f);
        addControl(TRControlRegistry.X, -0.95f, 0.63f, -0.42f, 0.06f, 0.06f);
        addControl(TRControlRegistry.Y, -0.99f, 0.63f, -0.33f, 0.06f, 0.06f);
        addControl(TRControlRegistry.Z, -1.05f, 0.63f, -0.25f, 0.06f, 0.06f);
        addControl(TRControlRegistry.INCREMENT, -1.11f, 0.56f, 0.27f, 0.06f, 0.06f);
        addControl(TRControlRegistry.ROTATE, -0.96f, 0.54f, 0.48f, 0.06f, 0.06f);
        addControl(TRControlRegistry.RANDOM, -0.88f, 0.56f, 0.56f, 0.06f, 0.06f);
        addControl(TRControlRegistry.DOOR_TOGGLE, -0.24f, 0.62f, 0.99f, 0.13f, 0.13f);
        addControl(TRControlRegistry.MONITOR, 0.395f, 1.06f, 0.24f, 0.4f, 0.25f);
        addControl(TRControlRegistry.MONITOR, -0.375f, 1.06f, 0.265f, 0.4f, 0.25f);
        addControl(TRControlRegistry.MONITOR, -0.015f, 1.06f, -0.45f, 0.5f, 0.25f);
        addControl(TRControlRegistry.DIMENSION, 0.205f, 0.58f, 0.99f, 0.25f, 0.25f);
        addControl(TRControlRegistry.FAST_RETURN, 0.15f, 0.83f, 0.73f, 0.13f, 0.13f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.11f, 0.67f, 0.91f, 0.06f, 0.06f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.01f, 0.67f, 0.91f, 0.06f, 0.06f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.01f, 0.61f, 0.99f, 0.06f, 0.06f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.01f, 0.61f, 0.99f, 0.06f, 0.06f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.11f, 0.61f, 0.99f, 0.06f, 0.06f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.11f, 0.57f, 1.07f, 0.06f, 0.06f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.01f, 0.57f, 1.07f, 0.06f, 0.06f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.37f, 0.57f, 1.07f, 0.06f, 0.06f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.66f, 0.67f, 0.60f, 0.13f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.81f, 0.72f, 0.28f, 0.13f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.94f, 0.61f, 0.28f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.79f, 0.54f, 0.74f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.79f, 0.77f, -0.33f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.92f, 0.67f, -0.57f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.67f, 0.67f, -0.64f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.77f, 0.56f, -0.84f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.82f, 0.56f, -0.70f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.17f, 0.79f, -0.79f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, 0.06f, 0.79f, -0.79f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, 1.06f, 0.62f, -0.24f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, 1.00f, 0.59f, -0.44f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, 0.69f, 0.70f, -0.64f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, 0.69f, 0.70f, 0.61f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, 0.97f, 0.32f, 0.55f, 0.25f, 0.25f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, 0.04f, 0.56f, -1.04f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.21f, 0.59f, -0.96f, 0.12f, 0.12f);
        addControl(TRControlRegistry.GENERIC_NO_SHOW, -0.11f, 0.87f, 0.74f, 0.13f, 0.13f);
        addControl(TRControlRegistry.HANDBRAKE, 0.77f, 0.63f, 0.25f, 0.25f, 0.25f);
        addControl(TRControlRegistry.READOUT, 0.74f, 0.75f, -0.34f, 0.13f, 0.13f);
        addControl(TRControlRegistry.FUEL, -0.55f, 1.06f, -0.36f, 0.25f, 0.25f);
    }


}
