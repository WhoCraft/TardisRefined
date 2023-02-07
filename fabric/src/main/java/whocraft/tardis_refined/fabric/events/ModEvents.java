package whocraft.tardis_refined.fabric.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DelayedTeleportData;
import whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerImpl;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.registry.DimensionTypes;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_WORLD_TICK;
import static net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.START_WORLD_TICK;

public class ModEvents {

    private static final ArrayList<UUID> uuids = new ArrayList<>();

    public static void addCommonEvents() {

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> !MiscHelper.shouldCancelBreaking(world, player, pos, state));

        END_WORLD_TICK.register(DelayedTeleportData::tick);
        START_WORLD_TICK.register(world -> {
            if (world.dimensionTypeId().location() == DimensionTypes.TARDIS.location()) {
                TardisLevelOperator.get(world).get().tick(world);
            }
        });
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ServerLevel world = server.getLevel(Level.OVERWORLD);
            DimensionHandlerImpl.loadLevels(world);
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> DimensionHandlerImpl.clear());
    }

    public static void addClientEvents() {
        ClientTickEvents.START_WORLD_TICK.register(world -> {
            TardisClientData.getAllEntries().forEach((levelResourceKey, tardisClientData) -> {
                if (world.dimension() != levelResourceKey) {
                    return;
                }
                tardisClientData.tickClientside();
            });

            if (Minecraft.getInstance().level == null) {
                TardisClientData.clearAll();
            }
        });
    }


    public static List<String> extractUuids(String json) {
        ArrayList<String> uuids = new ArrayList<>();
        JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            String uuid = jsonObject.get("uuid").getAsString();
            uuids.add(uuid);
        }
        return uuids;
    }

    public static JsonElement getResponse(URL url) throws IOException {
        System.out.println(url);
        HttpsURLConnection uc = (HttpsURLConnection) url.openConnection();
        uc.connect();
        uc.setConnectTimeout(120000);
        uc = (HttpsURLConnection) url.openConnection();
        uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
        InputStream inputStream = uc.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        JsonElement finalData = GsonHelper.fromJson(TardisRefined.GSON, br, JsonElement.class, false);
        uc.disconnect();
        return finalData;
    }

}
