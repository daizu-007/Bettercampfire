package com.daizuch.better_campfire.TileEntity;

import com.daizuch.better_campfire.ItemRegister;
import com.daizuch.better_campfire.TileEntitiesRegister;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class KindlingBlockTileEntity extends TileEntity {
    //変数の設定
    public int burnTime = 0; // 燃えている時間
    public int maxBurnTime = 100; // 灰に変わるまでの時間
    public boolean isBurning = false; // 燃えているかどうか
    //変数をNBTに保存
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putBoolean("isBurning", this.isBurning);
        return compound;
    }
    //NBTから変数を読み込み
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.isBurning = nbt.getBoolean("isBurning");
    }
    public KindlingBlockTileEntity() {
        super(TileEntitiesRegister.KindlingBlockTileEntity.get());
    }
    //燃焼処理
    public void tick(){
        if(isBurning){
            burnTime++;
            if(burnTime >= maxBurnTime){
                isBurning = false;
                burnTime = 0;
                maxBurnTime = 100;
                world.setBlockState(pos, ItemRegister.ASH.get().getDefaultState());
            }
        }
    }
    //着火処理
    public void ignite(){
        isBurning = true;
    }
}
