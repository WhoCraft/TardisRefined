package whocraft.tardis_refined.patterns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.patterns.sound.ConsoleSoundProfile;
import whocraft.tardis_refined.patterns.sound.TRConsoleSoundProfiles;

import java.util.Optional;

public class ConsolePattern extends BasePattern {

    public static final Codec<ConsolePattern> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                Identifier.CODEC.fieldOf("id").forGetter(ConsolePattern::id),
                Codec.STRING.orElse("Placeholder").fieldOf("name_component").forGetter(BasePattern::name),
                PatternTexture.getCodec().fieldOf("texture_definition").forGetter(ConsolePattern::patternTexture),
                ConsoleSoundProfile.CODEC.optionalFieldOf("sound_profile").orElse(Optional.of(TRConsoleSoundProfiles.DEFAULT_SOUND_PROFILE)).forGetter(ConsolePattern::soundProfile)
        ).apply(instance, ConsolePattern::new);
    });

    private final PatternTexture patternTexture;
    private final Optional<ConsoleSoundProfile> consoleSoundProfile;

    public ConsolePattern(String identifier, PatternTexture textureDefinition, Optional<ConsoleSoundProfile> consoleSoundProfile) {
        this(Identifier.fromNamespaceAndPath(TardisRefined.MODID, identifier), textureDefinition, consoleSoundProfile);
    }

    public ConsolePattern(Identifier identifier, PatternTexture textureDefinition, Optional<ConsoleSoundProfile> consoleSoundProfile) {
        super(identifier);
        this.patternTexture = textureDefinition;
        this.consoleSoundProfile = consoleSoundProfile;
    }

    public ConsolePattern(Identifier identifier, String name, PatternTexture textureDefinition, Optional<ConsoleSoundProfile> consoleSoundProfile) {
        super(identifier, name);
        this.patternTexture = textureDefinition;
        this.consoleSoundProfile = consoleSoundProfile;
    }

    public PatternTexture patternTexture() {
        return patternTexture;
    }

    public Identifier texture() {
        return this.patternTexture.texture();
    }

    public Identifier emissiveTexture() {
        return this.patternTexture.emissiveTexture();
    }

    public Optional<ConsoleSoundProfile> soundProfile() {
        return this.consoleSoundProfile;
    }

    @Override
    public Codec<ConsolePattern> getCodec() {
        return CODEC;
    }
}
