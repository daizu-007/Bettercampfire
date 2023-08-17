package com.daizuch.better_campfire.TileEntity;

import com.daizuch.better_campfire.Block.KindlingBlock;
import com.daizuch.better_campfire.ItemRegister;
import com.daizuch.better_campfire.TileEntitiesRegister;
import net.minecraft.block.BlockState;
import net.minecraft.client.audio.Sound;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class KindlingBlockTileEntity extends TileEntity implements ITickableTileEntity{
    //変数の設定
    public int burnTime = 0; // 燃えている時間
    public int maxBurnTime = 200; // 灰に変わるまでの時間
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
    @Override
    public void tick(){
        if(isBurning){
            burnTime++;
            world.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            world.addParticle(ParticleTypes.SMOKE, pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, 0.0D, 0.0D, 0.0D);
            if(burnTime >= maxBurnTime){
                KindlingBlock.replace_to_ash(Objects.requireNonNull(world), pos);
            }
        }
    }
    //着火処理
    public void ignite(){
        isBurning = true;
    }
}
