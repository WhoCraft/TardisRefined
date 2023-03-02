package whocraft.tardis_refined.common.network.messages;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.patterns.BasePattern;
import whocraft.tardis_refined.patterns.ConsolePatterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncConsolePatternsMessage extends MessageS2C {

    private final Map<ConsoleTheme, List<BasePattern<ConsoleTheme>>> patterns;

    public SyncConsolePatternsMessage(Map<ConsoleTheme, List<BasePattern<ConsoleTheme>>> patterns) {
        this.patterns = patterns;
    }

    public SyncConsolePatternsMessage(FriendlyByteBuf buf) {
        int size = buf.readInt();
        patterns = new HashMap<>();
        for (int i = 0; i < size; i++) {
            ConsoleTheme theme = ConsoleTheme.valueOf(buf.readUtf());
            List<BasePattern<ConsoleTheme>> basePatternList = new ArrayList<>();
            int patternSize = buf.readInt();
            for (int j = 0; j < patternSize; j++) {
                basePatternList.add(getPattern(buf));
            }
            patterns.put(theme, basePatternList);
        }
    }

    private static BasePattern<ConsoleTheme> getPattern(FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation(); // ID
        ResourceLocation texture = buf.readResourceLocation(); // texture
        String name = buf.readUtf(); // name
        ConsoleTheme theme = ConsoleTheme.valueOf(buf.readUtf()); // theme
        boolean glow = buf.readBoolean(); // name

        BasePattern<ConsoleTheme> consoleBasePattern = new BasePattern<ConsoleTheme>(theme, id, texture);
        consoleBasePattern.setName(name);
        consoleBasePattern.setEmissive(glow);
        return consoleBasePattern;
    }

    @Override
    public MessageType getType() {
        return TardisNetwork.SYNC_CONSOLE_PATTERNS;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(patterns.size());
        patterns.forEach((consoleTheme, patterns) -> {
            buf.writeUtf(consoleTheme.name());
            buf.writeInt(patterns.size());
            for (BasePattern<ConsoleTheme> basePattern : patterns) {
                writePattern(basePattern, buf);
            }
        });
    }

    private void writePattern(BasePattern<ConsoleTheme> basePattern, FriendlyByteBuf buf) {
        buf.writeResourceLocation(basePattern.id()); // ID
        buf.writeResourceLocation(basePattern.texture()); // texture
        buf.writeUtf(basePattern.name()); // name
        buf.writeUtf(basePattern.theme().name()); // theme
        buf.writeBoolean(basePattern.emissive()); // glow
    }

    @Override
    public void handle(MessageContext context) {
        patterns.forEach((consoleTheme, patterns) -> {
            for (BasePattern<ConsoleTheme> basePattern : patterns) {
                ConsolePatterns.addPattern(consoleTheme, basePattern);
            }
        });
    }
}
