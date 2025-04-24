package net.im_maker.waxed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.particles.WParticles;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

public class WaxedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register block render layers
        registerBlockRenderLayers();
        ParticleFactoryRegistry.getInstance().register(
                WParticles.SMALL_SOUL_FLAME,
                FlameParticle.SmallFlameProvider::new
        );
    }

    private void registerBlockRenderLayers() {
        renderBlock(WBlocks.WICK, 2);
        renderBlock(WBlocks.SOUL_WICK, 2);

        renderBlock(WBlocks.TALL_CANDLE, 2);
        renderBlock(WBlocks.SOUL_TALL_CANDLE, 2);
        for (DyeColor color : DyeColor.values()) {
            renderBlock(WBlocks.COLORED_TALL_CANDLE.get(color.getId()), 2);
        }

        renderBlock(WBlocks.WAX_PILLAR, 2);
        renderBlock(WBlocks.SOUL_WAX_PILLAR, 2);
        for (DyeColor color : DyeColor.values()) {
            renderBlock(WBlocks.COLORED_WAX_PILLAR.get(color.getId()), 2);
        }

        renderBlock(WBlocks.WAXED_ICE, 1);

        // Coral blocks
        renderBlock(WBlocks.WAXED_TUBE_CORAL, 2);
        renderBlock(WBlocks.WAXED_BRAIN_CORAL, 2);
        renderBlock(WBlocks.WAXED_BUBBLE_CORAL, 2);
        renderBlock(WBlocks.WAXED_FIRE_CORAL, 2);
        renderBlock(WBlocks.WAXED_HORN_CORAL, 2);
        renderBlock(WBlocks.WAXED_TUBE_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_BRAIN_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_BUBBLE_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_FIRE_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_HORN_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_TUBE_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_BRAIN_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_BUBBLE_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_FIRE_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_HORN_CORAL_WALL_FAN, 2);

        // Custom coral blocks
        renderBlock(WBlocks.WAXED_ACAN_CORAL, 2);
        renderBlock(WBlocks.WAXED_FINGER_CORAL, 2);
        renderBlock(WBlocks.WAXED_STAR_CORAL, 2);
        renderBlock(WBlocks.WAXED_MOSS_CORAL, 2);
        renderBlock(WBlocks.WAXED_PETAL_CORAL, 2);
        renderBlock(WBlocks.WAXED_BRANCH_CORAL, 2);
        renderBlock(WBlocks.WAXED_ROCK_CORAL, 2);
        renderBlock(WBlocks.WAXED_PILLOW_CORAL, 2);
        renderBlock(WBlocks.WAXED_SILK_CORAL, 2);
        renderBlock(WBlocks.WAXED_CHROME_CORAL, 2);
        renderBlock(WBlocks.WAXED_PRISMARINE_CORAL, 2);
        renderBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL, 2);

        // Custom coral fans
        renderBlock(WBlocks.WAXED_ACAN_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_FINGER_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_STAR_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_MOSS_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_PETAL_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_BRANCH_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_ROCK_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_PILLOW_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_SILK_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_CHROME_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_PRISMARINE_CORAL_FAN, 2);
        renderBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_FAN, 2);

        // Custom coral wall fans
        renderBlock(WBlocks.WAXED_ACAN_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_FINGER_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_STAR_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_MOSS_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_PETAL_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_BRANCH_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_ROCK_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_PILLOW_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_SILK_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_CHROME_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_PRISMARINE_CORAL_WALL_FAN, 2);
        renderBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_WALL_FAN, 2);

        // Coral showers
        renderBlock(WBlocks.WAXED_PRISMARINE_CORAL_SHOWER, 2);
        renderBlock(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_SHOWER, 2);

        // Candle holders
        renderBlock(WBlocks.SOUL_CANDLE_HOLDER, 2);
        renderBlock(WBlocks.GOLD_SOUL_CANDLE_HOLDER, 2);
    }

    private static void renderBlock(Block block, int i) {
        RenderType renderLayer = i == 1 ? RenderType.translucent() :
                i == 2 ? RenderType.cutout() :
                        RenderType.solid();
        BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
    }
}