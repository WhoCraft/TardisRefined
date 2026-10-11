package whocraft.tardis_refined.common.entity;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import whocraft.tardis_refined.registry.TREntityRegistry;

/** Client-side-only animatable used to submit the selected shell model in GUI picture-in-picture rendering. */
public final class ShellPreviewEntity extends Entity implements GeoEntity {
    private final AnimatableInstanceCache geckoCache = GeckoLibUtil.createInstanceCache(this);
    private Identifier shellTheme = Identifier.fromNamespaceAndPath("tardis_refined", "factory");
    private Identifier shellPattern = Identifier.fromNamespaceAndPath("tardis_refined", "factory");
    private boolean vortexOnly;
    private boolean vortexBackgroundPreview;
    private Identifier vortexPreviewType;
    private int vortexViewportWidth;
    private int vortexViewportHeight;
    private final Quaternionf shellFlightRotation = new Quaternionf();
    private final Vector3f shellFlightOffset = new Vector3f();

    public ShellPreviewEntity(EntityType<?> type, Level level) {
        super(type, level);
        noPhysics = true;
    }

    public ShellPreviewEntity(Level level) {
        this(TREntityRegistry.SHELL_PREVIEW.get(), level);
    }

    public void setAppearance(Identifier theme, Identifier pattern) {
        if (theme != null) shellTheme = theme;
        if (pattern != null) shellPattern = pattern;
    }

    public void setVortexOnly(boolean vortexOnly) { this.vortexOnly = vortexOnly; }
    public void setVortexBackgroundPreview(boolean preview) { this.vortexBackgroundPreview = preview; }
    public void setVortexPreviewType(Identifier vortexType) { this.vortexPreviewType = vortexType; }
    public void setVortexViewport(int width, int height) {
        this.vortexViewportWidth = width;
        this.vortexViewportHeight = height;
    }
    public void setShellFlightRotation(Quaternionf rotation) { this.shellFlightRotation.set(rotation); }
    public void setShellFlightOffset(float x, float y, float z) { this.shellFlightOffset.set(x, y, z); }

    public Identifier shellTheme() { return shellTheme; }
    public Identifier shellPattern() { return shellPattern; }
    public boolean vortexOnly() { return vortexOnly; }
    public boolean vortexBackgroundPreview() { return vortexBackgroundPreview; }
    public Identifier vortexPreviewType() { return vortexPreviewType; }
    public int vortexViewportWidth() { return vortexViewportWidth; }
    public int vortexViewportHeight() { return vortexViewportHeight; }
    public Quaternionf shellFlightRotation() { return shellFlightRotation; }
    public Vector3f shellFlightOffset() { return shellFlightOffset; }

    @Override protected void defineSynchedData(SynchedEntityData.Builder builder) {}
    @Override protected void readAdditionalSaveData(ValueInput input) {}
    @Override protected void addAdditionalSaveData(ValueOutput output) {}
    @Override public boolean hurtServer(ServerLevel level, DamageSource source, float amount) { return false; }
    @Override public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // GeckoLib requires at least one controller to populate its immutable
        // render-state manager ticket, even though the shell model has no bones
        // animated by GeckoLib in this preview.
        controllers.add(new AnimationController<>("shell_preview", state -> PlayState.STOP));
    }
    @Override public AnimatableInstanceCache getAnimatableInstanceCache() { return geckoCache; }
}
