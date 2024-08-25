package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.TRControlRegistry;

public class InitiativeConsoleTheme extends ConsoleTheme {

    public InitiativeConsoleTheme(ResourceLocation translationKey) {
        super(translationKey);
    }

    @Override
    public void addControlSpecifications() {
        addControl(TRControlRegistry.THROTTLE, -0.01f, 0.75f, 1.05f);
        addControl(TRControlRegistry.X, -0.67f, 0.72f, -0.62f);
        addControl(TRControlRegistry.Y, -0.75f, 0.72f, -0.45f, 0.12f, 0.13f);
        addControl(TRControlRegistry.Z, -0.84f, 0.72f, -0.29f, 0.12f, 0.13f);
        addControl(TRControlRegistry.INCREMENT, -0.32f, 0.56f, -1.04f, 0.13f, 0.12f);
        addControl(TRControlRegistry.ROTATE, -1.00f, 0.63f, 0.49f, 0.12f, 0.13f);
        addControl(TRControlRegistry.RANDOM, 0.30f, 0.56f, -1.06f, 0.12f, 0.12f);
        addControl(TRControlRegistry.DOOR_TOGGLE, 0.04f, 0.88f, -0.64f, 0.12f, 0.13f);
        addControl(TRControlRegistry.MONITOR, -0.515f, 1.379f, 0.289f, 0.35f, 0.3f);
        addControl(TRControlRegistry.DIMENSION, -0.54f, 0.81f, -0.37f, 0.25f, 0.25f);
        addControl(TRControlRegistry.FAST_RETURN, -0.87f, 0.75f, 0.44f);
        addEmptyControl(-0.04f, 0.92f, -0.67f, 0.06f, 0.06f);
        addEmptyControl(-0.01f, 0.62f, -0.99f, 0.12f, 0.13f);
        addEmptyControl(0.99f, 0.62f, 0.34f, 0.13f, 0.12f);
        addEmptyControl(0.82f, 0.62f, 0.68f, 0.12f, 0.12f);
        addControl(TRControlRegistry.HANDBRAKE, 0.79f, 0.75f, 0.44f, 0.12f, 0.12f);
        addEmptyControl(0.24f, 0.62f, 1.06f, 0.12f, 0.12f);
        addEmptyControl(-0.26f, 0.62f, 1.06f, 0.12f, 0.12f);
        addEmptyControl(-0.85f, 0.58f, 0.69f, 0.12f, 0.12f);
        addEmptyControl(1.05f, 0.61f, -0.27f, 0.06f, 0.06f);
        addEmptyControl(1.01f, 0.61f, -0.35f, 0.06f, 0.06f);
        addEmptyControl(0.96f, 0.61f, -0.43f, 0.06f, 0.06f);
        addEmptyControl(0.91f, 0.61f, -0.51f, 0.06f, 0.06f);
        addEmptyControl(0.87f, 0.61f, -0.58f, 0.06f, 0.06f);
        addEmptyControl(0.82f, 0.61f, -0.68f, 0.06f, 0.06f);
        addEmptyControl(0.76f, 0.61f, -0.76f, 0.06f, 0.06f);
        addEmptyControl(-1.06f, 0.72f, 0.33f);
        addControl(TRControlRegistry.READOUT, -0.87f, 0.50f, -0.5f, 0.28F, 0.13F);
        addControl(TRControlRegistry.FUEL, -0.76f, 0.73f, 0.61f);
    }


}
