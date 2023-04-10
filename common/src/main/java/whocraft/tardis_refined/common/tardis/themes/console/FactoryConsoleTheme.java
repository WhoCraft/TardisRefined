package whocraft.tardis_refined.common.tardis.themes.console;

import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class FactoryConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0,0.6f,-1), EntityDimensions.scalable(0.125f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.85f,0.55f,-0.425f), EntityDimensions.scalable(0.15F, 0.15F)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-.275f, .575f, 0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-.275f, .54f, 0.875f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-.275f, .5f, 1), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(0.2750000059604645F, 0.6249999888241291F, 0.75F), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(1f, 0.525f, 0.215f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.06f, .5f, 1f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.6000000238418579F, 0.6249999757856131F, -0.5750000234693289F), EntityDimensions.scalable(0.2F, 0.1F)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.849999975413084F, 0.5250015258789062F, 0.41500015184283257F), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(-0.6250000353902578F, 0.550000011920929F, -0.8599998597055674F), EntityDimensions.scalable(0.1f, 0.1f))
        };
    }
}
