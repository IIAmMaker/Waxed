package net.im_maker.waxed.common.item;

import net.im_maker.waxed.Waxed;
import net.im_maker.waxed.common.block.WBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;

public class WItems {
    //Wax =]
    public static final Item WAX = registerItem("wax", new HoneycombItem(new Item.Properties()));
    //Waxed Coral Fans
    public static final Item WAXED_TUBE_CORAL_FAN = registerItem("waxed_tube_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_TUBE_CORAL_FAN, WBlocks.WAXED_TUBE_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_BRAIN_CORAL_FAN = registerItem("waxed_brain_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_BRAIN_CORAL_FAN, WBlocks.WAXED_BRAIN_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_BUBBLE_CORAL_FAN = registerItem("waxed_bubble_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_BUBBLE_CORAL_FAN, WBlocks.WAXED_BUBBLE_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_FIRE_CORAL_FAN = registerItem("waxed_fire_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_FIRE_CORAL_FAN, WBlocks.WAXED_FIRE_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_HORN_CORAL_FAN = registerItem("waxed_horn_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_HORN_CORAL_FAN, WBlocks.WAXED_HORN_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    //Upgrade Aquatic Waxed Coral Fans
    public static final Item WAXED_ACAN_CORAL_FAN = registerItem("waxed_acan_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_ACAN_CORAL_FAN, WBlocks.WAXED_ACAN_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_FINGER_CORAL_FAN = registerItem("waxed_finger_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_FINGER_CORAL_FAN, WBlocks.WAXED_FINGER_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_STAR_CORAL_FAN = registerItem("waxed_star_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_STAR_CORAL_FAN, WBlocks.WAXED_STAR_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_MOSS_CORAL_FAN = registerItem("waxed_moss_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_MOSS_CORAL_FAN, WBlocks.WAXED_MOSS_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_PETAL_CORAL_FAN = registerItem("waxed_petal_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_PETAL_CORAL_FAN, WBlocks.WAXED_PETAL_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_BRANCH_CORAL_FAN = registerItem("waxed_branch_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_BRANCH_CORAL_FAN, WBlocks.WAXED_BRANCH_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_ROCK_CORAL_FAN = registerItem("waxed_rock_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_ROCK_CORAL_FAN, WBlocks.WAXED_ROCK_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_PILLOW_CORAL_FAN = registerItem("waxed_pillow_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_PILLOW_CORAL_FAN, WBlocks.WAXED_PILLOW_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_SILK_CORAL_FAN = registerItem("waxed_silk_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_SILK_CORAL_FAN, WBlocks.WAXED_SILK_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_CHROME_CORAL_FAN = registerItem("waxed_chrome_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_CHROME_CORAL_FAN, WBlocks.WAXED_CHROME_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_PRISMARINE_CORAL_FAN = registerItem("waxed_prismarine_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_PRISMARINE_CORAL_FAN, WBlocks.WAXED_PRISMARINE_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));
    public static final Item WAXED_ELDER_PRISMARINE_CORAL_FAN = registerItem("waxed_elder_prismarine_coral_fan", new StandingAndWallBlockItem(WBlocks.WAXED_ELDER_PRISMARINE_CORAL_FAN, WBlocks.WAXED_ELDER_PRISMARINE_CORAL_WALL_FAN, new Item.Properties(), Direction.DOWN));

    public static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Waxed.MOD_ID, name), item);
    }

    public static void registerItems() {
        Waxed.LOGGER.info("Registering Mod Items for " + Waxed.MOD_ID);
    }
}