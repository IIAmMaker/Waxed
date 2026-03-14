package net.im_maker.waxed.datagen;

import com.ninni.dye_depot.registry.DDDyes;
import com.teamabnormals.caverns_and_chasms.core.other.tags.CCBlockTags;
import com.teamabnormals.endergetic.core.other.tags.EEBlockTags;
import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.util.WTags;
import net.mehvahdjukaar.supplementaries.reg.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Waxed.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        List<DyeColor> colors = new ArrayList<>(Arrays.asList(
                DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
                DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
                DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
                DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
        ));
        List<DDDyes> colors2 = new ArrayList<>();
        if (ModList.get().isLoaded("dye_depot")) {
            colors2 = new ArrayList<>(Arrays.asList(
                    DDDyes.MAROON, DDDyes.ROSE, DDDyes.CORAL, DDDyes.INDIGO,
                    DDDyes.NAVY, DDDyes.SLATE, DDDyes.CORAL, DDDyes.AMBER,
                    DDDyes.BEIGE, DDDyes.TEAL, DDDyes.MINT, DDDyes.AQUA,
                    DDDyes.VERDANT, DDDyes.FOREST, DDDyes.GINGER, DDDyes.TAN
            ));
        }

        if (ModList.get().isLoaded("oreganized")) {
            this.tag(TagKey.create(Registries.BLOCK, new ResourceLocation("oreganized","mineable/scribe"))).add(
                    WBlocks.WAXED_ICE.get(),
                    WBlocks.WAXED_GROOVED_ICE.get()
            );
        }

        this.tag(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON).add(
                WBlocks.WAXED_ICE.get(),
                WBlocks.WAXED_GROOVED_ICE.get()
        );

        this.tag(BlockTags.POLAR_BEARS_SPAWNABLE_ON_ALTERNATE).add(
                WBlocks.WAXED_ICE.get(),
                WBlocks.WAXED_GROOVED_ICE.get()
        );

        this.tag(BlockTags.STAIRS).add(
                WBlocks.WAXED_PRISMARINE_STAIRS.get()
        );

        this.tag(BlockTags.SLABS).add(
                WBlocks.WAXED_PRISMARINE_SLAB.get()
        );

        this.tag(BlockTags.WALLS).add(
                WBlocks.WAXED_PRISMARINE_WALL.get()
        );

        this.tag(BlockTags.CANDLES).add(
                WBlocks.SOUL_CANDLE.get(),
                WBlocks.CUPRIC_CANDLE.get(),
                WBlocks.ENDER_CANDLE.get()
        );

        this.tag(BlockTags.CANDLE_CAKES).add(
                WBlocks.SOUL_CANDLE_CAKE.get(),
                WBlocks.CUPRIC_CANDLE_CAKE.get(),
                WBlocks.ENDER_CANDLE_CAKE.get()
        );

        this.tag(BlockTags.PIGLIN_REPELLENTS).add(
                WBlocks.SOUL_CANDLE.get(),
                WBlocks.SOUL_CANDLE_CAKE.get(),
                WBlocks.SOUL_CANDLE_HOLDER.get(),
                WBlocks.GOLD_SOUL_CANDLE_HOLDER.get()
        );

        for (DyeColor color : colors) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                    Waxed.getBlockFromString(color + "_wax_block"),
                    Waxed.getBlockFromString(color + "_wax_pillar"),
                    Waxed.getBlockFromString(color + "_tall_candle")
            );
            this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                    Waxed.getBlockFromString(color + "_wax_block"),
                    Waxed.getBlockFromString(color + "_wax_pillar"),
                    Waxed.getBlockFromString(color + "_tall_candle"),
                    Waxed.getBlockFromString("waxed_" + color + "_concrete_powder")
            );

            this.tag(WTags.Blocks.WAX_PILLARS).add(
                    Waxed.getBlockFromString(color + "_wax_pillar")
            );

            this.tag(WTags.Blocks.TALL_CANDLES).add(
                    Waxed.getBlockFromString(color + "_tall_candle")
            );

            this.tag(ModTags.LIGHTABLE_BY_GUNPOWDER).add(
                    Waxed.getBlockFromString(color + "_tall_candle")
            );

            this.tag(vectorwing.farmersdelight.common.tag.ModTags.TRAY_HEAT_SOURCES).add(
                    Waxed.getBlockFromString(color + "_tall_candle")
            );
        }

        this.tag(WTags.Blocks.WAX_PILLARS).add(
                WBlocks.WAX_PILLAR.get(),
                WBlocks.SOUL_WAX_PILLAR.get(),
                WBlocks.CUPRIC_WAX_PILLAR.get(),
                WBlocks.ENDER_WAX_PILLAR.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                WBlocks.WAX_BLOCK.get(),
                WBlocks.SOUL_WAX_BLOCK.get(),
                WBlocks.CUPRIC_WAX_BLOCK.get(),
                WBlocks.ENDER_WAX_BLOCK.get(),
                WBlocks.WAX_PILLAR.get(),
                WBlocks.SOUL_WAX_PILLAR.get(),
                WBlocks.CUPRIC_WAX_PILLAR.get(),
                WBlocks.ENDER_WAX_PILLAR.get(),
                WBlocks.TALL_CANDLE.get(),
                WBlocks.SOUL_TALL_CANDLE.get(),
                WBlocks.CUPRIC_TALL_CANDLE.get(),
                WBlocks.ENDER_TALL_CANDLE.get(),
                WBlocks.WAXED_REDSTONE_BLOCK.get(),
                WBlocks.WAXED_MAGMA_BLOCK.get(),
                WBlocks.WAXED_TUBE_CORAL_BLOCK.get(),
                WBlocks.WAXED_BRAIN_CORAL_BLOCK.get(),
                WBlocks.WAXED_BUBBLE_CORAL_BLOCK.get(),
                WBlocks.WAXED_FIRE_CORAL_BLOCK.get(),
                WBlocks.WAXED_HORN_CORAL_BLOCK.get(),
                WBlocks.WAXED_GROOVED_ICE.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                WBlocks.WAX_BLOCK.get(),
                WBlocks.SOUL_WAX_BLOCK.get(),
                WBlocks.CUPRIC_WAX_BLOCK.get(),
                WBlocks.ENDER_WAX_BLOCK.get(),
                WBlocks.WAX_PILLAR.get(),
                WBlocks.SOUL_WAX_PILLAR.get(),
                WBlocks.CUPRIC_WAX_PILLAR.get(),
                WBlocks.ENDER_WAX_PILLAR.get(),
                WBlocks.TALL_CANDLE.get(),
                WBlocks.SOUL_TALL_CANDLE.get(),
                WBlocks.CUPRIC_TALL_CANDLE.get(),
                WBlocks.ENDER_TALL_CANDLE.get(),
                WBlocks.WAXED_SAND.get(),
                WBlocks.WAXED_RED_SAND.get(),
                WBlocks.WAXED_GRAVEL.get(),
                WBlocks.WAXED_POWDER_SNOW.get(),
                WBlocks.WAXED_ICE.get(),
                WBlocks.WAXED_SOUL_SAND.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                WBlocks.WAXED_SPONGE.get()
        );

        this.tag(BlockTags.BAMBOO_PLANTABLE_ON).add(
                WBlocks.WAXED_SAND.get(),
                WBlocks.WAXED_RED_SAND.get(),
                WBlocks.WAXED_GRAVEL.get()
        );

        this.tag(BlockTags.SAND).add(
                WBlocks.WAXED_SAND.get(),
                WBlocks.WAXED_RED_SAND.get()
        );

        this.tag(BlockTags.SMELTS_TO_GLASS).add(
                WBlocks.WAXED_SAND.get(),
                WBlocks.WAXED_RED_SAND.get()
        );

        this.tag(Tags.Blocks.SAND).add(
                WBlocks.WAXED_SAND.get(),
                WBlocks.WAXED_RED_SAND.get()
        );

        this.tag(Tags.Blocks.SAND_COLORLESS).add(
                WBlocks.WAXED_SAND.get()
        );

        this.tag(Tags.Blocks.SAND_RED).add(
                WBlocks.WAXED_RED_SAND.get()
        );

        this.tag(BlockTags.SNOW).add(
                WBlocks.WAXED_POWDER_SNOW.get()
        );

        this.tag(BlockTags.ICE).add(
                WBlocks.WAXED_ICE.get(),
                WBlocks.WAXED_GROOVED_ICE.get()
        );

        this.tag(BlockTags.INFINIBURN_OVERWORLD).add(
                WBlocks.WAXED_MAGMA_BLOCK.get()
        );

        this.tag(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON).add(
                WBlocks.WAXED_SOUL_SAND.get()
        );

        this.tag(BlockTags.SOUL_SPEED_BLOCKS).add(
                WBlocks.WAXED_SOUL_SAND.get(),
                WBlocks.SOUL_WAX_BLOCK.get(),
                WBlocks.SOUL_WAX_PILLAR.get()
        );

        this.tag(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(
                WBlocks.WAXED_SOUL_SAND.get(),
                WBlocks.SOUL_WAX_BLOCK.get(),
                WBlocks.SOUL_WAX_PILLAR.get()
        );

        if (ModList.get().isLoaded("caverns_and_chasms")) {
            this.tag(CCBlockTags.CUPRIC_FIRE_BASE_BLOCKS).add(
                    WBlocks.CUPRIC_WAX_BLOCK.get(),
                    WBlocks.CUPRIC_WAX_PILLAR.get()
            );
        }

        if (ModList.get().isLoaded("endergetic")) {
            this.tag(EEBlockTags.ENDER_FIRE_BASE_BLOCKS).add(
                    WBlocks.ENDER_WAX_BLOCK.get(),
                    WBlocks.ENDER_WAX_PILLAR.get()
            );
        }

        this.tag(BlockTags.WITHER_SUMMON_BASE_BLOCKS).add(
                WBlocks.WAXED_SOUL_SAND.get()
        );

        this.tag(Tags.Blocks.STORAGE_BLOCKS).add(
                WBlocks.WAXED_REDSTONE_BLOCK.get()
        );

        this.tag(Tags.Blocks.STORAGE_BLOCKS_REDSTONE).add(
                WBlocks.WAXED_REDSTONE_BLOCK.get()
        );

        this.tag(WTags.Blocks.TALL_CANDLES).add(
                WBlocks.TALL_CANDLE.get(),
                WBlocks.SOUL_TALL_CANDLE.get(),
                WBlocks.CUPRIC_TALL_CANDLE.get(),
                WBlocks.ENDER_TALL_CANDLE.get()
        );

        this.tag(ModTags.LIGHTABLE_BY_GUNPOWDER).add(
                WBlocks.WICK.get(),
                WBlocks.SOUL_WICK.get(),
                WBlocks.CUPRIC_WICK.get(),
                WBlocks.ENDER_WICK.get(),
                WBlocks.TALL_CANDLE.get(),
                WBlocks.SOUL_TALL_CANDLE.get(),
                WBlocks.CUPRIC_TALL_CANDLE.get(),
                WBlocks.ENDER_TALL_CANDLE.get()
        );

        this.tag(vectorwing.farmersdelight.common.tag.ModTags.TRAY_HEAT_SOURCES).add(
                WBlocks.WICK.get(),
                WBlocks.SOUL_WICK.get(),
                WBlocks.CUPRIC_WICK.get(),
                WBlocks.ENDER_WICK.get(),
                WBlocks.TALL_CANDLE.get(),
                WBlocks.SOUL_TALL_CANDLE.get(),
                WBlocks.CUPRIC_TALL_CANDLE.get(),
                WBlocks.ENDER_TALL_CANDLE.get()
        );
    }
}