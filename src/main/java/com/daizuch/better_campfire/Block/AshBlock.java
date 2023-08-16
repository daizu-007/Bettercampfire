package com.daizuch.better_campfire.Block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class AshBlock extends Block {
    //焚付の形状を設定
    private static final VoxelShape SHAPE = Block.makeCuboidShape(5, 0, 5, 11, 5, 11);
    //親クラスのBlockコンストラクタを呼び出す
    public AshBlock(Properties properties) {
        super(properties);
    }
    //焚付の形状を適用
    public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
    //mobが通れるようにする
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, MobEntity entity) {
        return PathNodeType.OPEN;
    }

    //ピストンで押されたら破壊する
    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}
