package com.chromanyan.chromagadgets.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.ItemStack;

public class GoldenFoodOnAStickItem extends FoodOnAStickItem<Pig> {
    public GoldenFoodOnAStickItem() {
        super(new Properties().durability(25).tab(CreativeModeTab.TAB_TRANSPORTATION), EntityType.PIG, 5);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 7; // better enchantability
    }
}
