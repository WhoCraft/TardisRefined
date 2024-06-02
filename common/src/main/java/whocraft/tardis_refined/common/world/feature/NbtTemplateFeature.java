package whocraft.tardis_refined.common.world.feature;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.world.feature.config.NbtTemplateFeatureConfig;

import java.util.Optional;

/**
 * Custom Feature implementation that allows for use of NBT templates
 */
public class NbtTemplateFeature extends Feature<NbtTemplateFeatureConfig> {

    private final BlockIgnoreProcessor IGNORE_STRUCTURE_VOID = new BlockIgnoreProcessor(ImmutableList.of(Blocks.STRUCTURE_VOID));
    private final StructurePlaceSettings placementSettings = (new StructurePlaceSettings()).setMirror(Mirror.NONE).addProcessor(IGNORE_STRUCTURE_VOID).setIgnoreEntities(false);

    public NbtTemplateFeature() {
        super(NbtTemplateFeatureConfig.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NbtTemplateFeatureConfig> context) {
        BlockPos.MutableBlockPos posMutable = new BlockPos.MutableBlockPos();
        StructureTemplateManager templateManager = context.level().getLevel().getServer().getStructureManager();
        ResourceLocation templateLocation = context.config().templateLocation;
        Optional<StructureTemplate> template = templateManager.get(templateLocation);

        if (template.isEmpty()) {
            TardisRefined.LOGGER.warn(context.config().templateLocation.toString() + " NTB does not exist!");
            return false;
        }

        BlockPos centre = new BlockPos(template.get().getSize().getX() / 2, 0, template.get().getSize().getZ() / 2);
        placementSettings.setRotation(Rotation.getRandom(context.random())).setRotationPivot(centre).setIgnoreEntities(false);

        posMutable.set(context.origin());
        BlockPos offset = new BlockPos(-template.get().getSize().getX() / 2, context.config().heightOffset, -template.get().getSize().getZ() / 2);
        template.get().placeInWorld(context.level(), posMutable.offset(offset), posMutable.offset(offset), placementSettings, context.random(), Block.UPDATE_CLIENTS);
//        .println("Placed template at: " + posMutable.offset(offset).toString());
        return true;
    }
}