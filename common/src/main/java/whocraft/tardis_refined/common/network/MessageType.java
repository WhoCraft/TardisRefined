package whocraft.tardis_refined.common.network;


public class MessageType {

    private final String id;
    private final NetworkManager networkManager;
    private final NetworkManager.MessageDecoder<?> decoder;
    private final boolean toServer;

    MessageType(String id, NetworkManager networkManager, NetworkManager.MessageDecoder<?> decoder, boolean toServer) {
        this.id = id;
        this.networkManager = networkManager;
        this.decoder = decoder;
        this.toServer = toServer;
    }

    public String getId() {
        return this.id;
    }

    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    public NetworkManager.MessageDecoder<?> getDecoder() {
        return this.decoder;
    }

    public boolean isToServer() {
        return this.toServer;
    }

    public boolean isToClient() {
        return !this.toServer;
    }

}
