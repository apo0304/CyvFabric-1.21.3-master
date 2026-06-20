package net.cyvfabric.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.cyvfabric.CyvFabric;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RenderLayers {
    public static final ResourceLocation LABELS_LAYER = ResourceLocation.fromNamespaceAndPath(CyvFabric.MOD_ID, "labels-layer");
    public static final ResourceLocation MACRO_LAYER = ResourceLocation.fromNamespaceAndPath(CyvFabric.MOD_ID, "macro-layer");

    public static final RenderType HIGHLIGHT_BOX_LAYER = new RenderType(
            CyvFabric.MOD_ID + "_highlight_box",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.TRIANGLE_STRIP,
            1536,
            false,
            true,
            () -> {
                RenderSystem.setShader(CoreShaders.POSITION_COLOR);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.disableCull();
            },
            () -> {
                RenderSystem.enableCull();
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();
                RenderSystem.disableBlend();
            }
    ) {};
}
