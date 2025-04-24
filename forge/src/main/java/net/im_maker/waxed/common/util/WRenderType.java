package net.im_maker.waxed.common.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class WRenderType extends RenderType{

    private static final Function<ResourceLocation, RenderType> SHINING = Util.memoize((p_286174_) -> {
        RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(p_286174_, false, false);
        return create("shining", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_CRUMBLING_SHADER).setTextureState(renderstateshard$texturestateshard).setTransparencyState(CRUMBLING_TRANSPARENCY).setWriteMaskState(COLOR_WRITE).setLayeringState(POLYGON_OFFSET_LAYERING).createCompositeState(false));
    });

    public WRenderType(String s, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean b, boolean b1, Runnable runnable, Runnable runnable1) {
        super(s, vertexFormat, mode, i, b, b1, runnable, runnable1);
    }

    public static RenderType shining(ResourceLocation resourceLocation) {
        return SHINING.apply(resourceLocation);
    }
}