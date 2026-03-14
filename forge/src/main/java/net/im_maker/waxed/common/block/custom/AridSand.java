package net.im_maker.waxed.common.block.custom;

import com.teamabnormals.atmospheric.core.registry.AtmosphericBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class AridSand {
   public static final Supplier<Block> WAXED_ARID_SAND = () -> new WaxedAridSandBlock(14406560, BlockBehaviour.Properties.copy(AtmosphericBlocks.ARID_SAND.get()));
   public static final Supplier<Block> WAXED_RED_ARID_SAND = () -> new WaxedAridSandBlock(16241568, BlockBehaviour.Properties.copy(AtmosphericBlocks.RED_ARID_SAND.get()));
}