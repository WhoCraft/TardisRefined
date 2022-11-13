package whocraft.tardis_refined.common.capability;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import whocraft.tardis_refined.common.tardis.interior.TardisArchitecture;
import whocraft.tardis_refined.common.tardis.interior.TardisArchitectureHandler;

import java.util.Optional;

public class TardisLevelOperator {

    private ServerLevel level;
    private boolean setUp = false;

    public TardisLevelOperator(ServerLevel level) {
        this.level = level;
    }

    @ExpectPlatform
    public static Optional<TardisLevelOperator> get(ServerLevel level) {
        throw new AssertionError();
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean(NBT_IS_SETUP, this.setUp);
        return compoundTag;
    }

    public ServerLevel getLevel() {
        return level;
    }

    public void deserializeNBT(CompoundTag tag) {
        this.setUp = tag.getBoolean(NBT_IS_SETUP);
    }

    public void tick(Level level) {

    }

    public void enterTardis(Player player) {
        if (!setUp) {
            TardisArchitectureHandler.generateDesktop(getLevel(), TardisArchitecture.FACTORY_THEME);
            this.setUp = true;
        }

        // TODO: Door system of some sorts.
        ChunkAccess preloadedArea = getLevel().getChunk(new BlockPos(0,0,0));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.teleportTo(getLevel(), 0, 101, 0, 0, 0);
    }


    private static final String NBT_IS_SETUP = "nbt_has_setup";

}
