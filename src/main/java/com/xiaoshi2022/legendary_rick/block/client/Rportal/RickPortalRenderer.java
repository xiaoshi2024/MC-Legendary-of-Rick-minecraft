package com.xiaoshi2022.legendary_rick.block.client.Rportal;

import com.xiaoshi2022.legendary_rick.block.client.RickPortalBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RickPortalRenderer extends GeoBlockRenderer<RickPortalBlockEntity> {
    public RickPortalRenderer(BlockEntityRendererProvider.Context context) {
        super(new RickPortalModel());
    }

    @Override
    public GeoModel<RickPortalBlockEntity> getGeoModel() {
        return this.animatable.getCurrentModel();
    }
}
