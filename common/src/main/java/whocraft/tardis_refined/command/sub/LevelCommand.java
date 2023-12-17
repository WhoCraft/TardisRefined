package whocraft.tardis_refined.command.sub;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.capability.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.util.CommandHelper;

public class LevelCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.argument("dimension", DimensionArgument.dimension()).suggests(CommandHelper.SUGGEST_TARDISES)
                .then(Commands.literal("get-points").executes(LevelCommand::getDimensionPoints))
                .then(Commands.literal("set-points").then(Commands.argument("points", IntegerArgumentType.integer()).executes(LevelCommand::setDimensionPoints)))
                .then(Commands.literal("add-points").then(Commands.argument("points", IntegerArgumentType.integer()).executes(LevelCommand::addDimensionPoints)))
                .then(Commands.literal("get-xp").executes(LevelCommand::getDimensionXp))
                .then(Commands.literal("set-xp").then(Commands.argument("xp", IntegerArgumentType.integer()).executes(LevelCommand::setDimensionXp)))
                .then(Commands.literal("add-xp").then(Commands.argument("xp", IntegerArgumentType.integer()).executes(LevelCommand::addDimensionXp)));

    }

    private static int getDimensionPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel dimension = DimensionArgument.getDimension(context, "dimension");

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            int points = tardisLevelOperator.getUpgradeHandler().getUpgradePoints();
            context.getSource().sendSystemMessage(Component.literal("Tardis: " + dimension.toString().split(":")[1] + " levels: " + points));
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int setDimensionPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel dimension = DimensionArgument.getDimension(context, "dimension");
        int points = IntegerArgumentType.getInteger(context, "points");

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            tardisLevelOperator.getUpgradeHandler().setUpgradePoints(points);
            context.getSource().sendSystemMessage(Component.literal("Tardis: " + dimension.toString().split(":")[1] + " points set to " + points));
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int addDimensionPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel dimension = DimensionArgument.getDimension(context, "dimension");
        int points = IntegerArgumentType.getInteger(context, "points");

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            UpgradeHandler upgradeHandler = tardisLevelOperator.getUpgradeHandler();
            upgradeHandler.setUpgradePoints(upgradeHandler.getUpgradePoints() + points);
            context.getSource().sendSystemMessage(Component.literal("Tardis: " + dimension.toString().split(":")[1] + " points set to " + upgradeHandler.getUpgradePoints()));
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int addDimensionXp(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel dimension = DimensionArgument.getDimension(context, "dimension");
        int points = IntegerArgumentType.getInteger(context, "points");

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            UpgradeHandler upgradeHandler = tardisLevelOperator.getUpgradeHandler();
            upgradeHandler.setUpgradeXP(upgradeHandler.getUpgradeXP() + points);
            context.getSource().sendSystemMessage(Component.literal("Tardis: " + dimension.toString().split(":")[1] + " points set to " + upgradeHandler.getUpgradeXP()));
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int getDimensionXp(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel dimension = DimensionArgument.getDimension(context, "dimension");

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            int xp = tardisLevelOperator.getUpgradeHandler().getUpgradeXP();
            context.getSource().sendSystemMessage(Component.literal("Tardis: " + dimension.toString().split(":")[1] + " xp: " + xp));
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int setDimensionXp(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel dimension = DimensionArgument.getDimension(context, "dimension");
        int points = IntegerArgumentType.getInteger(context, "xp");

        TardisLevelOperator.get(dimension).ifPresent(tardisLevelOperator -> {
            tardisLevelOperator.getUpgradeHandler().setUpgradePoints(points);
            context.getSource().sendSystemMessage(Component.literal("Tardis: " + dimension.toString().split(":")[1] + " xp set to " + points));
        });

        return Command.SINGLE_SUCCESS;
    }
}
