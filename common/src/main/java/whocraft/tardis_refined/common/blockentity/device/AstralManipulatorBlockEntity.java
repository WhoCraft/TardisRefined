package whocraft.tardis_refined.common.blockentity.device;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import whocraft.tardis_refined.common.crafting.ManipulatorCrafting;
import whocraft.tardis_refined.common.items.ScrewdriverItem;
import whocraft.tardis_refined.common.items.ScrewdriverMode;
import whocraft.tardis_refined.constants.NbtConstants;
import whocraft.tardis_refined.patterns.ConsolePatterns;
import whocraft.tardis_refined.registry.BlockEntityRegistry;
import whocraft.tardis_refined.registry.BlockRegistry;
import whocraft.tardis_refined.registry.SoundRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AstralManipulatorBlockEntity extends BlockEntity {

    // Constants
    public static final String MAN_POINT_A = "man_point_a";
    public static final String MAN_POINT_B = "man_point_b";
    public static final String SHOULD_DISPLAY = "should_display";


    private boolean shouldDisplay = false;
    private BlockPos pointABlockPos;
    private BlockPos pointBBlockPos;

    public AstralManipulatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.ASTRAL_MANIPULATOR.get(), blockPos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);

        compoundTag.putBoolean(SHOULD_DISPLAY, this.shouldDisplay);

        if (this.pointABlockPos != null) {
            compoundTag.put(MAN_POINT_A, NbtUtils.writeBlockPos(this.pointABlockPos));
        }

        if (this.pointBBlockPos != null) {
            compoundTag.put(MAN_POINT_B, NbtUtils.writeBlockPos(this.pointBBlockPos));

        }
    }

    public BlockPos getPointABlockPos() {
        return this.pointABlockPos;
    }

    public BlockPos getPointBBlockPos() {
        return this.pointBBlockPos;
    }

    public void setShouldDisplay(boolean display) {
        this.shouldDisplay = display;
        this.pointABlockPos = this.getBlockPos();
        this.pointBBlockPos = this.getBlockPos();
        sendUpdates();
    }

    public boolean shouldDisplay() {

        return shouldDisplay;
    }

    public void clearDisplay() {
        this.shouldDisplay = false;
        this.pointABlockPos = this.getBlockPos();
        this.pointBBlockPos = this.getBlockPos();
        sendUpdates();
    }

    @Override
    public void load(CompoundTag tag) {

        if (tag.contains(MAN_POINT_A)) {
            this.pointABlockPos = NbtUtils.readBlockPos(tag.getCompound(MAN_POINT_A));
        }
        if (tag.contains(MAN_POINT_B)) {
            this.pointBBlockPos = NbtUtils.readBlockPos(tag.getCompound(MAN_POINT_B));
        }

        if (tag.contains(SHOULD_DISPLAY)) {
            this.shouldDisplay = tag.getBoolean(SHOULD_DISPLAY);
        }

        super.load(tag);
    }


    public void OnRightClick(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ScrewdriverItem screwdriverItem) {
            if (!screwdriverItem.isScrewdriverMode(itemStack, ScrewdriverMode.DRAWING)) {
                screwdriverItem.setScrewdriverMode(itemStack, ScrewdriverMode.DRAWING, getBlockPos(), null);
                setShouldDisplay(true);
                screwdriverItem.playScrewdriverSound((ServerLevel) getLevel(), getBlockPos(), SoundRegistry.SCREWDRIVER_CONNECT.get());

            } else {

                var points = screwdriverItem.getScrewdriverPoint(itemStack);
                screwdriverItem.setScrewdriverMode(itemStack, ScrewdriverMode.DISABLED, getBlockPos(), null);

                if (points.size() == 2) {
                    var pointA = points.get(0);
                    var pointB = points.get(1);

                    attemptToBuild(pointA, pointB);
                    screwdriverItem.clearBlockPosFromScrewdriver(itemStack);
                    this.clearDisplay();
                }

                this.clearDisplay();
            }
        }
    }

    public boolean setBlockPos(BlockPos pos, boolean isPointA) {

        if (!this.shouldDisplay) {
            return false;
        }

        if (isPointA) {
            pointABlockPos = pos;
        } else {
            pointBBlockPos = pos;
        }

        if (checkIfDistanceIsTooGreat(pos)) {
            this.clearDisplay();
            sendUpdates();
            return false;

        }

        sendUpdates();
        return true;
    }

    private boolean checkIfDistanceIsTooGreat(BlockPos pos) {
        var distance = pos.distManhattan(getBlockPos());
        System.out.println(distance);
        return distance > 25;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void sendUpdates() {
        level.sendBlockUpdated(this.getBlockPos(), level.getBlockState(this.getBlockPos()), level.getBlockState(this.getBlockPos()), Block.UPDATE_CLIENTS);
        setChanged();
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }


    private void attemptToBuild(BlockPos pointA, BlockPos pointB) {

        var submittedBlocks = new ArrayList<ManipulatorCraftingRecipeItem>();

        // Grab the absolutes for these items.
        var xDiff = Math.abs(pointA.getX() - pointB.getX());
        var yDiff = Math.abs(pointA.getY() - pointB.getY());
        var zDiff = Math.abs(pointA.getZ() - pointB.getZ());

        var smallestPointX = pointA.getX() > pointB.getX() ? pointB.getX() : pointA.getX();
        var smallestPointY = pointA.getY() > pointB.getY() ? pointB.getY() : pointA.getY();
        var smallestPointZ = pointA.getZ() > pointB.getZ() ? pointB.getZ() : pointA.getZ();

        var minPoint = new BlockPos(smallestPointX, smallestPointY, smallestPointZ);

        for (int y = 0; y < yDiff + 1; y++) {

            for (int x = 0; x < xDiff + 1; x++) {

                for (int z = 0; z < zDiff + 1; z++) {

                    var blockPosInWorld = new BlockPos(minPoint.getX() + x, minPoint.getY() + y, minPoint.getZ() + z);

                    submittedBlocks.add(new ManipulatorCraftingRecipeItem(new BlockPos(x, y, z), level.getBlockState(blockPosInWorld).getBlock()));
                }
            }
        }

        // Filter recipes by the first block, will make it a LOT easier later down the line. (when I make it)
        var firstBlock = submittedBlocks.stream().filter(x -> x.relativeBlockPos.getX() == 0 && x.relativeBlockPos.getY() == 0 && x.relativeBlockPos.getZ() == 0).findFirst();
        if (!firstBlock.isPresent()) {
            return;
        }

        var possibleRecipes = new ArrayList<ManipulatorCraftingRecipe>();

        for (var recipe : ManipulatorCrafting.MANIPULATOR_CRAFTING_RECIPES) {

            var zeroPos = recipe.itemList.stream().filter(x -> x.relativeBlockPos.getX() == 0 && x.relativeBlockPos.getY() == 0 && x.relativeBlockPos.getZ() == 0).findFirst();
            if (zeroPos.isPresent()) {
                if (zeroPos.get().block == firstBlock.get().block) {
                    possibleRecipes.add(recipe);
                }
            }

        }

        // No recipes, so no result!
        if (possibleRecipes.size() == 0) {
            return;
        }

        for (var recipe : possibleRecipes) {

            if (recipe.hasSameItems(submittedBlocks)) {

                // Remove the recipe blocks.
                for (var blockPos : BlockPos.betweenClosed(pointA, pointB)) {
                    level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                }

                List<ItemEntity> droppedItems = getLevel().getEntitiesOfClass(ItemEntity.class, new AABB(pointABlockPos, pointBBlockPos).inflate(1));
                droppedItems.forEach(Entity::discard);

                // TODO: Make this system also accept placing a structure.
                ItemStack itemStack = new ItemStack(recipe.recipeOutput.asItem());
                ItemEntity item = new ItemEntity(level, getBlockPos().getX() + 0.5f, getBlockPos().getY() + 1, getBlockPos().getZ() + 0.5f, itemStack);
                level.addFreshEntity(item);

                return;
            }

        }
    }
}
