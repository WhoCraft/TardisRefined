package whocraft.tardis_refined.patterns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public class ShellPatternCollection extends PatternCollection<ShellPattern>{

    public static final Codec<ShellPatternCollection> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                Codec.BOOL.optionalFieldOf("replace", false).forGetter(PatternCollection::isReplace),
                ShellPattern.CODEC.listOf().fieldOf("patterns").forGetter(ShellPatternCollection::patterns)
        ).apply(instance, ShellPatternCollection::new);
    });

    public ShellPatternCollection(boolean replace, List<ShellPattern> patterns) {
        super(replace, patterns);
    }

    public ShellPatternCollection(List<ShellPattern> patterns) {
        super(patterns);
    }

    @Override
    public Codec<ShellPatternCollection> getCodec() {
        return CODEC;
    }
}
