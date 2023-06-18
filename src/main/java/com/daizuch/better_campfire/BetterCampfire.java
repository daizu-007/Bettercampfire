package com.daizuch.better_campfire;


import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(BetterCampfire.MOD_ID)
public class BetterCampfire {
    //ModIDを設定
    public static final String MOD_ID = "better_campfire";

    public BetterCampfire() {
        //EventBus(?)の登録
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //アイテムの登録
        ItemRegister.register(modEventBus);
        //必須
        MinecraftForge.EVENT_BUS.register(this);
    }
}
