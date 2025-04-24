package net.im_maker.waxed.datagen;

import com.github.alexthe668.iwannaskate.server.item.IWSItemRegistry;
import com.ninni.dye_depot.registry.DDDyes;
import com.teamabnormals.buzzier_bees.core.registry.BBBlocks;
import com.teamabnormals.caverns_and_chasms.core.other.tags.CCItemTags;
import com.teamabnormals.caverns_and_chasms.core.registry.CCItems;
import com.teamabnormals.endergetic.core.other.tags.EEItemTags;
import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.item.WItems;
import net.im_maker.waxed.common.util.WTags;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
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

    private void waxedBlock(ItemLike waxedBlock, ItemLike unWaxedBlock, Consumer consumer, String s) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, waxedBlock)
                .requires(unWaxedBlock)
                .requires(WTags.Items.CAN_WAX)
                .unlockedBy(getHasName(unWaxedBlock), has(unWaxedBlock))
                .save(consumer, s);
    }

    private void waxedBlock(ItemLike waxedBlock, ItemLike unWaxedBlock, Consumer consumer, ResourceLocation s) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, waxedBlock)
                .requires(unWaxedBlock)
                .requires(WTags.Items.CAN_WAX)
                .unlockedBy(getHasName(unWaxedBlock), has(unWaxedBlock))
                .save(consumer, s);
    }

    private static void stairs(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pPressurePlate, ItemLike pMaterial) {
        stairBuilder(pPressurePlate, Ingredient.of(pMaterial)).unlockedBy(getHasName(pMaterial), has(pMaterial)).save(pFinishedRecipeConsumer);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, EMPTY_HONEYCOMB, RecipeCategory.MISC, WBlocks.WAX_BLOCK.get(), 0.25f, 200, "wax_block");
        //stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, WaxedModBlocks.WAXED_PRISMARINE_STAIRS.get(), WaxedModBlocks.WAXED_PRISMARINE.get());
        //stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, WaxedModBlocks.WAXED_PRISMARINE_SLAB.get(), WaxedModBlocks.WAXED_PRISMARINE.get());
        //stonecutterResultFromBase(pWriter, RecipeCategory.BUILDING_BLOCKS, WaxedModBlocks.WAXED_PRISMARINE_WALL.get(), WaxedModBlocks.WAXED_PRISMARINE.get());
        //stairs(pWriter, WaxedModBlocks.WAXED_PRISMARINE_STAIRS.get(), WaxedModBlocks.WAXED_PRISMARINE.get());
        //slab(pWriter, RecipeCategory.BUILDING_BLOCKS, WaxedModBlocks.WAXED_PRISMARINE_SLAB.get(), WaxedModBlocks.WAXED_PRISMARINE.get());
        //wall(pWriter, RecipeCategory.BUILDING_BLOCKS, WaxedModBlocks.WAXED_PRISMARINE_WALL.get(), WaxedModBlocks.WAXED_PRISMARINE.get());
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
            waxBlock(Waxed.getBlockFromString(color + "_wax_block"), Waxed.getBlockFromString(dyeID, color + "_dye"), pWriter);
            waxPillar(Waxed.getBlockFromString(color + "_wax_block"), Waxed.getBlockFromString(color + "_wax_pillar"), pWriter);
            System.out.println(Waxed.getBlockFromString(color + "_tall_candle"));
            System.out.println(Waxed.getBlockFromString(dyeID, color + "_candle"));
            tallCandle(Waxed.getBlockFromString(color + "_tall_candle"), Waxed.getBlockFromString(dyeID, color + "_candle"), pWriter);
            ///--waxedBlock(Waxed.getBlockFromString("waxed_" + color + "_concrete_powder"), Waxed.getBlockFromString(dyeID, color + "_concrete_powder"), pWriter);
        }

        waxPillar(WBlocks.WAX_BLOCK.get(), WBlocks.WAX_PILLAR.get(), pWriter);
        waxPillar(WBlocks.SOUL_WAX_BLOCK.get(), WBlocks.SOUL_WAX_PILLAR.get(), pWriter);
        if (ModList.get().isLoaded("caverns_and_chasms")) waxPillar(WBlocks.CUPRIC_WAX_BLOCK.get(), WBlocks.CUPRIC_WAX_PILLAR.get(), pWriter);
        if (ModList.get().isLoaded("endergetic")) waxPillar(WBlocks.ENDER_WAX_BLOCK.get(), WBlocks.ENDER_WAX_PILLAR.get(), pWriter);

        tallCandle(WBlocks.TALL_CANDLE.get(), Blocks.CANDLE, pWriter);
        tallCandle(WBlocks.SOUL_TALL_CANDLE.get(), WBlocks.SOUL_CANDLE.get(), pWriter);
        if (ModList.get().isLoaded("caverns_and_chasms")) tallCandle(WBlocks.CUPRIC_TALL_CANDLE.get(), WBlocks.CUPRIC_CANDLE.get(), pWriter);
        if (ModList.get().isLoaded("endergetic")) tallCandle(WBlocks.ENDER_TALL_CANDLE.get(), WBlocks.ENDER_CANDLE.get(), pWriter);

        waxedBlock(CCItems.OXIDIZED_COPPER_GOLEM.get(), CCItems.WAXED_OXIDIZED_COPPER_GOLEM.get(), pWriter);
        ///--
        //waxedBlock(WBlocks.WAXED_SAND.get(), Items.SAND, pWriter);
        //waxedBlock(WBlocks.WAXED_RED_SAND.get(), Items.RED_SAND, pWriter);
        //waxedBlock(WBlocks.WAXED_GRAVEL.get(), Items.GRAVEL, pWriter);
        //waxedBlock(WBlocks.WAXED_POWDER_SNOW.get(), Items.POWDER_SNOW_BUCKET, pWriter);
        //waxedBlock(WBlocks.WAXED_ICE.get(), Items.ICE, pWriter);
        //waxedBlock(WBlocks.WAXED_SPONGE.get(), Items.SPONGE, pWriter);

        ///--
        //String[] corals = {"tube", "brain", "bubble", "fire", "horn"};
        //for (String coralType : corals) {
        //    ResourceLocation waxedCoralBlockLocation = new ResourceLocation("waxed:waxed_" + coralType + "_coral_block");
        //    ResourceLocation coralBlockLocation = new ResourceLocation("minecraft:" + coralType + "_coral_block");
        //    Block waxedCoralBlock = Block.byItem(ForgeRegistries.ITEMS.getValue(waxedCoralBlockLocation));
        //    Block coralBlock = Block.byItem(ForgeRegistries.ITEMS.getValue(coralBlockLocation));
        //    waxedBlock(waxedCoralBlock, coralBlock, pWriter, new ResourceLocation("waxed", "waxed_" + coralType + "_coral_block"));
        //    ResourceLocation waxedCoralLocation = new ResourceLocation("waxed:waxed_" + coralType  + "_coral");
        //    ResourceLocation coralLocation = new ResourceLocation("minecraft:" + coralType  + "_coral");
        //    Block waxedCoral = Block.byItem(ForgeRegistries.ITEMS.getValue(waxedCoralLocation));
        //    Block coral = Block.byItem(ForgeRegistries.ITEMS.getValue(coralLocation));
        //    waxedBlock(waxedCoral, coral, pWriter, new ResourceLocation("waxed", "waxed_" + coralType  + "_coral"));
        //    ResourceLocation waxedCoralFanLocation = new ResourceLocation("waxed:waxed_" + coralType + "_coral_fan");
        //    ResourceLocation coralFanLocation = new ResourceLocation("minecraft:" + coralType + "_coral_fan");
        //    Block waxedCoralFan = Block.byItem(ForgeRegistries.ITEMS.getValue(waxedCoralFanLocation));
        //    Block coralFan = Block.byItem(ForgeRegistries.ITEMS.getValue(coralFanLocation));
        //    waxedBlock(waxedCoralFan, coralFan, pWriter, new ResourceLocation("waxed", "waxed_" + coralType + "_coral_fan"));
        //}

        ///--
        //if (ModList.get().isLoaded("upgrade_aquatic")) {
        //    String[] coralsUQ = {"acan", "finger", "star", "moss", "petal", "branch", "rock", "pillow", "silk", "chrome", "prismarine", "elder_prismarine"};
        //    for (String coralType : coralsUQ) {
        //        ResourceLocation waxedCoralBlockLocation = new ResourceLocation("waxed:waxed_" + coralType + "_coral_block");
        //        ResourceLocation coralBlockLocation = new ResourceLocation("upgrade_aquatic:" + coralType + "_coral_block");
        //        Block waxedCoralBlock = Block.byItem(ForgeRegistries.ITEMS.getValue(waxedCoralBlockLocation));
        //        Block coralBlock = Block.byItem(ForgeRegistries.ITEMS.getValue(coralBlockLocation));
        //        waxedBlock(waxedCoralBlock, coralBlock, pWriter, new ResourceLocation("waxed", "waxed_" + coralType + "_coral_block"));
        //        ResourceLocation waxedCoralLocation = new ResourceLocation("waxed:waxed_" + coralType + "_coral");
        //        ResourceLocation coralLocation = new ResourceLocation("upgrade_aquatic:" + coralType + "_coral");
        //        Block waxedCoral = Block.byItem(ForgeRegistries.ITEMS.getValue(waxedCoralLocation));
        //        Block coral = Block.byItem(ForgeRegistries.ITEMS.getValue(coralLocation));
        //        waxedBlock(waxedCoral, coral, pWriter, new ResourceLocation("waxed", "waxed_" + coralType  + "_coral"));
        //        ResourceLocation waxedCoralFanLocation = new ResourceLocation("waxed:waxed_" + coralType + "_coral_fan");
        //        ResourceLocation coralFanLocation = new ResourceLocation("upgrade_aquatic:" + coralType + "_coral_fan");
        //        Block waxedCoralFan = Block.byItem(ForgeRegistries.ITEMS.getValue(waxedCoralFanLocation));
        //        Block coralFan = Block.byItem(ForgeRegistries.ITEMS.getValue(coralFanLocation));
        //        waxedBlock(waxedCoralFan, coralFan, pWriter, new ResourceLocation("waxed", "waxed_" + coralType + "_coral_fan"));
        //    }
        //    waxedBlock(WBlocks.WAXED_PRISMARINE_CORAL_SHOWER.get(), UABlocks.PRISMARINE_CORAL_SHOWER.get(), pWriter, new ResourceLocation("waxed", "waxed_prismarine_coral_shower"));
        //    waxedBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_SHOWER.get(), UABlocks.ELDER_PRISMARINE_CORAL_SHOWER.get(), pWriter, new ResourceLocation("waxed", "waxed_elder_prismarine_coral_shower"));
        //}

        //waxedBlock(WaxedModBlocks.WAXED_TAN_CONCRETE_POWDER.get(), DDItems.TAN_CONCRETE_POWDER.get(), pWriter);

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

        //twoByTwoPackerMod(pWriter, Waxed.MOD_ID + ":sandstone_from_waxed_sand", RecipeCategory.BUILDING_BLOCKS, Blocks.SANDSTONE, WaxedModBlocks.WAXED_SAND.get());
        //twoByTwoPackerMod(pWriter, Waxed.MOD_ID + ":red_sandstone_from_waxed_red_sand", RecipeCategory.BUILDING_BLOCKS, Blocks.RED_SANDSTONE, WaxedModBlocks.WAXED_RED_SAND.get());

        if (ModList.get().isLoaded("iwannaskate")) {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, IWSItemRegistry.SHIMMERING_WAX.get())
                    .requires(WTags.Items.CAN_WAX)
                    .requires(Items.LAPIS_LAZULI)
                    .requires(Items.GLOW_INK_SAC)
                    .unlockedBy(getHasName(WItems.WAX.get()), has(WItems.WAX.get()))
                    .unlockedBy(getHasName(Items.HONEYCOMB), has(Items.HONEYCOMB))
                    .save(pWriter, new ResourceLocation("iwannaskate", "shimmering_wax"));
        }

        ///--
        //if (ModList.get().isLoaded("aromatic")) {
        //    waxedBlock(AromaticModBlocks.WAXED_CINNAMON_LOG_THIN.get(), AromaticModBlocks.CINNAMON_LOG_THIN.get(), pWriter);
        //    waxedBlock(AromaticModBlocks.WAXED_STRIPPED_CINNAMON_LOG_THIN.get(), AromaticModBlocks.STRIPPED_CINNAMON_LOG_THIN.get(), pWriter);
        //}
//
        //if (ModList.get().isLoaded("mynethersdelight")) {
        //    waxedBlock(MNDBlocks.WAXED_HOGLIN_TROPHY.get(), MNDBlocks.HOGLIN_TROPHY.get(), pWriter);
        //}
//
        //if (ModList.get().isLoaded("twigs")) {
        //    waxedBlock(TwigsBlocks.WAXED_COPPER_PILLAR.get(), TwigsBlocks.COPPER_PILLAR.get(), pWriter, new ResourceLocation("twigs", "waxed_copper_pillar_from_wax"));
        //    waxedBlock(TwigsBlocks.WAXED_EXPOSED_COPPER_PILLAR.get(), TwigsBlocks.EXPOSED_COPPER_PILLAR.get(), pWriter, new ResourceLocation("twigs", "waxed_exposed_copper_pillar_from_wax"));
        //    waxedBlock(TwigsBlocks.WAXED_WEATHERED_COPPER_PILLAR.get(), TwigsBlocks.WEATHERED_COPPER_PILLAR.get(), pWriter, new ResourceLocation("twigs", "waxed_weathered_copper_pillar_from_wax"));
        //    waxedBlock(TwigsBlocks.WAXED_OXIDIZED_COPPER_PILLAR.get(), TwigsBlocks.OXIDIZED_COPPER_PILLAR.get(), pWriter, new ResourceLocation("twigs", "waxed_oxidized_copper_pillar_from_wax"));
        //}

        //if (ModList.get().isLoaded("quark")) {
        //    String[] quarkCorundumColors = {"red", "orange", "yellow", "green", "blue", "indigo", "violet", "white", "black"};
        //    for (String color : quarkCorundumColors) {
        //        ResourceLocation waxedCorundumLocation = new ResourceLocation("quark:waxed_" + color + "_corundum");
        //        ResourceLocation corundumLocation = new ResourceLocation("quark:" + color + "_corundum");
        //        Block waxedCorundum = Block.byItem(ForgeRegistries.ITEMS.getValue(waxedCorundumLocation));
        //        Block corundum = Block.byItem(ForgeRegistries.ITEMS.getValue(corundumLocation));
        //        waxedBlock(waxedCorundum, corundum, pWriter, new ResourceLocation("quark", "world/crafting/waxed_" + color + "_corundum"));
        //    }
//
        //    String[] quarkVerticalCopperSlabs = {"", "exposed_", "weathered_", "oxidized_"};
        //    for (String type : quarkVerticalCopperSlabs) {
        //        ResourceLocation waxedVerticalCopperSlabLocation = new ResourceLocation("quark", "waxed_" + type + "cut_copper_vertical_slab");
        //        ResourceLocation VerticalCopperSlabLocation = new ResourceLocation("quark", type + "cut_copper_vertical_slab");
        //        Block waxedVerticalCopperSlab = Block.byItem(ForgeRegistries.ITEMS.getValue(waxedVerticalCopperSlabLocation));
        //        Block VerticalCopperSlab = Block.byItem(ForgeRegistries.ITEMS.getValue(VerticalCopperSlabLocation));
//
        //        waxedBlock(waxedVerticalCopperSlab, VerticalCopperSlab, pWriter, new ResourceLocation("quark", "building/crafting/vertslabs/wax/waxed_" + type + "cut_copper_vertical_slab"));
        //    }
        //}

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
                    .pattern("NCN")
                    .pattern(" N ")
                    .define('C', WBlocks.SOUL_CANDLE.get())
                    .define('N', Items.IRON_INGOT)
                    .unlockedBy(getHasName(WBlocks.SOUL_CANDLE.get()), has(WBlocks.SOUL_CANDLE.get()))
                    .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                    .save(pWriter);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WBlocks.CUPRIC_CANDLE_HOLDER.get())
                    .pattern("NCN")
                    .pattern(" N ")
                    .define('C', WBlocks.CUPRIC_CANDLE.get())
                    .define('N', Items.IRON_INGOT)
                    .unlockedBy(getHasName(WBlocks.CUPRIC_CANDLE.get()), has(WBlocks.CUPRIC_CANDLE.get()))
                    .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                    .save(pWriter);
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WBlocks.ENDER_CANDLE_HOLDER.get())
                    .pattern("NCN")
                    .pattern(" N ")
                    .define('C', WBlocks.ENDER_CANDLE.get())
                    .define('N', Items.IRON_INGOT)
                    .unlockedBy(getHasName(WBlocks.ENDER_CANDLE.get()), has(WBlocks.ENDER_CANDLE.get()))
                    .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                    .save(pWriter);

            ///--waxedBlock(WBlocks.WAXED_SUGAR_CUBE.get(), ModRegistry.SUGAR_CUBE.get().asItem(), pWriter);
            ///--waxedBlock(WBlocks.WAXED_SOAP_BLOCK.get(), ModRegistry.SOAP_BLOCK.get().asItem(), pWriter);

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.SUGAR, 9)
                    .requires(WBlocks.WAXED_SUGAR_CUBE.get(), 1)
                    .unlockedBy(getHasName(WBlocks.WAXED_SUGAR_CUBE.get()), has(WBlocks.WAXED_SUGAR_CUBE.get()))
                    .save(pWriter, "sugar_from_waxed_sugar_block");
        }

        if (ModList.get().isLoaded("buzzier_bees")) {
            //ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, BBBlocks.SOUL_CANDLE.get())
            //        .save(pWriter, new ResourceLocation("buzzier_bees", "candles/soul_candle"));

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

    protected static void twoByTwoPackerMod(Consumer<FinishedRecipe> pFinishedRecipeConsumer, String pId, RecipeCategory pCategory, ItemLike pPacked, ItemLike pUnpacked) {
        ShapedRecipeBuilder.shaped(pCategory, pPacked, 1).define('#', pUnpacked).pattern("##").pattern("##").unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, pId);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  Waxed.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}