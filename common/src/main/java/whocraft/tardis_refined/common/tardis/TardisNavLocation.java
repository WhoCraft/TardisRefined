package whocraft.tardis_refined.common.tardis;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.compat.SublevelAccessor;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * TardisNavLocation
 * Co-ordinates that represent position, rotation, level and name.
 **/
public class TardisNavLocation {


    public static final TardisNavLocation ORIGIN = new TardisNavLocation(BlockPos.ZERO, Direction.NORTH, Level.OVERWORLD).setSublevelCache(null);
    public static final Codec<TardisNavLocation> CODEC = CompoundTag.CODEC.xmap(
            TardisNavLocation::deserialize, TardisNavLocation::serialise
    );
    public static final StreamCodec<ByteBuf, TardisNavLocation> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    private BlockPos position;
    private Direction direction;
    private ServerLevel level;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<SublevelAccessor.LoadablePositionReference> sublevelReference = Optional.empty();
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<SublevelData> sublevelCache = Optional.empty();

    private ResourceKey<Level> dimensionKey;

    private String name = "";

    /**
     * @param position  World co-ordinate
     * @param direction Rotation/Facing direction.
     * @param level     ResourceKey of the desired level.
     **/
    public TardisNavLocation(BlockPos position, Direction direction, ServerLevel level) {
        this.position = position;
        this.direction = direction;
        this.level = level;
        if (level != null) {
            this.dimensionKey = level.dimension();
        }
    }

    /**
     * <br> Alternate Constructor ONLY for static references.
     * <br> DO NOT use for logic E.g. Using methods from the Level instance
     * <br> This is because this version doesn't have a {@link Level}  reference
     *
     * @param position
     * @param direction
     * @param level
     */
    public TardisNavLocation(BlockPos position, Direction direction, ResourceKey<Level> level) {
        this.position = position;
        this.direction = direction;
        this.dimensionKey = level;
    }

    public static TardisNavLocation deserialize(CompoundTag tag) {
        TardisNavLocation loc = new TardisNavLocation(
                BlockPos.of(tag.getLong("position")),
                Direction.values()[tag.getInt("direction")],
                ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(tag.getString("dimension"))));

        if (tag.contains("name"))
            loc.setName(tag.getString("name"));

        if (tag.contains("sublevel_cache")) {
            loc.sublevelCache = SublevelData.CODEC.parse(
                    NbtOps.INSTANCE, tag.get("sublevel_cache")
            ).resultOrPartial();
        }
        if (tag.contains("sublevel_reference")) {
            loc.sublevelReference = SublevelAccessor.LoadablePositionReference.fromNbt(tag.get("sublevel_reference"));
        }

        return loc;
    }

    public CompoundTag serialise() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("position", this.position.asLong());
        tag.putString("dimension", this.dimensionKey.location().toString());
        tag.putInt("direction", this.direction.ordinal());
        tag.putString("name", this.name);
        sublevelCache.flatMap(
                sl -> SublevelData.CODEC.encodeStart(NbtOps.INSTANCE, sl).resultOrPartial()
        ).ifPresent(
                value -> tag.put("sublevel_cache", value)
        );
        sublevelReference.flatMap(SublevelAccessor.LoadablePositionReference::toNbt).ifPresent(
                value -> tag.put("sublevel_reference", value)
        );
        return tag;
    }

    public ServerLevel getLevel() {

        if(Platform.getServer() == null){
            throw new RuntimeException("Called TardisNavLocation::getLevel before server was created! Please adjust your code!");
        }

        if (this.level != null) {
            this.dimensionKey = this.level.dimension();
            return DimensionUtil.getLevel(dimensionKey);
        }

        this.level = Platform.getServer().getLevel(dimensionKey);
        if (level != null) {
            return level;
        }
        return Platform.getServer().getLevel(Level.OVERWORLD);
    }

    public void setLevel(ServerLevel level) {
        this.dimensionKey = level.dimension();
        this.level = level;
        forMinecraftServer(this::removeSublevelData);
    }

    private void updateLevel() {
        //noinspection ConstantValue
        if (level == null && Platform.getServer() != null) {
            getLevel();
        }
    }

    public void updateCachedPosition() {
	    updateLevel();
        forMinecraftServer(server -> sublevelReference.flatMap(ref -> ref.tryLoad(server)).ifPresent(pos -> {
            var newPos = BlockPos.containing(pos.pos());
            // If the position no longer matches it means the position was assembled/disassembled and needs to be updated.
            if (!position.equals(newPos)) {
                position = newPos;
                sublevelCache.flatMap(SublevelData::visualDirection).ifPresent(d -> direction = d);
            }
        }));
        sublevelCache.ifPresent(data -> data.update(this));
    }

    public TardisNavLocation generateSublevelData() {
        updateLevel();
        if (level != null) {
            sublevelReference.ifPresent(data -> data.destroy(level.getServer()));
            sublevelReference = SublevelAccessor.get().getPositionReference(
                    level, position
            );
            forMinecraftServer(server -> {
                sublevelCache = sublevelReference.flatMap(ref -> ref.tryLoad(server).map(pos -> new SublevelData(this)));
            });
        } else {
            if (Platform.getServer() != null) {
                sublevelReference.ifPresent(data -> data.destroy(level.getServer()));
            }
            sublevelReference = Optional.empty();
            sublevelCache = Optional.empty();
        }
        return this;
    }

    public ResourceKey<Level> getDimensionKey() {
        return dimensionKey;
    }

    public void setDimensionKey(ResourceKey<Level> dimensionKey) {
        this.dimensionKey = dimensionKey;
        updateLevel();
        forMinecraftServer(this::removeSublevelData);
    }

    public BlockPos getPositionNoUpdates() {
        return position;
    }

    public BlockPos getPosition() {
        updateCachedPosition();
        return position;
    }

    public BlockPos getRealPosition() {
        updateCachedPosition();
        return sublevelCache.flatMap(SublevelData::visualPos).orElse(position);
    }

    public Vec3 getRealAccuratePosition(MinecraftServer server) {
        return sublevelReference.flatMap(data -> data.tryLoad(server)).map(
                p -> p.sublevel().toMainLevelPos(p.pos())
        ).orElseGet(() -> Vec3.atBottomCenterOf(position));
    }

    public TardisNavLocation setPosition(BlockPos pos) {
        this.position = pos;
        forMinecraftServer(this::removeSublevelData);
        return this;
    }

    public Direction getDirection() {
        return direction;
    }

    public Direction getRealDirection() {
        updateCachedPosition();
        return sublevelCache.flatMap(SublevelData::visualDirection).orElse(direction);
    }

    public Vector3d getRealAccurateDirection(MinecraftServer server) {
        return sublevelReference.flatMap(
                ref -> ref.tryLoad(server).map(pos -> pos.sublevel().toMainLevelAccurateDirection(direction))
        ).orElseGet(
                () -> SublevelAccessor.directionToVector(direction)
        );
    }

    public TardisNavLocation setDirection(Direction dir) {
        this.direction = dir;
        return this;
    }

    public TardisNavLocation setSublevelCache(SublevelData data) {
        forMinecraftServer(this::removeSublevelData);
        this.sublevelCache = Optional.ofNullable(data);
        updateCachedPosition();
        return this;
    }

    public Optional<Component> getSublevelDescription() {
        return sublevelCache.flatMap(SublevelData::description);
    }

    public String getName() {
        return this.name;
    }

    public TardisNavLocation setName(String name) {
        this.name = name;
        return this;
    }


    public BlockPos setX(int x) {
        BlockPos blockPos = new BlockPos(x, position.getY(), position.getZ());
        position = blockPos;
        forMinecraftServer(this::removeSublevelData);
        return position;
    }

    public BlockPos setY(int y) {
        BlockPos blockPos = new BlockPos(position.getX(), y, position.getZ());
        position = blockPos;
        forMinecraftServer(this::removeSublevelData);
        return position;
    }

    public BlockPos setZ(int z) {
        BlockPos blockPos = new BlockPos(position.getX(), position.getY(), z);
        position = blockPos;
        forMinecraftServer(this::removeSublevelData);
        return position;
    }

    private void forMinecraftServer(Consumer<MinecraftServer> action) {
        if (level != null) {
            action.accept(level.getServer());
        } else if (Platform.getServer() != null) {
            action.accept(Platform.getServer());
        }
    }

    public TardisNavLocation copy() {
        updateCachedPosition();
        TardisNavLocation copy = new TardisNavLocation(this.position, this.direction, this.dimensionKey);

        if (this.getLevel() != null) {
            copy.setLevel(this.getLevel());
        }

        if (this.name != null) {
            copy.setName(this.name);
        }

        sublevelCache.ifPresent(copy::setSublevelCache);

        return copy;
    }

    public void removeSublevelData() {
        this.removeSublevelData(level != null ? level.getServer() : Platform.getServer());
    }

    public void removeSublevelData(MinecraftServer server) {
        if (server != null) {
            sublevelReference.ifPresent(data -> data.destroy(server));
        }
        sublevelReference = Optional.empty();
        sublevelCache = Optional.empty();
    }

    public static class SublevelData {

        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        private Optional<BlockPos> visualPos;
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        private Optional<Direction> visualDirection;
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        private Optional<Component> description;

        public static final Codec<SublevelData> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        BlockPos.CODEC.optionalFieldOf("visual_pos").forGetter(SublevelData::visualPos),
                        Direction.CODEC.optionalFieldOf("visual_direction").forGetter(SublevelData::visualDirection),
                        ComponentSerialization.CODEC.optionalFieldOf("description").forGetter(SublevelData::description)
                ).apply(instance, SublevelData::new)
        );

        private SublevelData(TardisNavLocation location) {
            this(Optional.empty(), Optional.empty(), Optional.empty());
            update(location);
        }

        private SublevelData(Optional<BlockPos> pos, Optional<Direction> direction, Optional<Component> description) {
            this.visualPos = pos;
            this.visualDirection = direction;
            this.description = description;
        }

        public void update(TardisNavLocation location) {
            location.sublevelReference.ifPresent(ref -> {
                location.forMinecraftServer(server -> {
                    var loaded = ref.tryLoad(server);
                    visualPos = loaded.map(pos -> pos.sublevel().toMainLevelPos(BlockPos.containing(pos.pos())));
                    visualDirection = loaded.map(pos -> pos.sublevel().toMainLevelDirection(location.getDirection()));
                    description = loaded.flatMap(pos -> pos.sublevel().description());
                });
            });
        }

        public Optional<BlockPos> visualPos() {
            return visualPos;
        }

        public Optional<Direction> visualDirection() {
            return visualDirection;
        }

        public Optional<Component> description() {
            return description;
        }

    }

}
