package whocraft.tardis_refined.common.network.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import whocraft.tardis_refined.client.model.blockentity.console.ConsolePatterns;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;

public class SyncConsolePatternsMessage extends MessageS2C {

    private final Map<ConsoleTheme, List<ConsolePatterns.Pattern>> patterns;

    public SyncConsolePatternsMessage(Map<ConsoleTheme, List<ConsolePatterns.Pattern>> patterns) {
        this.patterns = patterns;
    }

    public SyncConsolePatternsMessage(FriendlyByteBuf buf) {
        int size = buf.readInt();
        patterns = new HashMap<>();
        for (int i = 0; i < size; i++) {
            ConsoleTheme theme = ConsoleTheme.valueOf(buf.readUtf());
            List<ConsolePatterns.Pattern> patternList = new ArrayList<>();
            int patternSize = buf.readInt();
            for (int j = 0; j < patternSize; j++) {
                patternList.add(getPattern(buf));
            }
            patterns.put(theme, patternList);
        }
    }

    private static ConsolePatterns.Pattern getPattern(FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation(); // ID
        ResourceLocation texture = buf.readResourceLocation(); // texture
        String name = buf.readUtf(); // name
        ConsoleTheme theme = ConsoleTheme.valueOf(buf.readUtf()); // theme

        ConsolePatterns.Pattern consolePattern = new ConsolePatterns.Pattern(theme, id, texture);
        consolePattern.setName(name);
        return consolePattern;
    }

    @Override
    public MessageType getType() {
        return TardisNetwork.SYNC_PATTERNS;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(patterns.size());
        patterns.forEach((consoleTheme, patterns) -> {
            buf.writeUtf(consoleTheme.name());
            buf.writeInt(patterns.size());
            for (ConsolePatterns.Pattern pattern : patterns) {
                writePattern(pattern, buf);
            }
        });
    }

    private void writePattern(ConsolePatterns.Pattern pattern, FriendlyByteBuf buf) {
        buf.writeResourceLocation(pattern.id()); // ID
        buf.writeResourceLocation(pattern.textureLocation()); // texture
        buf.writeUtf(pattern.name()); // name
        buf.writeUtf(pattern.theme().name()); // theme
    }

    @Override
    public void handle(MessageContext context) {
        ConsolePatterns.clearPatterns();
        patterns.forEach((consoleTheme, patterns) -> {
            for (ConsolePatterns.Pattern pattern : patterns) {
                System.out.println(pattern.name());
                ConsolePatterns.addPattern(consoleTheme, pattern);
            }
        });
    }
}
