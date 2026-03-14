package net.im_maker.waxed.mixin;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(HoneycombItem.class)
public class HoneycombItemMixin {

    @Shadow @Final @Mutable
    private static Supplier<BiMap<Block, Block>> WAXABLES;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void waxed$addCustomWaxables(CallbackInfo ci) {
        BiMap<Block, Block> original = WAXABLES.get();
        ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
        builder.putAll(original);
        //builder.put(Blocks.BAMBOO_BLOCK, Blocks.DARK_OAK_PLANKS);
        BiMap<Block, Block> newMap = builder.build();
        WAXABLES = Suppliers.memoize(() -> newMap);
    }
}