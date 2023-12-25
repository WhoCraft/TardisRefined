package whocraft.tardis_refined.common.hum;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public class HumEntry {


    private static final Codec<HumEntry> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter(HumEntry::getIdentifier),
                ResourceLocation.CODEC.fieldOf("hum_sound").forGetter(HumEntry::getSound),
                Codec.STRING.orElse("Placeholder").fieldOf("name_component").forGetter(HumEntry::getName)
        ).apply(instance, HumEntry::new);
    });
    private ResourceLocation identifier;
    private ResourceLocation sound;
    private String name;

    public HumEntry(ResourceLocation identifier, ResourceLocation sound, String name) {
        this.identifier = identifier;
        this.sound = sound;
        this.name = name;
    }

    public static Codec<HumEntry> codec() {
        return CODEC;
    }

    public ResourceLocation getSound() {
        return sound;
    }

    public void setSound(ResourceLocation sound) {
        this.sound = sound;
    }

    public ResourceLocation getIdentifier() {
        return identifier;
    }

    public void setIdentifier(ResourceLocation identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
