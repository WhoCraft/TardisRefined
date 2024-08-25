package whocraft.tardis_refined.command.sub;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRConsoleThemes;

public class GetControlIndexCommand {

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("get_control_index").then(Commands.argument("entity", EntityArgument.entity()).executes(context ->
                getControlIndex(context, EntityArgument.getEntity(context, "entity"))));
    }

    public static int getControlIndex(CommandContext<CommandSourceStack> context, Entity entity) {
        ServerPlayer player = context.getSource().getPlayer();
        if (entity instanceof ControlEntity controlEntity && controlEntity.getConsoleBlockEntity() != null) {
            ConsoleTheme theme = TRConsoleThemes.CONSOLE_THEME_REGISTRY.get(controlEntity.getConsoleBlockEntity().theme());
            int index = theme.getControlSpecificationList().indexOf(controlEntity.controlSpecification());
            player.sendSystemMessage(Component.translatable(ModMessages.CMD_CONTROL_INDEX_SUCCESS, index));
        } else {
            player.sendSystemMessage(Component.translatable(ModMessages.CMD_CONTROL_INDEX_INVALID_ENTITY, entity.getName()).withStyle(ChatFormatting.RED));
        }
        return 0;
    }

}
