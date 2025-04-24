package net.im_maker.waxed.common.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SoulCandleCakeBlock extends CandleCakeBlock {
    private final ResourceLocation particle;
    public SoulCandleCakeBlock(Block pCandleBlock, Properties pProperties, ResourceLocation particle) {
        super(pCandleBlock, pProperties);
        this.particle = particle;
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            this.getParticleOffsets(pState).forEach((vec3) -> {
                addParticlesAndSound(pLevel, particle, vec3.add((double)pPos.getX(), (double)pPos.getY(), (double)pPos.getZ()), pRandom);
            });
        }
    }

    private static void addParticlesAndSound(Level pLevel, ResourceLocation particleType, Vec3 pOffset, RandomSource pRandom) {
        float f = pRandom.nextFloat();
        if (f < 0.3F) {
            pLevel.addParticle(ParticleTypes.SMOKE, pOffset.x, pOffset.y, pOffset.z, 0.0D, 0.0D, 0.0D);
            if (f < 0.17F) {
                pLevel.playLocalSound(pOffset.x + 0.5D, pOffset.y + 0.5D, pOffset.z + 0.5D, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + pRandom.nextFloat(), pRandom.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        ParticleType<?> options = BuiltInRegistries.PARTICLE_TYPE.get(particleType);
        pLevel.addParticle((ParticleOptions)options, pOffset.x, pOffset.y, pOffset.z, 0.0D, 0.0D, 0.0D);
    }
}