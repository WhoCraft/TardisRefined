package whocraft.tardis_refined.experiment.voice;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class VoiceActions {

    private static final Map<String, VoiceAction> actions = new HashMap<>();

    public static void registerAction(String command, VoiceAction action) {
        actions.put(command.toLowerCase(), action);
    }

    public static void processCommand(String command, ServerPlayer serverPlayer) {
        String[] parts = command.toLowerCase().split("\\s+");
        for (String part : parts) {
            if (actions.containsKey(part)) {
                actions.get(part).process(command, serverPlayer);
            } else {
              //  serverPlayer.sendSystemMessage(Component.literal("Sorry, I didn't understand that command."));
            }
        }
    }

    public static void init() {

        String[] takeOffs = new String[]{"takeoff", "fly", "flight"};
        for (String takeOff : takeOffs) {
            registerAction(takeOff, new VoiceAction() {
                @Override
                public void process(String speech, ServerPlayer serverPlayer) {
                    TardisLevelOperator.get(serverPlayer.serverLevel()).ifPresent(tardisLevelOperator -> {
                        if(!tardisLevelOperator.getPilotingManager().isInFlight()){
                            tardisLevelOperator.getPilotingManager().setTargetPosition(new BlockPos(45,45,45));
                            tardisLevelOperator.getPilotingManager().beginFlight(true, null);
                        }
                    });
                }
            });
        }

    }
}
