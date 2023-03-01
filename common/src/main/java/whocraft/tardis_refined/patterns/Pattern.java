package whocraft.tardis_refined.patterns;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class Pattern<T extends Pattern.DataDrivenPattern> {

    public interface DataDrivenPattern {
        String getObjectName();
    }

    private final ResourceLocation textureLocation, emissiveTexture;
    private final ResourceLocation identifier;
    private String name;
    private boolean hasEmissiveTexture = false;

    public T theme() {
        return theme;
    }

    private final T theme;

    public Pattern(T consoleTheme, ResourceLocation identifier, ResourceLocation texture) {
        this.identifier = identifier;
        this.textureLocation = texture;
        this.emissiveTexture = new ResourceLocation(texture.getNamespace(), texture.getPath().replace(".png", "_emissive.png"));
        this.theme = consoleTheme;
        this.name = identifier.getPath().substring(0, 1).toUpperCase() + identifier.getPath().substring(1).replace("_", "");
    }

    public String name() {
        return name;
    }

    public boolean emissive() {
        return hasEmissiveTexture;
    }

    public Pattern<? extends DataDrivenPattern> setEmissive(boolean hasEmissiveTexture) {
        this.hasEmissiveTexture = hasEmissiveTexture;
        return this;
    }

    public Pattern<? extends DataDrivenPattern> setName(String name) {
        this.name = name;
        return this;
    }

    public ResourceLocation emissiveTexture() {
        return emissiveTexture;
    }

    public ResourceLocation texture() {
        return textureLocation;
    }

    public ResourceLocation id() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pattern<T> pattern = (Pattern<T>) o;
        return Objects.equals(textureLocation, pattern.textureLocation) && Objects.equals(identifier, pattern.identifier) && theme == pattern.theme;
    }

    @Override
    public int hashCode() {
        return Objects.hash(textureLocation, identifier, theme);
    }
}