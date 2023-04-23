package whocraft.tardis_refined.patterns;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
/** Template for multiple patterns, to be assigned to a specific Console or Shell Theme*/
public abstract class PatternCollection<T extends BasePattern> {

    protected ResourceLocation themeId;

    protected List<T> patterns;

    public PatternCollection(List<T> patterns){
        this.patterns = patterns;
    }

    public PatternCollection setThemeId(ResourceLocation themeId){
        this.themeId = themeId;
        return this;
    }

    public List<T> patterns(){
        return this.patterns;
    }

    public PatternCollection setPatterns(List<T> patterns){
        this.patterns = patterns;
        return this;
    }

    public ResourceLocation themeId(){ return this.themeId;}

    public abstract Codec<? extends PatternCollection<T>> getCodec();
}
