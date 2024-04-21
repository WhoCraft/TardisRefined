package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.registry.TRControlRegistry;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class VictorianConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(TRControlRegistry.THROTTLE, new Vector3f(-0.7f , 0.5f, 0.6125f), EntityDimensions.scalable(0.25f , 0.25f)),
                new ControlSpecification(TRControlRegistry.X, new Vector3f(-0.575f , 0.78125f, 0.1125f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(TRControlRegistry.Y, new Vector3f(-0.48125f , 0.78125f, 0.253125f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(TRControlRegistry.Z, new Vector3f(-0.403125f , 0.765625f, 0.39375f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(TRControlRegistry.INCREMENT, new Vector3f(-0.715625f , 0.65625f, 0.159375f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(TRControlRegistry.ROTATE, new Vector3f(-0.02187500000000002f , 0.75f, -0.675f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(TRControlRegistry.RANDOM, new Vector3f(-0.2625f , 0.662499999999999f, -0.7437500000000001f), EntityDimensions.scalable(0.12499999999999997f , 0.125f)),
                new ControlSpecification(TRControlRegistry.DOOR_TOGGLE, new Vector3f(0.8312500000000002f , 0.5625f, -0.15624999999999994f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(TRControlRegistry.MONITOR, new Vector3f(-0.6375f , 0.5625f, -0.3875f), EntityDimensions.scalable(0.25f , 0.25f)),
                new ControlSpecification(TRControlRegistry.DIMENSION, new Vector3f(0.8f , 0.5f, -0.85625f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(TRControlRegistry.FAST_RETURN, new Vector3f(0.721875f , 0.75f, -0.090625f), EntityDimensions.scalable(0.0625f , 0.0625f)),

                new ControlSpecification(TRControlRegistry.HANDBRAKE, new Vector3f(0.315625f , 0.5f, 0.846875f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(TRControlRegistry.FUEL, new Vector3f(-0.34062499999999996f , 0.5f, 0.846875f), EntityDimensions.scalable(0.12499999999999994f , 0.125f)),
                new ControlSpecification(TRControlRegistry.READOUT, new Vector3f(-0.00937499999999987f , 0.7500000000000018f, 0.646875f), EntityDimensions.scalable(0.12499999999999994f , 0.125f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.12187499999999994f , 0.5437500000000013f, 0.8593749999999993f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.8156250000000004f , 0.5437500000000013f, 0.45312500000000033f), EntityDimensions.scalable(0.12499999999999978f , 0.12499999999999994f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.8968750000000001f , 0.5125000000000008f, 0.24062500000000042f), EntityDimensions.scalable(0.12499999999999978f , 0.12499999999999986f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.6406250000000002f , 0.5125000000000008f, -0.7343749999999998f), EntityDimensions.scalable(0.12499999999999978f , 0.12499999999999989f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.4406250000000001f , 0.8125000000000013f, -0.44687500000000074f), EntityDimensions.scalable(0.12499999999999978f , 0.1250000000000001f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.590625f , 0.8125000000000013f, -0.17187500000000083f), EntityDimensions.scalable(0.12499999999999967f , 0.12500000000000025f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.49062500000000037f , 0.8125000000000013f, -0.29062500000000074f), EntityDimensions.scalable(0.12499999999999989f , 0.12500000000000014f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.2406249999999997f , 0.6874999999999996f, -0.784375000000001f), EntityDimensions.scalable(0.12499999999999975f , 0.12500000000000022f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.0843749999999997f , 0.5874999999999995f, -0.9406250000000005f), EntityDimensions.scalable(0.12499999999999979f , 0.12500000000000022f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(-0.0718750000000003f , 0.5874999999999995f, -0.9406250000000005f), EntityDimensions.scalable(0.12499999999999974f , 0.12500000000000022f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.5031249999999995f , 0.5874999999999995f, -0.6218750000000006f), EntityDimensions.scalable(0.12499999999999967f , 0.12500000000000022f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.8781249999999995f , 0.5249999999999995f, 0.12812499999999932f), EntityDimensions.scalable(0.12499999999999967f , 0.12500000000000022f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.5843749999999995f , 0.5249999999999995f, 0.7031249999999994f), EntityDimensions.scalable(0.12499999999999978f , 0.12500000000000022f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.6968749999999991f , 0.5249999999999995f, 0.5281249999999993f), EntityDimensions.scalable(0.12499999999999978f , 0.12500000000000033f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.5468749999999996f , 0.7499999999999987f, 0.3031249999999996f), EntityDimensions.scalable(0.12499999999999978f , 0.12500000000000033f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.8218749999999991f , 0.537499999999997f, 0.34687499999999943f), EntityDimensions.scalable(0.12499999999999978f , 0.12500000000000033f)),
                new ControlSpecification(TRControlRegistry.GENERIC_NO_SHOW, new Vector3f(0.6468749999999989f , 0.6249999999999982f, 0.3593749999999994f), EntityDimensions.scalable(0.12499999999999978f , 0.12500000000000033f))
        };
    }
}
