package whocraft.tardis_refined.common.tardis.interior;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.interior.arctypes.DesktopTheme;
import whocraft.tardis_refined.common.tardis.interior.exit.ITardisInternalDoor;
import whocraft.tardis_refined.registry.BlockRegistry;

import java.util.Optional;

// Responsible for all the tedious generation of the desktop;
public class TardisArchitectureHandler {

    public static void generateDesktop(ServerLevel operator, DesktopTheme theme) {
        System.out.println(String.format("Attempting to generate desktop theme: %s for TARDIS.", theme.name));
        Optional<StructureTemplate> structureNBT = operator.getLevel().getStructureManager().get(theme.resourceLocation);
        structureNBT.ifPresent(structure -> {
            BlockPos consolePosition = new BlockPos(0, 100, 0);
            BlockPos offsetPosition = calculateArcOffset(theme, consolePosition);
            structure.placeInWorld(operator.getLevel(), offsetPosition, offsetPosition, new StructurePlaceSettings(), operator.getLevel().random, 3);
        });
    }

    public static void generateDesktop(TardisLevelOperator operator, DesktopTheme theme) {
        generateDesktop(operator.getLevel(), theme);
    }

    // TODO: Logic works in only one scenario. Got to change that.
    // Ideally the door will already be set up and ready to go.
    public static void createDesktopDoor(TardisLevelOperator operator, BlockPos position) {
        operator.getLevel().setBlockAndUpdate(position, BlockRegistry.INTERNAL_DOOR_BLOCK.get().defaultBlockState());
        ITardisInternalDoor internalDoor = (ITardisInternalDoor) operator.getLevel().getBlockEntity(position);
        if (internalDoor != null) {
            operator.setInternalDoor(internalDoor);
        }
    }

    public static BlockPos calculateArcOffset(DesktopTheme theme, BlockPos centerPos) {
        return new BlockPos(centerPos.getX() - theme.offset.getX(), centerPos.getY() - theme.offset.getY(),centerPos.getZ() - theme.offset.getZ());
    }
    
}
