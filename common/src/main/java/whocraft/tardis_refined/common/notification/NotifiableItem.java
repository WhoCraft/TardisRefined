package whocraft.tardis_refined.common.notification;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;

import java.util.logging.Level;

public interface NotifiableItem {

   boolean containsIntendedTardis(ItemStack stack, ResourceKey<Level> tardis);

}
