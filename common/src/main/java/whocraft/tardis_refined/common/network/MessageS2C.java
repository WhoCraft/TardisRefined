package whocraft.tardis_refined.common.network;


import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
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
        this.getType().getNetworkManager().sendToAllPlayers(this);
    }

    public void sendToTracking(Entity entity) {
        this.getType().getNetworkManager().sendToTracking(entity, this);
    }

    public void sendToTracking(BlockEntity blockEntity) {
        this.getType().getNetworkManager().sendToTracking(blockEntity, this);
    }

}
