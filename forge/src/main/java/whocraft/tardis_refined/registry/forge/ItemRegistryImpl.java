package whocraft.tardis_refined.registry.forge;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

@Mod.EventBusSubscriber(modid = TardisRefined.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistryImpl {
    public static CreativeModeTab TAB;

    public static CreativeModeTab getCreativeTab() {
        return TAB;
    }

    @SubscribeEvent
    public static void addCreativeTab(CreativeModeTabEvent.Register event) {
        TAB = event.registerCreativeModeTab(new ResourceLocation(TardisRefined.MODID, TardisRefined.MODID),
                builder -> builder.icon(() -> new ItemStack(BlockRegistry.GLOBAL_CONSOLE_BLOCK.get()))
                        .title(Component.literal("TARDIS Refined")));
    }

    @SubscribeEvent
    public static void onPopulateTab(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab() == TAB) {
            for (RegistrySupplier<?> itemRegistrySupplier : ItemRegistry.TAB_ITEMS) {
                event.accept((Item) itemRegistrySupplier.get());
            }
        }
    }
}