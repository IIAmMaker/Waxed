package net.im_maker.waxed.datagen;

import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.block.WBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Waxed.MOD_ID, exFileHelper);
    }

    private ResourceLocation blockR (Block block) {
        String id = BuiltInRegistries.BLOCK.getKey(block).getNamespace();
        String blockName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        return new ResourceLocation(id, "block/" + blockName);
    }
    private RegistryObject<Block> blockO (String block) {
        ResourceLocation blockLocation = new ResourceLocation(Waxed.MOD_ID, block);
        return RegistryObject.create(blockLocation, ForgeRegistries.BLOCKS);
    }

    @Override
    protected void registerStatesAndModels() {
        for (DyeColor color : DyeColor.values()) {
            blockWithItem(blockO(color + "_wax_block"));
            logBlock((RotatedPillarBlock) Waxed.getBlockFromString(color + "_wax_pillar"));
            blockWithItem(blockO("waxed_" + color + "_concrete_powder"));
        }
        blockWithItem(WBlocks.WAX_BLOCK);
        blockWithItem(WBlocks.SOUL_WAX_BLOCK);
        blockWithItem(WBlocks.CUPRIC_WAX_BLOCK);
        blockWithItem(WBlocks.ENDER_WAX_BLOCK);
        logBlock((RotatedPillarBlock) WBlocks.WAX_PILLAR.get());
        logBlock((RotatedPillarBlock) WBlocks.SOUL_WAX_PILLAR.get());
        logBlock((RotatedPillarBlock) WBlocks.CUPRIC_WAX_PILLAR.get());
        logBlock((RotatedPillarBlock) WBlocks.ENDER_WAX_PILLAR.get());
        blockWithItem(WBlocks.EMPTY_HONEYCOMB);
        blockWithItem(WBlocks.WAXED_SAND);
        blockWithItem(WBlocks.WAXED_RED_SAND);
        blockWithItem(WBlocks.WAXED_GRAVEL);
        blockWithItem(WBlocks.WAXED_POWDER_SNOW );
        blockWithItem(WBlocks.WAXED_ICE);
        blockWithItem(WBlocks.WAXED_MAGMA_BLOCK);
        blockWithItem(WBlocks.WAXED_SOUL_SAND);
        blockWithItem(WBlocks.WAXED_PRISMARINE);
        stairsBlock((StairBlock) WBlocks.WAXED_PRISMARINE_STAIRS.get(), blockR(Blocks.PRISMARINE));
        slabBlock((SlabBlock) WBlocks.WAXED_PRISMARINE_SLAB.get(), blockR(Blocks.PRISMARINE), blockR(Blocks.PRISMARINE));
        wallBlock((WallBlock) WBlocks.WAXED_PRISMARINE_WALL.get(), blockR(Blocks.PRISMARINE));
        blockWithItem(WBlocks.WAXED_SPONGE);
        blockWithItem(WBlocks.WAXED_REDSTONE_BLOCK);
        blockWithItem(WBlocks.WAXED_COBWEB);
    }

    private void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    private void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}