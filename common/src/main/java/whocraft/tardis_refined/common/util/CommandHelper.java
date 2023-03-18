package whocraft.tardis_refined.common.util;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Collection;

public class CommandHelper {

    public static final SuggestionProvider<CommandSourceStack> SUGGEST_TARDISES = (context, suggestionBuilder) -> {
        Collection<ResourceKey<Level>> collection = DimensionUtil.getTardisLevels(context.getSource().getServer());
        return SharedSuggestionProvider.suggestResource(collection.stream().map(ResourceKey::location), suggestionBuilder);
    };

    public static MutableComponent createComponentWithTooltip(String text, String tooltipText) {
        MutableComponent component = Component.literal("[" + text + "]");
        component.withStyle(style -> {
            return style.withColor(ChatFormatting.YELLOW)
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(tooltipText)))
                    .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, tooltipText));
        });
        return component;
    }

    public static MutableComponent createComponentOpenFile(String text, String tooltipText) {
        MutableComponent component = Component.literal("[" + text + "]");
        component.withStyle(style -> {
            return style.withColor(ChatFormatting.YELLOW)
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(tooltipText)))
                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, tooltipText));
        });
        return component;
    }

    /** Creates a Text Component but doesn't allow copying of the tooltip text. Instead, will copy the text in the text component*/
    public static MutableComponent createTextWithoutTooltipCopying(String text, String tooltipText) {
        MutableComponent component = Component.literal("[" + text + "]");
        component.withStyle(style -> {
            return style.withColor(ChatFormatting.YELLOW)
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(tooltipText)))
                    .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text));
        });
        return component;
    }

}