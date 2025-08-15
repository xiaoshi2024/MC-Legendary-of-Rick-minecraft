package com.xiaoshi2022.legendary_rick.Items.client.RickPortalGuns;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedGeoModel;

import static com.xiaoshi2022.legendary_rick.Legendary_Rick.MOD_ID;

public class RickPortalGunModel<T extends GeoAnimatable> extends DefaultedGeoModel<T> {
    /**
     * Create a new instance of this model class.<br>
     * The asset path should be the truncated relative path from the base folder.<br>
     * E.G.
     * <pre>{@code
     * 	new ResourceLocation("myMod", "armor/obsidian")
     * }</pre>
     */
    public RickPortalGunModel(ResourceLocation assetSubpath) {
        super(assetSubpath);
    }

    @Override
    protected String subtype() {
        return "item";
    }

    /**
     * Changes the constructor-defined model path for this model to an alternate.<br>
     * This is useful if your animatable shares a model path with another animatable that differs in path to the texture and animations for this model
     */
    @Override
    public RickPortalGunModel<T> withAltModel(ResourceLocation altPath) {
        return (RickPortalGunModel<T>)super.withAltModel(new ResourceLocation(MOD_ID,"geo/item/rick_portal_gun.geo.json"));
    }

    /**
     * Changes the constructor-defined animations path for this model to an alternate.<br>
     * This is useful if your animatable shares an animations path with another animatable that differs in path to the model and texture for this model
     */
    @Override
    public RickPortalGunModel<T> withAltAnimations(ResourceLocation altPath) {
        return (RickPortalGunModel<T>)super.withAltAnimations(new ResourceLocation(MOD_ID,"animations/item/rick_portal_gun.animation.json"));
    }

    /**
     * Changes the constructor-defined texture path for this model to an alternate.<br>
     * This is useful if your animatable shares a texture path with another animatable that differs in path to the model and animations for this model
     */
    @Override
    public RickPortalGunModel<T> withAltTexture(ResourceLocation altPath) {
        return (RickPortalGunModel<T>)super.withAltTexture(new ResourceLocation(MOD_ID,"textures/item/rick_portal_gun.png"));
    }
}
