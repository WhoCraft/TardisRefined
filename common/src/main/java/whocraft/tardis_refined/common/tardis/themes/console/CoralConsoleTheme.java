package whocraft.tardis_refined.common.tardis.themes.console;


import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class CoralConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.5999999999999998f , 0.6718750000000007f, -1.0999999999999999f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.2625f , 0.4843749999999978f, 1.1031250000000001f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.012500000000000053f , 0.484375f, 1.0250000000000001f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(0.2375f , 0.484375f, 1.0875000000000001f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.090625f , 0.625f, 0.76875f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.46875000000000017f , 0.625f, 0.9062500000000001f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.371875f , 0.546875f, -1.278125f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0.253125f , 0.5625f, -1.04375f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.7187500000000004f , 0.9375f, -0.6124999999999998f), EntityDimensions.scalable(0.374999999999998f , 0.375f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(1.190625f , 0.59375f, -0.325f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-1.1375f , 0.4375f, 0.440625f), EntityDimensions.scalable(0.125f , 0.125f)),

                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.0374999999999994f , 0.4874999999999998f, 0.7f), EntityDimensions.scalable(0.0625f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.9499999999999992f , 0.4874999999999998f, 0.8f), EntityDimensions.scalable(0.062499999999999556f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.8812499999999996f , 0.4874999999999998f, 0.88125f), EntityDimensions.scalable(0.0625f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.8124999999999999f , 0.4874999999999998f, 0.9562499999999997f), EntityDimensions.scalable(0.0625f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.37500000000000017f , 0.3624999999999998f, 1.2624999999999995f), EntityDimensions.scalable(0.12500000000000006f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.0624999999999998f , 0.3624999999999998f, 0.6999999999999995f), EntityDimensions.scalable(0.12500000000000022f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.6874999999999998f , 0.4874999999999998f, 1.1999999999999995f), EntityDimensions.scalable(0.12500000000000022f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.6249999999999998f , 0.6124999999999998f, 0.9499999999999995f), EntityDimensions.scalable(0.12500000000000022f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.8749999999999998f , 0.4874999999999998f, 0.6999999999999995f), EntityDimensions.scalable(0.12500000000000022f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.0437499999999995f , 0.4874999999999998f, 0.41874999999999934f), EntityDimensions.scalable(0.125000000000001f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.9624999999999999f , 0.4874999999999998f, 0.5499999999999993f), EntityDimensions.scalable(0.12500000000000067f , 0.12499999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.6750000000000005f , 0.6624999999999994f, 0.612499999999999f), EntityDimensions.scalable(0.12500000000000022f , 0.12499999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.5749999999999997f , 0.2874999999999994f, -0.012500000000000996f), EntityDimensions.scalable(0.125f , 0.1249999999999997f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.8624999999999997f , 0.4124999999999994f, -1.000000000000001f), EntityDimensions.scalable(0.12499999999999967f , 0.12500000000000022f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.11562499999999966f , 0.5843749999999994f, -1.000000000000001f), EntityDimensions.scalable(0.0625f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.02187499999999966f , 0.5843749999999994f, -1.000000000000001f), EntityDimensions.scalable(0.062499999999999944f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.07187500000000037f , 0.5843749999999994f, -1.000000000000001f), EntityDimensions.scalable(0.06249999999999997f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.0281249999999997f , 0.44062499999999905f, -1.3249999999999986f), EntityDimensions.scalable(0.06249999999999991f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.1249999999999997f , 0.44062499999999905f, -1.2937499999999982f), EntityDimensions.scalable(0.06249999999999996f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.22187499999999977f , 0.44062499999999905f, -1.274999999999998f), EntityDimensions.scalable(0.062499999999999944f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.3093749999999999f , 0.44062499999999905f, -1.2499999999999976f), EntityDimensions.scalable(0.06249999999999978f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.3906249999999996f , 0.44062499999999905f, -1.2499999999999976f), EntityDimensions.scalable(0.06249999999999978f , 0.0625f))


        };
    }
}
