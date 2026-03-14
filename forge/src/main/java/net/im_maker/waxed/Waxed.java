package net.im_maker.waxed;

import com.ninni.dye_depot.registry.DDDyes;
import com.teamabnormals.buzzier_bees.core.registry.BBBlocks;
import com.teamabnormals.upgrade_aquatic.core.registry.UABlocks;
import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.item.WItems;
import net.im_maker.waxed.common.particles.WParticles;
import net.im_maker.waxed.common.util.WRecipeSerializers;
import net.im_maker.waxed.common.sounds.WSounds;
import net.im_maker.waxed.config.WaxedAndShinyClientConfig;
import net.im_maker.waxed.config.WaxedAndShinyConfig;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.mehvahdjukaar.suppsquared.SuppSquared;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Mod(Waxed.MOD_ID)
public class Waxed {
    public static final String MOD_ID = "waxed";
    private static final Function<ItemLike, ItemStack> FUNCTION = ItemStack::new;
    public static List<DyeColor> customColorOrder = new ArrayList<>(Arrays.asList(
            DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
            DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
            DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
            DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
    ));
    public static List<DyeColor> customColorOrderR = new ArrayList<>(customColorOrder);
    static {Collections.reverse(customColorOrderR);}
    public Waxed() {
        isDyeDepotLoaded(ModList.get().isLoaded("dye_depot"));
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WaxedAndShinyConfig.SPEC, "waxed_and_shiny-common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, WaxedAndShinyClientConfig.SPEC, "waxed_and_shiny-client.toml");
        WParticles.register(modEventBus);
        WBlocks.register(modEventBus);
        WItems.register(modEventBus);
        WSounds.register(modEventBus);
        WRecipeSerializers.RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        modEventBus.addListener(this::addCreative);
    }

    public static void isDyeDepotLoaded(Boolean b) {
        if (b) {
            customColorOrder = new ArrayList<>(Arrays.asList(
                    DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
                    DyeColor.BROWN, DDDyes.MAROON.get(), DDDyes.ROSE.get(), DyeColor.RED,
                    DDDyes.CORAL.get(), DDDyes.GINGER.get(), DyeColor.ORANGE, DDDyes.TAN.get(),
                    DDDyes.BEIGE.get(), DyeColor.YELLOW, DDDyes.AMBER.get(), DDDyes.OLIVE.get(),
                    DyeColor.LIME, DDDyes.FOREST.get(), DyeColor.GREEN, DDDyes.VERDANT.get(),
                    DDDyes.TEAL.get(), DyeColor.CYAN, DDDyes.MINT.get(), DDDyes.AQUA.get(),
                    DyeColor.LIGHT_BLUE, DyeColor.BLUE, DDDyes.SLATE.get(), DDDyes.NAVY.get(),
                    DDDyes.INDIGO.get(), DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
            ));
            customColorOrderR = new ArrayList<>(customColorOrder);
            Collections.reverse(customColorOrderR);
        }
    }

    private static void addAfter(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike after, ItemLike... blocks) {
        for (int i = blocks.length - 1; i >= 0; i--) {
            ItemLike block = blocks[i];
            if (block != null) {
                map.putAfter(FUNCTION.apply(after), FUNCTION.apply(block), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }

    private static void addBefore(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike before, ItemLike... blocks) {
        for (ItemLike block : blocks) {
            if (block != null) {
                map.putBefore(FUNCTION.apply(before), FUNCTION.apply(block), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = event.getEntries();
        entries.remove(new ItemStack(Blocks.HONEYCOMB_BLOCK));
        if (ModList.get().isLoaded("buzzier_bees") && event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS && WaxedAndShinyConfig.CANDLE_VARIANTS.get()) {
            entries.remove(new ItemStack(BBBlocks.SOUL_CANDLE.get()));
            entries.remove(new ItemStack(BBBlocks.CUPRIC_CANDLE.get()));
            entries.remove(new ItemStack(BBBlocks.ENDER_CANDLE.get()));
        }
        if (ModList.get().isLoaded("supplementaries") && WaxedAndShinyConfig.CANDLE_VARIANTS.get()) {
            if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
                addBefore(entries, ModRegistry.CANDLE_HOLDERS.get(DyeColor.WHITE).get(), WBlocks.SOUL_CANDLE_HOLDER.get());
                if (ModList.get().isLoaded("endergetic")) {
                    addAfter(entries, WBlocks.SOUL_CANDLE_HOLDER.get(), WBlocks.ENDER_CANDLE_HOLDER.get());
                }
                if (ModList.get().isLoaded("caverns_and_chasms")) {
                    addAfter(entries, WBlocks.SOUL_CANDLE_HOLDER.get(), WBlocks.CUPRIC_CANDLE_HOLDER.get());
                }
            }
            if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
                addBefore(entries, ModRegistry.CANDLE_HOLDERS.get(DyeColor.WHITE).get(), WBlocks.SOUL_CANDLE_HOLDER.get());
                if (ModList.get().isLoaded("endergetic")) {
                    addAfter(entries, WBlocks.SOUL_CANDLE_HOLDER.get(), WBlocks.ENDER_CANDLE_HOLDER.get());
                }
                if (ModList.get().isLoaded("caverns_and_chasms")) {
                    addAfter(entries, WBlocks.SOUL_CANDLE_HOLDER.get(), WBlocks.CUPRIC_CANDLE_HOLDER.get());
                }
            }
        }
        if (ModList.get().isLoaded("suppsquared") && WaxedAndShinyConfig.CANDLE_VARIANTS.get()) {
            if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
                addBefore(entries, SuppSquared.GOLDEN_CANDLE_HOLDERS.get(DyeColor.WHITE).get(), WBlocks.GOLD_SOUL_CANDLE_HOLDER.get());
                if (ModList.get().isLoaded("endergetic")) {
                    addAfter(entries, WBlocks.GOLD_SOUL_CANDLE_HOLDER.get(), WBlocks.GOLD_ENDER_CANDLE_HOLDER.get());
                }
                if (ModList.get().isLoaded("caverns_and_chasms")) {
                    addAfter(entries, WBlocks.GOLD_SOUL_CANDLE_HOLDER.get(), WBlocks.GOLD_CUPRIC_CANDLE_HOLDER.get());
                }
            }
            if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
                addBefore(entries, SuppSquared.GOLDEN_CANDLE_HOLDERS.get(DyeColor.WHITE).get(), WBlocks.GOLD_SOUL_CANDLE_HOLDER.get());
                if (ModList.get().isLoaded("endergetic")) {
                    addAfter(entries, WBlocks.GOLD_SOUL_CANDLE_HOLDER.get(), WBlocks.GOLD_ENDER_CANDLE_HOLDER.get());
                }
                if (ModList.get().isLoaded("caverns_and_chasms")) {
                    addAfter(entries, WBlocks.GOLD_SOUL_CANDLE_HOLDER.get(), WBlocks.GOLD_CUPRIC_CANDLE_HOLDER.get());
                }
            }
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            if (WaxedAndShinyConfig.CANDLE_VARIANTS.get()) {
                addAfter(entries, Items.CANDLE, WBlocks.SOUL_CANDLE.get());
                if (ModList.get().isLoaded("endergetic")) {
                    addAfter(entries, WBlocks.SOUL_CANDLE.get(), WBlocks.ENDER_CANDLE.get());
                }
                if (ModList.get().isLoaded("caverns_and_chasms")) {
                    addAfter(entries, WBlocks.SOUL_CANDLE.get(), WBlocks.CUPRIC_CANDLE.get());
                }
            }
            if (WaxedAndShinyConfig.TALL_CANDLES.get()) {
                addAfter(entries, Items.PINK_CANDLE, WBlocks.TALL_CANDLE.get());
                for (DyeColor color : customColorOrderR) {
                    addAfter(entries, WBlocks.TALL_CANDLE.get(), getBlockFromString(color + "_tall_candle"));
                }
                if (WaxedAndShinyConfig.CANDLE_VARIANTS.get()) {
                    addAfter(entries, WBlocks.TALL_CANDLE.get(), WBlocks.SOUL_TALL_CANDLE.get());
                    if (ModList.get().isLoaded("endergetic")) {
                        addAfter(entries, WBlocks.SOUL_TALL_CANDLE.get(), WBlocks.ENDER_TALL_CANDLE.get());
                    }
                    if (ModList.get().isLoaded("caverns_and_chasms")) {
                        addAfter(entries, WBlocks.SOUL_TALL_CANDLE.get(), WBlocks.CUPRIC_TALL_CANDLE.get());
                    }
                }
            }
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS && WaxedAndShinyConfig.WAX_AND_WAX_BLOCKS.get()) {
            addAfter(entries, Items.STRING, WBlocks.WICK.get());
            addAfter(entries, Items.HONEYCOMB, WItems.WAX.get());
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS && WaxedAndShinyConfig.WAXED_BLOCKS.get()) {
            addAfter(entries, Items.WAXED_OXIDIZED_CUT_COPPER_SLAB,
                    WBlocks.WAXED_SAND.get(),
                    WBlocks.WAXED_RED_SAND.get(),
                    WBlocks.WAXED_GRAVEL.get(),
                    WBlocks.WAXED_ICE.get());
            if (ModList.get().isLoaded("supplementaries")){
                addAfter(entries, WBlocks.WAXED_ICE.get(),
                        WBlocks.WAXED_SUGAR_CUBE.get(),
                        WBlocks.WAXED_RAKED_GRAVEL.get());
            }
            if (ModList.get().isLoaded("atmospheric")){
                addAfter(entries, WBlocks.WAXED_ICE.get(),
                        WBlocks.WAXED_ARID_SAND.get(),
                        WBlocks.WAXED_RED_ARID_SAND.get());
            }
            if (ModList.get().isLoaded("oreganized")){
                addAfter(entries, Waxed.getBlockFromString("oreganized", "grooved_ice"), WBlocks.WAXED_GROOVED_ICE.get());
            }
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            if (WaxedAndShinyConfig.WAX_AND_WAX_BLOCKS.get()) addBefore(entries, Blocks.SLIME_BLOCK, WBlocks.EMPTY_HONEYCOMB.get());
            if (WaxedAndShinyConfig.WAXED_BLOCKS.get())
            if (ModList.get().isLoaded("upgrade_aquatic")) {
                addAfter(entries, UABlocks.PRISMARINE_CORAL_BLOCK.get(),
                        WBlocks.WAXED_TUBE_CORAL_BLOCK.get(),
                        WBlocks.WAXED_BRAIN_CORAL_BLOCK.get(),
                        WBlocks.WAXED_BUBBLE_CORAL_BLOCK.get(),
                        WBlocks.WAXED_FIRE_CORAL_BLOCK.get(),
                        WBlocks.WAXED_HORN_CORAL_BLOCK.get(),
                        WBlocks.WAXED_ACAN_CORAL_BLOCK.get(),
                        WBlocks.WAXED_FINGER_CORAL_BLOCK.get(),
                        WBlocks.WAXED_STAR_CORAL_BLOCK.get(),
                        WBlocks.WAXED_MOSS_CORAL_BLOCK.get(),
                        WBlocks.WAXED_PETAL_CORAL_BLOCK.get(),
                        WBlocks.WAXED_BRANCH_CORAL_BLOCK.get(),
                        WBlocks.WAXED_ROCK_CORAL_BLOCK.get(),
                        WBlocks.WAXED_PILLOW_CORAL_BLOCK.get(),
                        WBlocks.WAXED_SILK_CORAL_BLOCK.get(),
                        WBlocks.WAXED_CHROME_CORAL_BLOCK.get(),
                        WBlocks.WAXED_PRISMARINE_CORAL_BLOCK.get(),
                        WBlocks.WAXED_ELDER_PRISMARINE_CORAL_BLOCK.get());
                addAfter(entries, UABlocks.PRISMARINE_CORAL.get(),
                        WBlocks.WAXED_TUBE_CORAL.get(),
                        WBlocks.WAXED_BRAIN_CORAL.get(),
                        WBlocks.WAXED_BUBBLE_CORAL.get(),
                        WBlocks.WAXED_FIRE_CORAL.get(),
                        WBlocks.WAXED_HORN_CORAL.get(),
                        WBlocks.WAXED_ACAN_CORAL.get(),
                        WBlocks.WAXED_FINGER_CORAL.get(),
                        WBlocks.WAXED_STAR_CORAL.get(),
                        WBlocks.WAXED_MOSS_CORAL.get(),
                        WBlocks.WAXED_PETAL_CORAL.get(),
                        WBlocks.WAXED_BRANCH_CORAL.get(),
                        WBlocks.WAXED_ROCK_CORAL.get(),
                        WBlocks.WAXED_PILLOW_CORAL.get(),
                        WBlocks.WAXED_SILK_CORAL.get(),
                        WBlocks.WAXED_CHROME_CORAL.get(),
                        WBlocks.WAXED_PRISMARINE_CORAL.get(),
                        WBlocks.WAXED_ELDER_PRISMARINE_CORAL.get());
                addAfter(entries, UABlocks.PRISMARINE_CORAL_FAN.get(),
                        WItems.WAXED_TUBE_CORAL_FAN.get(),
                        WItems.WAXED_BRAIN_CORAL_FAN.get(),
                        WItems.WAXED_BUBBLE_CORAL_FAN.get(),
                        WItems.WAXED_FIRE_CORAL_FAN.get(),
                        WItems.WAXED_HORN_CORAL_FAN.get(),
                        WItems.WAXED_ACAN_CORAL_FAN.get(),
                        WItems.WAXED_FINGER_CORAL_FAN.get(),
                        WItems.WAXED_STAR_CORAL_FAN.get(),
                        WItems.WAXED_MOSS_CORAL_FAN.get(),
                        WItems.WAXED_PETAL_CORAL_FAN.get(),
                        WItems.WAXED_BRANCH_CORAL_FAN.get(),
                        WItems.WAXED_ROCK_CORAL_FAN.get(),
                        WItems.WAXED_PILLOW_CORAL_FAN.get(),
                        WItems.WAXED_SILK_CORAL_FAN.get(),
                        WItems.WAXED_CHROME_CORAL_FAN.get(),
                        WItems.WAXED_PRISMARINE_CORAL_FAN.get(),
                        WItems.WAXED_ELDER_PRISMARINE_CORAL_FAN.get());
                addAfter(entries, UABlocks.PRISMARINE_CORAL_SHOWER.get(), WBlocks.WAXED_PRISMARINE_CORAL_SHOWER.get());
                addAfter(entries, UABlocks.ELDER_PRISMARINE_CORAL_SHOWER.get(), WBlocks.WAXED_ELDER_PRISMARINE_CORAL_SHOWER.get());
            } else {
                addAfter(entries, Items.HORN_CORAL_BLOCK,
                        WBlocks.WAXED_TUBE_CORAL_BLOCK.get(),
                        WBlocks.WAXED_BRAIN_CORAL_BLOCK.get(),
                        WBlocks.WAXED_BUBBLE_CORAL_BLOCK.get(),
                        WBlocks.WAXED_FIRE_CORAL_BLOCK.get(),
                        WBlocks.WAXED_HORN_CORAL_BLOCK.get());
                addAfter(entries, Items.HORN_CORAL,
                        WBlocks.WAXED_TUBE_CORAL.get(),
                        WBlocks.WAXED_BRAIN_CORAL.get(),
                        WBlocks.WAXED_BUBBLE_CORAL.get(),
                        WBlocks.WAXED_FIRE_CORAL.get(),
                        WBlocks.WAXED_HORN_CORAL.get());
                addAfter(entries, Items.HORN_CORAL_FAN,
                        WBlocks.WAXED_TUBE_CORAL_FAN.get(),
                        WBlocks.WAXED_BRAIN_CORAL_FAN.get(),
                        WBlocks.WAXED_BUBBLE_CORAL_FAN.get(),
                        WBlocks.WAXED_FIRE_CORAL_FAN.get(),
                        WBlocks.WAXED_HORN_CORAL_FAN.get());
            }
        }
        if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            if (WaxedAndShinyConfig.WAXED_BLOCKS.get()) {
                for (DyeColor color : customColorOrderR) {
                    addAfter(entries, Items.PINK_CONCRETE_POWDER, getBlockFromString("waxed_" + color + "_concrete_powder"));
                }
            }
            if (WaxedAndShinyConfig.CANDLE_VARIANTS.get()) {
                addAfter(entries, Items.CANDLE, WBlocks.SOUL_CANDLE.get());
                if (ModList.get().isLoaded("endergetic")) {
                    addAfter(entries, WBlocks.SOUL_CANDLE.get(), WBlocks.ENDER_CANDLE.get());
                }
                if (ModList.get().isLoaded("caverns_and_chasms")) {
                    addAfter(entries, WBlocks.SOUL_CANDLE.get(), WBlocks.CUPRIC_CANDLE.get());
                }
            }
            if (WaxedAndShinyConfig.TALL_CANDLES.get()) {
                addAfter(entries, Items.PINK_CANDLE, WBlocks.TALL_CANDLE.get());
                for (DyeColor color : customColorOrderR) {
                    addAfter(entries, WBlocks.TALL_CANDLE.get(), getBlockFromString(color + "_tall_candle"));
                }
                if (WaxedAndShinyConfig.CANDLE_VARIANTS.get()) {
                    addAfter(entries, WBlocks.TALL_CANDLE.get(), WBlocks.SOUL_TALL_CANDLE.get());
                    if (ModList.get().isLoaded("endergetic")) {
                        addAfter(entries, WBlocks.SOUL_TALL_CANDLE.get(), WBlocks.ENDER_TALL_CANDLE.get());
                    }
                    if (ModList.get().isLoaded("caverns_and_chasms")) {
                        addAfter(entries, WBlocks.SOUL_TALL_CANDLE.get(), WBlocks.CUPRIC_TALL_CANDLE.get());
                    }
                }
            }
            if (WaxedAndShinyConfig.WAX_AND_WAX_BLOCKS.get()) {
                addBefore(entries, Items.CANDLE,
                        WBlocks.WAX_BLOCK.get(),
                        WBlocks.WAX_PILLAR.get());
                for (DyeColor color : customColorOrderR) {
                    addAfter(entries, WBlocks.WAX_BLOCK.get(), getBlockFromString(color + "_wax_block"));
                    addAfter(entries, WBlocks.WAX_PILLAR.get(), getBlockFromString(color + "_wax_pillar"));
                }
                if (WaxedAndShinyConfig.CANDLE_VARIANTS.get()) {
                    addBefore(entries, Items.CANDLE,
                            WBlocks.SOUL_WAX_BLOCK.get(),
                            WBlocks.SOUL_WAX_PILLAR.get());
                    if (ModList.get().isLoaded("endergetic")) {
                        addAfter(entries, WBlocks.SOUL_WAX_BLOCK.get(), WBlocks.ENDER_WAX_BLOCK.get());
                        addAfter(entries, WBlocks.SOUL_WAX_PILLAR.get(), WBlocks.ENDER_WAX_PILLAR.get());
                    }
                    if (ModList.get().isLoaded("caverns_and_chasms")) {
                        addAfter(entries, WBlocks.SOUL_WAX_BLOCK.get(), WBlocks.CUPRIC_WAX_BLOCK.get());
                        addAfter(entries, WBlocks.SOUL_WAX_PILLAR.get(), WBlocks.CUPRIC_WAX_PILLAR.get());
                    }
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            RenderType cutoutRenderType = RenderType.cutout();
            ItemBlockRenderTypes.setRenderLayer(WBlocks.SOUL_CANDLE_HOLDER.get(), cutoutRenderType);
            ItemBlockRenderTypes.setRenderLayer(WBlocks.GOLD_SOUL_CANDLE_HOLDER.get(), cutoutRenderType);
            ItemBlockRenderTypes.setRenderLayer(WBlocks.CUPRIC_CANDLE_HOLDER.get(), cutoutRenderType);
            ItemBlockRenderTypes.setRenderLayer(WBlocks.GOLD_CUPRIC_CANDLE_HOLDER.get(), cutoutRenderType);
            ItemBlockRenderTypes.setRenderLayer(WBlocks.ENDER_CANDLE_HOLDER.get(), cutoutRenderType);
            ItemBlockRenderTypes.setRenderLayer(WBlocks.GOLD_ENDER_CANDLE_HOLDER.get(), cutoutRenderType);
        }


        @SubscribeEvent
        public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
            // Registering particle factories should only happen on the client.
            // Using Minecraft.getInstance() on the server will crash, so this stays client-side.
            Minecraft.getInstance().particleEngine.register(WParticles.SMALL_SOUL_FLAME.get(), FlameParticle.SmallFlameProvider::new);
            Minecraft.getInstance().particleEngine.register(WParticles.SMALL_CUPRIC_FLAME.get(), FlameParticle.SmallFlameProvider::new);
            Minecraft.getInstance().particleEngine.register(WParticles.SMALL_ENDER_FLAME.get(), FlameParticle.SmallFlameProvider::new);
            Minecraft.getInstance().particleEngine.register(WParticles.CUPRIC_FLAME.get(), FlameParticle.Provider::new);
            Minecraft.getInstance().particleEngine.register(WParticles.ENDER_FLAME.get(), FlameParticle.Provider::new);
        }
    }



    public static Block getBlockFromString (String block) {
        return getBlockFromString(MOD_ID, block);
    }
    public static Block getBlockFromString (String nameSpace, String block) {
        ResourceLocation blockLocation = new ResourceLocation(nameSpace, block);
        return ForgeRegistries.BLOCKS.getValue(blockLocation);
    }

    public static Item getItemFromString (String item) {
        return getItemFromString(MOD_ID, item);
    }
    public static Item getItemFromString (String nameSpace, String item) {
        ResourceLocation itemLocation = new ResourceLocation(nameSpace, item);
        return ForgeRegistries.ITEMS.getValue(itemLocation);
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
