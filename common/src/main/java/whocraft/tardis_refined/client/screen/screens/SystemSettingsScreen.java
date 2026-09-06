package whocraft.tardis_refined.client.screen.screens;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.screen.main.MonitorOS;
import whocraft.tardis_refined.common.capability.tardis.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.network.messages.player.C2SSetSystemSetting;
import whocraft.tardis_refined.common.tardis.manager.SettingsHandler;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRUpgrades;

import java.util.*;
import java.util.function.BooleanSupplier;

public class SystemSettingsScreen extends MonitorOS {

    private final UpgradeHandler upgradeHandler;

    public SystemSettingsScreen(UpgradeHandler upgradeHandler) {
        super(Component.translatable(ModMessages.UI_MONITOR_SETTINGS), new ResourceLocation(TardisRefined.MODID, "textures/gui/monitor/backdrop.png"));
        this.upgradeHandler = upgradeHandler;
    }

    private TardisClientData getData() {
        return TardisClientData.getInstance(minecraft.player.level().dimension());
    }

    @Override
    protected void init() {
        super.init();
        this.setEvents(
                () -> {},
                this::back
        );

        int vPos = (height - monitorHeight) / 2;

        int settingsWidth = 180;

        var settings = addRenderableWidget(new OptionsList(
                width / 2 - 85, height/2 - 50,
                settingsWidth, 100,
                Component.literal("")
        ));

        String materializeAround = Util.makeDescriptionId(
                "upgrade", TRUpgrades.UPGRADE_DEFERRED_REGISTRY.getKey(
                        TRUpgrades.MATERIALIZE_AROUND.get()
                )
        );

        var materialiseAroundOffButton = new BooleanToggleButton(
                140, 1, materializeAround,
                ModMessages.UI_MONITOR_SETTINGS_ON, ModMessages.UI_MONITOR_SETTINGS_OFF,
                ModMessages.UI_MONITOR_SETTINGS_UNAVAILABLE_ARS,
                TRUpgrades.MATERIALIZE_AROUND.get().isUnlocked(upgradeHandler),
                () -> getData().getSettingsHandler().getSetting(SettingsHandler.MATERIALIZE_AROUND.get()).orElse(false),
                value -> new C2SSetSystemSetting<>(SettingsHandler.MATERIALIZE_AROUND.get(), value).send()
        );

        settings.add(
                new OptionsRow()
                        .addWidget(
                                new MultiLineTextWidget(
                                        Component.translatable(materializeAround).withStyle(
                                                style -> materialiseAroundOffButton.active ? style : style.withColor(ChatFormatting.DARK_GRAY)
                                        ),
                                        minecraft.font
                                ).setMaxWidth(100),
                                15, 6
                        )
                        .addWidget(materialiseAroundOffButton)
        );

        addCancelButton(width / 2 - 105, height - vPos - 25);
    }

    public static class BooleanToggleButton extends Button {

        private final String onSuffix;
        private final String offSuffix;
        private final String unavailableSuffix;
        private final BooleanSupplier getter;
        private State prevState;

        protected BooleanToggleButton(
                int x, int y, String generalMessage, String onSuffix, String offSuffix, String unavailableSuffix,
                boolean active, BooleanSupplier getter, BooleanConsumer setter
        ) {
            super(
                    x, y, 20, 20,
                    message(onSuffix, offSuffix, unavailableSuffix, getState(active, getter.getAsBoolean())),
                    button -> {
                        setter.accept(!getter.getAsBoolean());
                    },
                    narration -> Component.translatable(generalMessage).append(" ").append(narration.get())
            );
            this.active = active;
            this.onSuffix = onSuffix;
            this.offSuffix = offSuffix;
            this.unavailableSuffix = unavailableSuffix;
            this.getter = getter;
        }

        @Override
        public @NotNull Component getMessage() {
            return message(onSuffix, offSuffix, unavailableSuffix, getState());
        }

        private static Component message(
                String onSuffix, String offSuffix, String unavailableSuffix, State state
        ) {
            return Component.translatable(
                    switch (state) {
                        case ON -> onSuffix;
                        case OFF -> offSuffix;
                        case UNAVAILABLE -> unavailableSuffix;
                    }
            );
        }

        @Override
        public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            this.renderTexture(
                    guiGraphics, getState().texture(),
                    this.getX(), this.getY(),
                    0, 0, 20,
                    this.width, this.height,
                    20, getState().textureHeight()
            );
            if (getState() != prevState) {
                setTooltip(Tooltip.create(getMessage(), null));
                prevState = getState();
            }
        }

        private static State getState(boolean available, boolean on) {
            return available ? on ? State.ON : State.OFF : State.UNAVAILABLE;
        }

        public State getState() {
            return getState(active, getter.getAsBoolean());
        }

        public enum State {
            ON(new ResourceLocation(TardisRefined.MODID, "textures/gui/sprites/okay.png"), 40),
            OFF(new ResourceLocation(TardisRefined.MODID, "textures/gui/sprites/cancel.png"), 40),
            UNAVAILABLE(new ResourceLocation(TardisRefined.MODID, "textures/gui/sprites/control/control_dead.png"), 20);

            private final ResourceLocation texture;
            private final int textureHeight;

            State(ResourceLocation texture, int textureHeight) {
                this.texture = texture;
                this.textureHeight = textureHeight;
            }

            public ResourceLocation texture() {
                return texture;
            }

            public int textureHeight() {
                return textureHeight;
            }
        }
    }

    public static class OptionsList extends AbstractScrollWidget implements ContainerEventHandler {


        @Nullable
        private NarratableEntry lastNarratable;

        private int offset = 0;
        private final List<OptionsRow> rows = new ArrayList<>();
        private boolean dragging = false;
        private GuiEventListener focused;

        public OptionsList(int x, int y, int width, int height, Component message) {
            super(x, y, width, height, message);
        }

        public void add(OptionsRow row) {
            row.setPosition(getX(), getY() + offset);
            rows.add(row);
            offset += row.rowHeight();
        }

        @Override
        protected int getInnerHeight() {
            return offset;
        }

        @Override
        protected double scrollRate() {
            return 9 / 2.0;
        }

        @Override
        protected void renderContents(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            rows.forEach(row -> row.render(guiGraphics, mouseX, (int) getMouseY(mouseY), partialTick));
        }

        @Override
        protected void renderBackground(@NotNull GuiGraphics guiGraphics) {

        }

        @Override
        protected void renderDecorations(@NotNull GuiGraphics guiGraphics) {
            if (this.scrollbarVisible()) {
                int minX = this.getX() + this.width;
                int maxX = this.getX() + this.width + 8;
                guiGraphics.fill(minX, getY(), maxX, getY()+getHeight(), -16777216);
            }
            super.renderDecorations(guiGraphics);
        }

        @Override
        public @NotNull Optional<GuiEventListener> getChildAt(double mouseX, double mouseY) {
            return ContainerEventHandler.super.getChildAt(mouseX, getMouseY(mouseY));
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (ContainerEventHandler.super.mouseClicked(mouseX, getMouseY(mouseY), button)) {
                return true;
            } else {
                return super.mouseClicked(mouseX, mouseY, button);
            }
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            if (ContainerEventHandler.super.mouseReleased(mouseX, getMouseY(mouseY), button)) {
                return true;
            } else {
                return super.mouseReleased(mouseX, mouseY, button);
            }
        }

        @Override
        public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
            if (ContainerEventHandler.super.mouseDragged(mouseX, getMouseY(mouseY), button, dragX, dragY)) {
                return true;
            } else {
                return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
            }
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
            if (ContainerEventHandler.super.mouseScrolled(mouseX, getMouseY(mouseY), delta)) {
                return true;
            } else {
                return super.mouseScrolled(mouseX, mouseY, delta);
            }
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            return ContainerEventHandler.super.keyPressed(keyCode, scanCode, modifiers);
        }

        @Override
        public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
            return ContainerEventHandler.super.keyReleased(keyCode, scanCode, modifiers);
        }

        @Override
        public boolean charTyped(char codePoint, int modifiers) {
            return ContainerEventHandler.super.charTyped(codePoint, modifiers);
        }

        protected double getMouseY(double mouseY) {
            return mouseY + scrollAmount();
        }

        // Mostly copy-pasted from Screen to add barebones narrator support.
        @Override
        protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
            List<? extends NarratableEntry> list = rows.stream().flatMap(row -> row.narratables().stream())
                    .filter(element -> !(element instanceof AbstractStringWidget))
                    .sorted(Comparator.comparingInt(TabOrderedElement::getTabOrderGroup))
                    .toList();

            Screen.NarratableSearchResult narratableSearchResult = findNarratableWidget(list, this.lastNarratable);
            if (narratableSearchResult != null) {
                if (narratableSearchResult.priority.isTerminal()) {
                    this.lastNarratable = narratableSearchResult.entry;
                }

                if (list.size() > 1) {
                    narrationElementOutput.add(NarratedElementType.POSITION, Component.translatable("narrator.position.screen", narratableSearchResult.index + 1, list.size()));
                    if (narratableSearchResult.priority == NarratableEntry.NarrationPriority.FOCUSED) {
                        narrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration.component_list.usage"));
                    }
                }

                narratableSearchResult.entry.updateNarration(narrationElementOutput.nest());
            }
        }

        @Override
        public @NotNull List<? extends GuiEventListener> children() {
            return rows.stream().flatMap(row -> row.children().stream()).toList();
        }

        @Override
        public boolean isDragging() {
            return dragging;
        }

        @Override
        public void setDragging(boolean isDragging) {
            this.dragging = isDragging;
        }

        @Override
        public @Nullable GuiEventListener getFocused() {
            return focused;
        }

        @Override
        public void setFocused(@Nullable GuiEventListener focused) {
            if (this.focused != null) {
                this.focused.setFocused(false);
            }
            if (focused != null) {
                focused.setFocused(true);
                if (focused instanceof LayoutElement layout) {
                    double topY = layout.getY() - getY() - scrollAmount();
                    double bottomY = layout.getY() + layout.getHeight() - getY() - scrollAmount();
                    if (topY < 0) {
                        setScrollAmount(scrollAmount() + topY - 5);
                    }
                    if (bottomY > height) {
                        setScrollAmount(scrollAmount() + (bottomY - height) + 5);
                    }
                }
            }
            this.focused = focused;
        }

        @Override
        @Nullable
        public ComponentPath nextFocusPath(@NotNull FocusNavigationEvent event) {
            return ContainerEventHandler.super.nextFocusPath(event);
        }
    }

    public static class OptionsRow {

        private final List<GuiEventListener> children = Lists.newArrayList();
        private final List<NarratableEntry> narratables = Lists.newArrayList();
        public final List<Renderable> renderables = Lists.newArrayList();

        private int x;
        private int yTop;
        private int yBottom;

        private void setPosition(int x, int y) {
            int xDiff = x - this.x;
            int yDiff = y - this.yTop;
            int height = yBottom - yTop;
            this.x = x;
            this.yTop = y;
            this.yBottom = yTop + height;
            for (var child : children) {
                if (child instanceof LayoutElement layout) {
                    layout.setPosition(layout.getX() + xDiff, layout.getY() + yDiff);
                }
            }
        }

        public OptionsRow addWidget(AbstractWidget widget) {
            fixPosition(widget);
            narratables.add(widget);
            renderables.add(widget);
            children.add(widget);
            return this;
        }

        public OptionsRow addWidget(AbstractWidget widget, int x, int y) {
            widget.setPosition(x, y);
            return addWidget(widget);
        }

        private void fixPosition(LayoutElement element) {
            element.setPosition(element.getX() + x, element.getY() + yTop);
            yBottom = Math.max(element.getY() + element.getHeight(), yBottom);
        }

        public @NotNull List<? extends NarratableEntry> narratables() {
            return narratables;
        }

        public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            renderables.forEach(renderable -> renderable.render(guiGraphics, mouseX, mouseY, partialTick));
        }

        public @NotNull List<? extends GuiEventListener> children() {
            return children;
        }

        public int rowHeight() {
            return yBottom - yTop;
        }
    }
}
