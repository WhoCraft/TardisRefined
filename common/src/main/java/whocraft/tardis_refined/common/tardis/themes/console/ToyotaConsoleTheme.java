package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class ToyotaConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.26875000000000004f , 0.5f, 0.9437500000000003f), EntityDimensions.scalable(0.18750000000000008f , 0.1874999999999991f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.075f , 0.625f, 0.8f), EntityDimensions.scalable(0.0625f , 0.0625f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(0.01875000000000005f , 0.625f, 0.8f), EntityDimensions.scalable(0.06249999999999995f , 0.0625f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(0.1125f , 0.625f, 0.8f), EntityDimensions.scalable(0.0625f , 0.0625f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.725f , 0.7062499999999994f, 0.14375000000000016f), EntityDimensions.scalable(0.125f , 0.12499999999999994f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-1.0624999999999991f , 0.5312499999999987f, -0.18750000000000006f), EntityDimensions.scalable(0.125f , 0.1250000000000001f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.5812500000000003f , 0.5749999999999977f, -0.7437500000000006f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.012499999999999997f , 0.6874999999999991f, -0.6875000000000003f), EntityDimensions.scalable(0.12499999999999994f , 0.125f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.4312500000000001f , 1.0749999999999986f, -0.7437499999999995f), EntityDimensions.scalable(0.1874999999999999f , 0.1875f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.34374999999999983f , 1.0749999999999986f, 0.6250000000000008f), EntityDimensions.scalable(0.1874999999999999f , 0.1875f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(0.6437500000000007f , 0.6187499999999984f, -0.48125000000000057f), EntityDimensions.scalable(0.2499999999999999f , 0.25f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-0.653125f , 0.53125f, -0.85625f), EntityDimensions.scalable(0.125f , 0.125f)),

                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.11875000000000001f , 0.75f, 0.6875f), EntityDimensions.scalable(0.0625f , 0.06250000000000022f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.012499999999999997f , 0.75f, 0.6875f), EntityDimensions.scalable(0.06250000000000001f , 0.06250000000000022f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.13125f , 0.75f, 0.6875f), EntityDimensions.scalable(0.06250000000000001f , 0.06250000000000022f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.02500000000000001f , 0.5124999999999984f, 0.9750000000000001f), EntityDimensions.scalable(0.125f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.9437499999999995f , 0.6062499999999984f, 0.19374999999999987f), EntityDimensions.scalable(0.06249999999999978f , 0.06250000000000033f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.0187499999999996f , 0.5374999999999983f, 0.2437499999999999f), EntityDimensions.scalable(0.0625f , 0.06250000000000022f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.8562500000000004f , 0.5499999999999972f, -0.15000000000000013f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999958f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.0437500000000008f , 0.48749999999999716f, -0.19375000000000014f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999958f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.7312500000000008f , 0.48749999999999716f, -0.8187500000000001f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.1781250000000008f , 0.5374999999999983f, -0.9750000000000001f), EntityDimensions.scalable(0.06249999999999961f , 0.06250000000000022f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.08437500000000082f , 0.5374999999999983f, -0.9750000000000001f), EntityDimensions.scalable(0.062499999999999584f , 0.06250000000000022f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.04687499999999918f , 0.5374999999999983f, -0.9750000000000001f), EntityDimensions.scalable(0.06249999999999958f , 0.06250000000000022f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.14062499999999928f , 0.5374999999999983f, -0.9750000000000001f), EntityDimensions.scalable(0.06249999999999964f , 0.06250000000000022f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.02187500000000077f , 0.6437499999999987f, -0.7937500000000002f), EntityDimensions.scalable(0.06249999999999963f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.12812500000000077f , 0.6437499999999987f, -0.7937500000000002f), EntityDimensions.scalable(0.06249999999999967f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.07812499999999926f , 0.6437499999999987f, -0.7937500000000002f), EntityDimensions.scalable(0.06249999999999967f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.2843749999999991f , 0.49374999999999836f, -0.9999999999999989f), EntityDimensions.scalable(0.1249999999999997f , 0.12499999999999878f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.2656250000000009f , 0.49374999999999836f, -0.9999999999999989f), EntityDimensions.scalable(0.12499999999999978f , 0.12499999999999878f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.8812499999999995f , 0.5437499999999984f, 0.3749999999999996f), EntityDimensions.scalable(0.125f , 0.12499999999999989f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.6062500000000001f , 0.6937499999999996f, 0.31249999999999983f), EntityDimensions.scalable(0.125f , 0.12499999999999989f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.6062500000000001f , 0.6937499999999996f, 0.5749999999999996f), EntityDimensions.scalable(0.125f , 0.12499999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7687500000000005f , 0.531249999999998f, 0.5999999999999995f), EntityDimensions.scalable(0.125f , 0.12499999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7062500000000007f , 0.531249999999998f, 0.781249999999999f), EntityDimensions.scalable(0.125f , 0.12499999999999978f))
        };
    }
}
