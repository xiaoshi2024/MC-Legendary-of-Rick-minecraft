package com.xiaoshi2022.legendary_rick.Items;

import com.xiaoshi2022.legendary_rick.block.RickPortalBlock;
import com.xiaoshi2022.legendary_rick.util.PortalVariant;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class RickPortalItem extends Item {
    private static final String TAG_VARIANT = "PortalVariant";

    public RickPortalItem(Properties props) {
        super(props);
    }

    /* ========= 1. 右键空气时循环切换 ========= */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player,
                                                  InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && player.isShiftKeyDown()) {
            cycleVariant(stack);
            player.displayClientMessage(
                    Component.literal("Mode: " + getVariant(stack).getSerializedName()), true);
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(stack);
    }

    /* ========= 2. 右键方块时放置对应形态 ========= */
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        Player player = ctx.getPlayer();
        ItemStack stack = ctx.getItemInHand();
        if (player == null || level.isClientSide) return InteractionResult.PASS;

        PortalVariant variant = getVariant(stack);
        BlockPos pos = ctx.getClickedPos().relative(ctx.getClickedFace());
        BlockState state = getPlacementState(level, pos, variant, ctx.getClickedFace());

        if (state == null || !level.getBlockState(pos).canBeReplaced()) {
            return InteractionResult.FAIL;
        }

        level.setBlock(pos, state, 3);
        SoundType sound = state.getSoundType(level, pos, player);
        level.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS,
                (sound.getVolume() + 1f) / 2f, sound.getPitch() * 0.8f);

        if (!player.getAbilities().instabuild) stack.shrink(1);
        return InteractionResult.SUCCESS;
    }

    /* ========= 3. 工具方法 ========= */
    private static PortalVariant getVariant(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return PortalVariant.fromName(tag.getString(TAG_VARIANT));
    }

    private static void cycleVariant(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        PortalVariant current = PortalVariant.fromName(tag.getString(TAG_VARIANT));
        tag.putString(TAG_VARIANT, current.next().getSerializedName());
    }

    /* 根据点击方向修正 state（同你原来的逻辑） */
    @Nullable
    private BlockState getPlacementState(Level level, BlockPos pos,
                                         PortalVariant variant, Direction face) {
        Block block = variant.getBlock();
        if (face == Direction.UP && variant == PortalVariant.FLOOR) {
            return block.defaultBlockState();
        }
        if (face == Direction.DOWN && variant == PortalVariant.CEILING) {
            return block.defaultBlockState();
        }
        if (face.getAxis().isHorizontal() && variant == PortalVariant.WALL) {
            return block.defaultBlockState()
                    .setValue(RickPortalBlock.FACING, face);
        }
        return null; // 方向与形态不匹配
    }

    /* 方便查看当前模式 */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Mode: " + getVariant(stack).getSerializedName())
                .withStyle(ChatFormatting.GRAY));
    }
}