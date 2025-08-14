package com.xiaoshi2022.legendary_rick.block.client.Rportal;

import com.xiaoshi2022.legendary_rick.block.client.RickPortalBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib.model.GeoModel;

import static com.xiaoshi2022.legendary_rick.Legendary_Rick.MOD_ID;

// RickPortalFloorModel.java
public class RickPortalFloorModel extends GeoModel<RickPortalBlockEntity> {

    @Override
    public ResourceLocation getModelResource(RickPortalBlockEntity animatable) {
        return new ResourceLocation(MOD_ID, "geo/block/rick_portal_floor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RickPortalBlockEntity animatable) {
        return new ResourceLocation(MOD_ID, "textures/block/rick_portal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RickPortalBlockEntity animatable) {
        return new ResourceLocation(MOD_ID, "animations/block/rick_portal.animation.json");
    }
}
