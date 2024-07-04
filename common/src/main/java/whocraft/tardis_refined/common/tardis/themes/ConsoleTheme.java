package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import org.joml.Vector3f;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.console.sound.ConsoleSoundProfile;
import whocraft.tardis_refined.common.tardis.themes.console.sound.GenericConsoleSoundProfile;
import whocraft.tardis_refined.registry.RegistrySupplier;
import whocraft.tardis_refined.registry.TRControlRegistry;

import java.util.ArrayList;
import java.util.List;


public abstract class ConsoleTheme implements Theme {

    private final ArrayList<ControlSpecification> controlSpecifications = new ArrayList<>();
    private final ConsoleSoundProfile soundProfile = new GenericConsoleSoundProfile();
    private final ResourceLocation translationKey;

    public ConsoleTheme(ResourceLocation translationKey) {
        this.translationKey = translationKey;
        this.addControlSpecifications();
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

    public void replaceControl(RegistrySupplier<Control> replacementControl, int controlIndex) {
        try {
            controlSpecifications.get(controlIndex).setControl(replacementControl.get());
        } catch (IndexOutOfBoundsException exception) {
            TardisRefined.LOGGER.error("No control present at index " + controlIndex);
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
}
