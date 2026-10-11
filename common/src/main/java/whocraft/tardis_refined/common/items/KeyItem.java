package whocraft.tardis_refined.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.entity.ControlEntity;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.manager.TardisPilotingManager;
import whocraft.tardis_refined.common.util.DimensionUtil;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.registry.TRControlRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class KeyItem extends Item {


    private static CompoundTag readCustomTag(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

    private static void writeCustomTag(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    public KeyItem(Properties properties) {
        super(properties);
    }

    public static ItemStack addTardis(ItemStack itemStack, ResourceKey<Level> levelResourceKey) {
        // Get the tag of the itemStack object
        CompoundTag itemtag = readCustomTag(itemStack);

        StringTag dim = StringTag.valueOf(levelResourceKey.identifier().toString());

        ListTag keychain;
        if (!itemtag.contains(NbtConstants.KEYCHAIN)) {
            // Create a new keychain tag and add it to the itemtag object
            keychain = new ListTag();
            itemtag.put(NbtConstants.KEYCHAIN, keychain);
        } else {
            // Get the existing keychain tag from the itemtag object
            keychain = itemtag.getListOrEmpty(NbtConstants.KEYCHAIN);
        }

        // Add dim to the keychain, whether it already exists in the keychain
        keychain.add(dim);

        writeCustomTag(itemStack, itemtag);
        return itemStack;
    }

    public static void setKeychain(ItemStack itemStack, ArrayList<ResourceKey<Level>> levels) {
        CompoundTag nbt = readCustomTag(itemStack);
        ListTag keychain;
        if (!nbt.contains(NbtConstants.KEYCHAIN)) {
            // Create a new keychain tag and add it to the itemtag object
            keychain = new ListTag();
            nbt.put(NbtConstants.KEYCHAIN, keychain);
        } else {
            // Get the existing keychain tag from the itemtag object
            keychain = nbt.getListOrEmpty(NbtConstants.KEYCHAIN);
        }

        keychain.clear();

        levels.forEach(level -> {
            keychain.add(StringTag.valueOf(level.identifier().toString()));
        });

        writeCustomTag(itemStack, nbt);
    }

    public static ArrayList<ResourceKey<Level>> getKeychain(ItemStack itemStack) {
        CompoundTag nbt = readCustomTag(itemStack);


        if (!nbt.contains(NbtConstants.KEYCHAIN)) {
            return new ArrayList<>();
        }

        ListTag keychain = nbt.getListOrEmpty(NbtConstants.KEYCHAIN);

        ArrayList<ResourceKey<Level>> levels = new ArrayList<>();

        for (Tag tag : keychain) {
            String string = ((StringTag) tag).value();
            ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, Identifier.parse(string));
            levels.add(key);
        }

        // Return the list of ResourceKeys
        return levels;
    }

    public static boolean keychainContains(ItemStack itemStack, ResourceKey<Level> levelResourceKey) {
        ArrayList<ResourceKey<Level>> keychain = getKeychain(itemStack);
        return keychain.contains(levelResourceKey);
    }

    @Override
    public Component getName(ItemStack itemStack) {

        if (getKeychain(itemStack).size() >= 2) {
            return Component.translatable(ModMessages.ITEM_KEYCHAIN);
        }

        return super.getName(itemStack);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {

        // Whistle Easter Egg for InDev: https://youtu.be/IqQsL79UpMs?t=526
        boolean canUseWhistle = "96511168-1bb3-4ff0-a894-271e42606a39".equals(player.getStringUUID()) || Platform.isProduction();
        if (player.getOffhandItem().is(Items.GOAT_HORN) && !level.isClientSide() && canUseWhistle) {
            ArrayList<ResourceKey<Level>> keychain = KeyItem.getKeychain(player.getMainHandItem());
            if (!keychain.isEmpty()) {
                var tardisLevel = DimensionUtil.getLevel(keychain.get(0));
                var operatorOptional = TardisLevelOperator.get(tardisLevel);
                var pilotManager = operatorOptional.get().getPilotingManager();
                if (!operatorOptional.get().getPilotingManager().isInRecovery()) {
                    pilotManager.setTargetLocation(new TardisNavLocation(player.blockPosition(), player.getDirection().getOpposite(), (ServerLevel) player.level()));
                    pilotManager.beginFlight(true);
                }
            }
        }

        return super.use(level, player, interactionHand);
    }

    public boolean interactMonitor(ItemStack itemStack, Player player, ControlEntity controlEntity, InteractionHand interactionHand) {
        if (controlEntity.level() instanceof ServerLevel serverLevel) {
            ResourceKey<Level> tardis = serverLevel.dimension();
            if (controlEntity.controlSpecification() != null
                    && controlEntity.controlSpecification().control() != null) {
                if (controlEntity.controlSpecification().control() == TRControlRegistry.MONITOR.get()) {
                    return bindToTardis(itemStack, player, interactionHand, tardis);
                }
            }
        }

        return false;
    }

    /** Bind this key to a TARDIS once, preserving any existing keychain entries. */
    public static boolean bindToTardis(ItemStack itemStack, Player player, InteractionHand interactionHand,
                                       ResourceKey<Level> tardis) {
        if (keychainContains(itemStack, tardis)) return false;

        player.setItemInHand(interactionHand, addTardis(itemStack, tardis));
        PlayerUtil.sendMessage(player, Component.translatable(ModMessages.MSG_KEY_BOUND, tardis.identifier().getPath()), true);
        player.playSound(SoundEvents.PLAYER_LEVELUP, 1, 0.5F);
        return true;
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {

        if (context.getLevel() instanceof ServerLevel) {
            if (context.getPlayer().isShiftKeyDown()) {
                var keychain = getKeychain(context.getItemInHand());
                if (!keychain.isEmpty()) {
                    Collections.rotate(keychain.subList(0, keychain.size()), -1);
                    setKeychain(context.getItemInHand(), keychain);
                    PlayerUtil.sendMessage(context.getPlayer(), Component.translatable(ModMessages.MSG_KEY_CYCLED, keychain.get(0).identifier().getPath()), true);
                    context.getLevel().playSound(null, context.getPlayer().blockPosition(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.BLOCKS, 1, 2);
                }
            }
        }


        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, tooltip, tooltipFlag);

        ArrayList<ResourceKey<Level>> keychain = KeyItem.getKeychain(itemStack);

        if (!keychain.isEmpty()) {

            ResourceKey<Level> mainTardisLevel = keychain.get(0);

            if (TardisClientData.getInstance(mainTardisLevel).isFlying()) {
                tooltip.accept(Component.translatable(ModMessages.TOOLTIP_IN_FLIGHT));
            }

            tooltip.accept(Component.translatable(ModMessages.TOOLTIP_TARDIS_LIST_TITLE));

            for (int i = 0; i < keychain.size(); i++) {
                MutableComponent hyphen = Component.literal((i == 0) ? ChatFormatting.YELLOW + "> " : ChatFormatting.GRAY + "- ");
                tooltip.accept(hyphen.append(Component.literal(keychain.get(i).identifier().getPath().substring(0, 5))));
            }


            TardisClientData tardisClientData = TardisClientData.getInstance(mainTardisLevel);
            if (tardisClientData.isInRecovery()) {
                int cooldownTicks = tardisClientData.getRecoveryTicks();
                int maxCooldownTicks = TardisPilotingManager.TICKS_COOLDOWN_MAX;
                int percentage = (int) ((cooldownTicks / (float) maxCooldownTicks) * 100);
                tooltip.accept(Component.translatable(ModMessages.RECOVERY_PROGRESS, percentage + "%"));
            }

        }
    }
}
