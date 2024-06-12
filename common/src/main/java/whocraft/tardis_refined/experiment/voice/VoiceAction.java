package whocraft.tardis_refined.experiment.voice;

import net.minecraft.server.level.ServerPlayer;

public interface VoiceAction {

    void process(String speech, ServerPlayer serverPlayer);

}
