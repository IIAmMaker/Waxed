package net.im_maker.waxed.common.util.custom_recipes;

import net.im_maker.waxed.common.player.interactions.WaxingBlocks;
import net.im_maker.waxed.common.util.WTags;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class WaxingBlocksRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<WaxingBlocksRecipe> SERIALIZER =
            new SimpleCraftingRecipeSerializer<>(WaxingBlocksRecipe::new);

    public WaxingBlocksRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        int blockCount = 0;
        int waxCount = 0;
        Block targetBlock = null;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty()) continue;

            if (stack.is(WTags.Items.CAN_WAX)) {
                waxCount++;
            } else {
                Block block = Block.byItem(stack.getItem());
                if (WaxingBlocks.WAXABLES_SHOVEL.get().containsKey(block) ||
                        WaxingBlocks.WAXABLES_AXE.get().containsKey(block)) {
                    blockCount++;
                    targetBlock = block;
                } else {
                    return false; // Non-waxable item found
                }
            }
        }

        return blockCount == 1 && waxCount == 1 && targetBlock != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty()) continue;

            if (!stack.is(WTags.Items.CAN_WAX)) {
                Block block = Block.byItem(stack.getItem());

                // Check shovel waxables first, then axe waxables
                Block waxedBlock = WaxingBlocks.WAXABLES_SHOVEL.get().get(block);
                if (waxedBlock == null) {
                    waxedBlock = WaxingBlocks.WAXABLES_AXE.get().get(block);
                }

                if (waxedBlock != null) {
                    return new ItemStack(waxedBlock);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY; // Consistent with vanilla waxing recipes
    }
}