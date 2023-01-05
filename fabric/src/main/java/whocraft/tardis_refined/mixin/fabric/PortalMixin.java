package whocraft.tardis_refined.mixin.fabric;

import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qouteall.imm_ptl.core.mc_utils.IPEntityEventListenableEntity;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.PortalLike;
import qouteall.q_misc_util.my_util.BoxPredicate;
import qouteall.q_misc_util.my_util.Plane;
import whocraft.tardis_refined.common.dimension.fabric.DimensionHandlerIP;
import whocraft.tardis_refined.common.dimension.fabric.TARDISPortalData;

import java.util.UUID;
import java.util.function.Consumer;

@Mixin(value = Portal.class, remap = false)
public abstract class PortalMixin extends Entity implements PortalLike, IPEntityEventListenableEntity, TARDISPortalData {
    @Shadow public abstract void reloadAndSyncToClient();

    @Unique
    private UUID tardisID;

    public PortalMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "INVOKE", target = "Lqouteall/q_misc_util/my_util/SignalBiArged;emit(Ljava/lang/Object;Ljava/lang/Object;)V", shift = At.Shift.AFTER))
    private void addTARDISData(CompoundTag compoundTag, CallbackInfo ci) {
        if(tardisID != null) {
            compoundTag.putUUID("tardis_id", tardisID);
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At(value = "INVOKE", target = "Lqouteall/q_misc_util/my_util/SignalBiArged;emit(Ljava/lang/Object;Ljava/lang/Object;)V", shift = At.Shift.AFTER))
    private void readTARDISData(CompoundTag compoundTag, CallbackInfo ci) {
        if(compoundTag.contains("tardis_id")) {
            tardisID = compoundTag.getUUID("tardis_id");
            if (DimensionHandlerIP.tardisToPortalsMap.get(tardisID) == null) {
                kill();
                reloadAndSyncToClient();
            } else {
                for (Portal portal : DimensionHandlerIP.tardisToPortalsMap.get(tardisID)) {
                    if (portal == null) {
                        kill();
                        reloadAndSyncToClient();
                    }
                }
            }
        }
    }

    @Shadow
    protected void defineSynchedData() {

    }

    @Shadow
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Shadow
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Shadow
    public Packet<?> getAddEntityPacket() {
        return null;
    }

    @Override
    public void setTardisID(UUID id) {
        tardisID = id;
    }

    @Shadow
    public void ip_onEntityPositionUpdated() {

    }

    @Shadow
    public void ip_onRemoved(RemovalReason reason) {

    }

    @Shadow
    public BoxPredicate getInnerFrustumCullingFunc(double cameraX, double cameraY, double cameraZ) {
        return null;
    }

    @Shadow
    public boolean isConventionalPortal() {
        return false;
    }

    @Shadow
    public AABB getExactAreaBox() {
        return null;
    }

    @Shadow
    public Vec3 transformPoint(Vec3 pos) {
        return null;
    }

    @Shadow
    public Vec3 transformLocalVec(Vec3 localVec) {
        return null;
    }

    @Shadow
    public Vec3 inverseTransformLocalVec(Vec3 localVec) {
        return null;
    }

    @Shadow
    public Vec3 inverseTransformPoint(Vec3 point) {
        return null;
    }

    @Shadow
    public double getDistanceToNearestPointInPortal(Vec3 point) {
        return 0;
    }

    @Shadow
    public double getDestAreaRadiusEstimation() {
        return 0;
    }

    @Shadow
    public Vec3 getOriginPos() {
        return null;
    }

    @Shadow
    public Vec3 getDestPos() {
        return null;
    }

    @Shadow
    public Level getOriginWorld() {
        return null;
    }

    @Shadow
    public Level getDestWorld() {
        return null;
    }

    @Shadow
    public ResourceKey<Level> getDestDim() {
        return null;
    }

    @Shadow
    public boolean isRoughlyVisibleTo(Vec3 cameraPos) {
        return false;
    }

    @Nullable
    @Shadow
    public Plane getInnerClipping() {
        return null;
    }

    @Nullable
    @Shadow
    public Quaternion getRotation() {
        return null;
    }

    @Shadow
    public double getScale() {
        return 0;
    }

    @Shadow
    public boolean getIsGlobal() {
        return false;
    }

    @Shadow
    public boolean isVisible() {
        return false;
    }

    @Nullable
    @Shadow
    public Vec3[] getOuterFrustumCullingVertices() {
        return new Vec3[0];
    }

    @Shadow
    public void renderViewAreaMesh(Vec3 portalPosRelativeToCamera, Consumer<Vec3> vertexOutput) {

    }

    @Nullable
    @Shadow
    public Matrix4f getAdditionalCameraTransformation() {
        return null;
    }

    @Nullable
    @Shadow
    public UUID getDiscriminator() {
        return null;
    }

    @Shadow
    public boolean cannotRenderInMe(Portal portal) {
        return false;
    }

    @Shadow
    public boolean isFuseView() {
        return false;
    }

    @Shadow
    public boolean getDoRenderPlayer() {
        return false;
    }

    @Shadow
    public boolean getHasCrossPortalCollision() {
        return false;
    }
}
