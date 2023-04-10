package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class ToyotaConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0f, 0.65f, -0.65f), EntityDimensions.scalable(0.125f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.3100000023841858F, 0.5249999761581421F, 0.974999999627471F), EntityDimensions.scalable(0.15F, 0.2F)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.1f, 0.6f, 0.8f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(0f, 0.6f, 0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(0.1f, 0.6f, 0.8f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.7f, 0.65f, 0.15f), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-1.05f, 0.5f, -0.175f), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.5750000234693289F, 0.550000011920929F, -0.7249999884516001F), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.3499999940395355F, 1.0499999523162842F, 0.6000000238418579F), EntityDimensions.scalable(0.35000002F, 0.25F)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(0.7000000476837158F, 0.6000030636787415F, -0.39999979734420776F), EntityDimensions.scalable(0.3F, 0.1F)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-0.65f, 0.5f, -0.82500017f), EntityDimensions.scalable(0.15F, 0.1F))
        };
    }
}
