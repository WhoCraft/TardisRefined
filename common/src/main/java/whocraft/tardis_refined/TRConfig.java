package whocraft.tardis_refined;

import com.google.common.collect.Lists;


import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.List;

public class TRConfig {

    public static Common COMMON;
    public static ForgeConfigSpec COMMON_SPEC;
    public static Server SERVER;
    public static ForgeConfigSpec SERVER_SPEC;
    public static Client CLIENT;
    public static ForgeConfigSpec CLIENT_SPEC;

    static {
        Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();

        Pair<Server, ForgeConfigSpec> specServerPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = specServerPair.getRight();
        SERVER = specServerPair.getLeft();


        Pair<Client, ForgeConfigSpec> specClientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specClientPair.getRight();
        CLIENT = specClientPair.getLeft();
    }


    public static class Client {
        public final ForgeConfigSpec.BooleanValue CONTROL_NAMES;
        public final ForgeConfigSpec.BooleanValue PLAY_CONSOLE_IDLE_ANIMATIONS;
        public final ForgeConfigSpec.BooleanValue RENDER_VORTEX_IN_DOOR;
        public final ForgeConfigSpec.BooleanValue USE_INTERNAL_SHADERS;
        public final ForgeConfigSpec.DoubleValue SCREEN_SHAKE_MULTIPLIER;

        public Client(ForgeConfigSpec.Builder builder) {
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
        public final ForgeConfigSpec.BooleanValue COMPATIBILITY_IP;

        public final ForgeConfigSpec.BooleanValue IP_VS_COLLISION;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("compatibility");
            COMPATIBILITY_IP = builder.comment("Toggle Immersive Portals compatibility (TR 2.0+). 2.0 has limited support").translation(ModMessages.CONFIG_IP_COMPAT).define("immersive_portals_support", true);
            builder.push("immersive_portals_valkyrien_skies");
            IP_VS_COLLISION = builder.comment("If false, the TARDIS door's collision box will be disabled when opened on a Valkyrien Skies ship if teleportation_mode_vs is set to PORTAL.").translation(ModMessages.CONFIG_IP_VS_COLLISION).define("open_door_ship_collision", true);
            builder.pop();
            builder.pop();
        }

    }

    public static class Server {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> BANNED_DIMENSIONS;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> ADVENTURE_MODE_DEFAULTS;
        public final ForgeConfigSpec.BooleanValue ADVENTURE_MODE;
        public final ForgeConfigSpec.EnumValue<DeleteMode> DIMENSION_DELETE_MODE;

        public final ForgeConfigSpec.BooleanValue IP_DIMENSION_ADDER;
        public final ForgeConfigSpec.BooleanValue IP_DIMENSION_REMOVER;

        public final ForgeConfigSpec.BooleanValue IP_SMOOTH_TELEPORTATION;

        public final ForgeConfigSpec.EnumValue<IPTeleportationMode> IP_TELEPORTATION_MODE;
        public final ForgeConfigSpec.EnumValue<IPTeleportationMode> IP_TELEPORTATION_MODE_VS;

        public enum IPTeleportationMode {
            PORTAL,
            ITP;

            private static final String COMMENT = "PORTAL is the normal immersive portals with maximum smoothness. ITP instead teleports the player directly similar to when Immersive Portals integration is disabled, making the boti effect purely visual.";
        }

        public enum DeleteMode {
            IMMEDIATE,
            NEXT_SHUTDOWN;

            private static final Platform.CommonVersionRange VS_BROKEN_DELETE_VERSIONS = Platform.CommonVersionRange.Builder.builder().atMost(
                    "2.4.11"
            ).build();

            static DeleteMode getDefault() {
                if (Platform.isModLoaded("valkyrienskies", VS_BROKEN_DELETE_VERSIONS)) {
                    return NEXT_SHUTDOWN;
                }
                return IMMEDIATE;
            }
        }

        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("travel");
            BANNED_DIMENSIONS = builder.translation("config.tardis_refined.banned_dimensions").comment("A list of Dimensions the TARDIS cannot land in.").defineList("banned_dimensions", Lists.newArrayList("example:dimension", "[substring]will_match_any_dimension_containing_this_substring", "[namespace]immersive_portals", "[regex]insert_regex_here"), String.class::isInstance);
            ADVENTURE_MODE_DEFAULTS = builder.translation("config.tardis_refined.adventure_mode_defaults").comment("A list of Dimensions that are automatically sampled").defineList("adventure_mode_defaults", Lists.newArrayList("minecraft:overworld"), String.class::isInstance);
            ADVENTURE_MODE = builder.translation("config.tardis_refined.adventure_mode").comment("Toggles whether players must discover and sample dimensions before they can travel there").define("adventure_mode", false);
            DIMENSION_DELETE_MODE = builder.translation(ModMessages.CONFIG_DIMENSION_DELETE_MODE).comment("The method used to delete dimensions. IMMEDIATE deletes the dimension immediately while NEXT_SHUTDOWN schedules the dimension for deletion on server shutdown. NEXT_SHUTDOWN is primarily intended for use with Valkyrien Skies 2.4.11 and lower as it may crash otherwise due to a bug. IMMEDIATE should work fine with most unless they do something weird. Note that NEXT_SHUTDOWN does NOT allow you to recover a TARDIS deleted accidentally.").defineEnum("dimension_delete_mode", DeleteMode.getDefault());
            builder.pop();
            builder.push("compatibility");
            builder.push("immersive_portals");
            IP_DIMENSION_ADDER = builder.comment("Whether or not to let Immersive Portals handle dimension additions.").translation(ModMessages.CONFIG_IP_DIMENSION_ADDER).define("dimension_adder", true);
            IP_DIMENSION_REMOVER = builder.comment("Whether or not to let Immersive Portals handle dimension removal.").translation(ModMessages.CONFIG_IP_DIMENSION_REMOVER).define("dimension_remover", true);
            IP_SMOOTH_TELEPORTATION = builder.comment("Whether or not to let Immersive Portals handle regular non-boti teleportation.").translation(ModMessages.CONFIG_IP_SMOOTH_TELEPORTATION).define("smooth_teleportation", true);
            IP_TELEPORTATION_MODE = builder.comment("Choose what teleportation method to use when walking through the TARDIS door. " + IPTeleportationMode.COMMENT).translation(ModMessages.CONFIG_IP_TELEPORTATION_MODE).defineEnum("teleportation_mode", IPTeleportationMode.PORTAL);
            builder.pop();
            builder.push("immersive_portals_valkyrien_skies");
            IP_TELEPORTATION_MODE_VS = builder.comment("Choose what teleportation method to use when walking through the TARDIS door on a Valkyrien Skies ship. " + IPTeleportationMode.COMMENT + " ITP is recommended to avoid getting stuck in walls/the void when the ship is moving.").translation(ModMessages.CONFIG_IP_TELEPORTATION_MODE_VS).defineEnum("teleportation_mode_vs", IPTeleportationMode.ITP);
            builder.pop();
            builder.pop();
        }

    }


}
