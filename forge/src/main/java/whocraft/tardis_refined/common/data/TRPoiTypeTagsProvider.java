package whocraft.tardis_refined.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.TRPointOfInterestTypes;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TRPoiTypeTagsProvider extends PoiTypeTagsProvider {

    public TRPoiTypeTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        super.addTags(arg);
        var appender = tag(PoiTypeTags.ACQUIRABLE_JOB_SITE);
        for (Map.Entry<ResourceKey<PoiType>, PoiType> resourceKeyPoiTypeEntry : TRPointOfInterestTypes.POIS.getRegistry().get().entrySet()) {
            appender.add(resourceKeyPoiTypeEntry.getKey());
        }

    }
}
