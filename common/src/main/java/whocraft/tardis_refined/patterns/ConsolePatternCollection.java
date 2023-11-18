package whocraft.tardis_refined.patterns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public class ConsolePatternCollection extends PatternCollection<ConsolePattern>{

    public static final Codec<ConsolePatternCollection> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                Codec.BOOL.optionalFieldOf("replace", false).forGetter(PatternCollection::isReplace),
                ConsolePattern.CODEC.listOf().fieldOf("patterns").forGetter(ConsolePatternCollection::patterns)
        ).apply(instance, ConsolePatternCollection::new);
    });

    public ConsolePatternCollection(boolean replace, List<ConsolePattern> patterns) {
        super(replace, patterns);
    }

    public ConsolePatternCollection(List<ConsolePattern> patterns) {
        super(patterns);
    }

    @Override
    public Codec<ConsolePatternCollection> getCodec() {
        return CODEC;
    }
}
