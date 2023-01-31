package whocraft.tardis_refined.compat.portals;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.phys.Vec3;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalManipulation;
import qouteall.q_misc_util.MiscHelper;
import qouteall.q_misc_util.api.DimensionAPI;
import qouteall.q_misc_util.my_util.DQuaternion;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.api.event.TardisEvents;
import whocraft.tardis_refined.common.blockentity.door.TardisInternalDoor;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.registry.DimensionTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;


public class ImmersivePortals {

    public static Map<UUID, List<Portal>> tardisToPortalsMap = new HashMap<>();
    // First 4 is exterior, last 4 is door offsets, in order of East, South, West, North
    private static final Map<ShellTheme, List<Vec3>> themeToOffsetMap = new HashMap<>();

    public static ServerLevel createDimension(Level level, ResourceKey<Level> id) {
        if (Platform.isClient()) return null;
        MinecraftServer server = MiscHelper.getServer();
        if (server == null) return null;
        ServerLevel world = server.levelKeys().contains(id) ? server.getLevel(id) : null;
        if (world != null) return world;
        BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory = DimensionHandler::formLevelStem;
        final ResourceKey<LevelStem> dimensionKey = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, id.location());
        DimensionAPI.addDimensionDynamically(id.location(), dimensionFactory.apply(server, dimensionKey));
        DimensionAPI.saveDimensionConfiguration(id);
        world = server.getLevel(id);
        DimensionHandler.addDimension(world.dimension());
        return world;
    }

    public static void init() {
        if (!ModCompatChecker.immersivePortals()) return;
        TardisRefined.LOGGER.info("Immersive Portals Detected - Setting up Compatibility");
        TardisEvents.DOOR_OPENED_EVENT.register(ImmersivePortals::createPortals);
        TardisEvents.DOOR_CLOSED_EVENT.register(ImmersivePortals::destroyPortals);
        TardisEvents.SHELL_CHANGE_EVENT.register((operator, theme) -> {
            ImmersivePortals.destroyPortals(operator);
            if (operator.getInternalDoor().isOpen()) {
                ImmersivePortals.createPortals(operator);
            }
        });

        themeToOffsetMap.put(ShellTheme.FACTORY, List.of(new Vec3(0.499, 0, 0),
                new Vec3(0, 0, 0.499), new Vec3(-0.499, 0, 0), new Vec3(0, 0, -0.499),
                new Vec3(-1.375, 0, 0), new Vec3(0, 0, -1.375),
                new Vec3(1.375, 0, 0), new Vec3(0, 0, 1.375)));
        themeToOffsetMap.put(ShellTheme.POLICE_BOX, List.of(new Vec3(0.6, 0.25, 0),
                new Vec3(0, 0.25, 0.6), new Vec3(-0.6, 0.25, 0), new Vec3(0, 0.25, -0.6),
                new Vec3(-1.425, 0.125, 0), new Vec3(0, 0.125, -1.425),
                new Vec3(1.425, 0.125, 0), new Vec3(0, 0.125, 1.425)));
        themeToOffsetMap.put(ShellTheme.PHONE_BOOTH, List.of(new Vec3(0.5, 0.145, 0),
                new Vec3(0, 0.145, 0.5), new Vec3(-0.5, 0.145, 0), new Vec3(0, 0.145, -0.5),
                new Vec3(-1.435, 0.145, 0), new Vec3(0, 0.145, -1.435),
                new Vec3(1.435, 0.145, 0), new Vec3(0, 0.145, 1.435)));
        themeToOffsetMap.put(ShellTheme.MYSTIC, List.of(new Vec3(0.5, 0.15, 0),
                new Vec3(0, 0.15, 0.5), new Vec3(-0.5, 0.15, 0), new Vec3(0, 0.175, -0.5),
                new Vec3(-1.425, 0.15, 0), new Vec3(0, 0.15, -1.425),
                new Vec3(1.425, 0.15, 0), new Vec3(0, 0.15, 1.425)));
        themeToOffsetMap.put(ShellTheme.PRESENT, List.of(new Vec3(0.57, 0.175, 0),
                new Vec3(0, 0, 0.57), new Vec3(-0.57, 0, 0), new Vec3(0, 0, -0.57),
                new Vec3(-1.425, 0, 0), new Vec3(0, 0, -1.425),
                new Vec3(1.425, 0, 0), new Vec3(0, 0, 1.425)));
        themeToOffsetMap.put(ShellTheme.DRIFTER, List.of(new Vec3(0.61, 0, 0),
                new Vec3(0, 0, 0.61), new Vec3(-0.61, 0, 0), new Vec3(0, 0, -0.61),
                new Vec3(-1.425, 0, 0), new Vec3(0, 0, -1.425),
                new Vec3(1.425, 0, 0), new Vec3(0, 0, 1.425)));
        themeToOffsetMap.put(ShellTheme.VENDING, List.of(new Vec3(0.32, 0, 0),
                new Vec3(0, 0, 0.33), new Vec3(-0.32, 0, 0), new Vec3(0, 0, -0.32),
                new Vec3(-1.48, 0, 0), new Vec3(0, 0, -1.48),
                new Vec3(1.48, 0, 0), new Vec3(0, 0, 1.48)));
        themeToOffsetMap.put(ShellTheme.GROENING, List.of(new Vec3(0.5, 0, 0),
                new Vec3(0, 0, 0.5), new Vec3(-0.5, 0, 0), new Vec3(0, 0, -0.5),
                new Vec3(-1.33, 0, 0), new Vec3(0, 0, -1.33),
                new Vec3(1.33, 0, 0), new Vec3(0, 0, 1.33)));
        themeToOffsetMap.put(ShellTheme.BIG_BEN, List.of(new Vec3(0.46, 0, 0),
                new Vec3(0, 0, 0.46), new Vec3(-0.46, 0, 0), new Vec3(0, 0, -0.46),
                new Vec3(-1.3, 0, 0), new Vec3(0, 0, -1.3),
                new Vec3(1.3, 0, 0), new Vec3(0, 0, 1.3)));
        themeToOffsetMap.put(ShellTheme.NUKA, List.of(new Vec3(0.65, 0.35, 0),
                new Vec3(0, 0.35, 0.65), new Vec3(-0.65, 0.35, 0), new Vec3(0, 0.35, -0.65),
                new Vec3(-1.33, 0, 0), new Vec3(0, 0, -1.33),
                new Vec3(1.33, 0, 0), new Vec3(0, 0, 1.33)));

        for (ShellTheme value : ShellTheme.values()) {
            if (!themeToOffsetMap.containsKey(value) && !value.equals(ShellTheme.BRIEFCASE)) {
                TardisRefined.LOGGER.info("{} shell has not been setup for ImmersivePortals", value);
            }
        }
    }

    public static boolean exteriorHasPortalSupport(ShellTheme shellTheme) {
        return themeToOffsetMap.containsKey(shellTheme);
    }

    public static boolean onDoorRemoved(Level level, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        if (blockEntity instanceof TardisInternalDoor door) {
            if (level instanceof ServerLevel serverLevel) {
                if (!door.isMainDoor()) {
                    return true;
                }
                if (serverLevel.dimensionTypeId().equals(DimensionTypes.TARDIS)) {
                    TardisLevelOperator.get(serverLevel).ifPresent(ImmersivePortals::destroyPortals);
                }
            }
        }
        return true;
    }

    public static void createPortals(TardisLevelOperator operator) {
        if (!operator.getInternalDoor().isOpen()) {
            return;
        }
        if (!operator.isTardisReady()) {
            return;
        }
        if (tardisToPortalsMap.get(UUID.fromString(operator.getLevel().dimension().location().getPath())) != null) {
            return;
        }

        if (operator.getExteriorManager().getCurrentTheme().equals(ShellTheme.BRIEFCASE)) {
            return;
        }

        TardisInternalDoor door = operator.getInternalDoor();
        if (door == null) {
            return;
        }

        TardisNavLocation location = operator.getExteriorManager().getLastKnownLocation();
        BlockPos entryPositionBPos = door.getEntryPosition();
        Vec3 entryPosition = new Vec3(entryPositionBPos.getX() + 0.5, entryPositionBPos.getY() + 1, entryPositionBPos.getZ() + 0.5);
        BlockPos exteriorEntryBPos = location.position;
        Vec3 exteriorEntryPosition = new Vec3(exteriorEntryBPos.getX() + 0.5, exteriorEntryBPos.getY() + 1, exteriorEntryBPos.getZ() + 0.5);

        ShellTheme theme = operator.getExteriorManager().getCurrentTheme();
        switch (location.rotation) {
            case EAST -> exteriorEntryPosition = exteriorEntryPosition.add(themeToOffsetMap.get(theme).get(0));
            case SOUTH -> exteriorEntryPosition = exteriorEntryPosition.add(themeToOffsetMap.get(theme).get(1));
            case WEST -> exteriorEntryPosition = exteriorEntryPosition.add(themeToOffsetMap.get(theme).get(2));
            case NORTH -> exteriorEntryPosition = exteriorEntryPosition.add(themeToOffsetMap.get(theme).get(3));
        }
        switch (door.getEntryRotation()) {
            case EAST -> entryPosition = entryPosition.add(themeToOffsetMap.get(theme).get(4));
            case SOUTH -> entryPosition = entryPosition.add(themeToOffsetMap.get(theme).get(5));
            case WEST -> entryPosition = entryPosition.add(themeToOffsetMap.get(theme).get(6));
            case NORTH -> entryPosition = entryPosition.add(themeToOffsetMap.get(theme).get(7));
        }
        DQuaternion extQuat = DQuaternion.rotationByDegrees(new Vec3(0, -1, 0), location.rotation.toYRot());
        DQuaternion interiorQuat = DQuaternion.rotationByDegrees(new Vec3(0, -1, 0), door.getEntryRotation().toYRot());

        Portal exteriorPortal = createPortal(operator.getExteriorManager().getLevel(), exteriorEntryPosition, entryPosition, operator.getLevel().dimension(), extQuat);
        Portal interiorPortal = createDestPortal(exteriorPortal, entryPosition, retrievePortalType(), interiorQuat);
        tardisToPortalsMap.put(UUID.fromString(operator.getLevel().dimension().location().getPath()), List.of(exteriorPortal, interiorPortal));

        PortalManipulation.adjustRotationToConnect(exteriorPortal, interiorPortal);
        exteriorPortal.setInteractable(false);
        interiorPortal.setInteractable(false);

        CompoundTag tag = exteriorPortal.writePortalDataToNbt();
        tag.putBoolean("adjustPositionAfterTeleport", false);
        exteriorPortal.readPortalDataFromNbt(tag);

        tag = interiorPortal.writePortalDataToNbt();
        tag.putBoolean("adjustPositionAfterTeleport", false);
        interiorPortal.readPortalDataFromNbt(tag);

        exteriorPortal.level.addFreshEntity(exteriorPortal);
        interiorPortal.level.addFreshEntity(interiorPortal);
    }

    @ExpectPlatform
    public static EntityType<Portal> retrievePortalType(){
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }

    public static void destroyPortals(TardisLevelOperator operator) {
        UUID tardisID = UUID.fromString(operator.getLevel().dimension().location().getPath());
        List<Portal> portals = tardisToPortalsMap.get(tardisID);
        if (portals == null) {
            return;
        }
        for (Portal portal : portals) {
            if (portal != null) {
                portal.kill();
            }
        }
        tardisToPortalsMap.remove(tardisID);
    }

    public static Portal createDestPortal(Portal portal, Vec3 doorPos, EntityType<Portal> entityType, DQuaternion quat) {
        Level world = portal.getDestinationWorld();

        Portal newPortal = entityType.create(world);
        ((TARDISPortalData) newPortal).setTardisID(UUID.fromString(world.dimension().location().getPath()));
        newPortal.dimensionTo = portal.level.dimension();
        newPortal.setPos(doorPos);
        newPortal.setDestination(portal.getOriginPos());
        newPortal.specificPlayerId = portal.specificPlayerId;

        newPortal.initCullableRange(portal.cullableXStart * portal.scaling, portal.cullableXEnd * portal.scaling, -portal.cullableYStart * portal.scaling, -portal.cullableYEnd * portal.scaling);

        newPortal.width = portal.width;
        newPortal.height = portal.height;
        newPortal.axisW = new Vec3(1, 0, 0);
        newPortal.axisH = new Vec3(0, 1, 0);

        PortalManipulation.rotatePortalBody(newPortal, quat.toMcQuaternion());

        return newPortal;
    }

    public static Portal createPortal(Level level, Vec3 origin, Vec3 destination, ResourceKey<Level> destinationLvl, DQuaternion quat) {
        Portal portal = retrievePortalType().create(level);
        ((TARDISPortalData) portal).setTardisID(UUID.fromString(destinationLvl.location().getPath()));
        portal.setOriginPos(origin);
        portal.setDestinationDimension(destinationLvl);
        portal.setDestination(destination);
        portal.setOrientationAndSize(
                new Vec3(1, 0, 0), // axisW
                new Vec3(0, 1, 0), // axisH
                1, // width
                2.175 // height
        );
        PortalManipulation.rotatePortalBody(portal, quat.toMcQuaternion());

        return portal;
    }

}
