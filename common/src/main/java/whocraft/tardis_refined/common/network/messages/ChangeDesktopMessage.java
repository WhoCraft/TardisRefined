package whocraft.tardis_refined.common.network.messages;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.registry.SoundRegistry;

import java.util.Optional;

public class ChangeDesktopMessage extends MessageC2S {

    private final ResourceKey<Level> resourceKey;
    private final DesktopTheme desktopTheme;

    public ChangeDesktopMessage(ResourceKey<Level> tardisLevel, DesktopTheme theme) {
        this.resourceKey = tardisLevel;
        this.desktopTheme = theme;
    }

    public ChangeDesktopMessage(FriendlyByteBuf buffer) {
        resourceKey = buffer.readResourceKey(Registry.DIMENSION_REGISTRY);
        this.desktopTheme = TardisDesktops.getDesktopThemeById(buffer.readUtf(500));
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.CHANGE_DESKTOP;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceKey(this.resourceKey);
        buf.writeUtf(this.desktopTheme.id);
    }

    @Override
    public void handle(MessageContext context) {
        Optional<ServerLevel> level = Optional.ofNullable(context.getPlayer().getServer().levels.get(resourceKey));
        level.ifPresent(x -> {
            TardisLevelOperator.get(x).ifPresent(y -> {
                if (!y.getControlManager().isInFlight()) {
                    y.getInteriorManager().prepareDesktop(desktopTheme);
                } else {
                    x.playSound(null, context.getPlayer(), SoundRegistry.TARDIS_SINGLE_FLY.get(), SoundSource.BLOCKS, 10f, 0.25f);
                }
            });
        });

    }


}
