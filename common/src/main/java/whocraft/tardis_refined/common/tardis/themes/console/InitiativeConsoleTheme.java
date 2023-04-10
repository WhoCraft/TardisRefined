package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class InitiativeConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(1.1920929E-8f, 0.6500015f, -0.9999997f), EntityDimensions.scalable(0.125f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(3.5762788286319847E-8F, 0.6750061269849539F, 1.0749999042600393F), EntityDimensions.scalable(0.15F, 0.15F)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.59999996f, 0.700003f, -0.5999999f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.75f, 0.7f, -0.44999972f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.84999996f, 0.700003f, -0.2999998f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.9499999f, 0.6f, 0.5000001f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.3249999824911356F, 0.5750015135854483F, -1.049999713897705F), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.8499999f, 0.75f, 0.4500001f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.5000000111758709F, 1.4000014774501324F, 0.32499980367720127F), EntityDimensions.scalable(0.3F, 0.25F)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(-0.47499999962747097F, 0.9499969482421875F, -0.29999980330467224F), EntityDimensions.scalable(0.3F, 0.15F)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.0F, 0.8500015139579773F, -0.5999995470046997F), EntityDimensions.scalable(0.2F, 0.15F))
        };
    }
}
