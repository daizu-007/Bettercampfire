package com.daizuch.better_campfire;

import com.daizuch.better_campfire.TileEntity.KindlingBlockTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = BetterCampfire.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntitiesRegister {
    //TileEntityの定義
    public static final DeferredRegister<TileEntityType<?>> Tiles = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BetterCampfire.MOD_ID);
    //焚付のタイルエンティティ
    public static final RegistryObject<TileEntityType<KindlingBlockTileEntity>> KindlingBlockTileEntity=Tiles.register("kindling", () -> TileEntityType.Builder.create(KindlingBlockTileEntity::new, ItemRegister.KINDLING.get()).build(null));
    //TileEntityの追加
    public static void register(IEventBus eventBus) {
        Tiles.register(eventBus);
    }
}
