package whocraft.tardis_refined.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.registry.TRTagKeys;

import java.util.*;

public class TRTeleporter {
    public static boolean canTeleportTo(BlockPos pPos, Level level, Entity entity) {
        BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(level, pPos.mutable());
        if (blockpathtypes != BlockPathTypes.WALKABLE) {
            return false;
        } else {
            BlockPos blockpos = pPos.subtract(entity.blockPosition());
            return level.noCollision(entity, entity.getBoundingBox().move(blockpos));
        }
    }

    public static boolean performTeleport(Entity pEntity, ServerLevel pLevel, double pX, double pY, double pZ, float pYaw, float pPitch) {
        return performTeleport(pEntity, null, pLevel, pX, pY, pZ, pYaw, pPitch, false, new HashSet<>());
    }

    public static boolean performTeleport(Entity pEntity, ServerLevel pLevel, double pX, double pY, double pZ, float pYaw, float pPitch, Set<Entity> teleportedEntities) {
        Entity rootVehicle = pEntity.getRootVehicle();
        Entity controller = pEntity.getControllingPassenger();
        if (pEntity.isVehicle() && controller != null){
            // If there is a valid controlling passenger, this entity must be a vehicle, so we will teleport the vehicle
            // The subsequent teleport logic will eject all passengers who will be placed at the vehicle's position before teleport occurs
            // Since we assume the vehicle is inside a block or area where teleport is being requested, the dismounted passengers should be teleported too
            return performTeleport(rootVehicle, null, pLevel, pX, pY, pZ, pYaw, pPitch, false, teleportedEntities);
        }
        return performTeleport(pEntity, rootVehicle, pLevel, pX, pY, pZ, pYaw, pPitch, false, teleportedEntities);
    }

    public static boolean performTeleport(Entity pEntity, Entity vehicle, ServerLevel destination, double pX, double pY, double pZ, float pYaw, float pPitch, boolean safeBlockCheck, Set<Entity> teleportedEntities) {
        if (pEntity.level() instanceof ServerLevel) {
            if (safetyCheck(pEntity, destination, pX, pY, pZ, pYaw, pPitch, safeBlockCheck, teleportedEntities)) {
                pEntity.unRide();
                Entity teleportedEntity;
                List<Entity> passengers = pEntity.getPassengers();

                float updatedYRot = Mth.wrapDegrees(pYaw);
                float updatedXRot = Mth.wrapDegrees(pPitch);

                if (pEntity instanceof ServerPlayer serverPlayer) {
                    //Always update player data, do not create new instances of the player
                    
                    teleportedEntity = teleportPlayer(serverPlayer, destination, pX, pY, pZ, updatedYRot, updatedXRot);

                } else { //If not a player
                    teleportedEntity = teleportEntity(pEntity, destination, pX, pY, pZ, updatedYRot, updatedXRot);
                }

                if (teleportedEntity != null){

                    // update teleported entities set to keep track of entities teleported. Check if the list already contains our entity because this will be modified by the TardisTeleportData too
                    if(!teleportedEntities.contains(pEntity))
                       teleportedEntities.add(pEntity);

                    ChunkPos chunkpos = new ChunkPos(BlockPos.containing(pX, pY, pZ));
                    destination.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkpos, 1, teleportedEntity.getId());

                    //Handle Elytra flying - Logic matches vanilla TeleportCommand
                    if (!(teleportedEntity instanceof LivingEntity) || !((LivingEntity) teleportedEntity).isFallFlying()) {
                        teleportedEntity.setDeltaMovement(teleportedEntity.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D));
                        teleportedEntity.setOnGround(true);
                    }

                    if (teleportedEntity instanceof PathfinderMob) {
                        ((PathfinderMob) teleportedEntity).getNavigation().stop();
                    }

                    if (vehicle != null){
                        if (!vehicle.equals(teleportedEntity)) { // DO NOT REMOVE THIS CHECK - used to prevent the entity riding itself because when we teleport a player it will have a root vehicle which is itself
                            teleportedEntity.startRiding(vehicle);
                        }
                    }

                    //Handle active potion effects
                    if(teleportedEntity instanceof LivingEntity livingEntity) {
                        if (livingEntity instanceof ServerPlayer serverPlayer) {
                            for(MobEffectInstance effectInstance : new ArrayList<>(livingEntity.getActiveEffects())) {
                                serverPlayer.connection.send(new ClientboundUpdateMobEffectPacket(serverPlayer.getId(), effectInstance));
                            }
                        }
                        else {
                            reAddStatusEffectTempFix(livingEntity);
                        }
                    }

                    //Handle teleporting any entities that are riding this entity by teleporting them too - equivalent to Entity#teleportPassengers() but works for both same dimension and different dimension since we account for destination dimension
                    if (passengers.size() != 0) {
                        passengers.forEach(passenger -> performTeleport(passenger, teleportedEntity, destination, pX, pY, pZ, pYaw, pPitch, safeBlockCheck, teleportedEntities));
                    }
                    else { //If all passengers are teleported (empty list), reset the time that the dimension had no players or forced chunks in it
                        ((ServerLevel)teleportedEntity.level()).resetEmptyTime();
                        destination.resetEmptyTime();
                    }

                    return true;
                }
            }
        }
        return false;
    }

    public static boolean safetyCheck(Entity pEntity, ServerLevel destination, double pX, double pY, double pZ, float pYaw, float pPitch, boolean safeBlockCheck, Set<Entity> teleportedEntities){
        int xRound = (int) pX;
        int yRound = (int) pY;
        int zRound = (int) pZ;
        BlockPos blockpos = new BlockPos(xRound, yRound, zRound);

        if (!teleportedEntities.isEmpty()){
            if (teleportedEntities.contains(pEntity)){ //If we are calling this method by itself such as teleporting passengers, check if we have already teleported the entity
                return false;
            }
        }

        if (pEntity.getType().is(TRTagKeys.TARDIS_TELEPORT_BLACKLIST)) //Stop teleporting if the entity being teleported is blacklisted
            return false;

        if (safeBlockCheck) {
            if (!canTeleportTo(blockpos, destination, pEntity)) {
                return false;
            }
        }

        if (!Level.isInSpawnableBounds(blockpos)) {
            return false;
        }

        return true;
    }

    private static ServerPlayer teleportPlayer(ServerPlayer serverPlayer, ServerLevel destination, double pX, double pY, double pZ, float updatedYRot, float updatedXRot){
        if (serverPlayer.isSleeping()) {
            serverPlayer.stopSleepInBed(true, true);
        }

        serverPlayer.setDeltaMovement(Vec3.ZERO); //set velocity to 0 because otherwise we will trigger the "player moved wrongly" hardcoded vanilla check which will result in the player not changing coordinates in the new dimension

        serverPlayer.connection.send(new ClientboundChangeDifficultyPacket(destination.getLevelData().getDifficulty(), destination.getLevelData().isDifficultyLocked()));
        serverPlayer.teleportTo(destination, pX, pY, pZ, updatedYRot, updatedXRot);

        serverPlayer.setPortalCooldown(); //Prevent player from being teleport by nether portal in the destination dimension
        serverPlayer.setYHeadRot(updatedYRot);
        serverPlayer.setYBodyRot(updatedYRot);

        serverPlayer.onUpdateAbilities();


        //Handle player experience not getting received on client after interdimensional teleport
        serverPlayer.connection.send(new ClientboundSetExperiencePacket(serverPlayer.experienceProgress, serverPlayer.totalExperience, serverPlayer.experienceLevel));
        serverPlayer.connection.send(new ClientboundPlayerAbilitiesPacket(serverPlayer.getAbilities()));//Keep abilities like creative mode flight height etc.
        serverPlayer.connection.send(new ClientboundChangeDifficultyPacket(serverPlayer.level().getDifficulty(), serverPlayer.level().getLevelData().isDifficultyLocked()));

        return (ServerPlayer) destination.getPlayerByUUID(serverPlayer.getUUID());
    }

    private static Entity teleportEntity(Entity pEntity, ServerLevel destination, double pX, double pY, double pZ, float yRot, float xRot){
        //For non player entities, always create new instances if inter dimensional teleporting
        float adjustedXRot = Mth.clamp(xRot, -90.0F, 90.0F);
        Entity teleportedEntity;

        if (destination == pEntity.level()) {
            teleportedEntity = teleportEntitySameDimension(pEntity, pX, pY, pZ, yRot, adjustedXRot);
        }
        else {
            teleportedEntity = teleportEntityOtherDimension(pEntity, destination, pX, pY, pZ, yRot, adjustedXRot);
        }
        return teleportedEntity;
    }

    private static Entity teleportEntitySameDimension(Entity pEntity, double pX, double pY, double pZ, float yRot, float xRot) {
        pEntity.moveTo(pX, pY, pZ, yRot, xRot);
        pEntity.setYHeadRot(yRot);
        pEntity.setYBodyRot(yRot);
        pEntity.setPortalCooldown();
        return pEntity;
    }

    private static Entity teleportEntityOtherDimension(Entity pEntity, ServerLevel destination, double pX, double pY, double pZ, float yRot, float xRot) {
        //Get most of this logic from Entity#changeDimension which is most suitable as it has most syncing logic. However, we want to exclude the use of PortalInfo as it plays the vanilla portal sounds
        Entity newEntity = pEntity;

        newEntity = newEntity.getType().create(destination);
        if (newEntity == null) {
            return null;
        }

        newEntity.restoreFrom(pEntity);
        newEntity.moveTo(pX, pY, pZ, yRot, xRot);
        newEntity.setYHeadRot(yRot);
        newEntity.setPortalCooldown();
        newEntity.setDeltaMovement(Vec3.ZERO);

        pEntity.remove(Entity.RemovalReason.CHANGED_DIMENSION);
        destination.addDuringTeleport(newEntity); //DO NOT add the entity to the destination before removing them, else we will teleport them to the coordinates from the source world
        return newEntity;
    }

    public static boolean teleportIfCollided(ServerLevel serverLevel, BlockPos blockPos, Entity entity, AABB teleportAABB){
        AABB entityBoundingBox = TRTeleporter.getBoundingBoxWithMovement(entity);
        double insideBlockExpansion = 1.0E-7D; //Hardcoded value replicates logic from Entity#checkInsideBlocks
        AABB inflatedEntityBoundingBox = entityBoundingBox.inflate(insideBlockExpansion);
        AABB inflatedTeleportBoundingBox = teleportAABB.inflate(insideBlockExpansion);
        if (inflatedTeleportBoundingBox.intersects(inflatedEntityBoundingBox)){
            return true;
        }
        return false;
    }

    /**
     * Temporary fix until Mojang patches the bug that makes potion effect icons disappear when changing dimension.
     * To fix it ourselves, we remove the effect and re-add it to the entity.
     */
    private static void reAddStatusEffectTempFix(LivingEntity livingEntity) {
        //re-adds potion effects so the icon remains instead of disappearing when changing dimensions due to a bug
        ArrayList<MobEffectInstance> effectInstanceList = new ArrayList<>(livingEntity.getActiveEffects());
        for (int i = effectInstanceList.size() - 1; i >= 0; i--) {
            MobEffectInstance effectInstance = effectInstanceList.get(i);
            if (effectInstance != null) {
                livingEntity.removeEffect(effectInstance.getEffect());
                livingEntity.addEffect(
                        new MobEffectInstance(
                                effectInstance.getEffect(),
                                effectInstance.getDuration(),
                                effectInstance.getAmplifier(),
                                effectInstance.isAmbient(),
                                effectInstance.isVisible(),
                                effectInstance.showIcon()));
            }
        }
    }

    public static AABB getBoundingBoxWithMovement(Entity entity) {
        Vec3 backwardExpand = lastTickPosOf(entity).subtract(entity.position());
        Vec3 forwardExpand = entity.getDeltaMovement();
        AABB box = entity.getBoundingBox()
                .expandTowards(forwardExpand.scale(1.2))
                .expandTowards(backwardExpand);

        return box;
    }

    public static Vec3 lastTickPosOf(Entity entity) {
        return new Vec3(entity.xo, entity.yo, entity.zo);
    }

}