package whocraft.tardis_refined.common.notification;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.logging.Level;

public interface NotifiableItem {

   boolean containsIntendedTardis(ItemStack stack, ResourceKey<Level> tardis);

}
