package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.Optional;


public class DesktopTheme {

    private final ResourceLocation uiTexture;
    public boolean availableByDefault = false;
    public ResourceLocation identifier;
    public String name = "";
    public ResourceLocation location;



    public DesktopTheme(ResourceLocation id, ResourceLocation location, boolean availableByDefault) {
        this.identifier = id;
        this.availableByDefault = availableByDefault;
        this.location = location;
        this.uiTexture = new ResourceLocation(location.getNamespace(), "textures/ui/interiors/" + id.getPath().toString() + ".png");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getTranslationKey(){
        return ModMessages.desktop(identifier.getPath());
    }

    public Component getDisplayName() {
        return Component.translatable(getTranslationKey());
    }

    public ResourceLocation getPreviewTexture(){
        return uiTexture;
    }

}
