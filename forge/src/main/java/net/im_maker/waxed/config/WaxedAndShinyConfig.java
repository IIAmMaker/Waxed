package net.im_maker.waxed.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class WaxedAndShinyConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ConfigValue<Boolean> GENERATE_WAXING_RECIPES;
    public static final ConfigValue<Boolean> WAX_AND_WAX_BLOCKS;
    public static final ConfigValue<Boolean> CANDLE_VARIANTS;
    public static final ConfigValue<Boolean> TALL_CANDLES;
    public static final ConfigValue<Boolean> WAXED_BLOCKS;

    static {
        BUILDER.push("Configs for Waxed & Shiny");
        GENERATE_WAXING_RECIPES = BUILDER
                .comment("Auto generate the waxing recipes with wax")
                .comment("Default: true")
                .define("generate_waxing_recipes", true);
        WAX_AND_WAX_BLOCKS = BUILDER
                .comment("Wax, Wax Blocks and Wax Pillar")
                .comment("Default: true")
                .define("wax_and_wax_blocks", true);
        CANDLE_VARIANTS = BUILDER
                .comment("More Candle variants (soul, cupric, ender)")
                .comment("Default: true")
                .define("candle_variants", true);
        TALL_CANDLES = BUILDER
                .comment("Tall Candles")
                .comment("Default: true")
                .define("tall_candles", true);
        WAXED_BLOCKS = BUILDER
                .comment("Waxed Blocks")
                .comment("Default: true")
                .define("waxed_blocks", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}