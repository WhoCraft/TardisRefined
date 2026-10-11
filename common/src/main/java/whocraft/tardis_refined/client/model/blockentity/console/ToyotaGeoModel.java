package whocraft.tardis_refined.client.model.blockentity.console;

import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;

/** GeckoLib 5 model adapter for the Toyota console geometry. */
public final class ToyotaGeoModel extends GeoModel<GlobalConsoleBlockEntity> {
    private static final Identifier MODEL = Identifier.fromNamespaceAndPath("tardis_refined", "console/toyota");
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("tardis_refined", "textures/blockentity/console/toyota/toyota_console.png");
    private static final Identifier ANIMATIONS = Identifier.fromNamespaceAndPath("tardis_refined", "console/toyota");

    @Override public Identifier getModelResource(GeoRenderState state) { return MODEL; }
    @Override public Identifier getTextureResource(GeoRenderState state) { return TEXTURE; }
    @Override public Identifier getAnimationResource(GlobalConsoleBlockEntity animatable) { return ANIMATIONS; }
}
