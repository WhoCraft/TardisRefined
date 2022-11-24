package whocraft.tardis_refined.common.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.control.IControl;
import whocraft.tardis_refined.common.tardis.control.flight.ThrottleControl;
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
    public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand interactionHand) {
        System.out.println("Interacted in any wya");

        if (getLevel() instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(cap -> {
                this.controlSpecification.control.getControl().onRightClick(cap);
            });
        }

        return InteractionResult.SUCCESS;
    }




    @Override
    public void tick() {
        setNoAi(true);
        super.tick();
    }

}
