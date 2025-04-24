package net.im_maker.waxed.common.util;

import galena.oreganized.content.item.ScribeItem;
import net.im_maker.waxed.common.block.WBlocks;
import net.minecraftforge.fml.ModList;

public class GroovingWithScribe {

    public static void registerGroovedBlocks() {
        if (ModList.get().isLoaded("oreganized")) {
            ScribeItem.registerGroovedBlock(WBlocks.WAXED_ICE.get(), WBlocks.WAXED_GROOVED_ICE);
        }
    }
}