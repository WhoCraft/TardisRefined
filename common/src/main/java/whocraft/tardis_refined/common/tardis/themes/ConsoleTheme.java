package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.console.*;
import whocraft.tardis_refined.common.tardis.themes.console.sound.ConsoleSoundProfile;
import whocraft.tardis_refined.registry.CustomRegistry;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

import java.util.Optional;


public class ConsoleTheme implements Theme {

    /** Registry Key for the ConsoleTheme registry. For addon mods, use this as the registry key*/
    public static final ResourceLocation CONSOLE_THEME = new ResourceLocation(TardisRefined.MODID, "console_theme");

    /** Tardis Refined instance of the ConsoleTheme registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only*/
    public static final CustomRegistry<ConsoleTheme> CONSOLE_THEMES_REGISTRY = CustomRegistry.create(CONSOLE_THEME);
    /** Global instance of the Console Theme custom registry created by Tardis Refined*/
    public static final DeferredRegistry<ConsoleTheme> CONSOLE_THEME_DEFERRED_REGISTRY = DeferredRegistry.create(TardisRefined.MODID, CONSOLE_THEMES_REGISTRY);

    public static final RegistrySupplier<ConsoleTheme> FACTORY = registerConsoleTheme("factory", new FactoryConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> CRYSTAL = registerConsoleTheme("crystal", new CrystalConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> COPPER = registerConsoleTheme("copper", new CopperConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> CORAL = registerConsoleTheme("coral", new CoralConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> TOYOTA = registerConsoleTheme("toyota", new ToyotaConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> VICTORIAN = registerConsoleTheme("victorian", new VictorianConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> MYST = registerConsoleTheme("myst", new MystConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> NUKA = registerConsoleTheme("nuka", new NukaConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> INITIATIVE = registerConsoleTheme("initiative", new InitiativeConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> REFURBISHED = registerConsoleTheme("refurbished", new RefurbishedConsoleTheme());

    private ResourceLocation translationKey;
    private final ConsoleThemeDetails consoleThemeDetails;

    public ConsoleTheme(ResourceLocation translationKey, ConsoleThemeDetails consoleThemeDetails) {
        this.translationKey = translationKey;
        this.consoleThemeDetails = consoleThemeDetails;
    }

    public ControlSpecification[] getControlSpecificationList() {
        return consoleThemeDetails.getControlSpecification();
    }

    public ConsoleSoundProfile getSoundProfile() {
        return consoleThemeDetails.getSoundProfile();
    }


    private static RegistrySupplier<ConsoleTheme> registerConsoleTheme(String id, ConsoleThemeDetails themeDetails){
        return CONSOLE_THEME_DEFERRED_REGISTRY.register(id, () -> new ConsoleTheme(new ResourceLocation(TardisRefined.MODID, id), themeDetails));
    }

    @Override
    public String getTranslationKey() {
        return Util.makeDescriptionId("console", this.translationKey);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(this.getTranslationKey());
    }
}
