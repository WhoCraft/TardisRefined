package whocraft.tardis_refined.client.screen.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PresetFlatWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Optional;

public class GenericMonitorSelectionList extends ObjectSelectionList<GenericMonitorSelectionList.Entry> {

    public GenericMonitorSelectionList(Minecraft minecraft, int i, int j, int k, int l, int m) {
        super(minecraft, i, j, k, l, m);
    }

    @Override
    public Optional<GuiEventListener> getChildAt(double d, double e) {
        return super.getChildAt(d, e);
    }

    @Override
    public void mouseMoved(double d, double e) {
        super.mouseMoved(d, e);
    }

    @Override
    public boolean keyReleased(int i, int j, int k) {
        return super.keyReleased(i, j, k);
    }

    @Override
    public boolean charTyped(char c, int i) {
        return super.charTyped(c, i);
    }

    @Override
    public void setInitialFocus(@Nullable GuiEventListener guiEventListener) {
        super.setInitialFocus(guiEventListener);
    }

    @Override
    public void magicalSpecialHackyFocus(@Nullable GuiEventListener guiEventListener) {
        super.magicalSpecialHackyFocus(guiEventListener);
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {

        super.render(poseStack, i, j, f);
    }

    @Environment(EnvType.CLIENT)
    public static class Entry extends ObjectSelectionList.Entry<Entry> {

        private Component itemDisplayName;
        private GenericListSelection press;

        public Entry(Component name, GenericListSelection onSelection) {
            this.itemDisplayName = name;
            this.press = onSelection;
        }
        @Override
        public Component getNarration() {
            return itemDisplayName;
        }

        @Override
        public boolean mouseClicked(double d, double e, int i) {
            press.onClick();
            return super.mouseClicked(d, e, i);
        }

        @Override
        public void render(PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            drawString(poseStack, Minecraft.getInstance().font, itemDisplayName.getString(), left, top, Color.WHITE.getRGB());
        }
    }

    @FunctionalInterface
    public interface GenericListSelection {
        void onClick();
    }
}


