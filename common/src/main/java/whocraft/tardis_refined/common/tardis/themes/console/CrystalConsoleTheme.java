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
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.7050000187009573F, 1.0499999523162842F, 0.949999988079071F), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.5F, 1.0400000289082527F, 0.8700000047683716F), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.4549999888986349F, 1.0499999523162842F, 1.100000023841858F), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.9099999777972698F, 0.9000000059604645F, 0.849999975413084F), EntityDimensions.scalable(0.2F, 0.15F)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.0F, 1.3500000052154064F, 1.399999976158142F), EntityDimensions.scalable(0.5f, 0.55F)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(1.1999999769032001F, 0.9500000141561031F, -0.3500000238418579F), EntityDimensions.scalable(0.19999984F, 0.19999978F)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.5900000333786011F, 0.9499999999999744F, -1.0199999809265137F), EntityDimensions.scalable(0.19999984F, 0.19999984F)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.649999988079071f, 1.4500000476837016f, -0.4000000059604645f), EntityDimensions.scalable(0.29999983F, 0.34999985F)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(0.30000001192092896F, 0.9500000141561031F, 1.2000000476837158F), EntityDimensions.scalable(0.24999978F, 0.14999984F)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(-0.290000032633543F, 0.9500000067055225F, -1.2200000286102295F), EntityDimensions.scalable(0.2F, 0.10000001F)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.6599999666213989F, 1.0000000074505806F, -1.0199999809265137F), EntityDimensions.scalable(0.1F, 0.1F))
        };
    }
}
