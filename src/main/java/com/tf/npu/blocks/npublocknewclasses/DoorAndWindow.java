package com.tf.npu.blocks.npublocknewclasses;

import com.tf.npu.blocks.NpuBlocks;
import com.tf.npu.blocks.dataofnpublocks.ShapeData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DoorAndWindow extends HorizontalDirectionalStructure {
    // 额外属性
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    protected boolean open;
    // 存储方块的形状
    ArrayList<VoxelShape> shapeList_open = new ArrayList<>(0);
    ArrayList<VoxelShape> shapeList_close = new ArrayList<>(0);

    // 构造
    public DoorAndWindow(Properties properties, NpuBlocks.LoadMethod loadMethod) {
        super(properties, loadMethod);
        this.open = false;
    }
    // 与构造并用设置形状
    public DoorAndWindow setShape(ShapeData shapeData1, ShapeData shapeData2) {
        setShape(shapeData1, shapeList_open);
        setShape(shapeData2, shapeList_close);

        return this;
    }

    // 额外属性注册
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
        super.createBlockStateDefinition(builder);
    }

    // 放置时状态
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(OPEN, false).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    // 设置形状
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pGetter, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        if (shape == null || direction != pState.getValue(FACING) || open != pState.getValue(OPEN)) {
            direction = pState.getValue(FACING);
            open = pState.getValue(OPEN);
            loadShape();
        }
        return shape;
    }

    // 被空手点击时
    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                                        @NotNull Player player, @NotNull BlockHitResult res) {
        state = state.cycle(OPEN);
        level.setBlock(pos, state, 10);
        level.gameEvent(player, state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return InteractionResult.SUCCESS;
    }

    // 辅助函数
    // 根据loadMethod加载形状
    private void loadShape() {
        ArrayList<VoxelShape> list = open ? shapeList_open : shapeList_close;
        shape = NpuBlocks.EmunShape.HALF_SHPAE_BOTTOM.getShape();
        if (!shape.isEmpty()) switch (loadMethod) {
            case METICULOUS:
                shape = NpuBlocks.EmunShape.NULL_SHPAE.getShape();
                for (VoxelShape voxelShape : list) {
                    shape = Shapes.or(shape, getShapeByDirection(voxelShape, direction));
                }
                break;
            case ROUGH:
                shape = list.getFirst();
                for (VoxelShape voxelShape : list) {
                    AABB a = shape.bounds();
                    AABB b = voxelShape.bounds();
                    shape = getShapeByDirection(Shapes.box(
                                    Math.min(a.minX, b.minX), Math.min(a.minY, b.minY), Math.min(a.minZ, b.minZ),
                                    Math.max(a.maxX, b.maxX), Math.max(a.maxY, b.maxY), Math.max(a.maxZ, b.maxZ)),
                            direction);
                }
                break;
        }
    }

}
