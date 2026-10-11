package whocraft.tardis_refined.common.blockentity.console;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import com.geckolib.animatable.GeoBlockEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import whocraft.tardis_refined.TRConfig;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.block.console.GlobalConsoleBlock;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.ControlSpecification;
import whocraft.tardis_refined.common.tardis.manager.TardisInteriorManager;
import whocraft.tardis_refined.common.tardis.themes.ConsoleTheme;
import whocraft.tardis_refined.common.util.LevelHelper;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ConsolePattern;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.registry.TRBlockEntityRegistry;
import whocraft.tardis_refined.registry.TRDamageSources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GlobalConsoleBlockEntity extends BlockEntity implements BlockEntityTicker<GlobalConsoleBlockEntity>, GeoBlockEntity {

    private static final Map<String, RawAnimation> GEO_FLIGHT_ANIMATIONS = animations("flight",
            "factory", "crystal", "copper", "toyota", "victorian", "myst", "nuka", "initiative", "refurbished");
    private static final Map<String, RawAnimation> GEO_IDLE_ANIMATIONS = animations("idle",
            "factory", "crystal", "copper", "toyota", "victorian", "myst", "initiative", "refurbished");

    private final AnimatableInstanceCache geckoCache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("console_flight", state -> {
            if (this.level == null || !this.getBlockState().getValue(GlobalConsoleBlock.POWERED)) return PlayState.STOP;
            TardisClientData data = TardisClientData.getInstance(this.level.dimension());
            Identifier theme = this.theme();
            if (theme == null) theme = data.getConsoleTheme();
            if (theme == null) return PlayState.STOP;
            String themeName = theme.getPath();
            if (data.isFlying()) {
                RawAnimation flight = GEO_FLIGHT_ANIMATIONS.get(themeName);
                return flight == null ? PlayState.STOP : state.setAndContinue(flight);
            }
            if (TRConfig.CLIENT.PLAY_CONSOLE_IDLE_ANIMATIONS.get()) {
                RawAnimation idle = GEO_IDLE_ANIMATIONS.get(themeName);
                if (idle != null) return state.setAndContinue(idle);
            }
            return PlayState.STOP;
        }));
    }

    private static Map<String, RawAnimation> animations(String clip, String... themes) {
        Map<String, RawAnimation> result = new HashMap<>();
        for (String theme : themes) {
            result.put(theme, RawAnimation.begin().thenLoop("animation." + theme + "." + clip));
        }
        return Map.copyOf(result);
    }
    @Override public AnimatableInstanceCache getAnimatableInstanceCache() { return geckoCache; }

    private final List<ControlEntity> controlEntityList = new ArrayList<>();
    public AnimationState liveliness = new AnimationState();
    public AnimationState powerOff = new AnimationState();
    public AnimationState powerOn = new AnimationState();
    private boolean shouldSpawnControls = true;
    private Identifier consoleTheme;
    private int ticksBooting = 0;

    private ConsolePattern basePattern;

    public int getTicksBooting() {
        return ticksBooting;
    }

    public void setTicksBooting(int ticksBooting) {
        this.ticksBooting = ticksBooting;
    }

    public GlobalConsoleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TRBlockEntityRegistry.GLOBAL_CONSOLE_BLOCK.get(), blockPos, blockState);
        this.consoleTheme = ConsoleTheme.FACTORY.getId();
        this.basePattern = this.pattern();
    }

    public Identifier theme() {
        if (this.consoleTheme == null) {
            this.consoleTheme = ConsoleTheme.FACTORY.getId();
        }
        return this.consoleTheme;
    }

    public void setConsoleTheme(Identifier themeId) {
        this.consoleTheme = themeId;
        this.setChanged();
        this.sendUpdates();
        if (this.level instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(operator -> {
                operator.tardisClientData().setConsoleTheme(themeId);
                operator.tardisClientData().sync();
            });
        }
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
        if (this.level instanceof ServerLevel serverLevel && basePattern != null) {
            TardisLevelOperator.get(serverLevel).ifPresent(operator -> {
                operator.tardisClientData().setConsolePattern(basePattern.id());
                operator.tardisClientData().sync();
            });
        }
        return this;
    }

    @Override
    protected void saveAdditional(ValueOutput compoundTag) {
        super.saveAdditional(compoundTag);

        if (this.consoleTheme != null) {
            compoundTag.putString(NbtConstants.THEME, this.consoleTheme.toString());
        }

        if (this.basePattern != null) {
            compoundTag.putString(NbtConstants.PATTERN, basePattern.id().toString());
        }

        compoundTag.putInt("ticks_booting", ticksBooting);
    }

    @Override
    protected void loadAdditional(ValueInput tag) {

        boolean needsDataFixed = false;

        if (tag.getInt("ticks_booting").isPresent()) {
            this.ticksBooting = tag.getIntOr("ticks_booting", 0);
        }

        if (tag.getString(NbtConstants.THEME).isPresent()) {
            Identifier themeId = Identifier.parse(tag.getStringOr(NbtConstants.THEME, ""));

            ConsoleTheme theme = ConsoleTheme.CONSOLE_THEME_DEFERRED_REGISTRY.get(themeId);

            if (theme == null) {
                TardisRefined.LOGGER.info("The console theme: {} does not exist! Resetting Console Theme & Pattern at {}", themeId, getBlockPos());
                needsDataFixed = true;
                consoleTheme = theme();
            } else {
                this.consoleTheme = themeId;
            }
        }


        if (tag.getString(NbtConstants.PATTERN).isPresent() && !needsDataFixed) {
            Identifier currentPattern = Identifier.parse(tag.getStringOr(NbtConstants.PATTERN, ""));
            Identifier theme = this.theme();
            if (ConsolePatterns.doesPatternExist(theme, currentPattern)) {
                this.basePattern = ConsolePatterns.getPatternOrDefault(theme, currentPattern);
            }
        }



        if (this.consoleTheme == null) {
            this.consoleTheme = this.theme();
        }

        if (this.basePattern == null || needsDataFixed) {
            this.basePattern = this.pattern();
        }


        super.loadAdditional(tag);

        this.spawnControlEntities();
    }

    public void spawnControlEntities() {
        // Things needed.

        BlockPos currentBlockPos = getBlockPos();

        if (getLevel() instanceof ServerLevel serverLevel) {

            killControls();
            Identifier themeId = this.theme();
            ConsoleTheme consoleTheme = ConsoleTheme.CONSOLE_THEME_DEFERRED_REGISTRY.get(themeId);
            ControlSpecification[] controls = consoleTheme.getControlSpecificationList();
            for (ControlSpecification control : Arrays.stream(controls).toList()) {
                // Spawn a control!
                ControlEntity controlEntity = new ControlEntity(getLevel());

                controlEntity.setPosForDebug(control.offsetPosition());

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
        controlEntityList.forEach(Entity::discard);
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

        if(ticksBooting >= 1){
            ticksBooting++;
        }

        if(ticksBooting == 20 * 2){
            ticksBooting = 0;
        }


        if (level instanceof ServerLevel serverLevel) {
            TardisLevelOperator.get(serverLevel).ifPresent(x -> {

                if (x.getPilotingManager().isInRecovery() && serverLevel.getGameTime() % 20 == 0) {

                    List<Player> nearbyPlayers = TardisHelper.getPlayersInRange(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 3);
                    nearbyPlayers.forEach(player -> {
                        player.hurt(TRDamageSources.getSource(serverLevel, TRDamageSources.CHOKE), 0.5F);
                    });

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
