package whocraft.tardis_refined.client.screen.components;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class CommonTRWidgets {

    public static SpriteIconButton imageButton(int width, MutableComponent component, Button.OnPress onPress, boolean iconOnly, ResourceLocation texture) {
        return SpriteIconButton.builder(component, onPress, iconOnly).width(width).sprite(texture, 20, 20).build();
    }

    public static SpriteIconButton imageButton(int width, MutableComponent component, Button.OnPress onPress, ResourceLocation texture, int spriteWidth, int spriteHeight) {
        return SpriteIconButton.builder(component, onPress, true).width(width).sprite(texture, spriteWidth, spriteHeight).build();
    }
}
