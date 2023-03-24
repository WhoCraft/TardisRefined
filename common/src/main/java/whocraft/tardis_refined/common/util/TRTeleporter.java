package whocraft.tardis_refined.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class TRTeleporter {
    private static boolean canTeleportTo(BlockPos pPos, Level level, Entity entity) {
        BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(level, pPos.mutable());
        if (blockpathtypes != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            BlockPos blockpos = pPos.subtract(entity.blockPosition());
            return level.noCollision(entity, entity.getBoundingBox().move(blockpos));
        }
    }
    public static boolean performTeleport(Entity pEntity, ServerLevel pLevel, double pX, double pY, double pZ, float pYaw, float pPitch) {
        BlockPos blockpos = new BlockPos(pX, pY, pZ);

        if (!canTeleportTo(blockpos, pLevel, pEntity)) {
            return false;
        }

        if (!Level.isInSpawnableBounds(blockpos)) {
            return false;
        } else {
            float f = Mth.wrapDegrees(pYaw);
            float f1 = Mth.wrapDegrees(pPitch);
            if (pEntity instanceof ServerPlayer serverPlayer) {
                ChunkPos chunkpos = new ChunkPos(new BlockPos(pX, pY, pZ));
                pLevel.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkpos, 1, pEntity.getId());
                pEntity.stopRiding();
                if (serverPlayer.isSleeping()) {
                    serverPlayer.stopSleepInBed(true, true);
                }

                if (pLevel == pEntity.level) {
                    serverPlayer.connection.teleport(pX, pY, pZ, f, f1);
                } else {
                    serverPlayer.teleportTo(pLevel, pX, pY, pZ, f, f1);
                }
                pEntity.setYHeadRot(f);
            } else {
                float f2 = Mth.clamp(f1, -90.0F, 90.0F);
                if (pLevel == pEntity.level) {
                    pEntity.moveTo(pX, pY, pZ, f, f2);
                    pEntity.setYHeadRot(f);
                } else {
                    pEntity.unRide();
                    Entity entity = pEntity;
                    pEntity = pEntity.getType().create(pLevel);
                    if (pEntity == null) {
                        return false;
                    }

                    pEntity.restoreFrom(entity);
                    pEntity.moveTo(pX, pY, pZ, f, f2);
                    pEntity.setYHeadRot(f);
                    entity.setRemoved(Entity.RemovalReason.CHANGED_DIMENSION);
                    pLevel.addDuringTeleport(pEntity);
                }
            }

            if (!(pEntity instanceof LivingEntity) || !((LivingEntity) pEntity).isFallFlying()) {
                pEntity.setDeltaMovement(pEntity.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
                pEntity.setOnGround(true);
            }

            if (pEntity instanceof PathfinderMob) {
                ((PathfinderMob) pEntity).getNavigation().stop();
            }

            return true;
        }
    }

}