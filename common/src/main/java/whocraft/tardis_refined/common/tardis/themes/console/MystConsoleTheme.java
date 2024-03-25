package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class MystConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.39375000000000016f , 0.5937499999999991f, 1.0437499999999995f), EntityDimensions.scalable(0.2500000000000001f , 0.24999999999999933f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.8562500000000002f , 0.625f, -0.5937500000000001f), EntityDimensions.scalable(0.0625f , 0.0625f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.9687499999999998f , 0.625f, -0.5562500000000001f), EntityDimensions.scalable(0.06250000000000022f , 0.0625f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.9562499999999996f , 0.625f, -0.4375000000000006f), EntityDimensions.scalable(0.0625f , 0.0625f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.3875f , 0.5f, -1.075f), EntityDimensions.scalable(0.25f , 0.25f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.018750000000000003f , 0.6937499999999992f, -1.1000000000000008f), EntityDimensions.scalable(0.12499999999999994f , 0.12500000000000044f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(1.1437499999999998f , 0.5625f, 0.16875f), EntityDimensions.scalable(0.125f , 0.1250000000000001f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(1.0875000000000006f , 0.5f, 0.6687500000000002f), EntityDimensions.scalable(0.06250000000000044f , 0.06249999999999989f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.70625f , 0.5625f, 0.378125f), EntityDimensions.scalable(0.25f , 0.25f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(0.46875000000000006f , 0.5750000000000002f, 1.0750000000000004f), EntityDimensions.scalable(0.0625f , 0.0625f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.3312500000000001f , 0.6312499999999976f, 0.9499999999999974f), EntityDimensions.scalable(0.0625f , 0.06250000000000022f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.0125f , 0.6875f, 0.175f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.6250000000000001f , 0.7875000000000014f, 0.3499999999999999f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.8750000000000001f , 0.6875000000000009f, 0.8125f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.6875000000000001f , 0.8750000000000009f, -0.4437499999999999f), EntityDimensions.scalable(0.25f , 0.24999999999999994f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.9374999999999999f , 0.6875000000000009f, -0.2874999999999998f), EntityDimensions.scalable(0.125f , 0.1249999999999998f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.1437499999999987f , 0.5874999999999999f, -0.36874999999999963f), EntityDimensions.scalable(0.125f , 0.12499999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7062499999999987f , 0.662500000000001f, -0.8062499999999994f), EntityDimensions.scalable(0.125f , 0.12499999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.9250000000000014f , 0.600000000000001f, -0.5562499999999994f), EntityDimensions.scalable(0.125f , 0.12499999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.7625000000000016f , 0.5125000000000008f, 0.9000000000000006f), EntityDimensions.scalable(0.125f , 0.12499999999999989f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.012499999999998554f , 0.5750000000000008f, 1.0625000000000004f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.9375000000000001f , 0.7375000000000016f, 0.5250000000000001f), EntityDimensions.scalable(0.125f , 0.1250000000000001f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.0125000000000004f , 0.5312500000000004f, 0.5687500000000002f), EntityDimensions.scalable(0.12500000000000078f , 0.125f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.07500000000000008f , 0.8499999999999996f, -0.7656249999999996f), EntityDimensions.scalable(0.25f , 0.24999999999999978f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.7812499999999999f , 0.6312499999999992f, -0.8687500000000001f), EntityDimensions.scalable(0.12499999999999989f , 0.12499999999999956f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.5874999999999997f , 0.7999999999999992f, -0.3750000000000002f), EntityDimensions.scalable(0.12499999999999989f , 0.12499999999999956f))


        };
    }
}
