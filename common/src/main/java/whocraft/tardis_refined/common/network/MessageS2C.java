package whocraft.tardis_refined.common.network;


import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.util.Platform;

import java.util.List;

public abstract class MessageS2C extends Message {

    public void send(ServerPlayer player) {
        this.getType().getNetworkManager().sendToPlayer(player, this);
    }

    public void sendToDimension(Level level) {
        this.getType().getNetworkManager().sendToDimension(level, this);
    }

    public void sendToAll() {
        MinecraftServer server = Platform.getServer();
        List<ServerPlayer> players = server.getPlayerList().getPlayers();
        players.forEach(this::send);
    }

}
