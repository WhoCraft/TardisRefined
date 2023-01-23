package whocraft.tardis_refined.common.tardis.themes;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;


public enum ConsoleTheme implements StringRepresentable {


    FACTORY("factory", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0,0.6f,-1), EntityDimensions.scalable(0.125f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.85f,0.55f,-0.425f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vector3f(-.275f, .575f, 0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(-.275f, .54f, 0.875f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(-.275f, .5f, 1), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(.275f, .575f, 0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(1f, 0.525f, 0.215f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.06f, .5f, 1f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.6f,0.65f,-0.6f), EntityDimensions.scalable(0.1f, 0.1f))
    }),

    CRYSTAL("crystal", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.X, new Vector3f(-0.68f,0.5f,0.95f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.5f, 0.54f, 0.87f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.48f,0.5f,1.1f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.96f,0.5f,0.9f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0f,1f,1.4f), EntityDimensions.scalable(0.5f, 0.5f)),
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(1.1F + 0.05F, 0.4F, -0.3F - 0.05F), EntityDimensions.scalable(1f,1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.5F - 0.09F, 0.5F, -1F - 0.02F), EntityDimensions.scalable(1f,1f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.7F, 1.2F, -0.4F), EntityDimensions.scalable(1f,1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(0.3F, 0.4F, 1.2F), EntityDimensions.scalable(1f,1f))
    }),

    COPPER("copper", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.16f, 0.65f, -1.2f), EntityDimensions.scalable(0.125f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.175f, 0.65f, -1.2f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vector3f(0.725f, 0.95f, 0.15f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(.725f, 0.95f, 0), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(0.725f, 0.95f, -0.15f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(1.22f, 0.45f, -0.25f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(0.775f, 0.55f, 0.95f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.7f, 0.25f, -1.2f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(1.05f,1.4f,-0.5f), EntityDimensions.scalable(0.1f, 0.1f))
    }),

    CORAL("coral", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0.9f, 0.45f ,-1), EntityDimensions.scalable(0.125f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.615f, 0.6f, -1.1f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vector3f(-0.25f, 0.5f, 1.15f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(0, 0.5f, 1.05f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(0.25f, 0.5f, 1.15f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.075f, 0.65f, 0.8f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-.35f, 0.5f, -1.25f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.45f, 0.6f, 0.9f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.9f, 1f, -0.5f), EntityDimensions.scalable(0.1f, 0.1f))
    }),

    TOYOTA("toyota", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0f, 0.65f, -0.65f), EntityDimensions.scalable(0.125f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.31f, 0.525f, 1f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vector3f(-0.1f, 0.6f, 0.8f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(0f, 0.6f, 0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(0.1f, 0.6f, 0.8f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.7f, 0.65f, 0.15f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-1.05f, 0.5f, -0.175f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.6f, 0.55f, -0.7f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.35f,1.05f,0.6f), EntityDimensions.scalable(0.1f, 0.1f))
    }),

    VICTORIAN("victorian", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0.9f, 0.50000304f, 0.15f), EntityDimensions.scalable(0.125f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.59999996f, 0.55000305f, 0.65f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vector3f(-0.54999995f, 0.75000304f, 0.15f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.49999997f, 0.75000304f, 0.3000002f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.39999998f, 0.75000304f, 0.4000002f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.59999996f, 0.50000304f, -0.7f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.24999997f, 0.6500031f, -0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.25000003f, 0.6500031f, -0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.59999996f, 0.6500031f, -0.35f), EntityDimensions.scalable(0.1f, 0.1f))
    }),

    MYST("myst", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.7f, 0.6500015f, -0.2999999f- 0.5f), EntityDimensions.scalable(0.125f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(-0.34999996f, 0.6000061f, 1.5999999f- 0.5f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vector3f(-0.84999996f, 0.5500015f, -0.099999905f- 0.5f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.95f, 0.50000155f, -0.049999904f- 0.5f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.95f, 0.55f, -0.44999972f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-1.0999999f, 0.5f, 0.15000018f- 0.5f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(0.6f, 0.75000155f, 0.15f- 0.5f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(1.15f, 0.5500015f, 0.7f- 0.5f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0.75f, 0.6500015f, 0.95f- 0.5f), EntityDimensions.scalable(0.1f, 0.1f))
    }),

    NUKA("nuka", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.225f,0.6f,1), EntityDimensions.scalable(0.125f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.225f,0.6f,-1), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vector3f(-.975f, .55f, -0.45f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(-1, .55f, -0.35f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(-1.05f, .55f, -0.25f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.815f, 0.7f, -0.315f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.9f, .55f, 0.55f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(0.15f, 0.85f, 0.75f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(0,1.15f,-0.6f), EntityDimensions.scalable(0.1f, 0.1f))
    }),

    INITIATIVE("initiative", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(1.1920929E-8f, 0.6500015f, -0.9999997f), EntityDimensions.scalable(0.125f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(3.576279E-8f, 0.7000061f, 1.0999999f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vector3f(-0.59999996f, 0.700003f, -0.5999999f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(-0.75f, 0.7f, -0.44999972f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(-0.84999996f, 0.700003f, -0.2999998f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-0.9499999f, 0.6f, 0.5000001f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-0.29999998f, 0.6000015f, -1.0499997f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.8499999f, 0.75f, 0.4500001f), EntityDimensions.scalable(0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vector3f(-0.55f, 1.4500015f, 0.2999998f), EntityDimensions.scalable(0.1f, 0.1f))
    });

    private final String id;
    private final ControlSpecification[] controlSpecificationList;

    ConsoleTheme(String id, ControlSpecification[] controlSpecificationList) {
        this.id = id;
        this.controlSpecificationList = controlSpecificationList;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }

    public ControlSpecification[] getControlSpecificationList() {
        return controlSpecificationList;
    }

    private static final ConsoleTheme[] vals = values();

    public ConsoleTheme next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}