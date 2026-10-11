package whocraft.tardis_refined.common.tardis;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.common.blockentity.door.TardisInternalDoor;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.blockentity.device.ConsoleConfigurationBlockEntity;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.constants.ResourceConstants;
import whocraft.tardis_refined.constants.TardisDimensionConstants;
import whocraft.tardis_refined.registry.TRBlockRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static whocraft.tardis_refined.common.tardis.manager.TardisInteriorManager.STATIC_CORRIDOR_POSITION;

// Responsible for all the tedious generation of the desktop;
public class TardisArchitectureHandler {

    public static Logger LOGGER = LogManager.getLogger("TardisRefined/TardisArchitectureHandler");

    public static final BlockPos DESKTOP_CENTER_POS = new BlockPos(0, 100, 0);
    public static final BlockPos EYE_OF_HARMONY_PLACEMENT = new BlockPos(991, 41, 31);

    public static String currentArsStage = "one";

    public static void generateDesktop(ServerLevel operator, DesktopTheme theme) {
        LOGGER.debug("Attempting to generate desktop theme: {} for TARDIS.", theme.getIdentifier());

        Optional<StructureTemplate> structureNBT = operator.getLevel().getStructureTemplateManager().get(theme.getStructureLocation());
        if (structureNBT.isEmpty()) {
            LOGGER.error("Could not generate desktop: structure template is missing: desktop={}, structure={}",
                    theme.getIdentifier(), theme.getStructureLocation());
            return;
        }
        StructureTemplate desktopStructure = structureNBT.get();
        if (desktopStructure.getSize().getX() <= 0 || desktopStructure.getSize().getY() <= 0 || desktopStructure.getSize().getZ() <= 0) {
            LOGGER.error("Could not generate desktop: structure template has invalid dimensions: desktop={}, structure={}, size={}",
                    theme.getIdentifier(), theme.getStructureLocation(), desktopStructure.getSize());
            return;
        }

        // Fill the area out.
        BlockPos corner = new BlockPos(TardisDimensionConstants.TARDIS_CENTER_POS.getX() - TardisDimensionConstants.DESKTOP_RADIUS, TardisDimensionConstants.TARDIS_ROOT_GENERATION_MIN_HEIGHT, TardisDimensionConstants.TARDIS_CENTER_POS.getZ() - TardisDimensionConstants.DESKTOP_RADIUS);
        BlockPos farCorner = new BlockPos(TardisDimensionConstants.TARDIS_CENTER_POS.getX() + TardisDimensionConstants.DESKTOP_RADIUS, TardisDimensionConstants.TARDIS_ROOT_GENERATION_MAX_HEIGHT, TardisDimensionConstants.TARDIS_CENTER_POS.getZ() + TardisDimensionConstants.DESKTOP_RADIUS);


        if (theme != TardisDesktops.DEFAULT_OVERGROWN_THEME) {

            for (BlockPos pos : BlockPos.betweenClosed(corner, farCorner)) {
                if (operator.getBlockState(pos) != TRBlockRegistry.FOOLS_STONE.get().defaultBlockState()) {
                    operator.setBlock(pos, TRBlockRegistry.FOOLS_STONE.get().defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        }

        List<ItemEntity> itemEntities = operator.getLevel().getEntitiesOfClass(ItemEntity.class, AABB.encapsulatingFullBlocks(corner, farCorner));
        List<ControlEntity> controlEntities = operator.getLevel().getEntitiesOfClass(ControlEntity.class, AABB.encapsulatingFullBlocks(corner, farCorner));
        List<Entity> entitiesForDeath = new ArrayList<>(itemEntities);
        entitiesForDeath.addAll(controlEntities);
        entitiesForDeath.forEach(Entity::discard); //Don't teleport entities to a hard coded coordinate, that causes hanging entity out of world issues. In other cases, if another mod defines that coordinate as a safe area (possible) that will mean the entities never get killed.

        structureNBT.ifPresent(structure -> {
            BlockPos offsetPosition = calculateArcOffset(structure, DESKTOP_CENTER_POS);
            boolean placed = structure.placeInWorld(operator.getLevel(), offsetPosition, offsetPosition,
                    new StructurePlaceSettings(), operator.getLevel().getRandom(), 8);
            LOGGER.info("Desktop structure placement completed: desktop={}, structure={}, placed={}, origin={}, size={}",
                    theme.getIdentifier(), theme.getStructureLocation(), placed, offsetPosition, structure.getSize());
            if (!placed) {
                LOGGER.error("Desktop structure placement returned false; the cleared area may remain Fool's Stone: desktop={}, structure={}",
                        theme.getIdentifier(), theme.getStructureLocation());
            }
            applyMatchingConsoleTheme(operator, theme, structure, offsetPosition);

            // Assign the door from the created structure.
            setInteriorDoorFromStructure(structure, operator);
            buildAirlockEntranceFromStructure(structure, operator);
        });
    }

    /** Verifies exact block identities after the desktop regeneration cooldown and repairs mismatches. */
    public static void repairDesktopStructureIfNeeded(ServerLevel level, DesktopTheme theme) {
        if (theme == null) {
            LOGGER.error("Could not verify desktop structure after generation: no desktop theme was retained");
            return;
        }
        Optional<StructureTemplate> structureNbt = level.getStructureTemplateManager().get(theme.getStructureLocation());
        if (structureNbt.isEmpty()) {
            LOGGER.error("Could not verify desktop structure after generation: desktop={}, structure={}",
                    theme.getIdentifier(), theme.getStructureLocation());
            return;
        }

        StructureTemplate structure = structureNbt.get();
        BlockPos origin = calculateArcOffset(structure, DESKTOP_CENTER_POS);
        CompoundTag savedTemplate = structure.save(new CompoundTag());
        ListTag blocks = savedTemplate.getListOrEmpty("blocks");
        ListTag palette = savedTemplate.getListOrEmpty("palette");
        DesktopStructureAudit audit = auditDesktopStructure(level, origin, blocks, palette);
        if (audit.mismatches() == 0) {
            LOGGER.info("Desktop structure verification passed: desktop={}, templateBlocks={}, matchingBlocks={}",
                    theme.getIdentifier(), audit.totalBlocks(), audit.matchingBlocks());
            return;
        }

        LOGGER.warn("Desktop structure verification found mismatched blocks; repairing: desktop={}, mismatches={}, foolsStone={}, air={}, other={}, templateBlocks={}",
                theme.getIdentifier(), audit.mismatches(), audit.foolsStoneBlocks(), audit.airBlocks(), audit.otherBlocks(), audit.totalBlocks());
        boolean placed = structure.placeInWorld(level, origin, origin, new StructurePlaceSettings(), level.getRandom(), Block.UPDATE_ALL);
        applyMatchingConsoleTheme(level, theme, structure, origin);
        setInteriorDoorFromStructure(structure, level);
        buildAirlockEntranceFromStructure(structure, level);

        DesktopStructureAudit repairedAudit = auditDesktopStructure(level, origin, blocks, palette);
        LOGGER.info("Desktop structure repair completed: desktop={}, placed={}, mismatchesBefore={}, mismatchesAfter={}, foolsStoneAfter={}, airAfter={}, templateBlocks={}",
                theme.getIdentifier(), placed, audit.mismatches(), repairedAudit.mismatches(), repairedAudit.foolsStoneBlocks(), repairedAudit.airBlocks(), repairedAudit.totalBlocks());
    }

    private static DesktopStructureAudit auditDesktopStructure(ServerLevel level, BlockPos origin, ListTag blocks, ListTag palette) {
        int matching = 0;
        int foolsStone = 0;
        int air = 0;
        int other = 0;
        for (int i = 0; i < blocks.size(); i++) {
            CompoundTag blockEntry = blocks.getCompoundOrEmpty(i);
            ListTag relativePosition = blockEntry.getListOrEmpty("pos");
            if (relativePosition.size() < 3) continue;
            BlockPos worldPosition = origin.offset(
                    relativePosition.getIntOr(0, 0),
                    relativePosition.getIntOr(1, 0),
                    relativePosition.getIntOr(2, 0));
            int stateIndex = blockEntry.getIntOr("state", -1);
            if (stateIndex < 0 || stateIndex >= palette.size()) {
                other++;
                continue;
            }
            String expectedName = palette.getCompoundOrEmpty(stateIndex).getStringOr("Name", "minecraft:air");
            Block expectedBlock = BuiltInRegistries.BLOCK.getValue(Identifier.parse(expectedName));
            Block actualBlock = level.getBlockState(worldPosition).getBlock();
            if (actualBlock == expectedBlock) matching++;
            else if (actualBlock == TRBlockRegistry.FOOLS_STONE.get()) foolsStone++;
            else if (actualBlock == Blocks.AIR) air++;
            else other++;
        }
        int total = matching + foolsStone + air + other;
        return new DesktopStructureAudit(total, matching, total - matching, foolsStone, air, other);
    }

    private record DesktopStructureAudit(int totalBlocks, int matchingBlocks, int mismatches,
                                         int foolsStoneBlocks, int airBlocks, int otherBlocks) {}


    private static void applyMatchingConsoleTheme(ServerLevel level, DesktopTheme desktopTheme,
                                                  StructureTemplate structure, BlockPos offset) {
        // Most desktops use the console theme with the same id. Newwave is an
        // exception: its structure contains the Refurbished console, so map it
        // explicitly instead of leaving the console block entity at its
        // Factory default.
        Identifier consoleThemeId = getConsoleThemeForDesktop(desktopTheme);

        var size = structure.getSize();
        BlockPos end = offset.offset(size.getX() - 1, size.getY() - 1, size.getZ() - 1);
        var templateConsoleBlocks = structure.filterBlocks(BlockPos.ZERO,
                new StructurePlaceSettings(), TRBlockRegistry.GLOBAL_CONSOLE_BLOCK.get());
        if (templateConsoleBlocks.isEmpty()) {
            LOGGER.warn("Desktop structure contains no global console blocks: desktop={}, structure={}, size={}",
                    desktopTheme.getIdentifier(), desktopTheme.getStructureLocation(), size);
        }
        for (BlockPos pos : BlockPos.betweenClosed(offset, end)) {
            if (level.getBlockEntity(pos) instanceof ConsoleConfigurationBlockEntity configurator) {
                if (consoleThemeId != null && ConsoleTheme.CONSOLE_THEME_DEFERRED_REGISTRY.get(consoleThemeId) != null
                        && ConsoleTheme.FACTORY.getId().equals(configurator.theme())) {
                    configurator.setConsoleTheme(consoleThemeId);
                }
            }
            if (level.getBlockEntity(pos) instanceof GlobalConsoleBlockEntity console) {
                BlockPos relativePos = pos.subtract(offset);
                StructureTemplate.StructureBlockInfo templateInfo = templateConsoleBlocks.stream()
                        .filter(info -> info.pos().equals(relativePos))
                        .findFirst().orElse(null);
                // Some older structure files use a non-zero internal origin.
                // If there is only one console, its NBT is unambiguous even
                // when the structure's stored origin differs from placement.
                if (templateInfo == null && templateConsoleBlocks.size() == 1) {
                    templateInfo = templateConsoleBlocks.getFirst();
                    LOGGER.info("Matched desktop console by unique template entry: desktop={}, placedPosition={}, templatePosition={}",
                            desktopTheme.getIdentifier(), pos, templateInfo.pos());
                }
                CompoundTag templateNbt = templateInfo == null ? null : templateInfo.nbt();
                Identifier templateThemeId = templateNbt == null ? null
                        : templateNbt.getString(NbtConstants.THEME).map(Identifier::parse).orElse(null);
                Identifier templatePatternId = templateNbt == null ? null
                        : templateNbt.getString(NbtConstants.PATTERN).map(Identifier::parse).orElse(null);

                Identifier currentThemeId = console.theme();
                Identifier targetThemeId = templateThemeId != null
                        && ConsoleTheme.CONSOLE_THEME_DEFERRED_REGISTRY.get(templateThemeId) != null
                        ? templateThemeId
                        : ConsoleTheme.FACTORY.getId().equals(currentThemeId) && consoleThemeId != null
                            && ConsoleTheme.CONSOLE_THEME_DEFERRED_REGISTRY.get(consoleThemeId) != null
                                ? consoleThemeId : currentThemeId;
                Identifier previousPatternId = templatePatternId != null ? templatePatternId : console.pattern().id();
                console.killControls();
                // Always refresh the TARDIS-wide render cache. The block may
                // already have the right theme while the cache still points
                // at a previous desktop (which makes the inactive console
                // render as the last console used until it is interacted with).
                console.setConsoleTheme(targetThemeId);
                var patterns = ConsolePatterns.getPatternsForTheme(targetThemeId);
                ConsolePattern matchingPattern = patterns == null ? null : patterns.stream()
                        .filter(pattern -> pattern.id().equals(previousPatternId))
                        .findFirst()
                        .orElse(patterns.isEmpty() ? null : patterns.get(0));
                if (matchingPattern != null) {
                    console.setPattern(matchingPattern);
                    for (BlockPos configPos : BlockPos.betweenClosed(offset, end)) {
                        if (level.getBlockEntity(configPos) instanceof ConsoleConfigurationBlockEntity configurator) {
                            configurator.setConsoleTheme(targetThemeId);
                            configurator.setConsolePattern(matchingPattern);
                        }
                    }
                    LOGGER.info("Applied desktop console selection: desktop={}, position={}, theme={}, pattern={}, templateTheme={}, templatePattern={}",
                            desktopTheme.getIdentifier(), pos, targetThemeId, matchingPattern.id(), templateThemeId, templatePatternId);
                } else {
                    LOGGER.warn("Could not apply desktop console pattern: desktop={}, position={}, theme={}, requestedPattern={}, templateTheme={}, templatePattern={}",
                            desktopTheme.getIdentifier(), pos, targetThemeId, previousPatternId, templateThemeId, templatePatternId);
                }
                console.markDirty();
                console.sendUpdates();
            }
        }
    }

    /** Returns the console installed by a desktop, when that desktop has one. */
    public static Identifier getConsoleThemeForDesktop(DesktopTheme desktopTheme) {
        if (desktopTheme == null) return null;
        Identifier desktopId = desktopTheme.getIdentifier();
        if (desktopId.equals(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "newwave"))) {
            return ConsoleTheme.REFURBISHED.getId();
        }
        return ConsoleTheme.CONSOLE_THEME_DEFERRED_REGISTRY.get(desktopId) == null ? null : desktopId;
    }

    /** Returns the pattern stored on a desktop's console template, if present. */
    public static Identifier getConsolePatternForDesktop(ServerLevel level, DesktopTheme desktopTheme) {
        if (level == null || desktopTheme == null) return null;
        return level.getStructureTemplateManager().get(desktopTheme.getStructureLocation())
                .stream()
                .flatMap(structure -> structure.filterBlocks(BlockPos.ZERO, new StructurePlaceSettings(),
                        TRBlockRegistry.GLOBAL_CONSOLE_BLOCK.get()).stream())
                .map(StructureTemplate.StructureBlockInfo::nbt)
                .filter(Objects::nonNull)
                .map(nbt -> nbt.getString(NbtConstants.PATTERN).orElse(null))
                .filter(Objects::nonNull)
                .map(Identifier::parse)
                .findFirst().orElse(null);
    }

    /** Reconcile the live console block with its saved TARDIS selection before
     * sending appearance data to a player entering the interior. */
    public static void syncConsoleAppearanceForEntry(ServerLevel level, TardisLevelOperator operator) {
        DesktopTheme desktop = operator.getInteriorManager().currentTheme();
        if (desktop == null) return;
        Optional<StructureTemplate> templateOptional = level.getStructureTemplateManager().get(desktop.getStructureLocation());
        if (templateOptional.isEmpty()) return;
        StructureTemplate template = templateOptional.get();
        List<StructureTemplate.StructureBlockInfo> consoleInfos = template.filterBlocks(
                BlockPos.ZERO, new StructurePlaceSettings(), TRBlockRegistry.GLOBAL_CONSOLE_BLOCK.get());
        BlockPos origin = calculateArcOffset(template, DESKTOP_CENTER_POS);
        for (StructureTemplate.StructureBlockInfo info : consoleInfos) {
            BlockPos position = origin.offset(info.pos());
            if (!(level.getBlockEntity(position) instanceof GlobalConsoleBlockEntity console)) continue;

            Identifier factory = ConsoleTheme.FACTORY.getId();
            Identifier selectedTheme = console.theme();
            if (selectedTheme == null || factory.equals(selectedTheme)) {
                selectedTheme = operator.tardisClientData().getConsoleTheme();
            }
            if (selectedTheme == null || factory.equals(selectedTheme)
                    || ConsoleTheme.CONSOLE_THEME_DEFERRED_REGISTRY.get(selectedTheme) == null) {
                selectedTheme = getConsoleThemeForDesktop(desktop);
            }
            if (selectedTheme == null) continue;

            Identifier selectedPattern = console.pattern() != null
                    && selectedTheme.equals(console.pattern().getThemeId()) ? console.pattern().id() : null;
            if (selectedPattern == null) {
                Identifier cachedPattern = operator.tardisClientData().getConsolePattern();
                if (cachedPattern != null && ConsolePatterns.doesPatternExist(selectedTheme, cachedPattern)) {
                    selectedPattern = cachedPattern;
                }
            }
            if (selectedPattern == null && info.nbt() != null) {
                Identifier templatePattern = info.nbt().getString(NbtConstants.PATTERN)
                        .map(Identifier::parse).orElse(null);
                if (templatePattern != null && ConsolePatterns.doesPatternExist(selectedTheme, templatePattern)) {
                    selectedPattern = templatePattern;
                }
            }

            ConsolePattern pattern = ConsolePatterns.getPatternOrDefault(selectedTheme,
                    selectedPattern == null ? ResourceConstants.DEFAULT_PATTERN_ID : selectedPattern);
            console.setConsoleTheme(selectedTheme);
            if (pattern != null) console.setPattern(pattern);
            LOGGER.info("Reconciled console before TARDIS entry sync: desktop={}, position={}, theme={}, pattern={}",
                    desktop.getIdentifier(), position, selectedTheme, pattern == null ? "none" : pattern.id());
            return;
        }
    }

    public static void buildAirlockEntranceFromStructure(StructureTemplate template, ServerLevel level) {
        BlockPos minPos = calculateArcOffset(template, DESKTOP_CENTER_POS);
        BlockPos maxPos = new BlockPos(minPos.getX() + template.getSize().getX(), minPos.getY() + template.getSize().getY(), minPos.getZ() + template.getSize().getZ());

        for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
            if (level.getBlockState(pos).getBlock() == TRBlockRegistry.AIR_LOCK_GENERATION_BLOCK.get()) {

                TardisLevelOperator.get(level).ifPresent(cap -> {
                    Optional<StructureTemplate> structureNBT = level.getLevel().getStructureTemplateManager().get(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "corridors/airlock_entrance"));
                    structureNBT.ifPresent(structure -> {
                        BlockPos offsetPosition = new BlockPos(3, 2, 0);
                        structure.placeInWorld(level.getLevel(), pos.subtract(offsetPosition), pos.subtract(offsetPosition), new StructurePlaceSettings(), level.getLevel().getRandom(), 2);

                        cap.getInteriorManager().setCorridorAirlockCenter(pos.south(2));
                        level.setBlock(pos, level.getBlockState(pos).setValue(BulkHeadDoorBlock.LOCKED, false), 2);

                    });
                });
                return;
            }
        }
    }

    public static void generateArsTree(TardisLevelOperator tardisLevelOperator, ServerLevel level) {
        if (!currentArsStage.equals("one") && Objects.equals(tardisLevelOperator.getUpgradeHandler().getProgressLevel(), currentArsStage))
            return;

        currentArsStage = tardisLevelOperator.getUpgradeHandler().getProgressLevel();

        Optional<StructureTemplate> structureNBT = level.getLevel().getStructureTemplateManager().get(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "rooms/ars/room_ars_stage_" + currentArsStage));
        structureNBT.ifPresent(structure -> {
            structure.placeInWorld(level.getLevel(), TardisDimensionConstants.ARS_TREE_PLACEMENT_POS, TardisDimensionConstants.ARS_TREE_PLACEMENT_POS, new StructurePlaceSettings(), level.getLevel().getRandom(), Block.UPDATE_ALL);
        });


    }

    public static boolean setInteriorDoorFromStructure(StructureTemplate template, ServerLevel level) {

        BlockPos minPos = calculateArcOffset(template, DESKTOP_CENTER_POS);
        BlockPos maxPos = new BlockPos(minPos.getX() + template.getSize().getX(), minPos.getY() + template.getSize().getY(), minPos.getZ() + template.getSize().getZ());

        //First set the internal door to null so that we aren't caching the previous desktop's internal door
        TardisLevelOperator.get(level).ifPresent(cap -> cap.setInternalDoor(null));

        for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
            if (level.getBlockEntity(pos) instanceof TardisInternalDoor internalDoor) {
                TardisLevelOperator.get(level).ifPresent(cap -> cap.setInternalDoor(internalDoor));
                return true;
            }
        }
        LOGGER.error("Could not find an internal TARDIS door in desktop structure: structure={}, origin={}, size={}",
                template, minPos, template.getSize());
        return false;
    }

    public static BlockPos calculateArcOffset(StructureTemplate structureTemplate, BlockPos centerPos) {
        return new BlockPos(centerPos.getX() - structureTemplate.getSize().getX() / 2, centerPos.getY() - structureTemplate.getSize().getY() / 2, centerPos.getZ() - structureTemplate.getSize().getZ() / 2);
    }

}
