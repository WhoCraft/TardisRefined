package whocraft.tardis_refined;

import com.google.common.collect.Lists;


import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.List;

public class TRConfig {

    public static Common COMMON;
    public static ModConfigSpec COMMON_SPEC;
    public static Server SERVER;
    public static ModConfigSpec SERVER_SPEC;
    public static Client CLIENT;
    public static ModConfigSpec CLIENT_SPEC;

    static {
        Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();

        Pair<Server, ModConfigSpec> specServerPair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = specServerPair.getRight();
        SERVER = specServerPair.getLeft();


        Pair<Client, ModConfigSpec> specClientPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specClientPair.getRight();
        CLIENT = specClientPair.getLeft();
    }


    public static class Client {
        public final ModConfigSpec.BooleanValue CONTROL_NAMES;
        public final ModConfigSpec.BooleanValue PLAY_CONSOLE_IDLE_ANIMATIONS;
        public final ModConfigSpec.BooleanValue RENDER_VORTEX_IN_DOOR;
        public final ModConfigSpec.BooleanValue USE_INTERNAL_SHADERS;
        public final ModConfigSpec.DoubleValue SCREEN_SHAKE_MULTIPLIER;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("rendering");
            CONTROL_NAMES = builder.comment("Toggle control name rendering").translation(ModMessages.CONFIG_CONTROL_NAMES).define("control_name_rendering", true);
            PLAY_CONSOLE_IDLE_ANIMATIONS = builder.comment("Play idle console animations").translation(ModMessages.CONFIG_IDLE_CONSOLE_ANIMS).define("console_idle_animations", true);
            RENDER_VORTEX_IN_DOOR = builder.comment("Skip Vortex rendering").translation(ModMessages.CONFIG_RENDER_VORTEX_IN_DOOR).define("render_vortex_in_door", true);
            USE_INTERNAL_SHADERS = builder.comment("Use Custom Internal Shaders").translation(ModMessages.CONFIG_CUSTOM_SHADERS).define("use_internal_shaders", true);
            SCREEN_SHAKE_MULTIPLIER = builder.comment("Adjust the amount the game shakes the screen during travel").translation(ModMessages.SCREEN_SHAKE_MULTIPLIER).defineInRange("screen_shake_multiplier", 1d, 0, 2);
            builder.pop();
        }
    }

    public static class Common {
        public final ModConfigSpec.BooleanValue COMPATIBILITY_IP;

        public final ModConfigSpec.BooleanValue IP_SUBLEVEL_COLLISION;

        public Common(ModConfigSpec.Builder builder) {
            builder.push("compatibility");
            COMPATIBILITY_IP = builder.comment("Toggle Immersive Portals compatibility (TR 2.0+). 2.0 has limited support").translation(ModMessages.CONFIG_IP_COMPAT).define("immersive_portals_support", true);
            builder.push("immersive_portals_sublevels");
            IP_SUBLEVEL_COLLISION = builder.comment("If false, the TARDIS door's collision box will be disabled when opened on a sublevel if teleportation_mode_sublevels is set to PORTAL.").translation(ModMessages.CONFIG_IP_SUBLEVEL_COLLISION).define("open_door_sublevel_collision", true);
            builder.pop();
            builder.pop();
        }

    }

    public static class Server {
        public final ModConfigSpec.ConfigValue<List<? extends String>> BANNED_DIMENSIONS;
        public final ModConfigSpec.ConfigValue<List<? extends String>> ADVENTURE_MODE_DEFAULTS;
        public final ModConfigSpec.BooleanValue ADVENTURE_MODE;

        public final ModConfigSpec.EnumValue<IPTeleportationMode> IP_TELEPORTATION;
        public final ModConfigSpec.EnumValue<IPTeleportationMode> IP_TELEPORTATION_SUBLEVELS;

        public enum IPTeleportationMode {
            PORTAL,
            ITP;

            private static final String COMMENT = "PORTAL is the normal immersive portals with maximum smoothness. ITP instead teleports the player directly similar to when Immersive Portals integration is disabled, making the boti effect purely visual.";
        }

        public Server(ModConfigSpec.Builder builder) {
            builder.push("travel");
            BANNED_DIMENSIONS = builder.translation("config.tardis_refined.banned_dimensions").comment("A list of Dimensions the TARDIS cannot land in.").defineList("banned_dimensions", Lists.newArrayList("example:dimension", "[substring]will_match_any_dimension_containing_this_substring", "[namespace]immersive_portals", "[regex]insert_regex_here"), String.class::isInstance);
            ADVENTURE_MODE_DEFAULTS = builder.translation("config.tardis_refined.adventure_mode_defaults").comment("A list of Dimensions that are automatically sampled").defineList("adventure_mode_defaults", Lists.newArrayList("minecraft:overworld"), String.class::isInstance);
            ADVENTURE_MODE = builder.translation("config.tardis_refined.adventure_mode").comment("Toggles whether players must discover and sample dimensions before they can travel there").define("adventure_mode", false);
            builder.pop();
            builder.push("compatibility");
            builder.push("immersive_portals");
            IP_TELEPORTATION = builder.comment("Choose what teleportation method to use when walking through the TARDIS door. " + IPTeleportationMode.COMMENT).translation(ModMessages.CONFIG_IP_TELEPORTATION).defineEnum("teleportation_mode", IPTeleportationMode.PORTAL);
            builder.pop();
            builder.push("immersive_portals_sublevels");
            IP_TELEPORTATION_SUBLEVELS = builder.comment("Choose what teleportation method to use when walking through the TARDIS door on a sublevel. " + IPTeleportationMode.COMMENT + " ITP is recommended to avoid getting stuck in walls/the void when the ship is moving.").translation(ModMessages.CONFIG_IP_TELEPORTATION_SUBLEVELS).defineEnum("teleportation_mode_sublevels", IPTeleportationMode.ITP);
            builder.pop();
            builder.pop();
        }

    }


}
