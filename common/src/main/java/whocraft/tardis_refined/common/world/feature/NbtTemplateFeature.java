package whocraft.tardis_refined.common.world.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.util.RandomSource;
import whocraft.tardis_refined.TardisRefined;

import java.util.Optional;

/** Places a structure template with a random horizontal rotation. */
public class NbtTemplateFeature implements Feature {
    public static final MapCodec<NbtTemplateFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("template_location").forGetter(feature -> feature.templateLocation),
            com.mojang.serialization.Codec.INT.fieldOf("height_offset").orElse(0).forGetter(feature -> feature.heightOffset)
    ).apply(instance, NbtTemplateFeature::new));

    private final Identifier templateLocation;
    private final int heightOffset;

    /** Registry instance; configured instances are decoded through CODEC. */
    public NbtTemplateFeature() {
        this(Identifier.fromNamespaceAndPath("minecraft", "empty"), 0);
    }

    private NbtTemplateFeature(Identifier templateLocation, int heightOffset) {
        this.templateLocation = templateLocation;
        this.heightOffset = heightOffset;
    }

    @Override
    public MapCodec<? extends Feature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator generator, RandomSource random, BlockPos origin) {
        StructureTemplateManager templateManager = level.getLevel().getStructureTemplateManager();
        Optional<StructureTemplate> template = templateManager.get(templateLocation);
        if (template.isEmpty()) {
            TardisRefined.LOGGER.warn("Structure template {} does not exist", templateLocation);
            return false;
        }

        StructureTemplate structure = template.get();
        BlockPos pivot = new BlockPos(structure.getSize().getX() / 2, 0, structure.getSize().getZ() / 2);
        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setMirror(Mirror.NONE)
                .setRotation(Rotation.getRandom(random))
                .setRotationPivot(pivot)
                .setIgnoreEntities(false)
                .addProcessor(new BlockIgnoreProcessor(ImmutableList.of(Blocks.STRUCTURE_VOID)));
        BlockPos placement = origin.offset(-structure.getSize().getX() / 2, heightOffset, -structure.getSize().getZ() / 2);
        return structure.placeInWorld(level, placement, placement, settings, random, Block.UPDATE_CLIENTS);
    }
}
