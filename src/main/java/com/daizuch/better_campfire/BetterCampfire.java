package com.daizuch.better_campfire;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BetterCampfire.MOD_ID)
public class BetterCampfire {
    //ModIDを設定
    public static final String MOD_ID = "better_campfire";
    //loggerの設定
    private static final Logger LOGGER = LogManager.getLogger();

    public BetterCampfire() {
        //EventBusの登録
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //アイテムの登録
        ItemRegister.register(modEventBus);
        //TileEntityの登録
        TileEntitiesRegister.register(modEventBus);
        //必須 ここまでのEventBusを登録
        MinecraftForge.EVENT_BUS.register(this);
    }
}
