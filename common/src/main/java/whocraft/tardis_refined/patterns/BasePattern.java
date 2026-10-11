package whocraft.tardis_refined.patterns;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.MiscHelper;

import java.util.Objects;

/**
 * Template for patterns. Patterns are assigned to a theme based on PatternCollection, though they also hold an instance of the Theme ID for lookup purposes.
 */
public abstract class BasePattern {

    private final Identifier identifier;
    protected Identifier themeId;
    private String name;

    public BasePattern(String id) {
        this(Identifier.fromNamespaceAndPath(TardisRefined.MODID, id));
    }

    public BasePattern(Identifier identifier) {
        this(identifier, TardisRefined.GSON.toJson(Component.literal(MiscHelper.getCleanName(identifier.getPath())).setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))));
    }

    public BasePattern(Identifier identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public abstract Codec<? extends BasePattern> getCodec();

    public String name() {
        return name;
    }

    public BasePattern setName(String name) {
        this.name = name;
        return this;
    }

    public Identifier id() {
        return this.identifier;
    }

    public Identifier getThemeId() {
        return this.themeId;
    }

    public BasePattern setThemeId(Identifier themeId) {
        this.themeId = themeId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasePattern basePattern = (BasePattern) o;
        return Objects.equals(identifier, basePattern.identifier) && this.themeId == basePattern.themeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, themeId);
    }

}