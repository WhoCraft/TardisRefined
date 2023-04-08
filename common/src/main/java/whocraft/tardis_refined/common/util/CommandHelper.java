package whocraft.tardis_refined.common.util;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Collection;

public class CommandHelper {

    public static final SuggestionProvider<CommandSourceStack> SUGGEST_TARDISES = (context, suggestionBuilder) -> {
        Collection<ResourceKey<Level>> collection = DimensionUtil.getTardisLevels(context.getSource().getServer());
        return SharedSuggestionProvider.suggestResource(collection.stream().map(ResourceKey::location), suggestionBuilder);
    };

}