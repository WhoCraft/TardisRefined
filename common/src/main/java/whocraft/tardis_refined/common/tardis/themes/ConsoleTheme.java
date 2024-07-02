package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.console.*;
import whocraft.tardis_refined.common.tardis.themes.console.sound.ConsoleSoundProfile;
import whocraft.tardis_refined.common.tardis.themes.console.sound.GenericConsoleSoundProfile;
import whocraft.tardis_refined.registry.*;

import java.util.ArrayList;
import java.util.List;


public class ConsoleTheme implements Theme {

    final ControlSpecification[] controlSpecifications;
    ConsoleSoundProfile soundProfile = new GenericConsoleSoundProfile();
    private ResourceLocation translationKey;
    private final ConsoleThemeDetails consoleThemeDetails;

    public ConsoleTheme(ResourceLocation translationKey, ConsoleThemeDetails consoleThemeDetails, ControlSpecification[] controlSpecifications) {
        this.translationKey = translationKey;
        this.consoleThemeDetails = consoleThemeDetails;
        this.controlSpecifications = controlSpecifications;
    }

    public ControlSpecification[] getControlSpecificationList() {
        return consoleThemeDetails.getControlSpecification();
    }

    public final ControlSpecification[] getControlSpecification() {
        return controlSpecifications;
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

    public static class Builder {

        private final List<ControlSpecification> controlSpecifications;

        public Builder() {
            controlSpecifications = new ArrayList<>();
        }

        public Builder add(RegistrySupplier<Control> controlRegistrySupplier, float x, float y, float z, float width, float height) {
            controlSpecifications.add(new ControlSpecification(controlRegistrySupplier, new Vector3f(x, y, z), EntityDimensions.scalable(width, height)));
            return this;
        }

        public Builder addEmpty(float x, float y, float z, float width, float height){
            return add(TRControlRegistry.GENERIC_NO_SHOW, x, y, z, width, height);
        }

        public ControlSpecification[] build() {
            return controlSpecifications.toArray(ControlSpecification[]::new);
        }

    }
}
