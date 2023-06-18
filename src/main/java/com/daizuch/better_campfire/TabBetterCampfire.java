package com.daizuch.better_campfire;


import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TabBetterCampfire {

    public static final ItemGroup BetterCampfireMod = new ItemGroup("BetterCampfireModTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemRegister.FIRE_WOOD.get());
        }
    };
}
