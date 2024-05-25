package whocraft.tardis_refined.command.sub;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import whocraft.tardis_refined.command.arguments.UpgradeArgumentType;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.Upgrade;
import whocraft.tardis_refined.common.capability.upgrades.Upgrades;
import whocraft.tardis_refined.common.util.CommandHelper;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class UpgradesCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("upgrades")
                .then(Commands.literal("lock")
                        .then(Commands.argument("upgrade", UpgradeArgumentType.upgradeArgumentType())
                                .then(Commands.argument("tardis", DimensionArgument.dimension())
                                        .suggests(CommandHelper.SUGGEST_TARDISES)
                                        .executes(UpgradesCommand::setUpgradeLocked))))
                .then(Commands.literal("unlock")
                        .then(Commands.argument("upgrade", UpgradeArgumentType.upgradeArgumentType())
                                .then(Commands.argument("tardis", DimensionArgument.dimension())
                                        .suggests(CommandHelper.SUGGEST_TARDISES)
                                        .executes(UpgradesCommand::setUpgradeUnlocked))))
                .then(Commands.literal("unlock-all")
                        .then(Commands.argument("tardis", DimensionArgument.dimension())
                                .suggests(CommandHelper.SUGGEST_TARDISES)
                                .executes(UpgradesCommand::unlockAll)))
                .then(Commands.literal("lock-all")
                        .then(Commands.argument("tardis", DimensionArgument.dimension())
                                .suggests(CommandHelper.SUGGEST_TARDISES)
                                .executes(UpgradesCommand::lockAll)));
    }



    private static int unlockAll(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel dimension = DimensionArgument.getDimension(context, "tardis");
        MutableComponent tardisId = TardisHelper.createTardisIdComponent(dimension.dimension().location());

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            for (RegistrySupplier<Upgrade> entry : Upgrades.UPGRADE_DEFERRED_REGISTRY.getEntries()) {
                tardisLevelOperator.getUpgradeHandler().unlockUpgrade(entry.get());
                context.getSource().sendSystemMessage(Component.translatable(ModMessages.CMD_UPGRADE_UNLOCK, entry.get().getDisplayName(), tardisId));
            }
        });
        return Command.SINGLE_SUCCESS;

    }

    private static int lockAll(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel dimension = DimensionArgument.getDimension(context, "tardis");
        MutableComponent tardisId = TardisHelper.createTardisIdComponent(dimension.dimension().location());

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            for (RegistrySupplier<Upgrade> entry : Upgrades.UPGRADE_DEFERRED_REGISTRY.getEntries()) {
                tardisLevelOperator.getUpgradeHandler().lockUpgrade(entry.get());
                context.getSource().sendSystemMessage(Component.translatable(ModMessages.CMD_UPGRADE_LOCK, entry.get().getDisplayName(), tardisId));
            }
        });
        return Command.SINGLE_SUCCESS;

    }

    private static int setUpgradeLocked(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        Upgrade upgrade = UpgradeArgumentType.getUpgrade(context, "upgrade");
        ServerLevel dimension = DimensionArgument.getDimension(context, "tardis");

        MutableComponent tardisId = TardisHelper.createTardisIdComponent(dimension.dimension().location());

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            tardisLevelOperator.getUpgradeHandler().lockUpgrade(upgrade);
            context.getSource().sendSystemMessage(Component.translatable(ModMessages.CMD_UPGRADE_LOCK, upgrade.getDisplayName(), tardisId));
        });

        return Command.SINGLE_SUCCESS;
    }


    private static int setUpgradeUnlocked(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        Upgrade upgrade = UpgradeArgumentType.getUpgrade(context, "upgrade");
        ServerLevel dimension = DimensionArgument.getDimension(context, "tardis");

        MutableComponent tardisId = TardisHelper.createTardisIdComponent(dimension.dimension().location());

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            tardisLevelOperator.getUpgradeHandler().unlockUpgrade(upgrade);
            context.getSource().sendSystemMessage(Component.translatable(ModMessages.CMD_UPGRADE_UNLOCK, upgrade.getDisplayName(), tardisId));
        });

        return Command.SINGLE_SUCCESS;
    }
}
