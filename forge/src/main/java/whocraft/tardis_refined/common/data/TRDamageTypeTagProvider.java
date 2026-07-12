package whocraft.tardis_refined.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.TRDamageSources;

import java.util.concurrent.CompletableFuture;

public class TRDamageTypeTagProvider extends DamageTypeTagsProvider {
    public TRDamageTypeTagProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, TardisRefined.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(TRDamageSources.CHOKE);
        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(TRDamageSources.CHOKE);
        this.tag(DamageTypeTags.NO_IMPACT).add(TRDamageSources.CHOKE, TRDamageSources.EYE_OF_HARMONY);
        this.tag(DamageTypeTags.BYPASSES_SHIELD).add(TRDamageSources.CHOKE);
        // Reminder to add NO_KNOCKBACK in 1.20.2!
    }

}
