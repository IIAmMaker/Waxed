package net.im_maker.waxed.common.block.custom;

import net.im_maker.waxed.common.block.WBlocks;
import net.mehvahdjukaar.supplementaries.common.block.ModBlockProperties;
import net.mehvahdjukaar.supplementaries.common.block.blocks.RakedGravelBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class WaxedRakedGravelBlock extends RakedGravelBlock {
    public WaxedRakedGravelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = super.defaultBlockState();
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        if (!blockstate.canSurvive(world, pos)) {
            return Block.pushEntitiesUp(blockstate, Blocks.GRAVEL.defaultBlockState(), world, pos);
        } else {
            Direction front = context.getHorizontalDirection();
            return getConnectedState(blockstate, world, pos, front);
        }
    }

    private static boolean canConnect(BlockState state, Direction dir) {
        return state.getBlock() == WBlocks.WAXED_RAKED_GRAVEL.get() ?
                ((ModBlockProperties.RakeDirection)state.getValue(RAKE_DIRECTION)).getDirections().contains(dir.getOpposite()) : false;
    }

    public static BlockState getConnectedState(BlockState blockstate, LevelAccessor world, BlockPos pos, Direction front) {
        List<Direction> directionList = new ArrayList();
        Direction back = front.getOpposite();
        if (canConnect(world.getBlockState(pos.relative(back)), back)) {
            directionList.add(back);
        } else {
            directionList.add(front);
        }

        Direction side = front.getClockWise();

        for(int i = 0; i < 2; ++i) {
            BlockState state = world.getBlockState(pos.relative(side));
            if (canConnect(state, side)) {
                directionList.add(side);
                break;
            }
            side = side.getOpposite();
        }

        return blockstate.setValue(RAKE_DIRECTION, ModBlockProperties.RakeDirection.fromDirections(directionList));
    }

    // Override falling block behavior to prevent waxed raked gravel from falling
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        // Don't schedule a tick like the parent class does
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }
        return state;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            turnToGravel(state, level, pos);
        }
        // Don't call super.tick() to prevent falling block behavior
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        // No falling dust particles for waxed version
    }

    public static void turnToGravel(BlockState state, Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, Block.pushEntitiesUp(state, Blocks.GRAVEL.defaultBlockState(), level, pos));
    }
}