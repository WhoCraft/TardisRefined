package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;


public enum ConsoleTheme implements StringRepresentable {


    FACTORY("factory", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vec3(0,0.6f,-1), new BlockPos(0.125f, 0.1875f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vec3(0.85f,0.55f,-0.425f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vec3(-.275f, .575f, 0.75f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vec3(-.275f, .54f, 0.875f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vec3(-.275f, .5f, 1), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vec3(.275f, .575f, 0.75f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vec3(1f, 0.525f, 0.215f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vec3(-0.06f, .5f, 1f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vec3(-0.6f,0.65f,-0.6f), new BlockPos(0.1f, 0.1f, 0.1f))
    }),

    CRYSTAL("crystal", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.X, new Vec3(-0.68f,0.5f,0.95f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vec3(-0.5f, 0.54f, 0.87f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vec3(-0.48f,0.5f,1.1f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vec3(-0.96f,0.5f,0.9f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vec3(0f,1f,1.4f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vec3(1.1F + 0.05F, 0.4F, -0.3F - 0.05F), BlockPos.ZERO),
            new ControlSpecification(ConsoleControl.ROTATE, new Vec3(-0.5F - 0.09F, 0.5F, -1F - 0.02F), BlockPos.ZERO),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vec3(-0.7F, 1.2F, -0.4F), BlockPos.ZERO),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vec3(0.3F, 0.4F, 1.2F), BlockPos.ZERO)
    }),

    COPPER("copper", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vec3(-0.16f, 0.65f, -1.2f), new BlockPos(0.125f, 0.1875f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vec3(0.175f, 0.65f, -1.2f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vec3(0.725f, 0.95f, 0.15f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vec3(.725f, 0.95f, 0), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vec3(0.725f, 0.95f, -0.15f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vec3(1.22f, 0.45f, -0.25f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vec3(0.775f, 0.55f, 0.95f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vec3(0.7f, 0.25f, -1.2f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vec3(1.05f,1.4f,-0.5f), new BlockPos(0.1f, 0.1f, 0.1f))
    }),

    CORAL("coral", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vec3(0.9f, 0.45f ,-1), new BlockPos(0.125f, 0.1875f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vec3(0.615f, 0.6f, -1.1f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vec3(-0.25f, 0.5f, 1.15f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vec3(0, 0.5f, 1.05f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vec3(0.25f, 0.5f, 1.15f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vec3(-0.075f, 0.65f, 0.8f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vec3(-.35f, 0.5f, -1.25f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vec3(-0.45f, 0.6f, 0.9f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vec3(0.9f, 1f, -0.5f), new BlockPos(0.1f, 0.1f, 0.1f))
    }),

    TOYOTA("toyota", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vec3(0f, 0.65f, -0.65f), new BlockPos(0.125f, 0.1875f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vec3(0.31f, 0.525f, 1f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vec3(-0.1f, 0.6f, 0.8f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vec3(0f, 0.6f, 0.75f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vec3(0.1f, 0.6f, 0.8f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vec3(-0.7f, 0.65f, 0.15f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vec3(-1.05f, 0.5f, -0.175f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vec3(0.6f, 0.55f, -0.7f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vec3(0.35f,1.05f,0.6f), new BlockPos(0.1f, 0.1f, 0.1f))
    }),

    NUKA("nuka", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vec3(-0.225f,0.6f,1), new BlockPos(0.125f, 0.1875f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vec3(0.225f,0.6f,-1), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vec3(-.975f, .55f, -0.45f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vec3(-1, .55f, -0.35f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vec3(-1.05f, .55f, -0.25f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vec3(-0.815f, 0.7f, -0.315f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vec3(-0.9f, .55f, 0.55f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vec3(0.15f, 0.85f, 0.75f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.MONITOR, new Vec3(0,1.15f,-0.6f), new BlockPos(0.1f, 0.1f, 0.1f))
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
