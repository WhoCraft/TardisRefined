package whocraft.tardis_refined.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.EntityRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;

public class LangProviderEnglish extends LanguageProvider {

    public LangProviderEnglish(DataGenerator gen) {
        super(gen, TardisRefined.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(BlockRegistry.ARS_EGG.get(), "ARS Egg");
        add(BlockRegistry.ARS_LEAVES.get(), "ARS Leaves");
        add(BlockRegistry.ARS_LEAVES_FENCE.get(), "ARS Fence");
        add(BlockRegistry.ARS_LEAVES_SLAB.get(), "ARS Slab");
        add(BlockRegistry.BULK_HEAD_DOOR.get(), "Bulk Head Door");
        add(BlockRegistry.ROOT_PLANT_BLOCK.get(), "Root Plant");
        add(BlockRegistry.ROOT_SHELL_BLOCK.get(), "Root Shell");
        add(BlockRegistry.TERRAFORMER_BLOCK.get(), "Terraformer");
        add(BlockRegistry.GLOBAL_CONSOLE_BLOCK.get(), "Console");
        add(BlockRegistry.INTERNAL_DOOR_BLOCK.get(), "Internal Door");
        add(BlockRegistry.GLOBAL_DOOR_BLOCK.get(), "Tardis Door");

        add(ItemRegistry.KEY.get(), "Tardis Key");
        add(KeyItem.KEYCHAIN, "Tardis Keyset");

        add(EntityRegistry.CONTROL_ENTITY.get(), "Generic Control");

        addControl(ConsoleControl.DOOR_TOGGLE, "Door Toggle");
        addControl(ConsoleControl.X, "X");
        addControl(ConsoleControl.Y, "Y");
        addControl(ConsoleControl.Z, "Z");
        addControl(ConsoleControl.INCREMENT, "Increment");
        addControl(ConsoleControl.ROTATE, "Direction");
        addControl(ConsoleControl.RANDOM, "Randomizer");
        addControl(ConsoleControl.THROTTLE, "Throttle");
        addControl(ConsoleControl.MONITOR, "Computer Bank");

        add("itemGroup.tardis_refined.tardis_refined", "Tardis Refined");
        add("itemGroup.tardis_refined", "Tardis Refined");

        add("tardis_refined.monitor.main.title", "COMPUTER BANK");
        add("tardis_refined.monitor.main.gps", "GPS");
        add("tardis_refined.monitor.main.destination", "Destination");
        add("tardis_refined.monitor.list.selection", "Currently selected");
        add("tardis_refined.monitor.external_shell", "EXTERNAL SHELL CONFIGURATION");
        add("tardis_refined.gui.shell_selection", "EXTERNAL SHELL CONFIGURATION");
        add("tardis_refined.gui.desktop_selection", "DESKTOP CONFIGURATION");
        add("tardis_refined.monitor.desktop", "DESKTOP CONFIGURATION");
        add("tardis_refined.monitor.desktop_cancel.title", "OPERATION IN PROGRESS");
        add("tardis_refined.monitor.desktop_cancel_description", "Systems disabled as a Desktop reconfiguration has been scheduled.");
        add("tardis_refined.monitor.desktop_cancel_confirmation", "Would you like to cancel the upcoming reconfiguration?");

        addShell(ShellTheme.FACTORY, "Factory");
        addShell(ShellTheme.POLICE_BOX, "Police Box");
        addShell(ShellTheme.PHONE_BOOTH, "Phone Booth");

        add("tardis_refined.desktop.default_overgrown", "Overgrown Cave");
        add("tardis_refined.desktop.factory", "Factory");
        add("tardis_refined.desktop.coral", "Coral");

    }

    public void addControl(ConsoleControl control, String name){
        add(control.getLangId(), name);
    }
    public void addShell(ShellTheme theme, String name){
        add(theme.getRawLang(), name);
    }


}
