package com.daizuch.better_campfire.Block;

import com.daizuch.better_campfire.ItemRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class FireWoodBlock extends Block {
    //このクラスではキャンプファイアーの組み立ての土台となるファイアーピット（木炭を並べたもの）を定義する

    //薪の数を表す変数
    public static final IntegerProperty CHARGES = IntegerProperty.create("charges", 1, 4);

    @Override
    //block stateを設定
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CHARGES);
    }

    //親クラスのBlockコンストラクタを呼び出す。コンストラクタとはクラスをオブジェクト化するためのメソッド
    public FireWoodBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FireWoodBlock.CHARGES, Integer.valueOf(1)));
    }

    //ファイアーピットの形状を設定
    //makeCuboidShapeメソッドは、引数に与えられた座標を頂点とする立方体の形状を作成する
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 1, 16, 8, 15);

    //ファイアーピットの形状を適用
    public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    //mobが通れるようにする
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, MobEntity entity) {
        return PathNodeType.OPEN;
    }

    //右クリック時の処理
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        Integer charges = state.get(FireWoodBlock.CHARGES);
        //手に持っているアイテムを取得
        ItemStack itemstack = player.getHeldItem(hand);
        //もし手に持っているアイテムが薪か木炭なら
        if (itemstack.getItem() == ItemRegister.FIRE_WOOD_ITEM.get() || itemstack.getItem() == Items.CHARCOAL) {
            if (charges != 4) {
                //薪をひとつ増やす
                world.setBlockState(pos, state.with(FireWoodBlock.CHARGES, Integer.valueOf(state.get(FireWoodBlock.CHARGES) + 1)));
                //もしクリエイティブモードでないなら
                if (!player.abilities.isCreativeMode) {
                    //手に持っているアイテムをひとつ減らす
                    itemstack.shrink(1);
                }
                //薪をひとつ増やしたので終了
                LOGGER.info("charges: " + charges);
                return ActionResultType.SUCCESS;
            } else {
                LOGGER.info("charges is not 4");
                //もし手に持っているアイテムが木炭なら
                if (itemstack.getItem() == Items.CHARCOAL){
                    LOGGER.info("item is charcoal");
                    //消灯したキャンプファイヤーに置き換える
                    world.setBlockState(pos, Blocks.CAMPFIRE.getBlock().getDefaultState().with(CampfireBlock.LIT, Boolean.valueOf(false)));
                    //もしクリエイティブモードでないなら
                    if (!player.abilities.isCreativeMode) {
                        //手に持っているアイテムをひとつ減らす
                        itemstack.shrink(1);
                    }
                    return ActionResultType.SUCCESS;
                }
                return ActionResultType.PASS;
            }
        }else {
            return ActionResultType.PASS;
        }
    }
}
