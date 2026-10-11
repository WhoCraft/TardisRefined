package whocraft.tardis_refined.client.model.blockentity.shell;

import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.patterns.ShellPatterns;
import whocraft.tardis_refined.client.ShellPreviewDiagnostics;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;

import java.util.Set;

/** GeckoLib model adapter for the built-in exterior shell geometry. */
public final class ThemeShellGeoModel<T extends GeoAnimatable> extends GeoModel<T> {
    public static final DataTicket<GlobalShellBlockEntity> BLOCK_ENTITY =
            DataTicket.create("tardis_refined:shell_entity", GlobalShellBlockEntity.class);
    public static final DataTicket<Identifier> SHELL_THEME =
            DataTicket.create("tardis_refined:shell_theme", Identifier.class);
    public static final DataTicket<Identifier> SHELL_PATTERN =
            DataTicket.create("tardis_refined:shell_pattern", Identifier.class);
    public static final DataTicket<Boolean> SHELL_OPEN =
            DataTicket.create("tardis_refined:shell_open", Boolean.class);
    private static final Identifier EMPTY_ANIMATIONS =
            Identifier.fromNamespaceAndPath("tardis_refined", "shell/empty");
    private static final Set<String> BUILT_IN_SHELLS = Set.of(
            "factory", "police_box", "phone_booth", "mystic", "present", "drifter", "vending",
            "briefcase", "groening", "big_ben", "nuka", "growth", "portaloo", "pagoda",
            "lift", "hieroglyph", "castle", "pathfinder", "half_baked", "shulker");
    // The preview renderer owns a dedicated model instance. Keeping its appearance
    // here avoids relying on GeckoLib to preserve custom tickets through PIP state copies.
    private volatile Identifier previewTheme;
    private volatile Identifier previewPattern;

    public void setPreviewAppearance(Identifier theme, Identifier pattern) {
        this.previewTheme = theme;
        this.previewPattern = pattern;
    }

    public static boolean supports(Identifier theme) {
        return theme != null && "tardis_refined".equals(theme.getNamespace())
                && BUILT_IN_SHELLS.contains(theme.getPath());
    }

    public static Identifier selectedTheme(GeoRenderState state) {
        Identifier selected = state.getGeckolibData(SHELL_THEME);
        if (selected != null) return selected;
        GlobalShellBlockEntity entity = state.getGeckolibData(BLOCK_ENTITY);
        return entity == null ? Identifier.fromNamespaceAndPath("tardis_refined", "factory") : entity.theme();
    }

    private Identifier selectedPattern(GeoRenderState state) {
        Identifier selected = state.getGeckolibData(SHELL_PATTERN);
        if (selected != null) return selected;
        if (previewPattern != null && state.getGeckolibData(BLOCK_ENTITY) == null) return previewPattern;
        GlobalShellBlockEntity entity = state.getGeckolibData(BLOCK_ENTITY);
        return entity == null ? null : entity.pattern().id();
    }

    @Override
    public Identifier getModelResource(GeoRenderState state) {
        GlobalShellBlockEntity entity = state.getGeckolibData(BLOCK_ENTITY);
        Identifier theme = state.getGeckolibData(SHELL_THEME);
        if (theme == null) theme = entity == null && previewTheme != null ? previewTheme : selectedTheme(state);
        String source = entity == null ? "preview-entity" : "world-shell";
        String shell = theme.getPath();
        if (!BUILT_IN_SHELLS.contains(shell)) shell = "factory";
        Identifier model = Identifier.fromNamespaceAndPath("tardis_refined", "shell/" + shell);
        ShellPreviewDiagnostics.report("gecko-model-" + source,
                "source=" + source + ", theme=" + theme + ", model=" + model);
        return model;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState state) {
        GlobalShellBlockEntity entity = state.getGeckolibData(BLOCK_ENTITY);
        Identifier theme = state.getGeckolibData(SHELL_THEME);
        if (theme == null) theme = entity == null && previewTheme != null ? previewTheme : selectedTheme(state);
        Identifier patternId = selectedPattern(state);
        String source = entity == null ? "preview-entity" : "world-shell";
        if (patternId == null && entity == null) {
            return Identifier.fromNamespaceAndPath("tardis_refined", "textures/blockentity/shell/factory/factory.png");
        }
        Identifier texture = ShellPatterns.getPatternOrDefault(theme, patternId == null ? entity.pattern().id() : patternId)
                .shellTexture().texture();
        ShellPreviewDiagnostics.report("gecko-texture-" + source,
                "source=" + source + ", theme=" + theme + ", pattern=" + patternId + ", texture=" + texture);
        return texture;
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return EMPTY_ANIMATIONS;
    }
}
