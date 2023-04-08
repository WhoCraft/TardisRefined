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
                new ControlSpecification(ConsoleControl.X, new Vector3f(-0.7050000187009573F, 0.5F, 0.949999988079071F), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.5F, 0.5400000214576721F, 0.8700000196695328F), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.4549999888986349F, 0.5F, 1.100000023841858F), EntityDimensions.scalable(0.1f, 0.1f)),
                new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.9099999666213989F, 0.3999999985098839F, 0.8499999642372131F), EntityDimensions.scalable(0.2F, 0.15F)),
                new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.0F, 0.824999988079071F, 1.3999999519437551F), EntityDimensions.scalable(0.5f, 0.55F)),
                new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(1.1999999284744263F, 0.4250000063329935F, -0.3500000238418579F), EntityDimensions.scalable(0.19999984F, 0.19999978F)),
                new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.5900000333786011F, 0.4250000063329935F, -1.0199999809265137F), EntityDimensions.scalable(0.19999984F, 0.19999984F)),
                new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.6499999761581421F, 1.0500000566244125F, -0.4000000059604645F), EntityDimensions.scalable(0.29999983F, 0.34999985F)),
                new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(0.30000001192092896F, 0.4000000059604645F, 1.2000000476837158F), EntityDimensions.scalable(0.24999978F, 0.14999984F)),
                new ControlSpecification(ConsoleControl.DIMENSION, new Vector3f(-0.2900000214576721F, 0.4250000063329935F, -1.2200000286102295F), EntityDimensions.scalable(0.2F, 0.10000001F)),
                new ControlSpecification(ConsoleControl.FAST_RETURN, new Vector3f(0.6599999666213989F, 0.5000000149011612F, -1.0199999809265137F), EntityDimensions.scalable(0.1F, 0.1F))
        };
    }
}
