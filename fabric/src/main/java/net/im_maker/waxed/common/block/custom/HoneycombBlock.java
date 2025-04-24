package net.im_maker.waxed.common.block.custom;

import net.im_maker.waxed.common.block.WBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class HoneycombBlock extends Block {

    public HoneycombBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(Items.GLASS_BOTTLE)) {
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
    }
}