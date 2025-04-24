package net.im_maker.waxed.common.block.custom;

import net.fabricmc.loader.api.FabricLoader;
import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.particles.WParticles;
import net.mehvahdjukaar.supplementaries.common.block.blocks.CandleHolderBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class CandleHolders {

   public static final Supplier<Block> SOUL_CANDLE_HOLDER = () -> new CandleHolderBlock(DyeColor.BROWN, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY), WParticles.SMALL_SOUL_FLAME::getType);
   public static final Supplier<Block> GOLD_SOUL_CANDLE_HOLDER = () -> new CandleHolderBlock(DyeColor.BROWN, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY), WParticles.SMALL_SOUL_FLAME::getType);

   public static final Supplier<Block> WAXED_RAKED_GRAVEL = () -> new WaxedRakedGravelBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL));

    private static <T extends Block> Block registerBlockS(String name, Block block) {
        if (FabricLoader.getInstance().isModLoaded("supplementaries")) {
            Block toReturn = WBlocks.registerBlockS(name, block);
            WBlocks.registerBlockItem(name, toReturn);
            return toReturn;
        } else {
            Block toReturn = WBlocks.registerBlock(name, block);
            WBlocks.registerBlockItem(name, toReturn);
            return toReturn;
        }
    }
}