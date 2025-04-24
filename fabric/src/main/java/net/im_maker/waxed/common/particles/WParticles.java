package net.im_maker.waxed.common.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.im_maker.waxed.Waxed;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class WParticles {

    public static final SimpleParticleType SMALL_SOUL_FLAME = registerSimpleParticleType("small_soul_flame", false);

    public static class PARTICLES {
        public static final ResourceLocation SMALL_SOUL_FLAME = new ResourceLocation("waxed", "small_soul_flame");
        public static final ResourceLocation SOUL_FLAME = new ResourceLocation("minecraft", "soul_fire_flame");
        public static final ResourceLocation FLAME = new ResourceLocation("minecraft", "flame");
    }

    private static SimpleParticleType registerSimpleParticleType(String name, boolean alwaysSpawn) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(Waxed.MOD_ID, name), FabricParticleTypes.simple(alwaysSpawn));
    }

    public static void registerParticles() {
        Waxed.LOGGER.info("Registering Mod Particles for " + Waxed.MOD_ID);
    }
}
