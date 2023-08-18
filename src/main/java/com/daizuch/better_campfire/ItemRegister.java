package com.daizuch.better_campfire;

import com.daizuch.better_campfire.Block.AshBlock;
import com.daizuch.better_campfire.Block.KindlingBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister {
    //アイテムとブロックの定義
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterCampfire.MOD_ID);
    private static final DeferredRegister<Block> BLOCKS= DeferredRegister.create(ForgeRegistries.BLOCKS, BetterCampfire.MOD_ID);
    //ここから下でブロックを追加
    //焚付
    public static final RegistryObject<Block> KINDLING= BLOCKS.register("kindling", () -> {return new KindlingBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().zeroHardnessAndResistance().notSolid().tickRandomly());});
    //灰
    public static final RegistryObject<Block> ASH= BLOCKS.register("ash", () -> {return new AshBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).notSolid().harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND));});
    //薪
    public static final RegistryObject<Block> FIRE_WOOD= BLOCKS.register("fire_wood", () -> {return new Block(AbstractBlock.Properties.create(Material.WOOD).notSolid().harvestTool(ToolType.AXE).harvestLevel(0));});

    //ここから下でアイテムを追加
    //薪
    public static final RegistryObject<Item> FIRE_WOOD_ITEM= ITEMS.register("fire_wood", () -> {return new BlockItem(FIRE_WOOD.get(),new Item.Properties().group(TabBetterCampfire.BetterCampfireMod));});
    //焚付
    public static final RegistryObject<Item> KINDLING_ITEM= ITEMS.register("kindling", () -> {return new BlockItem(KINDLING.get(),new Item.Properties().group(TabBetterCampfire.BetterCampfireMod));});
    //木炭の欠片
    public static final RegistryObject<Item> PIECE_OF_CHARCOAL= ITEMS.register("piece_of_charcoal", () -> {return new Item((new Item.Properties()).group(TabBetterCampfire.BetterCampfireMod));});


    //アイテムとブロックの追加
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
    }
}
