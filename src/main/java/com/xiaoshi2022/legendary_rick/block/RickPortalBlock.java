package com.xiaoshi2022.legendary_rick.block;

import com.xiaoshi2022.legendary_rick.block.client.RickPortalBlockEntity;
import com.xiaoshi2022.legendary_rick.register.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RickPortalBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    /* 竖直贴墙：厚 2 格，高 32 格 */
    private static final VoxelShape SHAPE_NORTH = Shapes.create(Block.box(0, 0, 14, 16, 32, 16).bounds());
    private static final VoxelShape SHAPE_SOUTH = rotateHorizontal(SHAPE_NORTH, Direction.SOUTH);
    private static final VoxelShape SHAPE_EAST  = rotateHorizontal(SHAPE_NORTH, Direction.EAST);
    private static final VoxelShape SHAPE_WEST  = rotateHorizontal(SHAPE_NORTH, Direction.WEST);

    public RickPortalBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    /* ───────── 旋转工具 ───────── */
    private static VoxelShape rotateHorizontal(VoxelShape src, Direction target) {
        int times = (target.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) & 3;
        VoxelShape out = src;
        for (int i = 0; i < times; i++) {
            out = out.toAabbs().stream()
                    .map(box -> {
                        double minX = 1 - box.maxZ;
                        double minY = box.minY;
                        double minZ = box.minX;
                        double maxX = 1 - box.minZ;
                        double maxY = box.maxY;
                        double maxZ = box.maxX;
                        return Shapes.create(minX, minY, minZ, maxX, maxY, maxZ);
                    })
                    .reduce(Shapes.empty(), Shapes::or);
        }
        return out;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction face = ctx.getClickedFace();
        if (face == Direction.UP) {
            // 地板：直接返回变种方块状态
            return ModBlocks.RICK_PORTAL_FLOOR.get().defaultBlockState();
        }
        if (face == Direction.DOWN) {
            // 天花板：直接返回变种方块状态
            return ModBlocks.RICK_PORTAL_CEILING.get().defaultBlockState();
        }
        // 墙面：正常返回本类状态并设置朝向
        return this.defaultBlockState().setValue(FACING, face);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return switch (state.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST  -> SHAPE_EAST;
            case WEST  -> SHAPE_WEST;
            default    -> SHAPE_NORTH;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return Shapes.empty();
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return level.isClientSide ? null
                : (lvl, pos, stt, be) -> RickPortalBlockEntity.tick(lvl, pos, stt, (RickPortalBlockEntity) be);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RickPortalBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof RickPortalBlockEntity be) {
            return be.use(state, level, pos, player, hand, hit);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }
}