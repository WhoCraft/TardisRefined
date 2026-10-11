package whocraft.tardis_refined.common.crafting.astral_manipulator;

import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

/** Codec-backed serializer for the Astral Manipulator's custom recipe. */
public final class ManipulatorCraftingRecipeSerializer {
    private ManipulatorCraftingRecipeSerializer() {}

    public static final RecipeSerializer<ManipulatorCraftingRecipe> INSTANCE = new RecipeSerializer<>(
            ManipulatorCraftingRecipe.CODEC,
            StreamCodec.of(ManipulatorCraftingRecipeSerializer::write, ManipulatorCraftingRecipeSerializer::read)
    );

    private static void write(RegistryFriendlyByteBuf buffer, ManipulatorCraftingRecipe recipe) {
        buffer.writeNbt(ManipulatorCraftingRecipe.CODEC.codec()
                .encodeStart(NbtOps.INSTANCE, recipe)
                .getOrThrow());
    }

    private static ManipulatorCraftingRecipe read(RegistryFriendlyByteBuf buffer) {
        return ManipulatorCraftingRecipe.CODEC.codec()
                .parse(NbtOps.INSTANCE, buffer.readNbt())
                .getOrThrow();
    }
}
