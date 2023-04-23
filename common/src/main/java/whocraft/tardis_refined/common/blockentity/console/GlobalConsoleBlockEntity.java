package whocraft.tardis_refined.common.blockentity.console;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.manager.TardisInteriorManager;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.LevelHelper;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.BasePattern;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalConsoleBlockEntity extends BlockEntity implements BlockEntityTicker<GlobalConsoleBlockEntity> {

    private boolean isDirty = true;
    private final List<ControlEntity> controlEntityList = new ArrayList<>();

    public AnimationState liveliness = new AnimationState();
    private ConsolePattern basePattern = pattern();

    public GlobalConsoleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_CONSOLE_BLOCK.get(), blockPos, blockState);
    }

    public ConsolePattern pattern() {
        ConsoleTheme console = getBlockState().getValue(GlobalConsoleBlock.CONSOLE);
        ConsolePattern defaultBasePattern = ConsolePatterns.getPatternFromString(console, new ResourceLocation(TardisRefined.MODID, "default"));
        return basePattern == null ? defaultBasePattern : basePattern;
    }

    public GlobalConsoleBlockEntity setPattern(ConsolePattern basePattern) {
        this.basePattern = basePattern;
        return this;
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);

        if (basePattern != null) {
            compoundTag.putString(NbtConstants.PATTERN, basePattern.id().toString());
        }
    }

    @Override
    public void load(CompoundTag tag) {

        ConsoleTheme console = getBlockState().getValue(GlobalConsoleBlock.CONSOLE);

        if (tag.contains(NbtConstants.PATTERN)) {
            ResourceLocation currentPattern = new ResourceLocation(tag.getString(NbtConstants.PATTERN));
            if (ConsolePatterns.doesPatternExist(console, currentPattern)) {
                basePattern = ConsolePatterns.getPatternFromString(console, currentPattern);
            }
        }

        if (basePattern == null) {
            basePattern = pattern();
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

                ControlEntity controlEntity = new ControlEntity(getLevel());


                Vec3 location = LevelHelper.centerPos(currentBlockPos, true).add(control.offsetPosition().x(), control.offsetPosition().y(), control.offsetPosition().z());
                controlEntity.setPos(location.x(), location.y(), location.z());

                controlEntity.assignControlData(theme, control, this.getBlockPos());

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
            x.discard();
        });
        controlEntityList.clear();
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, GlobalConsoleBlockEntity blockEntity) {

        if (this.isDirty) {
            spawnControlEntities();
        }

        if (!liveliness.isStarted()) {
            liveliness.start(12);
        }

        if (level instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(x -> {
                if (x.getTardisFlightEventManager().isInDangerZone() && x.getLevel().getGameTime() % (20) == 0) {

                // Check if we're crashing and if its okay to explode the TARDIS a little.
                if (x.getControlManager().isCrashing() && x.getLevel().getRandom().nextInt(15) == 0) {
                    level.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2f, Explosion.BlockInteraction.NONE);
                }
                    TardisInteriorManager intManager = x.getInteriorManager();
                    if (intManager.isCave()) {
                        intManager.setCurrentTheme(intManager.preparedTheme());
                    }

                    if (x.getTardisFlightEventManager().isInDangerZone() && x.getLevel().getGameTime() % (20) == 0) {
                        serverLevel.playSound(null, blockPos, SoundEvents.NOTE_BLOCK_BELL, SoundSource.BLOCKS, 10f, 2f);
                    }

                }
            });
        }
    }
}
