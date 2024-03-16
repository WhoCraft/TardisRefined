package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class NukaConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.221875f , 0.5625f, -1.0125f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.95f , 0.625f, -0.41875f), EntityDimensions.scalable(0.0625f , 0.0625f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.9937499999999999f , 0.6312499999999992f, -0.32812500000000033f), EntityDimensions.scalable(0.06250000000000089f , 0.06250000000000011f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-1.0500000000000014f , 0.6312499999999988f, -0.2500000000000003f), EntityDimensions.scalable(0.0625f , 0.06250000000000006f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-1.10625f , 0.5625f, 0.26875000000000043f), EntityDimensions.scalable(0.0625f , 0.06249999999999978f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.9562499999999996f , 0.5374999999999996f, 0.47500000000000003f), EntityDimensions.scalable(0.062499999999999556f , 0.0625f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.8843749999999999f , 0.5625f, 0.5562500000000001f), EntityDimensions.scalable(0.06250000000000022f , 0.0625f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.2437500000000002f , 0.6249999999999991f, 0.9875f), EntityDimensions.scalable(0.12500000000000022f , 0.125f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.425f , 1.0625f, 0.2375f), EntityDimensions.scalable(0.25f , 0.25f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.55f , 1.0625f, 0.2375f), EntityDimensions.scalable(0.25f , 0.25f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.08750000000000006f , 1.0625f, -0.6000000000000002f), EntityDimensions.scalable(0.24999999999999986f , 0.2500000000000001f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(0.125f , 0.5812499999999994f, 0.9375000000000003f), EntityDimensions.scalable(0.24999999999999983f , 0.24999999999999922f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.14999999999999997f , 0.8250000000000011f, 0.7250000000000003f), EntityDimensions.scalable(0.125f , 0.125f)),

                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.10625f , 0.6687499999999988f, 0.9125000000000005f), EntityDimensions.scalable(0.0625f , 0.06249999999999922f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.006249999999999936f , 0.6687499999999988f, 0.9125000000000005f), EntityDimensions.scalable(0.062499999999999965f , 0.06249999999999922f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.006249999999999936f , 0.6687499999999988f, 0.9125000000000005f), EntityDimensions.scalable(0.062499999999999965f , 0.06249999999999922f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.006249999999999936f , 0.612499999999998f, 0.9875000000000003f), EntityDimensions.scalable(0.062499999999999965f , 0.06249999999999922f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.006249999999999936f , 0.612499999999998f, 0.9875000000000003f), EntityDimensions.scalable(0.062499999999999965f , 0.06249999999999922f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.10624999999999997f , 0.612499999999998f, 0.9875000000000003f), EntityDimensions.scalable(0.0625f , 0.06249999999999922f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.10624999999999997f , 0.612499999999998f, 0.9875000000000003f), EntityDimensions.scalable(0.0625f , 0.06249999999999922f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.10624999999999997f , 0.5687499999999974f, 1.0687500000000003f), EntityDimensions.scalable(0.0625f , 0.06249999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.10624999999999997f , 0.5687499999999974f, 1.0687500000000003f), EntityDimensions.scalable(0.0625f , 0.06249999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.006249999999999908f , 0.5687499999999974f, 1.0687500000000003f), EntityDimensions.scalable(0.062499999999999965f , 0.06249999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.006249999999999908f , 0.5687499999999974f, 1.0687500000000003f), EntityDimensions.scalable(0.062499999999999965f , 0.06249999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.37499999999999994f , 0.5687499999999974f, 1.0687500000000003f), EntityDimensions.scalable(0.062499999999999944f , 0.06249999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.37499999999999994f , 0.5687499999999974f, 1.0687500000000003f), EntityDimensions.scalable(0.062499999999999944f , 0.06249999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.6562499999999992f , 0.6749999999999985f, 0.6000000000000005f), EntityDimensions.scalable(0.125f , 0.12499999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.6562499999999992f , 0.6749999999999985f, 0.6000000000000005f), EntityDimensions.scalable(0.125f , 0.12499999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.8062499999999987f , 0.7249999999999988f, 0.2812500000000004f), EntityDimensions.scalable(0.125f , 0.12499999999999964f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.8062499999999987f , 0.7249999999999988f, 0.2812500000000004f), EntityDimensions.scalable(0.125f , 0.12499999999999964f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.9437499999999985f , 0.6124999999999972f, 0.2812500000000004f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999964f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.9437499999999985f , 0.6124999999999972f, 0.2812500000000004f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999964f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7874999999999991f , 0.5374999999999961f, 0.7375000000000004f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7874999999999991f , 0.5374999999999961f, 0.7375000000000004f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7874999999999991f , 0.7749999999999972f, -0.33124999999999954f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7874999999999991f , 0.7749999999999972f, -0.33124999999999954f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.9187499999999984f , 0.6687499999999964f, -0.5749999999999997f), EntityDimensions.scalable(0.12499999999999856f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.9187499999999984f , 0.6687499999999964f, -0.5749999999999997f), EntityDimensions.scalable(0.12499999999999856f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.6687499999999988f , 0.6687499999999964f, -0.6437499999999995f), EntityDimensions.scalable(0.12499999999999867f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.6687499999999988f , 0.6687499999999964f, -0.6437499999999995f), EntityDimensions.scalable(0.12499999999999867f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7749999999999985f , 0.5624999999999951f, -0.8374999999999989f), EntityDimensions.scalable(0.12499999999999867f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7749999999999985f , 0.5624999999999951f, -0.8374999999999989f), EntityDimensions.scalable(0.12499999999999867f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.8249999999999983f , 0.5624999999999951f, -0.6999999999999988f), EntityDimensions.scalable(0.12499999999999867f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.8249999999999983f , 0.5624999999999951f, -0.6999999999999988f), EntityDimensions.scalable(0.12499999999999867f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.16874999999999823f , 0.787499999999997f, -0.7937499999999993f), EntityDimensions.scalable(0.12499999999999872f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.16874999999999823f , 0.787499999999997f, -0.7937499999999993f), EntityDimensions.scalable(0.12499999999999872f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.06250000000000182f , 0.787499999999997f, -0.7937499999999993f), EntityDimensions.scalable(0.12499999999999872f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.06250000000000182f , 0.787499999999997f, -0.7937499999999993f), EntityDimensions.scalable(0.12499999999999872f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.056250000000002f , 0.624999999999996f, -0.23749999999999966f), EntityDimensions.scalable(0.12499999999999933f , 0.12499999999999989f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.7687500000000018f , 0.8124999999999962f, -0.3499999999999995f), EntityDimensions.scalable(0.12499999999999933f , 0.12499999999999967f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.0000000000000018f , 0.5874999999999932f, -0.4374999999999995f), EntityDimensions.scalable(0.12499999999999967f , 0.12499999999999967f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.687500000000002f , 0.6999999999999944f, -0.6374999999999997f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.687500000000002f , 0.6999999999999944f, 0.6062500000000001f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.9687500000000029f , 0.32499999999999485f, 0.5500000000000003f), EntityDimensions.scalable(0.24999999999999867f , 0.25f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.743750000000003f , 0.6249999999999938f, 0.2562500000000003f), EntityDimensions.scalable(0.2499999999999989f , 0.24999999999999975f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.037500000000001435f , 0.5624999999999951f, -1.0374999999999985f), EntityDimensions.scalable(0.12499999999999861f , 0.124999999999999f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.2124999999999987f , 0.5937499999999956f, -0.9624999999999981f), EntityDimensions.scalable(0.12499999999999872f , 0.12499999999999833f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.11250000000000011f , 0.8749999999999991f, 0.7437499999999999f), EntityDimensions.scalable(0.12500000000000003f , 0.125f))

        };
    }
}
