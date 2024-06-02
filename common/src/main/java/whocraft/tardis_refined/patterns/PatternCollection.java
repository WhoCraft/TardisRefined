package whocraft.tardis_refined.patterns;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * Collection for holding multiple patterns, to be assigned to a specific Console or Shell Theme
 * <br> This acts as a more debuggable bridging object between Themes and Patterns.
 * <br> The output of this object is the JSON array part of a pattern JSON.
 * <br> There is a many-to-one connection between BasePattern and a Theme
 * <br> Hence, we tend to assign BasePattern the Theme ID which technically bypasses this object, but this object is still needed nonetheless
 */
public abstract class PatternCollection<T extends BasePattern> {

    protected ResourceLocation themeId;

    protected List<T> patterns;

    protected boolean isReplace;


    public PatternCollection(List<T> patterns) {
        this(false, patterns);
    }

    public PatternCollection(boolean isReplace, List<T> patterns) {
        this.isReplace = isReplace;
        this.patterns = patterns;
    }

    public PatternCollection setThemeId(ResourceLocation themeId) {
        this.themeId = themeId;
        return this;
    }

    public List<T> patterns() {
        return this.patterns;
    }

    public boolean isReplace() {
        return this.isReplace;
    }

    public PatternCollection setPatterns(List<T> patterns) {
        this.patterns = patterns;
        return this;
    }

    public ResourceLocation themeId() {
        return this.themeId;
    }

    public abstract Codec<? extends PatternCollection<T>> getCodec();

}
