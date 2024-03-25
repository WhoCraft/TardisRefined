package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class InitiativeConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.012499999999999997f , 0.75f, 1.05f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.66875f , 0.7187499999999998f, -0.621875f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.7500000000000003f , 0.7187499999999998f, -0.45f), EntityDimensions.scalable(0.12499999999999956f , 0.125f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.8375000000000004f , 0.71875f, -0.29375f), EntityDimensions.scalable(0.12499999999999989f , 0.125f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.3249999999999997f , 0.5624999999999987f, -1.043749999999999f), EntityDimensions.scalable(0.12500000000000003f , 0.12499999999999933f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.9999999999999993f , 0.625f, 0.49374999999999997f), EntityDimensions.scalable(0.12499999999999911f , 0.125f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.3f , 0.5625f, -1.0562500000000001f), EntityDimensions.scalable(0.1249999999999997f , 0.12499999999999989f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0.037500000000000006f , 0.875f, -0.6375f), EntityDimensions.scalable(0.12499999999999999f , 0.125f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.6375f , 1.375f, 0.2375f), EntityDimensions.scalable(0.25f , 0.25f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(-0.54375f , 0.8125f, -0.371875f), EntityDimensions.scalable(0.25f , 0.25f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-0.871875f , 0.75f, 0.440625f), EntityDimensions.scalable(0.125f , 0.125f)),

                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.04374999999999997f , 0.9187500000000011f, -0.6749999999999998f), EntityDimensions.scalable(0.06249999999999997f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.012499999999999949f , 0.6249999999999982f, -0.993750000000001f), EntityDimensions.scalable(0.12499999999999997f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.9875f , 0.6249999999999982f, 0.3437499999999994f), EntityDimensions.scalable(0.125f , 0.12499999999999933f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.8187500000000005f , 0.6249999999999982f, 0.6812499999999995f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.7875000000000006f , 0.7499999999999982f, 0.4374999999999994f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999967f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.2375000000000006f , 0.6249999999999982f, 1.0562499999999988f), EntityDimensions.scalable(0.1249999999999995f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.2562499999999994f , 0.6249999999999982f, 1.0562499999999988f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.8499999999999995f , 0.5812499999999978f, 0.693749999999999f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999911f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7624999999999998f , 0.7437499999999995f, 0.6124999999999989f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999911f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.0999999999999999f , 0.7187499999999991f, 0.3499999999999988f), EntityDimensions.scalable(0.12499999999999956f , 0.12499999999999878f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.0499999999999996f , 0.6062500000000011f, -0.26875f), EntityDimensions.scalable(0.06249999999999967f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.0062499999999999f , 0.6062500000000011f, -0.3499999999999997f), EntityDimensions.scalable(0.06249999999999889f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.9593749999999999f , 0.6062500000000011f, -0.4281249999999997f), EntityDimensions.scalable(0.06249999999999889f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.9124999999999999f , 0.6062500000000011f, -0.5062499999999996f), EntityDimensions.scalable(0.06249999999999889f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.8656249999999999f , 0.6062500000000011f, -0.5843749999999996f), EntityDimensions.scalable(0.06249999999999889f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.8187499999999999f , 0.6062500000000011f, -0.6781249999999996f), EntityDimensions.scalable(0.06249999999999889f , 0.0625f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.7562499999999999f , 0.6062500000000011f, -0.7562499999999996f), EntityDimensions.scalable(0.06249999999999889f , 0.0625f))

        };
    }
}
