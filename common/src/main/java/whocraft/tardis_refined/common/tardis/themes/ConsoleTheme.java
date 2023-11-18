package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
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



public class ConsoleTheme {

    public static final ResourceKey<Registry<ConsoleTheme>> CONSOLE_THEME_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "console_theme"));

    public static final DeferredRegistry<ConsoleTheme> CONSOLE_THEMES = DeferredRegistry.createCustom(TardisRefined.MODID, CONSOLE_THEME_REGISTRY_KEY);

    public static final RegistrySupplier<ConsoleTheme> FACTORY = registerConsoleTheme("factory", new FactoryConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> CRYSTAL = registerConsoleTheme("crystal", new CrystalConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> COPPER = registerConsoleTheme("copper", new CopperConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> CORAL = registerConsoleTheme("coral", new CoralConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> TOYOTA = registerConsoleTheme("toyota", new ToyotaConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> VICTORIAN = registerConsoleTheme("victorian", new VictorianConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> MYST = registerConsoleTheme("myst", new MystConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> NUKA = registerConsoleTheme("nuka", new NukaConsoleTheme());
    public static final RegistrySupplier<ConsoleTheme> INITIATIVE = registerConsoleTheme("initiative", new InitiativeConsoleTheme());

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


    private static RegistrySupplier<ConsoleTheme> registerConsoleTheme(String id, ConsoleThemeDetails themeDetails){
        return CONSOLE_THEMES.register(id, () -> new ConsoleTheme(themeDetails));
    }

}
