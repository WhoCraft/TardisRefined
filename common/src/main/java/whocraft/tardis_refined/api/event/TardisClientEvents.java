package whocraft.tardis_refined.api.event;

import net.minecraft.client.model.geom.EntityModelSet;

public class TardisClientEvents {

    public static final Event<TardisClientEvents.SetupModels> SHELLENTRY_MODELS_SETUP = new Event<>(TardisClientEvents.SetupModels.class, listeners -> (EntityModelSet context) -> {
        for(TardisClientEvents.SetupModels listener : listeners) {
            listener.setUpShellAndInteriorModels(context);
        }
    });

    @FunctionalInterface
    public interface SetupModels {
        void setUpShellAndInteriorModels(EntityModelSet context);
    }


}
