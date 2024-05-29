package whocraft.tardis_refined.common.network.messages.upgrades;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.Upgrade;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.capability.upgrades.Upgrades;
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
        this.upgrade = Upgrades.UPGRADE_REGISTRY.get(buf.readResourceLocation());
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.UNLOCK_UPGRADE;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(Objects.requireNonNull(Upgrades.UPGRADE_REGISTRY.getKey(this.upgrade)));
    }

    @Override
    public void handle(MessageContext context) {

        ServerLevel serverLevel = context.getPlayer().serverLevel();
        TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
            UpgradeHandler upgradeHandler = tardisLevelOperator.getUpgradeHandler();
            boolean available = !upgradeHandler.isUpgradeUnlocked(upgrade) && upgradeHandler.getUpgradePoints() >= upgrade.getSkillPointsRequired();
            if (available) {
                upgradeHandler.setUpgradePoints(upgradeHandler.getUpgradePoints() - upgrade.getSkillPointsRequired());
                upgradeHandler.unlockUpgrade(upgrade);
                CompoundTag nbt = upgradeHandler.saveData(new CompoundTag());
                new S2CDisplayUpgradeScreen(nbt).send(context.getPlayer());
            }

        });
    }
}
