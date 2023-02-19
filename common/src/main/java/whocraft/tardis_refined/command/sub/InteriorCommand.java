package whocraft.tardis_refined.command.sub;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.common.util.TRTeleporter;
import whocraft.tardis_refined.constants.ModMessages;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InteriorCommand implements Command<CommandSourceStack> {

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("interior")
                .then(Commands.argument("tardis", DimensionArgument.dimension()).executes(context -> teleportToInterior(context, List.of(Objects.requireNonNull(context.getSource().getPlayer())), DimensionArgument.getDimension(context, "tardis")))
                        .then(Commands.argument("entities", EntityArgument.entities())
                                        .executes(context -> teleportToInterior(context, EntityArgument.getEntities(context, "entities"), DimensionArgument.getDimension(context, "tardis")))));
    }

    private static int teleportToInterior(CommandContext<CommandSourceStack> context, Collection<? extends Entity> entities, ServerLevel tardis) {
        Optional<TardisLevelOperator> tardisData = TardisLevelOperator.get(tardis);

        ServerPlayer sender = context.getSource().getPlayer();

        if (tardisData.isPresent()) {
            tardisData.ifPresent(tardisLevelOperator -> entities.forEach(entity -> teleportToInterior(tardisLevelOperator, entity)));
            return 0;
        }
        PlayerUtil.sendMessage(sender, Component.translatable(ModMessages.CMD_DIM_NOT_A_TARDIS, tardis.dimensionTypeId().location().toString()), false);
        return 0;
    }

    private static void teleportToInterior(TardisLevelOperator tardisLevelOperator, Entity entity) {
        Level tpLevel = tardisLevelOperator.getLevel();
        if (tpLevel instanceof ServerLevel finalTpLevel) {
            BlockPos pos = tardisLevelOperator.getInternalDoor().getDoorPosition();
            pos = pos.relative(tardisLevelOperator.getInternalDoor().getEntryRotation(), 1);
            TRTeleporter.performTeleport(entity, finalTpLevel, pos.getX(), pos.getY(), pos.getZ(), entity.getYRot(), entity.getXRot());
        }
    }


    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        return 0;
    }
}