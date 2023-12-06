package whocraft.tardis_refined.common.blockentity.console;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.manager.TardisInteriorManager;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.common.util.LevelHelper;
import whocraft.tardis_refined.common.util.RegistryHelper;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.constants.ResourceConstants;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.registry.BlockEntityRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GlobalConsoleBlockEntity extends BlockEntity implements BlockEntityTicker<GlobalConsoleBlockEntity> {

    private boolean isDirty = true;
    private final List<ControlEntity> controlEntityList = new ArrayList<>();

    public AnimationState liveliness = new AnimationState();

    private ResourceLocation consoleTheme;

    private ConsolePattern basePattern;

    public GlobalConsoleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.GLOBAL_CONSOLE_BLOCK.get(), blockPos, blockState);
        this.consoleTheme = ConsoleTheme.FACTORY.getId();
        this.basePattern = this.pattern();
    }

    public ResourceLocation theme(){
        if (this.consoleTheme == null){
            this.consoleTheme = ConsoleTheme.FACTORY.getId();
        }
        return this.consoleTheme;
    }

    public void setConsoleTheme(ResourceLocation themeId){
        this.consoleTheme = themeId;
    }

    public ConsolePattern pattern() {
        ResourceLocation console = this.theme();
        ConsolePattern defaultBasePattern = ConsolePatterns.getPatternOrDefault(console, ResourceConstants.DEFAULT_PATTERN_ID);
        return basePattern == null ? defaultBasePattern : basePattern;
    }

    public GlobalConsoleBlockEntity setPattern(ConsolePattern basePattern) {
        this.basePattern = basePattern;
        return this;
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);

        if (this.consoleTheme != null) {
            compoundTag.putString(NbtConstants.THEME, this.consoleTheme.toString());
        }

        if (this.basePattern != null) {
            compoundTag.putString(NbtConstants.PATTERN, basePattern.id().toString());
        }

    }

    @Override
    public void load(CompoundTag tag) {

        if (tag.contains(NbtConstants.THEME)) {
            ResourceLocation themeId = new ResourceLocation(tag.getString(NbtConstants.PATTERN));
            this.consoleTheme = themeId;
        }

        if (tag.contains(NbtConstants.PATTERN)) {
            ResourceLocation currentPattern = new ResourceLocation(tag.getString(NbtConstants.PATTERN));
            ResourceLocation theme = this.theme();
            if (ConsolePatterns.doesPatternExist(theme, currentPattern)) {
                this.basePattern = ConsolePatterns.getPatternOrDefault(theme, currentPattern);
            }
        }

        if (this.consoleTheme == null){
            this.consoleTheme = this.theme();
        }

        if (this.basePattern == null) {
            this.basePattern = this.pattern();
        }

        super.load(tag);

        this.spawnControlEntities();
    }

    public void spawnControlEntities() {
        // Things needed.

        BlockPos currentBlockPos = getBlockPos();

        if (getLevel() instanceof ServerLevel serverLevel) {

            killControls();
            ResourceLocation themeId = this.theme();
            ConsoleTheme consoleTheme = ConsoleTheme.CONSOLE_THEME_REGISTRY.get(themeId);
            ControlSpecification[] controls = consoleTheme.getControlSpecificationList();
            Arrays.stream(controls).toList().forEach(control -> {
                // Spawn a control!

                ControlEntity controlEntity = new ControlEntity(getLevel());


                Vec3 location = LevelHelper.centerPos(currentBlockPos, true).add(control.offsetPosition().x(), control.offsetPosition().y(), control.offsetPosition().z());
                controlEntity.setPos(location.x(), location.y(), location.z());

                controlEntity.assignControlData(consoleTheme, control, this.getBlockPos());

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
                if (x.getPilotingManager().isCrashing() && x.getLevel().getRandom().nextInt(15) == 0) {
                    level.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2f, Level.ExplosionInteraction.NONE);
                }
                    TardisInteriorManager intManager = x.getInteriorManager();
                    if (intManager.isCave()) {
                        intManager.setCurrentTheme(intManager.preparedTheme());
                    }

                    if (x.getTardisFlightEventManager().isInDangerZone() && x.getLevel().getGameTime() % (20) == 0) {
                        serverLevel.playSound(null, blockPos, SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.BLOCKS, 10f, 2f);
                    }

                }
            });
        }
    }
}
