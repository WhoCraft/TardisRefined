package whocraft.tardis_refined.client.screen.components;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import whocraft.tardis_refined.client.screen.ScreenHelper;

public class GenericMonitorSelectionList<T extends ObjectSelectionList.Entry<T>> extends ObjectSelectionList<T> {
    public GenericMonitorSelectionList(Minecraft minecraft, int x, int y, int width, int height, int itemHeight) {
        super(minecraft, width, height, y, y + height, itemHeight);
        this.setLeftPos(x);
        this.setRenderHeader(false, 0);
        this.setRenderTopAndBottom(false);
        this.setRenderSelection(false);
        this.setRenderBackground(true);
    }

    protected int getScrollbarPosition() {
        return this.x0 + this.width;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
    }


    @Environment(EnvType.CLIENT)
    public static class Entry extends ObjectSelectionList.Entry<Entry> {

        private final Component itemDisplayName;
        private final GenericListSelection press;
        private boolean checked = false;

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
            press.onClick(this);
            return super.mouseClicked(d, e, i);
        }

        @Override
        public void render(PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            int color = isMouseOver ? ChatFormatting.YELLOW.getColor() : (checked ? ChatFormatting.YELLOW.getColor() : ChatFormatting.GOLD.getColor());
            ScreenHelper.renderWidthScaledText((checked ? "> " : "") + itemDisplayName.getString(), poseStack, Minecraft.getInstance().font, left + 80, top, color, width, false);
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }

    @FunctionalInterface
    public interface GenericListSelection {
        void onClick(GenericMonitorSelectionList.Entry entry);
    }
}


