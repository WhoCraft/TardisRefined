package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class MystConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.674999987706542F, 0.6250015255063772F, -0.7749998923391104F), EntityDimensions.scalable(0.125f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.3249999638646841F, 0.5500061027705669F, 1.0999999046325684F), EntityDimensions.scalable(0.2F, 0.15F)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.84999996f, 0.5500015f, -0.099999905f- 0.5f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.95f, 0.50000155f, -0.049999904f- 0.5f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.95f, 0.55f, -0.44999972f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-1.0999999046325684F, 0.5F, -0.32499981485307217F), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(0.5750000234693289F, 0.7500015497207642F, -0.3249999936670065F), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(1.149999976158142F, 0.5500015020370483F, 0.19999998807907104F), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.75F, 0.5750015247613192F, 0.424999987706542F), EntityDimensions.scalable(0.40000004F, 0.15F)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(2.384185648907078E-8F, 0.649999987334013F, -1.0249999519437551F), EntityDimensions.scalable(0.15F, 0.15F)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.45000005f, 0.5500061f, 1.05f), EntityDimensions.scalable(0.15F, 0.1F))
        };
    }
}
