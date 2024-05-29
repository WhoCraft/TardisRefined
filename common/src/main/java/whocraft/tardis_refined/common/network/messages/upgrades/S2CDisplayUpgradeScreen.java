package whocraft.tardis_refined.common.network.messages.upgrades;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.ScreenHandler;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;

public class S2CDisplayUpgradeScreen extends MessageS2C {

    public CompoundTag compoundTag;

    public S2CDisplayUpgradeScreen(CompoundTag compoundTag) {
        this.compoundTag = compoundTag;
    }

    public S2CDisplayUpgradeScreen(FriendlyByteBuf friendlyByteBuf) {
        compoundTag = friendlyByteBuf.readNbt();
    }

    @Environment(EnvType.CLIENT)
    private static void display(CompoundTag compoundTag) {
        ScreenHandler.displayUpgradesScreen(compoundTag);
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.UPGRADE_SCREEN_S2C;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(compoundTag);
    }

    @Override
    public void handle(MessageContext context) {
        display(compoundTag);
    }

}
