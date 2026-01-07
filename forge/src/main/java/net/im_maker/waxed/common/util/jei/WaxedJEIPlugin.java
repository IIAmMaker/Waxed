package net.im_maker.waxed.common.util.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.im_maker.waxed.config.WaxedAndShinyConfig;
import net.minecraft.resources.ResourceLocation;
import net.im_maker.waxed.Waxed;
import net.minecraft.world.item.crafting.CraftingRecipe;

import java.util.List;

@JeiPlugin
public class WaxedJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Waxed.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (WaxedAndShinyConfig.GENERATE_WAXING_RECIPES.get()) {
            List<CraftingRecipe> waxingRecipes = WaxingRecipeMaker.createWaxingRecipes();
            registration.addRecipes(RecipeTypes.CRAFTING, waxingRecipes);
        }
    }
}