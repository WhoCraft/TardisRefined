package whocraft.tardis_refined.registry;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public abstract class CustomRegistry<T> {


    @ExpectPlatform
    public static <T> CustomRegistry<T> create(ResourceLocation resourceLocation) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static <T> CustomRegistry<T> create(ResourceLocation resourceLocation, boolean syncToClient) {
        throw new AssertionError();
    }

    public abstract ResourceKey<? extends Registry<T>> getRegistryKey();

    public abstract T get(ResourceLocation key);

    public abstract ResourceLocation getKey(T object);

    public abstract Set<ResourceLocation> getKeys();

    public abstract boolean containsKey(ResourceLocation key);

    public abstract Collection<T> getValues();

    public abstract Supplier<Codec<T>> getCodec();
}