package whocraft.tardis_refined.common.notification;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.logging.Level;

public class NotificationService {

    public static boolean canPlayerBeNotified(Player player, ResourceKey<Level> tardis) {
        Inventory inventory = player.getInventory();
        for (ItemStack item : inventory.items) {
            if (item.getItem() instanceof NotifiableItem notifiableItem) {
                if (notifiableItem.containsIntendedTardis(item, tardis)) {
                    return true;
                }
            }
        }
        return false;
    }


}
