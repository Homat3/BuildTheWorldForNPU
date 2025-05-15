package com.tf.npu.blocks.npublocknewclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class HorizontalDirectionalHalfSlab extends SlabBlock
{
    private boolean canBeDouble;
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    //构造
    public HorizontalDirectionalHalfSlab(Properties properties)
    {
        super(properties);
        canBeDouble = true;
    }
    //与构造并用
    public HorizontalDirectionalHalfSlab setCanBeDouble(boolean canBeDouble)
    {
        this.canBeDouble = canBeDouble;
        return this;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);
        FluidState fluidState = context.getLevel().getFluidState(pos);
        if (state.is(this)) {
            return state
                    .setValue(TYPE, canBeDouble ? SlabType.DOUBLE : Objects.requireNonNull(getOppositeSlabType(state.getValue(TYPE))))
                    .setValue(WATERLOGGED, !canBeDouble && fluidState.getType() == Fluids.WATER)
                    .setValue(FACING, context.getHorizontalDirection().getOpposite());
        } else {
            BlockState blockState = this.defaultBlockState()
                    .setValue(TYPE, SlabType.BOTTOM)
                    .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
            Direction direction = context.getClickedFace();
            return ((direction != Direction.DOWN) && (direction == Direction.UP || !(context.getClickLocation().y - (double)pos.getY() > 0.5)) ? blockState : blockState
                    .setValue(TYPE, SlabType.TOP))
                    .setValue(FACING, context.getHorizontalDirection().getOpposite());
        }
    }

    private SlabType getOppositeSlabType(SlabType current)
    {
        return switch (current)
        {
            case BOTTOM -> SlabType.TOP;
            case TOP -> SlabType.BOTTOM;
            case DOUBLE -> null;
        };
    }
}
