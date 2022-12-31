package whocraft.tardis_refined.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBiomeTagsProvider;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.TagKeys;

public class TRBiomeTagsProvider extends BiomeTagsProvider {
    public TRBiomeTagsProvider(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //Is mountain or ocean
        tag(TagKeys.IS_MOUNTAIN_OR_OCEAN)
                .addTags(BiomeTags.IS_OCEAN)
                .addTags(BiomeTags.IS_DEEP_OCEAN)
                .addTags(BiomeTags.IS_MOUNTAIN)
                .addOptionalTag(Tags.Biomes.IS_MOUNTAIN.location()) //Add Forge Mountain tag as optional because it contains other entries that vanilla excludes
                .addOptionalTag(new ResourceLocation("c", "ocean")); //Add Fabric Ocean tag as optional because it contains other entries that vanilla excludes
        //Tardis Root Cluster
        tag(TagKeys.TARDIS_ROOT_CLUSTER).addTags(TagKeys.IS_MOUNTAIN_OR_OCEAN);

    }
}