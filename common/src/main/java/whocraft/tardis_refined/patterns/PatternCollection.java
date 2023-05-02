package whocraft.tardis_refined.patterns;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Collection for holding multiple patterns, to be assigned to a specific Console or Shell Theme
 * <br> This acts as a more debuggable bridging object between Themes and Patterns.
 * <br> The output of this object is the JSON array part of a pattern JSON.
 * <br> There is a many-to-one connection between BasePattern and a Theme
 * <br> Hence, we tend to assign BasePattern the Theme ID which technically bypasses this object, but this object is still needed nonetheless
 * */
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

    /** Adds or merges the patterns of one PatternCollection with that of another*/
    public PatternCollection addOrMergePatterns(PatternCollection newPatternCollection) {
         List<T> newPatterns = newPatternCollection.patterns();
         return this.addOrMergePatterns(newPatterns);
    }

    public PatternCollection addOrMergePatterns(List<T> newPatterns){

        List<T> existingPatterns = this.patterns;

        //Create temporary maps to map patterns by id to allow for replacement or addition by id
        Map<ResourceLocation, T> finalPatternsMap = new HashMap<>();
        existingPatterns.stream().forEach(pattern -> {
            finalPatternsMap.put(pattern.id(), pattern);
        });

        Map<ResourceLocation, T> newPatternsMap = new HashMap<>();
        newPatterns.stream().forEach(pattern -> {
            newPatternsMap.put(pattern.id(), pattern);
        });

        //Add or replace all elements from the new patterns into the existing patterns list
        List<T> finalPatterns = new ArrayList<>();
        finalPatterns.addAll(existingPatterns);

        for (Map.Entry<ResourceLocation, T> entry : newPatternsMap.entrySet()){
            if (finalPatternsMap.containsKey(entry.getKey())){
                int index = finalPatterns.indexOf(entry.getValue());
                finalPatterns.set(index, entry.getValue());
            }
            else {
                finalPatterns.add(entry.getValue());
            }
        }

        this.patterns = finalPatterns;

        return this;
    }
}
