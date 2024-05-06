package whocraft.tardis_refined.compat.portals;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalManipulation;
import qouteall.imm_ptl.core.render.PortalEntityRenderer;
import qouteall.q_misc_util.MiscHelper;
import qouteall.q_misc_util.api.DimensionAPI;
import qouteall.q_misc_util.my_util.DQuaternion;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.api.event.TardisEvents;
import whocraft.tardis_refined.common.blockentity.door.TardisInternalDoor;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRDimensionTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

import static whocraft.tardis_refined.registry.TREntityRegistry.ENTITY_TYPES;
import static whocraft.tardis_refined.registry.TREntityRegistry.registerStatic;


public class ImmersivePortals {

    public static RegistrySupplier<EntityType<BOTIPortalEntity>> BOTI_PORTAL = null;
    public static Map<UUID, List<Portal>> tardisToPortalsMap = new HashMap<>();

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

    // First 4 is exterior, last 4 is door offsets, in order of East, South, West, North
    private static final Map<ResourceLocation, PortalOffets> themeToOffsetMap = new HashMap<>();

    public static void init() {
        if (!ModCompatChecker.immersivePortals()) return;
        TardisRefined.LOGGER.info("Immersive Portals Detected - Setting up Compatibility");

        BOTI_PORTAL = ENTITY_TYPES.register("boti_portal", () -> registerStatic(BOTIPortalEntity::new, MobCategory.MISC, 1, 1, 96, 20, "boti_portal"));

        //TODO Temp! Would crash a server
        EntityRendererRegistry.register(ImmersivePortals.BOTI_PORTAL.get(), PortalEntityRenderer::new);

        TardisEvents.DOOR_OPENED_EVENT.register(ImmersivePortals::createPortals);
        TardisEvents.DOOR_CLOSED_EVENT.register(ImmersivePortals::destroyPortals);
        TardisEvents.SHELL_CHANGE_EVENT.register((operator, theme, isSetupTardis) -> {
            TardisInternalDoor internalDoor = operator.getInternalDoor();
            ImmersivePortals.destroyPortals(operator);
            if (internalDoor != null){
                if (internalDoor.isOpen()) {
                    ImmersivePortals.createPortals(operator);
                }
            }
        });

        themeToOffsetMap.put(ShellTheme.FACTORY.getId(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.499, 0.3125, 0),
                new Vec3(0, 0.3125, 0.499), new Vec3(-0.499, 0.3125, 0), new Vec3(0, 0.3125, -0.499)), new PortalOffets.OffsetData(
                new Vec3(-1.375, 0.125, 0), new Vec3(0, 0.125, -1.375),
                new Vec3(1.375, 0.125, 0), new Vec3(0, 0.125, 1.375)), new Vec2(1, 2)));

        themeToOffsetMap.put(ShellTheme.POLICE_BOX.getId(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.6, 0.125, 0),
                new Vec3(0, 0.125, 0.6), new Vec3(-0.6, 0.125, 0), new Vec3(0, 0.125, -0.6)),
                new PortalOffets.OffsetData(new Vec3(-1.425, 0.0625, 0), new Vec3(0, 0.0625, -1.425),
                        new Vec3(1.425, 0.0625, 0), new Vec3(0, 0.0625, 1.425)), new Vec2(1, 2)));

        themeToOffsetMap.put(ShellTheme.PHONE_BOOTH.getId(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.5, 0.125, 0),
                new Vec3(0, 0.125, 0.5), new Vec3(-0.5, 0.125, 0), new Vec3(0, 0.125, -0.5)),
                new PortalOffets.OffsetData(new Vec3(-1.435, 0, 0), new Vec3(0, 0, -1.435),
                        new Vec3(1.435, 0, 0), new Vec3(0, 0, 1.435)), new Vec2(1, 2)));

        themeToOffsetMap.put(ShellTheme.MYSTIC.getId(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.5, 0.1875, 0),
                new Vec3(0, 0.1875, 0.5), new Vec3(-0.5, 0.1875, 0), new Vec3(0, 0.1875, -0.5)),
                new PortalOffets.OffsetData(new Vec3(-1.425, 0.0625, 0), new Vec3(0, 0.0625, -1.425),
                        new Vec3(1.425, 0.0625, 0), new Vec3(0, 0.0625, 1.425)), new Vec2(1, 2)));

        themeToOffsetMap.put(ShellTheme.PRESENT.getId(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.57, 0, 0),
                new Vec3(0, 0, 0.57), new Vec3(-0.57, 0, 0), new Vec3(0, 0, -0.57)), new PortalOffets.OffsetData(
                new Vec3(-1.455, 0, 0), new Vec3(0, 0, -1.455),
                new Vec3(1.455, 0, 0), new Vec3(0, 0, 1.455)), new Vec2(1, 2.175f)));

        themeToOffsetMap.put(ShellTheme.DRIFTER.getId(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.61, 0.125, 0),
                new Vec3(0, 0.125, 0.61), new Vec3(-0.61, 0.125, 0), new Vec3(0, 0.125, -0.61)), new PortalOffets.OffsetData(
                new Vec3(-1.425, 0.0625, 0), new Vec3(0, 0.0625, -1.425),
                new Vec3(1.425, 0.0625, 0), new Vec3(0, 0.0625, 1.425)), new Vec2(1, 2)));

        themeToOffsetMap.put(ShellTheme.GROENING.getId(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.5, 0, 0),
                new Vec3(0, 0, 0.5), new Vec3(-0.5, 0, 0), new Vec3(0, 0, -0.5)), new PortalOffets.OffsetData(
                new Vec3(-1.33, 0, 0), new Vec3(0, 0, -1.33),
                new Vec3(1.33, 0, 0), new Vec3(0, 0, 1.33)), new Vec2(1, 2.25f)));

        themeToOffsetMap.put(ShellTheme.BIG_BEN.getId(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.46, 0, 0),
                new Vec3(0, 0, 0.46), new Vec3(-0.46, 0, 0), new Vec3(0, 0, -0.46)), new PortalOffets.OffsetData(
                new Vec3(-1.3, 0, 0), new Vec3(0, 0, -1.3),
                new Vec3(1.3, 0, 0), new Vec3(0, 0, 1.3)), new Vec2(1, 2)));

        themeToOffsetMap.put(ShellTheme.NUKA.getId(),
                new PortalOffets(
                        new PortalOffets.OffsetData(new Vec3(0.65, 0.375, 0), new Vec3(0, 0.375, 0.65), new Vec3(-0.65, 0.375, 0), new Vec3(0, 0.375, -0.65)),
                        new PortalOffets.OffsetData( new Vec3(-1.33, 0, 0), new Vec3(0, 0, -1.33), new Vec3(1.33, 0, 0), new Vec3(0, 0, 1.33)), new Vec2(1, 2)
                )
        );

        /* TODO: Fix these
        themeToOffsetMap.put(ShellTheme.PAGODA.getId(),
                new PortalOffets(
                        new PortalOffets.OffsetData(new Vec3(0.65, 0.09375, 0), new Vec3(0, 0.09375, 0.51), new Vec3(-0.65, 0.09375, 0), new Vec3(0, 0.09375, -0.51)),
                        new PortalOffets.OffsetData( new Vec3(-1.33, 0.09375, 0), new Vec3(0, 0.09375, -1.33), new Vec3(1.33, 0.09375, 0), new Vec3(0, 0.09375, 1.33))
                )
        );
        themeToOffsetMap.put(ShellTheme.GROENING.getId(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.5, 0.09375, 0),
                new Vec3(0, 0.09375, 0.5), new Vec3(-0.5, 0.09375, 0), new Vec3(0, 0.09375, -0.5)), new PortalOffets.OffsetData(
                new Vec3(-1.33, 0.09375, 0), new Vec3(0, 0.09375, -1.33),
                new Vec3(1.33, 0.09375, 0), new Vec3(0, 0.09375, 1.33))));
         */


        // First 4 is exterior, last 4 is door offsets, in order of East, South, West, North
        themeToOffsetMap.put(ShellTheme.PORTALOO.getId(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.51, 0.125, 0),
                new Vec3(0, 0.125, 0.51), new Vec3(-0.51, 0.125, 0), new Vec3(0, 0.125, -0.51)),
                new PortalOffets.OffsetData(
                        new Vec3(-1.425, 0, 0), new Vec3(0, 0, -1.425),
                        new Vec3(1.425, 0, 0), new Vec3(0, 0, 1.425)), new Vec2(1, 2)));

        for (ResourceLocation value : ShellTheme.SHELL_THEME_REGISTRY.keySet()) {
            if (!themeToOffsetMap.containsKey(value) && !value.equals(ShellTheme.BRIEFCASE)) {
                TardisRefined.LOGGER.info("{} shell has not been setup for ImmersivePortals", value);
            }
        }
    }

    public static boolean exteriorHasPortalSupport(ResourceLocation shellTheme) {
        return themeToOffsetMap.containsKey(shellTheme);
    }

    public static boolean onDoorRemoved(Level level, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        if (blockEntity instanceof TardisInternalDoor door) {
            if (level instanceof ServerLevel serverLevel) {
                if (!door.isMainDoor()) {
                    return true;
                }
                if (serverLevel.dimensionTypeId().equals(TRDimensionTypes.TARDIS)) {
                    TardisLevelOperator.get(serverLevel).ifPresent(ImmersivePortals::destroyPortals);
                }
            }
        }
        return true;
    }

    public static void createPortals(TardisLevelOperator operator) {
        UUID dimId = UUID.fromString(operator.getLevel().dimension().location().getPath());
        ResourceLocation theme = operator.getAestheticHandler().getShellTheme();
        TardisInternalDoor door = operator.getInternalDoor();
        TardisPilotingManager pilotingManager = operator.getPilotingManager();

        if (operator.getInteriorManager().isCave() || !door.isOpen() || !operator.isTardisReady() || tardisToPortalsMap.get(dimId) != null || !exteriorHasPortalSupport(theme) || door == null) {
            return;
        }

        TardisNavLocation location = pilotingManager.getCurrentLocation();
        BlockPos entryPositionBPos = door.getEntryPosition();
        Vec3 entryPosition = new Vec3(entryPositionBPos.getX() + 0.5, entryPositionBPos.getY() + 1, entryPositionBPos.getZ() + 0.5);
        BlockPos exteriorEntryBPos = location.getPosition();
        Vec3 exteriorEntryPosition = new Vec3(exteriorEntryBPos.getX() + 0.5, exteriorEntryBPos.getY() + 1, exteriorEntryBPos.getZ() + 0.5);

        theme = operator.getAestheticHandler().getShellTheme();
        PortalOffets themeData = themeToOffsetMap.get(theme);
        PortalOffets.OffsetData interiorDoor = themeData.intDoor();
        PortalOffets.OffsetData exteriorDoor = themeData.shell();

        Level operatorLevel = operator.getLevel();


        switch (location.getDirection()) {
            case EAST -> exteriorEntryPosition = exteriorEntryPosition.add(exteriorDoor.east());
            case SOUTH -> exteriorEntryPosition = exteriorEntryPosition.add(exteriorDoor.south());
            case WEST -> exteriorEntryPosition = exteriorEntryPosition.add(exteriorDoor.west());
            case NORTH -> exteriorEntryPosition = exteriorEntryPosition.add(exteriorDoor.north());
        }

        switch (door.getEntryRotation()) {
            case EAST -> entryPosition = entryPosition.add(interiorDoor.east());
            case SOUTH -> entryPosition = entryPosition.add(interiorDoor.south());
            case WEST -> entryPosition = entryPosition.add(interiorDoor.west());
            case NORTH -> entryPosition = entryPosition.add(interiorDoor.north());
        }
        DQuaternion extQuat = DQuaternion.rotationByDegrees(new Vec3(0, -1, 0), location.getDirection().toYRot());
        DQuaternion interiorQuat = DQuaternion.rotationByDegrees(new Vec3(0, -1, 0), door.getEntryRotation().toYRot());

        BOTIPortalEntity exteriorPortal = createPortal(location.getLevel(), exteriorEntryPosition, entryPosition, operatorLevel.dimension(), extQuat);
        BOTIPortalEntity interiorPortal = createDestPortal(exteriorPortal, entryPosition, ImmersivePortals.BOTI_PORTAL.get(), interiorQuat);

        tardisToPortalsMap.put(UUID.fromString(operatorLevel.dimension().location().getPath()), List.of(exteriorPortal, interiorPortal));

        PortalManipulation.adjustRotationToConnect(exteriorPortal, interiorPortal);
        exteriorPortal.setInteractable(false);
        interiorPortal.setInteractable(false);


        CompoundTag tag = new CompoundTag();
        tag.putBoolean("adjustPositionAfterTeleport", false);
        exteriorPortal.updatePortalFromNbt(tag);

        interiorPortal.updatePortalFromNbt(tag);

        exteriorPortal.level().addFreshEntity(exteriorPortal);
        interiorPortal.level().addFreshEntity(interiorPortal);
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

    public static BOTIPortalEntity createDestPortal(BOTIPortalEntity portal, Vec3 doorPos, EntityType<BOTIPortalEntity> entityType, DQuaternion quat) {
        Level world = portal.getDestinationWorld();

        BOTIPortalEntity newPortal = entityType.create(world);
        portal.setTardisId(UUID.fromString(world.dimension().location().getPath()));
        newPortal.dimensionTo = portal.level().dimension();
        newPortal.setPos(doorPos);
        newPortal.setDestination(portal.getOriginPos());
        newPortal.specificPlayerId = portal.specificPlayerId;

        //TODO Is this important? newPortal.initCullableRange(portal.cullableXStart * portal.scaling, portal.cullableXEnd * portal.scaling, -portal.cullableYStart * portal.scaling, -portal.cullableYEnd * portal.scaling);

        newPortal.width = portal.width;
        newPortal.height = portal.height;
        newPortal.axisW = new Vec3(1, 0, 0);
        newPortal.axisH = new Vec3(0, 1, 0);

        PortalManipulation.rotatePortalBody(newPortal, DQuaternion.fromMcQuaternion(quat.toMcQuaternion()));

        return newPortal;
    }

    public static BOTIPortalEntity createPortal(Level level, Vec3 origin, Vec3 destination, ResourceKey<Level> destinationLvl, DQuaternion quat) {
        BOTIPortalEntity portal = ImmersivePortals.BOTI_PORTAL.get().create(level);
        portal.setTardisId(UUID.fromString(destinationLvl.location().getPath()));
        portal.setOriginPos(origin);
        portal.setDestinationDimension(destinationLvl);
        portal.setDestination(destination);
        portal.setOrientationAndSize(
                new Vec3(1, 0, 0), // axisW
                new Vec3(0, 1, 0), // axisH
                1, // width
                2.175 // height
        );
        PortalManipulation.rotatePortalBody(portal, DQuaternion.fromMcQuaternion(quat.toMcQuaternion()));

        return portal;
    }

}
