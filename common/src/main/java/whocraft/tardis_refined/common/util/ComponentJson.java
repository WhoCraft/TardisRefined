package whocraft.tardis_refined.common.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import java.lang.reflect.Type;

public final class ComponentJson implements JsonSerializer<Component>, JsonDeserializer<Component> {
    public ComponentJson() {}

    public static Component parse(String json) {
        return ComponentSerialization.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseString(json)).getOrThrow();
    }

    public static String toJson(Component component) {
        return ComponentSerialization.CODEC.encodeStart(JsonOps.INSTANCE, component).getOrThrow().toString();
    }

    @Override
    public JsonElement serialize(Component component, Type type, JsonSerializationContext context) {
        return ComponentSerialization.CODEC.encodeStart(JsonOps.INSTANCE, component).getOrThrow();
    }

    @Override
    public Component deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
        return ComponentSerialization.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
    }
}
