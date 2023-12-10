package whocraft.tardis_refined.common.network.messages.upgrades;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.screen.upgrades.UpgradesScreen;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
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
        //TODO Move to client util, as this WILL crash servers
        UpgradeHandler upgradeHandlerClient = new UpgradeHandler(new TardisLevelOperator(Minecraft.getInstance().level));
        upgradeHandlerClient.loadData(compoundTag);

        if (Minecraft.getInstance().screen instanceof UpgradesScreen screen && screen.selectedTab != null) {
            screen.selectedTab.populate(upgradeHandlerClient);
        } else {
            Minecraft.getInstance().setScreen(new UpgradesScreen(upgradeHandlerClient));
        }
    }
}
