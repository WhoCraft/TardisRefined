package whocraft.tardis_refined;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
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

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("rendering");
            CONTROL_NAMES = builder.comment("Toggle control name rendering").translation(ModMessages.CONFIG_CONTROL_NAMES).define("control_name_rendering", true);
            PLAY_CONSOLE_IDLE_ANIMATIONS = builder.comment("Play idle console animations").translation(ModMessages.CONFIG_IDLE_CONSOLE_ANIMS).define("console_idle_animations", true);
            builder.pop();
        }

    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue COMPATIBILITY_IP;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("compatibility");
            COMPATIBILITY_IP = builder.comment("Toggle Immersive Portals compatibility").translation(ModMessages.CONFIG_IP_COMPAT).define("immersive_portals", true);
            builder.pop();
        }

    }

    public static class Server {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> BANNED_DIMENSIONS;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("travel");
            BANNED_DIMENSIONS = builder.translation(ModMessages.CONFIG_BANNED_DIMENSIONS).comment("A list of Dimensions the TARDIS cannot land in.").defineList("banned_dimensions", Lists.newArrayList("example:dimension"), String.class::isInstance);
            builder.pop();
        }

    }


}
