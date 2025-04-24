package net.im_maker.waxed.common.block;

import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.block.custom.*;
import net.im_maker.waxed.common.item.WItems;
import net.im_maker.waxed.common.particles.WParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class WBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Waxed.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS_V =
            DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
    public static final DeferredRegister<Block> BLOCKS_S =
            DeferredRegister.create(ForgeRegistries.BLOCKS, "supplementaries");
    public static final DeferredRegister<Block> BLOCKS_O =
            DeferredRegister.create(ForgeRegistries.BLOCKS, "oreganized");
    //Stuff
    public static final RegistryObject<Block> EMPTY_HONEYCOMB = registerBlock("empty_honeycomb", () -> new EmptyHoneycombBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.6f).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> HONEYCOMB_BLOCK = BLOCKS_V.register("honeycomb_block", () -> new HoneycombBlock(BlockBehaviour.Properties.copy(Blocks.HONEYCOMB_BLOCK)));

    public static final RegistryObject<Block> WICK = registerBlock("wick", () -> new WickBlock(1, BlockBehaviour.Properties.of().noCollission().lightLevel(WickBlock.LIGHT_EMISSION).strength(0.1f).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> SOUL_WICK = BLOCKS.register("soul_wick", () -> new WickBlock(2, BlockBehaviour.Properties.of().noCollission().lightLevel(WickBlock.SOUL_LIGHT_EMISSION).strength(0.1f).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> SOUL_CANDLE = registerBlock("soul_candle", () -> new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(SoulCandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_SOUL_FLAME));
    public static final RegistryObject<Block> SOUL_CANDLE_CAKE = BLOCKS.register("soul_candle_cake", () -> new SoulCandleCakeBlock(SOUL_CANDLE.get(), BlockBehaviour.Properties.copy(Blocks.CAKE).lightLevel(litBlockEmission(2)), WParticles.PARTICLES.SMALL_SOUL_FLAME));

    public static final RegistryObject<Block> CUPRIC_WICK = BLOCKS.register("cupric_wick", () -> new WickBlock(2, BlockBehaviour.Properties.of().noCollission().lightLevel(WickBlock.SOUL_LIGHT_EMISSION).strength(0.1f).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> CUPRIC_CANDLE = registerBlock("cupric_candle", () -> new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(SoulCandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_CUPRIC_FLAME));
    public static final RegistryObject<Block> CUPRIC_CANDLE_CAKE = BLOCKS.register("cupric_candle_cake", () -> new SoulCandleCakeBlock(CUPRIC_CANDLE.get(), BlockBehaviour.Properties.copy(Blocks.CAKE).lightLevel(litBlockEmission(2)), WParticles.PARTICLES.SMALL_CUPRIC_FLAME));

    public static final RegistryObject<Block> ENDER_WICK = BLOCKS.register("ender_wick", () -> new WickBlock(2, BlockBehaviour.Properties.of().noCollission().lightLevel(WickBlock.LIGHT_EMISSION).strength(0.1f).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> ENDER_CANDLE = registerBlock("ender_candle", () -> new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(CandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_ENDER_FLAME));
    public static final RegistryObject<Block> ENDER_CANDLE_CAKE = BLOCKS.register("ender_candle_cake", () -> new SoulCandleCakeBlock(ENDER_CANDLE.get(), BlockBehaviour.Properties.copy(Blocks.CAKE).lightLevel(litBlockEmission(2)), WParticles.PARTICLES.SMALL_ENDER_FLAME));
    //Supplementaries
    public static final RegistryObject<Block> SOUL_CANDLE_HOLDER = registerBlockS("soul_candle_holder", () -> ModList.get().isLoaded("supplementaries") ? CandleHolders.SOUL_CANDLE_HOLDER.get() : new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(SoulCandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_SOUL_FLAME));
    public static final RegistryObject<Block> GOLD_SOUL_CANDLE_HOLDER = registerBlock("gold_soul_candle_holder", () -> ModList.get().isLoaded("suppsquared") ? CandleHolders.GOLD_SOUL_CANDLE_HOLDER.get() : new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(SoulCandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_SOUL_FLAME));

    public static final RegistryObject<Block> CUPRIC_CANDLE_HOLDER = registerBlockS("cupric_candle_holder", () -> ModList.get().isLoaded("supplementaries") ? CandleHolders.CUPRIC_CANDLE_HOLDER.get() : new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(SoulCandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_CUPRIC_FLAME));
    public static final RegistryObject<Block> GOLD_CUPRIC_CANDLE_HOLDER = registerBlock("gold_cupric_candle_holder", () -> ModList.get().isLoaded("suppsquared") ? CandleHolders.GOLD_CUPRIC_CANDLE_HOLDER.get() : new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(SoulCandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_CUPRIC_FLAME));

    public static final RegistryObject<Block> ENDER_CANDLE_HOLDER = registerBlockS("ender_candle_holder", () -> ModList.get().isLoaded("supplementaries") ? CandleHolders.ENDER_CANDLE_HOLDER.get() : new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(CandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_ENDER_FLAME));
    public static final RegistryObject<Block> GOLD_ENDER_CANDLE_HOLDER = registerBlock("gold_ender_candle_holder", () -> ModList.get().isLoaded("suppsquared") ? CandleHolders.GOLD_ENDER_CANDLE_HOLDER.get() : new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(CandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_ENDER_FLAME));
    //public static final RegistryObject<Block> SKULL_CANDLE_SOUL = registerBlock("skull_candle_soul", () -> new FloorCandleSkullBlock(BlockBehaviour.Properties.copy(ModRegistry.SKULL_CANDLE.get()), WaxedModParticles.SMALL_SOUL_FLAME::get));
    //public static final RegistryObject<Block> SKULL_CANDLE_SOUL_WALL = registerBlock("skull_candle_soul_wall", () -> new WallCandleSkullBlock(BlockBehaviour.Properties.copy(ModRegistry.SKULL_CANDLE.get()), WaxedModParticles.SMALL_SOUL_FLAME::get));
    //Wax Blocks
    public static final RegistryObject<Block> TALL_CANDLE = registerBlock("tall_candle", () -> tall_candle(MapColor.SAND));
    public static final RegistryObject<Block> SOUL_TALL_CANDLE = registerBlock("soul_tall_candle", () -> tall_candle(MapColor.COLOR_BROWN, true, WParticles.PARTICLES.SOUL_FLAME));
    public static final RegistryObject<Block> CUPRIC_TALL_CANDLE = registerBlock("cupric_tall_candle", () -> tall_candle(MapColor.COLOR_ORANGE, true, ModList.get().isLoaded("caverns_and_chasms") ? WParticles.PARTICLES.CUPRIC_FLAME : WParticles.PARTICLES.FLAME));
    public static final RegistryObject<Block> ENDER_TALL_CANDLE = registerBlock("ender_tall_candle", () -> tall_candle(MapColor.COLOR_PURPLE, false, ModList.get().isLoaded("endergetic") ? WParticles.PARTICLES.ENDER_FLAME : WParticles.PARTICLES.FLAME));
    public static final List<RegistryObject<Block>> COLORED_TALL_CANDLE = registerColoredTallCandle();
    private static <T extends Block> List<RegistryObject<T>> registerColoredTallCandle () {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_tall_candle";
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> tall_candle(color.getMapColor()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    public static final RegistryObject<Block> WAX_BLOCK = registerBlock("wax_block", () -> wax_block(MapColor.SAND));
    public static final RegistryObject<Block> SOUL_WAX_BLOCK = registerBlock("soul_wax_block", () -> wax_block(MapColor.COLOR_BROWN));
    public static final RegistryObject<Block> CUPRIC_WAX_BLOCK = registerBlock("cupric_wax_block", () -> wax_block(MapColor.COLOR_ORANGE));
    public static final RegistryObject<Block> ENDER_WAX_BLOCK = registerBlock("ender_wax_block", () -> wax_block(MapColor.COLOR_PURPLE));
    public static final List<RegistryObject<Block>> COLORED_WAX_BLOCK = registerColoredWaxBlock();

    private static <T extends Block> List<RegistryObject<T>> registerColoredWaxBlock () {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_wax_block";
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> wax_block(color.getMapColor()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    //Wax Pillars
    public static final RegistryObject<Block> WAX_PILLAR = registerBlock("wax_pillar", () -> wax_pillar(MapColor.SAND));
    public static final RegistryObject<Block> SOUL_WAX_PILLAR = registerBlock("soul_wax_pillar", () -> wax_pillar(MapColor.COLOR_BROWN));
    public static final RegistryObject<Block> CUPRIC_WAX_PILLAR = registerBlock("cupric_wax_pillar", () -> wax_pillar(MapColor.COLOR_ORANGE));
    public static final RegistryObject<Block> ENDER_WAX_PILLAR = registerBlock("ender_wax_pillar", () -> wax_pillar(MapColor.COLOR_PURPLE));
    public static final List<RegistryObject<Block>> COLORED_WAX_PILLAR = registerColoredWaxPillar();

    private static <T extends Block> List<RegistryObject<T>> registerColoredWaxPillar () {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_wax_pillar";
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> wax_pillar(color.getMapColor()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    //Waxed Blocks
    public static final RegistryObject<Block> WAXED_SAND = registerBlock("waxed_sand", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sound(SoundType.SAND)));
    public static final RegistryObject<Block> WAXED_RED_SAND = registerBlock("waxed_red_sand", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sound(SoundType.SAND)));
    public static final RegistryObject<Block> WAXED_GRAVEL = registerBlock("waxed_gravel", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.SNARE).strength(0.6f).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> WAXED_POWDER_SNOW = registerBlock("waxed_powder_snow", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).strength(0.25F).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> WAXED_ICE = registerBlock("waxed_ice", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> WAXED_MAGMA_BLOCK = registerBlock("waxed_magma_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.NETHER).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().lightLevel((BlockState) -> {return 3;}).strength(0.5F).emissiveRendering(WBlocks::always)));
    public static final RegistryObject<Block> WAXED_SOUL_SAND = registerBlock("waxed_soul_sand", () -> new SoulSandBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.COW_BELL).strength(0.5f).sound(SoundType.SOUL_SAND).isRedstoneConductor(WBlocks::always).isViewBlocking(WBlocks::always).isSuffocating(WBlocks::always)));
    public static final RegistryObject<Block> WAXED_PRISMARINE = registerBlock("waxed_prismarine", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> WAXED_PRISMARINE_STAIRS = registerBlock("waxed_prismarine_stairs", () -> new StairBlock(WAXED_PRISMARINE.get().defaultBlockState(), BlockBehaviour.Properties.copy(WAXED_PRISMARINE.get())));
    public static final RegistryObject<Block> WAXED_PRISMARINE_SLAB = registerBlock("waxed_prismarine_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> WAXED_PRISMARINE_WALL = registerBlock("waxed_prismarine_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(WAXED_PRISMARINE.get()).forceSolidOn()));
    public static final RegistryObject<Block> WAXED_SPONGE = registerBlock("waxed_sponge", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> WAXED_REDSTONE_BLOCK = registerBlock("waxed_redstone_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> WAXED_COBWEB = registerBlock("waxed_cobweb", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).forceSolidOn().noCollission().requiresCorrectToolForDrops().strength(4.0F).pushReaction(PushReaction.DESTROY)));
   public static final List<RegistryObject<Block>> WAXED_COLORED_CONCRETE_POWDER = registerWaxedColoredConcretePowder();
    private static <T extends Block> List<RegistryObject<T>> registerWaxedColoredConcretePowder () {
        List<RegistryObject<T>> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = "waxed_" + color.getName() + "_concrete_powder";
            RegistryObject<T> block = (RegistryObject<T>) BLOCKS.register(blockId, () -> waxed_concrete_powder(color.getMapColor()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }
    public static final RegistryObject<Block> WAXED_TUBE_CORAL_BLOCK = registerBlock("waxed_tube_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_BRAIN_CORAL_BLOCK = registerBlock("waxed_brain_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_BUBBLE_CORAL_BLOCK = registerBlock("waxed_bubble_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_FIRE_CORAL_BLOCK = registerBlock("waxed_fire_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_HORN_CORAL_BLOCK = registerBlock("waxed_horn_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_TUBE_CORAL = registerBlock("waxed_tube_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_BRAIN_CORAL = registerBlock("waxed_brain_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_BUBBLE_CORAL = registerBlock("waxed_bubble_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_FIRE_CORAL = registerBlock("waxed_fire_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_HORN_CORAL = registerBlock("waxed_horn_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_TUBE_CORAL_FAN = BLOCKS.register("waxed_tube_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_BRAIN_CORAL_FAN = BLOCKS.register("waxed_brain_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_BUBBLE_CORAL_FAN = BLOCKS.register("waxed_bubble_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_FIRE_CORAL_FAN = BLOCKS.register("waxed_fire_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_HORN_CORAL_FAN = BLOCKS.register("waxed_horn_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_TUBE_CORAL_WALL_FAN = BLOCKS.register("waxed_tube_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_TUBE_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_BRAIN_CORAL_WALL_FAN = BLOCKS.register("waxed_brain_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_BRAIN_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_BUBBLE_CORAL_WALL_FAN = BLOCKS.register("waxed_bubble_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_BUBBLE_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_FIRE_CORAL_WALL_FAN = BLOCKS.register("waxed_fire_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_FIRE_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_HORN_CORAL_WALL_FAN = BLOCKS.register("waxed_horn_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_HORN_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    //Upgrade Aquatic Waxed Blocks
    public static final RegistryObject<Block> WAXED_ACAN_CORAL_BLOCK = registerBlock("waxed_acan_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_FINGER_CORAL_BLOCK = registerBlock("waxed_finger_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_STAR_CORAL_BLOCK = registerBlock("waxed_star_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_MOSS_CORAL_BLOCK = registerBlock("waxed_moss_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_PETAL_CORAL_BLOCK = registerBlock("waxed_petal_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_BRANCH_CORAL_BLOCK = registerBlock("waxed_branch_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_ROCK_CORAL_BLOCK = registerBlock("waxed_rock_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_PILLOW_CORAL_BLOCK = registerBlock("waxed_pillow_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_SILK_CORAL_BLOCK = registerBlock("waxed_silk_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_CHROME_CORAL_BLOCK = registerBlock("waxed_chrome_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_PRISMARINE_CORAL_BLOCK = registerBlock("waxed_prismarine_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> WAXED_ELDER_PRISMARINE_CORAL_BLOCK = registerBlock("waxed_elder_prismarine_coral_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));

    public static final RegistryObject<Block> WAXED_ACAN_CORAL = registerBlock("waxed_acan_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_FINGER_CORAL = registerBlock("waxed_finger_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_STAR_CORAL = registerBlock("waxed_star_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_MOSS_CORAL = registerBlock("waxed_moss_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_PETAL_CORAL = registerBlock("waxed_petal_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_BRANCH_CORAL = registerBlock("waxed_branch_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_ROCK_CORAL = registerBlock("waxed_rock_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_PILLOW_CORAL = registerBlock("waxed_pillow_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_SILK_CORAL = registerBlock("waxed_silk_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_CHROME_CORAL = registerBlock("waxed_chrome_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_PRISMARINE_CORAL = registerBlock("waxed_prismarine_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_ELDER_PRISMARINE_CORAL = registerBlock("waxed_elder_prismarine_coral", () -> new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> WAXED_ACAN_CORAL_FAN = BLOCKS.register("waxed_acan_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_FINGER_CORAL_FAN = BLOCKS.register("waxed_finger_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_STAR_CORAL_FAN = BLOCKS.register("waxed_star_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_MOSS_CORAL_FAN = BLOCKS.register("waxed_moss_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_PETAL_CORAL_FAN = BLOCKS.register("waxed_petal_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_BRANCH_CORAL_FAN = BLOCKS.register("waxed_branch_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_ROCK_CORAL_FAN = BLOCKS.register("waxed_rock_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_PILLOW_CORAL_FAN = BLOCKS.register("waxed_pillow_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_SILK_CORAL_FAN = BLOCKS.register("waxed_silk_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_CHROME_CORAL_FAN = BLOCKS.register("waxed_chrome_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_PRISMARINE_CORAL_FAN = BLOCKS.register("waxed_prismarine_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_ELDER_PRISMARINE_CORAL_FAN = BLOCKS.register("waxed_elder_prismarine_coral_fan", () -> new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> WAXED_ACAN_CORAL_WALL_FAN = BLOCKS.register("waxed_acan_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_ACAN_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_FINGER_CORAL_WALL_FAN = BLOCKS.register("waxed_finger_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_FINGER_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_STAR_CORAL_WALL_FAN = BLOCKS.register("waxed_star_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_STAR_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_MOSS_CORAL_WALL_FAN = BLOCKS.register("waxed_moss_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_MOSS_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_PETAL_CORAL_WALL_FAN = BLOCKS.register("waxed_petal_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_PETAL_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_BRANCH_CORAL_WALL_FAN = BLOCKS.register("waxed_branch_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_BRANCH_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_ROCK_CORAL_WALL_FAN = BLOCKS.register("waxed_rock_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_ROCK_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_PILLOW_CORAL_WALL_FAN = BLOCKS.register("waxed_pillow_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_PILLOW_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_SILK_CORAL_WALL_FAN = BLOCKS.register("waxed_silk_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_SILK_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_CHROME_CORAL_WALL_FAN = BLOCKS.register("waxed_chrome_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_CHROME_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_PRISMARINE_CORAL_WALL_FAN = BLOCKS.register("waxed_prismarine_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_PRISMARINE_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_ELDER_PRISMARINE_CORAL_WALL_FAN = BLOCKS.register("waxed_elder_prismarine_coral_wall_fan", () -> new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_PRISMARINE_CORAL_FAN.get()).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> WAXED_PRISMARINE_CORAL_SHOWER = registerBlock("waxed_prismarine_coral_shower", () -> new WaxedCoralShowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WAXED_ELDER_PRISMARINE_CORAL_SHOWER = registerBlock("waxed_elder_prismarine_coral_shower", () -> new WaxedCoralShowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    //Supplementaries Waxed Blocks
    public static final RegistryObject<Block> WAXED_SUGAR_CUBE = registerBlockS("waxed_sugar_cube", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> WAXED_RAKED_GRAVEL = registerBlockS("waxed_raked_gravel",  () -> ModList.get().isLoaded("supplementaries") ? CandleHolders.WAXED_RAKED_GRAVEL.get() : new Block(BlockBehaviour.Properties.copy(Blocks.GRAVEL)));
    public static final RegistryObject<Block> WAXED_SOAP_BLOCK = registerBlockS("waxed_soap_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.PINK).instrument(NoteBlockInstrument.DIDGERIDOO).strength(1.25F, 4.0F).sound(SoundType.CORAL_BLOCK)));
    //Oreganized Waxed Blocks
    public static final RegistryObject<Block> WAXED_GROOVED_ICE = registerBlockO("waxed_grooved_ice", () -> new Block(BlockBehaviour.Properties.copy(Blocks.ICE)));

    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }

    private static Block tall_candle(MapColor pMapColor) {
        return new TallCandleBlock(BlockBehaviour.Properties.of().mapColor(pMapColor).strength(0.35f).sound(SoundType.CANDLE).pushReaction(PushReaction.PUSH_ONLY).forceSolidOn().noOcclusion());
    }

    private static Block tall_candle(MapColor pMapColor, Boolean isSoul, ResourceLocation particle) {
        return new TallCandleBlock(isSoul, particle, BlockBehaviour.Properties.of().mapColor(pMapColor).strength(0.35f).sound(SoundType.CANDLE).pushReaction(PushReaction.PUSH_ONLY).forceSolidOn().noOcclusion());
    }

    private static Block wax_block(MapColor pMapColor) {
        return new Block(BlockBehaviour.Properties.of().mapColor(pMapColor).strength(0.6f).sound(SoundType.CORAL_BLOCK).pushReaction(PushReaction.PUSH_ONLY));
    }

    private static Block wax_pillar(MapColor pMapColor) {
        return new WaxPillarBlock(BlockBehaviour.Properties.of().mapColor(pMapColor).strength(0.6f).sound(SoundType.CORAL_BLOCK).pushReaction(PushReaction.PUSH_ONLY));
    }

    private static Block waxed_concrete_powder(MapColor pMapColor) {
        return new Block(BlockBehaviour.Properties.of().mapColor(pMapColor).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sound(SoundType.SAND));
    }
    
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockS(String name, Supplier<T> block) {
        if (ModList.get().isLoaded("supplementaries")) {
            RegistryObject<T> toReturn = BLOCKS_S.register(name, block);
            registerBlockItem(name, toReturn);
            return toReturn;
        } else {
            RegistryObject<T> toReturn = BLOCKS.register(name, block);
            registerBlockItem(name, toReturn);
            return toReturn;
        }
    }

    private static <T extends Block> RegistryObject<T> registerBlockO(String name, Supplier<T> block) {
        if (ModList.get().isLoaded("oreganized")) {
            RegistryObject<T> toReturn = BLOCKS_O.register(name, block);
            registerBlockItem(name, toReturn);
            return toReturn;
        } else {
            RegistryObject<T> toReturn = BLOCKS.register(name, block);
            registerBlockItem(name, toReturn);
            return toReturn;
        }
    }

    public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return WItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return (blockState) -> {
            return blockState.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
        };
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCKS_S.register(eventBus);
        BLOCKS_V.register(eventBus);
        BLOCKS_O.register(eventBus);
    }
}
