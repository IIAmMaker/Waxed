package net.im_maker.waxed.common.util.jei;

import mezz.jei.api.constants.ModIds;
import net.im_maker.waxed.common.item.WItems;
import net.im_maker.waxed.common.player.interactions.WaxingBlocks;
import net.im_maker.waxed.common.util.WTags;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class WaxingRecipeMaker {
    private static final String WAXING_GROUP = "jei.waxed.waxing";
    private static final String WAXING_WITH_WAX_GROUP = "jei.waxed.waxing_with_wax";

    public static List<CraftingRecipe> createWaxingRecipes() {
        List<CraftingRecipe> recipes = new ArrayList<>();

        // Get the can_wax tag
        TagKey<Item> canWaxTag = WTags.Items.CAN_WAX;
        Ingredient waxIngredient = Ingredient.of(canWaxTag);

        // Add recipes for waxing with tools (using can_wax tag)
        addWaxingRecipes(recipes, WaxingBlocks.WAXABLES_SHOVEL.get(), WAXING_GROUP, waxIngredient);
        addWaxingRecipes(recipes, WaxingBlocks.WAXABLES_AXE.get(), WAXING_GROUP, waxIngredient);

        // Add recipes for waxing with specific wax item
        Ingredient specificWaxIngredient = Ingredient.of(WItems.WAX);
        addWaxingWithWaxRecipes(recipes, WaxingBlocks.WAXABLES_MODDED_BLOCKS_WITH_COPPER.get(), WAXING_WITH_WAX_GROUP, specificWaxIngredient);

        return recipes;
    }

    private static void addWaxingRecipes(List<CraftingRecipe> recipes,
                                         Map<Block, Block> waxables,
                                         String group,
                                         Ingredient waxIngredient) {
        for (Map.Entry<Block, Block> entry : waxables.entrySet()) {
            Block inputBlock = entry.getKey();
            Block outputBlock = entry.getValue();

            ItemStack inputStack = new ItemStack(inputBlock.asItem());
            ItemStack outputStack = new ItemStack(outputBlock.asItem());

            Ingredient inputIngredient = Ingredient.of(inputStack);

            NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, inputIngredient, waxIngredient);

            ResourceLocation id = new ResourceLocation(
                    ModIds.MINECRAFT_ID,
                    group + "." + outputStack.getDescriptionId().toLowerCase().replace(" ", "_")
            );

            ShapelessRecipe recipe = new ShapelessRecipe(id, group, CraftingBookCategory.MISC, outputStack, inputs);
            recipes.add(recipe);
        }
    }

    private static void addWaxingWithWaxRecipes(List<CraftingRecipe> recipes,
                                                Map<Block, Block> waxables,
                                                String group,
                                                Ingredient waxIngredient) {
        for (Map.Entry<Block, Block> entry : waxables.entrySet()) {
            Block inputBlock = entry.getKey();
            Block outputBlock = entry.getValue();

            ItemStack inputStack = new ItemStack(inputBlock.asItem());
            ItemStack outputStack = new ItemStack(outputBlock.asItem());

            Ingredient inputIngredient = Ingredient.of(inputStack);

            NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, inputIngredient, waxIngredient);

            ResourceLocation id = new ResourceLocation(
                    ModIds.MINECRAFT_ID,
                    group + "." + outputStack.getDescriptionId().toLowerCase().replace(" ", "_")
            );

            ShapelessRecipe recipe = new ShapelessRecipe(id, group, CraftingBookCategory.MISC, outputStack, inputs);
            recipes.add(recipe);
        }
    }

    private WaxingRecipeMaker() {
    }
}