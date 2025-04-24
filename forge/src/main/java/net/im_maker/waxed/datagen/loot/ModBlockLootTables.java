package net.im_maker.waxed.datagen.loot;

import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.block.block_values.CandlePart;
import net.im_maker.waxed.common.block.custom.TallCandleBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(WBlocks.EMPTY_HONEYCOMB.get());
        this.dropSelf(WBlocks.WICK.get());
        this.add(WBlocks.SOUL_WICK.get(), createItemDrops(WBlocks.SOUL_WICK.get(), WBlocks.WICK.get().asItem()));
        this.add(WBlocks.SOUL_CANDLE.get(), (block) -> {
            return this.createCandleDrops(block);
        });
        this.add(WBlocks.SOUL_CANDLE_HOLDER.get(), (block) -> {
            return this.createCandleDrops(block);
        });
        this.add(WBlocks.GOLD_SOUL_CANDLE_HOLDER.get(), (block) -> {
            return this.createCandleDrops(block);
        });
        this.add(WBlocks.SOUL_CANDLE_CAKE.get(), createCandleCakeDrops(WBlocks.SOUL_CANDLE.get()));

        this.add(WBlocks.CUPRIC_WICK.get(), createItemDrops(WBlocks.CUPRIC_WICK.get(), WBlocks.WICK.get().asItem()));
        this.add(WBlocks.CUPRIC_CANDLE.get(), (block) -> {
            return this.createCandleDrops(block);
        });
        this.add(WBlocks.CUPRIC_CANDLE_HOLDER.get(), (block) -> {
            return this.createCandleDrops(block);
        });
        this.add(WBlocks.GOLD_CUPRIC_CANDLE_HOLDER.get(), (block) -> {
            return this.createCandleDrops(block);
        });
        this.add(WBlocks.CUPRIC_CANDLE_CAKE.get(), createCandleCakeDrops(WBlocks.CUPRIC_CANDLE.get()));

        this.add(WBlocks.ENDER_WICK.get(), createItemDrops(WBlocks.ENDER_WICK.get(), WBlocks.WICK.get().asItem()));
        this.add(WBlocks.ENDER_CANDLE.get(), (block) -> {
            return this.createCandleDrops(block);
        });
        this.add(WBlocks.ENDER_CANDLE_HOLDER.get(), (block) -> {
            return this.createCandleDrops(block);
        });
        this.add(WBlocks.GOLD_ENDER_CANDLE_HOLDER.get(), (block) -> {
            return this.createCandleDrops(block);
        });
        this.add(WBlocks.ENDER_CANDLE_CAKE.get(), createCandleCakeDrops(WBlocks.ENDER_CANDLE.get()));

        this.dropSelf(WBlocks.WAX_BLOCK.get());
        this.dropSelf(WBlocks.SOUL_WAX_BLOCK.get());
        this.dropSelf(WBlocks.CUPRIC_WAX_BLOCK.get());
        this.dropSelf(WBlocks.ENDER_WAX_BLOCK.get());

        this.add(WBlocks.TALL_CANDLE.get(), this::createTallCandleDrops);
        this.add(WBlocks.SOUL_TALL_CANDLE.get(), this::createTallCandleDrops);
        this.add(WBlocks.CUPRIC_TALL_CANDLE.get(), this::createTallCandleDrops);
        this.add(WBlocks.ENDER_TALL_CANDLE.get(), this::createTallCandleDrops);

        for (DyeColor color : DyeColor.values()) {
            this.dropSelf(Waxed.getBlockFromString(color + "_wax_block"));
            this.dropSelf(Waxed.getBlockFromString(color + "_wax_pillar"));
            this.add(Waxed.getBlockFromString(color + "_tall_candle"), (block) -> {
                return this.createTallCandleDrops(block);
            });


            this.dropSelf(Waxed.getBlockFromString("waxed_" + color + "_concrete_powder"));
        }

        this.dropSelf(WBlocks.WAX_PILLAR.get());
        this.dropSelf(WBlocks.SOUL_WAX_PILLAR.get());
        this.dropSelf(WBlocks.CUPRIC_WAX_PILLAR.get());
        this.dropSelf(WBlocks.ENDER_WAX_PILLAR.get());

        this.dropSelf(WBlocks.WAXED_SAND.get());
        this.dropSelf(WBlocks.WAXED_RED_SAND.get());
        this.dropSelf(WBlocks.WAXED_GRAVEL.get());
        this.dropSelf(WBlocks.WAXED_POWDER_SNOW.get());
        this.dropSelf(WBlocks.WAXED_ICE.get());
        this.dropSelf(WBlocks.WAXED_PRISMARINE.get());
        this.dropSelf(WBlocks.WAXED_PRISMARINE_STAIRS.get());
        this.dropSelf(WBlocks.WAXED_PRISMARINE_SLAB.get());
        this.dropSelf(WBlocks.WAXED_PRISMARINE_WALL.get());
        this.dropSelf(WBlocks.WAXED_REDSTONE_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_MAGMA_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_SOUL_SAND.get());
        this.dropSelf(WBlocks.WAXED_SPONGE.get());
        this.add(WBlocks.WAXED_COBWEB.get(), block -> createItemDropsFromShears(WBlocks.WAXED_COBWEB.get(), Items.STRING));
        this.dropSelf(WBlocks.WAXED_TUBE_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_BRAIN_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_BUBBLE_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_FIRE_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_HORN_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_TUBE_CORAL.get());
        this.dropSelf(WBlocks.WAXED_BRAIN_CORAL.get());
        this.dropSelf(WBlocks.WAXED_BUBBLE_CORAL.get());
        this.dropSelf(WBlocks.WAXED_FIRE_CORAL.get());
        this.dropSelf(WBlocks.WAXED_HORN_CORAL.get());
        this.dropSelf(WBlocks.WAXED_TUBE_CORAL_FAN.get());
        this.dropSelf(WBlocks.WAXED_BRAIN_CORAL_FAN.get());
        this.dropSelf(WBlocks.WAXED_BUBBLE_CORAL_FAN.get());
        this.dropSelf(WBlocks.WAXED_FIRE_CORAL_FAN.get());
        this.dropSelf(WBlocks.WAXED_HORN_CORAL_FAN.get());

        this.dropSelf(WBlocks.WAXED_ACAN_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_ACAN_CORAL.get());
        this.dropSelf(WBlocks.WAXED_ACAN_CORAL_FAN.get());

        this.dropSelf(WBlocks.WAXED_FINGER_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_FINGER_CORAL.get());
        this.dropSelf(WBlocks.WAXED_FINGER_CORAL_FAN.get());

        this.dropSelf(WBlocks.WAXED_STAR_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_STAR_CORAL.get());
        this.dropSelf(WBlocks.WAXED_STAR_CORAL_FAN.get());

        this.dropSelf(WBlocks.WAXED_MOSS_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_MOSS_CORAL.get());
        this.dropSelf(WBlocks.WAXED_MOSS_CORAL_FAN.get());

        this.dropSelf(WBlocks.WAXED_PETAL_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_PETAL_CORAL.get());
        this.dropSelf(WBlocks.WAXED_PETAL_CORAL_FAN.get());

        this.dropSelf(WBlocks.WAXED_BRANCH_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_BRANCH_CORAL.get());
        this.dropSelf(WBlocks.WAXED_BRANCH_CORAL_FAN.get());

        this.dropSelf(WBlocks.WAXED_ROCK_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_ROCK_CORAL.get());
        this.dropSelf(WBlocks.WAXED_ROCK_CORAL_FAN.get());

        this.dropSelf(WBlocks.WAXED_PILLOW_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_PILLOW_CORAL.get());
        this.dropSelf(WBlocks.WAXED_PILLOW_CORAL_FAN.get());

        this.dropSelf(WBlocks.WAXED_SILK_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_SILK_CORAL.get());
        this.dropSelf(WBlocks.WAXED_SILK_CORAL_FAN.get());

        this.dropSelf(WBlocks.WAXED_CHROME_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_CHROME_CORAL.get());
        this.dropSelf(WBlocks.WAXED_CHROME_CORAL_FAN.get());

        this.dropSelf(WBlocks.WAXED_PRISMARINE_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_PRISMARINE_CORAL.get());
        this.dropSelf(WBlocks.WAXED_PRISMARINE_CORAL_FAN.get());
        this.dropSelf(WBlocks.WAXED_PRISMARINE_CORAL_SHOWER.get());
        this.dropSelf(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_ELDER_PRISMARINE_CORAL.get());
        this.dropSelf(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_FAN.get());
        this.dropSelf(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_SHOWER.get());

        this.dropSelf(WBlocks.WAXED_SUGAR_CUBE.get());
        this.dropSelf(WBlocks.WAXED_SOAP_BLOCK.get());
        this.dropSelf(WBlocks.WAXED_RAKED_GRAVEL.get());

        //this.dropSelf(WBlocks.WAXED_GROOVED_ICE.get());
    }

    protected LootTable.Builder createCandleDrops(Block pCandleBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(pCandleBlock, LootItem.lootTableItem(pCandleBlock).apply(List.of(2, 3, 4), (integer) -> {
            return SetItemCountFunction.setCount(ConstantValue.exactly((float)integer.intValue())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pCandleBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CandleBlock.CANDLES, integer)));
        }))));
    }

    protected LootTable.Builder createTallCandleDrops(Block pCandleBlock) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionDecay(pCandleBlock,
                                LootItem.lootTableItem(pCandleBlock)
                                        .apply(List.of(1, 2), (integer) -> {
                                            if (integer == 1) {
                                                // Drop 1 if SHORT
                                                return SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))
                                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pCandleBlock)
                                                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                        .hasProperty(TallCandleBlock.CANDLE_PART, CandlePart.SHORT)));
                                            } else {
                                                // Drop 2 if MIDDLE or TALL
                                                return SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                                        .when(
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(pCandleBlock)
                                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                                .hasProperty(TallCandleBlock.CANDLE_PART, CandlePart.MIDDLE))
                                                                        .or(
                                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(pCandleBlock)
                                                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                                                .hasProperty(TallCandleBlock.CANDLE_PART, CandlePart.TALL))
                                                                        )
                                                        );
                                            }
                                        })
                        ))
                );
    }

    protected LootTable.Builder createItemDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)));
    }

    protected static LootTable.Builder createCandleCakeDrops(Block pCandleCakeBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(pCandleCakeBlock)));
    }

    protected LootTable.Builder createItemDropsFromShears(Block pBlock, Item item) {
        return createSilkTouchOrShearsDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        Stream<Block> blockStream = Stream.concat(
                WBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get),
                WBlocks.BLOCKS_S.getEntries().stream().map(RegistryObject::get)
        );
        return blockStream::iterator;
    }
}