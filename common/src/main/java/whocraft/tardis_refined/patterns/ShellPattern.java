package whocraft.tardis_refined.patterns;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

public class ShellPattern extends BasePattern<ShellTheme>{

    private final ResourceLocation interiorDoorTexture;

    public ShellPattern(ShellTheme consoleTheme, ResourceLocation identifier, ResourceLocation texture, ResourceLocation interiorDoor) {
        super(consoleTheme, identifier, texture);
        this.interiorDoorTexture = interiorDoor;
    }

    public ResourceLocation interiorDoorTexture() {
        return interiorDoorTexture;
    }
}
