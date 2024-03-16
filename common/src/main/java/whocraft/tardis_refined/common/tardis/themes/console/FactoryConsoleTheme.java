package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class FactoryConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.8625f , 0.5625f, -0.45f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.29375f , 0.625f, 0.7375f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.29375f , 0.5625f, 0.8625f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.29375f , 0.5f, 0.9875f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0.018750000000000003f , 0.5249999999999988f, -1.003125f), EntityDimensions.scalable(0.1875f , 0.1875f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(0.09999999999999999f , 0.5437499999999988f, 0.9375000000000002f), EntityDimensions.scalable(0.1875f , 0.1875f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.075f , 0.5625f, 0.9875f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.8625f , 0.5625f, 0.425f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.6375f , 0.5625f, -0.6375f), EntityDimensions.scalable(0.1875f , 0.1875f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(-0.6562500000000002f , 0.5f, -0.84375f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.675f , 0.625f, -0.7f), EntityDimensions.scalable(0.125f , 0.125f)),

                new ControlSpecification(ConsoleControl.READOUT, new Vector3f(0.7874999999999993f , 0.6875f, 0.16875000000000034f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.6875000000000002f , 0.625f, 0.6875000000000003f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.6875000000000002f , 0.5625f, 0.8750000000000003f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.6718749999999998f , 0.625f, 0.4687500000000003f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.7499999999999998f , 0.625f, 0.3281250000000003f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.5000000000000002f , 0.6875f, 0.5625000000000003f), EntityDimensions.scalable(0.125f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(-0.03749999999999985f , 0.6875f, 0.7062499999999998f), EntityDimensions.scalable(0.12500000000000006f , 0.125f)),
                new ControlSpecification(ConsoleControl.GENERIC_NO_SHOW, new Vector3f(0.10625000000000018f , 0.6875f, 0.7062499999999998f), EntityDimensions.scalable(0.1250000000000001f , 0.125f))
        };
    }
}
