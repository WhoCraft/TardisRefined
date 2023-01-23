package whocraft.tardis_refined.common.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.control.ship.MonitorControl;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.EntityRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;

public class ControlEntity extends Entity {

    private ControlSpecification controlSpecification;
    private BlockPos consoleBlockPos;

    public ControlEntity(EntityType<?> entityTypeIn, Level level) {
        super(entityTypeIn, level);
    }

    public ControlEntity(Level level) {
        super(EntityRegistry.CONTROL_ENTITY.get(), level);
    }

    @Override
    public Component getName() {
        if(controlSpecification == null){
            return super.getName();
        }
        return Component.translatable(controlSpecification.control().getTranslationKey());
    }

    public ControlSpecification controlSpecification() {
        return controlSpecification;
    }

    public void assignControlData(ControlSpecification consoleControl, BlockPos entityPosition) {
        this.consoleBlockPos = entityPosition;
        this.controlSpecification = consoleControl;
//        this.setBoundingBox(new AABB(new BlockPos(consoleControl.scale())));
        this.refreshDimensions();
        this.setCustomName(Component.translatable(consoleControl.control().getTranslationKey()));

    }

    @Override
    public boolean save(CompoundTag compound) {
        compound.put(NbtConstants.CONSOLE_POS,NbtUtils.writeBlockPos(this.consoleBlockPos));
        return super.save(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        var consolePos = (CompoundTag) compound.get(NbtConstants.CONSOLE_POS);
        if (consolePos != null) {
            this.consoleBlockPos = NbtUtils.readBlockPos(consolePos);
        }

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }


    @Override
    protected void defineSynchedData() {

    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }


    @Override
    public Packet<?> getAddEntityPacket() {
        return MiscHelper.spawnPacket(this);
    }

    @Override
    public boolean mayInteract(Level level, BlockPos blockPos) {
        return true;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getDirectEntity() instanceof Player player) {
            if (getLevel() instanceof ServerLevel serverLevel) {

                if (player.getMainHandItem().getItem() == Items.DEBUG_STICK) {
                    if (player.getOffhandItem().getItem() == Items.DIAMOND) {
                        setPos(position().add( 0, player.isCrouching() ? -0.05 : 0.05, 0));
                    } else {
                        setPos(position().add(player.isCrouching() ? -0.05 : 0.05, 0, 0));
                    }

                    return false;
                }

                TardisLevelOperator.get(serverLevel).ifPresent(cap -> {
                    if (!(this.controlSpecification.control().getControl() instanceof MonitorControl)) {
                        if (cap.getInteriorManager().isWaitingToGenerate()) {
                            serverLevel.playSound(null, this.blockPosition(), SoundEvents.NOTE_BLOCK_BIT, SoundSource.BLOCKS, 100, (float)(0.1 + (serverLevel.getRandom().nextFloat() * 0.5)) );
                            return;
                        }
                    }
                    this.controlSpecification.control().getControl().onLeftClick(cap, this, player);
                });

                return true;
            }
        }
        return super.hurt(damageSource, f);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand interactionHand) {
        if (interactionHand == InteractionHand.MAIN_HAND) {
            if (getLevel() instanceof ServerLevel serverLevel) {

                if (player.getMainHandItem().getItem() == Items.DEBUG_STICK) {
                    if (player.getOffhandItem().getItem() == Items.REDSTONE) {
                        float x = (float) ( this.position().x - 0.5f);
                        float y = (float) ( this.position().y - 97.5f);
                        float z = (float) ( this.position().z - -4.5f);
                        System.out.println("Output: " + x +"f, "+ y +"f, "+ z +"f");
                    } else {
                        setPos(position().add(0, 0, player.isCrouching() ? 0.05 : -0.05));
                    }

                    return InteractionResult.SUCCESS;
                }

                TardisLevelOperator.get(serverLevel).ifPresent(cap -> {

                    if (this.controlSpecification.control().getControl() instanceof MonitorControl) {
                        if (PlayerUtil.isInMainHand(player, ItemRegistry.KEY.get())) {
                            ItemStack itemStack = player.getMainHandItem();
                            if (itemStack.getItem() instanceof KeyItem keyItem){
                                ResourceKey<Level> tardis = serverLevel.dimension();
                                if (controlSpecification.control() != null) {
                                    if (controlSpecification.control() == ConsoleControl.MONITOR && !KeyItem.keychainContains(itemStack, tardis)) {
                                        player.setItemInHand(interactionHand, KeyItem.addTardis(itemStack, tardis));
                                        PlayerUtil.sendMessage(player, Component.translatable(ModMessages.MSG_KEY_BOUND, tardis.location().getPath()), true);
                                        player.playSound(SoundEvents.PLAYER_LEVELUP, 1, 0.5F);
                                    }
                                }
                            }
                        }
                    }
                    else {
                        if (cap.getInteriorManager().isWaitingToGenerate()) {
                            serverLevel.playSound(null, this.blockPosition(), SoundEvents.NOTE_BLOCK_BIT, SoundSource.BLOCKS, 100, (float)(0.1 + (serverLevel.getRandom().nextFloat() * 0.5)) );
                            return;
                        }
                    }

                    this.controlSpecification.control().getControl().onRightClick(cap, this, player);

                });
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }

    @Override
    public void tick() {
        if (level instanceof ServerLevel){
            if (this.controlSpecification == null) {
                if (this.consoleBlockPos != null) {
                    if (level.getBlockEntity(this.consoleBlockPos) instanceof GlobalConsoleBlockEntity globalConsoleBlockEntity) {
                        discard();

                        globalConsoleBlockEntity.markDirty();

                    }
                }
                else {
                    discard();
                }
            }
        }


        super.tick();
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (this.controlSpecification != null) {
            System.out.println(controlSpecification.control().toString() + " " + controlSpecification.scale().toString());
            return controlSpecification.scale();
        }
        return super.getDimensions(pose);
    }
}
