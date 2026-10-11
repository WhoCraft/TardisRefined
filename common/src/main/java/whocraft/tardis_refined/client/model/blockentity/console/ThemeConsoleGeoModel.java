package whocraft.tardis_refined.client.model.blockentity.console;

import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.patterns.ConsolePatterns;

import java.util.List;

import java.util.Set;

/** GeckoLib model adapter for the built-in console geometries. */
public final class ThemeConsoleGeoModel extends GeoModel<GlobalConsoleBlockEntity> {
    public static final DataTicket<GlobalConsoleBlockEntity> BLOCK_ENTITY =
            DataTicket.create("tardis_refined:console_entity", GlobalConsoleBlockEntity.class);
    public static final DataTicket<Identifier> CONSOLE_THEME =
            DataTicket.create("tardis_refined:console_theme", Identifier.class);

    private static final Identifier BUILT_IN_ANIMATIONS =
            Identifier.fromNamespaceAndPath("tardis_refined", "console/builtin");
    // The built-in themes use their legacy ModelPart/Frame path so their
    // authored 1.20.2 animation definitions and variant models stay intact.
    private static final Set<String> BUILT_IN_CONSOLES = Set.of();

    public static boolean supports(Identifier theme) {
        return theme != null && "tardis_refined".equals(theme.getNamespace())
                && BUILT_IN_CONSOLES.contains(theme.getPath());
    }

    public static Identifier selectedTheme(GeoRenderState state) {
        Identifier selected = state.getGeckolibData(CONSOLE_THEME);
        if (selected != null) return selected;
        GlobalConsoleBlockEntity entity = state.getGeckolibData(BLOCK_ENTITY);
        return entity == null ? Identifier.fromNamespaceAndPath("tardis_refined", "factory") : entity.theme();
    }

    @Override
    public Identifier getModelResource(GeoRenderState state) {
        Identifier theme = selectedTheme(state);
        String name = supports(theme) ? theme.getPath() : "factory";
        return Identifier.fromNamespaceAndPath("tardis_refined", "console/" + name);
    }

    @Override
    public Identifier getTextureResource(GeoRenderState state) {
        GlobalConsoleBlockEntity entity = state.getGeckolibData(BLOCK_ENTITY);
        Identifier theme = selectedTheme(state);
        ConsolePattern blockEntityPattern = entity == null ? null : entity.pattern();
        ConsolePattern pattern = null;
        if (entity != null && entity.getLevel() != null) {
            Identifier syncedPattern = TardisClientData.getInstance(entity.getLevel().dimension()).getConsolePattern();
            if (syncedPattern != null && (blockEntityPattern == null || !theme.equals(blockEntityPattern.getThemeId()))) {
                pattern = resolvePattern(theme, ConsolePatterns.getPatternOrDefault(theme, syncedPattern));
            }
        }
        if (pattern == null) pattern = resolvePattern(theme, blockEntityPattern);
        if (pattern != null) return pattern.texture();

        String name = supports(theme) ? theme.getPath() : "factory";
        return Identifier.fromNamespaceAndPath("tardis_refined",
                "textures/blockentity/console/" + name + "/" + name + "_console.png");
    }

    private static ConsolePattern resolvePattern(Identifier theme, ConsolePattern blockEntityPattern) {
        if (theme == null) return blockEntityPattern;
        if (blockEntityPattern != null && theme.equals(blockEntityPattern.getThemeId())) {
            return blockEntityPattern;
        }

        List<ConsolePattern> patterns = ConsolePatterns.getPatternsForTheme(theme);
        if (patterns == null || patterns.isEmpty()) return null;

        Identifier preferredPattern = blockEntityPattern == null ? null : blockEntityPattern.id();
        for (ConsolePattern pattern : patterns) {
            if (pattern.id().equals(preferredPattern)) return pattern;
        }
        return patterns.getFirst();
    }

    @Override
    public Identifier getAnimationResource(GlobalConsoleBlockEntity animatable) {
        return BUILT_IN_ANIMATIONS;
    }
}
