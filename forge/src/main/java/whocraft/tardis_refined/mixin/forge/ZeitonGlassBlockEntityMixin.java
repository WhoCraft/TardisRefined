package whocraft.tardis_refined.mixin.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import whocraft.tardis_refined.client.ZeitonGlassTracker;
import whocraft.tardis_refined.common.blockentity.life.ZeitonGlassBlockEntity;

@Mixin(ZeitonGlassBlockEntity.class)
public abstract class ZeitonGlassBlockEntityMixin extends BlockEntity {

    public ZeitonGlassBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (hasLevel() && level.isClientSide()) {
            ZeitonGlassTracker.onLoad((ZeitonGlassBlockEntity) (Object) this);
        }
    }


}
