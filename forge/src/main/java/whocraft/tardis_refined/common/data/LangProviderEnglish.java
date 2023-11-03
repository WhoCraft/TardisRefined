package whocraft.tardis_refined.common.data;

import net.minecraft.ChatFormatting;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.LanguageProvider;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.EntityRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;
import whocraft.tardis_refined.registry.SoundRegistry;

public class LangProviderEnglish extends LanguageProvider {

    public LangProviderEnglish(DataGenerator gen) {
        super(gen, TardisRefined.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {

        /*Sounds*/
        addSound(SoundRegistry.TARDIS_LAND.get(), "TARDIS lands");
        addSound(SoundRegistry.TARDIS_SINGLE_FLY.get(), "TARDIS flies");
        addSound(SoundRegistry.TARDIS_TAKEOFF.get(), "TARDIS takes off");
        addSound(SoundRegistry.TARDIS_CRASH_LAND.get(), "TARDIS crash lands");
        addSound(SoundRegistry.STATIC.get(), "Screen display static");
        addSound(SoundRegistry.PATTERN_MANIPULATOR.get(), "Pattern Manipulator activates");
        addSound(SoundRegistry.TARDIS_MISC_SPARKLE.get(), "TARDIS arriving");
        addSound(SoundRegistry.TIME_BLAST.get(), "Time Vortex blast");

        /*Block*/
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
        add(BlockRegistry.ROOT_SHELL_DOOR.get(), "Root Door");
        add(BlockRegistry.AIR_LOCK_GENERATION_BLOCK.get(), "Air Lock Generator");
        add(BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get(), "Console Configurator");
        add(BlockRegistry.LANDING_PAD.get(), "Landing Pad");
        add(BlockRegistry.GROWTH_STONE.get(), "Growth Stone");
        add(BlockRegistry.HARDENED_GROWTH_STONE.get(), "Hardened Growth Stone");
        add(BlockRegistry.FLIGHT_DETECTOR.get(), "Flight Detector");

        /*Items*/
        add(ItemRegistry.PATTERN_MANIPULATOR.get(), "Pattern Manipulator");
        add(ItemRegistry.KEY.get(), "Tardis Key");
        add(ItemRegistry.DRILL.get(), "Growth Drill");
        add(ModMessages.ITEM_KEYCHAIN, "Tardis Keyset");

        /*Entity*/
        add(EntityRegistry.CONTROL_ENTITY.get(), "Generic Control");

        /*Controls*/
        addControl(ConsoleControl.DOOR_TOGGLE, "Door Toggle");
        addControl(ConsoleControl.X, "X");
        addControl(ConsoleControl.Y, "Y");
        addControl(ConsoleControl.Z, "Z");
        addControl(ConsoleControl.INCREMENT, "Increment");
        addControl(ConsoleControl.ROTATE, "Direction");
        addControl(ConsoleControl.RANDOM, "Randomizer");
        addControl(ConsoleControl.THROTTLE, "Throttle");
        addControl(ConsoleControl.MONITOR, "Computer Bank");
        addControl(ConsoleControl.FAST_RETURN, "Fast Return");
        addControl(ConsoleControl.DIMENSION, "Dimension");

        /*Messages*/
        add(ModMessages.MSG_EXTERIOR_COOLDOWN, "You must wait %s seconds");
        add(ModMessages.MSG_KEY_BOUND, "Key Bound to %s");
        add(ModMessages.MSG_KEY_CYCLED, "Main: %s");

        /*Command*/
        add(ModMessages.CMD_DIM_NOT_A_TARDIS, ChatFormatting.RED + "%s is not a TARDIS Dimension!");
        add(ModMessages.CMD_NO_INTERNAL_DOOR, ChatFormatting.RED + "No Internal Door found in dimension %s! Consider using the default teleport command %s");
        add(ModMessages.CMD_EXPORT_DESKTOP_IN_PROGRESS, "Generating datapack for desktop %s, this may take some time depending on the structure's size...");
        add(ModMessages.CMD_EXPORT_DESKTOP_SUCCESS, ChatFormatting.GREEN + "Successfully exported desktop %s to datapack %s! Use the %s command to see changes.");
        add(ModMessages.CMD_EXPORT_DESKTOP_RESOURCE_PACK, ChatFormatting.BLUE + "To define the Desktop's preview image, please create a Resource Pack. See some example Resource Packs at: %s");
        add(ModMessages.CMD_EXPORT_DESKTOP_FAIL, ChatFormatting.RED + "Failed to export desktop %s!");

        /*Creative Tab*/
        add("itemGroup.tardis_refined.tardis_refined", "Tardis Refined");
        add("itemGroup.tardis_refined", "Tardis Refined");

        /*GUI*/
        add(ModMessages.UI_MONITOR_MAIN_TITLE, "COMPUTER BANK");
        add(ModMessages.UI_MONITOR_WAYPOINTS, "WAYPOINTS");
        add(ModMessages.UI_MONITOR_UPLOAD_WAYPOINTS, "UPLOAD WAYPOINTS");
        add(ModMessages.UI_MONITOR_UPLOAD_COORDS, "UPLOAD COORDS");
        add(ModMessages.UI_MONITOR_GPS, "GPS");
        add(ModMessages.UI_MONITOR_DESTINATION, "Destination");
        add(ModMessages.UI_LIST_SELECTION, "Currently selected: &s");
        add(ModMessages.UI_EXTERNAL_SHELL, "EXTERNAL SHELL CONFIGURATION");
        add(ModMessages.UI_SHELL_SELECTION, "EXTERNAL SHELL CONFIGURATION");
        add(ModMessages.UI_DESKTOP_SELECTION, "DESKTOP CONFIGURATION");
        add(ModMessages.UI_DESKTOP_CONFIGURATION, "DESKTOP CONFIGURATION");
        add(ModMessages.UI_DESKTOP_CANCEL_TITLE, "OPERATION IN PROGRESS");
        add(ModMessages.UI_DESKTOP_CANCEL_DESCRIPTION, "Systems disabled as a Desktop reconfiguration has been scheduled.");
        add(ModMessages.UI_DESKTOP_CANCEL_DESKTOP, "Would you like to cancel the upcoming reconfiguration?");
        add(ModMessages.UI_DESKTOP_CANCEL, "Cancel Desktop Reconfiguration");

        /*Shell Themes*/
        addShell(ShellTheme.FACTORY, "Factory");
        addShell(ShellTheme.POLICE_BOX, "Police Box");
        addShell(ShellTheme.PHONE_BOOTH, "Phone Booth");
        addShell(ShellTheme.MYSTIC, "Mystic");
        addShell(ShellTheme.DRIFTER, "Drifter");
        addShell(ShellTheme.PRESENT, "Present");
        addShell(ShellTheme.BRIEFCASE, "Briefcase");
        addShell(ShellTheme.GROENING, "Groening");
        addShell(ShellTheme.VENDING, "Vending Machine");
        addShell(ShellTheme.BIG_BEN, "Big Ben");
        addShell(ShellTheme.NUKA, "Nuka");
        addShell(ShellTheme.GROWTH, "Growth");
        addShell(ShellTheme.PORTALOO, "Portaloo");
        addShell(ShellTheme.PAGODA, "Pagoda");

        /*Tool Tips*/
        add(ModMessages.TOOLTIP_TARDIS_LIST_TITLE, "Key Set:");
        add(ModMessages.CONTROL_DIMENSION_SELECTED, "Selected: %s");

        /*Config*/
        add(ModMessages.CONFIG_IP_COMPAT, "Immersive Portals Compatibility?");
        add(ModMessages.CONFIG_CONTROL_NAMES, "Render control names?");
        add(ModMessages.CONFIG_BANNED_DIMENSIONS, "Banned Dimensions");
        add(ModMessages.CONFIG_IDLE_CONSOLE_ANIMS, "Play idle console animations");
    }

    public void addControl(ConsoleControl control, String name) {
        add(control.getTranslationKey(), name);
    }

    public void addShell(ShellTheme theme, String name) {
        add(theme.getTranslationKey(), name);
    }

    public void addSound(SoundEvent soundEvent, String lang) {
        String subtitleKey = SoundProvider.createSubtitle(soundEvent.getLocation().getPath());
        add(subtitleKey, lang);
    }

}
