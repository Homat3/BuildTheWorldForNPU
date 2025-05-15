package com.tf.npu.blocks.npublocknewclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;
import java.util.Objects;

public class NormalHalfSlab extends SlabBlock {
    private boolean canBeDouble;

    //构造
    public NormalHalfSlab(Properties properties) {
        super(properties);
        canBeDouble = true;
    }

    //与构造并用
    public NormalHalfSlab setCanBeDouble(boolean canBeDouble) {
        this.canBeDouble = canBeDouble;
        return this;
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
                    .setValue(WATERLOGGED, !canBeDouble && fluidState.getType() == Fluids.WATER);
        } else {
            BlockState blockState = this.defaultBlockState()
                    .setValue(TYPE, SlabType.BOTTOM)
                    .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
            Direction direction = context.getClickedFace();
            return ((direction != Direction.DOWN) && (direction == Direction.UP || !(context.getClickLocation().y - (double) pos.getY() > 0.5)) ? blockState : blockState
                    .setValue(TYPE, SlabType.TOP));
        }
    }

    private SlabType getOppositeSlabType(SlabType current) {
        return switch (current) {
            case BOTTOM -> SlabType.TOP;
            case TOP -> SlabType.BOTTOM;
            case DOUBLE -> null;
        };
    }
}
