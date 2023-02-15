package whocraft.tardis_refined.client.screen.selections;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.common.network.messages.ChangeShellMessage;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.List;

public class ShellSelectionScreen extends SelectionScreen {

    private final List<ShellTheme> themeList;
    private ShellTheme currentShellTheme;

    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;


    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/ui/shell.png");

    public ShellSelectionScreen() {
        super(Component.translatable(ModMessages.UI_SHELL_SELECTION));
        this.themeList = List.of(ShellTheme.values());
    }

    @Override
    protected void init() {
        this.setEvents(() -> {
            ShellSelectionScreen.selectShell(currentShellTheme);
        }, () -> {
            Minecraft.getInstance().setScreen(null);
        });
        this.currentShellTheme = this.themeList.get(0);

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        addSubmitButton(width / 2 + 90, (height) / 2 + 35);
        addCancelButton(width / 2 - 11, (height) / 2 + 35);

        super.init();
    }

    public static void selectShell(ShellTheme theme) {
        new ChangeShellMessage(Minecraft.getInstance().player.getLevel().dimension(), theme).send();
        Minecraft.getInstance().setScreen(null);
    }


    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);

        /*Render Back drop*/
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MONITOR_TEXTURE);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        /*Model*/
        ShellModel model = ShellModelCollection.getInstance().getShellModel(currentShellTheme);


        model.setDoorPosition(false);

        Lighting.setupForFlatItems();
        int k = (int) this.minecraft.getWindow().getGuiScale();
        RenderSystem.viewport((this.width - 320) / 2 * k, (this.height - 240) / 2 * k, 320 * k, 240 * k);
        Matrix4f matrix4f = Matrix4f.createTranslateMatrix(-0.34F, 0.23F, 0.0F);
        matrix4f.multiply(Matrix4f.perspective(Integer.MAX_VALUE, 1.3333334F, 9.0F, Integer.MAX_VALUE));
        RenderSystem.backupProjectionMatrix();
        RenderSystem.setProjectionMatrix(matrix4f);


        poseStack.pushPose();
        PoseStack.Pose pose = poseStack.last();
        pose.pose().setIdentity();
        pose.normal().setIdentity();
        poseStack.translate(-3, -1.0, 1984.0);
        poseStack.scale(4.5F, 4.5F, 4.5F);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(minecraft.level.getGameTime()));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));

        MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        VertexConsumer vertexConsumer = bufferSource.getBuffer(model.renderType(model.texture()));
        model.renderToBuffer(poseStack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        bufferSource.endBatch();
        poseStack.popPose();
        RenderSystem.viewport(0, 0, this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
        RenderSystem.restoreProjectionMatrix();
        Lighting.setupFor3DItems();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        /*Render Widgets*/
        super.render(poseStack, i, j, f);

    }

    @Override
    public Component getSelectedDisplayName() {
        return currentShellTheme.getDisplayName();
    }

    @Override
    public GenericMonitorSelectionList createSelectionList() {
        GenericMonitorSelectionList<GenericMonitorSelectionList.Entry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, width / 2 - 50, height / 2 - 60, 150, 80, 12);

        selectionList.setRenderBackground(false);
        selectionList.setRenderTopAndBottom(false);

        for (ShellTheme shellTheme : ShellTheme.values()) {
            selectionList.children().add(new GenericMonitorSelectionList.Entry(shellTheme.getDisplayName(), (entry) -> {
                this.currentShellTheme = shellTheme;

                for (Object child : selectionList.children()) {
                    if (child instanceof GenericMonitorSelectionList.Entry current) {
                        current.setChecked(false);
                    }
                }
                entry.setChecked(true);

            }));
        }

        return selectionList;
    }
}
