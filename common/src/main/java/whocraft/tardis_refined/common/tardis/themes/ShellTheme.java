package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.patterns.Pattern;

import java.util.Locale;

public enum ShellTheme implements StringRepresentable, Theme, Pattern.DataDrivenPattern {

    FACTORY("factory", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/factory_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/factory_shell_door.png")),
    POLICE_BOX("police_box", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/tdis_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/tdis_shell_door.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/tdis_shell_emissive.png")),
    PHONE_BOOTH("phone_booth", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/phone_booth_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/phone_booth_shell_door.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/phone_booth_shell_emissive.png")),
    MYSTIC("mystic", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/mystic_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/mystic_shell_door.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/mystic_shell_emissive.png")),
    PRESENT("present", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/present_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/present_shell_door.png")),
    DRIFTER("drifter", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/drifter_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/drifter_shell_door.png")),
    VENDING("vending", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/vending_machine_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/vending_machine_shell_door.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/vending_machine_shell_emissive.png")),
    BRIEFCASE("briefcase", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/briefcase_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/briefcase_shell_door.png")),
    GROENING("groening", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/groening_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/groening_shell_door.png")),
    BIG_BEN("big_ben", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/big_ben_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/big_ben_shell_door.png")),
    NUKA("nuka", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/nuka_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/nuka_shell_door.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/nuka_shell_emissive.png")),
    GROWTH("growth", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/growth_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/growth_shell_door.png")),
    PORTALOO("portaloo", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/portaloo_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/portaloo_shell_door.png")),
    PAGODA("pagoda", new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/pagoda_shell.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/pagoda_shell_door.png"), new ResourceLocation(TardisRefined.MODID, "textures/blockentity/shell/pagoda_emissive.png"));

    private final String id;
    private final ResourceLocation externalShellTexture, internalDoorTexture;
    private final ResourceLocation emmissiveExternal;

    ShellTheme(String id, ResourceLocation externalShellTexture, ResourceLocation internalDoorTexture) {
        this.id = id;
        this.externalShellTexture = externalShellTexture;
        this.internalDoorTexture = internalDoorTexture;
        this.emmissiveExternal = null;
    }

    ShellTheme(String id, ResourceLocation externalShellTexture, ResourceLocation internalDoorTexture, ResourceLocation emmissiveExternalShellTexture) {
        this.id = id;
        this.externalShellTexture = externalShellTexture;
        this.internalDoorTexture = internalDoorTexture;
        this.emmissiveExternal = emmissiveExternalShellTexture;
    }

    public ResourceLocation getExternalShellTexture() {
        return this.externalShellTexture;
    }

    public ResourceLocation getInternalDoorTexture() {
        return this.internalDoorTexture;
    }

    public ResourceLocation emmissiveExternal() {
        return emmissiveExternal;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }

    public String getTranslationKey() {
        return ModMessages.shell(id);
    }

    public Component getDisplayName() {
        return Component.translatable(getTranslationKey());
    }

    public static ShellTheme findOr(String id, ShellTheme shellTheme) {
        for (ShellTheme value : ShellTheme.values()) {
            if (value.name().toLowerCase(Locale.ENGLISH).matches(id.toLowerCase(Locale.ENGLISH))) {
                return value;
            }
        }
        return shellTheme;
    }

    @Override
    public String getObjectName() {
        return getSerializedName();
    }
}
