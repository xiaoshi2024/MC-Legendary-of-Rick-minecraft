package com.xiaoshi2022.legendary_rick.entity.client.Rportal;

import com.xiaoshi2022.legendary_rick.entity.RickPortalEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RickPortalRenderer extends GeoEntityRenderer<RickPortalEntity> {
    public RickPortalRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new RickPortalModel());
    }
}
