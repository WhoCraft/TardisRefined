package whocraft.tardis_refined.compat.portals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import qouteall.imm_ptl.core.api.PortalAPI;
import qouteall.imm_ptl.core.portal.PortalManipulation;
import qouteall.q_misc_util.MiscHelper;
import qouteall.q_misc_util.api.DimensionAPI;
import qouteall.q_misc_util.my_util.DQuaternion;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.api.event.EventResult;
import whocraft.tardis_refined.api.event.TardisCommonEvents;
import whocraft.tardis_refined.common.blockentity.door.TardisInternalDoor;
import whocraft.tardis_refined.common.blockentity.shell.ExteriorShell;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.AestheticHandler;
import whocraft.tardis_refined.common.tardis.manager.TardisInteriorManager;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.compat.ModCompatChecker;
import whocraft.tardis_refined.compat.valkyrienskies.VSHelper;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRDimensionTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

import static whocraft.tardis_refined.registry.TREntityRegistry.ENTITY_TYPES;
import static whocraft.tardis_refined.registry.TREntityRegistry.registerStatic;


public class ImmersivePortals {

    private static final Map<UUID, PortalEntry> EXISTING_PORTALS = new HashMap<>();
    // First 4 is exterior, last 4 is door offsets, in order of East, South, West, North
    private static final Map<ResourceLocation, PortalOffets> THEME_OFFSETS = new HashMap<>();
    public static RegistrySupplier<EntityType<BotiPortalEntity>> BOTI_PORTAL = null;
    public static Logger LOGGER = LogManager.getLogger("TardisRefined/ImmersivePortals");

    public static void clearPortalCache() {
        EXISTING_PORTALS.clear();
    }

    public static boolean doPortalsExistForTardis(ResourceKey<Level> dim) {
        try {
            return doPortalsExistForTardis(getUUIDForTARDIS(dim));
        } catch (IllegalArgumentException ignored) { // Thrown when not a valid UUID.
            return false;
        }
    }

    public static boolean doPortalsExistForTardis(UUID uuid) {
        return EXISTING_PORTALS.containsKey(uuid);
    }

    public static boolean isTeleportingPortalPresent(ResourceKey<Level> dim) {
        try {
            return isTeleportingPortalPresent(getUUIDForTARDIS(dim));
        } catch (IllegalArgumentException ignored) { // Thrown when not a valid UUID.
            return false;
        }
    }

    public static boolean isTeleportingPortalPresent(UUID uuid) {
        if (doPortalsExistForTardis(uuid)) {
            PortalEntry portal = getPortalsForTardis(uuid);
            return portal.getInternalPortal().teleportable && portal.getShellPortal().teleportable;
        } else {
            return false;
        }
    }

    public static PortalEntry getPortalsForTardis(UUID uuid) {
        return EXISTING_PORTALS.get(uuid);
    }

    public static UUID getUUIDForTARDIS(ResourceKey<Level> tardisID) {
        return UUID.fromString(tardisID.location().getPath());
    }

    public static ServerLevel createDimension(Level level, ResourceKey<Level> id) {
        MinecraftServer server = MiscHelper.getServer();
        if (server == null) return null;
        ServerLevel world = server.levelKeys().contains(id) ? server.getLevel(id) : null;
        if (world != null) return world;
        BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory = DimensionHandler::formLevelStem;
        final ResourceKey<LevelStem> dimensionKey = ResourceKey.create(Registries.LEVEL_STEM, id.location());
        DimensionAPI.addDimensionDynamically(id.location(), dimensionFactory.apply(server, dimensionKey));
        // TODO, Is this important? DimensionAPI.saveDimensionConfiguration(id);

        world = server.getLevel(id);
        DimensionHandler.addDimension(world.dimension());
        return world;
    }

    public static void init() {
        if (!ModCompatChecker.immersivePortals()) return; // If the mod isn't detected, we shouldn't do anything

        LOGGER.info("Immersive Portals Detected - Setting up Compatibility");

        // Register BOTI Portal here, as doing it in main code would make it a hard dependency
        BOTI_PORTAL = ENTITY_TYPES.register("boti_portal", () -> registerStatic(BotiPortalEntity::new, MobCategory.MISC, 1, 1, 96, 20, "boti_portal"));

        setupEvents();
    }

    public static void postInit() {
        // Set up for Portals!
        setupPortalsForShellThemes();
    }

    private static void setupEvents() {


        // Create Portals when Doors are opened
        TardisCommonEvents.DOOR_OPENED_EVENT.register(ImmersivePortals::createPortals);

        // Destroy Portals when Doors are closed
        TardisCommonEvents.DOOR_CLOSED_EVENT.register(ImmersivePortals::destroyPortals);

        // Destroy & Create Portals when a shell is changed
        TardisCommonEvents.SHELL_CHANGE_EVENT.register((operator, theme, isSetupTardis) -> {
            TardisInternalDoor internalDoor = operator.getInternalDoor();
            ImmersivePortals.destroyPortals(operator);
            if (internalDoor != null) {
                if (internalDoor.isOpen()) {
                    ImmersivePortals.createPortals(operator);
                }
            }
        });

        TardisCommonEvents.DESKTOP_CHANGE_EVENT.register(ImmersivePortals::destroyPortals);

        TardisCommonEvents.TAKE_OFF.register((tardisLevelOperator, level, pos) -> {
            ImmersivePortals.destroyPortals(tardisLevelOperator);
            return EventResult.pass();
        });
    }

    private static void setupPortalsForShellThemes() {

        THEME_OFFSETS.clear();

        PortalOffets ttCapsule = new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.499, 0.3125, 0),
                new Vec3(0, 0.3125, 0.499), new Vec3(-0.499, 0.3125, 0), new Vec3(0, 0.3125, -0.499)), new PortalOffets.OffsetData(
                new Vec3(-1.375, 0.125, 0), new Vec3(0, 0.125, -1.375),
                new Vec3(1.375, 0.125, 0), new Vec3(0, 0.125, 1.375)), new Vec2(1, 2));

        registerThemePortal(ShellTheme.FACTORY.get(), ttCapsule);

        registerThemePortal(ShellTheme.HALF_BAKED.get(), ttCapsule);


        registerThemePortal(ShellTheme.POLICE_BOX.get(),
                new PortalOffets(new PortalOffets.OffsetData(
                        new Vec3(0.6, 0.125, 0),
                        new Vec3(0, 0.125, 0.6),
                        new Vec3(-0.6, 0.125, 0),
                        new Vec3(0, 0.125, -0.6)),

                        new PortalOffets.OffsetData(
                                new Vec3(-1.425, 0.0625, 0),
                                new Vec3(0, 0.0625, -1.425),
                                new Vec3(1.425, 0.0625, 0),
                                new Vec3(0, 0.0625, 1.700)), new Vec2(1, 2)));

        registerThemePortal(ShellTheme.PHONE_BOOTH.get(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.5, 0.125, 0),
                new Vec3(0, 0.125, 0.5), new Vec3(-0.5, 0.125, 0), new Vec3(0, 0.125, -0.5)),
                new PortalOffets.OffsetData(new Vec3(-1.435, 0, 0), new Vec3(0, 0, -1.435),
                        new Vec3(1.435, 0, 0), new Vec3(0, 0, 1.435)), new Vec2(1, 2)));

        registerThemePortal(ShellTheme.MYSTIC.get(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.5, 0.1875, 0),
                new Vec3(0, 0.1875, 0.5), new Vec3(-0.5, 0.1875, 0), new Vec3(0, 0.1875, -0.5)),
                new PortalOffets.OffsetData(new Vec3(-1.425, 0.0625, 0), new Vec3(0, 0.0625, -1.425),
                        new Vec3(1.425, 0.0625, 0), new Vec3(0, 0.0625, 1.425)), new Vec2(1, 2)));

        registerThemePortal(ShellTheme.VENDING.get(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.57, 0, 0),
                new Vec3(0, 0, 0.57), new Vec3(-0.57, 0, 0), new Vec3(0, 0, -0.57)), new PortalOffets.OffsetData(
                new Vec3(-1.455, 0, 0), new Vec3(0, 0, -1.455),
                new Vec3(1.455, 0, 0), new Vec3(0, 0, 1.455)), new Vec2(1, 2.175f)));

        registerThemePortal(ShellTheme.PRESENT.get(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.57, 0, 0),
                new Vec3(0, 0, 0.57), new Vec3(-0.57, 0, 0), new Vec3(0, 0, -0.57)), new PortalOffets.OffsetData(
                new Vec3(-1.455, 0, 0), new Vec3(0, 0, -1.455),
                new Vec3(1.455, 0, 0), new Vec3(0, 0, 1.455)), new Vec2(1, 2.175f)));


        registerThemePortal(ShellTheme.DRIFTER.get(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.61, 0.125, 0),
                new Vec3(0, 0.125, 0.61), new Vec3(-0.61, 0.125, 0), new Vec3(0, 0.125, -0.61)), new PortalOffets.OffsetData(
                new Vec3(-1.425, 0.0625, 0), new Vec3(0, 0.0625, -1.425),
                new Vec3(1.425, 0.0625, 0), new Vec3(0, 0.0625, 1.425)), new Vec2(1, 2)));

        registerThemePortal(ShellTheme.HIEROGLYPH.get(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.5, 0, 0),
                new Vec3(0, 0, 0.5), new Vec3(-0.5, 0, 0), new Vec3(0, 0, -0.5)), new PortalOffets.OffsetData(
                new Vec3(-1.33, 0, 0), new Vec3(0, 0, -1.33),
                new Vec3(1.33, 0, 0), new Vec3(0, 0, 1.33)), new Vec2(1, 2.25f)));


        registerThemePortal(ShellTheme.GROENING.get(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.5, 0, 0),
                new Vec3(0, 0, 0.5), new Vec3(-0.5, 0, 0), new Vec3(0, 0, -0.5)), new PortalOffets.OffsetData(
                new Vec3(-1.33, 0, 0), new Vec3(0, 0, -1.33),
                new Vec3(1.33, 0, 0), new Vec3(0, 0, 1.33)), new Vec2(1, 2.25f)));

        registerThemePortal(ShellTheme.BIG_BEN.get(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.46, 0, 0),
                new Vec3(0, 0, 0.46), new Vec3(-0.46, 0, 0), new Vec3(0, 0, -0.46)), new PortalOffets.OffsetData(
                new Vec3(-1.3, 0, 0), new Vec3(0, 0, -1.3),
                new Vec3(1.3, 0, 0), new Vec3(0, 0, 1.3)), new Vec2(1, 2)));

        registerThemePortal(ShellTheme.NUKA.get(),
                new PortalOffets(
                        new PortalOffets.OffsetData(new Vec3(0.65, 0.375, 0), new Vec3(0, 0.375, 0.65), new Vec3(-0.65, 0.375, 0), new Vec3(0, 0.375, -0.65)),
                        new PortalOffets.OffsetData(new Vec3(-1.33, 0, 0), new Vec3(0, 0, -1.33), new Vec3(1.33, 0, 0), new Vec3(0, 0, 1.33)), new Vec2(1, 2)
                )
        );

        /* TODO: Fix these
        registerShellPortal(ShellTheme.PAGODA.get(),
                new PortalOffets(
                        new PortalOffets.OffsetData(new Vec3(0.65, 0.09375, 0), new Vec3(0, 0.09375, 0.51), new Vec3(-0.65, 0.09375, 0), new Vec3(0, 0.09375, -0.51)),
                        new PortalOffets.OffsetData( new Vec3(-1.33, 0.09375, 0), new Vec3(0, 0.09375, -1.33), new Vec3(1.33, 0.09375, 0), new Vec3(0, 0.09375, 1.33))
                )
        );
        registerShellPortal(ShellTheme.GROENING.get(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.5, 0.09375, 0),
                new Vec3(0, 0.09375, 0.5), new Vec3(-0.5, 0.09375, 0), new Vec3(0, 0.09375, -0.5)), new PortalOffets.OffsetData(
                new Vec3(-1.33, 0.09375, 0), new Vec3(0, 0.09375, -1.33),
                new Vec3(1.33, 0.09375, 0), new Vec3(0, 0.09375, 1.33))));
         */


        // First 4 is exterior, last 4 is door offsets, in order of East, South, West, North
        registerThemePortal(ShellTheme.PORTALOO.get(), new PortalOffets(new PortalOffets.OffsetData(new Vec3(0.51, 0.125, 0),
                new Vec3(0, 0.125, 0.51), new Vec3(-0.51, 0.125, 0), new Vec3(0, 0.125, -0.51)),
                new PortalOffets.OffsetData(
                        new Vec3(-1.425, 0, 0), new Vec3(0, 0, -1.425),
                        new Vec3(1.425, 0, 0), new Vec3(0, 0, 1.425)), new Vec2(1, 2)));


        detectMissingSetup();
    }


    private static void detectMissingSetup() {
        for (ResourceLocation value : ShellTheme.SHELL_THEME_DEFERRED_REGISTRY.keySet()) {
            if (!isShellThemeSupported(value) && !value.equals(ShellTheme.getKey(ShellTheme.BRIEFCASE.get()))) {
                LOGGER.info("{} shell has not been setup for ImmersivePortals", value);
            }
        }
    }


    public static void registerThemePortal(ShellTheme shellTheme, PortalOffets portalOffets) {
        THEME_OFFSETS.put(ShellTheme.getKey(shellTheme), portalOffets);
    }

    public static boolean isShellThemeSupported(ResourceLocation shellTheme) {
        return THEME_OFFSETS.containsKey(shellTheme);
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

    public record PositionHolder(Vec3 pos, Vec3 axisW, Vec3 axisH, boolean airship) {
        public DQuaternion getQuaternion() {
            return DQuaternion.fromFacingVecs(axisW, axisH);
        }
    }

    private static PositionHolder getPortalPosition(Level level, BlockPos blockPos, Direction direction, Vec3 doorPos) {
        boolean airship = false;
        Vec3 axisW = Vec3.atLowerCornerOf(direction.getCounterClockWise().getNormal());

        Vec3 axisH = Vec3.atLowerCornerOf(Direction.UP.getNormal());

        if (ModCompatChecker.valkyrienSkies()) {
            axisW = VSHelper.toWorldRotation(level, blockPos, axisW);
            axisH = VSHelper.toWorldRotation(level, blockPos, axisH);
            doorPos = VSHelper.toWorldPosition(level, blockPos, doorPos);
            airship = VSHelper.isBlockOnShip(level, blockPos);
        }
        return new PositionHolder(doorPos, axisW, axisH, airship);
    }

    private static Vec3 getPortalPosForBlockPos(BlockPos pos, Direction direction, PortalOffets.OffsetData offset) {
        Vec3 returnPos = new Vec3(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);

        return switch (direction) {
            case EAST -> returnPos.add(offset.east());
            case SOUTH -> returnPos.add(offset.south());
            case WEST -> returnPos.add(offset.west());
            case NORTH -> returnPos.add(offset.north());
            default -> throw new IllegalArgumentException("Why is the TARDIS facing up/down?");
        };
    }

    private static boolean updatePortalPosition(PositionHolder position, BotiPortalEntity portal, BotiPortalEntity otherPortal) {
        boolean updated = false;
        if (!portal.axisH.equals(position.axisH) || !portal.axisW.equals(position.axisW)) {
            portal.setOrientation(position.axisW, position.axisH);
            PortalManipulation.adjustRotationToConnect(portal, otherPortal);
            updated = true;
        }
        if (!portal.getOriginPos().equals(position.pos)) {
            portal.setOriginPos(position.pos);
            updated = true;
        }
        if (!otherPortal.getDestPos().equals(position.pos)) {
            otherPortal.setDestination(position.pos);
            updated = true;
        }

        return updated;
    }

    private static <T extends Entity> T reloadEntityIfLoaded(T entity) {
        // Portal will get unloaded at some point.
        // If we notice that, and the chunk where it's supposed to be is loaded, we try getting that one instead.
        if (
                entity.isRemoved() && entity.level() instanceof ServerLevel sl &&
                sl.areEntitiesLoaded(ChunkPos.asLong(entity.blockPosition()))
        ) {
            Entity newEntity = sl.getEntity(entity.getUUID());
            if (newEntity != null && newEntity.getClass() == entity.getClass()) {
                //noinspection unchecked
                return (T) newEntity;
            }
        }
        return entity;
    }

    public static void updatePortalPositions(TardisLevelOperator operator) {
        ResourceLocation theme = operator.getAestheticHandler().getShellTheme();

        if (!isShellThemeSupported(theme)) {
            destroyPortals(operator);
            return;
        }
        PortalOffets themeData = THEME_OFFSETS.get(theme);

        UUID dimId = getUUIDForTARDIS(operator.getLevelKey());
        if (!doPortalsExistForTardis(dimId)) {
            return;
        }
        PortalEntry portal = getPortalsForTardis(dimId);

        BotiPortalEntity interiorPortal = reloadEntityIfLoaded(portal.getInternalPortal());
        BotiPortalEntity exteriorPortal = reloadEntityIfLoaded(portal.getShellPortal());

        if (interiorPortal != portal.getInternalPortal() || exteriorPortal != portal.getShellPortal()) {
            EXISTING_PORTALS.put(dimId, new PortalEntry(interiorPortal, exteriorPortal, ShellTheme.getShellTheme(theme), dimId));
        }

        TardisInternalDoor door = operator.getInternalDoor();
        PositionHolder interiorPos = getPortalPosition(
                operator.getLevel(), door.getDoorPosition(), door.getTeleportRotation(),
                getPortalPosForBlockPos(door.getTeleportPosition(), door.getTeleportRotation(), themeData.intDoor())
        );
        boolean result = updatePortalPosition(interiorPos, interiorPortal, exteriorPortal);

        TardisNavLocation location = operator.getPilotingManager().getCurrentLocation();
        PositionHolder exteriorPos = getPortalPosition(
                location.getLevel(), location.getPosition(), location.getDirection(),
                getPortalPosForBlockPos(location.getPosition(), location.getDirection(), themeData.shell())
        );
        result |= updatePortalPosition(exteriorPos, exteriorPortal, interiorPortal);

        if (result) {
            exteriorPortal.reloadAndSyncToClient();
            interiorPortal.reloadAndSyncToClient();
        }
    }

    private static void setAllowTeleportation(BotiPortalEntity portal, boolean isOnAirship) {
        if ((isOnAirship ? TRConfig.SERVER.IP_TELEPORTATION_VS.get() : TRConfig.SERVER.IP_TELEPORTATION.get()) != TRConfig.Server.IPTeleportationMode.PORTAL) {
            portal.setTeleportable(false);
        }
    }

    public static void createPortals(TardisLevelOperator operator) {

        if(operator.getPilotingManager().isInFlight()){
            destroyPortals(operator);
            return;
        }

        // Just for debugging editing values
        if (!Platform.isProduction()) {
            setupPortalsForShellThemes();
        }

        destroyPortals(operator);
        UUID dimId = getUUIDForTARDIS(operator.getLevelKey());

        AestheticHandler aestheticsHandler = operator.getAestheticHandler();
        TardisInteriorManager interiorManager = operator.getInteriorManager();

        ResourceLocation theme = aestheticsHandler.getShellTheme();
        TardisInternalDoor door = operator.getInternalDoor();
        TardisPilotingManager pilotingManager = operator.getPilotingManager();

        if (!isShellThemeSupported(theme)) {
            destroyPortals(operator); //we're going to make sure.
            return;
        }

        boolean open = door != null && door.isOpen();
        TardisNavLocation location = pilotingManager.getCurrentLocation();

        // If exterior is loaded we use it to check if door is open as well.
        // The state we get for the interior door might be an outdated unloaded block entity.
        if (location.getLevel().areEntitiesLoaded(ChunkPos.asLong(location.getPosition()))) {
            BlockEntity entity = location.getLevel().getBlockEntity(location.getPosition());
            if (entity instanceof ExteriorShell shell && shell.isOpen()) {
                open = true;
            }
        }

        if (interiorManager.isCave() || !open || !operator.isTardisReady() || EXISTING_PORTALS.get(dimId) != null || door == null) {
            return;
        }

        theme = operator.getAestheticHandler().getShellTheme();
        PortalOffets themeData = THEME_OFFSETS.get(theme);
        PortalOffets.OffsetData interiorDoor = themeData.intDoor();
        PortalOffets.OffsetData exteriorDoor = themeData.shell();

        BlockPos entryPositionBPos = door.getTeleportPosition();
        Vec3 entryPosition = getPortalPosForBlockPos(entryPositionBPos, door.getTeleportRotation(), interiorDoor);
        BlockPos exteriorEntryBPos = location.getPosition();
        Vec3 exteriorEntryPosition = getPortalPosForBlockPos(exteriorEntryBPos, location.getDirection(), exteriorDoor);

        Level operatorLevel = operator.getLevel();

        PositionHolder ext = getPortalPosition(location.getLevel(), location.getPosition(), location.getDirection(), exteriorEntryPosition);
        PositionHolder interior = getPortalPosition(operatorLevel, door.getDoorPosition(), door.getTeleportRotation(), entryPosition);
        exteriorEntryPosition = ext.pos;
        entryPosition = interior.pos;
        boolean airship = ext.airship || interior.airship;

        DQuaternion extQuat = ext.getQuaternion();
        DQuaternion interiorQuat = interior.getQuaternion();

        BotiPortalEntity exteriorPortal = createPortal(location.getLevel(), exteriorEntryPosition, entryPosition, operatorLevel.dimension(), extQuat);
        BotiPortalEntity interiorPortal = createDestPortal(exteriorPortal, entryPosition, ImmersivePortals.BOTI_PORTAL.get(), interiorQuat);

        exteriorPortal.setShellTheme(ShellTheme.getShellTheme(theme));
        interiorPortal.setShellTheme(ShellTheme.getShellTheme(theme));

        updatePortalEntry(operator, dimId, interiorPortal, exteriorPortal, theme);

        PortalManipulation.adjustRotationToConnect(exteriorPortal, interiorPortal);
        exteriorPortal.setInteractable(false);
        interiorPortal.setInteractable(false);
        interiorPortal.setValid(true);
        exteriorPortal.setValid(true);
        setAllowTeleportation(exteriorPortal, airship);
        setAllowTeleportation(interiorPortal, airship);

        CompoundTag tag = new CompoundTag();
        tag.putBoolean("adjustPositionAfterTeleport", false);

        exteriorPortal.updatePortalFromNbt(tag);

        interiorPortal.updatePortalFromNbt(tag);

        exteriorPortal.level().addFreshEntity(exteriorPortal);
        interiorPortal.level().addFreshEntity(interiorPortal);

        exteriorPortal.reloadPortal();
        interiorPortal.reloadPortal();
    }

    private static void updatePortalEntry(TardisLevelOperator operator, UUID dimId, BotiPortalEntity interiorPortal, BotiPortalEntity exteriorPortal, ResourceLocation theme) {
        destroyPortals(operator);
        EXISTING_PORTALS.put(dimId, new PortalEntry(interiorPortal, exteriorPortal, ShellTheme.getShellTheme(theme), dimId));
    }

    public static void destroyPortals(TardisLevelOperator operator) {
        UUID tardisID = UUID.fromString(operator.getLevel().dimension().location().getPath());
        PortalEntry portalEntry = EXISTING_PORTALS.get(tardisID);
        if (portalEntry == null) {
            return;
        }

        PortalManipulation.removeConnectedPortals(portalEntry.getInternalPortal(), portal -> {

        });

        PortalManipulation.removeConnectedPortals(portalEntry.getShellPortal(), portal -> {

        });

        portalEntry.getInternalPortal().setValid(false);
        portalEntry.getInternalPortal().kill();

        portalEntry.getShellPortal().setValid(false);
        portalEntry.getShellPortal().kill();

        EXISTING_PORTALS.remove(tardisID);
    }

    public static BotiPortalEntity createDestPortal(BotiPortalEntity portal, Vec3 doorPos, EntityType<BotiPortalEntity> entityType, DQuaternion quat) {
        Level world = portal.getDestinationWorld();

        BotiPortalEntity newPortal = entityType.create(world);
        newPortal.setTardisId(UUID.fromString(world.dimension().location().getPath()));
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

    public static BotiPortalEntity createPortal(Level level, Vec3 origin, Vec3 destination, ResourceKey<Level> destinationLvl, DQuaternion quat) {
        BotiPortalEntity portal = ImmersivePortals.BOTI_PORTAL.get().create(level);
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

    public static void teleportViaIp(Entity pEntity, ServerLevel destination, double pX, double pY, double pZ) {
        PortalAPI.teleportEntity(pEntity, destination, new Vec3(pX, pY, pZ));
    }

    public static void onServerStopping(MinecraftServer server) {
        EXISTING_PORTALS.forEach((uuid, portalEntry) -> {
            portalEntry.getShellPortal().setValid(false);
            portalEntry.getInternalPortal().setValid(false);
            portalEntry.getShellPortal().kill();
            portalEntry.getInternalPortal().kill();
        });

        EXISTING_PORTALS.clear();
    }
}
