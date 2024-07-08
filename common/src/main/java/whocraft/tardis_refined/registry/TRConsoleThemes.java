package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.console.*;
import whocraft.tardis_refined.common.util.RegistryHelper;

public class TRConsoleThemes {

    /**
     * Registry Key for the ConsoleTheme registry. For addon mods, use this as the registry key
     */
    public static final ResourceKey<Registry<ConsoleTheme>> CONSOLE_THEME_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "console_theme"));

    /**
     * Tardis Refined instance of the ConsoleTheme registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only
     */
    public static final DeferredRegistry<ConsoleTheme> CONSOLE_THEME_DEFERRED_REGISTRY = DeferredRegistry.createCustom(TardisRefined.MODID, CONSOLE_THEME_REGISTRY_KEY, true);
    /**
     * Instance of registry containing all ConsoleTheme entries. Addon mod entries will be included in this registry as long as they are use the same ResourceKey<Registry<ObjectType>>.
     */
    public static final Registry<ConsoleTheme> CONSOLE_THEME_REGISTRY = CONSOLE_THEME_DEFERRED_REGISTRY.getRegistry().get();

    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> FACTORY = CONSOLE_THEME_DEFERRED_REGISTRY.registerHolder("factory", () -> new FactoryConsoleTheme(RegistryHelper.makeKey("factory")));
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> CRYSTAL = CONSOLE_THEME_DEFERRED_REGISTRY.registerHolder("crystal", () -> new CrystalConsoleTheme(RegistryHelper.makeKey("crystal")));
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> COPPER = CONSOLE_THEME_DEFERRED_REGISTRY.registerHolder("copper", () -> new CopperConsoleTheme(RegistryHelper.makeKey("copper")));
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> CORAL = CONSOLE_THEME_DEFERRED_REGISTRY.registerHolder("coral", () -> new CoralConsoleTheme(RegistryHelper.makeKey("coral")));
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> TOYOTA = CONSOLE_THEME_DEFERRED_REGISTRY.registerHolder("toyota", () -> new ToyotaConsoleTheme(RegistryHelper.makeKey("toyota")));
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> VICTORIAN = CONSOLE_THEME_DEFERRED_REGISTRY.registerHolder("victorian", () -> new VictorianConsoleTheme(RegistryHelper.makeKey("victorian")));
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> MYST = CONSOLE_THEME_DEFERRED_REGISTRY.registerHolder("myst", () -> new MystConsoleTheme(RegistryHelper.makeKey("myst")));
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> NUKA = CONSOLE_THEME_DEFERRED_REGISTRY.registerHolder("nuka", () -> new NukaConsoleTheme(RegistryHelper.makeKey("nuka")));
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> INITIATIVE = CONSOLE_THEME_DEFERRED_REGISTRY.registerHolder("initiative", () -> new InitiativeConsoleTheme(RegistryHelper.makeKey("initiative")));
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> REFURBISHED = CONSOLE_THEME_DEFERRED_REGISTRY.registerHolder("refurbished", () -> new RefurbishedConsoleTheme(RegistryHelper.makeKey("refurbished")));

}