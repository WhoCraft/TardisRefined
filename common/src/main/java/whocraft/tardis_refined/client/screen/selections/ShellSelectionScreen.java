package whocraft.tardis_refined.client.screen.selections;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import whocraft.tardis_refined.client.screen.components.GenericMonitorSelectionList;
import whocraft.tardis_refined.common.network.messages.ChangeShellMessage;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

import java.util.Arrays;
import java.util.List;

public class ShellSelectionScreen extends SelectionScreen {

    private List<ShellTheme> themeList;
    private ShellTheme currentShellTheme;

    public ShellSelectionScreen() {
        super(Component.translatable("tardis_refined.gui.shell_selection"));
        this.themeList = List.of(ShellTheme.values());
    }

    @Override
    protected void init() {
        this.setEvents(() -> {
            ShellSelectionScreen.selectShell(currentShellTheme);
        }, ()-> {
            Minecraft.getInstance().setScreen(null);
        });
        this.currentShellTheme = this.themeList.get(0);

        super.init();
    }

    public static void selectShell(ShellTheme theme) {
        new ChangeShellMessage(Minecraft.getInstance().player.getLevel().dimension(), theme).send();
        Minecraft.getInstance().setScreen(null);
    }

    @Override
    public Component getSelectedDisplayName() {
        return currentShellTheme.getDisplayName();
    }

    @Override
    public ObjectSelectionList createSelectionList() {
        var selectionList = new GenericMonitorSelectionList(this.minecraft, this.listWidth, this.listHeight, 0, 100, 15);
        selectionList.setRenderBackground(false);
        selectionList.setRenderTopAndBottom(false);
        Arrays.stream(ShellTheme.values()).toList().forEach(x -> selectionList.children().add(new GenericMonitorSelectionList.Entry(x.getDisplayName(), () -> {
            this.currentShellTheme = x;
        })));
        return selectionList;
    }
}
