package whocraft.tardis_refined.compat.portals;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import qouteall.dimlib.api.DimensionAPI;
import qouteall.q_misc_util.MiscHelper;
import whocraft.tardis_refined.common.dimension.DimensionHandler;

import java.util.function.BiFunction;

public class DimLibCompat {

	public static ServerLevel createDimension(Level level, ResourceKey<Level> id) {
		MinecraftServer server = MiscHelper.getServer();
		if (server == null) return null;
		ServerLevel world = server.levelKeys().contains(id) ? server.getLevel(id) : null;
		if (world != null) return world;
		BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory = DimensionHandler::formLevelStem;
		final ResourceKey<LevelStem> dimensionKey = ResourceKey.create(Registries.LEVEL_STEM, id.location());
		DimensionAPI.addDimensionDynamically(server, id.location(), dimensionFactory.apply(server, dimensionKey));
		// TODO, Is this important? DimensionAPI.saveDimensionConfiguration(id);

		world = server.getLevel(id);
		DimensionHandler.addDimension(world.dimension());
		return world;
	}

}
