package com.tf.npu.blocks.npublocknewclasses;

import com.tf.npu.blocks.NpuBlocks;
import com.tf.npu.blocks.dataofnpublocks.ShapeData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NormalStructure extends Block
{
    //体积
    public ArrayList<VoxelShape> shapeList;
    public VoxelShape shape;
    //加载方式
    public NpuBlocks.LoadMethod loadMethod;

    //构造
    public NormalStructure(BlockBehaviour.Properties properties, NpuBlocks.LoadMethod loadMethod)
    {
        super(properties);
        shapeList = new ArrayList<>(0);
        shape = null;
        this.loadMethod = loadMethod;
    }
    //与构造并用
    public NormalStructure setSHAPE(ShapeData shapeData)
    {
        if (!shapeData.loaderIsObj()) for (List<Double> shape : shapeData.getShapeList())
        {
            shapeList.add(Shapes.box(shape.get(0), shape.get(1), shape.get(2), shape.get(3), shape.get(4), shape.get(5)));
        }

        return this;
    }

    private void loadShape()
    {
        shape = NpuBlocks.EmunShape.HALF_SHPAE_BOTTOM.getShape();
        if (!shapeList.isEmpty()) switch (loadMethod)
        {
            case METICULOUS:
                shape = NpuBlocks.EmunShape.NULL_SHPAE.getShape();
                for (VoxelShape voxelShape : shapeList)
                {
                    shape = Shapes.or(shape, voxelShape);
                }
                break;
            case ROUGH:
                shape = shapeList.get(0);
                for (VoxelShape voxelShape : shapeList)
                {
                    AABB a = shape.bounds();
                    AABB b = voxelShape.bounds();
                    shape = Shapes.box(
                            Math.min(a.minX, b.minX), Math.min(a.minY, b.minY), Math.min(a.minZ, b.minZ),
                            Math.max(a.maxX, b.maxX), Math.max(a.maxY, b.maxY), Math.max(a.maxZ, b.maxZ));
                }
                break;
        }
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pGetter, @NotNull BlockPos pPos, @NotNull CollisionContext pContext)
    {
        if (shape == null)
        {
            loadShape();
        }
        return shape.optimize();
    }

}
