package whocraft.tardis_refined.client.screen.components;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

public class CommonTRWidgets {

    public static Button imageButton(int width, MutableComponent component, Button.OnPress onPress, boolean iconOnly, Identifier texture) {
        return new ImageButton(0, 0, 20, 20, new WidgetSprites(texture), onPress, component);
    }
}
