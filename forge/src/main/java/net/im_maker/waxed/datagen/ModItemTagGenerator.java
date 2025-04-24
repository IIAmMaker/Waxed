package net.im_maker.waxed.datagen;

import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.item.WItems;
import net.im_maker.waxed.common.util.WTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Waxed.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(ItemTags.STAIRS).add(
                WBlocks.WAXED_PRISMARINE_STAIRS.get().asItem()
        );

        this.tag(ItemTags.SLABS).add(
                WBlocks.WAXED_PRISMARINE_SLAB.get().asItem()
        );

        this.tag(ItemTags.WALLS).add(
                WBlocks.WAXED_PRISMARINE_WALL.get().asItem()
        );

        this.tag(WTags.Items.CAN_WAX).add(
                WItems.WAX.get(),
                Items.HONEYCOMB
        );

        this.tag(WTags.Items.CANDLE_STRING).add(
                WBlocks.WICK.get().asItem(),
                Items.STRING
        );

        this.tag(ItemTags.CANDLES).add(
                WBlocks.SOUL_CANDLE.get().asItem(),
                WBlocks.CUPRIC_CANDLE.get().asItem(),
                WBlocks.ENDER_CANDLE.get().asItem()
        );

        this.tag(Tags.Items.GRAVEL).add(
                WBlocks.WAXED_GRAVEL.get().asItem()
        );

        this.tag(Tags.Items.SAND).add(
                WBlocks.WAXED_SAND.get().asItem(),
                WBlocks.WAXED_RED_SAND.get().asItem()
        );

        this.tag(Tags.Items.SAND_COLORLESS).add(
                WBlocks.WAXED_SAND.get().asItem()
        );

        this.tag(Tags.Items.SAND_RED).add(
                WBlocks.WAXED_RED_SAND.get().asItem()
        );

        this.tag(ItemTags.SAND).add(
                WBlocks.WAXED_SAND.get().asItem(),
                WBlocks.WAXED_RED_SAND.get().asItem()
        );

        this.tag(ItemTags.SMELTS_TO_GLASS).add(
                WBlocks.WAXED_SAND.get().asItem(),
                WBlocks.WAXED_RED_SAND.get().asItem()
        );

        this.tag(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
                WBlocks.WAXED_SOUL_SAND.get().asItem()
        );

        this.copy(WTags.Blocks.TALL_CANDLES, WTags.Items.TALL_CANDLES);
    }
}