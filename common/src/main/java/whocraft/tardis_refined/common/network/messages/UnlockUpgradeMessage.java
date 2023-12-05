package whocraft.tardis_refined.common.network.messages;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.screen.upgrades.BuyUpgradeScreen;
import whocraft.tardis_refined.client.screen.upgrades.UpgradesScreen;
import whocraft.tardis_refined.common.capability.upgrades.Upgrade;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;

import java.util.Objects;

public class UnlockUpgradeMessage extends MessageC2S {

    private final Upgrade upgrade;

    public UnlockUpgradeMessage(Upgrade upgrade) {
        this.upgrade = upgrade;
    }

    public UnlockUpgradeMessage(FriendlyByteBuf buf) {
        this.upgrade = Upgrade.UPGRADES.get(buf.readResourceLocation());
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.UNLOCK_UPGRADE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(Objects.requireNonNull(Upgrade.UPGRADES.getKey(this.upgrade)));
    }

    @Override
    public void handle(MessageContext context) {
        boolean available = true;

        if (available) {
            // TODO actually unlock the upgrade in the tardis thingy
            // TODO when you sync the unlocked state to the client, execute this on the client:
            /**
             * if (Minecraft.getInstance().screen instanceof UpgradesScreen screen && screen.selectedTab != null) {
             *    screen.selectedTab.populate();
             *  }
             */
        }
    }
}
