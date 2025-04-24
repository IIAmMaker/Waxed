package net.im_maker.waxed.common.sounds;

import net.im_maker.waxed.Waxed;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class WSounds {
    public static final SoundEvent SHOVEL_WAX_OFF = registerSoundEvent("item.shovel.wax_off");
    public static final SoundEvent HOE_WAX_OFF = registerSoundEvent("item.hoe.wax_off");

    private static SoundEvent registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(Waxed.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void registerSoundEvents() {
        Waxed.LOGGER.info("Registering Mod Sound Events for " + Waxed.MOD_ID);
    }
}