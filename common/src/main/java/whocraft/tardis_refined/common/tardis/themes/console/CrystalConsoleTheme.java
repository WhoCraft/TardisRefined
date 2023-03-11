package whocraft.tardis_refined.common.tardis.themes.console;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityDimensions;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class CrystalConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.68f,0.5f,0.95f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.5f, 0.54f, 0.87f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.48f,0.5f,1.1f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.96f,0.5f,0.9f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0f,1f,1.4f), EntityDimensions.scalable(0.5f, 0.5f)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(1.1F + 0.05F, 0.4F, -0.3F - 0.05F), EntityDimensions.scalable(1f,1f)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.5F - 0.09F, 0.5F, -1F - 0.02F), EntityDimensions.scalable(1f,1f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.7F, 1.2F, -0.4F), EntityDimensions.scalable(1f,1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(0.3F, 0.4F, 1.2F), EntityDimensions.scalable(1f,1f)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(-0.34000003f, 0.5f, -1.22f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.65999997f, 0.5f, -1.02f), EntityDimensions.scalable(0.1f, 0.1f))
        };
    }
}
