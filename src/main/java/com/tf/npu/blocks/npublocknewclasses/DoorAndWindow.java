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
import java.util.List;

public class DoorAndWindow extends HorizontalDirectionalStructure
{
    //体积映射
    private final ArrayList<VoxelShape> shapeList_open;
    private final ArrayList<VoxelShape> shapeList_close;

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    protected boolean open;

    //构造
    public DoorAndWindow(Properties properties, NpuBlocks.LoadMethod loadMethod)
    {
        super(properties, loadMethod);
        shapeList_open = new ArrayList<>(0);
        shapeList_close = new ArrayList<>(0);
        open = false;
    }
    //与构造并用
    public DoorAndWindow setSHAPE(ShapeData openShapeData, ShapeData closeShapeData)
    {
        if (!openShapeData.loaderIsObj()) for (List<Double> shape : openShapeData.getShapeList())
        {
            shapeList_open.add(Shapes.box(shape.get(0), shape.get(1), shape.get(2), shape.get(3), shape.get(4), shape.get(5)));
        }
        if (!closeShapeData.loaderIsObj()) for (List<Double> shape : closeShapeData.getShapeList())
        {
            shapeList_close.add(Shapes.box(shape.get(0), shape.get(1), shape.get(2), shape.get(3), shape.get(4), shape.get(5)));
        }

        return this;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(OPEN);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState().setValue(OPEN, false).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    private void loadShape()
    {
        ArrayList<VoxelShape> shapeList = open ? shapeList_open : shapeList_close;
        shape = NpuBlocks.EmunShape.HALF_SHPAE_BOTTOM.getShape();
        if(!shapeList.isEmpty()) switch (loadMethod)
        {
            case METICULOUS:
                shape = NpuBlocks.EmunShape.NULL_SHPAE.getShape();
                for (VoxelShape voxelShape : shapeList)
                {
                    shape = Shapes.or(shape, getShapeByDirection(voxelShape, direction));
                }
                break;
            case ROUGH:
                shape = shapeList.get(0);
                for (VoxelShape voxelShape : shapeList)
                {
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

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pGetter, @NotNull BlockPos pPos, @NotNull CollisionContext pContext)
    {
        if (shape == null || direction != pState.getValue(FACING) || open != pState.getValue(OPEN))
        {
            direction = pState.getValue(FACING);
            open = pState.getValue(OPEN);
            loadShape();
        }
        return shape;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                                        @NotNull Player player, @NotNull BlockHitResult res) {
        state = state.cycle(OPEN);
        level.setBlock(pos, state, 10);
        level.gameEvent(player, state.getValue(OPEN) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return InteractionResult.SUCCESS;
    }
}
