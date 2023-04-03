package whocraft.tardis_refined.common.tardis.themes.console;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityDimensions;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class NukaConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.225f,0.6f,1), EntityDimensions.scalable(0.125f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.22499999403953552F, 0.6000000238418579F, -1.0F), EntityDimensions.scalable(0.15F, 0.15F)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-.975f, .55f, -0.45f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-1, .55f, -0.35f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-1.05f, .55f, -0.25f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.7399999964982271F, 0.699999988079071F, -0.2899999972432852F), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.9f, .55f, 0.55f), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.15000000596046448F, 0.8500000238418579F, 0.6999999992549419F), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.0F, 1.049999974668026F, -0.4250000212341547F), EntityDimensions.scalable(0.45000005F, 0.25F)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(0.2000000085681677F, 0.5999984741210938F, 0.9499999992549419F), EntityDimensions.scalable(0.25F, 0.15F)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.65f, 0.6499985f, 0.6f),  EntityDimensions.scalable(0.2F, 0.1F))
        };
    }
}
