package net.im_maker.waxed.common.block.custom;

import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.block.block_values.WBlockStateProperties;
import net.im_maker.waxed.common.util.WTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class WaxPillarBlock extends RotatedPillarBlock {
    public static final BooleanProperty MELTED = WBlockStateProperties.MELTED;

    public WaxPillarBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MELTED, false));
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        BlockPos blockPosAbove = pPos.above();
        BlockState blockStateAbove = pLevel.getBlockState(blockPosAbove);
        if (blockStateAbove.getBlock() instanceof WickBlock && blockStateAbove.getValue(BlockStateProperties.LIT)) {
            pLevel.setBlock(pPos, pState.setValue(MELTED, true), 11);
        } else if (blockStateAbove.is(WTags.Blocks.WAX_PILLARS)){
            pLevel.setBlock(pPos, pState.setValue(MELTED, false), 11);
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS);
        pBuilder.add(MELTED);
    }
}