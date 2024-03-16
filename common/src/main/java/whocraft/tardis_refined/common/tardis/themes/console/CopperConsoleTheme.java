package whocraft.tardis_refined.common.tardis.themes.console;


import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class CopperConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.1125f , 0.6875f, -1.2625f), EntityDimensions.scalable(0.1875f , 0.1875f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(0.675f , 0.96875f, 0.14375f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(0.675f , 0.96875f, -0.006250000000000026f), EntityDimensions.scalable(0.125f , 0.12499999999999997f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(0.675f , 0.96875f, -0.16875f), EntityDimensions.scalable(0.125f , 0.12500000000000003f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(1.1937499999999983f , 0.5124999999999973f, -0.2562499999999999f), EntityDimensions.scalable(0.125f , 0.12499999999999989f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(0.7375f , 0.5f, 0.925f), EntityDimensions.scalable(0.1875f , 0.1875f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.675f , 0.25f, -1.2625f), EntityDimensions.scalable(0.1875f , 0.1875f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.2f , 0.625f, -1.2625f), EntityDimensions.scalable(0.1875f , 0.1875f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.8625f , 1.4375f, -0.6375f), EntityDimensions.scalable(0.3125f , 0.3125f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(0.346875f , 0.625f, 1.1125f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-0.79375f , 0.53125f, -0.95f), EntityDimensions.scalable(0.125f , 0.125f)),

                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.70625f , 0.47499999999999953f, -0.9062500000000002f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.26875f , 0.9749999999999996f, -0.7187500000000002f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.0187499999999998f , 0.6312499999999979f, -0.2500000000000003f), EntityDimensions.scalable(0.12500000000000044f , 0.12500000000000003f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.2250000000000012f , 0.5687499999999972f, -3.191891195797325e-16f), EntityDimensions.scalable(0.1250000000000009f , 0.12500000000000006f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.2250000000000012f , 0.5687499999999972f, 0.26874999999999966f), EntityDimensions.scalable(0.1250000000000009f , 0.12499999999999994f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.2250000000000012f , 0.5687499999999972f, 0.5374999999999995f), EntityDimensions.scalable(0.1250000000000009f , 0.12499999999999983f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.1000000000000012f , 0.6937499999999972f, 0.47499999999999953f), EntityDimensions.scalable(0.1250000000000009f , 0.12499999999999983f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.0375000000000012f , 0.6937499999999972f, 0.7874999999999996f), EntityDimensions.scalable(0.1250000000000009f , 0.12499999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.13125000000000114f , 0.8187499999999972f, 0.9749999999999996f), EntityDimensions.scalable(0.1250000000000009f , 0.12499999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.08750000000000109f , 0.5999999999999961f, 1.1874999999999993f), EntityDimensions.scalable(0.18750000000000033f , 0.1875f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.1874999999999989f , 0.7562499999999972f, 1.0312499999999993f), EntityDimensions.scalable(0.12500000000000094f , 0.12500000000000078f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.062499999999999f , 0.5687499999999972f, 0.7499999999999994f), EntityDimensions.scalable(0.1250000000000009f , 0.12500000000000078f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.9624999999999988f , 0.6937499999999972f, 0.6937499999999994f), EntityDimensions.scalable(0.12500000000000067f , 0.1250000000000009f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7374999999999988f , 0.7624999999999982f, 0.5562499999999999f), EntityDimensions.scalable(0.12500000000000044f , 0.1250000000000009f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.3624999999999989f , 1.0562500000000008f, 0.6499999999999996f), EntityDimensions.scalable(0.12500000000000033f , 0.1250000000000009f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.249999999999999f , 0.5687499999999972f, 0.5124999999999996f), EntityDimensions.scalable(0.1250000000000009f , 0.12500000000000078f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.9999999999999989f , 0.7562499999999972f, 0.38749999999999957f), EntityDimensions.scalable(0.1250000000000009f , 0.12500000000000078f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.2062499999999987f , 0.5749999999999957f, 0.04999999999999964f), EntityDimensions.scalable(0.12500000000000044f , 0.12500000000000078f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.2062499999999987f , 0.6374999999999957f, -0.2812500000000004f), EntityDimensions.scalable(0.12500000000000044f , 0.12500000000000078f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.2062499999999987f , 0.6374999999999957f, -0.5187500000000002f), EntityDimensions.scalable(0.1875f , 0.1875f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.4875000000000002f , 0.5374999999999996f, -1.0937500000000002f), EntityDimensions.scalable(0.12499999999999994f , 0.125f))

        };
    }
}
