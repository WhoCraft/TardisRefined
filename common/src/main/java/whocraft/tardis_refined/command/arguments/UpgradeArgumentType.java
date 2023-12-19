package whocraft.tardis_refined.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.capability.upgrades.Upgrade;
import whocraft.tardis_refined.common.capability.upgrades.Upgrades;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class UpgradeArgumentType implements ArgumentType<Upgrade> {

    public static final DynamicCommandExceptionType INVALID_UPGRADE_EXCEPTION = new DynamicCommandExceptionType((UPGRADE) -> Component.translatable("argument.regeneration.upgrade.invalid", UPGRADE));

    public static UpgradeArgumentType upgradeArgumentType() {
        return new UpgradeArgumentType();
    }

    @Override
    public Upgrade parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation location = ResourceLocation.read(reader);
        Upgrade upgrade = Upgrades.UPGRADE_DEFERRED_REGISTRY.getRegistry().get(location);
        if (upgrade != null) {
            return upgrade;
        }
        throw INVALID_UPGRADE_EXCEPTION.create(location);    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(Upgrades.UPGRADE_REGISTRY.keySet(), builder);
    }

    @Override
    public Collection<String> getExamples() {
        return ArgumentType.super.getExamples();
    }
}