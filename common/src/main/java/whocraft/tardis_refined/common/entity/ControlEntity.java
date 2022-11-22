package whocraft.tardis_refined.common.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.control.IControl;
import whocraft.tardis_refined.common.tardis.control.flight.ThrottleControl;
import whocraft.tardis_refined.common.util.MiscHelper;

public class ControlEntity extends Entity{

    private ControlSpecification controlSpecification;
    private IControl consoleControl;

    public ControlEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public void setControlSpecification(ControlSpecification consoleControl) {
        this.controlSpecification = consoleControl;

        if (getLevel() instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(cap -> {
                switch (controlSpecification.control) {
                    case THROTTLE -> this.consoleControl = new ThrottleControl();
                }
            });
        }

        //this.setBoundingBox(new AABB(0,0,0,1,1,1));
    }

//    @Override
//    protected AABB makeBoundingBox() {
//        if (controlSpecification != null) {
//            double controlSizeX= controlSpecification.scale.getX() / 2;
//            double controlSizeY= controlSpecification.scale.getY() / 2;
//            double controlSizeZ= controlSpecification.scale.getZ() / 2;
//            return new AABB(-controlSizeX,-controlSizeY,-controlSizeZ,controlSizeX,controlSizeY,controlSizeZ);
//        }
//
//        return super.makeBoundingBox();
//    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
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
    protected AABB makeBoundingBox() {
        return super.makeBoundingBox();
    }



    @Override
    public InteractionResult interact(Player player, InteractionHand interactionHand) {

        System.out.println("Interacted in any wya");

        if (getLevel() instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(cap -> {
               // this.consoleControl.onRightClick(cap);
            });
        }


        return super.interact(player, interactionHand);
    }

    @Override
    public void tick() {
        super.tick();

    }
}
