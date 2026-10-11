package whocraft.tardis_refined.client.screen.screens;

import com.mojang.brigadier.StringReader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.client.screen.components.SelectionListEntry;
import whocraft.tardis_refined.client.screen.main.MonitorOS;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModel;
import whocraft.tardis_refined.client.model.blockentity.shell.ShellModelCollection;
import whocraft.tardis_refined.client.renderer.vortex.ShellRenderer;
import whocraft.tardis_refined.common.network.messages.C2SChangeShell;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;

import java.util.*;

public class ShellSelectionScreen extends MonitorOS.MonitorOSExtension {

    private static ShellPattern PATTERN = ShellPatterns.DEFAULT;
    private static Identifier previousShellTheme;
    private Button patternButton;

    public ShellSelectionScreen(Identifier currentShellTheme) {
        super(Component.translatable(ModMessages.UI_EXTERNAL_SHELL), currentShellTheme);
    }

    @Override
    public Identifier getPatternForRender() {
        return PATTERN.id();
    }

    @Override
    protected void init() {
        super.init();
        this.setEvents(
                () -> selectShell(CURRENTSHELLTHEME),
                () -> {
                    //selectShell(CURRENTSHELLTHEME);
                    if (PREVIOUS != null)
                        this.switchScreenToLeft(PREVIOUS);
                }
        );

        int vPos = (height - monitorHeight) / 2;

        addSubmitButton(width / 2 + 90, height - vPos - 25);
        addCancelButton(width / 2 - 11, height - vPos - 25);



        patternButton = addRenderableWidget(Button.builder(Component.literal(""), button -> {
            PATTERN = ShellPatterns.next(PATTERNCOLLECTION, PATTERN);
            button.setMessage(whocraft.tardis_refined.common.util.ComponentJson.parse(PATTERN.name()));
        }).pos(width / 2 + 14, height - vPos - 25).size(70, 20).build());
        boolean themeHasPatterns = PATTERNCOLLECTION.size() > 1;
        patternButton.visible = themeHasPatterns;
        if (themeHasPatterns) //Update the button name now that we have confirmed that there is more than one pattern in the shell
            this.patternButton.setMessage(whocraft.tardis_refined.common.util.ComponentJson.parse(PATTERN.name()));
    }

    @Override
    public void renderBackdrop(@NotNull GuiGraphicsExtractor guiGraphics) {
        super.renderBackdrop(guiGraphics);

        int hPos = (width - monitorWidth) / 2;
        int vPos = (height - monitorHeight) / 2;

        int b = height - vPos, r = width - hPos;
        int l2 = hPos + monitorWidth / 2;

        guiGraphics.fill(l2, vPos, r, b, -1072689136);

        guiGraphics.fill(l2 - 4, vPos, l2, b, 0x88000000);
    }

    @Override
    public void inMonitorRender(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderShell(guiGraphics, ShellRenderer.previewCenterX(width), ShellRenderer.previewCenterY(height), 15F);
    }

    @Override
    public GenericMonitorSelectionList<SelectionListEntry> createSelectionList() {
        int leftPos = width / 2;
        int topPos = (height - monitorHeight) / 2;
        GenericMonitorSelectionList<SelectionListEntry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, 105, 80, leftPos, topPos + 15, topPos + monitorHeight - 30, 12);

        List<Map.Entry<ResourceKey<ShellTheme>, ShellTheme>> values = ShellTheme.SHELL_THEME_DEFERRED_REGISTRY.entrySet().stream().toList();
        values = values.stream()
                .sorted(Comparator.comparing(theme -> theme.getValue().getDisplayName().toString()))
                .toList();

        for (Map.Entry<ResourceKey<ShellTheme>, ShellTheme> shellTheme : values) {
            Identifier shellThemeId = ShellTheme.getKey(shellTheme.getValue());

            String owner = Platform.getModName(shellTheme.getKey().identifier().getNamespace());
            Component tooltip = Component.literal(ChatFormatting.BLUE + owner);

            SelectionListEntry selectionListEntry = new SelectionListEntry(shellTheme.getValue().getDisplayName(), (entry) -> {
                previousShellTheme = CURRENTSHELLTHEME;
                CURRENTSHELLTHEME = shellThemeId;
                age = 0;

                for (Object child : selectionList.children()) {
                    if (child instanceof SelectionListEntry current) {
                        current.setChecked(false);
                    }
                }
                PATTERNCOLLECTION = ShellPatterns.getPatternCollectionForTheme(CURRENTSHELLTHEME);
                PATTERN = PATTERNCOLLECTION.get(0);

                boolean themeHasPatterns = PATTERNCOLLECTION.size() > 1;

                patternButton.visible = themeHasPatterns;

                if (themeHasPatterns) {
                    this.patternButton.setMessage(whocraft.tardis_refined.common.util.ComponentJson.parse(PATTERN.name()));
                }

                entry.setChecked(true);
            }, leftPos);

            selectionListEntry.setTooltip(tooltip);

            if (CURRENTSHELLTHEME.toString().equals(shellThemeId.toString())) {
                selectionListEntry.setChecked(true);
            }

            selectionList.addEntryPublic(selectionListEntry);
        }

        return selectionList;
    }

    public void selectShell(Identifier themeId) {
        assert Minecraft.getInstance().player != null;
        new C2SChangeShell(Minecraft.getInstance().player.level().dimension(), themeId, PATTERN).send();
        //Minecraft.getInstance().setScreenAndShow(null);
    }

    @Override
    public void renderShell(GuiGraphicsExtractor guiGraphics, int x, int y, float scale) {
        ShellPattern pattern = ShellPatterns.getPatternOrDefault(CURRENTSHELLTHEME, getPatternForRender());
        float fade = Math.min(1.0F, age / 10.0F);
        ShellRenderer.renderSelectedShell(guiGraphics, CURRENTSHELLTHEME, pattern.id(), x, y, 15F);
        if (fade >= 1.0F) previousShellTheme = null;
    }

}
