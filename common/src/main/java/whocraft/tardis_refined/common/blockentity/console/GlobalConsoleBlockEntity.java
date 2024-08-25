package whocraft.tardis_refined.common.blockentity.console;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
import whocraft.tardis_refined.common.util.LevelHelper;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;
import whocraft.tardis_refined.registry.TRConsoleThemes;

import java.util.ArrayList;
import java.util.List;

public class GlobalConsoleBlockEntity extends BlockEntity implements BlockEntityTicker<GlobalConsoleBlockEntity> {

    private boolean shouldSpawnControls = true;

    private final List<ControlEntity> controlEntityList = new ArrayList<>();

    public AnimationState liveliness = new AnimationState();

    private ResourceLocation consoleTheme;

    private ConsolePattern basePattern;

    public GlobalConsoleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.GLOBAL_CONSOLE_BLOCK.get(), blockPos, blockState);
        this.consoleTheme = TRConsoleThemes.FACTORY.getId();
        this.basePattern = this.pattern();
    }

    public ResourceLocation theme() {
        if (this.consoleTheme == null) {
            this.consoleTheme = TRConsoleThemes.FACTORY.getId();
        }
        return this.consoleTheme;
    }

    public void setConsoleTheme(ResourceLocation themeId) {
        this.consoleTheme = themeId;
        this.setChanged();
        this.sendUpdates();
    }

    public ConsolePattern pattern() {
        return basePattern == null ? ConsolePatterns.DEFAULT : basePattern;
    }

    public List<ControlEntity> getControlEntityList() {
        return this.controlEntityList;
    }

    public GlobalConsoleBlockEntity setPattern(ConsolePattern basePattern) {
        this.basePattern = basePattern;
        this.setChanged();
        this.sendUpdates();
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
            ResourceLocation themeId = new ResourceLocation(tag.getString(NbtConstants.THEME));
            this.consoleTheme = themeId;
        }

        if (tag.contains(NbtConstants.PATTERN)) {
            ResourceLocation currentPattern = new ResourceLocation(tag.getString(NbtConstants.PATTERN));
            ResourceLocation theme = this.theme();
            if (ConsolePatterns.doesPatternExist(theme, currentPattern)) {
                this.basePattern = ConsolePatterns.getPatternOrDefault(theme, currentPattern);
            }
        }

        if (this.consoleTheme == null) {
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
            ConsoleTheme consoleTheme = TRConsoleThemes.CONSOLE_THEME_REGISTRY.get(themeId);
            for (ControlSpecification control : consoleTheme.getControlSpecificationList()) {
                // Spawn a control!
                ControlEntity controlEntity = new ControlEntity(getLevel());

                Vec3 location = LevelHelper.centerPos(currentBlockPos, true).add(control.offsetPosition().x(), control.offsetPosition().y(), control.offsetPosition().z());
                controlEntity.setPos(location.x(), location.y(), location.z());

                controlEntity.assignControlData(consoleTheme, control, this.getBlockPos());

                serverLevel.addFreshEntity(controlEntity);
                controlEntityList.add(controlEntity);
            }

            this.shouldSpawnControls = false;
        }
    }

    public void markDirty() {
        this.shouldSpawnControls = true;
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
        level.updateNeighbourForOutputSignal(this.getBlockPos(), getBlockState().getBlock());
        level.sendBlockUpdated(this.getBlockPos(), level.getBlockState(this.getBlockPos()), level.getBlockState(this.getBlockPos()), Block.UPDATE_CLIENTS);
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

        if (this.shouldSpawnControls && blockState.getValue(GlobalConsoleBlock.POWERED)) {
            spawnControlEntities();
        }

        if (!liveliness.isStarted()) {
            liveliness.start(12);
        }


        if (level instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(x -> {

                if (x.getPilotingManager().isOnCooldown() && serverLevel.getGameTime() % 20 == 0) {

                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, blockPos.getX(), blockPos.getY() + 1.0, blockPos.getZ(), 120, 2.0, 1.0, 2.0, 0.005);
                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, blockPos.getX(), blockPos.getY() + 1.0, blockPos.getZ(), 120, 2.0, 1.0, 2.0, 0.005);
                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, blockPos.getX(), blockPos.getY() + 1.0, blockPos.getZ(), 120, 2.0, 1.0, 2.0, 0.01);
                }

                if (x.getLevel().getGameTime() % (20) == 0) {

                    // Check if we're crashing and if its okay to explode the TARDIS a little.
                    if (x.getPilotingManager().isCrashing() && x.getLevel().getRandom().nextInt(15) == 0) {
                        level.explode(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 2f, Level.ExplosionInteraction.NONE);
                    }
                    TardisInteriorManager intManager = x.getInteriorManager();
                    if (intManager.isCave()) {
                        intManager.setCurrentTheme(intManager.preparedTheme());
                    }
                }
            });
        }
    }
}
