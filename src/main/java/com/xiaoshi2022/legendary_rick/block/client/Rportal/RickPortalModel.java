package com.xiaoshi2022.legendary_rick.block.client.Rportal; // 修正包路径

import com.xiaoshi2022.legendary_rick.block.client.RickPortalBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import static com.xiaoshi2022.legendary_rick.Legendary_Rick.MOD_ID;

public class RickPortalModel extends GeoModel<RickPortalBlockEntity> {
    // 模型资源路径
    private static final ResourceLocation MODEL =
            new ResourceLocation(MOD_ID, "geo/block/rick_portal.geo.json");

    // 纹理资源路径
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MOD_ID, "textures/block/rick_portal.png");

    // 动画资源路径
    private static final ResourceLocation ANIMATION =
            new ResourceLocation(MOD_ID, "animations/block/rick_portal.animation.json");

    @Override
    public ResourceLocation getModelResource(RickPortalBlockEntity animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(RickPortalBlockEntity animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(RickPortalBlockEntity animatable) {
        return ANIMATION;
    }

    @Override
    public RenderType getRenderType(RickPortalBlockEntity animatable, ResourceLocation texture) {
        // 使用半透明渲染类型
        return RenderType.entityTranslucent(texture);
    }
}