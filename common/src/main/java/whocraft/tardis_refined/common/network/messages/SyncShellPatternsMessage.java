package whocraft.tardis_refined.common.network.messages;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.patterns.BasePattern;
import whocraft.tardis_refined.patterns.ShellPattern;
import whocraft.tardis_refined.patterns.ShellPatterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncShellPatternsMessage extends MessageS2C {

    private final Map<ShellTheme, List<ShellPattern>> patterns;

    public SyncShellPatternsMessage(Map<ShellTheme, List<ShellPattern>> patterns) {
        this.patterns = patterns;
    }

    public SyncShellPatternsMessage(FriendlyByteBuf buf) {
        int size = buf.readInt();
        patterns = new HashMap<>();
        for (int i = 0; i < size; i++) {
            ShellTheme theme = ShellTheme.valueOf(buf.readUtf());
            List<ShellPattern> basePatternList = new ArrayList<>();
            int patternSize = buf.readInt();
            for (int j = 0; j < patternSize; j++) {
                basePatternList.add(getPattern(buf));
            }
            patterns.put(theme, basePatternList);
        }
    }

    private static ShellPattern getPattern(FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation(); // ID
        ResourceLocation texture = buf.readResourceLocation(); // texture
        ResourceLocation intTexture = buf.readResourceLocation(); // texture
        String name = buf.readUtf(); // name
        ShellTheme theme = ShellTheme.valueOf(buf.readUtf()); // theme
        boolean glow = buf.readBoolean(); // name

        ShellPattern consoleBasePattern = new ShellPattern(theme, id, texture, intTexture);
        consoleBasePattern.setName(name);
        consoleBasePattern.setEmissive(glow);
        return consoleBasePattern;
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
            for (ShellPattern basePattern : patterns) {
                writePattern(basePattern, buf);
            }
        });
    }

    private void writePattern(ShellPattern basePattern, FriendlyByteBuf buf) {
        buf.writeResourceLocation(basePattern.id()); // ID
        buf.writeResourceLocation(basePattern.texture()); // texture
        buf.writeResourceLocation(basePattern.interiorDoorTexture()); // texture
        buf.writeUtf(basePattern.name()); // name
        buf.writeUtf(basePattern.theme().name()); // theme
        buf.writeBoolean(basePattern.emissive()); // glow
    }

    @Override
    public void handle(MessageContext context) {
        patterns.forEach((ShellTheme, patterns) -> {
            for (ShellPattern basePattern : patterns) {
                ShellPatterns.addPattern(ShellTheme, basePattern);
            }
        });
    }
}
