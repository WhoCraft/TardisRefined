package whocraft.tardis_refined.common.items;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.client.model.pallidium.BedrockModelUtil;
import whocraft.tardis_refined.common.block.device.ConsoleConfigurationBlock;
import whocraft.tardis_refined.common.blockentity.device.AstralManipulatorBlockEntity;
import whocraft.tardis_refined.common.util.PlayerUtil;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.TRBlockRegistry;
import whocraft.tardis_refined.registry.TRSoundRegistry;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.item.component.TooltipDisplay;

public class ScrewdriverItem extends Item {


    private static CompoundTag readCustomTag(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

    private static void writeCustomTag(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    private static CompoundTag writeBlockPos(BlockPos pos) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("X", pos.getX());
        tag.putInt("Y", pos.getY());
        tag.putInt("Z", pos.getZ());
        return tag;
    }

    private static BlockPos readBlockPos(CompoundTag tag) {
        return new BlockPos(tag.getIntOr("X", 0), tag.getIntOr("Y", 0), tag.getIntOr("Z", 0));
    }

    // Constants
    public static final String SCREWDRIVER_MODE = "screwdriver_mode";
    public static final String LINKED_MANIPULATOR_POS = "linked_manipulator_pos";
    public static final String SCREWDRIVER_POINT_A = "screwdriver_point_a";
    public static final String SCREWDRIVER_POINT_B = "screwdriver_point_b";
    public static final String SCREWDRIVER_B_WAS_LAST_UPDATED = "screwdriver_b_was_last_updated_pos";

    public ScrewdriverItem(Properties properties) {
        super(properties);
    }

    public static ItemStack forceColor(ItemStack itemStack, int color) {
        itemStack.set(DataComponents.DYED_COLOR, new DyedItemColor(color));
        return itemStack;
    }

    public int getColor(ItemStack itemStack) {
        return DyedItemColor.getOrDefault(itemStack, DyeColor.PINK.getTextColor());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        if (!(context.getLevel() instanceof ServerLevel serverLevel)) {
            return super.useOn(context);
        }

        var player = context.getPlayer();
        var itemInHand = context.getItemInHand();
        var clickedPos = context.getClickedPos();
        var blockState = context.getLevel().getBlockState(clickedPos);

        if (!player.isCrouching()
                && !isScrewdriverMode(itemInHand, ScrewdriverMode.DRAWING)
                && blockState.getBlock() instanceof ConsoleConfigurationBlock configurator) {
            TardisRefined.LOGGER.info("Amethyst screwdriver used on console configurator: player={}, dimension={}, position={}, mode={}",
                    player.getGameProfile().name(), serverLevel.dimension().identifier(), clickedPos,
                    readCustomTag(itemInHand).getStringOr(SCREWDRIVER_MODE, "default"));
            return configurator.useScrewdriver(serverLevel, clickedPos, player);
        }

        if (player.isCrouching()) {
            ScrewdriverMode newMode = isScrewdriverMode(itemInHand, ScrewdriverMode.ENABLED)
                    ? ScrewdriverMode.DISABLED
                    : ScrewdriverMode.ENABLED;
            setScrewdriverMode(context.getPlayer(), itemInHand, newMode, clickedPos, serverLevel);
        } else if (isScrewdriverMode(itemInHand, ScrewdriverMode.DRAWING)
                && blockState.getBlock() != TRBlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get()) {
            addBlockPosToScrewdriver(serverLevel, player, itemInHand, clickedPos);
        }

        return super.useOn(context);
    }


    public void setScrewdriverMode(Player player, ItemStack stack, ScrewdriverMode mode, BlockPos sourceChange, @Nullable ServerLevel serverLevel) {
        CompoundTag itemTag = readCustomTag(stack);
        ScrewdriverMode currentMode = itemTag.contains(SCREWDRIVER_MODE)
                ? ScrewdriverMode.valueOf(itemTag.getStringOr(SCREWDRIVER_MODE, ""))
                : ScrewdriverMode.DISABLED;

        if (serverLevel != null) {
            if (currentMode != ScrewdriverMode.DISABLED && mode == ScrewdriverMode.DISABLED) {
                // Play the disabled sound when transitioning to DISABLED mode
                playScrewdriverSound(serverLevel, sourceChange, TRSoundRegistry.SCREWDRIVER_DISCARD.get());
            }
            if (currentMode == ScrewdriverMode.DRAWING && mode != ScrewdriverMode.DRAWING) {
                // Clear manipulator if leaving DRAWING mode
                clearLinkedManipulator(serverLevel, stack);
            }
        }

        // Update the mode in the item's NBT
        itemTag.putString(SCREWDRIVER_MODE, mode.toString());

        if (mode == ScrewdriverMode.DRAWING) {
            // Save manipulator position for DRAWING mode
            itemTag.put(LINKED_MANIPULATOR_POS, writeBlockPos(sourceChange));
        }

        Block sourceBlock = player.level().getBlockState(sourceChange).getBlock();
        if(sourceBlock != null && sourceBlock != TRBlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get()) {
            PlayerUtil.sendMessage(player, mode.toString(), true);
        }
        writeCustomTag(stack, itemTag);
    }


    public boolean isScrewdriverMode(ItemStack stack, ScrewdriverMode mode) {
        CompoundTag itemtag = readCustomTag(stack);

        if (itemtag.contains(SCREWDRIVER_MODE)) {
            ScrewdriverMode currentMode = ScrewdriverMode.valueOf(itemtag.getStringOr(SCREWDRIVER_MODE, ""));
            return mode == currentMode;
        }

        return false;
    }

    private void addBlockPosToScrewdriver(ServerLevel serverLevel, Player player, ItemStack stack, BlockPos pos) {
        CompoundTag itemtag = readCustomTag(stack);


        boolean isUpdatingA = true;
        String target = SCREWDRIVER_POINT_A;

        if (itemtag.contains(SCREWDRIVER_B_WAS_LAST_UPDATED)) {
            isUpdatingA = itemtag.getBooleanOr(SCREWDRIVER_B_WAS_LAST_UPDATED, false);

            if (!isUpdatingA) {
                target = SCREWDRIVER_POINT_B;
            }

        }

        itemtag.put(target, writeBlockPos(pos));
        updatedLinkedManipulator(player, (ServerLevel) player.level(), stack, pos, isUpdatingA);

        itemtag.putBoolean(SCREWDRIVER_B_WAS_LAST_UPDATED, !isUpdatingA);
        writeCustomTag(stack, itemtag);

        playScrewdriverSound(serverLevel, player.getOnPos(), TRSoundRegistry.SCREWDRIVER_SHORT.get());
    }

    public void playScrewdriverSound(ServerLevel level, BlockPos pos, SoundEvent soundEvent) {
        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), soundEvent, SoundSource.PLAYERS, 1, 0.875f + level.getRandom().nextFloat() / 4);
    }

    private void updatedLinkedManipulator(Player player, ServerLevel level, ItemStack stack, BlockPos pos, boolean isPointA) {
        CompoundTag itemtag = readCustomTag(stack);
        if (itemtag.contains(LINKED_MANIPULATOR_POS)) {
            BlockPos manipulator = readBlockPos(itemtag.getCompoundOrEmpty(LINKED_MANIPULATOR_POS));
            if (level.getBlockEntity(manipulator) instanceof AstralManipulatorBlockEntity astralManipulatorBlockEntity) {

                if (!astralManipulatorBlockEntity.setProjectionBlockPos(pos, isPointA)) {
                    setScrewdriverMode(player, stack, ScrewdriverMode.DISABLED, pos, level);
                }

            }
        }
    }

    private void clearLinkedManipulator(ServerLevel level, ItemStack stack) {
        CompoundTag itemtag = readCustomTag(stack);
        if (itemtag.contains(LINKED_MANIPULATOR_POS)) {
            BlockPos manipulator = readBlockPos(itemtag.getCompoundOrEmpty(LINKED_MANIPULATOR_POS));
            if (level.getBlockEntity(manipulator) instanceof AstralManipulatorBlockEntity astralManipulatorBlockEntity) {
                astralManipulatorBlockEntity.clearDisplay();
            }

            itemtag.remove(LINKED_MANIPULATOR_POS);
        }
    }

    public void clearBlockPosFromScrewdriver(ItemStack stack) {
        CompoundTag itemtag = readCustomTag(stack);
        if (itemtag.contains(SCREWDRIVER_POINT_A)) {
            itemtag.remove(SCREWDRIVER_POINT_A);
        }

        if (itemtag.contains(SCREWDRIVER_POINT_B)) {
            itemtag.remove(SCREWDRIVER_POINT_B);
        }

        if (itemtag.contains(LINKED_MANIPULATOR_POS)) {
            itemtag.remove(LINKED_MANIPULATOR_POS);
        }

        writeCustomTag(stack, itemtag);
    }

    public List<BlockPos> getScrewdriverPoint(ItemStack stack) {
        CompoundTag itemtag = readCustomTag(stack);
        List<BlockPos> listOfBlockPos = new ArrayList<BlockPos>();

        if (itemtag.contains(SCREWDRIVER_POINT_A)) {
            listOfBlockPos.add(readBlockPos(itemtag.getCompoundOrEmpty(SCREWDRIVER_POINT_A)));
        }

        if (itemtag.contains(SCREWDRIVER_POINT_B)) {
            listOfBlockPos.add(readBlockPos(itemtag.getCompoundOrEmpty(SCREWDRIVER_POINT_B)));
        }

        return listOfBlockPos;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, tooltip, tooltipFlag);
        tooltip.accept(Component.translatable(ModMessages.TOOLTIP_SCREWDRIVER_DESCRIPTION));
    }
}
