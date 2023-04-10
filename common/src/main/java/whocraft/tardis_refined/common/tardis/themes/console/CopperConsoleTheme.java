package whocraft.tardis_refined.common.tardis.themes.console;


import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class CopperConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.16f, 0.65f, -1.2f), EntityDimensions.scalable(0.175F, 0.1875F)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.17499999701976776F, 0.6499999761581421F, -1.1750000473111868F), EntityDimensions.scalable(0.2F, 0.25F)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(0.725f, 0.95f, 0.15f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(.725f, 0.95f, 0), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(0.725f, 0.95f, -0.15f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(1.1950000282377005F, 0.44999998807907104F, -0.25F), EntityDimensions.scalable(0.15F, 0.15F)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(0.7499999757856131F, 0.5250000115483999F, 0.924999987706542F), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.674999987706542F, 0.25F, -1.1500000469386578F), EntityDimensions.scalable(0.2F, 0.15F)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.924999950453639F, 1.324999975040555F, -0.5F), EntityDimensions.scalable( 0.44999987F, 0.5999999F)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(0.299999987706542F, 0.5500030517578125F, 1.0999997854232788F),  EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-0.7850000020116568F, 0.5250015016645193F, -0.8999998085200787F),  EntityDimensions.scalable(0.15F, 0.1F))
        };
    }
}
