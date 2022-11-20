package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import whocraft.tardis_refined.TardisRefined;

public enum ShellTheme implements StringRepresentable {

    FACTORY("factory", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/factory_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/factory_shell_door.png")),
    // This doesn't exist, but we need at least two to work this!
    COPPER("copper", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/copper_shell.png"),new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/copper_shell_door.png"));


    private final String id;
    private final ResourceLocation externalShellTexture;
    private final ResourceLocation internalDoorTexture;

    ShellTheme(String id, ResourceLocation externalShellTexture, ResourceLocation internalDoorTexture)
    {
        this.id = id;
        this.externalShellTexture = externalShellTexture;
        this.internalDoorTexture = internalDoorTexture;
    }

    public ResourceLocation getExternalShellTexture() {
        return this.externalShellTexture;
    }
    public ResourceLocation getInternalDoorTexture() {
        return this.internalDoorTexture;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }
}
