package net.im_maker.waxed.common.block.block_values;

import net.minecraft.util.StringRepresentable;

public enum CandlePart implements StringRepresentable {
    TALL("tall"),
    SHORT("short"),
    MIDDLE("middle");

    private final String name;

    CandlePart(String name) {
        this.name = name;
    }

    public String asString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}