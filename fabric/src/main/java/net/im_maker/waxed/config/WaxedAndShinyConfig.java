package net.im_maker.waxed.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "waxed_and_shiny-common")
public class WaxedAndShinyConfig implements ConfigData {

    @ConfigEntry.Category("Recipes")
    @ConfigEntry.Gui.Tooltip
    public boolean generateWaxingRecipes = true;
}
