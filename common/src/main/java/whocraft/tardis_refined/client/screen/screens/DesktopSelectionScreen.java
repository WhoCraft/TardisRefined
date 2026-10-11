package whocraft.tardis_refined.client.screen.screens;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.ARGB;
import org.joml.Matrix3x2fStack;
import com.mojang.brigadier.StringReader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.client.screen.components.SelectionListEntry;
import whocraft.tardis_refined.client.screen.main.MonitorOS;
import whocraft.tardis_refined.common.network.messages.C2SChangeDesktop;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import java.util.Collection;
import java.util.Comparator;

public class DesktopSelectionScreen extends MonitorOS {

    public static Logger LOGGER = LogManager.getLogger("TardisRefined/DesktopSelectionScreen");

    public static Identifier previousImage = TardisDesktops.FACTORY_THEME.getPreviewTexture();
    private DesktopTheme currentDesktopTheme;

    public DesktopSelectionScreen() {
        super(Component.translatable(ModMessages.UI_DESKTOP_CONFIGURATION), Identifier.fromNamespaceAndPath(TardisRefined.MODID, "textures/gui/monitor/backdrop.png"));
    }

    public static void selectDesktop(DesktopTheme theme) {
        assert Minecraft.getInstance().player != null;
        new C2SChangeDesktop(Minecraft.getInstance().player.level().dimension(), theme).send();
        Minecraft.getInstance().setScreenAndShow(null);
    }

    @Override
    protected void init() {
        super.init();
        this.setEvents(() -> DesktopSelectionScreen.selectDesktop(currentDesktopTheme), () -> {
            if (PREVIOUS != null)
                this.switchScreenToLeft(PREVIOUS);
        });
        this.currentDesktopTheme = grabDesktop();
        if (this.currentDesktopTheme != null) previousImage = this.currentDesktopTheme.getPreviewTexture();
        int vPos = (height - monitorHeight) / 2;
        addSubmitButton(width / 2 + 25, height - vPos - 25);
        addCancelButton(width / 2 + 5, height - vPos - 25);
    }

    private DesktopTheme grabDesktop() {
        for (DesktopTheme desktop : TardisDesktops.getRegistry().values()) {
            return desktop;
        }
        return null;
    }

    @Override
    public void renderBackdrop(@NotNull GuiGraphicsExtractor guiGraphics) {
        super.renderBackdrop(guiGraphics);
    }

    @Override
    public void inMonitorRender(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {

        Matrix3x2fStack poseStack = guiGraphics.pose();
        int hPos = (width - monitorWidth) / 2;
        int vPos = (height - monitorHeight) / 2;

        /*Render Interior Image*/
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, backdrop, 5 + width / 2, -50 + height / 2, 0, monitorHeight, 110, 95, 256, 256);
        poseStack.pushMatrix();
        int trim = 15;
        poseStack.translate(hPos + trim - 5, vPos + trim + 5);
        float scale = (monitorHeight - 2 * trim) / 400.0f;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, backdrop, -5, -5, 0, monitorHeight, 110, 110, 256, 256);
        poseStack.scale(scale, scale);
        float fade = Math.min(1.0F, age / 10.0F);
        if (fade < 1.0F && previousImage != null) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, previousImage, 0, 0, 0, 0,
                    400, 400, 400, 400, ARGB.colorFromFloat(1.0F - fade, 1, 1, 1));
        }
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, currentDesktopTheme.getPreviewTexture(), 0, 0, 0, 0,
                400, 400, 400, 400, ARGB.colorFromFloat(fade, 1, 1, 1));

        // Keep the intentional static transition over the desktop preview while
        // the new image fades in.
        float staticAlpha = Math.max(0.0F, (100.0F - age * 3.0F) / 100.0F);
        if (staticAlpha > 0.0F) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, NOISE, 0, 0,
                    (int) (Math.random() * 736), (int) (414 * (System.currentTimeMillis() % 1000) / 1000.0),
                    400, 400, 736, 414, ARGB.colorFromFloat(staticAlpha, 1, 1, 1));
        }
        poseStack.popMatrix();

    }

    @Override
    public ObjectSelectionList<SelectionListEntry> createSelectionList() {
        int leftPos = 5 + width / 2;
        int topPos = (height - monitorHeight) / 2;
        GenericMonitorSelectionList<SelectionListEntry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, 100, 80, leftPos, topPos + 15, topPos + monitorHeight - 30, 12);

        Collection<DesktopTheme> values = TardisDesktops.getRegistry().values();
        values = values.stream().sorted(Comparator.comparing(DesktopTheme::getName)).toList();

        for (DesktopTheme desktop : values) {
            Component name = Component.literal(MiscHelper.getCleanName(desktop.getIdentifier().getPath()));
            // Attempt to parse the name from JSON
            try {
                name = whocraft.tardis_refined.common.util.ComponentJson.parse(desktop.getName());
            } catch (Exception ex) {
                LOGGER.error("Could not process Name for datapack desktop {}", desktop.getIdentifier().toString());
            }

            Component tooltip = Component.literal(ChatFormatting.BLUE + Platform.getModName(desktop.getIdentifier().getNamespace()));

            SelectionListEntry entry = new SelectionListEntry(name, (selectedEntry) -> {
                previousImage = currentDesktopTheme.getPreviewTexture();
                this.currentDesktopTheme = desktop;

                for (Object child : selectionList.children()) {
                    if (child instanceof SelectionListEntry current) {
                        current.setChecked(false);
                    }
                }
                selectedEntry.setChecked(true);
                age = 0;

                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(TRSoundRegistry.STATIC.get(), (float) Math.random()));
            }, leftPos);

            entry.setTooltip(tooltip);

            selectionList.addEntryPublic(entry);
        }

        return selectionList;
    }


}
