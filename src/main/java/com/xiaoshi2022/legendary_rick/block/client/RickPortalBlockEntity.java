package com.xiaoshi2022.legendary_rick.block.client;

import com.xiaoshi2022.legendary_rick.block.client.Rportal.RickPortalCeilingModel;
import com.xiaoshi2022.legendary_rick.block.client.Rportal.RickPortalFloorModel;
import com.xiaoshi2022.legendary_rick.block.client.Rportal.RickPortalModel;
import com.xiaoshi2022.legendary_rick.register.ModBlocks;
import com.xiaoshi2022.legendary_rick.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RickPortalBlockEntity extends BlockEntity implements GeoBlockEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final RawAnimation SHOW = RawAnimation.begin().thenPlay("show");

    /* -------------------------------------------------- */
    /* 1. 运行时缓存：当前方块形态 + 对应模型                */
    public final Variant variant;
    public final GeoModel<RickPortalBlockEntity> currentModel;

    /* -------------------------------------------------- */

    public enum Variant {
        WALL, FLOOR, CEILING
    }

    public RickPortalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RICK_PORTAL.get(), pos, state);

        Block block = state.getBlock();
        if (block == ModBlocks.RICK_PORTAL_FLOOR.get()) {
            variant      = Variant.FLOOR;
            currentModel = MODEL_FLOOR;
        } else if (block == ModBlocks.RICK_PORTAL_CEILING.get()) {
            variant      = Variant.CEILING;
            currentModel = MODEL_CEIL;
        } else {
            variant      = Variant.WALL;
            currentModel = MODEL_WALL;
        }
    }

    /* -------------------------------------------------- */
    /* 2. 静态共享模型，避免到处 new                      */
    public static final GeoModel<RickPortalBlockEntity> MODEL_WALL  = new RickPortalModel();
    public static final GeoModel<RickPortalBlockEntity> MODEL_FLOOR = new RickPortalFloorModel();
    public static final GeoModel<RickPortalBlockEntity> MODEL_CEIL  = new RickPortalCeilingModel();
    /* -------------------------------------------------- */

    /* GeoBlockEntity */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5,
                state -> state.setAndContinue(SHOW)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    /* 供渲染器直接拿模型 */
    public GeoModel<RickPortalBlockEntity> getCurrentModel() {
        return currentModel;
    }

    /* 其余交互、传送逻辑保持不变 */
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) teleportPlayer(player);
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private void teleportPlayer(Player player) {
        // TODO: 实现传送
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }
}