package net.im_maker.waxed.common.util;

import net.im_maker.waxed.Waxed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class WTags {
    public static class Blocks {
        public static final TagKey<Block> TALL_CANDLES = tag("tall_candles");
        public static final TagKey<Block> WAX_PILLARS = tag("wax_pillars");
        public static final TagKey<Block> WAXED_BLOCKS = tag("waxed_blocks");

        public static TagKey<Block> create(ResourceLocation name) {
            return TagKey.create(Registries.BLOCK, name);
        }

        private static TagKey<Block> tag(String name) {
            return create(new ResourceLocation(Waxed.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> TALL_CANDLES = tag("tall_candles");
        public static final TagKey<Item> WAX_PILLARS = tag("wax_pillars");
        public static final TagKey<Item> CAN_WAX = tag("can_wax");
        public static final TagKey<Item> CAN_SHOW_WAXED_BLOCKS = tag("can_show_waxed_blocks");
        public static final TagKey<Item> CANDLE_STRING = tag("candle_string");

        public static TagKey<Item> create(ResourceLocation name) {
            return TagKey.create(Registries.ITEM, name);
        }

        private static TagKey<Item> tag(String name) {
            return create(new ResourceLocation(Waxed.MOD_ID, name));
        }
    }
}