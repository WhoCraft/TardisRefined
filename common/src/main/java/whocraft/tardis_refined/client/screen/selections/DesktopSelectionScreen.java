package whocraft.tardis_refined.client.screen.selections;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.common.network.messages.ChangeDesktopMessage;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.constants.ModMessages;

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
        this.themeList = TardisDesktops.DESKTOPS.stream().toList();
    }

    @Override
    protected void init() {
        this.setEvents(() -> {
            DesktopSelectionScreen.selectDesktop(currentDesktopTheme);
        }, () -> {
            Minecraft.getInstance().setScreen(null);
        });
        this.currentDesktopTheme = grabDesktop();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        addSubmitButton(width / 2 + 90, (height) / 2 + 35);
        addCancelButton(width / 2 + 40, (height) / 2 + 35);

        super.init();
    }

    private DesktopTheme grabDesktop() {
        for (DesktopTheme desktop : TardisDesktops.DESKTOPS) {
            if (desktop.availableByDefault) {
                return desktop;
            }
        }
        return null;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);


        /*Render Back drop*/
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MONITOR_TEXTURE);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        /*Render Interior Image*/
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, currentDesktopTheme.getPreviewTexture());
        poseStack.pushPose();
        poseStack.translate(width / 2 - 85, height / 2 - 60, 0);
        poseStack.scale(0.2F, 0.2F, 0.2F);
        blit(poseStack, 0, 0, 0, 0, 400, 400, 400, 400);
        poseStack.popPose();

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
        GenericMonitorSelectionList<GenericMonitorSelectionList.Entry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, width / 2, height / 2 - 60, 150, 80, 12);
        selectionList.setRenderBackground(false);
        selectionList.setRenderTopAndBottom(false);

        for (DesktopTheme desktop : TardisDesktops.DESKTOPS) {
            if (desktop.availableByDefault) {
                selectionList.children().add(new GenericMonitorSelectionList.Entry(desktop.getDisplayName(), (entry) -> {
                    this.currentDesktopTheme = desktop;

                    for (Object child : selectionList.children()) {
                        if (child instanceof GenericMonitorSelectionList.Entry current) {
                            current.setChecked(false);
                        }
                    }
                    entry.setChecked(true);
                }));
            }
        }

        return selectionList;
    }

}
