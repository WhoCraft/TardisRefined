package whocraft.tardis_refined.patterns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;

public class ShellPattern extends BasePattern {

    public static final Codec<ShellPattern> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter(ShellPattern::id),
                Codec.STRING.orElse("Placeholder").fieldOf("name_component").forGetter(ShellPattern::name),
                PatternTexture.getCodec().fieldOf("exterior").forGetter(ShellPattern::exteriorDoorTexture),
                PatternTexture.getCodec().fieldOf("interior").forGetter(ShellPattern::interiorDoorTexture)
        ).apply(instance, ShellPattern::new);
    });

    private final PatternTexture interiorDoorTexture;
    private final PatternTexture exteriorDoorTexture;

    public ShellPattern(String identifier, PatternTexture exteriorDoorTexture, PatternTexture interiorDoorTexture) {
        this(new ResourceLocation(TardisRefined.MODID, identifier), exteriorDoorTexture, interiorDoorTexture);
    }

    public ShellPattern(ResourceLocation identifier, PatternTexture exteriorDoorTexture, PatternTexture interiorDoorTexture) {
        super(identifier);
        this.exteriorDoorTexture = exteriorDoorTexture;
        this.interiorDoorTexture = interiorDoorTexture;
    }

    public ShellPattern(ResourceLocation identifier, String name, PatternTexture exteriorDoorTexture, PatternTexture interiorDoorTexture) {
        super(identifier, name);
        this.exteriorDoorTexture = exteriorDoorTexture;
        this.interiorDoorTexture = interiorDoorTexture;
    }

    public PatternTexture exteriorDoorTexture() {
        return this.exteriorDoorTexture;
    }

    public PatternTexture interiorDoorTexture() {
        return this.interiorDoorTexture;
    }

    @Override
    public Codec<ShellPattern> getCodec() {
        return CODEC;
    }
}
