package whocraft.tardis_refined.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.api.event.TardisEvents;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.ShellBaseBlock;
import whocraft.tardis_refined.common.blockentity.shell.GlobalShellBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.common.dimension.TardisTeleportData;
import whocraft.tardis_refined.common.mixin.EndDragonFightAccessor;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisExteriorManager;
import whocraft.tardis_refined.common.tardis.manager.TardisInteriorManager;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.registry.BlockRegistry;

import java.util.concurrent.atomic.AtomicBoolean;

import static whocraft.tardis_refined.common.block.shell.ShellBaseBlock.LOCKED;
import static whocraft.tardis_refined.constants.TardisDimensionConstants.ARS_TREE_CORNER_A;
import static whocraft.tardis_refined.constants.TardisDimensionConstants.ARS_TREE_CORNER_B;

public class TardisHelper {

    public static void playCloisterBell(TardisLevelOperator tardisLevelOperator) {
        for (int i = 0; i < 3; i++) {
            tardisLevelOperator.getLevel().playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 1000f, 0.1f);
        }
    }

    public static boolean isInArsArea(BlockPos blockPos) {

        BlockPos corner1 = ARS_TREE_CORNER_A;
        BlockPos corner2 = ARS_TREE_CORNER_B;

        int minX = Math.min(corner1.getX(), corner2.getX());
        int maxX = Math.max(corner1.getX(), corner2.getX());
        int minY = Math.min(corner1.getY(), corner2.getY());
        int maxY = Math.max(corner1.getY(), corner2.getY());
        int minZ = Math.min(corner1.getZ(), corner2.getZ());
        int maxZ = Math.max(corner1.getZ(), corner2.getZ());

        return blockPos.getX() >= minX && blockPos.getX() <= maxX && blockPos.getY() >= minY && blockPos.getY() <= maxY && blockPos.getZ() >= minZ && blockPos.getZ() <= maxZ;
    }

    public static boolean createTardis(BlockPos blockPos, ServerLevel serverLevel, ResourceKey<Level> generatedLevelKey, ResourceLocation shellTheme, DesktopTheme desktopTheme) {

        AtomicBoolean generated = new AtomicBoolean(false);

        //Set global shell block
        BlockState targetBlockState = BlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState().setValue(GlobalShellBlock.FACING, Direction.NORTH).setValue(GlobalShellBlock.REGEN, false).setValue(LOCKED, false).setValue(GlobalShellBlock.WATERLOGGED, serverLevel.getBlockState(blockPos).getFluidState().getType() == Fluids.WATER);

        serverLevel.setBlock(blockPos, targetBlockState, Block.UPDATE_ALL);

        if (serverLevel.getBlockEntity(blockPos) instanceof GlobalShellBlockEntity shellBaseBlockEntity) {
            if (shellBaseBlockEntity.shouldSetup()) {

                //Set the shell with this level
                shellBaseBlockEntity.setTardisId(generatedLevelKey);
                shellBaseBlockEntity.setShellTheme(shellTheme);
                //Create the Level on demand which will create our capability
                ServerLevel interior = DimensionHandler.getOrCreateInterior(serverLevel, shellBaseBlockEntity.getTardisId().location());

                TardisLevelOperator.get(interior).ifPresent(tardisLevelOperator -> {
                    TardisInteriorManager intManager = tardisLevelOperator.getInteriorManager();
                    TardisExteriorManager extManager = tardisLevelOperator.getExteriorManager();
                    TardisPilotingManager pilotManager = tardisLevelOperator.getPilotingManager();
                    if (!tardisLevelOperator.hasInitiallyGenerated()) {
                        intManager.generateDesktop(desktopTheme);
                        Direction direction = targetBlockState.getValue(ShellBaseBlock.FACING).getOpposite();
                        TardisNavLocation navLocation = new TardisNavLocation(blockPos, direction, serverLevel);
                        extManager.setLastKnownLocation(navLocation);
                        pilotManager.setTargetLocation(navLocation);
                        tardisLevelOperator.setInitiallyGenerated(true);
                        serverLevel.setBlock(blockPos, targetBlockState.setValue(ShellBaseBlock.OPEN, true), Block.UPDATE_ALL);
                        generated.set(true);
                    }
                });

                return generated.get();
            }
        }
        return false;

    }

    public static MutableComponent createTardisIdComponent(ResourceLocation levelId) {
        String id = levelId.toString();
        String displayId = levelId.getPath().substring(0, 5);
        MutableComponent tardisId = CommandHelper.createComponentWithTooltip(displayId, id);
        return tardisId;
    }

    public static boolean teleportEntityTardis(TardisLevelOperator cap, Entity entity, TardisNavLocation sourceLocation, TardisNavLocation destinationLocation, boolean enterTardis) {

        if (entity.level() instanceof ServerLevel teleportingEntityLevel) {

            BlockPos destinationPos = destinationLocation.getPosition();
            ServerLevel destinationLevel = destinationLocation.getLevel();
            Direction destinationDirection = destinationLocation.getDirection().getOpposite(); //Use the opposite facing of the destination so that when teleported, the entity faces away from the doorway

            Direction sourceDirection = sourceLocation.getDirection();


            BlockPos targetTeleportPos = destinationPos;

            //Calculate entity motion and rotation, taking into account for the internal door's direction and rotation
            float entityYRot = entity.getYRot();
            float destinationRotationYaw = destinationDirection.toYRot();
            float sourceRotationYaw = sourceDirection.toYRot();
            //Calculate the difference between the entity's rotation and the source direction's rotation. Get the difference and find the final rotation that preserves the entities' rotation but facing the direction at the destination
            float diff = LevelHelper.getAdjustedRotation(entityYRot) - LevelHelper.getAdjustedRotation(sourceRotationYaw);

            float adjustedRotationYaw = destinationRotationYaw + diff;

            if (entity.getType().getDimensions().width > 1F) {
                targetTeleportPos = destinationPos.offset(destinationDirection.getNormal());
            }

            BlockPos finalTeleportPos = targetTeleportPos;

            Vec3 centredTarget = LevelHelper.centerPos(finalTeleportPos, false);

            TardisTeleportData.getOrCreate(teleportingEntityLevel).scheduleEntityTeleport(entity, destinationLevel.dimension(), centredTarget.x(), centredTarget.y(), centredTarget.z(), adjustedRotationYaw, entity.getXRot());

            //Fire exit or enter events
            if (entity instanceof LivingEntity livingEntity) {
                if (enterTardis) {
                    TardisEvents.TARDIS_ENTRY_EVENT.invoker().onEnterTardis(cap, livingEntity, sourceLocation, destinationLocation);
                } else {
                    TardisEvents.TARDIS_EXIT_EVENT.invoker().onExitTardis(cap, livingEntity, sourceLocation, destinationLocation);
                }
            }

            cap.tardisClientData().sync();

            return true;
        }
        return false;

    }

    public static boolean hasTheEndBeenCompleted(ServerLevel serverLevel) {

        if (serverLevel.dimension() == Level.END) {
            if (serverLevel.dragonFight != null) {
               return ((EndDragonFightAccessor) serverLevel.dragonFight).isDragonKilled();
            }

            return false; // Better safe than sorry.
        }

        return false;
    }

}
