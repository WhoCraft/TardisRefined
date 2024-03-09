package whocraft.tardis_refined.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.blockentity.device.AstralManipulatorBlockEntity;
import whocraft.tardis_refined.constants.ModMessages;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.ItemRegistry;
import whocraft.tardis_refined.registry.SoundRegistry;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScrewdriverItem extends Item implements DyeableLeatherItem {

    // Constants
    public static final String SCREWDRIVER_MODE = "screwdriver_mode";
    public static final String LINKED_MANIPULATOR_POS = "linked_manipulator_pos";
    public static final String SCREWDRIVER_POINT_A = "screwdriver_point_a";
    public static final String SCREWDRIVER_POINT_B = "screwdriver_point_b";
    public static final String SCREWDRIVER_B_WAS_LAST_UPDATED = "screwdriver_b_was_last_updated_pos";

    public ScrewdriverItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getColor(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTagElement("display");
        return compoundTag != null && compoundTag.contains("color", 99) ? compoundTag.getInt("color") : DyeColor.PINK.getTextColor();
    }

    public static ItemStack forceColor(ItemStack itemStack, int color){
        itemStack.getOrCreateTagElement("display").putInt("color", color);
        return itemStack;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        if (context.getLevel() instanceof ServerLevel serverLevel) {

            if (context.getPlayer().isCrouching()) {
                setScrewdriverMode(context.getItemInHand(), ScrewdriverMode.DISABLED, context.getClickedPos(), serverLevel);
            } else {

                if (isScrewdriverMode(context.getItemInHand(), ScrewdriverMode.DRAWING) && context.getLevel().getBlockState(context.getClickedPos()).getBlock() != BlockRegistry.ASTRAL_MANIPULATOR_BLOCK.get()) {
                    addBlockPosToScrewdriver(serverLevel, context.getPlayer(), context.getItemInHand(), context.getClickedPos());
                }
            }
        }


        return super.useOn(context);
    }

    public void setScrewdriverMode(ItemStack stack, ScrewdriverMode mode, BlockPos sourceChange, @Nullable ServerLevel serverLevel) {
        CompoundTag itemtag = stack.getOrCreateTag();

        if (itemtag.contains(SCREWDRIVER_MODE)) {
            ScrewdriverMode currentMode = ScrewdriverMode.valueOf(itemtag.getString(SCREWDRIVER_MODE));


            if (currentMode != ScrewdriverMode.DISABLED && mode == ScrewdriverMode.DISABLED) {
                if (serverLevel != null) {
                    playScrewdriverSound(serverLevel, sourceChange, SoundRegistry.SCREWDRIVER_DISCARD.get());
                }
            }

            if (currentMode == ScrewdriverMode.DRAWING && mode == ScrewdriverMode.DISABLED) {

                if (serverLevel != null) {
                    clearLinkedManipulator(serverLevel, stack);
                }


            }
        }

        itemtag.putString(SCREWDRIVER_MODE, mode.toString());
        if (mode == ScrewdriverMode.DRAWING) {
            itemtag.put(LINKED_MANIPULATOR_POS, NbtUtils.writeBlockPos(sourceChange));
        }

        stack.setTag(itemtag);
    }

    public boolean isScrewdriverMode(ItemStack stack, ScrewdriverMode mode) {
        CompoundTag itemtag = stack.getOrCreateTag();

        if (itemtag.contains(SCREWDRIVER_MODE)) {
            ScrewdriverMode currentMode = ScrewdriverMode.valueOf(itemtag.getString(SCREWDRIVER_MODE));
            return mode == currentMode;
        }

        return false;
    }

    private void addBlockPosToScrewdriver(ServerLevel serverLevel, Player player, ItemStack stack, BlockPos pos) {
        CompoundTag itemtag = stack.getOrCreateTag();


        boolean isUpdatingA = true;
        String target = SCREWDRIVER_POINT_A;

        if (itemtag.contains(SCREWDRIVER_B_WAS_LAST_UPDATED)) {
            isUpdatingA = itemtag.getBoolean(SCREWDRIVER_B_WAS_LAST_UPDATED);

            if (!isUpdatingA) {
                target = SCREWDRIVER_POINT_B;
            }

        }

        itemtag.put(target, NbtUtils.writeBlockPos(pos));
        updatedLinkedManipulator((ServerLevel) player.level(), stack, pos, isUpdatingA);

        itemtag.putBoolean(SCREWDRIVER_B_WAS_LAST_UPDATED, !isUpdatingA);
        stack.setTag(itemtag);

        playScrewdriverSound(serverLevel, player.getOnPos(), SoundRegistry.SCREWDRIVER_SHORT.get());
    }

    public void playScrewdriverSound(ServerLevel level, BlockPos pos, SoundEvent soundEvent) {

        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), soundEvent, SoundSource.PLAYERS, 1, 0.875f + level.getRandom().nextFloat() / 4);
    }

    private void updatedLinkedManipulator(ServerLevel level, ItemStack stack, BlockPos pos, boolean isPointA) {
        CompoundTag itemtag = stack.getOrCreateTag();
        if (itemtag.contains(LINKED_MANIPULATOR_POS)) {
            BlockPos manipulator = NbtUtils.readBlockPos(itemtag.getCompound(LINKED_MANIPULATOR_POS));
           if ( level.getBlockEntity(manipulator) instanceof AstralManipulatorBlockEntity astralManipulatorBlockEntity) {

               if (!astralManipulatorBlockEntity.setProjectionBlockPos(pos, isPointA)) {
                   setScrewdriverMode(stack, ScrewdriverMode.DISABLED, pos, level);
               }

           }

        }
    }

    private void clearLinkedManipulator(ServerLevel level, ItemStack stack) {
        CompoundTag itemtag = stack.getOrCreateTag();
        if (itemtag.contains(LINKED_MANIPULATOR_POS)) {
            BlockPos manipulator = NbtUtils.readBlockPos(itemtag.getCompound(LINKED_MANIPULATOR_POS));
            if ( level.getBlockEntity(manipulator) instanceof AstralManipulatorBlockEntity astralManipulatorBlockEntity) {
                astralManipulatorBlockEntity.clearDisplay();
            }

            itemtag.remove(LINKED_MANIPULATOR_POS);
        }
    }

    public void clearBlockPosFromScrewdriver(ItemStack stack) {
        CompoundTag itemtag = stack.getOrCreateTag();
        if (itemtag.contains(SCREWDRIVER_POINT_A)) {
            itemtag.remove(SCREWDRIVER_POINT_A);
        }

        if (itemtag.contains(SCREWDRIVER_POINT_B)) {
            itemtag.remove(SCREWDRIVER_POINT_B);
        }

        if (itemtag.contains(LINKED_MANIPULATOR_POS)) {
            itemtag.remove(LINKED_MANIPULATOR_POS);
        }

        stack.setTag(itemtag);
    }

    public List<BlockPos> getScrewdriverPoint(ItemStack stack) {
        CompoundTag itemtag = stack.getOrCreateTag();
        List<BlockPos> listOfBlockPos = new ArrayList<BlockPos>();

        if (itemtag.contains(SCREWDRIVER_POINT_A)) {
            listOfBlockPos.add(NbtUtils.readBlockPos(itemtag.getCompound(SCREWDRIVER_POINT_A)));
        }

        if (itemtag.contains(SCREWDRIVER_POINT_B)) {
            listOfBlockPos.add(NbtUtils.readBlockPos(itemtag.getCompound(SCREWDRIVER_POINT_B)));
        }

        return listOfBlockPos;
    }


    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);

        list.add(Component.translatable(ModMessages.TOOLTIP_SCREWDRIVER_DESCRIPTION));
    }
}

