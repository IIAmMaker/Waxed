package net.im_maker.waxed.datagen;

import com.github.alexthe668.iwannaskate.server.item.IWSItemRegistry;
import com.ninni.dye_depot.registry.DDDyes;
import com.teamabnormals.buzzier_bees.core.registry.BBBlocks;
import com.teamabnormals.caverns_and_chasms.core.other.tags.CCItemTags;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import com.teamabnormals.endergetic.core.other.tags.EEItemTags;
import com.teamabnormals.upgrade_aquatic.core.registry.UABlocks;
import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.item.WItems;
import net.im_maker.waxed.common.util.WTags;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fml.ModList;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> EMPTY_HONEYCOMB = List.of(WBlocks.EMPTY_HONEYCOMB.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    protected static void waxRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        HoneycombItem.WAXABLES.get().forEach((block1, shapeless) -> {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, shapeless)
                    .requires(block1).requires(WItems.WAX.get())
                    .group(getItemName(shapeless)).
                    unlockedBy(getHasName(block1), has(block1))
                    .save(pFinishedRecipeConsumer, getConversionRecipeName(shapeless, WItems.WAX.get()));
        });
    }

    private void waxBlock(ItemLike waxBlock, ItemLike dye, Consumer consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, waxBlock, 8)
                .pattern("WWW")
                .pattern("WDW")
                .pattern("WWW")
                .define('W', WBlocks.WAX_BLOCK.get())
                .define('D', dye)
                .unlockedBy(getHasName(WBlocks.WAX_BLOCK.get()), has(WBlocks.WAX_BLOCK.get()))
                .group("wax_blocks")
                .save(consumer);
    }

    private void waxingBlock(ItemLike waxed, ItemLike unWaxed, Consumer consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, waxed, 1)
                .requires(unWaxed)
                .requires(WTags.Items.CAN_WAX)
                .unlockedBy(getHasName(unWaxed), has(unWaxed))
                .save(consumer, new ResourceLocation("waxed",waxed.asItem() + "_from_wax"));
    }

    private void waxBlock(ItemLike waxBlock, TagKey<Item> dye, Consumer consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, waxBlock, 8)
                .pattern("WWW")
                .pattern("WDW")
                .pattern("WWW")
                .define('W', WBlocks.WAX_BLOCK.get())
                .define('D', dye)
                .unlockedBy(getHasName(WBlocks.WAX_BLOCK.get()), has(WBlocks.WAX_BLOCK.get()))
                .group("wax_blocks")
                .save(consumer);
    }

    private void waxPillar(ItemLike waxBlock, ItemLike waxPillar, Consumer consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, waxPillar, 2)
                .pattern("W")
                .pattern("W")
                .define('W', waxBlock)
                .unlockedBy(getHasName(waxBlock), has(waxBlock))
                .group("wax_pillars")
                .save(consumer);
    }

    private void tallCandle(ItemLike tallCandle, ItemLike candle, Consumer consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, tallCandle, 1)
                .pattern("C")
                .pattern("C")
                .define('C', candle)
                .unlockedBy(getHasName(candle), has(candle))
                .group("tall_candles")
                .save(consumer);
    }

    private void waxedBlock(ItemLike waxedBlock, ItemLike unWaxedBlock, Consumer consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, waxedBlock)
                .requires(unWaxedBlock)
                .requires(WTags.Items.CAN_WAX)
                .unlockedBy(getHasName(unWaxedBlock), has(unWaxedBlock))
                .save(consumer);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        smelting(pWriter, EMPTY_HONEYCOMB, RecipeCategory.MISC, WBlocks.WAX_BLOCK.get(), 0.25f, 200, "wax_block");
        waxRecipes(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.HONEYCOMB, 4)
                .requires(Items.HONEYCOMB_BLOCK)
                .unlockedBy(getHasName(Items.HONEYCOMB_BLOCK), has(Items.HONEYCOMB_BLOCK))
                .save(pWriter, Items.HONEYCOMB + "_from_" + Items.HONEYCOMB_BLOCK);

        waxBlock(WBlocks.SOUL_WAX_BLOCK.get(), ItemTags.SOUL_FIRE_BASE_BLOCKS, pWriter);
        if (ModList.get().isLoaded("caverns_and_chasms")) waxBlock(WBlocks.CUPRIC_WAX_BLOCK.get(), CCItemTags.CUPRIC_FIRE_BASE_BLOCKS, pWriter);
        if (ModList.get().isLoaded("endergetic")) waxBlock(WBlocks.ENDER_WAX_BLOCK.get(), EEItemTags.ENDER_FIRE_BASE_BLOCKS, pWriter);

        for (DyeColor color : DyeColor.values()) {
            String dyeID = "minecraft";
            if (ModList.get().isLoaded("dye_depot")) {
                for (DDDyes dddcolor : DDDyes.values()) {
                    if (dddcolor.get().getName() == color.getName()) dyeID = "dye_depot";
                }
            }
            waxBlock(Waxed.getItemFromString(color + "_wax_block"), Waxed.getItemFromString(dyeID, color + "_dye"), pWriter);
            waxPillar(Waxed.getItemFromString(color + "_wax_block"), Waxed.getItemFromString(color + "_wax_pillar"), pWriter);
            tallCandle(Waxed.getItemFromString(color + "_tall_candle"), Waxed.getItemFromString(dyeID, color + "_candle"), pWriter);
            ///--waxedBlock(Waxed.getItemFromString("waxed_" + color + "_concrete_powder"), Waxed.getItemFromString(dyeID, color + "_concrete_powder"), pWriter);
        }

        waxPillar(WBlocks.WAX_BLOCK.get(), WBlocks.WAX_PILLAR.get(), pWriter);
        waxPillar(WBlocks.SOUL_WAX_BLOCK.get(), WBlocks.SOUL_WAX_PILLAR.get(), pWriter);
        if (ModList.get().isLoaded("caverns_and_chasms")) waxPillar(WBlocks.CUPRIC_WAX_BLOCK.get(), WBlocks.CUPRIC_WAX_PILLAR.get(), pWriter);
        if (ModList.get().isLoaded("endergetic")) waxPillar(WBlocks.ENDER_WAX_BLOCK.get(), WBlocks.ENDER_WAX_PILLAR.get(), pWriter);

        tallCandle(WBlocks.TALL_CANDLE.get(), Blocks.CANDLE, pWriter);
        tallCandle(WBlocks.SOUL_TALL_CANDLE.get(), WBlocks.SOUL_CANDLE.get(), pWriter);
        if (ModList.get().isLoaded("caverns_and_chasms")) tallCandle(WBlocks.CUPRIC_TALL_CANDLE.get(), WBlocks.CUPRIC_CANDLE.get(), pWriter);
        if (ModList.get().isLoaded("endergetic")) tallCandle(WBlocks.ENDER_TALL_CANDLE.get(), WBlocks.ENDER_CANDLE.get(), pWriter);

        waxingBlock(WBlocks.WAXED_SAND.get(), Blocks.SAND, pWriter);
        waxingBlock(WBlocks.WAXED_RED_SAND.get(), Blocks.RED_SAND, pWriter);
        waxingBlock(WBlocks.WAXED_GRAVEL.get(), Blocks.GRAVEL, pWriter);
        waxingBlock(WBlocks.WAXED_ICE.get(), Blocks.ICE, pWriter);
        waxingBlock(WBlocks.WAXED_SPONGE.get(), Blocks.SPONGE, pWriter);
        for (DyeColor color : DyeColor.values()) {
            String dyeID = "minecraft";
            if (ModList.get().isLoaded("dye_depot")) {
                for (DDDyes dddcolor : DDDyes.values()) {
                    if (dddcolor.get().getName() == color.getName()) dyeID = "dye_depot";
                }
            }
            waxingBlock(Waxed.getItemFromString("waxed_" + color + "_concrete_powder"), Waxed.getItemFromString(dyeID, color + "_concrete_powder"), pWriter);
        }
        waxingBlock(WBlocks.WAXED_TUBE_CORAL_BLOCK.get(), Blocks.TUBE_CORAL_BLOCK, pWriter);
        waxingBlock(WBlocks.WAXED_BRAIN_CORAL_BLOCK.get(), Blocks.BRAIN_CORAL_BLOCK, pWriter);
        waxingBlock(WBlocks.WAXED_BUBBLE_CORAL_BLOCK.get(), Blocks.BUBBLE_CORAL_BLOCK, pWriter);
        waxingBlock(WBlocks.WAXED_FIRE_CORAL_BLOCK.get(), Blocks.FIRE_CORAL_BLOCK, pWriter);
        waxingBlock(WBlocks.WAXED_HORN_CORAL_BLOCK.get(), Blocks.HORN_CORAL_BLOCK, pWriter);
        waxingBlock(WBlocks.WAXED_TUBE_CORAL.get(), Blocks.TUBE_CORAL, pWriter);
        waxingBlock(WBlocks.WAXED_BRAIN_CORAL.get(), Blocks.BRAIN_CORAL, pWriter);
        waxingBlock(WBlocks.WAXED_BUBBLE_CORAL.get(), Blocks.BUBBLE_CORAL, pWriter);
        waxingBlock(WBlocks.WAXED_FIRE_CORAL.get(), Blocks.FIRE_CORAL, pWriter);
        waxingBlock(WBlocks.WAXED_HORN_CORAL.get(), Blocks.HORN_CORAL, pWriter);
        waxingBlock(WBlocks.WAXED_TUBE_CORAL_FAN.get(), Blocks.TUBE_CORAL_FAN, pWriter);
        waxingBlock(WBlocks.WAXED_BRAIN_CORAL_FAN.get(), Blocks.BRAIN_CORAL_FAN, pWriter);
        waxingBlock(WBlocks.WAXED_BUBBLE_CORAL_FAN.get(), Blocks.BUBBLE_CORAL_FAN, pWriter);
        waxingBlock(WBlocks.WAXED_FIRE_CORAL_FAN.get(), Blocks.FIRE_CORAL_FAN, pWriter);
        waxingBlock(WBlocks.WAXED_HORN_CORAL_FAN.get(), Blocks.HORN_CORAL_FAN, pWriter);
        waxingBlock(WBlocks.WAXED_SUGAR_CUBE.get(), ModRegistry.SUGAR_CUBE.get(), pWriter);
        waxingBlock(WBlocks.WAXED_RAKED_GRAVEL.get(), ModRegistry.RAKED_GRAVEL.get(), pWriter);
        //waxingBlock(WBlocks.WAXED_SOAP_BLOCK.get(), ModRegistry.SOAP_BLOCK.get(), pWriter);
        //waxingBlock(WBlocks.WAXED_GROOVED_ICE.get(), Waxed.getBlockFromString("oreganized", "grooved_ice"), pWriter);
        //waxingBlock(WBlocks.WAXED_GROOVED_ICE.get(),WBlocks.GROOVED_ICE.get(), pWriter);
        waxingBlock(WBlocks.WAXED_ACAN_CORAL_BLOCK.get(), UABlocks.ACAN_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_FINGER_CORAL_BLOCK.get(), UABlocks.FINGER_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_STAR_CORAL_BLOCK.get(), UABlocks.STAR_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_MOSS_CORAL_BLOCK.get(), UABlocks.MOSS_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_PETAL_CORAL_BLOCK.get(), UABlocks.PETAL_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_BRANCH_CORAL_BLOCK.get(), UABlocks.BRANCH_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_ROCK_CORAL_BLOCK.get(), UABlocks.ROCK_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_PILLOW_CORAL_BLOCK.get(), UABlocks.PILLOW_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_SILK_CORAL_BLOCK.get(), UABlocks.SILK_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_CHROME_CORAL_BLOCK.get(), UABlocks.CHROME_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_PRISMARINE_CORAL_BLOCK.get(), UABlocks.PRISMARINE_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_BLOCK.get(), UABlocks.ELDER_PRISMARINE_CORAL_BLOCK.get(), pWriter);
        waxingBlock(WBlocks.WAXED_ACAN_CORAL.get(), UABlocks.ACAN_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_FINGER_CORAL.get(), UABlocks.FINGER_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_STAR_CORAL.get(), UABlocks.STAR_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_MOSS_CORAL.get(), UABlocks.MOSS_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_PETAL_CORAL.get(), UABlocks.PETAL_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_BRANCH_CORAL.get(), UABlocks.BRANCH_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_ROCK_CORAL.get(), UABlocks.ROCK_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_PILLOW_CORAL.get(), UABlocks.PILLOW_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_SILK_CORAL.get(), UABlocks.SILK_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_CHROME_CORAL.get(), UABlocks.CHROME_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_PRISMARINE_CORAL.get(), UABlocks.PRISMARINE_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL.get(), UABlocks.ELDER_PRISMARINE_CORAL.get(), pWriter);
        waxingBlock(WBlocks.WAXED_ACAN_CORAL_FAN.get(), UABlocks.ACAN_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_FINGER_CORAL_FAN.get(), UABlocks.FINGER_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_STAR_CORAL_FAN.get(), UABlocks.STAR_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_MOSS_CORAL_FAN.get(), UABlocks.MOSS_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_PETAL_CORAL_FAN.get(), UABlocks.PETAL_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_BRANCH_CORAL_FAN.get(), UABlocks.BRANCH_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_ROCK_CORAL_FAN.get(), UABlocks.ROCK_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_PILLOW_CORAL_FAN.get(), UABlocks.PILLOW_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_SILK_CORAL_FAN.get(), UABlocks.SILK_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_CHROME_CORAL_FAN.get(), UABlocks.CHROME_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_PRISMARINE_CORAL_FAN.get(), UABlocks.PRISMARINE_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_FAN.get(), UABlocks.ELDER_PRISMARINE_CORAL_FAN.get(), pWriter);
        waxingBlock(WBlocks.WAXED_PRISMARINE_CORAL_SHOWER.get(), UABlocks.PRISMARINE_CORAL_SHOWER.get(), pWriter);
        waxingBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_SHOWER.get(), UABlocks.ELDER_PRISMARINE_CORAL_SHOWER.get(), pWriter);



        waxedBlock(CCItems.OXIDIZED_COPPER_GOLEM.get(), CCItems.WAXED_OXIDIZED_COPPER_GOLEM.get(), pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WBlocks.WICK.get(), 16)
                .pattern("SSS")
                .pattern("SCS")
                .pattern("SSS")
                .define('S', Items.STRING)
                .define('C', ItemTags.COALS)
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.CANDLE, 4)
                .pattern("S")
                .pattern("W")
                .define('S', WTags.Items.CANDLE_STRING)
                .define('W', WTags.Items.CAN_WAX)
                .unlockedBy(getHasName(WBlocks.WICK.get()), has(WBlocks.WICK.get()))
                .unlockedBy(getHasName(WItems.WAX.get()), has(WItems.WAX.get()))
                .unlockedBy(getHasName(Items.HONEYCOMB), has(Items.HONEYCOMB))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WBlocks.SOUL_CANDLE.get(), 4)
                .pattern("S")
                .pattern("W")
                .pattern("T")
                .define('S', WTags.Items.CANDLE_STRING)
                .define('W', WTags.Items.CAN_WAX)
                .define('T', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .unlockedBy(getHasName(WBlocks.WICK.get()), has(WBlocks.WICK.get()))
                .unlockedBy(getHasName(WItems.WAX.get()), has(WItems.WAX.get()))
                .unlockedBy(getHasName(Items.HONEYCOMB), has(Items.HONEYCOMB))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WBlocks.CUPRIC_CANDLE.get(), 4)
                .pattern("S")
                .pattern("W")
                .pattern("T")
                .define('S', WTags.Items.CANDLE_STRING)
                .define('W', WTags.Items.CAN_WAX)
                .define('T', CCItemTags.CUPRIC_FIRE_BASE_BLOCKS)
                .unlockedBy(getHasName(WBlocks.WICK.get()), has(WBlocks.WICK.get()))
                .unlockedBy(getHasName(WItems.WAX.get()), has(WItems.WAX.get()))
                .unlockedBy(getHasName(Items.HONEYCOMB), has(Items.HONEYCOMB))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, WBlocks.ENDER_CANDLE.get(), 4)
                .pattern("S")
                .pattern("W")
                .pattern("T")
                .define('S', WTags.Items.CANDLE_STRING)
                .define('W', WTags.Items.CAN_WAX)
                .define('T', EEItemTags.ENDER_FIRE_BASE_BLOCKS)
                .unlockedBy(getHasName(WBlocks.WICK.get()), has(WBlocks.WICK.get()))
                .unlockedBy(getHasName(WItems.WAX.get()), has(WItems.WAX.get()))
                .unlockedBy(getHasName(Items.HONEYCOMB), has(Items.HONEYCOMB))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, WItems.WAX.get(), 9)
                .requires(WBlocks.WAX_BLOCK.get())
                .unlockedBy(getHasName(WBlocks.WAX_BLOCK.get()), has(WBlocks.WAX_BLOCK.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, WBlocks.WAX_BLOCK.get(), 1)
                .requires(WItems.WAX.get(), 9)
                .unlockedBy(getHasName(WItems.WAX.get()), has(WItems.WAX.get()))
                .save(pWriter);

        if (ModList.get().isLoaded("iwannaskate")) {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, IWSItemRegistry.SHIMMERING_WAX.get())
                    .requires(WTags.Items.CAN_WAX)
                    .requires(Items.LAPIS_LAZULI)
                    .requires(Items.GLOW_INK_SAC)
                    .unlockedBy(getHasName(WItems.WAX.get()), has(WItems.WAX.get()))
                    .unlockedBy(getHasName(Items.HONEYCOMB), has(Items.HONEYCOMB))
                    .save(pWriter, new ResourceLocation("iwannaskate", "shimmering_wax"));
        }

        if (ModList.get().isLoaded("suppsquared")) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WBlocks.GOLD_SOUL_CANDLE_HOLDER.get())
                    .pattern("C")
                    .pattern("N")
                    .define('C', WBlocks.SOUL_CANDLE.get())
                    .define('N', Items.GOLD_INGOT)
                    .unlockedBy(getHasName(WBlocks.SOUL_CANDLE.get()), has(WBlocks.SOUL_CANDLE.get()))
                    .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                    .save(pWriter);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WBlocks.GOLD_CUPRIC_CANDLE_HOLDER.get())
                    .pattern("C")
                    .pattern("N")
                    .define('C', WBlocks.CUPRIC_CANDLE.get())
                    .define('N', Items.GOLD_INGOT)
                    .unlockedBy(getHasName(WBlocks.CUPRIC_CANDLE.get()), has(WBlocks.CUPRIC_CANDLE.get()))
                    .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                    .save(pWriter);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WBlocks.GOLD_ENDER_CANDLE_HOLDER.get())
                    .pattern("C")
                    .pattern("N")
                    .define('C', WBlocks.ENDER_CANDLE.get())
                    .define('N', Items.GOLD_INGOT)
                    .unlockedBy(getHasName(WBlocks.ENDER_CANDLE.get()), has(WBlocks.ENDER_CANDLE.get()))
                    .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                    .save(pWriter);
        }

        if (ModList.get().isLoaded("supplementaries")) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WBlocks.SOUL_CANDLE_HOLDER.get())
                    .pattern("C")
                    .pattern("N")
                    .define('C', WBlocks.SOUL_CANDLE.get())
                    .define('N', Items.IRON_INGOT)
                    .unlockedBy(getHasName(WBlocks.SOUL_CANDLE.get()), has(WBlocks.SOUL_CANDLE.get()))
                    .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                    .save(pWriter);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WBlocks.CUPRIC_CANDLE_HOLDER.get())
                    .pattern("C")
                    .pattern("N")
                    .define('C', WBlocks.CUPRIC_CANDLE.get())
                    .define('N', Items.IRON_INGOT)
                    .unlockedBy(getHasName(WBlocks.CUPRIC_CANDLE.get()), has(WBlocks.CUPRIC_CANDLE.get()))
                    .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                    .save(pWriter);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WBlocks.ENDER_CANDLE_HOLDER.get())
                    .pattern("C")
                    .pattern("N")
                    .define('C', WBlocks.ENDER_CANDLE.get())
                    .define('N', Items.IRON_INGOT)
                    .unlockedBy(getHasName(WBlocks.ENDER_CANDLE.get()), has(WBlocks.ENDER_CANDLE.get()))
                    .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                    .save(pWriter);

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.SUGAR, 9)
                    .requires(WBlocks.WAXED_SUGAR_CUBE.get(), 1)
                    .unlockedBy(getHasName(WBlocks.WAXED_SUGAR_CUBE.get()), has(WBlocks.WAXED_SUGAR_CUBE.get()))
                    .save(pWriter, "sugar_from_waxed_sugar_block");
        }

        if (ModList.get().isLoaded("oreganized")) {
            //scribe(new ResourceLocation("waxed"),new BlockPredicate(WBlocks.WAXED_ICE.get(), WBlocks.WAXED_ICE.get().gep) WBlocks.WAXED_ICE.get(), WBlocks.WAXED_GROOVED_ICE.get(), false);
        }

        if (ModList.get().isLoaded("buzzier_bees")) {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BBBlocks.SOUL_CANDLE.get(), 2)
                    .pattern("##")
                    .define('#', Ingredient.EMPTY)
                    .unlockedBy(getHasName(BBBlocks.SOUL_CANDLE.get()), has(BBBlocks.SOUL_CANDLE.get()))
                    .save(pWriter, new ResourceLocation("buzzier_bees", "candles/soul_candle"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, WBlocks.SOUL_CANDLE.get())
                    .requires(BBBlocks.SOUL_CANDLE.get())
                    .unlockedBy(getHasName(BBBlocks.SOUL_CANDLE.get()), has(BBBlocks.SOUL_CANDLE.get()))
                    .save(pWriter, new ResourceLocation("waxed", "soul_candle_from_buzzier_bees_soul_candle"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BBBlocks.CUPRIC_CANDLE.get(), 2)
                    .pattern("##")
                    .define('#', Ingredient.EMPTY)
                    .unlockedBy(getHasName(BBBlocks.CUPRIC_CANDLE.get()), has(BBBlocks.CUPRIC_CANDLE.get()))
                    .save(pWriter, new ResourceLocation("buzzier_bees", "candles/cupric_candle"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, WBlocks.CUPRIC_CANDLE.get())
                    .requires(BBBlocks.CUPRIC_CANDLE.get())
                    .unlockedBy(getHasName(BBBlocks.CUPRIC_CANDLE.get()), has(BBBlocks.CUPRIC_CANDLE.get()))
                    .save(pWriter, new ResourceLocation("waxed", "cupric_candle_from_buzzier_bees_cupric_candle"));

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BBBlocks.CUPRIC_CANDLE.get(), 2)
                    .pattern("##")
                    .define('#', Ingredient.EMPTY)
                    .unlockedBy(getHasName(BBBlocks.CUPRIC_CANDLE.get()), has(BBBlocks.CUPRIC_CANDLE.get()))
                    .save(pWriter, new ResourceLocation("buzzier_bees", "candles/ender_candle"));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, WBlocks.ENDER_CANDLE.get())
                    .requires(BBBlocks.ENDER_CANDLE.get())
                    .unlockedBy(getHasName(BBBlocks.ENDER_CANDLE.get()), has(BBBlocks.ENDER_CANDLE.get()))
                    .save(pWriter, new ResourceLocation("waxed", "ender_candle_from_buzzier_bees_ender_candle"));
        }
    }

    protected static void smelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        cooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void cooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  Waxed.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}