package whocraft.tardis_refined.common.dimension.fabric;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.phys.Vec3;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalManipulation;
import qouteall.q_misc_util.MiscHelper;
import qouteall.q_misc_util.api.DimensionAPI;
import qouteall.q_misc_util.my_util.DQuaternion;
import whocraft.tardis_refined.common.dimension.DimensionHandler;

import java.util.function.BiFunction;

import static whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerImpl.addDimension;

public class DimensionHandlerIP {

    public static ServerLevel createDimension(Level level, ResourceKey<Level> id) {
        MinecraftServer server = MiscHelper.getServer();
        if (server == null) return null;
        ServerLevel world = server.levelKeys().contains(id) ? server.getLevel(id) : null;
        if (world != null) return world;
        BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory = DimensionHandler::formLevelStem;
        final ResourceKey<LevelStem> dimensionKey = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, id.location());
        DimensionAPI.addDimensionDynamically(id.location(), dimensionFactory.apply(server, dimensionKey));
        DimensionAPI.saveDimensionConfiguration(id);
        world = server.getLevel(id);
        addDimension(world.dimension());
        return world;
    }


    public static Portal createPortal(Level level, Vec3 origin, Vec3 destination, ResourceKey<Level> destinationLvl, Direction direction) {
        Portal portal = Portal.entityType.create(level);
        portal.setOriginPos(origin);
        portal.setDestinationDimension(destinationLvl);
        portal.setDestination(destination);
        portal.teleportable = false;
        portal.setOrientationAndSize(
                new Vec3(1, 0, 0), // axisW
                new Vec3(0, 1, 0), // axisH
                1, // width
                2 // height
        );
        PortalManipulation.rotatePortalBody(portal, DQuaternion.rotationByDegrees(new Vec3(0, -1, 0), Direction.EAST.toYRot()).toMcQuaternion());
        portal.level.addFreshEntity(portal);
        return portal;
    }


    public static boolean hasIP() {
        try {
            Class.forName("qouteall.q_misc_util.MiscHelper");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
