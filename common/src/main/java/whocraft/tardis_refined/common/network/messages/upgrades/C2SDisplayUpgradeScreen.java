package whocraft.tardis_refined.common.network.messages.upgrades;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;

public class C2SDisplayUpgradeScreen extends MessageC2S {

    public ResourceKey<Level> levelResourceKey;

    public C2SDisplayUpgradeScreen(ResourceKey<Level> levelResourceKey) {
        this.levelResourceKey = levelResourceKey;
    }

    public C2SDisplayUpgradeScreen(FriendlyByteBuf friendlyByteBuf){
        levelResourceKey = friendlyByteBuf.readResourceKey(Registries.DIMENSION);
    }


    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.UPGRADE_SCREEN_C2S;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceKey(levelResourceKey);
    }

    @Override
    public void handle(MessageContext context) {
        ServerLevel serverLevel = context.getPlayer().serverLevel();
        TardisLevelOperator.get(serverLevel).ifPresent(tardisLevelOperator -> {
            new S2CDisplayUpgradeScreen(tardisLevelOperator.getUpgradeHandler().saveData(new CompoundTag())).send(context.getPlayer());
        });
    }
}
