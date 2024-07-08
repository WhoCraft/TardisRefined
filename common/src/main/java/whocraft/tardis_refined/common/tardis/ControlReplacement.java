package whocraft.tardis_refined.common.tardis;

import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.RegistrySupplierHolder;
import whocraft.tardis_refined.registry.TRConsoleThemes;
import whocraft.tardis_refined.registry.TRControlRegistry;

import java.util.Map;

/**
 * This record is used for addon mods to register replacements for generic controls registered as {@link TRControlRegistry#GENERIC_NO_SHOW}
 * @param control - The control to replace the {@link TRControlRegistry#GENERIC_NO_SHOW}
 * @param replacements - A {@link Map} of the {@link ConsoleTheme} and index of the generic control to replace in the list of {@link ControlSpecification}s
 */
public record ControlReplacement(Control control, Map<RegistrySupplierHolder<ConsoleTheme, ConsoleTheme>, Integer> replacements) {

    public ControlReplacement {
        replacements.forEach((theme, index) -> {
            if (TRConsoleThemes.CONSOLE_THEME_REGISTRY.containsKey(theme.getId())) {
                TRConsoleThemes.CONSOLE_THEME_REGISTRY.get(theme.getId()).replaceControl(control, index);
            }
        });
    }
}
