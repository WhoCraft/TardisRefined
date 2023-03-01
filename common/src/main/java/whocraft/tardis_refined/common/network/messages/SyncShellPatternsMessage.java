package whocraft.tardis_refined.common.network.messages;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.patterns.Pattern;
import whocraft.tardis_refined.patterns.ShellPatterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncShellPatternsMessage extends MessageS2C {

    private final Map<ShellTheme, List<Pattern<ShellTheme>>> patterns;

    public SyncShellPatternsMessage(Map<ShellTheme, List<Pattern<ShellTheme>>> patterns) {
        this.patterns = patterns;
    }

    public SyncShellPatternsMessage(FriendlyByteBuf buf) {
        int size = buf.readInt();
        patterns = new HashMap<>();
        for (int i = 0; i < size; i++) {
            ShellTheme theme = ShellTheme.valueOf(buf.readUtf());
            List<Pattern<ShellTheme>> patternList = new ArrayList<>();
            int patternSize = buf.readInt();
            for (int j = 0; j < patternSize; j++) {
                patternList.add(getPattern(buf));
            }
            patterns.put(theme, patternList);
        }
    }

    private static Pattern<ShellTheme> getPattern(FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation(); // ID
        ResourceLocation texture = buf.readResourceLocation(); // texture
        String name = buf.readUtf(); // name
        ShellTheme theme = ShellTheme.valueOf(buf.readUtf()); // theme
        boolean glow = buf.readBoolean(); // name

        Pattern<ShellTheme> consolePattern = new Pattern<>(theme, id, texture);
        consolePattern.setName(name);
        consolePattern.setEmissive(glow);
        return consolePattern;
    }

    @Override
    public MessageType getType() {
        return TardisNetwork.SYNC_SHELL_PATTERNS;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(patterns.size());
        patterns.forEach((ShellTheme, patterns) -> {
            buf.writeUtf(ShellTheme.name());
            buf.writeInt(patterns.size());
            for (Pattern<ShellTheme> pattern : patterns) {
                writePattern(pattern, buf);
            }
        });
    }

    private void writePattern(Pattern<ShellTheme> pattern, FriendlyByteBuf buf) {
        buf.writeResourceLocation(pattern.id()); // ID
        buf.writeResourceLocation(pattern.texture()); // texture
        buf.writeUtf(pattern.name()); // name
        buf.writeUtf(pattern.theme().name()); // theme
        buf.writeBoolean(pattern.emissive()); // glow
    }

    @Override
    public void handle(MessageContext context) {
        patterns.forEach((ShellTheme, patterns) -> {
            for (Pattern<ShellTheme> pattern : patterns) {
                ShellPatterns.addPattern(ShellTheme, pattern);
            }
        });
    }
}
