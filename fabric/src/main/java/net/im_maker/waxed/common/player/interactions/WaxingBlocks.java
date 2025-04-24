package net.im_maker.waxed.common.player.interactions;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.ninni.dye_depot.registry.DDDyes;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.item.WItems;
import net.im_maker.waxed.common.sounds.WSounds;
import net.im_maker.waxed.common.util.WTags;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class WaxingBlocks {

    public static final Supplier<BiMap<Block, Block>> WAXABLES_SHOVEL = Suppliers.memoize(() -> {
        BiMap<Block, Block> waxedBlocks = HashBiMap.create();
        waxedBlocks.put(Blocks.SAND, WBlocks.WAXED_SAND);
        waxedBlocks.put(Blocks.RED_SAND, WBlocks.WAXED_RED_SAND);
        waxedBlocks.put(Blocks.GRAVEL, WBlocks.WAXED_GRAVEL);
        waxedBlocks.put(Blocks.SPONGE, WBlocks.WAXED_SPONGE);
        for (DyeColor color : DyeColor.values()) {
            String dyeID = "minecraft";
            if (FabricLoader.getInstance().isModLoaded("dye_depot")) {
                for (DDDyes dddcolor : DDDyes.values()) {
                    if (dddcolor.getName() == color.getName()) dyeID = "dye_depot";
                }
            }
            waxedBlocks.put(Waxed.getBlockFromString(dyeID, color + "_concrete_powder"), Waxed.getBlockFromString("waxed_" + color + "_concrete_powder"));
        }

        if (FabricLoader.getInstance().isModLoaded("supplementaries")) {
            //waxedBlocks.put(ModRegistry.FODDER, WaxedModBlocks.WAXED_FODDER);
            waxedBlocks.put(ModRegistry.SUGAR_CUBE.get(), WBlocks.WAXED_SUGAR_CUBE);
            waxedBlocks.put(ModRegistry.RAKED_GRAVEL.get(), WBlocks.WAXED_RAKED_GRAVEL);
        }
        waxedBlocks.putAll(waxedBlocks);
        return waxedBlocks;
    });

    public static final Supplier<BiMap<Block, Block>> WAXABLES_MODDED_BLOCKS = Suppliers.memoize(() -> {
        BiMap<Block, Block> waxedBlocks = HashBiMap.create();
        List<Block> waxedModdedBlocks = new ArrayList<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
            if (id.getPath().contains("waxed_") && !id.getNamespace().equals("waxed")) {
                waxedModdedBlocks.add(block);
            }
        }
        Iterator<Block> iterator = waxedModdedBlocks.iterator();
        while (iterator.hasNext()) {
            Block waxedBlock = iterator.next();
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(waxedBlock);
            String unwaxedPath = id.getPath().replace("waxed_", "");
            Block unwaxedBlock = Waxed.getBlockFromString(id.getNamespace(), unwaxedPath);

            if (unwaxedBlock != null) {
                if (!(unwaxedBlock instanceof WeatheringCopper)) {
                    waxedBlocks.put(unwaxedBlock, waxedBlock);
                } else {
                    iterator.remove();
                }
            }
        }
        waxedBlocks.putAll(waxedBlocks);
        return waxedBlocks;
    });

    public static final Supplier<BiMap<Block, Block>> WAXABLES_MODDED_BLOCKS_WITH_COPPER = Suppliers.memoize(() -> {
        BiMap<Block, Block> waxedBlocks = HashBiMap.create();
        List<Block> waxedModdedBlocks = new ArrayList<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
            if (id.getPath().contains("waxed_") && !id.getNamespace().equals("waxed")) {
                waxedModdedBlocks.add(block);
            }
        }
        for (Block waxedBlock : waxedModdedBlocks) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(waxedBlock);
            String unwaxedPath = id.getPath().replace("waxed_", "");
            Block unwaxedBlock = Waxed.getBlockFromString(id.getNamespace(), unwaxedPath);
            if (unwaxedBlock == Blocks.AIR) {
                unwaxedBlock = Waxed.getBlockFromString("minecraft", unwaxedPath);
                if (unwaxedBlock != Blocks.AIR) {
                    waxedBlocks.put(unwaxedBlock, waxedBlock);
                }
            } else {
                waxedBlocks.put(unwaxedBlock, waxedBlock);
            }
        }
        waxedBlocks.putAll(waxedBlocks);
        return waxedBlocks;
    });

    public static final Supplier<BiMap<Block, Block>> WAXABLES_AXE = Suppliers.memoize(() -> {
        BiMap<Block, Block> waxedBlocks = HashBiMap.create();
        waxedBlocks.put(Blocks.ICE, WBlocks.WAXED_ICE);

        waxedBlocks.put(Blocks.TUBE_CORAL_BLOCK, WBlocks.WAXED_TUBE_CORAL_BLOCK);
        waxedBlocks.put(Blocks.BRAIN_CORAL_BLOCK, WBlocks.WAXED_BRAIN_CORAL_BLOCK);
        waxedBlocks.put(Blocks.BUBBLE_CORAL_BLOCK, WBlocks.WAXED_BUBBLE_CORAL_BLOCK);
        waxedBlocks.put(Blocks.FIRE_CORAL_BLOCK, WBlocks.WAXED_FIRE_CORAL_BLOCK);
        waxedBlocks.put(Blocks.HORN_CORAL_BLOCK, WBlocks.WAXED_HORN_CORAL_BLOCK);
        waxedBlocks.put(Blocks.TUBE_CORAL, WBlocks.WAXED_TUBE_CORAL);
        waxedBlocks.put(Blocks.BRAIN_CORAL, WBlocks.WAXED_BRAIN_CORAL);
        waxedBlocks.put(Blocks.BUBBLE_CORAL, WBlocks.WAXED_BUBBLE_CORAL);
        waxedBlocks.put(Blocks.FIRE_CORAL, WBlocks.WAXED_FIRE_CORAL);
        waxedBlocks.put(Blocks.HORN_CORAL, WBlocks.WAXED_HORN_CORAL);
        waxedBlocks.put(Blocks.TUBE_CORAL_FAN, WBlocks.WAXED_TUBE_CORAL_FAN);
        waxedBlocks.put(Blocks.BRAIN_CORAL_FAN, WBlocks.WAXED_BRAIN_CORAL_FAN);
        waxedBlocks.put(Blocks.BUBBLE_CORAL_FAN, WBlocks.WAXED_BUBBLE_CORAL_FAN);
        waxedBlocks.put(Blocks.FIRE_CORAL_FAN, WBlocks.WAXED_FIRE_CORAL_FAN);
        waxedBlocks.put(Blocks.HORN_CORAL_FAN, WBlocks.WAXED_HORN_CORAL_FAN);
        waxedBlocks.put(Blocks.TUBE_CORAL_WALL_FAN, WBlocks.WAXED_TUBE_CORAL_WALL_FAN);
        waxedBlocks.put(Blocks.BRAIN_CORAL_WALL_FAN, WBlocks.WAXED_BRAIN_CORAL_WALL_FAN);
        waxedBlocks.put(Blocks.BUBBLE_CORAL_WALL_FAN, WBlocks.WAXED_BUBBLE_CORAL_WALL_FAN);
        waxedBlocks.put(Blocks.FIRE_CORAL_WALL_FAN, WBlocks.WAXED_FIRE_CORAL_WALL_FAN);
        waxedBlocks.put(Blocks.HORN_CORAL_WALL_FAN, WBlocks.WAXED_HORN_CORAL_WALL_FAN);
        //if (FabricLoader.getInstance().isModLoaded("supplementaries")) {
        //    waxedBlocks.put(ModRegistry.SOAP_BLOCK.get(), WBlocks.WAXED_SOAP_BLOCK);
        //}
        waxedBlocks.putAll(waxedBlocks);
        return waxedBlocks;
    });

    public static Optional<Block> getWaxedModdedBlocks(Block block) {
        return Optional.ofNullable(WAXABLES_MODDED_BLOCKS.get().get(block));
    }

    public static Optional<Block> getVanillaWaxedCopperBlocks(Block block) {
        return Optional.ofNullable(HoneycombItem.WAXABLES.get().get(block));
    }

    public static Optional<Block> getWaxedShovels(Block block) {
        return Optional.ofNullable(WAXABLES_SHOVEL.get().get(block));
    }

    public static Optional<Block> getWaxedAxe(Block block) {
        return Optional.ofNullable(WAXABLES_AXE.get().get(block));
    }

    public static Optional<Block> getUnwaxedShovels(Block block) {
        return Optional.ofNullable(WAXABLES_SHOVEL.get().inverse().get(block));
    }

    public static Optional<Block> getUnwaxedAxe(Block block) {
        return Optional.ofNullable(WAXABLES_AXE.get().inverse().get(block));
    }

    public static void init() {
        // Register Fabric events
        UseBlockCallback.EVENT.register(WaxingBlocks::handleBlockInteractions);
    }

    private static InteractionResult handleBlockInteractions(Player player, Level level,
                                                             InteractionHand hand, BlockHitResult hitResult) {
        if (player.isSpectator()) return InteractionResult.PASS;

        BlockPos blockPos = hitResult.getBlockPos();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack itemStack = player.getItemInHand(hand);

        // Wax On with wax items
        InteractionResult waxOnResult = tryWaxOn(player, level, blockPos, blockState, itemStack);
        if (waxOnResult != InteractionResult.PASS) return waxOnResult;

        // Wax Off with tools
        InteractionResult waxOffResult = tryWaxOff(player, level, blockPos, blockState, itemStack);
        if (waxOffResult != InteractionResult.PASS) return waxOffResult;

        return InteractionResult.PASS;
    }

    private static InteractionResult tryWaxOn(Player player, Level level, BlockPos blockPos, BlockState blockState, ItemStack itemStack) {
        var waxedBlock1 = getWaxedShovels(blockState.getBlock());
        var waxedBlock2 = getWaxedAxe(blockState.getBlock());
        var waxedModdedBlock = getWaxedModdedBlocks(blockState.getBlock());
        var waxedVanillaBlock = getVanillaWaxedCopperBlocks(blockState.getBlock());

        if (itemStack.is(WTags.Items.CAN_WAX) &&
                (waxedBlock1.isPresent() || waxedBlock2.isPresent() ||
                        (blockState.hasProperty(BlockStateProperties.LAYERS) &&
                        blockState.getValue(BlockStateProperties.LAYERS) == 8))) {

            return processWaxOn(player, level, blockPos, blockState, itemStack,
                    waxedBlock1.orElse(null), waxedBlock2.orElse(null));
        }
        else if (itemStack.is(WItems.WAX) &&
                (waxedModdedBlock.isPresent() || waxedVanillaBlock.isPresent())) {

            return processWaxOnWithWax(player, level, blockPos, blockState, itemStack,
                    waxedModdedBlock.orElse(null));
        }

        return InteractionResult.PASS;
    }

    private static InteractionResult processWaxOn(Player player, Level level, BlockPos blockPos,
                                                  BlockState blockState, ItemStack itemStack, Block... waxedBlocks) {

        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, itemStack);
        }

        player.swing(InteractionHand.MAIN_HAND);

        if (!player.isCreative()) {
            itemStack.shrink(1);
        }

        for (Block waxedBlock : waxedBlocks) {
            if (waxedBlock != null) {
                level.setBlock(blockPos, waxedBlock.withPropertiesOf(blockState), Block.UPDATE_ALL);
                break;
            }
        }

        if (player instanceof ServerPlayer serverPlayer) {
            grantAdvancement(serverPlayer, "minecraft:husbandry/wax_on");
        }

        level.levelEvent(player, 3003, blockPos, 0);
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private static InteractionResult processWaxOnWithWax(Player player, Level level, BlockPos blockPos,
                                                         BlockState blockState, ItemStack itemStack, Block waxedBlock) {

        if (waxedBlock == null) return InteractionResult.PASS;

        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, itemStack);
        }

        player.swing(InteractionHand.MAIN_HAND);

        if (!player.isCreative()) {
            itemStack.shrink(1);
        }

        if (!level.isClientSide()) {
            if (level.getBlockState(blockPos).hasBlockEntity()) {
                BlockEntity blockEntity = level.getBlockEntity(blockPos);
                if (blockEntity != null) {
                    CompoundTag data = blockEntity.saveWithFullMetadata();
                    level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_KNOWN_SHAPE);
                    level.setBlockAndUpdate(blockPos, waxedBlock.withPropertiesOf(blockState));
                    BlockEntity newBlockEntity = level.getBlockEntity(blockPos);
                    if (newBlockEntity != null) {
                        newBlockEntity.load(data);
                        level.setBlockEntity(newBlockEntity);
                    }
                }
            } else {
                level.setBlock(blockPos, waxedBlock.withPropertiesOf(blockState), Block.UPDATE_ALL);
            }
        }

        if (player instanceof ServerPlayer serverPlayer) {
            grantAdvancement(serverPlayer, "minecraft:husbandry/wax_on");
        }

        level.levelEvent(player, 3003, blockPos, 0);
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private static InteractionResult tryWaxOff(Player player, Level level, BlockPos blockPos,
                                               BlockState blockState, ItemStack itemStack) {

        var unwaxedBlock1 = getUnwaxedShovels(blockState.getBlock());
        var unwaxedBlock2 = getUnwaxedAxe(blockState.getBlock());

        if (itemStack.is(ItemTags.SHOVELS)) {
            if (unwaxedBlock1.isPresent()) {
                return processWaxOff(player, level, blockPos, blockState, itemStack,
                        unwaxedBlock1.get(), WSounds.SHOVEL_WAX_OFF);
            }
        }
        else if (itemStack.is(ItemTags.AXES)) {
            if (unwaxedBlock2.isPresent()) {
                return processWaxOff(player, level, blockPos, blockState, itemStack,
                        unwaxedBlock2.get(), SoundEvents.AXE_WAX_OFF);
            }
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult processWaxOff(Player player, Level level, BlockPos blockPos,
                                                   BlockState blockState, ItemStack itemStack, Block unwaxedBlock, SoundEvent soundEvent) {

        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, itemStack);
        }

        player.swing(InteractionHand.MAIN_HAND);

        if (!player.isCreative()) {
            itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        }

        if (player instanceof ServerPlayer serverPlayer) {
            grantAdvancement(serverPlayer, "minecraft:husbandry/wax_off");
        }

        level.setBlock(blockPos, unwaxedBlock.withPropertiesOf(blockState), Block.UPDATE_ALL);
        level.levelEvent(player, 3004, blockPos, 0);
        level.playSound(null, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private static void grantAdvancement(ServerPlayer player, String advancementId) {
        Advancement advancement = player.server.getAdvancements()
                .getAdvancement(new ResourceLocation(advancementId));
        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
            if (!progress.isDone()) {
                for (String criteria : progress.getRemainingCriteria()) {
                    player.getAdvancements().award(advancement, criteria);
                }
            }
        }
    }
}