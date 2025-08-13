package com.xiaoshi2022.legendary_rick.entity.client.Rportal;

import com.xiaoshi2022.legendary_rick.Legendary_Rick;
import com.xiaoshi2022.legendary_rick.entity.RickPortalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RickPortalModel extends GeoModel<RickPortalEntity> {
    @Override
    public ResourceLocation getModelResource(RickPortalEntity entity) {
        return new ResourceLocation(Legendary_Rick.MODID, "geo/entity/rick_portal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RickPortalEntity entity) {
        return new ResourceLocation(Legendary_Rick.MODID, "textures/entity/rick_portal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RickPortalEntity entity) {
        return new ResourceLocation(Legendary_Rick.MODID, "animations/entity/rick_portal.animation.json");
    }
}