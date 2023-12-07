package whocraft.tardis_refined.common.capability.upgrades;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.RegistryHelper;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class Upgrades {

    public static final ResourceKey<Registry<Upgrade>> CONSOLE_THEME_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "upgrade"));

    /** Tardis Refined instance of the ConsoleTheme registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only*/
    public static final DeferredRegistry<Upgrade> UPGRADE_DEFERRED_REGISTRY = DeferredRegistry.createCustom(TardisRefined.MODID, CONSOLE_THEME_REGISTRY_KEY, true);
    /** Global instance of the Console Theme custom registry created by Tardis Refined*/
    public static final Registry<Upgrade> UPGRADE_REGISTRY = UPGRADE_DEFERRED_REGISTRY.getRegistry();


    // Base
    public static final RegistrySupplier<Upgrade> TARDIS_XP = UPGRADE_DEFERRED_REGISTRY.register("tardis_xp", () -> new Upgrade(Items.GLASS_BOTTLE::getDefaultInstance, RegistryHelper.makeKey("tardis_xp"))
            .setSkillPointsRequired(50).setPosition(0, 0));

    // Defense
    public static final RegistrySupplier<Upgrade> DEFENSE_SYSTEM = UPGRADE_DEFERRED_REGISTRY.register("defense_system", () -> new Upgrade(Items.DIAMOND_SWORD::getDefaultInstance, TARDIS_XP, RegistryHelper.makeKey("defense_system"))
            .setSkillPointsRequired(50).setPosition(1, 0));

    public static final RegistrySupplier<Upgrade> HOSTILE_DISPLACEMENT = UPGRADE_DEFERRED_REGISTRY.register("hostile_displacement", () -> new Upgrade(Items.ZOMBIE_HEAD::getDefaultInstance, DEFENSE_SYSTEM, RegistryHelper.makeKey("hostile_displacement"))
            .setSkillPointsRequired(50).setPosition(2, 0));

    public static final RegistrySupplier<Upgrade> MATERIALIZE_AROUND = UPGRADE_DEFERRED_REGISTRY.register("materialize_around", () -> new Upgrade(Items.GLASS_PANE::getDefaultInstance, DEFENSE_SYSTEM, RegistryHelper.makeKey("materialize_around"))
            .setSkillPointsRequired(50).setPosition(3, 0));

    // Chameleon Circuit
    public static final RegistrySupplier<Upgrade> ARCHITECTURE_SYSTEM = UPGRADE_DEFERRED_REGISTRY.register("architecture_system", () -> new Upgrade(BlockRegistry.ARS_EGG.get().asItem()::getDefaultInstance, TARDIS_XP, RegistryHelper.makeKey("architecture_system"))
            .setSkillPointsRequired(50).setPosition(1, 1));

    public static final RegistrySupplier<Upgrade> CHAMELEON_CIRCUIT_SYSTEM = UPGRADE_DEFERRED_REGISTRY.register("chameleon_circuit_system", () -> new Upgrade(BlockRegistry.ROOT_SHELL_BLOCK.get().asItem()::getDefaultInstance, ARCHITECTURE_SYSTEM, RegistryHelper.makeKey("chameleon_circuit_system"))
            .setSkillPointsRequired(50).setPosition(2, 1));

    public static final RegistrySupplier<Upgrade> INSIDE_ARCHITECTURE = UPGRADE_DEFERRED_REGISTRY.register("inside_architecture", () -> new Upgrade(BlockRegistry.TERRAFORMER_BLOCK.get().asItem()::getDefaultInstance, ARCHITECTURE_SYSTEM, RegistryHelper.makeKey("inside_architecture"))
            .setSkillPointsRequired(50).setPosition(3, 1));

    // Navigation
    public static final RegistrySupplier<Upgrade> NAVIGATION_SYSTEM = UPGRADE_DEFERRED_REGISTRY.register("navigation_system", () -> new Upgrade(Items.COMPASS::getDefaultInstance, TARDIS_XP, RegistryHelper.makeKey("navigation_system"))
            .setSkillPointsRequired(50).setPosition(1, 2));

    public static final RegistrySupplier<Upgrade> EXPLORER = UPGRADE_DEFERRED_REGISTRY.register("explorer", () -> new Upgrade(Items.COMPASS::getDefaultInstance, NAVIGATION_SYSTEM, RegistryHelper.makeKey("explorer"))
            .setSkillPointsRequired(50).setPosition(2, 2));

    public static final RegistrySupplier<Upgrade> EXPLORER_II = UPGRADE_DEFERRED_REGISTRY.register("explorer_ii", () -> new Upgrade(Items.COMPASS::getDefaultInstance, EXPLORER, RegistryHelper.makeKey("explorer_ii"))
            .setSkillPointsRequired(50).setPosition(3, 2));

    public static final RegistrySupplier<Upgrade> EXPLORER_III = UPGRADE_DEFERRED_REGISTRY.register("explorer_iii", () -> new Upgrade(Items.COMPASS::getDefaultInstance, EXPLORER_II, RegistryHelper.makeKey("explorer_iii"))
            .setSkillPointsRequired(50).setPosition(4, 2));


    public static final RegistrySupplier<Upgrade> WAYPOINTS = UPGRADE_DEFERRED_REGISTRY.register("waypoints", () -> new Upgrade(Items.MAP::getDefaultInstance, NAVIGATION_SYSTEM, RegistryHelper.makeKey("waypoints"))
            .setSkillPointsRequired(50).setPosition(1, 3));

    public static final RegistrySupplier<Upgrade> COORDINATE_INPUT = UPGRADE_DEFERRED_REGISTRY.register("coordinate_input", () -> new Upgrade(Items.FILLED_MAP::getDefaultInstance, WAYPOINTS, RegistryHelper.makeKey("coordinate_input"))
            .setSkillPointsRequired(50).setPosition(2, 3));


}