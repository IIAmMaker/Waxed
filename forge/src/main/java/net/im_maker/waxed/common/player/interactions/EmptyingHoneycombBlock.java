package net.im_maker.waxed.common.player.interactions;

import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.block.WBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Waxed.MOD_ID)
public class EmptyingHoneycombBlock {

    //@SubscribeEvent
    public static InteractionResult emptyingHoneycombBlock(final PlayerInteractEvent.RightClickBlock interactEvent) {
        Level level = interactEvent.getLevel();
        BlockPos blockPos = interactEvent.getPos();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack itemStack = interactEvent.getItemStack();
        Player player = interactEvent.getEntity();
        if (interactEvent.getHand() == InteractionHand.MAIN_HAND && itemStack.is(Items.GLASS_BOTTLE) && blockState.is(Blocks.HONEYCOMB_BLOCK)) {
            s(level, player, interactEvent, blockPos, itemStack, InteractionHand.MAIN_HAND);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (interactEvent.getHand() == InteractionHand.OFF_HAND && itemStack.is(Items.GLASS_BOTTLE) && blockState.is(Blocks.HONEYCOMB_BLOCK)) {
            s(level, player, interactEvent, blockPos, itemStack, InteractionHand.OFF_HAND);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return (InteractionResult.PASS);
        }
    }

    private static void s(Level level, Player player, PlayerInteractEvent.RightClickBlock interactEvent, BlockPos blockPos, ItemStack itemStack, InteractionHand interactionHand) {
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
        }
        interactEvent.getEntity().swing(interactionHand);
        if (!level.isClientSide()) itemStack.shrink(1);
        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (itemStack.isEmpty()) {
            if (!level.isClientSide()) player.setItemInHand(player.getUsedItemHand(), new ItemStack(Items.HONEY_BOTTLE));
        } else if (!player.getInventory().add(new ItemStack(Items.HONEY_BOTTLE))) {
            if (!level.isClientSide()) player.drop(new ItemStack(Items.HONEY_BOTTLE), false);
        }
        if (!level.isClientSide()) level.setBlock(blockPos, WBlocks.EMPTY_HONEYCOMB.get().defaultBlockState(), 11);
    }

    //@SubscribeEvent
    //public static void blockItemInteractions(PlayerInteractEvent.RightClickBlock event) {
    //    Level level = event.getLevel();
    //    BlockPos blockPos = event.getPos();
    //    BlockState blockState = level.getBlockState(blockPos);
    //    ItemStack itemStack = event.getItemStack();
    //    if (itemStack.is(Items.GLASS_BOTTLE) && blockState.is(Blocks.HONEYCOMB_BLOCK)) {
    //        Player player = event.getEntity();
    //        if (player instanceof ServerPlayer) {
    //            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
    //        }
    //        event.getEntity().swing(event.getHand());
    //        if (!level.isClientSide()) level.setBlock(blockPos, WaxedModBlocks.EMPTY_HONEYCOMB.get().defaultBlockState(), 11);
    //        itemStack.shrink(1);
    //        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
    //        if (itemStack.isEmpty()) {
    //            player.setItemInHand(player.getUsedItemHand(), new ItemStack(Items.HONEY_BOTTLE));
    //        } else if (!player.getInventory().add(new ItemStack(Items.HONEY_BOTTLE))) {
    //            player.drop(new ItemStack(Items.HONEY_BOTTLE), false);
    //        }
    //    }
    //    if (itemStack.is(Items.HONEY_BOTTLE) && blockState.is(WaxedModBlocks.EMPTY_HONEYCOMB.get())) {
    //        Player player = event.getEntity();
    //        if (player instanceof ServerPlayer) {
    //            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
    //        }
    //        event.getEntity().swing(event.getHand());
    //        if (!level.isClientSide()) level.setBlock(blockPos, Blocks.HONEYCOMB_BLOCK.defaultBlockState(), 11);
    //        itemStack.shrink(1);
    //        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
    //        if (itemStack.isEmpty()) {
    //            player.setItemInHand(player.getUsedItemHand(), new ItemStack(Items.GLASS_BOTTLE));
    //        } else if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
    //            player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
    //        }
    //    }
    //}
    ////@SubscribeEvent
    ////public static InteractionResult fillingHoneycombBlock(final PlayerInteractEvent.RightClickBlock interactEvent) {
    ////    Level level = interactEvent.getLevel();
    ////    BlockPos blockPos = interactEvent.getPos();
    ////    BlockState blockState = level.getBlockState(blockPos);
    ////    ItemStack itemStack = interactEvent.getItemStack();
    ////    if (itemStack.is(Items.HONEY_BOTTLE) && blockState.is(WaxedModBlocks.EMPTY_HONEYCOMB.get())) {
    ////        Player player = interactEvent.getEntity();
    ////        if (player instanceof ServerPlayer) {
    ////            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
    ////        }
    ////        interactEvent.getEntity().swing(interactEvent.getHand());
    ////        itemStack.shrink(1);
    ////        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
    ////        if (itemStack.isEmpty()) {
    ////            player.setItemInHand(player.getUsedItemHand(), new ItemStack(Items.GLASS_BOTTLE));
    ////        } else if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
    ////            player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
    ////        }
    ////        if (!level.isClientSide()) level.setBlock(blockPos, Blocks.HONEYCOMB_BLOCK.defaultBlockState(), 11);
    ////        return InteractionResult.sidedSuccess(level.isClientSide);
    ////    } else {
    ////        return (InteractionResult.PASS);
    ////    }
    ////}
}