package whocraft.tardis_refined.common.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/**
 * List of parameters for configuring an instance of the feature
 */
public class NbtTemplateFeatureConfig implements FeatureConfiguration {

    public static final Codec<NbtTemplateFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ResourceLocation.CODEC.fieldOf("template_location").forGetter(config -> config.templateLocation),
            Codec.INT.fieldOf("height_offset").orElse(0).forGetter(config -> config.heightOffset)
    ).apply(instance, NbtTemplateFeatureConfig::new));

    public final int heightOffset;
    public final ResourceLocation templateLocation;

    public NbtTemplateFeatureConfig(ResourceLocation templateLocation, int heightOffset) {
        this.templateLocation = templateLocation;
        this.heightOffset = heightOffset;
    }
}