package whocraft.tardis_refined.common.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.protection.ProtectedZone;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.DimensionTypes;

public class MiscHelper {

    @ExpectPlatform
    public static Packet<?> spawnPacket(Entity entity) {
        throw new RuntimeException(TardisRefined.PLATFORM_ERROR);
    }

    public static boolean isBlockPosInBox(BlockPos blockPos, AABB aabb) {
        return aabb.contains(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static ResourceKey<Level> idToKey(ResourceLocation identifier) {
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, identifier);
    }

    public static boolean performTeleport(Entity pEntity, ServerLevel pLevel, double pX, double pY, double pZ, float pYaw, float pPitch) {
        TardisRefined.LOGGER.debug("Teleported {} to {} {} {}", pEntity.getDisplayName().getString(), pX, pY, pZ);
        BlockPos blockpos = new BlockPos(pX, pY, pZ);

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

    public static boolean shouldStopItem(Level level, Player player, BlockPos blockPos, ItemStack itemInHand) {
        if (level.dimensionTypeId() == DimensionTypes.TARDIS && level instanceof ServerLevel serverLevel) {
            TardisLevelOperator data = TardisLevelOperator.get(serverLevel).get();

            // Consoles
            Item consoleItem = BlockRegistry.GLOBAL_CONSOLE_BLOCK.get().asItem();
            Item consoleConfigItem = BlockRegistry.CONSOLE_CONFIGURATION_BLOCK.get().asItem();
            if (data.getInteriorManager().isCave() && (itemInHand.getItem() == consoleConfigItem || itemInHand.getItem() == consoleItem)) {
                return true;
            }

            // Protected Zones
            for (ProtectedZone protectedZone : data.getInteriorManager().unbreakableZones()) {
                boolean shouldCancel = !protectedZone.isAllowPlacement() && isBlockPosInBox(blockPos, protectedZone.getArea());
                if (shouldCancel) return true;
            }
        }
        return false;
    }

    public static boolean shouldCancelBreaking(Level world, Player player, BlockPos pos, BlockState state) {

        if (world.dimensionTypeId() == DimensionTypes.TARDIS && world instanceof ServerLevel serverLevel) {
            TardisLevelOperator data = TardisLevelOperator.get(serverLevel).get();
            for (ProtectedZone protectedZone : data.getInteriorManager().unbreakableZones()) {
                boolean shouldCancel = !protectedZone.isAllowBreaking() && isBlockPosInBox(pos, protectedZone.getArea());
                if(shouldCancel) return true;
            }
        }

        return state.getBlock() instanceof GlobalConsoleBlock || state.getBlock() instanceof ShellBaseBlock;
    }

}
