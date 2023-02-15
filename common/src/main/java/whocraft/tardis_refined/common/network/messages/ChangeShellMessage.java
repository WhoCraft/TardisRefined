package whocraft.tardis_refined.common.network.messages;

import net.minecraft.core.Registry;
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
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;

import java.util.Optional;

public class ChangeShellMessage extends MessageC2S {

    private final ResourceKey<Level> resourceKey;
    private final ShellTheme shellTheme;

    public ChangeShellMessage(ResourceKey<Level> tardisLevel, ShellTheme theme) {
        this.resourceKey = tardisLevel;
        this.shellTheme = theme;
    }

    public ChangeShellMessage(FriendlyByteBuf buffer) {
        resourceKey = buffer.readResourceKey(Registry.DIMENSION_REGISTRY);
        this.shellTheme = ShellTheme.valueOf(buffer.readUtf(500));
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.CHANGE_SHELL;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceKey(this.resourceKey);
        buf.writeUtf(this.shellTheme.toString());
    }

    @Override
    public void handle(MessageContext context) {
        Optional<ServerLevel> level = Optional.ofNullable(context.getPlayer().getServer().levels.get(resourceKey));
        level.ifPresent(x -> {
            TardisLevelOperator.get(x).ifPresent(y -> {
                y.setShellTheme(this.shellTheme);
            });
        });

    }


}
