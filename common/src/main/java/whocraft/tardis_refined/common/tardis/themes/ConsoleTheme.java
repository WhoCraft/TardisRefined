package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.console.*;
import whocraft.tardis_refined.common.tardis.themes.console.sound.ConsoleSoundProfile;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.RegistrySupplierHolder;


public class ConsoleTheme implements Theme {

    /** Registry Key for the ConsoleTheme registry. For addon mods, use this as the registry key*/
    public static final ResourceKey<Registry<ConsoleTheme>> CONSOLE_THEME_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "console_theme"));

    /** Tardis Refined instance of the ConsoleTheme registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only*/
    public static final DeferredRegistry<ConsoleTheme> CONSOLE_THEMES = DeferredRegistry.createCustom(TardisRefined.MODID, CONSOLE_THEME_REGISTRY_KEY);

    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> FACTORY = registerConsoleTheme("factory", new FactoryConsoleTheme());
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> CRYSTAL = registerConsoleTheme("crystal", new CrystalConsoleTheme());
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> COPPER = registerConsoleTheme("copper", new CopperConsoleTheme());
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> CORAL = registerConsoleTheme("coral", new CoralConsoleTheme());
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> TOYOTA = registerConsoleTheme("toyota", new ToyotaConsoleTheme());
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> VICTORIAN = registerConsoleTheme("victorian", new VictorianConsoleTheme());
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> MYST = registerConsoleTheme("myst", new MystConsoleTheme());
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> NUKA = registerConsoleTheme("nuka", new NukaConsoleTheme());
    public static final RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> INITIATIVE = registerConsoleTheme("initiative", new InitiativeConsoleTheme());

    private final ConsoleThemeDetails consoleThemeDetails;

    public ConsoleTheme(ConsoleThemeDetails consoleThemeDetails) {
        this.consoleThemeDetails = consoleThemeDetails;
    }

    public ControlSpecification[] getControlSpecificationList() {
        return consoleThemeDetails.getControlSpecification();
    }

    public ConsoleSoundProfile getSoundProfile() {
        return consoleThemeDetails.getSoundProfile();
    }


    private static RegistrySupplierHolder<ConsoleTheme, ConsoleTheme> registerConsoleTheme(String id, ConsoleThemeDetails themeDetails){
        return CONSOLE_THEMES.registerHolder(id, () -> new ConsoleTheme(themeDetails));
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    public String getTranslationKey() {
        return null;
    }
}
