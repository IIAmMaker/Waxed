package net.im_maker.waxed.common.block.block_values;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class WBlockStateProperties {
    public static final BooleanProperty MELTED = BooleanProperty.create("melted");
    public static final EnumProperty<CandlePart> CANDLE_PART = EnumProperty.create("part", CandlePart.class);
}

