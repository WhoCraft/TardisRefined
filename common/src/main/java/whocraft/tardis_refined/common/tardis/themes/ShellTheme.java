package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplierHolder;
import whocraft.tardis_refined.registry.RegistrySupplierHolderHolder;

import java.util.Locale;

public class ShellTheme implements Theme {

    /** Registry Key for the ShellTheme registry. For addon mods, use this as the registry key*/
    public static final ResourceKey<Registry<ShellTheme>> SHELL_THEME_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(TardisRefined.MODID, "shell_theme"));

    /** Tardis Refined instance of the ShellTheme registry. Addon Mods: DO NOT USE THIS, it is only for Tardis Refined use only*/
    public static final DeferredRegistry<ShellTheme> SHELL_THEMES = DeferredRegistry.createCustom(TardisRefined.MODID, SHELL_THEME_REGISTRY_KEY);

    /** Global instance of the Shell Theme custom registry created by Tardis Refined*/
    public static final Registry<ShellTheme> SHELL_THEME_REGISTRY = SHELL_THEMES.getRegistry();

    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> FACTORY = registerShellTheme("factory");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> POLICE_BOX = registerShellTheme("police_box");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> PHONE_BOOTH = registerShellTheme("phone_booth");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> MYSTIC = registerShellTheme("mystic");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> PRESENT = registerShellTheme("present");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> DRIFTER = registerShellTheme("drifter");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> VENDING = registerShellTheme("vending");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> BRIEFCASE = registerShellTheme("briefcase");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> GROENING = registerShellTheme("groening");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> BIG_BEN = registerShellTheme("big_ben");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> NUKA = registerShellTheme("nuka");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> GROWTH = registerShellTheme("growth");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> PORTALOO = registerShellTheme("portaloo");
    public static final RegistrySupplierHolder<ShellTheme, ShellTheme> PAGODA = registerShellTheme("pagoda");


    public ShellTheme(){}

    private static RegistrySupplierHolder<ShellTheme, ShellTheme> registerShellTheme(String id){
        return SHELL_THEMES.registerHolder(id,  () -> new ShellTheme());
    }

    @Override
    public String getTranslationKey(ResourceLocation themeId) {
        return ModMessages.shell(themeId.getPath());
    }

    @Override
    public Component getDisplayName(ResourceLocation themeId) {
        return Component.translatable(this.getTranslationKey(themeId));
    }

}
