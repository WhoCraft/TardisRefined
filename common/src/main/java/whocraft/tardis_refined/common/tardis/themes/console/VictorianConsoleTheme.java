package whocraft.tardis_refined.common.tardis.themes.console;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityDimensions;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class VictorianConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0.9f, 0.50000304f, 0.15f), EntityDimensions.scalable(0.125f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.59999996f, 0.55000305f, 0.65f), EntityDimensions.scalable(0.1F, 0.25F)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.54999995f, 0.75000304f, 0.15f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.49999997f, 0.75000304f, 0.3000002f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.39999998f, 0.75000304f, 0.4000002f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.5999999642372131F, 0.5000030398368835F, -0.699999988079071F), EntityDimensions.scalable(0.15F, 0.15F)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.2499999701976776F, 0.6500030755996704F, -0.6999999992549419F), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.2500000298023224F, 0.6500030755996704F, -0.724999999627471F), EntityDimensions.scalable(0.15F, 0.1F)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.5499999634921551F, 0.6500030755996704F, -0.29999999329447746F), EntityDimensions.scalable(0.3F, 0.15F)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(0.800000011920929F, 0.5000015497207642F, -0.8250000234693289F), EntityDimensions.scalable(0.2F, 0.1F)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.5499999523162842F, 0.525000000372529F, 0.7000001072883606F), EntityDimensions.scalable(0.15F, 0.1F))
        };
    }
}
