package whocraft.tardis_refined.command.sub;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.util.CommandHelper;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRDimensionTypes;

import java.util.Optional;

public class DeleteCommand implements Command<CommandSourceStack> {

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("delete")
                .then(Commands.argument("tardis", DimensionArgument.dimension()).suggests(CommandHelper.SUGGEST_TARDISES).executes(context -> deleteTARDIS(context, DimensionArgument.getDimension(context, "tardis")))
                                .executes(context -> deleteTARDIS(context, DimensionArgument.getDimension(context, "tardis"))));
    }

    private static int deleteTARDIS(CommandContext<CommandSourceStack> context, ServerLevel tardis) {
        Optional<TardisLevelOperator> tardisData = TardisLevelOperator.get(tardis);

        ServerPlayer sender = context.getSource().getPlayer();

        if (tardisData.isPresent() && tardis.dimensionTypeId() == TRDimensionTypes.TARDIS) {

            if (tardisData.get().deleteTARDIS()) {
                PlayerUtil.sendMessage(sender, Component.translatable(ModMessages.DELETED_TARDIS), false);
            }

            return Command.SINGLE_SUCCESS;
        }
        PlayerUtil.sendMessage(sender, Component.translatable(ModMessages.CMD_DIM_NOT_A_TARDIS, tardis.dimensionTypeId().location().toString()), false);
        return 0;
    }


    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        return 0;
    }
}