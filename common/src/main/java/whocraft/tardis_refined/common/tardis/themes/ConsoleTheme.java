package whocraft.tardis_refined.common.tardis.themes;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;


public enum ConsoleTheme implements StringRepresentable {


    FACTORY("factory", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0,0.6f,-1), new BlockPos(0.125f, 0.1875f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.85f,0.55f,-0.425f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vector3f(-.275f, .575f, 0.75f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(-.275f, .54f, 0.875f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(-.275f, .5f, 1), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(.275f, .575f, 0.75f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(1f, 0.525f, 0.215f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.06f, .5f, 1f), new BlockPos(0.1f, 0.1f, 0.1f))
    }),

    COPPER("copper", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(0,0.6f,-1), new BlockPos(0.125f, 0.1875f, 0.1875f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(0.85f,0.55f,-0.425f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vector3f(-.275f, .575f, 0.75f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(-.275f, .54f, 0.875f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(-.275f, .5f, 1), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(.275f, .575f, 0.75f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(1f, 0.525f, 0.215f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.RANDOM, new Vector3f(-0.06f, .5f, 1f), new BlockPos(0.1f, 0.1f, 0.1f))
    }),

    NUKA("nuka", new ControlSpecification[] {
            new ControlSpecification(ConsoleControl.DOOR_TOGGLE, new Vector3f(-0.65f, 1f,-0.85f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.THROTTLE, new Vector3f(1f ,1.05f ,-0.15f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.X, new Vector3f(-1f ,1.05f ,1f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Y, new Vector3f(-1f ,1.05f ,1.25f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.Z, new Vector3f(-1f ,1.05f ,1.5f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.INCREMENT, new Vector3f(-1f ,1.3f ,1.25f), new BlockPos(0.1f, 0.1f, 0.1f)),
            new ControlSpecification(ConsoleControl.ROTATE, new Vector3f(-1f ,1.3f ,1.5f), new BlockPos(0.1f, 0.1f, 0.1f))
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
}
