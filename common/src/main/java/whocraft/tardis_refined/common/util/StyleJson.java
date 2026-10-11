package whocraft.tardis_refined.common.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.chat.Style;

import java.lang.reflect.Type;

/** Gson bridge for Minecraft's codec-backed text styles. */
public final class StyleJson implements JsonSerializer<Style>, JsonDeserializer<Style> {
    @Override
    public JsonElement serialize(Style style, Type type, JsonSerializationContext context) {
        return Style.Serializer.CODEC.encodeStart(JsonOps.INSTANCE, style).getOrThrow();
    }

    @Override
    public Style deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        return Style.Serializer.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
    }
}
