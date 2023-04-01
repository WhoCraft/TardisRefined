package whocraft.tardis_refined.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TRParticles;
import whocraft.tardis_refined.common.blockentity.console.GlobalConsoleBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.items.KeyItem;
import whocraft.tardis_refined.common.network.messages.ControlSizeSyncMessage;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.control.ship.MonitorControl;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.LevelHelper;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.EntityRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;

public class ControlEntity extends Entity {

    private ControlSpecification controlSpecification;
    private ConsoleTheme consoleTheme;
    private BlockPos consoleBlockPos;

    public ControlEntity(EntityType<?> entityTypeIn, Level level) {
        super(entityTypeIn, level);
    }

    private static final EntityDataAccessor<Boolean> SHOW_PARTICLE = SynchedEntityData.defineId(ControlEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> SCALE_WIDTH = SynchedEntityData.defineId(ControlEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SCALE_HEIGHT = SynchedEntityData.defineId(ControlEntity.class, EntityDataSerializers.FLOAT);

    public ControlEntity(Level level) {
        super(EntityRegistry.CONTROL_ENTITY.get(), level);
    }

    public void assignControlData(ConsoleTheme theme, ControlSpecification specification, BlockPos consoleBlockPos){
        this.consoleBlockPos = consoleBlockPos;
        this.controlSpecification = specification;
        this.consoleTheme = theme;
        if(this.controlSpecification != null) {
            float width = controlSpecification.scale().width;
            float height = controlSpecification.scale().height;
            this.setSizeData(width, height);
            this.setCustomName(Component.translatable(controlSpecification.control().getTranslationKey()));
            if (!this.level.isClientSide)
                new ControlSizeSyncMessage(this.getId(), width, height).sendToTracking(this);
        }
    }

    protected void setSizeData(float width, float height){
        this.getEntityData().set(SCALE_WIDTH, width);
        this.getEntityData().set(SCALE_HEIGHT, height);
    }

    public void setSize(float width, float height){
        this.setSizeData(width, height);
        this.refreshDimensions();
        this.reapplyPosition(); //DO NOT delete, or else the entity's hitbox becomes stuck at 0,0
    }

    public ControlSpecification controlSpecification() {
        return this.controlSpecification;
    }

    public ConsoleTheme consoleTheme() {return this.consoleTheme;}

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (this.getEntityData().get(SCALE_WIDTH) != null && this.getEntityData().get(SCALE_HEIGHT) != null){
            return EntityDimensions.scalable(this.getEntityData().get(SCALE_WIDTH), this.getEntityData().get(SCALE_HEIGHT));
        }
        return super.getDimensions(pose);
    }

    @Override
    public Component getName() {
        if (this.controlSpecification == null) {
            return super.getName();
        }
        return Component.translatable(this.controlSpecification.control().getTranslationKey());
    }

    @Override
    protected void defineSynchedData() {
        getEntityData().define(SHOW_PARTICLE, false);
        getEntityData().define(SCALE_WIDTH, 1F);
        getEntityData().define(SCALE_HEIGHT, 1F);

    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        this.setSize(this.getEntityData().get(SCALE_WIDTH), this.getEntityData().get(SCALE_HEIGHT));
    }


    @Override
    public boolean save(CompoundTag compound) {
        if(consoleBlockPos != null){
            compound.put(NbtConstants.CONSOLE_POS, NbtUtils.writeBlockPos(this.consoleBlockPos));
        }
        compound.putFloat(NbtConstants.CONTROL_SIZE_WIDTH, this.getEntityData().get(SCALE_WIDTH));
        compound.putFloat(NbtConstants.CONTROL_SIZE_HEIGHT, this.getEntityData().get(SCALE_HEIGHT));
        return super.save(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        var consolePos = (CompoundTag) compound.get(NbtConstants.CONSOLE_POS);
        if (consolePos != null) {
            this.consoleBlockPos = NbtUtils.readBlockPos(consolePos);
        }

        float width = compound.getFloat(NbtConstants.CONTROL_SIZE_WIDTH);
        float height = compound.getFloat(NbtConstants.CONTROL_SIZE_HEIGHT);

        this.setSizeData(width, height);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        if(consoleBlockPos != null){
            compound.put(NbtConstants.CONSOLE_POS, NbtUtils.writeBlockPos(this.consoleBlockPos));
        }

        compound.putFloat(NbtConstants.CONTROL_SIZE_WIDTH, this.getEntityData().get(SCALE_WIDTH));
        compound.putFloat(NbtConstants.CONTROL_SIZE_HEIGHT, this.getEntityData().get(SCALE_HEIGHT));

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return MiscHelper.spawnPacket(this);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getDirectEntity() instanceof Player player) {
            if (this.level instanceof ServerLevel serverLevel) {
                handleLeftClick(player, serverLevel);
                return true;
            }
        }
        return super.hurt(damageSource, f);
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 hitPos, InteractionHand interactionHand) {
        if (interactionHand == InteractionHand.MAIN_HAND) {
            if (getLevel() instanceof ServerLevel serverLevel) {

                if (player.getMainHandItem().getItem() == Items.ENCHANTED_BOOK) {
                    this.handleControlSizeAndPositionAdjustment(player);
                    return InteractionResult.SUCCESS;
                }

                this.handleRightClick(player, serverLevel, interactionHand);

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }

    @Override
    public void tick() {


        if (level instanceof ServerLevel serverLevel) {
            if (this.controlSpecification == null) {
                if (this.consoleBlockPos != null) {
                    if (serverLevel.getBlockEntity(this.consoleBlockPos) instanceof GlobalConsoleBlockEntity globalConsoleBlockEntity) {

                        globalConsoleBlockEntity.markDirty();
                    }
                    discard();
                }
            } else {
                TardisLevelOperator.get(serverLevel).ifPresent(x -> {
                    var shouldShowParticle = x.getTardisFlightEventManager().isWaitingForControlResponse() && x.getTardisFlightEventManager().getWaitingControlPrompt() == this.controlSpecification.control();
                    if (getEntityData().get(SHOW_PARTICLE) != shouldShowParticle) {
                        getEntityData().set(SHOW_PARTICLE, shouldShowParticle);
                    }
                });
            }
        } else {
            if (getEntityData().get(SHOW_PARTICLE)) {
                if (getLevel().random.nextInt(5) == 0) {
                    this.level.addParticle(TRParticles.GALLIFREY.get(), this.getRandomX(0.1), blockPosition().getY(), this.getRandomZ(0.1), 0.0, 0.0, 0.0);
                }
            }
        }

    }

    // Whilst in flight, the TARDIS will have waiting controls for the player to interact with. If this control is of that type, tell the control manager.
    private boolean interactWaitingControl(TardisLevelOperator operator) {
        if (operator.getTardisFlightEventManager().isWaitingForControlResponse() && operator.getTardisFlightEventManager().getWaitingControlPrompt() == this.controlSpecification.control()) {
            operator.getTardisFlightEventManager().respondToWaitingControl(this, this.controlSpecification.control());
            return true;
        }
        return false;
    }

    private void handleControlSizeAndPositionAdjustment(Player player){
        float width = this.getEntityData().get(SCALE_WIDTH);
        float height = this.getEntityData().get(SCALE_HEIGHT);
        float incrementAmount = 0.05F;

        if (player.getOffhandItem().getItem() == Items.REDSTONE) { //Print position output to console
            Vec3 centre = LevelHelper.centerPos(this.consoleBlockPos, false);
            double x = this.position().x() - centre.x;
            double y = this.position().y() - centre.y;
            double z = this.position().z() - centre.z;
            if (controlSpecification != null)
                TardisRefined.LOGGER.info("Control Info for: " + this.controlSpecification.control().getSerializedName());
            TardisRefined.LOGGER.info("Offset: " + x + "F, " + y + "F, " + z + "F");
            float finalWidth = this.getEntityData().get(SCALE_WIDTH);
            float finalHeight = this.getEntityData().get(SCALE_HEIGHT);
            TardisRefined.LOGGER.info("Size (Width, Height): " + finalWidth + "F, " + finalHeight + "F");
        }
        if (player.getOffhandItem().getItem() == Items.DIAMOND) { //Increase Y
            this.setPos(this.position().add(0, player.isShiftKeyDown() ? -0.05 : 0.05, 0));
        }
        if (player.getOffhandItem().getItem() == Items.EMERALD){ //Increase X
            this.setPos(this.position().add(player.isShiftKeyDown() ? -0.05 : 0.05, 0, 0));
        }
        if (player.getOffhandItem().getItem() == Items.GOLD_INGOT){ //Increase Z
            this.setPos(this.position().add(0, 0, player.isShiftKeyDown() ? 0.05 : -0.05));
        }
        if (player.getOffhandItem().getItem() == Items.IRON_INGOT){ //Adjust Size Width
            float newWidth = player.isShiftKeyDown() ? width - incrementAmount : width + incrementAmount;
            this.setSize(newWidth, height);
        }
        if (player.getOffhandItem().getItem() == Items.COPPER_INGOT){ //Adjust Size Height
            float newHeight = player.isShiftKeyDown() ? height - incrementAmount : height + incrementAmount;
            this.setSize(width, newHeight);
        }
    }

    private boolean isDesktopWaitingToGenerate(TardisLevelOperator operator, ServerLevel serverLevel){
        if (!(this.controlSpecification.control().getControl() instanceof MonitorControl)) {
            if (operator.getInteriorManager().isWaitingToGenerate()) {
                serverLevel.playSound(null, this.blockPosition(), SoundEvents.NOTE_BLOCK_BIT, SoundSource.BLOCKS, 100, (float) (0.1 + (serverLevel.getRandom().nextFloat() * 0.5)));
                return true;
            }
        }
        return false;
    }

    private void handleLeftClick(Player player, ServerLevel serverLevel){
        TardisLevelOperator.get(serverLevel).ifPresent(cap -> {

            if (isDesktopWaitingToGenerate(cap, serverLevel))
                return;

            if (!interactWaitingControl(cap)) {
                this.controlSpecification.control().getControl().onLeftClick(cap, consoleTheme, this, player);
            }
        });
    }

    private void handleRightClick(Player player, ServerLevel serverLevel, InteractionHand interactionHand){
        TardisLevelOperator.get(serverLevel).ifPresent(cap -> {

            if (this.controlSpecification.control().getControl() instanceof MonitorControl) {
                if (PlayerUtil.isInMainHand(player, ItemRegistry.KEY.get())) {
                    ItemStack itemStack = player.getMainHandItem();
                    if (itemStack.getItem() instanceof KeyItem keyItem) {
                        ResourceKey<Level> tardis = serverLevel.dimension();
                        if (controlSpecification.control() != null) {
                            if (controlSpecification.control() == ConsoleControl.MONITOR && !KeyItem.keychainContains(itemStack, tardis)) {
                                player.setItemInHand(interactionHand, KeyItem.addTardis(itemStack, tardis));
                                PlayerUtil.sendMessage(player, Component.translatable(ModMessages.MSG_KEY_BOUND, tardis.location().getPath()), true);
                                player.playSound(SoundEvents.PLAYER_LEVELUP, 1, 0.5F);
                                return;
                            }
                        }
                    }
                }
            }
            else {

                if (!cap.getControlManager().canUseControls() && controlSpecification.control() != ConsoleControl.MONITOR) {
                    if (player.isCreative()) {
                        serverLevel.playSound(null, this.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 100, (float) (0.1 + (serverLevel.getRandom().nextFloat() * 0.5)));
                    } else {
                        player.hurt(DamageSource.ON_FIRE, 0.1F);
                    }
                    return;
                }

                if (isDesktopWaitingToGenerate(cap, serverLevel))
                    return;

            }

            if (!interactWaitingControl(cap)) {
                this.controlSpecification.control().getControl().onRightClick(cap, consoleTheme, this, player);
            }
        });
    }


    @Override
    public boolean mayInteract(Level level, BlockPos blockPos) {
        return true;
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
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

}
