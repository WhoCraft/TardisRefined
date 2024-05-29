package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface Theme {

    public String getTranslationKey();

    Component getDisplayName();
}
