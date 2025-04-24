package net.im_maker.waxed;


import net.im_maker.waxed.common.block.WBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Waxed.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class WaxedClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        renderBlock(WBlocks.WICK.get(), 2);
        renderBlock(WBlocks.SOUL_WICK.get(), 2);
        renderBlock(WBlocks.CUPRIC_WICK.get(), 2);
        renderBlock(WBlocks.ENDER_WICK.get(), 2);

        renderBlock(WBlocks.TALL_CANDLE.get(), 2);
        renderBlock(WBlocks.SOUL_TALL_CANDLE.get(), 2);
        renderBlock(WBlocks.CUPRIC_TALL_CANDLE.get(), 2);
        renderBlock(WBlocks.ENDER_TALL_CANDLE.get(), 2);
        for (DyeColor color : DyeColor.values()) {
            renderBlock(WBlocks.COLORED_TALL_CANDLE.get(color.getId()).get(), 2);
        }

        renderBlock(WBlocks.WAX_PILLAR.get(), 2);
        renderBlock(WBlocks.SOUL_WAX_PILLAR.get(), 2);
        renderBlock(WBlocks.CUPRIC_WAX_PILLAR.get(), 2);
        renderBlock(WBlocks.ENDER_WAX_PILLAR.get(), 2);
        for (DyeColor color : DyeColor.values()) {
            renderBlock(WBlocks.COLORED_WAX_PILLAR.get(color.getId()).get(), 2);
        }

        renderBlock(WBlocks.WAXED_COBWEB.get(), 2);
        renderBlock(WBlocks.WAXED_ICE.get(), 1);
        renderBlock(WBlocks.WAXED_GROOVED_ICE.get(), 1);

        renderBlock(WBlocks.WAXED_TUBE_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_BRAIN_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_BUBBLE_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_FIRE_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_HORN_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_TUBE_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_BRAIN_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_BUBBLE_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_FIRE_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_HORN_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_TUBE_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_BRAIN_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_BUBBLE_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_FIRE_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_HORN_CORAL_WALL_FAN.get(), 2);

        renderBlock(WBlocks.WAXED_ACAN_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_FINGER_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_STAR_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_MOSS_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_PETAL_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_BRANCH_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_ROCK_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_PILLOW_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_SILK_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_CHROME_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_PRISMARINE_CORAL.get(), 2);
        renderBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL.get(), 2);

        renderBlock(WBlocks.WAXED_ACAN_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_FINGER_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_STAR_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_MOSS_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_PETAL_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_BRANCH_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_ROCK_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_PILLOW_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_SILK_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_CHROME_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_PRISMARINE_CORAL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_FAN.get(), 2);

        renderBlock(WBlocks.WAXED_ACAN_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_FINGER_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_STAR_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_MOSS_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_PETAL_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_BRANCH_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_ROCK_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_PILLOW_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_SILK_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_CHROME_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_PRISMARINE_CORAL_WALL_FAN.get(), 2);
        renderBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_WALL_FAN.get(), 2);

        renderBlock(WBlocks.WAXED_PRISMARINE_CORAL_SHOWER.get(), 2);
        renderBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_SHOWER.get(), 2);

        renderBlock(WBlocks.SOUL_CANDLE_HOLDER.get(), 2);
        renderBlock(WBlocks.GOLD_SOUL_CANDLE_HOLDER.get(), 2);
        renderBlock(WBlocks.CUPRIC_CANDLE_HOLDER.get(), 2);
        renderBlock(WBlocks.GOLD_CUPRIC_CANDLE_HOLDER.get(), 2);
        renderBlock(WBlocks.ENDER_CANDLE_HOLDER.get(), 2);
        renderBlock(WBlocks.GOLD_ENDER_CANDLE_HOLDER.get(), 2);
    }
    private static void renderBlock (Block block, int i) {
        RenderType renderType = i == 1 ? RenderType.translucent() : i == 2 ? RenderType.cutout() : RenderType.solid();
        ItemBlockRenderTypes.setRenderLayer(block, renderType);
    }
}
