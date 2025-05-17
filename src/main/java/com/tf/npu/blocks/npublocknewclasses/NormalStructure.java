package com.tf.npu.blocks.npublocknewclasses;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

public class NormalStructure extends Block{
    // 额外属性
    private static final MapCodec<NormalStructure> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    propertiesCodec(),
                    Codec.STRING.fieldOf("load_method").forGetter(p -> p.loadMethod.name())
            ).apply(instance, NormalStructure::new)
    );
    public NpuBlocks.LoadMethod loadMethod;
    // 体积
    public ArrayList<VoxelShape> shapeList;
    public VoxelShape shape;
    @Override
    protected @NotNull MapCodec<? extends NormalStructure> codec() {
        return CODEC;
    }

    // 构造
    public NormalStructure(BlockBehaviour.Properties properties, NpuBlocks.LoadMethod loadMethod) {
        super(properties);
        shapeList = new ArrayList<>(0);
        shape = null;
        this.loadMethod = loadMethod;
    }
    private NormalStructure(BlockBehaviour.Properties properties, String loadMethod){
        this(properties, NpuBlocks.LoadMethod.valueOf(loadMethod));
    }
    // 与构造并用设置形状
    public NormalStructure setShape(ShapeData shapeData) {
        setShape(shapeData, shapeList);

        return this;
    }
    protected void setShape(ShapeData shapeData, ArrayList<VoxelShape> shapeList) {
        if (!shapeData.loaderIsObj()) for (List<Double> shape : shapeData.getShapeList()) {
            shapeList.add(Shapes.box(shape.get(0), shape.get(1), shape.get(2), shape.get(3), shape.get(4), shape.get(5)));
        }
    }

    // 设置形状
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pGetter, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        if (shape == null) {
            loadShape();
        }
        return shape.optimize();
    }

    // 根据loadMethod加载形状
    private void loadShape() {
        shape = NpuBlocks.EmunShape.HALF_SHPAE_BOTTOM.getShape();
        if (!shapeList.isEmpty()) switch (loadMethod) {
            case METICULOUS:
                shape = NpuBlocks.EmunShape.NULL_SHPAE.getShape();
                for (VoxelShape voxelShape : shapeList) {
                    shape = Shapes.or(shape, voxelShape);
                }
                break;
            case ROUGH:
                shape = shapeList.getFirst();
                for (VoxelShape voxelShape : shapeList) {
                    AABB a = shape.bounds();
                    AABB b = voxelShape.bounds();
                    shape = Shapes.box(
                            Math.min(a.minX, b.minX), Math.min(a.minY, b.minY), Math.min(a.minZ, b.minZ),
                            Math.max(a.maxX, b.maxX), Math.max(a.maxY, b.maxY), Math.max(a.maxZ, b.maxZ));
                }
                break;
        }
    }
}
