package whocraft.tardis_refined.common.network.messages.sync;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.TardisClientLogic;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;

public class S2CSyncTardisClientData extends MessageS2C {

    public ResourceKey<Level> level;
    public CompoundTag compoundTag;

    public S2CSyncTardisClientData(ResourceKey<Level> level, CompoundTag compoundTag) {
        this.level = level;
        this.compoundTag = compoundTag;
    }

    public S2CSyncTardisClientData(FriendlyByteBuf friendlyByteBuf) {
        this.level = friendlyByteBuf.readResourceKey(Registries.DIMENSION);
        this.compoundTag = friendlyByteBuf.readNbt();
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.INT_REACTION;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceKey(this.level);
        buf.writeNbt(this.compoundTag);
    }

    @Override
    public void handle(MessageContext context) {
        // Retrieve the TardisIntReactions instance for the current level
        TardisClientData data = TardisClientData.getInstance(level);
        String oldTheme = String.valueOf(data.getShellTheme());
        String oldPattern = String.valueOf(data.getShellPattern());
        String newTheme = compoundTag.getStringOr("shellTheme", "<missing>");
        String newPattern = compoundTag.getStringOr("shellPattern", "<missing>");
        if (!oldTheme.equals(newTheme) || !oldPattern.equals(newPattern)) {
            TardisRefined.LOGGER.info("Received TARDIS appearance sync for {}: theme {} -> {}, pattern {} -> {}",
                    level.identifier(), oldTheme, newTheme, oldPattern, newPattern);
        }

        // Deserialize the Tardis instance from the given CompoundTag
        data.deserializeNBT(compoundTag);

        // Update the Tardis instance
        TardisClientLogic.update(data);
    }
}
