package net.im_maker.waxed.common.tags;

import net.im_maker.waxed.Waxed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class WTags {

    public static class Blocks {
        public static final TagKey<Block> WALLPAPER_BLOCKS = tag("wallpaper_blocks");

        public static TagKey<Block> create(ResourceLocation name) {
            return TagKey.create(Registries.BLOCK, name);
        }
        private static TagKey<Block> tag(String name) {
            return WTags.Blocks.create(new ResourceLocation(Waxed.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> WALLPAPER_BLOCKS = tag("wallpaper_blocks");

        public static TagKey<Item> create(ResourceLocation name) {
            return TagKey.create(Registries.ITEM, name);
        }
        private static TagKey<Item> tag(String name) {
            return WTags.Items.create(new ResourceLocation(Waxed.MOD_ID, name));
        }
    }
}