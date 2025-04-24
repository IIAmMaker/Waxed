package net.im_maker.waxed.common.sounds;

import net.im_maker.waxed.Waxed;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Waxed.MOD_ID);

    public static final RegistryObject<SoundEvent> SHOVEL_WAX_OFF = registerSoundEvents("item.shovel.wax_off");
    public static final RegistryObject<SoundEvent> HOE_WAX_OFF = registerSoundEvents("item.hoe.wax_off");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Waxed.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}