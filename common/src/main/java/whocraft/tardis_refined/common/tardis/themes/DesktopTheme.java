package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;


public class DesktopTheme {

    private final ResourceLocation uiTexture;
    private ResourceLocation identifier;
    private String name = "";
    private ResourceLocation structureLocation;


    public DesktopTheme(String id, ResourceLocation structureLocation) {
        this(new ResourceLocation(TardisRefined.MODID, id), structureLocation);
    }


    public DesktopTheme(ResourceLocation id, ResourceLocation structureLocation) {
        this.identifier = id;
        this.structureLocation = structureLocation;
        this.uiTexture = new ResourceLocation(id.getNamespace(), "textures/ui/interiors/" + id.getPath().toString() + ".png");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getPreviewTexture(){
        return uiTexture;
    }

    public ResourceLocation getIdentifier() {
        return identifier;
    }
    public ResourceLocation getStructureLocation() {
        return structureLocation;
    }
}
