package whocraft.tardis_refined.common.data;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.EntityRegistry;

public class ProviderEntityTags extends EntityTypeTagsProvider {

    public static final TagKey<EntityType<?>> ENTITY_BLACKLIST = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("carryon", "entity_blacklist"));

    public ProviderEntityTags(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //Carry On Mod compat
        tag(ENTITY_BLACKLIST).add(EntityRegistry.CONTROL_ENTITY.get());
    }
}
