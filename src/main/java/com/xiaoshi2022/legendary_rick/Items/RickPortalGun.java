package com.xiaoshi2022.legendary_rick.Items;

import com.xiaoshi2022.legendary_rick.Items.client.RickPortalGuns.RickPortalGunRenderer;
import com.xiaoshi2022.legendary_rick.block.RickPortalBlock;
import com.xiaoshi2022.legendary_rick.register.ModBlocks;
import com.xiaoshi2022.legendary_rick.sound.ModSounds;
import com.xiaoshi2022.legendary_rick.util.PortalHub;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.joml.Vector3f;
import org.joml.Vector3fc;
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

        Vec3 start = player.getEyePosition(1F);
        Vec3 look  = player.getLookAngle();
        Vec3 end   = start.add(look.scale(8));//8格距离

        /* 1️⃣ 客户端：画绿色激光 */
        if (level.isClientSide) {
            drawGreenBeam(player, start, end);
        } else {
            /* 2️⃣ 服务端：射线判定 + 放置传送门 */
            HitResult res = level.clip(new ClipContext(start, end,
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (res.getType() == HitResult.Type.BLOCK) {
                BlockHitResult hit = (BlockHitResult) res;
                placePortal(level, hit.getBlockPos(), hit.getDirection(), player);
            }

            // 2. 服务端动画 & 音效
            triggerAnim(player, GeoItem.getOrAssignId(stack, (ServerLevel) level), "controller", "fire");
            level.playSound(null, player.blockPosition(),
                    ModSounds.PORTAL_GUN_SHOOTING.get(), SoundSource.PLAYERS, 1, 1);
        }
        return InteractionResultHolder.success(stack);
    }

    /* 绿色激光粒子 */
    private static void drawGreenBeam(Player player, Vec3 from, Vec3 to) {
        if (!(player.level() instanceof ClientLevel level)) return;

        final double STEP = 0.5;
        final Vector3f COLOR = new Vector3f(0.0F, 1.0F, 0.0F); // 纯绿
        final Vec3 dir = to.subtract(from).normalize();

        for (double d = 0; d <= from.distanceTo(to); d += STEP) {
            Vec3 pos = from.add(dir.scale(d));
            level.addParticle(new DustParticleOptions(COLOR, 1.0F),
                    pos.x, pos.y, pos.z, 0, 0, 0);
        }
    }
    private static void placePortal(Level level, BlockPos hitPos, Direction face, Player player) {
        if (level.isClientSide) return;

        // 1. 全球最多 2 个
        if (PortalHub.count() >= 2) {
            player.displayClientMessage(Component.literal("传送门数量已达上限！"), true);
            return;
        }

        // 2. 计算实际放置位置
        BlockPos placePos = hitPos.relative(face);
        if (!level.getBlockState(placePos).canBeReplaced()) return;

        // 3. 根据面选择方块
        Block blockToPlace = switch (face) {
            case UP    -> ModBlocks.RICK_PORTAL_FLOOR.get();
            case DOWN  -> ModBlocks.RICK_PORTAL_CEILING.get();
            default    -> ModBlocks.RICK_PORTAL.get();
        };

        BlockState state = face.getAxis().isHorizontal()
                ? blockToPlace.defaultBlockState()
                .setValue(RickPortalBlock.FACING, face)
                : blockToPlace.defaultBlockState();

        // 4. 放置并自动注册
        level.setBlock(placePos, state, 3);
        // 方块实体在 onLoad 里会把自己注册到 PortalHub
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