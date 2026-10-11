package whocraft.tardis_refined.common.network.messages;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.api.event.ShellChangeSources;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.registry.TRUpgrades;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

import java.util.Objects;
import java.util.Optional;

public class C2SChangeShell extends MessageC2S {

    private final ResourceKey<Level> resourceKey;
    private final Identifier shellTheme;
    private final ShellPattern pattern;

    public C2SChangeShell(ResourceKey<Level> tardisLevel, Identifier theme, ShellPattern pattern) {
        this.resourceKey = tardisLevel;
        this.shellTheme = theme;
        this.pattern = pattern;
    }

    public C2SChangeShell(FriendlyByteBuf buffer) {
        resourceKey = buffer.readResourceKey(Registries.DIMENSION);
        this.shellTheme = buffer.readIdentifier();
        this.pattern = ShellPatterns.getPatternOrDefault(shellTheme, buffer.readIdentifier());
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.CHANGE_SHELL;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceKey(this.resourceKey);
        buf.writeIdentifier(this.shellTheme);
        buf.writeIdentifier(pattern.id());
    }

    @Override
    public void handle(MessageContext context) {
        TardisRefined.LOGGER.info("Shell change request from {}: tardis={}, theme={}, pattern={}",
                context.getPlayer().getName().getString(), resourceKey.identifier(), shellTheme, pattern.id());
        Optional<ServerLevel> level = Optional.ofNullable(context.getPlayer().level().getServer().getLevel(resourceKey));
        level.flatMap(TardisLevelOperator::get).ifPresentOrElse(y -> {
            boolean chameleonUnlocked = TRUpgrades.CHAMELEON_CIRCUIT_SYSTEM.get().isUnlocked(y.getUpgradeHandler());
            boolean fuelAvailable = y.getExteriorManager().hasEnoughFuelForShellChange();
            TardisNavLocation location = y.getPilotingManager().getCurrentLocation();
            if (chameleonUnlocked && fuelAvailable) {
                y.setShellTheme(this.shellTheme, pattern.id(), ShellChangeSources.GENERIC_UPDATE);
                y.getPilotingManager().removeFuel(y.getExteriorManager().getFuelForShellChange());
                TardisClientData clientData = y.tardisClientData();
                clientData.setShellTheme(this.shellTheme);
                clientData.setShellPattern(this.pattern.id());
                clientData.sync();
                Object exterior = location.getLevel().getBlockEntity(location.getPosition());
                String exteriorState = exterior instanceof GlobalShellBlockEntity shell
                        ? "theme=" + shell.theme() + ", pattern=" + shell.pattern().id() + ", tardis=" + shell.getTardisId()
                        : "missing/wrong block entity: " + exterior;
                TardisRefined.LOGGER.info("Shell change applied: tardis={}, storedTheme={}, storedPattern={}, exterior@{} {}",
                        resourceKey.identifier(), y.getAestheticHandler().getShellTheme(), y.getAestheticHandler().shellPattern().id(),
                        location.getPosition(), exteriorState);
            } else {
                TardisRefined.LOGGER.warn("Shell change rejected: tardis={}, theme={}, chameleonUnlocked={}, fuelAvailable={}",
                        resourceKey.identifier(), shellTheme, chameleonUnlocked, fuelAvailable);
                PlayerUtil.sendMessage(context.getPlayer(), ModMessages.HARDWARE_OFFLINE, true);
            }
        }, () -> TardisRefined.LOGGER.warn("Shell change could not find a TARDIS operator for dimension {}", resourceKey.identifier()));

    }


}
