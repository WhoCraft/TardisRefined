package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.api.event.TardisCommonEvents;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.console.sound.ConsoleSoundProfile;
import whocraft.tardis_refined.common.tardis.themes.console.sound.GenericConsoleSoundProfile;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRControlRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class ConsoleTheme implements Theme {

    private final ArrayList<ControlSpecification> controlSpecifications = new ArrayList<>();
    private final ConsoleSoundProfile soundProfile = new GenericConsoleSoundProfile();
    private final ResourceLocation translationKey;

    public ConsoleTheme(ResourceLocation translationKey) {
        this.translationKey = translationKey;
        this.addControlSpecifications();
        TardisCommonEvents.CONSOLE_THEME_REGISTERED.invoker().onThemeRegistered(this);
    }

    public final List<ControlSpecification> getControlSpecificationList() {
        return controlSpecifications;
    }

    public abstract void addControlSpecifications();

    public void addControl(RegistrySupplier<Control> controlRegistrySupplier, float x, float y, float z, float width, float height) {
        controlSpecifications.add(new ControlSpecification(controlRegistrySupplier, new Vector3f(x, y, z), EntityDimensions.scalable(width, height)));
    }

    public void addControl(RegistrySupplier<Control> controlRegistrySupplier, float x, float y, float z) {
        controlSpecifications.add(new ControlSpecification(controlRegistrySupplier, new Vector3f(x, y, z), EntityDimensions.scalable(0.13f, 0.13f)));
    }

    public void addEmptyControl(float x, float y, float z, float width, float height){
        addControl(TRControlRegistry.GENERIC_NO_SHOW, x, y, z, width, height);
    }

    public void addEmptyControl(float x, float y, float z) {
        addControl(TRControlRegistry.GENERIC_NO_SHOW, x, y, z);
    }

    public void replaceControl(Control replacementControl, int controlIndex, ConsoleTheme consoleTheme) {
        try {
            Control originalControl = consoleTheme.getControlSpecificationList().get(controlIndex).control();
            if (originalControl.equals(TRControlRegistry.GENERIC_NO_SHOW.get())) {
                controlSpecifications.get(controlIndex).setControl(replacementControl);
            } else {
                TardisRefined.LOGGER.error("Could not replace non-empty control {} at index {} on console {}", originalControl.getId(), controlIndex, consoleTheme.translationKey.toString());
            }
        } catch (IndexOutOfBoundsException exception) {
            TardisRefined.LOGGER.error("Could not replace control: no control present at index {} on console {}", controlIndex, consoleTheme.translationKey.toString());
        }
    }

    @Override
    public String getTranslationKey() {
        return Util.makeDescriptionId("console", this.translationKey);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(this.getTranslationKey());
    }

    public ConsoleSoundProfile getSoundProfile() {
        return soundProfile;
    }

    // Convenience function for addon mod developers to replace empty controls
    public static void registerReplacementControl(RegistrySupplier<Control> replacementControl,  Map<ConsoleTheme, Integer> replacements) {
        for (ConsoleTheme consoleTheme : replacements.keySet()) {
            int index = replacements.get(consoleTheme);
            System.out.println("yeah heck");

            consoleTheme.replaceControl(replacementControl.get(), index, consoleTheme);
        }
    }
}
