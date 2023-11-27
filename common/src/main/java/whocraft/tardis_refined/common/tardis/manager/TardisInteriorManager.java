package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.block.door.BulkHeadDoorBlock;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.block.door.RootShellDoorBlock;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.blockentity.door.BulkHeadDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.blockentity.door.TardisInternalDoor;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.protection.ProtectedZone;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.BlockRegistry;

import java.util.ArrayList;
import java.util.List;

public class TardisInteriorManager {

    private final TardisLevelOperator operator;
    private boolean isWaitingToGenerate = false;
    private boolean isGeneratingDesktop = false;
    private boolean hasGeneratedCorridors = false;
    private int interiorGenerationCooldown = 0;
    private BlockPos corridorAirlockCenter;
    private DesktopTheme preparedTheme, currentTheme = TardisDesktops.DEFAULT_OVERGROWN_THEME;

    // Airlock systems.
    private boolean processingWarping = false;
    private int airlockCountdownSeconds = 3;
    private int airlockTimerSeconds = 5;

    public static final BlockPos STATIC_CORRIDOR_POSITION = new BlockPos(1000, 100, 0);
    public DesktopTheme preparedTheme() {
        return preparedTheme;
    }

    public TardisInteriorManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public boolean isGeneratingDesktop() {
        return this.isGeneratingDesktop;
    }

    public boolean isWaitingToGenerate() {
        return this.isWaitingToGenerate;
    }

    public int getInteriorGenerationCooldown() {
        return this.interiorGenerationCooldown / 20;
    }

    public ProtectedZone[] unbreakableZones() {

        if (!hasGeneratedCorridors || corridorAirlockCenter == null) return new ProtectedZone[]{};

        ProtectedZone ctrlRoomAirlck = new ProtectedZone(corridorAirlockCenter.below(2).north(2).west(3), corridorAirlockCenter.south(3).east(3).above(6), "control_room_airlock");
        ProtectedZone hubAirlck = new ProtectedZone(STATIC_CORRIDOR_POSITION.below(2).north(2).west(3), STATIC_CORRIDOR_POSITION.south(3).east(3).above(6), "hub_airlock");

        return new ProtectedZone[]{ctrlRoomAirlck, hubAirlck};
    }

    public DesktopTheme currentTheme() {
        return currentTheme;
    }

    public TardisInteriorManager setCurrentTheme(DesktopTheme currentTheme) {
        this.currentTheme = currentTheme;
        return this;
    }

    public boolean isCave() {
        return currentTheme == TardisDesktops.DEFAULT_OVERGROWN_THEME;
    }

    public CompoundTag saveData(CompoundTag tag) {
        tag.putBoolean(NbtConstants.TARDIS_IM_IS_WAITING_TO_GENERATE, this.isWaitingToGenerate);
        tag.putBoolean(NbtConstants.TARDIS_IM_GENERATING_DESKTOP, this.isGeneratingDesktop);
        tag.putInt(NbtConstants.TARDIS_IM_GENERATION_COOLDOWN, this.interiorGenerationCooldown);
        tag.putBoolean(NbtConstants.TARDIS_IM_GENERATED_CORRIDORS, this.hasGeneratedCorridors);

        if (this.corridorAirlockCenter != null) {
            tag.put(NbtConstants.TARDIS_IM_AIRLOCK_CENTER, NbtUtils.writeBlockPos(this.corridorAirlockCenter));
        }


        tag.putString(NbtConstants.TARDIS_IM_PREPARED_THEME, this.preparedTheme != null ? this.preparedTheme.getIdentifier().toString() : "");
        tag.putString(NbtConstants.TARDIS_IM_CURRENT_THEME, this.currentTheme.getIdentifier().toString());
        return tag;
    }

    public void loadData(CompoundTag tag) {
        this.isWaitingToGenerate = tag.getBoolean(NbtConstants.TARDIS_IM_IS_WAITING_TO_GENERATE);
        this.isGeneratingDesktop = tag.getBoolean(NbtConstants.TARDIS_IM_GENERATING_DESKTOP);
        this.interiorGenerationCooldown = tag.getInt(NbtConstants.TARDIS_IM_GENERATION_COOLDOWN);
        this.hasGeneratedCorridors = tag.getBoolean(NbtConstants.TARDIS_IM_GENERATED_CORRIDORS);
        this.preparedTheme = TardisDesktops.getDesktopById(new ResourceLocation(tag.getString(NbtConstants.TARDIS_IM_PREPARED_THEME)));
        this.currentTheme = tag.contains(NbtConstants.TARDIS_IM_CURRENT_THEME) ? TardisDesktops.getDesktopById(new ResourceLocation((NbtConstants.TARDIS_IM_CURRENT_THEME))) : preparedTheme;
        this.corridorAirlockCenter = NbtUtils.readBlockPos(tag.getCompound(NbtConstants.TARDIS_IM_AIRLOCK_CENTER));
    }

    public void tick(ServerLevel level) {

        if (this.isWaitingToGenerate) {
            if (level.random.nextInt(30) == 0) {
                level.playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 5.0F + level.random.nextFloat(), level.random.nextFloat() * 0.7F + 0.3F);
            }

            if (level.random.nextInt(100) == 0) {
                level.playSound(null, TardisArchitectureHandler.DESKTOP_CENTER_POS, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 15.0F + level.random.nextFloat(), 0.1f);
            }

            if (level.players().size() == 0) {

                this.operator.getExteriorManager().triggerShellRegenState();
                operator.setDoorClosed(true);
                generateDesktop(this.preparedTheme);

                this.isWaitingToGenerate = false;
                this.isGeneratingDesktop = true;
            }
        }

        if (this.isGeneratingDesktop) {

            if (!level.isClientSide()) {
                interiorGenerationCooldown--;
            }

            if (interiorGenerationCooldown == 0) {
                this.operator.setShellTheme((this.operator.getExteriorManager().getCurrentTheme() != null) ? operator.getExteriorManager().getCurrentTheme() : ShellTheme.FACTORY);
                this.isGeneratingDesktop = false;
            }

            if (level.getGameTime() % 60 == 0) {
                operator.getExteriorManager().playSoundAtShell(SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 1.0F + operator.getExteriorManager().getLastKnownLocation().getLevel().getRandom().nextFloat(), 0.1f);
            }
        }


        /// Airlock Logic

        // Check if a player is in the radius of either airlock points
        if (!processingWarping) {
            if (level.getGameTime() % 20 == 0) {
                // Dynamic desktop position.
                List<LivingEntity> desktopEntities = getAirlockEntities(level);
                List<LivingEntity> corridorEntities = getCorridorEntities(level);

                if (desktopEntities.size() > 0 || corridorEntities.size() > 0) {
                    airlockCountdownSeconds--;
                    if (airlockCountdownSeconds <= 0) {

                        this.processingWarping = true;
                        airlockCountdownSeconds = 10;
                        this.airlockTimerSeconds = 0;

                        // Lock the doors.
                        BlockPos desktopDoorPos = corridorAirlockCenter.north(2);
                        if (level.getBlockEntity(desktopDoorPos) instanceof BulkHeadDoorBlockEntity bulkHeadDoorBlockEntity) {
                            bulkHeadDoorBlockEntity.closeDoor(level, desktopDoorPos, level.getBlockState(desktopDoorPos));
                            level.setBlock(desktopDoorPos, level.getBlockState(desktopDoorPos).setValue(BulkHeadDoorBlock.LOCKED, true), 2);
                        }

                        BlockPos corridorDoorBlockPos = new BlockPos(1000, 100, 2);
                        if (level.getBlockEntity(corridorDoorBlockPos) instanceof BulkHeadDoorBlockEntity bulkHeadDoorBlockEntity) {
                            bulkHeadDoorBlockEntity.closeDoor(level, corridorDoorBlockPos, level.getBlockState(corridorDoorBlockPos));
                            level.setBlock(corridorDoorBlockPos, level.getBlockState(corridorDoorBlockPos).setValue(BulkHeadDoorBlock.LOCKED, true), 2);
                        }
                    }
                } else {
                    this.processingWarping = false;
                    this.airlockCountdownSeconds = 6;
                    this.airlockTimerSeconds = 0;
                }

            }
        }

        if (processingWarping) {


            if (level.getGameTime() % 20 == 0) {

                RandomSource rand = level.getRandom();
                for (ProtectedZone protectedZone : unbreakableZones()) {
                    if(!protectedZone.getName().contains("_airlock")) continue;
                    BlockPos.betweenClosedStream(protectedZone.getArea()).forEach(position -> {
                        double velocityX = (rand.nextDouble() - 0.5) * 0.02;
                        double velocityY = (rand.nextDouble() - 0.5) * 0.02;
                        double velocityZ = (rand.nextDouble() - 0.5) * 0.02;

                        level.sendParticles(ParticleTypes.CLOUD, position.getX(), position.getY(), position.getZ(), 2, velocityX, velocityY, velocityZ, velocityZ);
                    });
                }


                if (airlockTimerSeconds == 1) {
                    level.playSound(null, corridorAirlockCenter, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 5, 0.25f);
                    level.playSound(null, STATIC_CORRIDOR_POSITION, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 5, 0.25f);
                }

                if (airlockTimerSeconds == 5) {
                    List<LivingEntity> desktopEntities = getAirlockEntities(level);
                    List<LivingEntity> corridorEntities = getCorridorEntities(level);

                    desktopEntities.forEach(x -> {
                        Vec3 offsetPos = x.position().subtract(Vec3.atCenterOf(corridorAirlockCenter.north(2)));
                        MiscHelper.performTeleport(x, level, 1000.5f + offsetPos.x(), 100.5f + offsetPos.y(), -1.5f + offsetPos.z(), x.getYRot(), x.getXRot());
                    });

                    corridorEntities.forEach(x -> {
                        Vec3 offsetPos = x.position().subtract(Vec3.atCenterOf(new BlockPos(1000, 100, -2)));
                        MiscHelper.performTeleport(x, level, corridorAirlockCenter.north(2).getX() + offsetPos.x() + 0.5f, corridorAirlockCenter.north(2).getY() + offsetPos.y() + 0.5f, corridorAirlockCenter.north(2).getZ() + offsetPos.z() + 0.5f, x.getYRot(), x.getXRot());
                    });
                }

                if (airlockTimerSeconds == 7) {
                    airlockTimerSeconds = 0;
                    this.processingWarping = false;
                    this.airlockTimerSeconds = 20;
                    BlockPos desktopDoorPos = corridorAirlockCenter.north(2);
                    if (level.getBlockEntity(desktopDoorPos) instanceof BulkHeadDoorBlockEntity bulkHeadDoorBlockEntity) {
                        bulkHeadDoorBlockEntity.openDoor(level, desktopDoorPos, level.getBlockState(desktopDoorPos));
                        level.setBlock(desktopDoorPos, level.getBlockState(desktopDoorPos).setValue(BulkHeadDoorBlock.LOCKED, false), 2);
                    }

                    BlockPos corridorDoorBlockPos = new BlockPos(1000, 100, 2);
                    if (level.getBlockEntity(corridorDoorBlockPos) instanceof BulkHeadDoorBlockEntity bulkHeadDoorBlockEntity) {
                        bulkHeadDoorBlockEntity.openDoor(level, corridorDoorBlockPos, level.getBlockState(corridorDoorBlockPos));
                        level.setBlock(corridorDoorBlockPos, level.getBlockState(corridorDoorBlockPos).setValue(BulkHeadDoorBlock.LOCKED, false), 2);
                    }
                }


                airlockTimerSeconds++;
            }
        }
    }

    public List<LivingEntity> getCorridorEntities(Level level) {
        return level.getEntitiesOfClass(LivingEntity.class, new AABB(STATIC_CORRIDOR_POSITION.north(2).west(2), STATIC_CORRIDOR_POSITION.south(2).east(2).above(4)));
    }

    private List<LivingEntity> getAirlockEntities(Level level) {

        if (corridorAirlockCenter == null) {
            return new ArrayList<>();
        }

        return level.getEntitiesOfClass(LivingEntity.class, new AABB(corridorAirlockCenter.north(2).west(2), corridorAirlockCenter.south(2).east(2).above(4)));
    }

    public boolean isInAirlock(LivingEntity livingEntity) {

        if (!hasGeneratedCorridors) return false;

        List<LivingEntity> airlock = getAirlockEntities(livingEntity.level);
        List<LivingEntity> corridor = getCorridorEntities(livingEntity.level);

        return airlock.contains(livingEntity) || corridor.contains(livingEntity);
    }

    public void generateDesktop(DesktopTheme theme) {
        setCurrentTheme(theme);

        if (operator.getLevel() instanceof ServerLevel serverLevel) {

            // Remove Tardis Interior DOor
            TardisInternalDoor tardisInternalDoor = this.operator.getInternalDoor();
            if (tardisInternalDoor != null) {
                serverLevel.removeBlock(tardisInternalDoor.getDoorPosition(), false);
            }

            // Generate Corridors
            if (!this.hasGeneratedCorridors) {
                TardisArchitectureHandler.generateEssentialCorridors(serverLevel);
                this.hasGeneratedCorridors = true;
            }

            // Generate Desktop Interior
            TardisArchitectureHandler.generateDesktop(serverLevel, theme);

        }
    }

    public void setCorridorAirlockCenter(BlockPos center) {
        this.corridorAirlockCenter = center;
    }

    public BlockPos getCorridorAirlockCenter() {
        return this.corridorAirlockCenter;
    }

    public void prepareDesktop(DesktopTheme theme) {
        this.preparedTheme = theme;
        this.isWaitingToGenerate = true;
        this.interiorGenerationCooldown = 1200; // Make this more independent.
    }

    public void cancelDesktopChange() {
        this.preparedTheme = null;
        this.isWaitingToGenerate = false;
    }

    public void setShellTheme(ShellTheme theme) {
        if (operator.getInternalDoor() != null){
            BlockState state = operator.getLevel().getBlockState(operator.getInternalDoor().getDoorPosition());
            // Check if its our default global shell.

            if (state.getBlock() instanceof GlobalDoorBlock) {
                operator.getLevel().setBlock(operator.getInternalDoor().getDoorPosition(),
                        state.setValue(GlobalDoorBlock.SHELL, theme), 2);
            } else {
                if (state.getBlock() instanceof RootShellDoorBlock) {
                    operator.getLevel().setBlock(operator.getInternalDoor().getDoorPosition(),
                            BlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState().setValue(GlobalShellBlock.OPEN, state.getValue(RootedShellBlock.OPEN))
                                    .setValue(GlobalShellBlock.FACING, state.getValue(RootedShellBlock.FACING)).setValue(GlobalShellBlock.SHELL, theme), 2);

                    var shellBlockEntity = operator.getLevel().getBlockEntity(operator.getInternalDoor().getDoorPosition());
                    if (shellBlockEntity instanceof GlobalDoorBlockEntity entity) {
                        operator.setInternalDoor(entity);
                    }
                }
            }
        }
    }
}
