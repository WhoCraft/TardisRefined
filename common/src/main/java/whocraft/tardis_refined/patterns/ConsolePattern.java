package whocraft.tardis_refined.patterns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;

public class ConsolePattern extends BasePattern {

    public static final Codec<ConsolePattern> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter(ConsolePattern::id),
                Codec.STRING.orElse("Placeholder").fieldOf("name_component").forGetter(BasePattern::name),
                PatternTexture.getCodec().fieldOf("texture_definition").forGetter(ConsolePattern::patternTexture)
        ).apply(instance, ConsolePattern::new);
    });

    private final PatternTexture patternTexture;

    public ConsolePattern(String identifier, PatternTexture textureDefinition) {
        this(new ResourceLocation(TardisRefined.MODID, identifier), textureDefinition);
    }

    public ConsolePattern(ResourceLocation identifier, PatternTexture textureDefinition) {
        super(identifier);
        this.patternTexture = textureDefinition;
    }

    public ConsolePattern(ResourceLocation identifier, String name, PatternTexture textureDefinition) {
        super(identifier, name);
        this.patternTexture = textureDefinition;
    }

    public PatternTexture patternTexture() {
        return patternTexture;
    }

    public ResourceLocation texture() {
        return this.patternTexture.texture();
    }

    public ResourceLocation emissiveTexture() {
        return this.patternTexture.emissiveTexture();
    }

    @Override
    public Codec<ConsolePattern> getCodec() {
        return CODEC;
    }
}
