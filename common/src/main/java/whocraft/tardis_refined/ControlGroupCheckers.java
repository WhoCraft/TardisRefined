package whocraft.tardis_refined;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static whocraft.tardis_refined.TardisRefined.IS_RELEASE;

public class ControlGroupCheckers {

    public static final ControlGroupCheckers INSTANCE = new ControlGroupCheckers();
    private static final String API_URL = "https://mc.craig.software/api/skin/beta_players";
    private final List<String> uuidList;

    public ControlGroupCheckers() {
        this.uuidList = new ArrayList<>();
        fetchUUIDsFromAPI();
    }

    public static void tickServer(MinecraftServer serverLevel){
        if(IS_RELEASE) return;
        serverLevel.getPlayerList().getPlayers().iterator().forEachRemaining(serverPlayer -> {
            if(!ControlGroupCheckers.INSTANCE.isUUIDInList(serverPlayer.getStringUUID())){
                serverPlayer.connection.disconnect(Component.literal("Womp Womp! You're not on the list! :("));
            }
        });
    }


    public void fetchUUIDsFromAPI() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON response and extract UUIDs
                parseJSON(response.toString());
            } else {
                System.out.println("Failed to fetch UUIDs from API. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseJSON(String jsonResponse) {
        JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
        for (JsonElement element : jsonArray) {
            String uuid = element.getAsJsonObject().get("uuid").getAsString();
            uuidList.add(uuid);
        }
    }

    public boolean isUUIDInList(String uuid) {
        return uuidList.contains(uuid);
    }
}
