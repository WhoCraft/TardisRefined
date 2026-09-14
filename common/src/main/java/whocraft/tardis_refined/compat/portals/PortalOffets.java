package whocraft.tardis_refined.compat.portals;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class PortalOffets {

    private final OffsetData shell, intDoor;
    private final Vec2 shellSize, doorSize;

    public PortalOffets(OffsetData shell, OffsetData intDoor, Vec2 shellSize, Vec2 doorSize) {
        this.shell = shell;
        this.intDoor = intDoor;
        this.shellSize = shellSize;
        this.doorSize = doorSize;
    }

    public OffsetData shell() {
        return shell;
    }

    public OffsetData intDoor() {
        return intDoor;
    }

    public Vec2 shellSize() {
        return shellSize;
    }

    public Vec2 doorSize() {
        return doorSize;
    }

    // East, South, West, North
    public static class OffsetData {

        public Vec3 east, south, west, north;

        public OffsetData(Vec3 east, Vec3 south, Vec3 west, Vec3 north) {
            this.east = east;
            this.south = south;
            this.west = west;
            this.north = north;
        }

       public static OffsetData fromNorth(Vec3 northOffset) {
            Vec3 east = rotateClockwise(northOffset);
            Vec3 south = rotateClockwise(east);
            Vec3 west = rotateClockwise(south);
            return new OffsetData(east, south, west, northOffset);
        }

          public static Vec3 rotateClockwise(Vec3 vec) {
            return new Vec3(-vec.z, vec.y, vec.x);
        }

        public static Vec3 rotateForDirection(Vec3 northOffset, Direction direction) {
            return switch (direction) {
                case NORTH -> northOffset;
                case EAST -> rotateClockwise(northOffset);
                case SOUTH -> rotateClockwise(rotateClockwise(northOffset));
                case WEST -> rotateClockwise(rotateClockwise(rotateClockwise(northOffset)));
                default -> throw new IllegalArgumentException("Unsupported (non-horizontal) direction for a portal offset: " + direction);
            };
        }

        public Vec3 east() {
            return east;
        }

        public OffsetData setEast(Vec3 east) {
            this.east = east;
            return this;
        }

        public Vec3 south() {
            return south;
        }

        public OffsetData setSouth(Vec3 south) {
            this.south = south;
            return this;
        }

        public Vec3 west() {
            return west;
        }

        public OffsetData setWest(Vec3 west) {
            this.west = west;
            return this;
        }

        public Vec3 north() {
            return north;
        }

        public OffsetData setNorth(Vec3 north) {
            this.north = north;
            return this;
        }
    }


}