package net.im_maker.waxed.common.block.custom;

import net.im_maker.waxed.common.block.block_values.CandlePart;
import net.im_maker.waxed.common.block.block_values.WBlockStateProperties;
import net.im_maker.waxed.common.particles.WParticles;
import net.im_maker.waxed.common.util.WTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TallCandleBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty MELTED = WBlockStateProperties.MELTED;
    public static final EnumProperty<CandlePart> CANDLE_PART = WBlockStateProperties.CANDLE_PART;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHORT_AABB = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D);
    private static final VoxelShape TALL_AABB = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    private final ResourceLocation particle;

    public TallCandleBlock(boolean isSoul, ResourceLocation particle, Properties properties) {
        // Create properties with light level first
        super(createProperties(isSoul, properties));
        this.particle = particle;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(MELTED, false)
                .setValue(LIT, false)
                .setValue(CANDLE_PART, CandlePart.SHORT)
                .setValue(WATERLOGGED, false));
    }

    public TallCandleBlock (Properties properties) {
        this(false, WParticles.PARTICLES.FLAME, properties);
    }

    //public TallCandleBlock(boolean isSoul, ResourceLocation particleType, Properties properties) {
    //    this(isSoul, (ParticleOptions)BuiltInRegistries.PARTICLE_TYPE.get(particleType), properties);
    //}

    private static Properties createProperties(boolean isSoul, Properties properties) {
        return properties.lightLevel(blockState -> {
            if (blockState.getValue(LIT)) {
                return blockState.getValue(CANDLE_PART).equals(CandlePart.MIDDLE) ? 1 : !isSoul ? 12 : 8;
            }
            return 0;
        });
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        // Handle candle stacking
        if (heldItem.is(this.asItem()) && hit.getDirection() == Direction.UP && state.getValue(CANDLE_PART) == CandlePart.SHORT) {
            boolean aboveStateIsTallCandle = level.getBlockState(pos.above()).is(WTags.Blocks.TALL_CANDLES);
            boolean isShortLit = level.getBlockState(pos).getValue(LIT);
            CandlePart newPart = aboveStateIsTallCandle ? CandlePart.MIDDLE : CandlePart.TALL;
            boolean willBeLit = (aboveStateIsTallCandle ? level.getBlockState(pos.above()).getValue(LIT) : false) || isShortLit;
            level.setBlock(pos, state.setValue(CANDLE_PART, newPart).setValue(LIT, willBeLit), 3);
            if (!player.isCreative()) heldItem.shrink(1);
            level.playSound(player, pos, SoundEvents.CANDLE_PLACE, SoundSource.BLOCKS);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        // Handle lighting
        if (canLight(state) && (heldItem.is(Items.FLINT_AND_STEEL) || heldItem.is(Items.FIRE_CHARGE))) {
            if (heldItem.is(Items.FLINT_AND_STEEL)) {
                heldItem.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            } else {
                if (!player.isCreative()) heldItem.shrink(1);
                RandomSource rand = level.getRandom();
                level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }
            level.setBlock(pos, state.setValue(LIT, true), 3);
            level.updateNeighborsAt(pos.below(), level.getBlockState(pos.below()).getBlock());
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        // Handle extinguishing
        if (player.getAbilities().mayBuild && heldItem.isEmpty() && state.getValue(LIT) && !state.getValue(CANDLE_PART).equals(CandlePart.MIDDLE)) {
            level.setBlock(pos, state.setValue(LIT, false), 3);
            double yOffset = state.getValue(CANDLE_PART).equals(CandlePart.SHORT) ? 0.5D : 0.0D;
            level.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.1875D - yOffset, pos.getZ() + 0.5D, 0.0D, 0.1F, 0.0D);
            level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    //@Override
    //public boolean canBeReplaced(BlockState blockState, BlockPlaceContext context) {
    //    // Allow replacement if:
    //    // 1. Not sneaking
    //    // 2. Holding the same candle
    //    // 3. Current candle is SHORT
    //    return !context.isSecondaryUseActive()
    //            && context.getItemInHand().is(this.asItem())
    //            && blockState.getValue(CANDLE_PART) == CandlePart.SHORT;
    //}

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.getValue(WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
            BlockState newState = state.setValue(WATERLOGGED, true);
            if (state.getValue(LIT)) {
                level.setBlock(pos, newState.setValue(LIT, false), 3);
                level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS);
            } else {
                level.setBlock(pos, newState, 3);
            }
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            return true;
        }
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos clickedPos = context.getClickedPos();
        Level level = context.getLevel();
        BlockPos belowPos = clickedPos.below();
        BlockState blockState = level.getBlockState(clickedPos);
        BlockState belowState = level.getBlockState(belowPos);
        FluidState fluidstate = level.getFluidState(clickedPos);
        boolean waterlogged = fluidstate.getType() == Fluids.WATER;

        if (belowState.is(WTags.Blocks.TALL_CANDLES)) {

            CandlePart belowPart = belowState.getValue(CANDLE_PART);
            if (belowPart != CandlePart.SHORT) {
                level.setBlock(belowPos, belowState.setValue(CANDLE_PART, CandlePart.MIDDLE), Block.UPDATE_ALL);
                if (belowState.getValue(LIT) && !belowState.getValue(CANDLE_PART).equals(CandlePart.SHORT)) {
                    return this.defaultBlockState().setValue(CANDLE_PART, CandlePart.SHORT).setValue(LIT, true).setValue(WATERLOGGED, waterlogged);
                }
                return this.defaultBlockState()
                        .setValue(CANDLE_PART, CandlePart.SHORT)
                        .setValue(WATERLOGGED, waterlogged);
            }
        }

        System.out.println(belowState.is(WTags.Blocks.TALL_CANDLES) && belowState.getValue(LIT) && !belowState.getValue(CANDLE_PART).equals(CandlePart.SHORT));


        // Default placement (always SHORT candle)
        return this.defaultBlockState()
                .setValue(CANDLE_PART, CandlePart.SHORT)
                .setValue(WATERLOGGED, waterlogged);
    }


    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos blockPos, BlockPos neighborPos) {
        if (blockState.getValue(WATERLOGGED)) {
            level.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        if (!level.getBlockState(blockPos.above()).is(WTags.Blocks.TALL_CANDLES) && blockState.getValue(CANDLE_PART).equals(CandlePart.MIDDLE)) {
            return blockState.setValue(CANDLE_PART, CandlePart.TALL);
        }
        if (level.getBlockState(blockPos.above()).is(WTags.Blocks.TALL_CANDLES) && level.getBlockState(blockPos.above()).getValue(LIT) ) {
            return blockState.setValue(LIT, true);
        }
        if (level.getBlockState(blockPos.above()).is(WTags.Blocks.TALL_CANDLES) && !level.getBlockState(blockPos.above()).getValue(LIT) ) {
            return blockState.setValue(LIT, false);
        }
        return blockState;
    }

    private static void setLit(LevelAccessor level, BlockState blockState, BlockPos blockPos, boolean pLit) {
        level.setBlock(blockPos, blockState.setValue(LIT, Boolean.valueOf(pLit)), 11);
    }

    protected boolean canBeLit(BlockState blockState) {
        return !blockState.getValue(LIT) && !blockState.getValue(CANDLE_PART).equals(CandlePart.MIDDLE);
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (blockState.getValue(CANDLE_PART).equals(CandlePart.SHORT)) {
            return SHORT_AABB;
        }
        return TALL_AABB;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public static boolean canLight(BlockState blockState) {
        // Check if it's a candle block with required properties
        if (!blockState.hasProperty(LIT) ||
                !blockState.hasProperty(WATERLOGGED)) {
            return false;
        }

        // Combine all lighting conditions
        return !blockState.getValue(CANDLE_PART).equals(CandlePart.MIDDLE) &&
                !blockState.getValue(LIT) &&
                !blockState.getValue(WATERLOGGED);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS);
        pBuilder.add(LIT);
        pBuilder.add(MELTED);
        pBuilder.add(CANDLE_PART);
        pBuilder.add(WATERLOGGED);
    }


    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(LIT)) {
            Vec3 vec3 = new Vec3(blockPos.getX() + 0.5D, blockPos.getY() + 1.21875D - (blockState.getValue(CANDLE_PART).equals(CandlePart.SHORT) ? 0.5D : 0.0D), blockPos.getZ() + 0.5D);
            addParticlesAndSound(level, particle, vec3, randomSource);
        }
    }

    private void addParticlesAndSound(Level level, ResourceLocation particleType, Vec3 vec3, RandomSource randomSource) {
        float f = randomSource.nextFloat();
        if (f < 0.3F) {
            level.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, 0.0D, 0.0D, 0.0D);
            if (f < 0.17F) {
                level.playLocalSound(vec3.x + 0.5D, vec3.y + 0.5D, vec3.z + 0.5D, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + randomSource.nextFloat(), randomSource.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        ParticleType<?> options = BuiltInRegistries.PARTICLE_TYPE.get(particleType);
        level.addParticle((ParticleOptions)options, vec3.x, vec3.y, vec3.z, 0.0D, 0.0D, 0.0D);
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return Block.canSupportCenter(levelReader, blockPos.below(), Direction.UP);
    }
}
