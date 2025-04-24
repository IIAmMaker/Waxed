package net.im_maker.waxed;

import com.ninni.dye_depot.registry.DDDyes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.im_maker.waxed.common.block.WBlocks;
import net.im_maker.waxed.common.item.WItems;
import net.im_maker.waxed.common.particles.WParticles;
import net.im_maker.waxed.common.player.interactions.BlockInteractionHandler;
import net.im_maker.waxed.common.player.interactions.ShiningEffectForWaxedBlocks;
import net.im_maker.waxed.common.player.interactions.WaxingBlocks;
import net.im_maker.waxed.common.sounds.WSounds;
import net.im_maker.waxed.common.util.DataPackRegistrar;
import net.im_maker.waxed.common.util.WRecipeSerializers;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.mehvahdjukaar.suppsquared.SuppSquared;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Waxed implements ModInitializer {
	public static final String MOD_ID = "waxed";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final Function<ItemLike, ItemStack> FUNCTION = ItemStack::new;
	//public static WaxedServerConfigs CONFIG;
	public static List<DyeColor> customColorOrder = new ArrayList<>(Arrays.asList(
			DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
			DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
			DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
			DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
	));
	public static List<DyeColor> customColorOrderR = new ArrayList<>(customColorOrder);
	static {
		Collections.reverse(customColorOrderR);
	}

	@Override
	public void onInitialize() {
		isDyeDepotLoaded(FabricLoader.getInstance().isModLoaded("dye_depot"));
		DataPackRegistrar.loadBuiltinResourcePacks();
		WParticles.registerParticles();
		WBlocks.registerBlocks();
		WItems.registerItems();
		WSounds.registerSoundEvents();
		WRecipeSerializers.registerRecipeSerializers();
		WaxingBlocks.init();
		ShiningEffectForWaxedBlocks.register();
		BlockInteractionHandler.register();
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(Waxed::addToColoredBlocksTap);
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(Waxed::addToNaturalBlocksTap);
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(Waxed::addToFunctionalBlocksTap);
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(Waxed::addToBuildingBlocksTap);
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(Waxed::addToIngredientsTap);
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
		} else {
			customColorOrder = customColorOrder;
			customColorOrderR = customColorOrderR;
		}
	}

	public static void addToColoredBlocksTap(FabricItemGroupEntries entries) {
		if (FabricLoader.getInstance().isModLoaded("supplementaries")) {
			entries.addBefore(ModRegistry.CANDLE_HOLDERS.get(DyeColor.WHITE).get(), WBlocks.SOUL_CANDLE_HOLDER);
		}
		if (FabricLoader.getInstance().isModLoaded("suppsquared")) {
			entries.addBefore(SuppSquared.GOLDEN_CANDLE_HOLDERS.get(DyeColor.WHITE).get(), WBlocks.GOLD_SOUL_CANDLE_HOLDER);
		}
		entries.addAfter(Items.CANDLE, WBlocks.SOUL_CANDLE);
		entries.addAfter(Items.PINK_CANDLE, WBlocks.TALL_CANDLE);
		entries.addAfter(WBlocks.TALL_CANDLE, WBlocks.SOUL_TALL_CANDLE);
		for (DyeColor color : customColorOrderR) {
			entries.addAfter(WBlocks.SOUL_TALL_CANDLE, getBlockFromString(color + "_tall_candle"));
		}
		entries.addBefore(Items.CANDLE,
				WBlocks.WAX_BLOCK,
				WBlocks.SOUL_WAX_BLOCK,
				WBlocks.WAX_PILLAR,
				WBlocks.SOUL_WAX_PILLAR
		);
		for (DyeColor color : customColorOrderR) {
			entries.addAfter(WBlocks.SOUL_WAX_BLOCK, getBlockFromString(color + "_wax_block"));
			entries.addAfter(WBlocks.SOUL_WAX_PILLAR, getBlockFromString(color + "_wax_pillar"));
			entries.addAfter(Items.PINK_CONCRETE_POWDER, getBlockFromString("waxed_" + color + "_concrete_powder"));
		}
	}

	public static void addToNaturalBlocksTap(FabricItemGroupEntries entries) {
		entries.addAfter(Items.HORN_CORAL_BLOCK,
					WBlocks.WAXED_TUBE_CORAL_BLOCK,
					WBlocks.WAXED_BRAIN_CORAL_BLOCK,
					WBlocks.WAXED_BUBBLE_CORAL_BLOCK,
					WBlocks.WAXED_FIRE_CORAL_BLOCK,
					WBlocks.WAXED_HORN_CORAL_BLOCK);
		entries.addAfter(Items.HORN_CORAL,
					WBlocks.WAXED_TUBE_CORAL,
					WBlocks.WAXED_BRAIN_CORAL,
					WBlocks.WAXED_BUBBLE_CORAL,
					WBlocks.WAXED_FIRE_CORAL,
					WBlocks.WAXED_HORN_CORAL);
		entries.addAfter(Items.HORN_CORAL_FAN,
					WBlocks.WAXED_TUBE_CORAL_FAN,
					WBlocks.WAXED_BRAIN_CORAL_FAN,
					WBlocks.WAXED_BUBBLE_CORAL_FAN,
					WBlocks.WAXED_FIRE_CORAL_FAN,
					WBlocks.WAXED_HORN_CORAL_FAN);
	}

	public static void addToFunctionalBlocksTap(FabricItemGroupEntries entries) {
		if (FabricLoader.getInstance().isModLoaded("supplementaries")) {
			entries.addBefore(ModRegistry.CANDLE_HOLDERS.get(DyeColor.WHITE).get(), WBlocks.SOUL_CANDLE_HOLDER);
		}
		if (FabricLoader.getInstance().isModLoaded("suppsquared")) {
			entries.addBefore(SuppSquared.GOLDEN_CANDLE_HOLDERS.get(DyeColor.WHITE).get(), WBlocks.GOLD_SOUL_CANDLE_HOLDER);
		}
		entries.addAfter(Items.CANDLE, WBlocks.SOUL_CANDLE);
		entries.addAfter(Items.PINK_CANDLE, WBlocks.TALL_CANDLE);
		entries.addAfter(WBlocks.TALL_CANDLE, WBlocks.SOUL_TALL_CANDLE);
		for (DyeColor color : customColorOrderR) {
			entries.addAfter(WBlocks.SOUL_TALL_CANDLE, getBlockFromString(color + "_tall_candle"));
		}
	}

	public static void addToBuildingBlocksTap(FabricItemGroupEntries entries) {
		entries.addAfter(Items.WAXED_OXIDIZED_CUT_COPPER_SLAB,
				WBlocks.WAXED_SAND,
				WBlocks.WAXED_RED_SAND,
				WBlocks.WAXED_GRAVEL,
				WBlocks.WAXED_ICE,
				WBlocks.WAXED_SPONGE);
		if (FabricLoader.getInstance().isModLoaded("supplementaries")){
			entries.addAfter(WBlocks.WAXED_SPONGE,
					//WBlocks.WAXED_SOAP_BLOCK,
					WBlocks.WAXED_SUGAR_CUBE,
					WBlocks.WAXED_RAKED_GRAVEL);
		}
	}

	public static void addToIngredientsTap(FabricItemGroupEntries entries) {
		entries.addAfter(Items.STRING, WBlocks.WICK);
		entries.addAfter(Items.HONEYCOMB, WItems.WAX);
	}

	public static Block getBlockFromString (String block) {
		return getBlockFromString(MOD_ID, block);
	}
	public static Block getBlockFromString (String nameSpace, String block) {
		ResourceLocation blockLocation = new ResourceLocation(nameSpace, block);
		return BuiltInRegistries.BLOCK.get(blockLocation);
	}

	public static Item getItemFromString (String item) {
		return getItemFromString(MOD_ID, item);
	}
	public static Item getItemFromString (String nameSpace, String item) {
		ResourceLocation itemLocation = new ResourceLocation(nameSpace, item);
		return BuiltInRegistries.ITEM.get(itemLocation);
	}
}