package net.im_maker.waxed.common.particles;

import net.im_maker.waxed.Waxed;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Waxed.MOD_ID);

    // Registering particle type.
    public static final RegistryObject<SimpleParticleType> SMALL_SOUL_FLAME = PARTICLE_TYPES.register("small_soul_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SMALL_CUPRIC_FLAME = PARTICLE_TYPES.register("small_cupric_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SMALL_ENDER_FLAME = PARTICLE_TYPES.register("small_ender_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CUPRIC_FLAME = PARTICLE_TYPES.register("cupric_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ENDER_FLAME = PARTICLE_TYPES.register("ender_flame", () -> new SimpleParticleType(false));

    public static class PARTICLES {
        public static final ResourceLocation SMALL_SOUL_FLAME = new ResourceLocation("waxed", "small_soul_flame");
        public static final ResourceLocation SMALL_CUPRIC_FLAME = new ResourceLocation("waxed", "small_cupric_flame");
        public static final ResourceLocation SMALL_ENDER_FLAME = new ResourceLocation("waxed", "small_ender_flame");
        public static final ResourceLocation CUPRIC_FLAME = new ResourceLocation("waxed", "cupric_flame");
        public static final ResourceLocation ENDER_FLAME = new ResourceLocation("waxed", "ender_flame");
        public static final ResourceLocation SOUL_FLAME = new ResourceLocation("minecraft", "soul_fire_flame");
        public static final ResourceLocation FLAME = new ResourceLocation("minecraft", "flame");
    }


    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus); // Safe for both server and client.
    }
}
