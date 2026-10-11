package whocraft.tardis_refined.client.screen.screens;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.renderer.vortex.VortexRenderer;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.client.screen.components.SelectionListEntry;
import whocraft.tardis_refined.client.screen.main.MonitorOS;
import whocraft.tardis_refined.common.VortexRegistry;
import whocraft.tardis_refined.common.network.messages.C2SChangeVortex;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class VortexSelectionScreen extends MonitorOS {

    private final List<Identifier> vortexList;

    public VortexSelectionScreen(Identifier currentVortex) {
        super(Component.translatable(ModMessages.UI_MONITOR_VORTEX), null);
        this.vortexList = VortexRegistry.VORTEX_DEFERRED_REGISTRY.keySet().stream().toList();
        VortexSelectionScreen.currentVortex = currentVortex;
    }

    @Override
    protected void init() {
        super.init();
        this.setEvents(() -> selectVortex(currentVortex), () -> {
            //selectVortex(currentVortex);
            if (PREVIOUS != null)
                this.switchScreenToLeft(PREVIOUS);
        });
        if (currentVortex == null)
            currentVortex = this.vortexList.get(0);
        int vPos = (height - monitorHeight) / 2;
        addSubmitButton(width / 2 + 90, height - vPos - 25);
        addCancelButton(width / 2 - 11, height - vPos - 25);
    }

    public void selectVortex(Identifier themeId) {
        assert Minecraft.getInstance().player != null;
        new C2SChangeVortex(Minecraft.getInstance().player.level().dimension(), themeId).send();
        //Minecraft.getInstance().setScreenAndShow(null);
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
    public GenericMonitorSelectionList<SelectionListEntry> createSelectionList() {
        int leftPos = width / 2;
        int topPos = (height - monitorHeight) / 2;
        GenericMonitorSelectionList<SelectionListEntry> selectionList = new GenericMonitorSelectionList<>(this.minecraft, 105, 80, leftPos, topPos + 15, topPos + monitorHeight - 30, 12);


        List<Map.Entry<ResourceKey<VortexRegistry>, VortexRegistry>> values = VortexRegistry.VORTEX_DEFERRED_REGISTRY.entrySet().stream().toList();
        values = values.stream()
                .sorted(Comparator.comparing(theme -> theme.getValue().getDisplayName().toString()))
                .toList();

        for (Map.Entry<ResourceKey<VortexRegistry>, VortexRegistry> vort : values) {
            Identifier vortId = VortexRegistry.VORTEX_DEFERRED_REGISTRY.getKey(vort.getValue());
            String owner = Platform.getModName(vort.getKey().identifier().getNamespace());
            Component tooltip = Component.literal(ChatFormatting.BLUE + owner);

            SelectionListEntry selectionListEntry = new SelectionListEntry(vort.getValue().getDisplayName(), (entry) -> {
                currentVortex = vortId;

                for (Object child : selectionList.children()) {
                    if (child instanceof SelectionListEntry current) {
                        current.setChecked(false);
                    }
                }

                entry.setChecked(true);
            }, leftPos);

            selectionListEntry.setTooltip(tooltip);

            if (currentVortex.toString().equals(vortId.toString())) {
                selectionListEntry.setChecked(true);
            }

            selectionList.addEntryPublic(selectionListEntry);
        }

        return selectionList;
    }

}
