package whocraft.tardis_refined.common.blockentity.console;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.model.blockentity.console.ConsolePatterns;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.BlockEntityRegistry;
import whocraft.tardis_refined.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalConsoleBlockEntity extends BlockEntity implements BlockEntityTicker<GlobalConsoleBlockEntity> {

    private boolean isDirty = true;
    private final List<ControlEntity> controlEntityList = new ArrayList<>();

    private ConsolePatterns.Pattern pattern = ConsolePatterns.getPatternFromString(ConsoleTheme.FACTORY, new ResourceLocation(TardisRefined.MODID, "default"));

    public GlobalConsoleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_CONSOLE_BLOCK.get(), blockPos, blockState);
    }

    public ConsolePatterns.Pattern pattern() {
        ConsoleTheme console = getBlockState().getValue(GlobalConsoleBlock.CONSOLE);
        ConsolePatterns.Pattern defaultPattern = ConsolePatterns.getPatternFromString(console, new ResourceLocation(TardisRefined.MODID, "default"));
        return pattern == null ? defaultPattern : pattern;
    }

    public GlobalConsoleBlockEntity setPattern(ConsolePatterns.Pattern pattern) {
        this.pattern = pattern;
        return this;
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);

        if (pattern != null) {
            compoundTag.putString(NbtConstants.PATTERN, pattern.id().toString());
        }
    }

    @Override
    public void load(CompoundTag tag) {

        ConsoleTheme console = getBlockState().getValue(GlobalConsoleBlock.CONSOLE);

        if (tag.contains(NbtConstants.PATTERN)) {
            ResourceLocation currentPattern = new ResourceLocation(tag.getString(NbtConstants.PATTERN));
            if (ConsolePatterns.doesPatternExist(console, currentPattern)) {
                pattern = ConsolePatterns.getPatternFromString(console, currentPattern);
            }
        }

        if (pattern == null) {
            pattern = ConsolePatterns.getPatternFromString(console, new ResourceLocation(TardisRefined.MODID, "default"));
        }

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
                controlEntity.assignControlData(theme, control, this.getBlockPos());

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

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void sendUpdates() {
        level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
        level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 3);
        setChanged();
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

        if (level instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(x -> {
                if (x.getTardisFlightEventManager().isInDangerZone() && x.getLevel().getGameTime() % (1 * 20) == 0) {
                    serverLevel.playSound(null, blockPos, SoundEvents.NOTE_BLOCK_BELL, SoundSource.BLOCKS, 10f, 2f);
                }

                // Check if we're crashing and if its okay to explode the TARDIS a little.
                if (x.getControlManager().isCrashing() && x.getLevel().getRandom().nextInt(15) == 0) {
                    level.explode((Entity) null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2f, Explosion.BlockInteraction.NONE);
                }

            });
        }
    }
}
