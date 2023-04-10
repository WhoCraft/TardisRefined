package whocraft.tardis_refined.common.tardis.themes.console;


import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;

public class CoralConsoleTheme extends ConsoleThemeDetails {

    @Override
    public ControlSpecification[] getControlSpecification() {
        return new ControlSpecification[] {
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0.9f, 0.45f ,-1), EntityDimensions.scalable(0.125f, 0.1875f)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.6150000095367432F, 0.6000000238418579F, -1.0750000234693289F), EntityDimensions.scalable(0.15F, 0.2F)),
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.25f, 0.5f, 1.15f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(0, 0.5f, 1.05f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(0.25F, 0.5F, 1.124999975785613F), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.07500000298023224F, 0.6499999761581421F, 0.7500000111758709F), EntityDimensions.scalable(0.2F, 0.1F)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.3499999940395355F, 0.4249999988824129F, -1.25F), EntityDimensions.scalable(0.2F, 0.15F)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.45f, 0.6f, 0.9f), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.7749999742954969F, 0.8749999981373549F, -0.5F), EntityDimensions.scalable(0.40000004F, 0.35000002F)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(1.0500000230967999F, 0.3749969359487295F, 0.724999999627471F), EntityDimensions.scalable(0.2F, 0.1F)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(-1.0750000234693289F, 0.37500000558793545F, -0.724999999627471F), EntityDimensions.scalable(0.15F, 0.1F))
        };
    }
}
