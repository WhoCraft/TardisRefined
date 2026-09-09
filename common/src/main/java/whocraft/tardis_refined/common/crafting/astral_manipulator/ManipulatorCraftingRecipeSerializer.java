package whocraft.tardis_refined.common.crafting.astral_manipulator;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.TardisRefined;

/**
 * The recipe serializer implementation.
 * <br> This allows vanilla to automatically add our recipe types to its recipe packet entry and reload listener.
 */
public class ManipulatorCraftingRecipeSerializer implements RecipeSerializer<ManipulatorCraftingRecipe> {

    public static final ResourceLocation SERIALIZER_ID = ResourceLocation.fromNamespaceAndPath(TardisRefined.MODID, "astral_manipulator");

    public static Logger LOGGER = LogManager.getLogger("TardisRefined/ManipulatorCraftingRecipeSerializer");


    public ManipulatorCraftingRecipeSerializer() {
    }

    @Override
    public @NotNull MapCodec<ManipulatorCraftingRecipe> codec() {
        return ManipulatorCraftingRecipe.CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, ManipulatorCraftingRecipe> streamCodec() {
        return ByteBufCodecs.fromCodecWithRegistries(ManipulatorCraftingRecipe.CODEC.codec());
    }
}
