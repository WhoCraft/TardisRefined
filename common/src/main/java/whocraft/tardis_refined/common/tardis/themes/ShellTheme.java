package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.shell.RedirectBlock;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRBlockRegistry;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShellTheme implements Theme {

    public static final Map<Direction, VoxelShape> DEFAULT_EXTERIOR_SHAPES = Map.of(
            Direction.NORTH, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 30.0D, 11.0D),
            Direction.SOUTH, Block.box(0.0D, 0.0D, 2.0D, 16.0D, 30.0D, 16.0D),
            Direction.WEST, Block.box(0.0D, 0.0D, 0.0D, 11.0D, 30.0D, 16.0D),
            Direction.EAST, Block.box(2.0D, 0.0D, 0.0D, 16.0D, 30.0D, 16.0D)
    );

    public static final Map<Direction, VoxelShape> DEFAULT_INTERIOR_SHAPES = Map.of(
            Direction.NORTH, Block.box(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 0.25D),
            Direction.SOUTH, Block.box(0.0D, 0.0D, 15.75D, 16.0D, 32.0D, 16.0D),
            Direction.EAST, Block.box(15.75D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D),
            Direction.WEST, Block.box(0.0D, 0.0D, 0.0D, 0.25D, 32.0D, 16.0D)
    );

    public static final Map<ShapeType, Map<Direction, VoxelShape>> DEFAULT_SHAPES = sidedShapes(
            DEFAULT_EXTERIOR_SHAPES, DEFAULT_INTERIOR_SHAPES
    );

    /**
     * Registry Key for the ShellTheme registry. For addon mods, use this as the registry key
     */
    public static final ResourceKey<Registry<ShellTheme>> SHELL_THEME_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "shell_theme"));

    /**
     * Tardis Refined instance of the ShellTheme registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only
     */
    public static final DeferredRegistry<ShellTheme> SHELL_THEME_DEFERRED_REGISTRY = DeferredRegistry.createCustom(TardisRefined.MODID, SHELL_THEME_REGISTRY_KEY, true);

    public static final RegistrySupplier<ShellTheme> HALF_BAKED = registerShellTheme("half_baked"); // The default shell. Do not remove.

    public static final RegistrySupplier<ShellTheme> FACTORY = registerShellTheme("factory");
    public static final RegistrySupplier<ShellTheme> POLICE_BOX = registerShellTheme("police_box", true);
    public static final RegistrySupplier<ShellTheme> PHONE_BOOTH = registerShellTheme("phone_booth", true);
    public static final RegistrySupplier<ShellTheme> MYSTIC = registerShellTheme("mystic", true);
    public static final RegistrySupplier<ShellTheme> PRESENT = registerShellTheme("present");
    public static final RegistrySupplier<ShellTheme> DRIFTER = registerShellTheme("drifter");
    public static final RegistrySupplier<ShellTheme> VENDING = registerShellTheme("vending", true);
    public static final RegistrySupplier<ShellTheme> BRIEFCASE = registerShellTheme(
            "briefcase", false, sidedShapes(
                    shapes(Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)),
                    shapes(Block.box(0.0, 0.0, 0.0, 16.0, 48.0, 14.0))
            )
    );
    public static final RegistrySupplier<ShellTheme> GROENING = registerShellTheme("groening", true);
    public static final RegistrySupplier<ShellTheme> BIG_BEN = registerShellTheme("big_ben", true);
    public static final RegistrySupplier<ShellTheme> NUKA = registerShellTheme("nuka", true);
    public static final RegistrySupplier<ShellTheme> GROWTH = registerShellTheme("growth");
    public static final RegistrySupplier<ShellTheme> PORTALOO = registerShellTheme("portaloo");
    public static final RegistrySupplier<ShellTheme> PAGODA = registerShellTheme("pagoda");
    public static final RegistrySupplier<ShellTheme> LIFT = registerShellTheme("lift", true);
    public static final RegistrySupplier<ShellTheme> HIEROGLYPH = registerShellTheme("hieroglyph");
    public static final RegistrySupplier<ShellTheme> CASTLE = registerShellTheme("castle");
    public static final RegistrySupplier<ShellTheme> PATHFINDER = registerShellTheme("pathfinder");
    public static final RegistrySupplier<ShellTheme> SHULKER = registerShellTheme(
            "shulker", false, openClosedShapes(
                    shapes(Shapes.block()),
                    DEFAULT_EXTERIOR_SHAPES
            )
    );

    public static ShellTheme getShellTheme(ResourceLocation resourceLocation) {
        ShellTheme potentialTheme = SHELL_THEME_DEFERRED_REGISTRY.get(resourceLocation);
        if (potentialTheme != null) {
            return potentialTheme;
        }
        return HALF_BAKED.get();
    }

    public static ResourceLocation getKey(ShellTheme shellTheme) {
        return SHELL_THEME_DEFERRED_REGISTRY.getKey(shellTheme);
    }

    public enum ShapeType {
        CLOSED_EXTERIOR,
        OPEN_EXTERIOR,
        CLOSED_INTERIOR,
        OPEN_INTERIOR
    }

    /**
     * Creates a rotated shape from the given shape, unless rotation is NONE in which case it is returned unmodified.
     * @param shape The shape to rotate
     * @param rotation The rotation
     * @return A new rotated shape, or the original unmodified
     */
    public static VoxelShape rotateShape(VoxelShape shape, Rotation rotation) {
        if (rotation == Rotation.NONE) return shape;
        VoxelShape newShape = Shapes.empty();
        for (var aabb : shape.toAabbs()) {
            aabb = switch (rotation) {
	            case NONE -> aabb;
	            case CLOCKWISE_90 -> new AABB(1 - aabb.minZ, aabb.minY, aabb.minX,1 - aabb.maxZ, aabb.maxY, aabb.maxX);
                case CLOCKWISE_180 -> new AABB(1 - aabb.minX, aabb.minY, 1 - aabb.minZ, 1 - aabb.maxX, aabb.maxY, 1 - aabb.maxZ);
                case COUNTERCLOCKWISE_90 -> new AABB(aabb.minZ, aabb.minY, 1 - aabb.minX, aabb.maxZ, aabb.maxY, 1 - aabb.maxX);
            };
            newShape = Shapes.or(newShape, Shapes.create(aabb));
        }
        return newShape;
    }

    public static Rotation directionToRotation(Direction direction) {
        return switch (direction) {
            case NORTH -> Rotation.NONE;
            case EAST -> Rotation.CLOCKWISE_90;
            case SOUTH -> Rotation.CLOCKWISE_180;
            case WEST -> Rotation.COUNTERCLOCKWISE_90;
	        default -> throw new IllegalArgumentException(direction + " is not horizontal!");
        };
    }

    /**
     * Creates a map of shapes for each direction from the given shape.
     * Assumes the given shape is rotated north and creates rotated shapes accordingly.
     * @param shape The original shape.
     * @return A map of shapes for each direction.
     */
    public static Map<Direction, VoxelShape> shapes(VoxelShape shape) {
        return Direction.Plane.HORIZONTAL.stream().collect(Collectors.toMap(dir -> dir, dir -> rotateShape(shape, directionToRotation(dir))));
    }

    public static Map<ShapeType, Map<Direction, VoxelShape>> sidedShapes(Map<Direction, VoxelShape> exterior, Map<Direction, VoxelShape> interior) {
        return Map.of(
                ShapeType.CLOSED_EXTERIOR, exterior,
                ShapeType.OPEN_EXTERIOR, exterior,
                ShapeType.CLOSED_INTERIOR, interior,
                ShapeType.OPEN_INTERIOR, interior
        );
    }

    public static Map<ShapeType, Map<Direction, VoxelShape>> openClosedShapes(Map<Direction, VoxelShape> closed, Map<Direction, VoxelShape> open) {
        return Map.of(
                ShapeType.CLOSED_EXTERIOR, closed,
                ShapeType.OPEN_EXTERIOR, open,
                ShapeType.CLOSED_INTERIOR, closed,
                ShapeType.OPEN_INTERIOR, open
        );
    }

    private ResourceLocation translationKey;
    private boolean producesLight;
    private Map<ShapeType, Map<Direction, VoxelShape>> shapes;

    public ShellTheme(ResourceLocation translationKey, boolean producesLight, Map<ShapeType, Map<Direction, VoxelShape>> shapes) {
        this.translationKey = translationKey;
        this.producesLight = producesLight;
        this.shapes = shapes;

        for (var shapeType : ShapeType.values()) {
            if (!shapes.containsKey(shapeType)) throw new IllegalStateException("ShapeType " + shapeType + " is missing from shapes!");
            var shapesForType = shapes.get(shapeType);
            for (var direction : Direction.Plane.HORIZONTAL) {
                if (!shapesForType.containsKey(direction)) throw new IllegalStateException("Direction " + direction + " is missing from shapes!");
            }
        }
    }

    public ShellTheme(ResourceLocation translationKey, boolean producesLight) {
        this(translationKey, producesLight, DEFAULT_SHAPES);
    }

    public ShellTheme(ResourceLocation translationKey) {
        this(translationKey, false);
    }

    private static RegistrySupplier<ShellTheme> registerShellTheme(String id) {
        return SHELL_THEME_DEFERRED_REGISTRY.register(id, () -> new ShellTheme(new ResourceLocation(TardisRefined.MODID, id)));
    }

    private static RegistrySupplier<ShellTheme> registerShellTheme(String id, boolean producesLight) {
        return SHELL_THEME_DEFERRED_REGISTRY.register(id, () -> new ShellTheme(new ResourceLocation(TardisRefined.MODID, id), producesLight));
    }

    private static RegistrySupplier<ShellTheme> registerShellTheme(String id, boolean producesLight, Map<ShapeType, Map<Direction, VoxelShape>> shapes) {
        return SHELL_THEME_DEFERRED_REGISTRY.register(id, () -> new ShellTheme(new ResourceLocation(TardisRefined.MODID, id), producesLight, shapes));
    }

    public static VoxelShape getShape(Map<Direction, VoxelShape> shapes, Direction direction) {
        return shapes.getOrDefault(direction, shapes.get(Direction.SOUTH));
    }

    public VoxelShape getShape(ShapeType shapeType, Direction direction) {
        return getShape(shapes.getOrDefault(shapeType, DEFAULT_EXTERIOR_SHAPES), direction);
    }

    @Override
    public String getTranslationKey() {
        return Util.makeDescriptionId("shell", this.translationKey);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(this.getTranslationKey());
    }

    public boolean producesLight() {
        return producesLight;
    }

}
