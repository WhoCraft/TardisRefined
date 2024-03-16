package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class CrystalConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.7625000000000002f , 1.1312500000000014f, -0.5249999999999999f), EntityDimensions.scalable(0.25f , 0.25f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.7f , 0.5f, 0.925f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.5249999999999999f , 0.5f, 0.8875000000000004f), EntityDimensions.scalable(0.125f , 0.12499999999999911f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.45f , 0.5f, 1.05f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.9249999999999995f , 0.45625000000000016f, 0.8749999999999999f), EntityDimensions.scalable(0.124999999999999f , 0.125f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.6187499999999998f , 0.5687500000000001f, -1.068750000000002f), EntityDimensions.scalable(0.125f , 0.12500000000000255f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.6187499999999999f , 0.5499999999999994f, 1.0437499999999997f), EntityDimensions.scalable(0.1250000000000001f , 0.1250000000000011f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(1.175f , 0.5187499999999994f, -0.35000000000000014f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.1375f , 0.9374999999999973f, 1.2937499999999986f), EntityDimensions.scalable(0.37500000000000033f , 0.375f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(1.299999999999999f , 0.3250000000000004f, -0.075f), EntityDimensions.scalable(0.25f , 0.24999999999999994f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-0.38750000000000007f , 0.39374999999999916f, -1.2625f), EntityDimensions.scalable(0.25000000000000006f , 0.25f)),

                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.1125f , 0.5625f, 0.1125f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.1125f , 0.5625f, -0.16249999999999998f), EntityDimensions.scalable(0.125f , 0.12499999999999999f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.0562500000000017f , 0.4375f, -0.9000000000000015f), EntityDimensions.scalable(0.125f , 0.12500000000000133f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.6062500000000017f , 0.5625f, -0.9625000000000015f), EntityDimensions.scalable(0.12500000000000022f , 0.12500000000000133f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.6812500000000015f , 0.5625f, -1.075000000000002f), EntityDimensions.scalable(0.12500000000000022f , 0.12500000000000222f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.28750000000000187f , 0.5625f, -1.2750000000000028f), EntityDimensions.scalable(0.12500000000000022f , 0.12500000000000178f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.5249999999999979f , 0.6062500000000006f, -0.7375000000000029f), EntityDimensions.scalable(0.12500000000000022f , 0.12500000000000178f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.0312499999999996f , 0.6375000000000011f, -0.5187500000000017f), EntityDimensions.scalable(0.12500000000000167f , 0.12500000000000222f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.8062500000000002f , 0.6375000000000011f, -0.012500000000001801f), EntityDimensions.scalable(0.12500000000000178f , 0.12500000000000222f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-1.1437499999999992f , 0.5125000000000011f, -0.38125000000000203f), EntityDimensions.scalable(0.12500000000000178f , 0.12500000000000233f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.8249999999999992f , 1.068750000000001f, 0.1562499999999979f), EntityDimensions.scalable(0.1250000000000011f , 0.1250000000000024f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7187499999999994f , 0.5249999999999986f, 0.9312499999999981f), EntityDimensions.scalable(0.1250000000000011f , 0.12500000000000133f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.4937499999999994f , 0.5249999999999986f, 1.0687499999999983f), EntityDimensions.scalable(0.12500000000000122f , 0.12500000000000178f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.6062499999999994f , 0.5249999999999986f, 1.0062499999999983f), EntityDimensions.scalable(0.12500000000000133f , 0.125000000000001f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.28750000000000053f , 0.5249999999999986f, 1.2312499999999986f), EntityDimensions.scalable(0.12500000000000122f , 0.1250000000000009f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.5812500000000008f , 0.5249999999999986f, 1.2937499999999993f), EntityDimensions.scalable(0.12500000000000133f , 0.1250000000000009f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.6906250000000008f , 0.5249999999999986f, 1.2156249999999993f), EntityDimensions.scalable(0.12500000000000133f , 0.1250000000000009f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.8312500000000008f , 0.5249999999999986f, 1.1374999999999993f), EntityDimensions.scalable(0.12500000000000133f , 0.1250000000000009f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.1187500000000024f , 0.40000000000000024f, -0.9875000000000012f), EntityDimensions.scalable(0.125f , 0.1250000000000011f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(1.1187500000000024f , 0.40000000000000024f, -0.9875000000000012f), EntityDimensions.scalable(0.125f , 0.1250000000000011f))

        };
    }
}
