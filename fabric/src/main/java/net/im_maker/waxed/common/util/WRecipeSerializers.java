package net.im_maker.waxed.common.util;

import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.util.custom_recipes.WaxingBlocksRecipe;
import net.im_maker.waxed.common.util.custom_recipes.WaxingBlocksWithWaxRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class WRecipeSerializers {
    public static final RecipeSerializer<WaxingBlocksWithWaxRecipe> WAXING_BLOCKS_WITH_WAX =
            registerRecipeSerializer("waxing_blocks_with_wax", WaxingBlocksWithWaxRecipe.SERIALIZER);

    public static final RecipeSerializer<WaxingBlocksRecipe> WAXING_BLOCKS =
            registerRecipeSerializer("waxing_blocks", WaxingBlocksRecipe.SERIALIZER);

    private static <T extends RecipeSerializer<?>> T registerRecipeSerializer(String name, T serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(Waxed.MOD_ID, name), serializer);
    }

    public static void registerRecipeSerializers() {
        Waxed.LOGGER.info("Registering Recipe Serializers for " + Waxed.MOD_ID);
    }
}