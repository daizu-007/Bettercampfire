package com.daizuch.better_campfire.Block;

import com.daizuch.better_campfire.ItemRegister;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class FireWoodBlock extends Block {
    //焚付の形状を設定
    private static final VoxelShape SHAPE = Block.makeCuboidShape(11, 0, 0, 15, 4, 16);

    //親クラスのBlockコンストラクタを呼び出す
    public FireWoodBlock(Properties properties) {
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
    //右クリックによる組み立て
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        //手に持っているアイテムを取得
        ItemStack itemStack = player.getHeldItem(hand);
        //薪の数を取得
        BlockState blockState = world.getBlockState(pos);
        //手に持っているアイテムが薪なら
        if (itemStack.getItem() == ItemRegister.FIRE_WOOD_ITEM.get()) {
            //薪の数が3つ以下なら
            if (blockState.get(BlockStateProperties.CHARGES) < 3) {
                //薪を1つ減らす
                itemStack.shrink(1);
                //薪を1つ追加する
                world.setBlockState(pos, state.with(BlockStateProperties.CHARGES, blockState.get(BlockStateProperties.CHARGES) + 1));
                //音を鳴らす
                world.playSound(player, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                //成功を返す
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}
