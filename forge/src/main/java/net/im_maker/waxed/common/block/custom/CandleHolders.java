package net.im_maker.waxed.common.block.custom;

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
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CandleHolders {

   public static final Supplier<Block> SOUL_CANDLE_HOLDER = () -> new CandleHolderBlock(DyeColor.BROWN, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY), WParticles.SMALL_SOUL_FLAME::get);
   public static final Supplier<Block> GOLD_SOUL_CANDLE_HOLDER = () -> new CandleHolderBlock(DyeColor.BROWN, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY), WParticles.SMALL_SOUL_FLAME::get);

   public static final Supplier<Block> CUPRIC_CANDLE_HOLDER = () -> new CandleHolderBlock(DyeColor.BROWN, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY), WParticles.SMALL_CUPRIC_FLAME::get);
   public static final Supplier<Block> GOLD_CUPRIC_CANDLE_HOLDER = () -> new CandleHolderBlock(DyeColor.BROWN, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY), WParticles.SMALL_CUPRIC_FLAME::get);

   public static final Supplier<Block> ENDER_CANDLE_HOLDER = () -> new CandleHolderBlock(DyeColor.BROWN, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY), WParticles.SMALL_ENDER_FLAME::get);
   public static final Supplier<Block> GOLD_ENDER_CANDLE_HOLDER = () -> new CandleHolderBlock(DyeColor.BROWN, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollission().instabreak().sound(SoundType.LANTERN).pushReaction(PushReaction.DESTROY), WParticles.SMALL_ENDER_FLAME::get);

   public static final Supplier<Block> WAXED_RAKED_GRAVEL = () -> new WaxedRakedGravelBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL));

    private static <T extends Block> RegistryObject<T> registerBlockS(String name, Supplier<T> block) {
        if (ModList.get().isLoaded("supplementaries")) {
            RegistryObject<T> toReturn = WBlocks.BLOCKS_S.register(name, block);
            WBlocks.registerBlockItem(name, toReturn);
            return toReturn;
        } else {
            RegistryObject<T> toReturn = WBlocks.BLOCKS.register(name, block);
            WBlocks.registerBlockItem(name, toReturn);
            return toReturn;
        }
    }
}