package whocraft.tardis_refined.common.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.control.ship.MonitorControl;
import whocraft.tardis_refined.common.util.MiscHelper;
import whocraft.tardis_refined.registry.EntityRegistry;

public class ControlEntity extends PathfinderMob {

    private ControlSpecification controlSpecification;

    public ControlEntity(Level level) {
        super(EntityRegistry.CONTROL_ENTITY.get(), level);
    }

    public void setControlSpecification(ControlSpecification consoleControl) {
        this.controlSpecification = consoleControl;
        this.setBoundingBox(new AABB(new BlockPos(consoleControl.scale)));
        this.refreshDimensions();
        this.setCustomName(Component.translatable(consoleControl.control.getLangId()));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().
                add(Attributes.FOLLOW_RANGE, 35D).
                add(Attributes.MOVEMENT_SPEED, 0.23F).
                add(Attributes.ATTACK_DAMAGE, 3F).
                add(Attributes.MAX_HEALTH, 20000000000D).
                add(Attributes.ARMOR, 2000000000.0D);
    }

    @Override
    protected AABB makeBoundingBox() {
        if (controlSpecification != null) {
            return new AABB(new BlockPos(controlSpecification.scale));
        }
        return super.makeBoundingBox();
    }

    @Override
    public AttributeMap getAttributes() {
        return new AttributeMap(createAttributes().build());
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
                TardisLevelOperator.get(serverLevel).ifPresent(cap -> {

                    if (!(this.controlSpecification.control.getControl() instanceof MonitorControl)) {
                        if (cap.getInteriorManager().isWaitingToGenerate()) {
                            serverLevel.playSound(null, this.blockPosition(), SoundEvents.NOTE_BLOCK_BIT, SoundSource.BLOCKS, 100, (float)(0.1 + (serverLevel.getRandom().nextFloat() * 0.5)) );
                            return;
                        }
                    }
                    this.controlSpecification.control.getControl().onLeftClick(cap, this, player);
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
                TardisLevelOperator.get(serverLevel).ifPresent(cap -> {

                    if (!(this.controlSpecification.control.getControl() instanceof MonitorControl)) {
                        if (cap.getInteriorManager().isWaitingToGenerate()) {
                            serverLevel.playSound(null, this.blockPosition(), SoundEvents.NOTE_BLOCK_BIT, SoundSource.BLOCKS, 100, (float)(0.1 + (serverLevel.getRandom().nextFloat() * 0.5)) );
                            return;
                        }
                    }

                    this.controlSpecification.control.getControl().onRightClick(cap, this, player);

                });
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }


    @Override
    public void tick() {
        setNoAi(true);
        super.tick();
    }

}
