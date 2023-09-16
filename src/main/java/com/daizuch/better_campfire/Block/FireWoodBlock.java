package com.daizuch.better_campfire.Block;

import com.daizuch.better_campfire.ItemRegister;
import com.mojang.datafixers.types.templates.Tag;
import net.minecraft.block.*;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class FireWoodBlock extends Block {
    //このクラスではキャンプファイアーの組み立ての土台となる薪（を並べたもの）を定義する

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

    //薪の形状を設定
    //makeCuboidShapeメソッドは、引数に与えられた座標を頂点とする立方体の形状を作成する
    private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 7, 16);

    //薪の形状を適用
    public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    //薪の下にブロックがないなら破壊
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    //薪の設置条件を設定
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
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
        LOGGER.info(ItemRegister.FIRE_WOOD_ITEM.get().getTranslationKey());
        //手に持っているアイテムを取得
        ItemStack itemstack = player.getHeldItem(hand);
        //もし手に持っているアイテムが薪なら
        if (itemstack.getItem() == ItemRegister.FIRE_WOOD_ITEM.get()){
            if (charges != 4) {
                //薪をひとつ増やす
                world.setBlockState(pos, state.with(FireWoodBlock.CHARGES, Integer.valueOf(state.get(FireWoodBlock.CHARGES) + 1)));
                world.playSound(player, pos, SoundType.WOOD.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                //もしクリエイティブモードでないなら
                if (!player.abilities.isCreativeMode) {
                    //手に持っているアイテムをひとつ減らす
                    itemstack.shrink(1);
                }
                //薪をひとつ増やしたので終了
                //LOGGER.info("charges: " + charges);
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.PASS;
            //もし手に持っているアイテムが木炭なら
        } else if (itemstack.getItem().getTags() == Items.CHARCOAL.getTags() || itemstack.getItem().getTags() == Items.COAL.getTags()) {
            //LOGGER.info("item is charcoal");
            //もし薪が4つなら
            if (charges == 4) {
                //LOGGER.info("item is charcoal");
                //消灯したキャンプファイヤーに置き換える
                world.setBlockState(pos, Blocks.CAMPFIRE.getBlock().getDefaultState().with(CampfireBlock.LIT, Boolean.valueOf(false)));
                world.playSound(player, pos, SoundType.SAND.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                //もしクリエイティブモードでないなら
                if (!player.abilities.isCreativeMode) {
                    //手に持っているアイテムをひとつ減らす
                    itemstack.shrink(1);
                }
                return ActionResultType.SUCCESS;
            }//もし手に持っているアイテムがソウルサンドなら
        } else if (itemstack.getItem() == Items.SOUL_SAND) {
            //LOGGER.info("item is soul sand");
            //もし薪が4つなら
            if (charges == 4) {
                //LOGGER.info("item is soul sand");
                //消灯したソウルキャンプファイヤーに置き換える
                world.setBlockState(pos, Blocks.SOUL_CAMPFIRE.getBlock().getDefaultState().with(CampfireBlock.LIT, Boolean.valueOf(false)));
                world.playSound(player, pos, SoundType.SAND.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                //もしクリエイティブモードでないなら
                if (!player.abilities.isCreativeMode) {
                    //手に持っているアイテムをひとつ減らす
                    itemstack.shrink(1);
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}
