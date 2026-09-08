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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;

import static whocraft.tardis_refined.registry.TREntityRegistry.ENTITY_TYPES;
import static whocraft.tardis_refined.registry.TREntityRegistry.registerStatic;

public class ImmersivePortals {

    private static final Map<UUID, PortalEntry> EXISTING_PORTALS = new HashMap<>();

    public enum PortalPart {
        SHELL,
        INTERIOR
    }

    private record ThemeOffsetEntry(Vec3 offset, Vec2 size) {
    }

     private static final Map<ResourceLocation, ThemeOffsetEntry> SHELL_OFFSETS = new HashMap<>();
    private static final Map<ResourceLocation, ThemeOffsetEntry> INTERIOR_OFFSETS = new HashMap<>();

    private static final Map<ResourceLocation, PortalOffets> THEME_OFFSETS = new HashMap<>();

    private static final double SHELL_PORTAL_BACK_OFFSET = 2 * 0.0625;

    public static RegistrySupplier<EntityType<BotiPortalEntity>> BOTI_PORTAL = null;

    public static Logger LOGGER = LogManager.getLogger("TardisRefined/ImmersivePortals");

    public static void clearPortalCache() {
        EXISTING_PORTALS.clear();
    }

    public static boolean doPortalsExistForTardis(ResourceKey<Level> dim) {
        try {
            if (dim == null) return false;
            return doPortalsExistForTardis(getUUIDForTARDIS(dim));
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

    public static boolean doPortalsExistForTardis(UUID uuid) {
        return EXISTING_PORTALS.containsKey(uuid);
    }

    public static boolean isTeleportingPortalPresent(ResourceKey<Level> dim) {
        try {
            return isTeleportingPortalPresent(getUUIDForTARDIS(dim));
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

    public static boolean isTeleportingPortalPresent(UUID uuid) {
        if (doPortalsExistForTardis(uuid)) {
            PortalEntry portal = getPortalsForTardis(uuid);
            return portal.getInternalPortal().teleportable
                    && portal.getShellPortal().teleportable;
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

        ServerLevel world = server.levelKeys().contains(id)
                ? server.getLevel(id)
                : null;

        if (world != null) return world;

        BiFunction<MinecraftServer, ResourceKey<LevelStem>, LevelStem> dimensionFactory =
                DimensionHandler::formLevelStem;

        final ResourceKey<LevelStem> dimensionKey =
                ResourceKey.create(Registries.LEVEL_STEM, id.location());

        DimensionAPI.addDimensionDynamically(
                id.location(),
                dimensionFactory.apply(server, dimensionKey)
        );

        world = server.getLevel(id);

        DimensionHandler.addDimension(world.dimension());

        return world;
    }

    public static void init() {
        if (!ModCompatChecker.immersivePortals()) return;

        LOGGER.info("Immersive Portals Detected - Setting up Compatibility");

        BOTI_PORTAL = ENTITY_TYPES.register(
                "boti_portal",
                () -> registerStatic(
                        BotiPortalEntity::new,
                        MobCategory.MISC,
                        1,
                        1,
                        96,
                        20,
                        "boti_portal"
                )
        );

        setupEvents();
    }

    public static void postInit() {
        setupPortalsForShellThemes();
    }

    private static void setupEvents() {

        TardisCommonEvents.DOOR_OPENED_EVENT.register(
                ImmersivePortals::createPortals
        );

        TardisCommonEvents.DOOR_CLOSED_EVENT.register(
                ImmersivePortals::destroyPortals
        );

        TardisCommonEvents.SHELL_CHANGE_EVENT.register(
                (operator, theme, isSetupTardis) -> {
                    TardisInternalDoor internalDoor = operator.getInternalDoor();

                    ImmersivePortals.destroyPortals(operator);

                    if (internalDoor != null && internalDoor.isOpen()) {
                        ImmersivePortals.createPortals(operator);
                    }
                }
        );

        TardisCommonEvents.DESKTOP_CHANGE_EVENT.register(
                ImmersivePortals::destroyPortals
        );

        TardisCommonEvents.TAKE_OFF.register(
                (tardisLevelOperator, level, pos) -> {
                    ImmersivePortals.destroyPortals(tardisLevelOperator);
                    return EventResult.pass();
                }
        );
    }

      private static void setupPortalsForShellThemes() {

        SHELL_OFFSETS.clear();
        INTERIOR_OFFSETS.clear();

        // Done
        for (RegistrySupplier<ShellTheme> theme : List.of(ShellTheme.FACTORY, ShellTheme.HALF_BAKED)) {
            registerThemeOffset(theme.get(), PortalPart.SHELL, new Vec3(0, 0.3125, -0.499), new Vec2(1, 2));
            registerThemeOffset(theme.get(), PortalPart.INTERIOR, new Vec3(0, 0.125, 1.375), new Vec2(1, 2));
        }

        // Done
        registerThemeOffset(ShellTheme.GROWTH.get(), PortalPart.SHELL, new Vec3(0, 0, -0.725), new Vec2(0.5625f, 2.0000f));
        registerThemeOffset(ShellTheme.GROWTH.get(), PortalPart.INTERIOR, new Vec3(0, 0, 1.4), new Vec2(1, 2));

        // Done
        registerThemeOffset(ShellTheme.POLICE_BOX.get(), PortalPart.SHELL, new Vec3(0, 0, -0.7875), new Vec2(1.4F, 2.5F));
        registerThemeOffset(ShellTheme.POLICE_BOX.get(), PortalPart.INTERIOR, new Vec3(0, 0.0625, 1.425), new Vec2(1, 2));

        // Done
        registerThemeOffset(ShellTheme.PHONE_BOOTH.get(), PortalPart.SHELL, new Vec3(0, 0.1875, -0.6625), new Vec2(0.8750f, 2.1875f));
        registerThemeOffset(ShellTheme.PHONE_BOOTH.get(), PortalPart.INTERIOR, new Vec3(0, 0.125, 1.425), new Vec2(1.0000f, 2.3125f));

        // Done
        registerThemeOffset(ShellTheme.MYSTIC.get(), PortalPart.SHELL, new Vec3(0, 0.1875, -0.6625), new Vec2(0.9375f, 2.0625f));
        registerThemeOffset(ShellTheme.MYSTIC.get(), PortalPart.INTERIOR, new Vec3(0, 0.0625, 1.425), new Vec2(1.0000f, 2.1875f));

        // Done
        registerThemeOffset(ShellTheme.VENDING.get(), PortalPart.SHELL, new Vec3(0, 0.1875, -0.4625), new Vec2(0.9375f, 2.0625f));
        registerThemeOffset(ShellTheme.VENDING.get(), PortalPart.INTERIOR, new Vec3(0, 0.0625, 1.425), new Vec2(1.0000f, 2.1875f));

        // Done
        registerThemeOffset(ShellTheme.PRESENT.get(), PortalPart.SHELL, new Vec3(0, 0.125, -0.6625), new Vec2(1, 2.3f));
        registerThemeOffset(ShellTheme.PRESENT.get(), PortalPart.INTERIOR, new Vec3(0, 0.125, 1.455), new Vec2(1, 2.3f));

        // Done
        registerThemeOffset(ShellTheme.DRIFTER.get(), PortalPart.SHELL, new Vec3(0, 0.125, -0.61), new Vec2(1, 2));
        registerThemeOffset(ShellTheme.DRIFTER.get(), PortalPart.INTERIOR, new Vec3(0, 0.0625, 1.425), new Vec2(1, 2));

        // Done
        registerThemeOffset(ShellTheme.HIEROGLYPH.get(), PortalPart.SHELL, new Vec3(0, 0, -0.6625), new Vec2(1, 2.25f));
        registerThemeOffset(ShellTheme.HIEROGLYPH.get(), PortalPart.INTERIOR, new Vec3(0, 0, 1.33), new Vec2(1, 2.25f));

        // Done
        registerThemeOffset(ShellTheme.GROENING.get(), PortalPart.SHELL, new Vec3(0, 0, -0.6625), new Vec2(1, 2.25f));
        registerThemeOffset(ShellTheme.GROENING.get(), PortalPart.INTERIOR, new Vec3(0, 0, 1.33), new Vec2(1, 2.25f));

        // Done
        registerThemeOffset(ShellTheme.BIG_BEN.get(), PortalPart.SHELL, new Vec3(0, 0, -0.725), new Vec2(0.8750f, 2.1250f));
        registerThemeOffset(ShellTheme.BIG_BEN.get(), PortalPart.INTERIOR, new Vec3(0, 0.1, 1.36), new Vec2(0.8750f, 2.1875f));

        // Done
        registerThemeOffset(ShellTheme.NUKA.get(), PortalPart.SHELL, new Vec3(0.0000, 0.375, -0.725), new Vec2(1.0625f, 2.0000f));
        registerThemeOffset(ShellTheme.NUKA.get(), PortalPart.INTERIOR, new Vec3(0, 0, 1.33), new Vec2(1, 2));

        // Done
        registerThemeOffset(ShellTheme.PORTALOO.get(), PortalPart.SHELL, new Vec3(0, 0.0625, -0.51), new Vec2(1, 2));
        registerThemeOffset(ShellTheme.PORTALOO.get(), PortalPart.INTERIOR, new Vec3(0, 0, 1.425), new Vec2(1, 2));

        // Done
        registerThemeOffset(ShellTheme.PAGODA.get(), PortalPart.SHELL, new Vec3(0.0000, 0.0405, -0.725), new Vec2(0.8750f, 2.0000f));
        registerThemeOffset(ShellTheme.PAGODA.get(), PortalPart.INTERIOR, new Vec3(0, -0.03125, 1.26), new Vec2(1, 2));

        // Done
        registerThemeOffset(ShellTheme.LIFT.get(), PortalPart.SHELL, new Vec3(0.0000, 0.0405, -0.6625), new Vec2(1, 2));
        registerThemeOffset(ShellTheme.LIFT.get(), PortalPart.INTERIOR, new Vec3(0, 0, 1.39), new Vec2(1, 2));

        // Done
        registerThemeOffset(ShellTheme.CASTLE.get(), PortalPart.SHELL, new Vec3(0, 0, -0.975), new Vec2(1.0000f, 2.0625f));
        registerThemeOffset(ShellTheme.CASTLE.get(), PortalPart.INTERIOR, new Vec3(0, 0, 1.12), new Vec2(1, 2));

        // Done
        registerThemeOffset(ShellTheme.PATHFINDER.get(), PortalPart.SHELL, new Vec3(0, 0, 0.3025), new Vec2(1.50f, 2.9875f));
        registerThemeOffset(ShellTheme.PATHFINDER.get(), PortalPart.INTERIOR, new Vec3(0, 0, 1.38), new Vec2(1.50f, 2.9875f));

        // Done
        registerThemeOffset(ShellTheme.BRIEFCASE.get(), PortalPart.SHELL, new Vec3(0, 1.0, 0), new Vec2(1, 1));
        registerThemeOffset(ShellTheme.BRIEFCASE.get(), PortalPart.INTERIOR, new Vec3(0, 1.0, 0), new Vec2(1, 1));

        linkThemeOffsets();
        detectMissingSetup();
    }

    public static void registerThemeOffset(ShellTheme shellTheme, PortalPart part, Vec3 northOffset, Vec2 size) {
        mapFor(part).put(ShellTheme.getKey(shellTheme), new ThemeOffsetEntry(northOffset, size));
    }

    public static void registerThemeOffset(ShellTheme shellTheme, PortalPart part, Vec3 northOffset) {
        registerThemeOffset(shellTheme, part, northOffset, null);
    }

    private static Map<ResourceLocation, ThemeOffsetEntry> mapFor(PortalPart part) {
        return switch (part) {
            case SHELL -> SHELL_OFFSETS;
            case INTERIOR -> INTERIOR_OFFSETS;
        };
    }

    private static void linkThemeOffsets() {
        THEME_OFFSETS.clear();

        Set<ResourceLocation> themes = new HashSet<>();
        themes.addAll(SHELL_OFFSETS.keySet());
        themes.addAll(INTERIOR_OFFSETS.keySet());

        for (ResourceLocation theme : themes) {
            ThemeOffsetEntry shell = SHELL_OFFSETS.get(theme);
            ThemeOffsetEntry interior = INTERIOR_OFFSETS.get(theme);

            if (shell == null) {
                LOGGER.warn("Shell theme {} has an INTERIOR portal offset registered but no SHELL offset - portals will not be created for it until both are set up.", theme);
                continue;
            }

            if (interior == null) {
                LOGGER.warn("Shell theme {} has a SHELL portal offset registered but no INTERIOR offset - portals will not be created for it until both are set up.", theme);
                continue;
            }

            Vec2 shellSize = shell.size();
            Vec2 doorSize = interior.size();

            if (shellSize == null && doorSize == null) {
                LOGGER.warn("Shell theme {} has no portal size registered on either SHELL or INTERIOR - defaulting both to 1x2.", theme);
                shellSize = new Vec2(1, 2);
                doorSize = new Vec2(1, 2);
            } else if (shellSize == null) {
                LOGGER.warn("Shell theme {} has no SHELL portal size registered - using its INTERIOR size ({}) for the shell too.", theme, doorSize);
                shellSize = doorSize;
            } else if (doorSize == null) {
                LOGGER.warn("Shell theme {} has no INTERIOR portal size registered - using its SHELL size ({}) for the door too.", theme, shellSize);
                doorSize = shellSize;
            }

            THEME_OFFSETS.put(
                    theme,
                    new PortalOffets(
                            PortalOffets.OffsetData.fromNorth(shell.offset()),
                            PortalOffets.OffsetData.fromNorth(interior.offset()),
                            shellSize,
                            doorSize
                    )
            );
        }
    }

    public static PortalOffets getThemeOffsets(ResourceLocation theme) {
        return THEME_OFFSETS.get(theme);
    }

    public static void setThemeOffsetsOverride(
            ResourceLocation theme,
            PortalOffets offsets
    ) {
        THEME_OFFSETS.put(theme, offsets);
    }

    private static void detectMissingSetup() {
        for (ResourceLocation value :
                ShellTheme.SHELL_THEME_DEFERRED_REGISTRY.keySet()) {

            if (!isShellThemeSupported(value)) {
                LOGGER.info(
                        "{} shell has not been setup for ImmersivePortals",
                        value
                );
            }
        }
    }

    public static void registerThemePortal(
            ShellTheme shellTheme,
            PortalOffets portalOffets
    ) {
        THEME_OFFSETS.put(
                ShellTheme.getKey(shellTheme),
                portalOffets
        );
    }

    public static boolean isShellThemeSupported(
            ResourceLocation shellTheme
    ) {
        return THEME_OFFSETS.containsKey(shellTheme);
    }

    public static boolean onDoorRemoved(
            Level level,
            Player player,
            BlockPos blockPos,
            BlockState blockState,
            BlockEntity blockEntity
    ) {
        if (blockEntity instanceof TardisInternalDoor door) {
            if (level instanceof ServerLevel serverLevel) {
                if (!door.isMainDoor()) {
                    return true;
                }

                if (serverLevel.dimensionTypeId()
                        .equals(TRDimensionTypes.TARDIS)) {

                    TardisLevelOperator.get(serverLevel)
                            .ifPresent(ImmersivePortals::destroyPortals);
                }
            }
        }

        return true;
    }

    public record PositionHolder(
            Vec3 pos,
            Vec3 axisW,
            Vec3 axisH,
            boolean airship
    ) {

        public DQuaternion getQuaternion() {
            return DQuaternion.fromFacingVecs(axisW, axisH);
        }
    }

    private static boolean isAirship(
            Level level,
            BlockPos blockPos
    ) {
        if (ModCompatChecker.valkyrienSkies()) {
            return VSHelper.isBlockOnShip(level, blockPos);
        }

        return false;
    }

    private static PositionHolder getPortalPosition(
            Level level,
            BlockPos blockPos,
            Direction direction,
            Vec3 doorPos
    ) {
        Vec3 axisW =
                Vec3.atLowerCornerOf(
                        direction.getCounterClockWise().getNormal()
                );

        Vec3 axisH =
                Vec3.atLowerCornerOf(Direction.UP.getNormal());

        if (ModCompatChecker.valkyrienSkies()) {
            axisW = VSHelper.toWorldRotation(
                    level,
                    blockPos,
                    axisW
            );

            axisH = VSHelper.toWorldRotation(
                    level,
                    blockPos,
                    axisH
            );

            doorPos = VSHelper.toWorldPosition(
                    level,
                    blockPos,
                    doorPos
            );
        }

        return new PositionHolder(
                doorPos,
                axisW,
                axisH,
                isAirship(level, blockPos)
        );
    }

    private static PositionHolder getHorizontalPortalPosition(
            Level level,
            BlockPos blockPos,
            Vec3 doorPos,
            boolean facingUp
    ) {
        Vec3 axisW = new Vec3(1, 0, 0);

        Vec3 axisH = facingUp
                ? new Vec3(0, 0, -1)
                : new Vec3(0, 0, 1);

        if (ModCompatChecker.valkyrienSkies()) {
            axisW = VSHelper.toWorldRotation(
                    level,
                    blockPos,
                    axisW
            );

            axisH = VSHelper.toWorldRotation(
                    level,
                    blockPos,
                    axisH
            );

            doorPos = VSHelper.toWorldPosition(
                    level,
                    blockPos,
                    doorPos
            );
        }

        return new PositionHolder(
                doorPos,
                axisW,
                axisH,
                isAirship(level, blockPos)
        );
    }

    private static Vec3 getPortalPosForBlockPos(
            BlockPos pos,
            Direction direction,
            PortalOffets.OffsetData offset
    ) {
        Vec3 returnPos =
                new Vec3(
                        pos.getX() + 0.5,
                        pos.getY() + 1,
                        pos.getZ() + 0.5
                );

        return switch (direction) {
            case EAST -> returnPos.add(offset.east());
            case SOUTH -> returnPos.add(offset.south());
            case WEST -> returnPos.add(offset.west());
            case NORTH -> returnPos.add(offset.north());
            default -> throw new IllegalArgumentException(
                    "Why is the TARDIS facing up/down?"
            );
        };
    }

    private static boolean updatePortalPosition(
            PositionHolder position,
            BotiPortalEntity portal,
            BotiPortalEntity otherPortal
    ) {
        boolean updated = false;

        if (!portal.axisH.equals(position.axisH)
                || !portal.axisW.equals(position.axisW)) {

            portal.setOrientation(
                    position.axisW,
                    position.axisH
            );

            PortalManipulation.adjustRotationToConnect(
                    portal,
                    otherPortal
            );

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

        if (
                entity.isRemoved()
                        && entity.level() instanceof ServerLevel sl
                        && sl.areEntitiesLoaded(
                        ChunkPos.asLong(entity.blockPosition())
                )
        ) {
            Entity newEntity =
                    sl.getEntity(entity.getUUID());

            if (
                    newEntity != null
                            && newEntity.getClass() == entity.getClass()
            ) {
                return (T) newEntity;
            }
        }

        return entity;
    }

    public static void onDoorMoved(
            TardisLevelOperator operator
    ) {
        UUID dimId =
                getUUIDForTARDIS(operator.getLevelKey());

        if (!doPortalsExistForTardis(dimId)) {
            return;
        }

        PortalEntry portal =
                getPortalsForTardis(dimId);

        BotiPortalEntity interiorPortal =
                reloadEntityIfLoaded(
                        portal.getInternalPortal()
                );

        BotiPortalEntity exteriorPortal =
                reloadEntityIfLoaded(
                        portal.getShellPortal()
                );

        TardisNavLocation location =
                operator.getPilotingManager()
                        .getCurrentLocation();

        boolean airship =
                isAirship(
                        operator.getLevel(),
                        operator.getInternalDoor()
                                .getDoorPosition()
                )
                        || isAirship(
                        location.getLevel(),
                        location.getPosition()
                );

        setAllowTeleportation(
                interiorPortal,
                airship
        );

        setAllowTeleportation(
                exteriorPortal,
                airship
        );

        updatePortalPositions(operator);
    }

    public static void updatePortalPositions(
            TardisLevelOperator operator
    ) {
        ResourceLocation theme =
                operator.getAestheticHandler()
                        .getShellTheme();

        if (!isShellThemeSupported(theme)) {
            destroyPortals(operator);
            return;
        }

        PortalOffets themeData =
                THEME_OFFSETS.get(theme);

        boolean isBriefcase =
                theme.equals(
                        ShellTheme.getKey(
                                ShellTheme.BRIEFCASE.get()
                        )
                );

        UUID dimId =
                getUUIDForTARDIS(
                        operator.getLevelKey()
                );

        if (!doPortalsExistForTardis(dimId)) {
            return;
        }

        PortalEntry portal =
                getPortalsForTardis(dimId);

        BotiPortalEntity interiorPortal =
                reloadEntityIfLoaded(
                        portal.getInternalPortal()
                );

        BotiPortalEntity exteriorPortal =
                reloadEntityIfLoaded(
                        portal.getShellPortal()
                );

        if (
                interiorPortal != portal.getInternalPortal()
                        || exteriorPortal != portal.getShellPortal()
        ) {
            EXISTING_PORTALS.put(
                    dimId,
                    new PortalEntry(
                            interiorPortal,
                            exteriorPortal,
                            ShellTheme.getShellTheme(theme),
                            dimId
                    )
            );
        }

        TardisInternalDoor door =
                operator.getInternalDoor();

        Vec3 interiorRawPos =
                getPortalPosForBlockPos(
                        door.getTeleportPosition(),
                        door.getTeleportRotation(),
                        themeData.intDoor()
                );

        PositionHolder interiorPos =
                isBriefcase
                        ? getHorizontalPortalPosition(
                        operator.getLevel(),
                        door.getDoorPosition(),
                        interiorRawPos,
                        false
                )
                        : getPortalPosition(
                        operator.getLevel(),
                        door.getDoorPosition(),
                        door.getTeleportRotation(),
                        interiorRawPos
                );

        boolean result =
                updatePortalPosition(
                        interiorPos,
                        interiorPortal,
                        exteriorPortal
                );

        TardisNavLocation location =
                operator.getPilotingManager()
                        .getCurrentLocation();

        Direction shellFacing = location.getDirection();

        Vec3 exteriorRawPos =
                getPortalPosForBlockPos(
                        location.getPosition(),
                        shellFacing,
                        themeData.shell()
                ).subtract(
                        Vec3.atLowerCornerOf(shellFacing.getNormal())
                                .scale(SHELL_PORTAL_BACK_OFFSET)
                );

        PositionHolder exteriorPos =
                isBriefcase
                        ? getHorizontalPortalPosition(
                        location.getLevel(),
                        location.getPosition(),
                        exteriorRawPos,
                        true
                )
                        : getPortalPosition(
                        location.getLevel(),
                        location.getPosition(),
                        shellFacing,
                        exteriorRawPos
                );

        result |= updatePortalPosition(
                exteriorPos,
                exteriorPortal,
                interiorPortal
        );

        if (result) {
            exteriorPortal.reloadAndSyncToClient();
            interiorPortal.reloadAndSyncToClient();
        }
    }

    private static void setAllowTeleportation(
            BotiPortalEntity portal,
            boolean isOnAirship
    ) {
        if (portal.level().isClientSide()) {
            return;
        }

        if (
                (
                        isOnAirship
                                ? TRConfig.SERVER.IP_TELEPORTATION_VS.get()
                                : TRConfig.SERVER.IP_TELEPORTATION.get()
                )
                        != TRConfig.Server.IPTeleportationMode.PORTAL
        ) {
            portal.setTeleportable(false);
        } else {
            portal.setTeleportable(true);
        }

        portal.reloadAndSyncClusterToClientNextTick();
    }

    public static void createPortals(TardisLevelOperator operator) {

        if (operator.getPilotingManager().isInFlight()) {
            destroyPortals(operator);
            return;
        }

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
            destroyPortals(operator);
            return;
        }

        boolean open =
                door != null && door.isOpen();

        TardisNavLocation location =
                pilotingManager.getCurrentLocation();

        if (
                location.getLevel()
                        .areEntitiesLoaded(
                                ChunkPos.asLong(
                                        location.getPosition()
                                )
                        )
        ) {
            BlockEntity entity =
                    location.getLevel()
                            .getBlockEntity(
                                    location.getPosition()
                            );

            if (
                    entity instanceof ExteriorShell shell
                            && shell.isOpen()
            ) {
                open = true;
            }
        }

        if (
                interiorManager.isCave()
                        || !open
                        || !operator.isTardisReady()
                        || EXISTING_PORTALS.get(dimId) != null
                        || door == null
        ) {
            return;
        }

        theme =
                operator.getAestheticHandler()
                        .getShellTheme();

        PortalOffets themeData =
                THEME_OFFSETS.get(theme);

        boolean isBriefcase =
                theme.equals(
                        ShellTheme.getKey(
                                ShellTheme.BRIEFCASE.get()
                        )
                );

        PortalOffets.OffsetData interiorDoor =
                themeData.intDoor();

        PortalOffets.OffsetData exteriorDoor =
                themeData.shell();

        BlockPos entryPositionBPos =
                door.getTeleportPosition();

        /*
         * Calculate the interior portal position directly from the
         * configured TARDIS offset.
         *
         * This position is authoritative and is deliberately retained
         * throughout portal creation.
         */
        Vec3 entryPosition =
                getPortalPosForBlockPos(
                        entryPositionBPos,
                        door.getTeleportRotation(),
                        interiorDoor
                );

        BlockPos exteriorEntryBPos =
                location.getPosition();

        Direction shellFacing = location.getDirection();

        Vec3 exteriorEntryPosition =
                getPortalPosForBlockPos(
                        exteriorEntryBPos,
                        shellFacing,
                        exteriorDoor
                ).subtract(
                        Vec3.atLowerCornerOf(shellFacing.getNormal())
                                .scale(SHELL_PORTAL_BACK_OFFSET)
                );

        Level operatorLevel =
                operator.getLevel();

        PositionHolder ext =
                isBriefcase
                        ? getHorizontalPortalPosition(
                        location.getLevel(),
                        location.getPosition(),
                        exteriorEntryPosition,
                        true
                )
                        : getPortalPosition(
                        location.getLevel(),
                        location.getPosition(),
                        shellFacing,
                        exteriorEntryPosition
                );

        PositionHolder interior =
                isBriefcase
                        ? getHorizontalPortalPosition(
                        operatorLevel,
                        door.getDoorPosition(),
                        entryPosition,
                        false
                )
                        : getPortalPosition(
                        operatorLevel,
                        door.getDoorPosition(),
                        door.getTeleportRotation(),
                        entryPosition
                );

        /*
         * These are the final physical positions that the portals must use.
         */
        exteriorEntryPosition = ext.pos();
        entryPosition = interior.pos();

        boolean airship =
                ext.airship() || interior.airship();

        DQuaternion extQuat =
                ext.getQuaternion();

        DQuaternion interiorQuat =
                interior.getQuaternion();

        BotiPortalEntity exteriorPortal = null;
        BotiPortalEntity interiorPortal = null;

        try {

            exteriorPortal =
                    createPortal(
                            location.getLevel(),
                            exteriorEntryPosition,
                            entryPosition,
                            operatorLevel.dimension(),
                            extQuat,
                            themeData.shellSize()
                    );

            if (exteriorPortal == null) {
                throw new IllegalStateException(
                        "Immersive Portals returned a null exterior portal"
                );
            }

            interiorPortal =
                    createDestPortal(
                            exteriorPortal,
                            entryPosition,
                            ImmersivePortals.BOTI_PORTAL.get(),
                            interiorQuat,
                            themeData.doorSize()
                    );

            if (interiorPortal == null) {
                throw new IllegalStateException(
                        "Immersive Portals returned a null interior portal"
                );
            }

            exteriorPortal.setShellTheme(
                    ShellTheme.getShellTheme(theme)
            );

            interiorPortal.setShellTheme(
                    ShellTheme.getShellTheme(theme)
            );


            PortalManipulation.adjustRotationToConnect(
                    exteriorPortal,
                    interiorPortal
            );

            exteriorPortal.setOriginPos(
                    exteriorEntryPosition
            );

            exteriorPortal.setDestination(
                    entryPosition
            );

            interiorPortal.setOriginPos(
                    entryPosition
            );

            interiorPortal.setDestination(
                    exteriorEntryPosition
            );

            exteriorPortal.setInteractable(false);
            interiorPortal.setInteractable(false);

            interiorPortal.setValid(false);
            exteriorPortal.setValid(false);

            interiorPortal.setTeleportable(false);
            exteriorPortal.setTeleportable(false);

            CompoundTag tag =
                    new CompoundTag();

            tag.putBoolean(
                    "adjustPositionAfterTeleport",
                    false
            );

            exteriorPortal.updatePortalFromNbt(tag);
            interiorPortal.updatePortalFromNbt(tag);

            exteriorPortal.level()
                    .addFreshEntity(exteriorPortal);

            interiorPortal.level()
                    .addFreshEntity(interiorPortal);

            /*
             * Only expose the pair through the cache once both entities
             * have actually been inserted into their worlds.
             */
            updatePortalEntry(
                    operator,
                    dimId,
                    interiorPortal,
                    exteriorPortal,
                    theme
            );

            /*
             * Final validation/activation happens last.
             */
            exteriorPortal.setValid(true);
            interiorPortal.setValid(true);

            setAllowTeleportation(
                    exteriorPortal,
                    airship
            );

            setAllowTeleportation(
                    interiorPortal,
                    airship
            );

            exteriorPortal.reloadPortal();
            interiorPortal.reloadPortal();

        } catch (Exception e) {

            LOGGER.error(
                    "Failed to create Immersive Portals for TARDIS {}",
                    dimId,
                    e
            );

            destroyPortalSafely(
                    interiorPortal,
                    dimId,
                    "creation rollback (internal)"
            );

            destroyPortalSafely(
                    exteriorPortal,
                    dimId,
                    "creation rollback (exterior)"
            );

            EXISTING_PORTALS.remove(dimId);

            MinecraftServer server =
                    MiscHelper.getServer();

            if (server != null) {
                sweepOrphanedPortals(
                        server,
                        dimId
                );
            }
        }
    }

    private static void updatePortalEntry(
            TardisLevelOperator operator,
            UUID dimId,
            BotiPortalEntity interiorPortal,
            BotiPortalEntity exteriorPortal,
            ResourceLocation theme
    ) {
        if (
                interiorPortal == null
                        || exteriorPortal == null
        ) {
            throw new IllegalArgumentException(
                    "Cannot cache a null portal pair"
            );
        }

        EXISTING_PORTALS.put(
                dimId,
                new PortalEntry(
                        interiorPortal,
                        exteriorPortal,
                        ShellTheme.getShellTheme(theme),
                        dimId
                )
        );
    }

    public static void destroyPortals(
            TardisLevelOperator operator
    ) {

        if (
                operator == null
                        || operator.getLevel() == null
        ) {
            LOGGER.warn(
                    "Attempted to destroy TARDIS portals with an invalid operator"
            );

            return;
        }

        UUID tardisID;

        try {
            tardisID =
                    getUUIDForTARDIS(
                            operator.getLevelKey()
                    );
        } catch (IllegalArgumentException e) {

            LOGGER.error(
                    "Could not determine TARDIS UUID while destroying portals",
                    e
            );

            return;
        }

        PortalEntry portalEntry =
                EXISTING_PORTALS.remove(tardisID);

        if (portalEntry != null) {

            destroyPortalSafely(
                    portalEntry.getInternalPortal(),
                    tardisID,
                    "cached internal"
            );

            destroyPortalSafely(
                    portalEntry.getShellPortal(),
                    tardisID,
                    "cached exterior"
            );
        }

        MinecraftServer server =
                MiscHelper.getServer();

        if (server != null) {
            sweepOrphanedPortals(
                    server,
                    tardisID
            );
        }

        EXISTING_PORTALS.remove(tardisID);
    }

    private static void destroyPortalSafely(
            BotiPortalEntity portal,
            UUID tardisID,
            String source
    ) {
        if (portal == null) {
            return;
        }

        try {

            PortalManipulation.removeConnectedPortals(
                    portal,
                    connected -> {
                    }
            );

            portal.setValid(false);
            portal.setTeleportable(false);

            if (!portal.isRemoved()) {
                portal.kill();
            }

        } catch (Exception e) {

            LOGGER.error(
                    "Failed to destroy {} portal for TARDIS {} (entity UUID {})",
                    source,
                    tardisID,
                    portal.getUUID(),
                    e
            );

            try {

                portal.setValid(false);
                portal.setTeleportable(false);
                portal.kill();

            } catch (Exception retryException) {

                LOGGER.error(
                        "Second attempt to destroy {} portal for TARDIS {} also failed",
                        source,
                        tardisID,
                        retryException
                );
            }
        }
    }

    private static void sweepOrphanedPortals(
            MinecraftServer server,
            UUID tardisID
    ) {

        for (ServerLevel level : server.getAllLevels()) {

            try {

                List<BotiPortalEntity> orphanedPortals =
                        new ArrayList<>();

                for (Entity entity : level.getAllEntities()) {

                    if (
                            entity instanceof BotiPortalEntity portal
                                    && tardisID.equals(
                                    portal.getTardisId()
                            )
                    ) {
                        orphanedPortals.add(portal);
                    }
                }

                for (BotiPortalEntity portal : orphanedPortals) {

                    destroyPortalSafely(
                            portal,
                            tardisID,
                            "orphaned"
                    );
                }

            } catch (Exception e) {

                LOGGER.error(
                        "Failed to sweep orphaned portals for TARDIS {} in dimension {}",
                        tardisID,
                        level.dimension().location(),
                        e
                );
            }
        }
    }

    public static BotiPortalEntity createDestPortal(
            BotiPortalEntity portal,
            Vec3 doorPos,
            EntityType<BotiPortalEntity> entityType,
            DQuaternion quat,
            Vec2 size
    ) {

        Level world =
                portal.getDestinationWorld();

        BotiPortalEntity newPortal =
                entityType.create(world);

        newPortal.setTardisId(
                UUID.fromString(
                        world.dimension()
                                .location()
                                .getPath()
                )
        );

        newPortal.dimensionTo =
                portal.level().dimension();

        newPortal.setPos(doorPos);

        newPortal.setDestination(
                portal.getOriginPos()
        );

        newPortal.specificPlayerId =
                portal.specificPlayerId;

         newPortal.width =
                size.x;

        newPortal.height =
                size.y;

        newPortal.axisW =
                new Vec3(1, 0, 0);

        newPortal.axisH =
                new Vec3(0, 1, 0);

        PortalManipulation.rotatePortalBody(
                newPortal,
                DQuaternion.fromMcQuaternion(
                        quat.toMcQuaternion()
                )
        );

        return newPortal;
    }

    public static BotiPortalEntity createPortal(
            Level level,
            Vec3 origin,
            Vec3 destination,
            ResourceKey<Level> destinationLvl,
            DQuaternion quat,
            Vec2 size
    ) {

        BotiPortalEntity portal =
                ImmersivePortals.BOTI_PORTAL
                        .get()
                        .create(level);

        portal.setTardisId(
                UUID.fromString(
                        destinationLvl.location()
                                .getPath()
                )
        );

        portal.setOriginPos(origin);

        portal.setDestinationDimension(
                destinationLvl
        );

        portal.setDestination(
                destination
        );

        portal.setOrientationAndSize(
                new Vec3(1, 0, 0),
                new Vec3(0, 1, 0),
                size.x,
                size.y
        );

        PortalManipulation.rotatePortalBody(
                portal,
                DQuaternion.fromMcQuaternion(
                        quat.toMcQuaternion()
                )
        );

        return portal;
    }

    public static void teleportViaIp(
            Entity pEntity,
            ServerLevel destination,
            double pX,
            double pY,
            double pZ
    ) {
        PortalAPI.teleportEntity(
                pEntity,
                destination,
                new Vec3(pX, pY, pZ)
        );
    }

    public static void onServerStopping(
            MinecraftServer server
    ) {

        if (server == null) {
            EXISTING_PORTALS.clear();
            return;
        }

        List<UUID> cachedIds =
                new ArrayList<>(
                        EXISTING_PORTALS.keySet()
                );

        for (UUID tardisID : cachedIds) {

            PortalEntry entry =
                    EXISTING_PORTALS.remove(tardisID);

            if (entry != null) {

                destroyPortalSafely(
                        entry.getShellPortal(),
                        tardisID,
                        "server shutdown (exterior)"
                );

                destroyPortalSafely(
                        entry.getInternalPortal(),
                        tardisID,
                        "server shutdown (internal)"
                );
            }
        }

        for (ServerLevel level : server.getAllLevels()) {

            try {

                List<BotiPortalEntity> portals =
                        new ArrayList<>();

                for (Entity entity : level.getAllEntities()) {

                    if (entity instanceof BotiPortalEntity portal) {
                        portals.add(portal);
                    }
                }

                for (BotiPortalEntity portal : portals) {

                    UUID tardisID =
                            portal.getTardisId();

                    if (tardisID != null) {

                        destroyPortalSafely(
                                portal,
                                tardisID,
                                "server shutdown sweep"
                        );

                    } else {

                        try {

                            portal.setValid(false);
                            portal.setTeleportable(false);

                            if (!portal.isRemoved()) {
                                portal.kill();
                            }

                        } catch (Exception e) {

                            LOGGER.error(
                                    "Failed to destroy untagged portal during server shutdown",
                                    e
                            );
                        }
                    }
                }

            } catch (Exception e) {

                LOGGER.error(
                        "Failed to sweep portal entities in dimension {} during server shutdown",
                        level.dimension().location(),
                        e
                );
            }
        }

        EXISTING_PORTALS.clear();
    }
}