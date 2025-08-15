package com.xiaoshi2022.legendary_rick.Items;

import com.xiaoshi2022.legendary_rick.Items.client.RickPortalGuns.RickPortalGunRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class RickPortalGun extends Item implements GeoItem {
    private static final RawAnimation FIRE = RawAnimation.begin().thenPlay("fire");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public RickPortalGun(Properties properties) {
        super(properties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private RickPortalGunRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new RickPortalGunRenderer();

                return this.renderer;
            }
        });
    }

    /* 右键事件 */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // 1. 服务端逻辑
        if (!level.isClientSide) {
            triggerAnim(player, GeoItem.getOrAssignId(stack, (ServerLevel) level), "controller", "fire");
            level.playSound(null, player.blockPosition(),
                    SoundEvents.PORTAL_TRIGGER, SoundSource.PLAYERS, 1, 1);
        }

        // 2. 返回成功
        return InteractionResultHolder.success(stack);
    }

    /* GeckoLib 动画注册 */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, state -> PlayState.STOP)
                .triggerableAnim("fire", FIRE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}