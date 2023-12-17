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
import net.minecraft.server.level.ServerLevel;
import whocraft.tardis_refined.command.arguments.UpgradeArgumentType;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.Upgrade;
import whocraft.tardis_refined.common.util.CommandHelper;

public class UpgradesCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("upgrades")
                .then(Commands.literal("lock")
                        .then(Commands.argument("dimension", DimensionArgument.dimension()).suggests(CommandHelper.SUGGEST_TARDISES)
                                .then(Commands.argument("upgrade", UpgradeArgumentType.upgradeArgumentType())
                                        .executes(UpgradesCommand::setUpgradeLocked))))
                .then(Commands.literal("unlock")
                        .then(Commands.argument("dimension", DimensionArgument.dimension()).suggests(CommandHelper.SUGGEST_TARDISES)
                                .then(Commands.argument("upgrade", UpgradeArgumentType.upgradeArgumentType())
                                        .executes(UpgradesCommand::setUpgradeUnlocked))));
    }

    private static int setUpgradeLocked(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel dimension = DimensionArgument.getDimension(context, "dimension");
        Upgrade upgrade = context.getArgument("upgrade", Upgrade.class);

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            tardisLevelOperator.getUpgradeHandler().lockUpgrade(upgrade);
            context.getSource().sendSystemMessage(Component.literal("Tardis: " + dimension.toString().split(":")[1] + " locked " + upgrade.getDisplayName()));
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int setUpgradeUnlocked(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel dimension = DimensionArgument.getDimension(context, "dimension");
        Upgrade upgrade = context.getArgument("upgrade", Upgrade.class);

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            tardisLevelOperator.getUpgradeHandler().unlockUpgrade(upgrade);
            context.getSource().sendSystemMessage(Component.literal("Tardis: " + dimension.toString().split(":")[1] + " unlocked " + upgrade.getDisplayName()));
        });

        return Command.SINGLE_SUCCESS;
    }
}
