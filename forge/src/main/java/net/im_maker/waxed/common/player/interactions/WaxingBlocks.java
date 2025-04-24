package net.im_maker.waxed.common.player.interactions;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.ninni.dye_depot.registry.DDDyes;
import com.teamabnormals.caverns_and_chasms.common.entity.animal.CopperGolem;
import com.teamabnormals.upgrade_aquatic.core.registry.UABlocks;
import galena.oreganized.index.OBlocks;
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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Waxed.MOD_ID)

public class WaxingBlocks {

    public static final Supplier<BiMap<Block, Block>> WAXABLES_SHOVEL = Suppliers.memoize(() -> {
        BiMap<Block, Block> waxedBlocks = HashBiMap.create();
        waxedBlocks.put(Blocks.SAND, WBlocks.WAXED_SAND.get());
        waxedBlocks.put(Blocks.RED_SAND, WBlocks.WAXED_RED_SAND.get());
        waxedBlocks.put(Blocks.GRAVEL, WBlocks.WAXED_GRAVEL.get());
        waxedBlocks.put(Blocks.SPONGE, WBlocks.WAXED_SPONGE.get());
        for (DyeColor color : DyeColor.values()) {
            String dyeID = "minecraft";
            if (ModList.get().isLoaded("dye_depot")) {
                for (DDDyes dddcolor : DDDyes.values()) {
                    if (dddcolor.get().getName() == color.getName()) dyeID = "dye_depot";
                }
            }
            waxedBlocks.put(Waxed.getBlockFromString(dyeID, color + "_concrete_powder"), Waxed.getBlockFromString("waxed_" + color + "_concrete_powder"));
        }
        if (ModList.get().isLoaded("supplementaries")) {
            waxedBlocks.put(ModRegistry.SUGAR_CUBE.get(), WBlocks.WAXED_SUGAR_CUBE.get());
            waxedBlocks.put(ModRegistry.RAKED_GRAVEL.get(), WBlocks.WAXED_RAKED_GRAVEL.get());
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
        waxedBlocks.put(Blocks.ICE, WBlocks.WAXED_ICE.get());

        waxedBlocks.put(Blocks.TUBE_CORAL_BLOCK, WBlocks.WAXED_TUBE_CORAL_BLOCK.get());
        waxedBlocks.put(Blocks.BRAIN_CORAL_BLOCK, WBlocks.WAXED_BRAIN_CORAL_BLOCK.get());
        waxedBlocks.put(Blocks.BUBBLE_CORAL_BLOCK, WBlocks.WAXED_BUBBLE_CORAL_BLOCK.get());
        waxedBlocks.put(Blocks.FIRE_CORAL_BLOCK, WBlocks.WAXED_FIRE_CORAL_BLOCK.get());
        waxedBlocks.put(Blocks.HORN_CORAL_BLOCK, WBlocks.WAXED_HORN_CORAL_BLOCK.get());
        waxedBlocks.put(Blocks.TUBE_CORAL, WBlocks.WAXED_TUBE_CORAL.get());
        waxedBlocks.put(Blocks.BRAIN_CORAL, WBlocks.WAXED_BRAIN_CORAL.get());
        waxedBlocks.put(Blocks.BUBBLE_CORAL, WBlocks.WAXED_BUBBLE_CORAL.get());
        waxedBlocks.put(Blocks.FIRE_CORAL, WBlocks.WAXED_FIRE_CORAL.get());
        waxedBlocks.put(Blocks.HORN_CORAL, WBlocks.WAXED_HORN_CORAL.get());
        waxedBlocks.put(Blocks.TUBE_CORAL_FAN, WBlocks.WAXED_TUBE_CORAL_FAN.get());
        waxedBlocks.put(Blocks.BRAIN_CORAL_FAN, WBlocks.WAXED_BRAIN_CORAL_FAN.get());
        waxedBlocks.put(Blocks.BUBBLE_CORAL_FAN, WBlocks.WAXED_BUBBLE_CORAL_FAN.get());
        waxedBlocks.put(Blocks.FIRE_CORAL_FAN, WBlocks.WAXED_FIRE_CORAL_FAN.get());
        waxedBlocks.put(Blocks.HORN_CORAL_FAN, WBlocks.WAXED_HORN_CORAL_FAN.get());
        waxedBlocks.put(Blocks.TUBE_CORAL_WALL_FAN, WBlocks.WAXED_TUBE_CORAL_WALL_FAN.get());
        waxedBlocks.put(Blocks.BRAIN_CORAL_WALL_FAN, WBlocks.WAXED_BRAIN_CORAL_WALL_FAN.get());
        waxedBlocks.put(Blocks.BUBBLE_CORAL_WALL_FAN, WBlocks.WAXED_BUBBLE_CORAL_WALL_FAN.get());
        waxedBlocks.put(Blocks.FIRE_CORAL_WALL_FAN, WBlocks.WAXED_FIRE_CORAL_WALL_FAN.get());
        waxedBlocks.put(Blocks.HORN_CORAL_WALL_FAN, WBlocks.WAXED_HORN_CORAL_WALL_FAN.get());
        if (ModList.get().isLoaded("upgrade_aquatic")) {
            waxedBlocks.put(UABlocks.ACAN_CORAL_BLOCK.get(), WBlocks.WAXED_ACAN_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.FINGER_CORAL_BLOCK.get(), WBlocks.WAXED_FINGER_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.STAR_CORAL_BLOCK.get(), WBlocks.WAXED_STAR_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.MOSS_CORAL_BLOCK.get(), WBlocks.WAXED_MOSS_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.PETAL_CORAL_BLOCK.get(), WBlocks.WAXED_PETAL_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.BRANCH_CORAL_BLOCK.get(), WBlocks.WAXED_BRANCH_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.ROCK_CORAL_BLOCK.get(), WBlocks.WAXED_ROCK_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.PILLOW_CORAL_BLOCK.get(), WBlocks.WAXED_PILLOW_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.SILK_CORAL_BLOCK.get(), WBlocks.WAXED_SILK_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.CHROME_CORAL_BLOCK.get(), WBlocks.WAXED_CHROME_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.PRISMARINE_CORAL_BLOCK.get(), WBlocks.WAXED_PRISMARINE_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.ELDER_PRISMARINE_CORAL_BLOCK.get(), WBlocks.WAXED_ELDER_PRISMARINE_CORAL_BLOCK.get());
            waxedBlocks.put(UABlocks.ACAN_CORAL.get(), WBlocks.WAXED_ACAN_CORAL.get());
            waxedBlocks.put(UABlocks.FINGER_CORAL.get(), WBlocks.WAXED_FINGER_CORAL.get());
            waxedBlocks.put(UABlocks.STAR_CORAL.get(), WBlocks.WAXED_STAR_CORAL.get());
            waxedBlocks.put(UABlocks.MOSS_CORAL.get(), WBlocks.WAXED_MOSS_CORAL.get());
            waxedBlocks.put(UABlocks.PETAL_CORAL.get(), WBlocks.WAXED_PETAL_CORAL.get());
            waxedBlocks.put(UABlocks.BRANCH_CORAL.get(), WBlocks.WAXED_BRANCH_CORAL.get());
            waxedBlocks.put(UABlocks.ROCK_CORAL.get(), WBlocks.WAXED_ROCK_CORAL.get());
            waxedBlocks.put(UABlocks.PILLOW_CORAL.get(), WBlocks.WAXED_PILLOW_CORAL.get());
            waxedBlocks.put(UABlocks.SILK_CORAL.get(), WBlocks.WAXED_SILK_CORAL.get());
            waxedBlocks.put(UABlocks.CHROME_CORAL.get(), WBlocks.WAXED_CHROME_CORAL.get());
            waxedBlocks.put(UABlocks.PRISMARINE_CORAL.get(), WBlocks.WAXED_PRISMARINE_CORAL.get());
            waxedBlocks.put(UABlocks.ELDER_PRISMARINE_CORAL.get(), WBlocks.WAXED_ELDER_PRISMARINE_CORAL.get());
            waxedBlocks.put(UABlocks.ACAN_CORAL_FAN.get(), WBlocks.WAXED_ACAN_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.FINGER_CORAL_FAN.get(), WBlocks.WAXED_FINGER_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.STAR_CORAL_FAN.get(), WBlocks.WAXED_STAR_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.MOSS_CORAL_FAN.get(), WBlocks.WAXED_MOSS_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.PETAL_CORAL_FAN.get(), WBlocks.WAXED_PETAL_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.BRANCH_CORAL_FAN.get(), WBlocks.WAXED_BRANCH_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.ROCK_CORAL_FAN.get(), WBlocks.WAXED_ROCK_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.PILLOW_CORAL_FAN.get(), WBlocks.WAXED_PILLOW_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.SILK_CORAL_FAN.get(), WBlocks.WAXED_SILK_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.CHROME_CORAL_FAN.get(), WBlocks.WAXED_CHROME_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.PRISMARINE_CORAL_FAN.get(), WBlocks.WAXED_PRISMARINE_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.ELDER_PRISMARINE_CORAL_FAN.get(), WBlocks.WAXED_ELDER_PRISMARINE_CORAL_FAN.get());
            waxedBlocks.put(UABlocks.ACAN_CORAL_WALL_FAN.get(), WBlocks.WAXED_ACAN_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.FINGER_CORAL_WALL_FAN.get(), WBlocks.WAXED_FINGER_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.STAR_CORAL_WALL_FAN.get(), WBlocks.WAXED_STAR_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.MOSS_CORAL_WALL_FAN.get(), WBlocks.WAXED_MOSS_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.PETAL_CORAL_WALL_FAN.get(), WBlocks.WAXED_PETAL_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.BRANCH_CORAL_WALL_FAN.get(), WBlocks.WAXED_BRANCH_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.ROCK_CORAL_WALL_FAN.get(), WBlocks.WAXED_ROCK_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.PILLOW_CORAL_WALL_FAN.get(), WBlocks.WAXED_PILLOW_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.SILK_CORAL_WALL_FAN.get(), WBlocks.WAXED_SILK_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.CHROME_CORAL_WALL_FAN.get(), WBlocks.WAXED_CHROME_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.PRISMARINE_CORAL_WALL_FAN.get(), WBlocks.WAXED_PRISMARINE_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.ELDER_PRISMARINE_CORAL_WALL_FAN.get(), WBlocks.WAXED_ELDER_PRISMARINE_CORAL_WALL_FAN.get());
            waxedBlocks.put(UABlocks.PRISMARINE_CORAL_SHOWER.get(), WBlocks.WAXED_PRISMARINE_CORAL_SHOWER.get());
            waxedBlocks.put(UABlocks.ELDER_PRISMARINE_CORAL_SHOWER.get(), WBlocks.WAXED_ELDER_PRISMARINE_CORAL_SHOWER.get());
        }
        if (ModList.get().isLoaded("oreganized")) {
            waxedBlocks.put(OBlocks.GROOVED_ICE.get(), WBlocks.WAXED_GROOVED_ICE.get());
        }
        waxedBlocks.putAll(waxedBlocks);
        return waxedBlocks;
    });

    public static final Supplier<BiMap<Block, Block>> WAXABLES_PRISMARINE_AXE = Suppliers.memoize(() -> {
        BiMap<Block, Block> waxedBlocks = HashBiMap.create();
        waxedBlocks.put(Blocks.PRISMARINE, WBlocks.WAXED_PRISMARINE.get());
        waxedBlocks.put(Blocks.PRISMARINE_STAIRS, WBlocks.WAXED_PRISMARINE_STAIRS.get());
        waxedBlocks.put(Blocks.PRISMARINE_SLAB, WBlocks.WAXED_PRISMARINE_SLAB.get());
        waxedBlocks.put(Blocks.PRISMARINE_WALL, WBlocks.WAXED_PRISMARINE_WALL.get());
        waxedBlocks.put(Blocks.MAGMA_BLOCK, WBlocks.WAXED_MAGMA_BLOCK.get());
        waxedBlocks.put(Blocks.POWDER_SNOW, WBlocks.WAXED_POWDER_SNOW.get());
        waxedBlocks.put(Blocks.SOUL_SAND, WBlocks.WAXED_SOUL_SAND.get());
        waxedBlocks.put(Blocks.REDSTONE_BLOCK, WBlocks.WAXED_REDSTONE_BLOCK.get());
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

    public static Optional<Block> getUnwaxedPrismarineAxe(Block block) {
        return Optional.ofNullable(WAXABLES_PRISMARINE_AXE.get().inverse().get(block));
    }

    @SubscribeEvent
    public static InteractionResult waxOnPlayerInteract(final PlayerInteractEvent.RightClickBlock interactEvent) {
        Level level = interactEvent.getLevel();
        BlockPos blockPos = interactEvent.getPos();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack itemStack = interactEvent.getItemStack();
        var waxedBlock1 = getWaxedShovels(blockState.getBlock());
        var waxedBlock2 = getWaxedAxe(blockState.getBlock());
        if (itemStack.is(WTags.Items.CAN_WAX) && (waxedBlock1.isPresent() || waxedBlock2.isPresent() && (blockState.getValue(BlockStateProperties.LAYERS) == 8))) {
            Player player = interactEvent.getEntity();
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
            }
            interactEvent.getEntity().swing(interactEvent.getHand());
            if (!interactEvent.getEntity().isCreative()) interactEvent.getItemStack().shrink(1);
            if (!level.isClientSide() && waxedBlock1.isPresent()) level.setBlock(blockPos, waxedBlock1.get().withPropertiesOf(blockState), 11);
            if (!level.isClientSide() && waxedBlock2.isPresent()) level.setBlock(blockPos, waxedBlock2.get().withPropertiesOf(blockState), 11);
            if (player instanceof ServerPlayer serverPlayer) {
                Advancement advWaxOn = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("minecraft:husbandry/wax_on"));
                AdvancementProgress advWaxOnProgress = serverPlayer.getAdvancements().getOrStartProgress(advWaxOn);
                if (!advWaxOnProgress.isDone()) {
                    for (String criteria : advWaxOnProgress.getRemainingCriteria()) {
                        serverPlayer.getAdvancements().award(advWaxOn, criteria);
                    }
                }
            }
            level.levelEvent(player, 3003, blockPos, 0);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return (InteractionResult.PASS);
        }
    }

    @SubscribeEvent
    public static InteractionResult waxOffPlayerInteract(final PlayerInteractEvent.RightClickBlock interactEvent) {
        Level level = interactEvent.getLevel();
        BlockPos blockPos = interactEvent.getPos();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack itemStack = interactEvent.getItemStack();
        var unwaxedBlock1 = getUnwaxedShovels(blockState.getBlock());
        var unwaxedBlock2 = getUnwaxedAxe(blockState.getBlock());
        var unwaxedBlock5 = getUnwaxedPrismarineAxe(blockState.getBlock());
        if (itemStack.is(ItemTags.SHOVELS) && (unwaxedBlock1.isPresent())) {
            if (unwaxedBlock1.isPresent()) unwaxing(interactEvent ,blockPos ,itemStack ,level ,unwaxedBlock1.isPresent(), unwaxedBlock1.get().withPropertiesOf(blockState), WSounds.SHOVEL_WAX_OFF.get());
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else if (itemStack.is(ItemTags.AXES) && (unwaxedBlock2.isPresent() || unwaxedBlock5.isPresent())) {
            if (unwaxedBlock2.isPresent()) unwaxing(interactEvent ,blockPos ,itemStack ,level ,unwaxedBlock2.isPresent(), unwaxedBlock2.get().withPropertiesOf(blockState), SoundEvents.AXE_WAX_OFF);
            if (unwaxedBlock5.isPresent()) unwaxing(interactEvent ,blockPos ,itemStack ,level ,unwaxedBlock5.isPresent(), unwaxedBlock5.get().withPropertiesOf(blockState), SoundEvents.AXE_WAX_OFF);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return (InteractionResult.PASS);
        }
    }

    private static Optional unwaxing (PlayerInteractEvent interactEvent, BlockPos blockPos, ItemStack itemStack, Level level, Boolean present, BlockState unwaxedBlock, SoundEvent soundEvent){
        Player player = interactEvent.getEntity();
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, itemStack);
        }
        interactEvent.getEntity().swing(interactEvent.getHand());
        if (!interactEvent.getEntity().isCreative()) {
            interactEvent.getItemStack().hurtAndBreak(1, player, (player1) -> {
                player1.broadcastBreakEvent(interactEvent.getHand());
            });
        }
        if (player instanceof ServerPlayer serverPlayer) {
            Advancement advWaxOff = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("minecraft:husbandry/wax_off"));
            AdvancementProgress advWaxOffProgress = serverPlayer.getAdvancements().getOrStartProgress(advWaxOff);
            if (!advWaxOffProgress.isDone()) {
                for (String criteria : advWaxOffProgress.getRemainingCriteria()) {
                    serverPlayer.getAdvancements().award(advWaxOff, criteria);
                }
            }
        }
        if (!level.isClientSide() || present) level.setBlock(blockPos, unwaxedBlock, 11);
        level.levelEvent(player, 3004, blockPos, 0);
        level.playLocalSound(blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        return Optional.ofNullable(null);
    }

    @SubscribeEvent
    public static InteractionResult waxOnUsingWaxOnlyPlayerInteract(final PlayerInteractEvent.RightClickBlock interactEvent) {
        Level level = interactEvent.getLevel();
        BlockPos blockPos = interactEvent.getPos();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack itemStack = interactEvent.getItemStack();
        var waxedModdedBlock = getWaxedModdedBlocks(blockState.getBlock());
        var waxedVanillaBlock = getVanillaWaxedCopperBlocks(blockState.getBlock());
        if (itemStack.is(WItems.WAX.get()) && (waxedModdedBlock.isPresent() || waxedVanillaBlock.isPresent())) {
            Player player = interactEvent.getEntity();
            if (player instanceof ServerPlayer && waxedModdedBlock.isPresent()) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
            }
            if (waxedModdedBlock.isPresent()) interactEvent.getEntity().swing(interactEvent.getHand());
            if (!interactEvent.getEntity().isCreative() && waxedModdedBlock.isPresent()) interactEvent.getItemStack().shrink(1);
            if (!level.isClientSide() && waxedModdedBlock.isPresent()) {
                if (level.getBlockState(blockPos).hasBlockEntity()) {
                    BlockEntity blockEntity = level.getBlockEntity(blockPos);
                    if (blockEntity != null) {
                        CompoundTag data = blockEntity.saveWithFullMetadata();
                        level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_KNOWN_SHAPE);
                        level.setBlockAndUpdate(blockPos, waxedModdedBlock.get().withPropertiesOf(blockState));
                        BlockEntity newBlockEntity = level.getBlockEntity(blockPos);
                        if (newBlockEntity != null) {
                            newBlockEntity.load(data);
                            level.setBlockEntity(newBlockEntity);
                        }
                    }
                } else {
                    level.setBlock(blockPos, waxedModdedBlock.get().withPropertiesOf(blockState), 11);
                }
            }
            if (player instanceof ServerPlayer serverPlayer) {
                Advancement advWaxOn = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("minecraft:husbandry/wax_on"));
                AdvancementProgress advWaxOnProgress = serverPlayer.getAdvancements().getOrStartProgress(advWaxOn);
                if (!advWaxOnProgress.isDone()) {
                    for (String criteria : advWaxOnProgress.getRemainingCriteria()) {
                        serverPlayer.getAdvancements().award(advWaxOn, criteria);
                    }
                }
            }
            if (waxedModdedBlock.isPresent()) level.levelEvent(player, 3003, blockPos, 0);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return (InteractionResult.PASS);
        }
    }

    @SubscribeEvent
    public static InteractionResult onEntityInteract(final PlayerInteractEvent.EntityInteract interactEvent) {
        ItemStack itemStack = interactEvent.getItemStack();
        if (ModList.get().isLoaded("caverns_and_chasms")) {
            try {
                Class<?> copperGolemClass = Class.forName("com.teamabnormals.caverns_and_chasms.common.entity.animal.CopperGolem");
                if (itemStack.is(WItems.WAX.get()) && copperGolemClass.isInstance(interactEvent.getTarget())) {
                    Object golem = interactEvent.getTarget();

                    // Get all methods via reflection
                    Method isWaxedMethod = copperGolemClass.getMethod("isWaxed");
                    Method getOnPosMethod = copperGolemClass.getMethod("getOnPos");
                    Method setWaxedMethod = copperGolemClass.getMethod("setWaxed", boolean.class);
                    Method getRandomMethod = copperGolemClass.getMethod("getRandom");
                    Method getRandomXMethod = copperGolemClass.getMethod("getRandomX", double.class);
                    Method getRandomYMethod = copperGolemClass.getMethod("getRandomY");
                    Method getRandomZMethod = copperGolemClass.getMethod("getRandomZ", double.class);
                    Method levelMethod = copperGolemClass.getMethod("level");

                    // Check if already waxed
                    if (!(boolean)isWaxedMethod.invoke(golem)) {
                        // Handle item consumption
                        if (!interactEvent.getEntity().isCreative()) {
                            itemStack.shrink(1);
                        }

                        // Trigger advancement
                        Player player = interactEvent.getEntity();
                        if (player instanceof ServerPlayer serverPlayer) {
                            CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger(serverPlayer, itemStack, (Entity) golem);
                            Advancement advWaxOn = serverPlayer.server.getAdvancements().getAdvancement(
                                    new ResourceLocation("minecraft:husbandry/wax_on"));
                            AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(advWaxOn);
                            if (!progress.isDone()) {
                                for (String criteria : progress.getRemainingCriteria()) {
                                    serverPlayer.getAdvancements().award(advWaxOn, criteria);
                                }
                            }
                        }

                        // Client-side effects
                        Level level = interactEvent.getLevel();
                        if (level.isClientSide) {
                            RandomSource random = (RandomSource)getRandomMethod.invoke(golem);
                            for (int i = 0; i < 7; ++i) {
                                double d0 = Mth.nextDouble(random, -1.0, 1.0);
                                double d1 = Mth.nextDouble(random, -1.0, 1.0);
                                double d2 = Mth.nextDouble(random, -1.0, 1.0);
                                Level entityLevel = (Level)levelMethod.invoke(golem);
                                entityLevel.addParticle(ParticleTypes.WAX_ON,
                                        (double)getRandomXMethod.invoke(golem, 1.0),
                                        (double)getRandomYMethod.invoke(golem),
                                        (double)getRandomZMethod.invoke(golem, 1.0),
                                        d0, d1, d2);
                            }
                        }

                        // Play sound and set waxed
                        BlockPos blockPos = (BlockPos)getOnPosMethod.invoke(golem);
                        level.playLocalSound(blockPos, SoundEvents.HONEYCOMB_WAX_ON,
                                SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                        setWaxedMethod.invoke(golem, true);

                        return InteractionResult.SUCCESS;
                    }
                }
            } catch (Exception e) {
                // Log error if needed
                // ModList.get().getModContainerById("waxed").ifPresent(c ->
                //     c.getLogger().error("Failed to handle Copper Golem interaction", e));
            }
        }
        return InteractionResult.PASS;
    }
}