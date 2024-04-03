package whocraft.tardis_refined.common.data;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.capability.upgrades.Upgrade;
import whocraft.tardis_refined.common.capability.upgrades.Upgrades;
import whocraft.tardis_refined.common.tardis.control.Control;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.*;

public class LangProviderEnglish extends LanguageProvider {

    public LangProviderEnglish(DataGenerator gen) {
        super(gen.getPackOutput(), TardisRefined.MODID, "en_us");
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
        addSound(SoundRegistry.DESTINATION_DING.get(), "TARDIS reaches destination");
        addSound(SoundRegistry.ARS_HUM.get(), "ARS Tree Hum");
        addSound(SoundRegistry.FLIGHT_FAIL_START.get(), "Failing TARDIS groans");
        addSound(SoundRegistry.CONSOLE_POWER_ON.get(), "Console power on");
        addSound(SoundRegistry.INTERIOR_VOICE.get(), "...?");

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
        add(BlockRegistry.GLOBAL_DOOR_BLOCK.get(), "Tardis Door");
        add(BlockRegistry.ROOT_SHELL_DOOR.get(), "Root Door");
        add(BlockRegistry.AIR_LOCK_GENERATION_BLOCK.get(), "Air Lock Generator");
        add(BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get(), "Console Configurator");
        add(BlockRegistry.LANDING_PAD.get(), "Landing Pad");
        add(BlockRegistry.FOOLS_STONE.get(), "Fool's Stone");
        add(BlockRegistry.FLIGHT_DETECTOR.get(), "Flight Detector");
        add(BlockRegistry.GLOBAL_SHELL_BLOCK.get(), "TARDIS");
        add(BlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get(), "Astral Manipulator");
        add(BlockRegistry.ZEITON_FUSED_IRON_BLOCK.get(), "Zeiton Fused Iron Block");
        add(BlockRegistry.ZEITON_FUSED_COPPER_BLOCK.get(), "Zeiton Fused Copper Block");
        add(BlockRegistry.ZEITON_ORE.get(), "Zeiton Ore");
        add(BlockRegistry.ZEITON_ORE_DEEPSLATE.get(), "Deepslate Zeiton Ore");
        add(BlockRegistry.ZEITON_BLOCK.get(), "Block of Zeiton");
        add(BlockRegistry.GRAVITY_WELL.get(), "Gravity Well");
        add(BlockRegistry.ZEITON_LANTERN.get(), "Zeiton Lantern");

        /*Items*/
        add(ItemRegistry.PATTERN_MANIPULATOR.get(), "Pattern Manipulator");
        add(ItemRegistry.KEY.get(), "Tardis Key");
        add(ItemRegistry.DRILL.get(), "Growth Drill");
        add(ModMessages.ITEM_KEYCHAIN, "Tardis Keyset");
        add(ModMessages.ITEM_GROUP, "Tardis Refined");
        add(ItemRegistry.SCREWDRIVER.get(), "Amethyst Screwdriver");
        add(ModMessages.TOOLTIP_SCREWDRIVER_DESCRIPTION, "An amethyst frequency manipulator");
        add(ItemRegistry.ZEITON_INGOT.get(), "Zeiton Ingot");
        add(ItemRegistry.RAW_ZEITON.get(), "Raw Zeiton");
        add(ItemRegistry.GLASSES.get(), "AR Glasses");
        add(ItemRegistry.ZEITON_NUGGET.get(), "Zeiton Nugget");




        /*Entity*/
        add(EntityRegistry.CONTROL_ENTITY.get(), "Generic Control");

        /*Controls*/
        addControl(ControlRegistry.DOOR_TOGGLE.get(), "Door Toggle");
        addControl(ControlRegistry.X.get(), "X");
        addControl(ControlRegistry.Y.get(), "Y");
        addControl(ControlRegistry.Z.get(), "Z");
        addControl(ControlRegistry.INCREMENT.get(), "Increment");
        addControl(ControlRegistry.ROTATE.get(), "Direction");
        addControl(ControlRegistry.RANDOM.get(), "Randomizer");
        addControl(ControlRegistry.THROTTLE.get(), "Throttle");
        addControl(ControlRegistry.MONITOR.get(), "Computer Bank");
        addControl(ControlRegistry.FAST_RETURN.get(), "Fast Return");
        addControl(ControlRegistry.DIMENSION.get(), "Dimension");
        addControl(ControlRegistry.HANDBRAKE.get(), "Handbrake");;
        addControl(ControlRegistry.GENERIC_NO_SHOW.get(), "Switch");

        /*Messages*/
        add(ModMessages.MSG_EXTERIOR_COOLDOWN, "You must wait %s seconds");
        add(ModMessages.MSG_KEY_BOUND, "Key Bound to %s");
        add(ModMessages.MSG_KEY_CYCLED, "Main: %s");
        add(ModMessages.CONSOLE_CONFIGURATION_NOT_IN_FLIGHT, "Cannot update console block whilst in flight.");
        add(ModMessages.HARDWARE_OFFLINE, "Hardware offline.");
        add(ModMessages.HANDBRAKE_ENGAGED, "Engaged.");
        add(ModMessages.HANDBRAKE_DISENGAGED, "Disengaged.");
        add(ModMessages.NO_FLIGHT_TRANSITIVE, "Cannot change handbrake state whilst in transitive flight.");
        add(ModMessages.HANDBRAKE_WARNING, "Ship is in flight. Left click the handbrake to engage.");
        add(ModMessages.CONSOLE_NOT_IN_FLIGHT, "Cannot change consoles whilst in flight.");
        add(ModMessages.NO_END_DRAGON_PREVENTS, "A dragon prevents you from progressing to The End.");
        add(ModMessages.TARDIS_IS_ON_THE_WAY, "TARDIS has been summoned and is on the way.");
        add(ModMessages.LANDING_PAD_NOT_UNLOCKED, "Specified TARDIS rejected landing pad signal.");
        add(ModMessages.LANDING_PAD_TRANSIENT, "Cannot summon TARDIS at this time.");

        /*Command*/
        add(ModMessages.CMD_DIM_NOT_A_TARDIS, ChatFormatting.RED + "%s is not a TARDIS Dimension!");
        add(ModMessages.CMD_NO_INTERNAL_DOOR, ChatFormatting.RED + "No Internal Door found in dimension %s! Consider using the default teleport command %s");
        add(ModMessages.CMD_EXPORT_DESKTOP_IN_PROGRESS, "Generating datapack for desktop %s, this may take some time depending on the structure's size...");
        add(ModMessages.CMD_EXPORT_DESKTOP_SUCCESS, ChatFormatting.GREEN + "Successfully exported desktop %s to datapack %s! Use the %s command to see changes.");
        add(ModMessages.CMD_EXPORT_DESKTOP_RESOURCE_PACK, ChatFormatting.BLUE + "To define the Desktop's preview image, please create a Resource Pack. See some example Resource Packs at: %s");
        add(ModMessages.CMD_EXPORT_DESKTOP_FAIL, ChatFormatting.RED + "Failed to export desktop %s!");

        add(ModMessages.CMD_LEVEL_POINT_GET, "%s has %s upgrade points");
        add(ModMessages.CMD_LEVEL_POINT_SET, "Set upgrade points for %s to %s");
        add(ModMessages.CMD_LEVEL_POINT_ADD, "Added %s points for %s, total points are now %s");

        add(ModMessages.CMD_LEVEL_XP_GET, "%s has %s XP");
        add(ModMessages.CMD_LEVEL_XP_SET, "Set XP for %s to %s");
        add(ModMessages.CMD_LEVEL_XP_ADD, "Added %s XP for %s, total XP is now %s");

        add(ModMessages.CMD_UPGRADE_LOCK, "Locked upgrade %s for %s");
        add(ModMessages.CMD_UPGRADE_UNLOCK, "Unlocked upgrade %s for %s");

        add(ModMessages.CMD_CREATE_TARDIS_IN_PROGRESS, "Attempting to create Tardis %s, generation in progress");
        add(ModMessages.CMD_CREATE_TARDIS_SUCCESS, "Successfully created Tardis with id %s");

        add(ModMessages.CMD_ARG_UPGRADE_INVALID, "Invalid Upgrade of ID %s");
        add(ModMessages.CMD_ARG_DESKTOP_INVALID, "Invalid Desktop of ID %s");
        add(ModMessages.CMD_ARG_SHELL_INVALID, "Invalid Shell of ID %s");

        /*GUI*/
        add(ModMessages.UI_MONITOR_MAIN_TITLE, "COMPUTER BANK");
        add(ModMessages.UI_MONITOR_WAYPOINTS, "WAYPOINTS");
        add(ModMessages.UI_MONITOR_UPLOAD_WAYPOINTS, "WAYPOINT NAVIGATION");
        add(ModMessages.UI_MONITOR_UPLOAD_COORDS, "COORD NAVIGATION");
        add(ModMessages.UI_MONITOR_SELECT_HUM, "SOUNDSCAPE");
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
        add(ModMessages.UI_MONITOR_NO_WAYPOINTS, "No Waypoints Saved!");
        add(ModMessages.UI_MONITOR_WAYPOINT_UPLOAD, "Upload");
        add(ModMessages.UI_MONITOR_WAYPOINT_SUBMIT, "Submit");
        add(ModMessages.UI_MONITOR_WAYPOINT_DIMENSION, "Dimension");
        add(ModMessages.UI_MONITOR_WAYPOINT_DIRECTION, "Direction");
        add(ModMessages.UI_MONITOR_ISSUES, "Issues:");
        add(ModMessages.UI_MONITOR_WAYPOINT_ISSUE_NAME, "Invalid waypoint name");
        add(ModMessages.UI_MONITOR_WAYPOINT_ISSUE_X, "Invalid X value");
        add(ModMessages.UI_MONITOR_WAYPOINT_ISSUE_Y, "Invalid Y value");
        add(ModMessages.UI_MONITOR_WAYPOINT_ISSUE_Z, "Invalid Z value");
        add(ModMessages.UI_MONITOR_WAYPOINT_NAME, "Waypoint Name:");
        add(ModMessages.UI_UPGRADES, "Tardis Upgrades");
        add(ModMessages.UI_UPGRADES_BUY, "Purchase Upgrade?");
        add(ModMessages.UI_NO_INSTALLED_SUBSYSTEMS, "No Available Sub-Systems");

        /*Shell Themes*/
        addShell(ShellTheme.FACTORY.getId(), "Factory");
        addShell(ShellTheme.POLICE_BOX.getId(), "Police Box");
        addShell(ShellTheme.PHONE_BOOTH.getId(), "Phone Booth");
        addShell(ShellTheme.MYSTIC.getId(), "Mystic");
        addShell(ShellTheme.DRIFTER.getId(), "Drifter");
        addShell(ShellTheme.PRESENT.getId(), "Present");
        addShell(ShellTheme.BRIEFCASE.getId(), "Briefcase");
        addShell(ShellTheme.GROENING.getId(), "Groening");
        addShell(ShellTheme.VENDING.getId(), "Vending Machine");
        addShell(ShellTheme.BIG_BEN.getId(), "Big Ben");
        addShell(ShellTheme.NUKA.getId(), "Nuka");
        addShell(ShellTheme.GROWTH.getId(), "Growth");
        addShell(ShellTheme.PORTALOO.getId(), "Portaloo");
        addShell(ShellTheme.PAGODA.getId(), "Pagoda");
        addShell(ShellTheme.LIFT.getId(), "Lift");
        addShell(ShellTheme.HIEROGLYPH.getId(), "Hieroglyph");
        addShell(ShellTheme.CASTLE.getId(), "Castle");
        addShell(ShellTheme.PATHFINDER.getId(), "Pathfinder");

        /*Tool Tips*/
        add(ModMessages.TOOLTIP_TARDIS_LIST_TITLE, "Key Set:");
        add(ModMessages.CONTROL_DIMENSION_SELECTED, "Selected: %s");
        add(ModMessages.TOOLTIP_IN_FLIGHT, "In flight");

        /*Config*/
        add(ModMessages.CONFIG_IP_COMPAT, "Immersive Portals Compatibility?");
        add(ModMessages.CONFIG_CONTROL_NAMES, "Render control names?");
        add(ModMessages.CONFIG_BANNED_DIMENSIONS, "Banned Dimensions");
        add(ModMessages.CONFIG_IDLE_CONSOLE_ANIMS, "Play idle console animations");

        /*Overlay Messages*/
        add(ModMessages.ASCEND_KEY, "Ascend: %s");
        add(ModMessages.DESCEND_KEY, "Descend: %s");

        /*Upgrades*/
        addUpgrade(Upgrades.COORDINATE_INPUT.get(), "Coordinate Input", "Allows the Pilot to input coordinates with the monitor");
        addUpgrade(Upgrades.CHAMELEON_CIRCUIT_SYSTEM.get(), "Chameleon Circuit", "Allows the TARDIS to change it's shape");
        addUpgrade(Upgrades.DEFENSE_SYSTEM.get(), "Defense System", "Enables Defense Protocols");
        addUpgrade(Upgrades.WAYPOINTS.get(), "Waypoints", "Allows the Pilot to create saved locations");
        addUpgrade(Upgrades.NAVIGATION_SYSTEM.get(), "Navigation System", "Allows upgrades to the TARDIS Navigation System");
        addUpgrade(Upgrades.TARDIS_XP.get(), "System Upgrades", "Allows upgrades to the TARDIS");
        addUpgrade(Upgrades.MATERIALIZE_AROUND.get(), "Materialize Around", "Allows the TARDIS to have entities enter while materalizing");
        addUpgrade(Upgrades.ARCHITECTURE_SYSTEM.get(), "Architecture", "Enables TARDIS Architecture Upgrades");
        addUpgrade(Upgrades.INSIDE_ARCHITECTURE.get(), "Desktop Reconfiguration", "Allows the Pilot to change the appearance of the TARDIS Desktop");
        addUpgrade(Upgrades.EXPLORER.get(), "Explorer I", "x1000 Increment");
        addUpgrade(Upgrades.EXPLORER_II.get(), "Explorer II", "x2500 Increment");
        addUpgrade(Upgrades.EXPLORER_III.get(), "Explorer III", "x5000 Increment");
        addUpgrade(Upgrades.DIMENSION_TRAVEL.get(), "Inter-Dimensional Travel", "Allows the TARDIS to move between dimensions");
        addUpgrade(Upgrades.LANDING_PAD.get(), "Landing Pad", "Allows the TARDIS to be summoned to a landing pad");


    }


    public void addControl(Control control, String name) {
        add(control.getTranslationKey(), name);
    }

    public void addShell(ResourceLocation theme, String name) {
        add(ModMessages.shell(theme.getPath()), name);
    }

    public void addUpgrade(Upgrade upgrade, String title, String description) {
        add(Util.makeDescriptionId("upgrade", Upgrades.UPGRADE_REGISTRY.getKey(upgrade)), title);
        add(Util.makeDescriptionId("upgrade", Upgrades.UPGRADE_REGISTRY.getKey(upgrade)) + ".description", description);
    }

    public void addSound(SoundEvent soundEvent, String lang) {
        String subtitleKey = SoundProvider.createSubtitle(soundEvent.getLocation().getPath());
        add(subtitleKey, lang);
    }

}
