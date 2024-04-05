package whocraft.tardis_refined.common.blockentity.life;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import whocraft.tardis_refined.registry.BlockEntityRegistry;
import whocraft.tardis_refined.registry.RefinedDamageSources;

import java.util.List;

public class EyeBlockEntity extends BlockEntity implements BlockEntityTicker<EyeBlockEntity> {
    public EyeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.THE_EYE.get(), blockPos, blockState);


    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, EyeBlockEntity blockEntity) {

        if (level instanceof ServerLevel serverLevel) {
            List<Entity> entities = level.getEntities(null, new AABB(blockPos).inflate(3));
            if (entities != null) {
                for (Entity entity : entities) {
                    entity.setSecondsOnFire(30);
                    entity.hurt(RefinedDamageSources.getSource(serverLevel, RefinedDamageSources.EYE_OF_HARMONY), 10000f);
                }
            }
        }


    }

}
