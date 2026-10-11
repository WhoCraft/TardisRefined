package whocraft.tardis_refined.patterns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.TardisRefined;

/**
 * Common template for texture objects used in instances of {@link BasePattern}
 */
public class PatternTexture {

    private static final Codec<PatternTexture> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(PatternTexture::texture),
                Codec.BOOL.fieldOf("emissive").forGetter(PatternTexture::emissive)
        ).apply(instance, PatternTexture::new);
    });
    private final Identifier textureLocation, emissiveTexture;
    private boolean hasEmissiveTexture = false;

    public PatternTexture(String textureLocation, boolean hasEmissiveTexture) {
        this(Identifier.fromNamespaceAndPath(TardisRefined.MODID, textureLocation), hasEmissiveTexture);
    }

    public PatternTexture(String textureLocation) {
        this(Identifier.fromNamespaceAndPath(TardisRefined.MODID, textureLocation));
    }

    public PatternTexture(Identifier textureLocation) {
        this(textureLocation, false);
    }

    public PatternTexture(Identifier textureLocation, boolean hasEmissiveTexture) {
        this.textureLocation = textureLocation;
        this.hasEmissiveTexture = hasEmissiveTexture;
        this.emissiveTexture = Identifier.fromNamespaceAndPath(textureLocation.getNamespace(), textureLocation.getPath().replace(".png", "_emissive.png"));
    }

    public static Codec<PatternTexture> getCodec() {
        return CODEC;
    }

    public boolean emissive() {
        return this.hasEmissiveTexture;
    }

    public PatternTexture setEmissive(boolean hasEmissiveTexture) {
        this.hasEmissiveTexture = hasEmissiveTexture;
        return this;
    }

    public Identifier emissiveTexture() {
        return this.emissiveTexture;
    }

    public Identifier texture() {
        return this.textureLocation;
    }

}