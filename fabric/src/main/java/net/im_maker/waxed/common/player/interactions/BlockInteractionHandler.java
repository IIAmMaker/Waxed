package net.im_maker.waxed.common.player.interactions;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.im_maker.waxed.common.block.WBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class BlockInteractionHandler {
    public static void register() {
        UseBlockCallback.EVENT.register((player, level, interactionHand, hitResult) -> {
            if (level.getBlockState(hitResult.getBlockPos()).getBlock() == Blocks.HONEYCOMB_BLOCK
                    && player.getItemInHand(interactionHand).getItem() == Items.GLASS_BOTTLE) {
                ItemStack itemStack = player.getItemInHand(interactionHand);
                BlockPos blockPos = hitResult.getBlockPos();
                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    player.setItemInHand(player.getUsedItemHand(), new ItemStack(Items.HONEY_BOTTLE));
                } else if (!player.getInventory().add(new ItemStack(Items.HONEY_BOTTLE))) {
                    player.drop(new ItemStack(Items.HONEY_BOTTLE), false);
                }
                level.setBlock(blockPos, WBlocks.EMPTY_HONEYCOMB.defaultBlockState(), 11);
                level.playSound(player, blockPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            return InteractionResult.PASS;
        });
    }
}