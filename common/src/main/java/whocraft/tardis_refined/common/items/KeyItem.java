package whocraft.tardis_refined.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.control.ConsoleControl;
import whocraft.tardis_refined.common.util.PlayerUtil;

import java.util.ArrayList;
import java.util.List;

public class KeyItem extends Item {

    public KeyItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack itemStack) {

        if(keychain(itemStack).size() >= 2){
            return Component.translatable(ModMessages.ITEM_KEYCHAIN);
        }

        return super.getName(itemStack);
    }

    public static ItemStack addTardis(ItemStack itemStack, ResourceKey<Level> levelResourceKey) {
        // Get the tag of the itemStack object
        CompoundTag itemtag = itemStack.getOrCreateTag();

        StringTag dim = StringTag.valueOf(levelResourceKey.location().toString());

        ListTag keychain;
        if (!itemtag.contains("keychain", CompoundTag.TAG_LIST)) {
            // Create a new keychain tag and add it to the itemtag object
            keychain = new ListTag();
            itemtag.put("keychain", keychain);
        } else {
            // Get the existing keychain tag from the itemtag object
            keychain = itemtag.getList("keychain", Tag.TAG_STRING);
        }

        // Add dim to the keychain, whether or not it already exists in the keychain
        keychain.add(dim);

        itemStack.setTag(itemtag);
        return itemStack;
    }


    public static ArrayList<ResourceKey<Level>> keychain(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();


        if (!nbt.contains("keychain")) {
            return new ArrayList<>();
        }

        ListTag keychain = nbt.getList("keychain", Tag.TAG_STRING);

        ArrayList<ResourceKey<Level>> levels = new ArrayList<>();

        for (Tag tag : keychain) {
            String string = tag.getAsString();
            ResourceKey<Level> key = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(string));
            levels.add(key);
        }

        // Return the list of ResourceKeys
        return levels;
    }

    public static boolean keychainContains(ItemStack itemStack, ResourceKey<Level> levelResourceKey) {
        ArrayList<ResourceKey<Level>> keychain = keychain(itemStack);
        return keychain.contains(levelResourceKey);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {

        if (livingEntity.level instanceof ServerLevel serverLevel) {
            if (livingEntity instanceof ControlEntity control) {
                ResourceKey<Level> tardis = serverLevel.dimension();
                if(control.controlSpecification().control() != null) {
                    if (control.controlSpecification().control() == ConsoleControl.MONITOR && !keychainContains(itemStack, tardis))
                    {
                        player.setItemInHand(interactionHand, addTardis(itemStack, tardis));
                        PlayerUtil.sendMessage(player, Component.translatable(ModMessages.MSG_KEY_BOUND, tardis.location().getPath()), true);
                        player.playSound(SoundEvents.PLAYER_LEVELUP, 1, 0.5F);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }



        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }

    // USED FOR DEVELOPMENT. ENSURE THIS IS NOT IN PRODUCTION.

//    @Override
//    public InteractionResult useOn(UseOnContext context) {
//        if (context.getLevel() instanceof ServerLevel) {
//
//            if (context.getPlayer().getAbilities().instabuild && context.getPlayer().isCrouching()) {
//
//                var keychain = keychain(context.getItemInHand());
//                if (keychain.size() > 0) {
//                    ResourceKey<Level> tardis = keychain.get(0);
//                    var tardisLevel = Platform.getServer().levels.get(tardis);
//                    TardisLevelOperator.get(tardisLevel).ifPresent(cap -> {
//                        if (cap.getControlManager().isInFlight()) {
//                            cap.getControlManager().setTargetPosition(context.getClickedPos().above());
//                            cap.getControlManager().getTargetLocation().rotation = context.getHorizontalDirection().getOpposite();
//                            cap.getControlManager().endFlight();
//                            PlayerUtil.sendMessage(context.getPlayer(), "TARDIS LANDING", true);
//                        } else {
//                            cap.getControlManager().beginFlight();
//                            PlayerUtil.sendMessage(context.getPlayer(), "TARDIS TAKING OFF", true);
//                        }
//                    });
//                }
//            }
//        }
//
//        return super.useOn(context);
//    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);

        ArrayList<ResourceKey<Level>> keychain = KeyItem.keychain(itemStack);

        if(!keychain.isEmpty()) {
            list.add(Component.translatable(ModMessages.TOOLTIP_TARDIS_LIST_TITLE));

            for (ResourceKey<Level> resourceKey : keychain) {
                MutableComponent hyphen = Component.literal(ChatFormatting.YELLOW + "- ");
                list.add(hyphen.append(Component.literal(resourceKey.location().getPath().substring(0, 5))));
            }

        }
    }
}
