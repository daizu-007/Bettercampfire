package com.daizuch.better_campfire.Block;

import com.daizuch.better_campfire.ItemRegister;
import com.daizuch.better_campfire.TileEntity.KindlingBlockTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
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
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class KindlingBlock extends Block {
    //焚付の形状を設定
    private static final VoxelShape SHAPE = Block.makeCuboidShape(5, 0, 5, 11, 5, 11);

    //親クラスのBlockコンストラクタを呼び出す
    public KindlingBlock(Properties properties) {
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

    //TileEntityを持つことを示す
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true; // TileEntityを持つことを示す
    }

    //TileEntityの作成
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new KindlingBlockTileEntity(); // 新しいTileEntityのインスタンスを作成して返す
    }

    //右クリックされたときの処理
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        //手に持っているアイテムを取得
        ItemStack itemStack = player.getHeldItem(hand);
        //もし着火具なら
        if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
            //TileEntityを取得
            TileEntity tileEntity = world.getTileEntity(pos);
            //TileEntityがあれば
            if (tileEntity instanceof KindlingBlockTileEntity){
                //着火処理を実行
                ((KindlingBlockTileEntity) tileEntity).ignite();
                world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
    //灰に置き換える
    public static void replace_to_ash (World world, BlockPos pos) {
        //灰のブロックを作成
        BlockState ash = ItemRegister.ASH.get().getDefaultState();
        //灰のブロックに置き換える
        world.setBlockState(pos, ash);
    }
}