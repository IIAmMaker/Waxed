package net.im_maker.waxed.common.block.custom;

import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.util.WTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class WickBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final ToIntFunction<BlockState> LIGHT_EMISSION = (blockState) -> blockState.getValue(LIT) ? 15 : 0;
    public static final ToIntFunction<BlockState> SOUL_LIGHT_EMISSION = (blockState) -> blockState.getValue(LIT) ? 10 : 0;
    private static final VoxelShape Shape = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 12.0D, 11.0D);
    private final int fireDamage;
    public WickBlock(int pFireDamage, Properties properties) {
        super(properties);
        this.fireDamage = pFireDamage;
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(false)));
    }

    private static void setLit(LevelAccessor pLevel, BlockState pState, BlockPos pPos, boolean pLit) {
        pLevel.setBlock(pPos, pState.setValue(LIT, Boolean.valueOf(pLit)), 11);
    }

    protected boolean canBeLit(BlockState pState) {
        return !pState.getValue(LIT);
    }

    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pState.getValue(LIT) && pEntity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)pEntity)) {
            pEntity.hurt(pLevel.damageSources().inFire(), (float)this.fireDamage);
            //pEntity.setSecondsOnFire(2);
        }
        if (!pLevel.isClientSide && pEntity instanceof Projectile && pEntity.isOnFire() && this.canBeLit(pState)) {
            setLit(pLevel, pState, pPos, true);
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(WBlocks.WICK.get());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockPos blockBelowPos = pPos.below();
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if ((itemStack.is(Items.FLINT_AND_STEEL) || itemStack.is(Items.FIRE_CHARGE)) && !pState.getValue(LIT)) {
            Item item = itemStack.getItem();
            if (item.equals(Items.FLINT_AND_STEEL)) {
                itemStack.hurtAndBreak(1, pPlayer, (player) -> {
                    player.broadcastBreakEvent(pHand);
                });
                pLevel.playSound(pPlayer, pPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, pLevel.getRandom().nextFloat() * 0.4F + 0.8F);
            } else if (item.equals(Items.FIRE_CHARGE)) {
                if (!pPlayer.isCreative()) {
                    itemStack.shrink(1);
                }
                RandomSource randomsource = pLevel.getRandom();
                pLevel.playSound((Player) null, pPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, (randomsource.nextFloat() - randomsource.nextFloat()) * 0.2F + 1.0F);
            }

           setLit(pLevel, pState, pPos, true);
           pLevel.updateNeighborsAt(blockBelowPos, pLevel.getBlockState(blockBelowPos).getBlock());
           return InteractionResult.sidedSuccess(pLevel.isClientSide);
       }
       if (pPlayer.getAbilities().mayBuild && pPlayer.getItemInHand(pHand).isEmpty() && pState.getValue(LIT)) {
           setLit(pLevel, pState, pPos, false);
           pLevel.addParticle(ParticleTypes.SMOKE, pPos.getX() + 0.5D, pPos.getY() + 0.8D, pPos.getZ() + 0.5D, 0.0D, 0.1F, 0.0D);
           pLevel.playSound((Player)null, pPos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
           return InteractionResult.sidedSuccess(pLevel.isClientSide);
       }
        return InteractionResult.PASS;
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            Vec3 vec3 = new Vec3(pPos.getX() + 0.5D, pPos.getY() + 0.9D, pPos.getZ() + 0.5D);
            addParticlesAndSound(pLevel, vec3, pRandom);
        }
    }

    private static void addParticlesAndSound(Level pLevel, Vec3 pOffset, RandomSource pRandom) {
        float f = pRandom.nextFloat();
        if (f < 0.7F) {
            pLevel.addParticle(ParticleTypes.LARGE_SMOKE, pOffset.x, pOffset.y, pOffset.z, 0.0D, 0.0D, 0.0D);
            if (f < 0.4F) {
                pLevel.playLocalSound(pOffset.x + 0.5D, pOffset.y + 0.5D, pOffset.z + 0.5D, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + pRandom.nextFloat(), pRandom.nextFloat() * 0.7F + 0.3F, false);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shape;
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState1, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos1) {
        return direction == Direction.DOWN && !this.canSurvive(blockState, levelAccessor, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, blockState1, levelAccessor, blockPos, blockPos1);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockPos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        BlockPos blockPosBelow = blockPos.below();
        BlockState blockStateBelow = level.getBlockState(blockPosBelow);
        return blockStateBelow.is(WBlocks.SOUL_WAX_PILLAR.get()) ? WBlocks.SOUL_WICK.get().defaultBlockState()
                : blockStateBelow.is(WBlocks.CUPRIC_WAX_PILLAR.get()) ? WBlocks.CUPRIC_WICK.get().defaultBlockState()
                : blockStateBelow.is(WBlocks.ENDER_WAX_PILLAR.get()) ? WBlocks.ENDER_WICK.get().defaultBlockState()
                : super.getStateForPlacement(pContext);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPosBelow = blockPos.below();
        BlockState blockStateBelow = levelReader.getBlockState(blockPosBelow);
        return (blockStateBelow.is(WTags.Blocks.WAX_PILLARS) && (blockStateBelow.getValue(BlockStateProperties.AXIS) == Direction.Axis.Y) && canSupportCenter(levelReader, blockPosBelow, Direction.UP));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
    }
}