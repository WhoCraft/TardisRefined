package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.patterns.BasePattern;

import java.util.Locale;

public enum ShellTheme implements StringRepresentable, Theme, BasePattern.DataDrivenPattern {
    FACTORY("factory"),
    POLICE_BOX("police_box"),
    PHONE_BOOTH("phone_booth"),
    MYSTIC("mystic"),
    PRESENT("present"),
    DRIFTER("drifter"),
    VENDING("vending"),
    BRIEFCASE("briefcase"),
    GROENING("groening"),
    BIG_BEN("big_ben"),
    NUKA("nuka"),
    GROWTH("growth"),
    PORTALOO("portaloo"),
    PAGODA("pagoda");

    private final String id;

    ShellTheme(String id) {
        this.id = id;
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
