package whocraft.tardis_refined.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.constants.ModMessages;

public class TRKeybinds {

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(TardisRefined.MODID, "controls"));
    // GLFW key codes: Tab (258) and N (78).
    public static KeyMapping EXIT_EXTERIOR_VIEW = new KeyMapping(ModMessages.KEYBIND_EXIT_VIEW, 258, CATEGORY);
    public static KeyMapping TOGGLE_INFO_EXTERIOR_VIEW = new KeyMapping(ModMessages.KEYBIND_TOGGLE_INFO_EXTERIOR_VIEW, 78, CATEGORY);


}
