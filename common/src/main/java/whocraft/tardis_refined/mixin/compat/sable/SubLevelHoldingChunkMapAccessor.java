package whocraft.tardis_refined.mixin.compat.sable;

import dev.ryanhcode.sable.sublevel.storage.holding.SubLevelHoldingChunk;
import dev.ryanhcode.sable.sublevel.storage.holding.SubLevelHoldingChunkMap;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Invoker;

@Pseudo
@Mixin(SubLevelHoldingChunkMap.class)
public interface SubLevelHoldingChunkMapAccessor {

    @Invoker
    SubLevelHoldingChunk callGetOrLoadHoldingChunk(final ChunkPos chunkPos, final boolean create);
    
}
