package whocraft.tardis_refined.fabric.client;

import com.geckolib.renderer.GeoBlockRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.constant.dataticket.DataTicket;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.client.model.blockentity.console.ThemeConsoleGeoModel;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.ShellPreviewDiagnostics;
import whocraft.tardis_refined.client.renderer.LegacyBlockEntityRenderState;
import whocraft.tardis_refined.client.renderer.blockentity.console.GlobalConsoleRenderer;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;

import java.util.Set;

/** Uses GeckoLib where its coordinate conventions match and keeps the native model path for other consoles. */
public final class GeckoConsoleRenderer extends GeoBlockRenderer<GlobalConsoleBlockEntity, BlockEntityRenderState> {
    private static final Set<String> CONSOLES_WITH_AUTHORED_UPRIGHT_ORIENTATION = Set.of(
            "factory", "crystal", "copper", "victorian", "myst", "nuka", "initiative");
    private final GlobalConsoleRenderer legacy;

    public GeckoConsoleRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new ThemeConsoleGeoModel());
        this.legacy = new GlobalConsoleRenderer(context);
    }

    @Override
    public void preRenderPass(RenderPassInfo<BlockEntityRenderState> info, SubmitNodeCollector collector) {
        super.preRenderPass(info, collector);
        // Only apply the Geo orientation to models that will actually use the
        // Geo renderer. Refurbished keeps the legacy Bedrock-to-ModelPart path.
        Identifier theme = info.getGeckolibData(ThemeConsoleGeoModel.CONSOLE_THEME);
        if (!ThemeConsoleGeoModel.supports(theme)) return;
        String name = theme.getPath();
        if (CONSOLES_WITH_AUTHORED_UPRIGHT_ORIENTATION.contains(name)) {
            // These models are already authored upright. The shared 180-degree
            // Z rotation was inverting them, and the shared +1.5 Y offset was
            // raising them. Removing both corrections leaves them upright and
            // lowers the visible model by 1.5 blocks.
        } else if (!"coral".equals(name)) {
            // Keep the individually corrected orientation for any model whose
            // geometry still needs the established flip.
            info.poseStack().translate(0, 1.5, 0);
            info.poseStack().rotateDegrees(Axis.ZP, 180);
        }
    }

    @Override
    public void extractRenderState(GlobalConsoleBlockEntity entity, BlockEntityRenderState state, float partialTick,
                                   Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        super.extractRenderState(entity, state, partialTick, cameraPosition, crumblingOverlay);
        GeoRenderState geoState = (GeoRenderState) state;
        geoState.addGeckolibData(ThemeConsoleGeoModel.BLOCK_ENTITY, entity);
        geoState.addGeckolibData(ThemeConsoleGeoModel.CONSOLE_THEME, selectedTheme(entity));
    }

    @Override
    public void submit(BlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector,
                       CameraRenderState cameraState) {
        GeoRenderState geoState = (GeoRenderState) state;
        GlobalConsoleBlockEntity entity = geoState.getGeckolibData(ThemeConsoleGeoModel.BLOCK_ENTITY);
        Identifier theme = geoState.getGeckolibData(ThemeConsoleGeoModel.CONSOLE_THEME);
        if (entity != null && ThemeConsoleGeoModel.supports(theme)) {
            if (entity.getLevel() != null) {
                TardisClientData appearance = TardisClientData.getInstance(entity.getLevel().dimension());
                ShellPreviewDiagnostics.report("interior-console-gecko-render",
                        "dimension=" + entity.getLevel().dimension().identifier()
                                + ", console=" + theme
                                + ", blockEntityTheme=" + entity.theme()
                                + ", cachedTheme=" + appearance.getConsoleTheme()
                                + ", cachedPattern=" + appearance.getConsolePattern()
                                + ", pattern=" + entity.pattern().id() + "/" + entity.pattern().getThemeId()
                                + ", shell=" + appearance.getShellTheme() + "/" + appearance.getShellPattern());
            }
            super.submit(state, poseStack, collector, cameraState);
            return;
        }

        LegacyBlockEntityRenderState<GlobalConsoleBlockEntity> legacyState = legacy.createRenderState();
        legacy.extractRenderState(entity, legacyState, geoState.getPartialTick(), Vec3.ZERO, state.breakProgress);
        legacy.submit(legacyState, poseStack, collector, cameraState);
    }

    private static Identifier selectedTheme(GlobalConsoleBlockEntity entity) {
        Identifier storedTheme = entity == null ? null : entity.theme();
        Identifier factoryTheme = whocraft.tardis_refined.common.tardis.themes.ConsoleTheme.FACTORY.getId();
        if (storedTheme != null && !factoryTheme.equals(storedTheme)
                && ThemeConsoleGeoModel.supports(storedTheme)) return storedTheme;
        if (entity != null && entity.getLevel() != null) {
            Identifier syncedTheme = TardisClientData.getInstance(entity.getLevel().dimension()).getConsoleTheme();
            if (syncedTheme != null && !factoryTheme.equals(syncedTheme)) return syncedTheme;
        }
        return storedTheme;
    }
}
