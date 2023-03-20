package whocraft.tardis_refined.command.sub.export;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.util.CommandHelper;
import whocraft.tardis_refined.common.util.DatapackHelper;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.common.mixin.MinecraftServerStorageAccessor;

import java.nio.file.Path;

public class ExportDesktopCommand implements Command<CommandSourceStack> {

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("desktop")
                .then(Commands.argument("pos1", BlockPosArgument.blockPos())
                    .then(Commands.argument("pos2", BlockPosArgument.blockPos())
                        .then(Commands.argument("ignore_entities", BoolArgumentType.bool())
                            .then(Commands.argument("desktop_display_name", ComponentArgument.textComponent())
                                .then(Commands.argument("namespace", StringArgumentType.word())
                                    .then(Commands.argument("desktop_id", StringArgumentType.word())
                                        .then(Commands.argument("datapack_name", StringArgumentType.string())
                                            .executes(context -> exportDesktop(context, BlockPosArgument.getSpawnablePos(context, "pos1"),
                                                BlockPosArgument.getSpawnablePos(context, "pos2"),
                                                BoolArgumentType.getBool(context, "ignore_entities"),
                                                ComponentArgument.getComponent(context, "desktop_display_name"),
                                                StringArgumentType.getString(context, "namespace"),
                                                StringArgumentType.getString(context, "desktop_id"),
                                                StringArgumentType.getString(context, "datapack_name"))))))))));
    }

    private static int exportDesktop(CommandContext<CommandSourceStack> context, BlockPos bottomCorner, BlockPos topCorner, boolean ignoreEntities, Component displayName, String namespace, String desktopId, String datapackName) {
        ResourceLocation loc = new ResourceLocation(namespace, desktopId);
        DesktopTheme theme = new DesktopTheme(loc,loc,Component.Serializer.toJson(displayName));
        ServerPlayer sender = context.getSource().getPlayer();

        MinecraftServerStorageAccessor accessor = (MinecraftServerStorageAccessor)context.getSource().getServer();
        Path rootDir = accessor.getStorageSource().getLevelPath(LevelResource.DATAPACK_DIR).normalize();
        Path datapackRoot = rootDir.resolve(datapackName);

        PlayerUtil.sendMessage(sender, Component.translatable(ModMessages.CMD_EXPORT_DESKTOP_IN_PROGRESS, loc), false);

        boolean exported = DatapackHelper.writeDesktopToFile(context.getSource().getLevel(), bottomCorner, topCorner, ignoreEntities, loc, theme, datapackName);

        if (exported) {
            Component path = CommandHelper.createComponentOpenFile(datapackName, datapackRoot.toString());
            Component reloadCommandSuggestion = CommandHelper.createComponentSuggestCommand("/reload", "/reload");
            PlayerUtil.sendMessage(sender, Component.translatable(ModMessages.CMD_EXPORT_DESKTOP_SUCCESS, loc, path, reloadCommandSuggestion), false);
            return Command.SINGLE_SUCCESS;
        }
        PlayerUtil.sendMessage(sender, Component.translatable(ModMessages.CMD_EXPORT_DESKTOP_FAIL, loc), false);
        return 0;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        return 0;
    }
}