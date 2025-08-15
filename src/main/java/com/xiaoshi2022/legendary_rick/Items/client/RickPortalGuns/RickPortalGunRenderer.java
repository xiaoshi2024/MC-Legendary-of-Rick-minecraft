package com.xiaoshi2022.legendary_rick.Items.client.RickPortalGuns;

import com.xiaoshi2022.legendary_rick.Items.RickPortalGun;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import static com.xiaoshi2022.legendary_rick.Legendary_Rick.MOD_ID;

public class RickPortalGunRenderer extends GeoItemRenderer<RickPortalGun> {
    public RickPortalGunRenderer() {
        super(new RickPortalGunModel<>(new ResourceLocation(MOD_ID,"rick_portal_gun")));
    }
}
