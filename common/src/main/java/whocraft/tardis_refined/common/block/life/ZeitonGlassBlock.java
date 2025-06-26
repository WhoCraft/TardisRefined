
public class ZeitonGlassBlock extends BasEntityBlock {

    public ZeitonGlassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ZeitonGlassBlockEntityblockPos, blockState);
    }

}