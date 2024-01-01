package whocraft.tardis_refined.common.hum;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class HumEntry {

    private static final Codec<HumEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(HumEntry::getIdentifier),
            ResourceLocation.CODEC.fieldOf("hum_sound").forGetter(HumEntry::getSound),
            Codec.list(ResourceLocation.CODEC).fieldOf("ambient_sounds").forGetter(HumEntry::getAmbientSounds),
            Codec.STRING.orElse("Placeholder").fieldOf("name_component").forGetter(HumEntry::getName)
    ).apply(instance, HumEntry::new));

    private final ResourceLocation identifier;
    private ResourceLocation sound;
    private List<ResourceLocation> ambientSounds;
    private String name;

    public HumEntry(ResourceLocation identifier, ResourceLocation sound, List<ResourceLocation> ambientSounds, String name) {
        System.out.println("HELLO: " + ambientSounds.size());
        this.identifier = identifier;
        this.sound = sound;
        this.ambientSounds = ambientSounds;
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

    public List<ResourceLocation> getAmbientSounds() {
        return ambientSounds;
    }

    public void setAmbientSounds(List<ResourceLocation> ambientSounds) {
        this.ambientSounds = ambientSounds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
