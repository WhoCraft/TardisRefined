package whocraft.tardis_refined.compat.portals;

import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class PortalOffets {

    private final OffsetData shell, intDoor;
    private final Vec2 size;

    public PortalOffets(OffsetData shell, OffsetData intDoor, Vec2 size) {
        this.shell = shell;
        this.intDoor = intDoor;
        this.size = size;
    }

    public OffsetData shell() {
        return shell;
    }

    public OffsetData intDoor() {
        return intDoor;
    }

    public Vec2 size() {
        return size;
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
