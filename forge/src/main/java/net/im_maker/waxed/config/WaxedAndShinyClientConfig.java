package net.im_maker.waxed.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class WaxedAndShinyClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ConfigValue<Boolean> SHINING_OVERLAY_ON_WAXED_BLOCKS;

    static {
        BUILDER.push("Configs for Waxed & Shiny");
        SHINING_OVERLAY_ON_WAXED_BLOCKS = BUILDER
                .comment("Showing an overlay effect on waxed blocks while holding honeycomb or wax")
                .comment("Default: true")
                .define("shining_overlay_on_waxed_blocks", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}