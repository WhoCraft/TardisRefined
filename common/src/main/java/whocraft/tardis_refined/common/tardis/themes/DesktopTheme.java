package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.constants.ModMessages;


public class DesktopTheme {

    private final ResourceLocation uiTexture;
    public boolean availableByDefault = false;
    public String id = "";
    public ResourceLocation location;



    public DesktopTheme(String id, ResourceLocation location, boolean availableByDefault) {
        this.id = id;
        this.availableByDefault = availableByDefault;
        this.location = location;
        this.uiTexture = new ResourceLocation(location.getNamespace(), "textures/ui/interiors/" + id + ".png");
    }

    public String getTranslationKey(){
        return ModMessages.desktop(id);
    }

    public Component getDisplayName() {
        return Component.translatable(getTranslationKey());
    }

    public ResourceLocation getPreviewTexture(){
        return uiTexture;
    }

}
