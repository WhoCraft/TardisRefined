package whocraft.tardis_refined.common.tardis.themes.console;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityDimensions;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class FactoryConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0,0.6f,-1), EntityDimensions.scalable(0.125f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.85f,0.55f,-0.425f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-.275f, .575f, 0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-.275f, .54f, 0.875f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-.275f, .5f, 1), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(.275f, .575f, 0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(1f, 0.525f, 0.215f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.06f, .5f, 1f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.6f,0.65f,-0.6f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.9f, 0.5250015f, 0.46500015f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(-0.65000004f, 0.55f, -0.83499986f), EntityDimensions.scalable(0.1f, 0.1f))
        };
    }
}
