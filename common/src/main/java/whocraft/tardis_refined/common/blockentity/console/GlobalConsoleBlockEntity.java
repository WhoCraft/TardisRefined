package whocraft.tardis_refined.common.blockentity.console;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.registry.BlockEntityRegistry;
import whocraft.tardis_refined.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalConsoleBlockEntity extends BlockEntity implements BlockEntityTicker<GlobalConsoleBlockEntity> {

    private boolean isDirty = true;
    private final List<ControlEntity> controlEntityList = new ArrayList<>();

    public GlobalConsoleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_CONSOLE_BLOCK.get(), blockPos, blockState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        spawnControlEntities();
    }

    public void spawnControlEntities() {
        // Things needed.

        BlockPos currentBlockPos = getBlockPos();

        if (getLevel() instanceof ServerLevel serverLevel) {

            killControls();
            ConsoleTheme theme = getBlockState().getValue(GlobalConsoleBlock.CONSOLE);
            ControlSpecification[] controls = theme.getControlSpecificationList();
            Arrays.stream(controls).toList().forEach(control -> {
                // Spawn a control!
                ControlEntity controlEntity = EntityRegistry.CONTROL_ENTITY.get().create(getLevel());
                controlEntity.setControlSpecification(control);

                Vector3f location = new Vector3f(((float) currentBlockPos.getX() + control.offsetPosition().x() + 0.5f), (float) getBlockPos().getY() + control.offsetPosition().y() + 0.5f, (float) getBlockPos().getZ() + control.offsetPosition().z() + 0.5f);
                controlEntity.teleportTo(location.x(), location.y(), location.z());

                serverLevel.addFreshEntity(controlEntity);
                controlEntityList.add(controlEntity);
            });

            this.isDirty = false;
        }
    }

    public void markDirty() {
        this.isDirty = true;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        killControls();
    }

    public void killControls() {
        controlEntityList.forEach(x -> {
            x.teleportTo(0, -1000, 0);
            x.kill();
        });
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, GlobalConsoleBlockEntity blockEntity) {
        if (this.isDirty) {
            spawnControlEntities();
        }
    }
}
