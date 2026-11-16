package whocraft.tardis_refined.mixin.compat.sable;

import dev.ryanhcode.sable.api.SubLevelAssemblyHelper;
import dev.ryanhcode.sable.companion.math.BoundingBox3ic;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.DimensionUtil;

@Pseudo
@Mixin(SubLevelAssemblyHelper.class)
public class SubLevelAssemblyHelperMixin {

    @Unique
    private static void tardis_refined$updateIfInBounds(ServerLevel level, BoundingBox3ic bounds, TardisNavLocation location) {
        if (location.getLevel() == level) {
            var pos = location.getPositionNoUpdates();
            if (bounds.contains(pos.getX(), pos.getY(), pos.getZ())) {
                location.updateCachedPosition();
            }
        }
    }

    @Inject(method = "moveTrackingPoints", at = @At("HEAD"))
    private static void moveTrackingPoints(ServerLevel level, BoundingBox3ic bounds, ServerSubLevel subLevel, SubLevelAssemblyHelper.AssemblyTransform transform, CallbackInfo ci) {
        if (level == null || level.getServer() == null) return;
        var server = level.getServer();
        DimensionUtil.getTardisLevels(server).forEach(tardis -> {
            var tLevel = server.getLevel(tardis);
            if (tLevel != null) {
                TardisLevelOperator.get(tLevel).ifPresent(op -> {
                    // Tracking points currently do not store the orientation of the sublevel.
                    // This makes sure the visual direction is updated one last time while the ship is being disassembled.

                    var pilot = op.getPilotingManager();

                    tardis_refined$updateIfInBounds(level, bounds, pilot.getFastReturnLocation());
                    tardis_refined$updateIfInBounds(level, bounds, pilot.getCurrentLocation());
                    tardis_refined$updateIfInBounds(level, bounds, pilot.getTargetLocation());

                    op.getTardisWaypointManager().getWaypoints().forEach(
                            waypoint -> tardis_refined$updateIfInBounds(level, bounds, waypoint.getLocation())
                    );
                });
            }
        });
    }

}
