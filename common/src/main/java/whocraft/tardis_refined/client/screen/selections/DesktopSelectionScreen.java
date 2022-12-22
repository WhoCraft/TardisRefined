package whocraft.tardis_refined.client.screen.selections;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.ModMessages;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.common.network.messages.ChangeDesktopMessage;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;

import java.util.List;

public class DesktopSelectionScreen extends SelectionScreen {

    private List<DesktopTheme> themeList;
    private DesktopTheme currentDesktopTheme;


    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;


    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/ui/desktop.png");

    public DesktopSelectionScreen() {
        super(Component.translatable(ModMessages.UI_DESKTOP_SELECTION));
        this.themeList = TardisDesktops.DESKTOPS;
    }

    @Override
    protected void init() {
        this.setEvents(() -> {
            DesktopSelectionScreen.selectDesktop(currentDesktopTheme);
        }, () -> {
            Minecraft.getInstance().setScreen(null);
        });
        this.currentDesktopTheme = this.themeList.get(0);

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        super.init();
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);


        /*Render Back drop*/
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MONITOR_TEXTURE);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        super.render(poseStack, i, j, f);
    }

    public static void selectDesktop(DesktopTheme theme) {
        new ChangeDesktopMessage(Minecraft.getInstance().player.getLevel().dimension(), theme).send();
        Minecraft.getInstance().setScreen(null);
    }

    @Override
    public Component getSelectedDisplayName() {
        return currentDesktopTheme.getDisplayName();
    }

    @Override
    public ObjectSelectionList createSelectionList() {
        var selectionList = new GenericMonitorSelectionList(this.minecraft, width / 2 - (Minecraft.getInstance().options.guiScale().get() * 10), height / 2 - 60, 50, 80, 12);
        selectionList.setRenderBackground(false);
        selectionList.setRenderTopAndBottom(false);

        var desktops = TardisDesktops.DESKTOPS.stream().filter(x -> x.availableByDefault);
        desktops.forEach(x -> selectionList.children().add(new GenericMonitorSelectionList.Entry(x.getDisplayName(), () -> {
            this.currentDesktopTheme = x;
        })));
        return selectionList;
    }

}
