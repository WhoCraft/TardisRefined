package whocraft.tardis_refined.common.soundscape.hum;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.common.util.RegistryHelper;

import java.util.ArrayList;
import java.util.List;

public class HumEntry {

    private static final Codec<HumEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(HumEntry::getIdentifier),
            Identifier.CODEC.fieldOf("hum_sound").forGetter(HumEntry::getSoundEventId),
            Codec.list(Identifier.CODEC).fieldOf("ambient_sounds").forGetter(HumEntry::getAmbientSounds),
            Codec.STRING.orElse("Placeholder").fieldOf("name_component").forGetter(HumEntry::getNameComponent)
    ).apply(instance, HumEntry::new));

    private final Identifier identifier;
    private Identifier soundEventId;
    private List<Identifier> ambientSounds;
    private String nameComponent;

    /**
     * Generic constructor
     *
     * @param identifier    - Enter the registry name.
     * @param soundEventId  - the underlying SoundEvent id
     * @param ambientSounds - List of ambient sounds that can play in addition to the Hum sound event
     * @param nameComponent - the string that will be displayed. Can be either a language file translation key or a component
     */
    public HumEntry(Identifier identifier, Identifier soundEventId, List<Identifier> ambientSounds, String nameComponent) {
        this.identifier = identifier;
        this.soundEventId = soundEventId;
        this.ambientSounds = ambientSounds;
        this.nameComponent = nameComponent;
    }

    /**
     * Convenience constructor that creates a default value for the nameComponent parameter by creating a gold coloured text component
     *
     * @param identifier
     * @param soundEventId
     * @param ambientSounds
     */
    public HumEntry(Identifier identifier, Identifier soundEventId, List<Identifier> ambientSounds) {
        this(identifier, soundEventId, ambientSounds, TardisRefined.GSON.toJson(Component.literal(MiscHelper.getCleanName(identifier.getPath())).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))));
    }

    /**
     * Tardis Refined specific constructor
     *
     * @param identifier    - Enter the registry name. No need to add namespace because the TardisRefined namespace is already added
     * @param soundEventId  - the underlying SoundEvent id
     * @param ambientSounds - List of ambient sounds that can play in addition to the Hum sound event
     */
    public HumEntry(String identifier, Identifier soundEventId, List<Identifier> ambientSounds) {
        this(RegistryHelper.makeKey(identifier), soundEventId, ambientSounds);
    }

    /**
     * Tardis Refined specific constructor with an empty list of ambient sounds
     *
     * @param identifier   - Enter the registry name. No need to add namespace because the TardisRefined namespace is already added
     * @param soundEventId - the underlying SoundEvent id
     */
    public HumEntry(String identifier, Identifier soundEventId) {
        this(identifier, soundEventId, new ArrayList<>());
    }


    public static Codec<HumEntry> codec() {
        return CODEC;
    }

    /**
     * Gets the underlying SoundEvent's ID
     */
    public Identifier getSoundEventId() {
        return soundEventId;
    }

    public HumEntry setSoundEventId(Identifier soundEventId) {
        this.soundEventId = soundEventId;
        return this;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public List<Identifier> getAmbientSounds() {
        return ambientSounds;
    }

    public HumEntry setAmbientSounds(List<Identifier> ambientSounds) {
        this.ambientSounds = ambientSounds;
        return this;
    }

    public String getNameComponent() {
        return nameComponent;
    }

    public void setNameComponent(String nameComponent) {
        this.nameComponent = nameComponent;
    }
}
