package net.im_maker.waxed.common.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.im_maker.waxed.common.block.custom.*;
import net.im_maker.waxed.common.particles.WParticles;
import net.im_maker.waxed.Waxed;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

public class WBlocks {
    //Stuff
    public static final Block EMPTY_HONEYCOMB = registerBlock("empty_honeycomb", new EmptyHoneycombBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.6f).sound(SoundType.CORAL_BLOCK)));
    //public static final Block HONEYCOMB_BLOCK = registerBlockV("honeycomb_block", new HoneycombBlock(BlockBehaviour.Properties.copy(Blocks.HONEYCOMB_BLOCK)));

    public static final Block WICK = registerBlock("wick", new WickBlock(1, BlockBehaviour.Properties.of().noCollission().lightLevel(WickBlock.LIGHT_EMISSION).strength(0.1f).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));
    public static final Block SOUL_WICK = registerBlockNoItem("soul_wick", new WickBlock(2, BlockBehaviour.Properties.of().noCollission().lightLevel(WickBlock.SOUL_LIGHT_EMISSION).strength(0.1f).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));
    public static final Block SOUL_CANDLE = registerBlock("soul_candle", new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(SoulCandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_SOUL_FLAME));
    public static final Block SOUL_CANDLE_CAKE = registerBlockNoItem("soul_candle_cake", new SoulCandleCakeBlock(SOUL_CANDLE, BlockBehaviour.Properties.copy(Blocks.CAKE).lightLevel(litBlockEmission(2)), WParticles.PARTICLES.SMALL_SOUL_FLAME));

    public static final Block SOUL_CANDLE_HOLDER = registerBlockS("soul_candle_holder", FabricLoader.getInstance().isModLoaded("supplementaries") ? CandleHolders.SOUL_CANDLE_HOLDER.get() : new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(SoulCandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_SOUL_FLAME));
    public static final Block GOLD_SOUL_CANDLE_HOLDER = registerBlock("gold_soul_candle_holder", FabricLoader.getInstance().isModLoaded("suppsquared") ? CandleHolders.GOLD_SOUL_CANDLE_HOLDER.get() : new SoulCandleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noOcclusion().strength(0.1F).sound(SoundType.CANDLE).lightLevel(SoulCandleBlock.LIGHT_EMISSION).pushReaction(PushReaction.DESTROY), WParticles.PARTICLES.SMALL_SOUL_FLAME));

    public static final Block TALL_CANDLE = registerBlock("tall_candle", tall_candle(MapColor.SAND));
    public static final Block SOUL_TALL_CANDLE = registerBlock("soul_tall_candle", tall_candle(MapColor.COLOR_BROWN, true, WParticles.PARTICLES.SOUL_FLAME));
    public static final List<Block> COLORED_TALL_CANDLE = registerColoredTallCandle();
    private static <T extends Block> List<Block> registerColoredTallCandle () {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_tall_candle";
            Block block = registerBlockNoItem(blockId, tall_candle(color.getMapColor()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }
    
    public static final Block WAX_BLOCK = registerBlock("wax_block", wax_block(MapColor.SAND));
    public static final Block SOUL_WAX_BLOCK = registerBlock("soul_wax_block", wax_block(MapColor.COLOR_BROWN));
    public static final List<Block> COLORED_WAX_BLOCK = registerColoredWaxBlock();

    private static <T extends Block> List<Block> registerColoredWaxBlock () {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_wax_block";
            Block block = registerBlockNoItem(blockId, wax_block(color.getMapColor()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    //Wax Pillars
    public static final Block WAX_PILLAR = registerBlock("wax_pillar", wax_pillar(MapColor.SAND));
    public static final Block SOUL_WAX_PILLAR = registerBlock("soul_wax_pillar", wax_pillar(MapColor.COLOR_BROWN));
    public static final List<Block> COLORED_WAX_PILLAR = registerColoredWaxPillar();

    private static <T extends Block> List<Block> registerColoredWaxPillar () {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = color.getName() + "_wax_pillar";
            Block block = registerBlockNoItem(blockId, wax_pillar(color.getMapColor()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }

    //Waxed Blocks
    public static final Block WAXED_SAND = registerBlock("waxed_sand", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block WAXED_RED_SAND = registerBlock("waxed_red_sand", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.SNARE).strength(0.5f).sound(SoundType.SAND)));
    public static final Block WAXED_GRAVEL = registerBlock("waxed_gravel", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.SNARE).strength(0.6f).sound(SoundType.GRAVEL)));
    public static final Block WAXED_ICE = registerBlock("waxed_ice", new HalfTransparentBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).strength(0.5F).sound(SoundType.GLASS).noOcclusion()));
    public static final Block WAXED_SPONGE = registerBlock("waxed_sponge", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.6F).sound(SoundType.GRASS)));
    public static final List<Block> WAXED_COLORED_CONCRETE_POWDER = registerWaxedColoredConcretePowder();
    private static <T extends Block> List<Block> registerWaxedColoredConcretePowder () {
        List<Block> coloredBlocks = new ArrayList<>();
        for (DyeColor color : DyeColor.values()) {
            String blockId = "waxed_" + color.getName() + "_concrete_powder";
            Block block = registerBlockNoItem(blockId, waxed_concrete_powder(color.getMapColor()));
            registerBlockItem(blockId, block);
            coloredBlocks.add(block);
        }
        return coloredBlocks;
    }
    public static final Block WAXED_TUBE_CORAL_BLOCK = registerBlock("waxed_tube_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_BRAIN_CORAL_BLOCK = registerBlock("waxed_brain_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_BUBBLE_CORAL_BLOCK = registerBlock("waxed_bubble_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_FIRE_CORAL_BLOCK = registerBlock("waxed_fire_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_HORN_CORAL_BLOCK = registerBlock("waxed_horn_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_TUBE_CORAL = registerBlock("waxed_tube_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_BRAIN_CORAL = registerBlock("waxed_brain_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_BUBBLE_CORAL = registerBlock("waxed_bubble_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_FIRE_CORAL = registerBlock("waxed_fire_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_HORN_CORAL = registerBlock("waxed_horn_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_TUBE_CORAL_FAN = registerBlockNoItem("waxed_tube_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_BRAIN_CORAL_FAN = registerBlockNoItem("waxed_brain_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_BUBBLE_CORAL_FAN = registerBlockNoItem("waxed_bubble_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_FIRE_CORAL_FAN = registerBlockNoItem("waxed_fire_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_HORN_CORAL_FAN = registerBlockNoItem("waxed_horn_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_TUBE_CORAL_WALL_FAN = registerBlockNoItem("waxed_tube_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_TUBE_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_BRAIN_CORAL_WALL_FAN = registerBlockNoItem("waxed_brain_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_BRAIN_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_BUBBLE_CORAL_WALL_FAN = registerBlockNoItem("waxed_bubble_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_BUBBLE_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_FIRE_CORAL_WALL_FAN = registerBlockNoItem("waxed_fire_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_FIRE_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_HORN_CORAL_WALL_FAN = registerBlockNoItem("waxed_horn_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_HORN_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    //Upgrade Aquatic Waxed Blocks
    public static final Block WAXED_ACAN_CORAL_BLOCK = registerBlock("waxed_acan_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_FINGER_CORAL_BLOCK = registerBlock("waxed_finger_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_STAR_CORAL_BLOCK = registerBlock("waxed_star_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_MOSS_CORAL_BLOCK = registerBlock("waxed_moss_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_PETAL_CORAL_BLOCK = registerBlock("waxed_petal_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_BRANCH_CORAL_BLOCK = registerBlock("waxed_branch_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_ROCK_CORAL_BLOCK = registerBlock("waxed_rock_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_PILLOW_CORAL_BLOCK = registerBlock("waxed_pillow_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_SILK_CORAL_BLOCK = registerBlock("waxed_silk_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_CHROME_CORAL_BLOCK = registerBlock("waxed_chrome_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_PRISMARINE_CORAL_BLOCK = registerBlock("waxed_prismarine_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_ELDER_PRISMARINE_CORAL_BLOCK = registerBlock("waxed_elder_prismarine_coral_block", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.CORAL_BLOCK)));

    public static final Block WAXED_ACAN_CORAL = registerBlock("waxed_acan_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_FINGER_CORAL = registerBlock("waxed_finger_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_STAR_CORAL = registerBlock("waxed_star_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_MOSS_CORAL = registerBlock("waxed_moss_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_PETAL_CORAL = registerBlock("waxed_petal_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_BRANCH_CORAL = registerBlock("waxed_branch_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_ROCK_CORAL = registerBlock("waxed_rock_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_PILLOW_CORAL = registerBlock("waxed_pillow_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_SILK_CORAL = registerBlock("waxed_silk_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_CHROME_CORAL = registerBlock("waxed_chrome_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_PRISMARINE_CORAL = registerBlock("waxed_prismarine_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_ELDER_PRISMARINE_CORAL = registerBlock("waxed_elder_prismarine_coral", new BaseCoralPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final Block WAXED_ACAN_CORAL_FAN = registerBlockNoItem("waxed_acan_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_FINGER_CORAL_FAN = registerBlockNoItem("waxed_finger_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_STAR_CORAL_FAN = registerBlockNoItem("waxed_star_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_MOSS_CORAL_FAN = registerBlockNoItem("waxed_moss_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_PETAL_CORAL_FAN = registerBlockNoItem("waxed_petal_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_BRANCH_CORAL_FAN = registerBlockNoItem("waxed_branch_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_ROCK_CORAL_FAN = registerBlockNoItem("waxed_rock_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_PILLOW_CORAL_FAN = registerBlockNoItem("waxed_pillow_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_SILK_CORAL_FAN = registerBlockNoItem("waxed_silk_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_CHROME_CORAL_FAN = registerBlockNoItem("waxed_chrome_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_PRISMARINE_CORAL_FAN = registerBlockNoItem("waxed_prismarine_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_ELDER_PRISMARINE_CORAL_FAN = registerBlockNoItem("waxed_elder_prismarine_coral_fan", new BaseCoralFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    public static final Block WAXED_ACAN_CORAL_WALL_FAN = registerBlockNoItem("waxed_acan_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_ACAN_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_FINGER_CORAL_WALL_FAN = registerBlockNoItem("waxed_finger_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_FINGER_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_STAR_CORAL_WALL_FAN = registerBlockNoItem("waxed_star_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_STAR_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_MOSS_CORAL_WALL_FAN = registerBlockNoItem("waxed_moss_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_MOSS_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_PETAL_CORAL_WALL_FAN = registerBlockNoItem("waxed_petal_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_PETAL_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_BRANCH_CORAL_WALL_FAN = registerBlockNoItem("waxed_branch_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_BRANCH_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_ROCK_CORAL_WALL_FAN = registerBlockNoItem("waxed_rock_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_ROCK_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_PILLOW_CORAL_WALL_FAN = registerBlockNoItem("waxed_pillow_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_PILLOW_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_SILK_CORAL_WALL_FAN = registerBlockNoItem("waxed_silk_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_SILK_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_CHROME_CORAL_WALL_FAN = registerBlockNoItem("waxed_chrome_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GRAY).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_CHROME_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_PRISMARINE_CORAL_WALL_FAN = registerBlockNoItem("waxed_prismarine_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_PRISMARINE_CORAL_FAN).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_ELDER_PRISMARINE_CORAL_WALL_FAN = registerBlockNoItem("waxed_elder_prismarine_coral_wall_fan", new BaseCoralWallFanBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).dropsLike(WAXED_PRISMARINE_CORAL_FAN).pushReaction(PushReaction.DESTROY)));

    public static final Block WAXED_PRISMARINE_CORAL_SHOWER = registerBlock("waxed_prismarine_coral_shower", new WaxedCoralShowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));
    public static final Block WAXED_ELDER_PRISMARINE_CORAL_SHOWER = registerBlock("waxed_elder_prismarine_coral_shower", new WaxedCoralShowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY)));

    //Supplementaries Waxed Blocks
    public static final Block WAXED_SUGAR_CUBE = registerBlock("waxed_sugar_cube", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).strength(0.5F).sound(SoundType.SAND)));
    public static final Block WAXED_SOAP_BLOCK = registerBlock("waxed_soap_block", new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.PINK).instrument(NoteBlockInstrument.DIDGERIDOO).strength(1.25F, 4.0F).sound(SoundType.CORAL_BLOCK)));
    public static final Block WAXED_RAKED_GRAVEL = registerBlock("waxed_raked_gravel",  FabricLoader.getInstance().isModLoaded("supplementaries") ? CandleHolders.WAXED_RAKED_GRAVEL.get() : new Block(BlockBehaviour.Properties.copy(Blocks.GRAVEL)));

    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Waxed.MOD_ID, name), block);
    }

    private static Block registerBlockNoItem(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Waxed.MOD_ID, name), block);
    }

    public static Block registerBlockS(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation("supplementaries", name), block);
    }

    private static Block registerBlockV(String name, Block block) {
        //registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation("minecraft", name), block);
    }

    public static Item registerBlockItem(String name, Block block) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Waxed.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
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

    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }

    private static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return (blockState) -> {
            return blockState.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
        };
    }

    public static void registerBlocks() {
        Waxed.LOGGER.info("Registering Mod Blocks for " + Waxed.MOD_ID);
    }
}
