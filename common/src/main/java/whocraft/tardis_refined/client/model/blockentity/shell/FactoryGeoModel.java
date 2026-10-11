package whocraft.tardis_refined.client.model.blockentity.shell;

import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;

/** GeckoLib 5 model adapter for the factory exterior shell. */
public final class FactoryGeoModel extends GeoModel<GlobalShellBlockEntity> {
    private static final Identifier MODEL = Identifier.fromNamespaceAndPath("tardis_refined", "shell/factory");
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("tardis_refined", "textures/blockentity/shell/factory/factory.png");
    private static final Identifier ANIMATIONS = Identifier.fromNamespaceAndPath("tardis_refined", "shell/empty");

    @Override public Identifier getModelResource(GeoRenderState state) { return MODEL; }
    @Override public Identifier getTextureResource(GeoRenderState state) { return TEXTURE; }
    @Override public Identifier getAnimationResource(GlobalShellBlockEntity animatable) { return ANIMATIONS; }
}
