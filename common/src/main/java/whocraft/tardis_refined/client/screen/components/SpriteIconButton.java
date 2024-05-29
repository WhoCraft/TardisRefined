package whocraft.tardis_refined.client.screen.components;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

// I literally just grabbed this from 1.20.2
public abstract class SpriteIconButton
        extends Button {
    protected final ResourceLocation sprite;
    protected final int spriteWidth;
    protected final int spriteHeight;

    SpriteIconButton(int i, int j, Component component, int k, int l, ResourceLocation resourceLocation, Button.OnPress onPress) {
        super(0, 0, i, j, component, onPress, DEFAULT_NARRATION);
        this.spriteWidth = k;
        this.spriteHeight = l;
        this.sprite = resourceLocation;
    }

    public static Builder builder(Component component, Button.OnPress onPress, boolean bl) {
        return new Builder(component, onPress, bl);
    }

    @Environment(value = EnvType.CLIENT)
    public static class Builder {
        private final Component message;
        private final Button.OnPress onPress;
        private final boolean iconOnly;
        private int width = 150;
        private int height = 20;
        @Nullable
        private ResourceLocation sprite;
        private int spriteWidth;
        private int spriteHeight;

        public Builder(Component component, Button.OnPress onPress, boolean bl) {
            this.message = component;
            this.onPress = onPress;
            this.iconOnly = bl;
        }

        public Builder width(int i) {
            this.width = i;
            return this;
        }

        public Builder size(int i, int j) {
            this.width = i;
            this.height = j;
            return this;
        }

        public Builder sprite(ResourceLocation resourceLocation, int i, int j) {
            this.sprite = resourceLocation;
            this.spriteWidth = i;
            this.spriteHeight = j;
            return this;
        }

        public SpriteIconButton build() {
            if (this.sprite == null) {
                throw new IllegalStateException("Sprite not set");
            }
            if (this.iconOnly) {
                return new CenteredIcon(this.width, this.height, this.message, this.spriteWidth, this.spriteHeight, this.sprite, this.onPress);
            }
            return new TextAndIcon(this.width, this.height, this.message, this.spriteWidth, this.spriteHeight, this.sprite, this.onPress);
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class TextAndIcon
            extends SpriteIconButton {
        protected TextAndIcon(int i, int j, Component component, int k, int l, ResourceLocation resourceLocation, Button.OnPress onPress) {
            super(i, j, component, k, l, resourceLocation, onPress);
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
            super.renderWidget(guiGraphics, i, j, f);
            int k = this.getX() + this.getWidth() - this.spriteWidth - 2;
            int l = this.getY() + this.getHeight() / 2 - this.spriteHeight / 2;
            guiGraphics.blit(this.sprite, 0, 0, this.spriteWidth, this.spriteHeight, k, l, 10, 10);
        }

        @Override
        public void renderString(GuiGraphics guiGraphics, Font font, int i) {
            int j = this.getX() + 2;
            int k = this.getX() + this.getWidth() - this.spriteWidth - 4;
            int l = this.getX() + this.getWidth() / 2;
            TextAndIcon.renderScrollingString(guiGraphics, font, this.getMessage(), l, j, this.getY(), k, this.getY() + this.getHeight());
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class CenteredIcon
            extends SpriteIconButton {
        protected CenteredIcon(int i, int j, Component component, int k, int l, ResourceLocation resourceLocation, Button.OnPress onPress) {
            super(i, j, component, k, l, resourceLocation, onPress);
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
            super.renderWidget(guiGraphics, i, j, f);
            int k = this.getX() + this.getWidth() / 2 - this.spriteWidth / 2;
            int l = this.getY() + this.getHeight() / 2 - this.spriteHeight / 2;
            guiGraphics.blit(this.sprite, 0, 0, this.spriteWidth, this.spriteHeight, k, l, 10, 10);
        }

        @Override
        public void renderString(GuiGraphics guiGraphics, Font font, int i) {
        }
    }
}
