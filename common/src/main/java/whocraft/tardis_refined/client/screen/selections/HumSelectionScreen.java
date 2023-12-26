package whocraft.tardis_refined.client.screen.selections;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.StringReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.client.screen.components.SelectionListEntry;
import whocraft.tardis_refined.common.hum.HumEntry;
import whocraft.tardis_refined.common.hum.TardisHums;
import whocraft.tardis_refined.common.network.messages.ChangeDesktopMessage;
import whocraft.tardis_refined.common.network.messages.hums.ChangeHumMessage;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.SoundRegistry;

import static whocraft.tardis_refined.client.screen.selections.ShellSelectionScreen.NOISE;

public class HumSelectionScreen extends SelectionScreen {

    private HumEntry currentHumEntry;


    protected int imageWidth = 256;
    protected int imageHeight = 173;
    private int leftPos, topPos;

    public static ResourceLocation MONITOR_TEXTURE = new ResourceLocation(TardisRefined.MODID, "textures/gui/desktop.png");
    public static ResourceLocation MONITOR_TEXTURE_OVERLAY = new ResourceLocation(TardisRefined.MODID, "textures/gui/desktop_overlay.png");
    public static ResourceLocation previousImage = TardisDesktops.FACTORY_THEME.getPreviewTexture();

    public HumSelectionScreen() {
        super(Component.translatable(ModMessages.UI_DESKTOP_SELECTION));
    }

    @Override
    protected void init() {
        this.setEvents(() -> {
            HumSelectionScreen.selectHum(currentHumEntry);
        }, () -> {
            Minecraft.getInstance().setScreen(null);
        });
        this.currentHumEntry = grabHum();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        addSubmitButton(width / 2 + 90, (height) / 2 + 35);
        addCancelButton(width / 2 + 40, (height) / 2 + 35);

        super.init();
    }

    private HumEntry grabHum() {
        for (HumEntry humEntry : TardisHums.getRegistry().values()) {
            return humEntry;
        }
        return null;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        this.renderTransparentBackground(guiGraphics);

        PoseStack poseStack = guiGraphics.pose();

        /*Render Back drop*/
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(MONITOR_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);


   /*     *//*Render Interior Image*//*
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.pushPose();
        poseStack.translate(width / 2 - 110, height / 2 - 72, 0);
        poseStack.scale(0.31333333F, 0.31333333F, 0.313333330F);

        guiGraphics.blit(currentDesktopTheme.getPreviewTexture(), 0, 0, 0, 0, 400, 400, 400, 400);

        double alpha = (100.0D - this.age * 3.0D) / 100.0D;
        RenderSystem.enableBlend();

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, (float) alpha);
        guiGraphics.blit(previousImage, (int) ((Math.random() * 14) - 2), (int) ((Math.random() * 14) - 2), 400, 400, 400, 400);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, (float) alpha);
        RenderSystem.setShaderTexture(0, NOISE);
        guiGraphics.blit(NOISE, 0, 0, this.noiseX, this.noiseY, 400, 400);
        RenderSystem.disableBlend();
        poseStack.popPose();*/


        /*Render Back drop*/
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(MONITOR_TEXTURE_OVERLAY, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        super.render(guiGraphics, i, j, f);


    }

    public static void selectHum(HumEntry theme) {
        new ChangeHumMessage(Minecraft.getInstance().player.level().dimension(), theme).send();
        Minecraft.getInstance().setScreen(null);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {

    }

    @Override
    public Component getSelectedDisplayName() {
        return Component.Serializer.fromJson(currentHumEntry.getName());
    }

    @Override
    public ObjectSelectionList createSelectionList() {
        int leftPos = width / 2 + 45;
        GenericMonitorSelectionList<SelectionListEntry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, 57, 80, leftPos, this.topPos + 30, this.topPos + this.imageHeight - 60, 12);
        selectionList.setRenderBackground(false);

        for (HumEntry humEntry : TardisHums.getRegistry().values()) {
            System.out.println(humEntry);
            Component name = Component.literal(MiscHelper.getCleanName(humEntry.getIdentifier().getPath()));

            // Check for if the tellraw name is incomplete, or fails to pass.
            try {
                var json = Component.Serializer.fromJson(new StringReader(humEntry.getName()));
                name = json;
            } catch (Exception ex) {
                TardisRefined.LOGGER.error("Could not process Name for datapack desktop " + humEntry.getIdentifier().toString());
            }

            selectionList.children().add(new SelectionListEntry(name, (entry) -> {
               // previousImage = humEntry.getPreviewTexture();
                this.currentHumEntry = humEntry;

                for (Object child : selectionList.children()) {
                    if (child instanceof SelectionListEntry current) {
                        current.setChecked(false);
                    }
                }
                entry.setChecked(true);
                age = 0;
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundRegistry.STATIC.get(), (float) Math.random()));
            }, leftPos));
        }

        return selectionList;
    }

}