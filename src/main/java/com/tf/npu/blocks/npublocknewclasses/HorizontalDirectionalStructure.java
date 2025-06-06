package com.tf.npu.blocks.npublocknewclasses;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tf.npu.blocks.NpuBlocks;
import com.tf.npu.blocks.dataofnpublocks.ShapeData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HorizontalDirectionalStructure extends HorizontalDirectionalBlock {
    // 额外属性
    private static final MapCodec<HorizontalDirectionalStructure> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    propertiesCodec(),
                    Codec.STRING.fieldOf("load_method").forGetter(p -> p.loadMethod.name()),
                    Codec.STRING.fieldOf("direction").forGetter(p -> p.direction.name())
            ).apply(instance, HorizontalDirectionalStructure::new)
    );
    protected NpuBlocks.LoadMethod loadMethod;
    // 存储方块的形状
    ArrayList<VoxelShape> shapeList = new ArrayList<>(0);
    protected VoxelShape shape;
    // 当前方向
    protected Direction direction;
    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    // 构造
    public HorizontalDirectionalStructure(BlockBehaviour.Properties properties, NpuBlocks.LoadMethod loadMethod) {
        super(properties);
        this.shape = null;
        this.direction = Direction.NORTH;
        this.loadMethod = loadMethod;
    }
    private HorizontalDirectionalStructure(BlockBehaviour.Properties properties, String loadMethod, String direction) {
        super(properties);
        this.shape = null;
        this.direction = Direction.valueOf(direction);
        this.loadMethod = NpuBlocks.LoadMethod.valueOf(loadMethod);
    }
    // 与构造并用设置形状
    public HorizontalDirectionalStructure setShape(ShapeData shapeData) {
        setShape(shapeData, shapeList);

        return this;
    }
    protected void setShape(ShapeData shapeData, ArrayList<VoxelShape> shapeList) {
        if (!shapeData.loaderIsObj()) for (List<Double> shape : shapeData.getShapeList()) {
            shapeList.add(Shapes.box(shape.get(0), shape.get(1), shape.get(2), shape.get(3), shape.get(4), shape.get(5)));
        }
    }

    // 额外属性注册
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    // 放置时状态
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    // 设置形状
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pGetter, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        if (shape == null || direction != pState.getValue(FACING)) {
            direction = pState.getValue(FACING);
            loadShape();
        }
        return shape;
    }

    // 辅助函数
    // 旋转坐标变换
    protected VoxelShape getShapeByDirection(VoxelShape shape, Direction direction) {
        Double[] pos = new Double[6];
        {
            pos[0] = shape.bounds().minX;
            pos[1] = shape.bounds().minY;
            pos[2] = shape.bounds().minZ;
            pos[3] = shape.bounds().maxX;
            pos[4] = shape.bounds().maxY;
            pos[5] = shape.bounds().maxZ;
        }

        return switch (direction) {
            //坐标变化表
            case NORTH -> Shapes.box(pos[0], pos[1], pos[2], pos[3], pos[4], pos[5]);
            case SOUTH -> Shapes.box(1 - pos[3], pos[1], 1 - pos[5], 1 - pos[0], pos[4], 1 - pos[2]);
            case EAST -> Shapes.box(1 - pos[5], pos[1], pos[0], 1 - pos[2], pos[4], pos[3]);
            case WEST -> Shapes.box(pos[2], pos[1], 1 - pos[3], pos[5], pos[4], 1 - pos[0]);
            default -> shape;
        };
    }
    // 根据loadMethod加载形状
    private void loadShape() {
        shape = NpuBlocks.EmunShape.HALF_SHPAE_BOTTOM.getShape();
        if (!shapeList.isEmpty()) switch (loadMethod) {
            case METICULOUS:
                shape = NpuBlocks.EmunShape.NULL_SHPAE.getShape();
                for (VoxelShape voxelShape : shapeList) {
                    shape = Shapes.or(shape, getShapeByDirection(voxelShape, direction));
                }
                break;
            case ROUGH:
                shape = shapeList.getFirst();
                for (VoxelShape voxelShape : shapeList) {
                    AABB a = shape.bounds();
                    AABB b = voxelShape.bounds();
                    shape = Shapes.box(
                            Math.min(a.minX, b.minX), Math.min(a.minY, b.minY), Math.min(a.minZ, b.minZ),
                            Math.max(a.maxX, b.maxX), Math.max(a.maxY, b.maxY), Math.max(a.maxZ, b.maxZ)
                    );
                }
                shape = getShapeByDirection(shape, direction);
                break;
        }
    }
}
