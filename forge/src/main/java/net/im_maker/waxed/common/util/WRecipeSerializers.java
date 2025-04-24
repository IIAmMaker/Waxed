package net.im_maker.waxed.common.util;

import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.util.custom_recipes.WaxingBlocksRecipe;
import net.im_maker.waxed.common.util.custom_recipes.WaxingBlocksWithWaxRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Waxed.MOD_ID);

    public static final RegistryObject<RecipeSerializer<?>> WAXING_BLOCKS_WITH_WAX =
            RECIPE_SERIALIZERS.register("waxing_blocks_with_wax", () -> WaxingBlocksWithWaxRecipe.SERIALIZER);

    public static final RegistryObject<RecipeSerializer<?>> WAXING_BLOCKS =
            RECIPE_SERIALIZERS.register("waxing_blocks", () -> WaxingBlocksRecipe.SERIALIZER);
}